package fr.skytorstd.doxerbot.object;

import java.util.ArrayList;

public class Plugin {
    private String name;
    private String description;
    private ArrayList<String> commands;

    public Plugin(String name) {
        this.name = name;
    }

    public Plugin(String name, String description, ArrayList<String> commands) {
        this.name = name;
        this.description = description;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }
}
