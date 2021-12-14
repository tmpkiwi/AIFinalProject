import java.util.ArrayList;

public class PerceptronFaces {
    ArrayList<Face> trainingfaces;
    ArrayList<Face> testfaces;
    Perceptron p = new Perceptron(4200);

    public PerceptronFaces(ArrayList<Face> trainingfaces, ArrayList<Face> testfaces, int trainingIterations) {
        this.trainingfaces = trainingfaces;
        this.testfaces = testfaces;
        for (int t=0; t<trainingIterations; t++) {
            trainPerceptron(); // train perceptron according to user-specified number of iterations
        }
    }

    private void trainPerceptron() {
        for (Face curr : trainingfaces) {
            int actual;
            if (curr.isFace())
                actual = 1;
            else
                actual = -1;
            p.train('f', curr.pixelsAsVector, actual);
        }
    }

    public ArrayList<Integer> runPerceptron () {
        ArrayList<Integer> guesses = new ArrayList<Integer>();
        for (Face curr : testfaces) {
                guesses.add(p.predictFace(curr.pixelsAsVector));        
                }
        return guesses;
    }

    public double percentCorrect (ArrayList<Integer> guesses) {
        int numCorrect = 0;
        for (int i=0; i<testfaces.size(); i++) {
            if ((testfaces.get(i).isFace() && guesses.get(i) == 1) || (!testfaces.get(i).isFace() && guesses.get(i) == -1))
                numCorrect++;
        }
        return ((double)numCorrect/testfaces.size()) * 100;
    }
}