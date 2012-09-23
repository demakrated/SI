/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package practica1si2012;

/**
*
* @author danielpedrajasvandevelde
*/
public class Exhaustiva implements Heuristica{
       
    TNodo fin;
    
    public Exhaustiva(TNodo fin)
    {
        this.fin = fin;
    }
    
    //la heuristica no añade coste, por tanto es expansion por niveles
    public int calcular(TNodo inicio){
        
        return 0;
    }
    
    public String toString(){
         return this.getClass().getName();
    }
}