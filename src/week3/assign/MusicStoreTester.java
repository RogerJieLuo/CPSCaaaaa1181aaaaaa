package week3.assign;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *
 * Standard input and standard output user interface for the MusicStore class
 * which is a set of Instrument objects.
 * There is an option for interaction via the console and also for
 * batch operations. The operations supported are
 * <ul>
 *
 *   <li> add - add to the store an incoming new instrument </li>
 *   <li> find - retrieve the instrument based on its item id </li>
 *   <li> remove - remove an instrument using the item id </li>
 *   <li> price - get the price of an instrument given its item id </li>
 *   <li> count - count all the instruments with the given type </li>
 *   <li> discount - give a discount to all the instruments in the store </li>
 *   <li> markup - give a markup to all the instruments in the store </li>
 *   <li> total - give the total number of instruments in the store </li>
 *   <li> dearest - retrieve a most expensive instrument in the store </li>
 *   <li> print - print all the instruments in the music store </li>
 *   <li> comment - for documentation, the following line is ignored </li>
 *   <li> quit - stop the program </li>
 *   <li> anything else is not a valid command </li>
 * </ul>
 * @author Gladys Monagan
 * @version January 20, 2018
 */

public class MusicStoreTester
{
    private static final String PRECISION_2DIGITS = "%.2f";

    /**
     * Opens the input text stream, processes command to handle instruments
     * in a music store and outputs to standard output results.
     * @param args line arguments -- ignored in this assignment
     */
    public static void main( String[] args )
    {
        Scanner in = new Scanner(System.in);
        boolean notInteractive = !isInteractiveModeFromUser(in);
        MusicStore musicStore = new MusicStore();
        processCommands(musicStore, in, notInteractive);
        in.close();
    } // main

    /**
     * Outputs a query to find out if the session is interactive
     * and echoes the response if the session is not interactive.
     * If the user using the Scanner <code>in</code> types any word starting
     * with y or Y, we say that the session IS interactive, otherwise, it is not.
     * @param in an open input stream
     * @return true if the session is interactive, false otherwise
     */
    private static boolean isInteractiveModeFromUser(Scanner in)
    {
        output(systemPrompt() + "Is the session interactive? type \"yes\": ");
        String inputStr = in.nextLine();
        String answerStr = inputStr.trim().toLowerCase();
        boolean isInteractive = false;
        if (answerStr.length() >= 1 )
        {
            answerStr = inputStr.substring(0, 1);
            isInteractive = answerStr.equalsIgnoreCase("y");
        }
        if (!isInteractive) echo(inputStr);
        return isInteractive;
    } // isInteractiveModeFromUser


    /**
     * Outputs menu choices to standard output the commands available.
     */
    private static void printAvailableCommands()
    {
        outputln("\n   Commands:");
        for (String str : Command.EXPLANATION)
        {
            outputln("\t" + str);
        }
    } // printAvailableCommands

    /**
     * Outputs a prompt and reads the next line.
     * It skips over empty lines -- the empty lines are ignored.
     * If <code>notInteractive</code> is true, it outputs
     * the line read by using the method <code>echo</code>
     * @param in an open input stream
     * @param notInteractive with a value of true indicates that the session
     * is not interactive and that the line read must be echoed
     * @return the command in String form with "INVALID" as a possibility
     */
    private static Command getNextCommand(Scanner in, boolean notInteractive)
    {
        output("-----------------------------------------------------");
        outputln("-----------------");
        output(systemPrompt() + "Enter a command: ");
        String str = in.nextLine().trim();
        while (str.length() == 0)
        {
            str = in.nextLine().trim();
        }
        if (notInteractive) echo(str);
        return Command.type(str);
    } // getNextCommand

