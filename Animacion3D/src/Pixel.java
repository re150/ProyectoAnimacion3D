import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// para extraer las cordenadas

public class Pixel extends JPanel {
    private BufferedImage buffer;
    private BufferedImage fondo;
    private Graphics fondoG;
    private double vertex[][][][] = {{{{-1, -1, -1}, {1, -1, -1}}, {{-1, 1, -1}, {1, 1, -1}}}, {{{-1, -1, 1}, {1, -1, 1}}, {{-1, 1, 1}, {1, 1, 1}}}};
    private
    // Define vertices for a pyramid
    double[][][] vertex1 = {
            {{-1, -1, -1}, {1, -1, -1}, {0, -1, 1}}, // Base
            {{-1, -1, -1}, {1, -1, -1}, {0, 1, 0}},   // Side 1
            {{1, -1, -1}, {0, -1, 1}, {0, 1, 0}},     // Side 2
            {{0, -1, 1}, {-1, -1, -1}, {0, 1, 0}}     // Side 3
    };

    // rotations in radians
    private double xyR = 0, xzR = 0, yzR = 0;

    // view attributes
    private int posicion = 0;
    private  double scala = 0.10;
    private int viewX = 5, viewY = 20, viewZ = -20, tX = 0, tY = 0;
    private double t = 0;
    private double x, y, z, xf, yf, zf, sideCube = 0;
    private double[][] matrix = new double[4][241];
    private double[][] matrix1 = new double[4][241];
    private double tethax = 0;
    private double tethay = 0;
    private double tethaz = 0;
    private boolean estado1 = true;
    List<double[]> vertices;

