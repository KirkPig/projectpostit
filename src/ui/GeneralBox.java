package ui;


import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;


import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class GeneralBox extends VBox {
	public GeneralBox(int width, int height) {
		Label header = new Label("General");
		HBox idBox = new HBox();
		Label idLabel = new Label("ID:");
		Label idGen = new Label();
		idBox.getChildren().addAll(idLabel,idGen);
		
		TextField date = new TextField();
		HBox dateBox = new HBox();
		Label dateLabel = new Label("DATE:");
		dateBox.getChildren().addAll(dateLabel,date);
		dateBox.setSpacing(3);
		this.getChildren().addAll(header,idBox,dateBox);
		this.setMinSize(width, height);
	}

}
