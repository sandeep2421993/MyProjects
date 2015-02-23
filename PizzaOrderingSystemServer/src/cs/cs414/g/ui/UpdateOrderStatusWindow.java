package cs.cs414.g.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import cs.cs414.g.domain.*;
import cs.cs414.g.util.OrderStatus;

public class UpdateOrderStatusWindow extends JDialog implements ActionListener, WindowListener {
	private final JPanel contentPanel = new JPanel();
	private PhoneOrder phoneOperator;
	JLabel labelorderID; 
	JButton btnUpdateStatus;
	
	public UpdateOrderStatusWindow(final PhoneOrder po,final Customer customer){
		phoneOperator = po;
		labelorderID = new JLabel(""+po.getLastOrder(customer.getPhoneNumber()).getOrderId());
		labelorderID.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		labelorderID.setHorizontalAlignment(SwingConstants.CENTER);
		labelorderID.setBounds(133, 25, 61, 16);
		labelorderID.setForeground(Color.BLUE);
		contentPanel.add(labelorderID);
		
		btnUpdateStatus =  new JButton("Update Order Status");
		btnUpdateStatus.setBounds(133, 30, 20, 20);
		btnUpdateStatus.addActionListener( new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
			Order o = po.getLastOrder(customer.getPhoneNumber());
			
				
			}
		});
		
		contentPanel.add(btnUpdateStatus);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
	}
	
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
	}
	@Override
	public void windowActivated(WindowEvent arg0) { }
	@Override
	public void windowClosed(WindowEvent arg0) { }
	@Override
	public void windowDeactivated(WindowEvent arg0) { }
	@Override
	public void windowDeiconified(WindowEvent arg0) { }
	@Override
	public void windowIconified(WindowEvent arg0) { }
	@Override
	public void windowOpened(WindowEvent arg0) { }
}
