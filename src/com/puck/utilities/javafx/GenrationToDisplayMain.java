package com.puck.utilities.javafx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.piccolo2d.extras.pswing.PSwingCanvas;
import org.piccolo2d.nodes.PText;

import com.puck.arrows.ParrowExtends;
import com.puck.arrows.ParrowUses;
import com.puck.display.piccolo2d.NewDisplayDG;
import com.puck.menu.items.removing.RemovesHierarchyEdgesOf;
import com.puck.nodes.piccolo2d.Edge;
import com.puck.nodes.piccolo2d.NodeContent;
import com.puck.nodes.piccolo2d.PiccoloCustomNode;
import com.puck.refactoring.ExecuteRefactoringPlan;
import com.puck.refactoring.RefactoringCommands;
import com.puck.undoRedo.StateChanger2;
import com.puck.utilities.piccolo2d.PCustomInputEventHandler;
import com.sun.deploy.uitoolkit.impl.fx.Utils;

import java.awt.TextArea;
import java.awt.Toolkit;

public class GenrationToDisplayMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jarPathText;
	private JTextField projetPathText;
	private TextArea puck2StdOut;
	private final String jarConstraintService = "constraintChecker.jar"; 
	private final JFileChooser jarChoser = new JFileChooser();
	private final JFileChooser projectChooser = new JFileChooser();
	private boolean writingDone = false;
	private JFrame dgFrame;
	private RunCommand runCommand;
	private File currentWldFile;
	private JFrame dependencyManagerFrame;
	private JLayeredPane zoomOnDependencyFrame;
	private JSplitPane splitPane;
	

	


	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Muhammet Tan


	public GenrationToDisplayMain() {
		setTitle("Display");
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 1024, 554);
		getContentPane().add(scrollPane);
		Dimension windowSize = new Dimension(1000, 700);
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(windowSize);
		
		setLocation((int)(screenSize.getWidth()/2) - (int)(windowSize.getWidth()/2), (int)(screenSize.getHeight()/2) - (int)(windowSize.getHeight()/2));
		
		
		pack();
		
		
		
		
		
	
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		scrollPane.setViewportView(tabbedPane);

		JPanel panel_conf = new JPanel();
		tabbedPane.addTab("Configuration", null, panel_conf, null);
		panel_conf.setLayout(null);
		

		
		jarPathText = new JTextField(System.getProperty("user.dir")+"\\puck2.jar");
		jarPathText.setBounds(112, 6, 571, 33);
		panel_conf.add(jarPathText);
		jarPathText.setColumns(30);

		JButton jarButton = new JButton("Jar");
		jarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileFilter filter = new FileNameExtensionFilter("jar File", "jar");
				jarChoser.showOpenDialog(GenrationToDisplayMain.this);
				jarChoser.addChoosableFileFilter(filter);
				jarPathText.setText(jarChoser.getSelectedFile().getAbsolutePath());
			}
		});
		jarButton.setBounds(680, 6, 68, 33);
		panel_conf.add(jarButton);

		projetPathText = new JTextField(System.getProperty("user.dir")+"\\projectTest");
		projetPathText.setBounds(112, 44, 571, 33);
		panel_conf.add(projetPathText);
		projetPathText.setColumns(30);

		JButton btnNewButton = new JButton("projet");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				projectChooser.showOpenDialog(GenrationToDisplayMain.this);
				projetPathText.setText(projectChooser.getSelectedFile().getAbsolutePath());
			}
		});
		btnNewButton.setBounds(680, 44, 191, 33);
		panel_conf.add(btnNewButton);

		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String jarPath = jarPathText.getText().replaceAll("\"", "\\\\");
					String testFile = projetPathText.getText().replaceAll("\"", "\\\\");
					ProcessBuilder pb = new ProcessBuilder("java", "-jar", jarPath, testFile);
					pb.redirectErrorStream(true);
					runCommand = new RunCommand(pb);
					runCommand.start();
					while (runCommand.getWriter() == null) {}
					runCommand.sendCommand("saveGraph DependecyGraph.xml");
					while(writingDone == false) {
						System.out.println("Waiting DG xml File");
					}
					init(new String[] {});
					writingDone = false;
					
					
					checkForWLDFile();
					
					setState(JFrame.ICONIFIED);
					

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnRun.setBounds(731, 89, 90, 28);
		panel_conf.add(btnRun);
		
	    puck2StdOut = new TextArea();
		puck2StdOut.setEditable(false);
		puck2StdOut.setBounds(69, 131, 846, 348);
		panel_conf.add(puck2StdOut);
		
		JButton embeddedJar = new JButton("Embedded Jar");
		embeddedJar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File("puck2.jar");
				jarPathText.setText(file.getAbsolutePath());
			}
		});
		embeddedJar.setBounds(748, 6, 123, 33);
		panel_conf.add(embeddedJar);
	}

	private void checkForWLDFile() {
		
		File f = new File(projetPathText.getText());
		File[] wldFiles = f.listFiles(new FilenameFilter() {
			public boolean accept (File f, String name) {
				return name.endsWith("wld");
			}
		});
		if(wldFiles.length==0) {
			System.out.println("No .wld file found ... ");
			Object[] opt = { "yes", "no"};
			int result = JOptionPane.showOptionDialog(GenrationToDisplayMain.this,
					"Do you want to load a .wld file?" ,
					"No .wld file found",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					opt,
					opt[1]);
			if (result==JOptionPane.YES_OPTION) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("weland file", "wld");
				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(f);
				chooser.setDialogTitle("Load a .wld file");
				
				int resultValue = chooser.showOpenDialog(GenrationToDisplayMain.this);
				if(resultValue == JFileChooser.APPROVE_OPTION) {
					currentWldFile = chooser.getSelectedFile();
				}
			}
			
				
		}
		else if(wldFiles.length==1) {
			System.out.println("fichier "+wldFiles[0].getName()+" found");
			currentWldFile = wldFiles[0];
		}
		else {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("weland file", "wld");
			chooser.setFileFilter(filter);
			chooser.setCurrentDirectory(f);
			chooser.setDialogTitle("Load a .wld file");
			
			int resultValue = chooser.showOpenDialog(GenrationToDisplayMain.this);
			if(resultValue == JFileChooser.APPROVE_OPTION) {
				currentWldFile = chooser.getSelectedFile();
			}
		}
	}
		
		
