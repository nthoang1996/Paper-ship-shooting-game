package hoangnt.student.paper_battleship;

public class Setting {
    String language;
    String userName;

    public Setting(String language, String userName) {
        this.language = language;
        this.userName = userName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "language='" + language + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
