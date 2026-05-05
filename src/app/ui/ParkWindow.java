package app.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import app.model.ThemePark;
import app.service.Logic;

public class ParkWindow extends JDialog {

	private static final long serialVersionUID = 1L;

    
    private JLabel lblAdultPrice;
    private JTextField txtAdultPrice;
    private JLabel lblChildPrice;
    private JTextField txtChildPrice;
    private JPanel pnSelection;
    private JPanel pnAddNote;
    private JLabel lblAdultTicket;
    private JSpinner spAdultTickets;
    private JLabel lblChildTicket;
    private JSpinner spChildTickets;
    private JTextField txtAddNote;

    private Logic logic;
    private ThemePark p;
    private JCheckBox chckbxAddNote;
    private JLabel lblDays;
    private JPanel pnCenter;
    private JPanel pnDetails;
    private JScrollPane scDescription;
    private JTextArea txtAreaDescription;
    private JLabel lblCountry;
    private JLabel lblLocation;
    private JTextField txtCountry;
    private JTextField txtLocation;
    private JSpinner spDays;
    private JPanel pnButtons;
    private JButton btnChoose;
    private JLabel lblImage;
    private JPanel pnSouth;
    private JPanel pnParkInfo;
    private JButton btnCancel;
    private JLabel lblStartDate;
    private JSpinner spStartDate;

