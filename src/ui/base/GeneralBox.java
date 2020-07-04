package ui.base;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Formatter;

import javax.swing.text.DateFormatter;

import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

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
		date.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			@Override
			public String toString(LocalDate arg0) {
				// TODO Auto-generated method stub
				return dateFormatter.format(arg0);
			}
			
			@Override
			public LocalDate fromString(String arg0) {
				
				// TODO Auto-generated method stub
				return LocalDate.parse(arg0,dateFormatter);
			}
		});
	}
	
	public String getSelectedDate() {
		
		return date.getValue().toString();
	}
}