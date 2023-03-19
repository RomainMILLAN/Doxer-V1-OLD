package fr.skytorstd.doxerbot.messages;

public enum ConfigurationPluginsMessages {
    DESCRIPTION_COMMAND("Permet de gérer la configuration du bot"),
    DESCRIPTION_SELECTION_ARGUMENT("Sélectionner le plugin"),
    DESCRIPTION_ACTION_ARGUMENT("Action à effectuer"),

    OPTION_TRUE("Activé le plugin"),
    OPTION_FALSE("Désactivé le plugin"),
    OPTION_SHOW("Voir le status"),

    PLUGIN_DOESNT_EXIST(":x: Le plugin n'existe pas"),
    PLUGIN_ALREADY_ACTIVATE(":x: Le plugin est déja activé");

    private String message;

    ConfigurationPluginsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
