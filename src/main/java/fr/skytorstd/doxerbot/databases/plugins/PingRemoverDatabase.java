package fr.skytorstd.doxerbot.databases.plugins;

import fr.skytorstd.doxerbot.databases.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PingRemoverDatabase {
    /*
    GETTER
     */
    /**
     * Return list of ping to remove
     * @return
     */
    public static ArrayList<String> getListPings(String idGuild){
        final String sql = "SELECT * FROM pingRemover WHERE idGuild='"+idGuild+"'";
        ArrayList<String> resultat = new ArrayList<>();

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                resultat.add(ResultatSQL.getString("id_role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultat;
    }
    public static boolean isInDB(String id_role, String idGuild){
        final String sql = "SELECT COUNT(*) as isIn FROM pingRemover WHERE id_role='"+id_role+"' AND idGuild='"+idGuild+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                if(ResultatSQL.getInt("isIn") == 1)
                    return true;
                else
                    return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    /*
    AJOUT
     */
    /**
     * Add a ping to the DB
     * @param id_role
     */
    public static void addPing(String id_role, String idGuild){
        final String sql = "INSERT INTO pingRemover('id_role', 'idGuild') VALUES('"+id_role+"', '"+idGuild+"');";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    DELETE
     */
    /**
     * Delete role to the db
     * @param id_role
     */
    public static void removePing(String id_role, String idGuild){
        final String sql = "DELETE FROM pingRemover WHERE id_role='"+id_role+"' AND idGuild='"+idGuild+"'";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
