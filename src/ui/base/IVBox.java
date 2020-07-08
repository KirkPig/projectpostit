package ui.base;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IVBox extends VBox {
	private TextField paymentText;
	private TextField poText;
	private TextField orderText;
	private TextField saleText;
	private DatePicker date;
	public IVBox(int width, int height) {
		Label header = new Label("IVBOX");
		
		paymentText = new TextField();
		HBox paymentBox = new HBox();
		Label paymentLabel = new Label("payment:");
		paymentBox.getChildren().addAll(paymentLabel,paymentText);
		paymentBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		poText = new TextField();
		HBox poBox = new HBox();
		Label poLabel = new Label("PO. number:");
		poBox.getChildren().addAll(poLabel,poText);
		poBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		orderText = new TextField();
		HBox orderBox = new HBox();
		Label orderLabel = new Label("Ordered By:");
		orderBox.getChildren().addAll(orderLabel,orderText);
		orderBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		saleText = new TextField();
		HBox saleBox = new HBox();
		Label saleLabel = new Label("Seller:");
		saleBox.getChildren().addAll(saleLabel,saleText);
		saleBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		date = new DatePicker();
		date.setValue(LocalDate.now());
		date.setChronology(ThaiBuddhistChronology.INSTANCE);
		HBox dateBox = new HBox();
		Label dateLabel = new Label("Date Due:");
		dateBox.getChildren().addAll(dateLabel,date);
		dateBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		this.getChildren().addAll(header,poBox,paymentBox,orderBox,dateBox,saleBox);
		this.setMinSize(width, height);// TODO Auto-generated constructor stub
	}
	public String getPoText() {
		return poText.getText();
	}
	public void setPoText(String poText) {
		this.poText.setText(poText);
	}
	public String getOrderText() {
		return orderText.getText();
	}
	public void setOrderText(String orderText) {
		this.orderText.setText(orderText);
	}
	
	public String getSelectedDate() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // 08-02-1111
		return dateFormatter.format(date.getChronology().date(date.getValue()));
	}
	
	public void setDate(String selectedDate) {
		date.setValue(LocalDate.of(Integer.parseInt(selectedDate.substring(6)) - 543,
				Integer.parseInt(selectedDate.substring(3, 5)), Integer.parseInt(selectedDate.substring(0, 2))));
		
	}
	
	public String getPaymentText() {
		return paymentText.getText();
	}
	
	public void setPaymentText(String paymentText) {
		this.paymentText.setText(paymentText); 
	}
	
	public String getSaleText() {
		return saleText.getText();
	}
	
	public void setSaleText(String saleText) {
		this.saleText.setText(saleText);
	}
}
