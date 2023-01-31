package fr.skytorstd.doxerbot.messages;

public enum DiscordInviterMessages {
    DESCRIPTION("Permet d'inviter un utilisateur dans un channel vocal"),
    DESCRIPTION_COMMAND("Permet d'utiliser le plugin"),
    DESCRIPTION_USERTAG_ARGUMENT("Indiquer l'utilisateur à inviter (Wabezeter#3701)"),
    ERROR_VOICE("Aucune connexion à un channel vocal"),
    INVITER_CREATE("Invitation crée"),
    MEMBER_JOIN_DI("Un nouveau membre vient de rejoindre avec DiscordInviter"),
    MEMBER_LEAVE_DI("Un membre à était exclu car il était dans la liste DiscordInviter");

    private String message;

    DiscordInviterMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
