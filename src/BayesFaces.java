import java.util.ArrayList;

public class BayesFaces {

    ArrayList<Face> faces, testfaces;
    int percentTrain;

    public BayesFaces(ArrayList<Face> faces, int percentTrain, ArrayList<Face> testfaces) {
        this.faces = faces;
        this.percentTrain = percentTrain;
        this.testfaces = testfaces;
        NaiveBayesFaces();
    }

    public int TotalFaces() {
        int sum = 0;
        for (int i = 0; i < faces.size(); i++) {
            if (faces.get(i).isFace())
                sum++;
        }
        return sum;
    }

    public void removeFaces(int removefaces) {
        for (int i = 0; i < removefaces; i++) {
            int rand = (int) (Math.random() * faces.size());
            faces.remove(rand);
        }
    }

    public double[] BayesBySection(Face f, int[][] faceBlackPixels, int[][] nonfaceBlackPixels, int start, int end) {
        double faceprobability = 1.00;
        double nonfaceprobability = 1.00;
        int totalfaces = TotalFaces();
        int nonfaces = faces.size() - totalfaces;
        double zero = 1.00 / (faces.size() * 2);
        for (int r = start; r < end; r++) {
            for (int c = 0; c < 60; c++) {
                char pixel = f.getPixel(r, c);
                if (pixel == '#') {
                    // System.out.print(faceBlackPixels[r][c] + " " + totalfaces + " "
                    // + (double) faceBlackPixels[r][c] / totalfaces);
                    if (faceBlackPixels[r][c] > 0)
                        faceprobability *= ((double) faceBlackPixels[r][c] / totalfaces);
                    else
                        faceprobability *= zero;
                    if (nonfaceBlackPixels[r][c] > 0)
                        nonfaceprobability *= ((double) nonfaceBlackPixels[r][c] / nonfaces);
                    else
                        nonfaceprobability *= zero;
                } else {
                    int facewhitepixels = totalfaces - faceBlackPixels[r][c];
                    int nonfacewhitepixels = nonfaces - nonfaceBlackPixels[r][c];

                    // System.out.print(facewhitepixels + " " + totalfaces + " "
                    // + (double) facewhitepixels / totalfaces);
                    if (facewhitepixels > 0)
                        faceprobability *= ((double) facewhitepixels / totalfaces);
                    else
                        faceprobability *= zero;
                    if (nonfacewhitepixels > 0)
                        nonfaceprobability *= ((double) nonfacewhitepixels / nonfaces);
                    else
                        nonfaceprobability *= zero;
                }
                // System.out.println("Face probability: " + faceprobability);
                // System.out.println("Nonface probability: " + nonfaceprobability);
            }
        }
        double[] probabilites = new double[2];
        System.out.println(
                "faceprob:" + faceprobability + "\tnonfaceprob:" + nonfaceprobability + "\tisFace: " + f.isFace());
        probabilites[0] = faceprobability;
        probabilites[1] = nonfaceprobability;
        return probabilites;
    }

    public void NaiveBayesFaces() {
        if (percentTrain != 100) {
            int removeFaces = (451 * (100 - percentTrain)) / 100;
            removeFaces(removeFaces);
        }

        int[][] faceBlackPixels = new int[70][60];
        int[][] nonfaceBlackPixels = new int[70][60];

        for (int i = 0; i < 70; i++) {
            for (int j = 0; j < 60; j++) {
                for (int f = 0; f < faces.size(); f++) {
                    char pixel = faces.get(f).getPixel(i, j);
                    if (pixel == '#' && faces.get(f).isFace())
                        faceBlackPixels[i][j]++;
                    if (pixel == '#' && !faces.get(f).isFace())
                        nonfaceBlackPixels[i][j]++;
                }
            }
        }

        int numCorrect = 0;

        for (int i = 0; i < testfaces.size(); i++) {
            Face f = testfaces.get(i);
            int faceprobability = 0;
            int nonfaceprobability = 0;
            for (int r = 0; r < 70; r += 5) {
                double[] probabilities = BayesBySection(f, faceBlackPixels, nonfaceBlackPixels, r, r + 5);
                if (probabilities[0] / probabilities[1] > 1)
                    faceprobability += 1;
                else
                    nonfaceprobability += 1;
            }
            // System.out.println("Total faceprob: " + faceprobability + "\tTotal
            // nonfaceprob: " + nonfaceprobability
            // + "\tisFace: " + f.isFace());

            if (faceprobability > nonfaceprobability && f.isFace())
                numCorrect++;
            if (faceprobability < nonfaceprobability && !f.isFace())
                numCorrect++;

            System.out.println(numCorrect + " correct so far out of " + i);
        }

        System.out.println("Correct: " + numCorrect + " out of " + testfaces.size());

    }

}