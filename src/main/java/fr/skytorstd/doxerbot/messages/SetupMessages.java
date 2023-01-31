package fr.skytorstd.doxerbot.messages;

public enum SetupMessages {
    DESCRIPTION("Permet de mettre en place la configuration sur un serveur"),
    DESCRIPTION_COMMAND("Permet de définir les variables nécéssaire au fonctionnement du bot"),
    DESCRIPTION_SELECT_ARGUMENT("Permet de choisir quelle variable est à définir"),
    DESCRIPTION_VALUE_ARGUMENT("Permet de définir la valeur"),
    DESTROY_DESCRIPTION("Permet de supprimer le bot d'un serveur"),
    ID_SUDO_NOT_EXIST("L\'identifiant renseigner n'existe pas"),
    INIT_OK("L\'initialisation du bot à été effectué"),
    BOT_ALREADY_INIT("Le bot à déja était intialisée"),
    CONF_EDIT("La configuration à était modifié"),
    DESTROY_ERROR("Une erreur est survenue dans la suppression du bot"),
    DESTROY_CONFIRM("Le bot à été supprimé de votre serveur");

    private String messages;

    SetupMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
