package HW1;

public class Hilbert {
    private Turtle turtle;
    public Hilbert(int n) {
        turtle = new Turtle(0.5, 0.5, 0.0);
        double max = Math.pow(2, n);
        turtle.setXscale(0, max);
        turtle.setYscale(0, max);
        hilbert(n);
    }


    // Hilbert curve
    private void hilbert(int n) {
        if (n == 0) return;
        turtle.turnLeft(90);
        treblih(n-1);
        turtle.goForward(1.0);
        turtle.turnLeft(-90);
        hilbert(n-1);
        turtle.goForward(1.0);
        hilbert(n-1);
        turtle.turnLeft(-90);
        turtle.goForward(1.0);
        treblih(n-1);
        turtle.turnLeft(90);
    }

    // evruc trebliH
    public void treblih(int n) {
        if (n == 0) return;
        turtle.turnLeft(-90);
        hilbert(n-1);
        turtle.goForward(1.0);
        turtle.turnLeft(90);
        treblih(n-1);
        turtle.goForward(1.0);
        treblih(n-1);
        turtle.turnLeft(90);
        turtle.goForward(1.0);
        hilbert(n-1);
        turtle.turnLeft(-90);
    }


    // plot a Hilber curve of order n
    public static void main(String[] args) {
        int n = Integer.parseInt("6");
        new Hilbert(n);
    }
}
