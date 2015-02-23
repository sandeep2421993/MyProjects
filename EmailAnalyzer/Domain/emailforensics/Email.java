package emailforensics;

import java.io.File;

import javax.swing.JFileChooser;

public class Email {
	
	public static void main(String args[])
	{
		EmailParser ep=new EmailParser();
		Window w=new Window(ep);
		w.setVisible(true);
	}

	public static String fileChooser()
	{
		JFileChooser fc=new JFileChooser();
		File file = null;
		int checker;
		checker=fc.showOpenDialog(null);
		if(checker==JFileChooser.APPROVE_OPTION)
		{
			file=fc.getSelectedFile();
	    }
	return file.toString();	
	}
}
