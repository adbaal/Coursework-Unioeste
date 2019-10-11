package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Institution;
import model.exceptions.ValidationException;
import model.services.InstitutionService;

public class InstitutionFormController implements Initializable{

	
	private Institution entity;
	
	private InstitutionService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtAbbreviationOrAcronym;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorAbbreviationOrAcronym;
	
	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;
	
	
	public void setInstitution(Institution entity) {
		this.entity=entity;
	}
	
	public void setInstitutionService(InstitutionService service) {
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

	private Institution getFormData() {
		Institution obj = new Institution();
		
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if(txtName.getText()==null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can not be empty");
		}
		obj.setName(txtName.getText());
		
		if (txtAbbreviationOrAcronym.getText() == null || txtAbbreviationOrAcronym.getText().trim().equals("")) {
			exception.addError("abbreviationOrAcronym", "Field can not be empty");
		}
		obj.setAbbreviationOrAcronym(txtAbbreviationOrAcronym.getText());
		
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
		Constraints.setTextFieldMaxLength(txtAbbreviationOrAcronym, 30);
	}
	
	
	public void updateFormData() {
		if (entity==null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtAbbreviationOrAcronym.setText(entity.getAbbreviationOrAcronym());
		
	}
	
	
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields =  errors.keySet();
		
		labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
		
		labelErrorAbbreviationOrAcronym.setText((fields.contains("abbreviationOrAcronym") ? errors.get("abbreviationOrAcronym") : ""));
	}
	
	
}
