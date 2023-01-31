package fr.skytorstd.doxerbot.messages;

public enum WeatherMessages {

    DESCRIPTION("Permet de regarder la météo"),
    DESCRIPTION_VILLE_ARGUMENT("Permet de voir la météo d'une ville précise");

    private String message;

    WeatherMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
