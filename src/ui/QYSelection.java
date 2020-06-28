package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QYSelection extends VBox {
	public QYSelection() {
		HBox allFunc = new HBox();
		HBox simpleFunc = new HBox();
		HBox moreFunc = new HBox();
		HBox searchBox = new HBox();
		Button newButton = new Button("new");
		newButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new QuotationNewUI());
			//Stage newStage = new Stage();
			//VBox newBox = new VBox(new QuotationNewUI());
			//Scene newScene = new Scene(newBox);
			//newStage.setScene(newScene);
			//newStage.show();
		
		});		
		// Button
		simpleFunc.getChildren().addAll(newButton, new Button("open/edit"), new Button("delete"),
				new Button("bin"));
		moreFunc.getChildren().addAll(new Button("print report"), new Button("Name/Product"), new Button("month/year"));
		searchBox.getChildren().addAll(new TextField(), new Button("Genre"));
		
		allFunc.getChildren().addAll(simpleFunc,moreFunc,searchBox);
		allFunc.setSpacing(20);
		allFunc.setMaxHeight(610);
		allFunc.setMinWidth(1280);
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
		// TODO Auto-generated constructor stub
	}

}
