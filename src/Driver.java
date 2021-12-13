import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class Driver {

    static ArrayList<Face> faces = new ArrayList<>();
    static ArrayList<Digit> digits = new ArrayList<>();
    static ArrayList<Face> testfaces = new ArrayList<>();
    static ArrayList<Digit> testdigits = new ArrayList<>();

    public static void ReadTrainingFaces() {
        try {
            File faceDataTrain = new File("../facedata/facedatatrain");
            File faceDataTrainLabels = new File("../facedata/facedatatrainlabels");
            Scanner scanner = new Scanner(faceDataTrain);
            Scanner labels = new Scanner(faceDataTrainLabels);
            int counter = 0;
            char[][] currentface = new char[70][60];
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                for (int i = 0; i < 60; i++) {
                    if (data.length() > i)
                        currentface[counter][i] = data.charAt(i);
                    else
                        currentface[counter][i] = ' ';
                }
                counter++;
                if (counter == 70) {
                    Face newFace = new Face(currentface);
                    if (Integer.parseInt(labels.nextLine()) == 1)
                        newFace.setFace(true);
                    else
                        newFace.setFace(false);
                    newFace.trainPerceptron(); // temp
                    faces.add(newFace);
                    counter = 0;
                }

            }
            scanner.close();
            labels.close();
        } catch (FileNotFoundException e) {
            System.out.println("Face train data not found.");
            e.printStackTrace();
        }
    }

    public static void ReadFaces(String imagepath, String labelpath, boolean train) {
        try {
            File faceData = new File(imagepath);
            File faceDataLabels = new File(labelpath);
            Scanner scanner = new Scanner(faceData);
            Scanner labels = new Scanner(faceDataLabels);
            int counter = 0;
            char[][] currentface = new char[70][60];
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                for (int i = 0; i < 60; i++) {
                    if (data.length() > i)
                        currentface[counter][i] = data.charAt(i);
                    else
                        currentface[counter][i] = ' ';
                }
                counter++;
                if (counter == 70) {
                    Face newFace = new Face(currentface);
                    if (Integer.parseInt(labels.nextLine()) == 1)
                        newFace.setFace(true);
                    else
                        newFace.setFace(false);
                    if (train)
                        faces.add(newFace);
                    else
                        testfaces.add(newFace);
                    counter = 0;
                }

            }
            scanner.close();
            labels.close();
        } catch (FileNotFoundException e) {
            System.out.println("Face train data not found.");
            e.printStackTrace();
        }
    }

    public static void ReadTrainingDigits() {
        try {
            File digitDataTrain = new File("../digitdata/trainingimages");
            File digitDataTrainLabels = new File("../digitdata/traininglabels");
            Scanner scanner = new Scanner(digitDataTrain);
            Scanner labels = new Scanner(digitDataTrainLabels);
            int counter = 0;
            char[][] currentdigit = new char[28][28];
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                for (int i = 0; i < 28; i++) {
                    if (data.length() > i)
                        currentdigit[counter][i] = data.charAt(i);
                    else
                        currentdigit[counter][i] = ' ';
                }
                counter++;
                if (counter == 28) {
                    Digit newDigit = new Digit(currentdigit);
                    if (Integer.parseInt(labels.nextLine()) == 1)
                        newDigit.setDigit(true);
                    else
                        newDigit.setDigit(false);
                    digits.add(newDigit);
                    counter = 0;
                }

            }
            scanner.close();
            labels.close();
        } catch (FileNotFoundException e) {
            System.out.println("Digit train data not found.");
            e.printStackTrace();
        }
    }

    public static void ReadTestDigits() {
        try {
            File digitDataTrain = new File("../digitdata/trainingimages");
            File digitDataTrainLabels = new File("../digitdata/traininglabels");
            Scanner scanner = new Scanner(digitDataTrain);
            Scanner labels = new Scanner(digitDataTrainLabels);
            int counter = 0;
            char[][] currentdigit = new char[28][28];
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                for (int i = 0; i < 28; i++) {
                    if (data.length() > i)
                        currentdigit[counter][i] = data.charAt(i);
                    else
                        currentdigit[counter][i] = ' ';
                }
                counter++;
                if (counter == 28) {
                    Digit newDigit = new Digit(currentdigit);
                    if (Integer.parseInt(labels.nextLine()) == 1)
                        newDigit.setDigit(true);
                    else
                        newDigit.setDigit(false);
                    digits.add(newDigit);
                    counter = 0;
                }

            }
            scanner.close();
            labels.close();
        } catch (FileNotFoundException e) {
            System.out.println("Digit train data not found.");
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
            ReadFaces("../facedata/facedatatrain", "../facedata/facedatatrainlabels", true);
            ReadFaces("../facedata/facedatatest", "../facedata/facedatatestlabels", true);
            BayesFaces b = new BayesFaces(faces, percentTrain, "faces", testfaces);
            PerceptronFaces p = new PerceptronFaces(faces, testfaces);
            // } else if (digitsOrFaces.equals("digits")) {
            // ReadTrainingDigits();
            // ReadTestDigits();
            // BayesFaces b = new BayesFaces(faces, digits, percentTrain, "digits",
            // testfaces, testdigits);
        }

    }
}
