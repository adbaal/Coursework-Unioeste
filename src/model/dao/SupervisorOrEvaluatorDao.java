package model.dao;

import java.util.List;

import model.entities.Institution;
import model.entities.SupervisorOrEvaluator;

public interface SupervisorOrEvaluatorDao {

	void insert(SupervisorOrEvaluator obj);
	void update(SupervisorOrEvaluator obj);
	void deleteById(Integer id);
	SupervisorOrEvaluator findById(Integer id);
	List<SupervisorOrEvaluator> findAll();
	List<SupervisorOrEvaluator> findByInstitution(Institution institution);
}
