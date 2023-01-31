package fr.skytorstd.doxerbot.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class DiscordModeratorCrafter extends EmbedCrafter{
    private static Color colorWarns = Color.ORANGE;
    public static MessageEmbed craftEmbedShowWarns(String memberId, String warnsList){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDD12 **Warns** - (" + memberId + ")");
        embed.setColor(colorWarns);
        embed.setDescription(warnsList);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

}
