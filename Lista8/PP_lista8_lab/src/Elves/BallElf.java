package Elves;

import Baubles.Ball;
import Baubles.Bauble;
import Baubles.BigBall;
import Baubles.SmallBall;

public class BallElf extends Elf {

    protected int maxOfBigBall;

    protected int counterBigBall;
    protected int counterSmallBall;
    protected int maxOfSmallBall;

    public BallElf(String name, Elf nextElf, int maxOfSmallBall, int maxOfBigBall){
        super(name, nextElf);
        if(maxOfSmallBall < 0 || maxOfBigBall < 0) throw new IllegalArgumentException();
        baubleBox = new Ball[maxOfSmallBall + maxOfBigBall];
        this.maxOfBigBall = maxOfBigBall;
        this.maxOfSmallBall = maxOfSmallBall;
    }
    public BallElf(String name, Elf nextElf){
        this(name, nextElf, DEFAULT_BOX_SIZE, DEFAULT_BOX_SIZE);
    }

    public BallElf(String name){
        this(name, null);
    }

    public BallElf(){
        this(DEFAULT_NAME);
    }


    @Override
    public void packTheBauble(Bauble bauble) {
        if(this.lastBauble == bauble) breakTheBauble(bauble);
        else this.lastBauble = null;

        if (bauble instanceof BigBall && counterBigBall < maxOfBigBall) {
            baubleBox[this.numberOfBaubles] = bauble;
            this.numberOfBaubles++;
            this.counterBigBall++;
            if (numberOfBaubles == baubleBox.length) this.fullBoxMessage();
        } else if (bauble instanceof SmallBall && counterSmallBall < maxOfSmallBall){
            baubleBox[this.numberOfBaubles] = bauble;
            this.numberOfBaubles++;
            this.counterSmallBall++;
            if (numberOfBaubles == baubleBox.length){
                this.fullBoxMessage();
                numberOfBaubles = 0;
                counterSmallBall = 0;
                counterBigBall = 0;
            }
        } else if (nextElf != null){
            lastBauble = bauble;
            nextElf.packTheBauble(bauble);
        } else breakTheBauble(bauble);
    }

    @Override
    protected void fullBoxMessage(){
        super.fullBoxMessage();
        System.out.print(" of Balls!\n\n");
    }
}
