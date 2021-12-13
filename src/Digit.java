public class Digit {

    private char[][] chars;
    public int value;

    public Digit(char[][] chars) {
        System.out.print("New digit");
        this.chars = chars;
    }

    public char getPixel(int r, int c) {
        return chars[r][c];
    }


    public void setValue(int val) {
        System.out.println(" equals " + val);
        this.value = val;
    }
}
