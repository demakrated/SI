/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1si2012;
import java.util.ArrayList;
/**
 *
 * @author danielpedrajasvandevelde
 */
public class TNodo {
    
    private boolean visitado = false;
    private int fila = 0;
    private int columna = 0;
    private int coste = 0;
    private int orden = 1;
    private TNodo daddy;    //puntero para no perder el padre y poder reconstruir el camino
    
    //constructor por parametros
    TNodo(int x, int y, int coste, int orden, TNodo papa){
        fila = x; columna = y; daddy = papa; this.coste = coste; this.orden = orden;
    }
    
    //devuelve vector con los nodos expandidos (arriba, abajo, izq, dxa)
    public ArrayList<TNodo> expandir(int [][] mundo){
        
        //array de los nodos a los que se puede expandir
        ArrayList <TNodo> expansion = new ArrayList <TNodo>();        
        
        //si puedo expandir hacia arriba expando
        if(fila > 1 && mundo[fila - 1][columna] == 0){
            expansion.add(new TNodo(fila-1, columna, coste + 1, orden + 1, this));
            //System.out.println("expande arriba");
        }
        //si me puedo expandir hacia la izquierda
        if(columna > 1 && mundo[fila][columna - 1] == 0){
            expansion.add(new TNodo(fila, columna-1,coste + 1, orden+1, this));
            //System.out.println("expande izq");
        }
        //si me puedo expandir derecha
        if(columna + 1 < mundo.length - 1 && mundo[fila][columna + 1] == 0){
            expansion.add(new TNodo(fila, columna+1,coste + 1, orden + 1, this));
            //System.out.println("expande dxa");
        }
        //expansion hacia abajo
        if(fila + 1 < mundo.length - 1 && mundo[fila + 1][columna] == 0){
            expansion.add(new TNodo(fila + 1, columna,coste + 1, orden + 1, this));
            //System.out.println("expande abajo");
        }
        
        return expansion;
    }
   
    //funcion para comparar pesos de nodos
    public int comparador(TNodo b){
        
        if(this.coste < b.coste){
            return -1;}
        else if(this.coste == b.coste){
            return 0;}
        else{
            return 1;
        }
    }
    
    //getter coste
    public int getCoste(){
        return coste;
    }
    
    //metodo equals para la PQ sobrescribiendolo para usarlo en JUnit
    public boolean equals(Object tn){
        
        TNodo nodo = (TNodo) tn;
        
        return (fila == nodo.fila && columna == nodo.columna);
    }
    
    
    public String toString(){
        
        return fila + ", " + columna;
    }
    
    public int[] getPosicion(){
        int[] pos = new int[2];
        pos[0]=fila;
        pos[1]=columna;
        return pos;
    }
    
    //getter padre
    public TNodo getPapa(){
        return daddy;
    }
    
    //getter de orden
    public int getOrden(){
        return orden;
    }
    
    //setter del orden de visita
    public void setOrden(int orden){
        this.orden = orden;
    }
}
