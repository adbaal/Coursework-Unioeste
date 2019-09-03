package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.FinalProjectDefenseDao;
import model.entities.FinalProject;
import model.entities.FinalProjectDefense;
import model.entities.Institution;
import model.entities.Student;
import model.entities.SupervisorOrEvaluator;

public class FinalProjectDefenseDaoJDBC implements FinalProjectDefenseDao{
	
	private Connection conn;
	
	public FinalProjectDefenseDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(FinalProjectDefense obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FinalProjectDefense obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FinalProjectDefense findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FinalProjectDefense> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FinalProjectDefense> findBySupervisor(SupervisorOrEvaluator supervisor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FinalProjectDefense> findByAvaliator(SupervisorOrEvaluator avaliator) {
		return null;
	}

}
