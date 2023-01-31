package fr.skytorstd.doxerbot.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class WelcomerCrafter extends EmbedCrafter{
    private static Color colorWelcomer = Color.PINK;

    public static MessageEmbed craftEmbedWelcomer(Member member, String description){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83C\uDFC1 **Bienvenue** - " + member.getEffectiveName());
        embed.setDescription("> " + description);
        embed.setColor(colorWelcomer);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }


}
