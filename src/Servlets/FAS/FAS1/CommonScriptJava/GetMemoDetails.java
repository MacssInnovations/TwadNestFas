package Servlets.FAS.FAS1.CommonScriptJava;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import Servlets.Security.classes.UserProfile;
import Servlets.FAS.FAS1.CommonClass.ConvertDate;

/**
 * Servlet implementation class DrawingOfficerDetails
 */
public class GetMemoDetails extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null,ps4=null;
	ResultSet rs = null,rs4=null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
       
     public GetMemoDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
			 PrintWriter out = response.getWriter();
		      response.setHeader("cache-control","no-cache");
		      String CONTENT_TYPE = "text/xml; charset=windows-1252";
		      response.setContentType(CONTENT_TYPE);
	   HttpSession session=request.getSession(true);
	    String cmd=request.getParameter("command");
	    ConvertDate cc=new ConvertDate();
	    
	    System.out.println(cmd);
	  
	   
	   
        String xml="";
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
		
		
		if(cmd.equalsIgnoreCase("details"))
		{
			  response.setHeader("cache-control","no-cache");
		      CONTENT_TYPE = "text/xml; charset=windows-1252";
		      response.setContentType(CONTENT_TYPE);
			
			
			xml="<response><command>memodetails</command>";
			
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=cc.ConvertInt(cmbAcc_UnitCode);
			String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=cc.ConvertInt(cmbOffice_code);
		
			String txtCB_Year=request.getParameter("txtCB_Year");
			int year=cc.ConvertInt(txtCB_Year);
			String txtCB_Month=request.getParameter("txtCB_Month");
			int month=cc.ConvertInt(txtCB_Month);
			
			String cmbAdviceNO=request.getParameter("cmbAdviceNO");
			int no=cc.ConvertInt(cmbAdviceNO);
			
			int  slno=cc.ConvertInt(request.getParameter("slno"));
			
			String advice_type=request.getParameter("advice_type");
			
			
			System.out.println("*******************sl no ****accono ="+accno);
			try
			{      
			if(advice_type.equalsIgnoreCase("CR")){
				
				//siva changed on the  load only voc_NO on 01/08/2016
				/*ps=con.prepareStatement("Select * From Fas_Adjust_Memo_Trn " +
						"Where For_Accounting_Unit_Id="+accno+" And Cashbook_Year   ="+year+"  And " +
						"Cashbook_Month    ="+month+" And Voucher_No        ="+no+" And SL_NO="+slno+" AND " +
						"(ACCEPTANCE_STATUS='N' OR ACCEPTANCE_STATUS IS NULL)");*/
			
				ps=con.prepareStatement("Select * From Fas_Adjust_Memo_Trn " +
						"Where For_Accounting_Unit_Id="+accno+" And Cashbook_Year   ="+year+"  And " +
						"Cashbook_Month    ="+month+" And Voucher_No        ="+no+" And SL_NO="+slno+" AND ACCOUNT_HEAD_CODE =610101 AND " +
						"(ACCEPTANCE_STATUS='N' OR ACCEPTANCE_STATUS IS NULL)");
								
				rs=ps.executeQuery();
				xml=xml+"<flag>success</flag>";
				if(rs.next())
				{ 
					xml=xml+"<Amount>"+rs.getLong("AMOUNT")+"</Amount>";
					if(rs.getInt("LETTER_NO")==0)
					{
						/*String q="select * from FAS_ADJUST_MEMO_TRN where For_Accounting_Unit_Id ="+accno+" and CASHBOOK_YEAR='"+year+"' and VOUCHER_NO='"+no+"' And SL_NO="+slno+"  and CASHBOOK_MONTH='"+month+"' and  (ACCEPTANCE_STATUS='N' or ACCEPTANCE_STATUS is null)";*/
						String q="select * from FAS_ADJUST_MEMO_TRN where For_Accounting_Unit_Id ="+accno+" and CASHBOOK_YEAR='"+year+"' and VOUCHER_NO='"+no+"'And SL_NO="+slno+" And  ACCOUNT_HEAD_CODE        =610101  and CASHBOOK_MONTH='"+month+"' and  (ACCEPTANCE_STATUS='N' or ACCEPTANCE_STATUS is null)";
						System.out.println("q:"+q);
						ps=con.prepareStatement(q);
						rs1=ps.executeQuery();
						if(rs1.next())
						{
							xml=xml+"<lNo>"+rs1.getInt("LETTER_NO")+"</lNo>";
						}
					}
					else
					{
						xml=xml+"<lNo>"+rs.getInt("LETTER_NO")+"</lNo>";
					}
					System.out.println("afeter letterno");
					if(rs.getDate("LETTER_DATE")!=null)
					{
						String date1[]=rs.getDate("LETTER_DATE").toString().split("-");
						String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
						xml=xml+"<lDate>"+date2+"</lDate>";
					}
					else
					{
						
						/*ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+year+"' and VOUCHER_NO='"+no+"' And SL_NO="+slno+"  and CASHBOOK_MONTH='"+month+"' and  (ACCEPTANCE_STATUS='N' or ACCEPTANCE_STATUS is null)");*/
						ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+year+"' and VOUCHER_NO='"+no+"'And SL_NO="+slno+" AND ACCOUNT_HEAD_CODE        =610101  and CASHBOOK_MONTH='"+month+"' and  (ACCEPTANCE_STATUS='N' or ACCEPTANCE_STATUS is null)");
						rs1=ps.executeQuery();
						if(rs1.next())
						{
							String date1[]=rs1.getDate("LETTER_DATE").toString().split("-");
							String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
							xml=xml+"<lDate>"+date2+"</lDate>";
						}
						
					}
					
//					xml=xml+"<remarks>"+rs.getString("REMARKS")+"</remarks>";
					xml=xml+"<remarks><![CDATA["+rs.getString("REMARKS")+"]]></remarks>";
					
					try{
						/*ps4=con.prepareStatement("SELECT t.voucher_no,"+
										"  t.sl_no,to_char(m.voucher_date,'dd/mm/yyyy')as voucher_date"+
								" FROM FAS_ADJUST_MEMO_MST m,"+
								" 		  FAS_ADJUST_MEMO_TRN t"+
								" 		WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID"+
								" 		AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID"+
								" 		AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR"+
								" 	AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH"+
								" 	AND m.VOUCHER_NO              =t.VOUCHER_NO "+
								" 	AND m.MEMO_STATUS             ='L' "+
								" 	AND t.FOR_ACCOUNTING_UNIT_ID  =  "+cmbAcc_UnitCode+
								" 	AND t.CASHBOOK_YEAR           = "+year+
								" 	AND t.CASHBOOK_MONTH          =  "+month+
								" 	AND (t.ACCEPTANCE_STATUS      ='N' "+
								" 	OR t.ACCEPTANCE_STATUS       IS NULL) "+
								" 	and t.VOUCHER_NO= "+no+
								" 	and t.sl_no="+slno);
						*/
						ps4=con.prepareStatement("SELECT t.voucher_no,"+
								"  t.sl_no,to_char(m.voucher_date,'dd/mm/yyyy')as voucher_date"+
						" FROM FAS_ADJUST_MEMO_MST m,"+
						" 		  FAS_ADJUST_MEMO_TRN t"+
						" 		WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID"+
						" 		AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID"+
						" 		AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR"+
						" 	AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH"+
						" 	AND m.VOUCHER_NO              =t.VOUCHER_NO "+
						" 	AND m.MEMO_STATUS             ='L' "+
						" 	AND t.FOR_ACCOUNTING_UNIT_ID  =  "+cmbAcc_UnitCode+
						" 	AND t.CASHBOOK_YEAR           = "+year+
						" 	AND t.CASHBOOK_MONTH          =  "+month+
						" 	AND (t.ACCEPTANCE_STATUS      ='N' "+
						" 	OR t.ACCEPTANCE_STATUS       IS NULL) "+
						" 	and t.VOUCHER_NO= "+no+
						" 	and t.sl_no="+slno+
						" 	AND ACCOUNT_HEAD_CODE    =610101");
						
						rs4=ps4.executeQuery();
						if(rs4.next())
						{
							
							xml=xml+"<adjDate>"+rs4.getString("voucher_date")+"</adjDate>";
						}
						
					}
					catch(Exception e1)
					{
						System.out.println("excep in getting VDate:"+e1);
					}
					
					}
			else
				{
					xml=xml+"<flag>nodata</flag>";
				}
			}
			else
			{
			
				ps=con.prepareStatement("Select * From Fas_Adjust_Memo_Trn " +
						"Where For_Accounting_Unit_Id="+accno+" And Cashbook_Year   ="+year+"  And " +
						"Cashbook_Month    ="+month+" And Voucher_No        ="+no+" And SL_NO="+slno+" AND ACCOUNT_HEAD_CODE =900201 AND " +
						"(ACCEPTANCE_STATUS='N' OR ACCEPTANCE_STATUS IS NULL)");
						
				
				rs=ps.executeQuery();
				xml=xml+"<flag>success</flag>";
				if(rs.next())
				{ 
					xml=xml+"<Amount>"+rs.getLong("AMOUNT")+"</Amount>";
					if(rs.getInt("LETTER_NO")==0)
					{
						String q="select * from FAS_ADJUST_MEMO_TRN where For_Accounting_Unit_Id ="+accno+" and CASHBOOK_YEAR='"+year+"' and VOUCHER_NO='"+no+"'And SL_NO="+slno+" And  ACCOUNT_HEAD_CODE        =900201  and CASHBOOK_MONTH='"+month+"' and  (ACCEPTANCE_STATUS='N' or ACCEPTANCE_STATUS is null)";
						System.out.println("q:"+q);
						ps=con.prepareStatement(q);
						rs1=ps.executeQuery();
						if(rs1.next())
						{
							xml=xml+"<lNo>"+rs1.getInt("LETTER_NO")+"</lNo>";
						}
					}
					else
					{
						xml=xml+"<lNo>"+rs.getInt("LETTER_NO")+"</lNo>";
					}
					System.out.println("afeter letterno");
					if(rs.getDate("LETTER_DATE")!=null)
					{
						String date1[]=rs.getDate("LETTER_DATE").toString().split("-");
						String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
						xml=xml+"<lDate>"+date2+"</lDate>";
					}
					else
					{
						
						ps=con.prepareStatement("select * from FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+year+"' and VOUCHER_NO='"+no+"'And SL_NO="+slno+" AND ACCOUNT_HEAD_CODE        =610101  and CASHBOOK_MONTH='"+month+"' and  (ACCEPTANCE_STATUS='N' or ACCEPTANCE_STATUS is null)");
						rs1=ps.executeQuery();
						if(rs1.next())
						{
							String date1[]=rs1.getDate("LETTER_DATE").toString().split("-");
							String date2=date1[2]+"/"+date1[1]+"/"+date1[0];
							xml=xml+"<lDate>"+date2+"</lDate>";
						}
						
					}
					
					xml=xml+"<remarks><![CDATA["+rs.getString("REMARKS")+"]]></remarks>";
//					<![CDATA["+rs.getString("accounting_unit_name")+
//			                 "]]>
					
					
//					  xml=xml+"<cid>"+rs.getInt("accounting_unit_id")+
//				                 "</cid>"
//				                 + "<cname><![CDATA["+rs.getString("accounting_unit_name")+"]]></cname>";
//					
					try{
						
						ps4=con.prepareStatement("SELECT t.voucher_no,"+
								"  t.sl_no,to_char(m.voucher_date,'dd/mm/yyyy')as voucher_date"+
						" FROM FAS_ADJUST_MEMO_MST m,"+
						" 		  FAS_ADJUST_MEMO_TRN t"+
						" 		WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID"+
						" 		AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID"+
						" 		AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR"+
						" 	AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH"+
						" 	AND m.VOUCHER_NO              =t.VOUCHER_NO "+
						" 	AND m.MEMO_STATUS             ='L' "+
						" 	AND t.FOR_ACCOUNTING_UNIT_ID  =  "+cmbAcc_UnitCode+
						" 	AND t.CASHBOOK_YEAR           = "+year+
						" 	AND t.CASHBOOK_MONTH          =  "+month+
						" 	AND (t.ACCEPTANCE_STATUS      ='N' "+
						" 	OR t.ACCEPTANCE_STATUS       IS NULL) "+
						" 	and t.VOUCHER_NO= "+no+
						" 	and t.sl_no="+slno+
						" 	AND ACCOUNT_HEAD_CODE    =900201");
						
						rs4=ps4.executeQuery();
						if(rs4.next())
						{
							
							xml=xml+"<adjDate>"+rs4.getString("voucher_date")+"</adjDate>";
						}
						
					}
					catch(Exception e1)
					{
						System.out.println("excep in getting VDate:"+e1);
					}
					
					}
			else
				{
					xml=xml+"<flag>nodata</flag>";
				}
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
		else if(cmd.equalsIgnoreCase("loadmomono"))
		{
			  response.setHeader("cache-control","no-cache");
		      CONTENT_TYPE = "text/xml; charset=windows-1252";
		      response.setContentType(CONTENT_TYPE);			
			
			int ct=0;
			xml="<response><command>loadmomono</command>";
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=cc.ConvertInt(cmbAcc_UnitCode);
			//System.out.println("*************************************accno ********************"+accno);
			String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=cc.ConvertInt(cmbOffice_code);
		//System.out.println("*************************************office code ******************************"+officecode);
			String txtCB_Year=request.getParameter("txtCB_Year");
			String txtCB_Month=request.getParameter("txtCB_Month");
			int month=cc.ConvertInt(txtCB_Month);
			int year=cc.ConvertInt(txtCB_Year);
			String advice_type=request.getParameter("advice_type");
			System.out.println("advice_type===>"+advice_type);
			
			try
			{
				//ps=con.prepareStatement("select VOUCHER_NO,0 as sl_no from FAS_ADJUST_MEMO_MST where CASHBOOK_YEAR='"+year+"' and  ACCEPT_VOUCHER_NO is null and ACCEPT_VOUCHER_DATE is null and CASHBOOK_MONTH='"+month+"' and FOR_ACCOUNTING_UNIT_ID='"+accno+"' and (ACCEPTANCE_STATUS='N' or ACCEPTANCE_STATUS is null) union SELECT VOUCHER_NO,Sl_No FROM FAS_ADJUST_MEMO_TRN where CASHBOOK_YEAR='"+year+"' and CASHBOOK_MONTH='"+month+"' and FOR_ACCOUNTING_UNIT_ID='"+accno+"' and (ACCEPTANCE_STATUS='N' or ACCEPTANCE_STATUS is null)" );
				
				if(advice_type.equalsIgnoreCase("CR"))
				{
				/*siva changes on only load for voc_no on 01/08/2016*/
				   ps=con.prepareStatement("SELECT t.voucher_no,to_char(m.voucher_date,'dd/mm/yyyy') as voucher_date ,"+
								"  t.sl_no "+
								" FROM FAS_ADJUST_MEMO_MST m,"+
						"   FAS_ADJUST_MEMO_TRN t"+
						" 	WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID"+
						" 	AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
						" 	AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR "+
						" 	AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
						" 	AND m.VOUCHER_NO              =t.VOUCHER_NO "+
						" 	and m.MEMO_STATUS='L' "+
						" 	and t.FOR_ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
						" 	and t.CASHBOOK_YEAR       ="+year+" AND t.CASHBOOK_MONTH        = "+month+
						
						"  AND T.ACCOUNT_HEAD_CODE       =610101 "+
						" 	AND (t.ACCEPTANCE_STATUS    ='N' "+
						" 	OR t.ACCEPTANCE_STATUS     IS NULL)");
				   
				   
				   
				   System.out.println("SELECT t.voucher_no,to_char(m.voucher_date,'dd/mm/yyyy') as voucher_date, "+
							"  t.sl_no "+
							" FROM FAS_ADJUST_MEMO_MST m,"+
					"   FAS_ADJUST_MEMO_TRN t"+
					" 	WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID"+
					" 	AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
					" 	AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR "+
					" 	AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
					" 	AND m.VOUCHER_NO              =t.VOUCHER_NO "+
					" 	and m.MEMO_STATUS='L' "+
					" 	and t.FOR_ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
					" 	and t.CASHBOOK_YEAR       ="+year+" AND t.CASHBOOK_MONTH        = "+month+
					
					"  AND T.ACCOUNT_HEAD_CODE       =610101 "+
					" 	AND (t.ACCEPTANCE_STATUS    ='N' "+
					" 	OR t.ACCEPTANCE_STATUS     IS NULL)");
				}
				else
				{
					ps=con.prepareStatement("SELECT t.voucher_no,to_char(m.voucher_date,'dd/mm/yyyy') as voucher_date, "+
							"  t.sl_no "+
							" FROM FAS_ADJUST_MEMO_MST m,"+
					"   FAS_ADJUST_MEMO_TRN t"+
					" 	WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID"+
					" 	AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
					" 	AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR "+
					" 	AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
					" 	AND m.VOUCHER_NO              =t.VOUCHER_NO "+
					" 	and m.MEMO_STATUS='L' "+
					" 	and t.FOR_ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
					" 	and t.CASHBOOK_YEAR       ="+year+" AND t.CASHBOOK_MONTH        = "+month+
					
					"  AND T.ACCOUNT_HEAD_CODE       =900201 "+
					" 	AND (t.ACCEPTANCE_STATUS    ='N' "+
					" 	OR t.ACCEPTANCE_STATUS     IS NULL)");
			   
			   
			   
			   System.out.println("SELECT t.voucher_no,to_char(m.voucher_date,'dd/mm/yyyy') as voucher_date, "+
						"  t.sl_no "+
						" FROM FAS_ADJUST_MEMO_MST m,"+
				"   FAS_ADJUST_MEMO_TRN t"+
				" 	WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID"+
				" 	AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
				" 	AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR "+
				" 	AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
				" 	AND m.VOUCHER_NO              =t.VOUCHER_NO "+
				" 	and m.MEMO_STATUS='L' "+
				" 	and t.FOR_ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
				" 	and t.CASHBOOK_YEAR       ="+year+" AND t.CASHBOOK_MONTH        = "+month+
				
				"  AND T.ACCOUNT_HEAD_CODE       =900201 "+
				" 	AND (t.ACCEPTANCE_STATUS    ='N' "+
				" 	OR t.ACCEPTANCE_STATUS     IS NULL)");
				}
				
//				ps=con.prepareStatement("SELECT  distinct(t.voucher_no)"+
//						
//						" FROM FAS_ADJUST_MEMO_MST m,"+
//				"   FAS_ADJUST_MEMO_TRN t"+
//				" 	WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID"+
//				" 	AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
//				" 	AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR "+
//				" 	AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
//				" 	AND m.VOUCHER_NO              =t.VOUCHER_NO "+
//				" 	and m.MEMO_STATUS='L' "+
//				" 	and t.FOR_ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
//				" 	and t.CASHBOOK_YEAR       ="+year+" AND t.CASHBOOK_MONTH        = "+month+
//				" 	AND (t.ACCEPTANCE_STATUS    ='N' "+
//				" 	OR t.ACCEPTANCE_STATUS     IS NULL)");
				
				
				rs=ps.executeQuery();
				while(rs.next())
				{
					ct++;
					xml=xml+"<flag>success</flag>";
					xml=xml+"<memono>"+rs.getInt("VOUCHER_NO")+"</memono>";
					xml=xml+"<memodate>"+rs.getString("VOUCHER_DATE")+"</memodate>";
					xml=xml+"<sl_no>"+rs.getInt("sl_no")+"</sl_no>";
					
				}
				if(ct==0)
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
		else if(cmd.equalsIgnoreCase("load_acc_units"))
		{
			response.setHeader("cache-control","no-cache");
		      CONTENT_TYPE = "text/xml; charset=windows-1252";
		      response.setContentType(CONTENT_TYPE);	
			
			int y=0;
			xml="<response><command>load_acc_units</command>";
			String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=cc.ConvertInt(cmbAcc_UnitCode);
			
			
			 try {
                 ps=con.prepareStatement("SELECT accounting_unit_id,trim(accounting_unit_name) as accounting_unit_name FROM fas_mst_acct_units WHERE accounting_unit_id!=? and ACCOUNTING_UNIT_OFFICE_ID NOT IN(SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID IN('CL','RD','NC'))  and STATUS is null order by accounting_unit_name");
                 ps.setInt(1,accno);
                  rs=ps.executeQuery();
                
                 while(rs.next())
                 {
                 xml=xml+"<cid>"+rs.getInt("accounting_unit_id")+
                 "</cid><cname><![CDATA["+rs.getString("accounting_unit_name")+"]]></cname>";
                 y++;
                 }
                 if(y!=0)
                 {
                     xml=xml+"<flag>success</flag>";
                 }
                 else
                 {    xml=xml+"<flag>failure</flag>";}
                 rs.close();
            	 ps.close();
               
                     
               
             }
             catch(Exception e)
             {
             System.out.println("catch..HERE.in load supplier."+e);
             xml=xml+"<flag>failure</flag>";
             }
		
			xml = xml + "</response>";
			 
			System.out.println(xml);	
		}
		out.write(xml);

}
}
