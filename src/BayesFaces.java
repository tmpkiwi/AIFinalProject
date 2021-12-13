import java.util.ArrayList;

public class BayesFaces {

    ArrayList<Face> faces, testfaces;
    int percentTrain;
    String type;

    public BayesFaces(ArrayList<Face> faces, int percentTrain, String type, ArrayList<Face> testfaces) {
        this.faces = faces;
        this.percentTrain = percentTrain;
        this.type = type;
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

    public void NaiveBayesFaces() {
        if (percentTrain != 100) {
            int removeFaces = (451 * (100 - percentTrain)) / 100;
            removeFaces(removeFaces);
        }

        int totalfaces = TotalFaces();
        int nonfaces = faces.size() - totalfaces;

        double faceprobability = 1.00;
        double nonfaceprobability = 1.00;

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
    }

}