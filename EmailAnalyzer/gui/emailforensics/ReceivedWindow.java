package emailforensics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class ReceivedWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextArea text;
    private JButton button;
public ReceivedWindow(OptionsWindow ow,EmailParser ep)
{
	final OptionsWindow owin= ow;
	final EmailParser parser=ep;
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 800, 400);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	
	text = new JTextArea();
	text.setLineWrap(true);
	text.setWrapStyleWord(true);
    JScrollPane scr = new JScrollPane(text);
    scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scr.setBounds(20, 20, 600, 300);
    contentPane.add(scr);
	int hops=parser.getNum_of_hops();
	int t=1;
	for(int i=hops;i>0;i--)
	{   
		//if(i!=hops)
	  text.append((t++)+"\n");
		//text.append(""+(i+1)+"\n");
	  String	line=parser.r[i-1].getMessage();
	    text.append(line+"\n"+"\n");
	}
	
	button=new JButton("Back");
    button.setSize(75,50);
    button.setBounds(620, 100, 75, 50);
    button.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e)
    	{
    		ReceivedWindow.this.setVisible(false);
    		owin.setVisible(true);
    	}
    });
contentPane.add(button);
}
}
