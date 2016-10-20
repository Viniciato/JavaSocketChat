package Model;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nadin on 9/29/16.
 */
public class Server{
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
}