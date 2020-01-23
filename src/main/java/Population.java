import individual.Coordinate;
import individual.Individual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Population{

    List<Individual> list ;
    public Population(int count, double min, double max){
        list = new ArrayList<>();
        this.randPopulation(count, min, max);

    }


    public void sortPopulation(){
        list.sort(Comparator.comparingDouble(o -> o.value));
    }

    public void randPopulation(int count, double min, double max){
        for(int i=0; i<count; i++){
            Individual tmp = Individual.randIndividual(min,max);
            tmp.value = fun(tmp.x.getValue(), tmp.y.getValue());
            list.add(tmp);
        }
        this.sortPopulation();
    }

    public void printPopulation(){
        for(Individual i : list){
            System.out.println("f("+i.x.getValue()+i.y.getValue()+") = " + i.value);
        }
    }

    public void printPopulation(int count){
        int tmp = 0;
        for(Individual i : list){
            System.out.println("f("+i.x.getValue()+i.y.getValue()+") = " + i.value);
            tmp++;
            if(tmp == count){
                return;
            }
        }
    }

    public Individual checkIfStop(){
        for(Individual i: list){
            if(i.value<0.00001){
                return i;
            }
        }
        return null;
    }

    public void removeWeakest(int count){
        for(int i=0; i<count;i++){
            list.remove(list.size()-1);
        }
    }

    public void reproduceStrongest(int count){
        List<Individual> strongest1 = new ArrayList<>();
        List<Individual> strongest2 = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        for(int i=0; i<count;i++){
            strongest1.add(list.get(i));
        }
        for(int i=0; i<count/2;i++){
            int j = random.nextInt();
            j = Math.abs(j%(count-i));
            strongest2.add(strongest1.remove(j));
        }
        if(strongest1.size()>strongest2.size()){
            strongest1.remove(0);
        }
        for(Individual i : strongest1){
            Individual tmp = i.indirectFullCrossover(strongest2.remove(0));
            tmp.value = fun(tmp.x.getValue(), tmp.y.getValue());
            this.list.add(tmp);
        }
    }
    public void mutateStrongest(int count){
        List<Individual> strongest1 = new ArrayList<>();
        for(int i=0; i<count;i++){
            strongest1.add(list.get(i));
        }
        for(Individual i : strongest1){
            Individual tmp = new Individual(i);
            tmp.mutateOne();
            tmp.value = fun(tmp.x.getValue(), tmp.y.getValue());
            this.list.add(tmp);
        }

    }

    public void mutateWeakest(int count){
        Random random = new Random(System.currentTimeMillis());
        for(int i=1; i<count;i++){
            int tmp = random.nextInt();
            if(tmp % 20==0){
                //list.get(list.size()-i).mutateOne();
                list.get(list.size()-i).mutateAll();
            }
        }
    }

    public static double fun(double x, double y){
        double returnVale = 2*(x*x) - (1.05*x*x*x*x)+(x*x*x*x*x*x/6)+(x*y)+(y*y);
        //double returnVale = (-Math.cos(x))*Math.cos(y)*(Math.exp(-(Math.pow((x-Math.PI), 2)+Math.pow((y-Math.PI), 2))));
        return returnVale;
    }

}
/*

        for(int i=0; i<count;i++){
            Individual tmp = list.get(i).indirectFullCrossover(list.get(i+1));
            tmp.value = fun(tmp.x.getValue(), tmp.y.getValue());
            list.add(tmp);
        }

 */