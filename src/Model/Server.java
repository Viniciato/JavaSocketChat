package Model;



import Controller.ChatWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private BufferedWriter client;
    private ServerSocket server;
    private Socket connection;
    private String name;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader buffReader;

    public Server(Socket connection, ServerSocket server){
        this.connection = connection;
        this.server = server;
        try{
            inputStream = this.connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            buffReader = new BufferedReader(inputStreamReader);
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void run(){
        try{
            String message;
            OutputStream outStream =  this.connection.getOutputStream();
            Writer writer = new OutputStreamWriter(outStream);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            client = bufferedWriter;
            name = message = buffReader.readLine();
            System.out.println(message);
//            openChatWindow();
            while(message != null)
            {
                message = buffReader.readLine();
                System.out.println(message);
            }
            System.out.println("Cliente Desconectou-se!");

        }catch (Exception e) {
            e.printStackTrace();

        }
    }

//    public void openChatWindow() throws IOException{
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/chatWindow.fxml"));
//        Parent home_page_parent = loader.load();
//
//        ChatWindowController controller = loader.getController();
////        controller.setParams(buffeWriter, nameLabel.getText());
//
//        Scene home_page_scene = new Scene(home_page_parent);
//        Stage main_stage = new Stage();
//        main_stage.setScene(home_page_scene);
//        main_stage.setResizable(false);
//        main_stage.setMaximized(false);
//        main_stage.show();
//    }
}
