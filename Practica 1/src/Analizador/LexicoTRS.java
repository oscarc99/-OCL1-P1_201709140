/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import Objects.Erro;
import Objects.Token;
import java.io.FileReader;
import java.util.LinkedList;

/**
 *
 * @author loosc
 */
public class LexicoTRS {

    private LinkedList Errors = new LinkedList();
    private LinkedList tokens = new LinkedList();

    public LexicoTRS() {
    }

    public LinkedList getErrors() {
        return Errors;
    }

    public LinkedList getTokens() {
        return tokens;
    }

//Analizador lexico del archivo PZS que contiene un listado de las piezas 
    public LinkedList scannerPZS(String direction) {

        String content = getContent(direction);
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
                    if (chars[i] == '#') {
                        tokens.add(new Token(0, "#", row, column));
                        row++;
                    } else if (chars[i] == '*') {
                        tokens.add(new Token(1, "*", row, column));
                        row++;
                    } else if (Character.isLetter(chars[i])) {
                        //Reconociendo un id
                        state = 6;
                        aux += Character.toString(chars[i]);
                        row++;
                    } else if (Character.isDigit(chars[i])) {
                        //numero
                        aux += Character.toString(chars[i]);
                        state =7;
                        row++;
                    }else if (chars[i] == 10 || chars[i] == 11 || chars[i] == 9 || chars[i] == 13 || chars[i] == 65535) {//Salto de linea
                        column++;
                        row = 0;
                    } else if (chars[i] == 32) {

                        row++;
                    } else if (chars[i] == '/') {
                        state = 1;
                        row++;
                    } else {
                        int a = chars[i];
                        System.out.println(a);
                        Errors.add(new Erro("Error lexico", row, column, String.valueOf(chars[i])));
                        System.out.println("Error: " + String.valueOf(chars[i]));
                    }

                    break;
                case 1:
                    // code block
                    if (chars[i] == '/') {
                        state = 2;
                        row++;
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

                    }
                    break;
                case 3:
                    if (chars[i] == '!') {
                        state = 4;
                        row++;
                    } else {
                        tokens.add(new Token(9, "<", row, column));
                        row++;

                    }
                    break;
                case 4:
                    if (chars[i] == '!') {
                        state = 5;
                        row++;
                    } else {
                        if (chars[i] == 13 || chars[i] == 13) {
                            column++;
                            row = 0;
                        } else {
                            row++;
                        }
                    }
                    break;
                case 5:
                    if (chars[i] == '>') {
                        state = 0;
                        row++;

                    } else {
                        state = 4;
                        row++;
                    }
                    break;
                case 6://IDENTIFICADOR
                    if (Character.isDigit(chars[i]) || Character.isLetter(chars[i]) || chars[i] == '_') {
                        aux += String.valueOf(chars[i]);
                        row++;
                    } else {
                        if (aux.equals("x")) {
                            tokens.add(new Token(4, "x", row, column));
                            aux="";
                            
                        } else {
                            tokens.add(new Token(3, aux, row, column));
                            aux="";
                           

                        }

                        i--;
                    }
                    break;
                case 7:
                    if(Character.isDigit(chars[i])){
                        aux +=String.valueOf(chars[i]);
                        row++;
                    }else{
                        tokens.add(new Token(2, aux, row, column));
                        aux="";
                    }
                    i--;
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

}
