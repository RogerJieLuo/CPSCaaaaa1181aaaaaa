package week3.assign;
/**
 * The MusicStore is a class that can store lots of instruments.
 * MusicStore includes functions:
 * 	    Adding a new instrument to the music store
 •      Finding instrument based on the item id
 •      Getting the price of an existing instrument based on the item id
 •      Removing an instrument from the music store given the instrument’s item id
 •      Counting the number of instruments of a type
 •      Adding to the discount percentage of every instrument in the store
 •      Adding to the markup percentage of every instrument in the store
 •      Giving the total number of instruments in the music store.
 •      Giving a textual representation of all the instruments in the store
 •      Finding an instrument that is the most expensive

 * @author Jie Luo
 * @version Jan 20, 2018
 */

import java.util.ArrayList;

public class MusicStore {

    private static final double DEFAULT_DISCOUNT = 0;
    private static final double DEFAULT_MARKUP = 0;

    private ArrayList<Instrument> listOfInstrument;
    private double discount = DEFAULT_DISCOUNT;
    private double markup = DEFAULT_MARKUP;

    /**
     * constructor with no parameters and initiate the listOfInstrument
     */
    public MusicStore()
    {
        listOfInstrument = new ArrayList<>();
    }

    /**
     * This method is used to add an instrument into music store
     * @param instrument the instrument needed to be stored
     */
    public void addInstrument(Instrument instrument)
    {
        instrument.addDiscountPercentage(discount);
        instrument.addMarkupPercentage(markup);
        listOfInstrument.add(instrument);
    }

    /**
     * This method will find the instrument by item id, if not found, return null
     * @param itemId item id is the key to find instrument
     * @return instrument found or null
     */
    public Instrument find(String itemId)
    {
        for(Instrument i : listOfInstrument)
        {
            if(i.getItemId().equals(itemId))
                return i;
        }
        return null;
    }

    /**
     * This method is used to find the price of an instrument. The price will be calculated
     * after discount and markup
     * @param itemId the key to find the instrument
     * @return the price
     */
    public double getPrice(String itemId)
    {
        for(Instrument i : listOfInstrument)
        {
            if(i.getItemId().equals(itemId))
            {
//                return i.calculatePrice() * (1 - discount / 100.0) * ( 1 + markup / 100.0);
                return i.calculatePrice();
            }
        }
        return -1;
    }

    /**
     * This method is used to remove an instrument from music store
     * @param itemId the instrument needed to be removed
     * @return the instrument been removed
     */
    public Instrument remove(String itemId)
    {
        for(Instrument i : listOfInstrument)
        {
            if(i.getItemId().equals(itemId))
            {
                listOfInstrument.remove(i);
                return i;
            }
        }
        return null;
    }

    /**
     * This method is used to count the number of instruments of a type.
     * @param type instrument type
     * @return number of instrument of the type
     */
    public int countInstrumentByType(String type)
    {
        type = Instrument.normalize(type);
        int count = 0;
        for(Instrument i : listOfInstrument)
        {
            if(i.getType().equals(type))
            {
                count ++;
            }
        }
        return count;
    }

    /**
     * Set up the discount, the discount will affect the instrument price,
     * @param discount discount will be off from price
     */
    public void setDiscount(double discount )
    {
        if(discount < 0)
            return;
        for(Instrument instrument : listOfInstrument)
        {
            instrument.addDiscountPercentage(discount);
        }
        if(this.discount == 0)
        {
            this.discount = discount;
        }
        else
        {
            this.discount = 100.0 * ( 1 - ( 1 - this.discount / 100.0 ) * ( 1 - discount / 100.0 ) );
        }
    }

    /**
     * Set up markup
     * @param markup markup will be added up based on price
     */
    public void setMarkup(double markup)
    {
        if(markup < 0)
            return;
        // update the instrument discount
        for(Instrument instrument : listOfInstrument)
        {
            instrument.addMarkupPercentage(markup);
        }
        // set up markup value
        if(this.markup == 0)
        {
            this.markup = markup;
        }
        else
        {
            this.markup = 100.0 * ( ( 1 + this.markup / 100.0 ) * ( 1 + markup / 100.0) - 1 );
        }
    }

    /**
     * get the total number instruments in the music store
     * @return total number
     */
    public int getTotalInstruments()
    {
        return listOfInstrument.size();
    }

    /**
     * This method is used to find the most expensive instrument in the music store
     * @return the instrument which is the most expensive
     */
    public Instrument findMostExpensive()
    {
        double max = 0.0;
        Instrument instrument = null;
        for(Instrument i : listOfInstrument)
        {
            String itemId = i.getItemId();
            if(this.getPrice(itemId) > max)
            {
                max = this.getPrice(itemId);
                instrument = i;
            }
        }
        return instrument;
    }

    /**
     * Textual representation of all the instruments in the music store
     * @return textual representation
     */
    public String toString()
    {
        if(listOfInstrument.size() == 0)
            return "[]";
        String str = "[";
        for (Instrument i : listOfInstrument)
        {
            str += i + ", ";
        }
        str = str.substring(0, str.length() - 2);
        str += "]";
        return str;
    }

}
