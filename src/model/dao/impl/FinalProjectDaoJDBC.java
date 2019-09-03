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

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.FinalProjectDao;
import model.entities.FinalProject;
import model.entities.Institution;
import model.entities.Student;
import model.entities.SupervisorOrEvaluator;
import model.entities.enums.Gender;

public class FinalProjectDaoJDBC implements FinalProjectDao{
	
	private Connection conn;
	
	public FinalProjectDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(FinalProject obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tbFinalProject "
					+ "(StudentId, SupervisorId, CoSupervisorId, Title) "
					+ "VALUES "
					+ "(?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getStudent().getId());
			st.setInt(2, obj.getSupervisor().getId());
			st.setInt(3, obj.getCoSupervisor().getId());
			st.setString(4, obj.getTitle());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(FinalProject obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE tbFinalProject "
					+ "SET StudentId = ?, SupervisorId = ?, CoSupervisorId = ?, Title = ? "
					+ "WHERE Id = ?");
			
			st.setInt(1, obj.getStudent().getId());
			st.setInt(2, obj.getSupervisor().getId());
			st.setInt(3, obj.getCoSupervisor().getId());
			st.setString(4, obj.getTitle());
			st.setInt(5, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM tbFinalProject WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public FinalProject findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFP.*, tbSt.*, tbSup.*,  tbCoSup.*, tbInstSup.*, tbInstCoSup.* "
							+"FROM tbFinalProject AS tbFP "
							+"JOIN tbStudent AS tbSt "
							+"ON tbFP.StudentId = tbSt.Id "
							+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
							+"ON tbSup.Id = tbFP.SupervisorID "
							+"LEFT JOIN tbinstitution AS tbInstSup "
							+"ON tbInstSup.Id = tbSup.InstitutionId "
							+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
							+"ON tbCoSup.Id = tbFP.CoSupervisorID "
							+"LEFT JOIN tbinstitution AS tbInstCoSup "
							+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
							+ "WHERE tbFP.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Student student = instantiateStudent(rs);

				SupervisorOrEvaluator sup = null;
				Integer isNull = rs.getInt("tbFP.SupervisorId");
				if(isNull>0) {
					Institution instSup = instantiateInstitution(rs, "tbInstSup");
					sup = instantiateSupervisorOrEvaluator(rs, instSup, "tbSup");
				}

				SupervisorOrEvaluator coSup = null;
				isNull = rs.getInt("tbFP.CoSupervisorId");
				if(isNull>0) {
					Institution	instCoSup = instantiateInstitution(rs, "tbInstCoSup");
					coSup = instantiateSupervisorOrEvaluator(rs, instCoSup, "tbCoSup");
				}

				FinalProject obj = instantiateFinalProject(rs, student, sup, coSup);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private FinalProject instantiateFinalProject(ResultSet rs, Student student, SupervisorOrEvaluator sup, SupervisorOrEvaluator coSup) throws SQLException {
		FinalProject obj = new FinalProject();
		obj.setId(rs.getInt("tbFP.Id"));
		obj.setStudent(student);
		obj.setTitle(rs.getString("tbFP.Title"));
		obj.setSupervisor(sup);
		obj.setCoSupervisor(coSup);
		return obj;
	}
	
	private SupervisorOrEvaluator instantiateSupervisorOrEvaluator(ResultSet rs, Institution inst, String table) throws SQLException {
		SupervisorOrEvaluator obj = new SupervisorOrEvaluator();
		obj.setId(rs.getInt(table + ".Id"));
		obj.setName(rs.getString(table + ".Name"));
		obj.setEmail(rs.getString(table + ".Email"));
		obj.setMobileNamber(rs.getString(table + ".MobileNumber"));
		obj.setGender(Gender.valueOf(rs.getString(table + ".Gender")));
		obj.setInstitution(inst);
		return obj;
	}

	private Institution instantiateInstitution(ResultSet rs, String table) throws SQLException {
		Institution inst = new Institution();
		inst.setId(rs.getInt(table+".Id"));
		inst.setAbreviationOrAcronym(rs.getString(table+".AbreviationOrAcronym"));
		inst.setName(rs.getString(table+".Name"));
		return inst;
	}

	private Student instantiateStudent(ResultSet rs) throws SQLException {
		Student student = new Student();
		student.setId(rs.getInt("tbSt.Id"));
		student.setName(rs.getString("tbSt.Name"));
		student.setMobileNamber(rs.getString("tbSt.MobileNumber"));
		student.setGender(Gender.valueOf(rs.getString("tbSt.Gender")));
		return student;
	}
	
	@Override
	public List<FinalProject> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFP.*, tbSt.*, tbSup.*,  tbCoSup.*, tbInstSup.*, tbInstCoSup.* "
					+"FROM tbFinalProject AS tbFP "
					+"JOIN tbStudent AS tbSt "
					+"ON tbFP.StudentId = tbSt.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
					+"ON tbSup.Id = tbFP.SupervisorID "
					+"LEFT JOIN tbinstitution AS tbInstSup "
					+"ON tbInstSup.Id = tbSup.InstitutionId "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
					+"ON tbCoSup.Id = tbFP.CoSupervisorID "
					+"LEFT JOIN tbinstitution AS tbInstCoSup "
					+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
					+"ORDER BY tbSt.Name");
			rs = st.executeQuery();


			List<FinalProject> list = new ArrayList<>();
			Map<Integer, Institution> mapInst = new HashMap<>();
			Map<Integer, SupervisorOrEvaluator> mapSup = new HashMap<>();

			while (rs.next()) {
				
				Student student = instantiateStudent(rs);
				
				SupervisorOrEvaluator sup = null;
				Integer isNull = rs.getInt("tbFP.SupervisorId");
				if(isNull>0) {
					
					Institution instSup = mapInst.get(rs.getInt("tbSup.InstitutionId"));
					
					if (instSup == null) {
						instSup = instantiateInstitution(rs, "tbInstSup");
						mapInst.put(rs.getInt("tbSup.InstitutionId"), instSup);
					}
					
					sup = mapSup.get(rs.getInt("tbFP.SupervisorId"));

					if (sup == null) {
						sup = instantiateSupervisorOrEvaluator(rs, instSup, "tbSup");
						mapSup.put(rs.getInt("tbFP.SupervisorId"), sup);
					}
				}
				
				SupervisorOrEvaluator coSup = null;
				isNull = rs.getInt("tbFP.CoSupervisorId");
				if(isNull>0) {
					
					Institution instCoSup = mapInst.get(rs.getInt("tbCoSup.InstitutionId"));
					
					if (instCoSup == null) {
						instCoSup = instantiateInstitution(rs, "tbInstCoSup");
						mapInst.put(rs.getInt("tbCoSup.InstitutionId"), instCoSup);
					}
					
					coSup = mapSup.get(rs.getInt("tbFP.CoSupervisorId"));

					if (coSup == null) {
						coSup = instantiateSupervisorOrEvaluator(rs, instCoSup, "tbCoSup");
						mapSup.put(rs.getInt("tbFP.SupervisorId"), coSup);
					}
				}
				
				FinalProject obj = instantiateFinalProject(rs, student, sup, coSup);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<FinalProject> findBySupervisorOrEvaluator(SupervisorOrEvaluator supervisor) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFP.*, tbSt.*, tbSup.*,  tbCoSup.*, tbInstSup.*, tbInstCoSup.* "
					+"FROM tbFinalProject AS tbFP "
					+"JOIN tbStudent AS tbSt "
					+"ON tbFP.StudentId = tbSt.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
					+"ON tbSup.Id = tbFP.SupervisorID "
					+"LEFT JOIN tbinstitution AS tbInstSup "
					+"ON tbInstSup.Id = tbSup.InstitutionId "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
					+"ON tbCoSup.Id = tbFP.CoSupervisorID "
					+"LEFT JOIN tbinstitution AS tbInstCoSup "
					+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
					+"WHERE tbFP.SupervisorID = ? or tbFP.CoSupervisorID = ? "
					+"ORDER BY tbSt.Name");
			
			st.setInt(1, supervisor.getId());
			st.setInt(2, supervisor.getId());
			
			rs = st.executeQuery();


			List<FinalProject> list = new ArrayList<>();
			Map<Integer, Institution> mapInst = new HashMap<>();
			Map<Integer, SupervisorOrEvaluator> mapSup = new HashMap<>();

			while (rs.next()) {
				
				Student student = instantiateStudent(rs);
				
				SupervisorOrEvaluator sup = null;
				Integer isNull = rs.getInt("tbFP.SupervisorId");
				if(isNull>0) {
					
					Institution instSup = mapInst.get(rs.getInt("tbSup.InstitutionId"));
					
					if (instSup == null) {
						instSup = instantiateInstitution(rs, "tbInstSup");
						mapInst.put(rs.getInt("tbSup.InstitutionId"), instSup);
					}
					
					sup = mapSup.get(rs.getInt("tbFP.SupervisorId"));

					if (sup == null) {
						sup = instantiateSupervisorOrEvaluator(rs, instSup, "tbSup");
						mapSup.put(rs.getInt("tbFP.SupervisorId"), sup);
					}
				}
				
				SupervisorOrEvaluator coSup = null;
				isNull = rs.getInt("tbFP.CoSupervisorId");
				if(isNull>0) {
					
					Institution instCoSup = mapInst.get(rs.getInt("tbCoSup.InstitutionId"));
					
					if (instCoSup == null) {
						instCoSup = instantiateInstitution(rs, "tbInstCoSup");
						mapInst.put(rs.getInt("tbCoSup.InstitutionId"), instCoSup);
					}
					
					coSup = mapSup.get(rs.getInt("tbFP.CoSupervisorId"));

					if (coSup == null) {
						coSup = instantiateSupervisorOrEvaluator(rs, instCoSup, "tbCoSup");
						mapSup.put(rs.getInt("tbFP.SupervisorId"), coSup);
					}
				}
				
				FinalProject obj = instantiateFinalProject(rs, student, sup, coSup);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
