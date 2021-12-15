import java.util.ArrayList;

public class KnearestDigits {
    ArrayList<Digit> digits, testdigits;
    int percentTrain;
    int[] distances;
    long runtime = 0;

    public KnearestDigits(ArrayList<Digit> digits, int percentTrain, ArrayList<Digit> testdigits) {
        this.digits = digits;
        this.percentTrain = percentTrain;
        this.testdigits = testdigits;
        Knearest(100);
    }

    public void removeDigits(int removedigits) {
        for (int i = 0; i < removedigits; i++) {
            int rand = (int) (Math.random() * digits.size());
            digits.remove(rand);
        }
    }

    public void CalculateDistances(Digit d) {
        distances = new int[digits.size()];
        for (int i = 0; i < digits.size(); i++) {
            Digit trainingdigit = digits.get(i);
            for (int r = 0; r < 28; r++) {
                for (int c = 0; c < 28; c++) {
                    // boolean nearby = false;
                    // for (int r2 = r - 1; r2 < r + 2; r2++) {
                    // for (int c2 = c - 1; c2 < c + 2; c2++) {
                    // if (r2 >= 0 && c2 >= 0 && r2 < 70 && c2 < 60
                    // && f.getPixel(r, c) == trainingface.getPixel(r2, c2))
                    // nearby = true;
                    // }
                    // }
                    // if (!nearby)
                    // distances[i]++;
                    if (d.getPixel(r, c) != trainingdigit.getPixel(r, c))
                        distances[i]++;
                }
            }
            // System.out.println(distances[i]);
        }
    }

    public int CalculateMode(int k) {
        for (int i = 1; i < distances.length; i++) {
            int j = i;
            while (j > 0 && distances[j] < distances[j - 1]) {
                int temp = distances[j];
                distances[j] = distances[j - 1];
                distances[j - 1] = temp;
                Digit temp2 = digits.get(j);
                digits.set(j, digits.get(j - 1));
                digits.set(j - 1, temp2);
                j--;
            }
        }

        // System.out.println("\nSorted: ");
        // for (int j = 0; j < distances.length; j++) {
        // System.out.println(distances[j] + ": " + digits.get(j).value);
        // }

        int[] numofDigits = new int[10];
        for (int i = 0; i < k; i++) {
            numofDigits[digits.get(i).value]++;
        }

        int max = numofDigits[0];
        int maxdigit = 0;

        for (int d = 1; d < 10; d++) {
            if (numofDigits[d] > max) {
                max = numofDigits[d];
                maxdigit = d;
            }
        }

        return maxdigit;
    }

    public void Knearest(int k) {
        if (percentTrain != 100) {
            int removedigits = (5000 * (100 - percentTrain)) / 100;
            removeDigits(removedigits);
        }

        int numcorrect = 0;

        for (int i = 0; i < testdigits.size(); i++) {
            Digit d = testdigits.get(i);
            CalculateDistances(d);

            // System.out.println("Digit: " + d.value);
            // for (int j = 0; j < distances.length; j++) {
            // System.out.println(j + " " + distances[j] + ": " + digits.get(j).value);
            // }

            int a = CalculateMode(k);

            if (a == testdigits.get(i).value) {
                numcorrect++;
            }

            // FOR USE DURING RUNTIME
            //System.out.println(numcorrect + " correct so far out of " + i);
        }
        System.out.println("K-Nearest classified "+numcorrect + "/" + testdigits.size()+", or "+((double)numcorrect/testdigits.size()) * 100+"% of digits correctly with"+percentTrain+"% of training data.");
    }
}
