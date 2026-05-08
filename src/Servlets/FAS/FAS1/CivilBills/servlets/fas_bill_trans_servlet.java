package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;

public class fas_bill_trans_servlet extends HttpServlet {
    
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {
    	 System.out.println("fas_bill_trans_servlet .java");
                      response.setContentType(CONTENT_TYPE);
                     PrintWriter out = response.getWriter();
                    
                    /************** Initialising all the field values ***************/
                    String strcmd="",hid="";
                    int bill_majr_code=0;int bill_minr_code=0;int sub_type_code1=0;
                    String bill_minr_desc="";String sub_type_desc1="";String remk="";
                    String sub_type_avil="";String pro_avil="",xml="";
                    String selectsql;
                    int minor_type=0,count=0;
                    String update_user="";
                    HttpSession session=null;
                    Timestamp ts=null;
        /*********** connection establishment****************/
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    try {
                            ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                            String ConnectionString="";

                            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                            String strdsn=rs1.getString("Config.DSN");
                            String strhostname=rs1.getString("Config.HOST_NAME");
                            String strportno=rs1.getString("Config.PORT_NUMBER");
                            String strsid=rs1.getString("Config.SID");
                            String strdbusername=rs1.getString("Config.USER_NAME");
                            String strdbpassword=rs1.getString("Config.PASSWORD");
                            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                            Class.forName(strDriver.trim());
                            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           }
           catch(Exception e)
               {
                  System.out.println("Exception in openeing connection :"+e);
                  //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
               }
                try
                {
                    session=request.getSession(false);
                   if(session==null)
                   {
                       System.out.println(request.getContextPath()+"/index.jsp");
                       response.sendRedirect(request.getContextPath()+"/index.jsp");
                       return;
                   }
                   System.out.println(session);
                    }
                    catch(Exception e)
                    {
                    System.out.println("Redirect Error :"+e);
                    }
                    try
                    {
                              strcmd =  request.getParameter("command");     
                              System.out.println("Command passed via the button pressed : " + strcmd);
                    }
                      catch(Exception e3)
                      {
                                e3.printStackTrace();
                      }        
                  
          xml="<response>";
        /****************** getting the values from submit Button Pressed***********/
        
         if(strcmd.equalsIgnoreCase("Add")) 
         {
                 System.out.println("**********ADDing the Values*******");
                 try 
                    {
                             hid=request.getParameter("hid");
                            bill_majr_code=Integer.parseInt(request.getParameter("bill_majr_type_code"));
                            System.out.println("Bill Major Type Code Entered is :"+bill_majr_code);
                            bill_minr_desc=request.getParameter("bill_minr_desc");
                            System.out.println("Bill Minor Description    :"+bill_minr_desc);
                        
                            
                                update_user=(String)session.getAttribute("UserId");
                                long l=System.currentTimeMillis();
                                ts=new Timestamp(l);
                    }
                        catch(Exception e1)
                        {
                                System.out.println("Exception while getting the values");
                        }
                            try
                                {
                                        xml=xml+"<command>add</command>";
                                        if(hid.equalsIgnoreCase("Y")){
                                            sub_type_avil= request.getParameter("sub_type_YN");
                                            System.out.println("Is Sub Type Available    :"+sub_type_avil);
                                            pro_avil= request.getParameter("pro_avai_YN");
                                            System.out.println("Is Proceeding Available  :"+pro_avil);
                                            remk= request.getParameter("pro_remarks");
                                            System.out.println("Remarks  :"+remk);
                                        if(sub_type_avil.equalsIgnoreCase("N")) 
                                        {
                                               System.out.println("Sub type is not available");
                                               try
                                               {
                                                        String sqlsel="select decode(max(BILL_MINOR_TYPE_CODE),null,0,max(BILL_MINOR_TYPE_CODE))as minor_type from FAS_BILL_MINOR_TYPES_MST ";    
                                                        ps=con.prepareStatement(sqlsel);
                                                        rs=ps.executeQuery();
                                                        if(rs.next())
                                                        {
                                                        minor_type=rs.getInt("minor_type");
                                                        }
                                                        minor_type=minor_type+1;
                                                        System.out.println("Maximum value of Bill minor type is :"+minor_type);
                                                        ps.close();
                                                        rs.close();
                                               }
                                               catch(Exception e11)
                                               {
                                                        System.out.println("Exception arised :"+e11);
                                               }
                                               try
                                               {
                                               String sqladd="insert into FAS_BILL_MINOR_TYPES_MST values (?,?,?,?,?,?,?,?,?)";
                                                ps=con.prepareStatement(sqladd);  
                                                ps.setInt(1,bill_majr_code);
                                                ps.setInt(2,minor_type);
                                                ps.setString(3,bill_minr_desc);
                                                ps.setString(4,sub_type_avil);
                                                ps.setString(5,pro_avil);
                                                ps.setString(6,remk);
                                                ps.setString(7,update_user);
                                                ps.setTimestamp(8,ts);
                                                ps.setString(9, "L");
                                                
                                                ps.executeUpdate();
                                                ps.close();
                                                System.out.println("records inserted successfully");
                                                    xml=xml+"<flag>success</flag>"; 
                                                }
                                               catch(Exception e1){
                                            	   	System.out.println("Exception arised in insertion");
                                            	   	xml=xml+"<flag>fail</flag>";
                                            	   }
                                        }
                                        else if(sub_type_avil.equalsIgnoreCase("Y"))
                                        {
                                           
                                        	System.out.println("Sub type available");
                                                String record_id,record_desc;
                                                String rec1[],rec2[];
                                                record_id=request.getParameter("record_id");
                                                record_desc=request.getParameter("record_desc");
                                                rec1=record_id.split(",");
                                                rec2=record_desc.split(",");
                                                System.out.println("length of Records splitted :"+rec1.length);
                                                                                               
                                                try
                                                {
                                                 selectsql="select  decode(max(BILL_MINOR_TYPE_CODE),null,0, max(BILL_MINOR_TYPE_CODE))as minor_type from FAS_BILL_MINOR_TYPES_MST";
                                                 ps=con.prepareStatement(selectsql);
                                                  rs= ps.executeQuery();
                                                  if(rs.next()) 
                                                  {
                                                           minor_type=rs.getInt(1);
                                                    }
                                                 minor_type= minor_type+1;
                                                String sqladd1="insert into FAS_BILL_MINOR_TYPES_MST values (?,?,?,?,?,?,?,?,?)";
                                                ps=con.prepareStatement(sqladd1);
                                                ps.setInt(1,bill_majr_code);
                                                ps.setInt(2, minor_type);
                                                ps.setString(3,bill_minr_desc);
                                                ps.setString(4,sub_type_avil);
                                                ps.setString(5,pro_avil);
                                                ps.setString(6,remk);
                                                    ps.setString(7,update_user);
                                                    ps.setTimestamp(8,ts);
                                                    ps.setString(9, "L");
                                                ps.executeUpdate();
                                                ps.close();
                                                System.out.println("records inserted into the bill minor table");
                                                }
                                                catch(Exception e){
                                                	System.out.println("Exception while inserting the values in bill minor table");
                                                }
                                             /***** add the hidden fields values ************/                                                                                      
                                                try
                                                {
                                                      System.out.println("check my rec1 "+rec1.length);
                                                        for(int k=0;k<rec1.length;k++){                                                        		
                                                                String sqladd2="insert into FAS_BILL_SUB_TYPES(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS) values (?,?,?,?,?,?,?)";
                                                                ps=con.prepareStatement(sqladd2); 
                                                                System.out.println("getting the grid values");
                                                                 int subcode=0;
                                                                String subdesc="";
                                                                
                                                                ps.setInt(1,bill_majr_code);
                                                                ps.setInt(2,minor_type);
                                                                ps.setInt(3,Integer.parseInt(rec1[k]));
                                                                ps.setString(4,rec2[k]);
                                                                
                                                             ps.setString(5,update_user);
                                                             ps.setTimestamp(6,ts);
                                                             ps.setString(7, "L");
                                                                ps.executeUpdate();
                                                                System.out.println("updated successfully");
                                                                ps.close();
                                                             xml=xml+"<flag>success</flag>";  
                                                         }
                                                }
                                                catch(Exception e){
                                                	System.out.println("Exception arised while insertion in subtype table");
                                                	xml=xml+"<flag>fail</flag>";
                                                	}
                                      
                                      }//end of else part
                                        }else{
                                        	 String sub_id=""; 
                                        	 int subId=0;
                                        	 minor_type= Integer.parseInt(request.getParameter("bill_minr_type_code"));
                                        	   System.out.println("Sub type available minor_type "+minor_type);
                                               String record_id,record_desc;
                                               String rec1[],rec2[];
                                               record_id=request.getParameter("record_id");
                                               record_desc=request.getParameter("record_desc");
                                               rec1=record_id.split(",");
                                               rec2=record_desc.split(",");
                                               System.out.println("length of Records splitted :"+rec1.length);
                                        	  try
                                              {
                                                    System.out.println("check my rec1 "+rec1.length);
                                                      for(int k=0;k<rec1.length;k++){ 
                                                          System.out.println("check my rec1 "+rec1[k]);
                                                          try
                                                          {
                                                           selectsql="select decode(max(BILL_SUB_TYPE_CODE),null,0, max(BILL_SUB_TYPE_CODE))as sub_type from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+bill_majr_code+" and BILL_MINOR_TYPE_CODE="+minor_type;
                                                           PreparedStatement ps_max=con.prepareStatement(selectsql);
                                                          System.out
																.println("selectsq"+selectsql);
                                                           /* ps_max.setInt(1,bill_majr_code);
                                                           ps_max.setInt(2,minor_type);*/
                                                           ResultSet rs_max= ps_max.executeQuery();
                                                            if(rs_max.next()) 
                                                            {
                                                            	sub_id=rs_max.getString(1);
                                                              }
                                                          } catch (Exception e) {
																e.printStackTrace();
															}
                                                          subId=Integer.parseInt(sub_id)+1;
                                                              String sqladd2="insert into FAS_BILL_SUB_TYPES(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS) values (?,?,?,?,?,?,?)";
                                                              ps=con.prepareStatement(sqladd2); 
                                                              System.out.println("getting the grid values");
                                                               int subcode=0;
                                                              String subdesc="";
                                                              
                                                              ps.setInt(1,bill_majr_code);System.out
																	.println(bill_majr_code+" "+minor_type+" "+sub_id+" "+rec2[k]);
                                                              ps.setInt(2,minor_type);
                                                              ps.setInt(3,subId);
                                                              ps.setString(4,rec2[k]);
                                                              
                                                           ps.setString(5,update_user);
                                                           ps.setTimestamp(6,ts);
                                                           ps.setString(7, "L");
                                                              ps.executeUpdate();
                                                              System.out.println("updated successfully");
                                                              ps.close();
                                                           xml=xml+"<flag>success</flag>";  
                                                       }
                                              }
                                              catch(Exception e){
                                              	System.out.println("Exception arised while insertion in subtype table");
                                              	xml=xml+"<flag>fail</flag>";
                                              	}
                                        }
                                }
                                catch(Exception e11)
                                {
                                xml=xml+"<flag>fail</flag>";
                                System.out.println("Exception arised in inserting the values"+e11);
                                }
            }else if(strcmd.equals("loadMinor")){
                xml=xml+"<command>loadMinor</command>";
                try
                {
                	int strmajor=Integer.parseInt(request.getParameter("major_code"));
                    System.out.println("major code selected:"+strmajor);
                  String sql="select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST  where BILL_MAJOR_TYPE_CODE=? order by BILL_MINOR_TYPE_CODE";
                  System.out.println("sql >> "+sql);
                  ps=con.prepareStatement(sql);
                  ps.setInt(1,strmajor);
                rs=ps.executeQuery();
                    
                while(rs.next())
                {
                      xml=xml+"<desc>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</desc><id>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</id>";
                      count++;
                } // while close
                if(count>0)
                    xml=xml+"<flag>success</flag>";
                else
                    xml=xml+"<flag>nodata</flag>";
               
               ps.close();
               rs.close();
                } //try close
                catch(Exception e)
                {
                        xml=xml+"<flag>failure</flag>";
                        System.out.println(e);
                }
            }
            else if(strcmd.equals("major"))
                    {
                            
                            xml=xml+"<command>MajorCode</command><select>";
                            try
                            {
                              int strmajor=Integer.parseInt(request.getParameter("major_code"));
                              System.out.println("major code selected:"+strmajor);
                              String sql="select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=?";
                            ps=con.prepareStatement(sql);
                            ps.setInt(1,strmajor);
                            rs=ps.executeQuery();
                                
                            while(rs.next())
                            {
                                  xml=xml+"<option><desc>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</desc><id>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</id></option>";
                                  count++;
                            } // while close
                            if(count>0)
                                xml=xml+"</select><flag>success</flag>";
                            else
                                xml=xml+"</select><flag>nodata</flag>";
                           
                           ps.close();
                           rs.close();
                            } //try close
                            catch(Exception e)
                            {
                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                    System.out.println(e);
                            }
                    }
                    else if(strcmd.equals("list"))
                            {
                                    xml=xml+"<command>List</command>";
                                    try
                                    {
                                        String strmajor=request.getParameter("major_code");
                                        System.out.println("major code selected:"+strmajor);
                                        String strminor=request.getParameter("minor_code");
                                        System.out.println("minor code for the major code selected:"+strminor);
                                      
                                        String sql="select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES  where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=?";
                                        ps=con.prepareStatement(sql);
                                        ps.setInt(1,Integer.parseInt(strmajor));
                                        ps.setInt(2,Integer.parseInt(strminor));
                                        rs=ps.executeQuery();
                                      
                                        count=0;
                                    while(rs.next())
                                    {
                                          xml=xml+"<sub_desc>"+rs.getString("BILL_SUB_TYPE_DESC")+"</sub_desc>" ;
                                          xml=xml+"<sub_code>"+rs.getInt("BILL_SUB_TYPE_CODE")+"</sub_code>";
                                          count++;
                                     
                                          System.out.println("count"+count);
                                    } // while close
                                     if(count>0)
                                         xml=xml+"<flag>success</flag>";
                                     else if(count==0)
                                         {
                                                System.out.println("Count :"+count);
                                                xml=xml+"<flag>nodata</flag>";
                                         }
                                        ps.close();
                                        rs.close();
                                    } //try close
                                    catch(Exception e)
                                    {
                                            xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                            System.out.println(e);
                                    }
                            }
                        else if(strcmd.equalsIgnoreCase("Update"))
                        {
                                System.out.println("\n*************\nUpdate\n**************\n");
                                xml="<response><command>Update</command>";
                            try 
                            {
                                        int strmajor=Integer.parseInt(request.getParameter("major_code"));
                                        System.out.println("major code selected:"+strmajor);
                                        int strminor=Integer.parseInt(request.getParameter("minor_code"));
                                        System.out.println("minor code for the major code selected:"+strminor);
                                        String sub_desc=request.getParameter("sub_desc1");
                                        System.out.println("Sub type Description :"+sub_desc);
                                        int sub_code=Integer.parseInt(request.getParameter("sub_code1"));
                                        System.out.println("Sub type code :"+sub_code);
                                        
                                         String sqlUpdate = "UPDATE FAS_BILL_SUB_TYPES SET BILL_SUB_TYPE_DESC=? where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=? AND BILL_SUB_TYPE_CODE=?";
                                         ps = con.prepareStatement(sqlUpdate);
                                         ps.setString(1,sub_desc);
                                         ps.setInt(2,strmajor);
                                         ps.setInt(3,strminor);
                                         ps.setInt(4,sub_code);
                                         ps.executeUpdate();
                                        xml=xml+"<flag>success</flag>";  
                                        System.out.println("Successfully updated");
                                        xml=xml+"<sub_code>" +sub_code+ "</sub_code>";
                                        xml=xml+"<sub_desc>" +sub_desc + "</sub_desc>";
                                ps.close();
                                rs.close();
                            }
                            catch(Exception e7)
                            {
                                        System.out.println("Exception in Updating record ===> "+e7);
                                        xml=xml+"<flag>failure</flag>";
                            } 
                    }
            
    xml=xml+"</response>";
    System.out.println(xml);
    out.println(xml);
}
}