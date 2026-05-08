package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;

public class fas_bill_transModi_servlet extends HttpServlet {
    
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	                response.setHeader("cache-control","no-cache");
	                response.setContentType(CONTENT_TYPE);
	                PrintWriter out = response.getWriter();
	                Connection con=null;
	                ResultSet rs=null,rs2,rs3,rs4,rs5=null,rs6=null,rs7=null;
                  	PreparedStatement ps,ps1,ps2,ps3,ps4,ps5=null,ps6=null,ps7=null;
                    /************** Initialising all the field values ***************/
                  	 String update_user="";
                     Timestamp ts=null;
                    String strcmd="";String xml="";
                    String  strmajor,strminor,strsubcode,strsubdesc,sql,sql1,sql4,sql5="";
                    int count=0;
                 /*********** connection establishment****************/
         try{
                     ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                     String ConnectionString="";
                     String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                     String strdsn=rs1.getString("Config.DSN");
                     String strhostname=rs1.getString("Config.HOST_NAME");
                     String strportno=rs1.getString("Config.PORT_NUMBER");
                     String strsid=rs1.getString("Config.SID");
                     String strdbusername=rs1.getString("Config.USER_NAME");
                     String strdbpassword=rs1.getString("Config.PASSWORD");                                      
                     ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                     Class.forName(strDriver.trim());
                     con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
         }catch(Exception e){
                 System.out.println("Exception in connection...."+e);
         } 
         try{
                   HttpSession session=request.getSession(false);
                   if(session==null){
                       System.out.println(request.getContextPath()+"/index.jsp");
                       response.sendRedirect(request.getContextPath()+"/index.jsp");
                       return;
                   }
                   System.out.println(session);
                   update_user=(String)session.getAttribute("UserId");
                   long l=System.currentTimeMillis();
                   ts=new Timestamp(l);
                   
         }catch(Exception e){
               System.out.println("Redirect Error :"+e);
         }
         try{
                          strcmd =  request.getParameter("command");     
                          System.out.println("Command passed via the button pressed : " + strcmd);
         }catch(Exception e3){
                    e3.printStackTrace();
         }    
        
         
                 xml="<response>";
                if(strcmd.equalsIgnoreCase("loadMinorType"))
                    {
                           xml=xml+"<command>loadMinorType</command>";
                    try
                    {
                      strmajor=request.getParameter("MajorCode1");
                      System.out.println("major code selected:"+strmajor);
                       sql="select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST ";
                       if(!strmajor.equals("All"))
                           sql=sql+" where BILL_MAJOR_TYPE_CODE=? ";
                        ps=con.prepareStatement(sql);
                        if(!strmajor.equals("All"))
                            ps.setInt(1,Integer.parseInt(strmajor));
                        rs=ps.executeQuery();
                        while(rs.next())
                        {
                              xml=xml+"<option><desc>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</desc><id>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</id></option>";
                              count++;
                        } // while close
                        if(count>0)
                            xml=xml+"<flag>success</flag>";
                        else
                            xml=xml+"<flag>nodata</flag>";
                        } //try close
                        catch(Exception e)
                        {
                                xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                System.out.println(e);
                        }
                    }
                   else if(strcmd.equalsIgnoreCase("MajorMinorSub"))
                                     {
                                            xml=xml+"<command>MajorMinorSub</command>";
                                     try
                                     {
                                    	 System.out.println("------------------MajorMinorSub    ---------------------");
                                        strmajor=request.getParameter("MajorCode1");
                                        System.out.println("major code selected:"+strmajor);
                                        //strmajor1=Integer.parseInt(request.getParameter("MajorCode1"));
                                        //System.out.println(strmajor1);
                                        
                                        strminor=request.getParameter("MinorCode1");
                                        System.out.println("minor code selected:"+strminor);
                                        //strminor1=Integer.parseInt(request.getParameter("MinorCode1"));
                                        if(strminor.equals("")){
                                   		 strminor="All";                                               		 
                                   	 }
                                                 if(strmajor.equalsIgnoreCase("All") && strminor.equalsIgnoreCase("All"))
                                                 {
                                                     System.out.println("from here both all");
                                                     //sql1="select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST order by BILL_MAJOR_TYPE_CODE";
                                                     sql1="SELECT aa.BILL_MAJOR_TYPE_CODE AS BILL_MAJOR_TYPE_CODE, " +
                                                     "  aa.BILL_MINOR_TYPE_CODE      AS BILL_MINOR_TYPE_CODE, " +
                                                     "  aa.BILL_MINOR_TYPE_DESC      AS BILL_MINOR_TYPE_DESC, " +
                                                     "  aa.STATUS                    AS ASTATUS, " +
                                                     "  bb.STATUS                    AS STATUS " +
                                                     "FROM " +
                                                     "  (SELECT BILL_MAJOR_TYPE_CODE, " +
                                                     "    BILL_MINOR_TYPE_CODE, " +
                                                     "    BILL_MINOR_TYPE_DESC, " +
                                                     "    STATUS " +
                                                     "  FROM FAS_BILL_MINOR_TYPES_MST " +
                                                     "  ORDER BY BILL_MAJOR_TYPE_CODE " +
                                                     "  )aa " +
                                                     "LEFT OUTER JOIN " +
                                                     "  ( SELECT BILL_MAJOR_TYPE_CODE,STATUS FROM FAS_BILL_MAJOR_TYPES " +
                                                     "  )bb " +
                                                     "ON aa.BILL_MAJOR_TYPE_CODE=bb.BILL_MAJOR_TYPE_CODE";
                                                 }
                                                 else if((!strmajor.equals("All") && strminor.equals("All"))||(!strmajor.equals("All") && strminor.equals(" ")))
                                                 {
                                                     System.out.println("Maj ---  Min All");
                                                     //sql1="select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE= "+strmajor+ "order by BILL_MAJOR_TYPE_CODE";
                                                     sql1="SELECT aa.BILL_MAJOR_TYPE_CODE AS BILL_MAJOR_TYPE_CODE, " +
                                                     "  aa.BILL_MINOR_TYPE_CODE      AS BILL_MINOR_TYPE_CODE, " +
                                                     "  aa.BILL_MINOR_TYPE_DESC      AS BILL_MINOR_TYPE_DESC, " +
                                                     "  aa.STATUS                    AS ASTATUS, " +
                                                     "  bb.STATUS                    AS STATUS " +
                                                     "FROM " +
                                                     "  (SELECT BILL_MAJOR_TYPE_CODE, " +
                                                     "    BILL_MINOR_TYPE_CODE, " +
                                                     "    BILL_MINOR_TYPE_DESC, " +
                                                     "    STATUS " +
                                                     "  FROM FAS_BILL_MINOR_TYPES_MST " +
                                                     "  where BILL_MAJOR_TYPE_CODE= "+strmajor+
                                                     "  ORDER BY BILL_MAJOR_TYPE_CODE " +
                                                     "  )aa " +
                                                     "LEFT OUTER JOIN " +
                                                     "  ( SELECT BILL_MAJOR_TYPE_CODE,STATUS FROM FAS_BILL_MAJOR_TYPES " +
                                                     "  )bb " +
                                                     "ON aa.BILL_MAJOR_TYPE_CODE=bb.BILL_MAJOR_TYPE_CODE";
                                                 }
                                                 else if((strmajor.equals("All") && !strminor.equals("All"))||(strmajor.equals("All") && strminor.equals(" ")))
                                                 {
                                                	 
                                                     System.out.println("Maj  All    Min ---");
                                                     //sql1="select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MINOR_TYPE_CODE= "+strminor+ "order by BILL_MAJOR_TYPE_CODE";
                                                     sql1="SELECT aa.BILL_MAJOR_TYPE_CODE AS BILL_MAJOR_TYPE_CODE, " +
                                                     "  aa.BILL_MINOR_TYPE_CODE      AS BILL_MINOR_TYPE_CODE, " +
                                                     "  aa.BILL_MINOR_TYPE_DESC      AS BILL_MINOR_TYPE_DESC, " +
                                                     "  aa.STATUS                    AS ASTATUS, " +
                                                     "  bb.STATUS                    AS STATUS " +
                                                     "FROM " +
                                                     "  (SELECT BILL_MAJOR_TYPE_CODE, " +
                                                     "    BILL_MINOR_TYPE_CODE, " +
                                                     "    BILL_MINOR_TYPE_DESC, " +
                                                     "    STATUS " +
                                                     "  FROM FAS_BILL_MINOR_TYPES_MST " +
                                                     "  where BILL_MINOR_TYPE_CODE= "+strminor+
                                                     "  ORDER BY BILL_MAJOR_TYPE_CODE " +
                                                     "  )aa " +
                                                     "LEFT OUTER JOIN " +
                                                     "  ( SELECT BILL_MAJOR_TYPE_CODE,STATUS FROM FAS_BILL_MAJOR_TYPES " +
                                                     "  )bb " +
                                                     "ON aa.BILL_MAJOR_TYPE_CODE=bb.BILL_MAJOR_TYPE_CODE";
                                                 }
                                                 else
                                                 {
                                                    System.out.println("Maj ---- Min ----");
                                                     //sql1="select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE= "+strmajor+ "AND BILL_MINOR_TYPE_CODE="+strminor+ "order by BILL_MAJOR_TYPE_CODE";
                                                    sql1="SELECT aa.BILL_MAJOR_TYPE_CODE AS BILL_MAJOR_TYPE_CODE, " +
                                                    "  aa.BILL_MINOR_TYPE_CODE      AS BILL_MINOR_TYPE_CODE, " +
                                                    "  aa.BILL_MINOR_TYPE_DESC      AS BILL_MINOR_TYPE_DESC, " +
                                                    "  aa.STATUS                    AS ASTATUS, " +
                                                    "  bb.STATUS                    AS STATUS " +
                                                    "FROM " +
                                                    "  (SELECT BILL_MAJOR_TYPE_CODE, " +
                                                    "    BILL_MINOR_TYPE_CODE, " +
                                                    "    BILL_MINOR_TYPE_DESC, " +
                                                    "    STATUS " +
                                                    "  FROM FAS_BILL_MINOR_TYPES_MST " +
                                                    "  where BILL_MAJOR_TYPE_CODE= "+strmajor+
                                                    "  AND BILL_MINOR_TYPE_CODE="+strminor+
                                                    "  ORDER BY BILL_MAJOR_TYPE_CODE " +
                                                    "  )aa " +
                                                    "LEFT OUTER JOIN " +
                                                    "  ( SELECT BILL_MAJOR_TYPE_CODE,STATUS FROM FAS_BILL_MAJOR_TYPES " +
                                                    "  )bb " +
                                                    "ON aa.BILL_MAJOR_TYPE_CODE=bb.BILL_MAJOR_TYPE_CODE";
                                                }
                                                 System.out.println("sql1 "+sql1);
                                          int rec=0;   
                                         ps1=con.prepareStatement(sql1);
                                         rs2=ps1.executeQuery();
                                         int subCount = 0;
                                         while(rs2.next()) {
                                                    xml=xml+"<subcoderec>";
                                                    xml=xml+"<subcode>"+rs2.getInt("BILL_MINOR_TYPE_CODE")+"</subcode>";
                                                    xml=xml+"<subdesc>"+rs2.getString("BILL_MINOR_TYPE_DESC")+"</subdesc>";
                                                   // xml=xml+"<astatus>"+rs2.getString("ASTATUS")+"</astatus>";
                                                    ps2=con.prepareStatement("select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE=?");
                                                    ps2.setInt(1,rs2.getInt("BILL_MAJOR_TYPE_CODE"));
                                                    rs3=ps2.executeQuery();
                                                    while(rs3.next()){
                                                           xml=xml+"<majordesc>"+rs3.getString("BILL_MAJOR_TYPE_DESC")+"</majordesc>";                                                                     
                                                           xml=xml+"<majorcode>"+rs2.getInt("BILL_MAJOR_TYPE_CODE")+"</majorcode>";                                                                     
                                                           //ps2.close();
                                                           //rs3.close();
                                                    }                                                 
                                                    //ps3=con.prepareStatement("select BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and  BILL_MINOR_TYPE_CODE=? ");                                                        
                                                    ps3=con.prepareStatement("select BILL_SUB_TYPE_DESC,BILL_SUB_TYPE_CODE,STATUS from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?");
                                                    ps3.setInt(1,rs2.getInt("BILL_MAJOR_TYPE_CODE"));
                                                    ps3.setInt(2,rs2.getInt("BILL_MINOR_TYPE_CODE"));
                                                    rs4=ps3.executeQuery();
                                                    while(rs4.next()){                                                        	 	
                                                           xml=xml+"<minordesc>"+rs4.getString("BILL_SUB_TYPE_DESC")+"</minordesc>";                                                                
                                                           xml=xml+"<minorcode>"+rs4.getInt("BILL_SUB_TYPE_CODE")+"</minorcode>";
                                                           xml=xml+"<substatus>"+rs4.getString("STATUS")+"</substatus>";
                                                           //ps3.close();
                                                           //rs4.close();
                                                           subCount++;
                                                    }
                                                    if(subCount==0){
                                                        xml=xml+"<minordesc>"+"-"+"</minordesc>";                                                                
                                                        xml=xml+"<minorcode>"+"0"+"</minorcode>";
                                                        xml=xml+"<substatus>L</substatus>";
                                                    }
                                                    subCount=0;
                                                    xml=xml+"<status>"+rs2.getString("ASTATUS")+"</status>";
                                           
                                                    xml=xml+"</subcoderec>";
                                                    rec++;
                                                    System.out.println("Number of Recoreds: "+rec);
                                             }
                                            // System.out.println(rec);
                                             if(rec>0){
                                                    xml=xml+"<flag>success</flag>";      
                                             }else if(rec==0){
                                                 xml=xml+"<flag>nodata</flag>";      
                                             }
                                             }catch(Exception e){
                                                     xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                     System.out.println(e);
                                             }
                                     }else if(strcmd.equalsIgnoreCase("delrec")){
                                                     xml=xml+"<command>Delete</command>";
                                                     System.out.println("/**********Delete*************/");
                                                     try {
                                                        int strsub = 0;
                                                        strmajor=request.getParameter("major_code1");                                                                   
                                                        strminor=request.getParameter("minor_code1");
                                                        String minrdesc=request.getParameter("minr_code_desc");      
                                                        strsub=Integer.parseInt(request.getParameter("sub_code1"));  
                                                                   
                                                        //sql4="DELETE FROM FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=? AND BILL_SUB_TYPE_CODE=?";
                                                        sql4="UPDATE FAS_BILL_SUB_TYPES SET STATUS='C' where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=? AND BILL_SUB_TYPE_CODE=?";
                                                        ps4=con.prepareStatement(sql4);
                                                        ps4.setInt(1,Integer.parseInt(strmajor));
                                                        ps4.setInt(2,Integer.parseInt(strminor));
                                                        ps4.setInt(3, strsub);                                                                   
                                                        ps4.executeUpdate();
                                                        ps4.close();
                                                                   
                                                        sql4="select * from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=? AND STATUS='L'";
                                                        ps4=con.prepareStatement(sql4);
                                                        ps4.setInt(1,Integer.parseInt(strmajor));
                                                        ps4.setInt(2,Integer.parseInt(strminor));                                                                    
                                                        rs5=ps4.executeQuery();
                                                        int totrec=0;
                                                        String subtype_avil="";
                                                        while(rs5.next()){
                                                                 totrec++;    
                                                        }
                                                        if(totrec>0){
                                                               subtype_avil="Y";
                                                        }else{
                                                             subtype_avil="N";
                                                        }
                                                        ps4.close();
                                                        rs5.close();
                                                                   
                                                        if(subtype_avil.equalsIgnoreCase("N")){
                                                               sql4="UPDATE FAS_BILL_MINOR_TYPES_MST SET STATUS='C',SUB_TYPE_APPLICABLE=? where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=?";
                                                               ps4=con.prepareStatement(sql4);
                                                               ps4.setString(1, subtype_avil);
                                                               ps4.setInt(2,Integer.parseInt(strmajor));
                                                               ps4.setInt(3,Integer.parseInt(strminor));                                                                    
                                                               ps4.executeUpdate();
                                                               ps4.close();   
                                                        }                                                           
                                                        //System.out.println("The value for Sub Type available is:"+subtype_avil);

                                                        /*sql4="UPDATE FAS_BILL_MINOR_TYPES_MST set SUB_TYPE_APPLICABLE=? where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=? ";
                                                                    ps4=con.prepareStatement(sql4);
                                                                    ps4.setString(1,subtype_avil);
                                                                    ps4.setInt(2,Integer.parseInt(strmajor));
                                                                    ps4.setInt(3,strsub);
                                                                    ps4.executeUpdate();
                                                                    ps4.close();*/                                                         

                                                        xml=xml+"<flag>success</flag>";                                                                    
                                                           
                                                        } //try close
                                                        catch(Exception e){
                                                                  xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                                  System.out.println(e);
                                                        }
                                                    }
                                                               else if(strcmd.equalsIgnoreCase("update"))
                                                                                 {
                                                                                        xml=xml+"<command>Update</command>";
                                                                                        System.out.println("/**********Update*************/");
                                                                                 try 
                                                                                 {
                                                                                	 int subcc=0,sudcc1=1;
                                                                                     strmajor=request.getParameter("major_code1");
                                                                                     System.out.println("major code selected:"+strmajor);
                                                                                     strminor=request.getParameter("minor_code1");
                                                                                     System.out.println("minor code selected:"+strminor);  
                                                                                     String minrdesc=request.getParameter("minr_code_desc");    
                                                                                     System.out.println("minrdesc     "+minrdesc);
                                                                                     strsubcode=request.getParameter("sub_code1");
                                                                                     System.out.println("Sub code selected:"+strsubcode);
                                                                                     strsubdesc=request.getParameter("sub_desc1");
                                                                                     System.out.println("Sub Desc selected:"+strsubdesc); 
                                                                                     int subccc=Integer.parseInt(strsubcode);
                                                                                   
                                                                                     if((!strsubdesc.equalsIgnoreCase("-") && (subccc==0))||(!strsubdesc.equalsIgnoreCase(" ")&& (subccc==0))||(!strsubdesc.equalsIgnoreCase("")&& (subccc==0)))
                                                                                     {
                                                                                    	
                                                                                    	 String ss="select max(BILL_SUB_TYPE_CODE) as BILL_SUB_TYPE_CODE  from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and  BILL_MINOR_TYPE_CODE=?";
                                                                                         ps7=con.prepareStatement(ss);
                                                                                         ps7.setInt(1,Integer.parseInt(strmajor));
                                                                                         ps7.setInt(2,Integer.parseInt(strminor));
                                                                                         rs7= ps7.executeQuery();
                                                                                         if(rs7.next()){
                                                                                        	 subcc=rs7.getInt("BILL_SUB_TYPE_CODE");
                                                                                        	 sudcc1=sudcc1+subcc;
                                                                                         }
                                                                                       //  System.out.println("subccc  ,sudcc1   "+subcc+"    "+sudcc1);
                                                                                         ps7.close(); 
                                                                                         
                                                                                         
                                                                                         sql5="update FAS_BILL_MINOR_TYPES_MST set BILL_MINOR_TYPE_DESC=?,SUB_TYPE_APPLICABLE=? where BILL_MAJOR_TYPE_CODE =? AND BILL_MINOR_TYPE_CODE=? ";
                                                                                         ps5=con.prepareStatement(sql5);
                                                                                         ps5.setString(1,minrdesc);
                                                                                        // ps5.setString(1,strsubdesc);
                                                                                         ps5.setString(2,"Y");
                                                                                         ps5.setInt(3,Integer.parseInt(strmajor));
                                                                                         ps5.setInt(4,Integer.parseInt(strminor));
                                                                                        // ps5.setInt(5,Integer.parseInt(strsubcode));
                                                                                         int ii=ps5.executeUpdate();
                                                                                         ps5.close();
                                                                                        // System.out.println("iiii if FAS_BILL_MINOR_TYPES_MST "+ii);
                                                                                         
                                                                                        // System.out.println("update_user "+update_user);
                                                                                         String sqll="insert into FAS_BILL_SUB_TYPES(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS) values(?,?,?,?,?,?,?)";
                                                                                         ps6=con.prepareStatement(sqll);
                                                                                         ps6.setInt(1,Integer.parseInt(strmajor));
                                                                                         ps6.setInt(2,Integer.parseInt(strminor));
                                                                                         ps6.setInt(3,sudcc1);
                                                                                         ps6.setString(4,strsubdesc);
                                                                                         ps6.setString(5,update_user);
                                                                                         ps6.setTimestamp(6,ts); 
                                                                                         ps6.setString(7,"L");    
                                                                                        
                                                                                         int iii=ps6.executeUpdate();
                                                                                         ps6.close();
                                                                                        // System.out.println("iiiii  sqlll insert FAS_BILL_SUB_TYPES  == "+iii);
                                                                                         
                                                                                         xml=xml+"<flag>success</flag>";
                                                                                         System.out.println("Suceesully updated if ");
                                                                                         xml=xml+"<minrdesc>"+minrdesc+"</minrdesc>";
                                                                                         xml=xml+"<subcode>"+strsubcode+"</subcode>";
                                                                                         xml=xml+"<subdesc>"+strsubdesc+"</subdesc>";
                                                                                     }else{
                                                                  
                                                                                     sql5="update FAS_BILL_SUB_TYPES set BILL_SUB_TYPE_DESC=? where BILL_MAJOR_TYPE_CODE =? AND BILL_MINOR_TYPE_CODE=? AND BILL_SUB_TYPE_CODE=?";
                                                                                     ps5=con.prepareStatement(sql5);
                                                                                     ps5.setString(1,strsubdesc);
                                                                                     ps5.setInt(2,Integer.parseInt(strmajor));
                                                                                     ps5.setInt(3,Integer.parseInt(strminor));
                                                                                     ps5.setInt(4,Integer.parseInt(strsubcode));
                                                                                     int ii=ps5.executeUpdate();
                                                                                     ps5.close();
                                                                                    
                                                                                     
                                                                                     String sqll="update FAS_BILL_MINOR_TYPES_MST set BILL_MINOR_TYPE_DESC=? where BILL_MAJOR_TYPE_CODE =? AND BILL_MINOR_TYPE_CODE=? ";
                                                                                     ps6=con.prepareStatement(sqll);
                                                                                     ps6.setString(1,minrdesc);
                                                                                     ps6.setInt(2,Integer.parseInt(strmajor));
                                                                                     ps6.setInt(3,Integer.parseInt(strminor));
                                                                                     int iii=ps6.executeUpdate();
                                                                                     ps6.close();
                                                                                    /* System.out
																							.println("iiiii  sqlll update FAS_BILL_MINOR_TYPES_MST  == "+iii);*/
                                                                                     
                                                                                     xml=xml+"<flag>success</flag>";
                                                                                     System.out.println("Suceesully updated else ");
                                                                                     xml=xml+"<minrdesc>"+minrdesc+"</minrdesc>";
                                                                                     xml=xml+"<subcode>"+strsubcode+"</subcode>";
                                                                                     xml=xml+"<subdesc>"+strsubdesc+"</subdesc>";
                                                                                     }

                                                                                    
                                                                                      
                                                                                     } //try close
                                                                                     catch(Exception e){
                                                                                    	 System.out
																								.println("catch block");
                                                                                           xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                                                           System.out.println(e);
                                                                                     }
                                                                                     }
                xml=xml+"</response>";
                System.out.println(xml);
                out.println(xml);
                        } //doGet Close
} //Class Close
