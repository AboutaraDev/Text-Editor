import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class TextEditor extends JFrame implements ActionListener {

	JTextArea textArea;
	JScrollPane scrolledPane;
	JLabel fontLabel;
	JSpinner fontSpinner;
	JButton fontColorChooser;
	JComboBox fontBox;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem saveItem;
	JMenuItem openItem;
	JMenuItem exitItem;
	
	TextEditor() {
		
		this.setTitle("Text Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(450, 450));
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
		scrolledPane = new JScrollPane(textArea);
		scrolledPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrolledPane.setPreferredSize(new Dimension(450, 450));
	
		
		fontLabel = new JLabel("Font: ");
		
		fontSpinner = new 	JSpinner();
		fontSpinner.setPreferredSize(new Dimension(50, 25));
		fontSpinner.setValue(20);
		fontSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSpinner.getValue()));
			}
		});
		
		fontColorChooser = new JButton("Color");
		fontColorChooser.setFocusable(false);
		fontColorChooser.addActionListener(this);
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		fontBox = new JComboBox(fonts);
		fontBox.setSelectedItem("Arial");
		fontBox.setEditable(true);
		fontBox.addActionListener(this);
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("file");
		
		openItem = new JMenuItem("open");
		saveItem = new JMenuItem("save");
		exitItem = new JMenuItem("exit");
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		
		
		this.setJMenuBar(menuBar);
		this.add(fontLabel);
		this.add(fontSpinner);
		this.add(fontColorChooser);
		this.add(fontBox);
		this.add(scrolledPane);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == fontBox) {
			textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
		}
		
		if(e.getSource() == fontColorChooser) {
			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null, "choose a color", Color.black);
			
			textArea.setForeground(color);
			
		}
		
		if(e.getSource() == openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					
					e1.printStackTrace();
				}
				fileIn.close();
			}
		}
		if(e.getSource() == saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				PrintWriter fileOut = null;
				
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText())
					;
				} catch (FileNotFoundException e1) {
					
					e1.printStackTrace();
				}
				fileOut.close();
			}
			
			
		}
		if(e.getSource() == exitItem) {
			System.exit(0);
		}
		
	}
}
