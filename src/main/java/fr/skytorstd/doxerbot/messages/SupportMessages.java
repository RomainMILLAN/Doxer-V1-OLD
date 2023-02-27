package fr.skytorstd.doxerbot.messages;

public enum SupportMessages {
    DESCRIPTION("Permet de gérer les support"),
    DESCRIPTION_COMMAND("Permet d'utiliser le plugin support"),
    DESCRIPTION_ACTION_ARGUMENT("Permet de sélectionner l'action à effectué"),
    TICKET_CREATE_MESSAGE_CREATE("Le message de création de ticket à était envoyé"),
    TICKET_CREATE_SUCCESS("Vôtre ticket à était crée avec succès"),
    TICKET_DELETE_SUCCESS("Le ticket à était supprimé"),
    TICKET_DELETE_ERROR("Un problème est survenue dans la suppression du ticket");

    private String message;

    SupportMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
