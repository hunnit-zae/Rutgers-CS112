package art;

import java.awt.Color;

/*
 * This class contains methods to create and perform operations on a collage of images.
 * 
 * @author Ana Paula Centeno
 */ 

public class Collage {

    // The orginal picture
    private Picture originalPicture;

    // The collage picture is made up of tiles.
    // Each tile consists of tileDimension X tileDimension pixels
    // The collage picture has collageDimension X collageDimension tiles
    private Picture collagePicture;

    // The collagePicture is made up of collageDimension X collageDimension tiles
    // Imagine a collagePicture as a 2D array of tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    // Imagine a tile as a 2D array of pixels
    // A pixel has three components (red, green, and blue) that define the color 
    // of the pixel on the screen.
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 150
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public Collage (String filename) {

        // WRITE YOUR CODE HERE
        this.collageDimension = 4;
        this.tileDimension = 150;
        this.originalPicture = new Picture(filename);
        this.collagePicture = new Picture(this.tileDimension * this.collageDimension, this.tileDimension * this.collageDimension);

        for (int i = 0; i < (this.tileDimension * this.collageDimension); i++){
            for (int j = 0; j < (this.tileDimension * this.collageDimension); j++){
                int col = i * this.originalPicture.width() / (this.tileDimension * this.collageDimension);
                int row = j * this.originalPicture.height() / (this.tileDimension * this.collageDimension);
                Color color = this.originalPicture.get(col, row);
                this.collagePicture.set(i, j, color);
            }
        }
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes originalPicture with the filename image
     * 3. initializes collagePicture as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collagePicture to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */    
    public Collage (String filename, int td, int cd) {

        // WRITE YOUR CODE HERE
        this.collageDimension = cd;
        this.tileDimension = td;
        this.originalPicture = new Picture(filename);
        this.collagePicture = new Picture(td * cd, td * cd);

            for(int i = 0; i < (td * cd); i++){
                for(int j = 0; j < (td * cd); j++){
                    int col = i * this.originalPicture.width() / (td * cd);
                    int row = j * this.originalPicture.height() / (td * cd);
                    Color color = this.originalPicture.get(col, row);
                    this.collagePicture.set(i, j, color);
                }
            }
        }
               


    /*
     * Scales the Picture @source into Picture @target size.
     * In another words it changes the size of @source to make it fit into
     * @target. Do not update @source. 
     *  
     * @param source is the image to be scaled.
     * @param target is the 
     */
    public static void scale (Picture source, Picture target) {

        // WRITE YOUR CODE HERE
        int sWidth = source.width();
        int sHeight = source.height();
        int tWidth = target.width();
        int tHeight = target.height();

        for (int row = 0; row < tHeight; row ++) {
            for (int col = 0; col< tWidth; col++) {
                int sourcerow = row * sHeight / tHeight;
                int sourcecol = col * sWidth / tWidth;
                target.setRGB(col, row, source.getRGB(sourcecol, sourcerow));

            }
        }
    }
        

     /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */   
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */    
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    
    public Picture getOriginalPicture() {
        return originalPicture;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    
    public Picture getCollagePicture() {
        return collagePicture;
    }

    /*
     * Display the original image
     * Assumes that original has been initialized
     */    
    public void showOriginalPicture() {
        originalPicture.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */    
    public void showCollagePicture() {
	    collagePicture.show();
    }

    /*
     * Updates collagePicture to be a collage of tiles from original Picture.
     * collagePicture will have collageDimension x collageDimension tiles, 
     * where each tile has tileDimension X tileDimension pixels.
     */    
    public void makeCollage () {
        // WRITE YOUR CODE HERE
        Picture makeC = new Picture(this.tileDimension, this.tileDimension);
                
        for (int col = 0; col < this.tileDimension; col++){
            for (int row = 0; row < this.tileDimension; row++){
                int macol = col * this.originalPicture.width() / this.tileDimension;
                int marow = row * this.originalPicture.height() / this.tileDimension;
                Color color = this.originalPicture.get(macol, marow);
                makeC.set(col, row, color);
            }
        }
        
        int icol = 0;
        for(int col = 0; col < (this.tileDimension * this.collageDimension); col++){
            if(icol == this.tileDimension){
                icol = 0;
            }

            int jrow = 0;
            for(int row = 0; row < (this.tileDimension * this.collageDimension); row++){
                if(jrow == this.tileDimension){
                    jrow = 0;
                }
                    Color color = makeC.get(icol, jrow);
                    this.collagePicture.set(col, row, color);
                    jrow++;
                }
                icol++;
            }

        }
    /*
     * Colorizes the tile at (collageCol, collageRow) with component 
     * (see Week 9 slides, the code for color separation is at the 
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        int r = 0;
        int g = 0;
        int b = 0;

        int replaceColumn = collageCol * this.tileDimension;
        int replaceRow = collageRow * this.tileDimension;
        
        for (int col = replaceColumn; col < (replaceColumn + this.tileDimension); col++){
            for (int row = replaceRow; row < (replaceRow + this.tileDimension); row++){
                
                Color color = this.collagePicture.get(col, row);

                if (component == "red"){
                    r = color.getRed();
                    this.collagePicture.set(col, row, new Color(r, 0, 0));
                }
                else if (component == "green"){
                    g = color.getGreen();
                    this.collagePicture.set(col, row, new Color(0, g, 0));
                }
                else if (component == "blue"){
                   b = color.getBlue(); 
                   this.collagePicture.set(col, row, new Color(0, 0, b));
                }

                this.collagePicture.set(col, row, new Color(r, g, b));
            }
        }
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
            Picture picture = new Picture(filename);
            Picture replace = new Picture(tileDimension, tileDimension);

            scale(picture, replace);

            for (int row = 0; row < tileDimension; row++) {
                for (int col = 0; col < tileDimension; col++) {
                    this.collagePicture.setRGB(collageCol * tileDimension + col, collageRow * tileDimension + row,
                            replace.getRGB(col, row));
                }
            }
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {

        // WRITE YOUR CODE HERE
        int replaceCol = collageCol * this.tileDimension;
        int replaceRow = collageRow * this.tileDimension;
        
        for (int col = replaceCol; col < (replaceCol + this.tileDimension); col++){
            for (int row = replaceRow; row < (replaceRow + this.tileDimension); row++){

                Color color = this.collagePicture.get(col, row);
                Color gray = Collage.toGray(color);
                this.collagePicture.set(col, row, gray);
            }
        }
    }

    /**
     * Returns the monochrome luminance of the given color as an intensity
     * between 0.0 and 255.0 using the NTSC formula
     * Y = 0.299*r + 0.587*g + 0.114*b. If the given color is a shade of gray
     * (r = g = b), this method is guaranteed to return the exact grayscale
     * value (an integer with no floating-point roundoff error).
     *
     * @param color the color to convert
     * @return the monochrome luminance (between 0.0 and 255.0)
     */
    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        if (r == g && r == b) return r;   // to avoid floating-point issues
        return 0.299*r + 0.587*g + 0.114*b;
    }

    /**
     * Returns a grayscale version of the given color as a {@code Color} object.
     *
     * @param color the {@code Color} object to convert to grayscale
     * @return a grayscale version of {@code color}
     */
    private static Color toGray(Color color) {
        int y = (int) (Math.round(intensity(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    /*
     * Closes the image windows
     */
    public void closeWindow () {
        if ( originalPicture != null ) {
            originalPicture.closeWindow();
        }
        if ( collagePicture != null ) {
            collagePicture.closeWindow();
        }
    }
}
