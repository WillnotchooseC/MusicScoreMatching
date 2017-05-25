import java.util.*;

public class TestStringBuilder
{
    public static void main(String[] args)
    {
        String s = "";
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++)
        {
            result.append(rand.nextInt(1000));
            result.append(" ");
        }
        System.out.println(result.toString());
    }
}