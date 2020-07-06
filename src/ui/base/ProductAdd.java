package ui.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import bill.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import logic.DatabaseConnection;
import logic.Product;

public class ProductAdd extends HBox {
	public ProductAdd(TableView<Item> table) {
		this.setAlignment(Pos.CENTER);
		TextField searchBox = new TextField();
		searchBox.setPromptText("Search Product");
		SortedSet<String> allTree = getTree();
		this.getChildren().add(searchBox);
		ContextMenu allSuggest = new ContextMenu();
		searchBox.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if ((searchBox.getText().length() == 0) || (!searchBox.isFocused())) {
					allSuggest.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					searchResult.addAll(allTree.subSet(searchBox.getText(), searchBox.getText() + Character.MAX_VALUE));
					if (allTree.size() > 0) {
						populatePopup(searchResult);
						if (!allSuggest.isShowing()) {
							allSuggest.show(searchBox, Side.BOTTOM, 0, 0);
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
							try {
								Connection conn = DatabaseConnection.getConnection();
								Statement stmt = conn.createStatement();
								String sql = "SELECT * FROM product WHERE code ='" + result + "'";
								ResultSet rs = stmt.executeQuery(sql);

								while (rs.next()) {
									String code = rs.getString("code");
									String description = rs.getString("description");
									String unit = rs.getString("unit");
									double price = rs.getDouble("price");
									int quantity = rs.getInt("quantity");
									table.getItems().add(new Item(new Product(code, description, unit, price, quantity),0,0));

								}

								String sql2 = "SELECT * FROM product WHERE description ='" + result + "'";
								ResultSet rs2 = stmt.executeQuery(sql2);

								while (rs2.next()) {
									String code = rs2.getString("code");
									String description = rs2.getString("description");
									String unit = rs2.getString("unit");
									double price = rs2.getDouble("price");
									int quantity = rs2.getInt("quantity");

									table.getItems().add(new Item(new Product(code, description, unit, price, quantity),0,0));

									

								}
								stmt.close();
								conn.close();
							} catch (Exception e) {
								e.printStackTrace();
							}

							allSuggest.hide();
						}
					});
					menuItems.add(item);
				}
				allSuggest.getItems().clear();
				allSuggest.getItems().addAll(menuItems);
			}
		});

		searchBox.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

				allSuggest.hide();
			}
		});
		// TODO Auto-generated constructor stub
	}

	private SortedSet<String> getTree() {
		SortedSet<String> ans = new TreeSet<>();
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "select * from product";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ans.add(rs.getString("code"));
				ans.add(rs.getString("description"));
			}
			stmt.close();
			conn.close();
			return ans;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return null;
	}

}
