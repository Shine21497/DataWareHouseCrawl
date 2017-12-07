package helper;

import javafx.print.Printer;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class PathHelper {
    private static File configfile;
    private static Scanner scanner;
    private static JSONObject jsonObject;
    static {
        try {
            configfile = new File("configfile");
            scanner = new Scanner(new FileInputStream(configfile));
            String temp="" ;
            while(scanner.hasNext())
            {
                temp=temp+scanner.nextLine();
            }
            jsonObject=new JSONObject(temp);

        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    public static String getCrawlagainPath()
    {
       return jsonObject.getString("crawlagain");
    }
    public static String getCrawledPath()
    {
        return jsonObject.getString("crawled");
    }
    public static String getlogPath()
    {
        return jsonObject.getString("log");
    }
    public static String geturlPath()
    {
        return jsonObject.getString("url");
    }
    public static String getdatabasename()
    {
        return jsonObject.getString("databasename");
    }
}
