package model.entities;

import model.entities.enums.Gender;

public class Person {
	private Integer id;
	private String name;
	private String email;
	private String mobileNamber;
	private Gender gender;
	
	public Person() {
		
	}
	
	public Person(Integer id, String name, String email, String mobileNamber, Gender gender) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobileNamber = mobileNamber;
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNamber() {
		return mobileNamber;
	}

	public void setMobileNamber(String mobileNamber) {
		this.mobileNamber = mobileNamber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", email=" + email + ", mobileNamber=" + mobileNamber
				+ ", gender=" + gender + "]";
	}
	
	
	
	
}
