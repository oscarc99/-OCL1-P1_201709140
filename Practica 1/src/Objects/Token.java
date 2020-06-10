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
public class Token {
    private int token;
    private String lexema;
    private String tipo;
    private int row;
    private int column;
    

    public Token() {
    }

    public Token(int token, String lexema, int row, int column, String tipo) {
        this.token = token;
        this.lexema = lexema;
        this.row = row;
        this.column = column;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
    
    public int getToken() {
        return token;
    }

    public String getLexema() {
        return lexema;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    
}
