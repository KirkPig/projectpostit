package ui.base;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.selection.BLSelection;
import ui.selection.CRSelection;
import ui.selection.DESelection;
import ui.selection.DatabaseUI;
import ui.selection.IVSelection;
import ui.selection.Login;
import ui.selection.POSelection;
import ui.selection.QYSelection;
import ui.selection.RBSelection;

public class Header extends Pane {
	private HBox headerBox;
	private final int BUTTONHEIGHT = 60;
	private final int BUTTONWIDTH = 100;
	private ToggleButton homeButton;
	private ToggleButton quoButton;
	private ToggleButton orderButton;
	private ToggleButton productButton;
	private ToggleButton creditButton;	
	private ToggleButton deliveryButton;
	private ToggleButton invoiceButton;
	private ToggleButton BillingButton;
	private ToggleButton databaseButton;
	private ToggleButton logOutButton;
	private VBox mainBox;
	private StackPane bottomPane;
	private Stage mainStage;

	public Header(Stage e) {
		super();
		this.mainStage = e;
		this.setPrefSize(1280, 720);
		mainBox = new VBox();

		headerBox = new HBox(5);
		headerBox.setPadding(new Insets(5));
		headerBox.setPrefSize(1280, BUTTONHEIGHT);

		homeButton = new ToggleButton("Home");
		homeButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		homeButton.setFocusTraversable(false);
		homeButton.setOnAction(new EventHandler<ActionEvent>() {
			
		
			public void handle(ActionEvent arg0) {
				homePushed();
			}
		});

		quoButton = new ToggleButton("Quotation");
		quoButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		quoButton.setFocusTraversable(false);
		quoButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				quotationPushed();
			}
		});

		orderButton = new ToggleButton("Order");
		orderButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		orderButton.setFocusTraversable(false);
		orderButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				orderPushed();
			}
		});

		productButton = new ToggleButton("Product");
		productButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		productButton.setFocusTraversable(false);
		productButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				productPushed();
			}
		});

		creditButton = new ToggleButton("Credit");
		creditButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		creditButton.setFocusTraversable(false);
		creditButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				creditPushed();
			}
		});

		deliveryButton = new ToggleButton("Delivery");
		deliveryButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		deliveryButton.setFocusTraversable(false);
		deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				deliveryPushed();
			}
		});

		invoiceButton = new ToggleButton("Invoice");
		invoiceButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		invoiceButton.setFocusTraversable(false);
		invoiceButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				invoicePushed();
			}
		});

		BillingButton = new ToggleButton("Billing");
		BillingButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		BillingButton.setFocusTraversable(false);
		BillingButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				billingPushed();
			}
		});

		databaseButton = new ToggleButton("Database");
		databaseButton.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
		databaseButton.setFocusTraversable(false);
		databaseButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				databasePushed();
			}
		});

		Region space1 = new Region();	
		HBox.setHgrow(space1, Priority.ALWAYS);

		logOutButton = new ToggleButton("LogOut");
		logOutButton.setPrefSize(100, BUTTONHEIGHT);
		logOutButton.setFocusTraversable(false);
		logOutButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				logOutPushed();
			}
		});

		headerBox.getChildren().addAll(homeButton, quoButton, orderButton, productButton, creditButton, deliveryButton,
				invoiceButton, BillingButton, databaseButton, space1, logOutButton);
		
		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.getToggles().addAll(homeButton, quoButton, orderButton, productButton, creditButton, deliveryButton,
				invoiceButton, BillingButton, databaseButton);
		
		mainBox.getChildren().add(headerBox);
		
		bottomPane = new StackPane();
		bottomPane.setPadding(new Insets(5));
		bottomPane.setPrefSize(1280, 660);
		mainBox.getChildren().add(bottomPane);
		
		this.getChildren().add(mainBox);
	}

	public void homePushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new Login());
	}

	public void quotationPushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new QYSelection());
	}

	public void orderPushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new POSelection());
	}

	public void productPushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new BLSelection());
	}

	public void creditPushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new CRSelection());
	}
	
	public void deliveryPushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new DESelection());
	}

	public void invoicePushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new IVSelection());
	}

	public void billingPushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new RBSelection());
	}

	public void databasePushed() {
		bottomPane.getChildren().clear();
		bottomPane.getChildren().add(new DatabaseUI());
	}
	
	public void logOutPushed() {
		mainStage.close();
	}

}
