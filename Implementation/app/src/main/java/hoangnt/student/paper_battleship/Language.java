package hoangnt.student.paper_battleship;

public class Language {
    private String key;
    private String text;
    private String name;

    public Language(String key, String text, String name) {
        this.key = key;
        this.text = text;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Language{" +
                "key='" + key + '\'' +
                ", text='" + text + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
