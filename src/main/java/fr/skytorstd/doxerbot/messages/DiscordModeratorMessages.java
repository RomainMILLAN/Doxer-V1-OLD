package fr.skytorstd.doxerbot.messages;

public enum DiscordModeratorMessages {
    DESCRIPTION("Ce plugin permet au staff de modérer le discord"),
    DESCRIPTION_COMMAND("Warns utilisateur"),
    DESCRIPTION_USER_ARGUMENT("Utilisateur"),
    DESCRIPTION_ACTION_ARGUMENT("Action à effectué"),
    WARNS_SHOW_SUCESS("Liste des warns"),
    WARN_ADD("Warn ajouté à l'utilisateur"),
    TI_NAME_WARN("Nom du warn"),
    TI_ID_USER("Identifiant de l'utilisateur"),
    TI_ID_WARN("Identifiant du warn"),
    MODAL_NAME_CREATION("Creation de warn"),
    MODAL_NAME_REMOVE("Suppression de warn"),
    NONE_WARNS("Aucun warns pour cette utilisateur"),
    ID_NOT_FOUND_ON_DB("L'identifiant du warn n'est pas contenue dans la Base de Donées"),
    WARN_REMOVE_SUCCESS("Le warn à était supprimée"),
    LOG_ERROR_WARN_REMOVE("Erreur lors de la suppression de warn"),
    LOG_SUCCESS_WARN_REMOVE("Suppression de warn");

    private String message;

    DiscordModeratorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
