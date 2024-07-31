import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawingWindow extends JFrame {
    private final int WIDTH=800;
    private final int HEIGHT=800;
    private BufferedImage image;
    private final Range DOMAIN = new Range(-400, 400);
    private final Range RANGE = new Range(-400, 400);
    private final Range newRange = new Range(-10, 100);
    private final Range newDomain = new Range(-5, 100);

    public DrawingWindow() {
        setTitle("Drawing Window");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.dispose();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        };

        add(panel);

        setVisible(true);
        drawGridLines();
        drawAxes();
        
    }



    public void drawAxes(){
        for (double i = 0; i < HEIGHT; i+=newRange.getRange()/1000) {
            Coord c = new Coord(-newDomain.min(), i);
            c.scaleY(RANGE.getRange() / newRange.getRange());
            c.scaleX(DOMAIN.getRange() / newDomain.getRange());
            drawPixel(c, Color.BLACK);
        }
        for (double i = 0; i < WIDTH; i+=newDomain.getRange()/1000) {
            Coord c = new Coord(i, -newRange.min());
            c.scaleY(RANGE.getRange() / newRange.getRange());
            c.scaleX(DOMAIN.getRange() / newDomain.getRange());
            drawPixel(c, Color.BLACK);
        }
    }
    public void drawGridLines(){
        //fix this
        for (int y = 0; y < newRange.getRange(); y += newRange.getRange()/10) {
            for (double x = 0; x < newDomain.getRange(); x+=newDomain.getRange()/100) {
                Coord c = new Coord(x, y);
                c.scaleY(RANGE.getRange() / newRange.getRange());
                c.scaleX(DOMAIN.getRange() / newDomain.getRange());
                drawPixel(c, Color.GRAY);
            }
        }
        for (int x = 0; x < newDomain.getRange(); x += newDomain.getRange()/10) {
            for (double y = 0; y < newRange.getRange(); y+=newRange.getRange()/100) {
                Coord c = new Coord(x, y);
                c.scaleY(RANGE.getRange() / newRange.getRange());
                c.scaleX(DOMAIN.getRange() / newDomain.getRange());
                drawPixel(c, Color.GRAY);
            }
        }
    }

    private static Color[] colors = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA };
    private static int colorIndex = 0;

    public void drawFunction(Parser p){
        ArrayList<Coord> coords = new ArrayList<Coord>();
        Color color = colors[colorIndex % colors.length];
        colorIndex++;
            for (double x = newDomain.min(); x < newDomain.max(); x += .001) {
                Coord c = new Coord(x, p.evaluateAt(x));
                coords.add(c);
                c.scaleY(RANGE.getRange() / newRange.getRange());
                c.scaleX(DOMAIN.getRange() / newDomain.getRange());
                c.putInQ4(newRange, newDomain,RANGE, DOMAIN);
                if(x>-1&&x<1){
                    System.out.println(c);
                }
                
                SwingUtilities.invokeLater(() -> {
                    drawPixel(c, color);
                });
            }
    }

    public void drawPixel(Coord c, Color color) {
        double x = c.getX();
        double y = c.getY();
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            if (color == Color.YELLOW) {
                image.setRGB((int) x, (int) y, 0xb0a810);
            }
            if (color == Color.ORANGE) {
                image.setRGB((int) x, (int) y, 0xbf7f17);
            } else {
                image.setRGB((int) x, (int) y, color.getRGB());
            }
            repaint();
        }
    }
}