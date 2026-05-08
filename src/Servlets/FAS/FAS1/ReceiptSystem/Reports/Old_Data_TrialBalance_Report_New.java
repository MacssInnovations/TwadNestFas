package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.MathContext;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class Old_Data_TrialBalance_Report_New extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
     
    }
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, 
                                                           IOException {
      
       try {
           HttpSession session = request.getSession(false);
           if (session == null) {
               System.out.println(request.getContextPath() + "/index.jsp");
               response.sendRedirect(request.getContextPath() + "/index.jsp");
               return;
           }
           System.out.println(session);

       } catch (Exception e) {
           System.out.println("Redirect Error :" + e);
       }
       Connection con = null;
       ResultSet rs = null, rs2 = null, rs3 = null;
       //CallableStatement cs=null,cs1=null;
       PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null;
       String xml = "",q="";
       String strCommand = "";
       try {
           ResourceBundle rs1 = 
               ResourceBundle.getBundle("Servlets.Security.servlets.Config");
           String ConnectionString = "";

           String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
           String strdsn = rs1.getString("Config.DSN");
           String strhostname = rs1.getString("Config.HOST_NAME");
           String strportno = rs1.getString("Config.PORT_NUMBER");
           String strsid = rs1.getString("Config.SID");
           String strdbusername = rs1.getString("Config.USER_NAME");
           String strdbpassword = rs1.getString("Config.PASSWORD");
          
           
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection



           Class.forName(strDriver.trim());
           con = 
       DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                           strdbpassword.trim());
       } catch (Exception e) {
           System.out.println("Exception in opening connection :" + e);
           //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

       }

       response.setContentType(CONTENT_TYPE);
       response.setHeader("Cache-Control", "no-cache");
       PrintWriter out = response.getWriter();
      ResultSet    rs1=null;
       try {
           strCommand = request.getParameter("Command");
           System.out.println("assign..here command..." + strCommand);
       }

       catch (Exception e) {
           System.out.println("Exception in assigning..." + e);
       }         

/** Get Cash Book Month and Year */ 
String txtCB_Year=request.getParameter("txtCB_Year");
String txtCB_Month=request.getParameter("txtCB_Month");
int year=Integer.parseInt(txtCB_Year);
int month=Integer.parseInt(txtCB_Month);   
System.out.println("year"+year);
System.out.println("month"+month);

