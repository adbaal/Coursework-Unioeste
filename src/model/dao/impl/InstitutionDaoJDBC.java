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
import model.dao.InstitutionDao;
import model.entities.Institution;

public class InstitutionDaoJDBC implements InstitutionDao{

	private Connection conn;

	public InstitutionDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Institution obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO tbInstitution " +
				"(AbreviationOrAcronym, Name) " +
				"VALUES " +
				"(?, ?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getAbreviationOrAcronym());
			st.setString(2, obj.getName());

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
	public void update(Institution obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE tbInstitution "
					+ "SET AbreviationOrAcronym = ?, Name = ?"
					+ "WHERE Id = ?");

			st.setString(1, obj.getAbreviationOrAcronym());
			st.setString(2, obj.getName());
			st.setInt(3, obj.getId());

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
				"DELETE FROM tbInstitution WHERE Id = ?");

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
	public Institution findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM tbInstitution WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Institution obj = new Institution();
				obj.setId(rs.getInt("Id"));
				obj.setAbreviationOrAcronym(rs.getString("AbreviationOrAcronym"));
				obj.setName(rs.getString("Name"));
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
	public List<Institution> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM tbInstitution ORDER BY Name");
			rs = st.executeQuery();

			List<Institution> list = new ArrayList<>();

			while (rs.next()) {
				Institution obj = new Institution();
				obj.setId(rs.getInt("Id"));
				obj.setAbreviationOrAcronym(rs.getString("AbreviationOrAcronym"));
				obj.setName(rs.getString("Name"));
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
