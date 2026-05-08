package Servlets.PMS.PMS1.DCB.servlets;
/* 
 *  
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 20/09/2011
   
  */
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

//import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import java.io.*;
import java.util.Date;
import jxl.*;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;

public class Controller {
	public Statement stmt = null;
	public static ResultSet rs = null;
	public Connection con;
	public static ResultSet rs2 = null;
	public static String  new_cond=" STATUS='L' and ";
	public static String meter_status=" METER_STATUS='L' and ";
	public static String meter_status2=" PMS_DCB_MST_BENEFICIARY_METRE.METER_STATUS='L' and  ";
	public static String meter_status3=" PMS_DCB_MST_BENEFICIARY_METRE.METER_STATUS='L' and (1=1)  ";  
	public static String meter_ben_same=" METRE_SNO  in (select METRE_SNO  from PMS_DCB_MST_BENEFICIARY_METRE  where  meter_status='L'  ";
	public static String set_office="0";
	public static String meter_not_for_ben="";
	synchronized public Connection con() throws Exception {
		ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
		String ConnectionString = "";
		String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
		String strdsn = rs1.getString("Config.DSN");
		String strhostname = rs1.getString("Config.HOST_NAME");
		String strportno = rs1.getString("Config.PORT_NUMBER");
		String strsid = rs1.getString("Config.SID");
		String strdbusername = rs1.getString("Config.USER_NAME");
		String strdbpassword = rs1.getString("Config.PASSWORD");
		//ConnectionString = strdsn.trim()+"@"+strhostname.trim()+":"+strportno.trim()+":"+strsid.trim();
		ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
		ConnectionPool c_p=new ConnectionPool(strDriver,ConnectionString,strdbusername,strdbpassword,0,200,true);
		con=c_p.getConnection();		
		return con;
		/* public ConnectionPool(String driver, String url,
                 String username, String password,
                 int initialConnections,
                 int maxConnections,
                 boolean waitIfBusy)
		
		*/
		
		
	}

	synchronized public void createStatement(Connection con)
			throws SQLException {
		stmt = con.createStatement();
		 
	}

	synchronized public int setUpd(String qry) throws SQLException {

		int row = 0;
		try {
			System.out.println(qry);
			row = stmt.executeUpdate(qry);

		} catch (Exception e) {
			System.out.println("TEST Qry" + e);
		}
		return row;
	} 

	synchronized public int getMax(String table, String field, String userquery)
			throws SQLException {
		String value = "0";
		String qry = "select max(" + field + ") from " + table + " "
				+ userquery;

		rs = stmt.executeQuery(qry);
		try {
			if (rs.next()) {
				value = rs.getString(1);
				if (value == "" || value == null || value.equals(""))
					value = "0";

			}
		} catch (Exception e) {
			value = "0";
		}
		return Integer.parseInt(value) + 1;

	}

	synchronized public String setValue(String input, HttpServletRequest request) {
		input = request.getParameter(input);
		if (input == null || input.equals(""))
			input = "0";
	 return input;
	}
	 

