package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class POSelection extends VBox {
	public POSelection() {
		HBox allFunc = new HBox();
		HBox simpleFunc = new HBox();
		HBox moreFunc = new HBox();
		HBox searchBox = new HBox();
		Button newButton = new Button("new");
		Button switchButton = new Button("Customer");
		ComboBox month = new ComboBox();
		month.setEditable(true);
		month.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December");
		month.setPromptText("Month");
		
		TextField year = new TextField();
		year.setPromptText("Year(BE)");
		newButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new ProductOrderNewUI());
			//Stage newStage = new Stage();
			//VBox newBox = new VBox(new QuotationNewUI());
			//Scene newScene = new Scene(newBox);
			//newStage.setScene(newScene);
			//newStage.show();
		
		});		
		
		
		// Button
		ComboBox genre = new ComboBox();
		genre.getItems().addAll("Code","Product","Customer Name","Creator","Amount");
		
		simpleFunc.getChildren().addAll(newButton, new Button("open/edit"), new Button("delete"), new Button("bin"));
		simpleFunc.setSpacing(3);
		moreFunc.getChildren().addAll(new Button("print report"), switchButton, month,year);
		moreFunc.setSpacing(3);
		searchBox.getChildren().addAll(new TextField(), genre);

		allFunc.getChildren().addAll(simpleFunc, moreFunc, searchBox);
		allFunc.setSpacing(20);

		this.getChildren().add(allFunc);

		
	// =============================================================================
		TableView table = new TableView();
		TableColumn code = new TableColumn("Code.");
		code.setMinWidth(60);
		TableColumn date = new TableColumn("date");
		date.setMinWidth(100);
		TableColumn customerName = new TableColumn("Customer Name.");
		customerName.setMinWidth(200);
		TableColumn totalAmount = new TableColumn("Total Amount");
		totalAmount.setMinWidth(120);
		TableColumn creator = new TableColumn("Created by");
		creator.setMinWidth(200);
		table.getColumns().addAll(code,date,customerName,totalAmount,creator);
		table.setMaxHeight(500);
		this.getChildren().add(table);
		this.setSpacing(5);
		
		//this.setPadding(new Insets(5));
		
		TableView table2 = new TableView();
		TableColumn descriptionCol = new TableColumn("Description");
		customerName.setMinWidth(200);
		
		
		table2.getColumns().addAll(code,date,descriptionCol,creator);
		
		switchButton.setOnMouseClicked((MouseEvent e) -> {
			if (this.getChildren().contains(table)) {
				this.getChildren().remove(table);
				this.getChildren().add(table2);
				switchButton.setText("Product");
				
			} else {
				this.getChildren().remove(table2);
				this.getChildren().add(table);
				switchButton.setText("Customer");
			}
		});		
	}

}
