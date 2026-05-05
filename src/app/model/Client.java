package app.model;

public class Client {
	private String name;
	private String surname;
	private String id;
	
	
	public Client( String id, String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getID() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setID(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id + "  -  " + name.toUpperCase() + " " + surname.toUpperCase();
	}
	
}
