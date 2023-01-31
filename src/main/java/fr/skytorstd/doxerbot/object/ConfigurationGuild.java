package fr.skytorstd.doxerbot.object;

public class ConfigurationGuild {
    private String idGuild;
    private String idcLog;
    private String idcDSentry;
    private String idcUploader;
    private String idcJoinQuit;
    private String idcatGrouper;
    private String idrSudo;
    private String idrAdmin;
    private String idrModerateur;
    private String idrUser;

    /*
    CONSTRUCTEUR
     */
    public ConfigurationGuild(String idGuild, String idcLog, String idcDSentry, String idcUploader, String idcJoinQuit, String idcatGrouper, String idrSudo, String idrAdmin, String idrModerateur, String idrUser) {
        this.idGuild = idGuild;
        this.idcLog = idcLog;
        this.idcDSentry = idcDSentry;
        this.idcUploader = idcUploader;
        this.idcJoinQuit = idcJoinQuit;
        this.idcatGrouper = idcatGrouper;
        this.idrSudo = idrSudo;
        this.idrAdmin = idrAdmin;
        this.idrModerateur = idrModerateur;
        this.idrUser = idrUser;
    }

    /*
    GETTER & SETTERS
     */
    public String getIdGuild() {
        return idGuild;
    }

    public void setIdGuild(String idGuild) {
        this.idGuild = idGuild;
    }

    public String getIdcLog() {
        return idcLog;
    }

    public void setIdcLog(String idcLog) {
        this.idcLog = idcLog;
    }

    public String getIdcDSentry() {
        return idcDSentry;
    }

    public void setIdcDSentry(String idcDSentry) {
        this.idcDSentry = idcDSentry;
    }

    public String getIdcUploader() {
        return idcUploader;
    }

    public void setIdcUploader(String idcUploader) {
        this.idcUploader = idcUploader;
    }

    public String getIdcJoinQuit() {
        return idcJoinQuit;
    }

    public void setIdcJoinQuit(String idcJoinQuit) {
        this.idcJoinQuit = idcJoinQuit;
    }

    public String getIdcatGrouper() {
        return idcatGrouper;
    }

    public void setIdcatGrouper(String idcatGrouper) {
        this.idcatGrouper = idcatGrouper;
    }

    public String getIdrSudo() {
        return idrSudo;
    }

    public void setIdrSudo(String idrSudo) {
        this.idrSudo = idrSudo;
    }

    public String getIdrAdmin() {
        return idrAdmin;
    }

    public void setIdrAdmin(String idrAdmin) {
        this.idrAdmin = idrAdmin;
    }

    public String getIdrModerateur() {
        return idrModerateur;
    }

    public void setIdrModerateur(String idrModerateur) {
        this.idrModerateur = idrModerateur;
    }

    public String getIdrUser() {
        return idrUser;
    }

    public void setIdrUser(String idrUser) {
        this.idrUser = idrUser;
    }
}
