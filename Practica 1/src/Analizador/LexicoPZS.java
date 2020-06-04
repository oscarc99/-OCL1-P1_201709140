/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import Objects.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
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
                            tokens.add(new Token(11, "<", row, column));
                            
                        } else if (chars[i + 1] == '!') {
                            state = 3;
                            aux += String.valueOf(chars[i]);
                            row++;
                        } else {
                            tokens.add(new Token(11, "<", row, column));
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
                        tokens.add(new Token(12, aux, row, column));
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
                        tokens.add(new Token(9, "<", row, column));
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
                        tokens.add(new Token(13, aux, row, column));
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
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\loosc\\OneDrive\\Escritorio\\Practica1\\ErrorPZS.html"));
            String contenido = "";
            contenido += "<html><head><title>ERRORES PZS</title></head><body> \n";
            contenido += "        <table class=\"egt\" border=\"1\">\n"
                    + "            <tr>\n"
                    + "              <td>No.</td>\n"
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
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\loosc\\OneDrive\\Escritorio\\Practica1\\TokensPZS.html"));
            String contenido = "";
            contenido += "<html><head><title>Tokens PZS</title></head><body> \n";
            contenido += "        <table class=\"egt\" border=\"1\">\n"
                    + "            <tr>\n"
                    + "              <td>Token</td>\n"
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

    public void reportTokenPZS() throws IOException {
        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter("C:\\Users\\loosc\\OneDrive\\Escritorio\\TokensPZS.pdf");
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Tabla Tokens"));
        Table tabla = new Table(new float[]{4, 4, 4});
        tabla.setWidth(600);
        tabla.addHeaderCell(new Cell().add(new Paragraph("Token")));
        tabla.addHeaderCell(new Cell().add(new Paragraph("Lexema")));
        tabla.addHeaderCell(new Cell().add(new Paragraph("Fila")));
        tabla.addHeaderCell(new Cell().add(new Paragraph("Columna")));

        for (int i = 0; i < tokens.size(); i++) {
            tabla.addCell(new Cell().add(new Paragraph(Integer.toString(tokens.get(i).getToken()))));
            tabla.addCell(new Cell().add(new Paragraph(tokens.get(i).getLexema())));
            tabla.addCell(new Cell().add(new Paragraph(Integer.toString(tokens.get(i).getRow()))));
            tabla.addCell(new Cell().add(new Paragraph(Integer.toString(tokens.get(i).getColumn()))));

        }
        document.add(tabla);
        document.close();

    }

    public void reportErroresPZS() throws IOException {

        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter("C:\\Users\\loosc\\OneDrive\\Escritorio\\ErroresPZS.pdf");
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.add(new Paragraph("Tabla Errores"));
        Table tabla = new Table(new float[]{4, 4});
        tabla.setWidth(100);
        tabla.addHeaderCell(new Cell().add(new Paragraph("No.")));
        tabla.addHeaderCell(new Cell().add(new Paragraph("Error")));
        tabla.addHeaderCell(new Cell().add(new Paragraph("Fila")));
        tabla.addHeaderCell(new Cell().add(new Paragraph("Columna")));

        for (int i = 0; i < Errors.size(); i++) {
            tabla.addCell(new Cell().add(new Paragraph(Integer.toString(i + 1))));
            tabla.addCell(new Cell().add(new Paragraph(Errors.get(i).getError())));
            tabla.addCell(new Cell().add(new Paragraph(Integer.toString(Errors.get(i).getRow()))));
            tabla.addCell(new Cell().add(new Paragraph(Integer.toString(Errors.get(i).getColumn()))));
        }
        document.add(tabla);

        document.close();

    }

}
