/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Analizador.*;
import Objects.*;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author loosc
 */
public class Interface extends javax.swing.JFrame {

    /**
     * Creates new form Interface
     */
    public Interface() {
        initComponents();
        this.setLocationRelativeTo(null);
        datosJuego = new int[100][100];
        datosMuestra = new int[4][100];
        setMatrizJuego();
        setMatrizMuestra();

        //piezas();
    }

    LexicoPZS pzs = new LexicoPZS();
    LexicoTRS trs = new LexicoTRS();
    LinkedList<Pieza> piezas = new LinkedList<Pieza>();
    LinkedList<Nivel> niveles = new LinkedList<Nivel>();
    //Variables del juego
    private int ganar;
    private int puntos = 100;
    private int posPiezas = 0;
    private int posNivel = 0;
    private int numNiveles = 0;
    private boolean lose = false;
    //Area de juego
    JButton[][] juego;
    int datosJuego[][];
    //Area para mover las piezas
    JButton[][] muestra;
    int datosMuestra[][];

    /*
     Pieza I = new Pieza("I");
     Pieza J = new Pieza("J");
     Pieza L = new Pieza("L");
     Pieza O = new Pieza("0");
     Pieza S = new Pieza("0");
     Pieza Z = new Pieza("0");
     Pieza T = new Pieza("0");

     private void piezas() {
     I.llenar("1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0");
     J.llenar("0,1,0,0,0,1,0,0,0,1,0,0,1,1,0,0", "1,1,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0");
     L.llenar("1,0,0,0,1,0,0,0,1,0,0,0,1,1,0,0", "1,1,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,1,1,1,0,0,0,1,0,0,0,0,0,0,0,0", "1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0");
     O.llenar("1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0");
     S.llenar("0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
     Z.llenar("1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0");
     T.llenar("1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
     }
     */
    private void bajarPieza() {
        int inicio = 0;
        for (int j = 0; j < 100; j++) {
            //System.out.println("pieza");
            //System.out.println(datosMuestra[0][j] + "," + datosMuestra[1][j] + "," + datosMuestra[2][j] + "," + datosMuestra[3][j]);
            if (datosMuestra[0][j] != 0 || datosMuestra[1][j] != 0 || datosMuestra[2][j] != 0 || datosMuestra[3][j] != 0) {

                inicio = j;
                break;
            }
        }

        System.out.println(piezas.get(posPiezas).getDir());
        System.out.println(piezas.get(posPiezas).getPosicion());
        System.out.println(piezas.get(posPiezas).getTipo());
        System.out.println("La pieza incia: " + inicio);
        //Recorrdo para saber donde esta lo mas alto del 
        boolean encontro = false;
        
        int altura = 99;

        for (int x = inicio; x < inicio + piezas.get(posPiezas).getX(); x++) {

            String a = "";

            for (int y = 0; y < niveles.get(posNivel).getX(); y++) {

                a += datosJuego[y][x] + ",";
                if (datosJuego[y][x] == 1) {
                    if (altura > y) {
                        altura = y;
                        encontro = true;
                        break;
                    } else {
                        encontro = true;
                    }

                }

            }

            System.out.println(a);
            if (!encontro) {
                altura = niveles.get(posNivel).getX() - 1;

            } else {
                //altura--; 
            }

        }
        System.out.println("Altura:" + altura);

        if (altura == 0) {
            System.out.println("Juego perdido");
            JOptionPane.showMessageDialog(null, "Juego perdido");
            lose = true;

        } else if (altura <= niveles.get(posNivel).getX() - 1 && altura >= niveles.get(posNivel).getX() - piezas.get(posPiezas).getY()){
            //Si se pasa de la altura 

            //Consigo la matriz de muestra segun el tamaño de la pieza 
            /*
            int pieza[][] = new int[piezas.get(posPiezas).getY()][piezas.get(posPiezas).getX()];
            int ejeX = 0;
            for (int y = 0; y < piezas.get(posPiezas).getY(); y++) {
                ejeX = 0;
                for (int x = inicio; x < inicio + piezas.get(posPiezas).getX(); x++) {
                    pieza[y][ejeX] = datosMuestra[y][x];
                    ejeX++;
                }
            }
            */
            System.out.println("");
            //
            boolean coloco = false;
            String piezaJuego;
            String piezaJ;

            System.out.println("Pruebas colocacion");
            for (int juegoY = niveles.get(posNivel).getX() - 1; juegoY >= piezas.get(posPiezas).getY() - 1; juegoY--) {
                int juegoX = inicio;
                int piezasColocadas = 0;
                int temporal = juegoY;
                piezaJuego = "";
                piezaJ = "";
                if (!coloco) {
                    for (int i = piezas.get(posPiezas).getY() - 1; i >= 0; i--) {

                        for (int j = 0; j < piezas.get(posPiezas).getX(); j++) {
                            juegoX =inicio+ j;
                            if (datosMuestra[i][juegoX] == 0) {
                                if (datosJuego[juegoY][juegoX] == 1) {
                                    if(piezasColocadas>4)piezasColocadas++;

                                }
                            } else if (datosMuestra[i][juegoX] == 1) {
                                if (datosJuego[juegoY][juegoX] == 0) {
                                    datosJuego[juegoY][juegoX] = 3;
                                    piezasColocadas++;

                                }
                            }
                            piezaJ += datosMuestra[j][juegoX] + ",";
                            piezaJuego += datosJuego[juegoY][juegoX] + ",";

                        }
                        juegoY--;

                    }
                    System.out.println("Juego " + piezaJuego);
                    System.out.println("Pieza " + piezaJ);
                    System.out.println("Piezas colocadas " + piezasColocadas);
                    
                    juegoY = temporal;
                    coloco = piezasColocadas == 4;
                    colocarPieza(piezasColocadas == 4);

                    

                }

            }

            if (!coloco) {
                JOptionPane.showMessageDialog(null, " Juego perdido");
                lose = true;
            }
        } else if (altura <= niveles.get(posNivel).getX() - piezas.get(posPiezas).getY() && altura >= piezas.get(posPiezas).getY()-1){
            //Si se pasa de la altura 

            //Consigo la matriz de muestra segun el tamaño de la pieza 
            /*
            int pieza[][] = new int[piezas.get(posPiezas).getY()][piezas.get(posPiezas).getX()];
            int ejeX = 0;
            for (int y = 0; y < piezas.get(posPiezas).getY(); y++) {
                ejeX = 0;
                for (int x = inicio; x < inicio + piezas.get(posPiezas).getX(); x++) {
                    pieza[y][ejeX] = datosMuestra[y][x];
                    ejeX++;
                }
            }
            */
            System.out.println("");
            //
            boolean coloco = false;
            String piezaJuego;
            String piezaJ;

            System.out.println("Pruebas colocacion");
            for (int juegoY = altura; juegoY >= piezas.get(posPiezas).getY() - 1; juegoY--) {
                int juegoX = inicio;
                int piezasColocadas = 0;
                int temporal = juegoY;
                piezaJuego = "";
                piezaJ = "";
                if (!coloco) {
                    for (int i = piezas.get(posPiezas).getY() - 1; i >= 0; i--) {

                        for (int j = 0; j < piezas.get(posPiezas).getX(); j++) {
                            juegoX =inicio+ j;
                            if (datosMuestra[i][juegoX] == 0) {
                                if (datosJuego[juegoY][juegoX] == 1) {
                                    if(piezasColocadas>4)piezasColocadas++;

                                }
                            } else if (datosMuestra[i][juegoX] == 1) {
                                if (datosJuego[juegoY][juegoX] == 0) {
                                    datosJuego[juegoY][juegoX] = 3;
                                    piezasColocadas++;

                                }
                            }
                            piezaJ += datosMuestra[j][juegoX] + ",";
                            piezaJuego += datosJuego[juegoY][juegoX] + ",";

                        }
                        juegoY--;

                    }
                    System.out.println("Juego " + piezaJuego);
                    System.out.println("Pieza " + piezaJ);
                    System.out.println("Piezas colocadas " + piezasColocadas);
                    
                    juegoY = temporal;
                    coloco = piezasColocadas == 4;
                    colocarPieza(piezasColocadas == 4);

                    

                }

            }

            if (!coloco) {
                JOptionPane.showMessageDialog(null, " Juego perdido");
                lose = true;
            }
        }else{
            JOptionPane.showMessageDialog(null, " Juego perdido");
                lose = true;
        }
    }

