package fr.skytorstd.doxerbot.messages;

public enum VoiceClickMessages {
    DESCRIPTION("Permet de crée des channels vocaux"),
    DESCRIPTION_COMMAND("Permet de gérer le plugin"),
    DESCRIPTION_ACTION_ARGUMENT("Action à effectué"),
    SHOW_LIST_VOICECLICK("Liste des channels VoiceClick"),
    VCC_ADD("VoiceClick Channel ajouté"),
    VCC_REMOVE("VoiceClick Channel supprimé");

    private String message;

    VoiceClickMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
