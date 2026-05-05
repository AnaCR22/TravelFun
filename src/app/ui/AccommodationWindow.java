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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import app.model.Accommodation;
import app.model.TypeOfAccommodation;
import app.service.Logic;

public class AccommodationWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	private Logic logic;
    private Accommodation a;
    
    private JLabel lblPrice;
    private JTextField txtPrice;
    private JPanel pnSelection;
    private JPanel pnAddNote;
    private JLabel lblStartDate;
    private JSpinner spStartDate;
    private JLabel lblNights;
    private JSpinner spNights;
    private JTextField txtAddNote;
    private JCheckBox chckbxAddNote;
    private JLabel lblPeople;
    private JPanel pnCenter;
    private JPanel pnDetails;
    private JLabel lblType;
    private JLabel lblCategory;
    private JTextField txtType;
    private JTextField txtCategory;
    private JSpinner spPeople;
    private JPanel pnButtons;
    private JButton btnChoose;
    private JPanel pnSouth;
    private JButton btnCancel;
    private JLabel lblImage;
    private JLabel lblPark;
    private JTextField txtPark;
    private JLabel lblRooms;
    private JSpinner spRooms;
    private SpinnerValidation sv;
    private JLabel lblMaxPeople;
    private JTextField txtMaxPeople;
    
	/**
	 * Create the dialog.
	 */
	public AccommodationWindow(Logic logic, Accommodation a) {
		getContentPane().setBackground(new Color(255, 255, 255));
		this.logic = logic;
		this.a = a;
		
		setTitle("TravelFun: Accommodation Screen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ParkWindow.class.getResource("/img/logo.png")));
		setBounds(100, 100, 597, 417);

		((JComponent) getContentPane()).setBorder(new LineBorder(new Color(255, 255, 255), 5));
		getContentPane().setLayout(new BorderLayout(10, 0));

		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		getContentPane().add(getPnSouth(), BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private JLabel getLblAdult() {
		if (lblPrice == null) {
			lblPrice = new JLabel("Price:");
			lblPrice.setLabelFor(getTxtPrice());
			lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblPrice;
	}
	private JTextField getTxtPrice() {
		if (txtPrice == null) {
			txtPrice = new JTextField();
			txtPrice.setText(String.format("%.2f € %s", a.getPrice(), a.getPriceDetails()));
			txtPrice.setEditable(false);
			txtPrice.setFocusable(false);
		}
		return txtPrice;
	}

	private JPanel getPnSelection() {
		if (pnSelection == null) {
			pnSelection = new JPanel();
			pnSelection.setBackground(new Color(255, 255, 255));
			pnSelection.setLayout(new GridLayout(0, 4, 5, 0));
			pnSelection.add(getLblStartDate());
			pnSelection.add(getSpStartDate());
			pnSelection.add(getLblPeople());
			pnSelection.add(getSpPeople());
			pnSelection.add(getLblNights());
			pnSelection.add(getSpNights());
			pnSelection.add(getLblRooms());
			pnSelection.add(getSpRooms());
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
	private JLabel getLblStartDate() {
		if (lblStartDate == null) {
			lblStartDate = new JLabel("Start Date:");
			lblStartDate.setDisplayedMnemonic('S');
			lblStartDate.setLabelFor(getSpStartDate());
			lblStartDate.setBackground(new Color(255, 255, 255));
			lblStartDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblStartDate;
	}
	private JSpinner getSpStartDate() {
		if (spStartDate == null) {
			spStartDate = new JSpinner();
			spStartDate.setToolTipText("Select the date when the reservation starts");
			Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			spStartDate.setModel(new SpinnerDateModel(today, today, null, Calendar.DAY_OF_MONTH));

			spStartDate.setEditor( new JSpinner.DateEditor(spStartDate, "dd/MM/yyyy"));
			spStartDate.setBackground(new Color(255, 255, 255));
		}
		return spStartDate;
	}
	private JLabel getLblNights() {
		if (lblNights == null) {
			lblNights = new JLabel("Nigths:");
			lblNights.setDisplayedMnemonic('N');
			lblNights.setLabelFor(getSpNights());
			lblNights.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblNights;
	}
	private JSpinner getSpNights() {
		if (spNights == null) {
			spNights = new JSpinner();
			spNights.setToolTipText("Select the number of nights for which you want the accommodation");
			spNights.setBackground(new Color(255, 255, 255));
			spNights.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		}
		return spNights;
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

	private JLabel getLblPeople() {
		if (lblPeople == null) {
			lblPeople = new JLabel("People:");
			lblPeople.setDisplayedMnemonic('P');
			lblPeople.setLabelFor(lblPeople);
			lblPeople.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblPeople;
	}

	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setBorder(new TitledBorder(null, a.getName(), TitledBorder.LEADING, TitledBorder.TOP, 
					new Font("Bauhaus 93", Font.BOLD, 20), new Color(0x276D9B)));
			pnCenter.setBackground(new Color(255, 255, 255));
			pnCenter.setLayout(new BorderLayout(0, 0));
			pnCenter.add(getPnDetails(), BorderLayout.CENTER);
			pnCenter.add(getLblImage(), BorderLayout.WEST);
		}
		return pnCenter;
	}
	
	private JPanel getPnDetails() {
		if (pnDetails == null) {
			pnDetails = new JPanel();
			pnDetails.setBackground(new Color(255, 255, 255));
			pnDetails.setBorder(new MatteBorder(0, 10, 0, 0, (Color) new Color(255, 255, 255)));
			pnDetails.setLayout(new GridLayout(0, 2, 0, 0));
			pnDetails.add(getLblType());
			pnDetails.add(getTxtType());
			pnDetails.add(getLblCategory());
			pnDetails.add(getTxtCategory());
			pnDetails.add(getLblAdult());
			pnDetails.add(getTxtPrice());
			pnDetails.add(getLblPark());
			pnDetails.add(getTxtPark());
			pnDetails.add(getLblMaxPeople());
			pnDetails.add(getTxtMaxPeople());
		}
		return pnDetails;
	}
	private JLabel getLblType() {
		if (lblType == null) {
			lblType = new JLabel("Type:");
			lblType.setLabelFor(getTxtType());
			lblType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblType;
	}
	private JLabel getLblCategory() {
		if (lblCategory == null) {
			lblCategory = new JLabel("Category:");
			lblCategory.setLabelFor(getTxtCategory());
			lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblCategory;
	}

	private JTextField getTxtType() {
		if (txtType == null) {
			txtType = new JTextField();
			txtType.setText(a.getType().toString());
			txtType.setEditable(false);
			txtType.setFocusable(false);
		}
		return txtType;
	}
	private JTextField getTxtCategory() {
		if (txtCategory == null) {
			txtCategory = new JTextField();
			txtCategory.setText(a.getStars());
			txtCategory.setEditable(false);
			txtCategory.setFocusable(false);
		}
		return txtCategory;
	}
	private JSpinner getSpPeople() {
		if (spPeople == null) {
			spPeople = new JSpinner();
			spPeople.setToolTipText("Select the number of people");
			spPeople.setBackground(new Color(255, 255, 255));
			spPeople.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
			
			this.sv = new SpinnerValidation(spPeople, getSpRooms(), a.getType());
			spPeople.addChangeListener(sv);
			getSpRooms().addChangeListener(sv);
		}
		return spPeople;
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
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date startDate = (Date) getSpStartDate().getValue();
					int nights = (int) getSpNights().getValue();
					int people = (int) getSpPeople().getValue();
					int numberOfAccommodations = (int) getSpRooms().getValue();

					String notes = "";
					if(getChckbxAddNote().isSelected()) {
						notes = getTxtAddNote().getText();
					}
					
					logic.setAccommodation(a, startDate, nights, people, numberOfAccommodations, notes);
					
					JOptionPane.showMessageDialog(rootPane, "Accommodation succesfully added", 
							"TravelFun: Accommodation Screen", JOptionPane.INFORMATION_MESSAGE);
					
					dispose();
				}
			});
			btnChoose.setFont(new Font("Tahoma", Font.BOLD, 15));
			btnChoose.setActionCommand("OK");
		}
		return btnChoose;
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

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
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
	private JLabel getLblImage() {
		if (lblImage == null) {
			lblImage = new JLabel();
			lblImage.setHorizontalAlignment(SwingConstants.LEFT);
			lblImage.setForeground(new Color(27, 60, 75));
			lblImage.setFont(new Font("Bauhaus 93", Font.BOLD, 25));
			Image imgOriginal =new ImageIcon(ParkWindow.class.getResource("/img/" + a.getPicture())).getImage();
			Image imgEscalada = imgOriginal.getScaledInstance((int) (200), (int) (180),Image.SCALE_SMOOTH);

			lblImage.setIcon(new ImageIcon(imgEscalada));
		}
		return lblImage;
	}
	private JLabel getLblPark() {
		if (lblPark == null) {
			lblPark = new JLabel("Theme Park:");
			lblPark.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblPark;
	}
	private JTextField getTxtPark() {
		if (txtPark == null) {
			txtPark = new JTextField();
			txtPark.setEditable(false);
			txtPark.setFocusable(false);
			txtPark.setColumns(10);
			txtPark.setText(a.getParkAssociated().getName());
		}
		return txtPark;
	}
	private JLabel getLblRooms() {
		if (lblRooms == null) {
			lblRooms = new JLabel("");
			lblRooms.setFont(new Font("Tahoma", Font.PLAIN, 15));
			TypeOfAccommodation type = a.getType();
			
			if(TypeOfAccommodation.HO.equals(type)) {
				lblRooms.setText("Nº of Rooms:");
				lblRooms.setDisplayedMnemonic('R');
			}else {
				lblRooms.setText("Nº of Apartments:");
				lblRooms.setDisplayedMnemonic('A');
			}
		}
		return lblRooms;
	}
	private JSpinner getSpRooms() {
		if (spRooms == null) {
			spRooms = new JSpinner();
			spRooms.setToolTipText("Select the number of accommodations");
			spRooms.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		}
		return spRooms;
	}
	private JLabel getLblMaxPeople() {
		if (lblMaxPeople == null) {
			lblMaxPeople = new JLabel("Maximum People:");
			lblMaxPeople.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblMaxPeople;
	}
	private JTextField getTxtMaxPeople() {
		if (txtMaxPeople == null) {
			txtMaxPeople = new JTextField();
			txtMaxPeople.setFocusable(false);
			txtMaxPeople.setEditable(false);
			txtMaxPeople.setColumns(10);
			txtMaxPeople.setText(a.getMaxOccupancy());
		}
		return txtMaxPeople;
	}
}