//		JFileChooser chooser = new JFileChooser();
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("weland file", "wld");
//		chooser.setFileFilter(filter);
//		chooser.showOpenDialog(GenrationToDisplayMain.this);
		
	
	public JFrame init(String[] args) {
		// Component
	
		PSwingCanvas canvas = new PSwingCanvas();
		JButton undo = new JButton("UNDO");
		JButton redo = new JButton("REDO");
		JButton save = new JButton("SAVE-Refactoring Plan");
		JButton execute = new JButton("Execute-Refactoring Plan");
		JButton impor = new JButton("LOAD-refactoring Plan");
		JButton generate = new JButton("Generate code source");
		JButton dependenciesManager = new JButton("Manage Dependencies");
		
		undo.setSize(40, 40);
		JToolBar toolBar = new JToolBar();
		toolBar.add(undo);
		toolBar.add(redo);
		toolBar.add(save);
		toolBar.add(impor);
		toolBar.add(execute);
		toolBar.add(generate);
		toolBar.add(dependenciesManager);
		

		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(false);

		JTextArea textArear = new JTextArea();
		textArear.setEditable(false);
		textArear.setSize(200, 40);
		textArear.setForeground(Color.RED);
		textArear.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		//textArear.append("- INFO - Les menus des noeuds s'ouvrent et se ferment avec le bouton droit  ");

		toolBar.addSeparator();
		//toolBar.add(textArear);

		// Button action listeners
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StateChanger2.getInstance().undo();
			}
		});
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StateChanger2.getInstance().redo();
			}
		});
		execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			File file = new File("RefactoringCommands.xml");
			System.out.println(file.getAbsolutePath());
			runCommand.sendCommand("execPlan "+file.getAbsolutePath()+"\n");
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(canvas) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if(!file.toString().contains(".xml"))
					file = new File(file.toString() + ".xml");
					RefactoringCommands.getInstance().writeFile(file);
					System.out.println(file.getAbsolutePath());
				}
			}
		});
		
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fileChooser.showSaveDialog(canvas) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					runCommand.sendCommand("saveCode "+file.getAbsolutePath()+"\n");
				}
			}
		});
		dependenciesManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(dependencyManagerFrame==null) {
					 showDependencyManagerWindow(dependenciesManager);
					 System.out.println(!dependencyManagerFrame.isDisplayable());
				}
				else {
					dependencyManagerFrame.requestFocus();
				}
				
			}
		});
