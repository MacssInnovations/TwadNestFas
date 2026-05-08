package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AssetMasterServlet extends HttpServlet 
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
        ResultSet rs1=null;
        ResultSet results2=null;
        PreparedStatement ps1=null;
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
        String AssetTypeCode="";
        String AssetTypeDesc="";
        
        
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
        
            AssetTypeDesc = request.getParameter("AssetTypeDesc");
            AssetTypeCode=request.getParameter("AssetTypeCode");
            System.out.println("Asset Code:"+AssetTypeCode);
            System.out.println("Asset desc:"+AssetTypeDesc);
            
        }
        
        catch(Exception e)
        { 
            System.out.println("in getting values in all other values **** "+ e);
        }

           
        if(strCommand.equalsIgnoreCase("Delete"))
                {
                    xml="<response><command>Delete</command>";
                    try
                    {
                    	CallableStatement pstmt = connection.prepareCall("call FAS_ASSETMASTERPROCEDURE(?,?,?::numeric,?,?,?)");
                        System.out.println(pstmt);
                            pstmt.setString(1, AssetTypeCode);
                            pstmt.setString(2, AssetTypeDesc);
                            pstmt.registerOutParameter(3,Types.NUMERIC);
                            pstmt.setNull(3,java.sql.Types.NUMERIC);
                            pstmt.setString(4,"validate");
                            pstmt.setString(5,userid);
                            long l=System.currentTimeMillis();
                            Timestamp ts=new Timestamp(l);
                            pstmt.setTimestamp(6,ts);
                            
                            pstmt.execute();
//                            int errcode=pstmt.getInt(3);
                            int errcode= pstmt.getBigDecimal(3).intValue();
                            System.out.println("SQLCODE:::"+errcode);
                            if(errcode!=0)
                            {
                                   xml=xml+"<flag>failure</flag>";
                            }
                            else
                               xml=xml+"<flag>success</flag>";
                                    pstmt.close();
                   }catch(SQLException e) {
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
                        CallableStatement pstmt = connection.prepareCall("call FAS_ASSETMASTERPROCEDURE(?,?,?:numeric,?,?,?)");
                        System.out.println(pstmt);
                            pstmt.setString(1, AssetTypeCode);
                            pstmt.setString(2, AssetTypeDesc);
                            pstmt.registerOutParameter(3,Types.NUMERIC);
                            pstmt.setNull(3,java.sql.Types.NUMERIC);
                            pstmt.setString(4,"update");
                            pstmt.setString(5,userid);
                            long l=System.currentTimeMillis();
                            Timestamp ts=new Timestamp(l);
                            pstmt.setTimestamp(6,ts);
                            
                            pstmt.execute();
                            //int errcode=pstmt.getInt(3);
                            int errcode= pstmt.getBigDecimal(3).intValue();
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
            xml="<response><command>Add</command>";
                     try
                     {
                              
                                   ps1=connection.prepareStatement("select ASSET_TYPE_CODE,ASSET_TYPE_DESC from COM_MST_ASSETS_TYPE where ASSET_TYPE_CODE=?");
                                   ps1.setString(1,AssetTypeCode);
                                  
                                   rs1=ps1.executeQuery();
                                                   if(!rs1.next()){
                       CallableStatement pstmt = connection.prepareCall("call FAS_ASSETMASTERPROCEDURE(?,?,?::numeric,?,?,?)");
                       
                                       pstmt.setString(1,AssetTypeCode);
                                       pstmt.setString(2,AssetTypeDesc);
                                       pstmt.registerOutParameter(3,Types.INTEGER);
                                       pstmt.setNull(3,java.sql.Types.NUMERIC);
                                       pstmt.setString(4,"insert");
                                       pstmt.setString(5,userid);
                                       long l=System.currentTimeMillis();
                                       Timestamp ts=new Timestamp(l);
                                       pstmt.setTimestamp(6,ts);
                                       pstmt.execute();
                                       
                                       //int errcode=pstmt.getInt(3);
                                       int errcode= pstmt.getBigDecimal(3).intValue();
                                       System.out.println("SQLCODE:::"+errcode);
                                       if(errcode!=0)
                                       {
                                              xml=xml+"<flag>failure</flag>";
                                       }
                                       else
                                          xml=xml+"<flag>success</flag>";
                                       
                                       
                                       xml=xml+"<AssetTypeCode>"+AssetTypeCode+"</AssetTypeCode><AssetTypeDesc>"+AssetTypeDesc+"</AssetTypeDesc><status>LIVE</status>";
                                       pstmt.close();
                                                   }
                                       else
                                       {
                                           System.out.println("This is Else Loop");
                                           xml=xml+"<flag>AlreadyExist</flag>";
                                       }
                                                   
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
                        results2 = statement.executeQuery("select asset_type_code,asset_type_desc,status from com_mst_assets_type order by asset_type_code");
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                           { 
                                                            
                               String AssetTypeCode1=results2.getString("asset_type_code");
                               String AssetTypeDesc1=results2.getString("asset_type_desc");
                               
                              //<PayName>" + PayName + "</PayName>;
                               xml=xml+ "<AssetTypeCode>" + AssetTypeCode1 + "</AssetTypeCode><AssetTypeDesc>" + AssetTypeDesc1 + "</AssetTypeDesc>"+"<status>"+results2.getString("status")+"</status>";
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
