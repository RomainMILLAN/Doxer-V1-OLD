package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.databases.plugins.DiscordModeratorDatabase;
import fr.skytorstd.doxerbot.embedCrafter.DiscordModeratorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.DiscordModeratorMessages;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.object.Warn;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DiscordModerator extends ListenerAdapter {

    public DiscordModerator() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/warn [utilisateur] [action]*");
        Plugin discordModeratorPlugin = new Plugin(PluginName.DISCORDMODERTOR.getMessage(), DiscordModeratorMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(discordModeratorPlugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("warn")){
            ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());
            final Role sudo = e.getGuild().getRoleById(configurationGuild.getIdrSudo());
            final Role admin = e.getGuild().getRoleById(configurationGuild.getIdrAdmin());
            final Role moderateur = e.getGuild().getRoleByBot(configurationGuild.getIdrModerateur());

            if(e.getMember().getRoles().contains(sudo) || e.getMember().getRoles().contains(admin) || e.getMember().getRoles().contains(moderateur)){
                if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDMODERTOR.getMessage(), e.getGuild().getId())){
                    final String utilisateurId = e.getOption("utilisateur").getAsString();
                    final String action = e.getOption("action").getAsString();
                    ArrayList<Warn> warns_user = DiscordModeratorDatabase.getListWarnsByUID(utilisateurId);

                    switch (action){
                        case "add":
                            TextInput warnName = TextInput.create("ti-warnname", DiscordModeratorMessages.TI_NAME_WARN.getMessage(), TextInputStyle.SHORT).setRequired(true).setPlaceholder("Nom du warn").build();

                            TextInput warnUserId = TextInput.create("ti-warnuserid", DiscordModeratorMessages.TI_ID_USER.getMessage(), TextInputStyle.SHORT).setRequired(true).setValue(utilisateurId).build();

                            Modal modalCreate = Modal.create("dm-wc", DiscordModeratorMessages.MODAL_NAME_CREATION.getMessage())
                                    .addActionRows(ActionRow.of(warnName), ActionRow.of(warnUserId))
                                    .build();

                            e.replyModal(modalCreate).queue();
                            break;
                        case "remove":
                            TextInput warnId = TextInput.create("ti-warnid", DiscordModeratorMessages.TI_ID_WARN.getMessage(), TextInputStyle.SHORT).setRequired(true).setPlaceholder("00").build();

                            Modal modalRemove = Modal.create("dm-wr", DiscordModeratorMessages.MODAL_NAME_REMOVE.getMessage())
                                    .addActionRows(ActionRow.of(warnId))
                                    .build();

                            e.replyModal(modalRemove).queue();
                            break;
                        case "show":
                            String description = "";
                            if(warns_user.size() == 0){
                                description = "> " + DiscordModeratorMessages.NONE_WARNS.getMessage();
                            }else {
                                description += "Liste des warns: \n";
                                for(Warn warn : warns_user){
                                    description += " ▪ [`"+warn.getId()+"`] " + warn.getName() + "\n";
                                }
                            }

                            e.replyEmbeds(DiscordModeratorCrafter.craftEmbedShowWarns(utilisateurId, description)).setEphemeral(true).queue();
                            Logger.getInstance().toLog(PluginName.DISCORDMODERTOR.getMessage(), DiscordModeratorMessages.WARNS_SHOW_SUCESS.getMessage(), e.getGuild(), e.getMember(), true);
                            break;
                        default:
                            e.replyEmbeds(ErrorCrafter.errorCommand()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.DISCORDMODERTOR.getMessage(), LoggerMessages.COMMAND_NOT_EXIST.getMessage(), e.getGuild(), e.getMember(), false);
                            break;
                    }
                }else {
                    e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.DISCORDMODERTOR)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.DISCORDMODERTOR.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.DISCORDMODERTOR.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if(e.getModalId().equals("dm-wc")){
            String dmwn = e.getValue("ti-warnname").getAsString();
            String dmwuid = e.getValue("ti-warnuserid").getAsString();

            DiscordModeratorDatabase.addWarnByUIDAndNameWarn(new Warn(DiscordModeratorDatabase.getMaxId()+1, dmwn, dmwuid));
            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + DiscordModeratorMessages.WARN_ADD.getMessage() + " (`"+dmwuid+"` -> `"+dmwn+"`)", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
            Logger.getInstance().toLog(PluginName.DISCORDMODERTOR.getMessage(), DiscordModeratorMessages.WARN_ADD.getMessage() + " (`"+dmwuid+"` -> `"+dmwn+"`)", e.getGuild(), e.getMember(), true);
        }
        if(e.getModalId().equals("dm-wr")){
            String dmwid = e.getValue("ti-warnid").getAsString();
            Warn warnToRemove = DiscordModeratorDatabase.getWarnOnDB(dmwid);
            if(warnToRemove != null){
                DiscordModeratorDatabase.removeWarnByIdWarn(warnToRemove);
                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + DiscordModeratorMessages.WARN_REMOVE_SUCCESS.getMessage() + " (`"+dmwid+"`)", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.DISCORDMODERTOR.getMessage(), DiscordModeratorMessages.LOG_SUCCESS_WARN_REMOVE.getMessage() + " (`"+dmwid+"`)", e.getGuild(), e.getMember(), true);
            } else{
                e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + DiscordModeratorMessages.ID_NOT_FOUND_ON_DB.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.DISCORDMODERTOR.getMessage(), DiscordModeratorMessages.LOG_ERROR_WARN_REMOVE.getMessage() +" (`"+dmwid+"`)", e.getGuild(), e.getMember(), false);
            }
        }
    }
}
