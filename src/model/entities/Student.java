package model.entities;

import java.io.Serializable;

import model.entities.enums.Gender;

public class Student extends Person implements Serializable{

	private static final long serialVersionUID = 1L;


	public Student() {
		super();
	}

	public Student(Integer id, String name, String email, String mobileNumber, Gender gender) {
		super(id, name, email, mobileNumber, gender);
	}

	
	@Override
	public String toString() {
		return "Student [" + super.toString() + "]";
	}

	

	
	


	
	
	
	

}
