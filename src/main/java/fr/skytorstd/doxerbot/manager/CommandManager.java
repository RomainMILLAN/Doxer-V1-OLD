package fr.skytorstd.doxerbot.manager;

import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.messages.ConfigurationPluginsMessages;
import fr.skytorstd.doxerbot.messages.DiscordProfilerMessages;
import fr.skytorstd.doxerbot.messages.SetupMessages;
import fr.skytorstd.doxerbot.plugins.ConfigurationPlugins;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandManager {

    public static List<CommandData> updateSlashCommands(){
        List<CommandData> commandData = new ArrayList<>();

        //SETUP
        OptionData setup_select = new OptionData(OptionType.STRING, "selection", SetupMessages.DESCRIPTION_SELECT_ARGUMENT.getMessages()).setRequired(true).addChoice("ID Channel Logs", "idcLog").addChoice("ID Channel DiscordSentry", "idcDSentry").addChoice("ID Channel Uploader", "idcUploader").addChoice("ID Channel JoinQuit", "idcJoinQuit").addChoice("ID Channel Security", "idcSecurity").addChoice("ID Catégorie Grouper", "idcatGrouper").addChoice("ID Role Sudo", "idrSudo").addChoice("ID Role Administrateur", "idrAdmin").addChoice("ID Role Moderateur", "idrModerateur").addChoice("ID Role Utilisateur", "idrUser");
        OptionData setup_value = new OptionData(OptionType.STRING, "value", SetupMessages.DESCRIPTION_VALUE_ARGUMENT.getMessages()).setRequired(true);
        commandData.add(Commands.slash("setup", SetupMessages.DESCRIPTION_COMMAND.getMessages()).addOptions(setup_select).addOptions(setup_value));

        commandData.add(Commands.slash("supprimer", SetupMessages.DESTROY_DESCRIPTION.getMessages()));

        //ConfigurationLigar
        OptionData configurationLigarConfig = new OptionData(OptionType.STRING, "selection", ConfigurationPluginsMessages.DESCRIPTION_COMMAND.getMessage()).setRequired(false).addChoices(ConfigurationPlugins.allPluginInListByChoice());
        OptionData configurationLigarAction = new OptionData(OptionType.STRING, "action", ConfigurationPluginsMessages.DESCRIPTION_ACTION_ARGUMENT.getMessage()).setRequired(false).addChoice(ConfigurationPluginsMessages.OPTION_TRUE.getMessage(), "true").addChoice(ConfigurationPluginsMessages.OPTION_FALSE.getMessage(), "false").addChoice(ConfigurationPluginsMessages.OPTION_SHOW.getMessage(), "show");
        commandData.add(Commands.slash("configuration", ConfigurationPluginsMessages.DESCRIPTION_COMMAND.getMessage()).addOptions(configurationLigarConfig).addOptions(configurationLigarAction));

        //DiscordProfiler
        OptionData discordProfilerUser = new OptionData(OptionType.MENTIONABLE, "utilisateur", DiscordProfilerMessages.DESCRIPTION_UTILISATEUR_ARGUMENT.getMessages()).setRequired(false);
        commandData.add(Commands.slash("profil", DiscordProfilerMessages.DESCRIPTION.getMessages()).addOptions(discordProfilerUser));


        return commandData;
    }

}
