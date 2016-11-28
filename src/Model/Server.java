package Model;

import Controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    private ConnectionInfo connectionInfo;
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
            if(name.equals("997654")){
                conversation = new Conversation("Atendente", 100);
                name="Atendente";
            }
            else
                conversation = new Conversation(name, Controller.getRoom());

            ConnectionInfo cInfo = new ConnectionInfo(bufferedWriter, conversation);
            Controller.saves.add(cInfo);
            System.out.println(name);

            while (message != null) {
                message = buffReader.readLine();
                if(message!=null){
                System.out.println(message);
                message = message.replace("[", "");
                message = message.replace("]", "");
                System.out.println(message);
                String[] are = message.split(",");
                System.out.println(are[0]);
                if (message!= null && are[0].contains("9999")){
                    sendToAll(bufferedWriter, are);
                    message="";
                }else if(message!= null &&  are[0].contains("8888")){
                    if(are!= null && are[1].contains("9783940")){
                        System.out.println("GETT!!!");
                        OutputStream outputStream = connection.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                        ArrayList<Conversation> conversations = new ArrayList<>();
                        for (ConnectionInfo coInf : Controller.saves){
                            conversations.add(coInf.getConv());
                        }
                        oos.writeObject(conversations);
                        oos.flush();
                    }
                    else if(are!= null && are[1].contains("chSt"))
                    {
                        String room = are[2];
                        System.out.println(room.toString().replace(" ",""));
                        room = room.trim();
                        for (ConnectionInfo conI : Controller.saves)
                            if (conI.getConv().getRoom() == conversation.getRoom() && conI.getConv().getClientName()==conversation.getClientName()) {
                                conI.getConv().setRoom(Integer.valueOf(room));
                                conversation.setRoom(Integer.valueOf(room));
                            }
                    }
                    else if(are!=null && are[1].contains("7777")){
                        System.out.println("Get History");
                        String room = are[2];
                        System.out.println(room.toString().replace(" ",""));
                        room = room.trim();
                        ArrayList<String> history = new ArrayList<>();
                        for (ConnectionInfo conI : Controller.saves)
                            if (conI.getConv().getRoom() == conversation.getRoom() && conI.getConv().getClientName()==conversation.getClientName())
                                history = conI.getConv().getHistory();
                        OutputStream outputStream = connection.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                        oos.writeObject(history);
                        oos.flush();
                    }
                }
                }
            }
            Controller.saves.get(Controller.saves.indexOf(cInfo)).getConv().setStatus("Finalizado");
            System.out.println("Cliente Desconectou-se!");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void sendToAll(BufferedWriter bwSaida, String[] msg) throws  IOException
    {
        conversation.addMessage(name + " -> " +msg[1].toString()+"\r\n");
        for (int i = 0; i < Controller.saves.size(); i++) {
            ConnectionInfo c = Controller.saves.get(i);
            if(c.getConv().getRoom() == conversation.getRoom() && !c.getConv().getClientName().equals(conversation.getClientName())){
                c.getBuf().write(name + " -> " +msg[1].toString()+"\r\n");
                c.getConv().addMessage(name + " -> " +msg[1].toString()+"\r\n");
                c.getBuf().flush();
            }
        }
    }

}
