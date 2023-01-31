package fr.skytorstd.doxerbot.messages;

public enum HelperMessages {
    DESCRIPTION("Ce plugin permet de vous aidez dans les commandes à réaliser concernant un plugin"),
    DESCRIPTION_HELP_COMMAND("Permet de regarder la description et les commandes d'un plugin"),
    DESCRIPTION_HELP_ARGUMENT("Permet de regarder le help du plugin choisi"),
    DESCRIPTION_PLUGIN_COMMAND("Permet de voir la liste des plugins"),
    PLUGIN_DOESNT_EXIST("Le plugin n'existe pas"),
    LOG_PLUGIN_DOESNT_EXIST("Plugin rentrer en parametre inexistant"),
    LOG_SHOW_COMMANDS("Visualisation des commandes"),
    LOG_PLUGINS_SHOW("Visualisation des plugins");

    private String message;

    HelperMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