//		
		impor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.addChoosableFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
				fc.showDialog(canvas, "select");
				System.out.println(fc.getSelectedFile().getPath());
				ExecuteRefactoringPlan.getInstance().setFilePath(fc.getSelectedFile().getPath());
				ExecuteRefactoringPlan.getInstance().execute();
			}
		});

		
		try {
			if (args.length == 0) {
				System.out.println("Appel de la newDisplayDG sans aucun argument");
				dgFrame = new NewDisplayDG(canvas, "DependecyGraph.xml");
			} else {
				dgFrame = new NewDisplayDG(canvas, args[0]);
			}

			canvas.setPreferredSize(new Dimension(1000, 500));

			zoomOnDependencyFrame = new JLayeredPane();
			Dimension frameSize = new Dimension(1000, 700);
			Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
			dgFrame.setPreferredSize(frameSize);
            zoomOnDependencyFrame.setVisible(true);
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvas, zoomOnDependencyFrame);
            splitPane.setOneTouchExpandable(true);
            System.out.println(dgFrame.getPreferredSize().getWidth());
            splitPane.setDividerLocation((int)(frameSize.getWidth()));
            
          
          
            // Jframe Container
            Container container = dgFrame.getContentPane();
            container.setLayout(new BorderLayout());
            container.add(splitPane, BorderLayout.CENTER);
            container.add(toolBar, BorderLayout.PAGE_START);
    
