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
        
        Heuristica Hip = new Pitagoras(inicio);
        int hipotenusa = Hip.calcular(inicio);
        int horizontal, vertical;
        
        if(fin.getPosicion()[0] < inicio.getPosicion()[0]){
            horizontal = inicio.getPosicion()[0] - fin.getPosicion()[0];
        }
        else{
            horizontal = fin.getPosicion()[0] - inicio.getPosicion()[0];
        }
        
         if(fin.getPosicion()[1] < inicio.getPosicion()[1]){
            vertical = inicio.getPosicion()[1] - fin.getPosicion()[1];
        }
        else{
            vertical = fin.getPosicion()[1] - inicio.getPosicion()[1];
        }
        
        
        int min = hipotenusa*Math.min(horizontal, vertical);
        
        //multiplicmos por la distancia horizontal o vertical
        int mid = (horizontal + vertical)*vertical;
        
        int last = 2*Math.min(horizontal, vertical);
        
        return min + mid + last;
    }
    
    public String toString(){
         return this.getClass().getName();
    }
    
}
