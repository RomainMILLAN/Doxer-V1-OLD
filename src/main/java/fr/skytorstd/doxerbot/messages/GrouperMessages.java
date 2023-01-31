package fr.skytorstd.doxerbot.messages;

public enum GrouperMessages {
    DESCRIPTION("Permet de crée des groupes personnelle avec les personnes indiquée dans la commande"),
    DESCRIPTION_COMMAND("Crée un groupe"),
    DESCRIPTION_NAME_ARGUMENT("Met le nom passer en parametre"),
    GROUPE_CREATE("Groupe crée");

    private String message;

    GrouperMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
