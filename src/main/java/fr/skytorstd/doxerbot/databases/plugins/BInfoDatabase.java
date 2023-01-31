package fr.skytorstd.doxerbot.databases.plugins;

import fr.skytorstd.doxerbot.databases.DatabaseConnection;
import fr.skytorstd.doxerbot.object.Cour;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BInfoDatabase {
    /*
    GETTER
     */

    /**
     * Return id max
     * @return
     */
    public static int getMaxId(){
        final String sql = "SELECT MAX(id) as id FROM BIC_edt";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return ResultatSQL.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    /**
     * Return curse by id
     * @return
     */
    public static Cour getCourById(int id){
        final String sql = "SELECT * FROM BIC_edt WHERE id='"+id+"'";

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                return new Cour(ResultatSQL.getInt("id"), ResultatSQL.getString("groupe"), ResultatSQL.getString("name"), ResultatSQL.getString("salle"), ResultatSQL.getString("professeur"), ResultatSQL.getString("heureDebut"), ResultatSQL.getString("heureFin"), ResultatSQL.getString("date"), ResultatSQL.getString("information"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Return a liste of course by date and group
     * @param date
     * @param groupe
     * @return
     */
    public static ArrayList<Cour> getListCourByDateAndGroupe(String date, String groupe){
        final String sql = "SELECT * FROM BIC_edt WHERE date='"+date+"' AND groupe='"+groupe+"' ORDER BY heureDebut ASC";
        ArrayList<Cour> resultat = new ArrayList<>();

        try {
            ResultSet ResultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(ResultatSQL.next()){
                resultat.add(new Cour(ResultatSQL.getInt("id"), ResultatSQL.getString("groupe"), ResultatSQL.getString("name"), ResultatSQL.getString("salle"), ResultatSQL.getString("professeur"), ResultatSQL.getString("heureDebut"), ResultatSQL.getString("heureFin"), ResultatSQL.getString("date"), ResultatSQL.getString("information")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultat;
    }
    /*
    AJOUT
     */
    /**
     * Add course on DB
     * @param cour
     */
    public static void ajoutCour(Cour cour){
        final String sql = "INSERT INTO BIC_edt('id', 'groupe', 'name', 'salle', 'professeur', 'heureDebut', 'heureFin', 'date', 'information') VALUES('"+cour.getId()+"', '"+cour.getGroupe()+"', '"+cour.getName()+"', '"+cour.getSalle()+"', '"+cour.getProfesseur()+"', '"+cour.getHeureDebut()+"', '"+cour.getHeureFin()+"', '"+cour.getDate()+"', '"+cour.getInformation()+"');";

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
     * Delete course on DB by groupe
     * @param groupe
     */
    public static void deleteAllCourByGroupeId(String groupe){
        final String sql = "DELETE FROM BIC_edt WHERE groupe='"+groupe+"'";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    UPDATE
     */
    public static void updateInformationOfCourse(Cour cour){
        final String sql = "UPDATE BIC_edt SET information='"+cour.getInformation()+"' WHERE id='"+cour.getId()+"';";

        try {
            DatabaseConnection.getInstance().getStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
