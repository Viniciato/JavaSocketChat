package Controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import Model.Server;
import javafx.stage.Stage;

public class Controller {
    @FXML private TextField passField;
    @FXML private TextField nameField;
    @FXML private TextField ipField;
    @FXML private TextField portField;



    @FXML void initServer(ActionEvent event){
        openChatWindow();
        try{
            InetAddress addr = InetAddress.getByName(ipField.getText());
            Integer port = Integer.parseInt(portField.getText());
            Integer backLog = 50;

            ServerSocket server = new ServerSocket(port, backLog, addr);
            System.out.println("Waiting for connection...");
            Socket connection = server.accept();
            System.out.println("User connected...");


//            Task task = new Task() {
//                @Override
//                protected Object call() throws Exception {
//                    return new Server(connection, server);
//                }
//
//                @Override
//                protected void succeeded() {
//                    openChatWindow(bufferedWriter, name);
//                }
//            };
            Server server2 = new Server(connection, server);
            server2.run();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public void openChatWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/chatWindow.fxml"));
            Parent home_page_parent = loader.load();

//            ChatWindowController controller = loader.getController();
//            controller.setParams(buffeWriter, nameLabel);

            Scene home_page_scene = new Scene(home_page_parent);
            Stage main_stage = new Stage();
            main_stage.setScene(home_page_scene);
            main_stage.setResizable(false);
            main_stage.setMaximized(false);
            main_stage.isAlwaysOnTop();
            main_stage.show();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



}
