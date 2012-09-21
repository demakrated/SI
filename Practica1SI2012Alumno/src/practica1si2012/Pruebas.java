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
        origen = 0;
        destino =0;
        camino = new char[1][1];
        expandidos = new int[1][1];
        tamaño = 0;
        ordenExpansion = 0;
        inicio = new TNodo(1,1,1,1,null);
        fin = new TNodo(2,2,2,2,null);
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
        
        tamaño = 5;
            mundo = new int[tamaño][tamaño];
            camino = new char[tamaño][tamaño];
            expandidos = new int[tamaño][tamaño];
            origen = 1;
            destino = 3;
            mundo = new int[5][5];
            
            iniciarMatrices();
    
            inicializarMundo();
            
            //nodos inicial y final
        
            inicio = new TNodo(origen, 1, 0, 0, null);
            fin = new TNodo(destino, mundo.length -2,0,0, null);
            
            //camino de nodos visitados del camino más corto
            ArrayList<TNodo> listaInterior = new ArrayList<TNodo>();
            
             //camino de nodos visitados del camino más corto auxiliar
            ArrayList<TNodo> intermedios = new ArrayList<TNodo>();
            
            //comparador para el heap
            Comparator<TNodo> comparator = new TNodoComparator();
            
            //cola de prioridad con el comparador de nodos expandidos
            PriorityQueue<TNodo> listaFrontera = new PriorityQueue<TNodo>(10, comparator);
            
            listaFrontera.add(inicio);
            
            TNodo nodo;
            boolean end = false;
            while(!listaFrontera.isEmpty() && !end){
                
                nodo = listaFrontera.poll();    //devuelvo la cima sin eliminar
                listaInterior.add(nodo);
    
                if(nodo.equals(fin)){   //si ya he llegado paro, sino expando nodos
                     //construyo bien el nodo llegada
                    fin = listaInterior.get(listaInterior.size()-1);
                    escribirCamino();
                    end = true;
                }
                else{
                    //expando y añado nuevos nodos a la lista forntera filtrando visitados
                    intermedios.addAll(nodo.expandir(mundo));   //expando nodos
                    filtrarVisitados(intermedios, listaInterior);  //una vez expandidos filtro los ya visitados
                    listaFrontera.addAll(intermedios);
                    intermedios.clear();
                }
            }
            
            //construyo el nodo llegada bien
            fin = listaInterior.get(listaInterior.size()-1);
            
            //escribo camino y orden de expansion de nodos
            escribirCamino();
            escribirOrden(listaInterior, listaFrontera);
            imprimirCamino();
            imprimirExpandidos();
            
            return 0;
                
    }
    
    public void inicializarMundo(){
        
        //my mundo 
         /*
          * * * * *
          * . . . * 
          * . - - *
          * . . . *
          * * * * *   */
         
         //donde
         // * == muro
         // - == obstaculo
         // . == libre
         
         
         //lo inicializo
         for(int i=0; i<5; i++){
             for(int j=0;j<5;j++){
                 if(i == 0 || i == 4){    //primera linea
                     mundo[i][j] = 2;
                 }
                 if(i == 1 || i == 3){    //segunda
                     mundo[i][j] = 0;
                 }
                 if(i == 2){    //tercera
                     mundo[i][j] = 1;
                 }
                 if(j == 0 || j == 4){  //contorno
                    mundo[i][j] = 2;
                 }
             }
         }
         mundo[2][1] = 0;
        
            
            char matriz [][] = new char [mundo.length][mundo.length];
            for(int i=0; i<matriz.length; i++){
                for(int j=0; j<matriz.length; j++){
                    matriz[i][j] = '.';
                }
            }
        
    }
    //encuentra el camino mediante busqueda exhaustiva, por niveles con filtrado y sin filtrado de visitados
    public int [][] busquedaExaustiva(){
        mundo = new int[5][5];
         

            inicio = new TNodo(origen, 1, 0, 0, null);
            fin = new TNodo(destino, mundo.length -2,0,0, null);
            
            //camino de nodos visitados del camino más corto auxiliar
            ArrayList<TNodo> intermedios = new ArrayList<TNodo>();
            
            //comparador para el heap
            Comparator<TNodo> comparator = new TNodoComparator();
            
            //cola de prioridad con el comparador de nodos expandidos
            PriorityQueue<TNodo> MiExpandidos = new PriorityQueue<TNodo>(10, comparator);
            
            //nodos destino y origen

            
            boolean end = false;
            
            //expando primer nodo inicio
            MiExpandidos.addAll(inicio.expandir(mundo));
            visitados.add(inicio);
            
            //bucle de expansion de nodos
            while(!end && !MiExpandidos.isEmpty()){
                for(int i=0; i<visitados.size(); i++){  //si he llegado paro
                    if(visitados.get(i).equals(end)){
                        end = true;
                    }
                }
                if(!end){
                    intermedios.addAll(MiExpandidos);
                    filtrarVisitados(intermedios);  //una vez expandidos filtro los ya visitados
                    visitados.addAll(intermedios);
                    //MiExpandidos.clear();
                    for(int i=0; i<intermedios.size();i++){
                        MiExpandidos.addAll(intermedios.get(i).expandir(mundo));
                    }
                    intermedios.clear();
                }
            }
            
            //construyo bien el nodo llegada
            for(int i=0; i<visitados.size(); i++){  
                if(visitados.get(i).equals(fin)){
                    fin = visitados.get(i);
                }
            }
            
            escribirCamino();
            imprimirCamino();
            
            return new int[1][1];
        }
    
           
        //funcion de filtrado de nodos visitados para no volver a expadir hacia atras
        public void filtrarVisitados(ArrayList<TNodo> lista, ArrayList<TNodo> visitados){

            for(int i=0; i<lista.size();i++){
                if(visitados.contains(lista.get(i))){
                    lista.remove(i);
                }
            }      
        }    
        
        //escribir orden de expansion de nodos
        public void escribirOrden(ArrayList<TNodo> lista, PriorityQueue<TNodo> PQ){
            
            PriorityQueue <TNodo> aux = PQ;
            TNodo n;
            while(!aux.isEmpty()){
                n = aux.poll();
                expandidos[n.getPosicion()[0]][n.getPosicion()[1]] = n.getOrden();
            }
            for(int i=0; i<lista.size(); i++){
                n = lista.get(i);
                expandidos[n.getPosicion()[0]][n.getPosicion()[1]] = n.getOrden();
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
