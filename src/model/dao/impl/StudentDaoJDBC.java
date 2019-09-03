package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.StudentDao;
import model.entities.Student;
import model.entities.enums.Gender;

public class StudentDaoJDBC implements StudentDao {

	private Connection conn;

	public StudentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Student obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO tbStudent " +
				"(Name, Email, MobileNumber, Gender) " +
				"VALUES " +
				"(?, ?, ?, ?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, obj.getMobileNamber());
			st.setString(4, obj.getGender().toString());

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Student obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE tbStudent "
					+ "SET Name = ?, Email = ?, MobileNumber = ?, Gender = ?"
					+ "WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, obj.getMobileNamber());
			st.setString(4, obj.getGender().toString());
			st.setInt(5, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
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
				"DELETE FROM tbStudent WHERE Id = ?");

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
	public Student findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM tbStudent WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Student obj = new Student();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
				obj.setMobileNamber(rs.getString("MobileNumber"));
				obj.setGender(Gender.valueOf(rs.getString("Gender")));
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

	@Override
	public List<Student> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM tbStudent ORDER BY Name");
			rs = st.executeQuery();

			List<Student> list = new ArrayList<>();

			while (rs.next()) {
				Student obj = new Student();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
				obj.setMobileNamber(rs.getString("MobileNumber"));
				obj.setGender(Gender.valueOf(rs.getString("Gender")));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
