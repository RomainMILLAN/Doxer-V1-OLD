package fr.skytorstd.doxerbot.object;

import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;

public class Inviter {
    private String usertag;
    private AudioChannel audioChannel;

    public Inviter(String usertag, AudioChannel audioChannel) {
        this.usertag = usertag;
        this.audioChannel = audioChannel;
    }

    public String getUsertag() {
        return usertag;
    }

    public AudioChannel getAudioChannel() {
        return audioChannel;
    }
}