	synchronized public String getValue(String tablename, String name,
			String userquery) throws Exception {
		String fld = "";
		fld = name;
		String qry = "select " + fld + " from " + tablename + " " + userquery;
	  // System.out.println(qry);
		String res_value = "";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		ResultSet rs_cont = null;
		PreparedStatement ps_loca = con_loc.prepareStatement(qry);

		rs_cont = ps_loca.executeQuery();

		try   
		{
				if (rs_cont.next()) {
					res_value = rs_cont.getString(fld);
				}
				else
				{
					res_value = "0";
				}
		 
			  
				if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
					res_value = "0";
		}catch (Exception e) {
			  res_value = "0";
			 System.out.println(e);
		}
		  
		rs_cont.close();
		con_loc.close();
		ps_loca.close();
		 
		return res_value;
	}
	synchronized public String getValue(String tablename, String qrycond,String name,
			String userquery) throws Exception {
		String fld = "";
		fld = qrycond+" as "+name;
		String qry = "select " + fld + " from " + tablename + " " + userquery;
		// System.out.println(qry);
		String res_value = "";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		ResultSet rs_cont = null;
		PreparedStatement ps = con_loc.prepareStatement(qry);

		rs_cont = ps.executeQuery();

		try   
		{
				if (rs_cont.next()) {
					res_value = rs_cont.getString(name);
				}
				else
				{
					res_value = "0";
				}
		 
		
				if (res_value.equals("") || res_value == null || res_value.equalsIgnoreCase(null))
					res_value = "0";
		}catch (Exception e) {
			res_value = "0";
			 System.out.println(e);
		}
		 
		rs_cont.close();
		con_loc.close();
		ps.close();
		 
		return res_value;
	}
	synchronized public int getCount(String tablename, String userquery)
			throws Exception {

		String qry = "select count(*) from " + tablename + " " + userquery;
		String res_value = "";

	   System.out.println(qry);
		ResultSet rs2 = null;
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		PreparedStatement ps = con_loc.prepareStatement(qry);
		rs2 = ps.executeQuery();

		if (rs2.next()) {
			res_value = rs2.getString(1);
		}
		if (res_value.equals("") || res_value == null)
			res_value = "0";
		con_loc.close();
		rs2.close();
		ps.close();
		return Integer.parseInt(res_value);
	}

	synchronized public ResultSet getRS(String userquery) throws Exception {
 
		rs2 = stmt.executeQuery(userquery);
		return rs2;

	}

	public String combo_lkup(String value, String text, String table,
			String userquery, int type, String defaultvalue) throws Exception {

		Statement stmt1 = con.createStatement();
		String xml = "<result>";
		String qry = "select " + value + "," + text + " from " + table + " "
				+ userquery;

		int row = 0;

		if (type == 2) {
			xml += "<name>" + defaultvalue + "</name>";
			xml += "<sno>0</sno>";

		}
		ResultSet rs_loc = stmt1.executeQuery(qry);
		while (rs_loc.next()) {

			row++;
			xml += "<name>" + rs_loc.getString(text) + "</name>";
			xml += "<sno>" + rs_loc.getInt(value) + "</sno>";
		}
		xml += "<status>";
		if (row == 0)
			xml += "Data Not Found";
		else
			xml += "Data Found";
		xml += "</status>";
		xml += "</result>";

		stmt1.close();
		rs_loc.close();
 
		return xml;

	}
	public String combo_lkup(String value, String text, String table,
			String userquery, int type,String defaultvalue,Connection con1) throws Exception {

		Statement stmt1 = con1.createStatement();
		String xml = "<result>";
		String qry = "select " + value + "," + text + " from " + table + " "
				+ userquery;
System.out.println(qry);
		int row = 0;

		if (type == 2) {  
			xml += "<name>" + defaultvalue + "</name>";
			xml += "<sno>0</sno>";

		}
		ResultSet rs_loc = stmt1.executeQuery(qry);
		while (rs_loc.next()) {

			row++;
			xml += "<name>" + rs_loc.getString(text) + "</name>";
			xml += "<sno>" + rs_loc.getInt(value) + "</sno>";
		}
		xml += "<status>";
		if (row == 0)
			xml += "Data Not Found";
		else
			xml += "Data Found";
		xml += "</status>";
		xml += "</result>";

		stmt1.close();
		rs_loc.close();
 
		return xml;

	}
	synchronized public void conClose(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
	}

	public void testQry(String s) {
		System.out.println(s);
	}

	public String isNull(String input, int type) {
		if (type == 1) {
			if (input == null || input == "'null'")
				input = "0";
			if (input.equals("") || input == "")
				input = "0";
		} else if (type == 2) {
			if (input == null || input == "'null'")
				input = "-";
			if (input.equals("") || input == "" || input == null)
				input = "-";
		} else if (type == 3) {
			if (input == null || input == "'null'")
				input = "";
			if (input.equals("") || input == "" || input == null)
				input = "";

		} else if (type == 4) {
			if (input == null || input == "'null'")
				input = ".";
			if (input.equals("") || input == "" || input == null)
				input = ".";

		}

		return input;
	}

