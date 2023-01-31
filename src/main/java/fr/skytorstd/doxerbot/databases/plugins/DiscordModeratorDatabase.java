package fr.skytorstd.doxerbot.databases.plugins;

import fr.skytorstd.doxerbot.databases.DatabaseConnection;
import fr.skytorstd.doxerbot.object.Warn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscordModeratorDatabase {
    /*
    GETTER
     */
    /**
     * Return an hashmap of integer and string by uid
     * @param UID
     * @return
     */
    public static ArrayList<Warn> getListWarnsByUID(String UID){
        final String sql = "SELECT * FROM warns WHERE uid="+UID;
        ArrayList<Warn> resultat = new ArrayList<>();

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                resultat.add(new Warn(ResultatSQL.getInt("id"), ResultatSQL.getString("warn_name"), ResultatSQL.getString("uid")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultat;
    }

    /**
     * Return the id max of warn
     * @return
     */
    public static int getMaxId(){
        final String sql = "SELECT MAX(id) as maxId FROM warns";
        int resultat = 0;

        ResultSet ResultatSQL = null;
        try {
            ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                resultat = ResultatSQL.getInt("maxId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultat;
    }
    public static Warn getWarnOnDB(String id){
        final String sql = "SELECT * FROM warns WHERE id='"+id+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return new Warn(ResultatSQL.getInt("id"), ResultatSQL.getString("warn_name"), ResultatSQL.getString("uid"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /*
    ADD
     */

    /**
     * Ajoute un warn à l'utilisateur dans la BD
     * @param warn
     */
    public static void addWarnByUIDAndNameWarn(Warn warn){
        final int warnId = getMaxId()+1;
        final String sql = "INSERT INTO warns(id, warn_name, uid) VALUES('"+warnId+"','"+warn.getName()+"','"+warn.getUid()+"');";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    REMOVE
     */
    /**
     * Supprime un warn
     * @param warn
     */
    public static void removeWarnByIdWarn(Warn warn){
        final String sql = "DELETE FROM warns WHERE id='"+warn.getId()+"'";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    UPDATE
     */
}
