package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.DiscordProfilerCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.DiscordProfilerMessages;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DiscordProfiler extends ListenerAdapter {

    public DiscordProfiler() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/profil");
        commands.add("/profil [utilisateur]");
        Plugin discordProfilerPlugin = new Plugin(PluginName.DISCORDPROFILER.getMessage(), DiscordProfilerMessages.DESCRIPTION.getMessages(), commands);

        App.addPlugin(discordProfilerPlugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("profil")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDPROFILER.getMessage(), e.getGuild().getId())){
                Member member = e.getMember();
                if(e.getOptions().size() == 1)
                    member = e.getGuild().getMemberById(e.getOption("utilisateur").getAsString());

                if(member == null){
                    e.replyEmbeds(ErrorCrafter.errorUserNotExistCrafter()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.DISCORDPROFILER.getMessage(), LoggerMessages.USER_NO_EXIST.getMessage(), e.getGuild(), e.getMember(), false);
                    return;
                }

                e.replyEmbeds(DiscordProfilerCrafter.craftProfilEmbed(member)).setEphemeral(true).queue();
                Logger.getInstance().toLog(PluginName.DISCORDPROFILER.getMessage(), "Vus sur le profil de `"+member.getAsMention()+"`", e.getGuild(), e.getMember(), true);
                return;
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.DISCORDPROFILER)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.DISCORDPROFILER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
                return;
            }
        }
    }
}
