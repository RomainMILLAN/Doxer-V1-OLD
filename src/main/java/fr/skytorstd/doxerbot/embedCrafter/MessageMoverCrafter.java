package fr.skytorstd.doxerbot.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class MessageMoverCrafter extends EmbedCrafter{

    /**
     * Embed de MessageMove
     * @param user
     * @param message
     * @return
     */
    public static MessageEmbed getMessageEmbedToMessageMove(User user, String message){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDD28 " + user.getName());
        embed.setThumbnail(user.getAvatarUrl());
        embed.setDescription(message);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    public static MessageEmbed getDirectMessageMessageMove(String guildName, String newChannelName){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDD28 **MessageMover**");
        embed.setDescription("> **"+guildName+"**\nVôtre message à était déplacé dans le channel `" + newChannelName + "`");
        embed.setColor(Color.PINK);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

}
