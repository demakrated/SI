/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package practica1si2012;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
*
* @author danielpedrajasvandevelde
*/
public class Utilidades {
    
    private Utilidades instancia = new Utilidades();
    private Utilidades(){};
    private static int tamaño_mundo;
    private static int [][] mundo;
    private static int origen;
    private static int destino;
    
    public static int [][]getMundo(String archivo){
        leerMundo(archivo);
        return mundo;
    }
    
    
      //Función para leer el fichero
     //Lee un tablero de juego desde un fichero
    private static boolean leerMundo(String archivo){
        FileReader fr = null;
        String sCadena;
        String delimitador = " ";
        int i;

        try
            {
                //Abre el fichero
                fr = new FileReader(archivo);
                BufferedReader bf = new BufferedReader(fr);

                try
                    {

                        //Lee la primera línea del archivo que indica el tamaño del mundo
                        if((sCadena = bf.readLine()) != null){
                            sCadena = sCadena.trim();
                            tamaño_mundo = Integer.parseInt(sCadena);

                            //Crea el mundo del tamaño especificado
                            mundo = new int[tamaño_mundo][tamaño_mundo];
                        }else{
                            System.err.println("ERROR. Formato del fichero incorrecto");
                            return false;
                        }

                        //Lee la coordenada de origen
                        if((sCadena = bf.readLine()) != null){
                            sCadena = sCadena.trim();
                            origen = Integer.parseInt(sCadena);
                        }else{
                            System.err.println("ERROR. Formato del fichero incorrecto");
                            return false;
                        }

                        //Lee la coordenada de destino
                        if((sCadena = bf.readLine()) != null){
                            sCadena = sCadena.trim();
                            destino = Integer.parseInt(sCadena);
                        }else{
                            System.err.println("ERROR. Formato del fichero incorrecto");
                            return false;
                        }

                       //Lee el mundo
                        i = 0;
                        while (i<tamaño_mundo)
                        {
                            if((sCadena = bf.readLine())!=null){
                                //Separa los diferentes elementos de la cadena que ha leído
                                String[] elementos = sCadena.split(delimitador);

                                for (int j = 0; j < elementos.length; j++) {
                                    if(elementos[j].equals("."))
                                        mundo[i][j] = 0;
                                    else if(elementos[j].equals("-"))
                                            mundo[i][j] = 2;
                                         else
                                            mundo[i][j] = 1;
                                }
                                i++;
                            }else{
                                System.err.println("ERROR. Formato del fichero incorrecto");
                                return false;
                            }
                        }
                        //utilizo las esquinas del mundo inaccesibles para guardar origen y destino
                        mundo[0][0] = origen;
                        mundo[tamaño_mundo-1][tamaño_mundo-1] = destino;

                    } catch (IOException e1)
                    {
                        System.err.println("Error en la lectura del fichero:"+archivo);
                        return false;
                    }
                //Si falla la apertura del fichero
            } catch (FileNotFoundException e2)
            {
                System.err.println("Error al abrir el fichero: "+archivo);
                return false;
            }finally{
            // Cerramos el fichero en finally porque así nos aseguramos que se cierra tanto si todo ha ido bien, como
            // si ha saltado alguna excepción
            try{
                if( null != fr )
                    {
                        fr.close();
                    }
            }catch (Exception e3){
                System.err.println("Error al cerrar el fichero: "+archivo);

            }
        }
            return true;
    }
    
    public static int getOrigen(){
        return origen;
    }
    
    public static int getDestino(){
        return destino;
    }
    
}