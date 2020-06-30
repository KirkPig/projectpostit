package ui.base;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CRBox extends VBox{
	public CRBox(int width,int height) {
		Label header = new Label("CRBOX");
		TextField contactText = new TextField();
		HBox contactBox = new HBox();
		Label contactLabel = new Label("ID:");
		contactBox.getChildren().addAll(contactLabel,contactText);
		contactBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		DatePicker date = new DatePicker();
		date.setChronology(ThaiBuddhistChronology.INSTANCE);
		date.setValue(LocalDate.now());
		HBox dateBox = new HBox();
		Label dateLabel = new Label("DATE:");
		dateBox.getChildren().addAll(dateLabel,date);
		dateBox.setSpacing(5);// TODO Auto-generated constructor stub
		
		this.getChildren().addAll(header,contactBox,dateBox);
		this.setMinSize(width, height);
		// TODO Auto-generated constructor stub
	}

}
