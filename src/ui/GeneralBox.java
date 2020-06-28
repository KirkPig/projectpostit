package ui;


import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;


import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class GeneralBox extends VBox {
	public GeneralBox(int width, int height) {
		Label header = new Label("General");
		DatePicker datePicker = new DatePicker();
		datePicker.setChronology(ThaiBuddhistChronology.INSTANCE);
		datePicker.setValue(LocalDate.now());
		this.getChildren().addAll(header,datePicker);
		this.setMinSize(width, height);
	}

}
