public class Digit {

    public char[][] chars;
    public int value;
    public float[] pixelsAsVector;

    public Digit(char[][] chars) {
        this.chars = chars;
        this.pixelsAsVector = vector();
    }

    /**
     * Transforms the 2D array of pixels into a 1D vector of chars, then maps chars
     * to integers based on symbol.
     * ' ' -> 0
     * '#' -> 1
     * '+' -> 2
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
                   case '+': encodedVector[k++] = 2;
                   break; // black
               }
            }
       }
        return encodedVector;
   }

    public char getPixel(int r, int c) {
        return chars[r][c];
    }


    public void setValue(int val) {
        this.value = val;
    }
}