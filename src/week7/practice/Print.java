package week7.practice;

public class Print implements InterfaceA, InterfaceB {

    @Override
    public void printLowerCase(){
        System.out.println("asdf");
    }

    @Override
    public void printUpperCase(){
        System.out.println("ASDF");
    }

    @Override
    public int method(int n) {
        if(n <= 1)
            return 1;
        return n + method( n - 1);
    }
}
