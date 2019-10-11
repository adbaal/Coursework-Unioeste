package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.SupervisorOrEvaluator;
import model.services.InstitutionService;
import model.services.SupervisorOrEvaluatorService;

public class SupervisorOrEvaluatorListController implements Initializable, DataChangeListener{
	
	private SupervisorOrEvaluatorService service;

	private Integer lastSelectedItem=0;
	
	@FXML
	private TableView<SupervisorOrEvaluator> tableViewSupervisorOrEvaluator;
	
	@FXML
	private TableColumn<SupervisorOrEvaluator, Integer> tableColumnId;
	
	@FXML
	private TableColumn<SupervisorOrEvaluator, String> tableColumnName;
	
	@FXML
	private TableColumn<SupervisorOrEvaluator, String> tableColumnEmail;
	
	@FXML
	private TableColumn<SupervisorOrEvaluator, String> tableColumnMobileNumber;
	
	@FXML
	private TableColumn<SupervisorOrEvaluator, String> tableColumnInstitution;
	
	@FXML
	private Button btNew;
	
	@FXML
	private Button btEdit;
	
	@FXML
	private Button btRemove;
	
	private ObservableList<SupervisorOrEvaluator> obsList;
	
	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		SupervisorOrEvaluator obj = new SupervisorOrEvaluator();
		createDialogForm(obj, "/gui/SupervisorOrEvaluatorForm.fxml", parentStage);
	}
	
	
	@FXML
	private void onBtEditAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		lastSelectedItem = tableViewSupervisorOrEvaluator.getSelectionModel().getSelectedIndex();
		SupervisorOrEvaluator obj = tableViewSupervisorOrEvaluator.getSelectionModel().getSelectedItem();
		if(obj!=null) {
			createDialogForm(obj, "/gui/SupervisorOrEvaluatorForm.fxml", parentStage);
		}
	}
	
	@FXML
	private void onBtRemoveAction(ActionEvent event) {
		SupervisorOrEvaluator obj = tableViewSupervisorOrEvaluator.getSelectionModel().getSelectedItem();
		if(obj!=null) {
			removeEntity(obj);
		}
	}
	
	public void setSupervisorOrEvaluatorService(SupervisorOrEvaluatorService service) {
		this.service=service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableViewSupervisorOrEvaluator.setOnMouseClicked( event -> {
			   if( event.getClickCount() == 2 ) {
				   btEdit.fire();;
			   }});
		
	}



	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tableColumnMobileNumber.setCellValueFactory(new PropertyValueFactory<>("MobileNumber"));
		tableColumnInstitution.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getInstitution().getAbbreviationOrAcronym()));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSupervisorOrEvaluator.prefHeightProperty().bind(stage.heightProperty());

		
	}

	public void updateTableView() {
		if(service==null) {
			throw new IllegalStateException("Service was null");
		}
		List<SupervisorOrEvaluator> list = service.findAll();
		obsList = FXCollections.observableList(list);
		tableViewSupervisorOrEvaluator.setItems(obsList);
	}
	
	private void createDialogForm(SupervisorOrEvaluator obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			SupervisorOrEvaluatorFormController controller = loader.getController();
			controller.setSupervisorOrEvaluator(obj);
			controller.setSupervisorOrEvaluatorService(new SupervisorOrEvaluatorService(), new InstitutionService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Supervisor or Evaluator data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		tableViewSupervisorOrEvaluator.getItems().clear();
		updateTableView();
		tableViewSupervisorOrEvaluator.getSelectionModel().select(lastSelectedItem);
	}
	
	
	private void removeEntity(SupervisorOrEvaluator obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete "+ obj.getName() + "?");
		if(result.get()==ButtonType.OK) {
			if(service==null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
	}
	
}
