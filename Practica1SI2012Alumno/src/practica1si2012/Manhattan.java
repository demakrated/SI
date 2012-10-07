/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1si2012;

/**
 *
 * @author danielpedrajasvandevelde
 */
public class Manhattan implements Heuristica{
    
    private TNodo fin;
    
    public Manhattan(TNodo fin){
        this.fin = fin;
    }
    
    public int calcular(TNodo inicio){
        
        int D = 1;  //variable que ayuda a mantener una buena relacion entre la horizontal y vertical
                    //incrementandola podemos hacer que mejore h(x) hasta cierto punto
        
        int res1 = (fin.getPosicion()[1] - inicio.getPosicion()[1]);    //columnas
        int res2 = (fin.getPosicion()[0] - inicio.getPosicion()[0]);    //filas
        
        return D*Math.abs(res2) + Math.abs(res1);
        
    }
    
    public String toString(){
         return this.getClass().getName();
    }
    
}

