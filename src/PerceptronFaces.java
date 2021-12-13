import java.util.ArrayList;
import java.util.OptionalDouble;

public class PerceptronFaces {
    ArrayList<Face> trainingfaces;
    ArrayList<Face> testfaces;
    Perceptron hashtag = new Perceptron();
    Perceptron space = new Perceptron();

    public PerceptronFaces(ArrayList<Face> trainingfaces, ArrayList<Face> testfaces) {
        this.trainingfaces = trainingfaces;
        this.testfaces = testfaces;
        trainPerceptron();      
    }

    private void trainPerceptron() {
        for (Face curr : trainingfaces) {
            int actual;
            if (curr.isFace())
                actual = 1;
            else
                actual = 0;
            for (int i = 0; i < 70; i++) {
                for (int j = 0; j < 60; j++) {
                    float[] inputs = { i, j };
                    switch (curr.chars[i][j]) {
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

    public ArrayList<Integer> runPerceptron () {
        ArrayList<Integer> guesses = new ArrayList<Integer>();
        for (Face curr : testfaces) {
            ArrayList<Integer> guessesPerPixel = new ArrayList<Integer>();
            for (int i = 0; i < 70; i++) {
                for (int j = 0; j < 60; j++) {
                    float[] inputs = { i, j };
                    switch (curr.chars[i][j]) {
                        case '#':
                            guessesPerPixel.add(hashtag.guess(inputs));
                            break;
                        case ' ':
                            guessesPerPixel.add(space.guess(inputs));
                            break;
                    }
                }
            }
            OptionalDouble average = guessesPerPixel
                .stream()
                .mapToDouble(a -> a)
                .average();
            double avg = average.isPresent() ? average.getAsDouble() : 0;
            if (avg >= 0.5) guesses.add(1);
            else guesses.add(0);
        }
        for (Integer g : guesses) {
            System.out.print(g+ ", ");
        }
        return guesses;
    }
}