    /**
     * Manipulates a music store and processes the commands that it gets using <code>in</code>.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the MusicStore that will have data manipulated
     * @param in an open input stream
     * @param notInteractive flag which when true, does not echo the input
     */
    public static void processCommands(MusicStore musicStore, Scanner in, boolean notInteractive)
    {
        Command cmd;
        do
        {
            if (!notInteractive) printAvailableCommands();

            cmd = getNextCommand(in, notInteractive);
            switch (cmd)
            {
                case ADD:
                    add(musicStore, in, notInteractive);
                    break;
                case FIND:
                    find(musicStore, in, notInteractive);
                    break;
                case REMOVE:
                    remove(musicStore, in, notInteractive);
                    break;
                case PRICE:
                    getPrice(musicStore, in, notInteractive);
                    break;
                case COUNT:
                    count(musicStore, in, notInteractive);
                    break;
                case DISCOUNT:
                    giveStoreDiscount(musicStore, in, notInteractive);
                    break;
                case MARKUP:
                    giveStoreMarkup(musicStore, in, notInteractive);
                    break;
                case TOTAL:
                    numberOfInstruments(musicStore);
                    break;
                case DEAREST:
                    getMostExpensive(musicStore);
                    break;
                case PRINT:
                    printOutStore(musicStore);
                    break;
                case COMMENT:
                    commentLines(in, notInteractive);
                    break;
                case QUIT:
                    // nothing to do
                    break;
                default:
                    outputError("ignoring the invalid command");
                    break;
            }
        } while (cmd != Command.QUIT);
    } // processCommands

    /**
     * Obtains an item id (a single word) from str, printing an error
     * if there was more than one word.
     * @param in an open input stream
     * @param notInteractive declares that the session is not
     * with the console but likely from redirected input from a file
     * @return one word without trailing nor leading blanks or null if there
     * were only blanks or more than one word
     */
    private static String getItemId(Scanner in, boolean notInteractive)
    {
        output(systemPrompt() + "Enter the item id: ");
        String str = in.nextLine();
        if (notInteractive) echo(str);
        if (str.trim().indexOf(' ') >= 0)
        {
            outputError(str + " is not a valid item id");
            return null;
        }
        else
        {
            return str.trim();
        }
    } // getItemId

    /**
     * Creates an instrument and adds it to the store.
     * Reads the instrument's type, brand, model and msrp from standard input by
     * prompting the user. It prints the added instrument when
     * successful. If there is an error (e.g. due to a wrong amount for the msrp),
     * the error is printed and no instrument is added.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instrument objects
     * @param in an open input stream
     * @param notInteractive declares that the session is not
     * with the console but likely from redirected input from a file
     */
    private static void add(MusicStore musicStore, Scanner in, boolean notInteractive)
    {
        output(systemPrompt() + "Enter the type: ");
        String type = in.nextLine();
        if (notInteractive) echo(type);
        output(systemPrompt() + "Enter the brand: ");
        String brand = in.nextLine();
        if (notInteractive) echo(brand);
        output(systemPrompt() + "Enter the model: ");
        String model = in.nextLine();
        if (notInteractive) echo(model);
        output(systemPrompt() + "Enter the msrp in dollars: ");
        String msrpStr = in.nextLine();
        if (notInteractive) echo(msrpStr);
        double msrp = getDouble(msrpStr);
        if (msrp < 0)
        {
            outputError("Failed to add the instrument: " + type);
        }
        else
        {
            Instrument instrument = new Instrument(type, brand, model, msrp);
            musicStore.addInstrument(instrument);// HERE add instrument to musicStore
            output("added", instrument);
        }
    } // add

    /**
     * Finds and prints the instance of the Instrument given an item id
     * which is obtained from standard input.
     * If the item id given by the user is erroneous or if the item id
     * is not in the music store, an error message is printed.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     * @param in an open input stream
     * @param notInteractive declares that the session is not
     * with the console but likely from redirected input from a file
     */
    private static void find(MusicStore musicStore, Scanner in, boolean notInteractive)
    {
        String itemId = getItemId(in, notInteractive);
        if (itemId != null)
        {
            Instrument instrument = musicStore.find(itemId);
            if (instrument != null)
            {
                output("found", instrument);
            }
            else
            {
                outputError("failed to find an instrument with id " + itemId);
            }
        }
    } // find

