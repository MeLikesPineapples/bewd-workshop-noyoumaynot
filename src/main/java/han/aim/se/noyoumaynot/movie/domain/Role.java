package han.aim.se.noyoumaynot.movie.domain;

public class Role {
    private String name;
    private boolean admin;

    public Role(String name, boolean admin) {
        this.name = name;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public boolean getAdmin() {
        return admin;
    }
}
