package spider;


import dao.Dao;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

public class MysqlPipline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {

        MovieEntity movieEntity=(MovieEntity)resultItems.get("movie");
        if(movieEntity!=null) {
            if (movieEntity.getMovieName().equals(""))
            {
                String uncrawledurl=resultItems.get("uncrawledurl");
                if(uncrawledurl!=null)
                    Dao.getInstance().insertUncrawledURL(uncrawledurl);
            }
            else
            {
                Dao.getInstance().insertMovieInfo(movieEntity);
            }
            System.out.println(movieEntity.toString());

        }

    }
}
