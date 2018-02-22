package week1;

public class wk1 {
    public static String msg = "Error: \n";

    public static void main(String[] args) {
        ck();
        System.out.println(msg);
    }

    public static boolean ck(){
        msg += "hahahahaha";
        return true;
    }
}
