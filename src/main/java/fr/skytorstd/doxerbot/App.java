package fr.skytorstd.doxerbot;

import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.manager.CommandManager;
import fr.skytorstd.doxerbot.manager.Console;
import fr.skytorstd.doxerbot.messages.AppMessages;
import fr.skytorstd.doxerbot.plugins.ConsoleCommander;
import fr.skytorstd.doxerbot.plugins.Initialisation;
import fr.skytorstd.doxerbot.plugins.Setup;
import fr.skytorstd.doxerbot.states.ConsoleState;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class App {
    final public static String urlLinkToBDD = "jdbc:sqlite:doxerDB.db";
    private static JDA jda;
    private static String TOKEN = "";
    private static boolean debugMode = false;

    public static void main(String[] args) throws InterruptedException {
        /*
        Get TOKEN bot
         */
        //java -jar ligar.java TOKEN --debug
        //java -jar ligar.java TOKEN
        StringBuilder TOKEN = new StringBuilder();
        for(String argument : args){
            if(argument.equalsIgnoreCase("--debug")){
                debugMode = true;
            }else {
                TOKEN.append(argument);
            }
        }
        App.TOKEN = TOKEN.toString();

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

        jda.addEventListener(new Setup());
        jda.addEventListener(new Initialisation());

        /*
        Update Slash Commands
         */
        updateSlashCommands();

        /*
        Uploader

        Uploader.sendMessageEmbedOnUpload(guild);*/

        /*
        ConsoleCommander
        */
        ConsoleCommander.ligarConsoleCommander();
    }

    public static void updateSlashCommands(){
        for(String idGuild : ConfigurationDoxerDatabase.getAllIdGuild()){
            jda.getGuildById(idGuild).updateCommands().addCommands(CommandManager.updateSlashCommands()).queue();
        }
    }

    /**
     * Retourne le status du mode de débuggage
     * @return
     */
    public static boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Retourne le JDA en cour
     * @return
     */
    public static JDA getJda() {
        return jda;
    }
}
