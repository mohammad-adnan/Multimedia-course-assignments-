import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AdaptivHuffmanGUI extends JFrame {

	private JPanel contentPane;
	AdaptivHuffManAlgo HM=new AdaptivHuffManAlgo();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdaptivHuffmanGUI frame = new AdaptivHuffmanGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdaptivHuffmanGUI() {
		setTitle("Adaptive HuffMan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JScrollPane SP=new JScrollPane();
		SP.setBounds(0, 20, 434, 192);
		contentPane.add(SP);
		
		final JTextArea textArea = new JTextArea();
		SP.setViewportView(textArea);
		
		JLabel lblNewLabel = new JLabel("Write here");
		lblNewLabel.setBounds(10, 6, 73, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Compress");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(HM.compress(textArea.getText()));
			}
		});
		btnNewButton.setBounds(10, 215, 103, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DeCompress");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(HM.deCompress(textArea.getText()));
			}
		});
		btnNewButton_1.setBounds(142, 215, 109, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		btnClear.setBounds(335, 228, 89, 23);
		contentPane.add(btnClear);
		
		JLabel lblMohammadAdnanAlbukaai = new JLabel("Mohammad adnan albukaai ID:20120605");
		lblMohammadAdnanAlbukaai.setBounds(10, 248, 304, 14);
		contentPane.add(lblMohammadAdnanAlbukaai);
	}
}
