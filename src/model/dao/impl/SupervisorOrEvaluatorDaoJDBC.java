package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DbRegistration;
import db.DbException;
import db.DbIntegrityException;
import model.dao.SupervisorOrEvaluatorDao;
import model.entities.Institution;
import model.entities.SupervisorOrEvaluator;
import model.entities.enums.Gender;

public class SupervisorOrEvaluatorDaoJDBC implements SupervisorOrEvaluatorDao {

	private Connection conn;

	public SupervisorOrEvaluatorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(SupervisorOrEvaluator obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tbSupervisorOrEvaluator "
					+ "(Name, Email, MobileNumber, Gender, InstitutionId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, obj.getMobileNumber());
			st.setString(4, obj.getGender().toString());
			st.setInt(5, obj.getInstitution().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DbRegistration.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbRegistration.closeStatement(st);
		}

	}

	@Override
	public void update(SupervisorOrEvaluator obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE tbSupervisorOrEvaluator "
					+ "SET Name = ?, Email = ?, MobileNumber = ?, Gender = ?, InstitutionId = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, obj.getMobileNumber());
			st.setString(4, obj.getGender().toString());
			st.setInt(5, obj.getInstitution().getId());
			st.setInt(6, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbRegistration.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM tbSupervisorOrEvaluator WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DbRegistration.closeStatement(st);
		}

	}

	@Override
	public SupervisorOrEvaluator findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbS.*, tbI.* "
					+ "FROM tbSupervisorOrEvaluator AS tbS "
					+ "INNER JOIN tbInstitution AS tbI "
					+ "ON tbS.InstitutionId = tbI.Id "
					+ "WHERE tbS.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Institution inst = instantiateInstitution(rs);
				SupervisorOrEvaluator obj = instantiateSupervisorOrEvaluator(rs, inst);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbRegistration.closeStatement(st);
			DbRegistration.closeResultSet(rs);
		}
	}

	private SupervisorOrEvaluator instantiateSupervisorOrEvaluator(ResultSet rs, Institution inst) throws SQLException {
		SupervisorOrEvaluator obj = new SupervisorOrEvaluator();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setMobileNumber(rs.getString("MobileNumber"));
		obj.setGender(Gender.valueOf(rs.getString("Gender")));
		obj.setInstitution(inst);
		return obj;
	}

	private Institution instantiateInstitution(ResultSet rs) throws SQLException {
		Institution inst = new Institution();
		inst.setId(rs.getInt("tbI.Id"));
		inst.setAbbreviationOrAcronym(rs.getString("tbI.AbbreviationOrAcronym"));
		inst.setName(rs.getString("tbI.Name"));
		return inst;
	}
	
	@Override
	public List<SupervisorOrEvaluator> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbS.*, tbI.* "
					+ "FROM tbSupervisorOrEvaluator AS tbS "
					+ "INNER JOIN tbInstitution AS tbI "
					+ "ON tbS.InstitutionId = tbI.Id "
					+ "ORDER BY tbS.Name");
			rs = st.executeQuery();

			List<SupervisorOrEvaluator> list = new ArrayList<>();
			Map<Integer, Institution> map = new HashMap<>();

			while (rs.next()) {
				
				Institution inst = map.get(rs.getInt("InstitutionId"));
				
				if (inst == null) {
					inst = instantiateInstitution(rs);
					map.put(rs.getInt("InstitutionId"), inst);
				}
				
				SupervisorOrEvaluator obj = instantiateSupervisorOrEvaluator(rs, inst);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DbRegistration.closeStatement(st);
			DbRegistration.closeResultSet(rs);
		}
	}

	@Override
	public List<SupervisorOrEvaluator> findByInstitution(Institution institution) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbS.*, tbI.* "
					+ "FROM tbSupervisorOrEvaluator AS tbS "
					+ "INNER JOIN tbInstitution AS tbI "
					+ "ON tbS.InstitutionId = tbI.Id "
					+ "WHERE InstitutionId = ? "
					+ "ORDER BY tbS.Name");

			
			st.setInt(1, institution.getId());
			
			rs = st.executeQuery();
			
			List<SupervisorOrEvaluator> list = new ArrayList<>();
			Map<Integer, Institution> map = new HashMap<>();
			
			while (rs.next()) {
				
				Institution inst = map.get(rs.getInt("InstitutionId"));
				
				if (inst == null) {
					inst = instantiateInstitution(rs);
					map.put(rs.getInt("InstitutionId"), inst);
				}
				
				SupervisorOrEvaluator obj = instantiateSupervisorOrEvaluator(rs, inst);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DbRegistration.closeStatement(st);
			DbRegistration.closeResultSet(rs);
		}
	}

}
