package individual;

public class Coordinate{
    static int PRECISION = 64;
    private boolean isPositive = true;
    private boolean[] above = new boolean[PRECISION];
    private boolean[] below = new boolean[PRECISION];

    Coordinate(String val){
        String[] tab = val.split("\\.");
        for(int i=0;i<PRECISION;i++){
            above[i] = false;
            below[i] = false;
        }
        switch(PRECISION){
            case 32:
                make32(tab);
            break;
            case 64:
                make64(tab);
                break;
        }
    }
    Coordinate(Coordinate coordinate){
        for(int i=0;i<PRECISION;i++){
            this.above[i] = coordinate.above[i];
            this.below[i] = coordinate.below[i];
            this.isPositive = coordinate.isPositive;
        }
    }
    private void make32(String[] tab){
        int ab = Integer.parseInt(tab[0]);
        double bl = 0;
        if(tab.length>1){
            bl = Double.parseDouble("0."+tab[1]);
        }
        if(ab<0){
            isPositive = false;
            ab*=(-1);
        }
        for(int i=31;i>=0;i--){
            double tmp = (Math.pow(2,i));
            if(ab/tmp>=1){
                above[i] = true;
                ab -= tmp;
            }
            int j = 32-i;
            double tmp2 = (Math.pow(1/2.0,j));
            if(bl - tmp2 >= 0){
                below[j-1] = true;
                bl-=tmp2;
            }
            if(bl==0 && ab == 0){break;}
        }
    }
    private void make64(String[] tab){
        long ab = Long.parseLong(tab[0]);
        double bl = 0;
        if(tab.length>1){
            bl = Double.parseDouble("0."+tab[1]);
        }
        if(ab<0){
            isPositive = false;
            ab*=(-1);
        }
        for(int i=31;i>=0;i--){
            double tmp = (Math.pow(2,i));
            if(ab/tmp>=1){
                above[i] = true;
                ab -= tmp;
            }
            int j = 32-i;
            double tmp2 = (Math.pow(1/2.0,j));
            if(bl - tmp2 >= 0){
                below[j-1] = true;
                bl-=tmp2;
            }
            if(bl==0 && ab == 0){break;}
        }
    }


    void setAbove(boolean[] above) {
        this.above = above;
    }
    void setBelow(boolean[] below) {
        this.below = below;
    }
    boolean[] getAbove() {
        return above;
    }
    boolean[] getBelow() {
        return below;
    }
    void swapBitAbove(int bit){
        if(!(bit>PRECISION-1)){
            above[bit] = !above[bit];
        }
    }
    void swapBitBelow(int bit){
        if(!(bit>PRECISION-1)){
            {
                below[bit] = !below[bit];
            }
        }
    }

    public double getValue(){
        double returnVale = 0;
        for(int i=0 ;i<above.length;i++){
            if(above[i])
            returnVale += Math.pow(2,i);
            if(below[i])
                returnVale += Math.pow(1/2.0,(i+1));
        }
        if(!isPositive){
            returnVale *= (-1);
        }
        return returnVale;
    }
}