package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.PollCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.messages.PollExclamerMessages;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PollExclamer extends ListenerAdapter {

    public PollExclamer() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/poll [nom] [reponse]");
        Plugin pollExclamerPlugin = new Plugin(PluginName.POLLEXCLAMER.getMessage(), PollExclamerMessages.DESCRIPTION.getMessages(), commands);

        App.addPlugin(pollExclamerPlugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("poll")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.POLLEXCLAMER.getMessage(), e.getGuild().getId())){
                final String name = e.getOption("name").getAsString();
                final String res = e.getOption("resultat").getAsString();
                final String[] resultat = res.split(" ");

                e.getChannel().sendMessageEmbeds(PollCrafter.craftEmbedPoll(e.getUser(), name)).queue(message -> {
                    for(int i=0; i<resultat.length; i++){
                        message.addReaction(Emoji.fromFormatted(resultat[i])).queue();
                    }
                });

                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + PollExclamerMessages.LOG_SONDAGE_CREATE.getMessages(), Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.POLLEXCLAMER.getMessage(), PollExclamerMessages.LOG_SONDAGE_CREATE.getMessages() + " `"+name+"`", e.getGuild(), e.getMember(), true);
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.POLLEXCLAMER)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.POLLEXCLAMER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }
}
