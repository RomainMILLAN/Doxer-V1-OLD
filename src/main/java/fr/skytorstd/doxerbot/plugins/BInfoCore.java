package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.BInfoCrafter;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.manager.BInfoConfiguration;
import fr.skytorstd.doxerbot.manager.Downloader;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.BInfoCoreMessages;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BInfoCore extends ListenerAdapter {

    public BInfoCore() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/edt [action] [information]*");
        commands.add("/edt [action] [groupe]*");
        commands.add("/tutorat [date] [heure]*");
        commands.add("/edt");
        commands.add("/edt [date]");
        commands.add("/edt [groupe]");
        commands.add("/edt [groupe] [date]");
        commands.add("/edt [id]");
        Plugin plugin = new Plugin(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("tutorat")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.BINFOCORE.getMessage(), e.getGuild().getId())){
                Role tuteur = e.getGuild().getRoleById(BInfoConfiguration.IDR_TUTEUR);

                if(e.getMember().getRoles().contains(tuteur)){
                    String date = e.getOption("date").getAsString();
                    String heure = e.getOption("heure").getAsString();

                    e.getChannel().sendMessageEmbeds(BInfoCrafter.craftTutoratEmbed(date, heure)).queue(message -> {
                        message.addReaction(Emoji.fromFormatted("✅")).queue();
                    });
                    e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + BInfoCoreMessages.INSCRIPTION_TUTORAT_CREATE.getMessage(), Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.INSCRIPTION_TUTORAT_CREATE.getMessage(), e.getGuild(), e.getMember(), true);
                }else {
                    e.replyEmbeds(ErrorCrafter.errorNotPermissionToCommand(e.getMember())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                    Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), LoggerMessages.USER_NO_PERMISSION.getMessage(), e.getGuild(), e.getMember(), false);
                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.BINFOCORE)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }
}
