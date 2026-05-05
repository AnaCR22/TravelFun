package app.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.model.Accommodation;
import app.model.ThemePark;
import app.model.TypeOfAccommodation;
import app.service.Logic;

public class CatalogWindow extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel pnLogo;
    private JTabbedPane pnContents;
    private JPanel pnParks;
    private JPanel pnAccom;
    private JPanel pnPrice;
    private JTextField textPrice;
    private JLabel lblPrice;
    private JPanel pnTitle;
    private JPanel pnButtons;
    private JButton btnCancel;
    private JLabel lbl1;
    private JButton btnSee;
    private JLabel lbl2;
    private JPanel pnAccomList;
    private JPanel pnFilter;
    private JButton btnAll;
    private JScrollPane scAccom;
    private JScrollPane scParks;
    private JPanel pnParksList;
    private JToggleButton tglbtnApartment;
    private JToggleButton tglbtnHotel;
    private JToggleButton tglbtnApartHotel;
    private Logic logic;
    
	private ParkWindow pw;
	private AccommodationWindow aw;
	private ReservationWindow rw;
	
	private ButtonChooseAction aB;
	private JButton btnRestart;
	private JLabel lblOffer;
	
    public CatalogWindow(Logic logic) {
    	addWindowListener(new WindowAdapter() {
    		@Override
    		public void windowClosing(WindowEvent e) {
    			cancelReservation();
    		}
    	});
    	this.logic = logic;
		aB = new ButtonChooseAction();
    	setIconImage(Toolkit.getDefaultToolkit().getImage(CatalogWindow.class.getResource("/img/logo.png")));
    	setTitle("TravelFun: Catalog Window");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 810, 566);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnLogo(), BorderLayout.NORTH);
		contentPane.add(getPnContents(), BorderLayout.CENTER);
		contentPane.add(getPnButtons(), BorderLayout.SOUTH);
		contentPane.setBackground(Color.WHITE);
		this.setMinimumSize(new Dimension(700, 500));
		
		loadHelp();
    }

    public Logic getLogic(){
		return logic;
	}
    
    public void cancelReservation() {
		int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this reservation?", 
				"Choose an option. This cannot be undone", JOptionPane.YES_NO_OPTION);
		
		if(answer == JOptionPane.YES_OPTION) {
			initialize();
			dispose();
		}
    }
    
	private void initialize() {
		if (rw != null) {
			if(rw.getGw() != null) {
				rw.getGw().dispose();
			}
			rw.dispose();
		}
		
		logic.initialize();
		getTxtPrice().setText("0,00 €");
		getBtnAll().doClick();
		getBtnSee().setEnabled(false);
	}
	

	public void showStartScreen() {
		initialize();
		dispose();
	}

	
	private JPanel getPnLogo() {
		if (pnLogo == null) {
			pnLogo = new JPanel();
			pnLogo.setForeground(new Color(255, 255, 255));
			pnLogo.setBackground(new Color(255, 255, 255));
			pnLogo.setLayout(new BorderLayout(0, 0));
			pnLogo.add(getPnPrice(), BorderLayout.EAST);
			pnLogo.add(getPnTitle(), BorderLayout.CENTER);
		}
		return pnLogo;
	}
	
	private void adaptImageLbl(JLabel label, String imagePath, int size1, int size2) {
		 Image imgOriginal = new ImageIcon(getClass().getResource(imagePath)).getImage(); 
		 Image imgEscalada = imgOriginal.getScaledInstance((int) (size1), (int) (size2),Image.SCALE_SMOOTH);
		 label.setIcon(new ImageIcon(imgEscalada));
	}

	private JTabbedPane getPnContents() {
		if (pnContents == null) {
			pnContents = new JTabbedPane(JTabbedPane.TOP);
			pnContents.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {				    
				    int index = pnContents.getSelectedIndex();
				    String title = pnContents.getTitleAt(index);
				    	
					((CardLayout) getPnTitle().getLayout()).show(getPnTitle(), title);
				}
			});
			pnContents.setBackground(new Color(255, 255, 255));
			pnContents.setFont(new Font("Tahoma", Font.BOLD, 15));
			pnContents.addTab("Parks", null, getPnParks(), null);
			pnContents.setDisplayedMnemonicIndexAt(0, 0);
			pnContents.addTab("Accomodation", null, getPnAccom(), null);
			pnContents.setDisplayedMnemonicIndexAt(1, 0);
		}
		return pnContents;
	}
	
	private JPanel getPnParks() {
		if (pnParks == null) {
			pnParks = new JPanel();
			pnParks.setBackground(new Color(255, 255, 255));
			pnParks.setLayout(new BorderLayout(0, 0));
			pnParks.add(getScParks(), BorderLayout.CENTER);
		}
		return pnParks;
	}
	
	private JPanel getPnAccom() {
		if (pnAccom == null) {
			pnAccom = new JPanel();
			pnAccom.setBackground(new Color(255, 255, 255));
			pnAccom.setLayout(new BorderLayout(0, 0));
			pnAccom.add(getPnFilter(), BorderLayout.WEST);
			pnAccom.add(getScAccom(), BorderLayout.CENTER);
		}
		return pnAccom;
	}
	
	private JPanel getPnPrice() {
		if (pnPrice == null) {
			pnPrice = new JPanel();
			pnPrice.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnPrice.setLayout(new BorderLayout(0, 0));
			pnPrice.add(getTxtPrice());
			pnPrice.add(getLblPrice(), BorderLayout.WEST);
		}
		return pnPrice;
	}
	
	private JTextField getTxtPrice() {
		if (textPrice == null) {
			textPrice = new JTextField();
			textPrice.setBackground(new Color(255, 255, 255));
			textPrice.setToolTipText((String) null);
			textPrice.setText("0,00 €");
			textPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
			textPrice.setEditable(false);
			textPrice.setColumns(8);
			textPrice.setFocusable(false);
		}
		return textPrice;
	}
	
	private JLabel getLblPrice() {
		if (lblPrice == null) {
			lblPrice = new JLabel("Total Price: ");
			lblPrice.setHorizontalAlignment(SwingConstants.TRAILING);
			lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblPrice;
	}
	
	private JPanel getPnTitle() {
		if (pnTitle == null) {
			pnTitle = new JPanel();
			pnTitle.setBorder(null);
			pnTitle.setBackground(new Color(255, 255, 255));
			pnTitle.setLayout(new CardLayout(0, 0));
			pnTitle.add(getLbl1(), "Parks");
			pnTitle.add(getLbl2(), "Accomodation");
		}
		return pnTitle;
	}
	
	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.setLayout(new BorderLayout(0, 0));
			pnButtons.add(getBtnCancel(), BorderLayout.WEST);
			pnButtons.add(getBtnSee(), BorderLayout.EAST);
			pnButtons.add(getBtnRestart(), BorderLayout.CENTER);
		}
		return pnButtons;
	}
	
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel Reservation");
			btnCancel.setMnemonic('C');
			btnCancel.setToolTipText("Cancel the entire reservation");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancelReservation();
				}
			});
			btnCancel.setFont(new Font("Tahoma", Font.BOLD, 20));

		}
		return btnCancel;
	}
	
	private JLabel getLbl1() {
		if (lbl1 == null) {
			lbl1 = new JLabel(" Theme Parks");
			lbl1.setFont(new Font("Bauhaus 93", Font.BOLD, 40));
			lbl1.setForeground(new Color(0x276D9B));
		}
		return lbl1;
	}
	
	private JButton getBtnSee() {
		if (btnSee == null) {
			btnSee = new JButton("See Reservation");
			btnSee.setToolTipText("Show the details of the reservation");
			btnSee.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showReservationWindow();
					getTxtPrice().setText(String.format("%.2f €", logic.getTotalPrice()));
					
					if(logic.getAccommodation() == null && logic.getPark() == null) {
						btnSee.setEnabled(false);
					}
				}
			});
			btnSee.setEnabled(false);
			btnSee.setMnemonic('S');
			btnSee.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnSee;
	}
	
	private void showReservationWindow() {
		rw = new ReservationWindow(this);
		rw.setLocationRelativeTo(this);
		rw.setModal(true);
		rw.setVisible(true);
	}

	private JLabel getLbl2() {
		if (lbl2 == null) {
			lbl2 = new JLabel(" Accomodations");
			lbl2.setForeground(new Color(0x276D9B));
			lbl2.setFont(new Font("Bauhaus 93", Font.BOLD, 40));
		}
		return lbl2;
	}
	
	private JScrollPane getScAccom() {
		if (scAccom == null) {
			scAccom = new JScrollPane();
			scAccom.setViewportView(getPnAccomList());
		}
		return scAccom;
	}
	
	private JPanel getPnAccomList() {
		if (pnAccomList == null) {
			pnAccomList = new JPanel();
			pnAccomList.setBackground(new Color(255, 255, 255));
			pnAccomList.setLayout(new GridLayout(0, 1, 0, 0));
			createAccommodationPanel(logic.getAccommodations());
		}
		return pnAccomList;
	}
	
	private void createAccommodationPanel(List<Accommodation> accommodations) {
		pnAccomList.removeAll();
		
		int i = 0;
		for (Accommodation a : accommodations) {
			pnAccomList.add(newPanel(i, a, "/img/" + a.getPicture()));
			i++;
		}
		
		pnAccomList.revalidate();
		pnAccomList.repaint();
	}
	
	private JPanel newPanel(Integer position, Accommodation a, String path) {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout(0, 0));
		panel.setBorder(new LineBorder(new Color(255, 255, 255), 6));
	    panel.add(newLblImg(path, "", 175, 175), BorderLayout.WEST);
	    panel.add(newPanelAccom(a),BorderLayout.CENTER);
	    
		return panel;
	}

	private JLabel newLblImg(String path, String text, int size1, int size2) {
		JLabel lblImage = new JLabel(text);
		lblImage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblImage.setHorizontalTextPosition(SwingConstants.CENTER);
		lblImage.setVerticalTextPosition(SwingConstants.TOP);
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		adaptImageLbl(lblImage, path, size1, size2);	
		return lblImage;
	}
	
	private JPanel newPanelAccom(Accommodation a) {
		JPanel pnDetails = new JPanel();
		pnDetails.setBorder(new MatteBorder(0, 3, 0, 0, (Color) new Color(255, 255, 255)));
		pnDetails.setLayout(new BorderLayout(0, 0));
		pnDetails.add(newPnChoose(a, "A"), BorderLayout.SOUTH);
		pnDetails.add(newPnAccomInfo(a), BorderLayout.CENTER);
		
	    JLabel lblName = newLabel(a.getName(), 15, true);
		pnDetails.add(lblName, BorderLayout.NORTH);
	
		return pnDetails;
	}
	
	private JPanel newPnChoose(Object choosenObj, String code) {
		JPanel pnChoose = new JPanel();
		pnChoose.setBackground(new Color(192,192,192));
		pnChoose.setLayout(new BorderLayout(0, 0));
		pnChoose.add(newBtnChoose(choosenObj, code), BorderLayout.EAST);
		
		return pnChoose;
	}
	
	private JButton newBtnChoose(Object choosenObj, String code) {
		JButton	btnChoose = new JButton("Choose");
		btnChoose.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnChoose.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnChoose.setHorizontalAlignment(SwingConstants.RIGHT);
		btnChoose.putClientProperty("object", choosenObj);
		btnChoose.setActionCommand(code);
		btnChoose.addActionListener(aB);

		return btnChoose;
	}
	
	class ButtonChooseAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton bt = (JButton) e.getSource();
			Object object = bt.getClientProperty("object");
			
			if(bt.getActionCommand().equals("A")) {
				Accommodation a = (Accommodation) object;
				showAccommodationWindow(a);
			} 
			
			if(bt.getActionCommand().equals("P")) {
				ThemePark p = (ThemePark) object;
				showParkWindow(p);
			}
			
			getTxtPrice().setText(String.format("%.2f €", logic.getTotalPrice()));
			
			if(logic.getAccommodation() != null || logic.getPark() != null) {
				getBtnSee().setEnabled(true);
			}
		}
	}
	
	public void showParkWindow(ThemePark p) {
		Accommodation a = logic.getAccommodation();
		if(a != null) {
			if(!a.getParkCode().equals(p.getCode())){
				JOptionPane.showMessageDialog(this, "Accommodation must be associated at the same Theme Park "
						+ "\nPlease selecte another option" + "\nAccommodation Park Selected: " + a.getParkAssociated().getName(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
				
		}
		pw = new ParkWindow(logic, p);
		pw.setLocationRelativeTo(this);
		pw.setModal(true);
		pw.setVisible(true);
	}
	
	public void showAccommodationWindow(Accommodation a) {
		ThemePark p = logic.getPark();
		if(p != null&& !a.getParkCode().equals(p.getCode())) {
				JOptionPane.showMessageDialog(this, "Accommodation must be associated at the same Theme Park "
						+ "\nPlease selecte another option" + "\nPark Selected: " + p.getName(), "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
		}
		
		if(logic.getAccommodation() != null) {
			int answer = JOptionPane.showConfirmDialog(this, "An accommodation has already been selected. "
					+ "\nOnly one accommodation per reservation is allowed."
					+ "\nAre you sure you want to replace the current accommodation?", "Change Accommodation", 
					JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			
			if(answer != JOptionPane.YES_OPTION) {
				return;
			}
		}
		
		aw = new AccommodationWindow(logic, a);
		aw.setLocationRelativeTo(this);
		aw.setModal(true);
		aw.setVisible(true);
	}

	private JPanel newPnAccomInfo(Accommodation a) {
		JPanel pnInfo = new JPanel();
		pnInfo.setLayout(new GridLayout(0, 2, 0, 0));
		
	    pnInfo.add(newLabel("Type: ", 15, false));
	    pnInfo.add(newTextField(a.getType().toString(), false, 15));
	    
	    pnInfo.add(newLabel("Category: ", 15, false));
	    pnInfo.add(newTextField(a.getStars(), false, 15));

	    pnInfo.add(newLabel("Price: ", 15, false));
	    pnInfo.add(newTextField(String.format("%.2f € %s", a.getPrice(), a.getPriceDetails()), false, 15));
		
	    pnInfo.add(newLabel("Theme Park: ", 15, false));
	    pnInfo.add(newTextField(a.getParkAssociated().getName(), false, 15));
		return pnInfo;
	}
	

	private JLabel newLabel(String text, int fontSize, boolean bold) {
	    JLabel lbl = new JLabel(text);
	    lbl.setFont(new Font("Tahoma", bold ? Font.BOLD : Font.PLAIN, fontSize));
	    return lbl;
	}
	
	private JTextField newTextField(String text, boolean editable, int columns) {
	    JTextField txt = new JTextField(text);
	    txt.setEditable(editable);
	    txt.setColumns(columns);
	    return txt;
	}

	private JPanel getPnFilter() {
		if (pnFilter == null) {
			pnFilter = new JPanel();
			pnFilter.setBackground(new Color(255, 255, 255));
			pnFilter.setLayout(new GridLayout(0, 1, 0, 0));
			pnFilter.add(getTglbtnApartment());
			pnFilter.add(getTglbtnApartHotel());
			pnFilter.add(getTglbtnHotel());
			pnFilter.add(getBtnAll());
		}
		return pnFilter;
	}
	
	private JButton getBtnAll() {
		if (btnAll == null) {
			btnAll = new JButton("All");
			btnAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(Component c : getPnFilter().getComponents()) {
						if(c instanceof JToggleButton) {
							JToggleButton button = (JToggleButton) c;
							button.setSelected(false);
						}
					}
					updateAccommodationList();
				}
			});
			btnAll.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return btnAll;
	}
	
	private JToggleButton getTglbtnApartment() {
		if (tglbtnApartment == null) {
			tglbtnApartment = new JToggleButton("Apartment");
			tglbtnApartment.setActionCommand("AP");
			tglbtnApartment.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateAccommodationList();
				}
			});
			tglbtnApartment.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return tglbtnApartment;
	}
	
	private void updateAccommodationList() {
		List<TypeOfAccommodation> types = checkFilter();
		List<Accommodation> filteredList = new ArrayList<>();

		if(types.isEmpty()) {
			filteredList = logic.getAccommodations();
		} else {
			filteredList = logic.getAccommodationsByTypes(types);
		}

		createAccommodationPanel(filteredList);
	}

	private List<TypeOfAccommodation> checkFilter() {
		List<TypeOfAccommodation> types = new ArrayList<>();
		
		for(Component c : getPnFilter().getComponents()) {
			if(c instanceof JToggleButton) {
				JToggleButton button = (JToggleButton) c;
				if(button.isSelected()) {
					types.add(TypeOfAccommodation.valueOf(button.getActionCommand()));
				}
			}
		}
	
		return types;
	}
	
	private JToggleButton getTglbtnHotel() {
		if (tglbtnHotel == null) {
			tglbtnHotel = new JToggleButton("Hotel");
			tglbtnHotel.setActionCommand("HO");
			tglbtnHotel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateAccommodationList();
				}
			});
			tglbtnHotel.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return tglbtnHotel;
	}
	
	private JToggleButton getTglbtnApartHotel() {
		if (tglbtnApartHotel == null) {
			tglbtnApartHotel = new JToggleButton("ApartHotel");
			tglbtnApartHotel.setActionCommand("AH");
			tglbtnApartHotel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateAccommodationList();
				}
			});
			tglbtnApartHotel.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return tglbtnApartHotel;
	}
	
	private JScrollPane getScParks() {
		if (scParks == null) {
	        JPanel content = new JPanel(new BorderLayout());
	        content.setBackground(Color.WHITE);
			content.add(getLblOffer(), BorderLayout.NORTH);
	        content.add(getPnParksList(), BorderLayout.CENTER);

	        scParks = new JScrollPane(content);
		}
		return scParks;
	}
	
	private JLabel getLblOffer() {
		if(lblOffer == null) {
	        ThemePark offer = logic.getParkInOffer();

			lblOffer = new JLabel("Park with 15% of discount on final price!");
			lblOffer.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblOffer.setHorizontalTextPosition(SwingConstants.CENTER);
			lblOffer.setVerticalTextPosition(SwingConstants.BOTTOM);
			lblOffer.setHorizontalAlignment(SwingConstants.CENTER);
			lblOffer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x276D9B), 3),
			        "Special Offer: " + offer.getName(),TitledBorder.LEFT,TitledBorder.TOP,
			        new Font("Tahoma", Font.BOLD, 15),
			        new Color(0x276D9B)));
			adaptImageLbl(lblOffer, "/img/"+logic.getParkInOffer().getPicture(), 250, 200);	
		}
		return lblOffer;
	}
	
	private JPanel getPnParksList() {
		if (pnParksList == null) {
			pnParksList = new JPanel();
			pnParksList.setBackground(new Color(255, 255, 255));
			pnParksList.setLayout(new GridLayout(0, 3, 0, 0));
			createParksPanel();
		}
		return pnParksList;
	}
	
	private void createParksPanel() {
		pnParksList.removeAll();
		
		int i = 0;
		for (ThemePark p : logic.getParks()) {
			pnParksList.add(newPanelPark(i, p, "/img/" + p.getPicture()));
			i++;
		}
		
		pnParksList.revalidate();
		pnParksList.repaint();
	}
	
	
	private JPanel newPanelPark(Integer position, ThemePark p, String path) {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout(0, 0));
		panel.setBorder(new LineBorder(new Color(255, 255, 255), 6));
		JLabel labelImg = newLblImg(path, p.getName(), 240, 180);
		panel.add(labelImg, BorderLayout.NORTH);
		panel.add(newPnParkInfo(p), BorderLayout.CENTER);
		panel.add(newPnChoose(p, "P"), BorderLayout.SOUTH);

		return panel;
	}

	private JPanel newPnParkInfo(ThemePark p) {
		JPanel pnParkInfo = new JPanel();
		pnParkInfo.setBorder(new MatteBorder(4, 0, 0, 0, (Color) new Color(255, 255, 255)));

		pnParkInfo.setLayout(new GridLayout(0, 2, 0, 0));
		
		pnParkInfo.add(newLabel("Adult Price: ", 15, false));
		pnParkInfo.add(newTextField(String.format("%.2f €", p.getAdultPrice()), false, 0));

		pnParkInfo.add(newLabel("Child Price: ", 15, false));
		pnParkInfo.add(newTextField(String.format("%.2f €", p.getChildPrice()), false, 0));
	
		return pnParkInfo;
	}


	private JButton getBtnRestart() {
		if (btnRestart == null) {
			btnRestart = new JButton("Restart Reservation");
			btnRestart.setMnemonic('R');
			btnRestart.setToolTipText("Remove all the selected items from the actual reservation");
			btnRestart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					initialize();
				}
			});
			btnRestart.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return btnRestart;
	}
	
	private void loadHelp() {
		URL hsURL;
		HelpSet hs;
		try {
			File fichero = new File("help/Help.hs");
			hsURL = fichero.toURI().toURL();
			hs = new HelpSet(null, hsURL);
		}catch (Exception e) {
			System.out.println("Help not found!");
			return;
		}

		HelpBroker hb = hs.createHelpBroker();
		hb.enableHelpKey(getRootPane(), "browseCatalog", hs);
	}
}
