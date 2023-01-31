package fr.skytorstd.doxerbot;

import fr.skytorstd.doxerbot.manager.Console;
import fr.skytorstd.doxerbot.messages.AppMessages;
import fr.skytorstd.doxerbot.states.ConsoleState;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class App {
    final public static String urlLinkToBDD = "jdbc:sqlite:doxerBD.db";
    private static JDA jda;
    private static String TOKEN = "MTA2OTkzNTQ4MTQxODE2NjM0NA.GUG_A3.Jv6070v_HDoYfBFi385erWSQ3TfeUYGqmlKDeE";
    private static boolean debugMode = true;

    public static void main(String[] args) throws InterruptedException {
        run();
    }

    /**
     * Create and run the bot
     */
    public static void run() throws InterruptedException {
        jda = JDABuilder.createDefault(TOKEN)
                .setIdle(true)
                .enableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE)
                .setActivity(Activity.watching(AppMessages.ACTIVITY_PLAYING_BOT.getMessage()))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES)
                .build();

        jda.awaitStatus(JDA.Status.INITIALIZING);
        Console.getInstance().toConsole(AppMessages.JDA_BOT_INITIALIZING.getMessage(), ConsoleState.INFO);

        jda.awaitStatus(JDA.Status.CONNECTED);
        Console.getInstance().toConsole(AppMessages.JDA_BOT_CONNECTED.getMessage(), ConsoleState.INFO);

        jda.awaitReady();
        Console.getInstance().toConsole(AppMessages.JDA_BOT_READY.getMessage(), ConsoleState.INFO);

    }

    /**
     * Retourne le status du mode de débuggage
     * @return
     */
    public static boolean isDebugMode() {
        return debugMode;
    }
}
