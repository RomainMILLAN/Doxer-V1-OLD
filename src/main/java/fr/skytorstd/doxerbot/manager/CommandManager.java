package fr.skytorstd.doxerbot.manager;

import fr.skytorstd.doxerbot.messages.SetupMessages;
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

        OptionData setup_select = new OptionData(OptionType.STRING, "selection", SetupMessages.DESCRIPTION_SELECT_ARGUMENT.getMessages()).setRequired(true).addChoice("idcLog", "ID Channel Logs").addChoice("idcDSentry", "ID Channel DiscordSentry").addChoice("idcUploader", "ID Channel Uploader").addChoice("idcJoinQuit", "ID Channel JoinQuit").addChoice("idcSecurity", "ID Channel Security").addChoice("idcatGrouper", "ID Catégorie Grouper").addChoice("idrSudo", "ID Role Sudo").addChoice("idrAdmin", "ID Role Administrateur").addChoice("idrModerateur", "ID Role Moderateur").addChoice("idrUser", "ID Role Utilisateur");
        OptionData setup_value = new OptionData(OptionType.STRING, "value", SetupMessages.DESCRIPTION_VALUE_ARGUMENT.getMessages()).setRequired(true);
        commandData.add(Commands.slash("setup", SetupMessages.DESCRIPTION_COMMAND.getMessages()).addOptions(setup_select).addOptions(setup_value));

        return commandData;
    }

}
