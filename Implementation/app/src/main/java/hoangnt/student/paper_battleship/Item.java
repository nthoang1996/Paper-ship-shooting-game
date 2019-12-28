package hoangnt.student.paper_battleship;

public class Item {
    private String imageName;
    private int type;
    private int level = 0;
    private boolean unlock;
    private int desExp;
    private int curExp;
    private String spellName;

    public Item(String imageName, int type, int level, boolean unlock, int desExp, int curExp, String spellName) {
        this.imageName = imageName;
        this.type = type;
        this.level = level;
        this.unlock = unlock;
        this.desExp = desExp;
        this.curExp = curExp;
        this.spellName = spellName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String mageName) {
        this.imageName = mageName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isUnlock() {
        return unlock;
    }

    public void setUnlock(boolean unlock) {
        this.unlock = unlock;
    }

    public int getDesExp() {
        return desExp;
    }

    public void setDesExp(int desExp) {
        this.desExp = desExp;
    }

    public int getCurExp() {
        return curExp;
    }

    public void setCurExp(int curExp) {
        this.curExp = curExp;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    @Override
    public String toString() {
        return "Item{" +
                "mageName='" + imageName + '\'' +
                ", type=" + type +
                ", level=" + level +
                ", unlock=" + unlock +
                ", desExp=" + desExp +
                ", curExp=" + curExp +
                ", spellName='" + spellName + '\'' +
                '}';
    }
}
