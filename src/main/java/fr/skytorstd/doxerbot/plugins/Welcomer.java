package fr.skytorstd.doxerbot.plugins;

import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationDoxerDatabase;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.WelcomerCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.ConfigurationPluginsMessages;
import fr.skytorstd.doxerbot.messages.WelcomerMessages;
import fr.skytorstd.doxerbot.object.ConfigurationGuild;
import fr.skytorstd.doxerbot.object.Inviter;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Welcomer extends ListenerAdapter {

    public Welcomer() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("Attendre que quelqu'un arrive sur le serveur");
        Plugin plugin = new Plugin(PluginName.WELCOMER.getMessage(), WelcomerMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(plugin);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.WELCOMER.getMessage(), e.getGuild().getId())){
            Member member = e.getMember();
            ConfigurationGuild configurationGuild = ConfigurationDoxerDatabase.getConfigurationGuildForIdGuild(e.getGuild().getId());

            e.getGuild().getTextChannelById(configurationGuild.getIdcJoinQuit()).sendMessageEmbeds(WelcomerCrafter.craftEmbedWelcomer(member, "Bienvenue à toi sur le serveur " + e.getGuild().getName() + " !")).queue(message -> {
                for(Inviter inviter : DiscordInviter.listInviter){
                    String userTag = e.getMember().getUser().getAsTag();
                    if(inviter.getUsertag().equals(userTag)){
                        message.delete().queue();
                        return;
                    }
                }
            });
            e.getGuild().getTextChannelById(configurationGuild.getIdcJoinQuit()).sendMessage(member.getAsMention()).queue((m) -> m.delete().queueAfter(2, TimeUnit.SECONDS));
            Logger.getInstance().toLog(PluginName.WELCOMER.getMessage(), WelcomerMessages.LOG_NEW_MEMBER.getMessage() +" (`"+member.getAsMention()+"`)", e.getGuild(), e.getMember(), true);
        }
    }
}
