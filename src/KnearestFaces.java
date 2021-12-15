import java.util.ArrayList;;

public class KnearestFaces {

    ArrayList<Face> faces, testfaces;
    int percentTrain;
    int[] distances;

    public KnearestFaces(ArrayList<Face> faces, int percentTrain, ArrayList<Face> testfaces) {
        this.faces = faces;
        this.percentTrain = percentTrain;
        this.testfaces = testfaces;
        Knearest(20);
    }

    public void CalculateDistances(Face f) {
        distances = new int[faces.size()];
        for (int i = 0; i < faces.size(); i++) {
            Face trainingface = faces.get(i);
            for (int r = 0; r < 70; r++) {
                for (int c = 0; c < 60; c++) {
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
                    if (f.getPixel(r, c) != trainingface.getPixel(r, c))
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
                Face temp2 = faces.get(j);
                faces.set(j, faces.get(j - 1));
                faces.set(j - 1, temp2);
                j--;
            }
        }

        // System.out.println("\nSorted: ");
        // for (int j = 0; j < distances.length; j++) {
        // System.out.println(distances[j] + ": " + faces.get(j).isFace());
        // }

        int numfaces = 0;
        for (int i = 0; i < k; i++) {
            if (faces.get(i).isFace()) {
                numfaces++;
            }
        }

        if (numfaces > 6) {
            return 0;
        }
        return 1;
    }

    public void removeFaces(int removefaces) {
        for (int i = 0; i < removefaces; i++) {
            int rand = (int) (Math.random() * faces.size());
            faces.remove(rand);
        }
    }

    public void Knearest(int k) {
        if (percentTrain != 100) {
            int removeFaces = (451 * (100 - percentTrain)) / 100;
            removeFaces(removeFaces);
        }

        int numcorrect = 0;

        for (int i = 0; i < testfaces.size(); i++) {
            Face f = testfaces.get(i);
            CalculateDistances(f);

            // System.out.println("Face: " + f.isFace());
            // for (int j = 0; j < distances.length; j++) {
            // System.out.println(j + " " + distances[j] + ": " + faces.get(j).isFace());
            // }

            int a = CalculateMode(k);

            if (a == 0 && testfaces.get(i).isFace()) {
                numcorrect++;
            }
            if (a == 1 && !testfaces.get(i).isFace()) {
                numcorrect++;
            }

            System.out.println(numcorrect + " correct so far out of " + i);
        }

        System.out
                .println("Num correct: " + numcorrect + " out of " + testfaces.size());

    }
}
