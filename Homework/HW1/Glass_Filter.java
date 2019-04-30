package HW1;

import java.awt.*;

public class Glass_Filter {
    public static void main(String[] args) {
        Picture source = new Picture("mandrill.png");
        int w  = source.width();
        int h = source.height();
        Picture target = new Picture(w, h);

        for (int colT = 0; colT < w; colT++) {
            for (int rowT = 0; rowT < h; rowT++) {
                int colS = (w  + colT + random(-5, 5)) % w;
                int rowS = (h + rowT + random(-5, 5)) % h;
                Color color = source.get(colS, rowS);
                target.set(colT, rowT, color);
            }
        }
        source.show();
        target.show();
    }

    public static int random(int a, int b) {
        return a + StdRandom.uniform(b-a+1);
    }
}
