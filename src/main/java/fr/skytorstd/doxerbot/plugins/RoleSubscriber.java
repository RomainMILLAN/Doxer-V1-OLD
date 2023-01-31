package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.databases.plugins.RoleSubscriberDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.RoleSubscriberCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.messages.RoleSubscriberMessages;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RoleSubscriber extends ListenerAdapter {

    public RoleSubscriber() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/rolesubscriber [action]*");
        Plugin plugin = new Plugin(PluginName.ROLESUBSCRIBER.getMessage(), RoleSubscriberMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("rolesubscriber")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.ROLESUBSCRIBER.getMessage(), e.getGuild().getId())){
                String action = e.getOption("action").getAsString();

                switch (action){
                    case "add":
                        TextInput atiid_role = TextInput.create("ti-id_role", "ID du rôle", TextInputStyle.SHORT).setRequired(true).setPlaceholder("00000000000000000").build();

                        Modal modaladd = Modal.create("cs-add", "Ajout de RoleSubscriber")
                                .addActionRows(ActionRow.of(atiid_role))
                                .build();

                        e.replyModal(modaladd).queue();
                        break;
                    case "remove":
                        TextInput rtiid_channel = TextInput.create("ti-id_role", "ID du rôle", TextInputStyle.SHORT).setRequired(true).setPlaceholder("00000000000000000").build();

                        Modal modalremove = Modal.create("cs-remove", "Suppression de RoleSubscriber")
                                .addActionRows(ActionRow.of(rtiid_channel))
                                .build();

                        e.replyModal(modalremove).queue();
                        break;
                    case "show":
                        ArrayList<fr.skytorstd.doxerbot.object.RoleSubscriber> CSList = RoleSubscriberDatabase.getListRoleSubscriber(e.getGuild().getId());

                        e.replyEmbeds(RoleSubscriberCrafter.craftEmbedShowListCS(CSList)).queue();
                        Logger.getInstance().toLog(PluginName.ROLESUBSCRIBER.getMessage(), RoleSubscriberMessages.LOG_SHOW_CS_LIST.getMessage(), e.getGuild(), e.getMember(), true);
                        break;
                    case "see":
                        ArrayList<fr.skytorstd.doxerbot.object.RoleSubscriber> CSListToAdd = RoleSubscriberDatabase.getListRoleSubscriber(e.getGuild().getId());

                        e.getChannel().sendMessageEmbeds(RoleSubscriberCrafter.craftEmbedSee()).queue();
                        int CSListToAddNbMessage = CSListToAdd.size()/5;
                        while(CSListToAdd.size() > 0){
                            ArrayList<Button> buttonList = new ArrayList<>();
                            int j=0;
                            while(j<2 && CSListToAdd.size() > 0){
                                buttonList.add(Button.primary(CSListToAdd.get(0).getId_role(), e.getGuild().getRoleById(CSListToAdd.get(0).getId_role()).getName()));
                                CSListToAdd.remove(0);
                                j++;
                            }

                            e.getChannel().sendMessage(" ").addActionRow(buttonList).queue();
                            buttonList.clear();
                        }
                        e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + RoleSubscriberMessages.CS_SEND_MESSAGE.getMessage(), Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.ROLESUBSCRIBER.getMessage(), RoleSubscriberMessages.CS_SEND_MESSAGE.getMessage(), e.getGuild(), e.getMember(), true);
                        break;
                    default:
                        e.replyEmbeds(ErrorCrafter.errorCommand()).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.ROLESUBSCRIBER.getMessage(), LoggerMessages.COMMAND_NOT_EXIST.getMessage(), e.getGuild(), e.getMember(), false);
                        break;
                }
            }else{
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.ROLESUBSCRIBER)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.ROLESUBSCRIBER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if(e.getModalId().equals("cs-add")){
            String id_role = e.getValue("ti-id_role").getAsString();

            fr.skytorstd.doxerbot.object.RoleSubscriber cs = new fr.skytorstd.doxerbot.object.RoleSubscriber(id_role);
            RoleSubscriberDatabase.addRoleSubscriber(cs, e.getGuild().getId());

            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + RoleSubscriberMessages.CS_ADD_SUCCESS.getMessage() + " (`"+id_role+"`)", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
            Logger.getInstance().toLog(PluginName.ROLESUBSCRIBER.getMessage(), RoleSubscriberMessages.CS_ADD_SUCCESS.getMessage() + " (`"+id_role+"`)", e.getGuild(), e.getMember(), true);
        }

        if(e.getModalId().equals("cs-remove")){
            String id_role_remove = e.getValue("ti-id_role").getAsString();

            RoleSubscriberDatabase.addRoleSubscriber(new fr.skytorstd.doxerbot.object.RoleSubscriber(id_role_remove), e.getGuild().getId());

            e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + RoleSubscriberMessages.CS_REMOVE_SUCCESS.getMessage() + " (`"+id_role_remove+"`)", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
            Logger.getInstance().toLog(PluginName.ROLESUBSCRIBER.getMessage(), RoleSubscriberMessages.CS_REMOVE_SUCCESS.getMessage() + " (`"+id_role_remove+"`)", e.getGuild(), e.getMember(), true);
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        ArrayList<String> CSIdButton = new ArrayList<>();
        ArrayList<fr.skytorstd.doxerbot.object.RoleSubscriber> CS = RoleSubscriberDatabase.getListRoleSubscriber(e.getGuild().getId());
        for(fr.skytorstd.doxerbot.object.RoleSubscriber cs : CS){
            CSIdButton.add(cs.getId_role());
        }

        if(CSIdButton.contains(e.getButton().getId())){
            fr.skytorstd.doxerbot.object.RoleSubscriber cscurrent = null;
            for(fr.skytorstd.doxerbot.object.RoleSubscriber cs : CS){
                if(cs.getId_role().equals(e.getButton().getId())){
                    cscurrent = cs;
                    break;
                }
            }

            Role role = e.getGuild().getRoleById(e.getButton().getId());
            if(!e.getMember().getRoles().contains(role)){
                e.getGuild().addRoleToMember(e.getMember(), role).queue();
                e.replyEmbeds(RoleSubscriberCrafter.craftEmbedSuccess(RoleSubscriberMessages.CS_ADD_ROLE_SUCCESS, role)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.ROLESUBSCRIBER.getMessage(), RoleSubscriberMessages.CS_ADD_ROLE_SUCCESS.getMessage(), e.getGuild(), e.getMember(), true);
            }else {
                e.getGuild().removeRoleFromMember(e.getMember(), role).queue();
                e.replyEmbeds(RoleSubscriberCrafter.craftEmbedSuccess(RoleSubscriberMessages.CS_REMOVE_ROLE_SUCCESS, role)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.ROLESUBSCRIBER.getMessage(), RoleSubscriberMessages.CS_REMOVE_ROLE_SUCCESS.getMessage(), e.getGuild(), e.getMember(), true);
            }
        }
    }
}
