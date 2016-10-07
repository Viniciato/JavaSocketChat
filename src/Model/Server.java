package Model;

import java.net.Socket;

/**
 * Created by Nadin on 9/29/16.
 */
public class Server extends Thread {
    private Socket connection;

    public Server(Socket connection){
        this.connection = connection;
    }
}
