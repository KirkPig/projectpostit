package ui.base;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IVBox extends VBox {
	public IVBox(int width, int height) {
		Label header = new Label("IVBOX");
		
		TextField paymentText = new TextField();
		HBox paymentBox = new HBox();
		Label paymentLabel = new Label("payment:");
		paymentBox.getChildren().addAll(paymentLabel,paymentText);
		paymentBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		TextField poText = new TextField();
		HBox poBox = new HBox();
		Label poLabel = new Label("PO. number:");
		poBox.getChildren().addAll(poLabel,poText);
		poBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		TextField orderText = new TextField();
		HBox orderBox = new HBox();
		Label orderLabel = new Label("Ordered By:");
		orderBox.getChildren().addAll(orderLabel,orderText);
		orderBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		TextField saleText = new TextField();
		HBox saleBox = new HBox();
		Label saleLabel = new Label("Seller:");
		saleBox.getChildren().addAll(saleLabel,saleText);
		saleBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		TextField dateText = new TextField();
		HBox dateBox = new HBox();
		Label dateLabel = new Label("Date Due:");
		dateBox.getChildren().addAll(dateLabel,dateText);
		dateBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		this.getChildren().addAll(header,poBox,orderBox,dateBox,saleBox);
		this.setMinSize(width, height);// TODO Auto-generated constructor stub
	}
}
