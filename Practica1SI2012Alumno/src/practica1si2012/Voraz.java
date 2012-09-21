/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1si2012;

/**
 *
 * @author danielpedrajasvandevelde
 */
public class Voraz implements Heuristica {
    
    /**
     *
     */
    public int calcular(int [][]M, TNodo inicio, TNodo llegada){
        
        int res1 = (llegada.getPosicion()[1] - inicio.getPosicion()[1]);
        if(res1 < 0){
            res1 = -res1;
        }
                
        int res2 = (llegada.getPosicion()[0] - inicio.getPosicion()[0]);
        if(res2 < 0){
            res2 = -res2;
        }
        
        return res1 + res2;
    }
}
    