    /**
	 * Create the dialog.
	 */
	public ParkWindow(Logic logic, ThemePark p) {
		getContentPane().setBackground(new Color(255, 255, 255));
		this.logic = logic;
		this.p = p;
		
		setTitle("TravelFun: ThemePark Screen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ParkWindow.class.getResource("/img/logo.png")));
		setBounds(100, 100, 597, 417);

		((JComponent) getContentPane()).setBorder(new LineBorder(new Color(255, 255, 255), 5));
		getContentPane().setLayout(new BorderLayout(0, 0));

		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getPnSouth(), BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setResizable(false);
	}

	private JLabel getLblAdult() {
		if (lblAdultPrice == null) {
			lblAdultPrice = new JLabel("Adults:");
			lblAdultPrice.setLabelFor(getTxtAdultPrice());
			lblAdultPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblAdultPrice;
	}
	private JTextField getTxtAdultPrice() {
		if (txtAdultPrice == null) {
			txtAdultPrice = new JTextField();
			txtAdultPrice.setFocusable(false);
			txtAdultPrice.setText(String.format("%.2f €", p.getAdultPrice()));
			txtAdultPrice.setEditable(false);
		}
		return txtAdultPrice;
	}
	private JLabel getLblChild() {
		if (lblChildPrice == null) {
			lblChildPrice = new JLabel("Children:");
			lblChildPrice.setLabelFor(lblChildPrice);
			lblChildPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblChildPrice;
	}
	private JTextField getTxtChildPrice() {
		if (txtChildPrice == null) {
			txtChildPrice = new JTextField();
			txtChildPrice.setFocusable(false);
			txtChildPrice.setText(String.format("%.2f €", p.getChildPrice()));
			txtChildPrice.setEditable(false);
		}
		return txtChildPrice;
	}

	private JPanel getPnSelection() {
		if (pnSelection == null) {
			pnSelection = new JPanel();
			pnSelection.setBackground(new Color(255, 255, 255));
			pnSelection.setLayout(new GridLayout(0, 4, 5, 0));
			pnSelection.add(getLblStartDate());
			pnSelection.add(getSpStartDate());
			pnSelection.add(getLblAdultTicket());
			pnSelection.add(getSpAdultTickets());
			pnSelection.add(getLblDays());
			pnSelection.add(getSpDays());
			pnSelection.add(getLblChildTicket());
			pnSelection.add(getSpChildTickets());
		}
		return pnSelection;
	}
	private JPanel getPnAddNote() {
		if (pnAddNote == null) {
			pnAddNote = new JPanel();
			pnAddNote.setBackground(new Color(255, 255, 255));
			pnAddNote.setLayout(new BorderLayout(20, 0));
			pnAddNote.add(getChckbxAddNote(), BorderLayout.NORTH);
			pnAddNote.add(getTxtAddNote());
		}
		return pnAddNote;
	}
	private JLabel getLblAdultTicket() {
		if (lblAdultTicket == null) {
			lblAdultTicket = new JLabel("Adult Tickets:");
			lblAdultTicket.setDisplayedMnemonic('A');
			lblAdultTicket.setLabelFor(getSpAdultTickets());
			lblAdultTicket.setBackground(new Color(255, 255, 255));
			lblAdultTicket.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblAdultTicket;
	}
	private JSpinner getSpAdultTickets() {
		if (spAdultTickets == null) {
			spAdultTickets = new JSpinner();
			spAdultTickets.setToolTipText("Select the number of adult tickets");
			spAdultTickets.setBackground(new Color(255, 255, 255));
			spAdultTickets.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		}
		return spAdultTickets;
	}
	private JLabel getLblChildTicket() {
		if (lblChildTicket == null) {
			lblChildTicket = new JLabel("Children Tickets:");
			lblChildTicket.setToolTipText("Select the number of children tickets");
			lblChildTicket.setDisplayedMnemonic('C');
			lblChildTicket.setLabelFor(getSpChildTickets());
			lblChildTicket.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblChildTicket;
	}
	private JSpinner getSpChildTickets() {
		if (spChildTickets == null) {
			spChildTickets = new JSpinner();
			spChildTickets.setBackground(new Color(255, 255, 255));
			spChildTickets.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		}
		return spChildTickets;
	}

	private JTextField getTxtAddNote() {
		if (txtAddNote == null) {
			txtAddNote = new JTextField();
			txtAddNote.setBackground(new Color(255, 255, 255));
			txtAddNote.setEnabled(false);
			txtAddNote.setText("Add Note...");
			txtAddNote.setColumns(10);
		}
		return txtAddNote;
	}
	private JCheckBox getChckbxAddNote() {
		if (chckbxAddNote == null) {
			chckbxAddNote = new JCheckBox("Add Note");
			chckbxAddNote.setBackground(new Color(255, 255, 255));
			chckbxAddNote.setMnemonic('d');
			chckbxAddNote.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean isSelected = chckbxAddNote.isSelected();
					getTxtAddNote().setEnabled(isSelected);
				}
			});
			chckbxAddNote.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return chckbxAddNote;
	}

	private JLabel getLblDays() {
		if (lblDays == null) {
			lblDays = new JLabel("Days:");
			lblDays.setDisplayedMnemonic('D');
			lblDays.setLabelFor(lblDays);
			lblDays.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDays;
	}

	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setBackground(new Color(255, 255, 255));
			pnCenter.setBorder(new TitledBorder(null, p.getName(), TitledBorder.LEADING, TitledBorder.TOP, 
					new Font("Bauhaus 93", Font.BOLD, 20), new Color(0x276D9B)));
			pnCenter.setLayout(new BorderLayout(10, 0));
			pnCenter.add(getLblImage(), BorderLayout.WEST);
			pnCenter.add(getPnParkInfo(), BorderLayout.CENTER);
		}
		return pnCenter;
	}
	
	private JPanel getPnDetails() {
		if (pnDetails == null) {
			pnDetails = new JPanel();
			pnDetails.setBackground(new Color(255, 255, 255));
			pnDetails.setLayout(new GridLayout(0, 4, 0, 0));
			pnDetails.add(getLblCountry());
			pnDetails.add(getTxtCountry());
			pnDetails.add(getLblLocation());
			pnDetails.add(getTxtLocation());
			pnDetails.add(getLblAdult());
			pnDetails.add(getTxtAdultPrice());
			pnDetails.add(getLblChild());
			pnDetails.add(getTxtChildPrice());
		}
		return pnDetails;
	}
	private JScrollPane getScDescription() {
		if (scDescription == null) {
			scDescription = new JScrollPane();
			scDescription.setViewportView(getTxtAreaDescription());
		}
		return scDescription;
	}
	
	private JTextArea getTxtAreaDescription() {
		if (txtAreaDescription == null) {
			txtAreaDescription = new JTextArea();
			txtAreaDescription.setFocusable(false);
			txtAreaDescription.setEditable(false);
			txtAreaDescription.setLineWrap(true);
	        txtAreaDescription.setWrapStyleWord(true);
			txtAreaDescription.setText(p.getDescription());
		}
		return txtAreaDescription;
	}
	private JLabel getLblCountry() {
		if (lblCountry == null) {
			lblCountry = new JLabel("Country:");
			lblCountry.setLabelFor(getTxtCountry());
			lblCountry.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblCountry;
	}
	private JLabel getLblLocation() {
		if (lblLocation == null) {
			lblLocation = new JLabel("Location:");
			lblLocation.setLabelFor(getTxtLocation());
			lblLocation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblLocation;
	}

	private JTextField getTxtCountry() {
		if (txtCountry == null) {
			txtCountry = new JTextField();
			txtCountry.setFocusable(false);
			txtCountry.setText(p.getCountry());
			txtCountry.setEditable(false);
		}
		return txtCountry;
	}
	private JTextField getTxtLocation() {
		if (txtLocation == null) {
			txtLocation = new JTextField();
			txtLocation.setFocusable(false);
			txtLocation.setText(p.getLocation());
			txtLocation.setEditable(false);
		}
		return txtLocation;
	}
	private JSpinner getSpDays() {
		if (spDays == null) {
			spDays = new JSpinner();
			spDays.setToolTipText("Select the number of days for which you want the tickets");
			spDays.setBackground(new Color(255, 255, 255));
			spDays.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		}
		return spDays;
	}
	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.setLayout(new BorderLayout(0, 0));
			pnButtons.add(getBtnChoose(), BorderLayout.EAST);
			pnButtons.add(getBtnCancel(), BorderLayout.WEST);

		}
		return pnButtons;
	}
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton("Choose");
			btnChoose.setToolTipText("Add the current park to the reservation");
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date startDate = (Date) getSpStartDate().getValue();
					int days = (int) getSpDays().getValue();
					int adults = (int) getSpAdultTickets().getValue();
					int children = (int) getSpChildTickets().getValue();
					
					String notes = "";
					if(getChckbxAddNote().isSelected()) {
						notes = getTxtAddNote().getText();
					}
					
					logic.setPark(p, startDate, days, adults, children, notes);
					
					
					JOptionPane.showMessageDialog(rootPane, "Park succesfully added", 
							"TravelFun: Park Screen", JOptionPane.INFORMATION_MESSAGE);
					
					dispose();
				}
			});
			btnChoose.setFont(new Font("Tahoma", Font.BOLD, 15));
			btnChoose.setActionCommand("OK");
		}
		return btnChoose;
	}
	private JLabel getLblImage() {
		if (lblImage == null) {
			lblImage = new JLabel();
			lblImage.setBackground(new Color(255, 255, 255));
			lblImage.setHorizontalTextPosition(SwingConstants.CENTER);
			lblImage.setFont(new Font("Bauhaus 93", Font.BOLD, 25));
			
			lblImage.setHorizontalAlignment(SwingConstants.CENTER);
			Image imgOriginal =new ImageIcon(ParkWindow.class.getResource("/img/" + p.getPicture())).getImage();
			Image imgEscalada = imgOriginal.getScaledInstance((int) (230), (int) (180),Image.SCALE_SMOOTH);

			lblImage.setIcon(new ImageIcon(imgEscalada));
		}
		return lblImage;
	}
	private JPanel getPnSouth() {
		if (pnSouth == null) {
			pnSouth = new JPanel();
			pnSouth.setBackground(new Color(255, 255, 255));
			pnSouth.setLayout(new BoxLayout(pnSouth, BoxLayout.Y_AXIS));
			pnSouth.add(getPnSelection());
			pnSouth.add(getPnAddNote());
			pnSouth.add(getPnButtons());
		}
		return pnSouth;
	}
	private JPanel getPnParkInfo() {
		if (pnParkInfo == null) {
			pnParkInfo = new JPanel();
			pnParkInfo.setBackground(new Color(255, 255, 255));
			pnParkInfo.setLayout(new BoxLayout(pnParkInfo, BoxLayout.Y_AXIS));
			pnParkInfo.add(getPnDetails());
			pnParkInfo.add(getScDescription());
		}
		return pnParkInfo;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setToolTipText("Go back to the list of parks");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancel.setFont(new Font("Tahoma", Font.BOLD, 15));
			btnCancel.setActionCommand("Cancel");
		}
		return btnCancel;
	}
	private JLabel getLblStartDate() {
		if (lblStartDate == null) {
			lblStartDate = new JLabel("Start Date:");
			lblStartDate.setDisplayedMnemonic('S');
			lblStartDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblStartDate.setBackground(Color.WHITE);
		}
		return lblStartDate;
	}
	private JSpinner getSpStartDate() {
		if (spStartDate == null) {
			spStartDate = new JSpinner();
			spStartDate.setToolTipText("Select the date when the tickets start");
			spStartDate.setBackground(Color.WHITE);
			Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			spStartDate.setModel(new SpinnerDateModel(today, today, null, Calendar.DAY_OF_MONTH));

			spStartDate.setEditor( new JSpinner.DateEditor(spStartDate, "dd/MM/yyyy"));
		}
		return spStartDate;
	}
}
