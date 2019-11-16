package hoangnt.student.paper_battleship;

public class BattleHistory {
    private int ID;
    private String enemy;
    private String datetime;
    private int result;
    private int receiveExp;

    public BattleHistory(int ID, String enemy, String datetime, int result, int receiveExp) {
        this.ID = ID;
        this.enemy = enemy;
        this.datetime = datetime;
        this.result = result;
        this.receiveExp = receiveExp;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getReceiveExp() {
        return receiveExp;
    }

    public void setReceiveExp(int receiveExp) {
        this.receiveExp = receiveExp;
    }

    @Override
    public String toString() {
        return "BattleHistory{" +
                "ID=" + ID +
                ", enemy='" + enemy + '\'' +
                ", datetime='" + datetime + '\'' +
                ", result=" + result +
                ", receiveExp=" + receiveExp +
                '}';
    }
}