    /**
     * Deletes the instrument with the item id which is read from standard input.
     * The removed instrument is printed or an error message is printed if not found.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     * @param in an open input stream
     * @param notInteractive declares that the session is not
     * with the console but likely from redirected input from a file
     */
    private static void remove(MusicStore musicStore, Scanner in, boolean notInteractive)
    {
        String itemId = getItemId(in, notInteractive);
        if (itemId != null)
        {
            Instrument instrument = musicStore.remove(itemId);
            if (instrument != null)
            {
                output("removed", instrument);
            }
            else
            {
                outputError("failed to remove an instrument with id " + itemId);
            }
        }
    } // remove

    /**
     * Finds the price of an instrument given an item id which is read from standard input.
     * If the item id is invalid, print an error message
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     * @param in an open input stream
     * @param notInteractive declares that the session is not
     * with the console but likely from redirected input from a file
     */
    private static void getPrice(MusicStore musicStore, Scanner in, boolean notInteractive)
    {
        String itemId = getItemId(in, notInteractive);
        Instrument instrument = null;
        if (itemId != null)
        {
            instrument = musicStore.find(itemId);
            if (instrument != null)
            {
                String formatStr = "The %s with id %s costs " + PRECISION_2DIGITS;
                outputln(systemPrompt() + String.format(formatStr,
                        instrument.getType(), itemId, instrument.calculatePrice()));
            }
        }
        if (itemId == null || instrument == null)
        {
            outputError("failed to calculate a price for an instrument with id " + itemId);
        }
    } // getPrice

    /**
     * Reads a single line from standard input, it is considered a comment so no action
     * is taken.
     * <p> Precondition: the musicStore is not null. </p>
     * @param in an open input stream
     * @param notInteractive declares that the session is not
     * with the console but likely from redirected input from a file
     */
    private static void commentLines(Scanner in, boolean notInteractive)
    {
        String commentLine = in.nextLine();
        if (notInteractive) echo(commentLine);
    }

    /**
     * Counts all the instruments of a given type and prints out this number.
     * The type is read from standard input. The type is normalized before
     * the query is made.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     * @param in an open input stream
     * @param notInteractive declares that the session is not
     * with the console but likely from redirected input from a file
     */
    private static void count(MusicStore musicStore,
                              Scanner in, boolean notInteractive)
    {
        output(systemPrompt() + "Enter the type of instrument: ");
        String type = in.nextLine();
        if (notInteractive) echo(type);
        type = Instrument.normalize(type);
        int n = musicStore.countInstrumentByType(type);// HERE count the number of instruments of type
        String str = "there are " + n + " " + type + "s";
        outputln(systemPrompt() + str);
    }

    /**
     * Outputs one of all the most expensive instruments:
     * given that there might be more than one most expensive instrument,
     * only one is printed. If the music store does not have any instruments
     * yet, an error message is printed.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     */
    private static void getMostExpensive(MusicStore musicStore)
    {
        Instrument expensive = musicStore.findMostExpensive();// HERE find the most expensive instrument in musicStore
        if (expensive == null)
        {
            outputError("there are no instruments in the store yet");
        }
        else
        {
            output("that costs the most", expensive);
        }
    } // getMostExpensive

    /**
     * Gives a further discount to all the instruments in the store.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     * @param in an open input stream
     * @param notInteractive declares that the session is not
     * with the console but likely from redirected input from a file
     */
    private static void giveStoreDiscount(MusicStore musicStore, Scanner in, boolean notInteractive)
    {
        output(systemPrompt() + "Enter the discount percentage (0 <= percentage <= 100): ");
        String pStr = in.nextLine();
        if (notInteractive) echo(pStr);
        double p = getDouble(pStr);
        if (p < 0 || p > 100)
        {
            outputError("Failed to set the discount percentage with the value " + pStr);
        }
        else
        {
            musicStore.setDiscount(p);// HERE add the discount percentage
            output(systemPrompt());
            outputln("Added a discount of " + p + "% to the whole store");
        }
    } // giveStoreDiscount

