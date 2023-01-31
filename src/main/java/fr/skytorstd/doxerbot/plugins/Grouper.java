package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.GrouperMessages;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

public class Grouper extends ListenerAdapter {

    public Grouper() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/groupe [name]");
        Plugin plugin = new Plugin(PluginName.GROUPER.getMessage(), GrouperMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("groupe")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.GROUPER.getMessage(), e.getGuild().getId())){
                String name = e.getOption("name").getAsString();
                ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());
                e.getGuild().getCategoryById(configurationGuild.getIdcatGrouper()).createTextChannel(name).queue(textChannel -> {
                    EnumSet<Permission> allow = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.ADMINISTRATOR, Permission.MANAGE_PERMISSIONS, Permission.MANAGE_ROLES, Permission.MESSAGE_MANAGE, Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_SEND, Permission.MESSAGE_ATTACH_FILES);
                    textChannel.getManager().putPermissionOverride(e.getMember(), allow, null).queue();
                });

                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + GrouperMessages.GROUPE_CREATE.getMessage(), Color.GREEN)).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.GROUPER.getMessage(), GrouperMessages.GROUPE_CREATE.getMessage(), e.getGuild(), e.getMember(), true);
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.GROUPER)).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.GROUPER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }
}
