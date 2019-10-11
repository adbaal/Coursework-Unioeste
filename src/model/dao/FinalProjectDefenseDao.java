package model.dao;

import java.util.List;

import model.entities.FinalProjectDefense;
import model.entities.Student;
import model.entities.SupervisorOrEvaluator;

public interface FinalProjectDefenseDao {
	
	void insert(FinalProjectDefense obj);
	void update(FinalProjectDefense obj);
	void deleteById(Integer id);
	FinalProjectDefense findById(Integer id);
	FinalProjectDefense findByStudent(Student student);
	List<FinalProjectDefense> findAll();
	List<FinalProjectDefense> findBySupervisor(SupervisorOrEvaluator supervisor);
	List<FinalProjectDefense> findByCoSupervisor(SupervisorOrEvaluator coSupervisor);
	List<FinalProjectDefense> findByEvaluator(SupervisorOrEvaluator evaluator);
}
