package fr.skytorstd.doxerbot.manager;

import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.embedCrafter.LoggerCrafter;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.states.ConsoleState;
import fr.skytorstd.doxerbot.states.FileName;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.Objects;

public class Logger {
    private static Logger instance = null;
    private FileName fileName = FileName.LOGGER_FILENAME;

    /**
     * Contructeur de singleton
     */
    public Logger() {}

    /**
     * Retourne un Logger et le crée s'il est null
     * @return
     */
    public static Logger getInstance() {
        if(instance == null){
            instance = new Logger();
        }

        return instance;
    }

    /**
     * Log les informations demander dans la Console, Fichier + Channel discord
     * @param title
     * @param message
     * @param g
     * @param member
     * @param status
     */
    public void toLog(String title, String message, Guild g, Member member, boolean status) {
        if(status)
            message = ":white_check_mark: " + message + " (*Success*)";
        else
            message = ":x: " + message + " (*Failed*)";

        g.getTextChannelById(ConfigurationDoxerDatabase.getIdcLogForIdGuild(g.getId())).sendMessageEmbeds(LoggerCrafter.craftLogEmbedWithTitleAndMessage(title,message, member)).queue();
        Console.getInstance().toConsole(ConsoleColor.PURPLE.getConsoleColor()+" | " + ConsoleColor.RESET.getConsoleColor() + title + ConsoleColor.PURPLE.getConsoleColor() + " | " + ConsoleColor.RESET.getConsoleColor() + message, ConsoleState.LOG);
        WriteFile.getInstance().writeOnFile(fileName, "\n[" + DateHourFormatter.getInstance().getDateAndHourTimeFormat() + "] - DISCORD SENTRY/ " + title + " | " + message);
    }

    /**
     * Log les inforamtions demander sans status dans la Console, Fichier + Channel discord
     * @param title
     * @param message
     * @param g
     * @param member
     */
    public void toLogWithoutState(String title, String message, Guild g, Member member) {

        g.getTextChannelById(Objects.requireNonNull(ConfigurationDoxerDatabase.getIdcLogForIdGuild(g.getId()))).sendMessageEmbeds(LoggerCrafter.craftLogEmbedWithTitleAndMessage(title,message, member)).queue();
        Console.getInstance().toConsole(ConsoleColor.PURPLE.getConsoleColor()+" | " + ConsoleColor.RESET.getConsoleColor() + title + ConsoleColor.PURPLE.getConsoleColor() + " | " + ConsoleColor.RESET.getConsoleColor() + message, ConsoleState.LOG);
        WriteFile.getInstance().writeOnFile(fileName, "\n[" + DateHourFormatter.getInstance().getDateAndHourTimeFormat() + "] - DISCORD SENTRY/ " + title + " | " + message);
    }

    /**
     * Log les inforamtions demander sans status dans la Console, Fichier + Channel discord
     * @param title
     * @param message
     * @param g
     * @param member
     */
    public void toLogWithoutStateDifferentChannel(String title, String message, Guild g, Member member) {
        g.getTextChannelById(Objects.requireNonNull(ConfigurationDoxerDatabase.getIdcDSentryForIdGuild(g.getId()))).sendMessageEmbeds(LoggerCrafter.craftLogEmbedWithTitleAndMessage(title,message, member)).queue();
        Console.getInstance().toConsole(ConsoleColor.PURPLE.getConsoleColor()+" | " + ConsoleColor.RESET.getConsoleColor() + title + ConsoleColor.PURPLE.getConsoleColor() + " | " + ConsoleColor.RESET.getConsoleColor() + message, ConsoleState.LOG);
        WriteFile.getInstance().writeOnFile(fileName, "\n[" + DateHourFormatter.getInstance().getDateAndHourTimeFormat() + "] - DISCORD SENTRY/ " + title + " | " + message);
    }

    /**
     * Log les information passer en parametre uniquement dans le fichier et dans la console
     * @param title
     * @param message
     * @param g
     * @param member
     */
    public void toLogOnConsoleAndFileOnly(String title, String message, Guild g, Member member){
        Console.getInstance().toConsole(ConsoleColor.PURPLE.getConsoleColor()+" | " + ConsoleColor.RESET.getConsoleColor() + title + ConsoleColor.PURPLE.getConsoleColor() + " | " + ConsoleColor.RESET.getConsoleColor() + message, ConsoleState.LOG);
        WriteFile.getInstance().writeOnFile(fileName, "\n[" + DateHourFormatter.getInstance().getDateAndHourTimeFormat() + "] - DISCORD SENTRY/ " + title + " | " + message);
    }
}
