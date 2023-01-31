package fr.skytorstd.doxerbot.databases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConfigDoxerDatabase {
    /*
    GETTER
     */

    /**
     * Retourne le status d'un plugin par son nom
     * @param pluginName
     * @return
     */
    public static boolean getStatePluginWithPluginName(String pluginName) {
        String sql = "SELECT * FROM configurationLigar WHERE pluginName='" + pluginName + "'";
        int intBoolean = 0;

        ResultSet resultatSQL = null;
        try {
            resultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            intBoolean = resultatSQL.getInt("pluginState");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        if(intBoolean == 1)
            return true;
        else
            return false;
    }

    /**
     * Retourne une liste de String contenant tous les nom des plugins contenue dans la DB
     * @return
     */
    public static ArrayList<String> getAllPluginNameInDB(){
        ArrayList<String> pluginNameList = new ArrayList<>();
        String sql = "SELECT pluginName FROM configurationLigar";

        try {
            ResultSet resultatSQL = DatabaseConnection.getInstance().getStatement().executeQuery(sql);

            while(resultatSQL.next()){
                pluginNameList.add(resultatSQL.getString("pluginName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pluginNameList;
    }

    /*
    CREATE
     */
    /*
    UPDATE

    public static void updatePluginStateWithPlguinName(Plugin plugin){
        int pluginStateInt = 0;
        if(plugin.isConfigState())
            pluginStateInt = 1;

        String sql = "UPDATE configurationLigar SET pluginState='"+pluginStateInt+"' WHERE pluginName='"+plugin.getName()+"'";

        try {
            DatabaseConnection.getInstance().getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/
    /*
    DELETE
     */
}
