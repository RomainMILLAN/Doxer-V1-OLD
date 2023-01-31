package fr.skytorstd.doxerbot.embedCrafter;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.object.Plugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;

public class HelperCrafter extends EmbedCrafter{
    private static Color colorHelper = Color.PINK;

    private static EmbedBuilder craftHelperBuilder(String title, String description,  ArrayList<String> descriptionArgument){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(title);
        embed.setColor(colorHelper);
        for(String str : descriptionArgument){
            description += " ▪ " + str + " \n";
        }
        embed.setDescription(description);
        embed.setFooter(getFooterEmbed());

        return embed;
    }

    public static MessageEmbed craftEmbedHelper(String pluginName, ArrayList<String> commands){
        String description = "> " + App.getPlugin(pluginName).getDescription() + " \n\n";

        return craftHelperBuilder("\uD83D\uDCC4 **Helper** - " + pluginName, description + "Liste des commandes: \n", commands).build();
    }

    public static MessageEmbed craftEmbedPlugins(ArrayList<Plugin> plugins){
        ArrayList<String> pluginsName = new ArrayList<>();
        for(Plugin plugin : plugins){
            pluginsName.add(plugin.getName());
        }
        return craftHelperBuilder("\uD83D\uDD27 **Plugins**", "Liste des plugins: \n", pluginsName).build();
    }


}
