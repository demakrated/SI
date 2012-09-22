/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1si2012;

import java.util.ArrayList;
import java.util.PriorityQueue;
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
    private char camino[][]; //Camino que debe seguir el robot. Será el resultado del A*
    private int expandidos[][]; //Orden de los nodos expandidos. Será el resultado del A*
    private int tamaño; //Tamaño del mundo
    private int totalVisitados;
    private int longitudCamino;
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
        
        fin = new TNodo(3, 3, 0, null);
        
        //relleno el array con instancias de las diferenes heuristicas
        funciones = new ArrayList<Heuristica>();
        funciones.add(new Exhaustiva(fin));
        funciones.add(new Manhattan(fin));
        funciones.add(new Pitagoras(fin));
        funciones.add((new Voraz(fin)));
        funciones.add(new Diagonal(fin));
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of filtrarVisitados method, of class Pruebas.
     *
    @Test
    public void testFiltrarVisitados_ArrayList() {
        System.out.println("filtrarVisitados");
        ArrayList<TNodo> lista = null;
        Pruebas instance = new Pruebas();
        instance.filtrarVisitados(lista);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of Aestrella method, of class Pruebas.
     */
    @Test
    public void testAestrella() {
        
        //cambiar si cambia el mundo.txt!!!!!!
        int longitud = 4;
        String parser = "practica1si2012.";
        System.out.println("Aestrella");
        
        
        for(int i=0; i<funciones.size(); i++){
            System.out.println("Heuristica: " + (funciones.get(i).toString().replaceAll(parser, "")));
            inicio = new TNodo(1,1,0,null, funciones.get(i));
            Pruebas instance = new Pruebas(inicio);
            int expResult = 0;
            int result = instance.AEstrella();
            assertEquals(expResult, result);
            assertEquals(longitud, instance.getLongitud());
            System.out.println("OK!!");
            System.out.println();
        }
        
        System.out.println("Todas las longitudes coinciden...OK!!!");
       
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of inicializarMundo method, of class Pruebas.
     *
    @Test
    public void testInicializarMundo() {
        System.out.println("inicializarMundo");
        Pruebas instance = new Pruebas();
        instance.inicializarMundo();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of busquedaExaustiva method, of class Pruebas.
     *
    @Test
    public void testBusquedaExaustiva() {
        System.out.println("busquedaExaustiva");
        Pruebas instance = new Pruebas();
        int[][] expResult = null;
        int[][] result = instance.busquedaExaustiva();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of filtrarVisitados method, of class Pruebas.
     *
    @Test
    public void testFiltrarVisitados_ArrayList_ArrayList() {
        System.out.println("filtrarVisitados");
        ArrayList<TNodo> lista = null;
        ArrayList<TNodo> visitados = null;
        Pruebas instance = new Pruebas();
        instance.filtrarVisitados(lista, visitados);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of escribirOrden method, of class Pruebas.
     *
    @Test
    public void testEscribirOrden() {
        System.out.println("escribirOrden");
        ArrayList<TNodo> lista = null;
        PriorityQueue<TNodo> PQ = null;
        Pruebas instance = new Pruebas();
        instance.escribirOrden(lista, PQ);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of escribirCamino method, of class Pruebas.
     *
    @Test
    public void testEscribirCamino() {
        System.out.println("escribirCamino");
        Pruebas instance = new Pruebas();
        instance.escribirCamino();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of imprimirCamino method, of class Pruebas.
     *
    @Test
    public void testImprimirCamino() {
        System.out.println("imprimirCamino");
        Pruebas instance = new Pruebas();
        instance.imprimirCamino();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of imprimirExpandidos method, of class Pruebas.
     *
    @Test
    public void testImprimirExpandidos() {
        System.out.println("imprimirExpandidos");
        Pruebas instance = new Pruebas();
        instance.imprimirExpandidos();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
*/
}
