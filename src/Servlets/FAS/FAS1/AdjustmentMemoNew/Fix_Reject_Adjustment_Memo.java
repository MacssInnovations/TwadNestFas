package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

/**
 * Servlet implementation class Fix_Reject_Adjustment_Memo
 */
public class Fix_Reject_Adjustment_Memo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fix_Reject_Adjustment_Memo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
	        PreparedStatement ps=null,ps2=null;
	        ResultSet rs=null,rs2=null;
	      
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
	        
	      
	        try {
	                              ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                              String ConnectionString="";

	                              String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                              String strdsn=rs1.getString("Config.DSN");
	                              String strhostname=rs1.getString("Config.HOST_NAME");
	                              String strportno=rs1.getString("Config.PORT_NUMBER");
	                              String strsid=rs1.getString("Config.SID");
	                              String strdbusername=rs1.getString("Config.USER_NAME");
	                              String strdbpassword=rs1.getString("Config.PASSWORD");
	                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                              Class.forName(strDriver.trim());
	                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	             }
	             catch(Exception e)
	                 {
	                    System.out.println("Exception in opening connection :"+e);
	                    //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	                 }
	        
	        response.setContentType(CONTENT_TYPE);
	        response.setHeader("Cache-Control","no-cache");
	        PrintWriter out = response.getWriter();
	        String strCommand="";
	        try 
	        {
	            strCommand=request.getParameter("command");
	            System.out.println("assign..here command..."+strCommand);
	        }
	        
	        catch(Exception e) 
	        {
	            System.out.println("Exception in assigning..."+e);
	        }
	        
	        int cmbAcc_UnitCode=0,cmbOffice_code=0,cashbookYear=0,cashbookMonth=0;
	           Date txtCrea_date=null;
	        
	        if(strCommand.equalsIgnoreCase("loadvoucher")) 
	        {
	             String CONTENT_TYPE = "text/xml; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	             Calendar c;
	             String xml="";
	             long txtBankAccountNo=0;
	               try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	               
	               try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cmbOffice_code "+cmbOffice_code);
	               
	               try{cashbookYear=Integer.parseInt(request.getParameter("cashyear"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cashbookYear "+cashbookYear);
	               
	               try{cashbookMonth=Integer.parseInt(request.getParameter("cashmonth"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cashbookMonth "+cashbookMonth);
	               
	               
	               
	               xml="<response><command>loadvoucher</command>";

	              int cnt=0;
	          
	          
	                
	                
	            try 
	            {                        
	                ps=con.prepareStatement("select distinct t.for_accounting_unit_id,(select u.accounting_unit_name from fas_mst_acct_units u where u.accounting_unit_id=t.for_accounting_unit_id)as unitname " +
	                		" from FAS_ADJUST_MEMO_TRN t where accounting_unit_id=? and accounting_for_office_id=? and " +
	                		" cashbook_month=? and cashbook_year=? and acceptance_status='N'");
	                    ps.setInt(1,cmbAcc_UnitCode);
	                    ps.setInt(2,cmbOffice_code);
	                    ps.setInt(3,cashbookMonth);
	                    ps.setInt(4,cashbookYear);
	                   
	                    rs=ps.executeQuery();
	                  
	                    
	                    while(rs.next()) 
	                    {
	                        xml=xml+"<unitid>"+rs.getInt("for_accounting_unit_id")+"</unitid>";
	                        xml=xml+"<unitname>"+rs.getString("unitname")+"</unitname>";
	                          cnt++;
	                    } 
	                   
	                    if(cnt==0)
	                        xml+="<flag>failure</flag>";
	                    else
	                    	xml=xml+"<flag>success</flag>";
	                rs.close();
	                ps.close();
	                   
	            }
	            catch(Exception e)
	            {
	            System.out.println("catch..HERE.in failure to retrieve."+e);
	                xml="<response><command>loadvoucher</command>"+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	            System.out.println(xml);
	            out.println(xml);
	        }
	        if(strCommand.equalsIgnoreCase("loaddetails")) 
	        {
	             String CONTENT_TYPE = "text/xml; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	             
	             String xml="";
	            int voucherNo=0,forunitid=0,slno=0;
	               try{forunitid=Integer.parseInt(request.getParameter("forunitid"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	             //  System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	               
	              try{cashbookYear=Integer.parseInt(request.getParameter("cashyear"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	             //  System.out.println("cashbookYear "+cashbookYear);
	               
	               try{cashbookMonth=Integer.parseInt(request.getParameter("cashmonth"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	               
	               try{voucherNo=Integer.parseInt(request.getParameter("voucherno"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	             
	               try{slno=Integer.parseInt(request.getParameter("slno"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	             //  System.out.println("voucherNo "+voucherNo);
	               xml="<response><command>loaddetails</command>";

	              int cnt=0;
	          
	            try 
	            
	            {
	            

           	     	String	ss="SELECT AMOUNT,REASON_FOR_REJECT "+
								" FROM FAS_ADJUST_MEMO_TRN "+
           	     	" WHERE cashbook_month      = "+cashbookMonth+
           	     	" 		AND cashbook_year         = "+cashbookYear+
           	     	" 		AND acceptance_status     ='N' "+
           	     	" 		AND FOR_ACCOUNTING_UNIT_ID= "+forunitid+
           	     	" 		and VOUCHER_NO= "+voucherNo+
           	     	" 		and SL_NO=   "+slno;
           	     	
	                ps=con.prepareStatement(ss);
	               
	                    rs=ps.executeQuery();
	                    while(rs.next()) 
	                    {
	                       
	                    	xml=xml+"<amount>"+rs.getBigDecimal("AMOUNT")+"</amount>";
	                        xml=xml+"<reason>"+rs.getString("REASON_FOR_REJECT")+"</reason>"; 
	                        cnt++;
	                    } 
	             
	                    if(cnt==0)
	                        xml+="<flag>failure</flag>";
	                    else
	                    	xml=xml+"<flag>success</flag>";
	                rs.close();
	                ps.close();
	                   
	            }
	            catch(Exception e)
	            {
	            System.out.println("catch..HERE.in failure to retrieve."+e);
	                xml="<response><command>loaddetails</command>"+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	            System.out.println(xml);
	            out.println(xml);
	        }
	        
	        if(strCommand.equalsIgnoreCase("loadamountreasondetails")) 
	        {
	             String CONTENT_TYPE = "text/xml; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	             
	             String xml="";
	           
	            
	               
	               try{cashbookYear=Integer.parseInt(request.getParameter("cashyear"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cashbookYear "+cashbookYear);
	               
	               try{cashbookMonth=Integer.parseInt(request.getParameter("cashmonth"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cashbookMonth "+cashbookMonth);
	               
	             
	               int forUnitId=0;
	               try{forUnitId=Integer.parseInt(request.getParameter("forunitid"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("forUnitId "+forUnitId);
	               
	               xml="<response><command>loadamountreasondetails</command>";

	              int cnt=0;
	          
	            try 
	            
	            {
	            
	            	String sQL="SELECT VOUCHER_NO,SL_NO FROM FAS_ADJUST_MEMO_TRN WHERE cashbook_month      ="+cashbookMonth+" AND " +
	            			"cashbook_year         ="+cashbookYear+" AND acceptance_status     ='N' AND FOR_ACCOUNTING_UNIT_ID="+forUnitId;
           	     		
	                ps=con.prepareStatement(sQL);
	                rs=ps.executeQuery();
	                    while(rs.next()) 
	                    {
	                      	xml=xml+"<vno>"+rs.getInt("VOUCHER_NO")+"</vno>";
	                      	xml=xml+"<slno>"+rs.getInt("SL_NO")+"</slno>"; 
	                    	
	                        cnt++;
	                    } 
	              
	                    if(cnt==0)
	                        xml+="<flag>failure</flag>";
	                    else
	                    	xml=xml+"<flag>success</flag>";
	                rs.close();
	                ps.close();
	                   
	            }
	            catch(Exception e)
	            {
	            System.out.println("catch..HERE.in failure to retrieve."+e);
	                xml="<response><command>loadamountreasondetails</command>"+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	            System.out.println(xml);
	            out.println(xml);
	        }
	        
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
	        PreparedStatement ps=null,ps2=null;
	        ResultSet rs=null,rs2=null;
	      
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
	        
	      
	        try {
	                              ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                              String ConnectionString="";

	                              String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                              String strdsn=rs1.getString("Config.DSN");
	                              String strhostname=rs1.getString("Config.HOST_NAME");
	                              String strportno=rs1.getString("Config.PORT_NUMBER");
	                              String strsid=rs1.getString("Config.SID");
	                              String strdbusername=rs1.getString("Config.USER_NAME");
	                              String strdbpassword=rs1.getString("Config.PASSWORD");
	                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                              Class.forName(strDriver.trim());
	                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	             }
	             catch(Exception e)
	                 {
	                    System.out.println("Exception in opening connection :"+e);
	                    //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	                 }
	        
	        response.setContentType(CONTENT_TYPE);
	        response.setHeader("Cache-Control","no-cache");
	        PrintWriter out = response.getWriter();
	        String strCommand="";
	        try 
	        {
	            strCommand=request.getParameter("command");
	            System.out.println("assign..here command..."+strCommand);
	        }
	        
	        catch(Exception e) 
	        {
	            System.out.println("Exception in assigning..."+e);
	        }
	        
	        int cmbAcc_UnitCode=0,cmbOffice_code=0,cashbookYear=0,cashbookMonth=0,forUnitId=0;
	         
	           long l = System.currentTimeMillis();
	   		java.sql.Timestamp ts = new java.sql.Timestamp(l);
	   		
	   		
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
			String updatedby = (String) session.getAttribute("UserId");
	           
	           int voucherNo=0;
	             int adviceNo=0;
	             float amount=0;
	             String reason="",extendDate="";
	             
	             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	               
	               try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cmbOffice_code "+cmbOffice_code);
	               
	               try{cashbookYear=Integer.parseInt(request.getParameter("cashyear"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cashbookYear "+cashbookYear);
	               
	               try{cashbookMonth=Integer.parseInt(request.getParameter("cashmonth"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("cashbookMonth "+cashbookMonth);
	               
	               try{voucherNo=Integer.parseInt(request.getParameter("voucherno"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("voucherNo "+voucherNo);
	                             
	               
	               try{adviceNo=Integer.parseInt(request.getParameter("adviceno"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("adviceNo "+adviceNo);
	              

	               try{forUnitId=Integer.parseInt(request.getParameter("forunitid"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("forUnitId "+forUnitId);
	               
	               try{amount=Float.parseFloat(request.getParameter("amount"));}
	               catch(NumberFormatException e){System.out.println("exception"+e );}
	               System.out.println("amount "+amount); 
	               
	               String extendClose=request.getParameter("extend_close");
	                reason=request.getParameter("reason");
	                
	              if(extendClose.equalsIgnoreCase("E")) 
	              {
	            	  extendDate=request.getParameter("txtCrea_date");  
	              }
	              try{
	              ps=con.prepareStatement("insert into FAS_FIX_REJECT_ADJUSTMENT_MEMO(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,FOR_ACCOUNTING_UNIT_ID,AMOUNT,REJECT_REASON,EXTEND_CLOSE,EXTEND_DATE,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?)");
	              
	                ps.setInt(1,cmbAcc_UnitCode);
	                ps.setInt(2,cmbOffice_code);
	                ps.setInt(3,cashbookYear); 
	                ps.setInt(4,cashbookMonth);
	                ps.setInt(5,voucherNo);
	                ps.setInt(6,forUnitId);
	                ps.setFloat(7,amount);
	                ps.setString(8,reason);
	                ps.setString(9,extendClose);
	                ps.setString(10,extendDate);
	                ps.setString(11,updatedby);
	                ps.setTimestamp(12,ts);
	                ps.execute();
	                
	                sendMessage(response,"Verify Rejected Adjustment Memo Successfully ","ok");
	              
	              }catch(Exception e){
	            	  sendMessage(response,"Verify Rejected Adjustment Memo Failed ","ok");
	            	  System.out.println(e);}
	}
	
	private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
                  response.sendRedirect(url);
        }
        catch(IOException e)
        {
        }
    }

}
