package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Student;
import model.entities.enums.Gender;
import model.exceptions.ValidationException;
import model.services.StudentService;

public class StudentFormController implements Initializable{

	
	private Student entity;
	
	private StudentService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private TextField txtMobileNumber;
	
	@FXML
	private ComboBox<Gender> comboBoxGender;
	
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorMobileNumber;
	
	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;
	
	
	private ObservableList<Gender> obsList;
	
	public void setStudent(Student entity) {
		this.entity=entity;
	}
	
	public void setStudentService(StudentService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity==null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service==null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		
		
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Student getFormData() {
		Student obj = new Student();
		
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if(txtName.getText()==null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can not be empty");
		}
		obj.setName(txtName.getText());
		
		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "Field can not be empty");
		}
		obj.setEmail(txtEmail.getText());
		
		if (txtMobileNumber.getText() == null || txtMobileNumber.getText().trim().equals("")) {
			exception.addError("mobilenumber", "Field can not be empty");
		}
		obj.setMobileNumber(txtMobileNumber.getText());
		
		obj.setGender(comboBoxGender.getValue());
		
		
		if(exception.getErrors().size()>0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 100);
		Constraints.setTextFieldMaxLength(txtEmail, 100);
		Constraints.setTextFieldMaxLength(txtMobileNumber, 25);
		initializeComboBoxGender();
	}
	
	
	public void updateFormData() {
		if (entity==null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		txtMobileNumber.setText(entity.getMobileNumber());
		if(entity.getGender()==null) {
			comboBoxGender.getSelectionModel().selectFirst();
		}
		else {
			comboBoxGender.setValue(entity.getGender());
		}
		
	}
	
	
	private void initializeComboBoxGender() {
		List<Gender> list = Arrays.asList(Gender.values());
		obsList = FXCollections.observableArrayList(list);
		comboBoxGender.setItems(obsList);
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields =  errors.keySet();
		
		labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
		
		labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
		
		labelErrorMobileNumber.setText((fields.contains("mobilenumber") ? errors.get("mobilenumber") : ""));
		
		
	}
	
	
}
