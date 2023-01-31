package fr.skytorstd.doxerbot.messages;

public enum DiscordProfilerMessages {
    DESCRIPTION("Permet de regarder son profil ou le profil de quelqu'un"),
    DESCRIPTION_UTILISATEUR_ARGUMENT("Sélectionner le profil d'un utilisateur");
    private String messages;
    DiscordProfilerMessages(String messages) {
        this.messages = messages;
    }
    public String getMessages() {
        return messages;
    }
}
