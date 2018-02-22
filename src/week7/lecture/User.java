package week7.lecture;

public class User {
    public static void main(String[] args) {
        User user = new User();
        CallableBack callback = new CallableBackImplementation();
        user.register(callback);
    }

    public static void register(CallableBack callback)
    {
        callback.methodToCallBack(10);
    }
}
