package app.ui;

import java.awt.Color;
import java.awt.Component;
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
import java.util.Random;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GameWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private final static int NUM_OF_CELLS = 5;
	private final int winningIndex;

	private JLabel lblTitle;
	private JPanel pnBoard;
	private JLabel lblInfo;
	private JLabel lblGift;
	private JButton btnFinish;
	private RevealCell rc = null;
	private CatalogWindow cw;

	/**
	 * Create the frame.
	 */
	public GameWindow(CatalogWindow cw) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cw.cancelReservation();
			}
		});
		this.winningIndex = new Random().nextInt(5);
		this.rc = new RevealCell();
		this.cw = cw;

		setIconImage(Toolkit.getDefaultToolkit().getImage(CatalogWindow.class.getResource("/img/logo.png")));
		setTitle("TravelFun: Game Window");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 810, 566);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblTitle());
		contentPane.add(getPnBoard());
		contentPane.add(getLblInfo());
		contentPane.add(getLblGift());
		contentPane.add(getBtnFinish());

		setResizable(false);
		prepareBoard();

		loadHelp();
	}

	private void prepareBoard() {
		getPnBoard().removeAll();

		for (int i = 0; i < NUM_OF_CELLS; i++) {
			getPnBoard().add(newCell(i));
		}

		getPnBoard().revalidate();
		getPnBoard().repaint();
	}

	class RevealCell implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			button.setEnabled(false);

			for (Component c : getPnBoard().getComponents()) {
				c.setEnabled(false);
			}

			if (winningIndex == Integer.valueOf(e.getActionCommand())) {
				cw.getLogic().setGameWinned(true);
				getLblGift().setText("Congratulations! You won a voucher for your next booking!");
			} else {
				getLblGift().setText("You didn't get the voucher. Try again next time!");
			}

			getLblGift().setVisible(true);
			getBtnFinish().setVisible(true);
		}
	}

	private JButton newCell(int i) {
		final JButton cell = new JButton("");

		cell.setActionCommand(String.valueOf(i));
		cell.setDisabledIcon(loadResizedIcon("/img/lost.png", 100, 100));
		if (i == winningIndex)
			cell.setDisabledIcon(loadResizedIcon("/img/voucher.png", 100, 100));
		cell.addActionListener(rc);

		return cell;
	}

	private ImageIcon loadResizedIcon(String imagePath, int size1, int size2) {
		Image imgOriginal = new ImageIcon(getClass().getResource(imagePath)).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance((int) (size1), (int) (size2), Image.SCALE_SMOOTH);
		return new ImageIcon(imgEscalada);
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("Play a Game!");
			lblTitle.setBounds(5, 5, 781, 59);
			lblTitle.setForeground(new Color(39, 109, 155));
			lblTitle.setFont(new Font("Bauhaus 93", Font.BOLD, 40));
		}
		return lblTitle;
	}

	private JPanel getPnBoard() {
		if (pnBoard == null) {
			pnBoard = new JPanel();
			pnBoard.setBounds(103, 153, 579, 115);
			pnBoard.setLayout(new GridLayout(1, 0, 0, 0));
		}
		return pnBoard;
	}

	private JLabel getLblInfo() {
		if (lblInfo == null) {
			lblInfo = new JLabel("Pick ONE cell. Only one contains a 100€ voucher!");
			lblInfo.setFocusable(false);
			lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
			lblInfo.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblInfo.setBounds(142, 95, 500, 59);
		}
		return lblInfo;
	}

	private JLabel getLblGift() {
		if (lblGift == null) {
			lblGift = new JLabel("");
			lblGift.setVisible(false);
			lblGift.setHorizontalAlignment(SwingConstants.CENTER);
			lblGift.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblGift.setBounds(106, 312, 576, 53);
		}
		return lblGift;
	}

	private JButton getBtnFinish() {
		if (btnFinish == null) {
			btnFinish = new JButton("Finish and Pay");
			btnFinish.setMnemonic('F');
			btnFinish.setToolTipText("Finish the reservation and go to the pay screen");
			btnFinish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cw.getLogic().saveReservation();
					cw.showStartScreen();
				}
			});
			btnFinish.setVisible(false);
			btnFinish.setFont(new Font("Tahoma", Font.BOLD, 20));
			btnFinish.setBounds(215, 374, 360, 68);
		}
		return btnFinish;
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

		hb.enableHelpKey(getRootPane(), "playGame", hs);
	}
}
