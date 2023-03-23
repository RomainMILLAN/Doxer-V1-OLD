package fr.skytorstd.doxerbot.embedCrafter;

import fr.skytorstd.doxerbot.object.Cour;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;

public class BInfoCrafter extends EmbedCrafter{
    private static  Color colorBInfo = Color.CYAN;

    public static MessageEmbed craftTutoratEmbed(String date, String hour){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("✒ **BUT Informatique**");
        embed.setDescription("Pour vous inscrire au tutorat merci de réagir à l'icone :white_check_mark: ci-dessous.");
        embed.addField("Date: ", date, false);
        embed.addField("Heure: ", hour, false);
        embed.setFooter(getFooterEmbed());
        embed.setColor(colorBInfo);

        return embed.build();
    }
}
