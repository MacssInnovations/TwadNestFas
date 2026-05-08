package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet implementation class TB_download
 */
public class TB_download extends HttpServlet {
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TB_download() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("tb download in xls format");
        Connection connection=null;
        Statement statement=null;
        CallableStatement cs=null;
        ResultSet rs2=null;
        int kk=0;
        
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
        
        String userid=(String)session.getAttribute("UserId");
         
         
        String CashBook_Year=request.getParameter("from_txtCB_Year");
        String CashBook_Month=request.getParameter("from_txtCB_Month");
        String txtUnitId=request.getParameter("txtUnitId");
        
        
        
        
        int CashBookYear=0;
        int CashBookMonth=0,unitid=0;
      
        String update_user=(String)session.getAttribute("UserId");
        try 
        {
          
            CashBookYear=Integer.parseInt(CashBook_Year);
            CashBookMonth=Integer.parseInt(CashBook_Month);
            unitid=Integer.parseInt(txtUnitId);
            
            
            
        }catch(Exception e) 
        {
            System.out.println("Exception in Converting Integer:"+e);    
        }
        //This Two Variables for calculateting cashbookmonth and year
         
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
            
            
         try 
         {
        	
         
                   File reportFile=null;
                   try
                   {
                            try{
                       			response.setContentType("application/vnd.ms-excel");
                                   response.setHeader ("Content-Disposition", "attachment;filename=\"TBDownload Data.xls\"");
                       		//String filename="c:/hello.xls" ;
                       		HSSFWorkbook hwb=new HSSFWorkbook();
                       		HSSFSheet sheet =  hwb.createSheet("new sheet");

                       		HSSFRow rowhead=   sheet.createRow((short)0);
                       		rowhead.createCell((short) 0).setCellValue("UnitId");
                       		rowhead.createCell((short) 1).setCellValue("OfficeId");
                       		rowhead.createCell((short) 2).setCellValue("CashBookYear");
                       		rowhead.createCell((short) 3).setCellValue("CashBookMonth");
                       		rowhead.createCell((short) 4).setCellValue("AccountHeadCode");
                       	//	rowhead.createCell((short) 5).setCellValue("DebitOpeningBalance");
                       	//	rowhead.createCell((short) 6).setCellValue("creditOpeningBalance");
                       		
                       		rowhead.createCell((short) 5).setCellValue("CurrentMonthDebit");
                       		rowhead.createCell((short) 6).setCellValue("CurrentMonthCredit");
                       	//	rowhead.createCell((short) 9).setCellValue("DebitClosingBalance");
                       //		rowhead.createCell((short) 10).setCellValue("CreditClosingBalance");
                       	//	rowhead.createCell((short) 11).setCellValue("UpdatedByUserId");
                       		ServletOutputStream fileOut=null;
                            String sub_qry="";
                       		if(txtUnitId.equalsIgnoreCase("All")){
                       			sub_qry=" ";
                       		}else{
                       			sub_qry=" and accounting_unit_id="+unitid;	
                       		}
                       		
                       		String ss="SELECT *  "+
									" FROM FAS_TRIAL_BALANCE"+
                       				" WHERE accounting_unit_id IN"+
                       				" (SELECT accounting_unit_id"+
                       				" FROM FAS_TRIAL_BALANCE_STATUS"+
                       				" WHERE TB_status   ='Y'"+
                       				" AND cashbook_year ="+CashBookYear+
                       				" AND cashbook_month="+CashBookMonth+
                       				" )"+
                       				" AND cashbook_year  ="+CashBookYear+
                       				" AND cashbook_month ="+CashBookMonth+sub_qry+
                       				" ORDER BY accounting_unit_id";
                            System.out.println("ss::::"+ss);
                           PreparedStatement ps2=connection.prepareStatement(ss);
                          rs2=ps2.executeQuery();
                          int j=1;
                           while(rs2.next())
                           {
                        	  
                       		HSSFRow row=   sheet.createRow((short)j);
                       	
                       		row.createCell((short) 0).setCellValue(rs2.getInt("ACCOUNTING_UNIT_ID"));
                       		row.createCell((short) 1).setCellValue(rs2.getString("ACCOUNTING_FOR_OFFICE_ID"));
                       		row.createCell((short) 2).setCellValue(rs2.getInt("CASHBOOK_YEAR"));
                       		row.createCell((short) 3).setCellValue(rs2.getInt("CASHBOOK_MONTH"));
                       		row.createCell((short) 4).setCellValue(rs2.getInt("ACCOUNT_HEAD_CODE"));
                       		//row.createCell((short) 5).setCellValue(rs2.getDouble("DEBIT_OPENING_BALANCE"));
                       	//	row.createCell((short) 6).setCellValue(rs2.getDouble("CREDIT_OPENING_BALANCE"));
                       		
                       		row.createCell((short) 5).setCellValue(rs2.getDouble("CURRENT_MONTH_DEBIT"));
                       		row.createCell((short) 6).setCellValue(rs2.getDouble("CURRENT_MONTH_CREDIT"));
                       	//	row.createCell((short) 9).setCellValue(rs2.getDouble("DEBIT_CLOSING_BALANCE"));
                       	//	row.createCell((short) 10).setCellValue(rs2.getDouble("CREDIT_CLOSING_BALANCE"));
                       	//	row.createCell((short) 11).setCellValue(rs2.getString("UPDATED_BY_USER_ID"));
                       		
                       		j++;
                       	 }
                           fileOut = response.getOutputStream();
                           hwb.write(fileOut);
                      		fileOut.close();
                       		} catch ( Exception ex ) {
                       		    System.out.println(ex);

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
