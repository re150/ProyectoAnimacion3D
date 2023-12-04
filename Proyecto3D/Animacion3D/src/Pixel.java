import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.*;


public class Pixel extends JPanel {
    private BufferedImage buffer;
    private BufferedImage fondo;
    private Graphics fondoG;
    private double vertex[][][][] = {{{{-1, -1, -1}, {1, -1, -1}}, {{-1, 1, -1}, {1, 1, -1}}}, {{{-1, -1, 1}, {1, -1, 1}}, {{-1, 1, 1}, {1, 1, 1}}}};

    // rotations in radians
    private double xyRC1 = 0.04, xzRC1 = 0, yzRC1 = 0.02;
    private double xyRP = 0, xzRP = 0.08, yzRP = 0, xyR2 = 0, xzR2 = 0, yzR2 = 0, xyR3 = 0, xzR3 = 0, yzR3 = 0,
            xyR5 = 0, xzR5 = 0, yzR5 = 0;
    private  double xyR6 = 0, xzR6 = 0, yzR6 = 0;

    // posiciones
    private int posicionP = 300, posicion2 = 150,posicion3 = 500 ,posicion = 90,posicion5 = 100,posicion6 = 550;

    // x y y
    private int  pxp = 0, pyp = 0,  px2 = 0, py2 = 0, px3 = 0, py3 = 0, px4 = 0, py4 = 0,py5 = 0;
    private double scalaP = 0.09, scala2 =0.06, scala3 =0.09, scala4 =0.06,scala5 =20;
    private  int scala = 40, estadoM = 1;
    //Estados de la funciones
    private boolean estado1 = true,estado2 = false, estado3 = false, estado4 = false, estado5 = false;
    List<double[]> figure1, figure2, figure3,figure4, figure5;
    private Random random = new Random();
    private Clip Musicplay;

