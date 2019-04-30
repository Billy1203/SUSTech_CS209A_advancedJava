package HW1;

public class Scale {
    public static void main(String[] args){
        int w = Integer.parseInt(args[1]);
        int h = Integer.parseInt(args[2]);

        Picture source = new Picture(args[0]);
        Picture target = new Picture(w, h);
        for(int colT = 0; colT < w; colT++){
            for(int rowT = 0; rowT<h; rowT++){
                int colS = colT * source.width()/w;
                int rowS = rowT * source.height()/h;
                target.set(colT, rowT, source.get(colS, rowS));
            }
        }
        source.show();
        target.show();
    }
}
