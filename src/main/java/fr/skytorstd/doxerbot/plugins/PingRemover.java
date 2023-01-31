package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.databases.plugins.PingRemoverDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.PingRemoverCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.messages.PingRemoverMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PingRemover extends ListenerAdapter {

    public PingRemover() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/pingremover [action]");
        commands.add("/pingremover [add] [ping]");
        commands.add("/pingremover [remove] [ping]");
        Plugin plugin = new Plugin(PluginName.PINGREMOVER.getMessage(), PingRemoverMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("pingremover")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.PINGREMOVER.getMessage(), e.getGuild().getId())){
                ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());
                Role sudo = e.getGuild().getRoleById(configurationGuild.getIdrSudo());
                Role admin = e.getGuild().getRoleById(configurationGuild.getIdrAdmin());

                if(e.getMember().getRoles().contains(sudo) || e.getMember().getRoles().contains(admin)){
                    String action = e.getOption("action").getAsString();

                    if(action.equals("show")){
                        ArrayList<String> listPing = PingRemoverDatabase.getListPings(e.getGuild().getId());

                        e.replyEmbeds(PingRemoverCrafter.craftShowPingsRemover(listPing)).setEphemeral(true).queue();
                        Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), PingRemoverMessages.PINGS_SHOW_SUCCESS.getMessage(), e.getGuild(), e.getMember(), true);
                    }else {
                        if(e.getOption("ping") != null || e.getOption("pingtext") != null){
                            String ping = "";
                            if(e.getOption("ping") != null){
                                ping = e.getOption("ping").getAsString();
                            }else if(e.getOption("pingtext") != null){
                                ping = e.getOption("pingtext").getAsString();
                            }

                            if(action.equals("add")){
                                boolean isIn = PingRemoverDatabase.isInDB(ping, e.getGuild().getId());

                                if(!isIn){
                                    PingRemoverDatabase.addPing(ping, e.getGuild().getId());

                                    e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + PingRemoverMessages.PING_ADD.getMessage(), Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                    Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), PingRemoverMessages.PING_ADD.getMessage(), e.getGuild(), e.getMember(), true);
                                }else {
                                    e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + PingRemoverMessages.PING_ALREADY_IN_DB.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                    Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), PingRemoverMessages.PING_ALREADY_IN_DB.getMessage(), e.getGuild(), e.getMember(), false);

                                }
                            }else if(action.equals("remove")){
                                boolean isIn = PingRemoverDatabase.isInDB(ping, e.getGuild().getId());

                                if(isIn){
                                    PingRemoverDatabase.removePing(ping, e.getGuild().getId());

                                    e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + PingRemoverMessages.PING_REMOVE.getMessage(), Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                    Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), PingRemoverMessages.PING_REMOVE.getMessage(), e.getGuild(), e.getMember(), true);
                                }else {
                                    e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + PingRemoverMessages.PING_NOT_IN_DB.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                    Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), PingRemoverMessages.PING_NOT_IN_DB.getMessage(), e.getGuild(), e.getMember(), false);
                                }
                            }else {
                                e.replyEmbeds(ErrorCrafter.errorCommand()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), LoggerMessages.COMMAND_NOT_EXIST.getMessage(), e.getGuild(), e.getMember(), false);
                            }
                        }else {
                            e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + PingRemoverMessages.PING_NOT_IN_CMD.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), PingRemoverMessages.PING_NOT_IN_CMD.getMessage(), e.getGuild(), e.getMember(), false);
                        }
                    }
                }else {
                    e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.PINGREMOVER)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getMember() != null){
            if(!e.getMember().getUser().isBot()){
                if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.PINGREMOVER.getMessage(), e.getGuild().getId())){
                    for(String ping : PingRemoverDatabase.getListPings(e.getGuild().getId())){
                        if(e.getMessage().getContentRaw().contains(ping)){
                            e.getMessage().delete().queue();

                            e.getChannel().sendMessageEmbeds(PingRemoverCrafter.craftEmbedPing(ping)).queue((m) -> m.delete().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.PINGREMOVER.getMessage(), PingRemoverMessages.PING_DELETE.getMessage(), e.getGuild(), e.getMember(), true);
                            return;
                        }
                    }
                }
            }
        }
    }
}