    /**
     * Gives a further markup to all the instruments in the store.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     * @param in an open input stream
     * @param notInteractive declars that the session is not
     * with the console but likely from redirected input from a file
     */
    private static void giveStoreMarkup(MusicStore musicStore, Scanner in, boolean notInteractive)
    {
        output(systemPrompt() + "Enter the markup percentage (percentage >= 0): ");
        String pStr = in.nextLine();
        if (notInteractive) echo(pStr);
        double p = getDouble(pStr);
        if (p < 0)
        {
            outputError("Failed to set the markup percentage with the value " + pStr);
        }
        else
        {
            musicStore.setMarkup(p);// HERE add the markup percentage
            output(systemPrompt());
            outputln("Added a markup of " + p + "% to the whole store");
        }
    } // giveStoreMarkup

    /**
     * Prints the total number of instruments in the store.
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     */
    private static void numberOfInstruments(MusicStore musicStore)
    {
        output(systemPrompt());
        output("The total number of instruments in the store is ");
        int n = musicStore.getTotalInstruments();// HERE get the total number of instruments in store
        outputln(String.valueOf(n));

    } // numberOfInstruments

    /**
     * Returns a positive double from <code>aLine</code>.
     * If there is an error, the error is printed and -1 is returned.
     * <p> Precondition: aLine is not null. </p>
     * @param aLine the string from which the double will be read
     * @return the double obtained from aLine or -1 if an error was encountered
     */
    private static double getDouble(String aLine)
    {
        boolean foundError = false;
        double x = 0; // to keep the compiler happy
        try (Scanner aLineScanner = new Scanner(aLine))
        {
            x = aLineScanner.nextDouble();
        }
        catch (InputMismatchException e) // when the input is not a valid double
        {
            outputError("\"" + aLine + "\" should be a valid decimal");
            foundError = true;
        }
        if (!foundError && (x < 0))
        {
            outputError("\"" + aLine + "\" cannot be negative to be valid");
            foundError = true;
        }
        if (foundError) x = -1;
        return x;
    } // getDouble

    /**
     * Outputs all the instruments of the music store. The list of instruments
     * is enclosed in square brackets [ ]
     * <p> Precondition: the musicStore is not null. </p>
     * @param musicStore the musicStore of Instruments
     */
    private static void printOutStore(MusicStore musicStore)
    {
        outputln(systemPrompt() + musicStore.toString());
    } // printOutStore

    /**
     * Outputs a string with a few stars before and after.
     * @param msg the error message
     */
    private static void outputError(String msg)
    {
        outputln("->** " + msg + " **<-");
    } // outputError

    /**
     * Outputs the instrument; however, if instrument is null, an error is printed.
     * @param what gives what was done to the instrument, e.g. "added", "removed"
     * @param instrument whose information will be printed.
     */
    private static void output(String what, Instrument instrument)
    {
        if (instrument == null)
        {
            // the instrument is not in the music store: report it and return
            outputError("No match for this item id in our music store records");
            return;
        }
        outputln(systemPrompt() + "The instrument " + what + ":");
        outputln("\titem id: " + instrument.getItemId());
        outputln("\ttype: \"" + instrument.getType() + "\"");
        outputln("\tbrand-model: \"" + instrument.getBrandNModel() + "\"");
        outputln(String.format("\tprice: $" + PRECISION_2DIGITS,instrument.calculatePrice()));
    } // output

    /**
     * Outputs the string str. A typical str would be a command that needs to be echoed.
     * @param str string to print
     */
    private static void echo(String str)
    {
        outputln(str);
    } // echo

    /**
     * Outputs to standard output the string str.
     * @param str string to print
     */
    private static void output(String str)
    {
        System.out.print(str);
    } // output

    /**
     * Outputs to standard output the string str and then follows a carriage return.
     * @param str string to print
     */
    private static void outputln(String str)
    {
        System.out.println(str);
    } // outputln

    /**
     * Outputs a prompt to show that it's the system printing.
     * @return the prompt used by the system for the user to read.
     */
    private static String systemPrompt()
    {
        return "-> ";
    } // systemPrompt
}
