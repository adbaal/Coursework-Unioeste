package model.entities;

public class FinalProject {

	private Integer id;
	private Student student;
	private SupervisorOrEvaluator supervisor;
	private SupervisorOrEvaluator coSupervisor;
	private String title;
	
	
	public FinalProject() {
		
	}
	
	public FinalProject(Integer id, Student student, SupervisorOrEvaluator supervisor, SupervisorOrEvaluator coSupervisor, String title) {
		this.id = id;
		this.student = student;
		this.supervisor = supervisor;
		this.coSupervisor = coSupervisor;
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public SupervisorOrEvaluator getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(SupervisorOrEvaluator supervisor) {
		this.supervisor = supervisor;
	}

	public SupervisorOrEvaluator getCoSupervisor() {
		return coSupervisor;
	}

	public void setCoSupervisor(SupervisorOrEvaluator coSupervisor) {
		this.coSupervisor = coSupervisor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		FinalProject other = (FinalProject) obj;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FinalProject [id=" + id + ", student=" + student + ", supervisor=" + supervisor + ", coSupervisor="
				+ coSupervisor + ", title=" + title + "]";
	}
	
	
	
}
