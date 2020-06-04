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
public class Erro {
    private String type;
    private int row;
    private int column;
    String error;

    public Erro() {
    }

    public Erro(String type, int row, int column, String error) {
        this.type = type;
        this.row = row;
        this.column = column;
        this.error=error;
        
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    
    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
