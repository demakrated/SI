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

         uso  = new TNodo(0,0,0,0,null);
         n11 = new TNodo(1,1,0,0,uso);
         n12 = new TNodo(1,2,1,1,n11);
         n13 = new TNodo(1,3,2,1,n12);
         n21 = new TNodo(2,1,1,1,n11);
         n31 = new TNodo(3,1,2,1,n21);
         n32 = new TNodo(3,2,3,1,n31);
         
         mundo = new int[5][5];
         

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
        //int[][] mundo = null;
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

    /**
     * Test of comparador method, of class TNodo.
     */
    @Test
    public void testComparador() {
        System.out.println("comparador");
        TNodo mayor = n13;
        TNodo menor = n12;
        TNodo igual = n21;
        int expResult = -1;

        assertEquals(expResult, menor.comparador(mayor));
        expResult = 0;
        assertEquals(expResult, menor.comparador(igual));
        expResult = 1;
        assertEquals(expResult, mayor.comparador(menor));
        
        //con expansion
        TNodo instance2 = n11;
        ArrayList<TNodo> result = instance2.expandir(mundo);
        ArrayList<TNodo> expResult2 = new ArrayList<TNodo>();
        expResult2.add(n12);
        expResult2.add(n21);
        
        for(int i=0; i<expResult2.size();i++){
            assertTrue(expResult2.contains(result.get(i)));
        }
        
        System.out.print("OK!!");
    }

    /**
     * Test of getCoste method, of class TNodo.
     */
    @Test
    public void testGetCoste() {
        System.out.println("getCoste");
        TNodo instance = n11;
        int expResult = 0;
        int result = instance.getCoste();
        assertEquals(expResult, result);
        
        //con expansion
        TNodo instance2 = n11;
        ArrayList<TNodo> result2 = instance2.expandir(mundo);
        ArrayList<TNodo> expResult2 = new ArrayList<TNodo>();
        expResult2.add(n12);
        expResult2.add(n21);
        
        for(int i=0; i<expResult2.size();i++){
            assertTrue(expResult2.contains(result2.get(i)));
        }
        
        System.out.print("OK!!");

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
