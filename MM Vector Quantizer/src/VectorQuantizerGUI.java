import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JProgressBar;




@SuppressWarnings("serial")
public class VectorQuantizerGUI extends JFrame implements ActionListener {


	private JPanel contentPane;
	private JTextField sourceTextField;
	private JTextField desTextField;
	
	String src,des;
	vectorQuantizerWithFile vectorQuantizerWithFile=new vectorQuantizerWithFile();
	private JButton compress;
	private JButton deCompress;
	private JButton btnShowSrc;
	private JButton btnShowDis;
	private JTextField nBitTextField;
	
	
	JProgressBar progressBar;
	private Task task;
	private JButton startButton;
	private boolean done=false;
	class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException ignore) {}
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            progressBar.setIndeterminate(false);
            progressBar.setVisible(false);
        }
    }
 

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
					VectorQuantizerGUI frame = new VectorQuantizerGUI();
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
	public VectorQuantizerGUI() {
		setTitle("Vector Quantizer");
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
				progressBarAction(compress);
				
						progressBarAction(compress);
					
				
				Thread x=new Thread(new Runnable() {
					
					@Override
					public void run() {
						int n=Integer.valueOf(nBitTextField.getText());
						vectorQuantizerWithFile.compress(src, des,n);
						done=true;
					}
				});
				x.start();
			}
		});
		
		compress.setBounds(94, 97, 101, 23);
		
		contentPane.add(compress);
		
		deCompress = new JButton("DeCompress");
		deCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBarAction(compress);
				
				vectorQuantizerWithFile.deCompress(src, des);
				
				done=true;
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
							ShowFile frame = new ShowFile(src);
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
							ShowFile frame = new ShowFile(des);
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
		
		JLabel lblNBitQuantizer = new JLabel("n bit quantizer");
		lblNBitQuantizer.setBounds(119, 77, 79, 14);
		contentPane.add(lblNBitQuantizer);
		
		nBitTextField = new JTextField();
		nBitTextField.setBounds(20, 74, 86, 20);
		contentPane.add(nBitTextField);
		nBitTextField.setColumns(10);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(30, 225, 383, 14);
		progressBar.setVisible(false);
//        progressBar.setValue(0);
//        progressBar.setStringPainted(true);
		contentPane.add(progressBar);

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
	
void progressBarAction(JButton button){
	startButton=button;
	 startButton.setEnabled(false);
     setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
     //Instances of javax.swing.SwingWorker are not reusuable, so
     //we create new instances as needed.
     task = new Task();
     task.addPropertyChangeListener(new PropertyChangeListener() {
		
		@Override
		public void propertyChange(PropertyChangeEvent arg0) {
			if (!done) {
	            int progress = task.getProgress();
	            if (progress == 0) {
	                progressBar.setIndeterminate(true);
	            } else {
	                progressBar.setIndeterminate(true);
	                progressBar.setVisible(true);
//	                progressBar.setStringPainted(false);
//	                progressBar.setValue(progress);
	            }
	        }
			
		}
	});
     task.execute();
//	if(start){
//		button.setEnabled(false);
//	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//	    progressBar.setIndeterminate(true);
//	}
//	else{
//		button.setEnabled(true);
//	    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//	    progressBar.setIndeterminate(false);
//	}
	
	}
}
