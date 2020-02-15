package chatclient;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import interfaces.*;

public class CHatClient extends Application {
    BorderPane root;
    TextArea textarea;
    FlowPane flowPane;
    Button btn;
    TextField textfield;
    Scene scene;
    //Connection references
    Socket socket;
    DataInputStream dis;
    PrintStream ps;
    
    @Override
    public void init(){
        textfield=new TextField();
        btn=new Button("Send");
        flowPane=new FlowPane();
        textarea=new TextArea();
        root=new BorderPane();
        scene=new Scene(root,400,400);
    }
    @Override
    public void start(Stage primaryStage) {
    
        SceneConstruction();
        primaryStage.setTitle("Chatting Area");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Functions Caller
        PlayerSocketCreation();
        HandlerEvent();
        
        Thread th=new Thread(() -> {
            while(true){
                try {
                    String msg=dis.readLine();
                    Platform.runLater(() -> {
                        textarea.appendText(msg);
                    });
                    System.out.println("exception runlater");
                } catch (IOException ex) {
                }
            }
        });
        th.start();
    }
    public void SceneConstruction(){
        textarea.setPadding(new Insets(20, 5, 20, 5));
        textarea.setMaxSize(300,300);
        
        textfield.setPromptText("Typing ......");
        textfield.setPrefColumnCount(25);
        
        flowPane.getChildren().addAll(textfield,btn);
        flowPane.setPadding(new Insets(20, 30,20, 30));
        flowPane.setVgap(8);
        flowPane.alignmentProperty();
        
        root.setCenter(textarea);
        root.setBottom(flowPane);
    }
    public void PlayerSocketCreation(){
        try {
            socket=new Socket("127.0.0.1",5000);
            dis=new DataInputStream(socket.getInputStream());
            ps=new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(CHatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public void HandlerEvent(){
            btn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    Player player = new Player("mayahass", "123", "mayar", "hassan");
                    XOInterface xo = new XOInterface("Register", player);
                    Gson toServer = new Gson();
                    String s = toServer.toJson(xo);
                    ps.println(s);
                    textfield.setText("B3to");
                }
             });
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
