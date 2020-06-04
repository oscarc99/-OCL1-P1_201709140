/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author loosc
 */
public class Pieza {

    private String tipo;
    private int arriba[][] = new int[4][100];
    private int abajo[][] = new int[4][100];
    private int izquierda[][] = new int[4][100];
    private int derecha[][] = new int[4][100];
    private String dir;
    /*
     0 arriba
     1 abajo
     2 izquierda
     3 derecha
     */
    private int posicion;

    public String getTipo() {
        return tipo;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getPosicion() {
        return posicion;
    }

    public int[][] getPieza() {
        if (dir.equals("abajo")) {
            posicion = 1;
            return abajo;
        } else if (dir.equals("arriba")) {
            posicion = 0;
            return arriba;
        } else if (dir.equals("derecha")) {
            posicion = 3;
            return derecha;
        } else if (dir.equals("izquierda")) {
            posicion = 2;
            return izquierda;
        } else {
            return new int[4][100];
        }
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Pieza(String tipo, String dir) {
        this.tipo = tipo;
        this.dir = dir;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setArriba(int[][] arriba) {
        this.arriba = arriba;
    }

    public void setAbajo(int[][] abajo) {
        this.abajo = abajo;
    }

    public void setIzquierda(int[][] izquierda) {
        this.izquierda = izquierda;
    }

    public void setDerecha(int[][] derecha) {
        this.derecha = derecha;
    }

    public Pieza(String tipo) {
        this.tipo = tipo;
    }

    public int[][] getArriba() {
        return arriba;
    }

    public int[][] getAbajo() {
        return abajo;
    }

    public int[][] getIzquierda() {
        return izquierda;
    }

    public int[][] getDerecha() {
        return derecha;
    }

    public Pieza() {
    }

    public void llenar(String arriba, String abajo, String izquierda, String derecha) {
        //Lleno de 0 para indicar vacias
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 100; j++) {
                this.arriba[i][j] = 0;
                this.abajo[i][j] = 0;
                this.izquierda[i][j] = 0;
                this.derecha[i][j] = 0;
            }
        }
        //Coloco la pieza de como debe de ir para arriba
        String ar[] = arriba.split(",");
        int x = 0;
        int y = 0;
        for (String dato : ar) {
            this.arriba[y][x] = Integer.parseInt(dato);
            x++;
            if (x == 4) {
                x = 0;
                y++;
            }
        }
        //Coloco la pieza como debe ir para abajo
        String ab[] = abajo.split(",");
        x = 0;
        y = 0;
        for (String dato : ab) {
            this.abajo[y][x] = Integer.parseInt(dato);

            x++;
            if (x == 4) {
                x = 0;
                y++;
            }
        }
        //Coloco la pieza como debe ir para derecha
        String der[] = derecha.split(",");
        x = 0;
        y = 0;
        for (String dato : der) {
            this.derecha[y][x] = Integer.parseInt(dato);

            x++;
            if (x == 4) {
                x = 0;
                y++;
            }
        }
        //Coloco la pieza como debe ir para izquierda
        String iz[] = izquierda.split(",");
        x = 0;
        y = 0;
        for (String dato : iz) {
            this.izquierda[y][x] = Integer.parseInt(dato);

            x++;
            if (x == 4) {
                x = 0;
                y++;
            }
        }

    }

}
