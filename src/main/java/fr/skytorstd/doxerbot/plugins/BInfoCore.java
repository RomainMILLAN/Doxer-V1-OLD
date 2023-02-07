package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.databases.plugins.BInfoDatabase;
import fr.skytorstd.doxerbot.embedCrafter.BInfoCrafter;
import fr.skytorstd.doxerbot.embedCrafter.EmbedCrafter;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.manager.BInfoConfiguration;
import fr.skytorstd.doxerbot.manager.Downloader;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.BInfoCoreMessages;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.object.Cour;
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

        if(e.getName().equals("edt")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.BINFOCORE.getMessage(), e.getGuild().getId())){
                String action = null;
                if(e.getOption("action") != null)
                    action = e.getOption("action").getAsString();

                String groupe = null;
                if(e.getOption("groupe") != null)
                    groupe = e.getOption("groupe").getAsString();
                else{
                    Role infos6 = e.getGuild().getRoleById(BInfoConfiguration.IDR_INFOS6);
                    Role infoq5 = e.getGuild().getRoleById(BInfoConfiguration.IDR_INFOQ5);
                    if(e.getMember().getRoles().contains(infos6)){
                        groupe = "infos6";
                    }else if(e.getMember().getRoles().contains(infoq5)){
                        groupe = "infoq5";
                    }else {
                        e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + BInfoCoreMessages.NO_GROUP_SELECT.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                        Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.NO_GROUP_SELECT.getMessage(), e.getGuild(), e.getMember(), false);
                    }
                }

                String id = null;
                if(e.getOption("id") != null)
                    id = Objects.requireNonNull(e.getOption("id")).getAsString();

                if(action != null){
                    if(action.equals("refresh")){
                        if(groupe != null){
                            try {
                                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + BInfoCoreMessages.EDT_DOING_REFRSH.getMessage() + " (`"+groupe+"`)", Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                this.refreshEdtByGroupe(groupe);

                                e.getChannel().sendMessageEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + BInfoCoreMessages.EDT_REFRESH.getMessage() + " (`"+groupe+"`)", Color.GREEN)).queue((m) -> m.delete().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(),  BInfoCoreMessages.EDT_REFRESH.getMessage() + " (`"+groupe+"`)", e.getGuild(), e.getMember(), true);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            } catch (ParserException ex) {
                                throw new RuntimeException(ex);
                            }
                        }else {
                            e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + BInfoCoreMessages.NO_GROUP_SELECT.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.NO_GROUP_SELECT.getMessage(), e.getGuild(), e.getMember(), false);
                        }
                    }

                    if(action.equals("edit-info")){
                        if(e.getOption("information") != null){
                            if(id != null){
                                String information = e.getOption("information").getAsString();
                                Cour cour = BInfoDatabase.getCourById(Integer.parseInt(id));

                                information = information.replace("'", " ");
                                cour.setInformation(information);
                                BInfoDatabase.updateInformationOfCourse(cour);
                                e.replyEmbeds(EmbedCrafter.embedCraftWithDescriptionAndColor(":white_check_mark: " + BInfoCoreMessages.INFORMATION_UPDATE.getMessage(), Color.GREEN)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.SUCCESS_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.INFORMATION_UPDATE + " (`"+id+"` -> `"+information+"`)", e.getGuild(), e.getMember(), true);
                            }else {
                                e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + BInfoCoreMessages.NO_ID_ARGUMENT.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                                Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.NO_ID_ARGUMENT.getMessage(), e.getGuild(), e.getMember(), false);
                            }
                        }else {
                            e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + BInfoCoreMessages.NO_INFORMATION_ARGUMENT.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(),  BInfoCoreMessages.NO_INFORMATION_ARGUMENT.getMessage(), e.getGuild(), e.getMember(), false);
                        }
                    }
                }else {
                    //SEE EDT
                    String date = null;
                    if(e.getOption("date") != null)
                        date = Objects.requireNonNull(e.getOption("date")).getAsString();

                    if(id == null){
                        if(date == null){
                            //Date du jour
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            date = String.valueOf(dtf.format(LocalDateTime.now()));;
                        }

                        //Liste des cours
                        ArrayList<Cour> listCour = BInfoDatabase.getListCourByDateAndGroupe(date, groupe);

                        if(listCour.size() != 0){
                            e.replyEmbeds(BInfoCrafter.craftEDTListCour(listCour)).queue();
                            Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.SHOW_COUR_LIST.getMessage() + " (`"+date+"`), ", e.getGuild(), e.getMember(), true);
                        }else {
                            e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + BInfoCoreMessages.NO_COUR_TO_DATE.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.NO_COUR_TO_DATE.getMessage() +" (`"+date+"`)", e.getGuild(), e.getMember(), false);
                        }
                    }else {
                        Cour cour = BInfoDatabase.getCourById(Integer.parseInt(id));

                        if(cour != null){
                            e.replyEmbeds(BInfoCrafter.craftEDTCourEmbed(cour)).queue();
                            Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.SHOW_COUR.getMessage() +" (`"+cour.getId()+"`)", e.getGuild(), e.getMember(), true);
                        }else {
                            e.replyEmbeds(ErrorCrafter.errorEmbedCrafterWithDescription(":x: " + BInfoCoreMessages.NONE_COUR_WITH_ID.getMessage())).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                            Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), BInfoCoreMessages.NONE_COUR_WITH_ID.getMessage(), e.getGuild(), e.getMember(), false);
                        }
                    }

                }
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.BINFOCORE)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.BINFOCORE.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }



    public void refreshEdtByGroupe(String groupe) throws IOException, ParserException {
        String url = "";
        String fileName = groupe + ".ics";

        if(groupe.equalsIgnoreCase("infos6")){
            url = BInfoConfiguration.URL_INFOS6;
        }else if(groupe.equalsIgnoreCase("infoq5")){
            url = BInfoConfiguration.URL_INFOQ5;
        }else {
            url = BInfoConfiguration.URL_INFOQ5;
        }

        Downloader d = new Downloader(url, new File(fileName));
        d.run();

        FileInputStream fin = new FileInputStream(fileName);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);

        List<CalendarComponent> cs = calendar.getComponents();
        BInfoDatabase.deleteAllCourByGroupeId(groupe);
        for (CalendarComponent c: cs) {
            if (c instanceof VEvent) {
                String date = String.valueOf(c.getProperties().get(1));
                date = date.substring(8);
                date = date.substring(0, 8);
                String anne = date.substring(0, 4);
                String mois = date.substring(4, 6);
                String jour = date.substring(6, 8);
                date = jour + "/" + mois + "/" + anne;

                String hstart = String.valueOf(c.getProperties().get(1));
                hstart = hstart.substring(17);
                hstart = hstart.substring(0, 4);
                String hstarth = hstart.substring(0, 2);
                int hstarthint = Integer.parseInt(hstarth);
                hstarthint += 1;
                if(hstarthint < 10){
                    hstarth = "0" + String.valueOf(hstarthint);
                }else{
                    hstarth = String.valueOf(hstarthint);
                }
                String hstartm = hstart.substring(2, 4);
                hstart = hstarth + ":" + hstartm;

                String hend = String.valueOf(c.getProperties().get(2));
                hend = hend.substring(15);
                hend = hend.substring(0, 4);
                String hendh = hend.substring(0, 2);
                int hendhint = Integer.parseInt(hendh);
                hendhint += 1;
                hendh = String.valueOf(hendhint);
                String hendm = hend.substring(2, 4);
                hend = hendh + ":" + hendm;

                String location = " ";
                if(String.valueOf(c.getProperties().get(4)).length() > 2 && !String.valueOf(c.getProperties().get(4)).contains("\r\n")){
                    location = String.valueOf(c.getProperties().get(4));
                    location = location.substring(9);
                }
                if(location.contains("'"))
                    location = location.replace("'", " ");

                String description = String.valueOf(c.getProperties().get(5));
                description = description.substring(16);
                description = description.replaceAll("[\n\t ]", " ");
                description = description.replace("\\", " ");
                description = description.replace("n", "§");
                String descArgs[] = description.split("§");
                if(description.contains("'"))
                    description = description.replace("'", " ");

                String professeur = "";
                for(String sr : descArgs){
                    if(sr.equals(sr.toUpperCase()) && !sr.startsWith("ERIFIE") && !sr.contains("\n") && !sr.contains("\r")){
                        professeur += sr + " ";
                    }
                }
                if(professeur.length() < 4)
                    professeur = "AUCUN";
                for (String str : descArgs){
                    boolean allMAJ = true;
                    int i=0;
                    while(i<str.length() && allMAJ == true){
                        char ch = str.charAt(i);
                        if(Character.isLowerCase(ch)){
                            allMAJ = false;
                        }

                        i++;
                    }
                }

                int id = BInfoDatabase.getMaxId()+1;

                String name = String.valueOf(c.getProperties().get(3));
                name = name.substring(8);
                name = name.substring(0, name.length()-2);
                if(name.contains("'"))
                    name = name.replace("'", " ");

                BInfoDatabase.ajoutCour(new Cour(id, groupe, name, location, professeur, hstart, hend, date, " "));
            }
        }
    }
}
