package app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import app.util.FileUtil;

public class Reservation {

	private String code = "";
	private ThemePark selectedPark = null;
	private Accommodation selectedAccommodation = null;
	private Client client;
	
	private boolean gameWinned;
	private final static double DISCOUNT_AMOUNT = 0.15;
	
	public Reservation() {
		this.code = FileUtil.setFileName();
	}

	public String getCode() {
		return code;
	}
	
	public void saveReservation(){
		FileUtil.saveToFile(this);
	  }

	public ThemePark getSelectedPark() {
		return selectedPark;
	}
	
	public Accommodation getSelectedAccommodation() {
		return selectedAccommodation;
	}
	
	public void setSelectedPark(ThemePark p, Date startDate, int days, int adults, int children, String notes) {
		p.setNumChildren(children);
		p.setNumAdults(adults);
		p.setDays(days);
		p.setStartDate(startDate);
		p.setNotes(notes);
		
		this.selectedPark = p;
	}
	
	public void setSelectedAccommodation(Accommodation a, Date startDate, int nights, int people, int numberOfAccommodations, String notes) {
		a.setNights(nights);
		a.setNumPeople(people);
		a.setStartDate(startDate);
		a.setNumAccomodations(numberOfAccommodations);
		a.setNotes(notes);
		
		this.selectedAccommodation = a;
	}
	
	public void removePark() {
		this.selectedPark = null;
	}
	
	public void removeAccommodation() {
		this.selectedAccommodation = null;
	}

	public void setClient(Client c) {
		this.client = c;
	}
	
	private boolean isInDiscount() {
	    boolean parkOffer = (selectedPark != null && selectedPark.isParkInOffer());
	    boolean accOffer  = (selectedAccommodation != null && selectedAccommodation.getParkAssociated().isParkInOffer());

	    return parkOffer || accOffer;
	}
	
	private double getBasePrice() {
	    return getParkPrice() + getAccommodationPrice();
	}

	public double getDiscount() {
	    return isInDiscount() ? getBasePrice() * DISCOUNT_AMOUNT : 0.0;
	}

	public double getTotalPrice() {
	    return getBasePrice() - getDiscount();
	}

	public double getParkPrice() {
		return selectedPark != null ? selectedPark.getTotalPrice() : 0.0;
	}
	
	public double getAccommodationPrice() {
		return selectedAccommodation != null ? selectedAccommodation.getTotalPrice() : 0.0;
	}
	
	
	public boolean isGameWinned() {
		return gameWinned;
	}

	public void setGameWinned(boolean gameWinned) {
		this.gameWinned = gameWinned;
	}


	public String toString() {
	    DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		
		sb.append("TRAVELFUN AGENCY\n");
	    sb.append("----------------------------------------------------------------------------------------------\n");		
		sb.append("BOOKING CONFIRMATION  -  ").append(LocalDate.now().format(dateFmt)).append("  -  ");
		
		sb.append(selectedPark != null ? selectedPark.getName() : selectedAccommodation.getParkAssociated().getName());
		sb.append("\n");
		sb.append(client.toString());
  		sb.append("\n");

	    sb.append("----------------------------------------------------------------------------------------------\n");		
	    sb.append("****  BOOKING DETAILS  ****\n");

	  	if(selectedPark != null) {
		    sb.append("\n");
		    sb.append("**  TICKETS  **\n");
	  		sb.append(selectedPark.toString());
	  		sb.append("\n");
	  	}

	  	if(selectedAccommodation != null) {
		    sb.append("\n");
		    sb.append("**  ACCOMMODATION  **\n");
	  		sb.append(selectedAccommodation.toString());
	  		sb.append("\n");
	  	}

  		sb.append("\n");
  		sb.append("\n");

	    sb.append("****  BOOKING AMOUNT  ****\n");
  		sb.append("\n");

	    if(selectedPark != null) 
	    	sb.append(String.format("Tickets: \t\t\t\t %.2f €\n", selectedPark.getTotalPrice()));
	    if(selectedAccommodation != null)
	    	sb.append(String.format("Accommodation: \t\t\t %.2f €\n", selectedAccommodation.getTotalPrice()));
	    
	    sb.append(String.format("Offer discount: \t\t\t\t %.2f €\n", getDiscount()));
	    
	    if(gameWinned) 
	    	sb.append("Obtained a 100€ gift voucher for your next booking\n");
	    
	    sb.append("\n");
	    sb.append(String.format("Total amount: \t\t\t\t %.2f €\n", getTotalPrice()));
		    
	    
		return sb.toString();
	}
}
