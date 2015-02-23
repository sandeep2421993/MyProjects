package emailforensics;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

//import cs556.emailforensics.GetLocation;





public class OptionsWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filePath;
	final private EmailParser parser;
	JPanel pane;
	JFrame frame;
	JMenuBar menubar;
	JMenu view;
	JMenu help;
	JMenuItem items[];
	JLabel senderLabel;
    JLabel receiverLabel;
    JLabel messageLabel;
    JLabel hopsLabel;
    JLabel dateLabel;
    JLabel senderLabel1;
    JLabel receiverLabel1;
    JLabel messageLabel1;
    JLabel hopsLabel1;
    JLabel dateLabel1;
    JButton displayHeader;
    JButton displayReceived;
    JButton inspect;
    JButton location;
    JDialog hopdialog;
    JTextField hopfield;
    String senderValue;
    String receiverValue;
    String messageValue;
    String dateValue;
    String ip;
    String rby;
    int hopsValue;
	int j;
	int temp=0;
	String tempid="";
	boolean flag=true;
	@SuppressWarnings("unused")
	public OptionsWindow(Window w,String filePath, EmailParser ep) throws IOException {
        
		final Window win= w;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pane);
		pane.setLayout(null);
		
		this.filePath=filePath;
		this.parser=ep;
		
		
		
		parser.parseHeader(filePath);
		hopsValue=parser.getNum_of_hops();
		try {
			if(parser.check()==false)
			{
				JOptionPane.showMessageDialog(pane,"Suspect!");
				flag=false;
			}
			else
			{
				JOptionPane.showMessageDialog(pane,"Safe!");
			}
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}
		setMenuBar();
		setElements();

}

	
private void setMenuBar() {
		
	// final JMenuItem items[]=new JMenuItem[hopsValue];
	    menubar=new JMenuBar();
	    setJMenuBar(menubar);
	    view = new JMenu("View");
	    help=new JMenu("Help");
	    menubar.add(view);
	    menubar.add(help);
	    
	    JMenuItem header=new JMenuItem("Header");
	    header.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    	try {
				HeaderWindow hw=new HeaderWindow(OptionsWindow.this,filePath);
				hw.setVisible(true);
			    OptionsWindow.this.setVisible(false);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}	
	    	}
	    });
	    JMenuItem received=new JMenuItem("Received");
	    received.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		ReceivedWindow rw=new ReceivedWindow(OptionsWindow.this,parser);
	    		rw.setVisible(true);
	    		OptionsWindow.this.setVisible(false);
	    	}
	    });
	    JMenuItem smtpids=new JMenuItem("SMTP IDs");
	    smtpids.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		
	    		temp++;
	    		if(temp==1){
	    	parser.setSMTP();
	    	final ArrayList<String> ids=parser.getSMTP();
	    	
	    	for(String id: ids) 
	    	{
	    		tempid+=id;
	    		tempid+="\n";
	    	}}
	    	JOptionPane.showMessageDialog(pane,tempid);
	    	}
	    });
	    JMenuItem hop=new JMenuItem("Hop");
	    hop.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    	hopdialog= new JDialog(OptionsWindow.this,"Select Hop");
	    	hopdialog.setLayout(new FlowLayout());
	    	hopdialog.setSize(400,100);
	    	JButton ok=new JButton("Get");
	    	ok.setSize(50,25);
	    	ok.setBounds(130, 1, 50, 25);
	    	ok.addActionListener(new ActionListener(){
	    		public void actionPerformed(ActionEvent e)
	    		{
	    			ArrayList<ReceivedField> rec=parser.getRec();
	    			String s=hopfield.getText().trim();
	    			int t=Integer.parseInt(s)-1;
	    			//System.out.println(s);
	    			ReceivedField rf=rec.get(t);
	    			JOptionPane.showMessageDialog(pane,rf.getFormattedContent());
	    		}
	    	});
	    	JLabel hoplabel=new JLabel("Enter Hop:");
	    	hoplabel.setSize(150,50);
	    	hoplabel.setBounds(1, 1,100, 50);
	    	hopfield=new JTextField(" ");
	    	hopfield.setSize(25,25);
	    	hopfield.setBounds(101,1,25,25);
	    	hopfield.setColumns(1);
	    	
	    	hopdialog.add(hoplabel);
	    	hopdialog.add(hopfield);
	    	hopdialog.add(ok);
	    	hopdialog.setVisible(true);
	    	
	    	}
	    });
	    
	    view.add(header);
	    view.add(received);
	    view.add(smtpids);
	    view.add(hop);
		
	}