    public Pixel() {
        init();
        repaint();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setDoubleBuffered(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    viewX -= 5;
                    t -= 0.005;
                    // Ajustar rotación izquierda
                    if (xyR > 0) xyR -= 0.001;
                    System.out.println("xyR = " + xyR);
                }
                if (key == KeyEvent.VK_RIGHT) {
                    viewX += 5;
                    t += 0.005;
                    // Ajustar rotación derecha
                    if (xyR < 0.005) xyR += 0.001;
                }
                if (key == KeyEvent.VK_UP) {
                    viewY -= 5;
                    if (xzR < 0.05) xzR += 0.01;
                    tethax += 1;
                    sideCube += 5;
                    posicion += 5;
                }
                if (key == KeyEvent.VK_DOWN) {
                    viewY += 5;
                    if (xzR > 0) xzR -= 0.01;
                    tethax -= 1;
                    sideCube -= 5;
                    posicion -= 5;
                }
                if (key == KeyEvent.VK_Z) {
                    viewZ += 5;
                }
                if (key == KeyEvent.VK_X) {
                    viewZ -= 10;
                    estado1 = !estado1;
                }
                if (key == KeyEvent.VK_W) {
                    tX += 10;
                }
                if (key == KeyEvent.VK_S) {
                    tX -= 10;

                }
                if (key == KeyEvent.VK_D) {
                  if(scala < 0.20)  scala += 0.05;
                }
                if (key == KeyEvent.VK_A) {
                    if(scala > 0.10)  scala -= 0.05;
                }
            }
        });
        cordenadasObj();
    }

    public void cordenadasObj (){
        String filePath = "D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Figuras\\Airplane_v1_L1.123c4a6fedec-1680-4a36-a228-b0d440a4f280\\11803_Airplane_v1_l1.obj";
         vertices = extractVerticesFromOBJ(filePath);
        // Imprime las coordenadas de los vértices
        for (double[] vertex : vertices) {
            System.out.println("Vertex: " + vertex[0] + ", " + vertex[1] + ", " + vertex[2]);
        }
    }

    public void init() {
        setSize(700, 700);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        fondo = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        fondoG = fondo.getGraphics();
        fondoG.setColor(Color.black);
        fondoG.fillRect(0, 0, getWidth(), getWidth());
        matrix1 = curve(0, 0, 0, 10);

        JFrame frame = new JFrame("Pixel");
        frame.setResizable(false);
        frame.add(this);
        frame.setSize(this.getWidth(), this.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void update(Graphics g) {
        g.drawImage(fondo, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        update(g);
        clear();
        try {
            // practica
         // tiposDeProyeccion(500, 400, 200, 50, 600, 0, Color.white);
         /*   tiposDeProyeccion(200, 100, 5, 500, 400, 0, Color.blue);*/
            // tiposDeProyeccion(600, 600, 5, 400, 800, 8, Color.RED);

           //drawCuboR1(201,50,Color.green);

            // drawCurve(100);
            //   if(estado1) drawPyramidR(350,  Color.blue);

           /*for (int i = 0; i < vertices.size() - 1; i++) {
                double[] puntoActual = vertices.get(i);
                double[] puntoSiguiente = vertices.get(i + 1);

                drawDDL(puntoActual[0] * 0.09, puntoActual[1] * 0.09 , puntoActual[2] * 0.09 , puntoSiguiente[0] * 0.09 , puntoSiguiente[1]*  0.09, puntoSiguiente[2]*  0.09, Color.RED);
            }*/
            if(estado1)   drawFigura(300,Color.red);
        } catch (Exception e) {
        }
    }

    public void clear() {
        fondoG.setColor(Color.black);
        fondoG.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        fondo.getGraphics().drawImage(buffer, x, y, this);
        repaint();
    }

    public void algoritmoDDALine(int x0, int y0, int x1, int y1, Color c) {
        float dx = x1 - x0;
        float dy = y1 - y0;
        float x = x0;
        float y = y0;
        float length;
        float xinc;
        float yinc;

        if (Math.abs(dx) > Math.abs(dy)) {

            length = Math.abs(dx);

        } else length = Math.abs(dy);

        xinc = dx / length;
        yinc = dy / length;

        putPixel(Math.round(x), Math.round(y), c);

        for (int k = 1; k < length; k++) {
            x = x + xinc;
            y = y + yinc;
            putPixel(Math.round(x), Math.round(y), c);
        }
    }

    public void drawDDL(double x1, double y1, double z1, double x2, double y2, double z2, Color c) {
        int xp = -50;
        int yp = -50;
        int zp = -130;
        double xUno = x1 - xp * (z1 / zp);
        double yUno = y1 - yp * (z1 / zp);
        double xDos = x2 - xp * (z2 / zp);
        double yDos = y2 - yp * (z2 / zp), xcu, ycu;
        double dx, dy, steps, xf, yf;
        double xinc, yinc, x, y;

        dx = xDos - xUno;
        dy = yDos - yUno;
        if (Math.abs(dx) > Math.abs(dy)) {
            steps = Math.abs(dx);
        } else {
            steps = Math.abs(dy);
        }
        xinc = dx / (double) steps;
        yinc = dy / (double) steps;
        x = xUno;
        y = yUno;

        for (int k = 1; k <= steps; k++) {
            x = x + xinc;
            y = y + yinc;
            putPixel((int) x, (int) y, c);
        }

    }


    public void drawCuboR1(int posicionCubo, Color c) {
        posicion = posicionCubo;
        // rotate cube
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    xyRotate1(vertex[x][y][z], Math.sin(xyR), Math.cos(xyR));
                    xzRotate1(vertex[x][y][z], Math.sin(xzR), Math.cos(xzR));
                    yzRotate1(vertex[x][y][z], Math.sin(yzR), Math.cos(yzR));
                }
            }
        }
        //bordes
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                drawEdge1(vertex[x][y][0][0], vertex[x][y][0][1], vertex[x][y][1][0], vertex[x][y][1][1], c);
                drawEdge1(vertex[x][0][y][0], vertex[x][0][y][1], vertex[x][1][y][0], vertex[x][1][y][1], c);
                drawEdge1(vertex[0][x][y][0], vertex[0][x][y][1], vertex[1][x][y][0], vertex[1][x][y][1], c);
            }
        }
    }

    final void drawEdge1(double x1, double y1, double x2, double y2, Color c) {
        algoritmoDDALine((int) (x1 * scala) + posicion, (int) (-y1 * scala) + posicion, (int) (x2 * scala) + posicion, (int) (-y2 * scala) + posicion, c);
    }

    final void xyRotate1(double p[], double sin, double cos) {
        double temp;
        temp = cos * p[0] + sin * p[1];
        p[1] = -sin * p[0] + cos * p[1];
        p[0] = temp;
    }

    final void xzRotate1(double p[], double sin, double cos) {
        double temp;
        temp = cos * p[0] + sin * p[2];
        p[2] = -sin * p[0] + cos * p[2];
        p[0] = temp;
    }

    final void yzRotate1(double p[], double sin, double cos) {
        double temp;
        temp = cos * p[1] + sin * p[2];
        p[2] = -sin * p[1] + cos * p[2];
        p[1] = temp;
    }


    public double[][] curve(double x, double y, double z, int sideCube) {
        double[][] escala = {{sideCube, 0, 0, 0}, {0, sideCube, 0, 0}, {0, 0, sideCube, 0}, {0, 0, 0, 1}};
        matrix[0][0] = x;
        matrix[1][0] = y;
        matrix[2][0] = z;
        matrix[3][0] = 1;
        int a = 1, j = 0;
        for (double i = 0; i < Math.PI * 24; i += (Math.PI * 24) / 240) {
            matrix[j][a] = (Math.cos(i) * 8);
            matrix[1][a] = i + matrix[1][a] - 13;
            matrix[2][a] = (i + z);
            matrix[3][a] = 1;
            a++;
        }
        matrix = multiply(escala, matrix);
        return matrix;
    }

    public double[][] multiply(double[][] a, double[][] b) {
        double[][] c = new double[a.length][b[0].length];
        if (a[0].length == b.length) {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    for (int k = 0; k < a[0].length; k++) {
                        c[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
        return c;
    }

    static double[][] Rotarx(double[][] m, double angle) {
        double cos = Math.cos(Math.toRadians(angle));
        double sen = Math.sin(Math.toRadians(angle));
        for (int i = 1; i < 241; i++) {
            double y = m[1][i];
            double z = m[2][i];
            m[1][i] = (y * cos) - (z * sen);
            m[2][i] = (y * sen) + (z * cos);
        }

        return m;
    }

    public void drawCurve(int numCurba) {
        for (int i = 23; i < numCurba; i++) {
            x = matrix1[0][i];
            y = matrix1[1][i];
            z = matrix1[2][i];
            xf = matrix1[0][i + 1];
            yf = matrix1[1][i + 1];
            zf = matrix1[2][i + 1];
            drawDDL(x + 350, y + 350, z, xf + 350, yf + 350, zf, Color.RED);
        }
    }

    public double[][] translateOrigin(double[][] m) {
        double x = m[0][0];
        double y = m[1][0];
        double z = m[2][0];
        x -= 100;
        y -= 100;
        z -= 50;
        xf = x;
        yf = y;
        zf = z;
        curve(x, y, z, (int) sideCube);
        return m;
    }

    public double[][] change(double[][] m) {
        for (int i = 0; i < 9; i++) {
            double x = m[0][i];
            double y = m[1][i];
            double z = m[2][i];
            x += 100;
            y += 100;
            z += 50;
            m[0][i] = x;
            m[1][i] = y;
            m[2][i] = z;
        }
        return m;
    }

    public void animacion() {
        while (true) {

            matrix1 = translateOrigin(matrix1);
            matrix1 = Rotarx(matrix1, tethax);
            matrix1 = Rotarx(matrix1, tethay);
            matrix1 = Rotarx(matrix1, tethaz);
            matrix1 = change(matrix1);

            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static List<double[]> extractVerticesFromOBJ(String filePath) {
        List<double[]> vertices = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Lee cada línea del archivo
            while ((line = br.readLine()) != null) {
                // Filtra las líneas que comienzan con 'v'
                if (line.startsWith("v ")) {
                    // Divide la línea en palabras y convierte las coordenadas a double
                    String[] parts = line.split("\\s+");
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);

                    // Agrega las coordenadas a la lista de vértices
                    vertices.add(new double[]{x, y, z});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vertices;
    }

    public void drawFigura(int posicionCubo, Color c) {
        posicion = posicionCubo;
        // rotate cube
        for (int i = 0; i < vertices.size(); i++) {
            xyRotate1(vertices.get(i), Math.sin(xyR), Math.cos(xyR));
            xzRotate1(vertices.get(i), Math.sin(xzR), Math.cos(xzR));
            yzRotate1(vertices.get(i), Math.sin(yzR), Math.cos(yzR));
        }

        // bordes
        for (int i = 0; i < vertices.size(); i++) {
            int j = (i + 1) % vertices.size();
            drawEdge(vertices.get(i), vertices.get(j), c);
        }
    }

    final void drawEdge(double[] p1, double[] p2, Color c) {
        algoritmoDDALine((int) (p1[0] * scala) + posicion, (int) (-p1[1] * scala) + posicion,
                (int) (p2[0] * scala) + posicion, (int) (-p2[1] * scala) + posicion, c);
    }


}