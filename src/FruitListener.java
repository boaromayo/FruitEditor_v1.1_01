package FruitEditor;

import java.awt.event.*;

import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import java.util.*;

import java.beans.*;

public class FruitListener implements ActionListener,
	ChangeListener, PropertyChangeListener {
	
	private FruitEditor fruitEditor;
	
	//private Stack<ChangeEvent> changes;
	private Stack<PropertyChangeEvent> actions;
	
	public FruitListener(FruitEditor f) {
		fruitEditor = f;
		
		actions = new Stack<PropertyChangeEvent>();
	}
	
	/**==========================
	// actionPerformed(event) - Action event listener
	//===========================**/
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
				
		// FILE listeners.
		if (src == getComponent("newBtn") || 
				src == getComponent("newItem")) {
			new NewDialog(fruitEditor); // Load NEW dialog.
		} else if (src == getComponent("openBtn") ||
				src == getComponent("openItem")) {
			openAction();
		} else if (src == getComponent("saveBtn") ||
				src == getComponent("saveItem")) {
			saveAction();
		} else if (src == getComponent("saveAsItem")) {
			saveAsAction();
		} else if (src == getComponent("closeItem")) {
			closeAction();
		}
		
		// EDIT -> FIX
		else if (src == getComponent("undoItem") ||
				src == getComponent("undoBtn")) {
			undo();
		} else if (src == getComponent("redoItem") ||
				src == getComponent("redoBtn")) {
			redo();
		}
		
		// EDIT listeners
		else if (src == getComponent("cutItem") ||
				src == getComponent("cutRt") || 
				src == getComponent("cutBtn")) {
			cutAction();
		} else if (src == getComponent("copyItem") ||
				src == getComponent("copyRt") ||
				src == getComponent("copyBtn")) {
			copyAction();
		} else if (src == getComponent("pasteItem") ||
				src == getComponent("pasteRt") ||
				src == getComponent("pasteBtn")) {
			pasteAction();
		} else if (src == getComponent("deleteItem") ||
				src == getComponent("deleteRt") ||
				src == getComponent("deleteBtn")) {
			deleteAction();
		}
		
		// VIEW -> GRID
		else if (src == getComponent("gridItem")) {
			JCheckBoxMenuItem gridItem = (JCheckBoxMenuItem)getComponent("gridItem");
			JToggleButton gridBtn = (JToggleButton)getComponent("gridBtn");
			
			gridBtn.setSelected(gridItem.isSelected());
			setGrid(gridItem.isSelected());
		} else if (src == getComponent("gridBtn")) {
			JCheckBoxMenuItem gridItem = (JCheckBoxMenuItem)getComponent("gridItem");
			JToggleButton gridBtn = (JToggleButton)getComponent("gridBtn");
			
			gridItem.setSelected(gridBtn.isSelected());
			setGrid(gridBtn.isSelected());
		}
		
		// VIEW -> SHIFT
		else if (src == getComponent("shiftBtn")) {
			new ShiftDialog(fruitEditor); // Load Shift Map dialog.
		}
		
		// VIEW -> SCALE item listeners
		else if (src == getComponent("oneItem") ||
				src == getComponent("oneBtn")) {
			//setScale(Map.SCALE_ONE);
		} else if (src == getComponent("twoItem") ||
				src == getComponent("twoBtn")) {
			//setScale(Map.SCALE_TWO);
		} else if (src == getComponent("fourItem") ||
				src == getComponent("fourBtn")) {
			//setScale(Map.SCALE_FOUR);
		} else if (src == getComponent("eightItem") ||
				src == getComponent("eightBtn")) {
			//setScale(Map.SCALE_EIGHT);
		}
		
		// VIEW -> MODE item and button listeners
		else if (src == getComponent("mapModeItem")) {
			JToggleButton mapModeBtn = (JToggleButton) getComponent("mapModeBtn");
			mapModeBtn.setSelected(true);
			setMode(EditorMode.MAP_MODE);
		} else if (src == getComponent("eventModeItem")) {
			JToggleButton eventModeBtn = (JToggleButton) getComponent("eventModeBtn");
			eventModeBtn.setSelected(true);
			setMode(EditorMode.EVENT_MODE);
		} else if (src == getComponent("mapModeBtn")) {
			JRadioButtonMenuItem mapModeItem = (JRadioButtonMenuItem) getComponent("mapModeItem");
			mapModeItem.setSelected(true);
			setMode(EditorMode.MAP_MODE);
		} else if (src == getComponent("eventModeBtn")) {
			JRadioButtonMenuItem eventModeItem = (JRadioButtonMenuItem) getComponent("eventModeItem");
			eventModeItem.setSelected(true);
			setMode(EditorMode.EVENT_MODE);
		}
		
		// DRAW item listeners
		else if (src == getComponent("pencilItem") ||
				src == getComponent("pencilBtn")) {
			JRadioButtonMenuItem pencilItem = (JRadioButtonMenuItem)getComponent("pencilItem");
			JToggleButton pencilBtn = (JToggleButton)getComponent("pencilBtn");
			pencilItem.setSelected(true);
			pencilBtn.setSelected(true);
			setDrawMode(DrawMode.PENCIL);
		} else if (src == getComponent("rectItem") ||
				src == getComponent("rectBtn")) {
			JRadioButtonMenuItem rectItem = (JRadioButtonMenuItem)getComponent("rectItem");
			JToggleButton rectBtn = (JToggleButton)getComponent("rectBtn");
			rectItem.setSelected(true);
			rectBtn.setSelected(true);
			setDrawMode(DrawMode.RECTANGLE);
		} else if (src == getComponent("circleItem") ||
				src == getComponent("circleBtn")) {
			JRadioButtonMenuItem circleItem = (JRadioButtonMenuItem)getComponent("circleItem");
			JToggleButton circleBtn = (JToggleButton)getComponent("circleBtn");
			circleItem.setSelected(true);
			circleBtn.setSelected(true);
			setDrawMode(DrawMode.CIRCLE);
		} else if (src == getComponent("fillItem") ||
				src == getComponent("fillBtn")) {
			JRadioButtonMenuItem fillItem = (JRadioButtonMenuItem)getComponent("fillItem");
			JToggleButton fillBtn = (JToggleButton)getComponent("fillBtn");
			fillItem.setSelected(true);
			fillBtn.setSelected(true);
			setDrawMode(DrawMode.FILL);
		}
		
		// TOOLKIT item listeners
		/*else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		}*/
		
		// HELP item listener
		else if (src == getComponent("aboutItem")) {
			new AboutDialog(fruitEditor);
		}
		
		fruitEditor.update();
	}
	
	/**==============================
	// ACTION EVENT METHODS. 
	//===============================*/
	private void openAction() {
		// Load OPEN dialog.
		JFileChooser open = makeFileChooser();
		
		int confirm = open.showOpenDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = open.getSelectedFile();
			String confirmStr = "Map load complete.";
			
			try {
				readText(file); // read the map file
				setStatus(confirmStr + "\t");
			} catch (Exception e) {
				System.err.println("ERROR: Could not read file " + file.getPath());
				e.printStackTrace();
			}
		}
	}
	
	/* TODO: This method has redundant functionality.
	 * Maybe shorten this later. */
	private void saveAction() {
		JFileChooser save = makeFileChooser();
		
		int confirm = save.showSaveDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = save.getSelectedFile();
			String confirmStr = "Map saved.";
			
			try {
				writeText(file);
				setStatus(confirmStr + "\t");
				//statusPanel.repaint();
			} catch (Exception e) {
				System.err.println("ERROR: Unable to write file " + file.getPath());
				e.printStackTrace();
			}
		}
	}

	private void saveAsAction() {
		JFileChooser saveAs = makeFileChooser();
		
		int confirm = saveAs.showSaveDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = saveAs.getSelectedFile();
			String confirmStr = "Map saved as " + file.getName();
			
			try {
				writeText(file);
				setStatus(confirmStr);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to write file " + file);
				e.printStackTrace();
			}
		}
	}
	
	private void closeAction() {
		System.exit(0);
	}
	
	private void undo() {
		fruitEditor.update();
	}
	
	private void redo() {
		fruitEditor.update();
	}
	
	private void cutAction() {
		
	}
	
	private void copyAction() {
		
	}
	
	private void pasteAction() {
		
	}
	
	private void deleteAction() {
		
	}
	
	/**================================
	// SHORTCUT METHODS.
	//=================================*/
	private void setStatus(String text) {
		fruitEditor.getStatusPanel().setStatus(text);
	}
	
	private void setGrid(boolean grid) {
		fruitEditor.getMapPanel().setGrid(grid);
	}
	
	private void setMode(EditorMode e) {
		fruitEditor.getMapPanel().setMode(e);
	}
	
	/*private void setScale(int scale) {
		Map map = fruitEditor.getMap();
		map.setScale(scale);
	}*/
	
	private void setDrawMode(DrawMode d) {
		Map map = fruitEditor.getMap();
		map.setDrawMode(d);
	}
	
	/**================================
	// STATE CHANGE METHODS
	//=================================**/
	public void stateChanged(ChangeEvent e) {
		
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		// For any action taken, place in a stack of actions
		actions.add(e);
	}
	
	/**===========================
	// FILE MANIPULATION METHODS. 
	//============================**/
	private void readText(File file) {
		try {
			// Load reader
			BufferedReader reader = new BufferedReader(
					new FileReader(file));
			
			// Read in the map and convert text files to map
			// Read first line to get tileset pathname
			String line = reader.readLine();
			String [] lines = line.split("\\s+");
			
			// Fetch tileset pathname
			String path = line;
			
			// Read second line to get width and height
			// Assume there are two positive integers
			line = reader.readLine();
			
			// Set width and height
			int cols = Integer.parseInt(lines[0]);
			int rows = Integer.parseInt(lines[1]);
			
			Map map = new Map(cols,rows);
			Tileset tileset = new Tileset(path);
			
			// Read the IDs of tiles next and store them in an int array
			int [][] ids = new int[rows][cols];
			int r, c;
			r = c = 0;
			
			while (!line.isEmpty()) {
				for (c = 0; c < lines.length; c++) {
					ids[r][c] = Integer.parseInt(lines[c]);
				}
				
				r++;
			}
			
			fruitEditor.getMapPanel().setMap(map);
			fruitEditor.getTilePanel().setTileset(tileset);
			
			// Close reader to cleanup
			reader.close();
		} catch (Exception e) {
			System.err.println("ERROR: Cannot read file " + file);
			e.printStackTrace();
		}
	}
	
	private void writeText(File file) {
		try {
			// Load writer
			PrintWriter writer = new PrintWriter(
				new BufferedWriter(new FileWriter(file)));
			Map map = fruitEditor.getMap();
			
			// Write in tileset used
			writer.println(fruitEditor.getTileset().getTilesetPath());
			
			// Write width and height
			writer.print(map.getWidth() + " ");
			writer.println(map.getHeight());
			
			// Write integer reps of tiles in for one layer only
			int [][] ids = map.getMapIntArray2();
			
			for (int r = 0; r < ids.length; r++) {
				for (int c = 0; c < ids[0].length; c++) {
					writer.print(ids[r][c] + " ");
				}
				writer.println();
			}
			
			// Flush and close writer as cleanup
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.err.println("ERROR: Unable to write file " + file);
			e.printStackTrace();
		}
	}
	
	/**============================
	// HELPER METHODS.
	//=============================**/
	/**============================
	// makeFileChooser() - make file chooser and filter files to take in FruitEditor files.
	//=============================**/
	private JFileChooser makeFileChooser() {
		JFileChooser jfc = new JFileChooser("../FruitEditor_v1_1");
		
		// FruitEditor and text files only.
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("FruitEditor Maps (*.fmp)", "fmp");
		FileNameExtensionFilter textfilter =
				new FileNameExtensionFilter("Text Files (*.txt)", "txt");
		
		// Add file filters.
		jfc.addChoosableFileFilter(filter);
		jfc.addChoosableFileFilter(textfilter);
		
		// Accept text and .fmp files only
		jfc.setAcceptAllFileFilterUsed(false);
		
		// Set to .fmp file filter
		jfc.setFileFilter(filter);
		
		// Set map name as default file name.
		if (fruitEditor.getMapPanel().getMapName() != null) {
			jfc.setSelectedFile(new File(fruitEditor.getMapPanel().getMapName()));
		}
		
		return jfc;
	}
	
	private JComponent getComponent(String text) {
		return fruitEditor.getComponent(text);
	}
}
