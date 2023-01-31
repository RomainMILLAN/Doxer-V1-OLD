package fr.skytorstd.doxerbot.messages;

public enum MusicPlayerMessages {
    DESCRIPTION("Permet d'écouter de la musique dans un channel vocal"),
    DESCRIPTION_COMMAND("Permet de gérer le bot"),
    DESCRIPTION_LINK_ARGUMENT("Mettez le lien de la musique à écouter"),
    DESCRIPTION_ACTION_ARGUMENT("Action à effectuer"),
    BOT_ALREADY_CONNECT("Bot déjà présent dans un channel vocal"),
    DO_CONNECT_TO_COMMAND("Vous devez être connecté à un channel vocal pour effectué cette commande"),
    CONNECT_TO("Connection à"),
    DO_IN_SAME_CHANNEL("Vous devez être dans le même channel vocal que le bot"),
    MUSIC_STOP("Musique à l'arrêt"),
    NO_WAITING_LIST("Aucune file d'attente"),
    SKIP_MUSIC("Musique passée"),
    MUSIC_CURRENT("Musique courante"),
    MUSIC_REPEAT_ON("Musique sur le mode répétition"),
    MUSIC_REPEAT_OFF("Musique sur le mode non-répétition"),
    BOT_LEAVE_CHANNEL("Le bot à quitté le channel vocal"),
    LINK_ARGUMENT_NULL("Lien manquant"),
    NEW_MUSIC("Nouvelle musique sur le bot"),
    UPLOAD_NONE("Chargement de la musique échouée"),
    RESULT_NONE("Aucun résultat"),
    LOG_BOT_CONNEC_TO_VOCAL("Bot connecter dans un channel vocal"),
    LOG_DO_CONNECT_TO_COMMAND("L'utilisateur doit être connecté à un vocal pour effectuer cette commande"),
    LOG_WAITING_LIST("Liste d'attente");

    private String message;

    MusicPlayerMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
