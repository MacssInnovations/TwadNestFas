package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SubLedgerMasterServlet extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        

    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        Connection connection=null;
        Statement statement=null;
        ResultSet results=null;
        ResultSet results2=null;
        PreparedStatement ps=null;

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
        int SubLedgerTypeCode=0;
        String SubLedgerTypeDesc="";
        
        
        
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
        
        
        
        try
        {
            System.out.println("--------------------------___RKsbgsubbbbbbbbbbbbbb-------------------");
            
            SubLedgerTypeDesc = request.getParameter("SubLedgerTypeDesc");                 
            System.out.println("------------------------SubLedgerTypeDesc-------------------"+SubLedgerTypeDesc);
        }        
        catch(Exception e)
        { 
        System.out.println("--------------------------___RaviKumarrrrrrrrrrrr-------------------");
            System.out.println("in getting values in all other values **** "+ e);
        }
        

        try
        {
          SubLedgerTypeCode= Integer.parseInt(request.getParameter("SubLedgerTypeCode"));
          
        }
        catch(Exception e)
        { 
            System.out.println("in getting values in cadre id**** "+ e);
        }     
        
        
        
        if(strCommand.equalsIgnoreCase("Delete"))
                {
                    xml="<response><command>Delete</command>";
                    try
                    {
                        PreparedStatement pstmt = connection.prepareStatement("delete from COM_MST_SL_TYPES where sub_ledger_type_code=?");
                                            System.out.println(pstmt);
                                                pstmt.setInt(1,SubLedgerTypeCode);
                                                pstmt.executeUpdate();
                                                xml=xml+"<flag>success</flag><SubLedgerTypeCode>"+SubLedgerTypeCode+"</SubLedgerTypeCode>";
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
                        CallableStatement pstmt = connection.prepareCall("call FAS_SUBLEDGERPROCEDURE(::numeric,?,?::numeric,?)");
                        System.out.println(pstmt);
                            pstmt.setInt(1, SubLedgerTypeCode);
                            pstmt.setString(2, SubLedgerTypeDesc);
                            pstmt.registerOutParameter(3,Types.NUMERIC);
                            pstmt.setNull(3,java.sql.Types.NUMERIC);
                            pstmt.setString(4,"update");
                            pstmt.execute();
                            //int errcode=pstmt.getInt(3);
                            int errcode = pstmt.getBigDecimal(3).intValue();
                            System.out.println("SQLCODE:::"+errcode);
                            if(errcode!=0)
                            {
                                   xml=xml+"<flag>failure</flag>";
                            }
                            else
                               xml=xml+"<flag>success</flag>";
                                    pstmt.close();
                    }        
                                            catch(SQLException e) {
                                                ret_code = e.getErrorCode();
                                                 System.err.println(ret_code + e.getMessage());
                                                xml=xml+"<flag>failure</flag>";
                                                
                                            }

                    xml=xml+"</response>";
                }
                
        else if(strCommand.equalsIgnoreCase("Add"))
        {
            System.out.println("--------------------------Add    123-------------------");
            xml="<response><command>Add</command>";
                     try
                     {
                       CallableStatement pstmt = connection.prepareCall("call FAS_SUBLEDGERPROCEDURE(?::numeric,?,?::numeric,?)");
                                       pstmt.setInt(1,SubLedgerTypeCode);
                                       pstmt.setString(2, SubLedgerTypeDesc);
                                       pstmt.registerOutParameter(3, Types.NUMERIC);
                                       pstmt.setNull(3,java.sql.Types.NUMERIC);
                                       pstmt.setString(4,"insert");
                                       pstmt.registerOutParameter(1, Types.NUMERIC);
                                       pstmt.setNull(1,java.sql.Types.NUMERIC);
                                       pstmt.execute();
//                                       int SubLedgerCode = pstmt.getInt(1);
//                                       int errcode=pstmt.getInt(3);
                                       int SubLedgerCode = pstmt.getBigDecimal(1).intValue();
                                       int errcode = pstmt.getBigDecimal(3).intValue();
                                       System.out.println("SQLCODE:::"+errcode);
                                       if(errcode!=0)
                                       {
                                              xml=xml+"<flag>failure</flag>";
                                       }
                                       else
                                          xml=xml+"<flag>success</flag>";
                                       
                                       System.out.println(SubLedgerCode);
                                       xml=xml+"<SubLedgerTypeCode>"+SubLedgerCode+"</SubLedgerTypeCode><SubLedgerTypeDesc>"+SubLedgerTypeDesc+"</SubLedgerTypeDesc>";
                                       pstmt.close();
                                   }
                                   catch(SQLException e) {
                                      System.out.println("error is" + e);
                                       ret_code = e.getErrorCode();
                                        System.err.println(ret_code + e.getMessage());
                                       xml=xml+"<flag>failure</flag>";
                                       
                                   }
            
                     xml=xml+"</response>";
        }
        
        else if(strCommand.equals("Get"))
              { 
              xml="<response><command>Get</command>";
                  try {
                   System.out.println("bef res");
                   
                       //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                        results2 = statement.executeQuery("select sub_ledger_type_code,sub_ledger_type_desc from com_mst_sl_types order by sub_ledger_type_code");
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                             { 
                                                            
                               int SubLedgerTypeCode1=results2.getInt("sub_ledger_type_code");
                               String SubLedgerTypeDesc1=results2.getString("sub_ledger_type_desc");
                               
                               
                              //<PayName>" + PayName + "</PayName>;
                               xml=xml+ "<SubLedgerTypeCode>" + SubLedgerTypeCode1 + "</SubLedgerTypeCode><SubLedgerTypeDesc>" + SubLedgerTypeDesc1 + "</SubLedgerTypeDesc>";
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
