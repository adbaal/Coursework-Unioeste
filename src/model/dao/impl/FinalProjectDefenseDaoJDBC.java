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
import model.dao.FinalProjectDefenseDao;
import model.entities.FinalProject;
import model.entities.FinalProjectDefense;
import model.entities.Institution;
import model.entities.Student;
import model.entities.SupervisorOrEvaluator;
import model.entities.enums.Gender;

public class FinalProjectDefenseDaoJDBC implements FinalProjectDefenseDao{
	
	private Connection conn;
	
	public FinalProjectDefenseDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(FinalProjectDefense obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tbFinalProjectDefense "
					+ "(FinalProjectId, DefenseDate, Location, Evaluator1Id, Evaluator2Id, Evaluator3Id, Evaluator4Id, Evaluator5Id, Evaluator6Id) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getFinalProject().getId());
			st.setDate(2, new java.sql.Date(obj.getDefenseDate().getTime()));
			st.setString(3, obj.getLocation());
			if(obj.getEvaluator1()==null) {
				st.setNull(4, java.sql.Types.INTEGER);
			}else {
				st.setInt(4, obj.getEvaluator1().getId());
			}
			
			if(obj.getEvaluator2()==null) {
				st.setNull(5, java.sql.Types.INTEGER);
			}else {
				st.setInt(5, obj.getEvaluator2().getId());
			}
			
			if(obj.getEvaluator3()==null) {
				st.setNull(6, java.sql.Types.INTEGER);
			}else {
				st.setInt(6, obj.getEvaluator3().getId());
			}
			
			if(obj.getEvaluator4()==null) {
				st.setNull(7, java.sql.Types.INTEGER);
			}else {
				st.setInt(7, obj.getEvaluator4().getId());
			}
			
			if(obj.getEvaluator5()==null) {
				st.setNull(8, java.sql.Types.INTEGER);
			}else {
				st.setInt(8, obj.getEvaluator5().getId());
			}
			
			if(obj.getEvaluator6()==null) {
				st.setNull(9, java.sql.Types.INTEGER);
			}else {
				st.setInt(9, obj.getEvaluator6().getId());
			}
			
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
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DbRegistration.closeStatement(st);
		}
	}

	
	
	@Override
	public void update(FinalProjectDefense obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE tbFinalProjectDefense "
					+ "SET FinalProjectId = ?, DefenseDate = ?, Location = ?, Evaluator1Id = ?, Evaluator2Id = ?, Evaluator3Id = ?, Evaluator4Id = ?, Evaluator5Id = ?, Evaluator6Id = ? "
					+ "WHERE Id = ?");
			
			st.setInt(1, obj.getFinalProject().getId());
			st.setDate(2, new java.sql.Date(obj.getDefenseDate().getTime()));
			st.setString(3, obj.getLocation());
			
			if(obj.getEvaluator1()==null) {
				st.setNull(4, java.sql.Types.INTEGER);
			}else {
				st.setInt(4, obj.getEvaluator1().getId());
			}
			
			if(obj.getEvaluator2()==null) {
				st.setNull(5, java.sql.Types.INTEGER);
			}else {
				st.setInt(5, obj.getEvaluator2().getId());
			}
			
			if(obj.getEvaluator3()==null) {
				st.setNull(6, java.sql.Types.INTEGER);
			}else {
				st.setInt(6, obj.getEvaluator3().getId());
			}
			
			if(obj.getEvaluator4()==null) {
				st.setNull(7, java.sql.Types.INTEGER);
			}else {
				st.setInt(7, obj.getEvaluator4().getId());
			}
			
			if(obj.getEvaluator5()==null) {
				st.setNull(8, java.sql.Types.INTEGER);
			}else {
				st.setInt(8, obj.getEvaluator5().getId());
			}
			
			if(obj.getEvaluator6()==null) {
				st.setNull(9, java.sql.Types.INTEGER);
			}else {
				st.setInt(9, obj.getEvaluator6().getId());
			}
		
			st.setInt(10, obj.getId());

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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM tbFinalProjectDefense WHERE Id = ?");

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
	public FinalProjectDefense findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFPD.*, tbFP.*, tbSt.*, tbSup.*, tbInstSup.*, tbCoSup.*, tbInstCoSup.*, tbEv1.*, tbInstEv1.*, tbEv2.*, tbInstEv2.*, tbEv3.*, tbInstEv3.*, tbEv4.*, tbInstEv4.*, tbEv5.*, tbInstEv5.*, tbEv6.*, tbInstEv6.* "
					+"FROM tbFinalProjectDefense AS tbFPD "
					+"JOIN tbFinalProject AS tbFP "
					+"ON tbFPD.FinalProjectId = tbFP.Id "
					+"JOIN tbStudent AS tbSt "
					+"ON tbFP.StudentId = tbSt.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
					+"ON tbSup.Id = tbFP.SupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstSup "
					+"ON tbInstSup.Id = tbSup.InstitutionId "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
					+"ON tbCoSup.Id = tbFP.CoSupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstCoSup "
					+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv1 "
					+"ON tbFPD.Evaluator1Id = tbEv1.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv1 "
					+"ON tbEv1.InstitutionId = tbInstEv1.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv2 "
					+"ON tbFPD.Evaluator2Id = tbEv2.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv2 "
					+"ON tbEv2.InstitutionId = tbInstEv2.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv3 "
					+"ON tbFPD.Evaluator3Id = tbEv3.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv3 "
					+"ON tbEv3.InstitutionId = tbInstEv3.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv4 "
					+"ON tbFPD.Evaluator4Id = tbEv4.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv4 "
					+"ON tbEv4.InstitutionId = tbInstEv4.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv5 "
					+"ON tbFPD.Evaluator5Id = tbEv5.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv5 "
					+"ON tbEv5.InstitutionId = tbInstEv5.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv6 "
					+"ON tbFPD.Evaluator6Id = tbEv6.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv6 "
					+"ON tbEv6.InstitutionId = tbInstEv6.Id "
					+"WHERE tbFPD.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			Map<Integer, Institution> mapInst = new HashMap<>();
			Map<Integer, SupervisorOrEvaluator> mapSup = new HashMap<>();

			if(rs.next()) {

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
						mapSup.put(rs.getInt("tbFP.CoSupervisorId"), coSup);
					}
				}

				FinalProject fp = instantiateFinalProject(rs, student, sup, coSup);

				SupervisorOrEvaluator ev1 = null;
				isNull = rs.getInt("tbFPD.Evaluator1Id");
				if(isNull>0) {
					Institution instEv1 = mapInst.get(rs.getInt("tbEv1.InstitutionId"));

					if (instEv1 == null) {
						instEv1 = instantiateInstitution(rs, "tbInstEv1");
						mapInst.put(rs.getInt("tbEv1.InstitutionId"), instEv1);
					}

					ev1 = mapSup.get(rs.getInt("tbFPD.Evaluator1Id"));

					if (ev1 == null) {
						ev1 = instantiateSupervisorOrEvaluator(rs, instEv1, "tbEv1");
						mapSup.put(rs.getInt("tbFPD.Evaluator1Id"), ev1);
					}
				}

				SupervisorOrEvaluator ev2 = null;
				isNull = rs.getInt("tbFPD.Evaluator2Id");
				if(isNull>0) {
					Institution instEv2 = mapInst.get(rs.getInt("tbEv2.InstitutionId"));

					if (instEv2 == null) {
						instEv2 = instantiateInstitution(rs, "tbInstEv2");
						mapInst.put(rs.getInt("tbEv2.InstitutionId"), instEv2);
					}

					ev2 = mapSup.get(rs.getInt("tbFPD.Evaluator2Id"));

					if (ev2 == null) {
						ev2 = instantiateSupervisorOrEvaluator(rs, instEv2, "tbEv2");
						mapSup.put(rs.getInt("tbFPD.Evaluator2Id"), ev2);
					}
				}

				SupervisorOrEvaluator ev3 = null;
				isNull = rs.getInt("tbFPD.Evaluator3Id");
				if(isNull>0) {
					Institution instEv3 = mapInst.get(rs.getInt("tbEv3.InstitutionId"));

					if (instEv3 == null) {
						instEv3 = instantiateInstitution(rs, "tbInstEv3");
						mapInst.put(rs.getInt("tbEv3.InstitutionId"), instEv3);
					}

					ev3 = mapSup.get(rs.getInt("tbFPD.Evaluator3Id"));

					if (ev3 == null) {
						ev3 = instantiateSupervisorOrEvaluator(rs, instEv3, "tbEv3");
						mapSup.put(rs.getInt("tbFPD.Evaluator3Id"), ev3);
					}
				}

				SupervisorOrEvaluator ev4 = null;
				isNull = rs.getInt("tbFPD.Evaluator4Id");
				if(isNull>0) {
					Institution instEv4 = mapInst.get(rs.getInt("tbEv4.InstitutionId"));

					if (instEv4 == null) {
						instEv4 = instantiateInstitution(rs, "tbInstEv4");
						mapInst.put(rs.getInt("tbEv4.InstitutionId"), instEv4);
					}

					ev4 = mapSup.get(rs.getInt("tbFPD.Evaluator4Id"));

					if (ev4 == null) {
						ev4 = instantiateSupervisorOrEvaluator(rs, instEv4, "tbEv4");
						mapSup.put(rs.getInt("tbFPD.Evaluator4Id"), ev4);
					}
				}

				SupervisorOrEvaluator ev5 = null;
				isNull = rs.getInt("tbFPD.Evaluator5Id");
				if(isNull>0) {
					Institution instEv5 = mapInst.get(rs.getInt("tbEv5.InstitutionId"));

					if (instEv5 == null) {
						instEv5 = instantiateInstitution(rs, "tbInstEv5");
						mapInst.put(rs.getInt("tbEv5.InstitutionId"), instEv5);
					}

					ev5 = mapSup.get(rs.getInt("tbFPD.Evaluator5Id"));

					if (ev5 == null) {
						ev5 = instantiateSupervisorOrEvaluator(rs, instEv5, "tbEv5");
						mapSup.put(rs.getInt("tbFPD.Evaluator5Id"), ev5);
					}
				}

				SupervisorOrEvaluator ev6 = null;
				isNull = rs.getInt("tbFPD.Evaluator6Id");
				if(isNull>0) {
					Institution instEv6 = mapInst.get(rs.getInt("tbEv6.InstitutionId"));

					if (instEv6 == null) {
						instEv6 = instantiateInstitution(rs, "tbInstEv6");
						mapInst.put(rs.getInt("tbEv6.InstitutionId"), instEv6);
					}

					ev6 = mapSup.get(rs.getInt("tbFPD.Evaluator6Id"));

					if (ev6 == null) {
						ev6 = instantiateSupervisorOrEvaluator(rs, instEv6, "tbEv6");
						mapSup.put(rs.getInt("tbFPD.Evaluator6Id"), ev6);
					}
				}

				FinalProjectDefense obj = instantiateFinalProjectDefense(rs, fp, ev1, ev2, ev3, ev4, ev5, ev6);


				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DbRegistration.closeStatement(st);
			DbRegistration.closeResultSet(rs);
		}
	}

	private FinalProjectDefense instantiateFinalProjectDefense(ResultSet rs, FinalProject fp, SupervisorOrEvaluator ev1, SupervisorOrEvaluator ev2, SupervisorOrEvaluator ev3, SupervisorOrEvaluator ev4, SupervisorOrEvaluator ev5, SupervisorOrEvaluator ev6) throws SQLException {
		FinalProjectDefense obj = new FinalProjectDefense();
		obj.setId(rs.getInt("tbFPD.Id"));
		obj.setFinalProject(fp);
		obj.setDefenseDate(new java.util.Date(rs.getTimestamp("tbFPD.DefenseDate").getTime()));
		obj.setLocation(rs.getString("tbFPD.Location"));
		obj.setEvaluator1(ev1);
		obj.setEvaluator2(ev2);
		obj.setEvaluator3(ev3);
		obj.setEvaluator4(ev4);
		obj.setEvaluator5(ev5);
		obj.setEvaluator6(ev6);
		return obj;
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
		obj.setMobileNumber(rs.getString(table + ".MobileNumber"));
		obj.setGender(Gender.valueOf(rs.getString(table + ".Gender")));
		obj.setInstitution(inst);
		return obj;
	}

	private Institution instantiateInstitution(ResultSet rs, String table) throws SQLException {
		Institution inst = new Institution();
		inst.setId(rs.getInt(table+".Id"));
		inst.setAbbreviationOrAcronym(rs.getString(table+".AbbreviationOrAcronym"));
		inst.setName(rs.getString(table+".Name"));
		return inst;
	}

	private Student instantiateStudent(ResultSet rs) throws SQLException {
		Student student = new Student();
		student.setId(rs.getInt("tbSt.Id"));
		student.setName(rs.getString("tbSt.Name"));
		student.setMobileNumber(rs.getString("tbSt.MobileNumber"));
		student.setGender(Gender.valueOf(rs.getString("tbSt.Gender")));
		return student;
	}
	
	@Override
	public List<FinalProjectDefense> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFPD.*, tbFP.*, tbSt.*, tbSup.*, tbInstSup.*, tbCoSup.*, tbInstCoSup.*, tbEv1.*, tbInstEv1.*, tbEv2.*, tbInstEv2.*, tbEv3.*, tbInstEv3.*, tbEv4.*, tbInstEv4.*, tbEv5.*, tbInstEv5.*, tbEv6.*, tbInstEv6.* "
					+"FROM tbFinalProjectDefense AS tbFPD "
					+"JOIN tbFinalProject AS tbFP "
					+"ON tbFPD.FinalProjectId = tbFP.Id "
					+"JOIN tbStudent AS tbSt "
					+"ON tbFP.StudentId = tbSt.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
					+"ON tbSup.Id = tbFP.SupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstSup "
					+"ON tbInstSup.Id = tbSup.InstitutionId "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
					+"ON tbCoSup.Id = tbFP.CoSupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstCoSup "
					+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv1 "
					+"ON tbFPD.Evaluator1Id = tbEv1.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv1 "
					+"ON tbEv1.InstitutionId = tbInstEv1.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv2 "
					+"ON tbFPD.Evaluator2Id = tbEv2.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv2 "
					+"ON tbEv2.InstitutionId = tbInstEv2.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv3 "
					+"ON tbFPD.Evaluator3Id = tbEv3.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv3 "
					+"ON tbEv3.InstitutionId = tbInstEv3.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv4 "
					+"ON tbFPD.Evaluator4Id = tbEv4.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv4 "
					+"ON tbEv4.InstitutionId = tbInstEv4.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv5 "
					+"ON tbFPD.Evaluator5Id = tbEv5.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv5 "
					+"ON tbEv5.InstitutionId = tbInstEv5.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv6 "
					+"ON tbFPD.Evaluator6Id = tbEv6.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv6 "
					+"ON tbEv6.InstitutionId = tbInstEv6.Id "
					+"ORDER BY tbSt.Name");
			rs = st.executeQuery();

			List<FinalProjectDefense> list = new ArrayList<>();
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
						mapSup.put(rs.getInt("tbFP.CoSupervisorId"), coSup);
					}
				}

				FinalProject fp = instantiateFinalProject(rs, student, sup, coSup);

				SupervisorOrEvaluator ev1 = null;
				isNull = rs.getInt("tbFPD.Evaluator1Id");
				if(isNull>0) {
					Institution instEv1 = mapInst.get(rs.getInt("tbEv1.InstitutionId"));

					if (instEv1 == null) {
						instEv1 = instantiateInstitution(rs, "tbInstEv1");
						mapInst.put(rs.getInt("tbEv1.InstitutionId"), instEv1);
					}

					ev1 = mapSup.get(rs.getInt("tbFPD.Evaluator1Id"));

					if (ev1 == null) {
						ev1 = instantiateSupervisorOrEvaluator(rs, instEv1, "tbEv1");
						mapSup.put(rs.getInt("tbFPD.Evaluator1Id"), ev1);
					}
				}

				SupervisorOrEvaluator ev2 = null;
				isNull = rs.getInt("tbFPD.Evaluator2Id");
				if(isNull>0) {
					Institution instEv2 = mapInst.get(rs.getInt("tbEv2.InstitutionId"));

					if (instEv2 == null) {
						instEv2 = instantiateInstitution(rs, "tbInstEv2");
						mapInst.put(rs.getInt("tbEv2.InstitutionId"), instEv2);
					}

					ev2 = mapSup.get(rs.getInt("tbFPD.Evaluator2Id"));

					if (ev2 == null) {
						ev2 = instantiateSupervisorOrEvaluator(rs, instEv2, "tbEv2");
						mapSup.put(rs.getInt("tbFPD.Evaluator2Id"), ev2);
					}
				}

				SupervisorOrEvaluator ev3 = null;
				isNull = rs.getInt("tbFPD.Evaluator3Id");
				if(isNull>0) {
					Institution instEv3 = mapInst.get(rs.getInt("tbEv3.InstitutionId"));

					if (instEv3 == null) {
						instEv3 = instantiateInstitution(rs, "tbInstEv3");
						mapInst.put(rs.getInt("tbEv3.InstitutionId"), instEv3);
					}

					ev3 = mapSup.get(rs.getInt("tbFPD.Evaluator3Id"));

					if (ev3 == null) {
						ev3 = instantiateSupervisorOrEvaluator(rs, instEv3, "tbEv3");
						mapSup.put(rs.getInt("tbFPD.Evaluator3Id"), ev3);
					}
				}

				SupervisorOrEvaluator ev4 = null;
				isNull = rs.getInt("tbFPD.Evaluator4Id");
				if(isNull>0) {
					Institution instEv4 = mapInst.get(rs.getInt("tbEv4.InstitutionId"));

					if (instEv4 == null) {
						instEv4 = instantiateInstitution(rs, "tbInstEv4");
						mapInst.put(rs.getInt("tbEv4.InstitutionId"), instEv4);
					}

					ev4 = mapSup.get(rs.getInt("tbFPD.Evaluator4Id"));

					if (ev4 == null) {
						ev4 = instantiateSupervisorOrEvaluator(rs, instEv4, "tbEv4");
						mapSup.put(rs.getInt("tbFPD.Evaluator4Id"), ev4);
					}
				}

				SupervisorOrEvaluator ev5 = null;
				isNull = rs.getInt("tbFPD.Evaluator5Id");
				if(isNull>0) {
					Institution instEv5 = mapInst.get(rs.getInt("tbEv5.InstitutionId"));

					if (instEv5 == null) {
						instEv5 = instantiateInstitution(rs, "tbInstEv5");
						mapInst.put(rs.getInt("tbEv5.InstitutionId"), instEv5);
					}

					ev5 = mapSup.get(rs.getInt("tbFPD.Evaluator5Id"));

					if (ev5 == null) {
						ev5 = instantiateSupervisorOrEvaluator(rs, instEv5, "tbEv5");
						mapSup.put(rs.getInt("tbFPD.Evaluator5Id"), ev5);
					}
				}

				SupervisorOrEvaluator ev6 = null;
				isNull = rs.getInt("tbFPD.Evaluator6Id");
				if(isNull>0) {
					Institution instEv6 = mapInst.get(rs.getInt("tbEv6.InstitutionId"));

					if (instEv6 == null) {
						instEv6 = instantiateInstitution(rs, "tbInstEv6");
						mapInst.put(rs.getInt("tbEv6.InstitutionId"), instEv6);
					}

					ev6 = mapSup.get(rs.getInt("tbFPD.Evaluator6Id"));

					if (ev6 == null) {
						ev6 = instantiateSupervisorOrEvaluator(rs, instEv6, "tbEv6");
						mapSup.put(rs.getInt("tbFPD.Evaluator6Id"), ev6);
					}
				}

				FinalProjectDefense obj = instantiateFinalProjectDefense(rs, fp, ev1, ev2, ev3, ev4, ev5, ev6);


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
	public List<FinalProjectDefense> findBySupervisor(SupervisorOrEvaluator supervisor) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFPD.*, tbFP.*, tbSt.*, tbSup.*, tbInstSup.*, tbCoSup.*, tbInstCoSup.*, tbEv1.*, tbInstEv1.*, tbEv2.*, tbInstEv2.*, tbEv3.*, tbInstEv3.*, tbEv4.*, tbInstEv4.*, tbEv5.*, tbInstEv5.*, tbEv6.*, tbInstEv6.* "
					+"FROM tbFinalProjectDefense AS tbFPD "
					+"JOIN tbFinalProject AS tbFP "
					+"ON tbFPD.FinalProjectId = tbFP.Id "
					+"JOIN tbStudent AS tbSt "
					+"ON tbFP.StudentId = tbSt.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
					+"ON tbSup.Id = tbFP.SupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstSup "
					+"ON tbInstSup.Id = tbSup.InstitutionId "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
					+"ON tbCoSup.Id = tbFP.CoSupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstCoSup "
					+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv1 "
					+"ON tbFPD.Evaluator1Id = tbEv1.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv1 "
					+"ON tbEv1.InstitutionId = tbInstEv1.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv2 "
					+"ON tbFPD.Evaluator2Id = tbEv2.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv2 "
					+"ON tbEv2.InstitutionId = tbInstEv2.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv3 "
					+"ON tbFPD.Evaluator3Id = tbEv3.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv3 "
					+"ON tbEv3.InstitutionId = tbInstEv3.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv4 "
					+"ON tbFPD.Evaluator4Id = tbEv4.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv4 "
					+"ON tbEv4.InstitutionId = tbInstEv4.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv5 "
					+"ON tbFPD.Evaluator5Id = tbEv5.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv5 "
					+"ON tbEv5.InstitutionId = tbInstEv5.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv6 "
					+"ON tbFPD.Evaluator6Id = tbEv6.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv6 "
					+"ON tbEv6.InstitutionId = tbInstEv6.Id "
					+"WHERE tbFP.SupervisorID = ? "
					+"ORDER BY tbSt.Name");
			
			st.setInt(1, supervisor.getId());
			rs = st.executeQuery();

			List<FinalProjectDefense> list = new ArrayList<>();
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
						mapSup.put(rs.getInt("tbFP.CoSupervisorId"), coSup);
					}
				}
				
				FinalProject fp = instantiateFinalProject(rs, student, sup, coSup);
				
				SupervisorOrEvaluator ev1 = null;
				isNull = rs.getInt("tbFPD.Evaluator1Id");
				if(isNull>0) {
					Institution instEv1 = mapInst.get(rs.getInt("tbEv1.InstitutionId"));
					
					if (instEv1 == null) {
						instEv1 = instantiateInstitution(rs, "tbInstEv1");
						mapInst.put(rs.getInt("tbEv1.InstitutionId"), instEv1);
					}
					
					ev1 = mapSup.get(rs.getInt("tbFPD.Evaluator1Id"));

					if (ev1 == null) {
						ev1 = instantiateSupervisorOrEvaluator(rs, instEv1, "tbEv1");
						mapSup.put(rs.getInt("tbFPD.Evaluator1Id"), ev1);
					}
				}
				
				SupervisorOrEvaluator ev2 = null;
				isNull = rs.getInt("tbFPD.Evaluator2Id");
				if(isNull>0) {
					Institution instEv2 = mapInst.get(rs.getInt("tbEv2.InstitutionId"));
					
					if (instEv2 == null) {
						instEv2 = instantiateInstitution(rs, "tbInstEv2");
						mapInst.put(rs.getInt("tbEv2.InstitutionId"), instEv2);
					}
					
					ev2 = mapSup.get(rs.getInt("tbFPD.Evaluator2Id"));

					if (ev2 == null) {
						ev2 = instantiateSupervisorOrEvaluator(rs, instEv2, "tbEv2");
						mapSup.put(rs.getInt("tbFPD.Evaluator2Id"), ev2);
					}
				}
				
				SupervisorOrEvaluator ev3 = null;
				isNull = rs.getInt("tbFPD.Evaluator3Id");
				if(isNull>0) {
					Institution instEv3 = mapInst.get(rs.getInt("tbEv3.InstitutionId"));
					
					if (instEv3 == null) {
						instEv3 = instantiateInstitution(rs, "tbInstEv3");
						mapInst.put(rs.getInt("tbEv3.InstitutionId"), instEv3);
					}
					
					ev3 = mapSup.get(rs.getInt("tbFPD.Evaluator3Id"));

					if (ev3 == null) {
						ev3 = instantiateSupervisorOrEvaluator(rs, instEv3, "tbEv3");
						mapSup.put(rs.getInt("tbFPD.Evaluator3Id"), ev3);
					}
				}
				
				SupervisorOrEvaluator ev4 = null;
				isNull = rs.getInt("tbFPD.Evaluator4Id");
				if(isNull>0) {
					Institution instEv4 = mapInst.get(rs.getInt("tbEv4.InstitutionId"));
					
					if (instEv4 == null) {
						instEv4 = instantiateInstitution(rs, "tbInstEv4");
						mapInst.put(rs.getInt("tbEv4.InstitutionId"), instEv4);
					}
					
					ev4 = mapSup.get(rs.getInt("tbFPD.Evaluator4Id"));

					if (ev4 == null) {
						ev4 = instantiateSupervisorOrEvaluator(rs, instEv4, "tbEv4");
						mapSup.put(rs.getInt("tbFPD.Evaluator4Id"), ev4);
					}
				}
				
				SupervisorOrEvaluator ev5 = null;
				isNull = rs.getInt("tbFPD.Evaluator5Id");
				if(isNull>0) {
					Institution instEv5 = mapInst.get(rs.getInt("tbEv5.InstitutionId"));
					
					if (instEv5 == null) {
						instEv5 = instantiateInstitution(rs, "tbInstEv5");
						mapInst.put(rs.getInt("tbEv5.InstitutionId"), instEv5);
					}
					
					ev5 = mapSup.get(rs.getInt("tbFPD.Evaluator5Id"));

					if (ev5 == null) {
						ev5 = instantiateSupervisorOrEvaluator(rs, instEv5, "tbEv5");
						mapSup.put(rs.getInt("tbFPD.Evaluator5Id"), ev5);
					}
				}
				
				SupervisorOrEvaluator ev6 = null;
				isNull = rs.getInt("tbFPD.Evaluator6Id");
				if(isNull>0) {
					Institution instEv6 = mapInst.get(rs.getInt("tbEv6.InstitutionId"));
					
					if (instEv6 == null) {
						instEv6 = instantiateInstitution(rs, "tbInstEv6");
						mapInst.put(rs.getInt("tbEv6.InstitutionId"), instEv6);
					}
					
					ev6 = mapSup.get(rs.getInt("tbFPD.Evaluator6Id"));

					if (ev6 == null) {
						ev6 = instantiateSupervisorOrEvaluator(rs, instEv6, "tbEv6");
						mapSup.put(rs.getInt("tbFPD.Evaluator6Id"), ev6);
					}
				}
				
				FinalProjectDefense obj = instantiateFinalProjectDefense(rs, fp, ev1, ev2, ev3, ev4, ev5, ev6);
				
				
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
	public List<FinalProjectDefense> findByEvaluator(SupervisorOrEvaluator evaluator) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFPD.*, tbFP.*, tbSt.*, tbSup.*, tbInstSup.*, tbCoSup.*, tbInstCoSup.*, tbEv1.*, tbInstEv1.*, tbEv2.*, tbInstEv2.*, tbEv3.*, tbInstEv3.*, tbEv4.*, tbInstEv4.*, tbEv5.*, tbInstEv5.*, tbEv6.*, tbInstEv6.* "
					+"FROM tbFinalProjectDefense AS tbFPD "
					+"JOIN tbFinalProject AS tbFP "
					+"ON tbFPD.FinalProjectId = tbFP.Id "
					+"JOIN tbStudent AS tbSt "
					+"ON tbFP.StudentId = tbSt.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
					+"ON tbSup.Id = tbFP.SupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstSup "
					+"ON tbInstSup.Id = tbSup.InstitutionId "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
					+"ON tbCoSup.Id = tbFP.CoSupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstCoSup "
					+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv1 "
					+"ON tbFPD.Evaluator1Id = tbEv1.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv1 "
					+"ON tbEv1.InstitutionId = tbInstEv1.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv2 "
					+"ON tbFPD.Evaluator2Id = tbEv2.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv2 "
					+"ON tbEv2.InstitutionId = tbInstEv2.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv3 "
					+"ON tbFPD.Evaluator3Id = tbEv3.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv3 "
					+"ON tbEv3.InstitutionId = tbInstEv3.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv4 "
					+"ON tbFPD.Evaluator4Id = tbEv4.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv4 "
					+"ON tbEv4.InstitutionId = tbInstEv4.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv5 "
					+"ON tbFPD.Evaluator5Id = tbEv5.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv5 "
					+"ON tbEv5.InstitutionId = tbInstEv5.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv6 "
					+"ON tbFPD.Evaluator6Id = tbEv6.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv6 "
					+"ON tbEv6.InstitutionId = tbInstEv6.Id "
					+"WHERE tbFPD.Evaluator1Id = ? or tbFPD.Evaluator2Id = ? or tbFPD.Evaluator3Id = ? or tbFPD.Evaluator4Id = ? or tbFPD.Evaluator5Id = ? or tbFPD.Evaluator6Id = ? "
					+"ORDER BY tbSt.Name");
			
			st.setInt(1, evaluator.getId());
			st.setInt(2, evaluator.getId());
			st.setInt(3, evaluator.getId());
			st.setInt(4, evaluator.getId());
			st.setInt(5, evaluator.getId());
			st.setInt(6, evaluator.getId());
			rs = st.executeQuery();

			List<FinalProjectDefense> list = new ArrayList<>();
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
						mapSup.put(rs.getInt("tbFP.CoSupervisorId"), coSup);
					}
				}
				
				FinalProject fp = instantiateFinalProject(rs, student, sup, coSup);
				
				SupervisorOrEvaluator ev1 = null;
				isNull = rs.getInt("tbFPD.Evaluator1Id");
				if(isNull>0) {
					Institution instEv1 = mapInst.get(rs.getInt("tbEv1.InstitutionId"));
					
					if (instEv1 == null) {
						instEv1 = instantiateInstitution(rs, "tbInstEv1");
						mapInst.put(rs.getInt("tbEv1.InstitutionId"), instEv1);
					}
					
					ev1 = mapSup.get(rs.getInt("tbFPD.Evaluator1Id"));

					if (ev1 == null) {
						ev1 = instantiateSupervisorOrEvaluator(rs, instEv1, "tbEv1");
						mapSup.put(rs.getInt("tbFPD.Evaluator1Id"), ev1);
					}
				}
				
				SupervisorOrEvaluator ev2 = null;
				isNull = rs.getInt("tbFPD.Evaluator2Id");
				if(isNull>0) {
					Institution instEv2 = mapInst.get(rs.getInt("tbEv2.InstitutionId"));
					
					if (instEv2 == null) {
						instEv2 = instantiateInstitution(rs, "tbInstEv2");
						mapInst.put(rs.getInt("tbEv2.InstitutionId"), instEv2);
					}
					
					ev2 = mapSup.get(rs.getInt("tbFPD.Evaluator2Id"));

					if (ev2 == null) {
						ev2 = instantiateSupervisorOrEvaluator(rs, instEv2, "tbEv2");
						mapSup.put(rs.getInt("tbFPD.Evaluator2Id"), ev2);
					}
				}
				
				SupervisorOrEvaluator ev3 = null;
				isNull = rs.getInt("tbFPD.Evaluator3Id");
				if(isNull>0) {
					Institution instEv3 = mapInst.get(rs.getInt("tbEv3.InstitutionId"));
					
					if (instEv3 == null) {
						instEv3 = instantiateInstitution(rs, "tbInstEv3");
						mapInst.put(rs.getInt("tbEv3.InstitutionId"), instEv3);
					}
					
					ev3 = mapSup.get(rs.getInt("tbFPD.Evaluator3Id"));

					if (ev3 == null) {
						ev3 = instantiateSupervisorOrEvaluator(rs, instEv3, "tbEv3");
						mapSup.put(rs.getInt("tbFPD.Evaluator3Id"), ev3);
					}
				}
				
				SupervisorOrEvaluator ev4 = null;
				isNull = rs.getInt("tbFPD.Evaluator4Id");
				if(isNull>0) {
					Institution instEv4 = mapInst.get(rs.getInt("tbEv4.InstitutionId"));
					
					if (instEv4 == null) {
						instEv4 = instantiateInstitution(rs, "tbInstEv4");
						mapInst.put(rs.getInt("tbEv4.InstitutionId"), instEv4);
					}
					
					ev4 = mapSup.get(rs.getInt("tbFPD.Evaluator4Id"));

					if (ev4 == null) {
						ev4 = instantiateSupervisorOrEvaluator(rs, instEv4, "tbEv4");
						mapSup.put(rs.getInt("tbFPD.Evaluator4Id"), ev4);
					}
				}
				
				SupervisorOrEvaluator ev5 = null;
				isNull = rs.getInt("tbFPD.Evaluator5Id");
				if(isNull>0) {
					Institution instEv5 = mapInst.get(rs.getInt("tbEv5.InstitutionId"));
					
					if (instEv5 == null) {
						instEv5 = instantiateInstitution(rs, "tbInstEv5");
						mapInst.put(rs.getInt("tbEv5.InstitutionId"), instEv5);
					}
					
					ev5 = mapSup.get(rs.getInt("tbFPD.Evaluator5Id"));

					if (ev5 == null) {
						ev5 = instantiateSupervisorOrEvaluator(rs, instEv5, "tbEv5");
						mapSup.put(rs.getInt("tbFPD.Evaluator5Id"), ev5);
					}
				}
				
				SupervisorOrEvaluator ev6 = null;
				isNull = rs.getInt("tbFPD.Evaluator6Id");
				if(isNull>0) {
					Institution instEv6 = mapInst.get(rs.getInt("tbEv6.InstitutionId"));
					
					if (instEv6 == null) {
						instEv6 = instantiateInstitution(rs, "tbInstEv6");
						mapInst.put(rs.getInt("tbEv6.InstitutionId"), instEv6);
					}
					
					ev6 = mapSup.get(rs.getInt("tbFPD.Evaluator6Id"));

					if (ev6 == null) {
						ev6 = instantiateSupervisorOrEvaluator(rs, instEv6, "tbEv6");
						mapSup.put(rs.getInt("tbFPD.Evaluator6Id"), ev6);
					}
				}
				
				FinalProjectDefense obj = instantiateFinalProjectDefense(rs, fp, ev1, ev2, ev3, ev4, ev5, ev6);
				
				
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
	public FinalProjectDefense findByStudent(Student student) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFPD.*, tbFP.*, tbSt.*, tbSup.*, tbInstSup.*, tbCoSup.*, tbInstCoSup.*, tbEv1.*, tbInstEv1.*, tbEv2.*, tbInstEv2.*, tbEv3.*, tbInstEv3.*, tbEv4.*, tbInstEv4.*, tbEv5.*, tbInstEv5.*, tbEv6.*, tbInstEv6.* "
					+"FROM tbFinalProjectDefense AS tbFPD "
					+"JOIN tbFinalProject AS tbFP "
					+"ON tbFPD.FinalProjectId = tbFP.Id "
					+"JOIN tbStudent AS tbSt "
					+"ON tbFP.StudentId = tbSt.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
					+"ON tbSup.Id = tbFP.SupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstSup "
					+"ON tbInstSup.Id = tbSup.InstitutionId "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
					+"ON tbCoSup.Id = tbFP.CoSupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstCoSup "
					+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv1 "
					+"ON tbFPD.Evaluator1Id = tbEv1.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv1 "
					+"ON tbEv1.InstitutionId = tbInstEv1.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv2 "
					+"ON tbFPD.Evaluator2Id = tbEv2.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv2 "
					+"ON tbEv2.InstitutionId = tbInstEv2.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv3 "
					+"ON tbFPD.Evaluator3Id = tbEv3.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv3 "
					+"ON tbEv3.InstitutionId = tbInstEv3.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv4 "
					+"ON tbFPD.Evaluator4Id = tbEv4.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv4 "
					+"ON tbEv4.InstitutionId = tbInstEv4.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv5 "
					+"ON tbFPD.Evaluator5Id = tbEv5.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv5 "
					+"ON tbEv5.InstitutionId = tbInstEv5.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv6 "
					+"ON tbFPD.Evaluator6Id = tbEv6.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv6 "
					+"ON tbEv6.InstitutionId = tbInstEv6.Id "
					+"WHERE tbSt.Id = ?");
			
			st.setInt(1, student.getId());
			rs = st.executeQuery();
			
			Map<Integer, Institution> mapInst = new HashMap<>();
			Map<Integer, SupervisorOrEvaluator> mapSup = new HashMap<>();

			if(rs.next()) {

				Student student2 = instantiateStudent(rs);

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
						mapSup.put(rs.getInt("tbFP.CoSupervisorId"), coSup);
					}
				}

				FinalProject fp = instantiateFinalProject(rs, student2, sup, coSup);

				SupervisorOrEvaluator ev1 = null;
				isNull = rs.getInt("tbFPD.Evaluator1Id");
				if(isNull>0) {
					Institution instEv1 = mapInst.get(rs.getInt("tbEv1.InstitutionId"));

					if (instEv1 == null) {
						instEv1 = instantiateInstitution(rs, "tbInstEv1");
						mapInst.put(rs.getInt("tbEv1.InstitutionId"), instEv1);
					}

					ev1 = mapSup.get(rs.getInt("tbFPD.Evaluator1Id"));

					if (ev1 == null) {
						ev1 = instantiateSupervisorOrEvaluator(rs, instEv1, "tbEv1");
						mapSup.put(rs.getInt("tbFPD.Evaluator1Id"), ev1);
					}
				}

				SupervisorOrEvaluator ev2 = null;
				isNull = rs.getInt("tbFPD.Evaluator2Id");
				if(isNull>0) {
					Institution instEv2 = mapInst.get(rs.getInt("tbEv2.InstitutionId"));

					if (instEv2 == null) {
						instEv2 = instantiateInstitution(rs, "tbInstEv2");
						mapInst.put(rs.getInt("tbEv2.InstitutionId"), instEv2);
					}

					ev2 = mapSup.get(rs.getInt("tbFPD.Evaluator2Id"));

					if (ev2 == null) {
						ev2 = instantiateSupervisorOrEvaluator(rs, instEv2, "tbEv2");
						mapSup.put(rs.getInt("tbFPD.Evaluator2Id"), ev2);
					}
				}

				SupervisorOrEvaluator ev3 = null;
				isNull = rs.getInt("tbFPD.Evaluator3Id");
				if(isNull>0) {
					Institution instEv3 = mapInst.get(rs.getInt("tbEv3.InstitutionId"));

					if (instEv3 == null) {
						instEv3 = instantiateInstitution(rs, "tbInstEv3");
						mapInst.put(rs.getInt("tbEv3.InstitutionId"), instEv3);
					}

					ev3 = mapSup.get(rs.getInt("tbFPD.Evaluator3Id"));

					if (ev3 == null) {
						ev3 = instantiateSupervisorOrEvaluator(rs, instEv3, "tbEv3");
						mapSup.put(rs.getInt("tbFPD.Evaluator3Id"), ev3);
					}
				}

				SupervisorOrEvaluator ev4 = null;
				isNull = rs.getInt("tbFPD.Evaluator4Id");
				if(isNull>0) {
					Institution instEv4 = mapInst.get(rs.getInt("tbEv4.InstitutionId"));

					if (instEv4 == null) {
						instEv4 = instantiateInstitution(rs, "tbInstEv4");
						mapInst.put(rs.getInt("tbEv4.InstitutionId"), instEv4);
					}

					ev4 = mapSup.get(rs.getInt("tbFPD.Evaluator4Id"));

					if (ev4 == null) {
						ev4 = instantiateSupervisorOrEvaluator(rs, instEv4, "tbEv4");
						mapSup.put(rs.getInt("tbFPD.Evaluator4Id"), ev4);
					}
				}

				SupervisorOrEvaluator ev5 = null;
				isNull = rs.getInt("tbFPD.Evaluator5Id");
				if(isNull>0) {
					Institution instEv5 = mapInst.get(rs.getInt("tbEv5.InstitutionId"));

					if (instEv5 == null) {
						instEv5 = instantiateInstitution(rs, "tbInstEv5");
						mapInst.put(rs.getInt("tbEv5.InstitutionId"), instEv5);
					}

					ev5 = mapSup.get(rs.getInt("tbFPD.Evaluator5Id"));

					if (ev5 == null) {
						ev5 = instantiateSupervisorOrEvaluator(rs, instEv5, "tbEv5");
						mapSup.put(rs.getInt("tbFPD.Evaluator5Id"), ev5);
					}
				}

				SupervisorOrEvaluator ev6 = null;
				isNull = rs.getInt("tbFPD.Evaluator6Id");
				if(isNull>0) {
					Institution instEv6 = mapInst.get(rs.getInt("tbEv6.InstitutionId"));

					if (instEv6 == null) {
						instEv6 = instantiateInstitution(rs, "tbInstEv6");
						mapInst.put(rs.getInt("tbEv6.InstitutionId"), instEv6);
					}

					ev6 = mapSup.get(rs.getInt("tbFPD.Evaluator6Id"));

					if (ev6 == null) {
						ev6 = instantiateSupervisorOrEvaluator(rs, instEv6, "tbEv6");
						mapSup.put(rs.getInt("tbFPD.Evaluator6Id"), ev6);
					}
				}

				FinalProjectDefense obj = instantiateFinalProjectDefense(rs, fp, ev1, ev2, ev3, ev4, ev5, ev6);


				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DbRegistration.closeStatement(st);
			DbRegistration.closeResultSet(rs);
		}
	}

	@Override
	public List<FinalProjectDefense> findByCoSupervisor(SupervisorOrEvaluator coSupervisor) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT tbFPD.*, tbFP.*, tbSt.*, tbSup.*, tbInstSup.*, tbCoSup.*, tbInstCoSup.*, tbEv1.*, tbInstEv1.*, tbEv2.*, tbInstEv2.*, tbEv3.*, tbInstEv3.*, tbEv4.*, tbInstEv4.*, tbEv5.*, tbInstEv5.*, tbEv6.*, tbInstEv6.* "
					+"FROM tbFinalProjectDefense AS tbFPD "
					+"JOIN tbFinalProject AS tbFP "
					+"ON tbFPD.FinalProjectId = tbFP.Id "
					+"JOIN tbStudent AS tbSt "
					+"ON tbFP.StudentId = tbSt.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbSup "
					+"ON tbSup.Id = tbFP.SupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstSup "
					+"ON tbInstSup.Id = tbSup.InstitutionId "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbCoSup "
					+"ON tbCoSup.Id = tbFP.CoSupervisorId "
					+"LEFT JOIN tbinstitution AS tbInstCoSup "
					+"ON tbCoSup.InstitutionId = tbInstCoSup.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv1 "
					+"ON tbFPD.Evaluator1Id = tbEv1.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv1 "
					+"ON tbEv1.InstitutionId = tbInstEv1.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv2 "
					+"ON tbFPD.Evaluator2Id = tbEv2.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv2 "
					+"ON tbEv2.InstitutionId = tbInstEv2.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv3 "
					+"ON tbFPD.Evaluator3Id = tbEv3.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv3 "
					+"ON tbEv3.InstitutionId = tbInstEv3.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv4 "
					+"ON tbFPD.Evaluator4Id = tbEv4.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv4 "
					+"ON tbEv4.InstitutionId = tbInstEv4.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv5 "
					+"ON tbFPD.Evaluator5Id = tbEv5.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv5 "
					+"ON tbEv5.InstitutionId = tbInstEv5.Id "
					+"LEFT JOIN tbSupervisorOrEvaluator AS tbEv6 "
					+"ON tbFPD.Evaluator6Id = tbEv6.Id "
					+"LEFT JOIN tbinstitution AS tbInstEv6 "
					+"ON tbEv6.InstitutionId = tbInstEv6.Id "
					+"WHERE tbFP.CoSupervisorID = ? "
					+"ORDER BY tbSt.Name");
			
			st.setInt(1, coSupervisor.getId());
			rs = st.executeQuery();

			List<FinalProjectDefense> list = new ArrayList<>();
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
						mapSup.put(rs.getInt("tbFP.CoSupervisorId"), coSup);
					}
				}
				
				FinalProject fp = instantiateFinalProject(rs, student, sup, coSup);
				
				SupervisorOrEvaluator ev1 = null;
				isNull = rs.getInt("tbFPD.Evaluator1Id");
				if(isNull>0) {
					Institution instEv1 = mapInst.get(rs.getInt("tbEv1.InstitutionId"));
					
					if (instEv1 == null) {
						instEv1 = instantiateInstitution(rs, "tbInstEv1");
						mapInst.put(rs.getInt("tbEv1.InstitutionId"), instEv1);
					}
					
					ev1 = mapSup.get(rs.getInt("tbFPD.Evaluator1Id"));

					if (ev1 == null) {
						ev1 = instantiateSupervisorOrEvaluator(rs, instEv1, "tbEv1");
						mapSup.put(rs.getInt("tbFPD.Evaluator1Id"), ev1);
					}
				}
				
				SupervisorOrEvaluator ev2 = null;
				isNull = rs.getInt("tbFPD.Evaluator2Id");
				if(isNull>0) {
					Institution instEv2 = mapInst.get(rs.getInt("tbEv2.InstitutionId"));
					
					if (instEv2 == null) {
						instEv2 = instantiateInstitution(rs, "tbInstEv2");
						mapInst.put(rs.getInt("tbEv2.InstitutionId"), instEv2);
					}
					
					ev2 = mapSup.get(rs.getInt("tbFPD.Evaluator2Id"));

					if (ev2 == null) {
						ev2 = instantiateSupervisorOrEvaluator(rs, instEv2, "tbEv2");
						mapSup.put(rs.getInt("tbFPD.Evaluator2Id"), ev2);
					}
				}
				
				SupervisorOrEvaluator ev3 = null;
				isNull = rs.getInt("tbFPD.Evaluator3Id");
				if(isNull>0) {
					Institution instEv3 = mapInst.get(rs.getInt("tbEv3.InstitutionId"));
					
					if (instEv3 == null) {
						instEv3 = instantiateInstitution(rs, "tbInstEv3");
						mapInst.put(rs.getInt("tbEv3.InstitutionId"), instEv3);
					}
					
					ev3 = mapSup.get(rs.getInt("tbFPD.Evaluator3Id"));

					if (ev3 == null) {
						ev3 = instantiateSupervisorOrEvaluator(rs, instEv3, "tbEv3");
						mapSup.put(rs.getInt("tbFPD.Evaluator3Id"), ev3);
					}
				}
				
				SupervisorOrEvaluator ev4 = null;
				isNull = rs.getInt("tbFPD.Evaluator4Id");
				if(isNull>0) {
					Institution instEv4 = mapInst.get(rs.getInt("tbEv4.InstitutionId"));
					
					if (instEv4 == null) {
						instEv4 = instantiateInstitution(rs, "tbInstEv4");
						mapInst.put(rs.getInt("tbEv4.InstitutionId"), instEv4);
					}
					
					ev4 = mapSup.get(rs.getInt("tbFPD.Evaluator4Id"));

					if (ev4 == null) {
						ev4 = instantiateSupervisorOrEvaluator(rs, instEv4, "tbEv4");
						mapSup.put(rs.getInt("tbFPD.Evaluator4Id"), ev4);
					}
				}
				
				SupervisorOrEvaluator ev5 = null;
				isNull = rs.getInt("tbFPD.Evaluator5Id");
				if(isNull>0) {
					Institution instEv5 = mapInst.get(rs.getInt("tbEv5.InstitutionId"));
					
					if (instEv5 == null) {
						instEv5 = instantiateInstitution(rs, "tbInstEv5");
						mapInst.put(rs.getInt("tbEv5.InstitutionId"), instEv5);
					}
					
					ev5 = mapSup.get(rs.getInt("tbFPD.Evaluator5Id"));

					if (ev5 == null) {
						ev5 = instantiateSupervisorOrEvaluator(rs, instEv5, "tbEv5");
						mapSup.put(rs.getInt("tbFPD.Evaluator5Id"), ev5);
					}
				}
				
				SupervisorOrEvaluator ev6 = null;
				isNull = rs.getInt("tbFPD.Evaluator6Id");
				if(isNull>0) {
					Institution instEv6 = mapInst.get(rs.getInt("tbEv6.InstitutionId"));
					
					if (instEv6 == null) {
						instEv6 = instantiateInstitution(rs, "tbInstEv6");
						mapInst.put(rs.getInt("tbEv6.InstitutionId"), instEv6);
					}
					
					ev6 = mapSup.get(rs.getInt("tbFPD.Evaluator6Id"));

					if (ev6 == null) {
						ev6 = instantiateSupervisorOrEvaluator(rs, instEv6, "tbEv6");
						mapSup.put(rs.getInt("tbFPD.Evaluator6Id"), ev6);
					}
				}
				
				FinalProjectDefense obj = instantiateFinalProjectDefense(rs, fp, ev1, ev2, ev3, ev4, ev5, ev6);
				
				
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

}
