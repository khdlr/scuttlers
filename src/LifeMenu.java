import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LifeMenu extends JMenuBar implements ActionListener {
	JMenu sim, edit, file;
	JMenuItem
		file_new, file_open, file_save_initial, file_save_current,
		edit_toggle,
		sim_start, sim_rewind, sim_settings;
	JCheckBoxMenuItem sim_record;
	LifePanel panel;
	LifeFrame frame;
	
	public LifeMenu(LifeFrame frame, LifePanel panel) {
		this.panel = panel;
		this.frame = frame;
		file = new JMenu("File");
		// File Menu
		file_new = new JMenuItem("New");
			file_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));		
			file_new.addActionListener(this);
			file.add(file_new);
		file_open = new JMenuItem("Open");
			file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
			file_open.addActionListener(this);
			file.add(file_open);
		file_save_initial = new JMenuItem("Save Initial State");
			file_save_initial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
			file_save_initial.addActionListener(this);
			file.add(file_save_initial);
		file_save_current = new JMenuItem("Save Current State");
			file_save_current.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
			file_save_current.addActionListener(this);
			file.add(file_save_current);
		// Edit Menu
		edit = new JMenu("Edit");
		// Sim Menu
		sim = new JMenu("Simulation");	
		sim_start = new JMenuItem("Start Simulation");
			sim_start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
			sim_start.addActionListener(this);
			sim.add(sim_start);
		sim_rewind = new JMenuItem("Rewind Simulation");
			sim_rewind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
			sim_rewind.addActionListener(this);
			sim.add(sim_rewind);
		sim_record = new JCheckBoxMenuItem("Make .pngs");
			sim_record.addActionListener(this);
			sim.add(sim_record);
		add(file);
		add(edit);
		add(sim);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(file_new)) {
			panel.makeWorld(Config.DEFAULT_WIDTH, Config.DEFAULT_HEIGHT);
		} else if(e.getSource().equals(file_open)) {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Txt File", "txt");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(frame);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	try {
		    		panel.useWorld(World.load(chooser.getSelectedFile()));
		    	} catch(IOException ex) {
		    		System.err.println("Couldn't open file");
		    		ex.printStackTrace();
		    	}
		    }
		} else if(e.getSource().equals(file_save_initial)) {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Txt File", "txt");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showSaveDialog(frame);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	try {
		    		panel.world.saveInitial(chooser.getSelectedFile());
		    	} catch(IOException ex) {
		    		System.err.println("Couldn't open file");
		    		ex.printStackTrace();
		    	}
		    }
		} else if(e.getSource().equals(file_save_current)) {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Txt File", "txt");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showSaveDialog(frame);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	try {
		    		panel.world.saveCurrent(chooser.getSelectedFile());
		    	} catch(IOException ex) {
		    		System.err.println("Couldn't open file");
		    		ex.printStackTrace();
		    	}
		    }
		} else if(e.getSource().equals(sim_start)) {
			if(null != panel.thread && panel.thread.running) {
				sim_start.setText("Resume Simulation");
				panel.thread.running = false;
			} else {
				sim_start.setText("Pause Simulation");
				panel.thread = new LifeThread(panel.world, panel);
				panel.thread.start();
			}
		} else if(e.getSource().equals(sim_rewind)) {
			sim_start.setText("Start Simulation");
			if(null != panel.thread && panel.thread.running)
				panel.thread.running = false;
			panel.rewindWorld();
		} else if(e.getSource().equals(sim_record)) {
			Config.outputFiles = sim_record.getState();
		}
		
	}
}
