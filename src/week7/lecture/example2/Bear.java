package week7.lecture.example2;

public class Bear extends Animal {
    private int paw;
    public Bear(){}
    public Bear(String name)
    {
        super(name);
        this.paw = 4;
    }
    public String toString()
    {
        return super.toString() + " " + paw;
    }
}
