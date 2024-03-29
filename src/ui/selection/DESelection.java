package ui.selection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bill.Delivery;
import bill.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Customer;
import logic.DatabaseConnection;
import ui.news.DENewUI;

public class DESelection extends VBox {
	private static TableView<Delivery> table;
	private static TableView<Delivery> table2;
	private static ComboBox<Integer> month;
	private static ComboBox<Integer> year;
	private static ComboBox<String> genre;
	private static TextField search;
	private SortedSet<String> allTree;

	@SuppressWarnings("unchecked")
	public DESelection() {

		HBox allFunc = new HBox();
		HBox simpleFunc = new HBox();
		HBox moreFunc = new HBox();
		HBox searchBox = new HBox();
		Button switchButton = new Button("Customer");

		month = new ComboBox<Integer>();
		month.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		month.getSelectionModel().select(Integer.parseInt(LocalDate.now().toString().substring(5, 7)) - 1);
		month.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
						updateDE("");
					

			}
		});

		year = new ComboBox<Integer>();
		year.getItems().addAll(2563, 2564, 2565, 2566, 2567, 2568, 2569, 2570, 2571, 2572);
		year.setValue(Integer.parseInt(LocalDate.now().toString().substring(0, 4)) + 543);
		year.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
						updateDE("");
				
			}
		});

		Button newButton = new Button("new");
		newButton.setOnMouseClicked((MouseEvent e) -> {
			Stage newStage = new Stage();
			Scene denewScene = new Scene(new DENewUI(newStage));
			newStage.setScene(denewScene);
			newStage.show();
		});

		Button editButton = new Button("open/edit");
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				
						if (switchButton.getText().equals("Customer")) {
							openDE(table.getItems().get(table.getFocusModel().getFocusedCell().getRow()));
						} else {
							openDE(table2.getItems().get(table2.getFocusModel().getFocusedCell().getRow()));
						}
				
			}
		});
		Button deleteBtn = new Button("delete");
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				
						if (switchButton.getText().equals("Customer")) {
							delete(table.getItems().get(table.getFocusModel().getFocusedCell().getRow()));
						} else {
							delete(table2.getItems().get(table2.getFocusModel().getFocusedCell().getRow()));
						}
				
			}
		});
		// Button
		search = new TextField();
		search.setPromptText("Search");
		search.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode().equals(KeyCode.ENTER)) {
					
							updateDE(search.getText());
					

				}
			}
		});

		SortedSet<String> customerTree = getCustomerTree();
		SortedSet<String> codeTree = getCodeTree();
		SortedSet<String> amountTree = getAmountTree();
		SortedSet<String> creatorTree = getCreatorTree();
		SortedSet<String> productTree = getProductTree();

		ContextMenu allSuggest = new ContextMenu();
		search.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if ((search.getText().length() == 0) || (!search.isFocused()) || (genre.getValue() == null)) {
					allSuggest.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					switch (genre.getValue()) {
					case "Code":
						allTree = codeTree;
						break;

					case "Amount":
						allTree = amountTree;
						break;

					case "Creator":
						allTree = creatorTree;
						break;

					case "Customer Name":
						allTree = customerTree;
						break;

					case "Product":
						allTree = productTree;
						break;
					}

					searchResult.addAll(allTree.subSet(search.getText(), search.getText() + Character.MAX_VALUE));
					if (allTree.size() > 0) {
						populatePopup(searchResult);
						if (!allSuggest.isShowing()) {
							allSuggest.show(search, Side.BOTTOM, 0, 0);
						}
					} else {
						allSuggest.hide();
					}
				}

			}

			private void populatePopup(LinkedList<String> searchResult) {
				List<CustomMenuItem> menuItems = new LinkedList<>();

				int maxEntries = 10;
				int count = Math.min(searchResult.size(), maxEntries);

				for (int i = 0; i < count; i++) {
					final String result = searchResult.get(i);
					Label entryLabel = new Label(result);
					CustomMenuItem item = new CustomMenuItem(entryLabel, true);
					item.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent actionEvent) {

							
									System.out.println(result);
									updateDE(result);
									allSuggest.hide();
						
						}
					});
					menuItems.add(item);
				}
				allSuggest.getItems().clear();
				allSuggest.getItems().addAll(menuItems);
			}
		});

		search.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

				allSuggest.hide();
			}
		});
		genre = new ComboBox<String>();
		genre.getItems().addAll("Code", "Product", "Customer Name", "Creator", "Amount");

		simpleFunc.getChildren().addAll(newButton, editButton, deleteBtn);

		simpleFunc.setSpacing(3);
		moreFunc.getChildren().addAll(new Button("print report"), switchButton, month, year);
		moreFunc.setSpacing(3);
		searchBox.getChildren().addAll(search, genre);

		allFunc.getChildren().addAll(simpleFunc, moreFunc, searchBox);
		allFunc.setSpacing(250);

		this.getChildren().add(allFunc);

		// =============================================================================
		table = new TableView<Delivery>();
		TableColumn<Delivery, String> code = new TableColumn<Delivery, String>("ID");
		code.setMinWidth(60);
		code.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Delivery, String> date = new TableColumn<Delivery, String>("date");
		date.setMinWidth(100);
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<Delivery, String> customerName = new TableColumn<Delivery, String>("Customer Name.");
		customerName.setMinWidth(200);
		customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		TableColumn<Delivery, String> totalAmount = new TableColumn<Delivery, String>("Total Amount");
		totalAmount.setCellValueFactory(new PropertyValueFactory<>("valueAfterTaxForTable"));
		totalAmount.setMinWidth(120);
		TableColumn<Delivery, String> creator = new TableColumn<Delivery, String>("Created by");
		creator.setCellValueFactory(new PropertyValueFactory<>("creator"));
		creator.setMinWidth(200);
		table.getColumns().addAll(code, date, customerName, totalAmount, creator);
		table.setMaxHeight(500);
		this.getChildren().add(table);
		this.setSpacing(5);

		table.setRowFactory(tv -> {
			TableRow<Delivery> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					
							openDE(row.getItem());
				
				}
			});
			return row;
		});

		table.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				Delivery de = table.getSelectionModel().getSelectedItem();
				if (de != null) {
					if (keyEvent.getCode().equals(KeyCode.DELETE)) {
						
								delete(de);
					

					}

				}
			}
		});
