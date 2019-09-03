package model.dao;

import java.util.List;

import model.entities.FinalProject;
import model.entities.SupervisorOrEvaluator;

public interface FinalProjectDao {

	void insert(FinalProject obj);
	void update(FinalProject obj);
	void deleteById(Integer id);
	FinalProject findById(Integer id);
	List<FinalProject> findAll();
	List<FinalProject> findBySupervisorOrEvaluator(SupervisorOrEvaluator supervisor);
}
