package fr.skytorstd.doxerbot.messages;

public enum WelcomerMessages {
    DESCRIPTION("Ce plugin permet de souhaiter la bienvenue au nouvelles personnes"),
    LOG_NEW_MEMBER("Nouveau membre sur le serveur");

    private String message;

    WelcomerMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
