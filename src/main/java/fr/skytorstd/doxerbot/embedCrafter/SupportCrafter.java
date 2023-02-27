package fr.skytorstd.doxerbot.embedCrafter;

import fr.skytorstd.doxerbot.states.PluginName;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class SupportCrafter extends EmbedCrafter{
    private static Color color = Color.MAGENTA;

    public static MessageEmbed craftTicketCreatorEmbed(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCE8 **" + PluginName.SUPPORT.getMessage() + "**/Ticket");
        embed.setColor(color);
        embed.setDescription("Cliquez sur le bouton ci-dessous pour ouvrir un ticket");
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    public static MessageEmbed craftDeleteTicket(Guild guild){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCE8 **Support**");
        embed.setDescription("Vous venez de fermée un ticket.");
        embed.setAuthor(guild.getName());
        embed.setThumbnail(guild.getBannerUrl());
        embed.setColor(color);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }


}
