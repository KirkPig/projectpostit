package ui.base;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Formatter;
import java.util.Locale;

import javax.swing.text.DateFormatter;

import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import logic.ThaiBaht;

public class GeneralBox extends VBox {
	DatePicker date;

	public GeneralBox(int width, int height) {
		Label header = new Label("General");
		HBox idBox = new HBox();
		Label idLabel = new Label("ID:");
		Label idGen = new Label();
		idBox.getChildren().addAll(idLabel, idGen);
		idBox.setAlignment(Pos.BOTTOM_LEFT);

		date = new DatePicker();
		date.setValue(LocalDate.now());
		date.setChronology(ThaiBuddhistChronology.INSTANCE);
		HBox dateBox = new HBox();
		Label dateLabel = new Label("DATE:");
		dateBox.getChildren().addAll(dateLabel, date);
		dateBox.setSpacing(3);
		dateBox.setAlignment(Pos.BOTTOM_LEFT);
		this.getChildren().addAll(header, idBox, dateBox);
		this.setMinSize(width, height);
	}

	public String getSelectedDate() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return dateFormatter.format(date.getChronology().date(date.getValue()));
	}
}