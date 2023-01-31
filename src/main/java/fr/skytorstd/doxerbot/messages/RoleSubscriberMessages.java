package fr.skytorstd.doxerbot.messages;

public enum RoleSubscriberMessages {

    DESCRIPTION("Permet de vous inscrire dans des channels textuel"),
    DESCRIPTION_COMMAND("Permet de gérer le plugin"),
    DESCRIPTION_ACTION_ARGUMENT("Action à effectué"),
    LOG_SHOW_CS_LIST("Liste des channels ChannelSubscriber"),
    CS_ADD_SUCCESS("ChannelSubscriber ajouté"),
    CS_REMOVE_SUCCESS("ChannelSubscriber supprimé"),
    CS_ADD_ROLE_SUCCESS("Ajout du rôle"),
    CS_REMOVE_ROLE_SUCCESS("Suppression du rôle"),
    CS_SEND_MESSAGE("Message ChannelSubscriber envoyé");

    private String message;

    RoleSubscriberMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
