/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1si2012;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author danielpedrajasvandevelde
 */
public class Pruebas {
    
    private int [][] mundo; 

    private int origen; //Punto de partida del robot. Será la columna 1 y esta fila
    private int destino; //Punto de destino del robot. Será la columna tamaño-1 y esta fila
    private char camino[][]; //Camino que debe seguir el robot. Será el resultado del A*
    private int expandidos[][]; //Orden de los nodos expandidos. Será el resultado del A*
    private int tamaño; //Tamaño del mundo
    private int ordenExpansion = 0;  //Orden de los nodos
    private TNodo inicio;
    private TNodo fin; 
        
        
    //camino de nodos visitados del camino más corto
    private ArrayList<TNodo> visitados = new ArrayList<TNodo>();
    
    //constructor
    public Pruebas(){
        origen = 1;
        destino =3;
        tamaño = 5;
        ordenExpansion = 0;
        inicio = new TNodo(1,1,1,null);
        fin = new TNodo(2,2,2,null);
        mundo = new int[tamaño][tamaño];
        camino = new char[tamaño][tamaño];
        expandidos = new int[tamaño][tamaño];
        
        Utilidades.getLeer("mundo2", mundo, origen, destino);
    }
    
    //sobrecarga de comparator para PQ
    public class TNodoComparator implements Comparator<TNodo>
        {
            @Override
            public int compare(TNodo x, TNodo y){
                return x.comparador(y);
            }
        }
    
    //funcion de filtrado de nodos visitados para no volver a expadir hacia atras
    public void filtrarVisitados(ArrayList<TNodo> lista){
        
        for(int i=0; i<lista.size();i++){
            if(visitados.contains(lista.get(i))){
                lista.remove(i);
            }
        }      
    }
    
    public void iniciarMatrices(){
        //Inicializa las variables camino y expandidos donde el A* debe incluir el resultado
            for(int i=0;i<tamaño;i++){
                for(int j=0;j<tamaño;j++){
                    camino[i][j] = '.';
                    expandidos[i][j] = -1;
                }
            }
    }
    
    public int Aestrella(){

            //nodos inicial y final
        
            inicio = new TNodo(origen, 1, 0, null);
            fin = new TNodo(destino, mundo.length -2,0, null);
            
            //camino de nodos visitados del camino más corto
            ArrayList<TNodo> listaInterior = new ArrayList<TNodo>();
             //camino de nodos visitados del camino más corto auxiliar
            ArrayList<TNodo> intermedios = new ArrayList<TNodo>();
            //comparador para el heap
            Comparator<TNodo> comparator = new TNodoComparator();
            //cola de prioridad con el comparador de nodos expandidos
            PriorityQueue<TNodo> listaFrontera = new PriorityQueue<TNodo>(10, comparator);
            
            //comienza algoritmo
            listaFrontera.add(inicio);

            
            TNodo nodo;
            boolean end = false;

            while(!listaFrontera.isEmpty() && !end){
                
                nodo = listaFrontera.poll();    //devuelvo la cima sin eliminar
                listaInterior.add(nodo);
    
                if(nodo.equals(fin)){   //si ya he llegado paro, sino expando nodos
                     //construyo bien el nodo llegada
                    end = true;
                }
                else{
                    //expando y añado nuevos nodos a la lista forntera filtrando visitados
                    intermedios.addAll(nodo.expandir(mundo));   //expando nodos
                    filtrarVisitados(intermedios, listaInterior);  //una vez expandidos filtro los ya visitados
                    filtrarFrontera(listaFrontera, intermedios);
                    listaFrontera.addAll(intermedios);
                    intermedios.clear();
                }
            }
            
            //construyo el nodo llegada bien
            fin = listaInterior.get(listaInterior.size()-1);
            
            //escribo camino y orden de expansion de nodos
            escribirCamino();
            escribirOrden(listaInterior);
            imprimirCamino();
            imprimirExpandidos();

            return 0;
                
    }
    
    //funcion de filtrado de nodos visitados para no volver a expadir hacia atras
        public void filtrarVisitados(ArrayList<TNodo> lista, ArrayList<TNodo> visitados){
            
            lista.removeAll(visitados);
        }    
        
        public void filtrarFrontera(PriorityQueue<TNodo> pq, ArrayList<TNodo> lista){
            
            lista.removeAll(pq);
        }
        
        //escribir orden de exploracion de nodos
        public void escribirOrden(ArrayList<TNodo> lista){
            
            int longitud = lista.size()-1;
            //ultimo nodo
            TNodo aux;
            System.out.println(longitud);

            for(int i=longitud; i>=0; i--){
                aux = lista.get(i);
                expandidos[aux.getPosicion()[0]][aux.getPosicion()[1]] = i+1;
                
            }
        }
                
        //llenado de matriz camino
        public void escribirCamino(){
            int[] pos = new int[2];
            TNodo aux = fin;
            
            while(!aux.equals(inicio)){
                pos = aux.getPosicion();
                aux = aux.getPapa();
                camino[pos[0]][pos[1]] = 'X';
            }
            pos = aux.getPosicion();
            camino[pos[0]][pos[1]] = 'X';
        }
        
         //imprime matriz tipo char por pantalla
        public void imprimirCamino(){
            
            System.out.println("Camino:");
            for(int i=0; i<camino.length; i++){
                for(int j=0; j<camino.length; j++){
                    System.out.print(camino[i][j] + " ");
                }
                System.out.println();
            }
        }

        //imprime una matriz de enteros
        public void imprimirExpandidos(){

            System.out.println("Nodos explorados:");
            for(int i=0; i<expandidos.length; i++){
                for(int j=0; j<expandidos.length; j++){
                     if(expandidos[i][j] >= 0){
                        System.out.print(" " + expandidos[i][j] + " ");
                    }else{
                        System.out.print(expandidos[i][j] + " ");
                    }
                }
                System.out.println();
            }
        }
        
    
}
