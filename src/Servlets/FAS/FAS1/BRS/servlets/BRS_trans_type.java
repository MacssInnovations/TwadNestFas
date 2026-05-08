package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BRS_trans_type extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);       

    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        Connection connection=null;
        Statement statement=null;
        ResultSet results=null;
        ResultSet results2=null;
        ResultSet rs2=null;
        
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
        try
                  {
                       ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                         String ConnectionString="";
                        
                         String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                         String strdsn=rs.getString("Config.DSN");
                         String strhostname=rs.getString("Config.HOST_NAME");
                         String strportno=rs.getString("Config.PORT_NUMBER");
                         String strsid=rs.getString("Config.SID");
                         String strdbusername=rs.getString("Config.USER_NAME");
                         String strdbpassword=rs.getString("Config.PASSWORD");
                           
                         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

                          Class.forName(strDriver.trim());
                          connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
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
        
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
        int ret_code=0;
        int TransTypeCode=0;
        String TransTypeShDesc="";
        String TransTypeDesc="";
        String TransType="";
        int max_branch_id = 0;
        
        HttpSession session=request.getSession(false);
        try
        {
            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
               
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
         String userid=(String)session.getAttribute("UserId");
         System.out.println("session id is:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        try
        {
          strCommand = request.getParameter("command");      
        }
        catch(Exception e)
        {
          e.printStackTrace();
          
        }
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
      

          
        if(strCommand.equalsIgnoreCase("Delete"))
                {
                    xml="<response><command>Delete</command>";
                    try
                    {
                      TransTypeCode= Integer.parseInt(request.getParameter("TransTypeCode"));
                      
                    }
                    catch(Exception e)
                    { 
                        System.out.println("in getting values in cadre id**** "+ e);
                    }      
                    try
                    {
                        PreparedStatement pstmt = connection.prepareStatement("delete from FAS_BRS_TRANSACTION_TYPE where trans_code=?");
                                            System.out.println(pstmt);
                                                pstmt.setInt(1,TransTypeCode);
                                                pstmt.executeUpdate();
                                                xml=xml+"<flag>success</flag><TransTypeCode>"+TransTypeCode+"</TransTypeCode>";
                                            pstmt.close();
                                            }
                                            catch(SQLException e) {
                                                ret_code = e.getErrorCode();
                                                 System.err.println(ret_code + e.getMessage());
                                                xml=xml+"<flag>failure</flag>";
                                            }

                    xml=xml+"</response>";
                }
                
                else if(strCommand.equalsIgnoreCase("Update"))
                {
                    xml="<response><command>Update</command>";
                    try
                    {
                    
                    	TransTypeShDesc = request.getParameter("TransTypeShDesc");
                       // ReasonAbbr=request.getParameter("txtGroup");
                        System.out.println("Class desc:"+TransTypeShDesc);
                        //System.out.println("ReasonAbbr is:"+ReasonAbbr);
                    }
                    
                    catch(Exception e)
                    { 
                        System.out.println("in getting values in all other values **** "+ e);
                    }

                    try
                    {
                    	TransTypeCode= Integer.parseInt(request.getParameter("TransTypeCode"));
                      System.out.println("TransTypeCode" + TransTypeCode);
                    }
                    catch(Exception e)
                    { 
                        System.out.println("in getting values in TransTypeCode **** "+ e);
                    }
                    try
                    {
                    	TransTypeDesc=request.getParameter("TransTypeDesc");
                    	TransType=request.getParameter("TransType");
                        System.out.println("TransTypeDesc:"+TransTypeDesc);
                        System.out.println("TransType:"+TransType);
                        
                     }
                    
                    catch(Exception e)
                    { 
                        System.out.println("in getting values in all other values **** "+ e);
                    }
                    try {
                        ps =  connection.prepareStatement("update FAS_BRS_TRANSACTION_TYPE set TRANS_SHORT_DESC=?,TRANS_DESC=?,TRANS_TYPE=?,UPDATE_BY_USER_ID=?,UPDATED_DATE=? " + 
                              "where TRANS_CODE=?");

                        
                        ps.setString(1, TransTypeShDesc);
                        ps.setString(2, TransTypeDesc);
                        ps.setString(3, TransType);
                        ps.setString(4,userid);
                        ps.setTimestamp(5,ts);
                        ps.setInt(6, TransTypeCode);
                        // ps.setString(8,update_user);
                        // ps.setTimestamp(12,ts);
                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                    }

                    catch (Exception e) {
                        System.out.println("catch..HERE.in load head code." + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                   
                    xml = xml + "</response>";
                    System.out.println(xml);
                    

                    
                }
                
        else if(strCommand.equalsIgnoreCase("Add"))
        {
            xml="<response><command>Add</command>";
          /*  try
            {
            	TransTypeCode= Integer.parseInt(request.getParameter("TransTypeCode"));
              System.out.println("TransTypeCode" + TransTypeCode);
            }
            catch(Exception e)
            { 
                System.out.println("in getting values in TransTypeCode **** "+ e);
            }*/
            try
            {
                
            	TransTypeShDesc = request.getParameter("TransTypeShDesc");
            	TransTypeDesc=request.getParameter("TransTypeDesc");
            	TransType=request.getParameter("TransType");
                System.out.println("TransType:"+TransType);
               // System.out.println("ReasonAbbr is:"+ReasonAbbr);
            }
            
            catch(Exception e)
            { 
                System.out.println("in getting values in all other values **** "+ e);
            }
            try {
                ps2 = connection.prepareStatement("SELECT nvl(max(TRANS_CODE),1) AS br_id FROM FAS_BRS_TRANSACTION_TYPE ");
              //  ps2.setInt(1,TransTypeCode);
                rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    max_branch_id = rs2.getInt("br_id");
                    System.out.println("Maximum trans ID is ==" + max_branch_id);
                
                    max_branch_id++;
                    System.out.println("Maximum trans ID is ==" + max_branch_id);
                }

                System.out.println("Maximum trans ID is ==" + max_branch_id);

                ps2.close();
                rs2.close();

            } catch (Exception e) {
                System.out.println("Failed to Fetch Maximum trans ID " + e);
            }

            

            try {

                ps =  connection.prepareStatement("insert into FAS_BRS_TRANSACTION_TYPE(TRANS_CODE,TRANS_SHORT_DESC,TRANS_DESC,TRANS_TYPE,UPDATE_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)");

                ps.setInt(1, max_branch_id);
                ps.setString(2, TransTypeShDesc);
                ps.setString(3, TransTypeDesc);
                ps.setString(4, TransType);
                ps.setString(5,userid);
                ps.setTimestamp(6,ts);

                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml=xml+ "<TransTypeCode>" +max_branch_id + "</TransTypeCode><TransTypeShDesc>" + TransTypeShDesc + "</TransTypeShDesc><TransTypeDesc>"+TransTypeDesc+"</TransTypeDesc><TransType>"+TransType+"</TransType>";
                System.out.println("here is ok");


            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
         
            
        }
        
        else if(strCommand.equals("Get"))
              { 
              xml="<response><command>Get</command>";
                  try {
                   System.out.println("bef res");
                   
                       //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                        results2 = statement.executeQuery("select TRANS_CODE,TRANS_SHORT_DESC,TRANS_DESC,TRANS_TYPE from FAS_BRS_TRANSACTION_TYPE order by TRANS_CODE");
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                           { 
                                                            
                               int TransTypeCode1=results2.getInt("TRANS_CODE");
                               String TransTypeShDesc1=results2.getString("TRANS_SHORT_DESC");
                               String TransTypeDesc1=results2.getString("TRANS_DESC");
                               String TransType1=results2.getString("TRANS_TYPE");
                               
                              //<PayName>" + PayName + "</PayName>;
                               xml=xml+ "<TransTypeCode>" +TransTypeCode1 + "</TransTypeCode><TransTypeShDesc>" + TransTypeShDesc1 + "</TransTypeShDesc><TransTypeDesc>"+TransTypeDesc1+"</TransTypeDesc><TransType>"+TransType1+"</TransType>";
                           }
                             }catch(Exception aee){System.out.println("Exception in the getting values OF GET: " + aee);}  
                           results2.close();
                           response.setHeader("cache-control","no-cache");
                      }
                      catch(Exception e1)
                      {             System.out.println("Exception is in Get"+e1);
                          xml=xml+"<flag>failure</flag>";
                      }
                  xml=xml+"</response>";
              }  
              
        else if(strCommand.equals("Load"))
              { 
              xml="<response><command>Load</command>";
                  try {
                   System.out.println("bef load");
                   
                       //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                        results2 = statement.executeQuery("select TRANS_CODE,TRANS_SHORT_DESC,TRANS_DESC,TRANS_TYPE from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE= "+TransTypeCode+" order by TRANS_CODE");
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                           { 
                            	 int TransTypeCode1=results2.getInt("TRANS_CODE");
                                 String TransTypeShDesc1=results2.getString("TRANS_SHORT_DESC");
                                 String TransTypeDesc1=results2.getString("TRANS_DESC");
                                 String TransType1=results2.getString("TRANS_TYPE");
                                 
                               
                               xml=xml+ "<TransTypeCode>" +TransTypeCode1 + "</TransTypeCode><TransTypeShDesc1>" + TransTypeShDesc1 + "</TransTypeShDesc1><TransTypeDesc1>"+TransTypeDesc1+"</TransTypeDesc1>+<TransType1>"+TransType1+"</TransType1>"; 
                              
                           }
                             }catch(Exception aee){System.out.println("Exception in the getting values OF GET: " + aee);}  
                           results2.close();
                           response.setHeader("cache-control","no-cache");
                      }
                      catch(Exception e1)
                      {             System.out.println("Exception is in Get"+e1);
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