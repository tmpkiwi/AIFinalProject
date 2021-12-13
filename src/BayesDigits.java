import java.util.ArrayList;

public class BayesDigits {
    ArrayList<Digit> digits, testdigits;
    int percentTrain;
    String type;

    public BayesDigits(ArrayList<Digit> digits, int percentTrain, String type, ArrayList<Digit> testdigits) {
        this.digits = digits;
        this.percentTrain = percentTrain;
        this.type = type;
        this.testdigits = testdigits;
        NaiveBayesDigits();
    }

    public void removeDigits(int removedigits) {
        for (int i = 0; i < removedigits; i++) {
            int rand = (int) (Math.random() * digits.size());
            digits.remove(rand);
        }
    }

    public void NaiveBayesDigits() {
        if (percentTrain != 100) {
            int removeDigits = (451 * (100 - percentTrain)) / 100;
            removeDigits(removeDigits);
        }
    }

}
