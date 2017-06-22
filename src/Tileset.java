package FruitEditor;

import java.io.*;

import java.awt.*;
import java.awt.image.*;

public class Tileset {
	// TILESET IMAGE.
	private BufferedImage tilesetImg;
	
	// TILESET PATHS.
	private String tilesetPath;
	private String notePath; // text file to label the types (and properties if necessary) of tiles in tileset
	
	// TILES.
	private Tile[][] fruitTiles;
	
	// TILESET DIMENSIONS.
	private int tilesetWidth;
	private int tilesetHeight;
	
	// TILE DIMENSIONS.
	private int tileWidth;
	private int tileHeight;
	
	public Tileset() {
		tileWidth = FruitEditor.GRID_SIZE;
		tileHeight = FruitEditor.GRID_SIZE;
		
		tilesetWidth = tileWidth;
		tilesetHeight = tileHeight;
		
		int tw = (int)(tilesetWidth / tileWidth);
		int th = (int)(tilesetHeight / tileHeight);
		
		fruitTiles = new Tile[th][tw]; // Set number of tiles based on (tileset size / grid size).
		fruitTiles[0][0] = new Tile(); // Set first tile as blank tile.
		
		tilesetImg = null;
		
		tilesetPath = "";	
	}
	
	public Tileset(String path) {
		tileWidth = FruitEditor.GRID_SIZE;
		tileHeight = FruitEditor.GRID_SIZE;
		
		tilesetImg = FruitImgBank.get().
				loadBufferedImage(path);
		
		tilesetWidth = tilesetImg.getWidth();
		tilesetHeight = tilesetImg.getHeight();
		
		int cols = (int)(tilesetWidth / tileWidth);
		int rows = (int)(tilesetHeight / tileHeight);
		
		fruitTiles = new Tile[rows][cols];
		
		initTiles(path);
	}
	
	public Tileset(String path, String note) {
		tileWidth = FruitEditor.GRID_SIZE;
		tileHeight = FruitEditor.GRID_SIZE;
		
		tilesetImg = FruitImgBank.get().
				loadBufferedImage(path);
		
		tilesetWidth = tilesetImg.getWidth();
		tilesetHeight = tilesetImg.getHeight();
		
		int cols = (int)(tilesetWidth / tileWidth);
		int rows = (int)(tilesetHeight / tileHeight);
		
		fruitTiles = new Tile[rows][cols];
		
		initTiles(path,note);
	}
	
	public Tileset(String path, int tw, int th) {
		tileWidth = tw;
		tileHeight = th;
		
		tilesetImg = FruitImgBank.get().
				loadBufferedImage(path);
		
		tilesetWidth = tilesetImg.getWidth();
		tilesetHeight = tilesetImg.getHeight();
		
		int cols = (int)(tilesetWidth / tileWidth);
		int rows = (int)(tilesetHeight / tileHeight);
		
		fruitTiles = new Tile[rows][cols];
		
		initTiles(path);
	}
	
	public Tileset(String path, String note, int tw, int th) {
		this(path,note);
		tileWidth = tw;
		tileHeight = th;
	}
	
	public void initTiles(String path) {
		int r, c; // Loop counters.
		int i = 0; // Tile id counter.
		int rows = fruitTiles.length;
		int cols = fruitTiles[0].length;
		
		tilesetPath = path;
		notePath = "default.txt"; // filler String here
		
		BufferedImage[][] tileImg = getTilesetImages();
		
		for (r=0; r < rows; r++) {
			for (c=0; c < cols; c++, i++) {
				fruitTiles[r][c] = new Tile(i, tileImg[r][c], "None");
			}
		}
	}
	
	public void initTiles(String path, String note) {
		int r, c; // Loop counters.
		int i = 0; // Tile id counter.
		int rows = fruitTiles.length;
		int cols = fruitTiles[0].length;
		
		tilesetPath = path;
		notePath = note;
		
		BufferedImage[][] tileImg = getTilesetImages(tilesetImg);
		
		try {
			File noteFile = new File(notePath);
			BufferedReader br = new BufferedReader(new FileReader(noteFile));
			String line;
			String [] lines = new String[rows*cols];
			
			if (tilesetPath == br.readLine()) {
				line = br.readLine();
				while (line != null) {
					lines[i++] = line; // Increment counter after putting line into array.
					line = br.readLine(); // Go to next line.
				}
				
				i = 0;
				
				for (r=0; r < rows; r++) {
					for (c=0; c < cols; c++, i++) {
						line = lines[i];
						
						if (line == null) {
							System.err.println("WARNING: There is no name for the selected tile.");
							line = "None";
						}
						
						fruitTiles[r][c] = new Tile(i, tileImg[r][c], line);
					}
				}
			} else {
				throw new Exception("ERROR: " + notePath + " does not match file " + tilesetPath);
			}
			
			br.close();
		} catch (Exception e) {
			System.err.println("ERROR: Could not read file " + tilesetPath);
			System.exit(1);
		}
	}
	
	public void draw(Graphics g, int x, int y, Dimension size) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, tilesetWidth, tilesetHeight);
		
		if (tilesetImg != null) {
			g.drawImage(tilesetImg, 0, 0, tilesetWidth, tilesetHeight, null);
		}
	}
	
	public void setTileWidth(int tw) { tileWidth = tw; }
	
	public void setTileHeight(int th) { tileHeight = th; }
	
	public Tile getTile(int i) {
		int r = i / getCols();
		int c = i % getCols();
		
		return fruitTiles[r][c];
	}
	
	public Tile getTile(int r, int c) { return fruitTiles[r][c]; }
	
	public Tile[][] getTileset() { return fruitTiles; }
	
	public int getWidth() { return tilesetWidth; }
	
	public int getHeight() { return tilesetHeight; }
	
	public int getTileWidth() { return tileWidth; }
	
	public int getTileHeight() { return tileHeight; }

	public int getCols() { return fruitTiles[0].length; }
	
	public String getTilesetPath() { return tilesetPath; }
	
	public BufferedImage[][] getTilesetImages() {
		return getTilesetImages(tilesetPath);
	}
	
	private BufferedImage[][] getTilesetImages(String path) {
		int rows = fruitTiles.length;
		int cols = fruitTiles[0].length;
		BufferedImage[][] tileImg = new BufferedImage[rows][cols];
		
		int i, j; // Loop counters
		
		for (i=0; i < rows; i++) {
			for (j=0; j < cols; j++) {
				tileImg[i][j] = FruitImgBank.get().
						loadBufferedImage(path, j*tileWidth, i*tileHeight, tileWidth, tileHeight);
			}
		}
		
		return tileImg;
	}
	
	private BufferedImage[][] getTilesetImages(BufferedImage img) {
		int rows = fruitTiles.length;
		int cols = fruitTiles[0].length;
		BufferedImage[][] tileImg = new BufferedImage[rows][cols];
		
		int i, j; // Loop counters
		
		for (i=0; i < rows; i++) {
			for (j=0; j < cols; j++) {
				tileImg[i][j] = img.getSubimage(j*tileWidth, i*tileHeight, tileWidth, tileHeight);
			}
		}
		
		return tileImg;
	}
}
