package app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import app.model.Accommodation;
import app.model.Reservation;
import app.model.ThemePark;
import app.model.TypeOfAccommodation;

public abstract class FileUtil {
	
    public static void loadAccomodationFile (String fileName, List<Accommodation> accomodationList) {
		
	    String line;
	    String[] p= null;	   
	    
	    try {
	    	   BufferedReader file = new BufferedReader(new FileReader(fileName));
	    		while (file.ready()) {
	    			line = file.readLine();
	    			p = line.split("@");
	    			accomodationList.add(new Accommodation(p[0],TypeOfAccommodation.valueOf(p[1]), 
	    			    Integer.parseInt(p[2]), p[3], p[4], Double.parseDouble(p[5])));
	    		}
	    		file.close();
	    }
	    catch (FileNotFoundException fnfe) {
	      System.out.println("File not found.");
	    }
	    catch (IOException ioe) {
	      new RuntimeException("I/O Error.");
	    } 
	  }
	
    public static void loadThemeParkFile (String fileName, List<ThemePark> parksList) {
	
	    String line;
	    String[] p= null;	   
	    
	    try {
	    	   BufferedReader file = new BufferedReader(new FileReader(fileName));
	    		while (file.ready()) {
	    			line = file.readLine();
	    			p = line.split("@");
	    			parksList.add(new ThemePark(p[0],p[1],p[2], p[3],
	    			    p[4], Double.parseDouble(p[5]),Double.parseDouble(p[6])));
	    		}
	    		file.close();
	    }
	    catch (FileNotFoundException fnfe) {
	      System.out.println("File not found.");
	    }
	    catch (IOException ioe) {
	      new RuntimeException("I/O Error.");
	    } 
	  }
	
	public static String setFileName(){
		String code = "";
		String base = "0123456789abcdefghijklmnopqrstuvwxyz";
		int length = 8;
		for(int i=0; i<length;i++){ 
			int numero = (int)(Math.random()*(base.length())); 
			code += base.charAt(numero);
		}
		return code;
	}

	public static void saveToFile(Reservation reservation) {
	try {
		        BufferedWriter file = new BufferedWriter(new FileWriter("files/" + reservation.getCode() + ".dat"));
		        String line = reservation.toString();
		        file.write(line);
		        file.close();
			}

		catch (FileNotFoundException fnfe) {
		      System.out.println("The file could not be saved.");
		    }
		catch (IOException ioe) {
		      new RuntimeException("I/O Error.");
		}
	  }
}
