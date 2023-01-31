package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.DiscordInviterMessages;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.Inviter;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class DiscordInviter extends ListenerAdapter {
    public static ArrayList<Inviter> listInviter = new ArrayList<>();

    public DiscordInviter() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/inviter [usertag]");
        Plugin plugin = new Plugin(PluginName.DISCORDINVITER.getMessage(), DiscordInviterMessages.DESCRIPTION.getMessage(),commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("inviter")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDINVITER.getMessage(), e.getGuild().getId())){
                String usertag = e.getOption("usertag").getAsString();
                AudioChannel audioChannel = e.getMember().getVoiceState().getChannel();

                if(audioChannel != null){
                    Inviter inviter = new Inviter(usertag, audioChannel);
                    DiscordInviter.listInviter.add(inviter);

                    audioChannel.createInvite().queue(invite -> {
                        e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: "+DiscordInviterMessages.INVITER_CREATE.getMessage()+", veuilliez inviter la personne avec le lien ("+invite.getUrl()+")", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    });
                    Logger.getInstance().toLog(PluginName.DISCORDINVITER.getMessage(), DiscordInviterMessages.INVITER_CREATE.getMessage(), e.getGuild(), e.getMember(), true);
                }else {
                    e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + DiscordInviterMessages.ERROR_VOICE.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.DISCORDINVITER.getMessage(), DiscordInviterMessages.ERROR_VOICE.getMessage(), e.getGuild(), e.getMember(), false);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.DISCORDINVITER)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.DISCORDINVITER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDINVITER.getMessage(), e.getGuild().getId())){
            String userTag = e.getUser().getAsTag();

            for(Inviter inviter : listInviter){
                if(inviter.getUsertag().equals(userTag)){
                    AudioChannel audioChannel = inviter.getAudioChannel();
                    EnumSet<Permission> allow = EnumSet.of(Permission.VIEW_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_STREAM, Permission.VOICE_START_ACTIVITIES, Permission.VOICE_USE_VAD ,Permission.VOICE_SPEAK, Permission.MESSAGE_SEND, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_HISTORY, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_EXT_EMOJI, Permission.MESSAGE_EXT_STICKER, Permission.MESSAGE_SEND_IN_THREADS);
                    EnumSet<Permission> deny = EnumSet.of(Permission.BAN_MEMBERS, Permission.KICK_MEMBERS, Permission.ADMINISTRATOR);
                    audioChannel.getManager().putMemberPermissionOverride(e.getMember().getIdLong(), allow, deny).queue();

                    Logger.getInstance().toLog(PluginName.DISCORDINVITER.getMessage(), DiscordInviterMessages.MEMBER_JOIN_DI.getMessage(), e.getGuild(), e.getMember(), true);
                    return;
                }
            }
        }
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent e) {
        if(e.getChannelLeft() != null){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.DISCORDINVITER.getMessage(), e.getGuild().getId())){
                String userTag = e.getMember().getUser().getAsTag();

                for(Inviter inviter : listInviter){
                    if(inviter.getUsertag().equals(userTag)){
                        listInviter.remove(inviter);
                        e.getMember().kick("DiscordInviter").queue();

                        Logger.getInstance().toLog(PluginName.DISCORDINVITER.getMessage(), DiscordInviterMessages.MEMBER_LEAVE_DI.getMessage(), e.getGuild(), e.getMember(), true);
                        return;
                    }
                }
            }
        }
    }
}
