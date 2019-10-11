package model.dao;

import db.DbRegistration;
import model.dao.impl.FinalProjectDaoJDBC;
import model.dao.impl.FinalProjectDefenseDaoJDBC;
import model.dao.impl.InstitutionDaoJDBC;
import model.dao.impl.StudentDaoJDBC;
import model.dao.impl.SupervisorOrEvaluatorDaoJDBC;

public class DaoFactory {

	public static StudentDao createStudentDao() {
		return new StudentDaoJDBC(DbRegistration.getConnection());
	}
	
	public static InstitutionDao createInstitutionDao() {
		return new InstitutionDaoJDBC(DbRegistration.getConnection());
	}
	
	public static SupervisorOrEvaluatorDao createSupervisorOrEvaluatorDao() {
		return new SupervisorOrEvaluatorDaoJDBC(DbRegistration.getConnection());
	}
	
	public static FinalProjectDao createFinalProjectDao() {
		return new FinalProjectDaoJDBC(DbRegistration.getConnection());
	}
	
	public static FinalProjectDefenseDao createFinalProjectDefenseDao() {
		return new FinalProjectDefenseDaoJDBC(DbRegistration.getConnection());
	}
	
}
