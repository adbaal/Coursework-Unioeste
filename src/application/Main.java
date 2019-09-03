package application;
	
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.FinalProjectDao;
import model.dao.SupervisorOrEvaluatorDao;
import model.entities.FinalProject;
import model.entities.SupervisorOrEvaluator;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		FinalProjectDao dao = DaoFactory.createFinalProjectDao();
		
		SupervisorOrEvaluatorDao daoS = DaoFactory.createSupervisorOrEvaluatorDao();
		
		SupervisorOrEvaluator sup = daoS.findById(1);
		
		List<FinalProject> list = dao.findBySupervisorOrEvaluator(sup);
		for(FinalProject fp: list) {
			System.out.println(fp);
		}
		
		
		launch(args);

		
	}
}
