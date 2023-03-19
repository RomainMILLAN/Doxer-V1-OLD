package fr.skytorstd.doxerbot.states;

public enum FileName {
    LOGGER_FILENAME("DoxerLogs.txt");
    private String fileName;
    
    FileName(String fileName) {this.fileName = fileName;}

    /**
     * Retourne le nom du fichier
     * @return
     */
    public String getFileName() {return fileName;}
}
