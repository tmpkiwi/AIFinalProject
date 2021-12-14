public class Face {

    public char[][] chars;
    public float[] pixelsAsVector; // 1D array version of chars
    private boolean isFace;
    

    public Face(char[][] chars) {
        this.chars = chars;
        this.pixelsAsVector = vector();
    }

    /**
     * Transforms the 2D array of pixels into a 1D vector of chars, then maps chars
     * to integers based on symbol.
     * ' ' -> 0
     * '#' -> 1
     * @return encoded vector of ints (floats)
     **/
    public float[] vector() {
         float[] encodedVector = new float[this.chars.length * this.chars[0].length];
         int k = 0;
        for (int i = 0; i < this.chars.length; i++) {
            for (int j = 0; j < this.chars[i].length; j++) {
                switch (chars[i][j]) {
                    case ' ': encodedVector[k++] = 0;
                    break; // blank
                    case '#': encodedVector[k++] = 1;
                    break; // grayscale
                }
             }
        }
         return encodedVector;
    }

    public char getPixel(int r, int c) {
        return chars[r][c];
    }

    public boolean isFace() {
        return isFace;
    }

    public void setFace(boolean val) {
        isFace = val;
    }

}
