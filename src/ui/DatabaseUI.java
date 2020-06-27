package ui;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DatabaseUI extends VBox {
	
	public DatabaseUI() {
		//Setting
		this.setSpacing(10);
		
		//Tab Select
		HBox tab = new HBox();
		tab.setMinHeight(30);
		
		Button btnProduct = new Button();
		btnProduct.setMinWidth(100);
		Button btnCustomer = new Button();
		btnCustomer.setMinWidth(100);
		
		tab.getChildren().add(btnProduct);
		tab.getChildren().add(btnCustomer);
		
		//Product Database
		HBox productPane = new HBox();
		tab.setMinHeight(570);
		
		TableView table = new TableView();
		table.setMinWidth(800);
		
		productPane.getChildren().add(table);
		
		
		//Added Children
		this.getChildren().add(tab);
		this.getChildren().add(productPane);
	}

}
