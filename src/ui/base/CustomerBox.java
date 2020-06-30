package ui.base;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import logic.Customer;


public class CustomerBox extends VBox{
	Customer selectedCustomer;
	
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
		code.setAlignment(Pos.BOTTOM_LEFT);
		
		Label nameLabel = new Label("NAME:");
		HBox name = new HBox();
		name.getChildren().addAll(nameLabel,nameBox);
		name.setSpacing(10);
		name.setAlignment(Pos.BOTTOM_LEFT);
		
		Label taxLabel = new Label("TAX ID:");
		HBox tax = new HBox();
		tax.getChildren().addAll(taxLabel,taxIdBox);
		tax.setSpacing(10);
		tax.setAlignment(Pos.BOTTOM_LEFT);
		
		Label addressLabel = new Label("ADDRESS:");
		HBox address = new HBox();
		address.getChildren().addAll(addressLabel,addressBox);
		address.setSpacing(10);
		address.setAlignment(Pos.BOTTOM_LEFT);
		
		Label teleLabel = new Label("TELE:");
		HBox tele = new HBox();
		tele.getChildren().addAll(teleLabel,teleBox);
		tele.setSpacing(10);
		tele.setAlignment(Pos.BOTTOM_LEFT);
		
		Label faxLabel = new Label("FAX:");
		HBox fax = new HBox();
		fax.getChildren().addAll(faxLabel,faxBox);
		fax.setSpacing(10);
		fax.setAlignment(Pos.BOTTOM_LEFT);
		
		Label mailLabel = new Label("EMAIL:");
		HBox mail = new HBox();
		mail.getChildren().addAll(mailLabel,mailBox);
		mail.setSpacing(10);
		mail.setAlignment(Pos.BOTTOM_LEFT);
		
		this.getChildren().addAll(header,code,name,tax,address,tele,fax,mail);
		this.setMinHeight(height);
		this.setMaxWidth(width);
	}
	
	public Customer getSelelectedCustomer() {
		return selectedCustomer;
	}
}
