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
public class Nivel {

    private String nombre;
    private int x;
    private int y;
    private int matriz[][];
    private int dimension;

    public Nivel() {
    }
    //0  lightGray  (casilla vacia)
    //1 blue        (casilla ocupada)
    //2 #CCFFCC     (casilla fuera de dimensiones)

    public Nivel(String nombre, int x, int y) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        matriz = new int[100][100];
        dimension = x * y;
}
    
    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setMatriz(String casillas) {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                matriz[i][j]=2;
            }
        }
         String[] arr = casillas.split(","); 
         int contador =0;
         for (int i = 0; i < x; i++) {
             for (int j = 0; j < y; j++) {
                 matriz[i][j]=Integer.parseInt(arr[contador]);
                 contador++;
                 
             }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

}
