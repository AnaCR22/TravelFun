package app;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import app.service.Logic;
import app.ui.StartWindow;

public class Main {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Logic logic = new Logic();

		    JFrame.setDefaultLookAndFeelDecorated(true);
		    JDialog.setDefaultLookAndFeelDecorated(true);

		    UIManager.setLookAndFeel(
			"javax.swing.plaf.nimbus.NimbusLookAndFeel");

		    StartWindow frame = new StartWindow(logic);
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }
}
