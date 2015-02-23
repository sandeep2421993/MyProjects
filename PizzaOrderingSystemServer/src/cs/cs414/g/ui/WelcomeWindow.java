package cs.cs414.g.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cs.cs414.g.domain.Menu;
import cs.cs414.g.domain.PhoneOrder;
import cs.cs414.g.util.LoginUtil;
import cs.cs414.g.util.ViewCustomerOrders;

public class WelcomeWindow extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labelWelcomeToThe;
	private JPanel panel;
	private JButton buttonEmployee;
	private JButton buttonManager;
	private JButton buttonChef;
	private JButton buttonCustomer;
	private JButton buttonExit;
	private JLabel labelPleasePickAn;

	PhoneOperatorWindow phoneOperatorWindow = null;
	ManagerWindow mw ;


	/**
	 * Create the frame.
	 */
	public WelcomeWindow(final PhoneOrder phoneOrder, Menu menu) {
		mw = new ManagerWindow(menu);
		phoneOperatorWindow = new PhoneOperatorWindow(this, phoneOrder, menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		labelWelcomeToThe = new JLabel("Welcome to the Pizza Ordering System!");
		labelWelcomeToThe.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		labelWelcomeToThe.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelWelcomeToThe, BorderLayout.NORTH);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		buttonEmployee = new JButton("Cashier");
		buttonEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        //LoginDialog loginDlg = new LoginDialog();
                //loginDlg.setVisible(true);
                // if logon successfully
                //if(loginDlg.isSucceeded()&& LoginUtil.getUser(LoginUtil.userName)==1){
				
				String userName = JOptionPane.showInputDialog(null, "Please Enter Your Username", "Login Details - Username", 0);
				JPasswordField pf = new JPasswordField();
				String password = new String();
				int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (okCxl == JOptionPane.OK_OPTION) {
				  password = new String(pf.getPassword());
				}
				if(LoginUtil.authenticate(userName, password)){
					phoneOperatorWindow.present();
					//WelcomeWindow.this.setVisible(false);
				}
			}
		});
		panel.add(buttonEmployee);
		buttonManager = new JButton("Manager");
		buttonManager.addActionListener( new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				String userName = JOptionPane.showInputDialog(null, "Please Enter Your Username", "Login Details - Username", 0);
				JPasswordField pf = new JPasswordField();
				String password = new String();
				int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (okCxl == JOptionPane.OK_OPTION) {
				   password = new String(pf.getPassword());
				}
				if(LoginUtil.authenticate(userName, password)){
				mw.setVisible(true);
				}
			}
		});
		panel.add(buttonManager);
		buttonChef = new JButton("Chef");
		buttonChef.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = JOptionPane.showInputDialog(null, "Please Enter Your Username", "Login Details - Username", 0);
				JPasswordField pf = new JPasswordField();
				String password = new String();
				int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (okCxl == JOptionPane.OK_OPTION) {
				   password = new String(pf.getPassword());
				}
				if(LoginUtil.authenticate(userName, password)){
					ChefWindow chefWindow = new ChefWindow();
					chefWindow.setVisible(true);
				}
				
				}
		});
		panel.add(buttonChef);
		buttonCustomer = new JButton("Customer");
		buttonCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				phoneOperatorWindow.present();
				//WelcomeWindow.this.setVisible(false);
			}
		});
		panel.add(buttonCustomer);
		buttonExit = new JButton("Exit");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				WindowEvent windowClosing = new WindowEvent(WelcomeWindow.this, WindowEvent.WINDOW_CLOSING);
				WelcomeWindow.this.dispatchEvent(windowClosing);			
			}
		});
		panel.add(buttonExit);
		
		labelPleasePickAn = new JLabel("Please choose an option:");
		labelPleasePickAn.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelPleasePickAn.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelPleasePickAn, BorderLayout.CENTER);
	}

}
