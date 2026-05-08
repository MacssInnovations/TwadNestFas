package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Bank_AccountHeadCode_Module extends HttpServlet {
	private static final String CONTENT_TYPE ="text/html; charset=windows-1252";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {

		HttpSession session = request.getSession(false);
		try {

			if (session == null) {

				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}


		Connection con = null;
		ResultSet rs = null;
		
		PreparedStatement ps = null;
		//String xml="";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String strCommand = "";
		try {
			ResourceBundle rs1 =
				ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
			con =
				DriverManager.getConnection(ConnectionString, strdbusername.trim(),
						strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			//sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}

		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);

		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		int cmbAcc_UnitCode = 0;

		if(strCommand.equals("LoadBankAcc_No"))
		{
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>LoadBankAcc_No</command>";
			System.out.println("~~~~~~~~~~~~~~~~~~~xml~~~~~~~~~~~~~~~~~~~~~~ "+xml);
			try {
				cmbAcc_UnitCode =
					Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Exception to catch cmbAcc_UnitCode ");
			}
			try{
				System.out.println("~~~~~~~~~~~~~~~~~~~cmbAcc_UnitCode~~~~~~~~~~~~~~~~~~~~~~ "+cmbAcc_UnitCode);
				ps=con.prepareStatement("select BANK_AC_NO from FAS_MST_BANK_BALANCE where ACCOUNTING_UNIT_ID=? ");
				ps.setInt(1,cmbAcc_UnitCode);
				rs=ps.executeQuery();
				while(rs.next()) {
					xml=  xml + "<BANK_AC_NO>" + rs.getBigDecimal("BANK_AC_NO") + "</BANK_AC_NO>";
				}
				xml =xml + "<flag>success</flag>";
			}catch(Exception e) {
				e.printStackTrace();
				xml = xml +"<flag>failure</flag>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		}   
		if(strCommand.equals("LoadBankAcc_No_FDW"))
		{
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>LoadBankAcc_No_FDW</command>";
			System.out.println("~~~~~~~~~~~~~~~~~~~xml~~~~~~~~~~~~~~~~~~~~~~ "+xml);
			try {
				cmbAcc_UnitCode =
					Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Exception to catch cmbAcc_UnitCode ");
			}
			try{
				System.out.println("~~~~~~~~~~~~~~~~~~~cmbAcc_UnitCode~~~~~~~~~~~~~~~~~~~~~~ "+cmbAcc_UnitCode);
				ps=con.prepareStatement("select BANK_AC_NO from FAS_MST_BANK_BALANCE where ACCOUNTING_UNIT_ID=? and AC_OPERATIONAL_MODE_ID='FDW' and status='Y'");
				ps.setInt(1,cmbAcc_UnitCode);
				rs=ps.executeQuery();
				while(rs.next()) {
					xml=  xml + "<BANK_AC_NO>" + rs.getBigDecimal("BANK_AC_NO") + "</BANK_AC_NO>";
				}
				xml =xml + "<flag>success</flag>";
			}catch(Exception e) {
				e.printStackTrace();
				xml = xml +"<flag>failure</flag>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		}                 


	}
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {

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


		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null,ps3=null;
		//String xml="";
		int head_code=0;
		int sl_no=0;
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String strCommand = "";
		try {
			ResourceBundle rs1 =
				ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
			con =
				DriverManager.getConnection(ConnectionString, strdbusername.trim(),
						strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			//sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}

		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);

		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		int txtBankId = 0, cmbAcc_UnitCode = 0, txtBranchId =
			0, txtAcc_HeadCode = 0, radDefault = 0,count=0 ,txtSl_no=0;
		String txtOperation_mode = "", txtBankAcc_type = "", txtRemarks =
			"", cmbModule = "", rad_CR_DR = "", radStatus="",txtOperation_mode_view="";
		long txtBankAccountNo = 0;

		//String update_user="x";
		String update_user = (String)session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		try {
			txtBankId = Integer.parseInt(request.getParameter("txtBankId"));
		} catch (Exception e) {
			System.out.println("Exception to catch bank id ");
		}

		try {
			cmbAcc_UnitCode =
				Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Exception to catch cmbAcc_UnitCode ");
		}

		try {
			txtBranchId =
				Integer.parseInt(request.getParameter("txtBranchId"));
		} catch (Exception e) {
			System.out.println("Exception to catch txtBranchId ");
		}

		txtOperation_mode = request.getParameter("txtOperation_mode");
		txtBankAcc_type = request.getParameter("txtBankAcc_type");
		try {
			txtBankAccountNo =
				Long.parseLong(request.getParameter("txtBankAccountNo"));
			System.out.println("txtBankAccountNo===>"+txtBankAccountNo);
		} catch (Exception e) {
			System.out.println("Exception to catch txtBankAccountNo ");
		}

		try {
			txtAcc_HeadCode =
				Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
		} catch (Exception e) {
			System.out.println("Exception to catch txtAcc_HeadCode ");
		}
		try {
			txtSl_no =
				Integer.parseInt(request.getParameter("txtSl_no"));
			System.out.println("txtSl_no"+txtSl_no);
		} catch (Exception e) {
			System.out.println("Exception to catch txtSl_no ");
		}

		try {
			radStatus = request.getParameter("radStatus");
		} catch (Exception e) {
			System.out.println("Exception to catch radStatus ");
		}


		cmbModule = request.getParameter("cmbModule");

		rad_CR_DR = request.getParameter("rad_CR_DR");
		//txtRemarks = request.getParameter("txtRemarks");
		/*try {
			radDefault = Integer.parseInt(request.getParameter("radDefault"));
		} catch (NumberFormatException e) {
			System.out.println("Exception radDefault.." + e);
		}
    */

		System.out.println("after getting parameters");

		if (strCommand.equalsIgnoreCase("loadbankdeatils")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>loadbankdeatils</command>";
			int bani_id_temp = 0;
			String operation_mode_temp = "";

			try {
				ps =
					con.prepareStatement("select bb.BANK_ID,bb.BRANCH_ID,bk.BANK_NAME,br.BRANCH_NAME ||'-'|| coalesce (br.CITY_TOWN_NAME,'') " +
							" AS BRANCH_CITY,bb.BANK_AC_TYPE_ID,bb.AC_OPERATIONAL_MODE_ID,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE from " +
							" FAS_MST_BANK_BALANCE bb,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t," +
							" FAS_MST_AC_OPER_MODES m where ACCOUNTING_UNIT_ID=? and bb.BANK_AC_NO=? and t.ACCOUNT_TYPE_ID=bb.BANK_AC_TYPE_ID" +
							" and bb.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and bb.BANK_ID=br.BANK_ID and bb.BRANCH_ID=br.BRANCH_ID " +
					" and bb.BANK_ID=bk.BANK_ID ");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setLong(2, txtBankAccountNo);
				rs = ps.executeQuery();

				if (rs.next()) {
					xml = xml + "<flag>success</flag>";
					bani_id_temp = rs.getInt("BANK_ID");
					operation_mode_temp =rs.getString("AC_OPERATIONAL_MODE_ID");
					xml =xml + "<BANK_ID>" + rs.getInt("BANK_ID") + "</BANK_ID>";
					xml =xml + "<BANK_NAME>" + rs.getString("BANK_NAME") + "</BANK_NAME>";
					xml =xml + "<BRANCH_ID>" + rs.getInt("BRANCH_ID") + "</BRANCH_ID>";
					xml =xml + "<BRANCH_CITY>" + rs.getString("BRANCH_CITY") + "</BRANCH_CITY>";
					xml =xml + "<BANK_AC_TYPE_ID>" + rs.getString("BANK_AC_TYPE_ID") +"</BANK_AC_TYPE_ID>";
					xml =xml + "<ACCOUNT_TYPE>" + rs.getString("ACCOUNT_TYPE") + "</ACCOUNT_TYPE>";
					xml =xml + "<AC_OPERATIONAL_MODE_ID>" + rs.getString("AC_OPERATIONAL_MODE_ID") +"</AC_OPERATIONAL_MODE_ID>";
					xml =xml + "<AC_OPERATIONAL_MODE>" + rs.getString("AC_OPERATIONAL_MODE") +"</AC_OPERATIONAL_MODE>";
				} else
					xml = xml + "<flag>failure</flag>";
				ps.close();
				rs.close();
				ps =con.prepareStatement("select a.AC_HEAD_CODE,b.ACCOUNT_HEAD_DESC  from FAS_MST_BANK_ACCOUNT_HEADS a,"+
						"COM_MST_ACCOUNT_HEADS b where " +
					" a.AC_HEAD_CODE=b.ACCOUNT_HEAD_CODE and a.BANK_ID=? and a.AC_OPERATIONAL_MODE_ID=?");
				ps.setInt(1, bani_id_temp);
				ps.setString(2, operation_mode_temp);
				rs = ps.executeQuery();
				if (rs.next()) {
					xml =
						xml + "<AC_HEAD_CODE>" + rs.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>";
					xml =
						xml + "<ACCOUNT_HEAD_DESC>" + rs.getString("ACCOUNT_HEAD_DESC") +
						"</ACCOUNT_HEAD_DESC>";
					xml = xml + "<flag_mode>success</flag_mode>";
				} else {
					xml = xml + "<flag_mode>failure</flag_mode>";
				}

			} catch (Exception e) {
				System.out.println("Finding Branch failed due to exception" +
						e);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		} 
		 else if(strCommand.equalsIgnoreCase("Modify")) 
		  {
			  System.out.println("Modify servlet :");
			   String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             String xml="",qry="";
            int k=0;
             xml = "<response><command>Modify</command>";
             try{
           	String[] Acc_UnitCode=request.getParameterValues("cmbAcc_UnitCode");
     			String[] Acc_No=request.getParameterValues("txtBankAccountNo");
     			String[] Bank_Id=request.getParameterValues("txtBankId");
     			String[] Branch_Id=request.getParameterValues("txtBranchId");
     			String[] BankAcc_Type=request.getParameterValues("txtBankAcc_type");
     			String[] Opration_Mode=request.getParameterValues("txtOperation_mode");
     			String[] Acc_HeadCode=request.getParameterValues("txtAcc_HeadCode");
     			String[] Module=request.getParameterValues("cmbModule");
     			String[] CrDrType=request.getParameterValues("CrDrType");
     			String[] txtStatus=request.getParameterValues("txtStatus"); 
     			for(int i=0;i<Acc_UnitCode.length;i++){
   				int UnitCode=Integer.parseInt(Acc_UnitCode[i]);
   				long AccountNo=Long.parseLong(Acc_No[i]);
   				int BankID=Integer.parseInt(Bank_Id[i]);
   				int BranchID=Integer.parseInt(Branch_Id[i]);
   				int Acc_headCode=Integer.parseInt(Acc_HeadCode[i]);
   				
   					qry="UPDATE FAS_OFFICE_BANK_AC_CURRENT " +
   				" SET AC_HEAD_CODE          = " +Acc_headCode+
   				 " WHERE ACCOUNTING_UNIT_ID  ="+UnitCode+
               	 " AND BANK_ID               ="+BankID+
               	 " AND BRANCH_ID             ="+BranchID+
               	 " AND BANK_AC_NO            ="+AccountNo+
               	 " AND BANK_AC_TYPE_ID       = '"+BankAcc_Type[i]+"'"+
               	 " AND AC_OPERATIONAL_MODE_ID='"+Opration_Mode[i]+"'"+
               	 " AND CR_DR_TYPE       = '"+CrDrType[i]+"'";
   					
   					ps=con.prepareStatement(qry);
   					System.out.println("query......"+qry);
   					k=ps.executeUpdate();
   				    System.out.println(k+" k value");
   				
   			
            
		  }
             }catch (Exception e) {
					e.printStackTrace();
                 //System.out.println(e);
				}
     			if(k>0)  xml += "<flag>success</flag>";
     			else
     				xml+= "<flag>failure</flag>";
     			xml+="</response>";
     			System.out.println(xml);
     			out.println(xml);
		  }
		
		 else if(strCommand.equalsIgnoreCase("viewCombo")) {
             System.out.println("view grid servlet :");
                 String CONTENT_TYPE = "text/xml; charset=windows-1252";
                 response.setContentType(CONTENT_TYPE);
                 String xml = "",q="";
                 xml = "<response><command>viewCombo</command>";
                 txtOperation_mode_view = request.getParameter("txtOperation_mode");
                 head_code=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
                 
                 System.out.println("txtOperation_mode_view === "+txtOperation_mode_view+"test txtBankAccountNo>>>>"+head_code);
                 try {
                	// q="select MODULE_ID,MODULE_DESC,replace(ACCOUNT_HEAD_CODE,substr(ACCOUNT_HEAD_CODE,2,3),substr("+head_code+",2,3))as ACCOUNT_HEAD_CODE from FAS_MODULE_HEADS_TEMPLATE where AC_OPERATIONAL_MODE_ID='"+txtOperation_mode+"'";
                	 q="select MODULE_ID,MODULE_DESC from FAS_MODULE_HEADS_TEMPLATE where AC_OPERATIONAL_MODE_ID='"+txtOperation_mode+"' group by MODULE_ID,MODULE_DESC";
                	 System.out.println("q value"+q);
                	 ps =con.prepareStatement(q);
                          rs=ps.executeQuery();
                          while(rs.next()) {
                              xml=xml+"<moduleid>"+rs.getString("MODULE_ID")+"</moduleid>";
                             // xml=xml+"<account_code>"+rs.getString("ACCOUNT_HEAD_CODE") +"</account_code>";
                              xml=xml+"<moduleiddesc>"+rs.getString("MODULE_DESC") +"</moduleiddesc>";
                         
                              count++;
                          }
                         if(count>0) {
                             xml = xml + "<flag>success</flag>";
                         }
                         else {
                             xml = xml + "<flag>noData</flag>";
                         }
                         
                 } catch (Exception e) {
                         System.out.println("catch..HERE.in  view head." + e);
                         xml = xml + "<flag>failure</flag>";
                 }
                 xml = xml + "<count>"+count+"</count></response>";
                 System.out.println(xml);
                 out.println(xml);
                 
             }

		 else if(strCommand.equalsIgnoreCase("showGrid")){
			 System.out.println("Show grid Data :"); 
			 String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             String xml = "",q="";
             xml = "<response><command>showGrid</command>";
             txtOperation_mode_view = request.getParameter("txtOperation_mode");
             txtAcc_HeadCode =Integer.parseInt(request.getParameter("Acc_HeadCode"));
             txtBankId=Integer.parseInt(request.getParameter("txtBankId"));
             txtBranchId=Integer.parseInt(request.getParameter("txtBranchId"));
             cmbAcc_UnitCode =Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
             txtBankAcc_type=request.getParameter("txtBankAcc_type");
             Long Acc_No=Long.parseLong(request.getParameter("txtBankAccountNo"));
             System.out.println("txtOperation_mode_view === "+txtOperation_mode_view+"test txtBankAccountNo>>>>"+head_code);
             try {
            	
            	 q="SELECT ACCOUNTING_UNIT_ID, " +
            	 "  (SELECT ACCOUNTING_UNIT_NAME " +
            	 "  FROM FAS_MST_ACCT_UNITS " +
            	 "  WHERE ACCOUNTING_UNIT_ID=cur.accounting_unit_id " +
            	 "  )AS unit_name, " +
            	 "  BANK_ID, " +
            	 "  (SELECT BANK_SHORT_NAME FROM FAS_MST_BANKS WHERE BANK_ID=cur.bank_id " +
            	 "  )AS bank_desc, " +
            	 "  BRANCH_ID, " +
            	 "  (SELECT BRANCH_NAME " +
            	 "  FROM FAS_MST_BANK_BRANCHES " +  //FAS_BANK_BRANCH
            	 "  WHERE BANK_ID=cur.BANK_ID " +
            	 "  AND BRANCH_ID=cur.BRANCH_ID " +
            	 "  )AS branch_desc, " +
            	 "  BANK_AC_NO, " +
            	 "  BANK_AC_TYPE_ID, " +
            	 "  AC_OPERATIONAL_MODE_ID, " +
            	 "  AC_HEAD_CODE, " +
            	 "  MODULE_ID, " +
            	 "  (SELECT MODULE_DESC " +
            	 "  FROM FAS_MODULE_HEADS_TEMPLATE tem " +
            	 "  WHERE tem.MODULE_ID=cur.MODULE_ID " +
            	 "  GROUP BY MODULE_DESC " +
            	 "  ) AS Module_Desc, " +
            	 "  CR_DR_TYPE, " +
            	 "  STATUS " +
            	 "  FROM FAS_OFFICE_BANK_AC_CURRENT cur " +
            	 " WHERE ACCOUNTING_UNIT_ID  ="+cmbAcc_UnitCode+
            	 " AND BANK_ID               ="+txtBankId+
            	 " AND BRANCH_ID             ="+txtBranchId+
            	 " AND BANK_AC_NO            ="+Acc_No+
            	 " AND BANK_AC_TYPE_ID       ='"+txtBankAcc_type+"' " +
            	 " AND AC_OPERATIONAL_MODE_ID='"+txtOperation_mode+"' " +
            	 " AND AC_HEAD_CODE          ="+txtAcc_HeadCode;

            	 
            	 System.out.println("q value"+q);
            	 ps =con.prepareStatement(q);
                      rs=ps.executeQuery();
                      while(rs.next()) {
                          xml=xml+"<ACCOUNTING_UNIT_ID>"+rs.getString("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";
                          xml=xml+"<unit_name>"+rs.getString("unit_name").trim() +"</unit_name>";
                          xml=xml+"<BANK_ID>"+rs.getString("BANK_ID") +"</BANK_ID>";
                          xml=xml+"<bank_desc>"+rs.getString("bank_desc") +"</bank_desc>";
                          xml=xml+"<BRANCH_ID>"+rs.getString("BRANCH_ID")+"</BRANCH_ID>";
                          xml=xml+"<branch_desc>"+rs.getString("branch_desc") +"</branch_desc>";
                          xml=xml+"<BANK_AC_NO>"+rs.getString("BANK_AC_NO") +"</BANK_AC_NO>";
                          xml=xml+"<BANK_AC_TYPE_ID>"+rs.getString("BANK_AC_TYPE_ID")+"</BANK_AC_TYPE_ID>";
                          xml=xml+"<AC_OPERATIONAL_MODE_ID>"+rs.getString("AC_OPERATIONAL_MODE_ID") +"</AC_OPERATIONAL_MODE_ID>";
                          xml=xml+"<AC_HEAD_CODE>"+rs.getString("AC_HEAD_CODE") +"</AC_HEAD_CODE>";
                          xml=xml+"<MODULE_ID>"+rs.getString("MODULE_ID")+"</MODULE_ID>";
                          xml=xml+"<Module_Desc>"+rs.getString("Module_Desc") +"</Module_Desc>";
                          xml=xml+"<CR_DR_TYPE>"+rs.getString("CR_DR_TYPE") +"</CR_DR_TYPE>";
                          xml=xml+"<STATUS>"+rs.getString("STATUS") +"</STATUS>";
                     
                          count++;
                      }
                     if(count>0) {
                         xml = xml + "<flag>success</flag>";
                     }
                     else {
                         xml = xml + "<flag>noData</flag>";
                     }
                     
             } catch (Exception e) {
                     System.out.println("catch..HERE.in  view head." + e);
                     xml = xml + "<flag>failure</flag>";
             }
             xml = xml + "<count>"+count+"</count></response>";
             System.out.println(xml);
             out.println(xml);
			 
		 }
		
		 else if(strCommand.equalsIgnoreCase("ShowCombo")) {
             System.out.println("view grid servlet :");
                 String CONTENT_TYPE = "text/xml; charset=windows-1252";
                 response.setContentType(CONTENT_TYPE);
                 String xml = "",q="";
                 xml = "<response><command>viewCombo</command>";
                 txtOperation_mode_view = request.getParameter("txtOperation_mode");
                 txtAcc_HeadCode =Integer.parseInt(request.getParameter("Acc_HeadCode"));
                 txtBankId=Integer.parseInt(request.getParameter("txtBankId"));
                 txtBranchId=Integer.parseInt(request.getParameter("txtBranchId"));
                 cmbAcc_UnitCode =Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                // txtBankAccountNo=Integer.parseInt(request.getParameter("txtBankAccountNo"));
                 Long Acc_No=Long.parseLong(request.getParameter("txtBankAccountNo"));
                 System.out.println("txtOperation_mode_view === "+txtOperation_mode_view+"test txtBankAccountNo>>>>"+txtAcc_HeadCode);
                 try {
                	// q="select MODULE_ID,MODULE_DESC,replace(ACCOUNT_HEAD_CODE,substr(ACCOUNT_HEAD_CODE,2,3),substr("+head_code+",2,3))as ACCOUNT_HEAD_CODE from FAS_MODULE_HEADS_TEMPLATE where AC_OPERATIONAL_MODE_ID='"+txtOperation_mode+"'";
                	// q="select MODULE_ID,MODULE_DESC from FAS_MODULE_HEADS_TEMPLATE where AC_OPERATIONAL_MODE_ID='"+txtOperation_mode+"' group by MODULE_ID,MODULE_DESC";
                	q="SELECT cur.MODULE_ID, " +
                	"  cur.ac_operational_mode_id, " +
                	"  (SELECT MODULE_DESC " +
                	"  FROM FAS_MODULE_HEADS_TEMPLATE tem " +
                	"  WHERE tem.MODULE_ID=cur.MODULE_ID " +
                	"  GROUP BY MODULE_DESC " +
                	"  )AS Module_Desc " +
                	" FROM FAS_OFFICE_BANK_AC_CURRENT cur " +
                	" WHERE ACCOUNTING_UNIT_ID  ="+cmbAcc_UnitCode+
                	" AND BANK_ID               ="+txtBankId+
                	" AND branch_id             ="+txtBranchId+
                	" AND bank_ac_no            ="+Acc_No+
                	" AND ac_operational_mode_id='"+txtOperation_mode+"' " +
                	" AND AC_HEAD_CODE          ="+txtAcc_HeadCode+
                	" GROUP BY MODULE_ID, " +
                	"  CUR.AC_OPERATIONAL_MODE_ID " +
                	"ORDER BY cur.MODULE_ID";

                	 
                	 System.out.println("q value"+q);
                	 ps =con.prepareStatement(q);
                          rs=ps.executeQuery();
                          while(rs.next()) {
                              xml=xml+"<moduleid>"+rs.getString("MODULE_ID")+"</moduleid>";
                             // xml=xml+"<account_code>"+rs.getString("ACCOUNT_HEAD_CODE") +"</account_code>";
                              xml=xml+"<moduleiddesc>"+rs.getString("MODULE_DESC") +"</moduleiddesc>";
                         
                              count++;
                          }
                         if(count>0) {
                             xml = xml + "<flag>success</flag>";
                         }
                         else {
                             xml = xml + "<flag>noData</flag>";
                         }
                         
                 } catch (Exception e) {
                         System.out.println("catch..HERE.in  view head." + e);
                         xml = xml + "<flag>failure</flag>";
                 }
                 xml = xml + "<count>"+count+"</count></response>";
                 System.out.println(xml);
                 out.println(xml);
                 
             }
		
		  else if(strCommand.equalsIgnoreCase("loadhead")) {
             
                  String CONTENT_TYPE = "text/xml; charset=windows-1252";
                  response.setContentType(CONTENT_TYPE);
                  String xml = "";
                  xml = "<response><command>loadhead</command>";
                  int unitcode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
               
                  try {
                         String ss=" SELECT o.BANK_ID,  "+
							" (select b.bank_name from FAS_MST_BANKS b where b.bank_id=o.BANK_ID)as bankName, "+
                        		  "   o.BRANCH_ID, "+
                        		  " (select br.branch_name from fas_mst_bank_branches br where br.bank_id=o.BANK_ID and br.branch_id=o.BRANCH_ID)as branchName, "+
                        		  "   BANK_AC_NO, "+
                        		  "  o.AC_HEAD_CODE, "+
                        		  "  (SELECT h.account_head_desc from "+
                        		  "  com_mst_account_heads h "+
                        		  "   WHERE h.account_head_code=o.AC_HEAD_CODE "+
                        		  "  )AS head_desc, "+
                        		  "   STATUS from"+
                        		  "  FAS_OFFICE_BANK_AC_CURRENT o "+
                        		  " WHERE ACCOUNTING_UNIT_ID="+unitcode+" and STATUS='Y' order BY o.AC_HEAD_CODE";
                         System.out.println(ss);
                          ps=con.prepareStatement(ss);
                           rs=ps.executeQuery();
                           while(rs.next()) {
                               String acc=rs.getInt("AC_HEAD_CODE")+"("+rs.getString("head_desc")+")";
                               xml=xml+"<leng_head>";
                               xml=xml+"<bankName>"+rs.getString("bankName")+"</bankName>";
                               xml=xml+"<branchName>"+rs.getString("branchName")+"</branchName>";
                               xml=xml+"<bank_acc_no>"+rs.getLong("BANK_AC_NO")+"</bank_acc_no>";
                               xml=xml+"<account_head>"+acc +"</account_head>";
                               xml=xml+"<status>"+rs.getString("STATUS") +"</status>";
                               
                               xml=xml+"</leng_head>";
                               count++;
                           }
                          if(count>0) {
                              xml = xml + "<flag>success</flag>";
                          }
                          else {
                              xml = xml + "<flag>noData</flag>";
                          }
                          
                  } catch (Exception e) {
                          System.out.println("catch..HERE.in  view head." + e);
                          xml = xml + "<flag>failure</flag>";
                  }
                  xml = xml + "</response>";
                  System.out.println(xml);
                  out.println(xml);
                  
              }
		
                else if(strCommand.equalsIgnoreCase("viewGrid")) {
                System.out.println("view grid servlet :");
                    String CONTENT_TYPE = "text/xml; charset=windows-1252";
                    response.setContentType(CONTENT_TYPE);
                    String xml = "";
                    xml = "<response><command>viewGrid</command>";
                    txtOperation_mode_view = request.getParameter("txtOperation_mode");
                    System.out.println("txtOperation_mode_view === "+txtOperation_mode_view);
                    try {
                            ps =con.prepareStatement("select BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,MODULE_ID,MODULE_DESC,CR_DR_TYPE,STATUS,ACCOUNT_HEAD_CODE from FAS_MODULE_HEADS_TEMPLATE where AC_OPERATIONAL_MODE_ID='"+txtOperation_mode_view+"'");
                             rs=ps.executeQuery();
                             while(rs.next()) {
                                 
                                 xml=xml+"<leng>";
                                 xml=xml+"<typeid>"+rs.getString("BANK_AC_TYPE_ID")+"</typeid>";
                                 xml=xml+"<modeid>"+rs.getString("AC_OPERATIONAL_MODE_ID").trim()+"</modeid>";
                                 xml=xml+"<moduleid>"+rs.getString("MODULE_ID")+"</moduleid>";
                                 xml=xml+"<cr_type>"+rs.getString("CR_DR_TYPE") +"</cr_type>";
                                 xml=xml+"<status>"+rs.getString("STATUS") +"</status>";
                                 xml=xml+"<account_code>"+rs.getString("ACCOUNT_HEAD_CODE") +"</account_code>";
                                 xml=xml+"<moduleiddesc>"+rs.getString("MODULE_DESC") +"</moduleiddesc>";
                               try{
                            	PreparedStatement ps1 =con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE="+rs.getString("ACCOUNT_HEAD_CODE"));
                               ResultSet rs1=ps1.executeQuery();
                               }catch (Exception e) {
								// TODO: handle exception
							}
                                 
                                 
                                 xml=xml+"</leng>";
                                 count++;
                             }
                            if(count>0) {
                                xml = xml + "<flag>success</flag>";
                            }
                            else {
                                xml = xml + "<flag>noData</flag>";
                            }
                            
                    } catch (Exception e) {
                            System.out.println("catch..HERE.in  view head." + e);
                            xml = xml + "<flag>failure</flag>";
                    }
                    xml = xml + "</response>";
                    System.out.println(xml);
                    out.println(xml);
                    
                }
		/*else if (strCommand.equalsIgnoreCase("Add")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>Add</command>";
			System.out.println("add");
                        
		    String[] grid=request.getParameter("grid").split(",");
		                          
                      int len=grid.length;
                      System.out.println("arraylength"+len);
                        
		//	System.out.println(txtRemarks);
		    for(int i=0;i<len;i=i+len)
                            {
		    	System.out.println("length test"+len);
                               
                              
                                
                               System.out.println("0.."+grid[i]+"1.."+grid[i+1]+"2.."+grid[i+2]+"3.."+grid[i+3]+"4.."+grid[i+4]+"5.."+grid[i+5]);
                               System.out.println("6.."+grid[i+6]+"7.."+grid[i+7]+"8.."+grid[i+8]+"9.."+grid[i+9]+"10.."+grid[i+10]);
                               System.out.println("11.."+grid[i+11]+"12.."+grid[i+12]+"13.."+grid[i+13]+"14.."+grid[i+14]+"15.."+grid[i+15]);
                               System.out.println("16.."+grid[i+16]+"17.."+grid[i+17]);
                           
                          String acctype=grid[i+17];
                           System.out.println("******acctype******"+acctype);
                            
                          String operationMode=grid[i+13];
                               System.out.println("**operationMode***********"+operationMode);
                                
                                String moduleDesc=grid[i+7];
                                System.out.println("moduleDesc*******************"+moduleDesc);
                                
                                String crdrType=grid[i+8];
                                System.out.println("crdrType*******************"+crdrType);
                                
                                String status=grid[i+9];
                                System.out.println("status*******************"+status);
                                
                               //int accHcode=Integer.parseInt(grid[i+23]);
                               Long accHcode=Long.parseLong(grid[i+14]);
                                System.out.println("****accHcode*********"+accHcode);
                              
                                String moduleId=grid[i+16];
                                System.out.println("moduleId*******************"+moduleId);
                               
                            String q="",sl_val="";
                            
                                
                                	 try{
                                	 sl_val="select count(*) as sl_no from FAS_OFFICE_BANK_AC_CURRENT ";
                                	 ps=con.prepareStatement(sl_val);
                                	rs= ps.executeQuery();
                                	while(rs.next()){
                                		sl_no=rs.getInt("sl_no");
                                		 System.out.println("sl NO:::"+sl_no);
                                		
                                	}
                                	 }catch (Exception e) {
									System.out.println(e);
									}
                                	 
                                	 
                                	 System.out.println("sl NOOOO::::"+sl_no);
                                	 sl_no=sl_no+1;
                                	 try {
                                	 q="insert into FAS_OFFICE_BANK_AC_CURRENT_CPY(ACCOUNTING_UNIT_ID,BANK_ID,BRANCH_ID,BANK_AC_NO,BANK_AC_TYPE_ID," +
                                             " AC_OPERATIONAL_MODE_ID,AC_HEAD_CODE,REMARKS,SL_NO,MODULE_ID,CR_DR_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS)  " +
                                             " values("+cmbAcc_UnitCode+","+txtBankId+","+txtBranchId+","+txtBankAccountNo+",'"+acctype+"','"+operationMode+"',"+accHcode+",'"+txtRemarks+"',"+1+",'"+moduleId+"','"+crdrType+"','"+update_user+"',"+ts+",'"+status+"')";
                               
                                	 System.out.println("q values >> "+q);
                                	 
                                	 ps =con.prepareStatement("insert into FAS_OFFICE_BANK_AC_CURRENT(ACCOUNTING_UNIT_ID,BANK_ID,BRANCH_ID,BANK_AC_NO,BANK_AC_TYPE_ID," +
                                                        " AC_OPERATIONAL_MODE_ID,AC_HEAD_CODE,REMARKS,SL_NO,MODULE_ID,CR_DR_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS)  " +
                                        " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                		 ps =con.prepareStatement("insert into FAS_OFFICE_BANK_AC_CURRENT(ACCOUNTING_UNIT_ID,BANK_ID,BRANCH_ID,BANK_AC_NO,BANK_AC_TYPE_ID," +
                                                 " AC_OPERATIONAL_MODE_ID,AC_HEAD_CODE,REMARKS,SL_NO,MODULE_ID,CR_DR_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS)  " +
                                 " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
       
                                        ps.setInt(1, cmbAcc_UnitCode);
                                        ps.setInt(2, txtBankId);
                                        ps.setInt(3, txtBranchId);
                                        ps.setLong(4, txtBankAccountNo);
                                       ps.setString(5, acctype);//grid
                                       ps.setString(6, operationMode);
                                       ps.setLong(7, accHcode);
                                        ps.setString(8, txtRemarks);
                                        ps.setInt(9, 1);
                                       ps.setString(10, moduleId);
                                        ps.setString(11, crdrType);
                                        ps.setString(12, update_user);
                                        ps.setTimestamp(13, ts);
                                        ps.setString(14, status);
                                        System.out.println("q values >> "+txtRemarks+status);
                                        int kk=ps.executeUpdate();
                                        xml = xml + "<flag>success</flag>";
                                     
                                        if(kk>0) 
                                        {
                                             String up_sql ="update FAS_MODULE_HEADS_TEMPLATE set  ACCOUNT_HEAD_CODE="+accHcode+" where AC_OPERATIONAL_MODE_ID='"+operationMode+"' AND MODULE_ID='"+moduleId+"' and CR_DR_TYPE='"+crdrType+"'";
                                             System.out.println("up_sql>>>>>>"+up_sql);
                                             ps3 = con.prepareStatement(up_sql);
                                             ps3.executeUpdate();
                                        }  
                                        
                                } catch (Exception e) {
                                        System.out.println("catch..HERE.in load head code.." + e.getMessage());
                                        xml = xml + "<flag>failure</flag>";
                                }  
                           }
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		} */
                else if (strCommand.equalsIgnoreCase("Add")) {
        			String CONTENT_TYPE = "text/xml; charset=windows-1252";
        			response.setContentType(CONTENT_TYPE);
        			String xml = "";
        			xml = "<response><command>Add</command>";
        			System.out.println("add");
                                
        		    String[] grid=request.getParameter("grid").split(",");
        		                          
                              int len=grid.length;
                              System.out.println("arraylength"+len);
                                
        		System.out.println("Addd >>>>>"+txtRemarks);
        		    for(int i=0;i<len;i=i+7)
                                    {
                                    
                                       /* String acctype=grid[i];
                                                                                
                                    System.out.println("******acctype******"+acctype);
                                    
                                        String operationMode=grid[i+1];
                                        System.out.println("**operationMode***********"+operationMode);
                                        
                                        String moduleId=grid[i+2];
                                        System.out.println("moduleId*******************"+moduleId);
                                        
                                        String crdrType=grid[i+3];
                                        System.out.println("crdrType*******************"+crdrType);
                                        
                                        String status=grid[i+4];
                                        System.out.println("status*******************"+status);
                                        
                                        int accHcode=Integer.parseInt(grid[i+5]);
                                        System.out.println("****accHcode*********"+accHcode);*/
        		    	
        		    	  String checS=grid[i];

    		    	 String acctype=grid[i+1];
                     
                     System.out.println("******acctype******"+acctype);
                     
                   
                         String operationMode=grid[i+2];
                         System.out.println("**operationMode***********"+operationMode);
                         
                         String moduleId=grid[i+3];
                         System.out.println("moduleId*******************"+moduleId);
                         
                         String crdrType=grid[i+4];
                         System.out.println("crdrType*******************"+crdrType);
                         
                         String status=grid[i+5];
                         System.out.println("status*******************"+status);
                         
                         int accHcode=Integer.parseInt(grid[i+6]);
                         System.out.println("****accHcode*********"+accHcode);
                         
                         /*int txtSl_no=Integer.parseInt(grid[i+7]);
                         System.out.println("****accHcode*********"+txtSl_no);*/
                         
                         if((checS.equalsIgnoreCase("CHECKED"))){
                                    
                                         try {
                                        	 System.out.println("try ");
                                                ps =con.prepareStatement("insert into FAS_OFFICE_BANK_AC_CURRENT(ACCOUNTING_UNIT_ID,BANK_ID,BRANCH_ID,BANK_AC_NO,BANK_AC_TYPE_ID," +
                                                                " AC_OPERATIONAL_MODE_ID,AC_HEAD_CODE,REMARKS,SL_NO,MODULE_ID,CR_DR_TYPE,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS)  " +
                                                " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                
                                                ps.setInt(1, cmbAcc_UnitCode);
                                                ps.setInt(2, txtBankId); 
                                                ps.setInt(3, txtBranchId);
                                                ps.setLong(4, txtBankAccountNo);
                                                ps.setString(5, acctype);//grid
                                                ps.setString(6, operationMode.trim());
                                                ps.setInt(7, accHcode);
                                                ps.setString(8, txtRemarks);
                                                ps.setInt(9, txtSl_no);
                                                ps.setString(10, moduleId);
                                                ps.setString(11, crdrType);
                                                ps.setString(12, update_user);
                                                ps.setTimestamp(13, ts);
                                                ps.setString(14, status);
                
                                                int kk=ps.executeUpdate();
                                                xml = xml + "<flag>success</flag>";
                                                
                                                
                                             /*   if(kk>0) 
                                                {
                                                     String up_sql ="update FAS_MODULE_HEADS_TEMPLATE set  ACCOUNT_HEAD_CODE="+accHcode+" where AC_OPERATIONAL_MODE_ID='"+operationMode+"' AND MODULE_ID='"+moduleId+"' and CR_DR_TYPE='"+crdrType+"'";
                                                     System.out.println("up_sql>>>>>>"+up_sql);
                                                     ps3 = con.prepareStatement(up_sql);
                                                     ps3.executeUpdate();
                                                }  */
                                                System.out
														.println("End try "+kk);
                                        } catch (Exception e) {
                                                System.out.println("catch..HERE.in load head code.." + e.getMessage());
                                                xml = xml + "<flag>failure</flag>";
                                        }  
                                   }
                                    }
        			xml = xml + "</response>";
        			System.out.println(xml);
        			out.println(xml);
        		}
		else if(strCommand.equalsIgnoreCase("AddOrUpdate"))
		{

			int k=0;

			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>Add</command>";
			System.out.println("add");
			String[] UnitCode=request.getParameterValues("cmbAcc_UnitCode");
			//String[] cmbOffice_code=request.getParameterValues("cmbOffice_code");
			String[] BankAccountNo=request.getParameterValues("txtBankAccountNo");
			String[] BankId=request.getParameterValues("txtBankId");
			String[] BankAcc_type=request.getParameterValues("txtBankAcc_type");
			String[] Operation_mode=request.getParameterValues("txtOperation_mode");
			String[] Acc_HeadCode=request.getParameterValues("txtAcc_HeadCode");
			String[] Module=request.getParameterValues("cmbModule");
			//String[] Module=request.getParameterValues("module_id");
		
			String[] BranchId=request.getParameterValues("txtBranchId");
			String[] Cr_dR=request.getParameterValues("CrDrType");
			 String q="",sl_val="",q_update_Head="",chg_head="";
			 int h_code=0;
           for(int i=0;i<UnitCode.length;i++)
           {
        	   System.out.println(Module[i]);
        	   if(!Module[i].equalsIgnoreCase("MF009")){
        		   System.out.println("h_code value is not MF009 :::::: "+h_code);
        	   h_code=Integer.parseInt(Acc_HeadCode[i]);
           }
        	   if(Module[i].equalsIgnoreCase("MF009")){
        	System.out.println("Module[i] >>> "+Module[i]);
        	
 		    chg_head = Acc_HeadCode[i].substring(0, Acc_HeadCode[i].length() - 2)+"07";
 		 
 		   System.out.println(chg_head);
 		 h_code=Integer.parseInt(chg_head);
 		 System.out.println("h_code value is :::::: "+h_code);
        	}
        	   System.out.println("h_code value is :::::: "+h_code);	
        	  
        		   System.out.println(h_code);
        	   int B_id=Integer.parseInt(BankId[i]);
        	   int Branch_id=Integer.parseInt(BranchId[i]);
        	   long Acc_No=Long.parseLong(BankAccountNo[i]);
        	   int u_code=Integer.parseInt(UnitCode[i]);
        	  // int office_ID=Integer.parseInt(cmbOffice_code[i]);
        	  
        	   
				q = "insert into FAS_OFFICE_BANK_AC_CURRENT (ACCOUNTING_UNIT_ID," +
						"AC_OPERATIONAL_MODE_ID,MODULE_ID,AC_HEAD_CODE,BANK_ID," +
						"BRANCH_ID,BANK_AC_NO,BANK_AC_TYPE_ID,SL_NO," +
						"UPDATED_BY_USER_ID,UPDATED_DATE,CR_DR_TYPE,STATUS) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				//  q_update_Head=" update FAS_MST_BANK_ACCOUNT_HEADS set AC_HEAD_CODE="+h_code+"  where AC_OPERATIONAL_MODE_ID='"+Operation_mode[i]+"' and BANK_ID="+B_id;
				System.out.println(" "+q);
                  try{
                  	ps=con.prepareStatement(q);
                  	ps.setInt(1, u_code);
                  	ps.setString(2,Operation_mode[i] );
                	//int moduleid=Integer.parseInt(Module[i]);
                	//System.out.println("mod"+moduleid);
                  	ps.setString(3,Module[i].trim());
                  	ps.setInt(4, h_code);
                  	ps.setInt(5, B_id);
                  	ps.setInt(6, Branch_id);
                  	ps.setLong(7, Acc_No);
                  	ps.setString(8, BankAcc_type[i]);
                  	ps.setInt(9, i);
                  	//ps.setInt(10, office_ID);
                  	ps.setString(10, update_user);
                  	ps.setTimestamp(11, ts);
                  	ps.setString(12,Cr_dR[i]);
                  	ps.setString(13, "Y");
                    k=  ps.executeUpdate();
                                
                            }
                                 catch (Exception e) {
                                        System.out.println("catch..HERE.in load head code.." + e.getMessage());
                                        xml = xml + "<flag>failure</flag>";
                                }
           }
           
           if(k==0){
        	   xml = xml + "<flag>failure</flag>";
           }else{
        	   xml = xml + "<flag>success</flag>";
        	 /*  try {
        		   System.out.println(" "+q_update_Head);
				PreparedStatement ps_head=con.prepareStatement(q_update_Head);
				k=ps_head.executeUpdate();
				xml = xml + "<flag_update>success</flag_update>";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
           }
                                xml = xml + "</response>";
                    			System.out.println(xml);
                    			out.println(xml);
                    		
                           }
		
		
	/*	else if(strCommand.equalsIgnoreCase("AddOrUpdate"))
		{
			int k=0;

			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>Add</command>";
			System.out.println("add");
			String[] UnitCode=request.getParameterValues("cmbAcc_UnitCode");
			String[] BankAccountNo=request.getParameterValues("txtBankAccountNo");
			String[] BankId=request.getParameterValues("txtBankId");
			String[] BankAcc_type=request.getParameterValues("txtBankAcc_type");
			String[] Operation_mode=request.getParameterValues("txtOperation_mode");
			String[] Acc_HeadCode=request.getParameterValues("txtAcc_HeadCode");
			String[] Module=request.getParameterValues("cmbModule");
			String[] BranchId=request.getParameterValues("txtBranchId");
			 String q="",sl_val="";
			 
           for(int i=0;i<UnitCode.length;i++)
           {
        	   int h_code=Integer.parseInt(Acc_HeadCode[i]);
        	   int B_id=Integer.parseInt(BankId[i]);
        	   int Branch_id=Integer.parseInt(BranchId[i]);
        	   long Acc_No=Long.parseLong(BankAccountNo[i]);
        	   int u_code=Integer.parseInt(UnitCode[i]);
				q = "update FAS_OFFICE_BANK_AC_CURRENT set  "
						+ "AC_HEAD_CODE=" +h_code
						+ " , BANK_ID="+ B_id                 
						+ " , BRANCH_ID="+Branch_id 
						+ " , BANK_AC_NO="+Acc_No
					    + " , BANK_AC_TYPE_ID='"+BankAcc_type[i]+"'" 
						+ "  where ACCOUNTING_UNIT_ID=" + u_code
						+ " and AC_OPERATIONAL_MODE_ID='" + Operation_mode[i]
						+ "'  AND MODULE_ID='" + Module[i] + "' ";
           
                           System.out.println(" "+q);
                            try{
                            	ps=con.prepareStatement(q);
                              k=  ps.executeUpdate();
                                
                            }
                                 catch (Exception e) {
                                        System.out.println("catch..HERE.in load head code.." + e.getMessage());
                                        xml = xml + "<flag>failure</flag>";
                                }
           }
           
           if(k==0){
        	   xml = xml + "<flag>failure</flag>";
           }else{
        	   xml = xml + "<flag>success</flag>";
           }
                                xml = xml + "</response>";
                    			System.out.println(xml);
                    			out.println(xml);
                    		
                           }*/
		else if (strCommand.equalsIgnoreCase("Update")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>Update</command>";

			try {
				String up_sql =
					"update FAS_OFFICE_BANK_AC_CURRENT set  REMARKS=?,SL_NO=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? ,STATUS=? where ACCOUNTING_UNIT_ID=? and BANK_ID=? and "+
					"BRANCH_ID=? and BANK_AC_NO=? and BANK_AC_TYPE_ID=? and AC_HEAD_CODE=? and MODULE_ID=? and CR_DR_TYPE=?";
				ps = con.prepareStatement(up_sql);
				//                System.out.println("here.." + up_sql);
				//                System.out.println(ps);
				//                System.out.println(cmbAcc_UnitCode);
				//                System.out.println(txtBankId);
				//                System.out.println(txtBranchId);
				//                System.out.println(txtBankAccountNo);
				//                System.out.println(txtBankAcc_type);
				//                System.out.println(txtAcc_HeadCode);
				//                System.out.println(rad_CR_DR);
				//                System.out.println(cmbModule);
				//                System.out.println(txtRemarks);
				//                System.out.println("slNO" + radDefault);
				//                System.out.println("radStatus-----------------------------------------------------------"+radStatus);

				ps.setString(1, txtRemarks);
				ps.setInt(2, radDefault);
				ps.setString(3, update_user);
				ps.setTimestamp(4, ts);
				ps.setString(5, radStatus);

				ps.setInt(6, cmbAcc_UnitCode);
				ps.setInt(7, txtBankId);                
				ps.setInt(8, txtBranchId);
				ps.setLong(9, txtBankAccountNo);
				ps.setString(10, txtBankAcc_type);
				ps.setInt(11, txtAcc_HeadCode);
				ps.setString(12, cmbModule);
				ps.setString(13, rad_CR_DR);
				int u = ps.executeUpdate();
				System.out.println("output");
				System.out.println(u);
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				System.out.println("catch..HERE.in load head code." + e);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		}
		
		// started /siva added on 2016-05-06 in edit_module_wise_date
		
				else if (strCommand.equalsIgnoreCase("LoadDetails")) {
					   cmbModule= request.getParameter("cmbModule");
		        	
		        	System.out.println("system enterd to the LoadDetails..."+cmbModule);
		            String CONTENT_TYPE = "text/xml; charset=windows-1252";
		            response.setContentType(CONTENT_TYPE);
		            String xml = "";
		            xml = "<response><command>LoadDetails</command>";
		            
		            int y = 0;

		            try {
		            	  ps =    		  con.prepareStatement("SELECT o.bank_id," +
		            			"  (SELECT b.bank_name FROM FAS_MST_BANKS b WHERE b.bank_id=o.BANK_ID   )AS BANKNAME, o.branch_id," +
		            			" (SELECT br.branch_name   FROM fas_mst_bank_branches br   WHERE br.bank_id=o.BANK_ID   AND br.branch_id=o.BRANCH_ID   )AS BRANCHNAME, " +
		            			"  O.SL_NO, BANK_AC_NO, " +
		            			"  (SELECT ty.account_type   FROM FAS_MST_BANK_AC_TYPES ty   WHERE ty.ACCOUNT_TYPE_ID=o.BANK_AC_TYPE_ID   )AS typedesc, o.bank_ac_type_id,  " +
		            			"  o.AC_OPERATIONAL_MODE_ID,   o.AC_HEAD_CODE, " +
		            			" (SELECT h.account_head_desc  FROM com_mst_account_heads h   WHERE h.account_head_code=o.AC_HEAD_CODE   )AS head_desc,  o.MODULE_ID , " +
		            			"  (SELECT h.module_desc   FROM FAS_MODULE_HEADS_TEMPLATE h   WHERE h.MODULE_ID =o.MODULE_ID  limit 1   ) AS module_id_Desc , " +
		            			"  o.CR_DR_TYPE ,o.STATUS  FROM FAS_OFFICE_BANK_AC_CURRENT o " +
		            			"  WHERE ACCOUNTING_UNIT_ID  =?" +
		            			"  AND AC_OPERATIONAL_MODE_ID=?" +
		            			//"  AND STATUS                ='Y' " +
		            			"	ORDER BY O.BANK_AC_NO,O.MODULE_ID,O.CR_DR_TYPE ");
		              
		                ps.setInt(1, cmbAcc_UnitCode);
		                ps.setString(2, cmbModule );
		                rs = ps.executeQuery();
		                
		                while (rs.next()) {   
		                	//System.out.println("While Entered");
		                    xml =xml + "<BANK_AC_NO>" + rs.getLong("BANK_AC_NO") + "</BANK_AC_NO>" +
		                    		"<BANK_Ac_TYPE_ID>" + rs.getString("bank_ac_type_id") + "</BANK_Ac_TYPE_ID>"+
		                    		"<ACCOUNT_TYPE>" + rs.getString("typedesc") + "</ACCOUNT_TYPE>"+
		                    		"<AC_OPERATIONAL_MODE_ID>" + rs.getString("AC_OPERATIONAL_MODE_ID") + "</AC_OPERATIONAL_MODE_ID>" +
		                    		"<BANK_ID>" + rs.getInt("bank_id") +"</BANK_ID>"+ 
		                    		"<BANK_NAME>" + rs.getString("BANKNAME") + "" +"</BANK_NAME>" +
		                    		"<BRANCH_ID>" + rs.getInt("branch_id") + "</BRANCH_ID>"+ 
		                    		"<BRANCH_NAME>" + rs.getString("BRANCHNAME") + "</BRANCH_NAME>" + 
		                    		"<SL_NO>" + rs.getInt("SL_NO") + "</SL_NO>" + 
		                    		"<MODULE_ID>" + rs.getString("MODULE_ID") + "</MODULE_ID>"+
		                    		"<module_id_Desc>" + rs.getString("module_id_Desc") + "</module_id_Desc>" +
		                    		"<AC_HEAD_CODE>" + rs.getInt("AC_HEAD_CODE") + "</AC_HEAD_CODE>"+ 
		                    		"<CR_DR_TYPE>" + rs.getString("CR_DR_TYPE") + "</CR_DR_TYPE>"+
		                    		"<STATUS>" + rs.getString("STATUS") + "</STATUS>"+
		                    		"<head_desc>" + rs.getString("head_desc") + "</head_desc>"+
		                    		"<count>" +   y++  + "</count>" +
		                    		
		                    y++;
		                    
		                    
		                }
		                
		                if (y != 0) {
		                    xml = xml + "<flag>success</flag>";
		                } else
		                    xml = xml + "<flag>failure</flag>";

		                ps.close();
		                rs.close();
		                
		            } catch (Exception e) {
		                System.out.println("catch..HERE.in load head code." + e);
		                xml = xml + "<flag>failure</flag>";
		            }
		            xml = xml + "</response>";
		            System.out.println(xml);
		            out.println(xml);
		        
					
				}
				
				/*else if (strCommand.equalsIgnoreCase("Deleteheadcode")) {
					String CONTENT_TYPE = "text/xml; charset=windows-1252";
					response.setContentType(CONTENT_TYPE);
					String xml = "";
					xml = "<response><command>Deleteheadcode</command>";

					try {
						String up_sql =
							"update FAS_OFFICE_BANK_AC_CURRENT set  STATUS='N' where ACCOUNTING_UNIT_ID=? and BANK_ID=? and "+
							"BRANCH_ID=? and BANK_AC_NO=? and BANK_AC_TYPE_ID=? and AC_HEAD_CODE=? and MODULE_ID=? and CR_DR_TYPE=?";
						ps = con.prepareStatement(up_sql);
						//                System.out.println("here.." + up_sql);
						//                System.out.println(ps);
						//                System.out.println(cmbAcc_UnitCode);
						//                System.out.println(txtBankId);
						//                System.out.println(txtBranchId);
						//                System.out.println(txtBankAccountNo);
						//                System.out.println(txtBankAcc_type);
						//                System.out.println(txtAcc_HeadCode);
						//                System.out.println(rad_CR_DR);
						//                System.out.println(cmbModule);
						//                System.out.println(txtRemarks);
						//                System.out.println("slNO" + radDefault);
						//                System.out.println("radStatus-----------------------------------------------------------"+radStatus);

						ps.setString(1, txtRemarks);
						ps.setInt(2, radDefault);
						ps.setString(3, update_user);
						ps.setTimestamp(4, ts);
						//ps.setString(1, radStatus);
		System.out.println("data"+cmbAcc_UnitCode+"  "+txtBankId+" "+txtBranchId+" "+txtBankAccountNo+" "+txtBankAcc_type+" "+txtAcc_HeadCode+" "+cmbModule+" "+rad_CR_DR);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, txtBankId);                
						ps.setInt(3, txtBranchId);
						ps.setLong(4, txtBankAccountNo);
						ps.setString(5, txtBankAcc_type);
						ps.setInt(6, txtAcc_HeadCode);
						ps.setString(7, cmbModule);
						ps.setString(8, rad_CR_DR);
						int u = ps.executeUpdate();
						System.out.println("output");
						System.out.println(u);
						xml = xml + "<flag>success</flag>";
					} catch (Exception e) {
						System.out.println("catch..HERE.in load head code." + e);
						xml = xml + "<flag>failure</flag>";
					}
					xml = xml + "</response>";
					System.out.println(xml);
					out.println(xml);
				}
				else if (strCommand.equalsIgnoreCase("Updateheadcode")) {
					String CONTENT_TYPE = "text/xml; charset=windows-1252";
					response.setContentType(CONTENT_TYPE);
					String xml = "";
					xml = "<response><command>Updateheadcode</command>";

					try {
						String up_sql =
							"update FAS_OFFICE_BANK_AC_CURRENT set " +
							"ACCOUNTING_UNIT_ID=? , BANK_ID=? , BRANCH_ID=? " +
							", BANK_AC_NO=? , BANK_AC_TYPE_ID=? , AC_HEAD_CODE=? " +
							", MODULE_ID=? , CR_DR_TYPE=?,STATUS='Y' " +
							"" +
							"where ACCOUNTING_UNIT_ID=? " +
							"and BANK_ID=? and "+
							"BRANCH_ID=? and BANK_AC_NO=? and BANK_AC_TYPE_ID=? and AC_HEAD_CODE=? and MODULE_ID=? and CR_DR_TYPE=?";
						ps = con.prepareStatement(up_sql);
						//                System.out.println("here.." + up_sql);
						//                System.out.println(ps);
						//                System.out.println(cmbAcc_UnitCode);
						//                System.out.println(txtBankId);
						//                System.out.println(txtBranchId);
						//                System.out.println(txtBankAccountNo);
						//                System.out.println(txtBankAcc_type);
						//                System.out.println(txtAcc_HeadCode);
						//                System.out.println(rad_CR_DR);
						//                System.out.println(cmbModule);
						//                System.out.println(txtRemarks);
						//                System.out.println("slNO" + radDefault);
						//                System.out.println("radStatus-----------------------------------------------------------"+radStatus);

						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, txtBankId);                
						ps.setInt(3, txtBranchId);
						ps.setLong(4, txtBankAccountNo);
						ps.setString(5, txtBankAcc_type);
						ps.setInt(6, txtAcc_HeadCode);
						ps.setString(7, cmbModule);
						ps.setString(8, rad_CR_DR);

						ps.setInt(9, cmbAcc_UnitCode);
						ps.setInt(10, txtBankId);                
						ps.setInt(11, txtBranchId);
						ps.setLong(12, txtBankAccountNo);
						ps.setString(13, txtBankAcc_type);
						ps.setInt(14, txtAcc_HeadCode);
						ps.setString(15, cmbModule);
						ps.setString(16, rad_CR_DR);
						int u = ps.executeUpdate();
						System.out.println("output");
						System.out.println(u);
						xml = xml + "<flag>success</flag>";
					} catch (Exception e) {
						System.out.println("catch..HERE.in load head code." + e);
						xml = xml + "<flag>failure</flag>";
					}
					xml = xml + "</response>";
					System.out.println(xml);
					out.println(xml);
				}
		
		//end siva
		*/
		
		
				else if (strCommand.equalsIgnoreCase("Deleteheadcode")) {
		            String CONTENT_TYPE = "text/xml; charset=windows-1252";
		            cmbModule= request.getParameter("cmbModule");	           
		           
		           
		           //  cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		           
		                      
		           
		            txtBankId = Integer.parseInt(request.getParameter("txtBankId"));
		            txtBranchId = Integer.parseInt(request.getParameter("txtBranchId"));
		            txtBankAcc_type = request.getParameter("txtBankAcc_type");

		            //txtOperation_mode = request.getParameter("txtOperation_mode");
		            String txtOperation_mode_namel = request
		                    .getParameter("txtOperation_mode_name");
		            cmbAcc_UnitCode = Integer.parseInt(request
		                    .getParameter("cmbAcc_UnitCode"));
		            txtBankAccountNo = Long.parseLong(request
		                    .getParameter("txtBankAccountNo"));
		            String cmbModule_id = request.getParameter("cmbModule_id");
		            txtAcc_HeadCode = Integer.parseInt(request
		                    .getParameter("txtAcc_HeadCode"));
		            String txtcrdr = request.getParameter("txtcrdr");
		            
		          // int  txtSl_NO= Integer.parseInt(request.getParameter("txtSl_no"));

		           
		            Long txtBankAccountNol = Long.parseLong(request.getParameter("txtBankAccountNol"));           
		            String cmbModule_idl = request.getParameter("cmbModule_idl");           
		            int txtAcc_HeadCodel = Integer.parseInt(request
		                    .getParameter("txtAcc_HeadCodel"));           
		            String txtcrdrl = request.getParameter("txtcrdrl");
		            
		            String user = (String)session.getAttribute("UserId");
		    		long ll = System.currentTimeMillis();
		    		Timestamp updated_date = new Timestamp(ll);
		    		System.out.println("updated_date"+updated_date+" "+user);
		           
		           
		            response.setContentType(CONTENT_TYPE);
		            String xml = "";
		            xml = "<response><command>Deleteheadcode</command>";

		            try {
		                String up_sql =
		                    "update FAS_OFFICE_BANK_AC_CURRENT set  STATUS='N',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and BANK_ID=? and "+
		                    "BRANCH_ID=? and BANK_AC_NO=? and BANK_AC_TYPE_ID=? and AC_HEAD_CODE=? and MODULE_ID=? and CR_DR_TYPE=? and SL_NO=?";
		                ps = con.prepareStatement(up_sql);
		                //                System.out.println("here.." + up_sql);
		                //                System.out.println(ps);
		                //                System.out.println(cmbAcc_UnitCode);
		                //                System.out.println(txtBankId);
		                //                System.out.println(txtBranchId);
		                //                System.out.println(txtBankAccountNo);
		                //                System.out.println(txtBankAcc_type);
		                //                System.out.println(txtAcc_HeadCode);
		                //                System.out.println(rad_CR_DR);
		                //                System.out.println(cmbModule);
		                //                System.out.println(txtRemarks);
		                //                System.out.println("slNO" + radDefault);
		                //                System.out.println("radStatus-----------------------------------------------------------"+radStatus);

		                /*ps.setString(1, txtRemarks);
		                ps.setInt(2, radDefault);
		                ps.setString(3, update_user);
		                ps.setTimestamp(4, ts);*/
		                //ps.setString(1, radStatus);
		System.out.println("data"+cmbAcc_UnitCode+"  "+txtBankId+" "+txtBranchId+" "+txtBankAccountNol+" "+txtBankAcc_type+" "+txtAcc_HeadCodel+" "+cmbModule_idl+" "+txtcrdrl);
		                
		                ps.setString(1, user);
		                ps.setTimestamp(2, ts);
		                ps.setInt(3, cmbAcc_UnitCode);
		                ps.setInt(4, txtBankId);               
		                ps.setInt(5, txtBranchId);
		                ps.setLong(6, txtBankAccountNol);
		                ps.setString(7, txtBankAcc_type);
		                ps.setInt(8, txtAcc_HeadCodel);
		                ps.setString(9, cmbModule_idl);
		                ps.setString(10, txtcrdrl);
		                ps.setInt(11, txtSl_no);
		                int u = ps.executeUpdate();
		               
		                if (u>0) {
		                    System.out.println("output");
		                    System.out.println(u);
		                    xml = xml + "<flag>success</flag>";
		                } else {
		                    System.out.println("output");
		                    System.out.println(u);
		                    xml = xml + "<flag>failure</flag>";

		                }
		               
		            } catch (Exception e) {
		                System.out.println("catch..HERE.in load head code." + e);
		                xml = xml + "<flag>failure</flag>";
		            }
		            xml = xml + "</response>";
		            System.out.println(xml);
		            out.println(xml);
		        }
		        else if (strCommand.equalsIgnoreCase("Updateheadcode")) {
		            String CONTENT_TYPE = "text/xml; charset=windows-1252";
     System.out.println("Updateheadcode Entered");

		            txtBankId = Integer.parseInt(request.getParameter("txtBankId"));
		            txtBranchId = Integer.parseInt(request.getParameter("txtBranchId"));
		            txtBankAcc_type = request.getParameter("txtBankAcc_type");

		        //    txtOperation_mode = request.getParameter("txtOperation_mode");
		            String txtOperation_mode_namel = request
		                    .getParameter("txtOperation_mode_name");
		            cmbAcc_UnitCode = Integer.parseInt(request
		                    .getParameter("cmbAcc_UnitCode"));
		            txtBankAccountNo = Long.parseLong(request
		                    .getParameter("txtBankAccountNo"));
		            String cmbModule_id = request.getParameter("cmbModule_id");
		            txtAcc_HeadCode = Integer.parseInt(request
		                    .getParameter("txtAcc_HeadCode"));
		            String txtcrdr = request.getParameter("txtcrdr");
		            //txtBankAcc_type = request.getParameter("txtBankAcc_type");
		           // int  txtSl_NO= Integer.parseInt(request.getParameter("txtSl_no"));
		           
		            Long txtBankAccountNol = Long.parseLong(request
		                    .getParameter("txtBankAccountNol"));           
		            String cmbModule_idl = request.getParameter("cmbModule_idl");           
		            int txtAcc_HeadCodel = Integer.parseInt(request
		                    .getParameter("txtAcc_HeadCodel"));           
		            String txtcrdrl = request.getParameter("txtcrdrl");
		            String status=request.getParameter("txtstatus");
		            
		            String user = (String)session.getAttribute("UserId");
		    		long ll = System.currentTimeMillis();
		    		Timestamp updated_date = new Timestamp(ll);
		    		System.out.println("updated_date"+updated_date);
		           
		           
		            response.setContentType(CONTENT_TYPE);
		            String xml = "";
		            xml = "<response><command>Updateheadcode</command>";

		            try {
		                String up_sql =
		                    "update FAS_OFFICE_BANK_AC_CURRENT set " +
		                    "ACCOUNTING_UNIT_ID=? , BANK_ID=? , BRANCH_ID=? " +
		                    ", BANK_AC_NO=? , BANK_AC_TYPE_ID=? , AC_HEAD_CODE=? " +
		                    ", MODULE_ID=? , CR_DR_TYPE=? ,UPDATED_BY_USER_ID=?,UPDATED_DATE=? , SL_NO=? ,STATUS=?" +
		                    "" +
		                    "where ACCOUNTING_UNIT_ID=? " +
		                    "and BANK_ID=? and "+
		                    "BRANCH_ID=? and BANK_AC_NO=? and BANK_AC_TYPE_ID=? and AC_HEAD_CODE=? and MODULE_ID=? and CR_DR_TYPE=? ";
		               
		                System.out.println("up_sql===>"+up_sql);
		                ps = con.prepareStatement(up_sql);
		               
		                ps.setInt(1, cmbAcc_UnitCode);
		                System.out.println("cmbAcc_UnitCode==>"+cmbAcc_UnitCode);
		                ps.setInt(2, txtBankId);            
		                System.out.println("txtBankId==>"+txtBankId);
		                ps.setInt(3, txtBranchId);
		                System.out.println("txtBranchId==>"+txtBranchId);
		                ps.setLong(4, txtBankAccountNo);
		                System.out.println("txtBankAccountNo==>"+txtBankAccountNo);
		                ps.setString(5, txtBankAcc_type);
		                System.out.println("txtBankAcc_type==>"+txtBankAcc_type);
		                ps.setInt(6, txtAcc_HeadCode);
		                System.out.println("txtAcc_HeadCode==>"+txtAcc_HeadCode);
		                ps.setString(7, cmbModule_id);
		                System.out.println("cmbModule_id==>"+cmbModule_id);
		                ps.setString(8, txtcrdr);
		                System.out.println("txtcrdr==>"+txtcrdr);
		                ps.setString(9, user);
		                System.out.println("user==>"+user);
		                ps.setTimestamp(10, ts);
		                System.out.println("ts==>"+ts);
		                ps.setInt(11, txtSl_no);
		                System.out.println("txtSl_no==>"+txtSl_no);
		                ps.setString(12, status);
		                System.out.println("status==>"+status);

		                ps.setInt(13, cmbAcc_UnitCode);
		                System.out.println("cmbAcc_UnitCode==>"+cmbAcc_UnitCode);
		                ps.setInt(14, txtBankId);    
		                System.out.println("txtBankId==>"+txtBankId);
		                ps.setInt(15, txtBranchId);
		                System.out.println("txtBranchId==>"+txtBranchId);
		                ps.setLong(16, txtBankAccountNol);
		                System.out.println("txtBankAccountNol==>"+txtBankAccountNol);
		                ps.setString(17, txtBankAcc_type);
		                System.out.println("txtBankAcc_type==>"+txtBankAcc_type);
		                ps.setInt(18, txtAcc_HeadCodel);
		                System.out.println("txtAcc_HeadCodel==>"+txtAcc_HeadCodel);
		                ps.setString(19, cmbModule_idl);
		                System.out.println("cmbModule_idl==>"+cmbModule_idl);
		                ps.setString(20, txtcrdrl);
		                System.out.println("txtcrdrl==>"+txtcrdrl);
		                int u = ps.executeUpdate();
		                System.out.println("data"+cmbAcc_UnitCode+"  "+txtBankId+" "+txtBranchId+" "+txtBankAccountNol+" "+txtBankAcc_type+" "+txtAcc_HeadCodel+" "+cmbModule_idl+" "+cmbModule_id+" "+txtcrdrl);
		               
		               System.out.println("U Value==>"+u);
		               
		                if (u>0) {
		                    System.out.println("output");
		                    System.out.println(u);
		                    xml = xml + "<flag>success</flag>";
		                } else {
		                    System.out.println("output");
		                    System.out.println(u);
		                    xml = xml + "<flag>failure</flag>";
		                   
		                }
		               
		            } catch (Exception e) {
		                System.out.println("catch..HERE.in load head code." + e);
		                xml = xml + "<flag>failure</flag>";
		            }
		            xml = xml + "</response>";
		            System.out.println(xml);
		            out.println(xml);
		        }
		        else if (strCommand.equalsIgnoreCase("loadOperationMode")) {
		            String CONTENT_TYPE = "text/xml; charset=windows-1252";  		           
		            response.setContentType(CONTENT_TYPE);
		            int y=0;
		            
		            String xml = "";
		            xml = "<response><command>loadOperationMode</command>";

		            try {
		                String sql =
		                    "select distinct(ac_operational_mode_id) from FAS_OFFICE_BANK_AC_CURRENT where accounting_unit_id=? and status='Y'";
		                		                
		                ps = con.prepareStatement(sql);
		                ps.setInt(1, cmbAcc_UnitCode);  
		                
		                rs= ps.executeQuery();	
		                System.out.println("sql        >>>>>>>"+sql);
		                while (rs.next()) {
		                   // System.out.println("output");
		                    //System.out.println(rs);
		                   
		                    xml=xml+"<ac_operational_mode_id>" + rs.getString("ac_operational_mode_id") + "</ac_operational_mode_id>" ;
		                y++;
		                }
		                
		                if (y != 0) {
		                    xml = xml + "<flag>success</flag>";
		                } else
		                    xml = xml + "<flag>failure</flag>";
		               
		            } catch (Exception e) {
		                System.out.println("catch..HERE.in load head code." + e);
		                xml = xml + "<flag>failure</flag>";
		            }
		            xml = xml + "</response>";
		            System.out.println(xml);
		            out.println(xml);
		        }
//siva ends
		
		
	}
}
