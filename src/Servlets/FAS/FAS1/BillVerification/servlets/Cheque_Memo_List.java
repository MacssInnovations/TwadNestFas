package Servlets.FAS.FAS1.BillVerification.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Cheque_Memo_List
 */
public class Cheque_Memo_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cheque_Memo_List() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
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
	                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                 Class.forName(strDriver.trim());
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
	       
		 if(strType.equalsIgnoreCase("searchByMonth"))  
	        {
			 String reportType=request.getParameter("reportType");
			    String optioiiid="",subQry="";
			 if(reportType.equalsIgnoreCase("MemoOFF")){
  	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       
       
       try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
       catch(NumberFormatException e){System.out.println("exception"+e );}
       subQry=" ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and ";
			 }if(reportType.equalsIgnoreCase("MemoHO")){
				 cmbAcc_UnitCode=0;
				 cmbOffice_code=0; 
				  subQry="";
			 }
       
       txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
       txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
       String optionId=request.getParameter("optionId");
   
       
      if(optionId.equalsIgnoreCase("live")){
    	  optioiiid=" and m.STATUS='L'";
      }else if(optionId.equalsIgnoreCase("cancel")){
    	  optioiiid=" and m.STATUS='C'";
      }
	            xml="<response><command>searchByMonth</command>";                        
	       
	            String sql="select m.ACCOUNTING_UNIT_ID," +
	            		"(SELECT u.accounting_unit_name   FROM fas_mst_acct_units u   WHERE u.accounting_unit_id=m.accounting_unit_id   ) AS unit_name,   ACCOUNTING_FOR_OFFICE_ID," +
	            		"CHEQUE_MEMO_TYPE_CODE," +	            
	            		"(select CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST a where a.CHEQUE_MEMO_TYPE_CODE=m.CHEQUE_MEMO_TYPE_CODE )as MemoType,"+
	            		" cheque_memo_no,"+
	            " to_char(cheque_memo_date,'dd/mm/yyyy')cheque_memo_date, "+
	            " to_char(voucher_date,'dd/mm/yyyy')voucher_date,"+
	            " bank_ac_no,"+
	            " account_head_code," +
	            "(select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS b where b.ACCOUNT_HEAD_CODE=m.account_head_code ) as headDesc,"+
	            " PAYEE_TYPE_CODE," +
	            " CASE "+
		           "  WHEN M.PAYEE_TYPE_CODE = '2' "+
		           "  THEN "+
		           "   (SELECT t.PAYEE_TYPE_DESC "+
		           "   FROM FAS_CHEQUEMEMO_PAYEE_TYPES_MST MS, "+
		           "     FAS_PAYEE_TYPES_MST T "+
		           "   WHERE MS.CHEQUE_MEMO_TYPE_CODE = m.payee_type_code ::numeric "+
		           "  AND MS.PAYEE_TYPE_CODE         =T.PAYEE_TYPE_CODE ::numeric"+
		           "  ) "+
	            " else (select sub_ledger_type_desc from com_mst_sl_types a where a.sub_ledger_type_code=m.payee_type_code ::numeric) end as typedesc,"+
	            " payee_code," +
	           // "(select v.sl_codename from SL_TYPE_CODE_NAME_VIEW v where SL_TYPE=m.payee_type_code and SL_CODE=m.payee_code)as paydesc,"+
	           " CASE "+
	           "  WHEN M.PAYEE_TYPE_CODE = '2' "+
	           " then  payee_code||''  ELSE "+
	           "   (SELECT v.sl_codename "+
	           "   FROM SL_TYPE_CODE_NAME_VIEW v "+
	           "   WHERE SL_TYPE=m.payee_type_code::numeric "+
	           "  AND SL_CODE  =M.PAYEE_CODE::numeric  "+
	           "   limit 1 ) "+
	           "    END AS paydesc, "+
	           
	           " bank_id," +
	            "(select BANK_NAME from FAS_BANK_LIST c where c.BANK_ID=m.bank_id )as bankName,"+
	            " BRANCH_ID,"+
	            " cheque_no,"+
	            " to_char(cheque_date,'dd/mm/yyyy')cheque_date,"+
	            " cheque_amount,"+
	            " particulars,"+
	            "  to_char(CHEQUE_GIVEN_DATE,'dd/mm/yyyy')CHEQUE_GIVEN_DATE from fas_cheque_memo_mst m"+
	            " where "+subQry+" CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+optioiiid+
	            " order by m.ACCOUNTING_UNIT_ID, cheque_memo_no";
	            
				          System.out.println("SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
						                    xml=xml+"<leng>";
						                   
						                    xml=xml+"<ACCOUNTING_UNIT_ID>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";			
						                    xml=xml+"<ACCOUNTING_FOR_OFFICE_ID>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</ACCOUNTING_FOR_OFFICE_ID>";			
						                    xml=xml+"<unitname>"+rs.getString("unit_name")+"</unitname>";			
						                    xml=xml+"<cheque_memo_no>"+rs.getInt("cheque_memo_no")+"</cheque_memo_no>";						                  
						                    xml=xml+"<CHEQUE_MEMO_TYPE_CODE>"+rs.getString("MemoType")+"</CHEQUE_MEMO_TYPE_CODE>";	
						                    
						                    xml=xml+"<cheque_memo_date>"+rs.getString("cheque_memo_date")+"</cheque_memo_date>";						                  
						                    xml=xml+"<voucher_date>"+rs.getString("voucher_date")+"</voucher_date>";	
						                    xml=xml+"<bank_ac_no>"+rs.getString("bank_ac_no")+"</bank_ac_no>";						                  
						                    xml=xml+"<account_head_code>"+rs.getString("account_head_code")+"-"+rs.getString("headDesc")+"</account_head_code>";	
						                  //  xml=xml+"<headDesc>"+rs.getString("headDesc")+"</headDesc>";						                  
						                    xml=xml+"<typedesc>"+rs.getString("typedesc")+"</typedesc>";	
						                    xml=xml+"<paydesc>"+rs.getString("paydesc")+"</paydesc>";
						                    xml=xml+"<bankName>"+rs.getString("bankName")+"</bankName>";
						                    xml=xml+"<cheque_amount>"+rs.getString("cheque_amount")+"</cheque_amount>";	                
						                    xml=xml+"<particulars><![CDATA["+rs.getString("particulars")+"]]></particulars>";
						                    
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count>0) 
					                {
						                   // System.out.println("inside count==0");
						                    xml=xml+"<flag>success</flag>";
						                    xml=xml+"<reportType>"+reportType+"</reportType>";
						                  
					                }
					                else
					                {
					                	 xml=xml+"<flag>failure</flag>";
					                	 xml=xml+"<reportType>"+reportType+"</reportType>";
					                	
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
