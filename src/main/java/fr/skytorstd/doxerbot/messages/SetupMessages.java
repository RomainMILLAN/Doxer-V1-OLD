package fr.skytorstd.doxerbot.messages;

public enum SetupMessages {
    DESCRIPTION(""),
    DESCRIPTION_COMMAND("Permet de définir les variables nécéssaire au fonctionnement du bot"),
    DESCRIPTION_SELECT_ARGUMENT("Permet de choisir quelle variable est à définir"),
    DESCRIPTION_VALUE_ARGUMENT("Permet de définir la valeur"),
    ID_SUDO_NOT_EXIST("L\'identifiant renseigner n'existe pas"),
    INIT_OK("L\'initialisation du bot à été effectué"),
    BOT_ALREADY_INIT("Le bot à déja était intialisée");

    private String messages;

    SetupMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
