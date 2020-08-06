package ui.base;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GeneralBox extends VBox {
	DatePicker date;

	public GeneralBox(int width, int height) {
		Label header = new Label("General");

		date = new DatePicker();
		date.setValue(LocalDate.now());
		date.setChronology(ThaiBuddhistChronology.INSTANCE);
		HBox dateBox = new HBox();
		Label dateLabel = new Label("DATE:");
		dateBox.getChildren().addAll(dateLabel, date);
		dateBox.setSpacing(3);
		dateBox.setAlignment(Pos.BOTTOM_LEFT);
		this.getChildren().addAll(header, dateBox);
		this.setMinSize(width, height);
	}

	public String getSelectedDate() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // 08-02-1111
		return dateFormatter.format(date.getChronology().date(date.getValue()));
	}

	public void setGenBox(String id,String selectedDate) {
		this.getChildren().add(1, new Label("ID:"+id));
		date.setValue(LocalDate.of(Integer.parseInt(selectedDate.substring(6)) - 543,
				Integer.parseInt(selectedDate.substring(3, 5)), Integer.parseInt(selectedDate.substring(0, 2))));
	}
}