package fr.skytorstd.doxerbot.databases;

import fr.skytorstd.doxerbot.object.ConfigurationGuild;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConfigurationDoxerDatabase {

    /**
     * Retourne toute la configuration d'une guild
     * @param idGuild
     * @return
     */
    public static ConfigurationGuild getConfigurationGuildForIdGuild(String idGuild){
        String sql = "SELECT * FROM configuration WHERE idGuild='"+idGuild+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return new ConfigurationGuild(idGuild, ResultatSQL.getString("idcLog"), ResultatSQL.getString("idcDSentry"), ResultatSQL.getString("idcUploader"), ResultatSQL.getString("idcJoinQuit"), ResultatSQL.getString("idcatGrouper"), ResultatSQL.getString("idrSudo"), ResultatSQL.getString("idrAdmin"), ResultatSQL.getString("idrModerateur"), ResultatSQL.getString("idrUser"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Retourne l'identifiant du channel log pour 1 guild
     * @param idGuild
     * @return
     */
    public static String getIdcLogForIdGuild(String idGuild){
        String sql = "SELECT idcLog FROM configuration WHERE idGuild='"+idGuild+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return ResultatSQL.getString("idcLog");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Retourne l'identifiant du channel DiscordSentry pour 1 guild
     * @param idGuild
     * @return
     */
    public static String getIdcDSentryForIdGuild(String idGuild){
        String sql = "SELECT idcDSentry FROM configuration WHERE idGuild='"+idGuild+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return ResultatSQL.getString("idcDSentry");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Retourne un boolean indiquant si une guild fait partie de la configuration ou non
     * @param idGuild
     * @return
     */
    public static boolean idGuildIsInConfiguration(String idGuild){
        String sql = "SELECT COUNT(*) as nbGuild FROM configuration WHERE idGuild='"+idGuild+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                if(ResultatSQL.getInt("nbGuild") == 1)
                    return true;
                else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    /**
     * Retourne une liste contenant tous les id de guild du bot
     * @return
     */
    public static ArrayList<String> getAllIdGuild(){
        String sql = "SELECT idGuild FROM configuration";
        ArrayList<String> resultats = new ArrayList<>();

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                resultats.add(ResultatSQL.getString("idGuild"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultats;
    }


    /**
     * Modifier la valeur d'un parametre pour la configuration
     * @param idGuild
     * @param selection
     * @param value
     */
    public static void setupBySelectionAndValue(String idGuild, String selection, String value){
        String sql = "UPDATE configuration SET '"+selection+"'='"+value+"' WHERE idGuild='"+idGuild+"'";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialise un tuple contenant l'id d'une guild
     * @param idGuild
     * @param idrSudo
     */
    public static void initBot(String idGuild, String idrSudo){
        String sql = "INSERT INTO configuration('idGuild', 'idrSudo') VALUES('"+idGuild+"','"+idrSudo+"')";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Supprime le tuple d'une guild
     * @param idGuild
     */
    public static void destroyGuildBot(String idGuild){
        String sql = "DELETE FROM configuration WHERE idGuild='"+idGuild+"'";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
