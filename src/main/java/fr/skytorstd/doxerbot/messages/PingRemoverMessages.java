package fr.skytorstd.doxerbot.messages;

public enum PingRemoverMessages {
    DESCRIPTION("Permet de supprimer des pings"),
    DESCRIPTION_COMMAND("Permet de gérer le plugin"),
    DESCRIPTION_PING_ARGUMENT("Ping"),
    DESCRIPTION_ACTION_ARGUMENT("Action à effectuer"),
    PINGS_SHOW_SUCCESS("Liste des pings affiché"),
    PING_NOT_IN_CMD("Aucun ping renseigner"),
    PING_NOT_IN_DB("Ping non reconnue"),
    PING_ALREADY_IN_DB("Ping déja présent dans la liste"),
    PING_REMOVE("Ping supprimé"),
    PING_ADD("Ping ajouté"),
    PING_DELETE("Ping utilisé dans un message supprimé");

    private String message;

    PingRemoverMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
