package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class advance_applicable_master extends HttpServlet
{
  private static final String CONTENT_TYPE="text/xml; charset=UTF-8";
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
 	{
		response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                System.out.println("Welcome to Advance Applicable Servlet");
                LoadDriver load = new LoadDriver();
		String cmnd="";
		String xml="";
                int txtbill_majr_code=0;int slno=0;int count=0;
                int txtbill_minr_code=0;
                String advance_applicable_YN="";String txtbill_majr_desc="";String txtbill_minr_desc="";
                String txtbill_advremarks="";
                PrintWriter pw=response.getWriter();
                HttpSession session=null;
                /*********** connection establishment****************/
                Connection con=null;
                ResultSet rs=null;
                PreparedStatement ps=null;
                      xml="<response>";
                try{                            
                    	con=load.getConnection();
                }
                catch(Exception e)
                {
                        System.out.println("Exception in connection...."+e);
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
                       String userid=(String)session.getAttribute("UserId");
                       System.out.println("Session id is:"+userid);
                /****************** getting the values from Button Pressed***********/
                try
                {
                          cmnd =  request.getParameter("command");     
                          System.out.println("Command passed via the button pressed : " + cmnd);
                }
                  catch(Exception e3)
                  {
                    e3.printStackTrace();
                  }
                  /*****************Getting the values from Text box bill_type_code***************/
                   try
                  {
                          txtbill_majr_code=Integer.parseInt(request.getParameter("bill_majr_code"));
                          txtbill_minr_code=Integer.parseInt(request.getParameter("bill_minr_code"));
                          advance_applicable_YN=request.getParameter("advance_applicable_YN");
                          if(request.getParameter("txtbill_advremarks")!=null){
                        	  txtbill_advremarks=request.getParameter("txtbill_advremarks");  
                          }
                          if(request.getParameter("txtbill_majr_desc")!=null){
                        	  txtbill_majr_desc=request.getParameter("txtbill_majr_desc");  
                          }
                          if(request.getParameter("txtbill_minr_desc")!=null){
                        	  txtbill_minr_desc=request.getParameter("txtbill_minr_desc");  
                          }                          
                          
                  }
		  catch(Exception e15)
                  { 
                        System.out.println("Exception fetching values===> " + e15);
                  }
                       
                     if(cmnd.equalsIgnoreCase("loadAllMinorType"))
                      {
                          xml=xml+"<command>loadAllMinorType</command>";
                          try
                          {
                              //int strmajor=Integer.parseInt(request.getParameter("MajorCode1"));
                              //System.out.println("major code selected:"+strmajor);
                              String sql="select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST order by BILL_MINOR_TYPE_CODE ";
                             // if(strmajor!=0)
                             // sql=sql+"where BILL_MAJOR_TYPE_CODE=? order by BILL_MINOR_TYPE_CODE";
                              ps=con.prepareStatement(sql);
                              //if(strmajor!=0)
                              //ps.setInt(1,strmajor);
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
                                                     
                                  ps.close();
                                  rs.close();
                          } //try close
                          catch(Exception e)
                          {
                              xml=xml+"<flag>"+e.getMessage()+"</flag>";
                              System.out.println(e);
                          }
                      }
                  if(cmnd.equalsIgnoreCase("delete"))
                  {
                            System.out.println("\n*************\nDelete\n**************\n");
                            xml=xml+"<command>Delete</command>";
                      try 
                      {
                                  //String sqlDelete = "DELETE FROM ADVANCE_APPLICABLE_MASTER WHERE BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=?";
                                  String sqlDelete = "UPDATE ADVANCE_APPLICABLE_MASTER SET STATUS='C' WHERE BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=?";
                                  ps = con.prepareStatement(sqlDelete);
                                  ps.setInt(1, txtbill_majr_code);
                                  ps.setInt(2, txtbill_minr_code);
                                  ps.executeUpdate();
                                  ps.close();
                                  xml=xml+"<flag>success</flag>";
                      }
                      catch(Exception e6)
                      {
                          System.out.println("Exception in Deleting record ===> "+e6);
                          xml=xml+"<flag>failure</flag>";
                      }
                  }
                  else if(cmnd.equalsIgnoreCase("Update"))
                  {
                            System.out.println("\n*************\nUpdate\n**************\n");
                            xml=xml+"<command>Update</command>";
                      try 
                      {
                            String sqlUpdate = "UPDATE ADVANCE_APPLICABLE_MASTER SET ADVANCE_APPLICABLE=?,REMARKS=?, " +
                            " UPDATED_BY_USER_ID = ?," + 
                            " UPDATED_DATE = SYSTIMESTAMP " +
                            "WHERE BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=?";
                            ps = con.prepareStatement(sqlUpdate);
                            ps.setString(1,advance_applicable_YN);
                            ps.setString(2,txtbill_advremarks);
                            ps.setString(3,userid);
                            ps.setInt(4,txtbill_majr_code);
                            ps.setInt(5,txtbill_minr_code);
                            ps.executeUpdate();
                            ps.close();
                            xml=xml+"<flag>success</flag>";
                      }catch(Exception e7){
                          System.out.println("Exception in Updating record ===> "+e7);
                          xml=xml+"<flag>failure</flag>";
                      }
                  }
                  else if(cmnd.equalsIgnoreCase("Add"))
                  {
                          System.out.println("\n*************\nAdd\n**************\n");
                          xml=xml+"<command>Add</command>";
                          try{
                                  String update_user=(String)session.getAttribute("UserId");
                                  //long l=System.currentTimeMillis();
                                  //Timestamp ts=new Timestamp(l);
                                        String sqlsel1="select SLNO from ADVANCE_APPLICABLE_MASTER where BILL_MAJOR_TYPE_CODE=? AND BILL_MINOR_TYPE_CODE=?";  
                                        ps=con.prepareStatement(sqlsel1);
                                        ps.setInt(1,txtbill_majr_code);
                                        ps.setInt(2,txtbill_minr_code);
                                        rs=ps.executeQuery();
                                        if(!rs.next()){
                                               String sqlsel="select decode(max(SLNO),null,0,max(SLNO))as slno from ADVANCE_APPLICABLE_MASTER";    
                                               ps=con.prepareStatement(sqlsel);
                                               rs=ps.executeQuery();
                                               if(rs.next())
                                               {
                                                     slno=rs.getInt("SLNO");
                                               }
                                               slno=slno+1;
                                               System.out.println("Maximum value of Serial Number is :"+slno);
                                               ps.close();
                                               rs.close();
                                              //String sqlAdd = "insert into ADVANCE_APPLICABLE_MASTER() values(?,?,?,?,?,?,?)";
                                               String sqlAdd ="INSERT " +
	                                              "INTO ADVANCE_APPLICABLE_MASTER " +
	                                              "  ( " +
	                                              "    SLNO, " +
	                                              "    BILL_MAJOR_TYPE_CODE, " +
	                                              "    BILL_MINOR_TYPE_CODE, " +
	                                              "    ADVANCE_APPLICABLE, " +
	                                              "    REMARKS, " +
	                                              "    UPDATED_BY_USER_ID, " +
	                                              "    UPDATED_DATE, " +
	                                              "    STATUS )VALUES(?,?,?,?,?,?,SYSTIMESTAMP,'L')";                                              
                                              ps = con.prepareStatement(sqlAdd);
                                              ps.setInt(1,slno);
                                              ps.setInt(2,txtbill_majr_code);
                                              ps.setInt(3,txtbill_minr_code);
                                              ps.setString(4,advance_applicable_YN);
                                              ps.setString(5,txtbill_advremarks);        
                                              ps.setString(6,update_user);                                              
                                              ps.executeUpdate();
                                              xml=xml+"<flag>success</flag>";         
                                        }
                                        else
                                        {
                                                xml=xml+"<flag>AlreadyExist</flag>";    
                                        }
                          }catch (SQLException e) {
							// TODO: handle exception
                        	  e.printStackTrace();
                        	  xml=xml+"<flag>failure</flag>";
						}                          
                          catch(Exception e8)
                          {
                              System.out.println("Exception in Adding record ===> "+e8);
                              xml=xml+"<flag>failure</flag>";
                          }
                  }
                  else if(cmnd.equalsIgnoreCase("Get"))
                    {
                          System.out.println("\n*************\nGet\n**************\n");
                          xml=xml+"<command>Get</command>";
                          rs = null;
                          try
                          {
                           //String sqlGet = "select SLNO,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,ADVANCE_APPLICABLE,REMARKS from ADVANCE_APPLICABLE_MASTER order by SLNO";
                           String sqlGet="select * from( " 
                           +    "select * from " 
                           +    "(select SLNO,BILL_MAJOR_TYPE_CODE as mcode,BILL_MINOR_TYPE_CODE,ADVANCE_APPLICABLE,REMARKS,STATUS from ADVANCE_APPLICABLE_MASTER" 
                           +     ")a left outer join " 
                           +    "(select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L'" 
                           +    ")b on a.mCODE=b.BILL_MAJOR_TYPE_CODE " 
                           +    ")e left outer join " 
                           +    "(select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST" 
                           +     ")f on e.mcode=f.BILL_MAJOR_TYPE_CODE AND e.BILL_MINOR_TYPE_CODE=f.BILL_minor_TYPE_CODE " 
                           +     "order by slno "    ;
                           ps = con.prepareStatement(sqlGet);
                           rs = ps.executeQuery();
                           xml=xml+"<flag>success</flag>";
                           while(rs.next()){
                                xml=xml+"<serial_no>" + rs.getInt("SLNO") + "</serial_no>";
                                xml=xml+"<bill_majr_code>" + rs.getInt("BILL_MAJOR_TYPE_CODE") + "</bill_majr_code>";
                                xml=xml+"<bill_majr_desc>" + rs.getString("BILL_MAJOR_TYPE_DESC") + "</bill_majr_desc>";
                                xml=xml+"<bill_minr_code>" + rs.getInt("BILL_MINOR_TYPE_CODE") + "</bill_minr_code>";
                                xml=xml+"<bill_minr_desc>" + rs.getString("BILL_MINOR_TYPE_DESC") + "</bill_minr_desc>";
                                xml=xml+"<advance_applicable>" + rs.getString("ADVANCE_APPLICABLE") + "</advance_applicable>";
                                xml=xml+"<advance_app_remarks>" + rs.getString("REMARKS") + "</advance_app_remarks>";
                                xml=xml+"<status>" + rs.getString("STATUS") + "</status>";
                            }
                          }
                          catch(Exception e9)
                          {
                           System.out.println("Exception in Getting records ===> "+e9);
                           xml=xml+"<flag>failure</flag>";
                          }
                    }
                      xml=xml+"</response>";
                          System.out.println("xml is : " + xml);
                          pw.write(xml);
                          pw.flush();
                          pw.close();
                  
                  }
}
              
