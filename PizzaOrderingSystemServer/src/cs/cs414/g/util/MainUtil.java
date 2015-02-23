package cs.cs414.g.util;

import java.awt.EventQueue;


import cs.cs414.g.domain.Menu;
import cs.cs414.g.domain.PhoneOrder;
import cs.cs414.g.ui.WelcomeWindow;

public class MainUtil {

	public static void run(final PhoneOrder phoneOrder, final Menu menu) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeWindow frame = new WelcomeWindow(phoneOrder, menu);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
