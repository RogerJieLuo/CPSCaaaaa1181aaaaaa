package week7.practice;

public interface InterfaceA
{
    int size = 26;
    void printLowerCase();

    default void test(char a)
    {
        for(int i = 0 ; i < size; i++)
        {
            System.out.println((char)(a + i));
        }
    }

    int method(int n);
}
