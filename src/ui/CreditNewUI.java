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

public class CreditNewUI extends VBox{
	public CreditNewUI() {
		this.setAlignment(Pos.CENTER);
		
		HBox buttonGang = new HBox();
		Button backButton = new Button("Back");
		Button saveButton = new Button("Save");
		buttonGang.getChildren().addAll(backButton,saveButton);
		
		backButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new CRSelection());
			//Stage newStage = new Stage();
			//VBox newBox = new VBox(new QuotationNewUI());
			//Scene newScene = new Scene(newBox);
			//newStage.setScene(newScene);
			//newStage.show();
		
		});		
		
		HBox upper = new HBox();
		VBox left = new VBox();
		GeneralBox genBox = new GeneralBox(500,120);
		CRBox qy = new CRBox(500, 120);
		CustomerBox cusBox = new CustomerBox(500,250);
		left.getChildren().addAll(genBox,qy);
		left.setSpacing(4);
		upper.getChildren().addAll(left,cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);
		TableView table = new TableView();
		
		TableColumn numberCol = new TableColumn("No.");
		numberCol.setMinWidth(30);
		
		TableColumn descriptionCol = new TableColumn("Product Description");
		
		descriptionCol.setMinWidth(300);
		
		
		TableColumn amountCol = new TableColumn("Amount");
		amountCol.setMinWidth(100);
		
		table.getColumns().addAll(numberCol,descriptionCol,amountCol);
		
		GridPane lower = new GridPane();
		Label valueOld= new Label("Value Old:");
		Label valueReal = new Label("Value Real:");
		Label valueBefore = new Label("Value Before");
		Label tax7 = new Label("tax7");
		Label plusTax = new Label("Value After");
		lower.add(valueOld, 0,0);
		lower.add(valueReal, 1,0);
		lower.add(valueBefore, 2,0);
		lower.add(tax7, 0, 1);
		lower.add(plusTax, 1, 1);
		this.getChildren().addAll(buttonGang,upper,table,lower);
	}
		
		
	
}