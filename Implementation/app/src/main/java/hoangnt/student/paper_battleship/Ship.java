package hoangnt.student.paper_battleship;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

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

    public void setId_part(int index, int value) {
        this.id_part.set(index,value);
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

    public void setPosition(int index, int value) {
        this.position.set(index,value);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Boolean> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<Boolean> status) {
        this.status = status;
    }

    public void setStatus(int index, Boolean value) {
        this.status.set(index,value);
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
