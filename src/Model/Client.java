package Model;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by Nadin on 08/11/16.
 */
public class Client implements Serializable{
    private int nRoom;
    private String name;


    public Client(int nRoom, String name){
        this.nRoom = nRoom;
        this.name = name;

    }

    public int getnRoom() {
        return nRoom;
    }

    public void setnRoom(int nRoom) {
        this.nRoom = nRoom;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
