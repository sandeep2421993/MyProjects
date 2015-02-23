package cs.cs414.g.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import cs.cs414.g.domain.*;

public class CashPaymentWindow extends JFrame {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JLabel labelPrice;
    private JLabel labelCash;
    private JLabel priceLabel;
    private JLabel returnLabel;
    private JLabel labelReturn;
    private JButton buttonOk;
    private JButton buttonDone;
    private JButton buttonBack;
    private JTextField cashField;
    private double finalPrice;
	public CashPaymentWindow(final PaymentWindow paymentWindow, Order cOrder ,Double price )
	{
	
		final Order currentOrder=cOrder;
		final PaymentByCash paymentByCash= new PaymentByCash(price,"InStore");
		finalPrice=price;
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
		
		labelCash=new JLabel("Cash Recevied:$");
		labelCash.setSize(200,100);
		labelCash.setBounds(150, 70, 120, 50);
		contentPane.add(labelCash);
		
		
	    cashField= new JTextField();
	    cashField.setText("0.00");
	    cashField.setSize(200,100);
	    cashField.setBounds(270, 70, 50, 50);
		contentPane.add(cashField);
	    
		labelReturn= new JLabel("Return Change:");
		labelReturn.setSize(200,100);
		labelReturn.setBounds(150,120,120,50);
		contentPane.add(labelReturn);
		
		returnLabel=new JLabel("0.00");
	    returnLabel.setSize(100, 100);
	    returnLabel.setBounds(270,120,100,50);
	    contentPane.add(returnLabel);
		
		
		buttonOk=new JButton("OK");
		buttonOk.setSize(50,50);
		buttonOk.setBounds(320,80, 60,30);
		buttonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				double temp=Double.parseDouble(getAmount());
				double temp1=paymentByCash.getReturnAmount(temp);
				if(temp1<0)
				{
					JOptionPane.showMessageDialog(contentPane,"Amount Insufficient","Alert", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
				returnLabel.setText("$"+Double.toString(temp1));
				buttonDone.setVisible(true);
				buttonBack.setVisible(false);
				}
				
			}
		});
		contentPane.add(buttonOk);
	    
		buttonDone=new JButton("Done");
		buttonDone.setSize(100,50);
		buttonDone.setBounds(250, 200, 100, 50);
		buttonDone.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				CashPaymentWindow.this.setVisible(false);
				SummaryWindow summaryWindow=new SummaryWindow(paymentByCash,currentOrder,finalPrice);
				summaryWindow.setVisible(true);
			}
		
		});
		buttonDone.setVisible(false);
		contentPane.add(buttonDone);

		   buttonBack=new JButton("Back");
	       buttonBack.setBounds(450, 200, 100, 50);
	       buttonBack.addActionListener(new ActionListener(){
	    	   public void actionPerformed(ActionEvent e)
	    	   {
	    		paymentWindow.setVisible(true);   
	    		CashPaymentWindow.this.setVisible(false);   
	    	   }
	       });
	       contentPane.add(buttonBack);
	}
	

public String getAmount()
{
	return cashField.getText();
	
}
}
