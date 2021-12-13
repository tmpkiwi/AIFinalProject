public class Perceptron {
    float[] weights = new float[2];
    float learningRate = (float) 0.1;

    Perceptron() {
        // Initialize weights randomly between 0 (inclusive) and 1 (exclusive)
        for (int i = 0; i < weights.length; i++) {
            weights[i] = (float) Math.random();
        }
    }

    int guess(float[] inputs) {
        float sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return activate(sum);
    }

    int activate(float n) {
        if (n >= .5) // if probability of being a face/digit is over 50%, return true
            return 1;
        else
            return 0;
    }

    void train(float[] inputs, int actual) {
        int guess = guess(inputs); // STUCK HERE
        int error = actual - guess;

        for (int i = 0; i < weights.length; i++) {
            weights[i] += error * inputs[i] * learningRate;
        }

    }
}