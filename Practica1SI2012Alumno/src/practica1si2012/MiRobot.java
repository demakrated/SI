/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package practica1si2012;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import simbad.sim.*;
import javax.vecmath.Vector3d;
import javax.vecmath.Matrix3d;
import java.util.*;


/**
 *
 * @author mireia
 */
public class MiRobot extends Agent{

        //Variables utilizadas para el controlador difuso
        RangeSensorBelt sonars;
        FuzzyController controller;
        
        //Variables generales
        int mundo[][]; //Datos del entorno
        int origen; //Punto de partida del robot. Será la columna 1 y esta fila
        int destino; //Punto de destino del robot. Será la columna tamaño-1 y esta fila
        char camino[][]; //Camino que debe seguir el robot. Será el resultado del A*
        int expandidos[][]; //Orden de los nodos expandidos. Será el resultado del A*
        int tamaño; //Tamaño del mundo
        int totalVisitados;
        int longitudCamino;
        TNodo inicio;   //nodos creados a partir de origen y destino
        TNodo fin;


        public MiRobot(Vector3d position, String name, Practica1 practica1) {
            super(position, name);

            //Prepara las variables
            tamaño = practica1.tamaño_mundo;

            mundo = new int[tamaño][tamaño];
            camino = new char[tamaño][tamaño];
            expandidos = new int[tamaño][tamaño];
            origen = practica1.origen;
            destino = practica1.destino;
            mundo = practica1.mundo;
            totalVisitados = 0;
            
            //nodos inicial y final
            fin = new TNodo(destino, mundo.length -2,0, null);
            inicio = new TNodo(origen, 1, 0, null, new Diagonal(fin));
            
            //lista visitados
            
            //Inicializa las variables camino y expandidos donde el A* debe incluir el resultado
            for(int i=0;i<tamaño;i++){
                for(int j=0;j<tamaño;j++){
                    camino[i][j] = '.';
                    expandidos[i][j] = -1;
                }
            }

            // Añade sonars
            sonars = RobotFactory.addSonarBeltSensor(this); // de 0 a 1.5m
        }