//=========================
		table2 = new TableView<Delivery>();
		TableColumn<Delivery, String> codeCol = new TableColumn<Delivery, String>("code");
		codeCol.setMinWidth(20);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Delivery, String> dateCol = new TableColumn<Delivery, String>("date");
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableColumn<Delivery, String> descriptionCol = new TableColumn<Delivery, String>("Description");
		descriptionCol.setMinWidth(600);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
		TableColumn<Delivery, String> quantityCol = new TableColumn<Delivery, String>("Quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
		TableColumn<Delivery, String> unitCol = new TableColumn<Delivery, String>("Unit");
		unitCol.setMinWidth(100);
		unitCol.setCellValueFactory(new PropertyValueFactory<>("productUnit"));

		table2.getColumns().addAll(codeCol, dateCol, descriptionCol, quantityCol, unitCol, creator);
		table2.setRowFactory(tv -> {
			TableRow<Delivery> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {

					
							openDE(row.getItem());
					
				}
			});

			return row;
		});
		table2.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				Delivery de = table.getSelectionModel().getSelectedItem();
				if (de != null) {
					if (keyEvent.getCode().equals(KeyCode.DELETE)) {

						
								delete(de);
					
					}

				}
			}
		});

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
		
				updateDE("");


	}

	public static void updateDE(String search) {
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from delivery;";
			ResultSet rs = stmt.executeQuery(sql);
			table.getItems().clear();
			table2.getItems().clear();
			while (rs.next()) {
				String id = rs.getString("id");
				String date = rs.getString("date"); // 28-07-2563
				if (Integer.parseInt(date.substring(3, 5)) != month.getValue()
						|| Integer.parseInt(date.substring(6)) != year.getValue()) {
					continue;
				}
				String code = rs.getString("customercode");
				String contact = rs.getString("contact");

				Gson gson = new Gson();
				TypeToken<ArrayList<Item>> token = new TypeToken<ArrayList<Item>>() {
				};
				ArrayList<Item> itemList = gson.fromJson(rs.getString("product"), token.getType());
				String sql2 = "select * from customer where code = '" + code + "';";
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sql2);
				rs2.next();
				Customer customer = new Customer(rs2.getString("code"), rs2.getString("name"), rs2.getString("taxid"),
						rs2.getString("address"), rs2.getString("tel"), rs2.getString("fax"), rs2.getString("email"));
				Delivery delivery = new Delivery(id, date, customer, itemList, contact, rs.getString("user"));
				boolean addToTable = false;
				if (genre.getValue() != null && !search.isEmpty()) {
					switch (genre.getValue()) {
					case "Code":
						addToTable = id.contains(search);
						break;
					case "Product":
						for (Item item : itemList) {
							if (item.getCode().contains(search) || item.getDescription().contains(search)) {
								addToTable = true;
								break;
							}
							try {
								Double searchNum = Double.parseDouble(search);
								if (searchNum == item.getItemQuantity() || searchNum == item.getPrice()) {
									addToTable = true;
								}
							} catch (NumberFormatException nfe) {
								continue;
							}
						}
						break;
					case "Customer Name":
						addToTable = customer.getName().contains(search);
						break;
					case "Creator":

						addToTable = rs.getString("user").contains(search);
						break;
					case "Amount":
						try {
							Double searchNum = Double.parseDouble(search);
							addToTable = (searchNum == delivery.getValueAfterTax());
						} catch (NumberFormatException nfe) {
							break;
						}
						break;
					default:
						break;
					}
				} else {
					addToTable = true;
				}
				if (addToTable) {

					table.getItems().add(delivery);
					table2.getItems().add(delivery);

				}
				stmt2.close();
			}
			conn.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void openDE(Delivery de) {
		Stage newStage = new Stage();
		Scene denewScene = new Scene(new DENewUI(newStage, de));
		newStage.setScene(denewScene);
		newStage.show();
	}

	public static void delete(Delivery de) {
		Connection conn;
		try {
			conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();

			String sql = "DELETE from delivery WHERE id ='" + de.getId() + "'";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
			updateDE("");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SortedSet<String> getCustomerTree() {
		try {
			SortedSet<String> treeSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select code,name from customer";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("name");
				String b = rs.getString("code");
				treeSet.add(a);
				treeSet.add(b);
			}
			stmt.close();
			conn.close();
			return treeSet;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public SortedSet<String> getCodeTree() {
		try {
			SortedSet<String> treeSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select id from delivery";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("id");
				treeSet.add(a);
			}
			stmt.close();
			conn.close();
			return treeSet;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public SortedSet<String> getCreatorTree() {
		try {
			SortedSet<String> treeSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select user from delivery";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("user");
				treeSet.add(a);
			}
			stmt.close();
			conn.close();
			return treeSet;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public SortedSet<String> getAmountTree() {
		try {
			SortedSet<String> treeSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select valueaftertax from delivery";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("valueaftertax");
				treeSet.add(a);
			}
			stmt.close();
			conn.close();
			return treeSet;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public SortedSet<String> getProductTree() {
		try {
			SortedSet<String> treeSet = new TreeSet<String>();
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select code,description from product";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String a = rs.getString("code");
				String b = rs.getString("description");
				treeSet.add(a);
				treeSet.add(b);
			}
			stmt.close();
			conn.close();
			return treeSet;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
}
