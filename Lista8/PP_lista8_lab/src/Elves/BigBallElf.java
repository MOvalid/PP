package Elves;

import Baubles.Bauble;
import Baubles.BigBall;

public class BigBallElf extends Elf {

    public BigBallElf(String name, Elf nextElf, int boxSize){
        super(name, nextElf);
        if(boxSize <= 0) throw new IllegalArgumentException();
        else baubleBox = new BigBall[boxSize];
    }
    public BigBallElf(String name, Elf nextElf){
        this(name, nextElf, DEFAULT_BOX_SIZE);
    }

    public BigBallElf(String name){
        this(name, null);
    }

    public BigBallElf(){
        this(DEFAULT_NAME);
    }


    @Override
    public void packTheBauble(Bauble bauble) {
        if(this.lastBauble == bauble) breakTheBauble(bauble);
        else this.lastBauble = null;

        if (bauble instanceof BigBall && numberOfBaubles < baubleBox.length){
            baubleBox[this.numberOfBaubles] = bauble;
            this.numberOfBaubles++;
            if (numberOfBaubles == baubleBox.length){
                this.fullBoxMessage();
                numberOfBaubles = 0;
            }
        } else if (nextElf != null){
            lastBauble = bauble;
            nextElf.packTheBauble(bauble);
        } else breakTheBauble(bauble);
    }

    @Override
    protected void fullBoxMessage(){
        super.fullBoxMessage();
        System.out.print(" of Big Balls!\n\n");
    }
}
