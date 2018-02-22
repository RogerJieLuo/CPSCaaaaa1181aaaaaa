package week3.assign;

/**
 * Commands supported by the MusicStoreTester to test  and use the MusicStore
 * which is a set of Instrument objects.
 *
 * @author Gladys Monagan
 * @version January 20, 2018
 */
public enum Command
{
    ADD,
    FIND,
    REMOVE,
    PRICE,
    COUNT,
    DISCOUNT,
    MARKUP,
    TOTAL,
    DEAREST,
    PRINT,
    COMMENT,
    QUIT,
    INVALID;

    /**
     * Explanations given to clarify the commands allowed.
     * The order of the explanations is significant it should be the same
     * as the ordinal value of the commands.
     */
    public static final String[] EXPLANATION =
            {
                    "add - add to the store an incoming new instrument",
                    "find - retrieve the instrument based on its item id",
                    "remove - remove an instrument using the item id",
                    "price - get the price of an instrument given its item id",
                    "count - count all the instruments with the given type",
                    "discount - give a discount to all the instruments in the store",
                    "markup - give a markup to all the instruments in the store",
                    "total - give the total number of instruments in the store",
                    "dearest - retrieve a most expensive instrument in the store",
                    "print - print all the instruemnts in the music store",
                    "comment - for documentation, the following line is ignored",
                    "quit - stop the program",
                    "anything else is not a valid command"
            };

    /**
     * Determines if str corresponds to one of the "String values" of the
     * commands of the enumerated type. If it does not match any, the
     * enumerated type INVALID is returned otherwise the type corresponding
     * to the string is returned.
     * @param str which is going to be tested against the String Command types
     * @return an enumerated type that matches the str
     */
    public static Command type(String str)
    {
        Command cmd;
        str = str.trim().toUpperCase();
        if ((str.length() == 0 ) || (str.indexOf(' ') != -1) )
        {
            cmd = INVALID;
        }
        else
        {
            try
            {
                cmd = valueOf(str);
            }
            catch (IllegalArgumentException e)
            {
                cmd = INVALID;
            }
        }
        return cmd;
    }
}