package Servlets.FAS.FAS1.CivilBills.servlets;

//import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class ChequeMemoType extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static final String CONTENT_TYPE="text/xml; charset=windows-1252";
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException {
		String CONTENT_TYPE="text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control","no-cache");
		System.out.println("Welcome to ChequeMemoType Servlet");
		String cmnd="";
		String xml="";
		int count=0;int cheqmemo_code=0;String cheqmemo_desc="";String status="";
		String update_user="";
		HttpSession session=null;
		Timestamp ts=null;
		PrintWriter pw=response.getWriter();

		/*********** connection establishment****************/
		Connection con=null;
		ResultSet rs2;rs2=null;
		PreparedStatement ps2;ps2=null;
		xml="<response>";
		try {
			LoadDriver load = new LoadDriver();
			con = load.getConnection();                    
		} catch(Exception e) {
			System.out.println("Exception in connection...."+e);
		} 
		try {
			session=request.getSession(false);
			if(session==null){
				System.out.println(request.getContextPath()+"/index.jsp");
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return;
			}
			System.out.println(session);
		} catch(Exception e) {
			System.out.println("Redirect Error :"+e);
		}
		String userid=(String)session.getAttribute("UserId");
		System.out.println("session id is:"+userid);
		update_user=(String)session.getAttribute("UserId");
		long l=System.currentTimeMillis();
		ts=new Timestamp(l);           

		/****************** getting the values from Button Pressed***********/
		try
		{
			cmnd =  request.getParameter("command");     
			System.out.println("Command passed via the button pressed : " + cmnd);
		}
		catch(Exception e3)
		{
			e3.printStackTrace();
		}
		/*****************Getting the values from jsp page ***************/
		if(cmnd.equalsIgnoreCase("add")) 
		{
			xml=xml+"<command>addResponse</command>";
			try
			{    
				String sqlsel="select decode(max(CHEQUE_MEMO_TYPE_CODE),null,0,max(CHEQUE_MEMO_TYPE_CODE))as CHEQUE_MEMO_TYPE_CODE from FAS_CHEQUE_MEMO_TYPES_MST";    
				ps2=con.prepareStatement(sqlsel);
				rs2=ps2.executeQuery();
				if(rs2.next())
				{
					cheqmemo_code=rs2.getInt("CHEQUE_MEMO_TYPE_CODE");
				}
				cheqmemo_code=cheqmemo_code+1;
				System.out.println("Maximum value of cheqmemo_code is :"+cheqmemo_code);
				ps2.close();
				rs2.close();
			}
			catch(Exception e11)
			{
				System.out.println("Exception arised finding the maximum value **** :"+e11);
			} 
			try
			{
				cheqmemo_desc=request.getParameter("ChequeMemoTypeDesc1");
				System.out.println("description::::::"+cheqmemo_desc);
				status="L";
				String sqlload="insert into FAS_CHEQUE_MEMO_TYPES_MST(CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?)";
				System.out.println("insert query----"+sqlload);
				ps2 = con.prepareStatement(sqlload);
				ps2.setInt(1,cheqmemo_code);
				ps2.setString(2,cheqmemo_desc);
				ps2.setString(3,status);
				ps2.setString(4,update_user);
				ps2.setTimestamp(5,ts);
				ps2.executeUpdate();
				count++;
				if(count>0)
				{

					xml=xml+"<cheqmemeocode>"+cheqmemo_code+"</cheqmemeocode>";
					System.out.println("records inserted successfully");
					xml=xml+"<flag>success</flag>"; 
				}
				ps2.close();
			}catch(Exception e) {
				xml=xml+"<flag>failure</flag>";
				System.out.println(e);
			}
		}
		else if(cmnd.equalsIgnoreCase("updated")) {
			xml=xml+"<command>updated</command>";
			try{    
				System.out.println("calling update queery");  
				int cheqmemo_code1=Integer.parseInt(request.getParameter("ChequeMemoTypecode1"));
				System.out.println("code ::::::"+cheqmemo_code1);
				String cheqmemo_desc1=request.getParameter("ChequeMemoTypeDesc1");
				System.out.println("description::::::"+cheqmemo_desc1);

				String sqlload="update FAS_CHEQUE_MEMO_TYPES_MST set CHEQUE_MEMO_DESC=? where CHEQUE_MEMO_TYPE_CODE=?";
				System.out.println("update query---"+sqlload);
				ps2 = con.prepareStatement(sqlload);
				ps2.setString(1,cheqmemo_desc1);
				ps2.setInt(2,cheqmemo_code1);
				ps2.executeUpdate();
				count++;
				if(count>0)
				{
					System.out.println("record updated successfully");
					xml=xml+"<flag>success</flag>"; 
				}
				ps2.close();
			} catch(Exception e){
				xml=xml+"<flag>failure</flag>";
				System.out.println(e);
			}
		}
		else if(cmnd.equalsIgnoreCase("loadlist")){
			xml=xml+"<command>gett</command>";
			try{             
				String sqlload="select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC,STATUS from FAS_CHEQUE_MEMO_TYPES_MST order by CHEQUE_MEMO_TYPE_CODE";
				ps2 = con.prepareStatement(sqlload);
				rs2=ps2.executeQuery();
				while(rs2.next()) {
					xml=xml+"<cheqmemo_code>"+rs2.getInt("CHEQUE_MEMO_TYPE_CODE")+"</cheqmemo_code>";
					xml=xml+"<cheqmemo_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_desc>";
					xml=xml+"<status>"+rs2.getString("STATUS")+"</status>";
					count++;
				}
				if(count>0) {
					xml=xml+"<flag>success</flag>"; 
				} else {
					xml=xml+"<flag>nodata</flag>";    
				}
				ps2.close();
				rs2.close();
			} catch(Exception e){
				xml=xml+"<flag>"+e.getMessage()+"</flag>";
				System.out.println(e);
			}
		}
		else if(cmnd.equalsIgnoreCase("retrieve")) {
			xml=xml+"<command>retrieve</command>";   
			try {
				cheqmemo_code=Integer.parseInt(request.getParameter("cheqmemo_code1"));
				System.out.println("cheqmemo_code*****"+cheqmemo_code);

				String sql="select CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST where CHEQUE_MEMO_TYPE_CODE=?";
				ps2=con.prepareStatement(sql);   
				ps2.setInt(1,cheqmemo_code);
				rs2=ps2.executeQuery();
				if(rs2.next()) {
					xml=xml+"<cheqmemo_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_desc>";
					count++;
				}
				if(count>0)
					xml = xml+"<flag>success</flag>";
				else
					xml=xml+"<flag>failure</flag>";
				ps2.close();
				rs2.close();
			} catch(Exception e) {
				System.out.println("Exception in retrieving records ===> "+e);
				xml=xml+"<flag>failure</flag>";
			}
		}
		else if(cmnd.equalsIgnoreCase("deleted")) {
			xml=xml+"<command>deleted</command>";
			//cheqmemo_code=Integer.parseInt(request.getParameter("ChequeMemoTypecode1"));
			int cheqmemo_code1=Integer.parseInt(request.getParameter("ChequeMemoTypecode1"));                
			String cheqmemo_desc1=request.getParameter("ChequeMemoTypeDesc1");
			String sqlload="";
			boolean isInPayee = false;
			try {
				sqlload="select CHEQUE_MEMO_TYPE_CODE from FAS_CHEQUEMEMO_PAYEE_TYPES_MST where CHEQUE_MEMO_TYPE_CODE=? and STATUS='L'";
				ps2 = con.prepareStatement(sqlload);
				ps2.setInt(1,cheqmemo_code1);
				rs2 = ps2.executeQuery();
				while(rs2.next()){
					isInPayee=true;
					xml=xml+"<paytype>y</paytype>";
				}
				if(!isInPayee){
					sqlload="update FAS_CHEQUE_MEMO_TYPES_MST set CHEQUE_MEMO_DESC=?,STATUS='C' where CHEQUE_MEMO_TYPE_CODE=?";
					System.out.println("update query---"+sqlload);
					ps2 = con.prepareStatement(sqlload);
					ps2.setString(1,cheqmemo_desc1);
					ps2.setInt(2,cheqmemo_code1);
					ps2.executeUpdate();
					xml=xml+"<paytype>n</paytype>";
				}				
			} catch(Exception e) {
				xml=xml+"<flag>failure</flag>";
			}

		}else if(cmnd.equalsIgnoreCase("getcheque")){
			xml=xml+"<command>getmemo</command>";
			try {             
				String sqlload="select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC,STATUS from FAS_CHEQUE_MEMO_TYPES_MST order by CHEQUE_MEMO_TYPE_CODE";
				ps2 = con.prepareStatement(sqlload);
				rs2=ps2.executeQuery();
				while(rs2.next()){
					xml=xml+"<cheqmemo_code>"+rs2.getInt("CHEQUE_MEMO_TYPE_CODE")+"</cheqmemo_code>";
					xml=xml+"<cheqmemo_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_desc>";
					xml=xml+"<status>"+rs2.getString("STATUS")+"</status>";
					count++;
				}
				if(count>0) {
					xml=xml+"<flag>success</flag>"; 
				} else {
					xml=xml+"<flag>nodata</flag>";    
				}
				ps2.close();
				rs2.close();
			} catch(Exception e) {
				xml=xml+"<flag>"+e.getMessage()+"</flag>";
				System.out.println(e);
			}
		}else if(cmnd.equalsIgnoreCase("edit")){
			xml=xml+"<command>getmemo</command>";
			try {
				int chequeCode = Integer.parseInt(request.getParameter("chequeCode"));  
				String sqlload="select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST where CHEQUE_MEMO_TYPE_CODE=?";
				ps2 = con.prepareStatement(sqlload);
				ps2.setInt(1, chequeCode);
				rs2=ps2.executeQuery();
				if(rs2.next()){
					xml=xml+"<flag>success</flag>";
					xml=xml+"<cheqmemo_code>"+rs2.getInt("CHEQUE_MEMO_TYPE_CODE")+"</cheqmemo_code>";
					xml=xml+"<cheqmemo_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_desc>";
				}else{
					xml=xml+"<flag>nodata</flag>";    
				}
				ps2.close();
				rs2.close();
			} catch(Exception e){
				xml=xml+"<flag>"+e.getMessage()+"</flag>";
				System.out.println(e);
			}
		}
		xml=xml+"</response>";
		System.out.println("xml is : " + xml);		
		pw.write(xml);
		pw.flush();
		pw.close();
	}
}
