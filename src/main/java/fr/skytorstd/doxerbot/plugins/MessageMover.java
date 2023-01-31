package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.MessageMoverCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.messages.MessageMoverMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MessageMover extends ListenerAdapter {

    public MessageMover() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/messagemove [id_message] [id_channel]*");
        Plugin messageMoverPlugin = new Plugin(PluginName.MESSAGEMOVER.getMessage(), MessageMoverMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(messageMoverPlugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("messagemove")){
            ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());
            Role sudo = e.getGuild().getRoleById(configurationGuild.getIdrSudo());
            Role admin = e.getGuild().getRoleById(configurationGuild.getIdrAdmin());
            Role modo = e.getGuild().getRoleById(configurationGuild.getIdrModerateur());

            if(e.getMember().getRoles().contains(sudo) || e.getMember().getRoles().contains(admin) || e.getMember().getRoles().contains(modo)){
                if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.MESSAGEMOVER.getMessage(), e.getGuild().getId())){
                    String messageId = e.getOption("messageid").getAsString();
                    String channelId = e.getOption("channelid").getAsString();

                    e.getChannel().retrieveMessageById(messageId).queue((mes) -> {
                        String description = (String)mes.getContentRaw();
                        mes.delete().queue();
                        mes.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(MessageMoverCrafter.getDirectMessageMessageMove(e.getGuild().getName(), Objects.requireNonNull(e.getGuild().getTextChannelById(channelId)).getName()))).queue();

                        e.getGuild().getTextChannelById(channelId).sendMessageEmbeds(MessageMoverCrafter.getMessageEmbedToMessageMove(mes.getAuthor(), description)).queue();
                    });

                    e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: Message déplacé", Color.GREEN)).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.MESSAGEMOVER.getMessage(), "Message déplacé `"+channelId+"`", e.getGuild(), e.getMember(), true);
                }else{
                    e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.MESSAGEMOVER)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.MESSAGEMOVER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage() + " `"+PluginName.MESSAGEMOVER.getMessage()+"`", e.getGuild(), e.getMember(), false);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.MESSAGEMOVER.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    /**
     * Retourne la liste des commandes du plugin
     * @return
     */
    static ArrayList<String> getCommand() {
        ArrayList<String> r = new ArrayList<>();
        r.add("/messagemove [messageId] [channelId]");
        return r;
    }

}
