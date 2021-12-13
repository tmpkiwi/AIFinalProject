public class Digit {

    private char[][] chars;
    private boolean isDigit;

    public Digit(char[][] chars) {
        System.out.print("new Digit");
        this.chars = chars;
    }

    public char getPixel(int r, int c) {
        return chars[r][c];
    }

    public boolean isDigit() {
        return isDigit;
    }

    public void setDigit(boolean val) {
        System.out.println("is " + val);
        isDigit = val;
    }

}
