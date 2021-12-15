import java.util.ArrayList;

public class PerceptronFaces {
    ArrayList<Face> trainingfaces;
    ArrayList<Face> testfaces;
    Perceptron p = new Perceptron(4200);

    public PerceptronFaces(ArrayList<Face> trainingfaces, ArrayList<Face> testfaces, int trainingIterations, int percentTrain) {
        this.trainingfaces = trainingfaces;
        this.testfaces = testfaces;
        if (percentTrain != 100) {
            int removeFaces = (trainingfaces.size() * (100 - percentTrain)) / 100;
            removeData(removeFaces);
        }
        for (int t=0; t<trainingIterations; t++) {
            trainPerceptron(); // train perceptron according to user-specified number of iterations
        }
    }

    public void removeData(int removeFaces) {
        for (int i = 0; i < removeFaces; i++) {
            int rand = (int) (Math.random() * this.trainingfaces.size());
            this.trainingfaces.remove(rand);
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
        // PRINTS PREDICTED OUTPUTS
        //for (Integer g : guesses) System.out.println("Predicted value: "+g);
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