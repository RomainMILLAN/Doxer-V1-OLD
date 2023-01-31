package fr.skytorstd.doxerbot.messages;

public enum DiscordSentryMessages {
    VOICE_DEFEAN_ON("Sourdine"),
    VOICE_DEFEAN_OFF("Non Sourdine"),
    VOICE_MUTE_ON("Muet"),
    VOICE_MUTE_OFF("Non Muet");

    private String message;

    DiscordSentryMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
