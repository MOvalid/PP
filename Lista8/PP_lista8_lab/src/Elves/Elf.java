package Elves;

import Baubles.Bauble;


public abstract class Elf {
    protected static final int DEFAULT_BOX_SIZE = 10;
    protected static final String DEFAULT_NAME = "Stefan";
    protected Elf nextElf;
    protected String name;
    protected int numberOfBaubles;

    protected Bauble[] baubleBox;

    protected Bauble lastBauble;

    public Elf(String name, Elf nextElf){
        this.name = name;
        this.nextElf = nextElf;
        this.numberOfBaubles = 0;
    }

    public Elf(String name){
        this(name, null);
    }

    public Elf(){
        this(DEFAULT_NAME);
    }

    public int getNumberOfBaubles(){
        return numberOfBaubles;
    }

    public Elf getNextElf() {
        return nextElf;
    }

    public void setNextElf(Elf nextElf) {
        this.nextElf = nextElf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void packTheBauble(Bauble bauble);


    @Override
    public String toString() {
        return "Elf " + this.name;
    }
    protected void breakTheBauble(Bauble bauble){
        System.out.println("BAM!!!");
        System.out.println(bauble + " has just been turned into glitter ¯\\_(ツ)_/¯\n");
    }

    protected void fullBoxMessage(){
        System.out.println("ATTENTION!!!");
        System.out.print("I - " + this.name + " - already have a full box");
    }
}
