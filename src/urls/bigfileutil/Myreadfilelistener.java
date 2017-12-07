package urls.bigfileutil;

import dao.Dao;

import java.util.List;

public class Myreadfilelistener extends ReaderFileListener {
    public void output(List<String> stringList) throws Exception
    {
        Dao dao=new Dao();
        int i=0;
        while(i<stringList.size())
        {
            String line=stringList.get(i);
            //String[] attributes=line.split("\\t");
            int endindex=line.indexOf("\t");
            String sub=null;
            if(endindex>0)
                sub=line.substring(0,endindex);
            if(sub!=null&&sub!=""&&sub.length()<18)
            {

                try {
                    //System.out.println(sub);
                    dao.insertProduct(sub);
                }catch (Exception e)
                {
                    System.out.println("toolong");
                }

            }
            i++;

        }
    }
    public Myreadfilelistener(String encode)
    {
        this.setEncode(encode);
    }
}
