package individual;

import java.util.Random;
import java.util.concurrent.*;


public class Individual{

    /**
     *  wklepujesz mu min i max i dostajesz Osobnika z losowymi współrzędnymi, np:
     *  Individual i = Individual.randIndividual(-5,5);
     */
    public static Individual randIndividual(double min, double max){
        double rand1 = min + (max - min) * random.nextDouble();
        double rand2 = min + (max - min) * random.nextDouble();
        Individual returnVale = new Individual(rand1,rand2, min, max);


        return returnVale;
    }

    /**
    *  zwraca potmoka tego osobnika skrzyżowanego z 'parent2', np:
     *  Individual i1 = new Individual(5,3,-5,5);
     *  Individual i2 = new Individual(-2.5,4.2,-5,5);
     *  Individual i3 = i1.indirectFullCrossover(i2);
     *  Individual i4 = i2.indirectFullCrossover(i1);
     *  Uwaga: i3 oraz i4 to rózni osobnicy, mają różne współrzędne.
     *
    */
    public Individual indirectFullCrossover(Individual parent2){
        Individual returnVale;
        returnVale = new Individual(0,0, min, max);
        Future<boolean[]> aboveX = executor.submit(new FullCrossoverWorker(this.x.getAbove(), parent2.x.getAbove()));
        Future<boolean[]> belowX = executor.submit(new FullCrossoverWorker(this.x.getBelow(), parent2.x.getBelow()));
        Future<boolean[]> aboveY = executor.submit(new FullCrossoverWorker(this.y.getAbove(), parent2.y.getAbove()));
        Future<boolean[]> belowY = executor.submit(new FullCrossoverWorker(this.y.getBelow(), parent2.y.getBelow()));
        try{
            returnVale.x.setAbove(aboveX.get());
            returnVale.x.setBelow(belowX.get());
            returnVale.y.setAbove(aboveY.get());
            returnVale.y.setBelow(belowY.get());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return returnVale;
    }

    /**
     *  Mutuje jeden z chromosomów: próbuje mutować dopóki nie uda mu się
     *  i2.mutateOne();
     *  Uwaga: po mutacji może wystąpić sytuacja, kiedy jedna ze współrzędnych będzie wykraczać poza min lub max
     *  (w naszym przypadku osiągnie więcej niż '5' lub mniej niż '-5'),
     *  należałoby takie osobniki odseparować i odrzucić z ogólen puli
     */
    public void mutateOne(){
        for(int i=0; i<Coordinate.PRECISION;i++){
            int rand = random.nextInt()%30;
            switch(rand){
                case 1:
                    x.swapBitAbove(i);
                    return;
                case 2:
                    x.swapBitBelow(i);
                    return;
                case 3:
                    y.swapBitAbove(i);
                    return;
                case 4:
                    y.swapBitBelow(i);
                    return;
                default:
            }
        }
    }

    /**
     *  próbuje mutować wszystkie chromosomy: każdy chromosom spróbuje mutować raz
     *  i2.mutateAll();
     *  Uwaga: po mutacji może wystąpić sytuacja, kiedy jedna ze współrzędnych będzie wykraczać poza min lub max
     *  (w naszym przypadku osiągnie więcej niż '5' lub mniej niż '-5'),
     *  należałoby takie osobniki odseparować i odrzucić z ogólen puli
     */
    public void mutateAll(){
        int tmp =0;
        int i;
        for(i=0; i<Coordinate.PRECISION;i++){
            if(Math.pow(2,i) >= max){
                tmp =i;break;
            }
        }
        for(i=0; i<tmp;i++){
            int rand = random.nextInt()%10;
            switch(rand){
                case 1:
                    x.swapBitAbove(i);
                    break;
                case 3:
                    y.swapBitAbove(i);
                    break;
                default:
            }
        }
        for(i=i;i<Coordinate.PRECISION;i++){
            int rand = random.nextInt()%10;
            switch(rand){
                case 2:
                    x.swapBitBelow(i);
                    break;
                case 4:
                    y.swapBitBelow(i);
                    break;
                default:
            }
        }
    }



    private static Random random = new Random(System.currentTimeMillis());
    public Coordinate x, y;
    public double value;
    double min, max;

    private static ExecutorService executor = Executors.newFixedThreadPool(java.lang.Thread.activeCount());
    public Individual(double x, double y, double min, double max){
        this.min = min;
        this.max = max;
        this.x =new Coordinate(Double.toString(x));
        this.y =new Coordinate(Double.toString(y));
    }
    public Individual(Individual i){
        this.max = i.max;
        this.min = i.min;
        this.x=new Coordinate(i.x);
        this.y=new Coordinate(i.y);
    }

    @Override
    public String toString() {
        return "x: " + this.x.getValue() + " | y: " + this.y.getValue() + " | val: " + this.value;
    }

    private class FullCrossoverWorker implements Callable<boolean[]> {
        boolean[] tab1;
        boolean[] tab2;

        public FullCrossoverWorker(boolean[] tab1, boolean[] tab2) {
            this.tab1 = tab1;
            this.tab2 = tab2;
        }

        public boolean[] call() throws Exception{
            boolean[] returnVale = new boolean[tab1.length];
            for(int i=0; i<tab1.length;i++){
                if(i%2 == 0){
                    returnVale[i] = tab1[i];
                }else {
                    returnVale[i] = tab2[i];
                }
            }
            return returnVale;
        }
    }
}