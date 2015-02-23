package cs.cs414.g.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cs.cs414.g.domain.*;
import cs.cs414.g.util.MainUtil;
public class ManagerWindow  extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable menuTable;
	private JLabel labelMenu;
	private JScrollPane scrollPane;
	private JButton buttonAdd, buttonModify, buttonDelete, buttonRedeem;
	
	
	private DefaultTableModel menuTableModel;


	DecimalFormat moneyFormatter = new DecimalFormat("$0.00");
	

	/**
	 * Create the frame.
	 */
	public ManagerWindow(final Menu menu) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 712, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		scrollPane = new JScrollPane();
		labelMenu = new JLabel("Menu");
		labelMenu.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		labelMenu.setHorizontalAlignment(SwingConstants.CENTER);
		labelMenu.setBounds(133, 25, 61, 16);
		labelMenu.setForeground(Color.BLUE);
		contentPane.add(labelMenu);
		buttonAdd = new JButton("Add New Item");
		buttonAdd.setBounds(34, 331, 117, 29);
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ManagerWindow.this.setVisible(false);
				JTextField nameItem = new JTextField(20);
			    JTextField itemPrice = new JTextField(5);
			    JTextField toppingPrice = new JTextField(5);
			    
			    JRadioButton pizza = new JRadioButton("Pizza");
			    JRadioButton topping = new JRadioButton("topping");
			    JRadioButton others = new JRadioButton("others");
			    ButtonGroup buttonGroup = new ButtonGroup();
			    buttonGroup.add(pizza);
			    buttonGroup.add(topping);
			    buttonGroup.add(others);
			    
			    JPanel myPanel = new JPanel(new GridLayout(12, 2));
			      myPanel.add(new JLabel("Item Name: "));
			      myPanel.add(nameItem);
			      
			      myPanel.add(new JLabel("Item Price:"));
			      myPanel.add(itemPrice);
			      
			     
			     
			      myPanel.add(topping);
			      myPanel.add(others);
			      
			      JCheckBox box = new JCheckBox("Special?");
			      myPanel.add(box);
			      int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Please Enter Details Of New Item", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			    	  
			    	  MenuItemModification menuItemModification = new MenuItemModification();
			    	  menuItemModification.addNewItem(nameItem, itemPrice, toppingPrice, pizza, topping, others, box);
			      }
			      InputStream inputStream;
				try {
					inputStream = new FileInputStream(new File("menu"));
					Menu newMenu = new Menu(inputStream);
				      ManagerWindow addedItem = new ManagerWindow(newMenu);
				      addedItem.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      
			}
		});
		contentPane.add(buttonAdd);
		
		buttonRedeem = new JButton("Modify Redeem ");
		buttonRedeem.setBounds(34, 331, 117, 29);
		buttonRedeem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
			
				JPanel myPanel = new JPanel(new GridLayout(12, 2));
			      myPanel.add(new JLabel("Enter Redeem Point Limit "));
			      JTextField redeemPoint = new JTextField(10);
			      myPanel.add(redeemPoint);
			      
			     
			      
			      int result=JOptionPane.showConfirmDialog(null, myPanel, 
			               "Redeem Decider", JOptionPane.OK_CANCEL_OPTION);
			      
			      if(result == JOptionPane.OK_OPTION)
			      {
			    	  try
			    	  {
			    		  System.out.println("Yaa");
			    		  System.out.println(redeemPoint.getText());
				    	  File file = new File("redeemCount");
				    	  FileWriter fileWriter = new FileWriter(file);
				    	  BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
				    	  writer.write(redeemPoint.getText());
				    	  JOptionPane.showConfirmDialog(null, "Redeem Successfully Updated", 
					               "Redeem", JOptionPane.OK_CANCEL_OPTION);
				    	  fileWriter.close();
				    	  writer.close();
			    	  }
			    	  catch(Exception e)
			    	  {
			    		  
			    	  }
			    	  
			      }
			      
			}
		});
		contentPane.add(buttonRedeem);
		contentPane.add(scrollPane);
		
		
		
		menuTable = new JTable(new MenuTableModel(menu));
		menuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// disable moving of columns in tables
		menuTable.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(menuTable);
		
		menuTableModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		menuTableModel.setColumnCount(8);
		
		
		
		buttonModify = new JButton("Modify Item");
		buttonModify.setBounds(34, 331, 117, 29);
		buttonModify.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				ManagerWindow.this.setVisible(false);
				int selectedMenuRow = menuTable.getSelectedRow();
				if(selectedMenuRow != -1 && (selectedMenuRow == 0 || selectedMenuRow == 1 || selectedMenuRow == 2))
				{
					MenuItem selectedFood = menu.getFoodItems().get(selectedMenuRow);
					
					JTextField nameItem = new JTextField(selectedFood.getName());
					
					nameItem.setText(selectedFood.name);
				    JTextField itemPrice = new JTextField(5);
				    
				    
				    JTextField toppingPrice = new JTextField(5);
				    
				    
				    JPanel myPanel = new JPanel(new GridLayout(8, 2));
				      myPanel.add(new JLabel("Item Name: "));
				      myPanel.add(nameItem);
				      nameItem.setText(selectedFood.toString());
				      
				      myPanel.add(new JLabel("Item Price:"));
				      myPanel.add(itemPrice);
				      itemPrice.setText(Double.toString(selectedFood.getPrice()));
				      
				      myPanel.add(new JLabel("Topping Price:"));
				      myPanel.add(toppingPrice);
				      if(selectedMenuRow == 0) toppingPrice.setText("1");
				      else if(selectedMenuRow == 1) toppingPrice.setText("1.5");
				      else if(selectedMenuRow == 2) toppingPrice.setText("2");
				     
				      JCheckBox box = new JCheckBox("Special?");
				      myPanel.add(box);
				      if(selectedFood.special == 0) box.setSelected(false);
				      else box.setSelected(true);
				      
				      int result = JOptionPane.showConfirmDialog(null, myPanel, 
				               "Please Enter Details Of New Item", JOptionPane.OK_CANCEL_OPTION);
				      if (result == JOptionPane.OK_OPTION) {
				    	  
				    	  MenuItemModification menuItemModification = new MenuItemModification();
				    	  menuItemModification.modifyPizza(nameItem, itemPrice, toppingPrice, box, selectedFood.itemID);
				      }
				      InputStream inputStream;
					try {
						inputStream = new FileInputStream(new File("menu"));
						Menu newMenu = new Menu(inputStream);
					      ManagerWindow addedItem = new ManagerWindow(newMenu);
					      addedItem.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (selectedMenuRow != -1 && selectedMenuRow != 0 && selectedMenuRow != 1 && selectedMenuRow != 2) 
				{
					MenuItem selectedFood = menu.getFoodItems().get(selectedMenuRow);
					
					JTextField nameItem = new JTextField(selectedFood.getName());
					
					nameItem.setText(selectedFood.name);
				    JTextField itemPrice = new JTextField(5);
				    
				    
				    JTextField toppingPrice = new JTextField(5);
				    
				    JRadioButton pizza = new JRadioButton("Pizza");
				    JRadioButton topping = new JRadioButton("topping");
				    JRadioButton others = new JRadioButton("others");
				    ButtonGroup buttonGroup = new ButtonGroup();
				    buttonGroup.add(pizza);
				    buttonGroup.add(topping);
				    buttonGroup.add(others);
				    
				    JPanel myPanel = new JPanel(new GridLayout(12, 2));
				      myPanel.add(new JLabel("Item Name: "));
				      myPanel.add(nameItem);
				      nameItem.setText(selectedFood.toString());
				      
				      myPanel.add(new JLabel("Item Price:"));
				      myPanel.add(itemPrice);
				      itemPrice.setText(Double.toString(selectedFood.getPrice()));
				     
				     
				      myPanel.add(topping);
				      others.setSelected(true);
				      myPanel.add(others);
				     
				      JCheckBox box = new JCheckBox("Special?");
				      myPanel.add(box);
				      if(selectedFood.special == 0) box.setSelected(false);
				      else box.setSelected(true);
				      int result = JOptionPane.showConfirmDialog(null, myPanel, 
				               "Please Enter Details Of New Item", JOptionPane.OK_CANCEL_OPTION);
				      if (result == JOptionPane.OK_OPTION) {
				    	  
				    	  MenuItemModification menuItemModification = new MenuItemModification();
				    	  menuItemModification.modifyItem(nameItem, itemPrice, toppingPrice, pizza, topping, others, box, selectedFood.itemID);
				      }
				      InputStream inputStream;
					try {
						inputStream = new FileInputStream(new File("menu"));
						Menu newMenu = new Menu(inputStream);
					      ManagerWindow addedItem = new ManagerWindow(newMenu);
					      addedItem.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		contentPane.add(buttonModify);
	
		buttonDelete = new JButton("Delete Item");
		buttonDelete.setBounds(34, 331, 117, 29);
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int selectedMenuRow = menuTable.getSelectedRow();
				if (selectedMenuRow != -1 && selectedMenuRow != 0 && selectedMenuRow !=1  && selectedMenuRow != 2) {
					MenuItem selectedFood = menu.getFoodItems().get(selectedMenuRow);
					ManagerWindow.this.setVisible(false);
					 int result = JOptionPane.showConfirmDialog(null, "You want to delete selected item?", 
				               "Are you sure?", JOptionPane.OK_CANCEL_OPTION);
					 if (result == JOptionPane.OK_OPTION) {
				    	  
				    	  MenuItemModification menuItemModification = new MenuItemModification();
				    	  menuItemModification.deleteItem(selectedFood.itemID);
				      }
				      InputStream inputStream;
					try {
						inputStream = new FileInputStream(new File("menu"));
						Menu newMenu = new Menu(inputStream);
					      ManagerWindow addedItem = new ManagerWindow(newMenu);
					      addedItem.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(selectedMenuRow == 0 || selectedMenuRow == 1 || selectedMenuRow == 2)
				{
					JOptionPane.showConfirmDialog(null, "You cannot delete selected item?", 
				               "Are you sure?", JOptionPane.OK_CANCEL_OPTION);
				}
			}
		});
		contentPane.add(buttonDelete);
		/*
		JButton back = new JButton("Update Changes");
		back.setBounds(34, 331, 117, 29);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ManagerWindow.this.setVisible(false);
				
				Map< String, ArrayList< Order > > orders = Collections.synchronizedMap(new HashMap< String, ArrayList< Order > >());
				Map< String, Customer > customers = Collections.synchronizedMap(new HashMap< String, Customer >());
				FileInputStream menuFileStream = null;
				try {
					File file = new File("menu.txt"); 
					menuFileStream = new FileInputStream(file);
				}
				catch (FileNotFoundException e) {
					System.err.println("Unable to open menu file. Exiting...");
					System.exit(1);
				}
				
				Menu menu = null;
				try {
					menu = new Menu(menuFileStream);
				}
				catch (Exception exception) {
					System.err.println(exception.getMessage());
					exception.printStackTrace(System.err);
					System.exit(1);
				}
				
				final Kitchen kitchen = new Kitchen();
				
				MainUtil.run(new PhoneOrder(customers, orders), menu, kitchen);
			}
		});
		contentPane.add(back);
		*/
		
		
		
		
		
	
	}
	
	/**
	 * The timer event-action.
	 */
	
	public ManagerWindow() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Set the row data for a particular table model
	 * @param model
	 * @param row
	 * @param rowData
	 */
	private void setRow(DefaultTableModel model, int row, Object[] rowData) {
		if (row >= model.getRowCount()) {
			model.addRow(rowData);
		}
		else {
			for (int i = 0; i < rowData.length; ++i) {
				model.setValueAt(rowData[i], row, i);
			}
		}
	}
	
	/**
	 * Update the data in the order tracking table
	 */
	
	
	/**
	 * Update the data in the manager report table.
	*/

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