    public Pixel() {
        init();
        repaint();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setDoubleBuffered(true);
        playAudio("D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Music\\Free Bird Solo.wav");
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    // Ajustar rotación izquierda
                    if (xyRP > 0 && estado1) xyRP -= 0.005;
                    if (xyR2 > 0 && estado2) xyR2 -= 0.005;
                    if (xyR3 > 0 && estado3) xyR3 -= 0.005;
                    if (xyR5 > 0 && estado5) xyR5 -= 0.005;
                }
                if (key == KeyEvent.VK_RIGHT) {
                    // Ajustar rotación derecha
                    if (xyRP < 0.015 && estado1) xyRP += 0.005;
                    if (xyR2 < 0.015 && estado2) xyR2 += 0.005;
                    if (xyR3 < 0.015 && estado3) xyR3 += 0.005;
                    if (xyR5 < 0.015 && estado5) xyR5 += 0.005;
                }
                if (key == KeyEvent.VK_UP) {
                    //Rotacion  derecha en xz
                    if (xzRP < 0.10 && estado1) xzRP += 0.02;
                    if (xzR2 < 0.10 && estado2) xzR2 += 0.02;
                    if (xzR3 < 0.10 && estado3) xzR3 += 0.02;
                    if (xzR5 < 0.10 && estado5) xzR5 += 0.02;
                }
                if (key == KeyEvent.VK_DOWN) {
                    //Rotacion en izquierda en xz
                    if (xzRP > -0.04 && estado1) xzRP -= 0.02;
                    if (xzR2 > -0.04 && estado2) xzR2 -= 0.02;
                    if (xzR3 > -0.04 && estado3) xzR3 -= 0.02;
                    if (xzR5 > -0.04 && estado5) xzR5 -= 0.02;
                }
                if (key == KeyEvent.VK_E) {
                    //Rotacion en izquierda en xz
                    if (yzRP < 0.10 && estado1) yzRP += 0.02;
                    if (yzR2 < 0.10 && estado2) xzR2 += 0.02;
                    if (yzR3 < 0.10 && estado3) xzR3 += 0.02;
                    if (yzR5 < 0.10 && estado5) xzR5 += 0.02;
                }
                if (key == KeyEvent.VK_R) {
                    //Rotacion en izquierda en xz
                    if (yzRP > -0.04 && estado1) yzRP -= 0.02;
                    if (yzR2 > -0.04 && estado2) xzR2 -= 0.02;
                    if (yzR3 > -0.04 && estado3) xzR3 -= 0.02;
                    if (yzR5 > -0.04 && estado5) xzR5 -= 0.02;
                }
                if (key == KeyEvent.VK_Z) {
                    // Avance en x
                    if(pxp < 35 && estado1) pxp += 5;
                    if(px2 < 35 && estado2) px2 += 5;
                    if(px3 < 35 && estado3) px3 += 5;
                }
                if (key == KeyEvent.VK_X) {
                    // Retroceder en  x
                    if(pxp > -10 && estado1) pxp -=5;
                    if(px2 > -10 && estado2) px2 -=5;
                    if(px3 > -10 && estado3) px3 -=5;
                }
                if (key == KeyEvent.VK_W) {
                    // subir en y
                    if(pyp < 20 && estado1) pyp += 10;
                    if(py2 < 45 && estado2) py2 += 15;
                    if(py3 < 90 && estado3) py3 += 30;
                }
                if (key == KeyEvent.VK_S) {
                    // bajar en y
                  if(pyp > -5 && estado1)  pyp -= 5;
                  if(py2 > -80 && estado2)  py2 -= 20;
                  if(py3 > -60 && estado3)  py3 -= 15;
                  if(py4 > -60 && estado5)  py4 -= 15;
                  if(py5 > -60 && estado4)  py5 -= 1;
                }
                if (key == KeyEvent.VK_D) {
                    // Aumetar tamaño
                  if(scalaP < 0.20 && estado1)  scalaP += 0.05;
                  if(scala2 < 0.20 && estado2)  scala2 += 0.05;
                  if(scala3 < 0.20 && estado3)  scala3 += 0.05;
                }
                if (key == KeyEvent.VK_A) {
                    // Disminuir tamaño
                    if(scalaP > 0.05 && estado1)  scalaP -= 0.05;
                    if(scala2 > 0.05 && estado2)  scala2 -= 0.05;
                    if(scala3 > 0.05 && estado3)  scala3 -= 0.05;
                }
                if (key == KeyEvent.VK_M) {
                    // Reinicio
                    xyRP = 0; xzRP = 0; yzRP = 0; pxp = 0; pyp = 0;
                    xyR2 = 0; xzR2 = 0; yzR2 = 0; px2 = 0; py2 = 0;
                    xyR3 = 0; xzR3 = 0; yzR3 = 0; px3 = 0; py3 = 0;
                }
                if (key == KeyEvent.VK_1) {
                  estado1 = !estado1;
                  //  borrarFigura(figure1, posicionP, scalaP, xyRP, xzRP, yzRP, pxp, pyp);
                }
                if (key == KeyEvent.VK_2) {
                    estado2 = !estado2;
                }
                if (key == KeyEvent.VK_3) {
                    estado3 = !estado3;
                }
                if (key == KeyEvent.VK_4) {
                    estado4 = !estado4;
                }
                if (key == KeyEvent.VK_5) {
                    estado5 = !estado5;
                    estadoM = 2;
                    if(estadoM == 1) {
                        Musicplay.stop();
                        playAudio("D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Music\\Free Bird Solo.wav");
                    }else if(estadoM == 2) {
                        Musicplay.stop();
                       playAudio("D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Music\\XOCO1238⧹Tono de Alarma Nuclear.wav");
                    }
                }
                if (key == KeyEvent.VK_6) {
                    if(estado1)eliminarCoordenadasAleatorias(figure1, figure1.size()/2);
                    if(estado2) eliminarCoordenadasAleatorias(figure2, figure2.size()/2);
                   if(estado3)  eliminarCoordenadasAleatorias(figure3, figure3.size()/2);
                    if(estado4)eliminarCoordenadasAleatorias(figure5, figure5.size()/2);
                }
                if (key == KeyEvent.VK_7) {
                    cordenadasObj();
                    estadoM = 1;
                    Musicplay.stop();
                    playAudio("D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Music\\Free Bird Solo.wav");
                }
            }
        });
        cordenadasObj();

    }

    public void cordenadasObj (){
        String filePlane = "D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Figuras\\Airplane_v1_L1.123c4a6fedec-1680-4a36-a228-b0d440a4f280\\11803_Airplane_v1_l1.obj";
        String filePlane2 = "D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Figuras\\airplane_v2_L2.123c71795678-4b63-46c4-b2c6-549c45f4c806\\11805_airplane_v2_L2.obj";
        String file3 ="D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Figuras\\Tiger_I.obj";
        String file4 = "D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Figuras\\Atomic bomb.obj";
        String file5 = "D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Figuras\\obj\\Residential Buildings 001.obj";

        figure1 = extractVerticesFromOBJ(filePlane);
        figure2 = extractVerticesFromOBJ(filePlane);
        figure3 = extractVerticesFromOBJ(filePlane2);
        figure4 = extractVerticesFromOBJ(file4);
        figure5 = extractVerticesFromOBJ(file5);

    }
    public void init() {
        setSize(700, 700);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        fondo = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        fondoG = fondo.getGraphics();
        fondoG.setColor(Color.black);
        fondoG.fillRect(0, 0, getWidth(), getWidth());

        JFrame frame = new JFrame("Proyecto");
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
            if (estado1) drawFigura(figure1, posicionP, scalaP, xyRP, xzRP, yzRP, pxp, pyp, Color.red);
            if (estado2) drawFigura(figure2, posicion2, scala2, xyR2, xzR2, yzR2, px2, py2, Color.BLUE);
            if (estado3) drawFigura(figure3, posicion3, scala3, xyR3, xzR3, yzR3, px3, py3, Color.GREEN);
             if(estado4) drawFigura(figure5, posicion6,scala5,xyR6, xzR6, yzR6, px4, py5, Color.GRAY);
                 //drawCuboR1(Color.green);
            if(estado5) drawFigura(figure4,posicion5 ,scala4, xyR5, xzR5, yzR5,px4,py4,Color.GREEN);

        } catch (Exception e) {
        }
    }
    public void clear() {
        fondoG.setColor(Color.BLACK);
        fondoG.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }
    public void clearEnd () {
        fondoG.setColor(Color.white);
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
    public void drawFigura( List<double[]> figure, int posicion, double scala ,double xyR,double xzR,double yzR,int px,int py,  Color c) {
        // rotate
        for (int i = 0; i < figure.size(); i++) {
            xyRotate1(figure.get(i), Math.sin(xyR), Math.cos(xyR));
            xzRotate1(figure.get(i), Math.sin(xzR), Math.cos(xzR));
            yzRotate1(figure.get(i), Math.sin(yzR), Math.cos(yzR));

            // translate
            figure.get(i)[0] += px;
            figure.get(i)[1] += py;
        }
        // bordes
        for (int i = 0; i < figure.size(); i++) {
            int j = (i + 1) % figure.size();
            drawEdge(figure.get(i), figure.get(j),posicion,scala, c);

        }

    }
    private void drawEdge(double[] p1, double[] p2,int posicion, double scala, Color c) {
        algoritmoDDALine((int) (p1[0] * scala) + posicion , (int) (-p1[1] * scala) + posicion ,
                (int) (p2[0] * scala) + posicion , (int) (-p2[1] * scala) + posicion , c);

        if(((int) (p1[0] * scala) + posicion ) > 650) {
            if(!estado5)   pxp = -5;px2 = -1;px3 = -30;
        } else if( ((int) (p1[0] * scala) + posicion ) < 0){
            if(!estado5)  pxp =5;px2 = 1;px3 = 30;
        }
        if((int) (-p1[1] * scala) + posicion  < 0){
            if(!estado5)    pyp = -5; py2 = -1; py3 = -30;
        }else if((int) (-p1[1] * scala) + posicion >650 ){
          if(!estado5)  pyp = 5;  py2 = 1;py3 = 30;
        }
        if((int) (-p1[1] * scala) + posicion >400  && estado5 && estadoM != 3) {
            Musicplay.stop();
            estadoM =3;
            playAudio("D:\\M6\\Gráficas_por_computadora_2D_y_3D\\p3\\Music\\1.wav");
        }
        if((int) (-p1[1] * scala) + posicion >680  && estado5){
            // eliminarCoordenadasAleatorias(figure1, figure1.size());
           // figure1.clear();figure2.clear(); figure3.clear(); figure4.clear();
             clearEnd();
        }
    }
    final void xyRotate1(double p[], double sin, double cos) {
        double temp;
        temp = cos * p[0] + sin * p[1]  ;
        p[1] = -sin * p[0] + cos * p[1] ;
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
   final void drawEdge1(double x1, double y1, double x2, double y2,int scala, Color c) {
        algoritmoDDALine((int) (x1 * scala) + posicion, (int) (-y1 * scala) + posicion, (int) (x2 * scala) + posicion, (int) (-y2 * scala) + posicion, c);
    }
    public void drawCuboR1( Color c) {

        // rotate cube
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    xyRotate1(vertex[x][y][z], Math.sin(xyRC1), Math.cos(xyRC1));
                    xzRotate1(vertex[x][y][z], Math.sin(xzRC1), Math.cos(xzRC1));
                    yzRotate1(vertex[x][y][z], Math.sin(yzRC1), Math.cos(yzRC1));
                }
            }
        }
        //bordes
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                drawEdge1(vertex[x][y][0][0], vertex[x][y][0][1], vertex[x][y][1][0], vertex[x][y][1][1],scala, c);
                drawEdge1(vertex[x][0][y][0], vertex[x][0][y][1], vertex[x][1][y][0], vertex[x][1][y][1], scala,c);
                drawEdge1(vertex[0][x][y][0], vertex[0][x][y][1], vertex[1][x][y][0], vertex[1][x][y][1],scala ,c);
            }
        }
    }
    public void borrarFigura(List<double[]> figure, int posicion, double scala, double xyR, double xzR, double yzR, int px, int py) {
        // rotate
        for (int i = 0; i < figure.size(); i++) {
            xyRotate1(figure.get(i), Math.sin(xyR), Math.cos(xyR));
            xzRotate1(figure.get(i), Math.sin(xzR), Math.cos(xzR));
            yzRotate1(figure.get(i), Math.sin(yzR), Math.cos(yzR));

            // translate
            figure.get(i)[0] += px;
            figure.get(i)[1] += py;
        }
        // bordes
        for (int i = 0; i < figure.size(); i++) {
            int j = (i + 1) % figure.size();
            borrarEdge(figure.get(i), figure.get(j), posicion, scala);
        }
    }
    public void borrarEdge(double[] p1, double[] p2, int posicion, double scala) {
        algoritmoDDALine((int) (p1[0] * scala) + posicion, (int) (-p1[1] * scala) + posicion,
                (int) (p2[0] * scala) + posicion, (int) (-p2[1] * scala) + posicion, Color.black);
    }
    private void eliminarCoordenadaAleatoria(List<double[]> figura) {
        if (!figura.isEmpty()) {
            int indiceAleatorio = random.nextInt(figura.size());
            figura.remove(indiceAleatorio);
        }
    }
    private void eliminarCoordenadasAleatorias(List<double[]> figura, int cantidad) {
        if (figura.size() < 15) figura.clear();
        for (int i = 0; i < cantidad; i++) {
            eliminarCoordenadaAleatoria(figura);
        }
    }
    private  void playAudio(String audioFilePath) {
        try {
            switch (estadoM) {
                case 1 -> {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    Musicplay = clip;
                    Musicplay.loop(Musicplay.LOOP_CONTINUOUSLY);
                    Musicplay.start();
                }
                case 2 -> {
                    Musicplay.stop();
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    Musicplay = clip;
                    Musicplay.loop(Musicplay.LOOP_CONTINUOUSLY);
                    Musicplay.start();
                }
                case 3 -> {
                Musicplay.stop();
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                Musicplay = clip;
                Musicplay.loop(Musicplay.LOOP_CONTINUOUSLY);
                Musicplay.start();
            }
         }
        } catch (Exception e) {}
    }
}