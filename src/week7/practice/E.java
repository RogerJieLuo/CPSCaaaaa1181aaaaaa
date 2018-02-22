package week7.practice;

public class E {
    public static void main(String[] args) {
        Print p = new Print();
        p.printLowerCase();
        p.test('a');
        InterfaceA a = new Print();
        a.printLowerCase();
        a.test('A');

        System.out.println(a.method(100));
    }
}
