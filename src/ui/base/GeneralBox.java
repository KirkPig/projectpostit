package ui.base;


import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;

import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class GeneralBox extends VBox {
	DatePicker date;
	public GeneralBox(int width, int height) {
		Label header = new Label("General");
		HBox idBox = new HBox();
		Label idLabel = new Label("ID:");
		Label idGen = new Label();
		idBox.getChildren().addAll(idLabel,idGen);
		idBox.setAlignment(Pos.BOTTOM_LEFT);
		
		date = new DatePicker();
		date.setChronology(ThaiBuddhistChronology.INSTANCE);
		date.setValue(LocalDate.now());
		HBox dateBox = new HBox();
		Label dateLabel = new Label("DATE:");
		dateBox.getChildren().addAll(dateLabel,date);
		dateBox.setSpacing(3);
		dateBox.setAlignment(Pos.BOTTOM_LEFT);
		this.getChildren().addAll(header,idBox,dateBox);
		this.setMinSize(width, height);
	}
	
	public String getSelectedDate() {
		return date.getValue().toString();
	}
}
