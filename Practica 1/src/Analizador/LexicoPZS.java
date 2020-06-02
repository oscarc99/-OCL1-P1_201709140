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
import java.util.LinkedList;

/**
 *
 * @author loosc
 */
public class LexicoPZS {

    private LinkedList Errors = new LinkedList();
    private LinkedList tokens = new LinkedList();

    public LexicoPZS() {
    }

    public LinkedList getErrors() {
        return Errors;
    }

    public LinkedList getTokens() {
        return tokens;
    }

    public void clear(){
        Errors.clear();
        tokens.clear();
    }
//Analizador lexico del archivo PZS que contiene un listado de las piezas 
    public LinkedList scannerPZS(String direction) {
        
        String content=getContent(direction);
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
                        tokens.add(new Token(0, "I", row, column));
                        row++;
                    } else if (chars[i] == 'J') {
                        tokens.add(new Token(1, "J", row, column));
                        row++;
                    } else if (chars[i] == 'L') {
                        tokens.add(new Token(2, "L", row, column));
                        row++;
                    } else if (chars[i] == 'O') {
                        tokens.add(new Token(3, "O", row, column));
                        row++;
                    } else if (chars[i] == 'S') {
                        tokens.add(new Token(4, "S", row, column));
                        row++;
                    } else if (chars[i] == 'Z') {
                        tokens.add(new Token(5, "Z", row, column));
                        row++;
                    } else if (chars[i] == 'T') {
                        tokens.add(new Token(6, "T", row, column));
                        row++;
                    } else if (chars[i] == ',') {
                        tokens.add(new Token(7, ",", row, column));
                        row++;
                    } else if (chars[i] == '>') {
                        tokens.add(new Token(8, ">", row, column));
                        row++;
                    } else if (chars[i] == '^') {
                        tokens.add(new Token(9, "^", row, column));
                        row++;
                    } else if (chars[i] == 'v') {
                        tokens.add(new Token(10, "v", row, column));
                        row++;
                    } else if (chars[i] == '<') {
                        if (i == chars.length) {
                            tokens.add(new Token(9, "<", row, column));
                        } else if (chars[i + 1] == '!') {
                            state = 3;
                            row++;
                        } else {
                            tokens.add(new Token(9, "<", row, column));
                            row++;
                        }

                    } else if (chars[i] == 10 || chars[i] == 11 || chars[i] == 9 || chars[i] == 13 || chars[i]==65535) {//Salto de linea
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
                        System.out.println("Error: "+String.valueOf(chars[i]));
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

            }
        }

        return tokens;
    }

    public String getContent(String direction) {
        String cadena="";
        try {
            
            FileReader entrada = new FileReader(direction);
           int c=entrada.read();
            while(c!=-1){
                c=entrada.read();
                char letra = (char)c;
                cadena +=  String.valueOf(letra); 
                System.out.println(letra);
            }
            return cadena;

        } catch (Exception e) {
            System.out.println(e.toString());
            return cadena;
        }

    }
}
