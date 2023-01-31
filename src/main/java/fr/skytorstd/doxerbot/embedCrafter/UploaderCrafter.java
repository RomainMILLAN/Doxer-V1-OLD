package fr.skytorstd.doxerbot.embedCrafter;

import fr.skytorstd.doxerbot.messages.UploaderMessages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;


public class UploaderCrafter extends EmbedCrafter {
    private static Color colorConnected = Color.GREEN;

    public static MessageEmbed craftEmbedConnectedBot(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(UploaderMessages.CONNECTED_MESSAGE.getMessage());
        embed.setThumbnail(thumbnailURL);
        embed.setColor(colorConnected);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

}
