package fr.skytorstd.doxerbot.object;

public class VoiceClickChannel {
    private String id_channel;
    private String name;

    public VoiceClickChannel(String id_channel, String name) {
        this.id_channel = id_channel;
        this.name = name;
    }

    public String getId_channel() {
        return id_channel;
    }

    public String getName() {
        return name;
    }
}
