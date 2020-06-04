/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analizador;

import Objects.Erro;
import Objects.Token;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author loosc
 */
public class LexicoTRS {

    private LinkedList<Erro> Errors = new LinkedList<Erro>();
    private LinkedList<Token> tokens = new LinkedList<Token>();

    public LexicoTRS() {
    }

    public LinkedList<Erro> getErrors() {
        return Errors;
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    public void clear(){
        Errors.clear();
        tokens.clear();
    }
    
//Analizador lexico del archivo PZS que contiene un listado de las piezas 
    public LinkedList scannerTRS(String content) {
        
        Errors.clear();
        tokens.clear();
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
                    } else if (chars[i] == '-') {
                        tokens.add(new Token(4, "-", row, column));
                        aux = "";

                    } else if (Character.isLetter(chars[i])) {
                        //Reconociendo un id
                        aux += Character.toString(chars[i]);
                        state = 6;
                        row++;
                    } else if (Character.isDigit(chars[i])) {
                        //numero
                        aux += Character.toString(chars[i]);
                        state = 7;
                        row++;

                    } else if (chars[i] == 10 || chars[i] == 11 || chars[i] == 9 || chars[i] == 13 || chars[i] == 65535) {//Salto de linea
                        column++;
                        row = 0;
                    } else if (chars[i] == 32) {

                        row++;
                    } else if (chars[i] == '/') {
                        state = 1;
                        aux += Character.toString(chars[i]);
                        row++;
                    } else if (chars[i] == '<') {
                        aux += Character.toString(chars[i]);
                        state = 3;
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
                        aux += Character.toString(chars[i]);
                        row++;
                    } else {
                        Errors.add(new Erro("Error lexico", row, column, "/"));
                        i--;
                        aux="";
                        state = 0;

                    }
                    break;
                case 2:
                    if (chars[i] == 10 || chars[i] == 11 || chars[i] == 9 || chars[i] == 13) {
                        state = 0;
                        column++;
                        row = 0;
                        tokens.add(new Token(5, aux, row, column));
                        aux="";
                    }else{
                        aux += Character.toString(chars[i]);
                        row++;
                    }
                    break;
                case 3:
                    if (chars[i] == '!') {
                        state = 4;
                        row++;
                        aux += Character.toString(chars[i]);
                    } else {
                        tokens.add(new Token(9, "<", row, column));
                        aux="";
                        row++;
                        i--;

                    }
                    break;
                case 4:
                    if (chars[i] == '!') {
                        state = 5;
                        aux += Character.toString(chars[i]);
                        row++;
                    } else {
                        aux += Character.toString(chars[i]);
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
                        aux += Character.toString(chars[i]);
                        tokens.add(new Token(5, aux, row, column));
                        aux="";

                    } else {
                        state = 4;
                        aux += Character.toString(chars[i]);
                        row++;
                    }
                    break;
                case 6://IDENTIFICADOR
                    if (Character.isDigit(chars[i]) || Character.isLetter(chars[i]) || chars[i] == '_') {
                        aux += String.valueOf(chars[i]);
                        row++;
                    } else {
                        tokens.add(new Token(3, aux, row, column));
                        aux = "";
                        state = 0;
                        i--;
                    }
                    break;
                case 7:
                    if (Character.isDigit(chars[i])) {
                        aux += String.valueOf(chars[i]);
                        row++;
                    } else {
                        tokens.add(new Token(2, aux, row, column));
                        aux = "";
                        state = 0;
                        i--;
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
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\loosc\\OneDrive\\Escritorio\\Practica1\\ErrorTRS.html"));
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
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\loosc\\OneDrive\\Escritorio\\Practica1\\TokensTRS.html"));
            String contenido = "";
            contenido += "<html><head><title>Tokens TRS</title></head><body> \n";
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
    
    public void reportTokenTRS() throws IOException {
        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter("C:\\Users\\loosc\\OneDrive\\Escritorio\\TokensTRS.pdf");
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
  
  public void reportErroresTRS() throws IOException {
        
        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter("C:\\Users\\loosc\\OneDrive\\Escritorio\\ErroresTRS.pdf");
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
