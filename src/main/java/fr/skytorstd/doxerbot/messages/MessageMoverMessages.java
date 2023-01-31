package fr.skytorstd.doxerbot.messages;

public enum MessageMoverMessages {
    DESCRIPTION("Permet de déplacer un message d'un channel à un autre"),
    DESCRIPTION_COMMAND_MESSAGE_ID("Identifiant du message à déplacer"),
    DESCRIPTION_COMMAND_CHANNEL_ID("Identifiant du channel");

    private String message;

    MessageMoverMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
