/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package practica1si2012;

import java.util.ArrayList;
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
public class TNodoTest {
    
        
    private int [][] mundo; 
    private String nombre;
    
 
    private TNodo uso;
    private TNodo n11;
    private TNodo n12;
    private TNodo n13;
    private TNodo n21;
    private TNodo n31;
    private TNodo n32;
    
    public TNodoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {

        uso = new TNodo(0,0,0,null);
        n11 = new TNodo(1,1,0,uso);
        n12 = new TNodo(1,2,1,n11);
        n13 = new TNodo(1,3,2,n12);
        n21 = new TNodo(2,1,1,n11);
        n31 = new TNodo(3,1,2,n21);
        n32 = new TNodo(3,2,3,n31);
         
        //genero un mundo (cambiar a mano el tamaño al instanciar) pero parseo con utilidades.leerMundo
        nombre = "mundo2.txt";
        mundo = Utilidades.getMundo(nombre);


    }
    
    @After
    public void tearDown() {
    }

    /**
* Test of expandir method, of class TNodo.
*/
    @Test
    public void testExpandirYEquals() {
        System.out.println("expandir");
        TNodo instance = n11;
        ArrayList<TNodo> expResult = new ArrayList<TNodo>();
        expResult.add(n12);
        expResult.add(n21);
        ArrayList<TNodo> result;
        result = instance.expandir(mundo);
        
        for(int i=0; i<expResult.size();i++){
            assertTrue(expResult.contains(result.get(i)));
            assertEquals(expResult.get(i), result.get(i));
        }
            
        System.out.print("OK!!");
        
    }
    
    @Test
    public void testToString(){
        
        System.out.println("toString");
        assertEquals(n11.toString(), "1, 1");
        System.out.println("OK!!");
    }

 

    /**
* Test of getPosicion method, of class TNodo.
*/
    @Test
    public void testGetPosicion() {
        System.out.println("getPosicion");
        TNodo instance = n11;
        String expResult = "1, 1";
        String result = instance.toString();
        assertEquals(expResult, result);
        
        System.out.print("OK!!");
    }
}