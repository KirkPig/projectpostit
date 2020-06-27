package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Login;

public class LoginTESTER extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
		Login login = new Login();
		Scene testScene = new Scene(login);
		arg0.setScene(testScene);
		arg0.setResizable(false);
		arg0.show();
	}
}

