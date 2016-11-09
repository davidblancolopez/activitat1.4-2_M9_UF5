package activitat1.pkg4.pkg2_m9_uf2;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import static java.util.concurrent.ForkJoinTask.invokeAll;

public class ForkJoin {

    public class ForkJoin extends RecursiveTask<Integer> {

        private final double[] array;
        private final int inici, finale;

        public ForkJoin(double[] arr, int ini, int fin) {
            this.array = arr;
            this.inici = ini;
            this.finale = fin;
        }

        
        
        @Override
        protected double compute() {
            if (finale - inici <= 1) {
                
                double temperaturaMitjanaBaixa = 0;
                int i = 0;
                do{
                    double temperaturaMitjana = 0;
                        
                    for (int j = 0; j < 4; j++) {
                        temperaturaMitjana += array[i]; 
                    }
                    
                    if (temperaturaMitjana < temperaturaMitjanaBaixa) {
                        temperaturaMitjanaBaixa = temperaturaMitjana;
                    }
                    
                    System.out.println("Temperatura mitjana a les: " + i + ":00 :" + temperaturaMitjana);
                }while(i < 16);
                
                return temperaturaMitjanaBaixa;
            } else {
                //Dividir el problema en parts més petites
                int mitat = inici + (finale - inici) / 2;
                ForkJoin forkJoin1 = new ForkJoin(array, inici, mitat);
                ForkJoin forkJoin2 = new ForkJoin(array, mitat + 1, finale);
                invokeAll(forkJoin1, forkJoin2);
                return Math.max(forkJoin1.join(), forkJoin2.join());//PROVISIONAL
            }
        }

        public void main(String[] args) {
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
        
        ForkJoinPool pool = new ForkJoinPool(3);
        ForkJoin tasca = new ForkJoin(temperatures, 0, temperatures.length - 1);
        double result = pool.invoke(tasca);

        
        }

    }
}