        //Calcula el A*
        public int AEstrella(){
            
            //camino de nodos visitados del camino más corto
            ArrayList<TNodo> listaInterior = new ArrayList<TNodo>();
            //camino de nodos visitados del camino más corto auxiliar para filtrar nodos antes de visitarlos
            ArrayList<TNodo> intermedios = new ArrayList<TNodo>();
            //comparador para el heap
            Comparator<TNodo> comparator = new TNodoComparator();
            //cola de prioridad con el comparador de nodos expandidos
            PriorityQueue<TNodo> listaFrontera = new PriorityQueue<TNodo>(10, comparator);
            
            //comienza algoritmo
            listaFrontera.add(inicio);

            
            TNodo nodo;
            boolean end = false;
            int resultado = 1;

            //bucle del algoritmo
            while(!listaFrontera.isEmpty() && !end){
                
                nodo = listaFrontera.poll();    //devuelvo la cima sin eliminar
                listaInterior.add(nodo);
    
                if(nodo.equals(fin)){   //si ya he llegado paro, sino expando nodos
                     //construyo bien el nodo llegada
                    end = true;
                    resultado = 0;
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

            return resultado;
        }
        
        
        //funcion de filtrado de nodos visitados para no volver a expadir hacia atras
        public void filtrarVisitados(ArrayList<TNodo> lista, ArrayList<TNodo> visitados){
            
            lista.removeAll(visitados);
        }    
        
        //funcion que filtra los nodos que ya estén en la frontera
        public void filtrarFrontera(PriorityQueue<TNodo> pq, ArrayList<TNodo> lista){
            
            lista.removeAll(pq);
        }
        
        //escribir orden de exploracion de nodos en su matriz expandidos
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
        
         //imprime matriz tipo char por pantalla (camino)
        public void imprimirCamino(){
            
            System.out.println("Camino: (Cuya longitud es: " + longitudCamino + ")");
            for(int i=0; i<camino.length; i++){
                for(int j=0; j<camino.length; j++){
                    System.out.print(camino[i][j] + " ");
                }
                System.out.println();
            }
        }

        //imprime una matriz de enteros (visitados)
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
        
        //sobrecarga del comparador para poder ordenar los nodos
        public class TNodoComparator implements Comparator<TNodo>
        {
            @Override
            public int compare(TNodo x, TNodo y){
                return x.comparador(y);
            }
        }

        //Función utilizada para la parte de lógica difusa donde se le indica el siguiente punto al que debe ir el robot.
        //Busca cual es el punto más cercano.
        public Point3d puntoMasCercano(Point3d posicion){
            int inicio;
            Point3d punto = new Point3d(posicion);
            double distancia;
            double cerca = 100;

            inicio = (int) (tamaño-(posicion.z+(tamaño/2)));
            
            for(int i=0; i<tamaño; i++){
                for(int j=inicio+1; j<tamaño; j++){
                    if(camino[i][j]=='X'){
                        distancia = Math.abs(posicion.x+(tamaño/2)-i) + Math.abs(tamaño-(posicion.z+(tamaño/2))-j);
                        if(distancia < cerca){
                            punto.x=i;
                            punto.z=j;
                            cerca = distancia;
                        }
                    }
                }
            }

            return punto;
        }

        /** This method is called by the simulator engine on reset. */
    @Override
        public void initBehavior() {

            System.out.println("Entra en initBehavior");
            //Calcula A*
            int a = AEstrella();

            if(a!=0){
                System.err.println("Error en el A*");
            }else{
                // init controller
                controller = new FuzzyController();
            }
        }

        /** This method is call cyclically (20 times per second)  by the simulator engine. */
    @Override
        public void performBehavior() {

            double angulo;
            int giro;

            //Ponemos las lecturas de los sonares al controlador difuso
            //System.out.println("Fuzzy Controller Input:");
            float[] sonar = new float[9];
            for(int i=0; i<9; i++){
                if(sonars.getMeasurement(i)==Float.POSITIVE_INFINITY){
                    sonar[i] = sonars.getMaxRange();
                } else {
                    sonar[i] = (float) sonars.getMeasurement(i);
                }

                //System.out.println("    > S"+ i +": " + sonar[i]);
            }

     
            //Calcula ángulo del robot
            Transform3D rotTrans = new Transform3D();
            this.rotationGroup.getTransform(rotTrans); //Obtiene la transformada de rotación

            //Debe calcular el ángulo a partir de la matriz de transformación
            //Nos quedamos con la matriz 3x3 superior
            Matrix3d m1 = new Matrix3d();
            rotTrans.get(m1);

            //Calcula el ángulo sobre el eje y
            angulo = -java.lang.Math.asin(m1.getElement(2,0));

            if(angulo<0.0)
                angulo += 2*Math.PI;
            assert(angulo>=0.0 && angulo<=2*Math.PI);

            //Calcula la dirección
            if(m1.getElement(0, 0)<0)
                angulo = -angulo;
            angulo = angulo*180/Math.PI;            
            if(angulo<0 && angulo>-90)
                angulo += 180;
            if(angulo<-270 && angulo>-360)
                angulo += 180+360;


            //Calcula el siguiente punto al que debe ir del A*
            Point3d coord = new Point3d();
            this.getCoords(coord);

            Point3d punto = puntoMasCercano(coord);
            coord.x = coord.x+(tamaño/2);
            coord.z = tamaño-(coord.z+tamaño/2);
            coord.x = (int)coord.x;
            coord.z = (int)coord.z;
            
            
            //Calcula distancia y ángulo del vector, creado desde el punto que se encuentra el robot,
            //hasta el punt que se desea ir
            double distan = Math.sqrt(Math.pow(coord.z-punto.z, 2)+Math.pow(coord.x-punto.x, 2));    
            double phi= Math.atan2((punto.z-coord.z),(punto.x - coord.x));
            phi = phi*180/Math.PI;
            
            //Calcula el giro que debe realizar el robot. Este valor es el que se le pasa al controlador difuso.
            double rot = phi-angulo;
            if(rot<-180)
                rot += 360;
            if(rot>180)
                rot -=360;
            
            //System.out.println("Angulo de giro: "+rot);
            
            //Ejecuto el controlador
            controller.step(sonar, rot);

            //Obtengo las velocidades calculadas y las aplico al robot
            setTranslationalVelocity(controller.getVel());
            setRotationalVelocity(controller.getRot());

            //Para mostrar los valores del controlador
            //System.out.println("Fuzzy Controller Output:");
            //System.out.println("    >vel: "+ controller.getVel());
            //System.out.println("    >rot: "+ controller.getRot());
        }

}
