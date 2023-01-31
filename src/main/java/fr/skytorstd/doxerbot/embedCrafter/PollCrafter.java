package fr.skytorstd.doxerbot.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class PollCrafter extends EmbedCrafter{
    private static Color colorPoll = Color.CYAN;
    public static MessageEmbed craftEmbedPoll(User user, String name){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCBB **Sondage** par " + user.getName());
        embed.setDescription(name);
        embed.setFooter(getFooterEmbed());
        embed.setColor(colorPoll);

        return embed.build();
    }
}
