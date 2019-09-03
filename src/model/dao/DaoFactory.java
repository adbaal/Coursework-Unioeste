package model.dao;

import db.DB;
import model.dao.impl.FinalProjectDaoJDBC;
import model.dao.impl.InstitutionDaoJDBC;
import model.dao.impl.StudentDaoJDBC;
import model.dao.impl.SupervisorOrEvaluatorDaoJDBC;

public class DaoFactory {

	public static StudentDao createStudentDao() {
		return new StudentDaoJDBC(DB.getConnection());
	}
	
	public static InstitutionDao createInstitutionDao() {
		return new InstitutionDaoJDBC(DB.getConnection());
	}
	
	public static SupervisorOrEvaluatorDao createSupervisorOrEvaluatorDao() {
		return new SupervisorOrEvaluatorDaoJDBC(DB.getConnection());
	}
	
	public static FinalProjectDao createFinalProjectDao() {
		return new FinalProjectDaoJDBC(DB.getConnection());
	}
	
}
