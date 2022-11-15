
package lab_closest_pair_valeria_jimenez_;

import java.util.Arrays;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* Algorithms and Complexity                           
 * IST 4310
 * Valeria Andrea Jiménez Silvera ID 200135722
 * Closest-Pair Lab
 * 28/07/2022
 */

class Clock {
    public static void create(String name) // creates a file.
    { try {
            // defines name of the file.
            String fname = (name);

            File f = new File(fname);

            String msg = "creating file `" + fname + "' ... ";
            // creates the new file
            f.createNewFile();

        } catch (IOException err) {

            err.printStackTrace();
        }

        return;
    }

    private static void write(String Nombre_archivo, int Tamano, ArrayList<Integer> abc, ArrayList<Integer> Compare, ArrayList<Integer> R) // Se escriben los datos en un archivo
    {
        try {

            String filename = (Nombre_archivo);
            PrintWriter out = new PrintWriter(filename);
            String fmt = ("%10s %10s %10s\n");
            for (int i = 0; i < Tamano; ++i) {
                out.printf(fmt, abc.get(i), Compare.get(i), R.get(i));
            }
            out.close();
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }

        return;
    }
     public void Analitic(int Reach) { //Reach es el numero de enteros a llegar.
         
        ArrayList<Integer> R = new ArrayList<Integer>();
        ArrayList<Integer> abc = new ArrayList<Integer>();
        ArrayList<Integer> Compare = new ArrayList<Integer>(); //Se inicializa la lista.
        for (int i = 1000; i < Reach; i = i * 2 ) {
            Lab_Closest_Pair_Valeria_Jimenez_ Punto = new Lab_Closest_Pair_Valeria_Jimenez_();
            System.out.println(i);
            long total = 0;
            int C;
            ArrayList<lab_closest_pair_valeria_jimenez_.xyz> coords = Punto.Starting(i); //Se almacenan las coordenadas X y Y.
            coords.sort(Comparator.comparing(Point -> Point.X));
            for (int j = 0; j < 256; j++) {
                long startTime = System.nanoTime();
                Punto.ClosestPair(i, coords, 999999999);
                long endTime = System.nanoTime();
                long totalTime = endTime - startTime; //Se calcula el tiempo por repeticiones.
                total = total + totalTime;
            }
            C = Punto.getComparisons();
            C = C / 256; //Se halla el promedio de iteraciones.
            total = total / 256; //Se halla el tiempo de ejecucion promedio
            R.add((int) total);
            abc.add(i); //Se almacenan en un arreglo el numero total de coordenadas
            Compare.add(C);
           } 
        create("Tiempo.txt");
        write("Tiempo.txt", abc.size(), abc, Compare, R);
}}
  
  class xyz { //Coordenadas

    int X,Y,Posicion;

    public xyz(int X1, int Y1, int Posicion) {
        this.X = X1;
        this.Y = Y1;
        this.Posicion = Posicion;
    }
}



public class Lab_Closest_Pair_Valeria_Jimenez_ {
 
public static int Count; //Se mantiene un seguimiento de cada repeticion.

public int getComparisons() {
        return Count;
    }
  
