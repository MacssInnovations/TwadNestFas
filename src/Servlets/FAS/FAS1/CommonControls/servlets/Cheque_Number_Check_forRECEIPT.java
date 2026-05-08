package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Cheque_Number_Check_forRECEIPT extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
        
        
        /**
         * Set Content Type 
         */
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        
        
        /**
         * Session Checking 
         */
        HttpSession session=request.getSession(false);
        try
         {
             
             if(session==null)
             {
                 System.out.println(request.getContextPath()+"/index.jsp");
                 response.sendRedirect(request.getContextPath()+"/index.jsp");
                 return;
             }
             System.out.println(session);
                 
         }catch(Exception e)
         {
         System.out.println("Redirect Error :"+e);
         }
    
    
        /**
         * Variables Declaration 
         */
        int cmbAcc_UnitCode=0;  
         
        Connection con=null;
        ResultSet rs=null;
        ResultSet rsm=null;
        PreparedStatement ps=null;
        PreparedStatement psm=null;
        
        /**
         * Database Connection 
         */
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
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
              {
                  System.out.println("Exception in opening connection :"+e);
              }
              
         
        /**
         * Get Cheque Number 
         */
        String cheque_dd_no=request.getParameter("cheque_no");
        System.out.println("cheque no --->"+cheque_dd_no);
         
                 
        
        /**
         * Get Accounting Unit ID 
         */
        try
          {
            cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          }
        catch(NumberFormatException e)
          {
             System.out.println("exception"+e );
          }
          
        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
         
          
        
          
        String xml="";
        xml="<response><command>check_cheque_no</command>";
        
        
        
        String sql="select CHEQUE_DD_NO, AMOUNT, CASHBOOK_YEAR, CASHBOOK_MONTH, RECEIPT_NO from FAS_RECEIPT_TRANSACTION where CHEQUE_DD_NO='"+cheque_dd_no+"'and ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
        System.out.println(sql);
        
        
        try
        {
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            System.out.println("query executed ");
            int i=0;
            while(rs.next())
              {
              
                      try
                            {
                                
                                xml=xml+"<cheq_no>"+rs.getString("CHEQUE_DD_NO")+"</cheq_no>";
                                String sqlm="select * from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+rs.getInt("CASHBOOK_YEAR")+" and CASHBOOK_MONTH="+rs.getInt("CASHBOOK_MONTH")+" and RECEIPT_NO="+rs.getInt("RECEIPT_NO")+" and RECEIPT_STATUS='L' ";
                                   {
                                        psm=con.prepareStatement(sqlm);
                                        System.out.println(sqlm);
                                        rsm=psm.executeQuery();
                                        System.out.println("query executed ");
                                        while(rsm.next())
                                             {
                                                    xml=xml+"<amount>"+rsm.getInt("TOTAL_AMOUNT")+"</amount>";
                                                    xml=xml+"<cbyear>"+rsm.getInt("CASHBOOK_YEAR")+"</cbyear>";
                                                    xml=xml+"<cbmonth>"+rsm.getInt("CASHBOOK_MONTH")+"</cbmonth>";
                                                    xml=xml+"<vo_no>"+rsm.getInt("RECEIPT_NO")+"</vo_no>";
                                                    xml=xml+"<crm>"+rsm.getString("CREATED_BY_MODULE")+"</crm>";
                                                    i++;
                                             }
                                   }  
                            } 
                            catch(Exception e) 
                            {
                                    System.out.println("Error to Fetch data in FAS_RECEIPT_MASTER");
                                    xml=xml+"<flag>Failure</flag>";
                         
                            }
            
            }   // while loop end 
            if(i>=1)
            {
             xml=xml+"<flag>Success</flag>";
            }
            else
            {
             xml=xml+"<flag>Failure</flag>";   
            }
            
        }
        catch(Exception e )
        {
         System.out.println("Failed to Fetch Cheque/DD Details" +e );  
         xml=xml+"<flag>Failure</flag>";
        }
        
    
        xml=xml+"</response>";
        out.println(xml);
        System.out.println(xml);
        out.close();
        
        }
              
 }

