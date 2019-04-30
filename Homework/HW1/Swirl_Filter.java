package HW1;

public class Swirl_Filter {
    public static void main(String[] args) {
        Picture source = new Picture("mandrill.png");
        int w  = source.width();
        int h = source.height();
        
        double colCenter = 0.5 * (w  - 1);
        double rowCenter = 0.5 * (h - 1);

        Picture target = new Picture(w, h);

        for (int colT = 0; colT < w; colT++) {
            for (int rowT = 0; rowT < h; rowT++) {
                double distanceCol = colT - colCenter;
                double distanceRow = rowT - rowCenter;
                double r = Math.sqrt(distanceCol*distanceCol + distanceRow*distanceRow);
                double angle = Math.PI / 256 * r;
                int colS = (int) (+distanceCol * Math.cos(angle) - distanceRow * Math.sin(angle) + colCenter);
                int rowS = (int) (+distanceCol * Math.sin(angle) + distanceRow * Math.cos(angle) + rowCenter);

                if (colS >= 0 && colS < w && rowS >= 0 && rowS < h)
                    target.set(colT, rowT, source.get(colS, rowS));
            }
        }
        source.show();
        target.show();
    }
}
