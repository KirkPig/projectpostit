package ui.admin;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.selection.Login;

public class AdminUI extends VBox {
	
	private Stage stage;
	private AdminAccountPane adminAccountPane = new AdminAccountPane();
	
	public AdminUI(Stage stage, double width, double height) {
		
		this.setStage(stage);
		setWidth(width);
		setHeight(height);
		
		stage.setTitle("ProjectPostIt: Admin Panel");
		
		HBox tab = new HBox();
		tab.setMinHeight(50);
		Button btnAccount = new Button("Back");
		btnAccount.setPrefSize(200, 50);
		tab.getChildren().add(btnAccount);
		
		btnAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				stage.close();
//				Stage arg0 = new Stage();
//				
//				Login login = new Login(arg0);
//				Scene testScene = new Scene(login);
//				arg0.setTitle("yonotool");
//				Image logo = new Image ("/res/yono_logo.png");
//				arg0.getIcons().add(logo);
//				arg0.setScene(testScene);
//				arg0.setResizable(false);
//				arg0.show();
			}

		});
		
		getChildren().add(tab);
		getChildren().add(adminAccountPane);
		
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
