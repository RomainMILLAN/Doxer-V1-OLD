package fr.skytorstd.doxerbot.embedCrafter;

import fr.skytorstd.doxerbot.object.VoiceClickChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;

public class VoiceClickCrafter extends EmbedCrafter {
    private static Color colorVC = Color.ORANGE;

    public static MessageEmbed craftEmbedListShowVC(ArrayList<VoiceClickChannel> voiceClickChannels){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83C\uDFA7 **VoiceClick**");

        String description = "Liste des channels VoiceClicks: \n";
        if(voiceClickChannels.size() == 0){
            description += "*Aucun channel VoiceClick*";
        }else {
            for(VoiceClickChannel vcc : voiceClickChannels){
                description += " ■ [" + vcc.getId_channel() + "] `" + vcc.getName() + "` \n";
            }
        }
        embed.setDescription(description);
        embed.setColor(colorVC);
        embed.setFooter(getFooterEmbed());

        return embed.build();
    }
}
