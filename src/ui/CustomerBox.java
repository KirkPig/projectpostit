package ui;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;


public class CustomerBox extends VBox{
	
	
	
	public CustomerBox(int width,int height) {
		Label header = new Label("CUSTOMER");
		TextField codeBox = new TextField();
		TextField nameBox = new TextField();
		Label taxIdBox = new Label("213298373294");
		Label addressBox = new Label("afigdfYISGFSfysgfsyuf");
		Label teleBox = new Label("019389832480234");
		Label faxBox = new Label("ASDXZCZXCXZC");
		Label mailBox = new Label("sadiywadyta");
		
		
		Label codeLabel = new Label("CODE:");
		HBox code = new HBox();
		code.getChildren().addAll(codeLabel,codeBox);
		code.setSpacing(13);
		
		Label nameLabel = new Label("NAME:");
		HBox name = new HBox();
		name.getChildren().addAll(nameLabel,nameBox);
		name.setSpacing(10);
		
		Label taxLabel = new Label("TAX ID:");
		HBox tax = new HBox();
		tax.getChildren().addAll(taxLabel,taxIdBox);
		tax.setSpacing(10);
		
		Label addressLabel = new Label("ADDRESS:");
		HBox address = new HBox();
		address.getChildren().addAll(addressLabel,addressBox);
		address.setSpacing(10);
		
		Label teleLabel = new Label("TELE:");
		HBox tele = new HBox();
		tele.getChildren().addAll(teleLabel,teleBox);
		tele.setSpacing(10);
		
		Label faxLabel = new Label("FAX:");
		HBox fax = new HBox();
		fax.getChildren().addAll(faxLabel,faxBox);
		fax.setSpacing(10);
		
		Label mailLabel = new Label("EMAIL:");
		HBox mail = new HBox();
		mail.getChildren().addAll(mailLabel,mailBox);
		mail.setSpacing(10);
		
		this.getChildren().addAll(header,code,name,tax,address,tele,fax,mail);
		this.setMinHeight(height);
		this.setMaxWidth(width);
	}
}