	public String genReport(String qry, String table_header,
			String table_column, String property, String table_heading,
			String table_td_set) throws Exception {
		String s = "";

		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		System.out.println("DCB->Controller->genReport");
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		
  
 		ResultSet rs_loc = ps_loc.executeQuery();
 		// html+="<tr><td colspan='"+(table_header_f.length+1)+"'>"+table_heading+"</td></tr>";
		html += "<tr   bgcolor='skyblue'>";
		html += "<td class='tdText' width='5%'  align='center'>Sl.No</td>";
		for (int i = 0; i < table_header_f.length; i++) {

			html += " <td class='tdText' align=center ><font   size=2><b>" + table_header_f[i].trim() + "</b></font></td>";
			 
		}  
		   
		html += "</tr>"; 
		int row = 0;
		while (rs_loc.next()) {
			row++;
			html += "<tr><td class='tdText' width='5%' align='center'>" + (row) + "</td>";
		 
			for (int i = 0; i < table_header_f.length; i++) {
				 
				html += "<td  " +  obj_loc.isNull(table_td_set_f[i].trim(),1) + "><font   size=2>"
						+ obj_loc.isNull(rs_loc.getString(table_column_f[i].trim()),3) + "</td>";
				
			}
			html += "</tr>";
			 
		}  
		   
		html += "</table>"; 
		rs_loc.close();
		con_loc.close();
		return html;

	}
	public String genReport2(String qry,  
			String table_column, String property, String table_heading,
			String table_td_set) throws Exception {
		String s = "";

		 
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		System.out.println("DCB->Controller->genReport");
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		
  
 		ResultSet rs_loc = ps_loc.executeQuery();
 		// html+="<tr><td colspan='"+(table_header_f.length+1)+"'>"+table_heading+"</td></tr>";
		 
		int row = 0;
		while (rs_loc.next()) {
			row++;
			html += "<tr><td class='tdText' width='5%' align='center'>" + (row) + "</td>";
		 
			for (int i = 0; i < table_column_f.length; i++) {
				 
				html += "<td  " +  obj_loc.isNull(table_td_set_f[i].trim(),1) + "><font   size=2>"
						+ obj_loc.isNull(rs_loc.getString(table_column_f[i].trim()),3) + "</td>";
				
			}
			html += "</tr>";
			 
		}  
		   
		html += "</table>"; 
		rs_loc.close();
		con_loc.close();
		return html;

	}
	public String genReport(String qry, String table_header,
			String table_column, String property, String table_heading,
			String table_td_set,int control_type,int position,String control_name) throws Exception {
		String s = "";

		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		String html = "<table " + property + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		System.out.println("DCB->Controller->genReport");
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
 		ResultSet rs_loc = ps_loc.executeQuery();
 		// html+="<tr><td colspan='"+(table_header_f.length+1)+"'>"+table_heading+"</td></tr>";
		html += "<tr class='tdH'>";
		html += "<td class='tdText' width='5%' >Sl.No</td>";
		for (int i = 0; i < table_header_f.length; i++) {

			html += " <td class='tdText' align=center >" + table_header_f[i].trim() + "</td>";
			 
		}
		 
		html += "</tr>";
		int row = 0;
		System.out.println(table_td_set_f.length);
		while (rs_loc.next()) {
			row++;
			html += "<tr><td class='tdText' width='5%'>" + (row) + "</td>";
			for (int i = 0; i < table_td_set_f.length; i++) {
				 
				
				html += "<td class='tdText' " +  obj_loc.isNull(table_td_set_f[i].trim(),1) + " >"
						+ obj_loc.isNull(rs_loc.getString(table_column_f[i].trim()),3) + "</td>";
				
			}
			
				if (control_type==3)
				{
				html += "<td width='5%' align=center><input type=checkbox id="+(control_name+row)+" name="+(control_name+row)+"></td>";
				}
			
			html += "</tr>";
		} 
		 
		html += "</table>";
		rs_loc.close();
		con_loc.close(); 
		return html;

	}
	public String month_val(String no) {

		String[] cmonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };
		return cmonth[Integer.parseInt(no)];
	}

	public String combo_str(String table, String text, String value,
			String cond, String id, String attributes) throws Exception

	{
		String combo = "<select id='" + id + "' name='" + id + "' "
				+ attributes + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "Select " + text + "," + value + " from " + table + "  "
				+ cond;
		//System.out.println(qry);
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>Select </option>";

		try {
			while (rs_loc.next()) {

				combo += "<option value='" + rs_loc.getString(value) + "'>"
						+ rs_loc.getString(text) + " </option>";

			}
		} catch (Exception e) {
			System.out.println("Combo ----------------------> " + e);
		}
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		return combo;
	}
	public String combo_str(String table, String text, String value,
			String cond, String id,String initial_value, String attributes) throws Exception

	{
		String combo = "<select id='" + id + "' name='" + id + "' "
				+ attributes + ">";
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String qry = "Select " + text + "," + value + " from " + table + "  "
				+ cond;
	//	 System.out.println(qry);
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		combo += "<option value='0'>Select </option>";
		String vl="";
		try {
			while (rs_loc.next()) {
				vl=rs_loc.getString(value); 
				
				if (initial_value.equalsIgnoreCase(vl))
				combo += "<option value='" +vl + "' selected >"
					+ rs_loc.getString(text) + " </option>";
				else
				combo += "<option value='" +vl + "'>"
						+ rs_loc.getString(text) + " </option>";

			}
		} catch (Exception e) {
			System.out.println("Combo ----------------------> " + e);
		}
		combo += "</select>";
		rs_loc.close();
		con_loc.close();
		return combo;
	}
	public void ben_excel(String qry, String table_header, String table_column,
			String property, String table_heading, String table_td_set,
			String fname) throws Exception {
		Connection con_loc = null;
		Controller obj_loc = new Controller();
		con_loc = obj_loc.con();
		String[] table_header_f = table_header.split(",");
		String[] table_column_f = table_column.split(",");
		String[] table_td_set_f = table_td_set.split(",");
		
		File f=new File (fname+"/bentype.xls");
		System.out.println("path=====> " + f.getAbsolutePath());
		WritableWorkbook wc = Workbook.createWorkbook(f);
		WritableSheet sheet = wc.createSheet("First Sheet", 0);
        WritableFont wf = new WritableFont(WritableFont.ARIAL, 
                9, WritableFont.NO_BOLD);    
        WritableCellFormat cf = new WritableCellFormat(wf);
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);
        cf.setWrap(true);
        cf.setAlignment(Alignment.CENTRE);
        cf.setVerticalAlignment(VerticalAlignment.CENTRE);
         
         
          
        Label labe0 = new Label(0, 0, "Heading",cf);
		sheet.addCell(labe0);

		CellView cv = new CellView();
		 
		 
		cv.setAutosize(true);

		for (int i = 0; i < table_header_f.length; i++) {
			Label label = new Label(i, 1, table_header_f[i],cf);
			sheet.addCell(label);
			sheet.setColumnView(i, 20);
		} 
		PreparedStatement ps_loc = con_loc.prepareStatement(qry);
		ResultSet rs_loc = ps_loc.executeQuery();
		System.out.println("ENTRY"+qry); 
		int i = 0;
		int j = 2;
		int k=0;
		try {  
  
			while (rs_loc.next()) {
				
				 int s=0;
				while (s <table_header_f.length)
				{
				 sheet.addCell(new Label(s, j, rs_loc
					 	.getString(table_column_f[s])));
				 s++;
				}
				j++;
				k++;
			 
				
				  
				 
			} 

			
			System.out.println("ENTRY - LOOP");
			
		} catch (Exception e) {
			//   handle exception
			System.out.println(e.getMessage());
		}
		System.out.println("ENTRY - FINAL");
		wc.write();
		wc.close();
		con_loc.close();
	} 
  
	
	public long days(int fyear,int fmonth ,int fday,int lyear,int lmonth,int lday) {
		
		  Calendar cal1 = Calendar.getInstance();
		   
		   Calendar cal2 = Calendar.getInstance();
		   cal1.set(fyear, fmonth, fday);
		   cal2.set(lyear, lmonth, lday);
		   
		   
		    
		   long milis1 = cal1.getTimeInMillis();
		   
		   long milis2 = cal2.getTimeInMillis();
		   
		   long diff = milis2 - milis1;
		   
		   long diffSeconds = diff / 1000;
		   
		   long diffMinutes = diff / (60 * 1000);
		   
		   long diffHours = diff / (60 * 60 * 1000);
		   
		   long diffDays = diff / (24 * 60 * 60 * 1000);
		   
		     
		   return diffDays ;
		    
		   
		   
		
	}
	public int  rowUpdate(String qry) {
		
		int rows=0;
		
		Connection con_locUpdate = null;
		Controller obj_locUpdate = new Controller();
		try {
			
			  
			con_locUpdate = obj_locUpdate.con();
			PreparedStatement ps_locUpdate = con_locUpdate.prepareStatement(qry);
			rows=ps_locUpdate.executeUpdate();
			ps_locUpdate.close();
			con_locUpdate.commit();
			con_locUpdate.close();
			
			
			 
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		 
		return rows;
		
	}
	public String month_val2(int no) {

		String[] cmonthday = { "0", "31", "28", "31", "30","31", "30", "31", "31", "30", "31", "30", "31" };
		return cmonthday[no];
	}
	public String  prvfinyear(String input,int prv) {
		String val="";
		String []sr1=input.split("-");
		String ss1=sr1[0];
		String ss4=Integer.toString(Integer.parseInt(sr1[0])); 
		for (int i=1;i<=prv;i++)
		{
			
			ss4=Integer.toString(Integer.parseInt(ss4)-1);
			if (i==1)
			val+=""+ss4+"-"+ss1;			 
			else
			val+=","+ss4+"-"+ss1;  
			ss1 =ss4;
			
		}
		
		return val;
	}
	public String  prvfinyear(String input,int prv,int flag) {
		String val="";
		String []sr1=input.split("-");
		String ss1=sr1[0];
		String ss4=Integer.toString(Integer.parseInt(sr1[0])); 
		for (int i=1;i<=prv;i++)
		{
			
			ss4=Integer.toString(Integer.parseInt(ss4)-1);
			if (i==1)
			val+="'"+ss4+"-"+ss1+"'";			 
			else
			val+=",'"+ss4+"-"+ss1+"'";
			ss1 =ss4;
			
		}
		
		return val;
	}
	public int recordSave(Hashtable values,String table,Connection con) throws SQLException
	{   
		int row=0;
		Enumeration  er=values.keys();
		  
		String key="";
		String qry="insert into "+table;
		String column=" ( ";
		Object  pr_s=null;
		String value =" values ( ";
		int size=values.size();
		 
		int count=0;
		while (er.hasMoreElements())  
		{
			 
			key=(String)er.nextElement();
			 
			if (count==(size-1))
			{ 
			column+=""+key+")";
			pr_s=values.get(key);
			value+=pr_s+")";
			}else
			{  
				column+=""+key+",";
				 
			
				pr_s=values.get(key);
				value+=pr_s+",";
			 
			}  
			  
			 
			
			count++;
			 
		}	
		 
		  
		return insert((qry+column+value),con);
	}
	public int recordSave(Hashtable cols,Hashtable cond,String table,Connection con) throws SQLException
	{
		int row=0;
		Enumeration  er=cols.keys();
		 
		
		String key="";
		String qry="update "+table;
		String column=" set ";
		Object  pr_s=null;
		String value ="    ";
		int size=cols.size();
		
		 
		int count=0;
		while (er.hasMoreElements())
		{
			  
			key=(String)er.nextElement();
			 
			if (count==(size-1))
			{ 
			column+=""+key+"=";
			pr_s=cols.get(key);
			column+=pr_s+"";
			}else  
			{  
				column+=""+key+"="; 			
				pr_s=cols.get(key);
				column+=pr_s+",";
				 
			}  
			  
			 
			
			count++;
			 
		}	
		Enumeration  cond_er=cond.keys();
		String condcolumn="  where (1=1)  and   ";
		int condsize=cond.size();
		pr_s=""; 
		count=0;
		while (cond_er.hasMoreElements())
		{
			key=(String)cond_er.nextElement();
		 
			if (count==(condsize-1))
			{ 
				condcolumn+=" "+key+"=";
			pr_s= cond.get(key);
				condcolumn+=pr_s+"";
			}else  
			{  
				condcolumn+="  "+key+"=";			
				pr_s= cond.get(key);
				condcolumn+=pr_s+" and  ";		 
			} 
			count++;
		}
		
		
		  
		return insert((qry+column+condcolumn),con);
	}
	public int insert(String str,Connection prcon) throws SQLException
	{  
		  System.out.println(str);
		  PreparedStatement pn_=prcon.prepareStatement(str);
		  return pn_.executeUpdate();
	}
	
	 
	public ArrayList Records(String qry,Connection rcon) throws SQLException
	{
		Connection Rec_con=rcon;
		ArrayList<Object[]> val=new ArrayList<Object[]>();
		PreparedStatement Rec_ps=Rec_con.prepareStatement(qry);
		ResultSet Rec_rs=Rec_ps.executeQuery();
		
		while(Rec_rs.next())
		{
		    int cols = Rec_rs.getMetaData().getColumnCount();
		    Object[] arr = new Object[cols];
		    for(int i=0; i<cols; i++)
		    {
		      arr[i] = Rec_rs.getObject(i+1);
		    }
		    val.add(arr);
		 
		
		
		}
	    
		 Rec_rs.close();   
		 Rec_ps.close();
		 Rec_con.close();
		return val;
	}
	public ArrayList Records1(String qry,Connection rcon) throws SQLException
	{
		Connection Rec_con=rcon;
		ArrayList<String>  val=new ArrayList<String>();
		PreparedStatement Rec_ps=Rec_con.prepareStatement(qry);
		ResultSet Rec_rs=Rec_ps.executeQuery();
		
		int key=-1;
		while(Rec_rs.next())
		{
		    int cols = Rec_rs.getMetaData().getColumnCount();
		    key++;
		    for(int i=1; i<=cols; i++)
		    {		     
		      val.add(key, Rec_rs.getString(i));
		    }
		    	 
		
		
		}
	    
		 Rec_rs.close();   
		 Rec_ps.close();
		 Rec_con.close();
		return val;
	}
	public void writeInCsvFormat(String query,String fileName) throws Exception{        
	      //String res="";
		  Controller obj_locUpdate = new Controller();
		  Connection Rec_con=obj_locUpdate.con();
	      PreparedStatement preparedStatement=Rec_con.prepareStatement(query);
	      //Hashtable hashprocess=new Hashtable();
	      FileWriter writer = new FileWriter(fileName);
	      ResultSet local_rs=preparedStatement.executeQuery();
	      ResultSetMetaData rsmd = local_rs.getMetaData();
	      String columnName ="";
	      String columnValue="";
	      int NumOfCol=rsmd.getColumnCount();
	      //res="<response>";
	      //int row_count=0;
	      for(int i=1;i<=NumOfCol;i++){
	          columnName = rsmd.getColumnName(i);
	          writer.append(columnName);
	          writer.append(',');
	      }
	      writer.append('\n');
	      while (local_rs.next()){
	          //row_count++;         
	          for (int i=1;i<=NumOfCol;i++){
	              columnName = rsmd.getColumnName(i);    
	              //System.out.println("check --> "+local_rs.getString(columnName));
	              columnValue = local_rs.getString(columnName);
	              writer.append(columnValue);
	              writer.append(',');
	              //res+=Obj.generateXML(ColumnName,  Obj.isNull(ColumnValue, 1), 1, Obj);
	          }
	          writer.append('\n');
	      }
	      //res+="<row_count>"+row_count+"</row_count></response>";
	      local_rs.close();
	      preparedStatement.close();    
	      //return res;
	    }
 

public static void  condition(int type,String input)
{
	String res="";
	res="beneficiary_sno not in ( SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY a WHERE  1 >   ( SELECT COUNT(*)  FROM PMS_DCB_MST_BENEFICIARY_METRE b   WHERE a.office_id    = b.office_id   AND a.beneficiary_sno=b.beneficiary_sno   )  AND a.office_id="+input+" ) ";
	meter_not_for_ben=res;
	 
}

}
