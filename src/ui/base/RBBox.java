package ui.base;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RBBox extends VBox{
	private TextField billingByBox;
	private DatePicker date;
	private TextField psText;
	public RBBox(int width,int height) {
		Label header = new Label("RBBOX");
		billingByBox = new TextField();
		HBox attnBox = new HBox();
		Label attnLabel = new Label("Billing By:");
		attnBox.getChildren().addAll(attnLabel,billingByBox);
		attnBox.setSpacing(5);
		
		date = new DatePicker();
		date.setChronology(ThaiBuddhistChronology.INSTANCE);
		date.setValue(LocalDate.now());
		Label crLabel = new Label("Billing Date:");
		HBox crBox = new HBox();
		crBox.getChildren().addAll(crLabel,date);
		crBox.setSpacing(19);
		
		psText = new TextField();
		Label psLabel = new Label("PS:");
		HBox psBox = new HBox();
		psBox.getChildren().addAll(psLabel,psText);
		psBox.setSpacing(19);
		
		this.getChildren().addAll(header,attnBox,crBox,psBox);
		this.setMinSize(width, height);// TODO Auto-generated constructor stub
	}

	public String getSelectedDate() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // 08-02-1111
		return dateFormatter.format(date.getChronology().date(date.getValue()));
	}

	public String getBillingByBox() {
		return billingByBox.getText();
	}
	
	public void setBillingByBox(String billingByBox) {
		this.billingByBox.setText(billingByBox);
	}
	
	public String getPsText() {
		return psText.getText();
	}
	
	public void setPsText(String psText) {
		this.psText.setText(psText);
	}
	
	public void setDate(String selectedDate) {
		date.setValue(LocalDate.of(Integer.parseInt(selectedDate.substring(6)) - 543,
				Integer.parseInt(selectedDate.substring(3, 5)), Integer.parseInt(selectedDate.substring(0, 2))));
	}
	
	
}
