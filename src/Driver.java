import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class Driver {

    static ArrayList<Face> trainingfaces = new ArrayList<>();
    static ArrayList<Digit> trainingdigits = new ArrayList<>();
    static ArrayList<Face> testfaces = new ArrayList<>();
    static ArrayList<Digit> testdigits = new ArrayList<>();

    public static void ReadFile(char type, String imagepath, String labelpath, boolean train) {
        int height, width;
        if (type == 'f') {
            height = 70;
            width = 60;
        } else { // type == 'd'
            height = 28;
            width = 28;
        }
        try {
            File imageFile = new File(imagepath);
            File labelFile = new File(labelpath);
            Scanner pixels = new Scanner(imageFile);
            Scanner labels = new Scanner(labelFile);
            int counter = 0;
            int total = 0;
            char[][] currentImage = new char[height][width];
            while (pixels.hasNextLine()) {
                String line = pixels.nextLine();
                // System.out.println(line);
                for (int i = 0; i < width; i++) {
                    if (line.length() > i)
                        currentImage[counter][i] = line.charAt(i);
                    else
                        currentImage[counter][i] = ' ';
                }
                counter++;
                total++;
                if (counter == height) { // end of image
                    if (type == 'f') { // is face data
                        Face newFace = new Face(currentImage);
                        if (Integer.parseInt(labels.nextLine()) == 1)
                            newFace.setFace(true);
                        else
                            newFace.setFace(false);
                        if (train)
                            trainingfaces.add(newFace);
                        else
                            testfaces.add(newFace);
                    } else if (type == 'd') { // is digit data
                        Digit newDigit = new Digit(currentImage);
                        newDigit.setValue(Integer.parseInt(labels.nextLine()));
                        if (train)
                            trainingdigits.add(newDigit);
                        else
                            testdigits.add(newDigit);
                    }
                    currentImage = new char[height][width];
                    counter = 0;
                }

            }
            pixels.close();
            labels.close();
        } catch (FileNotFoundException e) {
            if (type == 'f' && train)
                System.out.println("Face train data not found.");
            else if (type == 'f' && !train)
                System.out.println("Face test data not found.");
            else if (type == 'd' && train)
                System.out.println("Digit train data not found.");
            else if (type == 'd' && !train)
                System.out.println("Digit test data not found.");
            else
                System.out.println("Unknown file read error.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        /* <-------- Handle command line arguments --------> */

        /* Default settings */
        String digitsOrFaces = "digits";
        int percentTrain = 100;
        String classifier = "naiveBayes";
        int numIterations = 3; // num training iterations for perceptron

        if (args.length >= 1)
            digitsOrFaces = args[0];
        if (args.length >= 2)
            percentTrain = Integer.parseInt(args[1]);
        if (args.length >= 3)
            classifier = args[2];
        if (args.length == 4)
            numIterations = Integer.parseInt(args[3]);

        if (digitsOrFaces.equals("faces")) {
            ReadFile('f', "./facedata/facedatatrain", "./facedata/facedatatrainlabels", true);
            ReadFile('f', "./facedata/facedatatest", "./facedata/facedatatestlabels", false);

            if (classifier.equals("naiveBayes")) {
                BayesFaces b = new BayesFaces(trainingfaces, percentTrain, testfaces);
            } else if (classifier.equals("kNearest")) {
                KnearestFaces k = new KnearestFaces(trainingfaces, percentTrain, testfaces);
            } else {
                PerceptronFaces p = new PerceptronFaces(trainingfaces, testfaces, numIterations, percentTrain);
                ArrayList<Integer> pResult = p.runPerceptron();
                System.out.println("Perceptron classified faces " + p.percentCorrect(pResult) + "% correctly with "
                        + percentTrain + "% of training data.");
            }
            // PerceptronFaces p = new PerceptronFaces(trainingfaces, testfaces);
            // ArrayList<Integer> pResult = p.runPerceptron();
            // } else if (digitsOrFaces.equals("digits")) {
            // ReadTrainingDigits();
            // ReadTestDigits();
            // BayesFaces b = new BayesFaces(faces, digits, percentTrain, "digits",
            // testfaces, testdigits);
        } else if (digitsOrFaces.equals("digits")) {
            ReadFile('d', "./digitdata/trainingimages", "./digitdata/traininglabels", true);
            ReadFile('d', "./digitdata/testimages", "./digitdata/testlabels", false);
            if (classifier.equals("naiveBayes")) {
                BayesDigits b = new BayesDigits(trainingdigits, percentTrain, testdigits);
            } else if (classifier.equals("kNearest")) {
                KnearestDigits k = new KnearestDigits(trainingdigits, percentTrain, testdigits);
            } else {
                PerceptronDigit p = new PerceptronDigit(trainingdigits, testdigits, numIterations, percentTrain);
                ArrayList<Integer> pResult = p.runPerceptron();
                System.out.println("Perceptron classified digits " + p.percentCorrect(pResult) + "% correctly with "
                        + percentTrain + "% of training data.");
            }
        } else if (digitsOrFaces.equals("report")) {
            ReadFile('f', "./facedata/facedatatrain", "./facedata/facedatatrainlabels", true);
            ReadFile('f', "./facedata/facedatatest", "./facedata/facedatatestlabels", false);
            ReadFile('d', "./digitdata/trainingimages", "./digitdata/traininglabels", true);
            ReadFile('d', "./digitdata/testimages", "./digitdata/testlabels", false);
            System.out.println("------------BEGIN REPORT------------");
            System.out.println("- Faces -");
            for (int percent = 10; percent <= 100; percent += 10) {
                double[] perceptroncorrect = new double[5];
                double sum = 0;
                for (int i = 0; i < 5; i++) {
                    PerceptronFaces p = new PerceptronFaces(trainingfaces, testfaces,
                            numIterations, percent);
                    ArrayList<Integer> pResult = p.runPerceptron();
                    perceptroncorrect[i] = p.percentCorrect(pResult);
                    sum += perceptroncorrect[i];
                    System.out.println("Perceptron classified faces " + perceptroncorrect[i] + "%correctly with "
                            + percent + "% of training data.");
                    System.out.println("Training Runtime: " + p.runtime + "\n");
                }
                System.out.println("Mean correct: " + sum / 5);
                sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += Math.pow((perceptroncorrect[i]) - sum / 5.00, 2);
                }
                System.out.println("Standard deviation: " + Math.sqrt(sum / 4));
                long start = System.currentTimeMillis();
                double[] knearestcorrect = new double[5];
                sum = 0;
                for (int i = 0; i < 5; i++) {
                    KnearestFaces k = new KnearestFaces(trainingfaces, percent, testfaces);
                    knearestcorrect[i] = k.Knearest(20);
                    sum += knearestcorrect[i];
                    long end = System.currentTimeMillis();
                    System.out.println("Total runtime: " + (end - start) + '\n');
                }
                System.out.println("Mean correct: " + sum / 5);
                sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += Math.pow((knearestcorrect[i]) - sum / 5.00, 2);
                }
                System.out.println("Standard deviation: " + Math.sqrt(sum / 4));
                double[] bayescorrect = new double[5];
                sum = 0;
                for (int i = 0; i < 5; i++) {
                    BayesFaces b = new BayesFaces(trainingfaces, percent, testfaces);
                    bayescorrect[i] = b.NaiveBayesFaces();
                    sum += bayescorrect[i];
                    System.out.println("Training Runtime: " + b.runtime + "\n");

                }
                System.out.println("Mean correct: " + sum / 5);
                sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += Math.pow((bayescorrect[i]) - sum / 5.00, 2);
                }
                System.out.println("Standard deviation: " + Math.sqrt(sum / 4));

            }
            System.out.println("- Digits -");
            for (int percent = 10; percent <= 100; percent += 10) {
                double[] perceptroncorrect = new double[5];
                double sum = 0;
                for (int i = 0; i < 5; i++) {
                    PerceptronDigit p = new PerceptronDigit(trainingdigits, testdigits, numIterations, percent);
                    ArrayList<Integer> pResult = p.runPerceptron();
                    perceptroncorrect[i] = p.percentCorrect(pResult);
                    sum += perceptroncorrect[i];
                    System.out.println("Perceptron classified faces " + perceptroncorrect[i] + "% correctly with "
                            + percent + "% of training data.");
                    System.out.println("Training Runtime: " + p.runtime + "\n");
                }
                System.out.println("Mean correct: " + sum / 5);
                sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += Math.pow((perceptroncorrect[i]) - sum / 5.00, 2);
                }
                System.out.println("Standard deviation: " + Math.sqrt(sum / 4));
                long start = System.currentTimeMillis();
                double[] knearestcorrect = new double[5];
                sum = 0;
                for (int i = 0; i < 5; i++) {
                    KnearestDigits k = new KnearestDigits(trainingdigits, percent, testdigits);
                    knearestcorrect[i] = k.Knearest(100);
                    sum += knearestcorrect[i];
                    long end = System.currentTimeMillis();
                    System.out.println("Total runtime: " + (end - start) + '\n');
                }
                System.out.println("Mean correct: " + sum / 5);
                sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += Math.pow((knearestcorrect[i]) - sum / 5.00, 2);
                }
                System.out.println("Standard deviation: " + Math.sqrt(sum / 4));
                BayesDigits b = new BayesDigits(trainingdigits, percent, testdigits);
                System.out.println("Training Runtime: " + b.runtime + "\n");
            }

        }

    }
}
