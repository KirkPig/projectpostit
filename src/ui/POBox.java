package ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class POBox extends VBox{
	public POBox(int width, int height) {
		Label header = new Label("POBOX");
		TextField paymentText = new TextField();
		HBox paymentBox = new HBox();
		Label paymentLabel = new Label("payment:");
		paymentBox.getChildren().addAll(paymentLabel,paymentText);
		paymentBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		this.getChildren().addAll(header,paymentBox);
		this.setMinSize(width, height);
		// TODO Auto-generated constructor stub
	}

}
