package week7.lecture.example2;

public class Animal {
    private String name;
    public Animal()
    {
        name = "Unkown";
    }
    public Animal(String name)
    {
        this.name = name;
    }
    public String toString()
    {
        return getClass().getName() + " " + name;
    }
}
