package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.ConfigPluginsCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ConfigurationPlugins extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("configuration")){
            ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());
            Role sudo = e.getGuild().getRoleById(configurationGuild.getIdrSudo());
            Role admin = e.getGuild().getRoleById(configurationGuild.getIdrAdmin());

            if(e.getMember().getRoles().contains(sudo) || e.getMember().getRoles().contains(admin)){
                if(e.getOptions().size() == 0){
                    e.replyEmbeds(ConfigPluginsCrafter.embedFullConfiguration(App.getPlugins(), e.getGuild().getId())).setEphemeral(true).queue();
                    Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), "Visualisation de toutes les configurations", e.getGuild(), e.getMember(), true);
                }else if(e.getOptions().size() == 2){
                    String plugin = e.getOption("selection").getAsString();
                    String action = e.getOption("action").getAsString();
                    ArrayList<String> pluginNameList = new ArrayList<>();

                    for(Plugin pl : App.getPlugins()){
                        pluginNameList.add(pl.getName());
                    }

                    if(pluginNameList.contains(plugin)){
                        if(action.equals("true")){
                            Plugin pluginObject = App.getPlugin(plugin);

                            if(!ConfigurationPluginsDatabase.getStatePluginWithPluginName(plugin, e.getGuild().getId())){
                                ConfigurationPluginsDatabase.updatePluginStateWithPluginAndIdGuild(pluginObject, e.getGuild().getId());

                                e.replyEmbeds(ConfigPluginsCrafter.embedUpdatePluginState(plugin, true)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), "Changement de status de `"+plugin+"` sur **true**", e.getGuild(), e.getMember(), true);
                            }else {
                                e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: Le plugin est déja activé")).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), "Changement de status de `"+plugin+"` sur **true**", e.getGuild(), e.getMember(), false);
                            }

                        } else if (action.equals("false")) {
                            Plugin pluginObject = App.getPlugin(plugin);

                            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(plugin, e.getGuild().getId())){
                                ConfigurationPluginsDatabase.updatePluginStateWithPluginAndIdGuild(pluginObject, e.getGuild().getId());


                                e.replyEmbeds(ConfigPluginsCrafter.embedUpdatePluginState(plugin, false)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), "Changement de status de `"+plugin+"` sur **false**", e.getGuild(), e.getMember(), true);
                            }else {
                                e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: Le plugin est déja activé")).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), "Changement de status de `"+plugin+"` sur **false**", e.getGuild(), e.getMember(), false);
                            }

                        } else if (action.equals("show")) {

                            Boolean pluginState = ConfigurationPluginsDatabase.getStatePluginWithPluginName(plugin, e.getGuild().getId());
                            e.replyEmbeds(ConfigPluginsCrafter.embedPluginState(plugin, pluginState)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), "Vus du status de `"+plugin+"`", e.getGuild(), e.getMember(), true);

                        }else {
                            e.replyEmbeds(ErrorCrafter.errorCommand()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), LoggerMessages.COMMAND_NOT_EXIST.getMessage() + " `"+e.getCommandString()+"`", e.getGuild(), e.getMember(), false);
                        }
                    }else {
                        e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: Le plugin n'existe pas")).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), "Plugin innexistant `"+plugin+"`", e.getGuild(), e.getMember(), false);
                    }
                }else {
                    e.replyEmbeds(ErrorCrafter.errorCommand()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), LoggerMessages.COMMAND_NOT_EXIST.getMessage() + " `"+e.getCommandString()+"`", e.getGuild(), e.getMember(), false);
                }
            }else{
                e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.CONFIGURATIONLIGAR.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    public static List<Command.Choice> allPluginInListByChoice(){
        List<Command.Choice> listChoice = new ArrayList<>();

        for(Plugin plugin : App.getPlugins()){
            listChoice.add(new Command.Choice(plugin.getName(), plugin.getName()));
        }

        return listChoice;
    }

}
