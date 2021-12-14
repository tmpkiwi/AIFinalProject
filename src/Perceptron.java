public class Perceptron {
    int numInputs;
    float[] weights; 
    float learningRate = (float) 0.5;

    Perceptron(int numInputs) {
        this.numInputs = numInputs;
        this.weights = new float[numInputs+1]; // +1 for bias
        // Initialize weights randomly between 0 (inclusive) and 1 (exclusive)
        for (int i = 0; i < numInputs; i++) {
            weights[i] = (float) Math.random();
        }
    }

    int predictFace(float[] inputs) {
        double sum = 0;
        for (int i = 0; i < numInputs; i++) {
            sum += inputs[i] * weights[i];
        }
        return activateFace(sum);
    }

    double probOfDigit(float[] inputs) {
        double sum = 0;
        for (int i = 0; i < numInputs; i++) {
            sum += inputs[i] * weights[i];
        }
        return sum;
    }

    int activateFace(double n) {
        if (n >= 0)
            {
                return 1;
            }
        else
            return -1;
    }

    void train(char type, float[] inputs, int actual) {
        int predict = predictFace(inputs);
        int error = actual - predict;
        weights[0] += error; // increment or decrement bias by 1
        for (int i = 0; i < numInputs; i++) {
            weights[i+1] += error * inputs[i] * learningRate;
        }

    }

}