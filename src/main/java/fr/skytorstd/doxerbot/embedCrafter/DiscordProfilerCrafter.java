package fr.skytorstd.doxerbot.embedCrafter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;

public class DiscordProfilerCrafter extends EmbedCrafter{
    private static Color profilColor = Color.ORANGE;

    public static MessageEmbed craftProfilEmbed(Member member){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83D\uDD16 Profil de: **" + member.getEffectiveName() + "**");
        embed.setThumbnail(member.getEffectiveAvatarUrl());
        embed.addField("ID: ", "`"+ member.getId()+"`", true);
        embed.addField("Avatar URL: ", "`"+ member.getEffectiveAvatarUrl()+"`", false);

        StringBuilder roleUser = new StringBuilder();
        for(Role role : member.getRoles()){
            roleUser.append("`"+role.getName()+"` | ");
        }
        roleUser = new StringBuilder(roleUser.substring(0, (roleUser.length() - 3)));
        embed.addField("Rôles: ", roleUser.toString(), false);

        String ownerState = "false";
        if(member.isOwner()) ownerState = "true";
        embed.addField("Propriétaire: ", ownerState, true);

        String boosterState = "false";
        if(member.isBoosting()) boosterState = "true";
        embed.addField("Booster: ", boosterState, true);



        embed.setFooter(getFooterEmbed());

        return embed.build();
    }

}
