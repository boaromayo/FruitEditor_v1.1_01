package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FruitEditor {
	// DIMENSION CONSTANTS.
	public static final int SCREEN_WIDTH = 960;
	public static final int SCREEN_HEIGHT = 640;
	
	// PROPERTIES.
	private boolean grid;
	
	// MAIN FRAME.
	private JFrame fruitFrame;
	
	// PANELS.
	private JPanel fruitPanel;
	private JPanel toolbarPanel;
	
	// MENU NAMES.
	private String[] menuName = {"File", "Edit", "View", "Draw", "FruitTools", "Help"};
	
	// MENU & TOOLBAR LISTENER.
	private FruitListener fruitListener;
	
	// MAP FILE
	private Map mapFile;
	
	// MENU COMPONENTS: The Main Menu Bar
	private JMenuBar menuBar;
	
	// MENU COMPS: The Menus
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenu drawMenu;
	private JMenu toolMenu;
	private JMenu helpMenu;
	
	// MENU COMPS: The Sub-Menus
	private JMenu modeMenu;
	private JMenu scaleMenu;
	
	// MENU COMPS: The Menu Toolbar
	private JToolBar mainToolBar;
	
	// MENU COMPS: The Menu Groups
	private ButtonGroup scalegrp;
	private ButtonGroup modegrp;
	private ButtonGroup drawgrp;
	private ButtonGroup scaleBtnGrp;
	private ButtonGroup modeBtnGrp;
	private ButtonGroup drawBtnGrp;
	
	// MENU COMPS: Menu items
	// MENU: FILE
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem closeItem;
	// MENU: EDIT -> FIX
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	// MENU: EDIT
	private JMenuItem cutItem;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JMenuItem deleteItem;
	// MENU: VIEW -> SCALE
	private JRadioButtonMenuItem oneItem;
	private JRadioButtonMenuItem twoItem;
	private JRadioButtonMenuItem fourItem;
	private JRadioButtonMenuItem eightItem;
	// MENU: VIEW -> MODE
	private JRadioButtonMenuItem mapModeItem;
	private JRadioButtonMenuItem eventModeItem;
	// MENU: VIEW
	private JCheckBoxMenuItem gridItem;
	// MENU: DRAW
	private JRadioButtonMenuItem pencilItem;
	private JRadioButtonMenuItem rectItem;
	private JRadioButtonMenuItem circleItem;
	private JRadioButtonMenuItem fillItem;
	// MENU: TOOLKIT
	private JMenuItem databaseItem;
	private JMenuItem mapConvertItem;
	private JMenuItem resourceItem;
	private JMenuItem configItem;
	// MENU: HELP
	private JMenuItem aboutItem;
	
	// MENU COMPS: Tool buttons
	// FILE
	private JButton newBtn;
	private JButton openBtn;
	private JButton saveBtn;
	// EDIT
	private JButton cutBtn;
	private JButton copyBtn;
	private JButton pasteBtn;
	private JButton deleteBtn;
	// EDIT -> FIX
	private JButton undoBtn;
	private JButton redoBtn;
	// VIEW -> SCALE
	private JToggleButton oneBtn;
	private JToggleButton twoBtn;
	private JToggleButton fourBtn;
	private JToggleButton eightBtn;
	// VIEW -> MODE
	private JToggleButton mapModeBtn;
	private JToggleButton eventModeBtn;
	// VIEW
	private JToggleButton gridBtn;
	// DRAW
	private JToggleButton pencilBtn;
	private JToggleButton rectBtn;
	private JToggleButton circleBtn;
	private JToggleButton fillBtn;
	// FRUITTOOLS/TOOLKIT
	private JButton cherryBtn;
	private JButton orangeBtn;
	private JButton limeBtn;
	
	public FruitEditor() {
		fruitFrame = new JFrame();
		
		fruitFrame.setTitle("FruitEditor");
		fruitFrame.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		fruitFrame.setLayout(new BorderLayout());
		
		fruitListener = new FruitListener(this);
		
		// Set grid on.
		grid = true;
		
		// Setup the editor menu.
		menuSetup();
		
		// Setup the toolbar.
		toolbarSetup();

		// Initialize panels.
		panelSetup();
		
		fruitFrame.pack();
		
		fruitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fruitFrame.setLocationRelativeTo(null);
		fruitFrame.setIconImage(FruitImgLoader.loadBufferedImage("", 20, 20));
		fruitFrame.setVisible(true);
		fruitFrame.setResizable(false);
	}
	
	private void panelSetup() {
	    fruitPanel = new FruitPanel(this);

	    fruitFrame.add(fruitPanel, BorderLayout.CENTER);
	}
	
	/**================================
	// menuSetup() - Set up main menu and main panel.
	//================================**/
	private void menuSetup() {
		// Assign main menu bar.
		menuBar = new JMenuBar();
			
		// Assign the menus.
		fileMenu = new JMenu(menuName[0]);			// FILE
		editMenu = new JMenu(menuName[1]);			// EDIT
		viewMenu = new JMenu(menuName[2]);			// VIEW
		drawMenu = new JMenu(menuName[3]);			// DRAW
		toolMenu = new JMenu(menuName[4]);			// TOOLKIT
		helpMenu = new JMenu(menuName[5]);			// HELP
			
		// Disable other menus if no map is loaded.
		//if (mapFile == null) {
			disableMenus();
		//}
			
		// Create menu shortcuts.
		fileMenu.setMnemonic(menuName[0].charAt(0));
		editMenu.setMnemonic(menuName[1].charAt(0));
		viewMenu.setMnemonic(menuName[2].charAt(0));
		drawMenu.setMnemonic(menuName[3].charAt(0));
		toolMenu.setMnemonic(menuName[4].charAt(5)); // Make 'T' menu shortcut
				
		subSetup(); // Get and add the menus.
			
		// Set the menuBar for the frame.
		fruitFrame.setJMenuBar(menuBar);
	}
		
	/**==================================================
	// disableMenus() - Disable the menus.
	//==================================================**/
	private void disableMenus() {
		editMenu.setEnabled(false);
		viewMenu.setEnabled(false);
		drawMenu.setEnabled(false);
		toolMenu.setEnabled(false);
	}
		
	/**==================================================
	// subSetup() - Get and add the methods that will be run for each menu.
	//==================================================**/
	private void subSetup() {
		fileSetup();	
		editSetup();
		viewSetup();
		drawSetup();
		toolSetup();
		helpSetup();
			
		// Add menus to menuBar.
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(drawMenu);
		menuBar.add(toolMenu);
		menuBar.add(helpMenu);
	}
	
	//=========================================
	// Setup the menu items for each menu.
	//=========================================
	private void fileSetup() {
		// FILE MENU ITEMS
		newItem = new JMenuItem("New");				// FILE -> NEW
		openItem = new JMenuItem("Open");			// FILE -> OPEN
		saveItem = new JMenuItem("Save");			// FILE -> SAVE
		saveAsItem = new JMenuItem("Save As...");   // FILE -> SAVE AS
		closeItem = new JMenuItem("Close");			// FILE -> CLOSE
		
		// Add in FILE ActionListeners
		newItem.addActionListener(fruitListener);
		openItem.addActionListener(fruitListener);
		//saveItem.addActionListener(fruitListener);
		//saveAsItem.addActionListener(fruitListener);
		closeItem.addActionListener(fruitListener);
		
		// Add in accelerator keys.
		makeShortcut(newItem, KeyEvent.VK_N, "CTRL");
		makeShortcut(openItem, KeyEvent.VK_O, "CTRL");
		makeShortcut(saveItem, KeyEvent.VK_S, "CTRL");
		
		// Case for FILE -> SAVE and FILE -> SAVE AS
		//if (mapFile == null) {
			saveItem.setEnabled(false);
			saveAsItem.setEnabled(false);
		//}
		
		// Add in components.
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		// FILE SEPARATOR.
		fileMenu.addSeparator();
		// Add in components.
		fileMenu.add(closeItem);
	}
	
	private void editSetup() {
		// EDIT MENU ITEMS
		// EDIT -> FIX
		undoItem = new JMenuItem("Undo");			// EDIT -> UNDO
		redoItem = new JMenuItem("Redo");			// EDIT -> REDO
		// EDIT
		cutItem = new JMenuItem("Cut");				// EDIT -> CUT
		copyItem = new JMenuItem("Copy");			// EDIT -> COPY
		pasteItem = new JMenuItem("Paste");			// EDIT -> PASTE
		deleteItem = new JMenuItem("Delete");		// EDIT -> DELETE
		
		// Add in EDIT ActionListeners.
		/*undoItem.addActionListener(fruitListener);
		redoItem.addActionListener(fruitListener);
		cutItem.addActionListener(fruitListener);
		copyItem.addActionListener(fruitListener);
		pasteItem.addActionListener(fruitListener);
		deleteItem.addActionListener(fruitListener);*/
		
		// Add in accelerator keys.
		makeShortcut(undoItem, KeyEvent.VK_Z, "CTRL");
		makeShortcut(redoItem, KeyEvent.VK_Y, "CTRL");
		makeShortcut(cutItem, KeyEvent.VK_X, "CTRL");
		makeShortcut(copyItem, KeyEvent.VK_C, "CTRL");
		makeShortcut(pasteItem, KeyEvent.VK_V, "CTRL");
		makeShortcut(deleteItem, KeyEvent.VK_DELETE);
		
		// Add in components.
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		// FILE SEPARATOR.
		editMenu.addSeparator();
		// Add in components.
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(deleteItem);
	}
	
	private void viewSetup() {
		// VIEW SUB-METHODS AND MENU ITEMS
		scaleSetup();
		modeSetup();
		// VIEW
		gridItem = new JCheckBoxMenuItem("Show/Hide Grid");			// VIEW -> GRID
		
		// Add in VIEW ActionListeners.
		//gridItem.addActionListener(fruitListener);

		// Set grid on.
		gridItem.setState(true);
		
		// Add in components.
		viewMenu.add(scaleMenu);
		// MENU SEPARATOR.
		viewMenu.addSeparator();
		// Add in components.
		viewMenu.add(modeMenu);
		// MENU SEPARATOR.
		viewMenu.addSeparator();
		// Add in component.
		viewMenu.add(gridItem);
	}
	
	private void scaleSetup() {
		// VIEW -> SCALE SUB-MENU AND MENU ITEMS
		scaleMenu = new JMenu("Scale...");
		// VIEW -> SCALE BUTTONGROUP
		scalegrp = new ButtonGroup();
		// VIEW -> SCALE
		oneItem = new JRadioButtonMenuItem("1:1");					// VIEW -> SCALE -> 1:1
		twoItem = new JRadioButtonMenuItem("1:2");					// VIEW -> SCALE -> 1:2
		fourItem = new JRadioButtonMenuItem("1:4");					// VIEW -> SCALE -> 1:4
		eightItem = new JRadioButtonMenuItem("1:8");				// VIEW -> SCALE -> 1:8
		
		// Add VIEW -> SCALE items to group.
		scalegrp.add(oneItem);
		scalegrp.add(twoItem);
		scalegrp.add(fourItem);
		scalegrp.add(eightItem);
		
		// Add in VIEW -> SCALE ActionListeners.
		//oneItem.addActionListener(fruitListener);
		//twoItem.addActionListener(fruitListener);
		//fourItem.addActionListener(fruitListener);
		//eightItem.addActionListener(fruitListener);
		
		// Add in sub-menu components.
		scaleMenu.add(oneItem);
		scaleMenu.add(twoItem);
		scaleMenu.add(fourItem);
		scaleMenu.add(eightItem);
		
		// Set 1:1 as default.
		oneItem.setSelected(true);
	}
	
	private void modeSetup() {
		// VIEW -> MODE SUB-MENU AND MENU ITEMS
		modeMenu = new JMenu("Mode...");
		// VIEW -> MODE BUTTONGROUP
		modegrp = new ButtonGroup();
		// VIEW -> MODE
		mapModeItem 	= new JRadioButtonMenuItem("Map Mode");			// VIEW -> MODE -> MAP MODE
		eventModeItem 	= new JRadioButtonMenuItem("Event Mode");		// VIEW -> MODE -> EVENT MODE
		
		// Add in VIEW -> MODE items to group.
		modegrp.add(mapModeItem);
		modegrp.add(eventModeItem);
		
		// Add in VIEW -> MODE ActionListeners.
		//mapModeItem.addActionListener(fruitListener());
		//eventModeItem.addActionListener(fruitListener());
		
		// Add in sub-menu components.
		modeMenu.add(mapModeItem);
		modeMenu.add(eventModeItem);
		
		// Set map mode as default.
		mapModeItem.setSelected(true);
	}
	
	private void drawSetup() {
		// DRAW BUTTONGROUP AND MENU ITEMS
		drawgrp = new ButtonGroup();
		// DRAW MENU ITEMS
		pencilItem 	= new JRadioButtonMenuItem("Pencil");			// DRAW -> PENCIL
		rectItem   	= new JRadioButtonMenuItem("Rectangle");		// DRAW -> RECTANGLE
		circleItem	= new JRadioButtonMenuItem("Circle");			// DRAW -> CIRCLE
		fillItem	= new JRadioButtonMenuItem("Flood Fill");		// DRAW -> FILL
		
		// Add in DRAW ActionListeners.
		/*pencilItem.addActionListener(fruitListener());
		rectItem.addActionListener(fruitListener());
		circleItem.addActionListener(fruitListener());
		fillItem.addActionListener(fruitListener());*/
		
		// Add DRAW items to group.
		drawgrp.add(pencilItem);
		drawgrp.add(rectItem);
		drawgrp.add(circleItem);
		drawgrp.add(fillItem);
		
		// Add in menu components.
		drawMenu.add(pencilItem);
		drawMenu.add(rectItem);
		drawMenu.add(circleItem);
		drawMenu.add(fillItem);
		
		// Set pencil mode as default.
		pencilItem.setSelected(true);
	}
	
	private void toolSetup() {
		// TOOLKIT MENU ITEMS
		databaseItem	= new JMenuItem("Cherry DataBase");			// TOOLKIT -> CHERRY DATABASE
		mapConvertItem	= new JMenuItem("Orange MapConverter");		// TOOLKIT -> ORANGE MAPCONVERT
		resourceItem 	= new JMenuItem("Lime ResourceBase");		// TOOLKIT -> LIME RESOURCEBASE
		configItem		= new JMenuItem("Settings...");				// TOOLKIT -> SETTINGS
		
		// Add in TOOLKIT ActionListeners.
		//databaseItem.addActionListener(fruitListener());
		//mapConvertItem.addActionListener(fruitListener());
		//resourceItem.addActionListener(fruitListener());
		//configItem.addActionListener(fruitListener());
		
		// Add in menu components.
		toolMenu.add(databaseItem);
		toolMenu.add(mapConvertItem);
		toolMenu.add(resourceItem);
		// MENU SEPARATOR.
		toolMenu.addSeparator();
		// Add in menu components.
		toolMenu.add(configItem);
	}
	
	private void helpSetup() {
		// HELP MENU ITEMS
		aboutItem = new JMenuItem("About...");			// HELP -> ABOUT
		
		// Add in HELP ActionListener.
		aboutItem.addActionListener(fruitListener);
		
		// Add in menu components.
		helpMenu.add(aboutItem);
	}
	
	/**=========================================
	// toolbarSetup() - Setup the tool buttons for toolbar.
	//=========================================**/
	private void toolbarSetup() {
		// Initialize toolbar panel.
		toolbarPanel = new JPanel();
		toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// Initialize toolbar.
		mainToolBar = new JToolBar();
		mainToolBar.setRollover(true);		// Make an indication if mouse goes over toolbar buttons
		mainToolBar.setFloatable(false);	// Do not let it be movable
		
		// Create and add the buttons in toolbar.
		subToolbarSetup();
		
		// Disable tool buttons if no map is loaded.
		//if (mapFile == null) {
			disableTools();		
		//}
		
		// add toolbar to toolbarPanel.
		toolbarPanel.add(mainToolBar, BorderLayout.CENTER);
		
		// add toolbarPanel to frame.
		fruitFrame.add(toolbarPanel, BorderLayout.NORTH);
	}
	
	/**=========================================
	// disableTools() - Disable tool buttons.
	//=========================================**/
	private void disableTools() {
		saveBtn.setEnabled(false);
		
		cutBtn.setEnabled(false);
		copyBtn.setEnabled(false);
		pasteBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		
		undoBtn.setEnabled(false);
		redoBtn.setEnabled(false);
		
		gridBtn.setEnabled(false);
		
		oneBtn.setEnabled(false);
		twoBtn.setEnabled(false);
		fourBtn.setEnabled(false);
		eightBtn.setEnabled(false);
		
		mapModeBtn.setEnabled(false);
		eventModeBtn.setEnabled(false);
		
		pencilBtn.setEnabled(false);
		rectBtn.setEnabled(false);
		circleBtn.setEnabled(false);
		fillBtn.setEnabled(false);
		
		cherryBtn.setEnabled(false);
		orangeBtn.setEnabled(false);
		limeBtn.setEnabled(false);
	}
	
	/**=========================================
	// subToolbarSetup() - Add in the tool buttons for toolbar.
	//=========================================**/
	private void subToolbarSetup() {
		fileToolSetup();
		editToolSetup();
		fixToolSetup();
		viewToolSetup();
		drawToolSetup();
		toolkitToolSetup();
	}
	
	//=========================================
	// Setup the buttons for each toolbar section.
	//=========================================
	private void fileToolSetup() {
		// FILE BUTTONS
		newBtn 		= makeButton("N", "../img/newfile.png",
				"New", "newBtn");
		openBtn 	= makeButton("O", "../img/openfile.png",
				"Open", "openBtn");
		saveBtn		= makeButton("S", "../img/savefile.png",
				"Save", "saveBtn");
		
		// Add in FILE buttons.
		mainToolBar.add(newBtn);						// FILE -> NEW
		mainToolBar.add(openBtn);						// FILE -> OPEN
		mainToolBar.add(saveBtn);						// FILE -> SAVE

		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
		
		// Add in FILE ActionListeners.
		newBtn.addActionListener(fruitListener);
		openBtn.addActionListener(fruitListener);
		//saveBtn.addActionListener(fruitListener);
	}
	
	private void editToolSetup() {
		// EDIT BUTTONS
		cutBtn		= makeButton("Cut", "Cut", "cutBtn");			// EDIT -> CUT
		copyBtn		= makeButton("Copy", "Copy", "copyBtn");		// EDIT -> COPY
		pasteBtn	= makeButton("Paste", "Paste", "pasteBtn");		// EDIT -> PASTE
		deleteBtn	= makeButton("X", "../img/delete.png", 
				"Delete", "deleteBtn");								// EDIT -> DELETE
		
		// Add in EDIT buttons.
		mainToolBar.add(cutBtn);
		mainToolBar.add(copyBtn);
		mainToolBar.add(pasteBtn);
		mainToolBar.add(deleteBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void fixToolSetup() {
		// EDIT -> FIX BUTTONS
		undoBtn		= makeButton("U", "../img/undo.png", 
									"Undo", "undoBtn");		// EDIT -> FIX -> UNDO
		redoBtn		= makeButton("R", "../img/redo.png", 
									"Redo", "redoBtn");		// EDIT -> FIX -> REDO
		
		// Add in EDIT -> FIX buttons.
		mainToolBar.add(undoBtn);
		mainToolBar.add(redoBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void viewToolSetup() {
		// VIEW SUB-METHODS
		scaleToolSetup();
		modeToolSetup();
		// VIEW BUTTONS
		gridBtn = makeButton("G", "", "Show/Hide Grid", "gridBtn", true);
	
		// Set VIEW button on.
		gridBtn.setSelected(true);
		
		// Add in VIEW button.
		mainToolBar.add(gridBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void scaleToolSetup() {
		// SCALE BUTTONGROUP
		scaleBtnGrp	= new ButtonGroup();
		// SCALE BUTTONS
		oneBtn		= makeButton("1:1", "", "Scale 1:1", "oneBtn", true);
		twoBtn		= makeButton("1:2", "", "Scale 1:2", "twoBtn", true);
		fourBtn		= makeButton("1:4", "", "Scale 1:4", "fourBtn", true);
		eightBtn	= makeButton("1:8", "", "Scale 1:8", "eightBtn", true);
		
		// Add in SCALE buttons to group.
		scaleBtnGrp.add(oneBtn);
		scaleBtnGrp.add(twoBtn);
		scaleBtnGrp.add(fourBtn);
		scaleBtnGrp.add(eightBtn);
		
		// Set 1:1 as default
		oneBtn.setSelected(true);
		
		// Add in SCALE buttons.
		mainToolBar.add(oneBtn);
		mainToolBar.add(twoBtn);
		mainToolBar.add(fourBtn);
		mainToolBar.add(eightBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void modeToolSetup() {
		// MODE BUTTONGROUP
		modeBtnGrp = new ButtonGroup();
		// MODE BUTTONS
		mapModeBtn	 = makeButton("MAP", "", "Map Mode", "mapModeBtn", true);
		eventModeBtn = makeButton("EV", "", "Event Mode", "eventModeBtn", true);
		
		// Add in MODE buttons to group.
		modeBtnGrp.add(mapModeBtn);
		modeBtnGrp.add(eventModeBtn);
		
		// Set map mode as default.
		mapModeBtn.setSelected(true);
		
		// Add in MODE buttons.
		mainToolBar.add(mapModeBtn);
		mainToolBar.add(eventModeBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void drawToolSetup() {
		// DRAW BUTTONGROUP
		drawBtnGrp	= new ButtonGroup();
		// DRAW BUTTONS
		pencilBtn 	= makeButton("Penc", "", "Pencil", "pencilBtn", true);
		rectBtn		= makeButton("Rect", "", "Rectangle", "rectBtn", true);
		circleBtn	= makeButton("Circ", "", "Circle", "circleBtn", true);
		fillBtn		= makeButton("Fill", "", "Flood Fill", "fillBtn", true);
		
		// Add in DRAW buttons to group.
		drawBtnGrp.add(pencilBtn);
		drawBtnGrp.add(rectBtn);
		drawBtnGrp.add(circleBtn);
		drawBtnGrp.add(fillBtn);
		
		// Set pencil mode as default.
		pencilBtn.setSelected(true);
		
		// Add in DRAW buttons.
		mainToolBar.add(pencilBtn);
		mainToolBar.add(rectBtn);
		mainToolBar.add(circleBtn);
		mainToolBar.add(fillBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void toolkitToolSetup() {
		// TOOLKIT BUTTONS
		cherryBtn	= makeButton("Cherry", "Cherry DataBase", "cherryBtn");
		orangeBtn	= makeButton("Orange", "Orange MapConvert", "orangeBtn");
		limeBtn		= makeButton("Lime", "Lime ResourceBase", "limeBtn");
		
		// Add in TOOLKIT buttons.
		mainToolBar.add(cherryBtn);
		mainToolBar.add(orangeBtn);
		mainToolBar.add(limeBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	/**========================================
	// toggleGrid() - Set grid on/off.
	//=========================================**/
	public void toggleGrid() {
		grid = !grid;
	}
	
	/**========================================
	// getButton(text) - Get button.
	//=========================================**/
	public JButton getButton(String text) {
		// FILE buttons
		if (text.equals(newBtn.getName())) {
			return newBtn;
		} else if (text.equals(openBtn.getName())) {
			return openBtn;
		} else if (text.equals(saveBtn.getName())) {
			return saveBtn;
		}
		
		// EDIT -> FIX buttons
		else if (text.equals(undoBtn.getName())) {
			return undoBtn;
		} else if (text.equals(redoBtn.getName())) {
			return redoBtn;
		}
		
		// EDIT buttons
		else if (text.equals(cutBtn.getName())) {
			return cutBtn;
		} else if (text.equals(copyBtn.getName())) {
			return copyBtn;
		} else if (text.equals(pasteBtn.getName())) {
			return pasteBtn;
		} else if (text.equals(deleteBtn.getName())) {
			return deleteBtn;
		}
		
		return null;
	}
	
	/**========================================
	// getFrame() - Get JFrame.
	//=========================================**/
	public JFrame getFrame() {
		return fruitFrame;
	}
	
	/**========================================
	// getMap() - Get Map file. 
	//=========================================**/
	public Map getMap() {
		return mapFile;
	}
	
	/**=======================================
	// grid() - Check if grid on.
	//========================================**/
	public boolean grid() { return grid; }
	
	/**========================================
	// HELPER METHODS.
	//=========================================**/
	/**=========================================
	// makeButton(text,tooltip,varname) - Make text button for the toolbar, assume not a toggle btn.
	//=========================================**/
	private JButton makeButton(String text, String tooltip, String varName) {
		return makeButton(text, null, tooltip, varName);
	}
	
	/**=========================================
	// makeButton(text,icon,tooltip,varname) - Make button for the toolbar.
	//=========================================**/
	private JButton makeButton(String text, String icon, String tooltip, String varName) {
		JButton btn;
		// Add in button, make text if icon unavailable.
		if (icon != null) {
			btn = new JButton(FruitImgLoader.loadIconImage(icon));
		} else {
			btn = new JButton(text);
		}
		
		//btn = new JButton(text);
		btn.setName(varName);
		btn.setToolTipText(tooltip);

		// Add button ActionListener.
		//btn.addActionListener(fruitListener);
		
		// Ensure the buttons are added in toolbar.
		System.out.println(btn.getName() + " added in toolbar");
		
		return btn;
	}
	
	/**=========================================
	// makeButton(text,icon,tooltip,varname,toggle) - Make toggle button for the toolbar.
	//=========================================**/
	private JToggleButton makeButton(String text, String icon, String tooltip, String varName, boolean toggle) {
		JToggleButton btn;
		// Add in toggle btn, make text if icon unavailable.
		if (toggle) {
			try {
				//btn = new JToggleButton(FruitImgLoader.loadIconImage(icon));
			} catch (Exception e) {
				//btn = new JToggleButton(text);
			}
			
			btn = new JToggleButton(text);
			btn.setName(varName);
			btn.setToolTipText(tooltip);
	
			// Add button ActionListener.
			//btn.addActionListener(fruitListener);
			
			// Ensure the buttons are added in toolbar.
			System.out.println(btn.getName() + " added in toolbar");
			
			return btn;
		}
		
		return null;
	}
	
	/**=========================================
	// makeShortcut(menu,key) - Make menu accelerator shortcut.
	//=========================================**/
	private void makeShortcut(JMenuItem menu, int key) {
		makeShortcut(menu, key, "");
	}
	
	/**=========================================
	// makeShortcut(menu,key,mask) - Make menu accelerator shortcut + key mask.
	//=========================================**/
	private void makeShortcut(JMenuItem menu, int key, String mask) {
		// Branch conditions based off of key mask
		if (mask.equals("CTRL")) {
			try {
				// Get KeyStroke for key and add in accelerator
				KeyStroke k = KeyStroke.getKeyStroke(key, ActionEvent.CTRL_MASK);
				menu.setAccelerator(k);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		} else if (mask.equals("ALT")) {
			try {
				// Get KeyStroke for key and add in accelerator
				KeyStroke k = KeyStroke.getKeyStroke(key, ActionEvent.ALT_MASK);
				menu.setAccelerator(k);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		} else {
			try {
				// Get KeyStroke for key and add in accelerator
				KeyStroke k = KeyStroke.getKeyStroke(key, 0);
				menu.setAccelerator(k);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		}
	}
}