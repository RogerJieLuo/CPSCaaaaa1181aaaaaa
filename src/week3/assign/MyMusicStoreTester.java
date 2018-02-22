package week3.assign;

/**
 * Several test cases for MusicStore.
 * MyMusicStoreTester tests MusicStore's main functions:
 *      addInstrument(Instrument instrument)
 *      toString()
 *      find(String itemId)
 *      getPrice(String itemId)
 *      remove(String itemId)
 *      countInstrumentByType(String type)
 *      getTotalInstruments()
 *      findMostExpensive()
 *      setDiscount()
 *      setMarkup()
 * tests the logic about after discount, the newly added instrument also get the discount
 * tests the display modification about Instrument class
 *
 */
public class MyMusicStoreTester {
    public static void main(String[] args) {

        MusicStore ms = new MusicStore();

        // create and add 6 instruments in music store
        Instrument flute1 = new Instrument("flute", "Brand 1", "Model 1", 100);
        Instrument flute2 = new Instrument("flute", "Brand 1", "Model 2", 150);
        Instrument flute3 = new Instrument("flute", "Brand 1", "Model 3", 200);
        Instrument erhu1 = new Instrument("erhu", "Brand 2", "Model 1", 100);
        Instrument erhu2 = new Instrument("erhu", "Brand 2", "Model 2", 150);
        Instrument erhu3 = new Instrument("erhu", "Brand 2", "Model 3", 300);
        ms.addInstrument(flute1);
        ms.addInstrument(flute2);
        ms.addInstrument(flute3);
        ms.addInstrument(erhu1);
        ms.addInstrument(erhu2);
        ms.addInstrument(erhu3);

        // check if there are 6 instruments print out
        System.out.println("\nall the instruments in the music store: ");
        System.out.println("\t" + ms.toString());

        // check the find methhod
        Instrument found1 = ms.find("f100");
        System.out.println("\nfind item id f100 ");
        System.out.println("\texpect is: (f100, flute, Brand 1 : Model 1, 100.00)");
        System.out.println("\tactual is: " + found1);

        Instrument found2 = ms.find("f104");
        System.out.println("\nfind item id f104 ");
        System.out.println("\texpect is: null");
        System.out.println("\tactual is: " + found2);

        // check the getPrice method
        double price = ms.getPrice("f100");
        System.out.println("\nget price of item id f100 ");
        System.out.println("\texpected price is 100.00, actual is " + price);

        // check the remove method
        Instrument removed = ms.remove("f101");
        System.out.println("\nremove instrument with item id f101");
        System.out.println("\texpect removed is: (f101, flute, Brand 1 : Model 2, 150.00)");
        System.out.println("\tactual removed is: " + removed);

        // check countInstrumentByType method
        int countFlute = ms.countInstrumentByType("flute");
        System.out.println("\ncount flute type instruments ");
        System.out.println("\texpected number is 2, actual is " + countFlute);

        // check setDiscount method
        ms.setDiscount(50);
        double flute1Price = ms.getPrice("f100");
        double erhu3Price = ms.getPrice("e105");
        System.out.println("\nafter discount 50%: ");
        System.out.println("\tflute1 expect price is 50.00, actual is " + flute1Price);
        System.out.println("\terhu3 expect price is 150.00, actual is " + erhu3Price);
        System.out.println(ms);

        // check the newly created instrument's price also get discount
        ms.addInstrument(new Instrument("flute", "Brand New", "Model New", 1000));
        double newlyPrice = ms.getPrice("f106");
        System.out.println("\ncreate new instrument after discount");
        System.out.println("\texpect new price is 500.00 ");
        System.out.println("\tactual new price is " +  newlyPrice);

        // check setMarkup method
        ms.setMarkup(100);
        flute1Price = ms.getPrice("f100");
        erhu3Price = ms.getPrice("e105");
        System.out.println("\nafter markup 100%: ");
        System.out.println("\tflute1 expect price is 100.00, actual is " + flute1Price);
        System.out.println("\terhu3 expect price is 300.00, actual is " + erhu3Price);
        System.out.println(ms);

        // check getTotalInstruments method
        int total = ms.getTotalInstruments();
        System.out.println("\nget the number of all the instrument");
        System.out.println("\texpect total number is 6, actual is " + total);

        // check findMostExpensive method
        Instrument mostExpensive = ms.findMostExpensive();
        System.out.println("\nfind most expensive instrument: ");
        System.out.println("\texpect is (erhu, Brand 2, Model 3, 300.00)");
        System.out.println("\tactual is " +  mostExpensive);

        // after the modification of Instruments
        // check default instrument
        Instrument defInstrument = new Instrument();
        System.out.println("\ndefault instrument: ");
        System.out.println("\texpect is (musical instrument, brand unknown, , 0.00)");
        System.out.println("\tactual is " + defInstrument);

        // check getBrandNModel format
        String brandAndModel = erhu1.getBrandNModel();
        System.out.println("\nerhu1's brand and model format");
        System.out.println("\texpect is Brand 2 : Model 1");
        System.out.println("\tactual is " + brandAndModel);


    }
}
