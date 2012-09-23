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
    private int real = 0;
    private Heuristica h;
    private TNodo daddy;    //puntero para no perder el padre y poder reconstruir el camino
    
    //constructor por parametros
    TNodo(int x, int y, int real, TNodo papa, Heuristica h){
        fila = x; columna = y; daddy = papa; this.h = h; this.real = real; coste = -1;
    }
    
    TNodo(int x, int y, int real, TNodo papa){
        fila = x; columna = y; daddy = papa; this.real = real; coste = -1;
    }
    
    //devuelve vector con los nodos expandidos (arriba, abajo, izq, dxa)
    public ArrayList<TNodo> expandir(int [][] mundo){
        
        //array de los nodos a los que se puede expandir
        ArrayList <TNodo> expansion = new ArrayList <TNodo>();
        
        TNodo aux;
        
        //si puedo expandir hacia arriba expando
        if(fila > 1 && mundo[fila - 1][columna] == 0){
            expansion.add(new TNodo(fila-1, columna, real + 1, this, h));
            //System.out.println("expande arriba");
        }
        //si me puedo expandir hacia la izquierda
        if(columna > 1 && mundo[fila][columna - 1] == 0){
            expansion.add(new TNodo(fila, columna-1, real + 1, this, h));
            //System.out.println("expande izq");
        }
        //si me puedo expandir derecha
        if(columna + 1 < mundo.length - 1 && mundo[fila][columna + 1] == 0){
            expansion.add(new TNodo(fila, columna+1, real + 1, this, h));
            //System.out.println("expande dxa");
        }
        //expansion hacia abajo
        if(fila + 1 < mundo.length - 1 && mundo[fila + 1][columna] == 0){
            expansion.add(new TNodo(fila + 1, columna, real + 1, this, h));
            //System.out.println("expande abajo");
        }
        
        return expansion;
    }
   
    //funcion para comparar pesos de nodos
    public int comparador(TNodo b){
        
        if(this.getCoste() < b.getCoste()){
            return -1;}
        else if(this.getCoste() == b.getCoste()){
            return 0;}
        else{
            return 1;
        }
    }
    
    //getter coste
    public int getCoste(){
        if(coste<0) coste = real + h.calcular(this);
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
    

}
