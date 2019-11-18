package hoangnt.student.paper_battleship;

import java.util.ArrayList;
import java.util.Arrays;

public class Ship {
    ArrayList<Integer> id_part;
    int origentation;
    ArrayList<Integer> position;
    ArrayList<Boolean> status;
    int type;

    public ArrayList<Integer> getId_part() {
        return id_part;
    }

    public void setId_part(ArrayList<Integer> id_part) {
        this.id_part = id_part;
    }

    public int getOrigentation() {
        return origentation;
    }

    public void setOrigentation(int origentation) {
        this.origentation = origentation;
    }

    public ArrayList<Integer> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Integer> position) {
        this.position = position;
    }

    public ArrayList<Boolean> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<Boolean> status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Ship(ArrayList<Integer> id_part, int origentation, ArrayList<Integer> position, ArrayList<Boolean> status, int type) {
        this.id_part = id_part;
        this.origentation = origentation;
        this.position = position;
        this.status = status;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id_part=" + id_part +
                ", origentation=" + origentation +
                ", position=" + position +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
