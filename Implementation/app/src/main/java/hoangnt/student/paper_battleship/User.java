package hoangnt.student.paper_battleship;

public class User {
    private String name;
    private int level;


    public User(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "User{" +
                "Name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
