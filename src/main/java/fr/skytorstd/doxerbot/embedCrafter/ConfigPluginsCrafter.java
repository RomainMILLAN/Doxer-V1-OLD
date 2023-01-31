package fr.skytorstd.doxerbot.embedCrafter;

import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.object.Plugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;

public class ConfigPluginsCrafter extends EmbedCrafter{

    /**
     * Embed normal pour la configuration de ligar
     * @return
     */
    private static EmbedBuilder createConfigLigarEmbedCrafter(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.ORANGE);
        embed.setThumbnail(thumbnailURL);
        embed.setFooter(getFooterEmbed());

        return embed;
    }

    /**
     * Retourne un embed qui indique que la configuration d'un plugin à était changer
     * @param pluginName
     * @param pluginState
     * @return
     */
    public static MessageEmbed embedUpdatePluginState(String pluginName, Boolean pluginState){
        EmbedBuilder embed = createConfigLigarEmbedCrafter();
        embed.setDescription(":white_check_mark: Le plugin à bien changer son état de configuration (`" + pluginName + "`, `" + pluginState + "`)");

        return embed.build();
    }

    /**
     * Retourne un embed qui indique quelle est l'état d'une configuration
     * @param pluginName
     * @param pluginState
     * @return
     */
    public static MessageEmbed embedPluginState(String pluginName, Boolean pluginState){
        EmbedBuilder embed = createConfigLigarEmbedCrafter();
        embed.setDescription("\uD83D\uDD27 Le plugin `" + pluginName + "` est sur l'état **" + pluginState + "**");

        return embed.build();
    }

    public static MessageEmbed embedFullConfiguration(ArrayList<Plugin> pluginsList, String idGuild){
        EmbedBuilder embed = createConfigLigarEmbedCrafter();

        StringBuilder description = new StringBuilder();
        for(Plugin pl : pluginsList){
            description.append(" ▪ " + pl.getName() + " -> **" + ConfigurationPluginsDatabase.getStatePluginWithPluginName(pl.getName(), idGuild) + "** \n");
        }
        embed.setDescription(description);

        return embed.build();
    }
}