//            dgFrame.add(jsp, BorderLayout.CENTER);
            
			dgFrame.setLocation((int)(screenSize.getWidth()/2) - (int)(frameSize.getWidth()/2), (int)(screenSize.getHeight()/2) - (int)(frameSize.getHeight()/2));
			dgFrame.pack();
			dgFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			dgFrame.setVisible(true);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return dgFrame;
	}

	public static void main(String[] args) {
		JFrame frame = new GenrationToDisplayMain();
		frame.pack();
		frame.setVisible(true);
	}

	public class RunCommand extends Thread {
		private ProcessBuilder processBuilder;
		private Process pro;
		private OutputStream stdin;
		private InputStream stdout;
		private BufferedReader reader;
		private BufferedWriter writer;

		public RunCommand(ProcessBuilder processBuilder) {
			super();
			this.processBuilder = processBuilder;

		}

		@Override
		public void run() {
			try {
				pro = this.processBuilder.start();
				stdin = pro.getOutputStream();
				stdout = pro.getInputStream();
				reader = new BufferedReader(new InputStreamReader(stdout));
				writer = new BufferedWriter(new OutputStreamWriter(stdin));
				sendCommand("display\n");
				Scanner scanner = new Scanner(stdout);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					puck2StdOut.append(line +"\n");
					System.out.println(line);
					if (line.trim().equals("DONE")) {
						writingDone = true;
					}
					if (line.trim().contains("DONE:")) {
						String sub = line.substring(line.indexOf(":")+2, line.length());
						String [] split = sub.split(" ");
						if (split.length > 0) 
						((NewDisplayDG)dgFrame).renameNodes(split);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			super.run();
		}

		public void sendCommand(String commande) {
			try {
				writer.write(commande + "\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public BufferedReader getReader() {
			return reader;
		}

		public BufferedWriter getWriter() {
			return writer;
		}

	}
	
	private void showDependencyManagerWindow(JButton button) {
		
		dependencyManagerFrame = new JFrame();
		dependencyManagerFrame.setVisible(true);
		dependencyManagerFrame.setLocationRelativeTo(null);
		
		JButton button1;
		JButton button2;
		JButton button3;
		JTextArea textArea1;
		JScrollPane scrollPane1;
		JTextArea textArea2;
		JRadioButton radioButton1;
		JRadioButton radioButton2;
		JTextField textField1;
		JLabel label1;
		
		
		label1 = new JLabel();
		button1 = new JButton();
		button2 = new JButton();
	
		scrollPane1 = new JScrollPane();
		JEditorPane editorPane1 = new JEditorPane();
		
		StringBuilder str = new StringBuilder();
		if(currentWldFile!=null) {
			BufferedReader br ;
			try {
				br = new BufferedReader(new FileReader(currentWldFile));
				String line;
				while((line=br.readLine())!=null) {
					str.append(line).append("\n");
				}
				br.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		editorPane1.setText(str.toString());
		//======== this ========
		dependencyManagerFrame.setTitle("Dependencies Manager");
		dependencyManagerFrame.setForeground(Color.black);
		Dimension frameDim = new Dimension(700, 450);
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		dependencyManagerFrame.setSize(frameDim);
		dependencyManagerFrame.setPreferredSize(frameDim);
		dependencyManagerFrame.setLocation((int)(screenSize.getWidth()/2 - frameDim.getWidth()/2), (int)(screenSize.getHeight()/2 - frameDim.getHeight()/2));
			
		
//		addMouseListener(new MouseAdapter() {
//			@Override
//			public void addActionListener(ActionListener e) {
//				actionPerformed(e);
//			}
//		});
		Container contentPane = dependencyManagerFrame.getContentPane();
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(445, 300));

		//---- label1 ----
				String nameWldFile = currentWldFile==null?"none":currentWldFile.getName();
				label1.setText("Current .wld file : "+ nameWldFile);
				
				contentPane.add(label1);
				label1.setBounds(20, 360, 255, 30);

				//---- button1 ----
				button1.setText("Load wld file");
				button1.setBackground(Color.white);
				contentPane.add(button1);
				int parentWidth = (int) dependencyManagerFrame.getWidth();
				int parentHeight = (int) dependencyManagerFrame.getHeight();
				
				System.out.println("parentWidht : "+ parentHeight);
				button1.setBounds(0, 0, 185, 35);
				button1.setLocation(parentWidth*18/100, parentHeight*70/100);
				

				//---- button2 ----
				button2.setText("Save modifications");
				button2.setBackground(Color.white);
				contentPane.add(button2);
				button2.setBounds(0, 0, 185, 35);
				button2.setLocation(parentWidth- parentWidth*18/100 - button2.getWidth() - 17, parentHeight*70/100);

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(editorPane1);
				}
				contentPane.add(scrollPane1);
				scrollPane1.setBounds(70, 25, 535, 275);

				
				

		

		
		
		
		
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = new File(projetPathText.getText());
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("weland file", "wld"));
				fileChooser.setCurrentDirectory(f);
				if (fileChooser.showSaveDialog(dependencyManagerFrame) == JFileChooser.APPROVE_OPTION) {
					System.out.println("fichier selectionné : "+fileChooser.getSelectedFile().getName());
					currentWldFile = fileChooser.getSelectedFile();
					String nameWldFile = currentWldFile==null?"none":currentWldFile.getName();
					label1.setText("Current .wld file : "+ nameWldFile);
				}						
				
			}
		});
		
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveWldFile(editorPane1);
				displayDependenciesOnDG();
				
				showForbiddenDependencies();
				Dimension dialogSize = new Dimension(500, 100);
				Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
				
						
				JOptionPane j = new JOptionPane(currentWldFile.getName()+" has been successfully saved in : "+ currentWldFile.getPath()+"\n\n"
						+ "Forbidden dependencies have been saved in : " +System.getProperty("user.dir")+ "\\forbiddenEdges.tan",
						JOptionPane.INFORMATION_MESSAGE);
				j.setPreferredSize(dialogSize);
				j.setLocation((int)(screenSize.getWidth()/2 - dialogSize.getWidth()/2), (int)(screenSize.getHeight()/2 - dialogSize.getHeight()/2));
				j.showMessageDialog(editorPane1, j.getMessage(), "files saved!", j.getMessageType());
			}
		});
		
		dependencyManagerFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dependencyManagerFrame = null;
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	
		
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
		
	}
	
	private void showForbiddenDependencies() {
		zoomOnDependencyFrame.removeAll();
		 splitPane.setDividerLocation((int)(dgFrame.getWidth()/2));
		 Collection<Edge> forbEdges = ((NewDisplayDG) dgFrame).getForbiddenEdges();
         int offsetY = 20;
         for (Edge ed : forbEdges) {
         	JButton bt = new JButton();
         	bt.setText("Edge type : "+ed.getType()+ " | " + " from : "+((NewDisplayDG)dgFrame).getAllPNodes().get(ed.getFrom())
         			+ ", to : " + ((NewDisplayDG)dgFrame).getAllPNodes().get(ed.getTo())+ " (id edge : "+ed.getId()+")");
         	bt.setBounds(0, 0, 500, 40);
         	bt.setPreferredSize(new Dimension(500, 40));
         	zoomOnDependencyFrame.add(bt);
         	
         	bt.setLocation((int)(zoomOnDependencyFrame.getWidth()/3), (int)(zoomOnDependencyFrame.getHeight()*10/100) + offsetY);
         	offsetY+=42;
         	bt.setVisible(true);
         	System.out.println(bt.getLocation().getX());
         	bt.addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						showOnlyFocusedDependency(ed);
					}
				});
         }
	}

	private void showOnlyFocusedDependency(Edge ed) {
		
		//cleanAllDependencies
		NewDisplayDG frame = ((NewDisplayDG)dgFrame);
		for(PiccoloCustomNode p : frame.getAllPNodes().values()) {
			System.out.println(p.getName());
			NodeContent newContent = new NodeContent(new PText(p.getName()), p.getContent().getType());
			
			newContent.getText().setTextPaint(Color.BLACK);
			newContent.getText().setFont(new Font(p.getContent().getText().getText(), Font.PLAIN, 12));
			
			newContent.setOffset(p.getContent().getOffset().getX(),
					p.getContent().getOffset().getY());
			newContent.addInputEventListener(new PCustomInputEventHandler(p, frame.getRoot(), frame.getCanvas(), frame.getAllPNodes(), frame.getMenu(), frame.getANH(), frame.getListNodes()));
			
			p.setContent(newContent);
			
		}
		new RemovesHierarchyEdgesOf(frame.getRoot(), frame.getCanvas(), frame.getAllPNodes(), frame.getMenu(), frame.getANH(), frame.getListNodes()).drawOutgoingdges(frame.getRoot(), frame.getCanvas());
		frame.getANH().updateAllPosition();
		//display focused dependency
		displayForbiddenDep(ed);
		frame.getANH().updateAllPosition();
		frame.getRoot().setLayout();
		
	}

	private void displayForbiddenDep(Edge ed) {
		NewDisplayDG frame = ((NewDisplayDG)dgFrame);
		List<PiccoloCustomNode> toAndFrom = new ArrayList<PiccoloCustomNode>();
		PiccoloCustomNode fromNode = frame.getAllPNodes().get(ed.getFrom());
		PiccoloCustomNode toNode = frame.getAllPNodes().get(ed.getTo());
		toAndFrom.add(fromNode);
		toAndFrom.add(toNode);
		if(ed.getType().equals("contains")) {
			for(PiccoloCustomNode p : toAndFrom) {
				NodeContent newContent = new NodeContent(new PText(p.getName()), p.getContent().getType());
				newContent.getText().setTextPaint(Color.RED);
				newContent.getText().setFont(new Font(p.getContent().getText().getText(), Font.BOLD, 12));
				newContent.setOffset(p.getContent().getOffset().getX(),
						p.getContent().getOffset().getY());
				newContent.addInputEventListener(new PCustomInputEventHandler(p, frame.getRoot(), frame.getCanvas(), frame.getAllPNodes(), frame.getMenu(), frame.getANH(), frame.getListNodes()));
				p.setContent(newContent);
			}
		}
		else {
			if(ed.getType().equals("uses")) {
				frame.getANH().addArrow(new ParrowUses(fromNode, toNode, 10, fromNode, toNode, "1"));
			}
			else {
				frame.getANH().addArrow(new ParrowExtends(fromNode, toNode, fromNode, toNode, "1"));
			}
		}
		
		PiccoloCustomNode p = null;
		
		System.out.println(toNode.getDistanceFromHigherParent());
		System.out.println(fromNode.getDistanceFromHigherParent());
		p = (toNode.getDistanceFromHigherParent()>fromNode.getDistanceFromHigherParent()) ? toNode : fromNode;
		
		System.out.println(p);
		while(p.isHidden()) {
			p.getLastVisibleParent().expandAll();
		}
		int it =  p.getParent().getChildrenCount();
		for(PiccoloCustomNode child : p.getParentNode().getAllChildren() ) {
			
			if(!(child.getidNode().equals(p.getidNode()))) {
				child.collapseAll();
			}
		}
		
	}

	private void saveWldFile(JEditorPane p) {
		String str = p.getText();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(currentWldFile));
			bw.write(str);
			bw.close();
			
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}

	private void displayDependenciesOnDG() {
		if(currentWldFile==null) {
			
			JOptionPane.showMessageDialog(GenrationToDisplayMain.this,
					"No .wld file found..." ,
					"Y .wld !",
					JOptionPane.WARNING_MESSAGE);
		}
		else {
			ProcessBuilder pb = new ProcessBuilder("java", "-jar", System.getProperty("user.dir")+"\\"+jarConstraintService,
					System.getProperty("user.dir")+"\\DependecyGraph.xml", currentWldFile.getAbsolutePath());
			pb.redirectErrorStream(true);
			runCommand = new RunCommand(pb);
			runCommand.start();
			
			Dimension dialogSize = new Dimension(400, 100);
			Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
			
			
			
//			JOptionPane.showMessageDialog(GenrationToDisplayMain.this,
//					"Forbidden dependencies have been saved in : " +System.getProperty("user.dir")+ "forbiddenEdges.tan" ,
//					"Successfully saved!",
//					JOptionPane.INFORMATION_MESSAGE);
		
			
			try {
				System.out.println("sleep");
				Thread.sleep(3000);
				System.out.println("wake");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			
			((NewDisplayDG) dgFrame).refreshEdgesDisplay();
			
//			
		}			
	}
}
