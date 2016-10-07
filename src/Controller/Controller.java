package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Model.Server;

public class Controller {
    @FXML private TextField passField;
    @FXML private TextField nameField;
    @FXML private TextField ipField;
    @FXML private TextField portField;

    @FXML void initServer(ActionEvent event) {
        try{
            ServerSocket server = new ServerSocket(Integer.parseInt(portField.getText()));
            ArrayList<BufferedWriter> clientes = new ArrayList<BufferedWriter>();

            while(true){
                System.out.println("Waiting for connection...");
                Socket connection = server.accept();
                System.out.println("User connected...");
                Thread t = new Server(connection);
                t.start();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
