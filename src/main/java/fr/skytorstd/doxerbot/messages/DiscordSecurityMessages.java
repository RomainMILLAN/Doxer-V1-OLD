package fr.skytorstd.doxerbot.messages;

public enum DiscordSecurityMessages {
    DESCRIPTION("Ce plugin permet de mettre de la sécurité sur vos discord"),
    DESCRIPTION_COMMAND("Permet de confirmer une nouvelle personne"),
    DESCRIPTION_USER_ARGUMENT("Utilisateur à confirmer"),
    DESCRIPTION_ROLE_ARGUMENT("Role à ajouter"),
    DESCRIPTION_RENAME_ARGUMENT("Nom à renomer"),
    USER_CONFIRM("Utilisateur confirmé"),
    USER_ALREADY_CONFIRM("Utilisateur déjà confirmé");

    private String message;

    DiscordSecurityMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
