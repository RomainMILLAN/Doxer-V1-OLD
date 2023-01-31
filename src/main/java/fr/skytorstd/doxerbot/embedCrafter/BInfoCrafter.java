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

    public static MessageEmbed craftEDTCourEmbed(Cour cour){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCDA **Cour** - " + cour.getId());
        embed.addField("Heure début: ", cour.getHeureDebut(), true);
        embed.addField("Heure fin: ", cour.getHeureFin(), true);
        embed.addField("Date: ", cour.getDate(), true);
        embed.addField("Professeur: ", cour.getProfesseur(), true);
        embed.addField("Groupe: ", cour.getGroupe(), true);
        embed.addField("Information: ", cour.getInformation(), true);
        embed.setColor(colorBInfo);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    public static MessageEmbed craftEDTListCour(ArrayList<Cour> cours){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDCDA **Cours** - [" + cours.get(0).getGroupeFr() + "] (*" + cours.get(0).getDate() + "*)");
        String description = "Liste des cours: \n";
        for(Cour cour : cours){
            description += " ■ [*" + cour.getId() + "*] *"+cour.getProfesseur()+"* **" + cour.getName() + "** - `" + cour.getHeureDebut() + "`/`" + cour.getHeureFin() + "`\n";
        }
        embed.setDescription(description);
        embed.setColor(colorBInfo);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }
}
