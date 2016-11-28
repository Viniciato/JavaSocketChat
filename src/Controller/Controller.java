package Controller;

import Model.ConnectionInfo;
import Model.Conversation;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import Model.Server;

public class Controller {
    @FXML private TextField ipField;
    @FXML private TextField portField;
    @FXML private Button startButton;
    private static ArrayList<Integer> rooms = new ArrayList<>();
    public static ArrayList<ConnectionInfo> saves = new ArrayList<>();

    public static int getRoom(){
        boolean verify = false;
        int number = 100;
        while (!verify){
            Random gen = new Random();
            number = gen.nextInt(10);
            if(!rooms.contains(number)){
                rooms.add(number);
                verify = true;
            }
        }
        return number;
    }


    @FXML void initServer(ActionEvent event){
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                try{

                    InetAddress addr = InetAddress.getByName(ipField.getText());
                    Integer port = Integer.parseInt(portField.getText());
                    Integer backLog = 50;

                    ServerSocket server = new ServerSocket(port, backLog, addr);
                    startButton.setDisable(true);
                    ipField.setDisable(true);
                    portField.setDisable(true);

                    while(true){
                        System.out.println("Aguardando conexão...");
                        Socket con = server.accept();
                        System.out.println("Cliente conectado...");
                        Thread t = new Server(con, server);
                        t.start();
                    }

                }catch (Exception e) {
                    System.out.println(e);
                }
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }


}
