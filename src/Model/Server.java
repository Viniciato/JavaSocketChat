package Model;

import Controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nadin on 9/29/16.
 */
public final class Server extends Thread {

    private ServerSocket server;
    private Socket connection;
    private String name;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader buffReader;

    public Server(Socket connection, ServerSocket server) {
        try {
            this.connection = connection;
            this.server = server;
            inputStream = this.connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            buffReader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            String message;
            OutputStream outStream = this.connection.getOutputStream();
            Writer writer = new OutputStreamWriter(outStream);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            Controller.clients.add(bufferedWriter);
            name = message = buffReader.readLine();

            while (message != null) {
                message = buffReader.readLine();
                sendToAll(bufferedWriter, message);
            }
            Controller.clients.remove(bufferedWriter);
            System.out.println("Cliente Desconectou-se!");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException
    {
        BufferedWriter bwS;
        for(BufferedWriter bw : Controller.clients){
            bwS = (BufferedWriter)bw;
            if(!(bwSaida == bwS)){
                bw.write(name + " -> " + msg+"\r\n");
                bw.flush();
            }
        }
    }
}
