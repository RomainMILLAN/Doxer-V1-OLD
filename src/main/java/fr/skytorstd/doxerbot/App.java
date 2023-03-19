package fr.skytorstd.doxerbot;

import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.manager.CommandManager;
import fr.skytorstd.doxerbot.manager.Console;
import fr.skytorstd.doxerbot.messages.AppMessages;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.plugins.RoleSubscriber;
import fr.skytorstd.doxerbot.plugins.*;
import fr.skytorstd.doxerbot.states.ConsoleState;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.ArrayList;

public class App {
    final public static String urlLinkToBDD = "jdbc:sqlite:doxerDB.db";
    private static JDA jda;
    private static String TOKEN = "";
    private static boolean debugMode = false;
    private static ArrayList<Plugin> plugins = new ArrayList<>();

    /**
     * Get the token of the bot and run it.
     * <pre/>
     * 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
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
     * Run the bot
     * <pre/>
     * 
     * @throws InterruptedException
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
        jda.addEventListener(new Uploader());
        jda.addEventListener(new ConfigurationPlugins());
        jda.addEventListener(new DiscordProfiler());
        jda.addEventListener(new MessageMover());
        jda.addEventListener(new Weather());
        jda.addEventListener(new PollExclamer());
        jda.addEventListener(new DiscordModerator());
        jda.addEventListener(new Helper());
        jda.addEventListener(new DiscordSecurity());
        jda.addEventListener(new BInfoCore());
        jda.addEventListener(new VoiceClick());
        jda.addEventListener(new RoleSubscriber());
        jda.addEventListener(new DiscordSentry());
        jda.addEventListener(new Grouper());
        jda.addEventListener(new Support());

        /*
        Update  all slash commands
         */
        updateSlashCommands();

        /*
        Uploader
        */
        for(String idGuild : ConfigurationDoxerDatabase.getAllIdGuild()){
            Uploader.sendMessageEmbedOnUpload(jda.getGuildById(idGuild));
        }

        /*
        ConsoleCommander
        */
        ConsoleCommander.consoleCommander();
    }

    /**
     * Update all the commands slash on all guild of the bot
     */
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

    /**
     * Return list plugin
     * @return
     */
    public static ArrayList<Plugin> getPlugins() {
        return plugins;
    }

    /**
     * Return plugin
     * @param pluginName
     * @return
     */
    public static Plugin getPlugin(String pluginName){
        for(Plugin pl : plugins){
            if(pl.getName().equalsIgnoreCase(pluginName)){
                return pl;
            }
        }

        return null;
    }

    /**
     * Add plugin at the list of plugin
     * @param plugin
     */
    public static void addPlugin(Plugin plugin){
        for(Plugin pl : plugins){
            if(pl.getName().equalsIgnoreCase(plugin.getName())){
                plugins.remove(pl);
                break;
            }
        }

        App.plugins.add(plugin);
    }
}
