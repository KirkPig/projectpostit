package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.*;

public class HeaderTest extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Scene testScene = new Scene(new BLSelection(),1280,720);
		arg0.setScene(testScene);
		arg0.setResizable(false);
		arg0.show();
	}
}
