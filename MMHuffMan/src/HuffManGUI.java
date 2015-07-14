import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.*;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import java.awt.Color;


@SuppressWarnings("serial")
public class HuffManGUI extends JFrame implements ActionListener {

	
	private JPanel contentPane;
	private JTextField sourceTextField;
	private JTextField desTextField;
	
	String src,des;
	HuffManAlgoWithFiles HuffManCom=new HuffManAlgoWithFiles();
	private JButton compress;
	private JButton deCompress;
	private JButton btnShowSrc;
	private JButton btnShowDis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
            //UIManager.setLookAndFeel(“com.jtattoo.plaf.smart.SmartLookAndFeel”);// change theme 

          } catch (Exception e) {
            System.err.println("Look and feel not set.");
          }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HuffManGUI frame = new HuffManGUI();
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
	public HuffManGUI() {
		setTitle("Huff Man coding");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton sourceButton = new JButton("Source");
		sourceButton.addActionListener(this);
		sourceButton.setBounds(10, 11, 89, 23);
		contentPane.add(sourceButton);
		
		sourceTextField = new JTextField();
		sourceTextField.setEditable(false);
		sourceTextField.setBackground(Color.WHITE);
		sourceTextField.setBounds(109, 12, 315, 20);
		contentPane.add(sourceTextField);
		sourceTextField.setColumns(10);
		
		JButton btnDestinationFile = new JButton("Destination");
		btnDestinationFile.addActionListener(this);
		btnDestinationFile.setBounds(10, 45, 89, 23);
		contentPane.add(btnDestinationFile);
		
		desTextField = new JTextField();
		desTextField.setEditable(false);
		desTextField.setColumns(10);
		desTextField.setBackground(Color.WHITE);
		desTextField.setBounds(109, 46, 315, 20);
		contentPane.add(desTextField);
		
		compress = new JButton("Compress");
		compress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HuffManCom.compress(src, des);
			}
		});
		
		compress.setBounds(94, 97, 101, 23);
		contentPane.add(compress);
		
		deCompress = new JButton("DeCompress");
		deCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HuffManCom.decompress(src, des);
			}
		});
		
		deCompress.setBounds(234, 97, 101, 23);
		contentPane.add(deCompress);
		
		btnShowSrc = new JButton("Show src");
		btnShowSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ShowFile frame = new ShowFile(HuffManCom.showFile(src));
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnShowSrc.setBounds(109, 166, 89, 23);
		contentPane.add(btnShowSrc);
		
		btnShowDis = new JButton("Show des");
		btnShowDis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ShowFile frame = new ShowFile(HuffManCom.showFile(des));
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnShowDis.setBounds(250, 166, 89, 23);
		contentPane.add(btnShowDis);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose File");
		fileChooser.showOpenDialog(null);
		if(((JButton) e.getSource()).getText()=="Source"){
			src=fileChooser.getSelectedFile().getAbsolutePath();
			this.sourceTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
		else if(((JButton) e.getSource()).getText()=="Destination"){
			des=fileChooser.getSelectedFile().getAbsolutePath();
			this.desTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
}
