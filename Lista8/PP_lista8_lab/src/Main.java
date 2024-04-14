import Baubles.*;
import Details.*;
import Elves.*;

import java.util.*;

public class Main {
    static Random random = new Random();

    public static void main(String[] args) {
        int boxSize = 10;
        Elf e1 = new BigBallElf("Luell", null, boxSize);
        Elf e2 = new SmallBallElf("Eldarion", e1, boxSize);
        Elf e3 = new IcycleElf("Legolas", e2, boxSize);
        Elf e4 = new MushroomElf("Gandalf", e3, boxSize);
        Elf e5 = new MushroomElf("Gandalf", e4, boxSize);

        int numberOfBaubles = 40;
        List<Bauble> baubles = generateBaubleList(numberOfBaubles);
        packBaubles(baubles, e4);

        System.out.println("--------------------------");
        System.out.println();
        System.out.println("--------------------------\n");


        Elf2 e6 = new Elf2(BaubleType.ICYCLE, "Luell", null, boxSize);
        Elf2 e7 = new Elf2(BaubleType.SMALL_BALL, "Eldarion", e6, boxSize);
        Elf2 e8 = new Elf2(BaubleType.BIG_BALL, "Legolas", e7, boxSize);
        Elf2 e9 = new Elf2(BaubleType.MUSHROOM, "Gandalf", e8, boxSize);


        Map<BaubleType, Integer> map1 = new HashMap<>();
        map1.put(BaubleType.MUSHROOM, 10);
        Elf2 e10 = new Elf2(map1, "Gandalf", null);

        Map<BaubleType, Integer> map2 = new HashMap<>();
        map2.put(BaubleType.SMALL_BALL, 10);
        map2.put(BaubleType.BIG_BALL, 10);
        Elf2 e11 = new Elf2(map2, "Gandalf", e10);


        Map<BaubleType, Integer> map3 = new HashMap<>();
        map3.put(BaubleType.ICYCLE, 10);
        Elf2 e12 = new Elf2(map3, "Gandalf", e11);








//        List<Bauble2> baubles2 = generateBauble2List(numberOfBaubles);
//        packBaubles(baubles2, e8);

//        System.out.println("---------------------");
//
//        Bauble2 b1 = new Bauble2(BaubleType.MUSHROOM, getRandomColor(), getRandomPattern());
//        System.out.println(b1.getPattern());
//        System.out.println(b1.getColor());
////        System.out.println(b1.getBaubleRadius());
//
//        System.out.println("---------------------");
//
//        Bauble2 b2 = new Bauble2(BaubleType.SMALL_BALL, getRandomColor(), getRandomPattern());
//        System.out.println(b2.getPattern());
//        System.out.println(b2.getColor());
//        System.out.println(b2.getBaubleRadius());
//        System.out.println(b2.isFittedToHand());
////        System.out.println(b2.isGilded());
//
//        System.out.println("---------------------");
//
//        Bauble2 b3 = new Bauble2(BaubleType.BIG_BALL, getRandomColor(), getRandomPattern());
//        System.out.println(b3.getPattern());
//        System.out.println(b3.getColor());
//        System.out.println(b3.getBaubleRadius());
//        System.out.println(b3.isGilded());
////        System.out.println(b3.isMuchomorek());
//
//        System.out.println("---------------------");
//
//        Bauble2 b4 = new Bauble2(BaubleType.ICYCLE, getRandomColor(), getRandomPattern());
//        System.out.println(b4.getPattern());
//        System.out.println(b4.getColor());
//        System.out.println(b4.getIcycleLength());
////        System.out.println(b4.isFittedToHand());


        List<Bauble3> baubles3 = generateBauble3List(numberOfBaubles);
        packBaubles(baubles3, e9);
        System.out.println("---------------------");
        System.out.println("---------------------");
        packBaubles(baubles3, e12);

        System.out.println("---------------------");

        Bauble3 b1 = new Bauble3(BaubleType.MUSHROOM, getRandomColor(), getRandomPattern(), getRandomBoolean());
        System.out.println(b1.getPattern());
        System.out.println(b1.getColor());
//        System.out.println(b1.getBaubleRadius());

        System.out.println("---------------------");

        Bauble3 b2 = new Bauble3(BaubleType.SMALL_BALL, getRandomColor(), getRandomPattern(), getRandomDouble(0,3), getRandomBoolean());
        System.out.println(b2.getPattern());
        System.out.println(b2.getColor());
        System.out.println(b2.getBaubleRadius());
        System.out.println(b2.isFittedToHand());
//        System.out.println(b2.isGilded());

        System.out.println("---------------------");

        Bauble3 b3 = new Bauble3(BaubleType.BIG_BALL, getRandomColor(), getRandomPattern(), getRandomDouble(3,6), getRandomBoolean());
        System.out.println(b3.getPattern());
        System.out.println(b3.getColor());
        System.out.println(b3.getBaubleRadius());
        System.out.println(b3.isGilded());
//        System.out.println(b3.isMuchomorek());

        System.out.println("---------------------");

        Bauble3 b4 = new Bauble3(BaubleType.ICYCLE, getRandomColor(), getRandomPattern(), getRandomDouble(0,15));
        System.out.println(b4.getPattern());
        System.out.println(b4.getColor());
        System.out.println(b4.getIcycleLength());
//        System.out.println(b4.isFittedToHand());

        System.out.println("---------------------");
    }


