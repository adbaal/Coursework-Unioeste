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
import model.entities.Student;
import model.services.StudentService;

public class StudentListController implements Initializable, DataChangeListener{
	
	private StudentService service;
	
	private Integer lastSelectedItem=0;
	
	@FXML
	private TableView<Student> tableViewStudent;
	
	@FXML
	private TableColumn<Student, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Student, String> tableColumnName;
	
	@FXML
	private TableColumn<Student, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Student, String> tableColumnMobileNumber;
	
	@FXML
	private Button btNew;
	
	@FXML
	private Button btEdit;
	
	@FXML
	private Button btRemove;
	
	private ObservableList<Student> obsList;
	
	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Student obj = new Student();
		createDialogForm(obj, "/gui/StudentForm.fxml", parentStage);
	}
	
	
	@FXML
	private void onBtEditAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		lastSelectedItem = tableViewStudent.getSelectionModel().getSelectedIndex();
		Student obj = tableViewStudent.getSelectionModel().getSelectedItem();
		if(obj!=null) {
			createDialogForm(obj, "/gui/StudentForm.fxml", parentStage);
		}
	}
	
	@FXML
	private void onBtRemoveAction(ActionEvent event) {
		Student obj = tableViewStudent.getSelectionModel().getSelectedItem();
		if(obj!=null) {
			removeEntity(obj);
		}
	}
	
	public void setStudentService(StudentService service) {
		this.service=service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableViewStudent.setOnMouseClicked( event -> {
			   if( event.getClickCount() == 2 ) {
				   btEdit.fire();;
			   }});
		
	}



	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tableColumnMobileNumber.setCellValueFactory(new PropertyValueFactory<>("MobileNumber"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewStudent.prefHeightProperty().bind(stage.heightProperty());
		
	}

	public void updateTableView() {
		if(service==null) {
			throw new IllegalStateException("Service was null");
		}
		List<Student> list = service.findAll();
		obsList = FXCollections.observableList(list);
		tableViewStudent.setItems(obsList);
	}
	
	private void createDialogForm(Student obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			StudentFormController controller = loader.getController();
			controller.setStudent(obj);
			controller.setStudentService(new StudentService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Student data");
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
		tableViewStudent.getItems().clear();
		updateTableView();
		tableViewStudent.getSelectionModel().select(lastSelectedItem);		
	}
	
	
	private void removeEntity(Student obj) {
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
