package ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class QYBox extends VBox{
	public QYBox(int width,int height) {
		Label header = new Label("QYBOX");
		TextField attn = new TextField();
		TextField cr = new TextField();
		this.getChildren().addAll(header,attn,cr);
		this.setMinSize(width, height);
		// TODO Auto-generated constructor stub
	}
}
