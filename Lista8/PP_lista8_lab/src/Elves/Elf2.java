package Elves;

import Baubles.Bauble;
import Baubles.Bauble2;
import Baubles.Bauble3;
import Baubles.BigBall;
import Details.BaubleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elf2 {
    protected static final int DEFAULT_BOX_SIZE = 10;
    protected static final String DEFAULT_NAME = "Stefan";
    protected Elf2 nextElf;
    protected String name;
    protected int numberOfBaubles;

    protected Bauble3[] baubleBox;

    protected Bauble3 lastBauble;

//    protected Bauble2[] baubleBox;
//
//    protected Bauble2 lastBauble;

    protected Map<BaubleType, Integer> baubleTypes;

    protected Map<BaubleType, Integer> actualCapacity;

    public Elf2(BaubleType baubleType, String name, Elf2 nextElf, int boxSize){
        if(boxSize <= 0) throw new IllegalArgumentException();

        this.baubleTypes = new HashMap<>();
        this.baubleTypes.put(baubleType, boxSize);

        this.actualCapacity = new HashMap<>();
        this.actualCapacity.put(baubleType, 0);

        this.name = name;
        this.nextElf = nextElf;
        this.numberOfBaubles = 0;
        this.baubleBox = new Bauble3[boxSize];
//        this.baubleBox = new Bauble2[boxSize];
    }

    public Elf2(Map<BaubleType, Integer> baubleTypes, String name, Elf2 nextElf){

        if(!isCorrectBaubleTypes(baubleTypes)) throw new IllegalArgumentException();

        this.baubleTypes = baubleTypes;
        initializeCapacity(baubleTypes);

        this.name = name;
        this.nextElf = nextElf;
        this.numberOfBaubles = 0;
        if(baubleTypes.size() == 2) this.baubleBox = new Bauble3[baubleTypes.get(BaubleType.BIG_BALL) + baubleTypes.get(BaubleType.SMALL_BALL)];
        else this.baubleBox = new Bauble3[(Integer)baubleTypes.values().toArray()[0]];
//        this.baubleBox = new Bauble2[boxSize];
    }

    protected void initializeCapacity(Map<BaubleType, Integer> baubleTypes){
        List<BaubleType> types = baubleTypes.keySet().stream().toList();

        this.actualCapacity = new HashMap<>();

        for(int i = 0; i < types.size(); i++){
            this.actualCapacity.put(types.get(i), 0);
        }
    }

    private boolean isCorrectBaubleTypes(Map<BaubleType, Integer> baubleTypes){
        if(baubleTypes.size() > 2) return false;
        if(baubleTypes.size() == 2){
            Integer value = baubleTypes.get(BaubleType.BIG_BALL);
            if (value == null || value < 0) return false;
            value = baubleTypes.get(BaubleType.SMALL_BALL);
            if (value == null || value < 0) return false;
            return true;
        }

        if(baubleTypes.size() == 1){
            return ((Integer) baubleTypes.values().toArray()[0] > 0);
        }
        return false;
    }

    public Elf2(BaubleType baubleType, String name, Elf2 nextElf){
        this(baubleType, name, nextElf, DEFAULT_BOX_SIZE);
    }

    public Elf2(BaubleType baubleType, String name){
        this(baubleType, name, null);
    }

    public Elf2(BaubleType baubleType){
        this(baubleType, DEFAULT_NAME);
    }

    public int getNumberOfBaubles(){
        return numberOfBaubles;
    }

    public Elf2 getNextElf() {
        return nextElf;
    }

    public void setNextElf(Elf2 nextElf) {
        this.nextElf = nextElf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<BaubleType,Integer> getBaubleType(){
        return this.baubleTypes;
    }

    public void packTheBauble(Bauble3 bauble){
        if(this.lastBauble == bauble) breakTheBauble(bauble);
        else this.lastBauble = null;

        BaubleType baubleType = bauble.getBaubleType();
        Integer value = this.baubleTypes.get(bauble.getBaubleType());

        if (value != null && this.actualCapacity.get(baubleType) < value && numberOfBaubles < baubleBox.length){
            baubleBox[this.numberOfBaubles] = bauble;
            this.numberOfBaubles++;
            int temp = this.actualCapacity.get(baubleType) + 1;
            actualCapacity.put(baubleType,temp);
            if (numberOfBaubles == baubleBox.length){
                this.fullBoxMessage();
                initializeCapacity(this.baubleTypes);
            }
        } else if (nextElf != null){
            lastBauble = bauble;
            nextElf.packTheBauble(bauble);
        } else {
            breakTheBauble(bauble);
        }
    }



//    public void packTheBauble(Bauble2 bauble) {
//        if(this.lastBauble == bauble) breakTheBauble(bauble);
//        else this.lastBauble = null;
//
//        if (bauble.getBaubleType() == this.baubleType && numberOfBaubles < baubleBox.length){
//            baubleBox[this.numberOfBaubles] = bauble;
//            this.numberOfBaubles++;
//            if (numberOfBaubles == baubleBox.length) this.fullBoxMessage();
//        } else if (nextElf != null){
//            lastBauble = bauble;
//            nextElf.packTheBauble(bauble);
//        } else breakTheBauble(bauble);
//    }


    @Override
    public String toString() {
        return "Elf " + this.name;
    }
    protected void breakTheBauble(Bauble3 bauble){
        System.out.println("BAM!!!");
        System.out.println(bauble + " has just been turned into glitter ¯\\_(ツ)_/¯\n");
    }

    protected void breakTheBauble(Bauble2 bauble){
        System.out.println("BAM!!!");
        System.out.println(bauble + " has just been turned into glitter ¯\\_(ツ)_/¯\n");
    }

    protected void fullBoxMessage(){
        System.out.println("ATTENTION!!!");
        System.out.print("I - " + this.name + " - already have a full box\n\n");
    }
}
