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

    private int destino; //Punto de destino del robot. Será la columna tamaño-2 y esta fila
    private char camino[][]; //Camino que debe seguir el robot. Será el resultado del A*
    private int expandidos[][]; //Orden de los nodos expandidos. Será el resultado del A*
    private int tamaño; //Tamaño del mundo
    private int totalVisitados;
    private int longitudCamino;
    private TNodo inicio;
    private TNodo fin; 
        
    
    //constructor
    public Pruebas(TNodo tn, String nombre){
        
        
        if(nombre.equals("mundo.txt")){
            destino = 10;
            tamaño = 20;
        }
        else if(nombre.equals("mundo2.txt")){
            destino = 3;
            tamaño = 5;
        }

        longitudCamino = 0;
        totalVisitados = 0;
        mundo = Utilidades.getMundo(nombre);
        camino = new char[tamaño][tamaño];
        expandidos = new int[tamaño][tamaño];
        
        
        //nodos inicial y final
        fin = new TNodo(destino, tamaño -2,0, null);
        
        //inicio lo construyo desde el test
        inicio = tn;
        
        //Inicializa las variables camino y expandidos donde el A* debe incluir el resultado
            for(int i=0;i<tamaño;i++){
                for(int j=0;j<tamaño;j++){
                    camino[i][j] = '.';
                    expandidos[i][j] = -1;
                }
            }
        
        
    }
    
    //constructor
    public Pruebas(){

        destino =3;
        tamaño = 5;
        longitudCamino = 0;
        totalVisitados = 0;
        mundo = new int[tamaño][tamaño];
        camino = new char[tamaño][tamaño];
        expandidos = new int[tamaño][tamaño];
        mundo = Utilidades.getMundo("mundo2.txt");
        
        //nodos inicial y final
        fin = new TNodo(destino, mundo.length -2,0, null);
        
        //inicio lo construyo desde el test
        inicio = new TNodo(1, 1, 0, null, new Diagonal(fin));
        
        //Inicializa las variables camino y expandidos donde el A* debe incluir el resultado
            for(int i=0;i<tamaño;i++){
                for(int j=0;j<tamaño;j++){
                    camino[i][j] = '.';
                    expandidos[i][j] = -1;
                }
            }
        
        
    }
    
    public int getLongitud(){
        return longitudCamino;
    }
    
    //sobrecarga de comparator para PQ
    public class TNodoComparator implements Comparator<TNodo>
        {
            @Override
            public int compare(TNodo x, TNodo y){
                return x.comparador(y);
            }
        }
    
    //Calcula el A*
        public int AEstrella(){
            
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
            totalVisitados = listaInterior.size();
            
            //escribo camino y orden de expansion de nodos
            escribirCamino();
            escribirOrden(listaInterior);
            imprimirCamino();
            imprimirExpandidos();

            return 0;
        }
        
        public int busquedaExhaustiva(){
            
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
            
            //ultimo nodo
            TNodo aux;
            
            for(int i=0; i<lista.size(); i++){
                aux = lista.get(i);
                expandidos[aux.getPosicion()[0]][aux.getPosicion()[1]] = i;            
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
                longitudCamino++;  
            }
            pos = aux.getPosicion();
            camino[pos[0]][pos[1]] = 'X';
        }
        
         //imprime matriz tipo char por pantalla
        public void imprimirCamino(){
            
            System.out.println("Camino: (Cuya longitud es: " + longitudCamino + ")");
            for(int i=0; i<camino.length; i++){
                for(int j=0; j<camino.length; j++){
                    System.out.print(camino[i][j] + " ");
                }
                System.out.println();
            }
        }

        //imprime una matriz de enteros
        public void imprimirExpandidos(){

            System.out.println("Nodos explorados: (en total " + (totalVisitados - 1) + ")");
            
            int anterior = -1;
            int siguiente = -1;
            for(int i=0; i<expandidos.length; i++){
                for(int j=0; j<expandidos.length; j++){
                    if(j > 0){
                        anterior = expandidos[i][j-1];  //nodo anterior para controlar espaciado
                    }
                    if(j < expandidos.length - 1){
                        siguiente = expandidos[i][j+1];
                    }
                    if(expandidos[i][j] >= 0 && anterior >= 0 && siguiente < 10 && siguiente >= 0){
                        System.out.print(expandidos[i][j]+"  ");
                    }
                    else if(expandidos[i][j] >= 0 && anterior < 0 && siguiente < 10 && siguiente >= 0){
                        System.out.print(expandidos[i][j] + "  ");
                    }
                    else if(expandidos[i][j] < 0 && anterior < 0 && siguiente < 10 && siguiente >= 0){
                        System.out.print(expandidos[i][j] + "  ");
                    }
                    else{
                        System.out.print(expandidos[i][j] + " ");
                    }
                }
                anterior = -1;
                System.out.println();
            }
        }
    
}
