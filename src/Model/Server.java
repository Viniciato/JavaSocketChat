package Model;

import Controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

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
    private Conversation conversation;

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
            name = message = buffReader.readLine();
            conversation = new Conversation(name, Controller.getRoom());
            Controller.cvs.add(conversation);
            System.out.println(name);

            while (message != null) {
                message = buffReader.readLine();
                System.out.println(message);
                if(message.contains("9783940")){
                    System.out.println("GETT!!!");
                    OutputStream outputStream = connection.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                    oos.writeObject(Controller.cvs);
                    oos.flush();
                }
                else{
                    System.out.println(message);
                    sendToAll(bufferedWriter, message);
                }
            }
            Controller.cvs.remove(conversation);

            System.out.println("Cliente Desconectou-se!");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException
    {
        for (int i = 0; i < Controller.cvs.size(); i++) {
            Conversation c = Controller.cvs.get(i);
            if(c.getRoom() == conversation.getRoom() && c.getClientName() != conversation.getClientName()){
                c.addMessage(msg+"\r\n");
                System.out.println(c.getClientName());
                bwSaida.write(name + " -> " + msg+"\r\n");
                bwSaida.flush();
            }
        }

//        BufferedWriter bwS;
//        for (int i = 0; i < Controller.cvs.size(); i++) {
//            BufferedWriter bw = Controller.cvs.get(i).getBfW();
//            bwS = (BufferedWriter) bw;
//            if(!(bwSaida == bwS) && conversation.getRoom() ==){
//                bw.write(name + " -> " + msg+"\r\n");
//                bw.flush();
//            }
//        }

    }
}
