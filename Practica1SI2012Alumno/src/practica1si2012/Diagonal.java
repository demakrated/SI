/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1si2012;

/**
 *
 * @author danielpedrajasvandevelde
 */
public class Diagonal implements Heuristica{
    
    private TNodo fin;
    
    public Diagonal(TNodo a){
        fin = a;
    }
    
    public int calcular(TNodo inicio){
        
        int horizontal, vertical;
        
        //horizontal = filas y columnas = columnas
        

        horizontal = Math.abs(inicio.getPosicion()[0] - fin.getPosicion()[0]);
        vertical = Math.abs(inicio.getPosicion()[1] - fin.getPosicion()[1]);

        if(vertical > horizontal){
            return 14*horizontal + 10*(vertical - horizontal);
        }
        else{
            return 14*vertical +10*(horizontal - vertical);
        }

    }
    
    public String toString(){
         return this.getClass().getName();
    }
    
}
