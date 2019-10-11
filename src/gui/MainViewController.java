package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.InstitutionService;
import model.services.StudentService;
import model.services.SupervisorOrEvaluatorService;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemSeeAll;
	
	@FXML
	private MenuItem menuItemStartNew;
	
	@FXML
	private MenuItem menuItemStudent;
	
	@FXML
	private MenuItem menuItemSupervisorOrEvaluator;
	
	@FXML
	private MenuItem menuItemInstitution;
	
	@FXML
	private MenuItem menuItemFinalProject;
	
	@FXML
	private MenuItem menuItemDefense;
	
	@FXML
	private MenuItem menuItemAbout;
	
	
	@FXML
	public void onMenuItemSeeAllAction() {
		System.out.println("onMenuItemSeeAllAction");
	}
	
	@FXML
	public void onMenuItemStartNewAction() {
		System.out.println("onMenuItemStartNewAction");
	}
	
	@FXML
	public void onMenuItemStudentAction() {
		loadView("/gui/StudentList.fxml", (StudentListController controller)-> {
			controller.setStudentService(new StudentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemSupervisorOrEvaluatorAction() {
		loadView("/gui/SupervisorOrEvaluatorList.fxml", (SupervisorOrEvaluatorListController controller)-> {
			controller.setSupervisorOrEvaluatorService(new SupervisorOrEvaluatorService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemInstitutionAction() {
		loadView("/gui/InstitutionList.fxml", (InstitutionListController controller)-> {
			controller.setInstitutionService(new InstitutionService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemFinalProjectAction() {
		System.out.println("onMenuItemFinalProjectAction");
	}
	
	@FXML
	public void onMenuItemDefenseAction() {
		System.out.println("onMenuItemDefenseAction");
	}
		
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x->{});
	}
			
			
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
				
	}

	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
