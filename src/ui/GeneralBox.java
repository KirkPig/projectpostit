package ui;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class GeneralBox extends VBox {
	public GeneralBox(int width, int height) {
		Label header = new Label("General");
		TextField date = new TextField();
		this.getChildren().addAll(header,date);
		this.setMinSize(width, height);

		// TODO Auto-generated constructor stub
	}

}
