package activitat1.pkg4.pkg2_m9_uf2;


import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import static java.util.concurrent.ForkJoinTask.invokeAll;


public class ForkJoin extends RecursiveTask<Double> {
    //Declaració de variables.
    private final double[] array;
    private final int inici, finale;
    //Constructor del ForkJoin.
    public ForkJoin(double[] arr, int ini, int fin) {
        this.array = arr;
        this.inici = ini;
        this.finale = fin;
    }

    
    /**
     * Metode Main que conté l'array amb les temperatures agafades cada hora.
     * Es creen el numero de fils segons el numero disponible i fa el invoke
     * que dura a terme la tasca que tornara la temperatura mitjana més baixa.
     * @param args 
     */
    public static void main(String[] args) {
        double[] temperatures = {
            13.0, 13.2, 13.3, 13.4, //00:00 h.
            13.5, 13.7, 13.8, 13.9, //01:00 h.
            14.1, 14.2, 14.3, 14.4, //02:00 h.
            14.6, 14.7, 14.8, 14.9, //03:00 h.
            15.0, 15.2, 15.3, 15.4, //04:00 h.
            15.5, 15.7, 15.8, 15.9, //05:00 h.
            16.1, 16.2, 16.3, 16.4, //06:00 h.
            16.6, 16.7, 16.8, 16.9, //07:00 h.
            17.0, 17.2, 17.3, 17.4, //08:00 h.
            17.5, 17.7, 17.8, 17.9, //09:00 h.
            18.1, 18.2, 18.3, 18.4, //10:00 h.
            18.6, 18.7, 18.8, 18.9, //11:00 h.
            18.0, 18.2, 18.3, 18.4, //12:00 h.
            18.5, 18.7, 18.8, 18.9, //13:00 h.
            17.1, 17.2, 17.3, 17.4, //14:00 h.
            17.6, 17.7, 17.8, 17.9, //15:00 h.
        };

        int NumberOfProcessors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(NumberOfProcessors);
        ForkJoin tasca = new ForkJoin(temperatures, 0, temperatures.length - 1);
        double result = pool.invoke(tasca);

        System.out.println("Resultat és: " + result);

    }
    
    /**
     * Metode compute() realitza la divisió per a poder calcular de forma més senzilla les mitjanes de temperatura.
     * Retorna la temperatura mitjana més baixa.
     * @return 
     */
    @Override
    protected Double compute() {

        if (finale - inici > 4) {
            //Dividir el problema en parts més petites. 
           
            int mitat = inici + (finale - inici) / 2;
            ForkJoin forkJoin1 = new ForkJoin(array, inici, mitat);
            ForkJoin forkJoin2 = new ForkJoin(array, mitat + 1, finale);
            invokeAll(forkJoin1, forkJoin2);
            return Math.min(forkJoin1.join(), forkJoin2.join());

        } else {
            double temperaturaMitjanaBaixa = 0;
            //sumem amb el bucle les 4 temperatures.
            for (int i = inici; i <= finale; i++) {
                temperaturaMitjanaBaixa += array[i];
            }
            //Dividim les temperatures per tenir la mitjana.
            temperaturaMitjanaBaixa /= 4;
            return temperaturaMitjanaBaixa;
        }
    }

}
