package dao;

import helper.PathHelper;
import spider.MovieEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Vector;

public class Dao extends BaseDao {

    private static final Dao dao;
    

    static {
        dao = new Dao();
    }

    public static Dao getInstance() {
        return dao;
    }

    // 相册
   /* public Vector selectAlbum(int id) {
        return super.selectOnlyNote("select * from tb_album where id=" + id);
    }

    public Vector selectAlbu
    ms(int fatherId) {
        return super.selectSomeNote("select * from tb_album where father_id=" + fatherId);
    }*/
public String getIdByName(String name,  String IdName){
	try{
		Connection conn =JDBC.getConnection();
		String sql="select "+IdName+" from my_order left outer join user on my_order.user_id=user.user_id  where user_name=?";
		System.out.println(name);
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,name);

		return (String) super.selectOnlyValue(pstmt).toString();
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
}
    public Vector selectUser(String username,String password)
    {	
    	try
    	{
    		Connection conn = JDBC.getConnection();
    		String sql="select * from user where user_name =?and password =?";
    		PreparedStatement pstmt=conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    		pstmt.setString(2,password);
    		return  super.selectOnlyNote(pstmt);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    		return null;
		}
    }
	public Vector selectProxy()
	{
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="select * from "+PathHelper.getdatabasename();
			PreparedStatement pstmt=conn.prepareStatement(sql);
			return  super.selectSomeNote(pstmt);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
    
    //changed
    public boolean insertUser(String username, String password,String phone,String area)
    {
    	try
    	{
    		Connection conn = JDBC.getConnection();
    		String sql="insert into user(user_name,password,tel_num,area) values(?,?,?,?)";
    		PreparedStatement pstmt=conn.prepareStatement(sql);
    		pstmt.setString(1, username);
    		pstmt.setString(2, password);
    		pstmt.setString(3, phone);
    		pstmt.setString(4, area);
    		return  super.longHaul(pstmt,conn);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		return false;
		}
 
    
    }
	public boolean insertreviewData(String productid, String userid,String profilename,String helpfulness,String score,String reviewtime,String summary)
	{
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="insert into reviewdata(productid,userid,profilename,helpfulness,score,reviewtime,summary) values(?,?,?,?,?,?,?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, productid);
			pstmt.setString(2, userid);
			pstmt.setString(3, profilename);
			pstmt.setString(4, helpfulness);
			pstmt.setString(5, score);
			pstmt.setString(6, reviewtime);
			pstmt.setString(7, summary);
			return  super.longHaul(pstmt,conn);
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}


	}
	public boolean insertMovieInfo(MovieEntity movieEntity)
	{
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="insert into movieinfo(movieid,moviename,director,leadingactors,actors,genres,releasetime,versionurls) values(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, movieEntity.getMovieID());
			pstmt.setString(2, movieEntity.getMovieName());
			pstmt.setString(3, movieEntity.getDirector());
			pstmt.setString(4, movieEntity.getLeadingActors());
			pstmt.setString(5, movieEntity.getActors());
			pstmt.setString(6, movieEntity.getMovieType());
			pstmt.setString(7, movieEntity.getReleaseTime());
            pstmt.setString(8, movieEntity.getVersions());
			return  super.longHaul(pstmt,conn);
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}


	}
	public boolean insertProxy(String ip,int port)
	{
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="insert into "+ PathHelper.getdatabasename()+"(ip,port) values(?,?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, ip);
			pstmt.setInt(2, port);
			return  super.longHaul(pstmt,conn);
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}


	}
	public boolean deleteProxy()
	{
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="DELETE from proxys";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			return  super.longHaul(pstmt,conn);
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}


	}
    public boolean insertUncrawledURL(String uncrawledurl)
    {
        try
        {
            Connection conn = JDBC.getConnection();
            String sql="insert into crawlagainurls(url) values(?)";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, uncrawledurl);
            return  super.longHaul(pstmt,conn);
        }
        catch (Exception e) {
            // TODO: handle exception
            return false;
        }


    }
	public boolean insertProduct(String productid)
	{
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="insert into products_copy7(productid) values(?)";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, productid);
			return  super.longHaul(pstmt,conn);
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}


	}
    public Vector getNutritionInfor(String nutrition_id){
    	try
    	{
    		Connection conn = JDBC.getConnection();
    		String sql="select * from bus_nutrition where nutrition_id=? ";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.parseInt(nutrition_id));
    		return super.selectOnlyNote(pstmt);
    	}
    	catch(Exception e){
    		return null;
    	}
    }
	public Vector getAllProductIds(){
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="select DISTINCT productid from products_copy7";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			return super.selectSomeValue(pstmt);
		}
		catch(Exception e){
			return null;
		}
	}
	public Vector getProxyWithIPandPort(String ip,int port){
		try
		{
			Connection conn = JDBC.getConnection();
			String sql="select * from "+PathHelper.getdatabasename()+" where ip=? and port=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,ip);
			pstmt.setInt(2,port);
			return super.selectSomeNote(pstmt);
		}
		catch(Exception e){
			return null;
		}
	}
    
    public Vector getReoveryInfor(String recovery_id){
    	try{
    		Connection conn = JDBC.getConnection();
    		String sql="select * from bus_recovery where recovery_id=? ";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.parseInt(recovery_id));
    		return super.selectOnlyNote(pstmt);
    	}
    	catch(Exception e){
    		return null;
    	}
    }
 
   
    
    public Vector getIllnessInfor(String illness_id){
    	try{
    		Connection conn = JDBC.getConnection();
    		String sql="select * from bus_illness where illness_id=? ";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.parseInt(illness_id));
    		return super.selectOnlyNote(pstmt);
    	}
    	catch(Exception e){
    		return null;
    	}
    }
    
    public Vector getAllergicInfor(String allergic_id){
    	try{
    		Connection conn = JDBC.getConnection();
    		String sql="select * from bus_allergic where allergic_id=? ";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.parseInt(allergic_id));
    		return super.selectOnlyNote(pstmt);
    	}
    	catch(Exception e){
    		return null;
    	}
    }
    
    public Vector getTalentInfor(String tallent_id){
    	try{
    		Connection conn = JDBC.getConnection();
    		String sql="select * from bus_talent where talent_id=? ";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.parseInt(tallent_id));
    		return super.selectOnlyNote(pstmt);
    	}
    	catch(Exception e){
    		return null;
    	}
    }
    
