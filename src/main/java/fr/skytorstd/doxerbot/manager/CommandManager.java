package fr.skytorstd.doxerbot.manager;

import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.messages.*;
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

        //MessageMover
        OptionData mmIdMessage = new OptionData(OptionType.STRING, "messageid", MessageMoverMessages.DESCRIPTION_COMMAND_MESSAGE_ID.getMessage()).setRequired(true);
        OptionData mmIdChannel = new OptionData(OptionType.STRING, "channelid", MessageMoverMessages.DESCRIPTION_COMMAND_CHANNEL_ID.getMessage()).setRequired(true);
        commandData.add(Commands.slash("messagemove", MessageMoverMessages.DESCRIPTION.getMessage()).addOptions(mmIdMessage).addOptions(mmIdChannel));

        //Weather
        OptionData wVille = new OptionData(OptionType.STRING, "ville", WeatherMessages.DESCRIPTION_VILLE_ARGUMENT.getMessage()).setRequired(false);
        commandData.add(Commands.slash("weather", WeatherMessages.DESCRIPTION.getMessage()).addOptions(wVille));

        //PollExclamer
        OptionData peName = new OptionData(OptionType.STRING, "name", PollExclamerMessages.DESCRIPTION_NAME_ARGUMENT.getMessages()).setRequired(true);
        OptionData peRes = new OptionData(OptionType.STRING, "resultat", PollExclamerMessages.DESCRIPTION_RES_ARGUMENT.getMessages()).setRequired(true);
        commandData.add(Commands.slash("poll", PollExclamerMessages.DESCRIPTION.getMessages()).addOptions(peName).addOptions(peRes));

        //DiscordModerator
        OptionData wUser = new OptionData(OptionType.MENTIONABLE, "utilisateur", DiscordModeratorMessages.DESCRIPTION_USER_ARGUMENT.getMessage()).setRequired(true);
        OptionData wAction = new OptionData(OptionType.STRING, "action", DiscordModeratorMessages.DESCRIPTION_ACTION_ARGUMENT.getMessage()).setRequired(true).addChoice("Ajouter un warn", "add").addChoice("Supprimer un warn", "remove").addChoice("Voir la liste des warns", "show");
        commandData.add(Commands.slash("warn", DiscordModeratorMessages.DESCRIPTION_COMMAND.getMessage()).addOptions(wUser).addOptions(wAction));

        //Helper
        OptionData hPlugin = new OptionData(OptionType.STRING, "plugin", HelperMessages.DESCRIPTION_HELP_ARGUMENT.getMessage()).setRequired(true).addChoices(ConfigurationPlugins.allPluginInListByChoice());
        commandData.add(Commands.slash("help", HelperMessages.DESCRIPTION_HELP_COMMAND.getMessage()).addOptions(hPlugin));
        commandData.add(Commands.slash("plugins", HelperMessages.DESCRIPTION_PLUGIN_COMMAND.getMessage()));

        //DiscordSecurity
        OptionData cUser = new OptionData(OptionType.MENTIONABLE, "utilisateur", DiscordSecurityMessages.DESCRIPTION_USER_ARGUMENT.getMessage()).setRequired(true);
        OptionData cRole = new OptionData(OptionType.MENTIONABLE, "role", DiscordSecurityMessages.DESCRIPTION_ROLE_ARGUMENT.getMessage()).setRequired(false);
        OptionData cRename = new OptionData(OptionType.STRING, "name", DiscordSecurityMessages.DESCRIPTION_RENAME_ARGUMENT.getMessage()).setRequired(false);
        commandData.add(Commands.slash("confirm", DiscordSecurityMessages.DESCRIPTION_COMMAND.getMessage()).addOptions(cUser).addOptions(cRole).addOptions(cRename));

        //BInfoCore
        OptionData tDate = new OptionData(OptionType.STRING, "date",BInfoCoreMessages.DESCRIPTION_TUTORAT_DATE_ARGUMENT.getMessage()).setRequired(true);
        OptionData tHour = new OptionData(OptionType.STRING, "heure", BInfoCoreMessages.DESCRIPTION_TUTORAT_HEURE_ARGUMENT.getMessage()).setRequired(true);
        commandData.add(Commands.slash("tutorat", BInfoCoreMessages.DESCRIPTION_TUTORAT_COMMAND.getMessage()).addOptions(tDate).addOptions(tHour));

        OptionData edtAction = new OptionData(OptionType.STRING, "action", BInfoCoreMessages.DESCRIPTION_EDT_ACTION_ARGUMENT.getMessage()).setRequired(false).addChoice("Réediter l'emploi du temp", "refresh").addChoice("Edition de l'information d'un cour", "edit-info");
        OptionData edtGroupe = new OptionData(OptionType.STRING, "groupe", BInfoCoreMessages.DESCRIPTION_EDT_GROUPE_ARGUMENT.getMessage()).setRequired(false).addChoice("BUT INFO - A1", "infos6").addChoice("BUT INFO - Q5", "infoq5");
        OptionData edtId = new OptionData(OptionType.STRING, "id", BInfoCoreMessages.DESCRIPTION_EDT_ID_ARGUMENT.getMessage()).setRequired(false);
        OptionData edtDate = new OptionData(OptionType.STRING, "date", BInfoCoreMessages.DESCRIPTION_EDT_DATE_ARGUMENT.getMessage()).setRequired(false);
        OptionData edtInfo = new OptionData(OptionType.STRING, "information", BInfoCoreMessages.DESCRIPTION_EDT_INFO_ARGUMENT.getMessage()).setRequired(false);
        commandData.add(Commands.slash("edt", BInfoCoreMessages.DESCRIPTION_EDT_COMMAND.getMessage()).addOptions(edtInfo).addOptions(edtAction).addOptions(edtId).addOptions(edtGroupe).addOptions(edtDate));

        //VoiceClick
        OptionData vcAction = new OptionData(OptionType.STRING, "action", VoiceClickMessages.DESCRIPTION_ACTION_ARGUMENT.getMessage()).setRequired(true).addChoice("Ajouter un channel VoiceClick", "add").addChoice("Supprimer un channel VoiceClick", "remove").addChoice("Voir la liste des channels VoiceClick", "show");
        commandData.add(Commands.slash("voiceclick", VoiceClickMessages.DESCRIPTION_COMMAND.getMessage()).addOptions(vcAction));

        //RoleSubscriber
        OptionData rsAction = new OptionData(OptionType.STRING, "action", RoleSubscriberMessages.DESCRIPTION_ACTION_ARGUMENT.getMessage()).setRequired(true).addChoice("Ajouter un rôle", "add").addChoice("Supprimer un rôle", "remove").addChoice("Voir la liste des rôles", "show").addChoice("Afficher le message", "see");
        commandData.add(Commands.slash("rolesubscriber", RoleSubscriberMessages.DESCRIPTION_COMMAND.getMessage()).addOptions(rsAction));

        //DiscordInviter
        OptionData diUserTag = new OptionData(OptionType.STRING, "usertag", DiscordInviterMessages.DESCRIPTION_USERTAG_ARGUMENT.getMessage()).setRequired(true);
        commandData.add(Commands.slash("inviter", DiscordInviterMessages.DESCRIPTION_COMMAND.getMessage()).addOptions(diUserTag));

        //Grouper
        OptionData grpName = new OptionData(OptionType.STRING, "name", GrouperMessages.DESCRIPTION_NAME_ARGUMENT.getMessage()).setRequired(true);
        commandData.add(Commands.slash("groupe", GrouperMessages.DESCRIPTION_COMMAND.getMessage()).addOptions(grpName));


        return commandData;
    }

}
