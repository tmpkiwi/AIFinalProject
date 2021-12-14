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

    public int numOfDigit(int value) {
        int sum = 0;
        for (int i = 0; i < digits.size(); i++) {
            if (digits.get(i).value == value)
                sum++;
        }
        return sum;
    }

    public double[] BayesBySection(Digit d, int[][][] blackPixels, int[][][] grayPixels, int rstart, int rend,
            int cstart, int cend) {
        double[] digitprobabilities = new double[10];
        for (int i = 0; i < 10; i++) {
            digitprobabilities[i] = 1.00;
        }
        double zero = 1.00 / (digits.size() * 5);

        boolean found = false;

        outerloop: for (int r = rstart; r < rend; r++) {
            for (int c = cstart; c < cend; c++) {
                char pixel = d.getPixel(r, c);
                if (pixel == '#') {
                    for (int i = 0; i < 10; i++) {
                        int numofDigit = numOfDigit(i);
                        int numblack = blackPixels[i][r][c];
                        if (numblack > 0)
                            digitprobabilities[i] *= ((double) numblack / numofDigit);
                        else
                            digitprobabilities[i] *= zero;

                        // System.out.println(i + ": " + digitprobabilities[i]);
                    }
                } else if (pixel == '+') {
                    for (int i = 0; i < 10; i++) {
                        int numofDigit = numOfDigit(i);
                        int numgray = grayPixels[i][r][c];
                        if (numgray > 0)
                            digitprobabilities[i] *= ((double) numgray / numofDigit);
                        else
                            digitprobabilities[i] *= zero;
                    }
                } else {
                    for (int i = 0; i < 10; i++) {
                        int numofDigit = numOfDigit(i);
                        int numwhite = numofDigit - blackPixels[i][r][c] - grayPixels[i][r][c];
                        if (numwhite >= 0)
                            digitprobabilities[i] *= ((double) numwhite / numofDigit);
                        else
                            digitprobabilities[i] *= zero;
                    }
                }
            }
        }

        // outerloop: for (int r = rstart; r < rend; r++) {
        // for (int c = cstart; c < cend; c++) {
        // char pixel = d.getPixel(r, c);
        // if (pixel == '+' || pixel == '#') {
        // for (int i = 0; i < 10; i++) {
        // int numofDigit = numOfDigit(i);
        // int numblack = blackPixels[i][r / 4][c / 4];
        // if (numblack > 0)
        // digitprobabilities[i] *= ((double) numblack / numofDigit);
        // else
        // digitprobabilities[i] *= zero;

        // // System.out.println(i + ": " + digitprobabilities[i]);
        // }
        // found = true;
        // break outerloop;
        // }

        // } else if (pixel == '+') {
        // for (int i = 0; i < 10; i++) {
        // int numofDigit = numOfDigit(i);
        // int numgray = grayPixels[i][r/4][c/4];
        // digitprobabilities[i] *= ((double) numgray / numofDigit);
        // }
        // } else {
        // for (int i = 0; i < 10; i++) {
        // int numofDigit = numOfDigit(i);
        // int numwhite = numofDigit - blackPixels[i][r][c] - grayPixels[i][r][c];
        // digitprobabilities[i] *= ((double) numwhite / numofDigit);
        // }
        // }
        // }
        // }

        // if (!found) {
        // for (int i = 0; i < 10; i++) {
        // int numofDigit = numOfDigit(i);
        // int numwhite = numofDigit - blackPixels[i][rstart / 4][cstart / 4];
        // if (numwhite > 0)
        // digitprobabilities[i] *= ((double) numwhite / numofDigit);
        // else
        // digitprobabilities[i] *= zero;
        // }
        // }

        return digitprobabilities;
    }

    public void NaiveBayesDigits() {
        if (percentTrain != 100) {
            int removeDigits = (451 * (100 - percentTrain)) / 100;
            removeDigits(removeDigits);
        }

        int[][][] blackpixels = new int[10][28][28];
        int[][][] graypixels = new int[10][28][28];

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                for (int d = 0; d < digits.size(); d++) {
                    char pixel = digits.get(d).getPixel(i, j);
                    int value = digits.get(d).value;

                    if (pixel == '#') {
                        blackpixels[value][i][j]++;
                    } else if (pixel == '+') {
                        graypixels[value][i][j]++;
                    }
                }
            }
        }

        // int[][][] blackpixels = new int[10][7][7];
        // int[][][] graypixels = new int[10][7][7];

        // for (int i = 0; i < 28; i += 4) {
        // for (int j = 0; j < 28; j += 4) {
        // for (int d = 0; d < digits.size(); d++) {
        // outerloop: for (int r = i; r < i + 4; r++) {
        // for (int c = j; c < j + 4; c++) {
        // char pixel = digits.get(d).getPixel(r, c);
        // int value = digits.get(d).value;
        // if (pixel == '*' || pixel == '+') {
        // blackpixels[value][i / 4][j / 4]++;
        // break outerloop;
        // }
        // }
        // }

        // }
        // }
        // }

        int numCorrect = 0;

        // for (int i = 0; i < testdigits.size(); i++) {
        // Digit d = testdigits.get(i);
        // double[] digitprobabilities = new double[10];
        // for (int p = 0; p < 10; p++) {
        // digitprobabilities[p] = 1.00;
        // }

        // for (int r = 0; r < 28; r += 2) {
        // for (int c = 0; c < 28; c += 2) {
        // double[] probabilities;
        // if (r + 4 <= 28 && c + 4 <= 28)
        // probabilities = BayesBySection(d, blackpixels, graypixels, r, r + 4, c, c +
        // 4);
        // else {
        // if (c + 4 <= 28)
        // probabilities = BayesBySection(d, blackpixels, graypixels, r, 28, c, c + 4);
        // else if (r + 4 <= 28)
        // probabilities = BayesBySection(d, blackpixels, graypixels, r, r + 4, c, 28);
        // else
        // probabilities = BayesBySection(d, blackpixels, graypixels, r, 28, c, 28);
        // }
        // for (int p = 0; p < 10; p++) {
        // digitprobabilities[p] *= probabilities[p];
        // }
        // }
        // }

        // System.out.println("Digit: " + d.value);
        // for (int p = 0; p < 10; p++) {
        // System.out.println(p + ": " + digitprobabilities[p]);
        // }
        // System.out.println('\n');

        // double max = digitprobabilities[0];
        // int maxdigit = 0;
        // for (int p = 1; p < 10; p++) {
        // if (digitprobabilities[p] / max > 1) {
        // max = digitprobabilities[p];
        // maxdigit = p;
        // }
        // }

        // if (maxdigit == d.value)
        // numCorrect++;
        // }

        for (int i = 0; i < testdigits.size(); i++) {
            Digit d = testdigits.get(i);
            int[] digitprobabilities = new int[10];

            for (int r = 0; r < 28; r += 5) {
                for (int c = 0; c < 28; c += 28) {
                    double[] probabilities;
                    if (r + 5 <= 28)
                        probabilities = BayesBySection(d, blackpixels, graypixels, r, r + 5, c, 28);
                    else
                        probabilities = BayesBySection(d, blackpixels, graypixels, r, 28, c, c +
                                28);
                    for (int p = 0; p < 10; p++) {
                        for (int q = 0; q < 10; q++) {
                            if (probabilities[p] / probabilities[q] > 1)
                                digitprobabilities[p]++;
                        }
                    }
                }
            }

            int max = digitprobabilities[0];
            int maxdigit = 0;
            for (int p = 1; p < 10; p++) {
                if (digitprobabilities[p] > max) {
                    max = digitprobabilities[p];
                    maxdigit = p;
                }
            }

            if (maxdigit == d.value)
                numCorrect++;
        }

        System.out.println("Correct: " + numCorrect + " out of " + testdigits.size());

    }

}
