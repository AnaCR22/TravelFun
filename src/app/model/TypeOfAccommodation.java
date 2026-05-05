package app.model;

public enum TypeOfAccommodation {
	AP("Apartment"), HO("Hotel"), AH("Aparthotel");
	
	private final String fullName;

	private TypeOfAccommodation(String fullName) {
		this.fullName = fullName;
	}
	
	@Override
    public String toString() {
        return fullName;
    }
}
