package ui.base;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QYBox extends VBox{
	private TextField attn;
	private TextField cr;
	public QYBox(int width,int height) {
		Label header = new Label("QYBOX");
		attn = new TextField();
		HBox attnBox = new HBox();
		Label attnLabel = new Label("ATTN:");
		attnBox.getChildren().addAll(attnLabel,attn);
		attnBox.setSpacing(5);
		attnBox.setAlignment(Pos.BOTTOM_LEFT);
		
		cr = new TextField();
		Label crLabel = new Label("CR:");
		HBox crBox = new HBox();
		crBox.getChildren().addAll(crLabel,cr);
		crBox.setSpacing(19);
		crBox.setAlignment(Pos.BOTTOM_LEFT);
		this.getChildren().addAll(header,attnBox,crBox);
		this.setMinSize(width, height);
	}
	
	public String getAttn() {
		return attn.getText();
	}
	
	public String getCr() {
		return cr.getText();	
	}
	
	public void setCr(String cr) {
		this.cr.setText(cr);
	}
	
	public void setAttn(String attn) {
		this.attn.setText(attn);
	}
}
