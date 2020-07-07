package ui.base;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class POBox extends VBox{
	private TextField paymentText;
	public POBox(int width, int height) {
		Label header = new Label("POBOX");
		paymentText = new TextField();
		HBox paymentBox = new HBox();
		Label paymentLabel = new Label("payment:");
		paymentBox.getChildren().addAll(paymentLabel,paymentText);
		paymentBox.setSpacing(5);
		paymentBox.setAlignment(Pos.BOTTOM_LEFT);
		
		this.getChildren().addAll(header,paymentBox);
		this.setMinSize(width, height);
	}
	public String getPaymentText() {
		return paymentText.getText();
	}
	
	public void setPaymentText(String paymentText) {
		this.paymentText.setText(paymentText);
	}
}
