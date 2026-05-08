package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DepreciationMasterServlet extends HttpServlet 
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
        int AssetClassCode=0;
        String AssetClassDesc="";
        String AssetType="";
        String Financial_Year="";
        double Depreciation_Rate=0;
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
            Financial_Year=request.getParameter("FinancialYear");
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
          Depreciation_Rate=Double.parseDouble(request.getParameter("DepreciationRate"));
          
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
                        System.out.println("asst inside delete"+AssetClassCode);
                        System.out.println("finyear delete"+Financial_Year);
                        PreparedStatement pstmt = connection.prepareStatement("delete from com_mst_depreciation_rates where ASSET_CLASS_CODE=? and financial_year=?");
                                            System.out.println(pstmt);
                                                pstmt.setInt(1,AssetClassCode);
                                                pstmt.setString(2,Financial_Year);
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
                        PreparedStatement  pstmt = connection.prepareStatement("update com_mst_depreciation_rates set DEPRECIATION_RATE=? where ASSET_CLASS_CODE=? and financial_year=?");
                        System.out.println(pstmt);
                            
                            
                            pstmt.setDouble(1,Depreciation_Rate);
                            pstmt.setInt(2,AssetClassCode);
                            pstmt.setString(3,Financial_Year);
                            
                            int ii=pstmt.executeUpdate();
                            
                            if(ii>=1)
                            {
                                   xml=xml+"<flag>success</flag>";
                            }
                            else
                               xml=xml+"<flag>failure</flag>";
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
                                PreparedStatement ps=connection.prepareStatement("select asset_class_code from com_mst_depreciation_rates where asset_class_code=? and financial_year=?");
                                ps.setInt(1,AssetClassCode);
                                ps.setString(2,Financial_Year);
                                ResultSet res=ps.executeQuery();
                                if(!res.next())
                                {
                                    PreparedStatement pstmt=connection.prepareStatement("insert into com_mst_depreciation_rates(ASSET_CLASS_CODE,FINANCIAL_YEAR,DEPRECIATION_RATE,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?)");
                       
                                       pstmt.setInt(1,AssetClassCode);
                                       pstmt.setString(2,Financial_Year);
                                       pstmt.setDouble(3,Depreciation_Rate);
                                       pstmt.setString(4,userid);
                                       long l=System.currentTimeMillis();
                                       Timestamp ts=new Timestamp(l);
                                       pstmt.setTimestamp(5,ts);
                                       int ii=pstmt.executeUpdate();
                                       
                                       if(ii>=1)
                                       {
                                              xml=xml+"<flag>success</flag>";
                                       }
                                       else
                                          xml=xml+"<flag>failure</flag>";
                                       
                                       
                                       xml=xml+"<AssetClassCode>"+AssetClassCode+"</AssetClassCode><FinancialYear>"+Financial_Year+"</FinancialYear><DepreciationRate>"+Depreciation_Rate+"</DepreciationRate>";
                                       pstmt.close();
                                    }
                                    else
                                    {
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
                   System.out.println("bef res"+Financial_Year);
                   
                       //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                        results2 = statement.executeQuery("select a.asset_class_code,a.ASSET_CLASS_DESC,a.ASSET_TYPE_CODE,b.financial_year,b.depreciation_rate from com_mst_assets_class a,com_mst_depreciation_rates b where a.asset_class_code=b.asset_class_code and b.financial_year='"+Financial_Year+"' order by a.ASSET_CLASS_CODE");
                        
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                           { 
                                                            
                               int AssetClassCode1=results2.getInt("ASSET_CLASS_CODE");
                               String AssetClassDesc1=results2.getString("ASSET_CLASS_DESC");
                               String AssetType1=results2.getString("ASSET_TYPE_CODE");
                               String FinancialYear=results2.getString("Financial_Year");
                               String DepreciationRate=results2.getString("depreciation_rate");
                               
                               
                               
                              //<PayName>" + PayName + "</PayName>;
                               xml=xml+ "<AssetClassCode>" + AssetClassCode1 + "</AssetClassCode><AssetClassDesc>" + AssetClassDesc1 + "</AssetClassDesc><AssetType>"+AssetType1+"</AssetType><FinancialYear>"+FinancialYear+"</FinancialYear><DepreciationRate>"+DepreciationRate+"</DepreciationRate>";
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
                        results2 = statement.executeQuery("select asset_class_code,ASSET_class_DESC from com_mst_assets_class order by ASSET_class_CODE");
                          System.out.println("aft res");
                             try
                             {
                                 xml=xml+"<flag>success</flag>";
                             while(results2.next())
                           { 
                                                            
                               int AssetClassCode1=results2.getInt("ASSET_class_CODE");
                               String AssetClassDesc1=results2.getString("ASSET_class_DESC");
                               
                               
                               
                              //<PayName>" + PayName + "</PayName>;
                               xml=xml+ "<options><AssetClassCode>" + AssetClassCode1 + "</AssetClassCode><AssetClassDesc>" + AssetClassDesc1 + "</AssetClassDesc></options>";
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
                
                System.out.println("xml is : " + xml);
                pw.write(xml);
                pw.flush();
                pw.close();
        
    }
}
