package ui.news;




import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import logic.Product;
import ui.base.CustomerBox;
import ui.base.GeneralBox;
import ui.base.ProductAdd;
import ui.base.QYBox;
import ui.selection.QYSelection;

public class QYNewUI extends VBox{
	private TableView<Product> productTable;
	public QYNewUI() {
		
		this.setAlignment(Pos.CENTER);
		
		HBox buttonGang = new HBox();
		Button backButton = new Button("Back");
		Button saveButton = new Button("Save");
		buttonGang.getChildren().addAll(backButton,saveButton);
		
		backButton.setOnMouseClicked((MouseEvent e) -> {
			this.getChildren().clear();
			this.getChildren().add(new QYSelection());
			//Stage newStage = new Stage();
			//VBox newBox = new VBox(new QuotationNewUI());
			//Scene newScene = new Scene(newBox);
			//newStage.setScene(newScene);
			//newStage.show();
		
		});		
		
		HBox upper = new HBox();
		VBox left = new VBox();
		GeneralBox genBox = new GeneralBox(500,120);
		QYBox qy = new QYBox(500, 120);
		CustomerBox cusBox = new CustomerBox(500,250);
		left.getChildren().addAll(genBox,qy);
		left.setSpacing(4);
		upper.getChildren().addAll(left,cusBox);
		upper.setSpacing(5);
		upper.setAlignment(Pos.CENTER);
		productTable = new TableView();
		
		TableColumn numberCol = new TableColumn("No.");
		numberCol.setMinWidth(30);

		
		TableColumn descriptionCol = new TableColumn("Product Description");
		descriptionCol.setMinWidth(300);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		TableColumn quantityCol = new TableColumn("Quantity");
		quantityCol.setMinWidth(30);
	
		quantityCol.setEditable(true);
		
		TableColumn unitCol = new TableColumn("Unit");
		unitCol.setMinWidth(30);
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		TableColumn priceCol = new TableColumn("Price/Unit");
		priceCol.setMinWidth(70);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		TableColumn discountCol = new TableColumn("Discount");
		quantityCol.setMinWidth(50);
		quantityCol.setEditable(true);
		// TODO Auto-generated constructor stub
		TableColumn amountCol = new TableColumn("Amount");
		amountCol.setMinWidth(100);
		productTable.getColumns().addAll(numberCol,descriptionCol,quantityCol,unitCol,priceCol,discountCol,amountCol);
		ProductAdd productAdd = new ProductAdd(productTable);
		Button newBtn = new Button("new");
		newBtn.setMinSize(100, 50);
		Button editBtn = new Button("edit");
		editBtn.setMinSize(100, 50);
		Button deleteBtn = new Button("delete");
		deleteBtn.setMinSize(100, 50);
		productTable.setMinWidth(1160);
		HBox tableBox = new HBox();
		VBox button = new VBox();
		button.getChildren().addAll(newBtn, editBtn, deleteBtn);
		tableBox.getChildren().addAll(productTable, button);
		tableBox.setAlignment(Pos.CENTER);
		GridPane lower = new GridPane();
		Label valueBefore = new Label("Value Before:");
		Label valueBeforeText= new Label("457677");
		Label tax7 = new Label("Tax 7%:");
		Label tax7Text = new Label("4356789");
		Label plusTax = new Label("Value after tax:");
		Label plusTaxText = new Label("1234567890");
		
		lower.add(valueBefore, 0, 0);
		lower.add(valueBeforeText,1	, 0);
		lower.add(tax7, 2, 0);
		lower.add(tax7Text, 3, 0);
		lower.add(plusTax, 4, 0);
		lower.add(plusTaxText, 5, 0);
		lower.setAlignment(Pos.CENTER);
		lower.setHgap(20);
		lower.setVgap(10);
	
		this.getChildren().addAll(buttonGang,upper,tableBox,productAdd,lower);
		this.setSpacing(20);
	}
		
		
	
}
