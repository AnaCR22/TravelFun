package app.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.model.Accommodation;
import app.model.Client;
import app.model.ThemePark;
import app.service.Logic;

public class ReservationWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private CatalogWindow cw;
	private Logic logic;
    private SpinnerValidation sv;

	private JLabel lblName;
	private JPanel pnCenter;
	private JPanel pn1;

	private JPanel pnBtn;
	private JButton btnCancel;
	private JButton btnNext;
	private JPanel pnTotal;
	private JButton btnBack;
	private JTextField txtTickets;
	private JTextField txtAccom;
	private JTextField txtDiscounts;
	private JTextField txtTotal;
	private JSeparator separator1;
	private JSeparator separator2;

	// PARK UI
	private JPanel pnPark;
	private JPanel pnParkNorth;
	private JPanel pnParkCenter;
	private JPanel pnParkSelection;

	private JLabel lblImagePark;

	private JButton btnRemovePark;

	private JCheckBox chckbxAddNotePark;
	private JTextField txtAddNotePark;

	private JSpinner spAdults;
	private JSpinner spChildren;
	private JSpinner spParkDate;
	private JSpinner spDays;

	// ACCOM UI
	private JPanel pnAccom;
	private JPanel pnAccomNorth;
	private JPanel pnAccomCenter;
	private JPanel pnAccomSelection;

	private JLabel lblImageAccom;

	private JButton btnRemoveAccom;

	private JCheckBox chckbxAddNoteAccom;
	private JTextField txtAddNoteAccom;

	private JSpinner spPeople;
	private JSpinner spRooms;
	private JSpinner spAccomDate;
	private JSpinner spNights;
	private JPanel pnContents;
	
	// Client info
	private JPanel pn2;
	private JPanel pnEast;
	private JPanel pnClientInfo;
	private JLabel lblNameClient;
	private JTextField txtNameClient;
	private JLabel lblSurnameClient;
	private JTextField txtSurnameClient;
	private JLabel lblIdClient;
	private JTextField txtIdClient;
	private JPanel pnBtn1;
	private JPanel pnBtn2;
	private JButton btnBack2;
	private JButton btnNext2;
	
	// Summary
	private JPanel pn3;
	private JTextArea txtSummary;
	private JPanel pnBtn3;
	private JButton btnBack3;
	private JButton btnFinish;
	private JButton btnCancel2;

	private GameWindow gw;

	public ReservationWindow(CatalogWindow catalogWindow) {
		this.cw = catalogWindow;
		this.logic = cw.getLogic();

		setIconImage(Toolkit.getDefaultToolkit().getImage(CatalogWindow.class.getResource("/img/logo.png")));
		setTitle("TravelFun: Catalog Window");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 810, 566);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.setBackground(Color.WHITE);
		contentPane.add(getPnBtn(), BorderLayout.SOUTH);
		contentPane.add(getPnContents(), BorderLayout.CENTER);
		contentPane.add(getLblName(), BorderLayout.NORTH);

		setResizable(false);
		setLocationRelativeTo(null);
		
		loadHelp();
	}

	private JLabel newLabel(String text, int fontSize, boolean bold) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font("Tahoma", bold ? Font.BOLD : Font.PLAIN, fontSize));
		return lbl;
	}

	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Reservation Summary");
			lblName.setFont(new Font("Bauhaus 93", Font.BOLD, 40));
			lblName.setForeground(new Color(0x276D9B));
		}
		return lblName;
	}

	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			pn1.setBorder(new LineBorder(new Color(255, 255, 255), 8, true));
			pn1.setBackground(new Color(255, 255, 255));
			pn1.setLayout(new BorderLayout(0, 0));
			pn1.add(getPnCenter(), BorderLayout.CENTER);
			pn1.add(getPnEast(), BorderLayout.EAST);
		}
		return pn1;
	}

	private JPanel getPnBtn() {
		if (pnBtn == null) {
			pnBtn = new JPanel();
			pnBtn.setLayout(new CardLayout(0, 0));
			pnBtn.add(getPnBtn1(), "pnBtn1");
			pnBtn.add(getPnBtn2(), "pnBtn2");
			pnBtn.add(getPnBtn3(), "pnBtn3");
		}
		return pnBtn;
	}

	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel();
			pnCenter.setLayout(null);
			pnCenter.setBackground(new Color(255, 255, 255));
			pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
			updateSummary();
		}
		return pnCenter;
	}

	private void updateSummary() {
		pnCenter.removeAll();

		ThemePark selectedPark = logic.getPark();
		Accommodation selectedAccommodation = logic.getAccommodation();

		if (selectedPark != null) {
			pnCenter.add(getPnPark(selectedPark));
		}

		if (selectedAccommodation != null) {
			pnCenter.add(getPnAccom(selectedAccommodation));
		}

		pnCenter.revalidate();
		pnCenter.repaint();

		if (selectedPark == null && selectedAccommodation == null) {
			JOptionPane.showMessageDialog(this,
					"No Parks neither Accommodations added! \nYou will be redirected to the catalog screen",
					"Reservation Summary", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}

	// PARK SECTION
	private JPanel getPnPark(ThemePark p) {
		if (pnPark == null) {
			pnPark = new JPanel();
			pnPark.setBackground(new Color(255, 255, 255));
			pnPark.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					p.getName(), TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 15),
					new Color(0, 0, 0)));
			pnPark.setLayout(new BorderLayout(0, 10));
			pnPark.add(getPnParkNorth(p), BorderLayout.NORTH);
			pnPark.add(getPnParkCenter(p), BorderLayout.CENTER);

			pnPark.setMaximumSize(pnPark.getPreferredSize());
		}
		return pnPark;
	}

	private JPanel getPnParkNorth(ThemePark p) {
		if (pnParkNorth == null) {
			pnParkNorth = new JPanel();
			pnParkNorth.setBackground(new Color(255, 255, 255));
			pnParkNorth.setLayout(new BorderLayout(10, 0));
			pnParkNorth.add(getPnParkSelection(p), BorderLayout.CENTER);
			pnParkNorth.add(getLblImagePark(p), BorderLayout.WEST);
		}
		return pnParkNorth;
	}

	private JPanel getPnParkCenter(ThemePark p) {
		if (pnParkCenter == null) {
			pnParkCenter = new JPanel();
			pnParkCenter.setBackground(new Color(255, 255, 255));
			pnParkCenter.setLayout(new BorderLayout(10, 0));
			pnParkCenter.add(getBtnRemovePark(), BorderLayout.EAST);
			pnParkCenter.add(getChckbxAddNotePark(p), BorderLayout.NORTH);
			pnParkCenter.add(getTxtAddNotePark(p), BorderLayout.CENTER);
		}
		return pnParkCenter;
	}

	private JLabel getLblImagePark(ThemePark p) {
		if (lblImagePark == null) {
			lblImagePark = new JLabel("");
			lblImagePark.setVerticalTextPosition(SwingConstants.TOP);
			lblImagePark.setHorizontalTextPosition(SwingConstants.CENTER);
			lblImagePark.setFont(new Font("Tahoma", Font.PLAIN, 15));
			Image imgOriginal = new ImageIcon(getClass().getResource("/img/" + p.getPicture())).getImage();
			Image imgEscalada = imgOriginal.getScaledInstance((int) (120), (int) (100), Image.SCALE_SMOOTH);
			lblImagePark.setIcon(new ImageIcon(imgEscalada));
		}
		return lblImagePark;
	}

	private JButton getBtnRemovePark() {
		if (btnRemovePark == null) {

			btnRemovePark = new JButton("Remove All");
			btnRemovePark.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnRemovePark.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					logic.removePark();
					getTxtTickets().setText("0.00 €");
					getTxtTotal().setText(String.format("%.2f €", logic.getTotalPrice()));
					getTxtDiscounts().setText(String.format("%.2f €", logic.getDiscount()));
					updateSummary();
				}
			});
		}
		return btnRemovePark;
	}

	private JCheckBox getChckbxAddNotePark(ThemePark p) {
		if (chckbxAddNotePark == null) {
			chckbxAddNotePark = new JCheckBox("Add Note");
			chckbxAddNotePark.setFont(new Font("Tahoma", Font.PLAIN, 15));
			chckbxAddNotePark.setBackground(new Color(255, 255, 255));
			boolean hasNote = p.getNotes() != null && !p.getNotes().isBlank();
			chckbxAddNotePark.setSelected(hasNote);
			chckbxAddNotePark.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean isSelected = chckbxAddNotePark.isSelected();
					getTxtAddNotePark(p).setEnabled(isSelected);
					
					if (!isSelected) {
				        p.setNotes("");
				    }
				}
			});
		}
		return chckbxAddNotePark;
	}

	private JTextField getTxtAddNotePark(ThemePark p) {
		if (txtAddNotePark == null) {
			txtAddNotePark = new JTextField();
			boolean hasNote = p.getNotes() != null && !p.getNotes().isBlank();
	    	txtAddNotePark.setEnabled(hasNote);
	    	txtAddNotePark.setText(hasNote ? p.getNotes() : "Add Note...");
			txtAddNotePark.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					p.setNotes(txtAddNotePark.getText());
				}
			});
		}
		return txtAddNotePark;
	}

	private JPanel getPnParkSelection(ThemePark p) {
		if (pnParkSelection == null) {
			pnParkSelection = new JPanel();
			pnParkSelection.setBackground(new Color(255, 255, 255));
			pnParkSelection.setLayout(new GridLayout(0, 4, 5, 20));
			pnParkSelection.add(newLabel("Date:", 15, false));
			pnParkSelection.add(getSpParkDate(p));
			pnParkSelection.add(newLabel("Adults:", 15, false));
			pnParkSelection.add(getSpAdults(p));
			pnParkSelection.add(newLabel("Days:", 15, false));
			pnParkSelection.add(getSpDays(p));
			pnParkSelection.add(newLabel("Children:", 15, false));
			pnParkSelection.add(getSpChildren(p));
		}
		return pnParkSelection;
	}

	private JSpinner getSpAdults(ThemePark p) {
		if (spAdults == null) {
			spAdults = new JSpinner();
			spAdults.setModel(new SpinnerNumberModel(p.getNumAdults(), Integer.valueOf(1), null, Integer.valueOf(1)));
			spAdults.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					p.setNumAdults((int) spAdults.getValue());
					getTxtTickets().setText(String.format("%.2f €", logic.getParkPrice()));
					getTxtTotal().setText(String.format("%.2f €", logic.getTotalPrice()));
					getTxtDiscounts().setText(String.format("%.2f €", logic.getDiscount()));
				}
			});
		}
		return spAdults;
	}

	private JSpinner getSpChildren(ThemePark p) {
		if (spChildren == null) {
			spChildren = new JSpinner();
			spChildren.setModel(new SpinnerNumberModel(p.getNumChildren(), Integer.valueOf(0), null, Integer.valueOf(1)));
			spChildren.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					p.setNumChildren((int) spChildren.getValue());
					getTxtTickets().setText(String.format("%.2f €", logic.getParkPrice()));
					getTxtTotal().setText(String.format("%.2f €", logic.getTotalPrice()));
					getTxtDiscounts().setText(String.format("%.2f €", logic.getDiscount()));
				}
			});
		}
		return spChildren;
	}

	private JSpinner getSpParkDate(ThemePark p) {
		if (spParkDate == null) {
			spParkDate = new JSpinner();
			Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

			spParkDate.setModel(new SpinnerDateModel(p.getStartDate(), today, null, Calendar.DAY_OF_MONTH));

			spParkDate.setEditor(new JSpinner.DateEditor(spParkDate, "dd/MM/yyyy"));
			spParkDate.setBackground(new Color(255, 255, 255));
			spParkDate.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					p.setStartDate((Date) spParkDate.getValue());
				}
			});
		}
		return spParkDate;
	}

	private JSpinner getSpDays(ThemePark p) {
		if (spDays == null) {
			spDays = new JSpinner();
			spDays.setModel(new SpinnerNumberModel(p.getDays(), Integer.valueOf(1), null, Integer.valueOf(1)));
			spDays.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					p.setDays((int) spDays.getValue());
					getTxtTickets().setText(String.format("%.2f €", logic.getParkPrice()));
					getTxtTotal().setText(String.format("%.2f €", logic.getTotalPrice()));
					getTxtDiscounts().setText(String.format("%.2f €", logic.getDiscount()));
				}
			});
		}
		return spDays;
	}

	// ACCOMMODATION SECTION
	private JPanel getPnAccom(Accommodation a) {
		if (pnAccom == null) {
			pnAccom = new JPanel();
			pnAccom.setBackground(new Color(255, 255, 255));
			pnAccom.setBorder(new TitledBorder(
					new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
					a.getName(), TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 15),
					new Color(0, 0, 0)));
			pnAccom.setLayout(new BorderLayout(0, 10));
			pnAccom.add(getPnAccomNorth(a), BorderLayout.NORTH);
			pnAccom.add(getPnAccomCenter(a), BorderLayout.CENTER);

			pnAccom.setMaximumSize(pnAccom.getPreferredSize());
		}
		return pnAccom;
	}

	private JPanel getPnAccomNorth(Accommodation a) {
		if (pnAccomNorth == null) {
			pnAccomNorth = new JPanel();
			pnAccomNorth.setBackground(new Color(255, 255, 255));
			pnAccomNorth.setLayout(new BorderLayout(10, 5));
			pnAccomNorth.add(getPnAccomSelection(a), BorderLayout.CENTER);
			pnAccomNorth.add(getLblImageAccom(a), BorderLayout.WEST);
			pnAccomNorth.add(newLabel("Maximum People: " + a.getMaxOccupancy(), 15, true), BorderLayout.SOUTH);
		}
		return pnAccomNorth;
	}

	private JPanel getPnAccomCenter(Accommodation a) {
		if (pnAccomCenter == null) {
			pnAccomCenter = new JPanel();
			pnAccomCenter.setBackground(new Color(255, 255, 255));
			pnAccomCenter.setLayout(new BorderLayout(10, 0));
			pnAccomCenter.add(getBtnRemoveAccom(a), BorderLayout.EAST);
			pnAccomCenter.add(getTxtAddNoteAccom(a), BorderLayout.CENTER);
			pnAccomCenter.add(getChckbxAddNoteAccom(a), BorderLayout.NORTH);
		}
		return pnAccomCenter;
	}

	private JLabel getLblImageAccom(Accommodation a) {
		if (lblImageAccom == null) {
			lblImageAccom = new JLabel("");
			lblImageAccom.setVerticalTextPosition(SwingConstants.TOP);
			lblImageAccom.setHorizontalTextPosition(SwingConstants.CENTER);
			Image imgOriginal = new ImageIcon(getClass().getResource("/img/" + a.getPicture())).getImage();
			Image imgEscalada = imgOriginal.getScaledInstance((int) (120), (int) (100), Image.SCALE_SMOOTH);
			lblImageAccom.setIcon(new ImageIcon(imgEscalada));
		}
		return lblImageAccom;
	}

	private JButton getBtnRemoveAccom(Accommodation a) {
		if (btnRemoveAccom == null) {
			btnRemoveAccom = new JButton("Remove All");
			btnRemoveAccom.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnRemoveAccom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					logic.removeAccommodation();
					getTxtAccom().setText("0.00 €");
					getTxtTotal().setText(String.format("%.2f €", logic.getTotalPrice()));
					getTxtDiscounts().setText(String.format("%.2f €", logic.getDiscount()));
					updateSummary();
				}
			});
		}
		return btnRemoveAccom;
	}

	private JTextField getTxtAddNoteAccom(Accommodation a) {
		if (txtAddNoteAccom == null) {
			txtAddNoteAccom = new JTextField();
			txtAddNoteAccom.setText("Add Note...");
			txtAddNoteAccom.setEnabled(false);
			boolean hasNote = a.getNotes() != null && !a.getNotes().isBlank();
			txtAddNoteAccom.setEnabled(hasNote);
			txtAddNoteAccom.setText(hasNote ? a.getNotes() : "Add Note...");
			txtAddNoteAccom.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					a.setNotes(txtAddNoteAccom.getText());
				}
			});		
		}
		return txtAddNoteAccom;
	}

	private JCheckBox getChckbxAddNoteAccom(Accommodation a) {
		if (chckbxAddNoteAccom == null) {
			chckbxAddNoteAccom = new JCheckBox("Add Note");
			chckbxAddNoteAccom.setFont(new Font("Tahoma", Font.PLAIN, 15));
			chckbxAddNoteAccom.setBackground(new Color(255, 255, 255));
			boolean hasNote = a.getNotes() != null && !a.getNotes().isBlank();
			chckbxAddNoteAccom.setSelected(hasNote);
			chckbxAddNoteAccom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean isSelected = chckbxAddNoteAccom.isSelected();
					getTxtAddNoteAccom(a).setEnabled(isSelected);
					
					if (!isSelected) {
				        a.setNotes("");
				    }
				}
			});
		}
		return chckbxAddNoteAccom;
	}

	private JPanel getPnAccomSelection(Accommodation a) {
		if (pnAccomSelection == null) {
			pnAccomSelection = new JPanel();
			pnAccomSelection.setBackground(Color.WHITE);
			pnAccomSelection.setLayout(new GridLayout(0, 4, 5, 20));
			pnAccomSelection.add(newLabel("Date:", 15, false));
			pnAccomSelection.add(getSpAccomDate(a));
			pnAccomSelection.add(newLabel("People:", 15, false));
			pnAccomSelection.add(getSpPeople(a));
			pnAccomSelection.add(newLabel("Nights:", 15, false));
			pnAccomSelection.add(getSpNights(a));
			pnAccomSelection.add(newLabel("Rooms:", 15, false));
			pnAccomSelection.add(getSpRooms(a));
		}
		return pnAccomSelection;
	}

	private JSpinner getSpPeople(Accommodation a) {
		if (spPeople == null) {
			spPeople = new JSpinner();
			spPeople.setModel(new SpinnerNumberModel(a.getNumPeople(), Integer.valueOf(1), null, Integer.valueOf(1)));
			spPeople.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					a.setNumPeople((int) spPeople.getValue());
					getTxtAccom().setText(String.format("%.2f €", logic.getAccommodationPrice()));
					getTxtTotal().setText(String.format("%.2f €", logic.getTotalPrice()));
					getTxtDiscounts().setText(String.format("%.2f €", logic.getDiscount()));
				}
			});
			this.sv = new SpinnerValidation(getSpPeople(a), getSpRooms(a), a.getType());
			spPeople.addChangeListener(sv);
			getSpRooms(a).addChangeListener(sv);
		}
		return spPeople;
	}

	private JSpinner getSpRooms(Accommodation a) {
		if (spRooms == null) {
			spRooms = new JSpinner();
			spRooms.setModel(new SpinnerNumberModel(a.getNumAccomodations(), Integer.valueOf(1), null, Integer.valueOf(1)));
			spRooms.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					a.setNumAccomodations((int) spRooms.getValue());
					getTxtAccom().setText(String.format("%.2f €", logic.getAccommodationPrice()));
					getTxtTotal().setText(String.format("%.2f €", logic.getTotalPrice()));
					getTxtDiscounts().setText(String.format("%.2f €", logic.getDiscount()));
				}
			});
		}
		return spRooms;
	}

	private JSpinner getSpAccomDate(Accommodation a) {
		if (spAccomDate == null) {
			spAccomDate = new JSpinner();
			Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

			spAccomDate.setModel(new SpinnerDateModel(a.getStartDate(), today, null, Calendar.DAY_OF_MONTH));

			spAccomDate.setEditor(new JSpinner.DateEditor(spAccomDate, "dd/MM/yyyy"));
			spAccomDate.setBackground(new Color(255, 255, 255));
			spAccomDate.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					a.setStartDate((Date) spAccomDate.getValue());
				}
			});
		}
		return spAccomDate;
	}

	private JSpinner getSpNights(Accommodation a) {
		if (spNights == null) {

			spNights = new JSpinner();
			spNights.setModel(new SpinnerNumberModel(a.getNights(), Integer.valueOf(1), null, Integer.valueOf(1)));
			spNights.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					a.setNights((int) spNights.getValue());
					getTxtAccom().setText(String.format("%.2f €", logic.getAccommodationPrice()));
					getTxtTotal().setText(String.format("%.2f €", logic.getTotalPrice()));
					getTxtDiscounts().setText(String.format("%.2f €", logic.getDiscount()));
				}
			});
		}
		return spNights;
	}

	//
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel Reservation");
			btnCancel.setMnemonic('C');
			btnCancel.setToolTipText("Cancel the entire reservation");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cw.cancelReservation();
				}
			});
			btnCancel.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnCancel;
	}
	
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
			btnNext.setMnemonic('N');
			btnNext.setToolTipText("Go the registration screen");
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showPn2();
				}
			});
			btnNext.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnNext;
	}

	private JPanel getPnTotal() {
		if (pnTotal == null) {
			pnTotal = new JPanel();
			pnTotal.setBorder(new LineBorder(new Color(255, 255, 255), 5));
			pnTotal.setBackground(new Color(255, 255, 255));
			pnTotal.setLayout(new GridLayout(0, 2, 0, 0));
			pnTotal.add(newLabel("Tickets:", 15, false));
			pnTotal.add(getTxtTickets());
			pnTotal.add(newLabel("Accommodation:", 15, false));
			pnTotal.add(getTxtAccom());
			pnTotal.add(newLabel("Discounts:", 15, false));
			pnTotal.add(getTxtDiscounts());
			pnTotal.add(getSeparator1());
			pnTotal.add(getSeparator2());
			pnTotal.add(newLabel("Total:", 15, false));
			pnTotal.add(getTxtTotal());
		}
		return pnTotal;
	}

	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
			btnBack.setMnemonic('B');
			btnBack.setToolTipText("Go back to the catalog screen");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnBack.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnBack;
	}

	private JTextField getTxtTickets() {
		if (txtTickets == null) {
			txtTickets = new JTextField();
			txtTickets.setHorizontalAlignment(SwingConstants.RIGHT);
			txtTickets.setColumns(10);
			txtTickets.setText(String.format("%.2f €", logic.getParkPrice()));
		}
		return txtTickets;
	}

	private JTextField getTxtAccom() {
		if (txtAccom == null) {
			txtAccom = new JTextField();
			txtAccom.setHorizontalAlignment(SwingConstants.RIGHT);
			txtAccom.setColumns(10);
			txtAccom.setText(String.format("%.2f €", logic.getAccommodationPrice()));
		}
		return txtAccom;
	}

	private JTextField getTxtDiscounts() {
		if (txtDiscounts == null) {
			txtDiscounts = new JTextField();
			txtDiscounts.setHorizontalAlignment(SwingConstants.RIGHT);
			txtDiscounts.setColumns(10);
			txtDiscounts.setText(String.format("%.2f €", logic.getDiscount()));
		}
		return txtDiscounts;
	}

	private JTextField getTxtTotal() {
		if (txtTotal == null) {
			txtTotal = new JTextField();
			txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
			txtTotal.setColumns(10);
			txtTotal.setText(String.format("%.2f €", logic.getTotalPrice()));
		}
		return txtTotal;
	}

	private JSeparator getSeparator1() {
		if (separator1 == null) {
			separator1 = new JSeparator();
		}
		return separator1;
	}

	private JSeparator getSeparator2() {
		if (separator2 == null) {
			separator2 = new JSeparator();
		}
		return separator2;
	}
	private JPanel getPnContents() {
		if (pnContents == null) {
			pnContents = new JPanel();
			pnContents.setLayout(new CardLayout(0, 0));
			pnContents.add(getPn1(), "pn1");
			pnContents.add(getPn2(), "pn2");
			pnContents.add(getPn3(), "pn3");
		}
		return pnContents;
	}
	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setBackground(new Color(255, 255, 255));
			pn2.setLayout(null);
			pn2.add(getPnClientInfo());
		}
		return pn2;
	}
	private JPanel getPnEast() {
		if (pnEast == null) {
			pnEast = new JPanel();
			pnEast.setBackground(new Color(255, 255, 255));
			pnEast.setLayout(new BorderLayout(0, 0));
			pnEast.add(getPnTotal(), BorderLayout.SOUTH);
		}
		return pnEast;
	}
	private JPanel getPnClientInfo() {
		if (pnClientInfo == null) {
			pnClientInfo = new JPanel();
			pnClientInfo.setBounds(108, 85, 558, 200);
			pnClientInfo.setLayout(null);
			pnClientInfo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Client Information", 
					TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 15), new Color(0, 0, 0)));
			pnClientInfo.setBackground(Color.WHITE);
			pnClientInfo.add(getLblNameClient());
			pnClientInfo.add(getTxtNameClient());
			pnClientInfo.add(getLblSurnameClient());
			pnClientInfo.add(getTxtSurnameClient());
			pnClientInfo.add(getLblIdClient());
			pnClientInfo.add(getTxtIdClient());
		}
		return pnClientInfo;
	}
	private JLabel getLblNameClient() {
		if (lblNameClient == null) {
			lblNameClient = new JLabel();
			lblNameClient.setText("Name:");
			lblNameClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNameClient.setDisplayedMnemonic('N');
			lblNameClient.setBounds(30, 39, 132, 25);
		}
		return lblNameClient;
	}
	private JTextField getTxtNameClient() {
		if (txtNameClient == null) {
			txtNameClient = new JTextField();
			txtNameClient.setFont(new Font("Arial", Font.PLAIN, 14));
			txtNameClient.setBounds(193, 38, 330, 25);
		}
		return txtNameClient;
	}
	private JLabel getLblSurnameClient() {
		if (lblSurnameClient == null) {
			lblSurnameClient = new JLabel();
			lblSurnameClient.setText("Surname:");
			lblSurnameClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSurnameClient.setDisplayedMnemonic('S');
			lblSurnameClient.setBounds(30, 87, 132, 25);
		}
		return lblSurnameClient;
	}
	private JTextField getTxtSurnameClient() {
		if (txtSurnameClient == null) {
			txtSurnameClient = new JTextField();
			txtSurnameClient.setFont(new Font("Arial", Font.PLAIN, 14));
			txtSurnameClient.setBounds(193, 88, 330, 25);
		}
		return txtSurnameClient;
	}
	private JLabel getLblIdClient() {
		if (lblIdClient == null) {
			lblIdClient = new JLabel();
			lblIdClient.setText("ID:");
			lblIdClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblIdClient.setDisplayedMnemonic('I');
			lblIdClient.setBounds(30, 138, 132, 25);
		}
		return lblIdClient;
	}
	private JTextField getTxtIdClient() {
		if (txtIdClient == null) {
			txtIdClient = new JTextField();
			txtIdClient.setFont(new Font("Arial", Font.PLAIN, 14));
			txtIdClient.setBounds(193, 139, 330, 25);
		}
		return txtIdClient;
	}
	
	//Card Layout
	private void showPn1() {
		((CardLayout) getPnBtn().getLayout()).show(pnBtn, "pnBtn1");
		((CardLayout) getPnContents().getLayout()).show(pnContents, "pn1");
	}

	private void showPn2() {
		((CardLayout) getPnBtn().getLayout()).show(pnBtn, "pnBtn2");
		((CardLayout) getPnContents().getLayout()).show(pnContents, "pn2");
	}
	
	private void showPn3() {
		if (checkFields()) { 
			Client c = new Client(
		            getTxtIdClient().getText().trim().toUpperCase(),
		            getTxtNameClient().getText().trim(),
		            getTxtSurnameClient().getText().trim());
		    logic.setClient(c);
		        
			getTxtSummary().setText(logic.getSummary());
			
			((CardLayout) getPnBtn().getLayout()).show(pnBtn, "pnBtn3");
			((CardLayout) getPnContents().getLayout()).show(pnContents, "pn3");
		}
	}
	
	public boolean checkFields() {
		if (isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error: Some fields are empty");
			return false;
		} else {
			if (isWrong()) {
				JOptionPane.showMessageDialog(null,
					"Error: Invalid ID format. \nPlease enter 8 digits followed by a letter");
				return false;
			}
		}
		return true;
	}
	
	private boolean isEmpty() {
		return (txtNameClient.getText().equals("")
				|| txtSurnameClient.getText().equals("") || txtIdClient.getText().equals(""));
	}
	
	private boolean isWrong() {
		return !txtIdClient.getText().matches("^\\d{8}[A-Za-z]$");
	}

	private JPanel getPnBtn1() {
		if (pnBtn1 == null) {
			pnBtn1 = new JPanel();
			pnBtn1.setLayout(new GridLayout(0, 3, 0, 0));
			pnBtn1.add(getBtnCancel());
			pnBtn1.add(getBtnBack());
			pnBtn1.add(getBtnNext());
		}
		return pnBtn1;
	}
	private JPanel getPnBtn2() {
		if (pnBtn2 == null) {
			pnBtn2 = new JPanel();
			pnBtn2.setLayout(new GridLayout(0, 2, 0, 0));
			pnBtn2.add(getBtnBack2());
			pnBtn2.add(getBtnNext2());
		}
		return pnBtn2;
	}
	private JButton getBtnBack2() {
		if (btnBack2 == null) {
			btnBack2 = new JButton("Back");
			btnBack2.setMnemonic('B');
			btnBack2.setToolTipText("Go back to the reservation details");
			btnBack2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showPn1();
				}
			});
			btnBack2.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnBack2;
	}
	private JButton getBtnNext2() {
		if (btnNext2 == null) {
			btnNext2 = new JButton("Next");
			btnNext2.setMnemonic('N');
			btnNext2.setToolTipText("Go to the reservation summary");
			btnNext2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showPn3();
				}
			});
			btnNext2.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnNext2;
	}
	private JPanel getPn3() {
		if (pn3 == null) {
			pn3 = new JPanel();
			pn3.setLayout(new BorderLayout(0, 0));
			pn3.add(getTxtSummary());
		}
		return pn3;
	}
	private JTextArea getTxtSummary() {
		if (txtSummary == null) {
			txtSummary = new JTextArea();
			txtSummary.setEditable(false);
		}
		return txtSummary;
	}
	private JPanel getPnBtn3() {
		if (pnBtn3 == null) {
			pnBtn3 = new JPanel();
			pnBtn3.setLayout(new GridLayout(0, 3, 0, 0));
			pnBtn3.add(getBtnCancel2());
			pnBtn3.add(getBtnBack3());
			pnBtn3.add(getBtnFinish());
		}
		return pnBtn3;
	}
	private JButton getBtnBack3() {
		if (btnBack3 == null) {
			btnBack3 = new JButton("Back");
			btnBack3.setMnemonic('B');
			btnBack3.setToolTipText("Go back to the registration screen");
			btnBack3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showPn2();
				}
			});
			btnBack3.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnBack3;
	}
	private JButton getBtnFinish() {
		if (btnFinish == null) {
			btnFinish = new JButton("Finish Reservation");
			btnFinish.setMnemonic('F');
			btnFinish.setToolTipText("Go to the finish screen and play a game");
			btnFinish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showGameWindow(cw);
				}
			});
			btnFinish.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnFinish;
	}
	
	private void showGameWindow(CatalogWindow mw) {
		gw = new GameWindow(mw);
		gw.setLocationRelativeTo(this);
		gw.setModal(true);
		gw.setVisible(true);
	}

	private JButton getBtnCancel2() {
		if (btnCancel2 == null) {
			btnCancel2 = new JButton("Cancel Reservation");
			btnCancel2.setMnemonic('C');
			btnCancel2.setToolTipText("Cancel the entire reservation");
			btnCancel2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cw.cancelReservation();
				}
			});
			btnCancel2.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnCancel2;
	}

	public GameWindow getGw() {
		return gw;
	}
	
	private void loadHelp() {
		URL hsURL;
		HelpSet hs;
		try {
			File fichero = new File("help/Help.hs");
			hsURL = fichero.toURI().toURL();
			hs = new HelpSet(null, hsURL);
		} catch (Exception e) {
			System.out.println("Help not found!");
			return;
		}
		HelpBroker hb = hs.createHelpBroker();
		hb.enableHelpKey(getRootPane(), "reservationSummary", hs);
	}
}
