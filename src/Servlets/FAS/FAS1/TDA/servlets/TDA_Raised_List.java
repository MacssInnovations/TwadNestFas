package Servlets.FAS.FAS1.TDA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TDA_Raised_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public TDA_Raised_List() {
        super();
       
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
        try
        {
	             HttpSession session=request.getSession(false);
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
        Connection con=null;
        ResultSet rs=null;
        
        PreparedStatement ps=null;
        try 
        {
                ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                String ConnectionString="";
                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                String strdsn=rs1.getString("Config.DSN");
                String strhostname=rs1.getString("Config.HOST_NAME");
                String strportno=rs1.getString("Config.PORT_NUMBER");
                String strsid=rs1.getString("Config.SID");
                String strdbusername=rs1.getString("Config.USER_NAME");
                String strdbpassword=rs1.getString("Config.PASSWORD");
                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                Class.forName(strDriver.trim());
                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
       	 System.out.println("Exception in openeing connection :"+e);

        }
        
       System.out.println("servlet called");
       String CONTENT_TYPE = "text/xml; charset=windows-1252";
       response.setContentType(CONTENT_TYPE);
       PrintWriter out = response.getWriter();
       String strType = "",xml="<response>";
       try
       {
       	     strType = request.getParameter("Command");
       }
       catch(Exception e)
       {
                    e.printStackTrace();
       }
       int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
       Date txtFrom_date=null,txtTo_date=null;
       Calendar c;
       String sql="",cmbStatus="",journal_type="",active="";
       
       
       try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       
       
       try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       
       txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
       txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
       cmbStatus=request.getParameter("cmbStatus");
       
       try{active=request.getParameter("cmbJournal_type");}
       catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
       
       if(strType.equalsIgnoreCase("searchByMonth"))  
       {
    	   System.out.println("searchByMonth");
	            xml="<response><command>searchByMonth</command>"; 
	            if(active.equalsIgnoreCase("TDAO"))
	            {
	            	sql="select voucher_no,\n" + 
	                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
	                "      particulars,\n" + 
	                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +  
	                "      TRF_ACCOUNTING_UNIT_ID as trf_unit,\n" + 
	                "     (select REASON_DESC from FAS_MST_TDA_TCA_REASON where REASON_CODE=REASON_FOR_TRANSFER) as trf_reason \n"+
	                "from fas_tda_tca_raised_mst \n" + 
	                "where    \n" + 
	                "      accounting_unit_id='"+cmbAcc_UnitCode+"' and  \n" + 
	                "      accounting_for_office_id='"+cmbOffice_code+"' and  \n" + 
	                "      cashbook_year='"+txtCB_Year+"' and  \n" + 
	                "      cashbook_month='"+txtCB_Month+"' and \n" + 
	                "      status='"+cmbStatus+"' and \n" + 
	                "      tda_or_tca='"+active+"' and \n" +
                       "      advice_type='P' " + 
	                "order by\n" + 
	                "      voucher_no";
	            }
	            else if(active.equalsIgnoreCase("TCAO"))
	            {
	            	sql="select voucher_no,\n" + 
	                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
	                "      particulars,\n" + 
	                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +  
	                "      TRF_ACCOUNTING_UNIT_ID as trf_unit,\n" + 
	                "     (select REASON_DESC from FAS_MST_TDA_TCA_REASON where REASON_CODE=REASON_FOR_TRANSFER) as trf_reason \n"+
	                "from fas_tda_tca_raised_mst \n" + 
	                "where    \n" + 
	                "      accounting_unit_id='"+cmbAcc_UnitCode+"' and  \n" + 
	                "      accounting_for_office_id='"+cmbOffice_code+"' and  \n" + 
	                "      cashbook_year='"+txtCB_Year+"' and  \n" + 
	                "      cashbook_month='"+txtCB_Month+"' and \n" + 
	                "      status='"+cmbStatus+"' and \n" + 
	                "      tda_or_tca='"+active+"' and \n" +
                       "      advice_type='R' " + 
	                "order by\n" + 
	                "      voucher_no";
	            }
	            else if(active.equalsIgnoreCase("TDAA"))
	            {
	            	sql="select voucher_no,\n" + 
	                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
	                "      particulars,\n" + 
	                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +  
	                "      TRF_ACCOUNTING_UNIT_ID as trf_unit,\n" + 
	                "     (select REASON_DESC from FAS_MST_TDA_TCA_REASON where REASON_CODE=REASON_FOR_TRANSFER) as trf_reason \n"+
	                "from fas_tda_tca_raised_mst \n" + 
	                "where    \n" + 
	                "      TRF_ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and  \n" + 
	                "      cashbook_year='"+txtCB_Year+"' and  \n" + 
	                "      cashbook_month='"+txtCB_Month+"' and \n" + 
	                "      status='"+cmbStatus+"' and \n" + 
	                "      tda_or_tca='"+active+"' and \n" +
                    "      advice_type='J' and \n" + 
                    "      ACCEPTANCE_STATUS='Y' " + 
	                "order by\n" + 
	                "      voucher_no";
	            }
	            else if(active.equalsIgnoreCase("TCAA"))
	            {
	            	sql="select voucher_no,\n" + 
	                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
	                "      particulars,\n" + 
	                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +  
	                "      TRF_ACCOUNTING_UNIT_ID as trf_unit,\n" + 
	                "     (select REASON_DESC from FAS_MST_TDA_TCA_REASON where REASON_CODE=REASON_FOR_TRANSFER) as trf_reason \n"+
	                "from fas_tda_tca_raised_mst \n" + 
	                "where    \n" + 
	                "      TRF_ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and  \n" + 
	                "      cashbook_year='"+txtCB_Year+"' and  \n" + 
	                "      cashbook_month='"+txtCB_Month+"' and \n" + 
	                "      status='"+cmbStatus+"' and \n" + 
	                "      tda_or_tca='"+active+"' and \n" +
                    "      advice_type='J' and \n" + 
                    "      ACCEPTANCE_STATUS='Y' " + 
	                "order by\n" + 
	                "      voucher_no";
	            }
	            
	            System.out.println("SQL::::"+sql);
	            try
	            {
			            int count=0;
			            ps=con.prepareStatement(sql);
//			            ps.setInt(1,cmbAcc_UnitCode);
//			            ps.setInt(2,cmbOffice_code);
//			            ps.setInt(3,txtCB_Year);
//			            ps.setInt(4,txtCB_Month);
//			            ps.setString(5,cmbStatus);
//			            ps.setString(6,active);
			           // ps.setString(7,txtMode_of_creat);
			            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
			            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
			            rs=ps.executeQuery();
                                   while(rs.next())
                                   {
                                               xml=xml+"<leng>";
                                               xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                               xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                               xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                               xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                               xml=xml+"<Trans_Unit>"+rs.getInt("trf_unit") +"</Trans_Unit>";
                                               xml=xml+"<Trans_Rea>"+rs.getString("trf_reason") +"</Trans_Rea>";
                                               
                                               xml=xml+"</leng>";
                                               count++;
                                   }
                                   if(count==0) 
                                   {
                                               System.out.println("inside count==0");
                                               xml="<response><command>searchByMonth</command><flag>failure</flag>";
                                   }
	            }
	            catch(SQLException sqle)
	            {
		        	    sqle.printStackTrace();
		        	    System.out.println("error while fetching data " + sqle);
                                   xml="<response><command>searchByMonth</command><flag>failure</flag>";
	            }
              
           
       }
       System.out.println("here "+strType.equalsIgnoreCase("searchByDate"));
       if(strType.equalsIgnoreCase("searchByDate"))  
       {
                   String[] sd=request.getParameter("txtFrom_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtFrom_date=new Date(d.getTime());
	            System.out.println("from_date "+txtFrom_date);
	            
	            sd=request.getParameter("txtTo_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            d=c.getTime();
	            txtTo_date=new Date(d.getTime());
	            System.out.println("txtTo_date "+txtTo_date);
	            xml="<response><command>searchByDate</command>"; 
	            if(active.equalsIgnoreCase("TDAO"))
	            {
	            	sql="select voucher_no,\n" + 
	                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
	                "      particulars,\n" + 
	                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +  
	                "      TRF_ACCOUNTING_UNIT_ID as trf_unit,\n" + 
	                "     (select REASON_DESC from FAS_MST_TDA_TCA_REASON where REASON_CODE=REASON_FOR_TRANSFER) as trf_reason \n"+
	                "from fas_tda_tca_raised_mst \n" + 
	                "where    \n" + 
	                "      accounting_unit_id='"+cmbAcc_UnitCode+"' and  \n" + 
	                "      accounting_for_office_id='"+cmbOffice_code+"' and  \n" + 
	                "      VOUCHER_DATE between ? and ? and\n" +  
	                "      status='"+cmbStatus+"' and \n" + 
	                "      tda_or_tca='"+active+"' and \n" +
                       "      advice_type='P' " + 
	                "order by\n" + 
	                "      voucher_no";
	            }
	            else if(active.equalsIgnoreCase("TCAO"))
	            {
	            	sql="select voucher_no,\n" + 
	                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
	                "      particulars,\n" + 
	                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +  
	                "      TRF_ACCOUNTING_UNIT_ID as trf_unit,\n" + 
	                "     (select REASON_DESC from FAS_MST_TDA_TCA_REASON where REASON_CODE=REASON_FOR_TRANSFER) as trf_reason \n"+
	                "from fas_tda_tca_raised_mst \n" + 
	                "where    \n" + 
	                "      accounting_unit_id='"+cmbAcc_UnitCode+"' and  \n" + 
	                "      accounting_for_office_id='"+cmbOffice_code+"' and  \n" + 
	                "      VOUCHER_DATE between ? and ? and\n" +  
	                "      status='"+cmbStatus+"' and \n" + 
	                "      tda_or_tca='"+active+"' and \n" +
                       "      advice_type='R' " + 
	                "order by\n" + 
	                "      voucher_no";
	            }
	            else if(active.equalsIgnoreCase("TDAA"))
	            {
	            	sql="select voucher_no,\n" + 
	                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
	                "      particulars,\n" + 
	                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +  
	                "      TRF_ACCOUNTING_UNIT_ID as trf_unit,\n" + 
	                "     (select REASON_DESC from FAS_MST_TDA_TCA_REASON where REASON_CODE=REASON_FOR_TRANSFER) as trf_reason \n"+
	                "from fas_tda_tca_raised_mst \n" + 
	                "where    \n" + 
	                "      TRF_ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and  \n" + 
	                "      VOUCHER_DATE between ? and ? and\n" + 
	                "      status='"+cmbStatus+"' and \n" + 
	                "      tda_or_tca='"+active+"' and \n" +
                    "      advice_type='J' and \n" + 
                    "      ACCEPTANCE_STATUS='Y' " + 
	                "order by\n" + 
	                "      voucher_no";
	            }
	            else if(active.equalsIgnoreCase("TCAA"))
	            {
	            	sql="select voucher_no,\n" + 
	                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
	                "      particulars,\n" + 
	                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" +  
	                "      TRF_ACCOUNTING_UNIT_ID as trf_unit,\n" + 
	                "     (select REASON_DESC from FAS_MST_TDA_TCA_REASON where REASON_CODE=REASON_FOR_TRANSFER) as trf_reason \n"+
	                "from fas_tda_tca_raised_mst \n" + 
	                "where    \n" + 
	                "      TRF_ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and  \n" + 
	                "      VOUCHER_DATE between ? and ? and\n" + 
	                "      status='"+cmbStatus+"' and \n" + 
	                "      tda_or_tca='"+active+"' and \n" +
                    "      advice_type='J' and \n" + 
                    "      ACCEPTANCE_STATUS='Y' " + 
	                "order by\n" + 
	                "      voucher_no";
	            }
                   
                       System.out.println("SQL::::"+sql);
                       try
                       {
                                       int count=0;
                                       ps=con.prepareStatement(sql);
                                       ps.setDate(1,txtFrom_date);
                                       ps.setDate(2,txtTo_date);
                                       System.out.println("journal_type ::: "+journal_type);
                                      // ps.setString(7,txtMode_of_creat);
                                       xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                       "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                                       rs=ps.executeQuery();
                                       while(rs.next())
                                       {
                                                   xml=xml+"<leng>";
                                                   xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                                   xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                                   xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                                   xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                   xml=xml+"<Trans_Unit>"+rs.getInt("trf_unit") +"</Trans_Unit>";
                                                   xml=xml+"<Trans_Rea>"+rs.getString("trf_reason") +"</Trans_Rea>";
                                                   xml=xml+"</leng>";
                                                   count++;
                                       }
                                       if(count==0) 
                                       {
                                                   System.out.println("inside count==0");
                                                   xml="<response><command>searchByMonth</command><flag>failure</flag>";
                                       }
                       }
                       catch(SQLException sqle)
                       {
                                       sqle.printStackTrace();
                                       System.out.println("error while fetching data " + sqle);
                                       xml="<response><command>searchByMonth</command><flag>failure</flag>";
                       }
           
       }
       xml=xml+"</response>";   
       out.println(xml); 
       System.out.println(xml); 
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
