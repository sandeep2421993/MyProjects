package emailforensics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class HeaderWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JTextArea text;
	JPanel contentPane;
	public HeaderWindow(OptionsWindow w,String filepath) throws IOException
	{
		final OptionsWindow owin= w;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		File file=new File(filepath);
		//System.out.println(filepath);
		text = new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
        JScrollPane scr = new JScrollPane(text);
        scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scr.setBounds(20, 20, 600, 300);
        contentPane.add(scr);
        
        BufferedReader in = new BufferedReader(new FileReader(file));
		String line = in.readLine();
		while(line != null){
			//System.out.println(line);
		  text.append(line + "\n");
		  line = in.readLine();
		}
		//text.setLineWrap(true);
        
		//contentPane.add(text);
       
        
		in.close();
		  JButton buttonBack = new JButton("Back");
		  buttonBack.setSize(100,50);
	       buttonBack.setBounds(650,100 ,100,50);
	       buttonBack.addActionListener(new ActionListener(){
	    	   public void actionPerformed(ActionEvent e)
	    	   {
	    		owin.setVisible(true);   
	    		HeaderWindow.this.setVisible(false);   
	    	   }
	       });
	       contentPane.add(buttonBack);
		
		
	}
	
}