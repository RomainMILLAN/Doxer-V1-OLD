package fr.skytorstd.doxerbot.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;

public class PingRemoverCrafter extends EmbedCrafter{
    private static Color colorPingRemover = Color.ORANGE;

    public static MessageEmbed craftShowPingsRemover(ArrayList<String> pings){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDD0F **PingRemover**");

        String description = "Liste des pings à supprimer: \n";

        if(pings.size() > 0){
            for(String str : pings){
                description += " ■ <@&" + str + "> \n";
            }
        }else {
            description += "*Aucun pings à supprimé*";
        }

        embed.setDescription(description);
        embed.setColor(colorPingRemover);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    public static MessageEmbed craftEmbedPing(String ping){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("\uD83D\uDD0F Ce ping ne peut pas être utilisée (<@&"+ping+">)");
        embed.setColor(colorPingRemover);
        embed.setThumbnail(thumbnailURL);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }
}
