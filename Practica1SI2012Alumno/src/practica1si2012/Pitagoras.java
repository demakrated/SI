/*
+ * To change this template, choose Tools | Templates
+ * and open the template in the editor.
+ */
package practica1si2012;

/**
+ *
+ * @author danielpedrajasvandevelde
+ */
public class Pitagoras implements Heuristica{
    
    private TNodo fin;
    
    public Pitagoras(TNodo fin){
        this.fin = fin;
    }
    
    public int calcular(TNodo inicio){
        
        int res1 = (fin.getPosicion()[1] - inicio.getPosicion()[1]);
       
        int res2 = (fin.getPosicion()[0] - inicio.getPosicion()[0]);

        return (int) Math.sqrt(Math.pow(res1, 2) + Math.pow(res2, 2));
    }
    
    public String toString(){
         return this.getClass().getName();
    }
}