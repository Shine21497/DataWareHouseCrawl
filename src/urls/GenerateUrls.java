package urls;

import dao.Dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

public class GenerateUrls {
    public static void main(String[] args) {
        Dao dao=new Dao();
        Vector ids=dao.getAllProductIds();
        int alllines=ids.size();
        int smallfile=0;
        try{
            File file=new File("F:\\datawarehouseurls\\url7_"+smallfile);
            File nextfile=null;
            if(!file.exists())
                file.createNewFile();
            PrintWriter pw=new PrintWriter(new FileOutputStream(file));
            int i=0;
            int linsinafile=0;
            while(i<ids.size())
            {
                if(((String)ids.get(i)).length()<10)
                    System.out.println(ids.get(i)+":");
                else
                {
                    String url="https://www.amazon.com/dp/"+ids.get(i)+"\r\n";
                    pw.append(url);
                    pw.flush();
                    linsinafile++;
                }

                i++;
                if(linsinafile==10000 && (alllines-i)>5000 )
                {
                    smallfile++;
                    nextfile=new File("F:\\datawarehouseurls\\url7_"+smallfile);
                    if(!nextfile.exists())
                        nextfile.createNewFile();
                    pw=new PrintWriter(new FileOutputStream(nextfile));
                    linsinafile=0;
                }

            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
