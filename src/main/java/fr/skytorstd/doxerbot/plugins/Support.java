package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.SupportCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.ErrorMessages;
import fr.skytorstd.doxerbot.messages.SupportMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class Support extends ListenerAdapter {

    public Support() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/support [action]*");
        Plugin plugin = new Plugin(PluginName.SUPPORT.getMessage(), SupportMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("support")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.SUPPORT.getMessage(), e.getGuild().getId())){
                String action = e.getOption("action").getAsString();

                if(action.equals("ticket-message")){
                    e.getChannel().sendMessageEmbeds(SupportCrafter.craftTicketCreatorEmbed()).addActionRow(Button.primary("support-ticket-button", "\uD83D\uDCE8 Création de ticket")).queue();
                    e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionOnly(":white_check_mark: " + SupportMessages.TICKET_CREATE_MESSAGE_CREATE.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.SUPPORT.getMessage(), SupportMessages.TICKET_CREATE_MESSAGE_CREATE.getMessage(), e.getGuild(), e.getMember(), true);
                }else{
                    e.replyEmbeds(ErrorCrafter.errorCommand()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.SUPPORT.getMessage(), ErrorMessages.ERROR_COMMAND.getErrorMessage(), e.getGuild(), e.getMember(), false);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.SUPPORT)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.SUPPORT.getMessage(), ErrorMessages.ERROR_CONFIGURATION_MESSAGE.getErrorMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        if(e.getComponent().getId().equals("support-ticket-button")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.SUPPORT.getMessage(), e.getGuild().getId())){
                Member member = e.getMember();
                assert member != null;
                createTicket(e.getGuild(), e.getChannel().asTextChannel().getParentCategory(), member);

                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + SupportMessages.TICKET_CREATE_SUCCESS.getMessage(), Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.SUPPORT.getMessage(), SupportMessages.TICKET_CREATE_SUCCESS.getMessage(), e.getGuild(), e.getMember(), true);
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.SUPPORT)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.SUPPORT.getMessage(), ErrorMessages.ERROR_CONFIGURATION_MESSAGE.getErrorMessage() + " (`"+PluginName.SUPPORT.getMessage()+"`)", e.getGuild(), e.getMember(), false);
            }
        }
    }

    private static void createTicket(Guild guild, Category category, Member member){
        String titleTicket = "";
        if(member.getNickname() != null)
            titleTicket = "\uD83D\uDCE8-ticket-" + member.getNickname();
        else
            titleTicket = "\uD83D\uDCE8-ticket-" + member.getEffectiveName();

        EnumSet<Permission> allowMember = EnumSet.of(Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_HISTORY, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_SEND);
        EnumSet<Permission> denyMember = EnumSet.of(Permission.MESSAGE_MANAGE);
        Role memberRole = guild.getRoleById(ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(guild.getId()).getIdrUser());
        EnumSet<Permission> denyRole = EnumSet.of(Permission.VIEW_CHANNEL);
        EnumSet<Permission> allowStaff = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_HISTORY, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_SEND, Permission.ADMINISTRATOR, Permission.MESSAGE_MANAGE);
        ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(guild.getId());
        Long idModo = Long.parseLong(configurationGuild.getIdrModerateur());
        Long idAdmin = Long.parseLong(configurationGuild.getIdrAdmin());
        Long idSudo = Long.parseLong(configurationGuild.getIdrSudo());
        guild.createTextChannel(titleTicket, category).addRolePermissionOverride(Long.parseLong(memberRole.getId()), null, denyRole).addMemberPermissionOverride(Long.parseLong(member.getId()), allowMember, denyMember).addRolePermissionOverride(idModo, allowStaff, null).addRolePermissionOverride(idAdmin, allowStaff, null).addRolePermissionOverride(idSudo, allowStaff, null).queue();
    }
}
