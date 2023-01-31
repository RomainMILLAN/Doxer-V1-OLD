package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.UploaderCrafter;
import fr.skytorstd.doxerbot.messages.UploaderMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class Uploader extends ListenerAdapter {

    public Uploader() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("Attendre que Ligar se lance");
        Plugin uploaderPlugin = new Plugin(PluginName.UPLOADER.getMessage(), UploaderMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(uploaderPlugin);
    }

    /**
     * Envoie un message dans le channel prévu à cette effet pour indiquer que le bot est en ligne
     * @param g
     */
    public static void sendMessageEmbedOnUpload(Guild g){
        ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(g.getId());

        if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.UPLOADER.getMessage(), g.getId()) && configurationGuild.getIdcUploader() != null){
            g.getTextChannelById(configurationGuild.getIdcUploader()).sendMessageEmbeds(UploaderCrafter.craftEmbedConnectedBot()).queue();
        }
    }

}
