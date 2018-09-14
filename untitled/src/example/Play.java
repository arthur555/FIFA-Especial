package example;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;

public class Play extends Application{
    public void start(Stage primaryStage) throws Exception{
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(9,8,14,18));

        Text title = new Text("Welcome you handsome boy");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD,20));
        root.add(title, 0, 0);

        Label userName = new Label("User Name: ");
        root.add(userName, 0, 1);

        TextField userTextField = new TextField();
        root.add(userTextField, 1,1);

        Label passWord = new Label("Password: ");
        root.add(passWord,0,2);

        PasswordField pwBox = new PasswordField();
        root.add(pwBox,1,2);


        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        root.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        root.add(actiontarget,1,6);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in Button Pressed");
            }
        });
        Scene scene = new Scene(root, 700,700);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main (String[] args){
        Application.launch(args);
    }
}
