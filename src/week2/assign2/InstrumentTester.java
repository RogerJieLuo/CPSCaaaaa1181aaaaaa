package week2.assign2;

/**
 * This is a Tester for instrument class.
 *
 * @author Jie Luo
 * @version Jan 13, 2018
 *
 */
public class InstrumentTester {

    public static void main(String[] args) {

        // check results with initial constructor with type and mrsp
        System.out.println("********   check results with initial constructor with type and mrsp   *********");
        Instrument flute = new Instrument("flute", null, null, 100);
        System.out.println(String.format("flute's type is: \"%s\", expected is \"flute\".", flute.getType() ));
        System.out.println(String.format("        item id is: \"%s\", expected is \"f100\".", flute.getItemId() ));
        System.out.println(String.format("        mrsp is: $%.2f, expected is $100.00", flute.getMrsp() ));
        // check discount added
        System.out.println("        ********   check discount added   *********");
        flute.addDiscountPercentage(20);
        System.out.println(String.format("        20%% discount, it's price is: $%.2f, expected is $80.00", flute.calculatePrice() ));
        flute.addDiscountPercentage(10);
        System.out.println(String.format("        10%% more discount, it's price is: $%.2f, expected is $72.00", flute.calculatePrice() ));
        System.out.println(String.format("        total discount is: %.2f%% off, expected 28.00%% off", flute.getDiscount()));
        System.out.println(String.format("        total markup is: %.2f%%, expected 0.00%%", flute.getMarkup()));
        System.out.println(String.format("        mrsp is: $%.2f, expected $100.00", flute.getMrsp()));
        System.out.println();

        // check markup added
        System.out.println("********   check markup added    *********");
        Instrument erhu = new Instrument("erhu", null, null, 416.00);
        System.out.println(String.format("erhu's item id is: \"%s\", expected is \"e101\".", erhu.getItemId() ));
        erhu.addMarkupPercentage(300);
        System.out.println(String.format("       300%% markup, it's price is: $%.2f, expected is $1664.00.", erhu.calculatePrice() ));
        erhu.addMarkupPercentage(50);
        System.out.println(String.format("       50%% markup, it's price is: $%.2f, expected is $2496.00.", erhu.calculatePrice() ));
        System.out.println(String.format("       total discount is: %.2f%% off, expected 0.00%% off", erhu.getDiscount()));
        System.out.println(String.format("       total markup is: %.2f%%, expected 500.00%%", erhu.getMarkup()));
        System.out.println(String.format("       mrsp is: $%.2f, expected $416.00", erhu.getMrsp()));
        System.out.println();

        // check digits in item id increases by 1
        System.out.println("********   check digits in item id keep continues   *********");
        Instrument flute2 = new Instrument("flute2", null, null, 200);
        System.out.println(String.format("The flute2's item id is: \"%s\", expected is \"f102\"", flute2.getItemId()));
        System.out.println();

        // check empty constructor results
        System.out.println("********   check empty constructor results   *********");
        Instrument erhu2 = new Instrument();
        System.out.println(String.format("erhu2's type is: \"%s\", expected is \"!none\".", erhu2.getType() ));
        System.out.println(String.format("        item id is: \"%s\", expected \"!103\"", erhu2.getItemId()));
        System.out.println(String.format("        mrsp is: $%.2f, expected $0.00", erhu2.getMrsp()));
        System.out.println();

        // check useless white space
        System.out.println("********   check useless white space    *********");
        Instrument horn = new Instrument("   English    Horn     ", "  Loud  Noise", " GC2 / 222", 100);
        System.out.println(String.format("horn's type is: \"%s\", expected \"english horn\"", horn.getType()));
        System.out.println(String.format("       item id is: \"%s\", expected \"e104\"", horn.getItemId()));
        System.out.println(String.format("       brand name and model is: \"%s\", expected \"Loud Noise - GC2 / 222\"",
                horn.getBrandNModel()));
        System.out.println();

        System.out.println("********   check getBrandNModel()   *********");
        Instrument horn2 = new Instrument("   English    Horn   2", "  Loud  Noise   ", null, 150);
        System.out.println(String.format("horn2's type is: \"%s\", expected \"english horn 2\"", horn2.getType()));
        System.out.println(String.format("       item id is: \"%s\", expected \"e105\"", horn2.getItemId()));
        System.out.println(String.format("       brand name and model is: \"%s\", expected \"Loud Noise\"",
                horn2.getBrandNModel()));
        System.out.println();

        // check getBrandNModel() when there is no brand name, negative discount and markup
        System.out.println("********   check negative discount and markup   *********");
        Instrument horn3 = new Instrument("   English    Horn   2", null, " GC2 / 222", 150);
        System.out.println(String.format("horn3's brand name and model is: \"%s\", expected empty string",
                horn3.getBrandNModel()));
        horn3.addDiscountPercentage(-100);
        System.out.println(String.format("        -100%% discount, price is: $%.2f, expected warning message and the price is $150.00.",
                horn3.calculatePrice() ));
        horn3.addMarkupPercentage(-100);
        System.out.println(String.format("        -100%% markup, price is: $%.2f, expected warning message and the price is $150.00.",
                horn3.calculatePrice() ));
        System.out.println();

        // check empty type, brand name and model
        System.out.println("********   check empty type brand name and model   *********");
        Instrument strange = new Instrument( "     ", "    ", "     ", -150);
        System.out.println(String.format("strange: type is: \"%s\", expected is \"!none\".", strange.getType() ));
        System.out.println(String.format("         item id is: \"%s\", expected is \"!107\".", strange.getItemId() ));
        System.out.println(String.format("         mrsp is: $%.2f, expected is $0.00", strange.getMrsp() ));
        System.out.println();
    }
}