    public void colocarPieza(boolean colocar) {
        if (colocar) {
            //En donde se encuentren 3 pongo 1 
            for (int i = 0; i < niveles.get(posNivel).getX(); i++) {
                for (int j = 0; j < niveles.get(posNivel).getY(); j++) {
                    if (datosJuego[i][j] == 3) {
                        datosJuego[i][j] = 1;
                    }
                }
            }
        } else {
            //En donde ayan 3 punto 0 
            for (int i = 0; i < niveles.get(posNivel).getX(); i++) {
                for (int j = 0; j < niveles.get(posNivel).getY(); j++) {
                    if (datosJuego[i][j] == 3) {
                        datosJuego[i][j] = 0;
                    }
                }
            }
        }
    }

    private void setMatrizJuego() {
        juego = new JButton[100][100];
        int x = 5;
        int y = 5;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                juego[i][j] = new JButton();
                //juego[i][j].setBackground(Color.lightGray);
                //Pinto del color de fondo predeterminado
                juego[i][j].setBackground(Color.decode("#CCFFCC"));

                juego[i][j].setBounds(y, x, 5, 5);
                juego[i][j].setBorderPainted(false);
                jPaneJuego.add(juego[i][j]);
                y += 5;

            }
            x += 5;
            y = 5;
        }

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                datosJuego[i][j] = 0;
            }
        }

    }

    private void comprobarJuego() {
        int linea = 0;
        //Reviso si alguna linea se lleno para suprimirla e ir bajando las lineas de arriba
        for (int i = niveles.get(posNivel).getX() - 1; i > 0; i--) {
            int casillas = 0;

            for (int j = 0; j < niveles.get(posNivel).getY(); j++) {
                if (datosJuego[i][j] == 1) {
                    casillas++;
                }

            }
            if (casillas == niveles.get(posNivel).getY()) {
                //Elimino la linea i
                linea++;
                for (int j = i; j > 0; j--) {
                    for (int k = 0; k < niveles.get(posNivel).getY(); k++) {
                        datosJuego[j][k] = datosJuego[j - 1][k];
                    }
                }
                for (int j = 0; j < niveles.get(posNivel).getY(); j++) {
                    datosJuego[0][j] = 0;
                }
            }
        }
        if (linea == 1) {
            puntos += 10;
        } else if (linea == 2) {
            puntos += 15;
        } else if (linea >= 3) {
            puntos += 20;
        }

    }

    private void setMatrizMuestra() {
        muestra = new JButton[4][100];
        int x = 5;
        int y = 5;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 100; j++) {
                muestra[i][j] = new JButton();
                muestra[i][j].setBackground(Color.decode("#CCFFCC"));
                muestra[i][j].setBounds(y, x, 5, 5);
                muestra[i][j].setBorderPainted(false);
                jpaneMuestra.add(muestra[i][j]);
                y += 5;

            }
            x += 5;
            y = 5;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 100; j++) {
                datosMuestra[i][j] = 0;
            }
        }

    }

    //Los metodos de pintar si en los datos esta 0 es vacio 1 es ocupado y 2 es que esta fuera del rango
    //0  lightGray  (casilla vacia)
    //1 blue        (casilla ocupada)
    //2 #CCFFCC     (casilla fuera de dimensiones)
    private void pintarMuestra() {
        for (int i = 0; i < 4; i++) {
            for (int j = niveles.get(posNivel).getY(); j < 100; j++) {
                datosMuestra[i][j] = 2;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 100; j++) {
                if (datosMuestra[i][j] == 0) {
                    muestra[i][j].setBackground(Color.LIGHT_GRAY);
                } else if (datosMuestra[i][j] == 1) {
                    muestra[i][j].setBackground(Color.blue);
                } else if (datosMuestra[i][j] == 2) {
                    muestra[i][j].setBackground(Color.decode("#CCFFCC"));
                }
            }
        }

    }

    public void pintarJuego() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (datosJuego[i][j] == 0) {
                    juego[i][j].setBackground(Color.LIGHT_GRAY);
                } else if (datosJuego[i][j] == 1) {
                    juego[i][j].setBackground(Color.blue);
                } else {
                    juego[i][j].setBackground(Color.decode("#CCFFCC"));
                }
            }
        }
    }

    private void pasarNivel() {
        //Verifica si ya tiene 100 puntos y pasa de nivel    
        if (puntos == ganar && posNivel + 1 == numNiveles) {
            JOptionPane.showMessageDialog(null, "Juego completado");
        } else if (puntos == ganar) {

            
            posNivel++;
            colocarNivel();
            pintarMuestra();
            pintarJuego();
            puntos=0;
        } else if (lose) {
            JOptionPane.showMessageDialog(null, "Juego perdido");
        }

    }

    private void colocarNivel() {
        datosJuego = niveles.get(posNivel).getMatriz();
        lblNivel.setText(niveles.get(posNivel).getNombre());
        lblPts.setText(String.valueOf(puntos));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblPts = new javax.swing.JLabel();
        lblNivel = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTRS = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPZS = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jPaneJuego = new javax.swing.JPanel();
        jpaneMuestra = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        btnTRS = new javax.swing.JMenuItem();
        btnPZS = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnATRS = new javax.swing.JMenuItem();
        btnAPZS = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuMe = new javax.swing.JMenuItem();
        jMenuManual = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jMenu3.setText("jMenu3");

        jMenuItem7.setText("jMenuItem7");

        jMenu5.setText("jMenu5");

        jMenu6.setText("jMenu6");

        jMenuItem8.setText("jMenuItem8");

        jMenuItem9.setText("jMenuItem9");

        jMenuItem1.setText("jMenuItem1");

        jMenu8.setText("jMenu8");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jButton1.setText("Rotar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Nivel:");

        jLabel2.setText("Puntos:");

        lblPts.setText("---------");

        lblNivel.setText("---------");

        jButton3.setText("<<");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(">>");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txtTRS.setColumns(20);
        txtTRS.setRows(5);
        jScrollPane1.setViewportView(txtTRS);

        txtPZS.setColumns(20);
        txtPZS.setRows(5);
        jScrollPane2.setViewportView(txtPZS);

        jButton5.setText("Bajar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPaneJuego.setBackground(new java.awt.Color(204, 255, 204));
        jPaneJuego.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jPaneJuegoLayout = new javax.swing.GroupLayout(jPaneJuego);
        jPaneJuego.setLayout(jPaneJuegoLayout);
        jPaneJuegoLayout.setHorizontalGroup(
            jPaneJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
        jPaneJuegoLayout.setVerticalGroup(
            jPaneJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

        jpaneMuestra.setBackground(new java.awt.Color(204, 255, 204));
        jpaneMuestra.setBorder(new javax.swing.border.MatteBorder(null));

        javax.swing.GroupLayout jpaneMuestraLayout = new javax.swing.GroupLayout(jpaneMuestra);
        jpaneMuestra.setLayout(jpaneMuestraLayout);
        jpaneMuestraLayout.setHorizontalGroup(
            jpaneMuestraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpaneMuestraLayout.setVerticalGroup(
            jpaneMuestraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jLabel3.setText("Archivo TRS");

        jLabel4.setText("Archivo PZS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPaneJuego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpaneMuestra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 70, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(150, 150, 150)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(36, 36, 36)
                                .addComponent(lblPts, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNivel))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(lblPts)
                            .addComponent(jButton5)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jpaneMuestra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPaneJuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jMenu1.setText("Aplicacion");

        btnTRS.setText("Abrir TRS");
        btnTRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTRSActionPerformed(evt);
            }
        });
        jMenu1.add(btnTRS);

        btnPZS.setText("Abrir PZS");
        btnPZS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPZSActionPerformed(evt);
            }
        });
        jMenu1.add(btnPZS);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Juego");

        btnATRS.setText("Analizar TRS");
        btnATRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnATRSActionPerformed(evt);
            }
        });
        jMenu2.add(btnATRS);

        btnAPZS.setText("Analizar PZS");
        btnAPZS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAPZSActionPerformed(evt);
            }
        });
        jMenu2.add(btnAPZS);

        jMenuItem2.setText("Iniciar Juego");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Configurar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Ayuda");

        jMenuMe.setText("Acerca de");
        jMenuMe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuMeActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuMe);

        jMenuManual.setText("Manual");
        jMenuManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuManualActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuManual);

        jMenuBar1.add(jMenu4);

        jMenu7.setText("Reportes");

        jMenuItem10.setText("Reporte Tokens");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem10);

        jMenuItem11.setText("Reporte Errores");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem11);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTRSActionPerformed
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.trs", "TRS");
        fc.setFileFilter(filtro);
        fc.showOpenDialog(this);
        File archivo = fc.getSelectedFile();
        if (archivo != null) {
            txtTRS.setText(getContent(archivo.getAbsolutePath()));
        }


    }//GEN-LAST:event_btnTRSActionPerformed

    private void btnATRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnATRSActionPerformed
        //Analiza el archivo TRS
        trs.clear();
        trs.scannerTRS(txtTRS.getText());
        JOptionPane.showMessageDialog(null, "Analisis TRS completo");
    }//GEN-LAST:event_btnATRSActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //MOVER A LA DERECHA

        for (int i = 0; i < 4; i++) {
            int ultimo = datosMuestra[i][niveles.get(posNivel).getY() - 1];
            int j;
            for (j = niveles.get(posNivel).getY() - 1; j > 0; j--) {
                //Realizar los cambios del nivel
                datosMuestra[i][j] = datosMuestra[i][j - 1];
            }
            datosMuestra[i][j] = ultimo;

        }
        pintarMuestra();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        try {
            trs.htmlToken();
            pzs.htmlToken();
            String webpage = "C:\\Users\\loosc\\OneDrive\\Escritorio\\Practica1\\TokensPZS.html";
            String webpage1 = "C:\\Users\\loosc\\OneDrive\\Escritorio\\Practica1\\TokensTRS.html";
            Runtime.getRuntime().exec("cmd /c start " + webpage);
            Runtime.getRuntime().exec("cmd /c start " + webpage1);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo generar reporte de tokens");

        }


    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Roto pieza del juego
        if (piezas.get(posPiezas).getPosicion() == 3) {
            piezas.get(posPiezas).setPosicion(0);
            datosMuestra = piezas.get(posPiezas).getArriba();
            pintarMuestra();
        } else if (piezas.get(posPiezas).getPosicion() == 0) {
            piezas.get(posPiezas).setPosicion(1);
            datosMuestra = piezas.get(posPiezas).getAbajo();
            pintarMuestra();
        } else if (piezas.get(posPiezas).getPosicion() == 1) {
            piezas.get(posPiezas).setPosicion(2);
            datosMuestra = piezas.get(posPiezas).getIzquierda();
            pintarMuestra();
        } else if (piezas.get(posPiezas).getPosicion() == 2) {
            piezas.get(posPiezas).setPosicion(3);
            datosMuestra = piezas.get(posPiezas).getDerecha();
            pintarMuestra();
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnPZSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPZSActionPerformed
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.pzs", "PZS");
        fc.setFileFilter(filtro);
        fc.showOpenDialog(this);
        File archivo = fc.getSelectedFile();
        if (archivo != null) {
            txtPZS.setText(getContent(archivo.getAbsolutePath()));
        }

    }//GEN-LAST:event_btnPZSActionPerformed

    private void btnAPZSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAPZSActionPerformed
        //Analizis PZS
        pzs.clear();
        pzs.scannerPZS(txtPZS.getText());
        JOptionPane.showMessageDialog(null, "Analizis PZS completo");
    }//GEN-LAST:event_btnAPZSActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // ERRORES
        try {
            trs.htmlError();
            pzs.htmlError();

            String webpage = "C:\\Users\\loosc\\OneDrive\\Escritorio\\Practica1\\ErrorPZS.html";
            String webpage1 = "C:\\Users\\loosc\\OneDrive\\Escritorio\\Practica1\\ErrorTRS.html";
            Runtime.getRuntime().exec("cmd /c start " + webpage);
            Runtime.getRuntime().exec("cmd /c start " + webpage1);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo generar reporte de tokens");

        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuMeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMeActionPerformed
        About me = new About();
        me.setVisible(true);

    }//GEN-LAST:event_jMenuMeActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // Inicia el juego
        //Reinicio todo
        
        lose = false;
        posPiezas = 0;
        numNiveles = 0;
        posNivel = 0;
        puntos = 0;
        
        
        //Obtengo los datos de las piezas y coloco la pieza 1
        for (int i = 0; i < pzs.getTokens().size(); i++) {
            //Si es coma reviso en i-1 para saber la pieza e i+1 para saber la direccion de la pieza
            if (pzs.getTokens().get(i).getToken() == 7) {
                if (pzs.getTokens().get(i - 1).getToken() == 0) {//Pieza I
                    if (pzs.getTokens().get(i + 1).getToken() == 8) {//derecha
                        Pieza temp = new Pieza("I");
                        temp.llenar("1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("derecha");
                        piezas.add(temp);

                    } else if (pzs.getTokens().get(i + 1).getToken() == 9) {//arriba
                        Pieza temp = new Pieza("I");
                        temp.llenar("1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("arriba");
                        piezas.add(temp);

                    } else if (pzs.getTokens().get(i + 1).getToken() == 10) {//abajo
                        Pieza temp = new Pieza("I");
                        temp.llenar("1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("abajo");
                        piezas.add(temp);

                    } else if (pzs.getTokens().get(i + 1).getToken() == 11) {//izquierda
                        Pieza temp = new Pieza("I");
                        temp.llenar("1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0", "1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("izquierda");
                        piezas.add(temp);

                    }
                } else if (pzs.getTokens().get(i - 1).getToken() == 1) {//PIEZA J

                    if (pzs.getTokens().get(i + 1).getToken() == 8) {//derecha
                        Pieza temp = new Pieza("J");
                        temp.llenar("0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0", "1,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0", "1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0");
                        temp.setDir("derecha");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 9) {//arriba
                        Pieza temp = new Pieza("J");
                        temp.llenar("0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0", "1,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0", "1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0");
                        temp.setDir("arriba");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 10) {//abajo
                        Pieza temp = new Pieza("J");
                        temp.llenar("0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0", "1,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0", "1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0");
                        temp.setDir("abajo");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 11) {//izquierda
                        Pieza temp = new Pieza("J");
                        temp.llenar("0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0", "1,1,0,0,1,0,0,0,1,0,0,0,0,0,0,0", "1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0");
                        temp.setDir("izquierda");
                        piezas.add(temp);
                    }
                } else if (pzs.getTokens().get(i - 1).getToken() == 2) {//Pieza L

                    if (pzs.getTokens().get(i + 1).getToken() == 8) {//derecha
                        Pieza temp = new Pieza("L");
                        temp.llenar("1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0", "1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0", "0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("derecha");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 9) {//arriba
                        Pieza temp = new Pieza("L");
                        temp.llenar("1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0", "1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0", "0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("arriba");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 10) {//abajo
                        Pieza temp = new Pieza("L");
                        temp.llenar("1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0", "1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0", "0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("abajo");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 11) {//izquierda
                        Pieza temp = new Pieza("L");
                        temp.llenar("1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0", "1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0", "0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("izquierda");
                        piezas.add(temp);
                    }
                } else if (pzs.getTokens().get(i - 1).getToken() == 3) {//PIEZA O

                    if (pzs.getTokens().get(i + 1).getToken() == 8) {//derecha
                        Pieza temp = new Pieza("O");
                        temp.llenar("1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("derecha");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 9) {//arriba
                        Pieza temp = new Pieza("O");
                        temp.llenar("1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("arriba");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 10) {//abajo
                        Pieza temp = new Pieza("O");
                        temp.llenar("1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("abajo");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 11) {//izquierda
                        Pieza temp = new Pieza("O");
                        temp.llenar("1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0");
                        temp.setDir("izquierda");
                        piezas.add(temp);
                    }
                } else if (pzs.getTokens().get(i - 1).getToken() == 4) {//PIEZA S

                    if (pzs.getTokens().get(i + 1).getToken() == 8) {//derecha
                        Pieza temp = new Pieza("S");
                        temp.llenar("0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
                        temp.setDir("derecha");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 9) {//arriba
                        Pieza temp = new Pieza("S");
                        temp.llenar("0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
                        temp.setDir("arriba");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 10) {//abajo
                        Pieza temp = new Pieza("S");
                        temp.llenar("0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
                        temp.setDir("abajo");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 11) {//izquierda
                        Pieza temp = new Pieza("S");
                        temp.llenar("0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "0,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
                        temp.setDir("izquierda");
                        piezas.add(temp);
                    }
                } else if (pzs.getTokens().get(i - 1).getToken() == 5) {//PIEZA Z

                    if (pzs.getTokens().get(i + 1).getToken() == 8) {//derecha
                        Pieza temp = new Pieza("Z");
                        temp.llenar("1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0");
                        temp.setDir("derecha");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 9) {//arriba
                        Pieza temp = new Pieza("Z");
                        temp.llenar("1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0");
                        temp.setDir("arriba");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 10) {//abajo
                        Pieza temp = new Pieza("Z");
                        temp.llenar("1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0");
                        temp.setDir("abajo");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 11) {//izquierda
                        Pieza temp = new Pieza("Z");
                        temp.llenar("1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,1,0,0,0,0,0,0,0");
                        temp.setDir("izquierda");
                        piezas.add(temp);
                    }
                } else if (pzs.getTokens().get(i - 1).getToken() == 6) {//PIEZA T

                    if (pzs.getTokens().get(i + 1).getToken() == 8) {//derecha
                        Pieza temp = new Pieza("T");
                        temp.llenar("1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
                        temp.setDir("derecha");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 9) {//arriba
                        Pieza temp = new Pieza("T");
                        temp.llenar("1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
                        temp.setDir("arriba");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 10) {//abajo
                        Pieza temp = new Pieza("T");
                        temp.llenar("1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
                        temp.setDir("abajo");
                        piezas.add(temp);
                    } else if (pzs.getTokens().get(i + 1).getToken() == 11) {//izquierda
                        Pieza temp = new Pieza("T");
                        temp.llenar("1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0", "0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0", "1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0", "0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,0");
                        temp.setDir("izquierda");
                        piezas.add(temp);
                    }
                }

            }
        }
        boolean levels = false;
        //Obtengo los datos y 
        for (int i = 0; i < trs.getTokens().size(); i++) {
            //Solo toma el primer token numero para el numero de niveles
            int a = trs.getTokens().get(i).getToken();
            if (levels == false && trs.getTokens().get(i).getToken() == 2) {
                numNiveles = Integer.valueOf(trs.getTokens().get(i).getLexema());
                levels = true;
            }
            //Segun el nivel que tiene
            if (trs.getTokens().get(i).getToken() == 4) {
                int x = Integer.parseInt(trs.getTokens().get(i - 1).getLexema());
                int y = Integer.parseInt(trs.getTokens().get(i + 1).getLexema());
                Nivel nivel = new Nivel(trs.getTokens().get(i + 2).getLexema(), x, y);

                String cadena = "";
                int contador = 0;
                int limite = i + nivel.getDimension() + 3;
                for (int j = i + 3; j < limite; j++) {
                    if (trs.getTokens().get(j).getLexema().equals("#")) {
                        cadena += "0,";
                    } else if (trs.getTokens().get(j).getLexema().equals("*")) {
                        cadena += "1,";
                    }

                }
                cadena += "2";
                nivel.setMatriz(cadena);
                System.out.println(contador);
                System.out.println(cadena);
                niveles.add(nivel);

            }

        }
        if (numNiveles >= 3 && numNiveles <= 10) {
            if (piezas.size() != 0 && niveles.size() != 0) {
                datosMuestra = piezas.get(posPiezas).getPieza();
                datosJuego = niveles.get(posNivel).getMatriz();
                pintarMuestra();

                colocarNivel();
                pintarJuego();
                JOptionPane.showMessageDialog(null, "Juego iniciado");

            }

        } else {
            JOptionPane.showMessageDialog(null, "Numero de niveles incorrectos");
        }


    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuManualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuManualActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // MOVER A LA IZQUIERDA
        //Cambair limites con variables segun nivel
        for (int i = 0; i < 4; i++) {
            int primero = datosMuestra[i][0];
            int j;
            for (j = 0; j < niveles.get(posNivel).getY() - 1; j++) {
                //Realizar los cambios del nivel
                datosMuestra[i][j] = datosMuestra[i][j + 1];
            }
            datosMuestra[i][j] = primero;

        }

        pintarMuestra();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //Indico ir a la pieza siguiente(BAJAR)

        //Logica del juego (BAJAR PIEZA)
        //Coloca la siguiente pieza
        if (lose) {
            JOptionPane.showMessageDialog(null, "Juego perdido");
        } else {

            comprobarJuego();

            bajarPieza();
            pintarJuego();
            comprobarJuego();

            pintarJuego();
            pasarNivel();
            lblNivel.setText(niveles.get(posNivel).getNombre());
            lblPts.setText(String.valueOf(puntos));
            posPiezas++;
            if (posPiezas == piezas.size()) {
                posPiezas = 0;
            }
            datosMuestra = piezas.get(posPiezas).getPieza();
            pintarMuestra();
            
        }
        //
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        String pts= JOptionPane.showInputDialog("Ingrese puntos: ");
        ganar = Integer.parseInt(pts);  
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });

    }

    public String getContent(String direction) {
        String cadena = "";
        try {

            FileReader entrada = new FileReader(direction);
            int c = entrada.read();
            while (c != -1) {
                c = entrada.read();
                char letra = (char) c;
                cadena += String.valueOf(letra);
                System.out.println(letra);
            }
            return cadena;

        } catch (Exception e) {
            System.out.println(e.toString());
            return cadena;
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnAPZS;
    private javax.swing.JMenuItem btnATRS;
    private javax.swing.JMenuItem btnPZS;
    private javax.swing.JMenuItem btnTRS;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuManual;
    private javax.swing.JMenuItem jMenuMe;
    private javax.swing.JPanel jPaneJuego;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpaneMuestra;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblPts;
    private javax.swing.JTextArea txtPZS;
    private javax.swing.JTextArea txtTRS;
    // End of variables declaration//GEN-END:variables

}