//System.out.println("accountheadcode"+accountheadcode);  
if(strCommand.equalsIgnoreCase("loadunit"))
        {  System.out.println("loadunit");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
           // PrintWriter out = response.getWriter();
           // String xml="";
            int oid=0;
            xml="<response><command>"+strCommand+"</command>";
           // try{oid=Integer.parseInt(request.getParameter("oid"));}catch(Exception e){System.out.println("getting office id failed");}
            System.out.println("year"+year);
            System.out.println("month==="+month);
            try 
            {
            	q="SELECT distinct a.ACCOUNTING_UNIT_ID,b.ACCOUNTING_UNIT_NAME "+
                " FROM FAS_TRAIL_BALANCE_DATA_9507 a,FAS_MST_ACCNT_UNIT_OLD9507 b where a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and a.cashbook_year="+year+" and a.cashbook_month="+month;
            	
            ps=con.prepareStatement("SELECT distinct a.ACCOUNTING_UNIT_ID,b.ACCOUNTING_UNIT_NAME "+
                                  " FROM FAS_TRAIL_BALANCE_DATA_9507 a,FAS_MST_ACCNT_UNIT_OLD9507 b where a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and a.cashbook_year=? and a.cashbook_month=? ") ;
            System.out.println("querry >>> "+q);
            ps.setInt(1,year);
            ps.setInt(2,month);
            rs1=ps.executeQuery();
           
                int cnt=0;
            while(rs1.next())
            {
                xml=xml+"<option>"; 
                xml=xml+"<accounting_unit_id>"+rs1.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                xml=xml+"<accounting_unit_name><![CDATA["+rs1.getString("ACCOUNTING_UNIT_NAME")+"]]></accounting_unit_name>";                                                                                                                                                                                                                                            
                xml=xml+"</option>";  
               cnt++;  
                //System.out.println(cnt+"count");
            }
                if(cnt>0) {
                    xml=xml+"<flag>success</flag>"; 
                }
              else if(cnt==0) {
                    xml=xml+"<flag>nodata</flag>";
                }
            }
            
            
            catch(Exception e)
            {
            System.out.println(e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
        }

             
                                                           
               }
    
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        Connection connection=null;
        Statement statement=null;
        CallableStatement cs=null;
        Connection   con=null;
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
       // PrintWriter out = response.getWriter();
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
        
        
        System.out.println("inside servlet");
        String userid=(String)session.getAttribute("UserId");
        String xml=""; 
        ResultSet    rs1=null;
        String strCommand="";   
        PreparedStatement ps=null;
        String Account_unit_Code=request.getParameter("cmbAcc_UnitCode");
        String CashBook_Year=request.getParameter("txtCB_Year");
        String CashBook_Month=request.getParameter("txtCB_Month");
      
        
        
        int AccountUnitCode=0,accountheadcode=0;
        int OfficeCode=0;
        int CashBookYear=0;
        int CashBookMonth=0;
      
        String update_user=(String)session.getAttribute("UserId");
        try 
        {
            AccountUnitCode=Integer.parseInt(Account_unit_Code);
            System.out.println(AccountUnitCode);
          //  OfficeCode=Integer.parseInt(Office_Code);
            CashBookYear=Integer.parseInt(CashBook_Year);
            System.out.println(CashBookYear);
            CashBookMonth=Integer.parseInt(CashBook_Month);
            System.out.println(CashBookMonth);
          
            
            
        }catch(Exception e) 
        {
            System.out.println("Exception in Converting Integer:"+e);    
        }
        //This Two Variables for calculateting cashbookmonth and year
         
         /** Find whether to display all or specific account heads */
         String Specifictype=request.getParameter("SpecificAHC");
        /** Find Whether report should be either html or text or pdf */
      //  String rtype= request.getParameter("txtoption");
        
        String monthInWords="";
        if(CashBookMonth==1)
            monthInWords="January";
        else if(CashBookMonth==2)
            monthInWords="February";
        else if(CashBookMonth==3)
            monthInWords="March";
        else if(CashBookMonth==4)
            monthInWords="April";
        else if(CashBookMonth==5)
            monthInWords="May";
        else if(CashBookMonth==6)
            monthInWords="June";
        else if(CashBookMonth==7)
            monthInWords="July";
        else if(CashBookMonth==8)
            monthInWords="August";
        else if(CashBookMonth==9)
            monthInWords="September";
        else if(CashBookMonth==10)
            monthInWords="October";
        else if(CashBookMonth==11)
            monthInWords="November";
        else if(CashBookMonth==12)
            monthInWords="December";
        /** Get Account Head */ 
        String cmbAccHeadCode=request.getParameter("txtAcc_HeadCode");
        
        
        System.out.println("cmbAccHeadCode is:"+cmbAccHeadCode);    
        System.out.println("inside main");
        if(cmbAccHeadCode!=null)
        {
            try 
            { 
               accountheadcode=Integer.parseInt(cmbAccHeadCode);
              }
            catch(Exception e)
             {
                accountheadcode=0;
              }
         }
        /** Get Account Head */ 
        String flagid=request.getParameter("suppmnt");
        
        
        System.out.println("cmbAccHeadCode is:"+cmbAccHeadCode);    
        System.out.println("inside main");
        if(cmbAccHeadCode!=null)
        {
            try 
            { 
               accountheadcode=Integer.parseInt(cmbAccHeadCode);
              }
            catch(Exception e)
             {
                accountheadcode=0;
              }
         }
         try 
         {
         
                   File reportFile=null;
                   try
                   {
                           System.out.println("calling servlet...");
                       /**
                        *  Single Month + All A/C Head 
                        */
                       
                       if(Specifictype.equalsIgnoreCase("All"))         
                       {
                           reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/old_trilbalance_data9507.jasper"));
                       }
                       /**
                        * Single Month + Single A/C Head 
                        */
                       else
                       {  System.out.println("Single");
                           reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/old_trilbalance_data9507_AllAC.jasper"));
                                 // reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/old_trilbalance_data9507_spAC.jasper")); 
                       }
                           if (!reportFile.exists())
                           throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                               System.out.println("from ...");
                           JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
                           Map map=null;
                           map = new HashMap();
                           
                               map.put("cbMonth",CashBookMonth);
                               map.put("cbYear",CashBookYear);
                               map.put("acunitId",AccountUnitCode);
                               map.put("monthInWords",monthInWords);
                               map.put("acHead",accountheadcode);
                               
                                                          
                               JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,connection);
                               
                               System.out.println("upto");
                           String rtype="PDF";// request.getParameter("cmbReportType");
                           System.out.println(rtype);
                           if (rtype.equalsIgnoreCase("HTML"))   
                           {
                                       response.setContentType("text/html");
                               
                                       response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.html\"");
                                       PrintWriter out = response.getWriter();
                                       JRHtmlExporter exporter = new JRHtmlExporter();
                                       exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                                       exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                                       exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                                       exporter.exportReport();
                                       out.flush();
                                       out.close();
                           }
                           else if (rtype.equalsIgnoreCase("PDF"))   
                           {       System.out.println(rtype+"...inside PDF");
                                       byte buf[] = 
                                         JasperExportManager.exportReportToPdf(jasperPrint);
                                       response.setContentType("application/pdf");
                                       response.setContentLength(buf.length);
                                       //response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
                                       OutputStream out = response.getOutputStream();
                                       out.write(buf, 0, buf.length);
                                       out.close();
                           }
                           else if (rtype.equalsIgnoreCase("EXCEL"))   
                           {
                           
                                   response.setContentType("application/vnd.ms-excel");
                                    response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.xls\"");
                                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
                                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
                                    
                                   ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
                                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
                                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
                                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
                                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
                                    exporterXLS.exportReport(); 
                                    byte []bytes;
                                   bytes = xlsReport.toByteArray();
                                   ServletOutputStream ouputStream = response.getOutputStream();
                                   ouputStream.write(bytes, 0, bytes.length);
                                   ouputStream.flush();
                                   ouputStream.close();
                           
                           }
                           else if (rtype.equalsIgnoreCase("TXT"))   
                           {
                           
                                   response.setContentType("text/plain");
                                   response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.txt\"");
                                    
                               JRTextExporter exporter = new JRTextExporter();
                               exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                               ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                               exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
                               exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
                               exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
                               exporter.exportReport(); 
                               
                                   byte []bytes;
                                   bytes = txtReport.toByteArray();
                                   ServletOutputStream ouputStream = response.getOutputStream();
                                   ouputStream.write(bytes, 0, bytes.length);
                                   ouputStream.flush();
                                   ouputStream.close();
                           
                           }
                   
                   } 
                   catch (Exception ex) 
                   {
                   String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
                   System.out.println(connectMsg);
                   //sendMessage(response,"The Challan Report Creation failed","ok");
                    sendMessage(response,"Unable to display the PDF file","ok");
                   }
                  //////////////////---------------------------- End -----------------
                  System.out.println("here after PDF");
                  //sendMessage(response,"The Trial Balance done successfully","ok");  
                  System.out.println("after send message");
                 
                 
                 
             
         }catch(Exception e) {
             System.out.println("Exception in Main:"+e);
             try{connection.rollback();}catch(SQLException e1){System.out.println("catch:"+e1);}
             String msg="Trial Balance Has failed to Update";
              msg=msg+"<br><br>";
              sendMessage(response,msg,"ok");
              
         }
    }
    
     
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);          
        }
        catch(IOException e)
        {
        System.out.println("ERROR");
        }
    }  
 
    
}