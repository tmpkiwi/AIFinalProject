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

        if (args.length >= 1)
            digitsOrFaces = args[0];
        if (args.length >= 2)
            percentTrain = Integer.parseInt(args[1]);
        if (args.length == 3)
            classifier = args[2];

        if (digitsOrFaces.equals("faces")) {
            ReadFile('f', "./facedata/facedatatrain", "./facedata/facedatatrainlabels", true);
            ReadFile('f', "./facedata/facedatatest", "./facedata/facedatatestlabels", false);

            BayesFaces b = new BayesFaces(trainingfaces, percentTrain, "faces",
                    testfaces);
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
            BayesDigits b = new BayesDigits(trainingdigits, percentTrain, "digits",
                    testdigits);
        }

    }
}
