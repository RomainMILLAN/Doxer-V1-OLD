package fr.skytorstd.doxerbot.databases.plugins;

import fr.skytorstd.doxerbot.databases.DatabaseConnection;
import fr.skytorstd.doxerbot.object.RoleSubscriber;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleSubscriberDatabase {
    /*
    GETTER
     */
    public static ArrayList<RoleSubscriber> getListRoleSubscriber(String idGuild){
        final String sql = "SELECT * FROM rolesubscriber WHERE idGuild='"+idGuild+"'";
        ArrayList<RoleSubscriber> resultat = new ArrayList<>();

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                resultat.add(new RoleSubscriber(ResultatSQL.getString("id_role")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultat;
    }
    public static RoleSubscriber getRoleSubscriber(String idRole, String idGuild){
        final String sql = "SELECT * FROM rolesubscriber WHERE id_role='"+idRole+"' AND idGuild='"+idGuild+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return new RoleSubscriber(ResultatSQL.getString("id_role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    /*
    AJOUT
     */
    public static void addRoleSubscriber(RoleSubscriber cs, String idGuild){
        final String sql = "INSERT INTO rolesubscriber('id_role', 'idGuild') VALUES('"+cs.getId_role()+"', '"+idGuild+"');";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    SUPPRESION
     */
    public static void removeRoleSubscriber(String id_role){
        final String sql = "DELETE FROM 'rolesubscriber' WHERE id_role='"+id_role+"';";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
