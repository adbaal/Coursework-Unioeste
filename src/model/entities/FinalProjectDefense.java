package model.entities;

import java.util.Date;

public class FinalProjectDefense {

	private Integer id;
	private FinalProject finalProject;
	private Date defenseDate;
	private String location;
	private SupervisorOrEvaluator evaluator1;
	private SupervisorOrEvaluator evaluator2;
	private SupervisorOrEvaluator evaluator3;
	private SupervisorOrEvaluator evaluator4;
	private SupervisorOrEvaluator evaluator5;
	private SupervisorOrEvaluator evaluator6;
	
	public FinalProjectDefense() {
		
	}
	
	public FinalProjectDefense(Integer id, FinalProject finalProject, Date defenseDate, String location,
			SupervisorOrEvaluator evaluator1, SupervisorOrEvaluator evaluator2, SupervisorOrEvaluator evaluator3,
			SupervisorOrEvaluator evaluator4, SupervisorOrEvaluator evaluator5, SupervisorOrEvaluator evaluator6) {
		super();
		this.id = id;
		this.finalProject = finalProject;
		this.defenseDate = defenseDate;
		this.location = location;
		this.evaluator1 = evaluator1;
		this.evaluator2 = evaluator2;
		this.evaluator3 = evaluator3;
		this.evaluator4 = evaluator4;
		this.evaluator5 = evaluator5;
		this.evaluator6 = evaluator6;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FinalProject getFinalProject() {
		return finalProject;
	}

	public void setFinalProject(FinalProject finalProject) {
		this.finalProject = finalProject;
	}

	public Date getDefenseDate() {
		return defenseDate;
	}

	public void setDefenseDate(Date defenseDate) {
		this.defenseDate = defenseDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public SupervisorOrEvaluator getEvaluator1() {
		return evaluator1;
	}

	public void setEvaluator1(SupervisorOrEvaluator evaluator1) {
		this.evaluator1 = evaluator1;
	}

	public SupervisorOrEvaluator getEvaluator2() {
		return evaluator2;
	}

	public void setEvaluator2(SupervisorOrEvaluator evaluator2) {
		this.evaluator2 = evaluator2;
	}

	public SupervisorOrEvaluator getEvaluator3() {
		return evaluator3;
	}

	public void setEvaluator3(SupervisorOrEvaluator evaluator3) {
		this.evaluator3 = evaluator3;
	}

	public SupervisorOrEvaluator getEvaluator4() {
		return evaluator4;
	}

	public void setEvaluator4(SupervisorOrEvaluator evaluator4) {
		this.evaluator4 = evaluator4;
	}

	public SupervisorOrEvaluator getEvaluator5() {
		return evaluator5;
	}

	public void setEvaluator5(SupervisorOrEvaluator evaluator5) {
		this.evaluator5 = evaluator5;
	}

	public SupervisorOrEvaluator getEvaluator6() {
		return evaluator6;
	}

	public void setEvaluator6(SupervisorOrEvaluator evaluator6) {
		this.evaluator6 = evaluator6;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finalProject == null) ? 0 : finalProject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinalProjectDefense other = (FinalProjectDefense) obj;
		if (finalProject == null) {
			if (other.finalProject != null)
				return false;
		} else if (!finalProject.equals(other.finalProject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FinalProjectDefense [id=" + id + ", finalProject=" + finalProject + ", defenseDate=" + defenseDate
				+ ", location=" + location + ", evaluator1=" + evaluator1 + ", evaluator2=" + evaluator2
				+ ", evaluator3=" + evaluator3 + ", evaluator4=" + evaluator4 + ", evaluator5=" + evaluator5
				+ ", evaluator6=" + evaluator6 + "]";
	}
	
	
	
	
	
}
