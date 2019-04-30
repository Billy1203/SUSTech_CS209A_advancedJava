package HW1;

public class Rotation_Filter {

//        colT = (float)((colS - ceni)*Math.sin(degree*Math.PI/180) - (rowS - cenj)*Math.sin(degree*Math.PI/180)+ceni);
//        rowT = (float)((colS - ceni)*Math.cos(degree*Math.PI/180) - (rowS - cenj)*Math.cos(degree*Math.PI/180)+ceni);
    
    public static void main(String[] args) {
        Picture source = new Picture("mandrill.png");
        int w  = source.width();
        int h = source.height();
        double cenI = 0.5 * (w  - 1);
        double cenJ = 0.5 * (h - 1);
        double angle = Math.toRadians(Double.parseDouble("30"));

        int colS;
        int rowS;

        Picture target = new Picture(w, h);

        for (int colT = 0; colT < w; colT++) {
            for (int rowT = 0; rowT < h; rowT++) {
                colS = (int) ((colT - cenI) * Math.cos(angle) - (rowT - cenJ) * Math.sin(angle) + cenI);
                rowS = (int) ((colT - cenI) * Math.sin(angle) + (rowT - cenJ) * Math.cos(angle) + cenJ);

                if (colS >= 0 && colS < w && rowS >= 0 && rowS < h) {
                    target.set(colT, rowT, source.get(colS, rowS));
                }
            }
        }
        source.show();
        target.show();
    }
}
