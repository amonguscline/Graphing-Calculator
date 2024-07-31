import java.io.*;

class Main {
    static final BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    static final DrawingWindow drawingWindow = new DrawingWindow();;
    public static void main(String[] args) throws Exception {
        System.out.println("enter an expression");
        while (true) {
            Parser function = null;
            function = new Parser(r.readLine());
            drawingWindow.drawFunction(function);
            System.out.println("type another expression if you want");
        }
    }
}