package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.DiscordSecurityMessages;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DiscordSecurity extends ListenerAdapter {

    public DiscordSecurity() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/confirm [member] [rôle] [rename]*");
        Plugin plugin = new Plugin(PluginName.DISCORDSECURITY.getMessage(), DiscordSecurityMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("confirm")) {
            if (ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDSECURITY.getMessage(), e.getGuild().getId())) {
                ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());
                Role sudo = e.getGuild().getRoleById(configurationGuild.getIdrSudo());
                Role admin = e.getGuild().getRoleById(configurationGuild.getIdrAdmin());
                Role modo = e.getGuild().getRoleById(configurationGuild.getIdrModerateur());

                if(e.getMember().getRoles().contains(sudo) || e.getMember().getRoles().contains(admin) || e.getMember().getRoles().contains(modo)){
                    String utilisateurId = e.getOption("utilisateur").getAsString();
                    Role userRole = e.getGuild().getRoleById(configurationGuild.getIdrUser());

                    e.getGuild().retrieveMemberById(utilisateurId).queue(member -> {
                        if(!member.getRoles().contains(userRole)){
                            e.getGuild().addRoleToMember(member, userRole).queue();

                            if(e.getOption("name") != null){
                                String rename = e.getOption("name").getAsString();
                                member.modifyNickname(rename).queue();
                            }

                            if(e.getOption("role") != null){
                                Role roleToAdd = e.getGuild().getRoleById(e.getOption("role").getAsString());
                                e.getGuild().addRoleToMember(member, roleToAdd).queue();
                            }

                            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: L'utilisateur à était confirmer", Color.GREEN)).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.DISCORDSECURITY.getMessage(), DiscordSecurityMessages.USER_CONFIRM.getMessage() + " ("+e.getMember().getAsMention()+" -> "+member.getAsMention()+")", e.getGuild(), e.getMember(), true);
                        }else {
                            e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + DiscordSecurityMessages.USER_ALREADY_CONFIRM.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.DISCORDSECURITY.getMessage(), DiscordSecurityMessages.USER_ALREADY_CONFIRM.getMessage(), e.getGuild(), e.getMember(), false);
                        }
                    });
                }else{
                    e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.DISCORDSECURITY.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
                }
            } else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.DISCORDSECURITY)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.DISCORDSECURITY.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }
}
