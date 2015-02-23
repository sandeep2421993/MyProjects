package emailforensics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JFrame frame;
	private JPanel pane;
	private JButton chooser;
	String filepath;
	private EmailParser parser;
	public Window(EmailParser ep)
	{
		parser=ep;
		gui();
	}

	private void gui() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,200,100);
		pane=new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pane);
		pane.setLayout(null);
		//pane.setBackground(Color.white);
		chooser=new JButton("File");
		chooser.setSize(100,50);
		chooser.setBounds(10, 10, 100, 50);
		chooser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
			filepath=Email.fileChooser();
			//System.out.println(filepath);
			try {
				OptionsWindow ow=new OptionsWindow(Window.this,filepath,parser);
				ow.setVisible(true);
				} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			}
		});
		
		pane.add(chooser);
	}
	
	
}