private void setElements() {
		
	  
	    
		senderLabel = new JLabel("Sender:");
		senderLabel.setSize(75,50);
		senderLabel.setBounds(50, 50, 75, 50);
		pane.add(senderLabel);
		
		senderValue=parser.getSender();
		senderLabel1 = new JLabel("Sender:");
		senderLabel1.setText(senderValue);
		senderLabel1.setSize(400,50);
		senderLabel1.setBounds(150, 50, 400, 50);
		pane.add(senderLabel1);
		
		receiverLabel=new JLabel("Receiver:");
		receiverLabel.setSize(75,50);
		receiverLabel.setBounds(50, 100, 75, 50);
		pane.add(receiverLabel);
		
		receiverValue=parser.getRecipient();
		receiverLabel1=new JLabel("Receiver:");
		receiverLabel1.setText(receiverValue);
		receiverLabel1.setSize(400,50);
		receiverLabel1.setBounds(150, 100, 400, 50);
		pane.add(receiverLabel1);
		
		
		messageLabel= new JLabel("Message-ID:");
		messageLabel.setSize(75,50);
		messageLabel.setBounds(50, 150, 75, 50);
	    pane.add(messageLabel);
	    
	    messageValue=parser.getMessageID();
	    messageLabel1= new JLabel("Message-ID:");
	    messageLabel1.setText(messageValue);
		messageLabel1.setSize(400,50);
		messageLabel1.setBounds(150, 150, 400, 50);
	    pane.add(messageLabel1);
	    
	    hopsLabel=new JLabel("No. of Hops:");
	    hopsLabel.setSize(75,50);
		hopsLabel.setBounds(50, 200, 75, 50);
	    pane.add(hopsLabel);
	    
	    hopsValue=parser.getNum_of_hops();
	    hopsLabel1=new JLabel("No. of Hops:");
	    hopsLabel1.setText(String.valueOf(hopsValue));
	    hopsLabel1.setSize(200,50);
		hopsLabel1.setBounds(150, 200, 200, 50);
	    pane.add(hopsLabel1);
	    
	    dateLabel=new JLabel("Date sent:");
	    dateLabel.setSize(100,50);
		dateLabel.setBounds(50, 250, 100, 50);
        pane.add(dateLabel);
        
        dateValue=parser.getDate();
        dateLabel1=new JLabel("Date sent:");
        dateLabel1.setText(dateValue);
	    dateLabel1.setSize(400,50);
		dateLabel1.setBounds(150, 250, 400, 50);
        pane.add(dateLabel1);
        
	  /*  displayHeader=new JButton("Header");
	    displayHeader.setSize(75,50);
		displayHeader.setBounds(100, 300, 75, 50);
		displayHeader.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Window.this.setVisible(false);
				try {
					HeaderWindow hw= new HeaderWindow(Window.this,filePath);
					hw.setVisible(true);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				
			}

			
		
		});
		
	    pane.add(displayHeader);
	    */
        /*
	    displayReceived=new JButton("Received");
	    displayReceived.setSize(100,50);
		displayReceived.setBounds(200, 300, 100, 50);
		displayReceived.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Window.this.setVisible(false);
				ReceivedWindow rw= new ReceivedWindow(Window.this,parser);
				rw.setVisible(true);
			}
		});
		pane.add(displayReceived);
		*/
        if(flag==false){
        	
        	ip=ReceivedField.getSuspectip();
		inspect=new JButton("Inspect");
	    inspect.setSize(100,50);
		inspect.setBounds(300, 300, 100, 50);
		inspect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String actual=ReceivedField.getSuspectfromactual();
                String claimed=ReceivedField.getSuspectfromclaimed();
                
                String smtpid=ReceivedField.getSuspectid();
                String message="This email has been labeled as suspect because it was claimed to be from the Domain :"+claimed+"\n But the email was actually from the Domain:"+actual+"\nThe SMTP id corresponsing to this is : "+smtpid+"\nThe IP address is :"+ip;
                //JOptionPane.showMessageDialog(pane,"Actual:"+actual+"\n"+"Claimed:"+claimed+"\n"+"IP:"+ip+"\n"+"SMTP ID:"+smtpid);
                JOptionPane.showMessageDialog(pane,message);
             
				
			}
			});
		pane.add(inspect);
		
		location = new JButton("Location");
		location.setSize(100, 50);
		location.setBounds(400, 300, 100, 50);
		location.addActionListener(new ActionListener(){
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e)
			{
				   try {
					   if(ip.split("\\.")[0].length()==3){
					   //System.out.println(ip);
				
				GetLocation gl=new GetLocation(ip);
				String s="Country: "+GetLocation.getCountry()+"\n"+"Region: "+GetLocation.getRegion()+"\n"+"City: "+GetLocation.getCity()+"\n"+"Latitude: "+GetLocation.getLatitude()+"\n"+"Longitude: "+GetLocation.getLongitude();
				JOptionPane.showMessageDialog(pane,s);
					   }
					   else
					   {
						   rby=ReceivedField.getSuspectby();
						   String dtemp[]=rby.split("\\.");
						   
						   String dns="www."+dtemp[dtemp.length-2]+"."+dtemp[dtemp.length-1];
						   InetAddress inetAddress = InetAddress.getByName(dns);
						   String domain=inetAddress.getHostAddress();
						   GetLocation gl=new GetLocation(domain);
						   String s="Country: "+GetLocation.getCountry()+"\n"+"Region: "+GetLocation.getRegion()+"\n"+"City: "+GetLocation.getCity()+"\n"+"Latitude: "+GetLocation.getLatitude()+"\n"+"Longitude: "+GetLocation.getLongitude();
							JOptionPane.showMessageDialog(pane,s);
						   //JOptionPane.showMessageDialog(pane,domain);
					   }
			} catch (IOException e1) {
			
				e1.printStackTrace();
			}
			
			}
		});
		pane.add(location);
		}
	}	
}
