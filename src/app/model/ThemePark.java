package app.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ThemePark implements Comparable<ThemePark> {
    
    private String code;        
    private String name;
    private String country;
    private String location;
    private String description;
    private double adultPrice;
    private double childPrice;
    
    private int numAdults;
    private int numChildren; 
    private int days;
    private Date startDate;
    private String notes;
    
    private boolean parkInOffer = false;
    
    
    public ThemePark(String code, String name, String country, String location,
	String description, double adultPrice, double childPrice) {
		this.code = code;
		this.name = name;
		this.country = country;
		this.location = location;
		this.description = description;
		this.adultPrice = adultPrice;
		this.childPrice = childPrice;
    }
    
    //Getters
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCountry() {
        return country;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getAdultPrice() {
        return adultPrice;
    }
    
    public double getChildPrice() {
        return childPrice;
    }

    public String getPicture() {
	return code + ".png";
    }

    public int getNumAdults() {
        return numAdults;
    }

    public int getNumChildren() {
        return numChildren;
    }
    
	public int getDays() {
		return days;
	}

	public Date getStartDate() {
		return startDate;
	}
    
    public boolean isParkInOffer() {
        return parkInOffer;
    }
    
    public double getTotalPrice() {
    	return (adultPrice * numAdults + childPrice * numChildren) * days;
    }
    
    public String getNotes() {
		return notes;
	}

    
    //Setters needed 
    public void setParkInOffer(boolean parkWithOffer) {
        this.parkInOffer = parkWithOffer;
    }
    
    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

	public void setDays(int days) {
		this.days = days;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}


	@Override
	public String toString() {
	    DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    
		return "Start Date: " + localDate.format(dateFmt) + "  /  Number of days: " + days 
			+ "\nN. adults: " + numAdults + "  /  N. children: " + numChildren;
	}

    
	@Override
	public int compareTo(ThemePark o) {
		return this.name.compareTo(o.name);
	}
}
