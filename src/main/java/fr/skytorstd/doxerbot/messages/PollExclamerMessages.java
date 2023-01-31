package fr.skytorstd.doxerbot.messages;

public enum PollExclamerMessages {
    DESCRIPTION("Permet de crée un sondage"),
    DESCRIPTION_NAME_ARGUMENT("Nom du sondage"),
    DESCRIPTION_RES_ARGUMENT("Résultats du sondage"),
    LOG_SONDAGE_CREATE("Sondage crée");

    private String messages;

    PollExclamerMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
