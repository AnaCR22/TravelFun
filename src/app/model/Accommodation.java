package app.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Accommodation implements Comparable<Accommodation>{

	private String code;
	private TypeOfAccommodation type;
	private int category;
	private String name;
	private String parkCode;
	private double price;

	private ThemePark parkAssociated;

	private int nights;
	private int numPeople;
	private int numAccomodations;
	private Date startDate;
	private String notes;
	
	public Accommodation(String code, TypeOfAccommodation type, int category, String name, String parkCode,
			double price) {
		this.code = code;
		this.type = type;
		this.category = category;
		this.name = name;
		this.parkCode = parkCode;
		this.price = price;
	}

	// Getters
	public String getCode() {
		return code;
	}

	public TypeOfAccommodation getType() {
		return type;
	}

	public int getCategory() {
		return category;
	}

	public String getStars() {
		return "\u2605".repeat(category) + "\u2606".repeat(5 - category);
	}

	public String getName() {
		return name;
	}

	public String getParkCode() {
		return parkCode;
	}

	public double getPrice() {
		return price;
	}

	public String getPriceDetails() {
		return "per nigth" + (type.equals(TypeOfAccommodation.HO) ? " / person" : "");
	}

	public int getNights() {
		return nights;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public Date getStartDate() {
		return startDate;
	}

	public int getNumAccomodations() {
		return numAccomodations;
	}

	public String getMaxOccupancy() {
		return type.equals(TypeOfAccommodation.HO) ? "2 per room" : "4 per apartment";
	}

	public double getTotalPrice() {
		return type.equals(TypeOfAccommodation.HO) ? price * nights * numPeople : price * nights * numAccomodations;
	}

	public String getPicture() {
		return code + ".png";
	}

	public ThemePark getParkAssociated() {
		return parkAssociated;
	}
	
	public String getNotes() {
		return notes;
	}


	// Setters needed
	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setNumAccomodations(int numAccomodations) {
		this.numAccomodations = numAccomodations;
	}

	public void setParkAssociated(ThemePark parkAssociated) {
		this.parkAssociated = parkAssociated;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return "Accommodation: " + code + "  /  " + type + "  /  " + category + " stars  /  " + name + "\nStart date: "
				+ localDate.format(dateFmt) + "  /  Number of nights: " + nights + "\nN. People: " + numPeople;

	}

	@Override
	public int compareTo(Accommodation o) {
		return this.name.compareTo(o.name);
	}

	

}
