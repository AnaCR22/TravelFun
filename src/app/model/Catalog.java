package app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.util.FileUtil;

public class Catalog {
	public final static String ACCOMMODATION_FILENAME = "files/accommodation.dat";
	public final static String PARKS_FILENAME = "files/parks.dat";

	private List<Accommodation> accommodationList = null;
	private List<ThemePark> parksList = null;

	public Catalog() {
		accommodationList = new ArrayList<Accommodation>();
		parksList = new ArrayList<ThemePark>();
		loadParks();
		loadAccommodations();
	}

	private void loadParks() {
		FileUtil.loadThemeParkFile(PARKS_FILENAME, parksList);
		assignParkInOffer();
	}

	private void loadAccommodations() {
		FileUtil.loadAccomodationFile(ACCOMMODATION_FILENAME, accommodationList);
		assignParks();
	}

	private void assignParks() {
		for (Accommodation a : getAccommodations()) {
			for (ThemePark p : getParks()) {
				if (p.getCode().equals(a.getParkCode())) {
					a.setParkAssociated(p);
				}
			}
		}
	}

	private void assignParkInOffer() {    
		if (parksList.isEmpty()) return;

		for (ThemePark p : parksList) {
	        p.setParkInOffer(false);
	    }
		
		int index = (int) (Math.random() * parksList.size());
		ThemePark parkInOffer = parksList.get(index);
		parkInOffer.setParkInOffer(true);
	}

	public List<Accommodation> getAccommodations() {
		List<Accommodation> list = new ArrayList<>(accommodationList);
	    Collections.sort(list);
	    return list;
	}

	public List<ThemePark> getParks() {
		List<ThemePark> list = new ArrayList<>(parksList);
	    Collections.sort(list);
	    return list;
	}

	public List<Accommodation> getAccommodationsByType(List<TypeOfAccommodation> types) {
		if (types.size() == 0) {
			return getAccommodations();
		}

		List<Accommodation> result = new ArrayList<>();

		for (Accommodation a : accommodationList) {
			for (TypeOfAccommodation type : types) {
				if (a.getType().equals(type)) {
					result.add(a);
				}
			}
		}
		
		Collections.sort(result);
		return result;
	}

}