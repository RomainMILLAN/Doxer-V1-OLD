package fr.skytorstd.doxerbot.embedCrafter;

import fr.skytorstd.doxerbot.messages.RoleSubscriberMessages;
import fr.skytorstd.doxerbot.object.RoleSubscriber;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.ArrayList;

public class RoleSubscriberCrafter extends EmbedCrafter{
    private static Color colorCS = Color.BLUE;

    public static MessageEmbed craftEmbedShowListCS(ArrayList<RoleSubscriber> cslist){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDC64 **RoleSubscriber**");

        String description = "Liste des channels: \n";
        if(cslist.size() == 0){
            description += "*Aucun RoleSubscriber*";
        }else {
            for(RoleSubscriber cs : cslist){
                description += " ■ " + cs.getId_role()+"\n";
            }
        }
        embed.setDescription(description);

        embed.setColor(colorCS);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    public static MessageEmbed craftEmbedSee(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDC64 **RoleSubscriber**");
        embed.setDescription("Cliquez sur les boutons ci-dessous pour vous ajoutez le rôle");
        embed.setColor(colorCS);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

    public static MessageEmbed craftEmbedSuccess(RoleSubscriberMessages csm, Role role){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(":white_check_mark: "+ csm.getMessage() + " " + role.getAsMention());
        embed.setColor(colorCS);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }
}
