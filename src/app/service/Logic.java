package app.service;

import java.util.Date;
import java.util.List;

import app.model.Accommodation;
import app.model.Catalog;
import app.model.Client;
import app.model.Reservation;
import app.model.ThemePark;
import app.model.TypeOfAccommodation;

public class Logic {

	Catalog catalog = new Catalog();
	Reservation reservation = new Reservation();

	public Logic() {
		initialize();
	}

	public void initialize() {
		reservation = new Reservation();
	}

	public List<Accommodation> getAccommodations() {
		return catalog.getAccommodations();
	}

	public List<ThemePark> getParks() {
		return catalog.getParks();
	}

	public List<Accommodation> getAccommodationsByTypes(List<TypeOfAccommodation> types) {
		return catalog.getAccommodationsByType(types);
	}
	
	
	public void setPark(ThemePark p, Date startDate, int days, int adults, int children, String notes) {
		reservation.setSelectedPark(p, startDate, days, adults, children, notes);
	}

	public ThemePark getPark() {
		return reservation.getSelectedPark();
	}

	public void removePark() {
		reservation.removePark();
	}

	public void setAccommodation(Accommodation a, Date startDate, int nights, int people, int numberOfAccommodations,String notes) {
		reservation.setSelectedAccommodation(a, startDate, nights, people, numberOfAccommodations, notes);
	}

	public Accommodation getAccommodation() {
		return reservation.getSelectedAccommodation();
	}

	public void removeAccommodation() {
		reservation.removeAccommodation();
	}
	
	

	public double getTotalPrice() {
		return reservation.getTotalPrice();
	}

	public double getDiscount() {
		return reservation.getDiscount();
	}

	public double getParkPrice() {
		return reservation.getParkPrice();
	}

	public double getAccommodationPrice() {
		return reservation.getAccommodationPrice();
	}

	

	public String getSummary() {
		return reservation.toString();
	}

	public void setClient(Client c) {
		reservation.setClient(c);
	}

	public void setGameWinned(boolean b) {
		reservation.setGameWinned(b);
	}

	public void saveReservation() {
		reservation.saveReservation();
	}

	public ThemePark getParkInOffer() {
		for (ThemePark p : catalog.getParks()) {
			if (p.isParkInOffer()) {
				return p;
			}
		}
		return null;
	}

}
