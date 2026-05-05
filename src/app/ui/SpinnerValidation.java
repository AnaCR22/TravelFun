package app.ui;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.model.TypeOfAccommodation;

class SpinnerValidation implements ChangeListener {

    private final JSpinner spPeople;
    private final JSpinner spNumAccom;
    private final TypeOfAccommodation type;
    private boolean showWarningMax = true; //only show warning once
    private boolean showWarningMin = true; //only show warning once

    public SpinnerValidation(
            JSpinner spPeople,
            JSpinner spNumAccom,
            TypeOfAccommodation type) {

        this.spPeople = spPeople;
        this.spNumAccom = spNumAccom;
        this.type = type;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        int currentValue = (int) spinner.getValue();

        int people = (int) spPeople.getValue();
        int rooms = (int) spNumAccom.getValue();

        int minPeople = rooms; // 1 person per unit
        int maxPerRoom = TypeOfAccommodation.HO.equals(type) ? 2 : 4; //2 for hotels, 4 others
        int maxPeople = rooms * maxPerRoom;
        
        if (people > maxPeople) {
        	if(showWarningMax) {
        		JOptionPane.showMessageDialog(null, 
        				"Maximum occupancy is " + maxPerRoom + " people per room/apartment.\n"
        				+ "Please try again", 
        				"Maximum occupancy exceeded", JOptionPane.ERROR_MESSAGE);
        		showWarningMax = false;
        	}
            spinner.setValue(spinner == spPeople ? currentValue - 1 : currentValue + 1);
            return;
        }

        if (people < minPeople) {
        	if(showWarningMin) {
        		JOptionPane.showMessageDialog(null, 
        				"Minimum occupancy is 1 person per room/apartment.\n"
        				+ "Please try again", 
        				"Minimum occupancy not met", JOptionPane.ERROR_MESSAGE);
        		showWarningMin = false;
        	}
            spinner.setValue(spinner == spPeople ? currentValue + 1 : currentValue - 1);
        }
    }
}
