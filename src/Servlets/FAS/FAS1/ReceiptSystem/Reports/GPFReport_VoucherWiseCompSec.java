package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import Servlets.Security.classes.UserProfile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class GPFReport_VoucherWiseCompSec extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException 
    {
    	
            response.setContentType(CONTENT_TYPE);
            
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
            
            /** Get User ID */
            String userid=(String)session.getAttribute("UserId");
            String empid = userid.substring(4,userid.length());
            System.out.println("Empid -------------->"+empid);
            
            /**
             * Variables Declaration 
             */        
            Connection con=null;
            
            /** Combo Loading */
            PreparedStatement ps=null;        
            ResultSet rs=null;  
            
           /**
            * Database Connection 
            */
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
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
              {
                  System.out.println("Exception in opening connection :"+e);
              }
              
              /** xml */
              String xml="";
        
        
                 String Command=request.getParameter("Command");
                 System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++="+Command);
                 
                 int headcode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                 if(Command.equalsIgnoreCase("checkCode")) {
            try {
            
                 
                     ps=con.prepareStatement("select ACCOUNT_HEAD_DESC,ACCOUNT_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE='"+headcode+"'");
                     rs=ps.executeQuery();
                     xml="<response><command>Load_Head_dec</command>";
                     if (rs.next()) {
                     xml=xml+"<flag>success</flag>";  
                         xml=xml+"<code>"+rs.getInt("account_head_code")+"</code>";  
                     xml=xml+"<des>"+rs.getString("ACCOUNT_HEAD_DESC")+"</des>";  
                 
                 
                     
                 }
                 else {
                     xml=xml+"<flag>Failure</flag>";    
                 }
        
        
            
        }
        catch(Exception e) {
            xml=xml+"<flag>Failure</flag>";    
            System.out.println(e);
        }
         
                 } 
                 else
                 {
            
            
             /** Query */         
             
              String sql_all=" select                                  \n" + 
              "                account_head_code,                      \n" + 
              "                account_head_desc                       \n" + 
              "              from                                      \n" + 
              "                com_mst_account_heads                   \n" + 
              "              where                                     \n" + 
              "                account_head_code in (                  \n" + 
              "               select \n" + 
              "                  account_head_code\n" + 
              "                from fas_mst_sections_groups_heads \n" + 
              "                where section_id in (\n" + 
              "                SELECT sectionid  \n" + 
              "                FROM fas_section_roles\n" + 
              "                WHERE userid =  "+empid+" \n" + 
              "                )  )          \n" + 
              "               order by account_head_desc               ";
             
              
              int cnt=0;
        
             /** Fetch Section ID and Name from DB */
             try
             {
                 ps=con.prepareStatement(sql_all);
                 rs=ps.executeQuery();
                xml="<response><command>Load_Section_Heads</command>";
                 while (rs.next()) {
                    xml=xml+"<Section_Pair>";                     
                     xml=xml+"<section_id>"+rs.getInt("account_head_code")+"</section_id>";  
                     xml=xml+"<section_name>"+rs.getString("account_head_desc")+"</section_name>";                      
                    xml=xml+"</Section_Pair>";   
                    cnt++;
                 }
          
          
                if(cnt==0) {
                    xml=xml+"<flag>NoData</flag>";   
                }
                else{
                    xml=xml+"<flag>Success</flag>";   
                }
            }
            catch(Exception e) {
                xml=xml+"<flag>Failure</flag>";    
                System.out.println(e);
            }
                 }
        
            xml=xml+"</response>";               
            out.println(xml);
            System.out.println(xml);
               

    }      

