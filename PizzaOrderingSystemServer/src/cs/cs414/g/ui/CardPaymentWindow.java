package cs.cs414.g.ui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import cs.cs414.g.domain.*;

public class CardPaymentWindow extends JFrame {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JLabel labelPrice;
    private JLabel labelCardNo;
    private JLabel priceLabel;
    private JLabel labelCvv;
    private JLabel labelExpiry;
    private JLabel labelName;
    private JButton buttonOk;
    private JButton buttonAuthenticate;
    private JButton buttonBack;
    private JTextField cardNoField;
    private JTextField cvvField;
    private JTextField expiryField;
    private JTextField nameField;
   
	public CardPaymentWindow(final PaymentWindow paymentWindow, Order cOrder ,Double price )
	{
		
		final Order currentOrder=cOrder;
		final double finalprice=price;
		final PaymentByCard paymentByCard= new PaymentByCard(price,"InStore");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		labelPrice = new JLabel("Final Price:");
		labelPrice.setSize(200, 100);
		labelPrice.setBounds(200, 20, 100, 50);
	    contentPane.add(labelPrice);
	    
	    priceLabel = new JLabel("$"+Double.toString(price));
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		priceLabel.setSize(100,100);
		priceLabel.setBounds(300, 20, 75, 50);
		contentPane.add(priceLabel);
		
		labelCardNo = new JLabel("Card No:");
		labelCardNo.setHorizontalAlignment(SwingConstants.CENTER);
		labelCardNo.setSize(200,100);
		labelCardNo.setBounds(100, 70, 100, 50);
		contentPane.add(labelCardNo);
		
		labelCvv = new JLabel("CVV:");
		labelCvv.setHorizontalAlignment(SwingConstants.CENTER);
		labelCvv.setSize(100,100);
		labelCvv.setBounds(100, 120, 100, 50);
		contentPane.add(labelCvv);
		
		labelExpiry= new JLabel("Expiry:");
		labelExpiry.setHorizontalAlignment(SwingConstants.CENTER);
		labelExpiry.setSize(200,100);
		labelExpiry.setBounds(100, 170, 100, 50);
		contentPane.add(labelExpiry);
		
	    cardNoField =new JTextField();
	    cardNoField.setText("Card No");
	    cardNoField.setSize(200,50);
	    cardNoField.setBounds(200,70,200,50);
	    cardNoField.setColumns(16);
	    contentPane.add(cardNoField);
	    
	    cvvField =new JTextField();
	    cvvField.setText("CVV");
	    cvvField.setSize(50,50);
	    cvvField.setBounds(200,120,50,50);
	    cvvField.setColumns(3);
	    contentPane.add(cvvField);
	    
	    expiryField =new JTextField();
	    expiryField.setText("mm/yy");
	    expiryField.setSize(100,50);
	    expiryField.setBounds(200,170,100,50);
	    expiryField.setColumns(5);
	    contentPane.add(expiryField);
	    
	    labelName = new JLabel("Name:");
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setSize(100,100);
		labelName.setBounds(100, 220, 100, 50);
		contentPane.add(labelName);
		
		nameField =new JTextField();
	    nameField.setText("Name");
	    nameField.setSize(100,50);
	    nameField.setBounds(200,220,100,50);
	    nameField.setColumns(20);
	    contentPane.add(nameField);
	    
	    buttonOk=new JButton("Done");
	    buttonOk.setSize(100,50);
	    buttonOk.setBounds(400,275,100,50);
	    buttonOk.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    	CardPaymentWindow.this.setVisible(false);
	    	SummaryWindow summaryWindow=new SummaryWindow(paymentByCard,currentOrder,finalprice);
	    	summaryWindow.setVisible(true);
	    	}
	    });
	    buttonOk.setVisible(false);
	    contentPane.add(buttonOk);
	    
        buttonAuthenticate=new JButton("Authenticate");
        buttonAuthenticate.setSize(150,50);
        buttonAuthenticate.setBounds(250,275, 150, 50);
        buttonAuthenticate.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		try {
					if(paymentByCard.authenticateCard(getCardNo(), getCVV(), getExpiry()))
					{
					buttonOk.setVisible(true);
					buttonAuthenticate.setVisible(false);
					buttonBack.setVisible(false);
					}
					else
					{
					JOptionPane.showMessageDialog(contentPane,"Authentication Failed","Alert", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(contentPane,"Check date"+e1.toString(),"Alert",JOptionPane.ERROR_MESSAGE);
				} 
        	}
        });
        contentPane.add(buttonAuthenticate);
        
           buttonBack=new JButton("Back");
	       buttonBack.setBounds(500, 275, 100, 50);
	       buttonBack.addActionListener(new ActionListener(){
	    	   public void actionPerformed(ActionEvent e)
	    	   {
	    		paymentWindow.setVisible(true);   
	    		CardPaymentWindow.this.setVisible(false);   
	    	   }
	       });
	       contentPane.add(buttonBack);

		
	}
	public String getCardNo()
	{
		return cardNoField.getText();
	}
	public String getCVV()
	{
		return cvvField.getText();
	}
	public String getExpiry()
	{
		return expiryField.getText();
	}

}
