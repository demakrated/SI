/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package practica1si2012;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
*
* @author danielpedrajasvandevelde
*/
public class PruebasTest {
    
    private int [][] mundo;

    private int origen; //Punto de partida del robot. Será la columna 1 y esta fila
    private int destino; //Punto de destino del robot. Será la columna tamaño-2 y esta fila
    private int tamaño; //Tamaño del mundo
    private int longitudCamino;
    private String nombre;
    private ArrayList<Heuristica> funciones;
    private TNodo inicio;
    private TNodo fin;

    public PruebasTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        nombre = "mundo.txt";
        mundo = Utilidades.getMundo(nombre);
        tamaño = mundo.length;
        //saco origen y destino de las esquinas del mundo y las pongo de nuevo a 1
        origen = mundo[0][0];
        destino = mundo[tamaño-1][tamaño-1];
        mundo[0][0] = 1;
        mundo[tamaño-1][tamaño-1] = 1;
        
        fin = new TNodo(destino, mundo.length-2,0,null);
        if(nombre.equals("mundo.txt")){
            longitudCamino = 27;
        }
        else if(nombre.equals("mundo2.txt")){
            longitudCamino = 4;
        }
        else if(nombre.equals("mundo3.txt")){
            longitudCamino = 18;
        }
           
        //relleno el array con instancias de las diferenes heuristicas
        funciones = new ArrayList<Heuristica>();
        funciones.add(new Exhaustiva(fin));
        funciones.add(new DistanciaEuclidea(fin));
        funciones.add(new Manhattan(fin));
        funciones.add(new Diagonal(fin));
  
    }
    
    @After
    public void tearDown() {
    }

    /**
* Test of Aestrella method, of class Pruebas.
*/
    @Test
    public void testAestrella() {
        
        //cambiar si cambia el mundo.txt!!!!!!
        String parser = "practica1si2012.";
        System.out.println("Aestrella");
        
        
        for(int i=0; i<funciones.size(); i++){
            System.out.println("Heuristica: " + (funciones.get(i).toString().replaceAll(parser, "")));
            inicio = new TNodo(origen,1,0,null, funciones.get(i));
            
            Pruebas instance = new Pruebas(inicio, nombre);
            int expResult = 0;
            int result = instance.AEstrella();
            assertEquals(expResult, result);
            assertEquals(longitudCamino, instance.getLongitud());
            System.out.println("OK!!");
            System.out.println();
        }
        
        System.out.println("Todas las longitudes coinciden...OK!!!");
       
    }
    
}