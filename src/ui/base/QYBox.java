package ui.base;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QYBox extends VBox{
	public QYBox(int width,int height) {
		Label header = new Label("QYBOX");
		TextField attn = new TextField();
		HBox attnBox = new HBox();
		Label attnLabel = new Label("ATTN:");
		attnBox.getChildren().addAll(attnLabel,attn);
		attnBox.setSpacing(5);
		attnBox.setAlignment(Pos.BOTTOM_LEFT);
		
		TextField cr = new TextField();
		Label crLabel = new Label("CR:");
		HBox crBox = new HBox();
		crBox.getChildren().addAll(crLabel,cr);
		crBox.setSpacing(19);
		crBox.setAlignment(Pos.BOTTOM_LEFT);
		this.getChildren().addAll(header,attnBox,crBox);
		this.setMinSize(width, height);
	}
}
