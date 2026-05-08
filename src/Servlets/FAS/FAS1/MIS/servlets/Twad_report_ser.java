package Servlets.FAS.FAS1.MIS.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import javax.servlet.*;
import javax.servlet.http.*;

import org.syntax.jedit.InputHandler.prev_char;

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

public class Twad_report_ser extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("test servlet");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		// ** Get User ID *//*
		String userid = (String) session.getAttribute("UserId");
		String empid = userid.substring(4, userid.length());
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		System.out.println("Empid -------------->" + empid);

		Connection con = null;
		 Calendar cal;
		 Date txtCrea_date=null,txtlast_date=null,txtRef_date=null;
		PreparedStatement ps = null, ps_category = null;
		ResultSet rs = null, rs_category = null;
		int c = 1;double sum_cre=0,sum_deb=0,sum_net=0;
		java.util.Date sDate = null;
		java.util.Date df=null;
		int sl_no=0;
		int k=0,k1=0;
		
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
		}
		
		String Command = request.getParameter("Command");
		System.out
				.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++="
						+ Command+" .. ");
		String qry_str ="",qry="";
		String xml = "", cat_id = "";
	
		if(Command.equalsIgnoreCase("LoadProgram")){
			xml+="<response><command>LoadProgram</command>";
			qry="select ID,IMISCLASSIFICATION from IMISGROUPING where id not in (101,102,103,104,105)";
		
		try {
			ps = con.prepareStatement(qry);
		
		 rs=ps.executeQuery();
		while(rs.next()){
			
			xml+="<ID>"+rs.getInt("ID")+"</ID>";
			xml+="<IMISCLASSIFICATION><![CDATA["+rs.getString("IMISCLASSIFICATION").trim()+"]]></IMISCLASSIFICATION>";
			c++;
		}
		if(c>0){
			xml+="<flag>success</flag>";	
		}else{
			xml+="<flag>failure</flag>";
		}
		} catch (SQLException e) {
			xml+="<flag>failure</flag>";
			e.printStackTrace();
		}
		xml = xml + "</response>";
		
		}	
		
		if (Command.equalsIgnoreCase("All")) {
			String fin_year = request.getParameter("fin_year");
			String[] year = fin_year.split("-");
			int yearFrom = Integer.parseInt(year[0]);
			int yearTo = Integer.parseInt(year[1]);
			int CmbFrom_Month = Integer.parseInt(request
					.getParameter("CmbFrom_Month"));
			int CmbTo_Month = Integer.parseInt(request.getParameter("CmbTo_Month"));
			System.out.println("fin_year" + fin_year + "... monthfrom >> "
					+ CmbFrom_Month + ".... month To >> " + CmbTo_Month);
			
			xml = "<response><command>All</command>";
		//	System.out.println("all units::::::");
			
			try
			{
				 qry= "select MAJOR_HEAD_DESC,MAJOR_HEAD_CODE from COM_MST_MAJOR_HEADS order by MAJOR_HEAD_CODE";
				System.out.println(qry);
				ps_category = con.prepareStatement(qry);
				rs_category = ps_category.executeQuery();
				while (rs_category.next()) {
					cat_id = rs_category.getString("MAJOR_HEAD_CODE");
					sum_cre=0;sum_deb=0;sum_net=0;
					
					xml += "<count>";
					xml += "<Category_ID>"
							+ rs_category.getString("MAJOR_HEAD_CODE")
							+ "</Category_ID>";
					xml += "<Category_DESC>"
							+ rs_category.getString("MAJOR_HEAD_DESC")
							+ "</Category_DESC>";
			
				
				
				
					qry_str="SELECT " +
					"  (SELECT MAJOR_HEAD_DESC " +
					"  FROM COM_MST_MAJOR_HEADS " +
					"  WHERE MAJOR_HEAD_CODE=aa.major_code " +
					"  )AS major_desc, " +
					"  aa.* " +
					"FROM " +
					"  (SELECT a.ACCOUNT_HEAD_CODE AS head_code, " +
					"    (SELECT MAJOR_HEAD_CODE " +
					"    FROM COM_MST_ACCOUNT_HEADS " +
					"    WHERE ACCOUNT_HEAD_CODE= a.ACCOUNT_HEAD_CODE " +
					"    ) AS major_code, " +
					"    (SELECT ACCOUNT_HEAD_DESC " +
					"    FROM COM_MST_ACCOUNT_HEADS " +
					"    WHERE ACCOUNT_HEAD_CODE=a.ACCOUNT_HEAD_CODE " +
					"    )                AS ACCOUNT_HEADS, " +
					"    SUM(Debit)       AS Debit, " +
					"    SUM(Credit)      AS Credit, " +
					"    SUM(Debit-Credit)AS net " +
					"  FROM " +
					"    (SELECT ACCOUNT_HEAD_CODE, " +
					"      CURRENT_MONTH_DEBIT  AS Debit, " +
					"      CURRENT_MONTH_CREDIT AS Credit " +
					"    FROM FAS_TRIAL_BALANCE " +
					"    WHERE to_date((cashbook_month " +
					"      ||'-' " +
					"      ||cashbook_year),'mm-yyyy') BETWEEN to_date("+CmbFrom_Month+
					"      ||'-' " +
					"      ||"+yearFrom+",'mm-yyyy') " +
					"    AND to_date("+CmbTo_Month +
					"      ||'-' " +
					"      ||"+yearTo+",'mm-yyyy') " +
					"    AND ACCOUNT_HEAD_CODE IN " +
					"      (SELECT ACCOUNT_HEAD_CODE " +
					"      FROM COM_MST_ACCOUNT_HEADS " +
					"      WHERE MAJOR_HEAD_CODE='"+cat_id+"' " +
					"      ) " +
					"    ) a " +
					"  GROUP BY a.ACCOUNT_HEAD_CODE " +
					"  ORDER BY a.ACCOUNT_HEAD_CODE " +
					"  )aa";
					
					try {
						//System.out.println("... "+cat_id+" ..."+qry_str);
						ps = con.prepareStatement(qry_str);
						rs = ps.executeQuery();

						while (rs.next()) {
							
							sum_cre+=rs.getDouble("Credit");
							sum_deb+=rs.getDouble("Debit");		
							xml = xml + "<head_code"+c+">" + rs.getInt("head_code")
									+ "</head_code"+c+">";
							xml = xml + "<Debit"+c+">" + rs.getDouble("Debit")
									+ "</Debit"+c+">";
							xml = xml + "<Credit"+c+">" + rs.getDouble("Credit")
									+ "</Credit"+c+">";
							xml = xml + "<NET"+c+">" + rs.getDouble("NET") + "</NET"+c+">";
							xml = xml + "<head_desc"+c+"><![CDATA["
									+ rs.getString("ACCOUNT_HEADS")
									+ "]]></head_desc"+c+">";
						}
						// System.out.println("exception::::"+c);
				}
		

			 catch (Exception e) {
				 System.out.println("exception::::");
				xml = xml + "<flag1>Failure</flag1>";
				//System.out.println("fail:::::"+e.getMessage());
			}
			 xml += "</count><sum_deb>"+sum_deb+"</sum_deb>";			 
				xml+="<sum_cre>"+sum_cre+"</sum_cre>";
			 c++;
			}

				if(c>0)
				{
					
						xml = xml + "<flag>Success</flag>";						
				}
				else{
					xml = xml + "<flag>Failure</flag>";
					}
			}catch (Exception e) {
				 System.out.println("exception::::");
					xml = xml + "<flag2>Failure</flag2>";
				System.out.println("fail:::::"+e.getMessage());
			}
			 xml = xml + "</response>";
				
		}
		if (Command.equalsIgnoreCase("generalFunc")) {
			xml+="<response><command>generalFunc</command>";
			try{
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			String type=request.getParameter("type");
		String d_val=request.getParameter("date_val");
		System.out.println("string d_val "+d_val);
		 String[] sd=request.getParameter("date_val").split("/");
         cal=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
         java.util.Date d=cal.getTime();
         txtCrea_date=new Date(d.getTime());
         System.out.println("creation date ..."+txtCrea_date);
		
		
		String Fin_year=request.getParameter("fin_year");
		String[] Amount=request.getParameterValues("Amount");
		String[] prog_id=request.getParameterValues("sch_id");
		int prog_len=prog_id.length;
		int count=0;
		PreparedStatement p_fzd=null;
		ResultSet r_fzd=null;
			String frz_qry="select count(*)as count from TWAD_BOARD_OUTSTANDINGS_FZD where " +
					"YEAR='"+Fin_year+"' and ACCOUNTING_UNIT_ID="+unit_id;
			try{
				 p_fzd=con.prepareStatement(frz_qry);
				 r_fzd=p_fzd.executeQuery();
				 while(r_fzd.next()){
						System.out.println(r_fzd.getInt("count"));
						count=r_fzd.getInt("count");
						
					}System.out.println("count value is ...... "+count);
				 if(count==0){
						String ins_fz="insert into TWAD_BOARD_OUTSTANDINGS_FZD (YEAR,ACCOUNTING_UNIT_ID,FREEZED," +
								"UPDATED_BY_USER_ID,UPDATED_DATE) values( '" +Fin_year+"'," +unit_id+",'N','"+userid+"',?) ";
						try{
							System.out.println("...... >> "+ins_fz);
							 p_fzd=con.prepareStatement(ins_fz);
							 //p_fzd.setDate(1, txtCrea_date);
							 p_fzd.setTimestamp(1, ts);
							 int fzd=p_fzd.executeUpdate();
							 if(fzd==0){
								
										xml = xml + "<flag_frZ>failure</flag_frZ>";
									
									}
									else{
										xml = xml + "<flag_frZ>success</flag_frZ>";
									
									}
							 
					       }catch (Exception e) {
						System.out.println("Error in first excep th Line ...... "+e);
					       }
					}
			}catch (Exception e) {
System.out.println("Error in 429 th Line ...... "+e);
			}
			
		String del_qry="delete from TWAD_BOARD_OUTSTANDINGS where OUTSTANDING_TYPE='"+type+"' and  YEAR= '" +Fin_year+"'  and  "+
		"ACCOUNTING_UNIT_ID=" +unit_id;
		System.out.println(del_qry);
		PreparedStatement ps_del=con.prepareStatement(del_qry);
	
		 k1=ps_del.executeUpdate();
		k1++;
		System.out.println(" k1 value s ...... "+k1);
		
	if(k1==0){
		xml = xml + "<flag>failure</flag>";}
	else{
		for(int i=0;i<prog_len;i++){
			qry_str="select decode(max(SLNO),null,0,max(SLNO)) as no from twad_board_outstandings";
			
			try{
				ps=con.prepareStatement(qry_str);
				rs=ps.executeQuery();
				if(rs.next())
				sl_no=rs.getInt("no");
			   sl_no++;
			   System.out.println(" no value :"+sl_no);
			}catch (Exception e) {
             System.out.println(e);				}
			int Program=Integer.parseInt(prog_id[i]);
			double Balance=Double.parseDouble(Amount[i]);
			
			
			qry="INSERT " +
			" INTO twad_board_outstandings  " +
			"  ( " +
			"    YEAR, " +
			"    ACCOUNTING_UNIT_ID, " +
			"    PROGRAM_ID, " +
			//"    INDIVIDUAL_SCHEME_NAME, " +
			"    AMT_RECEIVED_UPTO_MAR2011, " +
			"    AMT_RECEIVED_UPTO_CUR_MAR, " +
			"    EXP_BOOKED_UPTO_MAR2011, " +
			"    EXP_BOOKED_UPTO_CUR_MAR, " +
	"    ENTRY_DATE, " +
			"    BALANCE, " +
		/*	"    LOCAL_BODY, " +*/
			"    BILLS_PENDING, " +
			"    UPDATED_BY_USER_ID, " +
			"    UPDATED_DATE, " +
			"    SLNO, " +
			"    OUTSTANDING_TYPE " +
			"  ) " +
			"  VALUES " +
			"  ( '" +Fin_year+"'"+
			", " +unit_id+
		//	", " +Program+
			", '" +Program+"'"+
			" ,0,0,0,0" +
			",?"+
			", " +Balance+
			",0 " +
			", '" +userid+"',"+
			"? " +
			", " +sl_no+
			",'"+type+"' )";
		
	
		
			
			System.out.println(" pro ================== "+qry);
			ps_category=con.prepareStatement(qry);
			ps_category.setDate(1, txtCrea_date);
			ps_category.setTimestamp(2, ts);
			 k=ps_category.executeUpdate();
			k++;
			System.out.println(" k value s ...... "+k);
		}
		if(k==0){
			xml = xml + "<flag>failure</flag>";
		/*	sendMessage(response,
					"Records Inserted Successfully ............ ",
					"ok", "Twad_report_ser.jsp");*/
		}
		else{
			xml = xml + "<flag>success</flag>";
		/*	sendMessage(response,
					"Records Inserted Successfully ............ ",
		}			"ok", "Twad_report_ser.jsp");*/
		}
		
		
	}}
		
		catch (Exception e) {
			System.out.println(e);
			xml = xml + "<flag>failure</flag>";
			/*sendMessage(response,
					"Records Inserted Successfully ............ ",
					"ok", "Twad_report_ser.jsp");*/
		} xml = xml + "</response>";
		}
		if (Command.equalsIgnoreCase("Sch_Liability")) {
			xml+="<response><command>Sch_Liability</command>";
			try{
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			String type=request.getParameter("type");
		String d_val=request.getParameter("date_val");
		System.out.println("string d_val "+d_val);
		String Fin_year=request.getParameter("fin_year");
		 String[] sd=request.getParameter("date_val").split("/");
         cal=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
         java.util.Date d=cal.getTime();
         txtCrea_date=new Date(d.getTime());
         System.out.println("creation date ..."+txtCrea_date);
			
			String[] pro_id=request.getParameterValues("pro_id");
			//System.out.println("string proid vdahkd"+pro_id);
			String[] tie_id=request.getParameterValues("tie_id");
			String[] sch_name=request.getParameterValues("sch_id");
			String[] AmtRec_id=request.getParameterValues("AmtRec_id");
			String[] ExpBook_id=request.getParameterValues("ExpBook_id");
			String[] ExpAct_id=request.getParameterValues("ExpAct_id");
			//String[] Bal_id=request.getParameterValues("Bal_id");
			String[] incur_id=request.getParameterValues("incur_id");
			String[] main_Cmb=request.getParameterValues("main_Cmb");
			String[] sub_Cmb=request.getParameterValues("sub_Div");
			String[] main_Type=request.getParameterValues("main_Type");
			String[] local_by=request.getParameterValues("local_by");
			int len=pro_id.length;
			System.out.println("------------------------------"+len);
			int count=0;
			PreparedStatement p_fzd=null;
			ResultSet r_fzd=null;
				String frz_qry="select count(*)as count from TWAD_BOARD_OUTSTANDINGS_FZD where " +
						"YEAR='"+Fin_year+"' and ACCOUNTING_UNIT_ID="+unit_id;
				try{
					 p_fzd=con.prepareStatement(frz_qry);
					 r_fzd=p_fzd.executeQuery();
					 if(r_fzd.next()){
							System.out.println(r_fzd.getInt("count"));
							count=r_fzd.getInt("count");
							
						}System.out.println("count value is ...... "+count);
					 if(count==0){
							String ins_fz="insert into TWAD_BOARD_OUTSTANDINGS_FZD (YEAR,ACCOUNTING_UNIT_ID,FREEZED," +
									"UPDATED_BY_USER_ID,UPDATED_DATE) values( '" +Fin_year+"'," +unit_id+",'N','"+userid+"',?) ";
							try{
								System.out.println("...... >> "+ins_fz);
								 p_fzd=con.prepareStatement(ins_fz);
								 //p_fzd.setDate(1, txtCrea_date);
								 p_fzd.setTimestamp(1, ts);
								 int fzd=p_fzd.executeUpdate();
								 if(fzd==0){
									
											xml = xml + "<flag_frZ>failure</flag_frZ>";
										
										}
										else{
											xml = xml + "<flag_frZ>success</flag_frZ>";
										
										}
								 
						       }catch (Exception e) {
							System.out.println("Error in first excep th Line ...... "+e);
						       }
						}
				}catch (Exception e) {
System.out.println("Error in 429 th Line ...... "+e);
				}
				
			
			for(int i=0;i<len;i++){
				qry_str="select decode(max(SLNO),null,0,max(SLNO)) as no from twad_board_outstandings";
				
				try{
					ps=con.prepareStatement(qry_str);
					rs=ps.executeQuery();
					if(rs.next())
					sl_no=rs.getInt("no");
				   sl_no++;
				   System.out.println(" no value :"+sl_no);
				}catch (Exception e) {
                 System.out.println(e);				}
				int Program=Integer.parseInt(pro_id[i]);
				String scheme=sch_name[i];
				String lb=local_by[i];
			String main=main_Type[i];
				double Amt_rec=Double.parseDouble(AmtRec_id[i]);	
				double tie_Amt=Double.parseDouble(tie_id[i]);			
				double Exp_booked=Double.parseDouble(ExpBook_id[i]);
				double Exp_Act=Double.parseDouble(ExpAct_id[i]);
				double Balance=0;
				//double Balance=Double.parseDouble(Bal_id[i]);
				double incur_Amt=Double.parseDouble(incur_id[i]);
				int sub_menu=Integer.parseInt(sub_Cmb[i]);
				
				qry="INSERT " +
				" INTO twad_board_outstandings  " +
				"  ( " +
				"    YEAR, " +
				"    ACCOUNTING_UNIT_ID, " +
				"    PROGRAM_ID, " +
				"    INDIVIDUAL_SCHEME_NAME, " +
				"    AMT_RECEIVED_UPTO_MAR2011, " +
				"    AMT_RECEIVED_UPTO_CUR_MAR, " +
				"    EXP_BOOKED_UPTO_MAR2011, " +
				"    EXP_BOOKED_UPTO_CUR_MAR, " +
			"    ENTRY_DATE, " +
				"    BALANCE, " +
				"    LOCAL_BODY, " +
				"    BILLS_PENDING, " +
				"    UPDATED_BY_USER_ID, " +
				"    UPDATED_DATE, " +
				"    SLNO, " +
				"    OUTSTANDING_TYPE,TIE_UP_AMT,LB_TYPE,DISTRICT_ID " +
				"  ) " +
				"  VALUES " +
				"  ( '" +Fin_year+"'"+
				", " +unit_id+
				", " +Program+
				", '" +scheme+"'"+
				" ,0," +Amt_rec+
				" ," +Exp_booked+" ,"+incur_Amt+
				",?"+
				", " +Balance+
				", '" +lb+
				"', " +Exp_Act+
				", '" +userid+"',"+
				"? " +
				", " +sl_no+
				",'"+type+"',"+tie_Amt+",'"+main+"',"+sub_menu+" )";
				
				System.out.println(" pro ================== "+qry);
				ps_category=con.prepareStatement(qry);
				ps_category.setDate(1, txtCrea_date);
				ps_category.setTimestamp(2, ts);
				 k=ps_category.executeUpdate();
				k++;
				System.out.println(" k value s ...... "+k);
				
			}if(k==0){
				xml = xml + "<flag>failure</flag>";
			/*	sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			else{
				xml = xml + "<flag>success</flag>";
				/*sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			
			}
			
			catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
			/*	sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			xml = xml + "</response>";
		}
		if(Command.equalsIgnoreCase("Freeze")){
			xml+="<response><command>Freezed</command>";
			try{
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			String type=request.getParameter("freeze");
			String d_val=request.getParameter("date_val");
			System.out.println("string d_val "+d_val);
			 String[] sd=request.getParameter("date_val").split("/");
	         cal=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	         java.util.Date d=cal.getTime();
	         txtCrea_date=new Date(d.getTime());
	         System.out.println("creation date ..."+txtCrea_date);
				
			String Fin_year=request.getParameter("fin_year");
			
			qry = "update TWAD_BOARD_OUTSTANDINGS_FZD set FREEZED=?" +
					",UPDATED_BY_USER_ID=?,UPDATED_DATE=?,FREEZE_DATE=? where ACCOUNTING_UNIT_ID ="
						+ unit_id + " and YEAR='" + Fin_year+"'" ;
			System.out.println(qry);
			PreparedStatement ps_frd=con.prepareStatement(qry);
			ps_frd.setString(1, type);
			//ps_frd.setString(2, d_val);
			ps_frd.setString(2, userid);
			ps_frd.setTimestamp(3, ts);
			ps_frd.setDate(4, txtCrea_date);
			
			k=ps_frd.executeUpdate();
			System.out.println(" k value s ...... "+k);
			
			if(k==0){
				xml = xml + "<flag>failure</flag>";
			/*	sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			else{
				xml = xml + "<flag>success</flag>";
				/*sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			
		}
			
			catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
			/*	sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			xml = xml + "</response>";
		}
		if(Command.equalsIgnoreCase("UnFreeze"))
		{

			xml+="<response><command>UnFreeze</command>";
			try{
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			String type=request.getParameter("freeze");
			String d_val=request.getParameter("date_val");
			System.out.println("string d_val "+d_val);
			 String[] sd=request.getParameter("date_val").split("/");
	         cal=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	         java.util.Date d=cal.getTime();
	         txtCrea_date=new Date(d.getTime());
	         System.out.println("creation date ..."+txtCrea_date);
				
			String Fin_year=request.getParameter("fin_year");
			
			qry = "update TWAD_BOARD_OUTSTANDINGS_FZD set FREEZED=?" +
					//",FREEZE_DATE=to_date(?,'DD/MM/YYYY')" +
					",UPDATED_BY_USER_ID=?,UPDATED_DATE=?,REQUESTED_DATE=? where ACCOUNTING_UNIT_ID ="
						+ unit_id + " and YEAR='" + Fin_year+"'" ;
			System.out.println(qry);
			PreparedStatement ps_frd=con.prepareStatement(qry);
			ps_frd.setString(1, "R");
			//ps_frd.setString(2, d_val);
			ps_frd.setString(2, userid);
			ps_frd.setTimestamp(3, ts);
			ps_frd.setDate(4, txtCrea_date);
			
			k=ps_frd.executeUpdate();
			System.out.println(" k value s ...... "+k);
			
			if(k==0){
				xml = xml + "<flag>failure</flag>";
		
			}
			else{
				xml = xml + "<flag>success</flag>";
				
			}
			
		}
			
			catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
			
			}
			xml = xml + "</response>";
		
		}
		if(Command.equalsIgnoreCase("updaet_gen")){
			xml+="<response><command>updaet_gen</command>";
			try{
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			int sno_val=Integer.parseInt(request.getParameter("sno_val"));
			
		String d_val=request.getParameter("date_val");
		String fin_year=request.getParameter("fin_year");
		String pro_id=request.getParameter("pro_id");
		String type=request.getParameter("type");
		 String[] sd=request.getParameter("date_val").split("/");
         cal=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
         java.util.Date d=cal.getTime();
         txtCrea_date=new Date(d.getTime());
         System.out.println("creation date ..."+txtCrea_date);
         double Balnce=Double.parseDouble(request.getParameter("Balnce"));
		System.out.println("string d_val "+d_val);
		qry="update twad_board_outstandings set BALANCE="+Balnce+" where YEAR='"+fin_year+"' and ACCOUNTING_UNIT_ID="+unit_id+" and PROGRAM_ID="+pro_id+" and OUTSTANDING_TYPE='"+type+"'"+"  and SLNO="+sno_val;
		System.out.println("update ...."+qry);
		ps_category=con.prepareStatement(qry);
		k=ps_category.executeUpdate();
		k++;
		System.out.println(" k value s ...... "+k);
		
	if(k==0){
		xml = xml + "<flag>failure</flag>";
	/*	sendMessage(response,
				"Records Inserted Successfully ............ ",
				"ok", "Twad_report_ser.jsp");*/
	}
	else{
		xml = xml + "<flag>success</flag>";
		/*sendMessage(response,
				"Records Inserted Successfully ............ ",
				"ok", "Twad_report_ser.jsp");*/
	}
	
			
			}catch (Exception e) {
			System.out.println(e);
			xml = xml + "<flag>failure</flag>";
		/*	sendMessage(response,
					"Records Inserted Successfully ............ ",
					"ok", "Twad_report_ser.jsp");*/
		}
		xml = xml + "</response>";
		}if(Command.equalsIgnoreCase("Update")) {
			xml+="<response><command>Update</command>";
			try{
				String type=request.getParameter("type");
			xml+="<type>"+type+"</type>";
			
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			
		String d_val=request.getParameter("date_val");
		System.out.println("string d_val "+d_val+" ... type"+type);
		 String[] sd=request.getParameter("date_val").split("/");
         cal=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
         java.util.Date d=cal.getTime();
         txtCrea_date=new Date(d.getTime());
         System.out.println("creation date ..."+txtCrea_date);
			String Fin_year=request.getParameter("fin_year");
			String pro_id=request.getParameter("pro_id");
			String sch_name=request.getParameter("sch_id");
			double AmtRec_id=Double.parseDouble(request.getParameter("AmtRec_id"));
			double ExpBook_id=Double.parseDouble(request.getParameter("ExpBook_id"));
			double ExpAct_id=Double.parseDouble(request.getParameter("ExpAct_id"));
			//double Bal_id=Double.parseDouble(request.getParameter("Bal_id"));
			//double Bal_id=0;
			double incur_id=Double.parseDouble(request.getParameter("incur_id"));
			double tie_id=Double.parseDouble(request.getParameter("tie_id"));
			int sno=Integer.parseInt(request.getParameter("sno"));
			String lb_type=request.getParameter("lb_type");
			String local=request.getParameter("local");
			
			System.out.println(local+"  ,,,,,,,");
			int District=Integer.parseInt(request.getParameter("Dist_val"));
			System.out.println(District+"  ,,,,District ,,");
			qry_str="update twad_board_outstandings set PROGRAM_ID="+pro_id+"," +
					"INDIVIDUAL_SCHEME_NAME='"+sch_name+"',AMT_RECEIVED_UPTO_MAR2011=0," +
					"AMT_RECEIVED_UPTO_CUR_MAR="+AmtRec_id+",EXP_BOOKED_UPTO_MAR2011="+ExpBook_id+"," +
					"BILLS_PENDING="+ExpAct_id+"," +
					"EXP_BOOKED_UPTO_CUR_MAR="+incur_id+",UPDATED_BY_USER_ID='"+userid+"'" +
					",TIE_UP_AMT="+tie_id+",DISTRICT_ID=" +District+
							",LB_TYPE='"+lb_type+"',LOCAL_BODY='"+local+
					"',UPDATED_DATE=? where ACCOUNTING_UNIT_ID=" +unit_id+ 
					"  and YEAR='" +Fin_year+ "' and OUTSTANDING_TYPE='" +type+ "' and SLNO=" +sno;
			

			System.out.println(" pro ================== "+qry_str);
			ps_category=con.prepareStatement(qry_str);
			ps_category.setTimestamp(1, ts);
			 k=ps_category.executeUpdate();
			k++;
			System.out.println(" k value s ...... "+k);
			
		if(k==0){
			xml = xml + "<flag>failure</flag>";
		/*	sendMessage(response,
					"Records Inserted Successfully ............ ",
					"ok", "Twad_report_ser.jsp");*/
		}
		else{
			xml = xml + "<flag>success</flag>";
			/*sendMessage(response,
					"Records Inserted Successfully ............ ",
					"ok", "Twad_report_ser.jsp");*/
		}
		
			}catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
			/*	sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			xml = xml + "</response>";
		}if(Command.equalsIgnoreCase("Delete")) 
		{
			xml+="<response><command>Delete</command>";
			try{
				String type=request.getParameter("type");
			xml+="<type>"+type+"</type>";
			
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			
		String d_val=request.getParameter("date_val");
		System.out.println("string d_val "+d_val+" ... type"+type);
			String Fin_year=request.getParameter("fin_year");
			String pro_id=request.getParameter("pro_id");
			String sch_name=request.getParameter("sch_id");
			String AmtRec_id=request.getParameter("AmtRec_id");
			String ExpBook_id=request.getParameter("ExpBook_id");
			String ExpAct_id=request.getParameter("ExpAct_id");
			double Bal_id=0;
			//String Bal_id=request.getParameter("Bal_id");
			String incur_id=request.getParameter("incur_id");
			int sno=Integer.parseInt(request.getParameter("sno"));
		
			qry_str="delete from twad_board_outstandings where PROGRAM_ID="+pro_id+" and " +
					"INDIVIDUAL_SCHEME_NAME='"+sch_name+"' and AMT_RECEIVED_UPTO_MAR2011=0 and " +
					"AMT_RECEIVED_UPTO_CUR_MAR="+AmtRec_id+" and EXP_BOOKED_UPTO_MAR2011="+ExpBook_id+" and " +
					"BILLS_PENDING="+ExpAct_id+" and " +
					"EXP_BOOKED_UPTO_CUR_MAR="+incur_id+" and UPDATED_BY_USER_ID='"+userid+"'" +
					" and  ACCOUNTING_UNIT_ID=" +unit_id+ 
					"  and YEAR='" +Fin_year+ "' and OUTSTANDING_TYPE='" +type+ "' and SLNO=" +sno;
			

			System.out.println(" pro ================== "+qry_str);
			ps_category=con.prepareStatement(qry_str);
			
			 k=ps_category.executeUpdate();
			k++;
			System.out.println(" k value s ...... "+k);
			
		if(k==0){
			xml = xml + "<flag>failure</flag>";
		/*	sendMessage(response,
					"Records Inserted Successfully ............ ",
					"ok", "Twad_report_ser.jsp");*/
		}
		else{
			xml = xml + "<flag>success</flag>";
			/*sendMessage(response,
					"Records Inserted Successfully ............ ",
					"ok", "Twad_report_ser.jsp");*/
		}
		
			}catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
			/*	sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			xml = xml + "</response>";
		}
		if(Command.equalsIgnoreCase("Board_Liability")) {

			xml+="<response><command>Board_Liability</command>";
			try{
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			
		String d_val=request.getParameter("date_val");
		String type=request.getParameter("type");
		System.out.println("string d_val "+d_val);
		 String[] sd=request.getParameter("date_val").split("/");
         cal=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
         java.util.Date d=cal.getTime();
         txtCrea_date=new Date(d.getTime());
         System.out.println("creation date ..."+txtCrea_date);
			String Fin_year=request.getParameter("fin_year");
			String[] pro_id=request.getParameterValues("pro_id");
			//System.out.println("string proid vdahkd"+pro_id);
			String[] sch_name=request.getParameterValues("sch_id");
			String[] tie_id=request.getParameterValues("tie_id");
			String[] AmtRec_id=request.getParameterValues("AmtRec_id");
			String[] ExpBook_id=request.getParameterValues("ExpBook_id");
			String[] ExpAct_id=request.getParameterValues("ExpAct_id");
			//String[] Bal_id=request.getParameterValues("Bal_id");
			String[] incur_id=request.getParameterValues("incur_id");
			String[] main_Cmb=request.getParameterValues("main_Cmb");
			String[] sub_Cmb=request.getParameterValues("sub_Div");
			String[] main_Type=request.getParameterValues("main_Type");
			String[] local_by=request.getParameterValues("local_by");
			int len=pro_id.length;
			System.out.println("------------------------------"+len);
			int count=0;
			PreparedStatement p_fzd=null;
			ResultSet r_fzd=null;
				String frz_qry="select count(*)as count from TWAD_BOARD_OUTSTANDINGS_FZD where " +
						"YEAR='"+Fin_year+"' and ACCOUNTING_UNIT_ID="+unit_id;
				try{
					 p_fzd=con.prepareStatement(frz_qry);
					 r_fzd=p_fzd.executeQuery();
					 if(r_fzd.next()){
							System.out.println(r_fzd.getInt("count"));
							count=r_fzd.getInt("count");
							
						}System.out.println("count value is ...... "+count);
					 if(count==0){
							String ins_fz="insert into TWAD_BOARD_OUTSTANDINGS_FZD (YEAR,ACCOUNTING_UNIT_ID,FREEZED," +
									"UPDATED_BY_USER_ID,UPDATED_DATE) values( '" +Fin_year+"'," +unit_id+",'N','"+userid+"',?) ";
							try{
								System.out.println("...... >> "+ins_fz);
								 p_fzd=con.prepareStatement(ins_fz);
								 //p_fzd.setDate(1, txtCrea_date);
								 p_fzd.setTimestamp(1, ts);
								 int fzd=p_fzd.executeUpdate();
								 if(fzd==0){
									
											xml = xml + "<flag_frZ>failure</flag_frZ>";
										
										}
										else{
											xml = xml + "<flag_frZ>success</flag_frZ>";
										
										}
								 
						       }catch (Exception e) {
							System.out.println("Error in 429 th Line ...... "+e);
						       }
						}
				}catch (Exception e) {
System.out.println("Error in 429 th Line ...... "+e);
				}
				
			
			for(int i=0;i<len;i++){
				qry_str="select decode(max(SLNO),null,0,max(SLNO)) as no from twad_board_outstandings";
				
				try{
					ps=con.prepareStatement(qry_str);
					rs=ps.executeQuery();
					if(rs.next())
					sl_no=rs.getInt("no");
				   sl_no++;
				   System.out.println(" no value :"+sl_no);
				}catch (Exception e) {
                 System.out.println(e);				}
				int Program=Integer.parseInt(pro_id[i]);
				String scheme=sch_name[i];
				String lb=local_by[i];
				String main_local=main_Cmb[i];
				String main=main_Type[i];
				double Amt_rec=Double.parseDouble(AmtRec_id[i]);	
				double tie_Amt=Double.parseDouble(tie_id[i]);	
				double Exp_booked=Double.parseDouble(ExpBook_id[i]);
				double Exp_Act=Double.parseDouble(ExpAct_id[i]);
			//double Balance=Double.parseDouble(Bal_id[i]);
				double incur_Amt=Double.parseDouble(incur_id[i]);
				double Balance=0;
				int sub_menu=Integer.parseInt(sub_Cmb[i]);
				
				qry="INSERT " +
				" INTO twad_board_outstandings  " +
				"  ( " +
				"    YEAR, " +
				"    ACCOUNTING_UNIT_ID, " +
				"    PROGRAM_ID, " +
				"    INDIVIDUAL_SCHEME_NAME, " +
				"    AMT_RECEIVED_UPTO_MAR2011, " +
				"    AMT_RECEIVED_UPTO_CUR_MAR, " +
				"    EXP_BOOKED_UPTO_MAR2011, " +
			    "    EXP_BOOKED_UPTO_CUR_MAR, " +
			   "    ENTRY_DATE, " +
				"    BALANCE, " +
				"    LOCAL_BODY, " +
				"    BILLS_PENDING, " +
				"    UPDATED_BY_USER_ID, " +
				"    UPDATED_DATE, " +
				"    SLNO, " +
				"    OUTSTANDING_TYPE,TIE_UP_AMT,LB_TYPE,DISTRICT_ID " +
				"  ) " +
				"  VALUES " +
				"  ( '" +Fin_year+"'"+
				", " +unit_id+
				", " +Program+
				", '" +scheme+"'"+
				" ,0," +Amt_rec+
				" ," +Exp_booked+" ," +incur_Amt+
				",?"+
				"," +Balance+",'"+lb+"'"+
				", " +Exp_Act+
				", '" +userid+"',"+
				"? " +
				", " +sl_no+
				",'"+type+"',"+tie_Amt+",'"+main+"',"+sub_menu+" )";
				
				System.out.println(" pro ================== "+qry);
				ps_category=con.prepareStatement(qry);
				
				ps_category.setDate(1, txtCrea_date);
				ps_category.setTimestamp(2, ts);
				 k=ps_category.executeUpdate();
				k++;
				System.out.println(" k value s ...... "+k);
				
			}if(k==0){
				xml = xml + "<flag>failure</flag>";
			/*	sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			else{
				xml = xml + "<flag>success</flag>";
				/*sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			
			}
			
			catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
				/*sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			xml = xml + "</response>";
		
		}
		
		
		if(Command.equalsIgnoreCase("FreezeDetail"))
		{
			xml+="<response><command>FreezeDetail</command>";
			try{
				int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
				String count="";
			String d_val=request.getParameter("date_val");
		    String out_type=request.getParameter("type");
			String fin_year=request.getParameter("fin_year");
			String fr_dat="select FREEZED from TWAD_BOARD_OUTSTANDINGS_FZD where ACCOUNTING_UNIT_ID= " +unit_id+
			" AND YEAR                ='"+fin_year+"'";
			
				System.out.println("freeze details qry .... "+fr_dat);
				ps=con.prepareStatement(fr_dat);
				rs=ps.executeQuery();
				c=0;
				while(rs.next()){
					count=rs.getString("FREEZED");System.out.println("co"+ count);
					xml+="<FREEZED>"+rs.getString("FREEZED")+"</FREEZED>";
				c++;
			}
				if(c==0)
				{
					xml = xml + "<flag>failure</flag>";
				}else
				{
					xml = xml + "<flag>success</flag>";	
				}
			}catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
	System.out.println(e);
			}	
			
			xml = xml + "</response>";
		}
		
		
		if(Command.equalsIgnoreCase("LocalBY"))
		{
			xml+="<response><command>LocalBY</command>";
			try{
				qry="select URBANLB_TYPE_ID,URBANLB_TYPE_DESC from COM_MST_URBAN_LB_TYPE_CP";
				System.out.println("qry .... "+qry);
				ps_category=con.prepareStatement(qry);
				rs_category=ps_category.executeQuery();
				c=0;
				
				while(rs_category.next()){
					xml+="<URBANLB_TYPE_ID>"+rs_category.getInt("URBANLB_TYPE_ID")+"</URBANLB_TYPE_ID>";
					xml+="<URBANLB_TYPE_DESC>"+rs_category.getString("URBANLB_TYPE_DESC")+"</URBANLB_TYPE_DESC>";
				c++;
				}
				if(c>0){xml = xml + "<flag>success</flag>";}
				else{xml = xml + "<flag>failure</flag>";}
					}catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
				/*sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			xml = xml + "</response>";
		}
		if(Command.equalsIgnoreCase("Add_oth")){
			xml+="<response><command>Add_oth</command>";
			try{
				int id=0;
				int txtAcc_UnitCode=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
				String others=request.getParameter("others");
			
				String qry_count="select count(*)as no from FAS_TWAD_LB_OTHERS where ACCOUNTING_UNIT_ID= "+txtAcc_UnitCode;
				System.out.println("qry_count ...."+qry_count);
				try{
				ps_category=con.prepareStatement(qry_count);
				rs_category=ps_category.executeQuery();
				while(rs_category.next())
				{
				id=rs_category.getInt("no");
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("id ... "+id);
				id=id+1;;
				qry="insert into FAS_TWAD_LB_OTHERS (ACCOUNTING_UNIT_ID,ID,NAME) values("+txtAcc_UnitCode+","+id+",'"+others+"')";
				System.out.println("qry ::::: "+qry);
				ps=con.prepareStatement(qry);
				 k=ps.executeUpdate();
				 System.out.println(" k value others .. "+k);
		if(k==0){
			xml = xml + "<flag>failure</flag>";
		}else{
			xml = xml + "<flag>success</flag>";
		}
			}
			catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
				}
			xml = xml + "</response>";
			
		}
		
		if(Command.equalsIgnoreCase("loadOnchange")){
			xml+="<response><command>loadOnchange</command>";
			try{
				String LB_id=request.getParameter("LB_id");
				c=0;
				if(LB_id.equalsIgnoreCase("Corporation")){
					xml+="<LB_Type>C</LB_Type>";
					qry="select DISTRICT_CODE,CORP_CODE,corporation_eng,'C' as Type from pms_corporation ";
					System.out.println("qry .... "+qry);
					ps_category=con.prepareStatement(qry);
					rs_category=ps_category.executeQuery();
					while(rs_category.next()){
						xml+="<DCODE>"+rs_category.getString("DISTRICT_CODE")+"</DCODE>";
						xml+="<ID>"+rs_category.getInt("CORP_CODE")+"</ID>";
						xml+="<DESC>"+rs_category.getString("corporation_eng")+"</DESC>";
						
					c++;
				}
				}else if(LB_id.equalsIgnoreCase("Municipality")){
					xml+="<LB_Type>M</LB_Type>";
					qry="select DISTRICT_CODE,MUCODE,MUNICIPALITY_ENG,'M' as Type from pms_mst_municipality";
					System.out.println("qry .... "+qry);
					ps_category=con.prepareStatement(qry);
					rs_category=ps_category.executeQuery();
					while(rs_category.next()){
						xml+="<DCODE>"+rs_category.getInt("DISTRICT_CODE")+"</DCODE>";
						xml+="<ID>"+rs_category.getInt("MUCODE")+"</ID>";
						xml+="<DESC>"+rs_category.getString("MUNICIPALITY_ENG")+"</DESC>";
						//xml+="<LB_Type>"+rs_category.getString("Type")+"</LB_Type>";
					c++;
				}
				}else if(LB_id.equalsIgnoreCase("Townpanchayat")){
					xml+="<LB_Type>T</LB_Type>";
					qry="select DCODE,TPCODE,TPNAME,'T' as Type from PMS_MST_TOWN_PANCHAYATS";
					System.out.println("qry .... "+qry);
					ps_category=con.prepareStatement(qry);
					rs_category=ps_category.executeQuery();
					while(rs_category.next()){
						xml+="<DCODE>"+rs_category.getInt("DCODE")+"</DCODE>";
						xml+="<ID>"+rs_category.getInt("TPCODE")+"</ID>";
						xml+="<DESC>"+rs_category.getString("TPNAME")+"</DESC>";
					//	xml+="<LB_Type>"+rs_category.getString("Type")+"</LB_Type>";
					c++;
				}
				}else if(LB_id.equalsIgnoreCase("Others")){
					int Cmb_UnitCode=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
					xml+="<LB_Type>O</LB_Type>";
					qry="select ID,ACCOUNTING_UNIT_ID,NAME,'O' as Type from FAS_TWAD_LB_OTHERS where ACCOUNTING_UNIT_ID="+Cmb_UnitCode;
					System.out.println("qry .... "+qry);
					ps_category=con.prepareStatement(qry);
					rs_category=ps_category.executeQuery();
					while(rs_category.next()){
						xml+="<DCODE>"+rs_category.getInt("ACCOUNTING_UNIT_ID")+"</DCODE>";
						xml+="<ID>"+rs_category.getInt("ID")+"</ID>";
						xml+="<DESC>"+rs_category.getString("NAME")+"</DESC>";
						//xml+="<LB_Type>"+rs_category.getString("Type")+"</LB_Type>";
					c++;
				}	
				}
				else{
					System.out.println("Error in Local Body selection ......");
				}
				if(c>0){xml = xml + "<flag>success</flag>";}
				else{xml = xml + "<flag>failure</flag>";}
			}catch (Exception e) {
				System.out.println(e);
				xml = xml + "<flag>failure</flag>";
				/*sendMessage(response,
						"Records Inserted Successfully ............ ",
						"ok", "Twad_report_ser.jsp");*/
			}
			xml = xml + "</response>";
			
		}
		if(Command.equalsIgnoreCase("user_Role")){
			xml+="<response><command>user_Role</command>";
			try{
				int id=Integer.parseInt(request.getParameter("Empid_val"));
			qry="SELECT " +
			"  CASE " +
			"    WHEN aa.count=0 " +
			"    THEN 0 " +
			"    WHEN aa.count>0 " +
			"    THEN 1 " +
			"  END AS flag " +
			"FROM " +
			"  (SELECT COUNT(*)AS COUNT " +
			"  FROM SEC_MST_USER_ROLES " +
			"  WHERE EMPLOYEE_ID IN " +
			"    (SELECT DISTINCT EMPLOYEE_ID FROM SEC_MST_USER_ROLES WHERE ROLE_ID =97 " +
			"    ) " +
			"  AND EMPLOYEE_ID=" +id+
			"  )aa";
			System.out.println("qry .... "+qry);
			ps_category=con.prepareStatement(qry);
			rs_category=ps_category.executeQuery();
			while(rs_category.next()){
				xml+="<ID_flag>"+rs_category.getInt("flag")+"</ID_flag>";	
			}
			}catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				System.out.println("Error in user_Role   ... "+e);
			}
			xml = xml + "</response>";
		}
		
		
		if(Command.equalsIgnoreCase("Load_grid")){
			xml+="<response><command>Load_grid</command>";
			try{
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			String count="";
		String d_val=request.getParameter("date_val");
	    String out_type=request.getParameter("type");
		String fin_year=request.getParameter("fin_year");
		String fr_dat="select FREEZED from TWAD_BOARD_OUTSTANDINGS_FZD where ACCOUNTING_UNIT_ID= " +unit_id+
		" AND YEAR                ='"+fin_year+"'";
		try{
			System.out.println("qry .... "+fr_dat);
			ps=con.prepareStatement(fr_dat);
			rs=ps.executeQuery();
			while(rs.next()){
				count=rs.getString("FREEZED");System.out.println("co"+ count);
			}
		}catch (Exception e) {
System.out.println(e);
		}
		xml+="<FREEZED>"+count+"</FREEZED><out_type>"+out_type+"</out_type>";
		System.out.println("string d_val "+d_val+"  typr value is ... "+out_type);
		qry="SELECT a.*,(select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID )as unit_Desc," +
	    " case "+
		   " when a.LB_TYPE='C' then (select corporation_eng from pms_corporation where DISTRICT_CODE=a.DISTRICT_ID and CORP_CODE=a.LOCAL_BODY)"+
		   " when a.LB_TYPE='T' then (select TPNAME from PMS_MST_TOWN_PANCHAYATS where DCODE=a.DISTRICT_ID and TPCODE=a.LOCAL_BODY)"+
		   " when a.LB_TYPE='M' then (select MUNICIPALITY_ENG from pms_mst_municipality where DISTRICT_CODE=a.DISTRICT_ID and MUCODE=a.LOCAL_BODY)"+
		   " when a.LB_TYPE='O' then (select NAME from FAS_TWAD_LB_OTHERS where ACCOUNTING_UNIT_ID=a.DISTRICT_ID and  ID=a.LOCAL_BODY)"+
		   " else '-'"+
		   " end as Lbody_DESC,"+		
		"(select IMISCLASSIFICATION from IMISGROUPING where ID=a.PROGRAM_ID )as program_desc " +
		"FROM twad_board_outstandings a " +
		"WHERE ACCOUNTING_UNIT_ID= " +unit_id+
		" AND YEAR                ='"+fin_year+"' " +
		"AND OUTSTANDING_TYPE    ='" +out_type+"' " +
		"ORDER BY slno";
		System.out.println("qry .... "+qry);
		ps_category=con.prepareStatement(qry);
		rs_category=ps_category.executeQuery();
		c=0;
		if(out_type.equalsIgnoreCase("G")){
		while(rs_category.next()){
			c++;
			xml+="<YEAR>"+rs_category.getString("YEAR")+"</YEAR>";
			xml+="<ACCOUNTING_UNIT_ID>"+rs_category.getString("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";
			xml+="<unit_Desc>"+rs_category.getString("unit_Desc")+"</unit_Desc>";
			xml+="<PROGRAM_ID>"+rs_category.getString("PROGRAM_ID")+"</PROGRAM_ID>";
			xml+="<program_desc>"+rs_category.getString("program_desc")+"</program_desc>";
			xml+="<BALANCE"+c+">"+rs_category.getString("BALANCE")+"</BALANCE"+c+">";
			xml+="<INDIVIDUAL_SCHEME_NAME>"+rs_category.getString("INDIVIDUAL_SCHEME_NAME")+"</INDIVIDUAL_SCHEME_NAME>";
			xml+="<AMT_RECEIVED_UPTO_MAR2011>"+rs_category.getString("AMT_RECEIVED_UPTO_MAR2011")+"</AMT_RECEIVED_UPTO_MAR2011>";
			xml+="<AMT_RECEIVED_UPTO_CUR_MAR>"+rs_category.getString("AMT_RECEIVED_UPTO_CUR_MAR")+"</AMT_RECEIVED_UPTO_CUR_MAR>";
			xml+="<EXP_BOOKED_UPTO_MAR2011>"+rs_category.getString("EXP_BOOKED_UPTO_MAR2011")+"</EXP_BOOKED_UPTO_MAR2011>";
			xml+="<EXP_BOOKED_UPTO_CUR_MAR>"+rs_category.getString("EXP_BOOKED_UPTO_CUR_MAR")+"</EXP_BOOKED_UPTO_CUR_MAR>";
			xml+="<ENTRY_DATE>"+rs_category.getDate("ENTRY_DATE")+"</ENTRY_DATE>";
			xml+="<EXP_ACTUALLY_PAID>"+rs_category.getString("BILLS_PENDING")+"</EXP_ACTUALLY_PAID>";
			xml+="<SLNO>"+rs_category.getString("SLNO")+"</SLNO>";
			xml+="<OUTSTANDING_TYPE>"+rs_category.getString("OUTSTANDING_TYPE")+"</OUTSTANDING_TYPE>";
			
			
			
		}
		}
		else if(out_type.equalsIgnoreCase("F")||out_type.equalsIgnoreCase("T")){
			while(rs_category.next()){
			c++;
			xml+="<YEAR>"+rs_category.getString("YEAR")+"</YEAR>";
			xml+="<ACCOUNTING_UNIT_ID>"+rs_category.getString("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";
			xml+="<unit_Desc>"+rs_category.getString("unit_Desc")+"</unit_Desc>";
			xml+="<PROGRAM_ID>"+rs_category.getString("PROGRAM_ID")+"</PROGRAM_ID>";
			xml+="<program_desc>"+rs_category.getString("program_desc")+"</program_desc>";
			xml+="<BALANCE>"+rs_category.getString("BALANCE")+"</BALANCE>";
			xml+="<LOCAL_BODY>"+rs_category.getString("Lbody_DESC")+"</LOCAL_BODY>";
			xml+="<LOCAL_BODY1>"+rs_category.getString("LOCAL_BODY")+"</LOCAL_BODY1>";
			xml+="<INDIVIDUAL_SCHEME_NAME>"+rs_category.getString("INDIVIDUAL_SCHEME_NAME")+"</INDIVIDUAL_SCHEME_NAME>";
			xml+="<AMT_RECEIVED_UPTO_MAR2011>"+rs_category.getString("AMT_RECEIVED_UPTO_MAR2011")+"</AMT_RECEIVED_UPTO_MAR2011>";
			xml+="<AMT_RECEIVED_UPTO_CUR_MAR>"+rs_category.getString("AMT_RECEIVED_UPTO_CUR_MAR")+"</AMT_RECEIVED_UPTO_CUR_MAR>";
			xml+="<EXP_BOOKED_UPTO_MAR2011>"+rs_category.getString("EXP_BOOKED_UPTO_MAR2011")+"</EXP_BOOKED_UPTO_MAR2011>";
			xml+="<EXP_BOOKED_UPTO_CUR_MAR>"+rs_category.getString("EXP_BOOKED_UPTO_CUR_MAR")+"</EXP_BOOKED_UPTO_CUR_MAR>";
			xml+="<ENTRY_DATE>"+rs_category.getDate("ENTRY_DATE")+"</ENTRY_DATE>";
			xml+="<EXP_ACTUALLY_PAID>"+rs_category.getString("BILLS_PENDING")+"</EXP_ACTUALLY_PAID>";
			xml+="<SLNO>"+rs_category.getString("SLNO")+"</SLNO>";
			xml+="<OUTSTANDING_TYPE>"+rs_category.getString("OUTSTANDING_TYPE")+"</OUTSTANDING_TYPE>";
			xml+="<TIE_UP_AMT>"+rs_category.getString("TIE_UP_AMT")+"</TIE_UP_AMT>";
			xml+="<LB_TYPE>"+rs_category.getString("LB_TYPE")+"</LB_TYPE>";
			xml+="<DISTRICT_ID>"+rs_category.getInt("DISTRICT_ID")+"</DISTRICT_ID>";
		}
		}
		System.out.println(" count vALUE ......... "+c);
		
		if(c>0){xml = xml + "<flag>success</flag>";}
		else{xml = xml + "<flag>failure</flag>";}
			}
		catch (Exception e) {
			System.out.println(e);
			xml = xml + "<flag>failure</flag>";
			/*sendMessage(response,
					"Records Inserted Successfully ............ ",
					"ok", "Twad_report_ser.jsp");*/
		}
		xml = xml + "</response>";
	
	}
		System.out.println(xml);
		out.write(xml);
		out.close();
		
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con = null;
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
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		String qry="",xml="";
		String Command = request.getParameter("Command");
		String head="",fin="";
		if (Command.equalsIgnoreCase("PDF")) {
			int unit_id=Integer.parseInt(request.getParameter("txtAcc_UnitCode"));
			
			String d_val=request.getParameter("date_val");
		    String out_type=request.getParameter("type");
			String fin_year=request.getParameter("fin_year");

			File reportFile = null;
			try {
				System.out.println("calling servlet...");
				if(out_type.equalsIgnoreCase("G")){
					head="General Liability to be paid by the Board detail Report ";
					fin="Financial Year :"+fin_year;
					xml="General Liability";
				reportFile = new File(getServletContext().getRealPath(
						"/org/FAS/FAS1/MIS/jaspers/TWAD_OS/Twad_os_Gen.jasper"));
			}else if(out_type.equalsIgnoreCase("T")){
				head="Outstanding Liability to be paid by the Board ";
				fin="Financial Year :"+fin_year;
				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/MIS/jaspers/TWAD_OS/Twad_os_LIABILTY_fin.jasper"));
				
	        }else if(out_type.equalsIgnoreCase("F")){
	        	head="Outstanding Receivables to be received by the Board";
	        	fin="Financial Year :"+fin_year;
				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/MIS/jaspers/TWAD_OS/Twad_os_LIABILTY_fin.jasper"));
				
	        }else if(out_type.equalsIgnoreCase("Summary")){
	        	head=" Report for Abstract Details of Liability in respect of the Schemes taken up before 31.3.2011";
	        	fin="Financial Year :"+fin_year;
				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/MIS/jaspers/TWAD_OS/Twad_os_Summary.jasper"));
				
	        }else if(out_type.equalsIgnoreCase("Liability")){
	        	head=" Report for Liability Details in respect of the Schemes taken up before 31.3.2011";
	        	fin="Financial Year :"+fin_year;
				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/MIS/jaspers/TWAD_OS/Twad_os_Abstract_HOjrxml.jasper"));
				
	        }else if(out_type.equalsIgnoreCase("Details")){
	        	head=" Report for Local Body Details of Liability in respect of the Schemes taken up before 31.3.2011";
	        	fin="Financial Year :"+fin_year;
				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/MIS/jaspers/TWAD_OS/Twad_os_Summary_All_Dteails.jasper"));
				xml="";
	        }else if(out_type.equalsIgnoreCase("REG_Detail")){
	        	int reg_id=Integer.parseInt(request.getParameter("sub_div"));
	        	String desc=request.getParameter("desc");
	        	head=desc;
	        	fin=fin_year;
				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/MIS/jaspers/TWAD_OS/Twad_os_Summary_All_Dteails.jasper"));
				xml="  where test1.REGID="+reg_id;
	        }
	        /*}else if(out_type.equalsIgnoreCase("Local Body")){
	        	head=" Report for Local Body Details of Liability in respect of the Schemes taken up before 31.3.2011";
	        	fin="Financial Year :"+fin_year;
				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/MIS/jaspers/TWAD_OS/Twad_os_reGION.jasper"));
				
	        }*/
				else
	        {
	        	System.out.println("Error in reportFile");
	        }
				System.out.println("..." + reportFile);
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();
				

				map.put("fin_year", fin_year);
				map.put("unit_id", unit_id);
				map.put("type", out_type);
				map.put("fin", fin);
				map.put("head", head);
				map.put("xml", xml);
				

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				System.out.println("upto");

				if (Command.equalsIgnoreCase("PDF")) {
					System.out.println(Command);
					byte buf[] = JasperExportManager
							.exportReportToPdf(jasperPrint);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					// response.setHeader("content-disposition",
					// "inline;filename=OpenActionItems.pdf");
					// response.setContentType("application/force-download");

					response.setHeader("Content-Disposition",
							"attachment;filename=\"("+unit_id+")"+xml+".pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				}
			} catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println(connectMsg);
			}
		}
	
	}
	private void sendMessage(HttpServletResponse response, String msg,
			String bType, String jsp) {
		try {
			String url = "org/FAS/FAS1/MIS/jsps/MessengerOkBack.jsp?message="
					+ msg + "&button=" + bType + "&jspname=" + jsp;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

