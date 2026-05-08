package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AssetClassMasterServlet extends HttpServlet 
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
        int AssetClassCode=0;
        String AssetClassDesc="";
        String AssetType="";
        
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
        
            AssetClassDesc = request.getParameter("AssetClassDesc");
            AssetType=request.getParameter("AssetType");
            System.out.println("Class desc:"+AssetClassDesc);
            System.out.println("AssetType is:"+AssetType);
        }
        
        catch(Exception e)
        { 
            System.out.println("in getting values in all other values **** "+ e);
        }

        try
        {
          AssetClassCode= Integer.parseInt(request.getParameter("AssetClassCode"));
          
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
                        PreparedStatement pstmt = connection.prepareStatement("delete from com_mst_assets_class where ASSET_CLASS_CODE=?");
                                            System.out.println(pstmt);
                                                pstmt.setInt(1,AssetClassCode);
                                                pstmt.executeUpdate();
                                                xml=xml+"<flag>success</flag><AssetClassCode>"+AssetClassCode+"</AssetClassCode>";
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
                        CallableStatement pstmt = connection.prepareCall("call FAS_ASSETCLASSMASTERPROCEDURE(?::numeric,?,?,?::numeric,?,?,?)");
                        System.out.println(pstmt);
                            pstmt.setInt(1, AssetClassCode);
                            pstmt.setString(2, AssetClassDesc);
                            pstmt.registerOutParameter(4,Types.NUMERIC);
                            pstmt.setNull(4,java.sql.Types.NUMERIC);
                            pstmt.setString(5,"update");
                            pstmt.setString(3,AssetType);
                            pstmt.setString(6,userid);
                            long l=System.currentTimeMillis();
                            Timestamp ts=new Timestamp(l);
                            pstmt.setTimestamp(7,ts);
                            
                            pstmt.execute();
                            //int errcode=pstmt.getInt(4);
                            int errcode= pstmt.getBigDecimal(4).intValue();
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
                       CallableStatement pstmt = connection.prepareCall("call FAS_ASSETCLASSMASTERPROCEDURE(?::numeric,?,?,?::numeric,?,?,?)");
                       
                                       pstmt.setInt(1,AssetClassCode);
                                       pstmt.setString(2,AssetClassDesc);
                                       pstmt.registerOutParameter(4,Types.INTEGER);
                                       pstmt.setNull(4,java.sql.Types.NUMERIC);
                                       pstmt.setString(5,"insert");
                                       pstmt.registerOutParameter(1,Types.INTEGER);
                                       pstmt.setNull(1,java.sql.Types.NUMERIC);
                                       pstmt.setString(3,AssetType);
                                       pstmt.setString(6,userid);
                                       long l=System.currentTimeMillis();
                                       Timestamp ts=new Timestamp(l);
                                       pstmt.setTimestamp(7,ts);
                                       pstmt.executeUpdate();
                                       int AssetClassCode1 = pstmt.getInt(1);
                                       System.out.println("value is:"+pstmt.getInt(1));
                                       //int errcode=pstmt.getInt(4);
                                       int errcode= pstmt.getBigDecimal(4).intValue();
                                       System.out.println("SQLCODE:::"+errcode);
                                       if(errcode!=0)
                                       {
                                              xml=xml+"<flag>failure</flag>";
                                       }
                                       else
                                          xml=xml+"<flag>success</flag>";
                                       
                                       System.out.println(AssetClassCode1);
                                       xml=xml+"<AssetClassCode>"+AssetClassCode1+"</AssetClassCode><AssetClassDesc>"+AssetClassDesc+"</AssetClassDesc><AssetType>"+AssetType+"</AssetType>";
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
                        results2 = statement.executeQuery("select asset_class_code,ASSET_CLASS_DESC,ASSET_TYPE_CODE from com_mst_assets_class order by ASSET_CLASS_CODE");
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                           { 
                                                            
                               int AssetClassCode1=results2.getInt("ASSET_CLASS_CODE");
                               String AssetClassDesc1=results2.getString("ASSET_CLASS_DESC");
                               String AssetType1=results2.getString("ASSET_TYPE_CODE");
                               
                               
                              //<PayName>" + PayName + "</PayName>;
                               xml=xml+ "<AssetClassCode>" + AssetClassCode1 + "</AssetClassCode><AssetClassDesc>" + AssetClassDesc1 + "</AssetClassDesc><AssetType>"+AssetType1+"</AssetType>";
                           }
                             }catch(Exception aee){System.out.println("Exception in the getting values OF GET: " + aee);}  
                           results2.close();
                           //response.setHeader("cache-control","no-cache");
                      }
                      catch(Exception e1)
                      {             System.out.println("Exception is in Get"+e1);
                          xml=xml+"<flag>failure</flag>";
                      }
                  xml=xml+"</response>";
              }  
        else if(strCommand.equals("Asset"))
              { 
              xml="<response><command>Asset</command>";
                  try {
                   System.out.println("bef res");
                   
                       //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                        results2 = statement.executeQuery("select asset_type_code,ASSET_type_DESC from com_mst_assets_type order by ASSET_type_CODE");
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                           { 
                                                            
                               String AssetTypeCode1=results2.getString("ASSET_type_CODE");
                               String AssetTypeDesc1=results2.getString("ASSET_type_DESC");
                               
                               
                               
                              //<PayName>" + PayName + "</PayName>;
                               xml=xml+ "<options><AssetTypeCode>" + AssetTypeCode1 + "</AssetTypeCode><AssetTypeDesc>" + AssetTypeDesc1 + "</AssetTypeDesc></options>";
                           }
                             }catch(Exception aee){System.out.println("Exception in the getting values OF Asset: " + aee);}  
                           results2.close();
                           //response.setHeader("cache-control","no-cache");
                      }
                      catch(Exception e1)
                      {             System.out.println("Exception is in Get"+e1);
                          xml=xml+"<flag>failure</flag>";
                      }
                  xml=xml+"</response>";
              } 
              
        else if(strCommand.equals("Load"))
              { 
              xml="<response><command>Get</command>";
                  try {
                   System.out.println("bef res");
                   
                       //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                        results2 = statement.executeQuery("select asset_class_code,ASSET_CLASS_DESC,ASSET_TYPE_CODE from com_mst_assets_class where asset_type_code='"+AssetType+"' order by ASSET_CLASS_CODE");
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                           { 
                                                            
                               int AssetClassCode1=results2.getInt("ASSET_CLASS_CODE");
                               String AssetClassDesc1=results2.getString("ASSET_CLASS_DESC");
                               String AssetType1=results2.getString("ASSET_TYPE_CODE");
                               
                               
                              //<PayName>" + PayName + "</PayName>;
                               xml=xml+ "<AssetClassCode>" + AssetClassCode1 + "</AssetClassCode><AssetClassDesc>" + AssetClassDesc1 + "</AssetClassDesc><AssetType>"+AssetType1+"</AssetType>";
                           }
                             }catch(Exception aee){System.out.println("Exception in the getting values OF GET: " + aee);}  
                           results2.close();
                           //response.setHeader("cache-control","no-cache");
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
