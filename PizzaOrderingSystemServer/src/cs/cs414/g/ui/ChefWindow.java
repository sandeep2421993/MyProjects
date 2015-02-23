package cs.cs414.g.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import cs.cs414.g.domain.Chef;
import cs.cs414.g.util.OrderUtil;

public class ChefWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	public static JButton inputButton = new JButton("Order Completed!");
	public static JTextArea editTextArea = new JTextArea("Type Here!");
	public static JTextArea uneditTextArea = new JTextArea();
	public static JButton deliver = new JButton("Delivered Completed!");
	private String myString;

	/**
	 * Create the frame.
	 */
	public ChefWindow() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 712, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		uneditTextArea.setText(OrderUtil.getOrderText());
		uneditTextArea.setEditable(false);

		contentPane.add(uneditTextArea, BorderLayout.CENTER);
		contentPane.add(editTextArea, BorderLayout.SOUTH);
		contentPane.add(inputButton, BorderLayout.WEST);
		contentPane.add(deliver, BorderLayout.EAST);
		inputButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myString = editTextArea.getText();

				int line = Integer.parseInt(myString);
				Chef chef = new Chef();
				myString = chef.makeCompleted(editTextArea,line);

				uneditTextArea.setText(OrderUtil.getOrderText());
			}
		});
		
		
		deliver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myString = editTextArea.getText();

				int line = Integer.parseInt(myString);
				Chef chef = new Chef();
				myString = chef.makeDelivered(editTextArea,line);

				uneditTextArea.setText(OrderUtil.getOrderText());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}