package ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BLBox extends VBox{
	public BLBox(int width,int height) {
		Label header = new Label("BLBOX");
		TextField contactText = new TextField();
		HBox contactBox = new HBox();
		Label contactLabel = new Label("CONTACT:");
		contactBox.getChildren().addAll(contactLabel,contactText);
		contactBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		this.getChildren().addAll(header,contactBox);
		this.setMinSize(width, height);
		
		
	}

}
