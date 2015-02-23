package cs.cs414.g.util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cs.cs414.g.domain.*;

public class ViewCustomerOrders extends JDialog implements ActionListener, WindowListener {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel tableModel;
	private PhoneOrder phoneOperator;
	private Customer customer;
	private Timer refreshTimer;
	
	/**
	 * Create the dialog.
	 */
	public ViewCustomerOrders(final PhoneOrder po, Customer customer) {
		phoneOperator = po;
		this.customer = customer;
		this.setTitle("Customer: " + customer.toString());
		
		setBounds(100, 100, 400, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				tableModel = new DefaultTableModel() {
				    public boolean isCellEditable(int row, int column) {
				        return false;
				     }
				};
				tableModel.setColumnCount(2);
				tableModel.setColumnIdentifiers(new String[] { "Order #", "Progress" });
				table = new JTable();
				table.setModel(tableModel);
				
				// disable moving of columns in tables
				table.getTableHeader().setReorderingAllowed(false);
				
				scrollPane.setViewportView(table);
			}
		}
		
		refreshTimer = new Timer(500, this);
		refreshTimer.start();
		
		this.addWindowListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		this.refresh();
	}
	
	private void refresh() {
		Vector< Order > orders = phoneOperator.getOrdersShallowCopyForCustomer(customer);
		if (orders == null) {
			tableModel.setRowCount(0);
		}
		else {
			Collections.reverse(orders);
			tableModel.setRowCount(orders.size());
			int i = 0;
			for (Order o : orders) {
				tableModel.setValueAt(new Integer(o.getOrderId()), i, 0);
				
				++i;
			}
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		refreshTimer.stop();
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
