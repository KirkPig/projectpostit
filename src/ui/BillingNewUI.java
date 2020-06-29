package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BillingNewUI extends VBox{
	public BillingNewUI() {
		this.setAlignment(Pos.CENTER);
		
		HBox buttonGang = new HBox();
		Button backButton = new Button("Back");
		Button saveButton = new Button("Save");
		buttonGang.getChildren().addAll(backButton,saveButton);
		
		backButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new RBSelection());
			//Stage newStage = new Stage();
			//VBox newBox = new VBox(new QuotationNewUI());
			//Scene newScene = new Scene(newBox);
			//newStage.setScene(newScene);
			//newStage.show();
		
		});		
		
		HBox upper = new HBox();
		VBox left = new VBox();
		GeneralBox genBox = new GeneralBox(500,120);
		RBBox qy = new RBBox(500, 120);
		CustomerBox cusBox = new CustomerBox(500,250);
		left.getChildren().addAll(genBox,qy);
		left.setSpacing(4);
		upper.getChildren().addAll(left,cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);
		TableView table = new TableView();
		
		TableColumn numberCol = new TableColumn("No.");
		numberCol.setMinWidth(30);
		
		TableColumn descriptionCol = new TableColumn("Invoice ID");
		
		descriptionCol.setMinWidth(300);
		
		TableColumn quantityCol = new TableColumn("Date");
		quantityCol.setMinWidth(30);
		
		TableColumn unitCol = new TableColumn("Date due");
		unitCol.setMinWidth(30);
		
		TableColumn priceCol = new TableColumn("PS.");
		priceCol.setMinWidth(70);
		
		
		// TODO Auto-generated constructor stub
		TableColumn amountCol = new TableColumn("Amount");
		amountCol.setMinWidth(100);
		
		table.getColumns().addAll(numberCol,descriptionCol,quantityCol,unitCol,priceCol,amountCol);
		
		GridPane lower = new GridPane();
		Label valueBefore = new Label("Total Value:");
		
		lower.add(valueBefore, 0,2);
		
		this.getChildren().addAll(buttonGang,upper,table,lower);
	}
		
		
	
}