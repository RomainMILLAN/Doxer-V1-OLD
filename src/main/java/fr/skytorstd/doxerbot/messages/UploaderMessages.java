package fr.skytorstd.doxerbot.messages;

public enum UploaderMessages {
    DESCRIPTION("Ce plugin permet d'envoyer un message dans un channel quand le bot Ligar est en ligne"),
    CONNECTED_MESSAGE("[**LG**] Ligar est connecté");

    private String message;
    UploaderMessages(String message) {this.message = message;}
    public String getMessage() {return message;}
}
