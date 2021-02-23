package ui.selection;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bill.Item;
import bill.Product;
import database.User;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import logic.DatabaseConnection;
import database.User;
public class Home extends VBox{
	private Button btAdd = new Button("add");
	private Button btdelete = new Button();
	private TextField t1 = new TextField(); 
	private TextField t2 = new TextField();
	private TextField t3 = new TextField();
	private TextField t4 = new TextField();
	private TextField t5 = new TextField();
	private TextField t6 = new TextField();
	
	
	public Home() {
		TableView<Product> product = new TableView<Product>();
		TableColumn<Product,String> codeCol = new TableColumn<Product,String>("code");
		codeCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		codeCol.setMinWidth(200);
			
		TableColumn<Product,String> dateCol = new TableColumn<Product,String>("date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateCol.setMinWidth(200);
		
		TableColumn<Product,String> userCol = new TableColumn<Product,String>("user");
		userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		userCol.setMinWidth(200);
		
		TableColumn<Product,String> product_initialcodeCol = new TableColumn<Product,String>("product_initial");
		product_initialcodeCol.setCellValueFactory(new PropertyValueFactory<>("product_initial"));
		product_initialcodeCol.setMinWidth(200);
		
		TableColumn<Product,String> product_changeCol = new TableColumn<Product,String>("product_change");
		product_changeCol.setCellValueFactory(new PropertyValueFactory<>("product_change"));
		product_changeCol.setMinWidth(200);
		
		TableColumn<Product,String> product_totalcodeCol = new TableColumn<Product,String>("product_total");
		product_totalcodeCol.setCellValueFactory(new PropertyValueFactory<>("product_totalcode"));
		product_totalcodeCol.setMinWidth(200);
		
	
		 product.getColumns().addAll(codeCol, dateCol, userCol,product_initialcodeCol,product_changeCol,product_totalcodeCol);
		getChildren().add(product);
		HBox tf = new HBox();
		
		tf.getChildren().addAll(t1,t2,t3,t4,t5,t6,btAdd); 
		tf.setSpacing(13);
		getChildren().add(tf);
		
		
		btAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				add();
			}
		});
		
		
		product.setRowFactory(tv -> {
			TableRow<Product> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

					Product clickedRow = row.getItem();
					
					 = clickedRow.getUsername();
					
					usernameField2.setText(clickedRow.getUsername());
					passwordField2.setText(clickedRow.getPassword());
					nameField2.setText(clickedRow.getName());

					accountChangeBox.setVisible(true);
					editPermissionBox.setVisible(true);
					updateCheckBox(usernamebf);

				}
			});
			return row;
		});
	
	}
	
	
	
	
	
	private void add() {
		try {
			Connection conn = DatabaseConnection.getConnection();
					
			String sql = "insert into productchange values('" + t1.getText() + "','" + t2.getText()  + "','" + t3.getText() + "'," + t4.getText() + "," + t5.getText() + "," + t6.getText()+");";

			PreparedStatement stmt = conn.prepareStatement(sql);
			int x = stmt.executeUpdate();

		} catch (Exception e1) {
			System.out.println(e1);
		}
		;

		

	}
	private void edit() {
//		.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
//		discountCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, Double>>() {
//			@Override
//			public void handle(CellEditEvent<Item, Double> t) {
//				
//				Thread th = new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						(t.getTableView().getItems().get(t.getTablePosition().getRow())).setDiscount(t.getNewValue());
//						(t.getTableView().getItems().get(t.getTablePosition().getRow())).setAmount();
//						productTable.refresh();
//						calculateTax();
//					}
//				});
//				th.start();
//			}
//		});

	}
	private void delete() {
		try {
			Connection conn = DatabaseConnection.getConnection();
					
			String sql = "insert into productchange values('" + t1.getText() + "','" + t2.getText()  + "','" + t3.getText() + "'," + t4.getText() + "," + t5.getText() + "," + t6.getText()+");";

			PreparedStatement stmt = conn.prepareStatement(sql);
			int x = stmt.executeUpdate();

		} catch (Exception e1) {
			System.out.println(e1);
		}
		;

		

	}
	
	
	

}
