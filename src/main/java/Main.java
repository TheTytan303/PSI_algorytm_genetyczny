import individual.Individual;

import java.util.*;

public class Main {
    public static void main(String[] arg){
        for(int i=0; i<1; i++){
            //System.out.print("-");
        }
        System.out.println();
        //p.printPopulation(40);
        for(int i=0; i<100; i++){
            fun(500, 500);
        }
    }
    static void fun(int populacja, int iterations){

        Population p = new Population(populacja, -5, 5);
        p.sortPopulation();
        int i= 0;
        int n = (int)(populacja*0.3);
        do {
            p.removeWeakest(n);
            p.mutateWeakest(populacja - (2 * n));
            if (i % 2 == 0) {
                p.reproduceStrongest(n * 2);
            } else {
                p.mutateStrongest(n * 2);
            }
            p.sortPopulation();
            //p.printPopulation(1);
            i++;
        } while (i < iterations);
        //System.out.println();
        Individual tmp = p.list.get(0);
        System.out.println(tmp);
    }

}

/*
 Map<Individual, Double> map = new HashMap<Individual, Double>();
        for(int i=0; i<100; i++){
            Individual tmp = Individual.randIndividual(-5,5);
            map.put(tmp,fun(tmp.x.getValue(), tmp.y.getValue()));
        }
        for(Map.Entry<Individual, Double> entry: map.entrySet()){
            System.out.println("f("+entry.getKey().x.getValue()+entry.getKey().y.getValue()+") = " + entry.getValue());
        }



        List<Individual> list = new ArrayList<Individual>();
        for(int i=0; i<10; i++){
            list.add(Individual.randIndividual(-5,5));
        }
        Individual i1 = new Individual(5,3,-5,5);
        Individual i2 = new Individual(-2.5,4.2,-5,5);
        for(int i=0; i<5;i++){
            Individual i3 = i1.indirectFullCrossover(i2);
            Individual i4 = i2.indirectFullCrossover(i1);
            System.out.println("i1 : " + i1);
            System.out.println("i2 : " + i2);
            System.out.println("i3 : " + i3);
            System.out.println("i4 : " + i4);
            if(i%2 == 0){
                i2.mutateAll();
                i1.mutateOne();
            }else {
                i1.mutateAll();
                i2.mutateOne();
            }
            System.out.println();
        }
        System.out.println();
 */