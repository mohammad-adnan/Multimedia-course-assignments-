import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class ShowFile extends JFrame {
	/**
	 * Create the frame.
	 */
	public ShowFile(String s) {
		setTitle("Show file");
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setContentPane(new JLabel(new ImageIcon(s)));
		pack();
	}

}
