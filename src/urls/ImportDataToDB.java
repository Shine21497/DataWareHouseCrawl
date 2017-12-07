package urls;

import dao.Dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class ImportDataToDB {
    public static void main(String[] args)
    {
        try {
            File result=new File("F:\\result");
            Scanner scanner=new Scanner(new FileInputStream(result));
            int i=0;
            Dao dao=new Dao();
            while(scanner.hasNext())
            {
                String line=scanner.nextLine();
                String[] attributes=line.split("\\t");
                if(attributes.length>=6)
                {
                    dao.insertProduct(attributes[0]);
                }
                i++;
                if(i%37891==0)
                {
                    System.out.println(i);
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
