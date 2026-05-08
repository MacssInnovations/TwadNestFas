package Servlets.FAS.FAS1.YourSelfCheque.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class YourSelfCheque_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public YourSelfCheque_Report() {
        super();
       
    }
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null,rs1=null,result=null,rs2 = null;
	PreparedStatement ps1 = null;
	//ResultSet rs1,result = null;
	PreparedStatement ps2 = null;
	//ResultSet 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		 System.out.println("Welcome Servlet");
		 PrintWriter out = response.getWriter();
		    
			String cmd=request.getParameter("command");
		    String xml="";
		    int count=0;
		    int cmbAcc_UnitCode = 0,cmbOffice_code=0;
		    
		    try {
				ResourceBundle rsb = ResourceBundle
						.getBundle("Servlets.Security.servlets.Config");
				String ConnectionString = "";

				String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
				String strdsn = rsb.getString("Config.DSN");
				String strhostname = rsb.getString("Config.HOST_NAME");
				String strportno = rsb.getString("Config.PORT_NUMBER");
				String strsid = rsb.getString("Config.SID");
				String strdbusername = rsb.getString("Config.USER_NAME");
				String strdbpassword = rsb.getString("Config.PASSWORD");

//				ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
//						+ strportno.trim() + ":" + strsid.trim();
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
				Class.forName(strDriver.trim());
				con = DriverManager.getConnection(ConnectionString,
						strdbusername.trim(), strdbpassword.trim());
				
			} catch (Exception e) {
				System.out.println("Exception in openeing connection:" + e);
			}
			try
		    {
		         cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		    } 
		    catch (Exception e) 
		    {
		        System.out.println("Exception to catch cmbAcc_UnitCode ");
		    }
		    try
		    {
		        cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		    }
		    catch (Exception e) 
		    {
		        System.out.println("Exception to catch cmbOffice_code ");
		    } 
		    int year=Integer.parseInt(request.getParameter("txtCB_Year"));
			int month=Integer.parseInt(request.getParameter("txtCB_Month"));//System.out.println("cash book month------"+month);
			
			 if(cmd.equalsIgnoreCase("check"))
			{
				xml="<response><command>check</command>";				
				try
							{										
								ps=con.prepareStatement("select VOUCHER_NO from FAS_YOURSELF_CHEQUE_MST where CASHBOOK_YEAR='"+year+"' and CASHBOOK_MONTH='"+month+"' and ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code);
								rs=ps.executeQuery();
								while(rs.next())
								{
									xml=xml+"<memono>"+rs.getInt("VOUCHER_NO")+"</memono>";	
								    count++;
							   	}
								if(count>0)
									xml = xml + "<flag>success</flag>";	
								else
									{
										xml = xml + "<flag>Nodata</flag>";
									}								
							}
							catch(Exception e)
							{
								xml = xml + "<flag>failure</flag>";
								System.out.println(e);
							}

				xml = xml + "</response>";
				System.out.println(xml);			
			}						
			out.write(xml);
		    out.close();	
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




	    int cmbAcc_UnitCode = 0,cmbOffice_code=0;
		String unit="";
	    
	    try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

//			ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
//					+ strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try
	    {
	         cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    } 
	    catch (Exception e) 
	    {
	        System.out.println("Exception to catch cmbAcc_UnitCode ");
	    }
	    try
	    {
	        cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));System.out.println(cmbOffice_code);
	    }
	    catch (Exception e) 
	    {
	        System.out.println("Exception to catch cmbOffice_code ");
	    } 
	    int year=Integer.parseInt(request.getParameter("txtCB_Year"));System.out.println(year);
		int month=Integer.parseInt(request.getParameter("txtCB_Month"));System.out.println("cash book month------"+month);
	    String rtype= "PDF";System.out.println(rtype);
		int vou_no=Integer.parseInt(request.getParameter("txtAdvice_No"));System.out.println(vou_no);

		File reportFile=null;
		 try {
           
           
                reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/YourSelfCheque/jasper/Yourself_Register.jasper"));
           System.out.println("path of servlet context..."+reportFile);
                
           
          
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        

      //  System.out.println("opt::" + opt);
     //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
        Map map=new HashMap();
        map.put("accountingunitid",cmbAcc_UnitCode);
        map.put("accountofficeid",cmbOffice_code);
        map.put("txtCB_Year",year);
        map.put("txtCB_Month",month);     
        map.put("vou_no",vou_no);
        System.out.println(""+cmbAcc_UnitCode+""+cmbOffice_code+""+year+""+vou_no);
        
   
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
        if (rtype.equalsIgnoreCase("PDF"))   
        {
        	System.out.println("PDF");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"yourself_register.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);System.out.println(buf.length);
                    out.close();
        }

     
    } catch (Exception ex) {
        String connectMsg = 
            "Could not create the report " + ex.getMessage() + " " + 
            ex.getLocalizedMessage();
        System.out.println(connectMsg);
    }
	
	
	}

}
