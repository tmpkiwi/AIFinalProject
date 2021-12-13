import java.util.ArrayList;

public class PerceptronFaces {
    ArrayList<Face> faces;
    ArrayList<Face> testfaces;

    public PerceptronFaces(ArrayList<Face> faces, ArrayList<Face> testfaces) {

        for (Face curr : faces) {
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
                            hashtag.guess(inputs);
                            break;
                        case ' ':
                            space.guess(inputs);
                            break;
                    }
                }

            }
        }
    }
}