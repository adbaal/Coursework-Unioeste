package model.dao;

import java.util.List;

import model.entities.Institution;


public interface InstitutionDao {

	void insert(Institution obj);
	void update(Institution obj);
	void deleteById(Integer id);
	Institution findById(Integer id);
	List<Institution> findAll();
}
