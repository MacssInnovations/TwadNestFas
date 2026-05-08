package Servlets.FAS.FAS1.TDALIST;


import java.io.IOException;
import java.io.PrintWriter;
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
import java.sql.Connection;
/**
 * Servlet implementation class TDA_Responding_List_supp
 */
public class TDA_Responding_List_supp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TDA_Responding_List_supp() {
        super();
        // TODO Auto-generated constructor stub
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
	        ResultSet rs=null,rs3=null,rs4=null,rs5=null;
	        
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
	                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                Class.forName(strDriver.trim());
	                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	        }
	        catch(Exception e)
	        {
	       	 System.out.println("Exception in openeing connection :"+e);

	        }
	        
	       System.out.println("servlet called for sjv");
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
	       int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0,supNo=0;
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
	       
	       supNo=Integer.parseInt(request.getParameter("supNo"));
	       
	       try{active=request.getParameter("active");}
	       catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
	       
	       if(strType.equalsIgnoreCase("searchByMonth"))  
	       {
	    	   System.out.println("searchByMonth");
		           
		     
                       if(active.equalsIgnoreCase("tdaCleared") || active.equalsIgnoreCase("tcaCleared") || active.equalsIgnoreCase("tdaClearedDuring") || active.equalsIgnoreCase("tcaClearedDuring"))
		            {
                           
		            	xml="<response><command>Cleared</command>"; 
	                   if(active.equalsIgnoreCase("tdaCleared")){
		            	sql="SELECT a.voucher_no,\n" + 
		            	"  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
		            	"  a.particulars,\n" + 
		            	"  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
		            	"  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
		            	"  a.ACCOUNTING_UNIT_ID,\n" + 
		            	"  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
		            	"  a.RESPONDING_JVR_NO,\n" + 
		            	"  TO_CHAR(a.RESPONDING_JVR_DATE,'DD/MM/YYYY') AS res_date\n" + 
		            	" FROM fas_tda_tca_raised_mst a,fas_tda_tca_raised_mst b\n" + 
		            	" WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
                               " and a.ACCEPTING_SLNO=b.VOUCHER_NO\n" + 
		            	" and a.ACCOUNTING_UNIT_ID     =?\n" + 
		            	" AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
		            	" AND a.cashbook_year            =?\n" + 
		            	" AND a.cashbook_month           =?\n" + 
		            	" AND a.status                   =?\n" + 
		            	" AND (a.tda_or_tca               ='TDAO' or a.tda_or_tca               ='TDACB')\n" + 
		            	" and b.ACCEPTING_JVR_NO is not null\n" + 
		            	" and b.ACCEPTING_JVR_DATE is not null\n" + 
		            	" AND a.RESPONDING_JVR_NO       IS NOT NULL\n" + 
		            	" AND a.RESPONDING_JVR_DATE     IS NOT NULL  and A.Supplement_No="+supNo+ 
		             	" ORDER BY a.voucher_no";
                      }
                      else if(active.equalsIgnoreCase("tcaCleared")){
                          sql="SELECT a.voucher_no,\n" + 
                          "  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
                          "  a.particulars,\n" + 
                          "  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
                          "  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
                          "  a.ACCOUNTING_UNIT_ID,\n" + 
                          "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                          "  a.RESPONDING_JVR_NO,\n" + 
                          "  TO_CHAR(a.RESPONDING_JVR_DATE,'DD/MM/YYYY') AS res_date\n" + 
                          " FROM fas_tda_tca_raised_mst a,fas_tda_tca_raised_mst b\n" + 
                          " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
                          " and a.ACCEPTING_SLNO=b.VOUCHER_NO\n" + 
                          "  and a.ACCOUNTING_UNIT_ID     =?\n" + 
                          " AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
                          " AND a.cashbook_year            =?\n" + 
                          " AND a.cashbook_month           =?\n" + 
                          " AND a.status                   =?\n" + 
                          " AND (a.tda_or_tca               ='TCAO' or a.tda_or_tca               ='TCACB')\n" + 
                          " and b.ACCEPTING_JVR_NO is not null\n" + 
                          " and b.ACCEPTING_JVR_DATE is not null\n" + 
                          " AND a.RESPONDING_JVR_NO       IS NOT NULL\n" + 
                          " AND a.RESPONDING_JVR_DATE     IS NOT NULL and A.Supplement_No="+supNo+ 
                         " ORDER BY a.voucher_no";
                      }
	           else if(active.equalsIgnoreCase("tdaClearedDuring")){
	               sql="SELECT a.voucher_no,\n" + 
	               "  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
	               "  a.particulars,\n" + 
	               "  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
	               "  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
	               "  a.ACCOUNTING_UNIT_ID,\n" + 
	               "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
	               "  a.RESPONDING_JVR_NO,\n" + 
	               "  TO_CHAR(a.RESPONDING_JVR_DATE,'DD/MM/YYYY') AS res_date\n" + 
	               " FROM fas_tda_tca_raised_mst a,fas_tda_tca_raised_mst b\n" + 
	               " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
	               " and a.ACCEPTING_SLNO=b.VOUCHER_NO\n" + 
	               " and a.ACCOUNTING_UNIT_ID     =?\n" + 
	               " AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
	               " AND EXTRACT(YEAR FROM a.RESPONDING_JVR_DATE)=?" + 
	               " AND extract(month from a.RESPONDING_JVR_DATE)=?\n" + 
	               " AND a.status                   =?\n" + 
	               " AND (a.tda_or_tca               ='TDAO' or a.tda_or_tca               ='TDACB')\n" + 
	               " and b.ACCEPTING_JVR_NO is not null\n" + 
	               " and b.ACCEPTING_JVR_DATE is not null\n" + 
	               " AND a.RESPONDING_JVR_NO       IS NOT NULL\n" + 
	               " AND a.RESPONDING_JVR_DATE     IS NOT NULL and A.Supplement_No="+supNo+  
	              " ORDER BY a.voucher_no";
	           }
	           else if(active.equalsIgnoreCase("tcaClearedDuring")){
	               sql="SELECT a.voucher_no,\n" + 
	               "  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
	               "  a.particulars,\n" + 
	               "  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
	               "  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
	               "  a.ACCOUNTING_UNIT_ID,\n" + 
	               "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
	               "  a.RESPONDING_JVR_NO,\n" + 
	               "  TO_CHAR(a.RESPONDING_JVR_DATE,'DD/MM/YYYY') AS res_date\n" + 
	               " FROM fas_tda_tca_raised_mst a,fas_tda_tca_raised_mst b\n" + 
	               " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
	               " and a.ACCEPTING_SLNO=b.VOUCHER_NO\n" + 
	               " and a.ACCOUNTING_UNIT_ID     =?\n" + 
	               " AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
	               " AND EXTRACT(YEAR FROM a.RESPONDING_JVR_DATE)=?" + 
	               " AND extract(month from a.RESPONDING_JVR_DATE)=?\n" + 
	               " AND a.status                   =?\n" + 
	               " AND (a.tda_or_tca               ='TCAO' or a.tda_or_tca               ='TCACB')\n" + 
	               " and b.ACCEPTING_JVR_NO is not null\n" + 
	               " and b.ACCEPTING_JVR_DATE is not null\n" + 
	               " AND a.RESPONDING_JVR_NO       IS NOT NULL\n" + 
	               " AND a.RESPONDING_JVR_DATE     IS NOT NULL and A.Supplement_No="+supNo+  
	               " ORDER BY a.voucher_no";
	           }
                  
	    	    System.out.println("SQL::::"+sql);
		            try
		            {
		            	
				            int count=0;
				            String unitName=null;
				            ps=con.prepareStatement(sql);
				            ps.setInt(1,cmbAcc_UnitCode); 
				            ps.setInt(2,cmbOffice_code);
				            ps.setInt(3,txtCB_Year); 
				            ps.setInt(4,txtCB_Month); 
				            ps.setString(5,cmbStatus); 
				        
				            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
				            rs5=ps.executeQuery();
				         
	                                   while(rs5.next())
	                                   {
	                                	  
	                                	   int accUnit=rs5.getInt("trf_unit");
	                                	  
	                                	   ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME FROM fas_mst_acct_units WHERE ACCOUNTING_UNIT_ID="+accUnit);
	                                	   rs3=ps.executeQuery();
	                                	   if(rs3.next())
	                                	   {
	                                		    unitName=rs3.getString("ACCOUNTING_UNIT_NAME");
	                                	   }
	                                	  
	                                	  
	                                               xml=xml+"<leng>";
	                                               xml=xml+"<vou_no>"+rs5.getInt("voucher_no")+"</vou_no>";
	                                               xml=xml+"<vou_date>"+rs5.getString("vou_date")+"</vou_date>";
	                                               xml=xml+"<Remak><![CDATA["+rs5.getString("particulars")+"]]></Remak>";
	                                               xml=xml+"<Tot_Amt>"+rs5.getString("tot_amount") +"</Tot_Amt>";
	                                               xml=xml+"<off_Unit>"+rs5.getInt("ACCOUNTING_FOR_OFFICE_ID") +"</off_Unit>";
	                                               xml=xml+"<res_jvrNo>"+rs5.getString("RESPONDING_JVR_NO") +"</res_jvrNo>";
	                                               xml=xml+"<res_date>"+rs5.getString("res_date")+"</res_date>";
	                                               xml=xml+"<acc_Name>"+ unitName+"</acc_Name>";
	                                               xml=xml+"<acc_Unit>"+ accUnit+"</acc_Unit>";
	                                              
	                                               xml=xml+"</leng>";
	                                               count++;
	                                   }
	                                   if(count==0) 
	                                   {
	                                               System.out.println("inside count==0");
	                                               xml="<response><command>Cleared</command><flag>failure</flag>";
	                                   }
		            }
		            catch(SQLException sqle)
		            {
			        	    sqle.printStackTrace();
			        	    System.out.println("error while fetching data " + sqle);
	                                   xml="<response><command>Cleared</command><flag>failure</flag>";
		            }
	              
	       }
	       }
	   
	       else if(strType.equalsIgnoreCase("searchByDate"))  
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
		           
		                if(active.equalsIgnoreCase("tdaPending") || active.equalsIgnoreCase("tcaPending"))
		             {
		            	 xml="<response><command>PendingDate</command>"; 
                                if(active.equalsIgnoreCase("tdaPending")){
		            	sql="SELECT a.voucher_no,\n" + 
		            	"  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
		            	"  a.particulars,\n" + 
		            	"  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
		            	"  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
		            	"  a.ACCOUNTING_UNIT_ID,\n" + 
		            	"  a.ACCOUNTING_FOR_OFFICE_ID \n" + 
		            	" FROM fas_tda_tca_raised_mst a,\n" + 
		            	"  fas_tda_tca_raised_mst b\n" + 
		            	" WHERE a.TRF_ACCOUNTING_UNIT_ID =b.ACCOUNTING_UNIT_ID\n" + 
		            	" AND a.ACCEPTING_SLNO           =b.VOUCHER_NO\n" + 
		            	" AND a.ACCOUNTING_UNIT_ID       =?\n" + 
		            	" AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
		            	" AND (a.VOUCHER_DATE BETWEEN ? AND ?)\n" + 
		            	" AND a.status               =?\n" + 
		            	" AND (a.tda_or_tca          ='TDAO'\n" + 
		            	" OR a.tda_or_tca            ='TDACB')\n" + 
		            	" AND b.ACCEPTING_JVR_NO    IS NOT NULL\n" + 
		            	" AND b.ACCEPTING_JVR_DATE  IS NOT NULL\n" + 
		            	" AND a.RESPONDING_JVR_NO   IS  NULL\n" + 
		            	" AND a.RESPONDING_JVR_DATE IS NULL and A.Supplement_No="+supNo+ 
		            	" ORDER BY a.voucher_no";
                                }
                                else if(active.equalsIgnoreCase("tcaPending")){
                                    sql="SELECT a.voucher_no,\n" + 
                                    "  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
                                    "  a.particulars,\n" + 
                                    "  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
                                    "  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
                                    "  a.ACCOUNTING_UNIT_ID,\n" + 
                                    "  a.ACCOUNTING_FOR_OFFICE_ID \n" + 
                                    " FROM fas_tda_tca_raised_mst a,\n" + 
                                    "  fas_tda_tca_raised_mst b\n" + 
                                    " WHERE a.TRF_ACCOUNTING_UNIT_ID =b.ACCOUNTING_UNIT_ID\n" + 
                                    " AND a.ACCEPTING_SLNO           =b.VOUCHER_NO\n" + 
                                    " AND a.ACCOUNTING_UNIT_ID       =?\n" + 
                                    " AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
                                    " AND (a.VOUCHER_DATE BETWEEN ? AND ?)\n" + 
                                    " AND a.status               =?\n" + 
                                    " AND (a.tda_or_tca          ='TCAO'\n" + 
                                    " OR a.tda_or_tca            ='TCACB')\n" + 
                                    " AND b.ACCEPTING_JVR_NO    IS NOT NULL\n" + 
                                    " AND b.ACCEPTING_JVR_DATE  IS NOT NULL\n" + 
                                    " AND a.RESPONDING_JVR_NO   IS  NULL\n" + 
                                    " AND a.RESPONDING_JVR_DATE IS NULL and A.Supplement_No="+supNo+ 
                                    " ORDER BY a.voucher_no";
                                }
		            	try
			            {
			            	System.out.println("inside try"+sql);
					            int count=0;
					            String unitName=null;
					            ps=con.prepareStatement(sql);
					            ps.setInt(1,cmbAcc_UnitCode); 
					            ps.setInt(2,cmbOffice_code);
					            ps.setDate(3,txtFrom_date);
					            ps.setDate(4,txtTo_date);
					            ps.setString(5,cmbStatus);
					        
					            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
					            rs=ps.executeQuery();
					            
		                                   while(rs.next())
		                                   {
		                                	  System.out.println("inside while");
		                                	   int accUnit=rs.getInt("trf_unit");
		                                	  
		                                	   ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME FROM fas_mst_acct_units WHERE ACCOUNTING_UNIT_ID="+accUnit);
		                                	   rs4=ps.executeQuery();
		                                	   if(rs4.next())
		                                	   {
		                                		    unitName=rs4.getString("ACCOUNTING_UNIT_NAME");
		                                	   }
		                                	        xml=xml+"<leng>";
		                                               xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
		                                               xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
		                                               xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
		                                               xml=xml+"<Tot_Amt>"+rs.getString("tot_amount") +"</Tot_Amt>";
		                                               xml=xml+"<acc_Name>"+ unitName+"</acc_Name>";
		                                               xml=xml+"<acc_Unit>"+ accUnit+"</acc_Unit>";
		                                               xml=xml+"<off_Unit>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID") +"</off_Unit>";
		                                               
		                                               xml=xml+"</leng>";
		                                               count++;
		                                   }
		                                   if(count==0) 
		                                   {
		                                               System.out.println("inside count==0");
		                                               xml="<response><command>Pending</command><flag>failure</flag>";
		                                   }
			            }
			            catch(SQLException sql1)
			            {
			            	sql1.printStackTrace();
				        	    System.out.println("error while fetching data " + sql1);
		                                   xml="<response><command>PendingDate</command><flag>failure</flag>";
			            }
		            }
	           else if(active.equalsIgnoreCase("tdaCleared") || active.equalsIgnoreCase("tcaCleared") || active.equalsIgnoreCase("tdaClearedDuring") || active.equalsIgnoreCase("tcaClearedDuring"))
	                 {
		            	xml="<response><command>ClearedDate</command>"; 
		                if(active.equalsIgnoreCase("tdaCleared")){
		            	    sql="SELECT a.voucher_no,\n" + 
		            	    "  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
		            	    "  a.particulars,\n" + 
		            	    "  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
		            	    "  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
		            	    "  a.ACCOUNTING_UNIT_ID,\n" + 
		            	    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
		            	    "  a.RESPONDING_JVR_NO,\n" + 
		            	    "  TO_CHAR(a.RESPONDING_JVR_DATE,'DD/MM/YYYY') AS res_date\n" + 
		            	    " FROM fas_tda_tca_raised_mst a,fas_tda_tca_raised_mst b\n" + 
		            	    " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
		            	    " and a.ACCEPTING_SLNO=b.VOUCHER_NO\n" + 
		            	    " and a.ACCOUNTING_UNIT_ID     =?\n" + 
		            	    " AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
		            	    " AND (a.VOUCHER_DATE  between ? AND ?) \n" + 
		            	    " AND a.status                   =?\n" + 
		            	    " AND (a.tda_or_tca               ='TDAO' or a.tda_or_tca               ='TDACB')\n" + 
		            	    " and b.ACCEPTING_JVR_NO is not null\n" + 
		            	    " and b.ACCEPTING_JVR_DATE is not null\n" + 
		            	    " AND a.RESPONDING_JVR_NO       IS NOT NULL\n" + 
		            	    " AND a.RESPONDING_JVR_DATE     IS NOT NULL and A.Supplement_No="+supNo+  
		            	    " ORDER BY a.voucher_no";
		            	}
                               else if(active.equalsIgnoreCase("tcaCleared")){
		            	    sql="SELECT a.voucher_no,\n" + 
		            	    "  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
		            	    "  a.particulars,\n" + 
		            	    "  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
		            	    "  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
		            	    "  a.ACCOUNTING_UNIT_ID,\n" + 
		            	    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
		            	    "  a.RESPONDING_JVR_NO,\n" + 
		            	    "  TO_CHAR(a.RESPONDING_JVR_DATE,'DD/MM/YYYY') AS res_date\n" + 
		            	    " FROM fas_tda_tca_raised_mst a,fas_tda_tca_raised_mst b\n" + 
		            	    " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
		            	    " and a.ACCEPTING_SLNO=b.VOUCHER_NO\n" + 
		            	    " and a.ACCOUNTING_UNIT_ID     =?\n" + 
		            	    " AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
		            	    " AND (a.VOUCHER_DATE  between ? AND ?) \n" + 
		            	    " AND a.status                   =?\n" + 
		            	    " AND (a.tda_or_tca               ='TCAO' or a.tda_or_tca               ='TCACB')\n" + 
		            	    " and b.ACCEPTING_JVR_NO is not null\n" + 
		            	    " and b.ACCEPTING_JVR_DATE is not null\n" + 
		            	    " AND a.RESPONDING_JVR_NO       IS NOT NULL\n" + 
		            	    " AND a.RESPONDING_JVR_DATE     IS NOT NULL and A.Supplement_No="+supNo+ 
		            	    " ORDER BY a.voucher_no";
		            	}
		                else if(active.equalsIgnoreCase("tdaClearedDuring")){
		                    sql="SELECT a.voucher_no,\n" + 
		                    "  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
		                    "  a.particulars,\n" + 
		                    "  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
		                    "  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
		                    "  a.ACCOUNTING_UNIT_ID,\n" + 
		                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
		                    "  a.RESPONDING_JVR_NO,\n" + 
		                    "  TO_CHAR(a.RESPONDING_JVR_DATE,'DD/MM/YYYY') AS res_date\n" + 
		                    " FROM fas_tda_tca_raised_mst a,fas_tda_tca_raised_mst b\n" + 
		                    " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
		                    " and a.ACCEPTING_SLNO=b.VOUCHER_NO\n" + 
		                    " and a.ACCOUNTING_UNIT_ID     =?\n" + 
		                    " AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
		                    " AND (a.RESPONDING_JVR_DATE  between ? AND ?) \n" + 
		                    " AND a.status                   =?\n" + 
		                    " AND (a.tda_or_tca               ='TDAO' or a.tda_or_tca               ='TDACB')\n" + 
		                    " and b.ACCEPTING_JVR_NO is not null\n" + 
		                    " and b.ACCEPTING_JVR_DATE is not null\n" + 
		                    " AND a.RESPONDING_JVR_NO       IS NOT NULL\n" + 
		                    " AND a.RESPONDING_JVR_DATE     IS NOT NULL and A.Supplement_No="+supNo+  
		                    " ORDER BY a.voucher_no";
		                }
		                else if(active.equalsIgnoreCase("tcaClearedDuring")){
		                    sql="SELECT a.voucher_no,\n" + 
		                    "  TO_CHAR(a.voucher_date,'DD/MM/YYYY') AS vou_date ,\n" + 
		                    "  a.particulars,\n" + 
		                    "  trim(TO_CHAR(a.total_amount,'99999999999999.99')) AS tot_amount,\n" + 
		                    "  a.TRF_ACCOUNTING_UNIT_ID                          AS trf_unit,\n" + 
		                    "  a.ACCOUNTING_UNIT_ID,\n" + 
		                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
		                    "  a.RESPONDING_JVR_NO,\n" + 
		                    "  TO_CHAR(a.RESPONDING_JVR_DATE,'DD/MM/YYYY') AS res_date\n" + 
		                    " FROM fas_tda_tca_raised_mst a,fas_tda_tca_raised_mst b\n" + 
		                    " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID\n" + 
		                    " and a.ACCEPTING_SLNO=b.VOUCHER_NO\n" + 
		                    " and a.ACCOUNTING_UNIT_ID     =?\n" + 
		                    " AND a.ACCOUNTING_FOR_OFFICE_ID =?\n" + 
		                    " AND (a.RESPONDING_JVR_DATE  between ? AND ?) \n" + 
		                    " AND a.status                   =?\n" + 
		                    " AND (a.tda_or_tca               ='TCAO' or a.tda_or_tca               ='TCACB')\n" + 
		                    " and b.ACCEPTING_JVR_NO is not null\n" + 
		                    " and b.ACCEPTING_JVR_DATE is not null\n" + 
		                    " AND a.RESPONDING_JVR_NO       IS NOT NULL\n" + 
		                    " AND a.RESPONDING_JVR_DATE     IS NOT NULL and A.Supplement_No="+supNo+  
		                    " ORDER BY a.voucher_no";
		                }
		            	 System.out.println("SQL::::"+sql);
				            try
				            {
				            	
						            int count=0;
						            String unitName=null;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode); 
						            ps.setInt(2,cmbOffice_code);
						            ps.setDate(3,txtFrom_date);
						            ps.setDate(4,txtTo_date);
						            ps.setString(5,cmbStatus);
						        
						            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
						            rs=ps.executeQuery();
						                   while(rs.next())
			                                   {
			                                	   
			                                	   int accUnit=rs.getInt("trf_unit");
			                                	  
			                                	   ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME FROM fas_mst_acct_units WHERE ACCOUNTING_UNIT_ID="+accUnit);
			                                	   rs3=ps.executeQuery();
			                                	   if(rs3.next())
			                                	   {
			                                		    unitName=rs3.getString("ACCOUNTING_UNIT_NAME");
			                                	   }
			                                	  
			                                	 
			                                               xml=xml+"<leng>";
			                                               xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
			                                               xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
			                                               xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
			                                               xml=xml+"<Tot_Amt>"+rs.getString("tot_amount") +"</Tot_Amt>";
			                                               xml=xml+"<acc_Name>"+ unitName+"</acc_Name>";
			                                               xml=xml+"<acc_Unit>"+ accUnit+"</acc_Unit>";
			                                               xml=xml+"<off_Unit>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID") +"</off_Unit>";
			                                               
			                                               xml=xml+"<res_jvrNo>"+rs.getString("RESPONDING_JVR_NO") +"</res_jvrNo>";
			                                               
			                                               xml=xml+"<res_date>"+rs.getString("res_date")+"</res_date>";
			                                               xml=xml+"</leng>";
			                                               count++;
			                                   }
			                                   if(count==0) 
			                                   {
			                                               System.out.println("inside count==0");
			                                               xml="<response><command>Cleared</command><flag>failure</flag>";
			                                   }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
			                                   xml="<response><command>Cleared</command><flag>failure</flag>";
				            }
		            	
		            }
	                   
	                       
	           
	       }
	       xml=xml+"</response>";   
	       out.println(xml); 
	       System.out.println("xml sjv>>>>>"+xml); 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
