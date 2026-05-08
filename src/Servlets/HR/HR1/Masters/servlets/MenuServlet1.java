package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class MenuServlet1 extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
      private Connection connection=null;
      private Statement statement=null;
      private ResultSet results=null;
      private ResultSet results1=null;
      private ResultSet results2=null;
      private PreparedStatement ps=null;
      private PreparedStatement ps1=null;
      private PreparedStatement ps2=null;
      private PreparedStatement ps3=null;
	 int strORDER_SEQ_NO;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try
                  {
        	LoadDriver driver=new LoadDriver();
        	connection=driver.getConnection();

              try
              {
                statement=connection.createStatement();
                connection.clearWarnings();
              }
              catch(SQLException e)
              {
                  System.out.println("Exception in creating statement:"+e);
              }          
           }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {   System.out.println("servlet called");
        String strCommand = ""; 
            String xml="";
            response.setContentType("text/xml");
            PrintWriter pw=response.getWriter();    
            response.setHeader("Cache-Control","no-cache");
            HttpSession session = request.getSession(false);
    		try {
    			if (session == null) {
    				System.out.println(request.getContextPath() + "/index.jsp");
    				response.sendRedirect(request.getContextPath() + "/index.jsp");

    			}
    			System.out.println(session);

    		} catch (Exception e) {
    			System.out.println("Redirect Error :" + e);
    		}

    		String userid = (String) session.getAttribute("UserId");
    		System.out.println("session id is:" + userid);
    		String updatedby = (String) session.getAttribute("UserId");
    		String Remote_host="";
    		Remote_host=request.getRemoteHost();
    		java.sql.Timestamp ts = null;
    		long l = System.currentTimeMillis();
    		ts = new Timestamp(l);

            try
            {
              strCommand = request.getParameter("command");      
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
            
          if(strCommand.equals("Load"))
                {
                String sxml="<response><command>Load</command>";
                String strMajor=request.getParameter("Major");
                String strMinor=request.getParameter("Minor");
                String strSub=request.getParameter("Sub");
              String strMenuCat=request.getParameter("MenuCat");
              
                 try
                {
                 
                  String sql="SELECT MENU_ID,MENU_DESC,MENU_SHORT_DESC,FILE_PATH,Order_Seq_No,Menu_Category,Category_Seq_No FROM SEC_MST_INTRANET_MENUS WHERE MAJOR_SYSTEM_ID=? AND MINOR_SYSTEM_ID=? AND SUB_SYSTEM_ID=? order by menu_id";
                  try
                 {
                 ps=connection.prepareStatement(sql);
                 ps.setString(1,strMajor);
                 ps.setString(2,strMinor);
                 ps.setString(3,strSub);
                   //  ps.setString(4,strMenuCat);
                 
                results=ps.executeQuery();
                int i=0;
        
                 while(results.next())
                {
                i++;
                
                    xml=xml+"<Menucode>" +results.getString("Menu_Id") + "</Menucode><Menudesc>" +results.getString("Menu_Desc") + "</Menudesc>";
                      xml=xml+"<MenuSDesc>" +results.getString("MENU_SHORT_DESC") + "</MenuSDesc><Menupath>" +results.getString("FILE_PATH") + "</Menupath>";
                      xml=xml+"<MenuCategory>" +results.getString("Menu_Category") +"</MenuCategory><MenuCatSeq>"+results.getInt("Category_Seq_No") +"</MenuCatSeq><MenuOrderSeq>"+results.getInt("Order_Seq_No") +"</MenuOrderSeq>";
                }
                if(i==0) {
                    xml=sxml+"<flag>NoRecord</flag>";
                }
                else {
                    xml=sxml+"<flag>success</flag>"+xml;
                }
                 }catch(Exception ae){System.out.println("Exception is IN select query:" +ae);}
                  
                ps.close();
              }
              
            
              catch(Exception e)
              {        
                 System.out.println("exception in the failure of load"+ e);
                xml=sxml+"<flag>failure</flag>";
                
              }
              
              xml=xml+"</response>";
            
          }
           
        else if(strCommand.equalsIgnoreCase("Get"))
            {
           
              xml="<response><command>Get</command>";
                    String strMajor=null;
                    String strMinor=null;
                    String strSub=null;
                    String strMenuid=null;
          
                    try
                    {   
                        strMajor = request.getParameter("Major");
                        strMinor = request.getParameter("Minor");
                        strSub = request.getParameter("SubSys");
                        strMenuid = request.getParameter("Menuid");
                    }
                    catch(Exception e)
                    {
                      e.printStackTrace();
                    }
                    try
                    {   
                    
                      String sql3="Select Menu_Id from sec_mst_intranet_menus where Major_System_Id=? And Minor_System_Id=? And Sub_System_Id=? And Menu_Id=?  order by menu_id";
                        ps1=connection.prepareStatement(sql3);
                        ps1.setString(1,strMajor);
                        ps1.setString(2,strMinor);
                        ps1.setString(3,strSub);
                        ps1.setString(4,strMenuid);
                            results1=ps1.executeQuery();
                            int j=0;
                            while(results1.next())
                            {
                              j++;
                            }
                            if(j==0)
                                xml=xml+"<flag>failure</flag>";
                            else     
                               xml=xml+"<flag>success</flag>";
                            
                        
                        }
                        catch(Exception e)
                        {
                        
                        System.out.println("error in the get of the menus" + e);
                        }
                        xml=xml+"</response>";
                        
                      }
                       
                       
        else if(strCommand.equals("check"))
                      {
                    String strMajor=null;
                    String strMinor=null;
                    String strSub=null;
                    String strMenuid=null;
                    String menudesc=null;
                     
                     String  sxml="<response><command>check</command>";
                    strMajor = request.getParameter("Major");
                    strMinor = request.getParameter("Minor");
                    strSub = request.getParameter("SubSys");
                    strMenuid = request.getParameter("Menuid");
                    menudesc=request.getParameter("MenuDesc");
                    menudesc = menudesc.toUpperCase();

               
                    System.out.println("hia "+strMenuid+menudesc);
                    int d=0;
                       try
                      {
                       
                        String sql="SELECT *  FROM SEC_MST_INTRANET_MENUS WHERE MAJOR_SYSTEM_ID='"+strMajor+"' AND MINOR_SYSTEM_ID='"+strMinor+"' AND SUB_SYSTEM_ID='"+strSub+"' AND MENU_ID='"+strMenuid+"'  order by menu_id ";
                        try
                       {
                           ps=connection.prepareStatement(sql);
                          /* ps.setString(1,strMajor);
                           ps.setString(2,strMinor);
                           ps.setString(3,strSub);
                           ps.setString(4,strMenuid);*/
                          
                       
                      results=ps.executeQuery();
                          int i=0;
                       while(results.next())
                      { i++;
                          
                      }
                      
                           sql="Select MENU_DESC from sec_mst_intranet_menus where Major_System_Id=? And Minor_System_Id=? And Sub_System_Id=? And upper(MENU_DESC)=?  order by menu_id";
                           ps1=connection.prepareStatement(sql);
                           ps1.setString(1,strMajor);
                           ps1.setString(2,strMinor);
                           ps1.setString(3,strSub);
                           ps1.setString(4,menudesc);
                               results1=ps1.executeQuery();
                              
                               while(results1.next())
                               {
                                 d++;
                               }
                               System.out.println("d:"+d);
                               System.out.println("i:"+i);
                           if((d==0)&&(i==0))
                               xml=sxml+"<flag>success</flag>";
                      if(i>0){
                          xml=sxml+"<flag>failure</flag>";
                             }
                      if(d>0)
                      
                          xml=sxml+"<flag>failure1</flag>";
                       d=0;
                       i=0;
                       }catch(Exception ae){System.out.println("Exception is in:the check" +ae);
                          
                       }
                        
                      
                    }
                  
                    catch(Exception e)
                    {        
                       System.out.println("exception in the failure of check "+ e);
                      xml=xml+"<flag>failure</flag>";
                      
                    }
                    
                    xml=xml+"</response>";
                  
                }

                       
                       
                        
        else if(strCommand.equalsIgnoreCase("Delete"))
            {
           
              xml="<response><command>Delete</command>";
              
              String strMajor=null;
              String strMinor=null;
              String strSub=null;
              String strMenuid=null;
                
              try
              {   
                  strMajor = request.getParameter("Major");
                  strMinor = request.getParameter("Minor");
                  strSub = request.getParameter("SubSys");
                  strMenuid = request.getParameter("Menuid");
              }
              catch(Exception e)
              {
                e.printStackTrace();
              }
              try
              {          
                  String sql5="delete from SEC_MST_INTRANET_MENUS where Major_System_Id=? And Minor_System_Id=? And Sub_System_Id=? And Menu_Id=?";
                  ps2=connection.prepareStatement(sql5);
                  ps2.setString(1,strMajor);
                  ps2.setString(2,strMinor);
                  ps2.setString(3,strSub);
                  ps2.setString(4,strMenuid);
                  ps2.executeUpdate();
                      xml=xml+"<flag>success</flag>";
              }
              catch(Exception e)
              {
                  xml=xml+"<flag>failure</flag>";
              }
              xml=xml+"</response>";
              
            }
            
        else if(strCommand.equals("Add"))
              {
            String strMajor=null;
            String strMinor=null;
            String strSub=null;
            String strMenuid=null;
              String strMenudesc=null;
              String strMenusdesc=null;
              String strMenufilepath=null;
              
            String strMenuCategory=null;
            int strMenuCategorySeq=0;
            //int strMenuOrderSeq=0;
              
              xml="<response><command>Add</command>";
            strMajor = request.getParameter("Major");
            strMinor = request.getParameter("Minor");
            strSub = request.getParameter("SubSys");
            strMenuid = request.getParameter("Menuid");
            strMenudesc = request.getParameter("MenuDesc");
            strMenusdesc = request.getParameter("MenuSDesc");
            strMenufilepath = request.getParameter("MenuFilePath");
            
            strMenuCategory = request.getParameter("MenuCategory");
            strMenuCategorySeq = Integer.parseInt(request.getParameter("MenuCategorySeq"));
            strORDER_SEQ_NO=Integer.parseInt(request.getParameter("ORDER_SEQ_NO"));
            //strMenuOrderSeq = Integer.parseInt(request.getParameter("MenuOrderSeq"));
            
               try
              {
               
               // String sql="INSERT INTO SEC_MST_INTRANET_MENUS(MAJOR_SYSTEM_ID,MINOR_SYSTEM_ID,SUB_SYSTEM_ID,MENU_ID,MENU_DESC,MENU_SHORT_DESC,FILE_PATH,Order_Seq_No,Menu_Category,Category_Seq_No) VALUES(?,?,?,?,?,?,?,?,?,?)";
                String sql="INSERT INTO SEC_MST_INTRANET_MENUS(MAJOR_SYSTEM_ID,MINOR_SYSTEM_ID,SUB_SYSTEM_ID,MENU_ID,MENU_DESC,MENU_SHORT_DESC,FILE_PATH,Menu_Category,Category_Seq_No,ORDER_SEQ_NO,UPDATED_BY_USER_ID,UPDATED_DATE,IP_ADDRESS) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                try
               {
                   ps=connection.prepareStatement(sql);
                   ps.setString(1,strMajor);
                   ps.setString(2,strMinor);
                   ps.setString(3,strSub);
                   ps.setString(4,strMenuid);
                   ps.setString(5,strMenudesc);
                   ps.setString(6,strMenusdesc);
                   ps.setString(7,strMenufilepath);
                // ps.setInt(8,strMenuOrderSeq);
                   ps.setString(8,strMenuCategory);
                   ps.setInt(9,strMenuCategorySeq);
                   ps.setInt(10,strORDER_SEQ_NO);
                   ps.setString(11,updatedby);
                   ps.setTimestamp(12, ts);
                   ps.setString(13,Remote_host);
                   ps.executeUpdate();
                  xml=xml+"<flag>success</flag>"; 
                  
               }catch(Exception ae){System.out.println("Exception isssssssssss:" +ae);}
            }
            
          
            catch(Exception e)
            {        
               System.out.println("exception in the failure of insert"+ e);
              xml=xml+"<flag>failure</flag>";
              
            }
            
            xml=xml+"</response>";
          
        }
        
     
        else if(strCommand.equalsIgnoreCase("Update"))
            {
           
              xml="<response><command>Update</command>";
              
                String strMajor=null;
                String strMinor=null;
                String strSub=null;
                String strMenuid=null;
                String strMenudesc=null;
                String strMenusdesc=null;
                String strMenufilepath=null;
                  
                String strMenuCategory=null;
                int strMenuCategorySeq=0;
                int strMenuOrderSeq=0;
                
              try
              {   
                  strMajor = request.getParameter("Major");
                  strMinor = request.getParameter("Minor");
                  strSub = request.getParameter("SubSys");
                  strMenuid = request.getParameter("Menuid");
                  strMenudesc = request.getParameter("MenuDesc");
                  strMenusdesc = request.getParameter("MenuSDesc");
                  strMenufilepath = request.getParameter("MenuFilePath");
                  
                  strMenuCategory = request.getParameter("MenuCategory");
                  strMenuCategorySeq = Integer.parseInt(request.getParameter("MenuCategorySeq"));
                  strMenuOrderSeq = Integer.parseInt(request.getParameter("MenuOrderSeq")!=null?request.getParameter("MenuOrderSeq"):"0");
                  
                  
              }
              catch(Exception e)
              {
                e.printStackTrace();
              }
              try
              {          
                  String sql="UPDATE SEC_MST_INTRANET_MENUS SET MENU_DESC=?,MENU_SHORT_DESC=?,FILE_PATH=?,Order_Seq_No=?,Menu_Category=?,Category_Seq_No=?,UPDATED_BY_USER_ID=?, UPDATED_DATE=?,IP_ADDRESS=? where Major_System_Id=? And Minor_System_Id=? AND SUB_SYSTEM_ID=? AND MENU_ID=?";
                  ps=connection.prepareStatement(sql);
                  
                  ps.setString(13,strMenuid);
                  ps.setString(1,strMenudesc);
                  ps.setString(2,strMenusdesc);
                  ps.setString(3,strMenufilepath);
                  
                  ps.setInt(4,strMenuOrderSeq);
                  ps.setString(5,strMenuCategory);
                  ps.setInt(6,strMenuCategorySeq);
                  ps.setString(7,updatedby);
                  ps.setTimestamp(8,ts);
                  ps.setString(9,Remote_host);
                  ps.setString(10,strMajor);
                  ps.setString(11,strMinor);
                  ps.setString(12,strSub);
                 
                  ps.executeUpdate();
                 System.out.println("strMenuOrderSeq:::"+strMenuOrderSeq);
                      xml=xml+"<flag>success</flag>>";
                     
                 
              }
              catch(Exception e)
              {  System.out.println("UPDATE exception is " + e);
                  xml=xml+"<flag>failure</flag>";
              }
              xml=xml+"</response>";
              
            }


            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();
            
           
           
           
    }
    
    
}
/* // code to get max value --  pending
 *                     String sql4="select max(order_seq_no) from sec_mst_intranet_menus where major_system_id=? and minor_system_id=? and sub_system_id=? and menu_category=?";
                    ps3=connection.prepareStatement(sql4);
                    ps3.setString(1,strMajor);
                    ps3.setString(2,strMinor);
                    ps3.setString(3,strSub);
                        ps3.setString(4,strMenuCat);
                    
                    results2=ps.executeQuery();
                    while(results2.next()) {
                         int MaxMenuSeq=results2.getInt("order_seq_no");
                         MaxMenuSeq=MaxMenuSeq+10;
                         xml=xml+"<MaxMenuSeq>"+MaxMenuSeq+"</MaxMenuSeq>";
                    }





   var cMaxMenuSeq=baseResponse.getElementsByTagName("MaxMenuSeq")[0].firstChild.nodeValue;
                          document.MenuForm.txtMenuOrderSeq.value=cMaxMenuSeq;

 * */