public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
	System.out.println("GPFReport_VoucherWiseCompSec");
        /**
         * Variables Declarations 
         */
        Connection connection = null;
        Statement statement = null;
        ResultSet results=null,rs2=null,rs1=null;
        PreparedStatement ps=null,ps2=null,ps1=null;

        /**
         * Database Connection 
         */
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }


        /**
         * Set Content Type 
         */
        response.setContentType(CONTENT_TYPE);


        /** 
         *   Session Checking 
         */ 
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

        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);

        
        /**
         * Get Cashbook_month and Cashbook Year for Single Month 
         */
        int CashBook_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        int CashBook_Month = Integer.parseInt(request.getParameter("txtCB_Month"));    
        
        
        /**
         * Get Cashbook_month and Cashbook Year for more than one Month  
         */
       
        java.util.Date d=null; 
        java.util.Date d1=null;  
        int oid=0;
        String office_level_id="";
        
        String fromdate=request.getParameter("txtfromdate");
        String todate=request.getParameter("txttodate");
        
        java.sql.Date dateOfAttachment=null;
        System.out.println("before converting date");
        String dateString = fromdate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                          
        try {
            d = dateFormat.parse(fromdate.trim());
            System.out.println("util date is:"+d);
            dateFormat.applyPattern("yyyy-MM-dd");
            dateString = dateFormat.format(d);
            dateOfAttachment = java.sql.Date.valueOf(dateString);
            
            java.sql.Date dateto=null;
            System.out.println("before converting date");
            String dateString1 = todate;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");           
            d1 = dateFormat1.parse(todate.trim());
            dateFormat1.applyPattern("yyyy-MM-dd");
            dateString1 = dateFormat1.format(d1);
            dateto = java.sql.Date.valueOf(dateString1);
         
        }
        catch (ParseException e) {
            System.out.println(e);
        }
       
       
        System.out.println("From Date ---->"+d);
        System.out.println("From Date ---->"+d1);
       
        
        //int accountheadcode = Integer.parseInt(request.getParameter("txtSectionHeads"));
        int accountheadcode = Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
        int txtSortingOrder = Integer.parseInt(request.getParameter("txtSortingOrder"));
        String command = request.getParameter("command");
        
        System.out.println("CashBook_Year---"+CashBook_Year);
        System.out.println("CashBook_Month--"+CashBook_Month);        
        System.out.println("Account Head Code--"+accountheadcode);
        System.out.println("Sorting Order --"+txtSortingOrder);
        
        String SortingOrder="";
        
        if ( txtSortingOrder == 11)
           SortingOrder=" order by debit_amt ";
        else if (txtSortingOrder == 22 )
           SortingOrder=" order by credit_amt ";
        else if (txtSortingOrder == 33 )
           SortingOrder=" order by unitid, doc_date ";           
        else if (txtSortingOrder == 44 )
           SortingOrder=" order by doc_date, debit_amt ";           
        else if (txtSortingOrder == 55 )
           SortingOrder=" order by doc_date, credit_amt  ";         
        else if (txtSortingOrder == 66 )
           SortingOrder=" order by doc_date, net_amt  ";    
           
        UserProfile up=(UserProfile)session.getAttribute("UserProfile");

       int empid1= up.getEmployeeId();
       System.out.println("======================================================================>"+empid1);
        /**
         * Find Month Value 
         */
        String monthInWords = "";
        if (CashBook_Month == 1)
            monthInWords = "January";
        else if (CashBook_Month == 2)
            monthInWords = "February";
        else if (CashBook_Month == 3)
            monthInWords = "March";
        else if (CashBook_Month == 4)
            monthInWords = "April";
        else if (CashBook_Month == 5)
            monthInWords = "May";
        else if (CashBook_Month == 6)
            monthInWords = "June";
        else if (CashBook_Month == 7)
            monthInWords = "July";
        else if (CashBook_Month == 8)
            monthInWords = "August";
        else if (CashBook_Month == 9)
            monthInWords = "September";
        else if (CashBook_Month == 10)
            monthInWords = "October";
        else if (CashBook_Month == 11)
            monthInWords = "November";
        else if (CashBook_Month == 12)
            monthInWords = "December";            
   
        File reportFile = null;
        
        
        try {
                                  
                                   ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                                   ps.setInt(1,empid1);
                                   results=ps.executeQuery();
                                   if(results.next())
                                   {
                                   oid=results.getInt("OFFICE_ID");
                                   System.out.println("-----------------------------------------------------------------------------------"+oid);
                                   }
                                   results.close();
                                   ps.close();   
                                   
                               }
                               catch(Exception e) {
                                   System.out.println(e);
                               }
        
        
        
        try
        {
            System.out.println("*****************************************************************BEFORE"+oid);
        ps2 =
        connection.prepareStatement("select OFFICE_LEVEL_ID  from COM_MST_OFFICES  where OFFICE_ID=?");
        ps2.setInt(1, oid);
        rs2 = ps2.executeQuery();
        if(rs2.next())
        {
        office_level_id=rs2.getString("OFFICE_LEVEL_ID");
            System.out.println("*****************************************************************office_level_id"+office_level_id);
        if(office_level_id.equals("AW")) {
        
            System.out.println("*****************************************************************Enter"+office_level_id);
            ps1 =
            connection.prepareStatement("select REGION_OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  where OFFICE_ID=?");
            ps1.setInt(1, oid);
            rs1 = ps1.executeQuery();
            if(rs1.next()) {
                
                oid=rs1.getInt("REGION_OFFICE_ID");
                
            }
            
            
            
        }
        }
        }
        catch(Exception e) {
        System.out.println(e);
        
        }
        
        
        System.out.println("*****************************************************************"+oid);
        
        
        
        
        
        
        
        
        
        
        
        
   
        try {
        
        
          /**display the all officeid for Head office*/
        
           if(oid==5000)
           {
             System.out.println("enter into the Head Office*********************************************************");
           
               if (txtSortingOrder == 33 )
               {            
                  if (command.equalsIgnoreCase("one"))  {
                    reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withMonth_unitWise_Total_Headoffice.jasper"));
                  }
                  else if (command.equalsIgnoreCase("morethanone")) {
                  System.out.println("Enter into morethan one month 33*******************************************");
                    reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withDate_unitWise_Total_Headoffice.jasper"));  
                  }
                  else if (command.equalsIgnoreCase("morethanone_sup")) 
                  {
                   // reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise_SJV/GPF_VoucherWise_withDate_Headoffice_sup.jasper"));  
                	  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise_SJV/GPF_VoucherWise_withDate_HO_sup_unitwise.jasper"));
                  }
               } 
               else if (txtSortingOrder == 77){
            	   if (command.equalsIgnoreCase("morethanone"))  {
            		   System.out.println("second option");
            		   reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withMonth_HeadWise_Abstract1_date.jasper"));
                     
            	   		}
            	   else if (command.equalsIgnoreCase("morethanone_sup")) 
                   {
                 	  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise_SJV/GPF_VoucherWise_withDate_HO_sup_unitwise_Abs.jasper"));
                   }
               }
               else 
               {
               System.out.println("888888888888888888888888888888888888888888888888888 else");
                   if (command.equalsIgnoreCase("one"))  
                   {
                	   
                   System.out.println("++++++++++++++++++++++++++++++++++ Else ");
                     reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withMonth_Headoffice.jasper"));
                   }
                   else if (command.equalsIgnoreCase("morethanone")) 
                   {
                	   System.out.println("second option");
                     reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withDate_Headoffice.jasper"));  
                   }
                   else if (command.equalsIgnoreCase("morethanone_sup")) 
                   {
                	   System.out.println("third option");
                     reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise_SJV/GPF_VoucherWise_withDate_Headoffice_sup.jasper"));  
                   }
               }
           
           
           
           
           
           }
        else
        {
           /** If displaying order is Unit wise and doc wise , Unit wise sub total is needed */
           if (txtSortingOrder == 33 )
           {            
              if (command.equalsIgnoreCase("one"))  {
                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withMonth_unitWise_Total.jasper"));
              }
              else if (command.equalsIgnoreCase("morethanone")) {
              System.out.println("Enter into morethan one month 33*******************************************");
                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withDate_unitWise_Total.jasper"));  
              }
              else if (command.equalsIgnoreCase("morethanone_sup")) 
              {
            	  System.out.println("33 in unitwise:");
                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise_SJV/GPF_VoucherWise_withDate_Headoffice_sup.jasper"));  
              }
           } 
           else if (txtSortingOrder == 77){
           if (command.equalsIgnoreCase("morethanone")) 
            {
             
        		 System.out.println("morethanone::::");
        		
        		reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withMonth_unitWise_Abstract1_date.jasper"));  
            }
           else if (command.equalsIgnoreCase("morethanone_sup")) 
           {
         	  System.out.println("33 in unitwise:");
             reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise_SJV/GPF_VoucherWise_withDate_Headoffice_sup_Abs1.jasper"));  
           }
           
           }
           else 
           {
           System.out.println("888888888888888888888888888888888888888888888888888 else");
               if (command.equalsIgnoreCase("one"))  
               {
               System.out.println("++++++++++++++++++++++++++++++++++ for other Else ");
                 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withMonth.jasper"));
               }
               else if (command.equalsIgnoreCase("morethanone")) 
               {
            	   System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise/GPF_VoucherWise_withDate.jasper"));  
               }
               else if (command.equalsIgnoreCase("morethanone_sup")) 
               {
                 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_VoucherWise_SJV/GPF_VoucherWise_withDate_Headoffice_sup.jasper"));  
               }
           }
        }
          
            if (!reportFile.exists()) throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
            JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
            System.out.println("reportFile"+reportFile);
            System.out.println("Command"+command);
            Map map = null;
            map = new HashMap();
            System.out.println("SortingOrder:::"+SortingOrder);
            if (command.equalsIgnoreCase("one"))              
            {
                map.put("cb_mon", CashBook_Month);
                map.put("cb_year", CashBook_Year);            
                map.put("monthvalue", monthInWords);                    
                map.put("accountheadcode", accountheadcode);
                map.put("sortingorder", SortingOrder);
                map.put("officeid", oid);
            }
            else if (command.equalsIgnoreCase("morethanone")) 
            {
                map.put("fromdate",d);
                map.put("todate",d1);
                map.put("accountheadcode", accountheadcode);
                map.put("sortingorder", SortingOrder);
                map.put("officeid", oid);
                              
            }
            else if (command.equalsIgnoreCase("morethanone_sup")) 
            {
           	 int supno = Integer.parseInt(request.getParameter("supno"));
            	 map.put("fromdate",d);
                 map.put("todate",d1);
                 map.put("accountheadcode", accountheadcode);
                 map.put("sortingorder", SortingOrder);
                 map.put("officeid", oid);
                 map.put("supno", supno);
            }
            
            
            
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
            String rtype = "PDF";
            if (rtype.equalsIgnoreCase("PDF")) {
             
                byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"GPFReport.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }


        } catch (Exception ex) {
            String connectMsg ="Could not create the report " + ex.getMessage(); 
            String con_err ="Could not create the report " + ex; 
            System.out.println(con_err);
            System.out.println(connectMsg);

        }


    }
}

