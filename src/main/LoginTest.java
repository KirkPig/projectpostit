package main;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.selection.Login;

public class LoginTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
		Login login = new Login();
		Scene testScene = new Scene(login);
		arg0.setTitle("yonotool");
		Image logo = new Image ("/res/yono_logo.png");
		arg0.getIcons().add(logo);
		arg0.setScene(testScene);
		arg0.setResizable(false);
		arg0.show();
	}
}

