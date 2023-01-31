package fr.skytorstd.doxerbot.object;

public class Warn {
    private int id;
    private String name;
    private String uid;

    public Warn(int id, String name, String uid) {
        this.id = id;
        this.name = name;
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }
}
