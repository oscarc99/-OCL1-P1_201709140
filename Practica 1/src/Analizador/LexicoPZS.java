/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import Objects.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author loosc
 */
public class LexicoPZS {

    private LinkedList<Erro> Errors = new LinkedList<Erro>();
    private LinkedList<Token> tokens = new LinkedList<Token>();

    public LexicoPZS() {
    }

    public LinkedList<Erro> getErrors() {
        return Errors;
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    public void clear() {
        Errors.clear();
        tokens.clear();
    }
//Analizador lexico del archivo PZS que contiene un listado de las piezas 

    public LinkedList scannerPZS(String content) {

        char[] chars = content.toCharArray();

        int state = 0;
        Token t = new Token();
        String aux = "";
        int row = 0;
        int column = 0;
        //recorre todos los caracteres 
        for (int i = 0; i < chars.length; i++) {

            switch (state) {
                case 0:
                    // Estado 0 del automata
                    if (chars[i] == 'I') {
                        tokens.add(new Token(0, "I", row, column,"Pieza I"));
                        row++;
                    } else if (chars[i] == 'J') {
                        tokens.add(new Token(1, "J", row, column,"Pieza J"));
                        row++;
                    } else if (chars[i] == 'L') {
                        tokens.add(new Token(2, "L", row, column,"Pieza L"));
                        row++;
                    } else if (chars[i] == 'O') {
                        tokens.add(new Token(3, "O", row, column,"Pieza O"));
                        row++;
                    } else if (chars[i] == 'S') {
                        tokens.add(new Token(4, "S", row, column,"Pieza S"));
                        row++;
                    } else if (chars[i] == 'Z') {
                        tokens.add(new Token(5, "Z", row, column,"Pieza Z"));
                        row++;
                    } else if (chars[i] == 'T') {
                        tokens.add(new Token(6, "T", row, column,"Pieza T"));
                        row++;
                    } else if (chars[i] == ',') {
                        tokens.add(new Token(7, ",", row, column,"Signo especial ,"));
                        row++;
                    } else if (chars[i] == '>') {
                        tokens.add(new Token(8, ">", row, column,"Posicion derecha"));
                        row++;
                    } else if (chars[i] == '^') {
                        tokens.add(new Token(9, "^", row, column,"Posicion arriba"));
                        row++;
                    } else if (chars[i] == 'v') {
                        tokens.add(new Token(10, "v", row, column,"Posicion abajo"));
                        row++;
                    } else if (chars[i] == '<') {
                        if (i == chars.length) {
                            tokens.add(new Token(11, "<", row, column,"Posicion izquierda"));
                            
                        } else if (chars[i + 1] == '!') {
                            state = 3;
                            aux += String.valueOf(chars[i]);
                            row++;
                        } else {
                            tokens.add(new Token(11, "<", row, column,"Posicion izquierda"));
                            row++;
                        }

                    } else if (chars[i] == 10 || chars[i] == 11 || chars[i] == 9 || chars[i] == 13 || chars[i] == 65535) {//Salto de linea
                        column++;
                        row = 0;
                    } else if (chars[i] == 32) {

                        row++;
                    } else if (chars[i] == '/') {
                        state = 1;
                        aux += String.valueOf(chars[i]);
                        row++;
                    } else {

                        Errors.add(new Erro("Error lexico", row, column, String.valueOf(chars[i])));

                    }

                    break;
                case 1:
                    // code block
                    if (chars[i] == '/') {
                        state = 2;
                        row++;
                        aux += String.valueOf(chars[i]);
                    } else {
                        Errors.add(new Erro("Error lexico", row, column, "/"));
                        i--;
                        state = 0;

                    }
                    break;
                case 2:
                    if (chars[i] == 10 || chars[i] == 11 || chars[i] == 9 || chars[i] == 13) {
                        state = 0;
                        column++;
                        row = 0;
                        tokens.add(new Token(12, aux, row, column,"Comentario unulinea"));
                        aux = "";

                    } else {
                        aux += String.valueOf(chars[i]);
                        row++;

                    }
                    break;
                case 3:
                    if (chars[i] == '!') {
                        aux += String.valueOf(chars[i]);
                        state = 4;
                        row++;
                    } else {
                        tokens.add(new Token(11, "<", row, column,"Posicion izquierda"));
                        row++;
                        aux = "";

                    }
                    break;
                case 4:
                    if (chars[i] == '!') {
                        state = 5;
                        aux += String.valueOf(chars[i]);
                        row++;
                    } else {
                        if (chars[i] == 13 || chars[i] == 13) {
                            column++;
                            row = 0;
                            aux += String.valueOf(chars[i]);
                        } else {
                            row++;
                            aux += String.valueOf(chars[i]);
                        }
                    }
                    break;
                case 5:
                    if (chars[i] == '>') {
                        state = 0;
                        row++;
                        aux += String.valueOf(chars[i]);
                        tokens.add(new Token(13, aux, row, column,"Comentario multilinea"));
                        aux = "";

                    } else {
                        state = 4;
                        row++;
                        aux += String.valueOf(chars[i]);
                    }
                    break;

            }
        }

        return tokens;
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

    public void htmlError() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("reportes\\ErrorPZS.html"));
            String contenido = "";
            contenido += "<html><head><title>ERRORES PZS</title></head><body> \n";
            contenido += "        <table class=\"egt\" border=\"1\">\n"
                    + "            <tr>\n"
                    + "              <td>No.</td>\n"
                    + "              <td>Tipo</td>\n"
                    + "              <td>Error</td>\n"
                    + "              <td>Fila</td>\n"
                    + "              <td>Columna</td>\n"
                    + "            </tr>";

            for (int i = 0; i < Errors.size(); i++) {
                contenido += "<tr> \n";
                contenido += "<td>\n";
                contenido += Integer.toString(i+1);
                contenido += "</td>\n";
                contenido += "<td>\n";
                contenido += Errors.get(i).getType();
                contenido += "</td>\n";
                contenido += "<td>\n";
                contenido += Errors.get(i).getError();
                contenido += "</td>\n";
                contenido += "<td>\n";
                contenido += Integer.toString(Errors.get(i).getRow());
                contenido +="</td>\n";
                contenido +="<td>\n";
                contenido += Integer.toString(Errors.get(i).getColumn());
                contenido += "</td>\n";
                contenido += "<td>\n";
                contenido += "</tr> \n";

            }

            contenido += "</body></html>";
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void htmlToken() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("reportes\\TokensPZS.html"));
            String contenido = "";
            contenido += "<html><head><title>Tokens PZS</title></head><body> \n";
            contenido += "        <table class=\"egt\" border=\"1\">\n"
                    + "            <tr>\n"
                    + "              <td>Token</td>\n"
                    + "              <td>Tipo</td>\n"
                    + "              <td>Lexema</td>\n"
                    + "              <td>Fila</td>\n"
                    + "              <td>Columna</td>\n"
                    + "            </tr>";

            for (int i = 0; i < tokens.size(); i++) {
                contenido += "<tr> \n";
                contenido += "<td>\n";
                contenido += Integer.toString(tokens.get(i).getToken());
                contenido += "</td>\n";
                contenido += "<td>\n";
                contenido += tokens.get(i).getTipo();
                contenido += "</td>\n";
                contenido += "<td>\n";
                contenido += tokens.get(i).getLexema();
                contenido += "</td>\n";
                contenido += "<td>\n";
                contenido += Integer.toString(tokens.get(i).getRow());
                contenido +="</td>\n";
                contenido +="<td>\n";
                contenido += Integer.toString(tokens.get(i).getColumn());
                contenido += "</td>\n";
                contenido += "<td>\n";
                contenido += "</tr> \n";

            }

            contenido += "</body></html>";
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

   



}