    public static Color getRandomColor() {
        Color[] colors = Color.values();
        int index = random.nextInt(0, colors.length);
        return colors[index];
    }

    public static Pattern getRandomPattern() {
        Pattern[] patterns = Pattern.values();
        int index = random.nextInt(0, patterns.length);
        return patterns[index];
    }

    public static boolean getRandomBoolean(){
        return random.nextInt(0, Integer.MAX_VALUE) % 2 == 0;
    }

    public static double getRandomDouble(double min, double max){
        return Math.round(random.nextDouble(min, max) * 100.0)/100.0;
    }

    public static double getRandomInt(int min, int max){
        return random.nextInt(min, max);
    }

    public static BaubleType getRandomBaubleType() {
        BaubleType[] baubles = BaubleType.values();
        int index = random.nextInt(0, baubles.length);
        return baubles[index];
    }

    public static List<Bauble> generateBaubleList(int size){
        if(size <= 0){
            return new ArrayList<>();
        }
        List<Bauble> result = new ArrayList<>(size);
        int rand;
        int min = 1, middle = 3, max = 6;
        for (int i = 0; i < size; i++) {
            rand = random.nextInt(0, 4);
            switch (rand) {
                case 0 ->
                        result.add(new BigBall(getRandomColor(), getRandomPattern(), getRandomDouble(middle, max), getRandomBoolean()));
                case 1 ->
                        result.add(new SmallBall(getRandomColor(), getRandomPattern(),  getRandomDouble(min, middle), getRandomBoolean()));
                case 2 ->
                        result.add(new Icycle(getRandomColor(), getRandomPattern(),  getRandomDouble(0.01,20)));
                case 3 ->
                        result.add(new Mushroom(getRandomColor(), getRandomPattern(), getRandomBoolean()));
                default -> --i;
            }
        }
        return result;
    }



    public static void packBaubles(List<Bauble> baubles, Elf firstElf){
        for(int i = 0; i < baubles.size(); i++){
            firstElf.packTheBauble(baubles.get(i));
        }
    }

//    public static void packBaubles(List<Bauble2> baubles, Elf2 pierwszyElf){
//        for(int i = 0; i < baubles.size(); i++){
//            pierwszyElf.packTheBauble(baubles.get(i));
//        }
//    }

    public static void packBaubles(List<Bauble3> baubles, Elf2 pierwszyElf){
        for(int i = 0; i < baubles.size(); i++){
            pierwszyElf.packTheBauble(baubles.get(i));
        }
    }
    public static List<Bauble3> generateBauble3List(int size){
        if(size <= 0){
            return new ArrayList<>();
        }
        List<Bauble3> result = new ArrayList<>(size);
        int rand;
        double min = 0.01, middle = 3, max = 6;
        for (int i = 0; i < size; i++) {
            rand = random.nextInt(0, 4);
            switch (rand) {
                case 0 ->
                        result.add(new Bauble3(BaubleType.BIG_BALL, getRandomColor(), getRandomPattern(), getRandomDouble(middle, max), getRandomBoolean()));
                case 1->
                        result.add(new Bauble3(BaubleType.SMALL_BALL, getRandomColor(), getRandomPattern(), getRandomDouble(min, middle), getRandomBoolean()));
                case 2 ->
                        result.add(new Bauble3(BaubleType.ICYCLE, getRandomColor(), getRandomPattern(), getRandomDouble(min, 20), getRandomBoolean()));
                case 3 ->
                        result.add(new Bauble3(BaubleType.MUSHROOM, getRandomColor(), getRandomPattern(), getRandomBoolean()));
                default -> --i;
            }
        }
        return result;
    }


    public static List<Bauble2> generateBauble2List(int size){
        if(size <= 0){
            return new ArrayList<>();
        }
        List<Bauble2> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(new Bauble2(getRandomBaubleType(), getRandomColor(), getRandomPattern()));
        }
        return result;
    }

}