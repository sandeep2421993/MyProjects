package cs.cs414.g.ui;

import javax.swing.table.AbstractTableModel;

import cs.cs414.g.domain.*;

public class MenuTableModel extends AbstractTableModel
{
	private Menu menu;
	
	public MenuTableModel(Menu menu) {
		this.menu = menu;
	}
	
	public int getColumnCount() {
		return 2;
	}
	
	public int getRowCount() {
		return menu.getFoodItems().size();
	}
	
	public Object getValueAt(int row, int col) {
		MenuItem food = menu.getFoodItems().get(row);
		if (col == 0) {
			if(food.special == 0) return food.getType();
			else return food.getType() + "  (Today's Special)  ";
		}
		else {
			 return "$" + food.getPrice();
		}
	}
	
	public String getColumnName(int col) {
		String[] values = { "Item", "Price" };
		return values[col];
	}
}
