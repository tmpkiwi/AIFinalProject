public class Face {

    public char[][] chars;
    private boolean isFace;
    

    public Face(char[][] chars) {
        System.out.print("New Face");
        this.chars = chars;
    }

    public char getPixel(int r, int c) {
        return chars[r][c];
    }

    public boolean isFace() {
        return isFace;
    }

    public void setFace(boolean val) {
        System.out.println(" is " + val);
        isFace = val;
    }

}
