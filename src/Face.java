public class Face {

    private char[][] chars;
    private boolean isFace;
    Perceptron hashtag = new Perceptron();
    Perceptron space = new Perceptron();

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
        System.out.println("is " + val);
        isFace = val;
    }

    public void trainPerceptron() {
        for (int i = 0; i < 70; i++) {
            for (int j = 0; j < 60; j++) {
                float[] inputs = { i, j };
                int actual;
                if (isFace())
                    actual = 1;
                else
                    actual = 0;
                switch (this.chars[i][j]) {
                    case '#':
                        hashtag.train(inputs, actual);
                        break;
                    case ' ':
                        space.train(inputs, actual);
                        break;
                }
            }

        }

    }
}
