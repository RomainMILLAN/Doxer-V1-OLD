package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.databases.plugins.VoiceClickDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.VoiceClickCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.messages.VoiceClickMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.object.VoiceClickChannel;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class VoiceClick extends ListenerAdapter {
    private ArrayList<String> voiceClickCreateChannels;
    public VoiceClick() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("Allez dans un channel VoiceClick");
        Plugin plugin = new Plugin(PluginName.VOICECLIK.getMessage(), VoiceClickMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(plugin);

        voiceClickCreateChannels = new ArrayList<>();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("voiceclick")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.VOICECLIK.getMessage(), e.getGuild().getId())){
                ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());
                Role sudo = e.getGuild().getRoleById(configurationGuild.getIdrSudo());
                Role admin = e.getGuild().getRoleById(configurationGuild.getIdrAdmin());

                if(e.getMember().getRoles().contains(sudo) || e.getMember().getRoles().contains(admin)){
                    String action = e.getOption("action").getAsString();

                    if(action.equalsIgnoreCase("add")){
                        TextInput tiidchannel = TextInput.create("ti-id_channel", "ID Channel", TextInputStyle.SHORT).setRequired(true).build();
                        TextInput tiname = TextInput.create("ti-name", "Nom", TextInputStyle.SHORT).setRequired(true).build();

                        Modal modal = Modal.create("vc-add", "Creation de VoiceClick Channel")
                                .addActionRows(ActionRow.of(tiidchannel), ActionRow.of(tiname))
                                .build();
                        e.replyModal(modal).queue();
                    }else if(action.equalsIgnoreCase("remove")){
                        TextInput tiidchannel = TextInput.create("ti-id_channel", "ID Channel", TextInputStyle.SHORT).setRequired(true).build();

                        Modal modal = Modal.create("vc-remove", "Creation de VoiceClick Channel")
                                .addActionRows(ActionRow.of(tiidchannel))
                                .build();
                        e.replyModal(modal).queue();
                    }else if(action.equalsIgnoreCase("show")){
                        e.replyEmbeds(VoiceClickCrafter.craftEmbedListShowVC(VoiceClickDatabase.getAllVoiceClickChanels(e.getGuild().getId()))).setEphemeral(true).queue();
                        Logger.getInstance().toLog(PluginName.VOICECLIK.getMessage(), VoiceClickMessages.SHOW_LIST_VOICECLICK.getMessage(), e.getGuild(), e.getMember(), true);
                    }else {
                        e.replyEmbeds(ErrorCrafter.errorCommand()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.VOICECLIK.getMessage(), LoggerMessages.COMMAND_NOT_EXIST.getMessage(), e.getGuild(), e.getMember(), false);
                    }
                }else {
                    e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.VOICECLIK.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.VOICECLIK)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.VOICECLIK.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if(e.getModalId().equals("vc-add")){
            String id_channel = e.getValue("ti-id_channel").getAsString();
            String name = e.getValue("ti-name").getAsString();

            VoiceClickChannel vcc = new VoiceClickChannel(id_channel, name);
            VoiceClickDatabase.addVoiceClickChannel(vcc, e.getGuild().getId());

            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + VoiceClickMessages.VCC_ADD.getMessage(), Color.GREEN)).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
            Logger.getInstance().toLog(PluginName.VOICECLIK.getMessage(), VoiceClickMessages.VCC_ADD.getMessage(), e.getGuild(), e.getMember(), true);
        }

        if(e.getModalId().equals("vc-remove")){
            String id_channel = e.getValue("ti-id_channel").getAsString();

            VoiceClickDatabase.removeVoiceClickChannel(id_channel, e.getGuild().getId());

            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + VoiceClickMessages.VCC_REMOVE.getMessage(), Color.GREEN)).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
            Logger.getInstance().toLog(PluginName.VOICECLIK.getMessage(), VoiceClickMessages.VCC_REMOVE.getMessage(), e.getGuild(), e.getMember(), true);
        }
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent e) {
        if(e.getChannelJoined() != null){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.VOICECLIK.getMessage(), e.getGuild().getId())){
                VoiceClickChannel vcc = null;
                for(VoiceClickChannel vccfe : VoiceClickDatabase.getAllVoiceClickChanels(e.getGuild().getId())){
                    if(vccfe.getId_channel().equals(e.getChannelJoined().getId())){
                        vcc = vccfe;
                        break;
                    }
                }

                if(vcc != null){
                    EnumSet<Permission> allow = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.PRIORITY_SPEAKER,
                            Permission.VOICE_MUTE_OTHERS, Permission.VOICE_DEAF_OTHERS, Permission.VOICE_SPEAK);
                    EnumSet<Permission> deny = EnumSet.of(Permission.BAN_MEMBERS, Permission.KICK_MEMBERS);
                    e.getChannelJoined().getParentCategory().createVoiceChannel(vcc.getName() + e.getMember().getEffectiveName()).addMemberPermissionOverride(Long.parseLong(e.getMember().getId()), allow, deny).queue(voiceChannel -> {
                        voiceClickCreateChannels.add(voiceChannel.getId());

                        e.getGuild().moveVoiceMember(e.getMember(), e.getGuild().getVoiceChannelById(voiceChannel.getId())).queue();
                    });
                }
            }
        }

        if(e.getChannelLeft() != null){
            if(voiceClickCreateChannels.contains(e.getChannelLeft().getId())){
                if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.VOICECLIK.getMessage(), e.getGuild().getId())){
                    if(e.getChannelLeft().getMembers().isEmpty()){
                        voiceClickCreateChannels.remove(e.getChannelLeft().getId());
                        e.getChannelLeft().delete().queue();
                    }
                }
            }
        }
    }
}
