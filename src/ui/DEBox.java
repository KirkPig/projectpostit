package ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DEBox extends VBox{
public DEBox(int width, int height) {
	Label header = new Label("DEBOX");
	TextField contactText = new TextField();
	HBox contactBox = new HBox();
	Label contactLabel = new Label("contact:");
	contactBox.getChildren().addAll(contactLabel,contactText);
	contactBox.setSpacing(5);// TODO Auto-generated constructor stub
	
	this.getChildren().addAll(header,contactBox);
	this.setMinSize(width, height);
}

}
