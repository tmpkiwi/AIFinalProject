import java.util.ArrayList;

public class PerceptronDigit {
   
    ArrayList<Digit> trainingdigits;
    ArrayList<Digit> testdigits;
    ArrayList<Perceptron> mlp = new ArrayList<Perceptron>(10);

    public PerceptronDigit(ArrayList<Digit> trainingdigits, ArrayList<Digit> testdigits, int trainingIterations) {
        this.trainingdigits = trainingdigits;
        this.testdigits = testdigits;
        for (int i=0; i<10; i++)
            mlp.add(new Perceptron(784));  // initialize multi-level perceptron
        for (int t=0; t<trainingIterations; t++) {
            trainPerceptron(); // train perceptron according to user-specified number of iterations
        }
    }

    private void trainPerceptron() {
        for (Digit curr : trainingdigits) {
            int actual = curr.value;
            double[] probPerDigit = new double[10];
            double maxProb = 0;
            int predictedDigit = 0;
            for (int i=0; i<10; i++) {
                probPerDigit[i] = mlp.get(i).probOfDigit(curr.pixelsAsVector);
               if (probPerDigit[i] > maxProb) {
                    maxProb = probPerDigit[i];
                    predictedDigit = i;
               }
            }
            if (predictedDigit != actual) {
                mlp.get(predictedDigit).weights[0] -= 1; // increment or decrement bias by 1
                for (int p=0; p<curr.pixelsAsVector.length; p++) // update predicted digit's perceptron weights
                    mlp.get(predictedDigit).weights[p+1] -= curr.pixelsAsVector[p];
                mlp.get(actual).weights[0] += 1;
                for (int a=0; a<curr.pixelsAsVector.length; a++) // update actual digit's perceptron weights
                    mlp.get(actual).weights[a+1] +=  curr.pixelsAsVector[a];
        }
                
        }
    }

    public ArrayList<Integer> runPerceptron () {
        double[] probPerDigit = new double[10];
        double maxProb = 0;
        int predictedDigit = 0;
        ArrayList<Integer> guesses = new ArrayList<Integer>();
        for (Digit curr : testdigits) {
            for (int i=0; i<10; i++) {
                probPerDigit[i] = mlp.get(i).probOfDigit(curr.pixelsAsVector);  
               if (probPerDigit[i] > maxProb) {
                    maxProb = probPerDigit[i];
                    predictedDigit = i;
               }
            }
            maxProb = 0;
            guesses.add(predictedDigit);
        }
        return guesses;
    }

    public double percentCorrect (ArrayList<Integer> guesses) {
        int numCorrect = 0;
        for (int i=0; i<testdigits.size(); i++) {
            if (testdigits.get(i).value == guesses.get(i))
                numCorrect++;
        }
        return ((double)numCorrect/testdigits.size()) * 100;
    }
}