//    public Vector getSeriousGeneInfor(String serious_gene_id ){
//    	try{
//    		Connection conn = JDBC.getConnection();
//    		String sql="select * from bus_serious_gene where serious_gene_id=? ";
//    		PreparedStatement pstmt = conn.prepareStatement(sql);
//    		pstmt.setInt(1, Integer.parseInt(serious_gene_id));
//    		return super.selectOnlyNote(pstmt);
//    	}
//    	catch(Exception e){
//    		return null;
//    	}
//    }
    
    
    public Vector getGeneInfor(String gene_id ){
    	try{
    		Connection conn = JDBC.getConnection();
    		String sql="select * from bus_gene where gene_id=? ";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setInt(1, Integer.parseInt(gene_id));
    		return super.selectOnlyNote(pstmt);
    	}
    	catch(Exception e){
    		return null;
    	}
    }
//    public Vector getVigilantGeneInfor(String vigilant_gene_id ){    
//    	try{
//    		Connection conn = JDBC.getConnection();
//    		String sql="select * from bus_vigilant_gene where vigilant_gene_id=? ";
//    		PreparedStatement pstmt = conn.prepareStatement(sql);
//    		pstmt.setInt(1, Integer.parseInt(vigilant_gene_id));
//    		return super.selectOnlyNote(pstmt);
//    	}
//    	catch(Exception e){
//    		return null;
//    	}
//    }
 
    // 照片
    /*public Vector selectPhoto(String photoName) {
        return super.selectOnlyNote("select * from tb_photo where num='" + photoName + "'");
    }*/

   /* public Vector selectPhoto(int albumId, String title, String date,
            char compare, String note) {
        if (date.length() == 0) {
            return selectPhoto(albumId, title, "", note);
        } else {
            return selectPhoto(albumId, title, " date" + compare + "'" + date + "'", note);
        }
    }

    public Vector selectPhoto(int albumId, String title, String startDate,
            String endDate, String note) {
        if (startDate.length() == 0) {
            return selectPhoto(albumId, title, "", note);
        } else {
            return selectPhoto(albumId, title, " date>='" + startDate + "' and date<='" + endDate + "'", note);
        }
    }*/

   /* public Vector selectPhoto(int albumId, String title, String date,
            String note) {
        boolean isSelectAll = true;
        StringBuffer sqlBuffer = new StringBuffer("select * from tb_photo");
        if (albumId != 0) {
            isSelectAll = false;
            sqlBuffer.append(" where album_id in (");
            sqlBuffer.append(albumId);
            for (Iterator it = selectChildAlbumId(albumId).iterator(); it.hasNext();) {
                sqlBuffer.append(",");
                sqlBuffer.append(it.next());
            }
            sqlBuffer.append(")");
        }
        title = title.trim();
        if (title.length() > 0) {
            if (isSelectAll) {
                sqlBuffer.append(" where");
            } else {
                sqlBuffer.append(" and");
            }
            sqlBuffer.append(" title like '%");
            sqlBuffer.append(title.replace(' ', '%'));
            sqlBuffer.append("%'");
        }
        if (date.length() > 0) {
            if (isSelectAll) {
                sqlBuffer.append(" where");
            } else {
                sqlBuffer.append(" and");
            }
            sqlBuffer.append(date);
        }
        note = note.trim();
        if (note.length() > 0) {
            if (isSelectAll) {
                sqlBuffer.append(" where");
            } else {
                sqlBuffer.append(" and");
            }
            sqlBuffer.append(" note like '%");
            sqlBuffer.append(note.replace(' ', '%'));
            sqlBuffer.append("%'");
        }
        return super.selectSomeNote(sqlBuffer.toString());
    }*/

    
}
