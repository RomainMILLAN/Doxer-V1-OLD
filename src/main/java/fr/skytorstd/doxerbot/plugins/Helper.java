package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.HelperCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.HelperMessages;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Helper extends ListenerAdapter {

    public Helper() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/plugins");
        commands.add("/help [plugin]");
        Plugin helperPlugin = new Plugin(PluginName.HELPER.getMessage(), HelperMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(helperPlugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("help")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.HELPER.getMessage(), e.getGuild().getId())){
                String pluginName = e.getOption("plugin").getAsString();
                ArrayList<String> allPluginsName = new ArrayList<>();

                for(Plugin plugin : App.getPlugins()){
                    allPluginsName.add(plugin.getName());
                }

                if(allPluginsName.contains(pluginName)){
                    e.replyEmbeds(HelperCrafter.craftEmbedHelper(pluginName, App.getPlugin(pluginName).getCommands())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.HELPER.getMessage(), HelperMessages.LOG_SHOW_COMMANDS.getMessage() + " de (`"+pluginName+"`)", e.getGuild(), e.getMember(), true);
                }else{
                    e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + HelperMessages.PLUGIN_DOESNT_EXIST.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.HELPER.getMessage(), HelperMessages.LOG_PLUGIN_DOESNT_EXIST.getMessage() + " (`"+pluginName+"`)", e.getGuild(), e.getMember(), false);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.HELPER)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.HELPER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE + " (`"+PluginName.HELPER.getMessage()+"`)", e.getGuild(), e.getMember(), false);
            }
        }

        if(e.getName().equals("plugins")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.HELPER.getMessage(), e.getGuild().getId())){
                e.replyEmbeds(HelperCrafter.craftEmbedPlugins(App.getPlugins())).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.HELPER.getMessage(), HelperMessages.LOG_PLUGINS_SHOW.getMessage(), e.getGuild(), e.getMember(), true);
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.HELPER)).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.HELPER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE + " (`"+PluginName.HELPER.getMessage()+"`)", e.getGuild(), e.getMember(), false);
            }
        }
    }
}
