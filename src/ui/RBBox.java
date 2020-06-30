package ui;

import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistChronology;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RBBox extends VBox{
	public RBBox(int width,int height) {
		Label header = new Label("RBBOX");
		TextField attn = new TextField();
		HBox attnBox = new HBox();
		Label attnLabel = new Label("Billing By:");
		attnBox.getChildren().addAll(attnLabel,attn);
		attnBox.setSpacing(5);
		
		DatePicker date = new DatePicker();
		date.setChronology(ThaiBuddhistChronology.INSTANCE);
		date.setValue(LocalDate.now());
		Label crLabel = new Label("Billing Date:");
		HBox crBox = new HBox();
		crBox.getChildren().addAll(crLabel,date);
		crBox.setSpacing(19);
		
		TextField psText = new TextField();
		Label psLabel = new Label("PS:");
		HBox psBox = new HBox();
		psBox.getChildren().addAll(psLabel,psText);
		psBox.setSpacing(19);
		
		this.getChildren().addAll(header,attnBox,crBox,psBox);
		this.setMinSize(width, height);// TODO Auto-generated constructor stub
	}

}
