package ui.selection;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ui.news.QYNewUI;

public class IVSelection extends VBox{
	private int monthSelecting = Integer.parseInt(LocalDate.now().toString().substring(5, 7));
	private int yearSelecting = Integer.parseInt(LocalDate.now().toString().substring(0, 4));
	
	public IVSelection() {
		HBox allFunc = new HBox();
		HBox simpleFunc = new HBox();
		HBox moreFunc = new HBox();
		HBox searchBox = new HBox();
		Button newButton = new Button("new");
		Button switchButton = new Button("Customer");
		ComboBox<Integer> month = new ComboBox<Integer>();
		month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		month.getSelectionModel().select(Integer.parseInt(LocalDate.now().toString().substring(5, 7)) - 1);
		month.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				monthSelecting = month.getValue();
			}
		});

		ComboBox<Integer> year = new ComboBox<Integer>();
		year.getItems().addAll(2563, 2564, 2565, 2566, 2567, 2568, 2569, 2570, 2571, 2572);
		year.setValue(Integer.parseInt(LocalDate.now().toString().substring(0, 4)) + 543);
		year.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				yearSelecting = year.getValue();
			}
		});

		newButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new QYNewUI());
		});

		// Button
		ComboBox<String> genre = new ComboBox<String>();
		genre.getItems().addAll("Code", "Product", "Customer Name", "Creator", "Amount");

		simpleFunc.getChildren().addAll(newButton, new Button("open/edit"), new Button("delete"), new Button("bin"));
		simpleFunc.setSpacing(3);
		moreFunc.getChildren().addAll(new Button("print report"), switchButton, month, year);
		moreFunc.setSpacing(3);
		searchBox.getChildren().addAll(new TextField(), genre);

		allFunc.getChildren().addAll(simpleFunc, moreFunc, searchBox);
		allFunc.setSpacing(250);

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
