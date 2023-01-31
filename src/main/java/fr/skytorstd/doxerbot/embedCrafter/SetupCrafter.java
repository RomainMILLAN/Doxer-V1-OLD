package fr.skytorstd.doxerbot.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class SetupCrafter extends EmbedCrafter{
    private static Color color_setup = Color.GREEN;

    private static EmbedBuilder embedCraft(){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("**Setup**");
        embed.setColor(color_setup);
        embed.setFooter(getFooterEmbed());

        return embed;
    }

    public static MessageEmbed setupSelectionByValue(String selection, String value){
        EmbedBuilder embed = embedCraft();
        embed.setDescription(":white_check_mark: La configuration de `"+selection+"` à était modifié sur `"+value+"`");

        return embed.build();
    }

    public static MessageEmbed initSudoId(){
        EmbedBuilder embed = embedCraft();
        embed.setDescription("Merci d\'effectué la commande !init [id_du_role_sudo]");

        return embed.build();
    }


}
