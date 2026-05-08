package Servlets.FAS.FAS1.TDA.servlets;

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


public class TDA_Accept_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TDA_Accept_Report() {
        super();
        
    }
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1,result = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
	    String CONTENT_TYPE = "text/xml; charset=windows-1252";
	    response.setContentType(CONTENT_TYPE); 
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
			int month=Integer.parseInt(request.getParameter("txtCB_Month"));
			System.out.println("cash book month------"+month);
            String jtype=request.getParameter("jtype");
			System.out.println("jtype"+jtype);
								
			 if(cmd.equalsIgnoreCase("check"))
			{
				xml="<response><command>check</command>";				
				try
							{
								String sql="select VOUCHER_NO from FAS_TDA_TCA_RAISED_MST where CASHBOOK_YEAR='"+year+"' and CASHBOOK_MONTH='"+month+"' and ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and  ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code+"' and ACCEPTING_JVR_NO is not null and TDA_OR_TCA='"+jtype+"' and STATUS='L'";
								System.out.println("sql "+sql);
								ps=con.prepareStatement(sql);
								rs=ps.executeQuery();
								while(rs.next())
								{
									System.out.println("inside master");
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
        {
	    String CONTENT_TYPE = "text/html; charset=windows-1252";
	    response.setContentType(CONTENT_TYPE); 

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

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try
	    {
	         cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));System.out.println(cmbAcc_UnitCode);
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
		int vou_no=0;//Integer.parseInt(request.getParameter("txtAdvice_No"));System.out.println(vou_no);
	    int jour_type=Integer.parseInt(request.getParameter("cmbJournal_type"));
	    String jou="";
	    if (jour_type==1)
	    {jou="TDAA";}
	    if (jour_type==2)
	    {jou="TCAA";}System.out.println(jou);
	    
	    String suppnumber=request.getParameter("supno");
        String reptype = request.getParameter("reporttype");
        System.out.println("suppnumber:::;;;;"+suppnumber);
        System.out.println("reptypereptypereptypereptype:::;;;;"+reptype);
        int supno=Integer.parseInt(suppnumber);
        int reporttype = Integer.parseInt(reptype);
        String repcondn="";
        
        if(reporttype==1)
        {
      	  repcondn = " and (mst.supplement_no  = 0 or mst.supplement_no is null)";
        }
        else if(reporttype==3)
        {
      	  repcondn = " and mst.supplement_no ="+suppnumber;
        }
		File reportFile=null;
		 try {
               
             
             String monthInWords="";
             if(month==1)
                 monthInWords="January";
             else if(month==2)
                 monthInWords="February";
             else if(month==3)
                 monthInWords="March";
             else if(month==4)
                 monthInWords="April";
             else if(month==5)
                 monthInWords="May";
             else if(month==6)
                 monthInWords="June";
             else if(month==7)
                 monthInWords="July";
             else if(month==8)
                 monthInWords="August";
             else if(month==9)
                 monthInWords="September";
             else if(month==10)
                 monthInWords="October";
             else if(month==11)
                 monthInWords="November";
             else if(month==12)
                 monthInWords="December";
             
            System.out.println("monthInWords....."+monthInWords);
           
                reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/Accept_Report.jasper"));
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
        
        map.put("monthInWords",monthInWords);
        map.put("vou_no",vou_no);
        map.put("jou",jou );
        map.put("supplement_no",repcondn);
        System.out.println("accountingunitid"+cmbAcc_UnitCode+"\n accountofficeid"+cmbOffice_code+"\n txtCB_Year"+year+"\n txtCB_Month"+month+"\n monthInWords"+monthInWords+
        		"\n vou_no"+vou_no+"\n jou"+jou+"\n supplement_no"+repcondn);
        
   
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
        if (rtype.equalsIgnoreCase("PDF"))   
        {
        	System.out.println("PDF");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"tda_tca_accepting.pdf\"");
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
