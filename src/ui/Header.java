package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Header extends Pane {
	private HBox headerBox;
	private final int BUTTONHEIGHT = 60;
	private final int BUTTONWIDTH = 100;
	private Button homeButton;
	private Button quoButton;
	private Button orderButton;
	private Button productButton;
	private Button creditButton;	
	private Button deliveryButton;
	private Button invoiceButton;
	private Button BillingButton;
	private Button databaseButton;
	private Button logOutButton;
	
	private VBox mainBox;
	

	public Header() {
		super();
		this.setPrefSize(1280, 720);
		mainBox = new VBox();

		headerBox = new HBox(5);
		headerBox.setPadding(new Insets(5));
		headerBox.setPrefSize(1280, BUTTONHEIGHT);

		homeButton = new Button("Home");
		homeButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		homeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				homePushed();
			}
		});

		quoButton = new Button("Quotation");
		quoButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		quoButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				quotationPushed();
			}
		});

		orderButton = new Button("Order");
		orderButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		orderButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				orderPushed();
			}
		});

		productButton = new Button("Product");
		productButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		productButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				productPushed();
			}
		});

		creditButton = new Button("Credit");
		creditButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		creditButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				creditPushed();
			}
		});

		deliveryButton = new Button("Delivery");
		deliveryButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				deliveryPushed();
			}
		});

		invoiceButton = new Button("Invoice");
		invoiceButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		invoiceButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				invoicePushed();
			}
		});

		BillingButton = new Button("Billing");
		BillingButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		BillingButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				billingPushed();
			}
		});

		databaseButton = new Button("Database");
		databaseButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		databaseButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				databasePushed();
			}
		});

		Region space1 = new Region();	
		HBox.setHgrow(space1, Priority.ALWAYS);

		logOutButton = new Button("LogOut");
		logOutButton.setPrefSize(100, BUTTONHEIGHT);
		logOutButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				logOutPushed();
			}
		});

		headerBox.getChildren().addAll(homeButton, quoButton, orderButton, productButton, creditButton, deliveryButton,
				invoiceButton, BillingButton, databaseButton, space1, logOutButton);
		
		mainBox.getChildren().add(headerBox);
		this.getChildren().add(mainBox);
	}

	public void homePushed() {
		mainBox.getChildren().clear();
		mainBox.getChildren().add(headerBox);
		mainBox.getChildren().add(new Login());
	}

	public void quotationPushed() {
		mainBox.getChildren().clear();
		mainBox.getChildren().add(headerBox);
		mainBox.getChildren().add(new QYSelection());
	}

	public void orderPushed() {

	}

	public void productPushed() {

	}

	public void creditPushed() {

	}
	
	public void deliveryPushed() {

	}

	public void invoicePushed() {

	}

	public void billingPushed() {

	}

	public void databasePushed() {
		mainBox.getChildren().clear();
		mainBox.getChildren().add(headerBox);
		mainBox.getChildren().add(new DatabaseUI());
	}
	
	public void logOutPushed() {
		mainBox.getChildren().clear();
		mainBox.getChildren().add(headerBox);
		mainBox.getChildren().add(new GeneralBox(100,100));
	}

}
