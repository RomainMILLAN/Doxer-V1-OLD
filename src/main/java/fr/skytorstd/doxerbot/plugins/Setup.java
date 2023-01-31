package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.SetupCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class Setup extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("setup")){
            ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());

            Role sudo = e.getGuild().getRoleById(configurationGuild.getIdrSudo());
            if(e.getMember().getRoles().contains(sudo)){
                String selection = e.getOption("selection").getAsString();
                String value = e.getOption("value").getAsString();

                ConfigurationDoxerDatabase.setupBySelectionAndValue(e.getGuild().getId(), selection, value);
                e.replyEmbeds(SetupCrafter.setupSelectionByValue(selection, value)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.SETUP.getMessage(), "La configuration(`"+selection+"`) à était modifié sur **"+value+"** par `"+e.getMember().getAsMention()+"`", e.getGuild(), e.getMember(), true);
            }else{
                e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.SETUP.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }
}
