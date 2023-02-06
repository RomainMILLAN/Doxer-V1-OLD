package fr.skytorstd.doxerbot.states;

public enum PluginName {
    UPLOADER("Uploader"),
    DISCORDPROFILER("DiscordProfiler"),
    CONFIGURATIONLIGAR("ConfigurationLigar"),
    MESSAGEMOVER("MessageMover"),
    WEATHER("Weather"),
    POLLEXCLAMER("PollExclamer"),
    DISCORDMODERTOR("DiscordModerator"),
    HELPER("Helper"),
    WELCOMER("Welcomer"),
    DISCORDSECURITY("DiscordSecurity"),
    BINFOCORE("BInfoCore"),
    VOICECLIK("VoiceClick"),
    MUSICPLAYER("MusicPlayer"),
    ROLESUBSCRIBER("RoleSubscriber"),
    DISCORDSENTRY("DiscordSentry"),
    DISCORDINVITER("DiscordInviter"),
    GROUPER("Grouper"),
    PINGREMOVER("PingRemover"),
    SETUP("Setup"),
    SUPPORT("Support");

    private String message;

    PluginName(String message) {
        this.message = message;
    }

    /**
     * Retourne le message choisi
     * @return
     */
    public String getMessage() {
        return message;
    }
}