  public static double[] BruteForce(int N, List<xyz> Coords, double d_min) { // Se halla el par mas cercano con Brute Force Algorithm.
        double dmin = d_min;
        double[] V = new double[3]; //Se crea una matriz para almacenar el d_min y el punto con el par mas cercano.
        V[0] = dmin;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double d = Distance(Coords, i, j); //Se compara la distancia con cada par.
                if (d < dmin) {
                    Count++;
                    dmin = d;
                    V[0] = d;
                    V[1] = Coords.get(i).Posicion;
                    V[2] = Coords.get(j).Posicion;
                } else {
                    Count++;
                }
            }
        }
        return V;
    }

    public static void PCoords(List<int[]> Coord) { // Se imprimen coordenadas.
        for (int i = 0; i < Coord.size(); i++) {
            System.out.println("X: " + Coord.get(i)[0] + " Y: " + Coord.get(i)[1] + " Posicion: " + Coord.get(i)[2]);
        }
    }
  public Lab_Closest_Pair_Valeria_Jimenez_() {
        Count = 0;
    }
  
  public static void main(String[] args) {
       
        Clock TT = new Clock();
        TT.Analitic(2000000);
        }
        
  
    public static ArrayList<xyz> Starting(int Tamano) { //Se crean coordenadas aleatorias.
        ArrayList L = new ArrayList<xyz>();
        Random Random = new Random();
        for (int i = 0; i < Tamano; i++) {
            xyz T = new xyz(Random.nextInt(1000), Random.nextInt(1000), i);
            L.add(T);
        }

        return L;
    }
    
     public static double[] ClosestPair(int N, List<xyz> x, double Mdistancia) {
        // Se encuentra el par mas cercano usando recursividad
        if (N > 3) { //Si hay mas de 3 particulas, se divide el plano entre 2.
            double[] G1 = new double[3];
            double[] Gg2 = new double[3];
            int Ledge = 0;
            Count++;
            if (N % 2 == 1) {
                Ledge = 1;  
            }
          
            G1 = ClosestPair(N / 2, x.subList(0, N / 2), Mdistancia);
            Gg2 = ClosestPair(N / 2 + Ledge, x.subList(N / 2, N), Mdistancia);
            double[] G = new double[3];
            if (G1[0] < Gg2[0]) { 
                G = G1;
                Count++;
            } else {
                Count++;
                G = Gg2;
            }
            ArrayList<xyz> Candi = new ArrayList<xyz>(); //Lista que almacena las posibles coordenadas.
            ArrayList<xyz> Candidato = FinalCandidato(x, G[0]);
            //Se aplica el algoritmo de fuerza bruta.
            if (Candidato.size() > 1) {
                Count++;
                G1 = BruteForce(Candi.size(), Candi, Mdistancia);
                if (G1[0] < G[0]) {
                    Count++;
                    return G1; 
                } else {
                    Count++;
                    return G;
                }
            } else {
                Count++;
                return G;
            }
        } else {
            Count++;
            double[] vector = new double[3];
            return BruteForce(N, x, Mdistancia); //Apply BruteForce algorithm to 3 or less coords
        }
    }

     public static ArrayList<xyz> FinalCandidato(List<xyz> Coors, double min) { 
        ArrayList<xyz> FinalCan = new ArrayList<xyz>();
        int i = 0;
        while (i < Coors.size() / 2) { //Se comparan las posiciones entre X y Y.
            if (Math.abs(Coors.get(i).X - Coors.get(Coors.size() / 2).X) < min && Math.abs(Coors.get(i).Y - Coors.get(Coors.size() / 2).Y) < min) {
                Count++;
                FinalCan.add(Coors.get(i)); //Se crea un candidato cuando la distancia es menor que d_min.
                i++;
            } else {
                Count++;
                i = Coors.size() / 2;
            }
        }
        while (i < Coors.size()) { //Se comparan para no obtener repetidos.
            if (Math.abs(Coors.get(i).X - Coors.get(Coors.size() / 2 - 1).X) < min && Math.abs(Coors.get(i).Y - Coors.get(Coors.size() / 2 - 1).Y) < min) {
                Count++;
                FinalCan.add(Coors.get(i));
                i++;
            } else {
                Count++;
                i = Coors.size();
            }
        }
        return FinalCan;
    }
 public static double Distance(List<xyz> Coordes, int i, int j) {
        // Se calcula la distancia entre i y j
        int x1 = Coordes.get(i).X;
        int x2 = Coordes.get(j).X;
        int y1 = Coordes.get(i).Y;
        int y2 = Coordes.get(j).Y;
        double d = ((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)); 
        return Math.sqrt(d);
    }

}


     

