package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.DiscordSentryMessages;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceDeafenEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class DiscordSentry extends ListenerAdapter {

    public DiscordSentry() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("Attendre les logs");
        Plugin plugin = new Plugin(PluginName.DISCORDSENTRY.getMessage(), "Gére les logs de ligar", commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(!e.getAuthor().isBot()){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDSENTRY.getMessage(), e.getGuild().getId())){
                Logger.getInstance().toLogWithoutStateDifferentChannel("Message", e.getChannel().getAsMention() + "\n > " + e.getMessage().getContentRaw(), e.getGuild(), e.getMember());
            }
        }
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent e) {
        if(!e.getAuthor().isBot()){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDSENTRY.getMessage(), e.getGuild().getId())){
                Logger.getInstance().toLogWithoutStateDifferentChannel("Message Update", e.getChannel().getAsMention() + "\n" + e.getMessage().getContentRaw() + " (*edited*)", e.getGuild(), e.getMember());
            }
        }
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent e) {
        if(!e.getMember().getUser().isBot()){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDSENTRY.getMessage(), e.getGuild().getId())){
                if(e.getChannelJoined() != null && e.getChannelLeft() != null)
                    Logger.getInstance().toLogWithoutStateDifferentChannel("Voice Move", e.getChannelLeft() + " -> " + e.getChannelJoined(), e.getGuild(), e.getMember());
                else if(e.getChannelJoined() != null && e.getChannelLeft() == null)
                    Logger.getInstance().toLogWithoutStateDifferentChannel("Voice Join", " -> " + e.getChannelJoined(), e.getGuild(), e.getMember());
                else if(e.getChannelJoined() == null && e.getChannelLeft() != null)
                    Logger.getInstance().toLogWithoutStateDifferentChannel("Voice Quit", " <- " + e.getChannelLeft(), e.getGuild(), e.getMember());
                else
                    Logger.getInstance().toLogWithoutStateDifferentChannel("Voice Update", e.getChannelLeft() + " -> " + e.getChannelJoined(), e.getGuild(), e.getMember());
            }
        }
    }

    @Override
    public void onGuildVoiceDeafen(GuildVoiceDeafenEvent e) {
        if(!e.getMember().getUser().isBot()){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDSENTRY.getMessage(), e.getGuild().getId())){
                if(e.getVoiceState().isDeafened()){
                    Logger.getInstance().toLogWithoutStateDifferentChannel("Voice Defean", DiscordSentryMessages.VOICE_DEFEAN_ON.getMessage(), e.getGuild(), e.getMember());
                }else {
                    Logger.getInstance().toLogWithoutStateDifferentChannel("Voice Defean", DiscordSentryMessages.VOICE_DEFEAN_OFF.getMessage(), e.getGuild(), e.getMember());
                }
            }
        }
    }

    @Override
    public void onGuildVoiceMute(GuildVoiceMuteEvent e) {
        if(!e.getMember().getUser().isBot()){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDSENTRY.getMessage(), e.getGuild().getId())){
                if(e.getVoiceState().isMuted()){
                    Logger.getInstance().toLogWithoutStateDifferentChannel("Voice Mute", DiscordSentryMessages.VOICE_MUTE_ON.getMessage(), e.getGuild(), e.getMember());
                }else {
                    Logger.getInstance().toLogWithoutStateDifferentChannel("Voice UnMute", DiscordSentryMessages.VOICE_MUTE_OFF.getMessage(), e.getGuild(), e.getMember());
                }
            }
        }
    }
}
