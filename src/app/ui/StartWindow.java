package app.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.service.Logic;

public class StartWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblName;
	private JButton btnEnter;
	private JMenuBar menuBar;
	private JMenu mnHelp;
	private JMenuItem mntmContent;
	private JMenuItem mntmAbout;
	private JSeparator separator;

	private Logic logic;
	private JDialog catalogScreen;

	/**
	 * Constructs the starting screen frame. Initializes the UI components, loads
	 * the default language, and sets up the help system.
	 * 
	 * @param logic
	 * 
	 * @param logic the Logic instance responsible for handling business logic
	 */
	public StartWindow(Logic logic) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (checkExit()) {
					System.exit(0);
				}
			}
		});

		this.logic = logic;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartWindow.class.getResource("/img/logo.png")));
		setTitle("TravelFun");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 743, 489);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblName());
		contentPane.add(getBtnEnter());

		setLocationRelativeTo(null);
		loadHelp();
	}

	private boolean checkExit() {
		return JOptionPane.showConfirmDialog(this, "Are you sure you want to exit the aplication?", "Choose an option.",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("TravelFun");
			lblName.setIcon(new ImageIcon(StartWindow.class.getResource("/img/logo.png")));
			lblName.setForeground(new Color(0x0C4160));
			lblName.setFont(new Font("Bauhaus 93", Font.BOLD, 65));
			lblName.setBounds(39, 32, 637, 264);
		}
		return lblName;
	}

	private JButton getBtnEnter() {
		if (btnEnter == null) {
			btnEnter = new JButton("Enter");
			btnEnter.setToolTipText("Enter to the app");
			btnEnter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showCatalogScreen();
				}
			});
			btnEnter.setMnemonic('E');
			btnEnter.setForeground(new Color(0x0C4160));
			btnEnter.setBorder(new LineBorder(new Color(128, 128, 128), 5, true));
			btnEnter.setBackground(Color.LIGHT_GRAY);
			btnEnter.setFont(new Font("Rockwell", Font.BOLD, 40));
			btnEnter.setBounds(218, 318, 275, 76);
		}
		return btnEnter;
	}

	private void showCatalogScreen() {
		catalogScreen = new CatalogWindow(logic);
		catalogScreen.setLocationRelativeTo(this);
		catalogScreen.setModal(true);
		catalogScreen.setVisible(true);
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}

	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Help");
			mnHelp.setMnemonic('H');
			mnHelp.add(getMntmContent());
			mnHelp.add(getSeparator());
			mnHelp.add(getMntmAbout());
		}
		return mnHelp;
	}

	private JMenuItem getMntmContent() {
		if (mntmContent == null) {
			mntmContent = new JMenuItem("Content");
			mntmContent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			mntmContent.setMnemonic('C');
		}
		return mntmContent;
	}

	private JMenuItem getMntmAbout() {
		if (mntmAbout == null) {
			mntmAbout = new JMenuItem("About");
			mntmAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(rootPane,
							"TravelFun\nVersion 1.0.0\nThis app allows the user to view the list of parks and "
									+ "accommodations and make a reservation.\nAuthor: Ana Calleja Ramón",
							"TravelFun", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			mntmAbout.setMnemonic('A');
		}
		return mntmAbout;
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
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
		hb.enableHelpKey(getRootPane(), "introduction", hs);
		hb.enableHelpOnButton(mntmContent, "introduction", hs);
	}
}
