package urls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        String a="B0001DCYKU\tA2WJMDOAK0CL5X\tJohn A. Tabone";
        int esa=a.indexOf("\t");
        String sub=a.substring(0,esa);

       String abc="dsfnkjddsf//BY9ADGO6HA";
        System.out.println(abc.substring(abc.length()-10,abc.length()));

        File file=new File("a");
        try {
            file.createNewFile();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
