package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import oracle.jdbc.OracleTypes;

public class new_Contractor_LJV extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final String[] Grid_CR_DR_type = null;
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    //@SuppressWarnings("null")
	public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
    	String CONTENT_TYPE ="";
  	  
    	
    
        String strCommand = "";
        Connection con = null;
        ResultSet rs = null,rs_n=null;
        CallableStatement cs = null;
        PreparedStatement ps = null,ps_n=null;
        String xml = "";
        HttpSession session = request.getSession(false);
   String combotype =     request.getParameter("combotype");
 
   
        request.setAttribute("JournalType2",combotype);
        System.out.println(request.getAttribute("JournalType2") + "fgdg");
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

        PrintWriter out=response.getWriter();
        try {

            strCommand = request.getParameter("command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
       if (strCommand.equalsIgnoreCase("loadAccDesc")) {
    		 CONTENT_TYPE = "text/xml; charset=windows-1252";
      	  response.setContentType(CONTENT_TYPE);
System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
			String txtaccountheadcode = request.getParameter("txtAcc_HeadCode");
			System.out.println(txtaccountheadcode);
			xml = "<response><command>loadAccDesc</command>";
			
			try {
				String su = "SELECT DISTINCT s.sch_sno,a.office_id,a.account_head_code,a.project_name|| '-'||a.account_head_code AS project_name FROM PMS_MST_PROJECTS_VIEW s	INNER JOIN PMS_FAS_SCH_ACCT_HEAD_MAP_VW a ON a.sch_sno   =s.sch_sno AND a.office_id=5081 and s.sch_sno=381";
				//System.out.println(su);
				ps = con.prepareStatement(su);
				
				rs = ps.executeQuery();
				
		while (rs.next()) {
					xml = xml + "<ACCOUNT_HEAD_CODE>"
							+ rs.getInt("ACCOUNT_HEAD_CODE")
							+ "</ACCOUNT_HEAD_CODE>";

					xml = xml + "<ACCOUNT_HEAD_DESC>"
							+ rs.getString("project_name")
							+ "</ACCOUNT_HEAD_DESC>";
					
				}
				
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml=xml+"</response>";
			System.out.println("xml ::"+xml);
		      out.println(xml);
		      out.close();	                        
		}
       else if (strCommand.equalsIgnoreCase("loadcRhead")) {
      	 
    System.out.println("strCommand    "+strCommand);
    	   CONTENT_TYPE = "text/xml; charset=windows-1252";
       	  response.setContentType(CONTENT_TYPE);
    	   int c=0;
    		xml = "<response><command>CRHEAD</command>";
    	   int a=0,b=0;
           String s1="",s2="";
           int cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
        
           if(request.getParameter("journal_ty").equalsIgnoreCase("11"))
           {
           s1="  select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE in ( 100101, 110101,110102,110103,110104,130109, 222110, 550102, 550112, 550401, 550402, 550520, 550601, 550602, 550603,550119,550120,550121)";           }
           else if(request.getParameter("journal_ty").equalsIgnoreCase("9")){
               s1="  select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE in ( 100101, 550103)";       

           }  else if(request.getParameter("journal_ty").equalsIgnoreCase("1")){
        	   s1="  select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE in ( 100101,130109,550101,550401,550505,550520,550702,550703,830201)";          
           }else if(request.getParameter("journal_ty").equalsIgnoreCase("2")){
        	   s1="  select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE in ( 100101,110101,110102,110103,110104,130109,550101,550401,550505,550520,550702,550703,830201)";
           }
        	   
        	   System.out.println("tetx "+request.getParameter("journal_ty"));
           
           try {
        		  System.out.println(s1);
				ps = con.prepareStatement(s1);
				CallableStatement cs_st=null;
				rs = ps.executeQuery();
				c=0;	
				int head=0;
			while (rs.next()) {
				
					xml = xml + "<ACCOUNT_HEAD_CODE>"
							+ rs.getInt("ACCOUNT_HEAD_CODE")
							+ "</ACCOUNT_HEAD_CODE>";

					xml = xml + "<ACCOUNT_HEAD_DESC>"
							+ rs.getString("account_head_desc")
							+ "</ACCOUNT_HEAD_DESC>";
					head=rs.getInt("ACCOUNT_HEAD_CODE");
					
					try{
					
					cs_st=con.prepareCall("{call fas_Applicable_sl_head1(?,?,?,?)}");
					cs_st.setInt(1, head);
					cs_st.setInt(2, Integer.parseInt(request.getParameter("journal_ty")));
				cs_st.registerOutParameter(3, OracleTypes.CURSOR);
				cs_st.registerOutParameter(4, java.sql.Types.NUMERIC);
				cs_st.execute();
				rs_n=(ResultSet)cs_st.getObject(3);
				while(rs_n.next()){	
					System.out.println("sssss >>> "+rs_n.getString("Account_Head_Code"));
						xml = xml +	 "<SUB_LEDGER_TYPE_CODE" + c + ">"
									+ rs_n.getInt("SUB_LEDGER_TYPE_CODE")
									+ "</SUB_LEDGER_TYPE_CODE" + c + ">"
									+ "<sub_ledger_type_desc" + c
									+ "><![CDATA["
									+ rs_n.getString("sub_ledger_type_desc")
									+ "]]></sub_ledger_type_desc" + c + ">";
					}
				  }catch (Exception e) {
					  System.out.println("TEST ");
					e.printStackTrace();
						
				  }
					xml = xml + "<c>"
							+ c
							+ "</c>";
				 c++;
				 
			   }
			xml = xml + "<flag>success</flag>";
               
		} catch (Exception e) {
			xml = xml + "<flag>failure</flag>";
			e.printStackTrace();
		}
           
		xml=xml+"</response>";
		System.out.println("xml ::"+xml);
	      out.println(xml);
	      out.close();	                   
	   
    	   
           
       
       }
       
       else if (strCommand.equalsIgnoreCase("loadSchdebitcode")) {
    	 
    	   ////Joan Changes
    	   CONTENT_TYPE = "text/xml; charset=windows-1252";
       	  response.setContentType(CONTENT_TYPE);
    	   int count=0;
    	   
    	   System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
			
    	   String schnoarr=request.getParameter("schno");
    	   System.out.println("schnoarr  "+schnoarr);   			   
    	   int schno = 0;
    	   
    	   //int schno = Integer.parseInt(request.getParameter("schno"));
		//String schnoDesc=request.getParameter("schnoDesc");
    	  
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			System.out.println(schno);
			String[] sd=request.getParameter("txtCrea_date").split("/");
			Date txtCrea_date=null;
			Calendar c;
			try {
				
						
					
					c = new GregorianCalendar(
							Integer.parseInt(sd[2]),
							Integer.parseInt(sd[1]) - 1,
							Integer.parseInt(sd[0]));
					java.util.Date d = c.getTime();
					txtCrea_date = new Date(d
							.getTime());
				}
			 catch (Exception e) {
				System.out.println(e);
			}
	           System.out.println("txtCrea_date " + txtCrea_date);
	    	   
			
			xml = "<response><command>loadSchdebitcode</command>";
			
			try {
				String su = "SELECT  distinct a.account_head_code, a.sch_sno, " +
						"  (SELECT PROJECT_NAME " +
						"  	  FROM PMS_MST_PROJECTS_VIEW v " +
						"  		  WHERE v.sch_sno= a.sch_sno " +
						"  		  AND a.office_id=v.office_id " +
						"  		  AND v.STATUS   ='L' " +
						"  	  )AS SCH_NAME , " +
"  a.office_id, " +
//"  a.account_head_code, " +
"  h.account_head_desc, " +
"  a.project_name " +
"FROM PMS_FAS_SCH_ACCT_HEAD_MAP_VW a " +
"INNER JOIN COM_MST_ACCOUNT_HEADS h " +
" ON a. account_head_code=h.account_head_code " +
" WHERE a.office_id        = " +cmbOffice_code+
" AND a.sch_sno       in ("+schnoarr+") " +
" AND ( Usage_Status ='Y'  " +
" AND Last_Used_Date  IS NULL ) " +
" OR ( USAGE_STATUS    ='B' " + 
" AND LAST_USED_DATE   > ?)" +
"   order BY  a.sch_sno,a.account_head_code ";
				System.out.println(su);
				String su1 =" SELECT h.ACCOUNT_HEAD_CODE, " +
					//	"  ah.ACCOUNTING_UNIT_ID, " +
						"  h.ACCOUNT_HEAD_DESC, " +
						"  h.BALANCE_TYPE, " +
						"  h.SUB_LEDGER_TYPE_APPLICABLE, " +
						"  h.REMARKS, " +
						"  h.sl_mandatory, " +
						"  app.SUB_LEDGER_TYPE_CODE, " +
						"  sl.sub_ledger_type_desc " +
						"FROM COM_MST_ACCOUNT_HEADS h, " +
						//"  Fas_Restricted_Ac_Heads ah, " +
						"  FAS_APPLICABLE_SL_TYPE app, " +
						"  COM_MST_SL_TYPES sl " +
						"WHERE h.USAGE_STATUS       ='Y' " +
						"AND h.ACCOUNT_HEAD_CODE   = ?" +
						"AND h.ACCOUNT_HEAD_CODE    =app.ACCOUNT_HEAD_CODE " +
					//	"AND H.ACCOUNT_HEAD_CODE    = AH.ACCOUNT_HEAD_CODE " +
						"AND sl.SUB_LEDGER_TYPE_CODE=app.sub_ledger_type_code " ;
					//	"AND ah.ACCOUNTING_UNIT_ID  ="+cmbOffice_code;
				ps = con.prepareStatement(su);
				ps.setDate(1, txtCrea_date);
				rs = ps.executeQuery();
				System.out.println("su "+su);
				count=0;
		while (rs.next()) {
			xml = xml + "<cmbAcc_UnitCode>"
					+ cmbAcc_UnitCode
					+ "</cmbAcc_UnitCode>";
			xml = xml + "<cmbOffice_code>"
					+ cmbOffice_code
					+ "</cmbOffice_code>";
					xml = xml + "<ACCOUNT_HEAD_CODE>"
							+ rs.getInt("ACCOUNT_HEAD_CODE")
							+ "</ACCOUNT_HEAD_CODE>";

					xml = xml + "<ACCOUNT_HEAD_DESC>"
							+ rs.getString("account_head_desc")
							+ "</ACCOUNT_HEAD_DESC>";
					
					xml = xml + "<SUB_LEDGER_TYPE>10</SUB_LEDGER_TYPE>";
					xml = xml + "<sub_ledger_name>Project</sub_ledger_name>";
				
					xml = xml + "<SUB_LEDGER_TYPE_CODE>"
							+ rs.getInt("sch_sno")
							+ "</SUB_LEDGER_TYPE_CODE>";
					xml = xml + "<sub_ledger_type_desc><![CDATA["
							+ rs.getString("SCH_NAME")
							+ "]]></sub_ledger_type_desc>";
				
				count++;
				
			/*	System.out.println(su1);
				PreparedStatement ps_new = con.prepareStatement(su1);
				ps_new.setInt(1,rs.getInt("ACCOUNT_HEAD_CODE"));
				ResultSet rs_new = ps_new.executeQuery();
				xml = xml + "<slType>";
			
			while(rs_new.next()) {
			
					xml = xml + "<SUB_LEDGER_TYPE_CODE"+c+">"
							+ rs_new.getInt("SUB_LEDGER_TYPE_CODE")
							+ "</SUB_LEDGER_TYPE_CODE"+c+">";

					xml = xml + "<sub_ledger_type_desc"+c+"><![CDATA["
							+ rs_new.getString("sub_ledger_type_desc")
							+ "]]></sub_ledger_type_desc"+c+">";
					
					
				}
			xml = xml + "</slType>";
		c++;*/
		
		}
		
		if(count>0)
		{
			xml = xml + "<flag>success</flag>";
		}
		else
		{
			xml = xml + "<flag>failure1</flag>";
		}

		
				
				
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml=xml+"</response>";
			System.out.println("xml ::"+xml);
		      out.println(xml);
		      out.close();	                   
    	   
       }
       
       
       else if (strCommand.equalsIgnoreCase("loadsubDesc")) {
    	   CONTENT_TYPE = "text/xml; charset=windows-1252";
       	  response.setContentType(CONTENT_TYPE);
    	   System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssss");
    	   			String txtaccountheadcode = request.getParameter("txtAcc_HeadCode");
    	   			int sel = Integer.parseInt(request.getParameter("sel"));
    	   			System.out.println(txtaccountheadcode);
    	   			xml = "<response><command>loadsubDesc</command>";
    	   			xml = xml + "<sel>"+sel+"</sel>";
    	   			try {
    	   				String su ="SELECT DISTINCT JOURNAL_TYPE_CODE , " +
    	   						"  v.project_name,"+
    	   						 " SUB_LEDGER_CODE " +
    	   						"FROM FAS_JOURNAL_MASTER m " +
    	   						"INNER JOIN pms_mst_projects_view v " +
    	   						"ON m.accounting_for_office_id =v.office_id " +
    	   						"AND m.JOURNAL_TYPE_CODE       ="+txtaccountheadcode+" " +
    	   						"AND v.status                  ='L' " +
    	   						"AND m.accounting_for_office_id=5081";




    	   				System.out.println(su);
    	   				ps = con.prepareStatement(su);
    	   				
    	   				rs = ps.executeQuery();
    	   				
    	   			while(rs.next()) {
    	   					xml = xml + "<SUB_LEDGER_CODE>"
    	   							+ rs.getInt("SUB_LEDGER_CODE")
    	   							+ "</SUB_LEDGER_CODE>";

    	   					xml = xml + "<project_name>"
    	   							+ rs.getString("project_name")
    	   							+ "</project_name>";
    	   					
    	   				}
    	   				
    	   				xml = xml + "<flag>success</flag>";
    	   			} catch (Exception e) {
    	   				xml = xml + "<flag>failure</flag>";
    	   				e.printStackTrace();
    	   			}
    	   			xml=xml+"</response>";
    	   			System.out.println("xml ::"+xml);
    	   		      out.println(xml);
    	   		      out.close();	                       
    	   		}   else if (strCommand.equalsIgnoreCase("loadSchEME")) {
    	   		 CONTENT_TYPE = "text/xml; charset=windows-1252";
    	      	  response.setContentType(CONTENT_TYPE);
    	   			int  cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    	   			int  cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
    	   			
    	   			System.out.println("cmbOffice_code"+cmbOffice_code);
    	   			xml = "<response><command>loadSchEME</command>";
    	   			try {
    	   			//	String su1 ="select SCH_SNO, SCH_NAME from PMS_SCH_MASTER  where office_id=(select u.accounting_unit_office_id from FAS_MST_ACCT_UNITS u where u.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+"  ) and (SCH_STATUS_ID != 10 and SCH_STATUS_ID !=11) order by SCH_SNO ";
    	   			String su1="SELECT a.SCH_SNO, " +
    	   					"  a.office_id, " +
    	   					"  b.SCH_NAME " +
    	   					" FROM " +
    	   					"  (SELECT DISTINCT SCH_SNO,office_id FROM PMS_FAS_SCH_ACCT_HEAD_MAP_VW " +
    	   					"  )a " +
    	   					" INNER JOIN " +
    	   					"  (SELECT SCH_SNO , " +
    	   					"    PROJECT_NAME AS SCH_NAME, " +
    	   					"    office_id " +
    	   					"  FROM PMS_MST_PROJECTS_VIEW " +
    	   					"  WHERE STATUS='L' " +
    	   					"    and (project_id !=0 "+
                            "    and project_id is not null)    "+
    	   					"  )b " +
    	   					" ON a.SCH_SNO    =b.SCH_SNO " +
    	   					" AND a.office_id =b.office_id " +
    	   					" AND a.office_id = "+
    	   					"  (SELECT u.accounting_unit_office_id " +
    	   					"  FROM FAS_MST_ACCT_UNITS u " +
    	   					"  WHERE u.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
    	   					"  ) " +
    	   					"ORDER BY a.SCH_SNO, " +
    	   					"  a.office_id";
    	   				System.out.println(su1);
    	   				ps = con.prepareStatement(su1);
    	   				rs = ps.executeQuery();
    	   				
    	   			while(rs.next()) {
    	   					xml = xml + "<sch_sno>"
    	   							+ rs.getInt("sch_sno")
    	   							+ "</sch_sno>";
    	   			
    	   					xml = xml + "<project_name><![CDATA["
    	   							+ rs.getString("SCH_NAME")
    	   							+ "]]></project_name>";
    	   					
    	   				}
    	   				
    	   				xml = xml + "<flag>success</flag>";
    	   			} catch (Exception e) {
    	   				xml = xml + "<flag>failure</flag>";
    	   				e.printStackTrace();
    	   			}
    	   			xml=xml+"</response>";
    	   			System.out.println("xml ::"+xml);
    	   		      out.println(xml);
    	   		      out.close();	                       
    	   		}
    	   		
       else if (strCommand.equalsIgnoreCase("loadsubdebitDesc")) {
    	   CONTENT_TYPE = "text/xml; charset=windows-1252";
       	  response.setContentType(CONTENT_TYPE);
    	   System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssss");
    	   			String txtaccountheadcode = request.getParameter("txtAcc_HeadCode");
    	   			System.out.println(txtaccountheadcode);
    	   			xml = "<response><command>loadsubdebitDesc</command>";
    	   			
    	   			try {
    	   				String su1 = "SELECT DISTINCT JOURNAL_TYPE_CODE , " +
    	   						"  v.project_name,"+
    	   						 " SUB_LEDGER_CODE " +
    	   						"FROM FAS_JOURNAL_MASTER m " +
    	   						"INNER JOIN pms_mst_projects_view v " +
    	   						"ON m.accounting_for_office_id =v.office_id " +
    	   						"AND m.JOURNAL_TYPE_CODE       =  11 " +
    	   						"AND v.status                  ='L' " +
    	   						"AND m.accounting_for_office_id=5081";
    	   				
    	   				System.out.println(su1);
    	   				ps = con.prepareStatement(su1);
    	   				rs = ps.executeQuery();
    	   				
    	   			while(rs.next()) {
    	   					xml = xml + "<SUB_LEDGER_CODE>"
    	   							+ rs.getInt("SUB_LEDGER_CODE")
    	   							+ "</SUB_LEDGER_CODE>";

    	   					xml = xml + "<project_name>"
    	   							+ rs.getString("project_name")
    	   							+ "</project_name>";
    	   					
    	   				}
    	   				
    	   				xml = xml + "<flag>success</flag>";
    	   			} catch (Exception e) {
    	   				xml = xml + "<flag>failure</flag>";
    	   				e.printStackTrace();
    	   			}
    	   			xml=xml+"</response>";
    	   			System.out.println("xml ::"+xml);
    	   		      out.println(xml);
    	   		      out.close();	                       
    	   		}
       
       
       else if (strCommand.equalsIgnoreCase("Add")) {

       	
       	System.out.println("THIS IS MINE");
       	
            CONTENT_TYPE = "text/html; charset=windows-1252";
           response.setContentType(CONTENT_TYPE);
           xml = "<response><command>Add</command>";
           Calendar c;
           int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
               0, txtCash_year = 0, txtJournalVou_No = 0;
          
           int Total_TRN_Rec = 0;
          
           String txtCheque_NO = "", txtCB_REF_TYPE = "",txtBill_type = null;

           Date txtCrea_date = null, txtCheque_date = null;
			
			String txtBill_date=null,   ACCOUNT_HEAD_DESC=null;;
           String txtRemarks = "",particlular=null;

           int cmbMas_SL_type = 0,SL_type=0,SL_code=0,sl_amt=0,Schemeno=0,  sno=0,cmbMas_SL_Code = 0,txtBill_NO = 0,Schemename=0,CR_DR_type=0;
           String txtMode_of_creat = "M", txtCreat_By_Module = "LJV";
           double dep_rate = 0; 
           String update_user = (String)session.getAttribute("UserId");
           long l = System.currentTimeMillis();
           Timestamp ts = new Timestamp(l);
           int count=0;

           try {
               cmbAcc_UnitCode =
                       Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
           } catch (NumberFormatException e) {
               System.out.println("exception" + e);
           }
           System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

           try {
               cmbOffice_code =
                       Integer.parseInt(request.getParameter("cmbOffice_code"));
           } catch (NumberFormatException e) {
               System.out.println("exception" + e);
           }
           System.out.println("cmbOffice_code " + cmbOffice_code);

           /*  try{txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}
           catch(NumberFormatException e){System.out.println("exception"+e );}
           System.out.println("txtCash_Acc_code "+txtCash_Acc_code);*/

           String[] sd = request.getParameter("txtCrea_date").split("/");
           c =
  new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                        Integer.parseInt(sd[0]));
           java.util.Date d = c.getTime();
           txtCrea_date = new Date(d.getTime());
           System.out.println("txtCrea_date " + txtCrea_date);

           System.out.println("b4 getting month and year");
           try {
               txtCash_year = Integer.parseInt(sd[2]);
           } catch (Exception e) {
               System.out.println("exception" + e);
           }
           System.out.println("txtCash_year " + txtCash_year);

           try {
               txtCash_Month_hid = Integer.parseInt(sd[1]);
           } catch (Exception e) {
               System.out.println("exception" + e);
           }
           System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

           int slNo=0;
        /*   try {
               txtJournalVou_No =
                       Integer.parseInt(request.getParameter("txtJournalVou_No"));
           } catch (Exception e) {
               System.out.println("exception" + e);
           }*/
          // System.out.println("txtJournalVou_No " + txtJournalVou_No);

           /*  try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
           catch(Exception e){System.out.println("exception"+e );}
           System.out.println("txtAmount "+txtAmount);*/

           //txtCheque_NO=request.getParameter("txtCheque_NO");                //no need in bill type*****************
         //  System.out.println("txtCheque_NO " + txtCheque_NO);

           /*  if(!request.getParameter("txtCheque_date").equalsIgnoreCase(""))  // no need in bill type*****************
           {
           sd=request.getParameter("txtCheque_date").split("/");
           c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
           d=c.getTime();
           txtCheque_date=new Date(d.getTime());
           }*/
        //   System.out.println("txtCheque_date " + txtCheque_date);
           // changes here
           try {
               cmbMas_SL_type =
                       Integer.parseInt(request.getParameter("cmbMas_SL_type"));
           } catch (Exception e) {
               System.out.println("exception" + e);
           }

           try {
               cmbMas_SL_Code =
                       Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
           } catch (Exception e) {
               System.out.println("exception" + e);
           }


           System.out.println("cmbMas_SL and SL_CODE " + cmbMas_SL_type +
                              " " + cmbMas_SL_Code); //+" "+cmbMas_offid);

           txtRemarks = request.getParameter("txtRemarks");
           System.out.println("txtRemarks " + txtRemarks);

           try {
               con.clearWarnings();
               con.setAutoCommit(false);
               System.out.println("inside proc");
             //  int supplement_no=0;
               String No_TRN_Rec_cr[] = request.getParameterValues("sl_amt");
               String No_TRN_Rec_dr[] = request.getParameterValues("sl_amt1");
               //int NTR=No_TRN_Rec.length;
              // System.out.println(Total_TRN_Rec+" No_TRN_Rec_cr length"+No_TRN_Rec_cr.length);
              // System.out.println(Total_TRN_Rec+" No_TRN_Rec_cr length"+No_TRN_Rec_dr.length);
               
               
               int Total_TRN_cr=0,Total_TRN_dr=0;
              
               for(int c_cr=0;c_cr<No_TRN_Rec_cr.length;c_cr++)
               {
               	if(No_TRN_Rec_cr[c_cr]!="" && No_TRN_Rec_cr[c_cr]!="0"){
               		Total_TRN_cr=Total_TRN_cr+1;
               	}              	
            	//System.out.println("Siva==============>"+No_TRN_Rec_cr[c_cr]);
            	
               	//System.out.println("total_transaction Cr"+Total_TRN_cr);
               } 
               
               for(int c_Dr=0;c_Dr<No_TRN_Rec_dr.length;c_Dr++)
               {
               	if(No_TRN_Rec_dr[c_Dr]!="" && No_TRN_Rec_dr[c_Dr]!="0"){
               		Total_TRN_dr=Total_TRN_dr+1;
               	}
            	//System.out.println("total_transaction Dr"+Total_TRN_dr);
               }
               
              // System.out.println("total_transaction"+Total_TRN_Rec);
               
               Total_TRN_Rec =Total_TRN_cr+Total_TRN_dr; //Integer.parseInt(No_TRN_Rec);
               System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
               cs =
 con.prepareCall("{call FAS_JOURNAL_MASTER_PROC_NEW(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
               cs.setInt(1, cmbAcc_UnitCode);
               cs.setInt(2, cmbOffice_code);
               cs.setInt(3, txtCash_year);
               cs.setInt(4, txtCash_Month_hid);
               cs.setInt(5, txtJournalVou_No);
               cs.setDate(6, txtCrea_date);
               // cs.setString(7,txtReceipt_type);
               //  cs.setInt(8,txtCash_Acc_code);
            //   cs.setInt(7, 11);
               cs.setInt(7, cmbMas_SL_type);
               
               cs.setInt(8, cmbMas_SL_Code);
               cs.setDouble(9, dep_rate);
               cs.setString(10, txtCheque_NO);
               cs.setDate(11, txtCheque_date);
               cs.setString(12, txtCB_REF_TYPE);
               // cs.setInt(13,txtCB_REF_NO);
               // cs.setDate(14,txtCB_REF_DATE);
               // cs.setDouble(19,txtAmount);
               cs.setInt(13, Total_TRN_Rec);
               cs.setString(14, txtRemarks);
               cs.setString(15, txtMode_of_creat);
               cs.setString(16, txtCreat_By_Module);
               cs.setString(17, "insert");
               cs.registerOutParameter(5, java.sql.Types.NUMERIC);
               cs.registerOutParameter(18, java.sql.Types.NUMERIC);
               cs.setString(19, update_user);
               cs.setTimestamp(20, ts);
             //  cs.setInt(21,supplement_no);
               System.out.println("b4 exe ");
               cs.execute();
               txtJournalVou_No = cs.getInt(5);
               int errcode = cs.getInt(18);
               System.out.println("SQLCODE:::" + errcode);
                System.out.println("result txtJournalVou_No:::" + txtJournalVou_No);
               if (errcode != 0) {
                   System.out.println("redirect");
                   sendMessage(response,
                               "The  Voucher Number Creation Failed ", "ok");
                   xml = xml + "<flag>failure</flag>";
               }else{
               	
               	   String Grid_Bill_No =
                              request.getParameter("txtBill_date");
                          String Grid_Bill_date =
                              request.getParameter("txtBill_date");
                          System.out.println("Grid_Bill_date"+Grid_Bill_date);
                          String Grid_Bill_type =
                              request.getParameter("txtBill_type");

                          String Grid_Agree_No =
                              request.getParameter("txtAgree_No");
                          String Grid_Agree_date =
                              request.getParameter("txtAgree_Date");
               	
               	
               	/// CREDIT SIDE
                          
                     System.out.println("credit side   txtJournalVou_No  "+txtJournalVou_No);  
                          
               	  String Grid_H_code[] =
                             request.getParameterValues("H_code");
                         String Grid_CR_DR_type[] =
                             request.getParameterValues("CR_DR_type");
                         String Grid_SL_type[] =
                             request.getParameterValues("SLtype");
                         String Grid_SL_code[] =null;
                            // request.getParameterValues("SL_code");
                         // String Grid_rec_from[]=request.getParameterValues("rec_from");
                      

                         String Grid_sl_amt[] =
                             request.getParameterValues("sl_amt");
                         String Grid_particular[] =
                             request.getParameterValues("particular");

                         String sql =
                             "insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                             "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                             "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                             "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
                             "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                             "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                         int SL_NO = 1, txtAcc_HeadCode = 0,txtAcc_HeadCode1=0, cmbSL_Code =
                             0,cmbSL_Code1=0, cmbSL_type = 0, cmbSL_type1=0,txtCB_REF_NO = 0;
                         Date txtBill_Date = null, txtAgree_Date =
                             null, txtCheque_DD_date = null, txtCB_REF_DATE = null;
                         double txtsub_Amount = 0,txtsub_Amount1=0;
                         String rad_sub_CR_DR = "",rad_sub_CR_DR1="", txtBill_no = "", txtBill_Type =
                             "", txtAgree_No = "", txtParticular = "",txtParticular1="";
                         String txtCheque_DD = "", txtCheque_DD_NO = "";

                         ps = con.prepareStatement(sql);
                         for (int k = 0; k < Grid_H_code.length; k++) {
                       	  System.out.println("'Grid_sl_amt[k] "+Grid_sl_amt[k]);
                       	  if(Grid_sl_amt[k]!="" && Grid_sl_amt[k] != "0"){
                             try {
                                 txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
                             } catch (Exception e) {
                                 System.out.println("exception in trans " + e);
                             }
                             System.out.println("step1");
                             rad_sub_CR_DR = Grid_CR_DR_type[k];

                             try {
                                 cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
                             } catch (Exception e) {
                                 System.out.println("exception in trans " + e);
                             }
                             System.out.println("step2");
                             try {
                                 cmbSL_Code = Integer.parseInt(request.getParameter("SL_code"+k));
                             } catch (Exception e) {
                                 System.out.println("exception in trans " + e);
                             }
                             System.out.println("step3"+cmbSL_Code);
                             System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
                             System.out.println("Grid_CR_DR_type[k] " +
                                                Grid_CR_DR_type[k]);
                             System.out.println("Grid_SL_type[k]" +
                                                Grid_SL_type[k] + "u");
                             System.out.println("Grid_SL_code[k]" +
                                               "from here" +
                                                cmbSL_Code);
                             //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                             //txtsub_Recei_from=Grid_rec_from[k];


                             txtBill_no = Grid_Bill_No;

                             txtBill_Type = Grid_Bill_type;

                             if (!Grid_Bill_date.equalsIgnoreCase("")) {
                                 sd = Grid_Bill_date.split("/");
                                 c =
        new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                              Integer.parseInt(sd[0]));
                                 d = c.getTime();
                                 txtBill_Date = new Date(d.getTime());
                             }

                             txtAgree_No = Grid_Agree_No;
                             if (!Grid_Agree_date.equalsIgnoreCase("")) {
                                 sd = Grid_Agree_date.split("/");
                                 c =
        new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                              Integer.parseInt(sd[0]));
                                 d = c.getTime();
                                 txtAgree_Date = new Date(d.getTime());
                             }

                             System.out.println("txtBill_no..." + txtBill_no);
                             System.out.println("txtBill_Type..." + txtBill_Type);
                             System.out.println("txtBill_Date..." + txtBill_Date);
                             System.out.println("txtAgree_No..." + txtAgree_No);
                             System.out.println("txtAgree_Date..." + txtAgree_Date);

                             txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                             txtParticular = Grid_particular[k];
                             System.out.println("amount");
                             System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);
                             // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                             System.out.println("Grid_particular[k] " +
                                                Grid_particular[k]);

                             ps.setInt(1, cmbAcc_UnitCode);
                             ps.setInt(2, cmbOffice_code);
                             ps.setInt(3, txtCash_year);
                             ps.setInt(4, txtCash_Month_hid);
                             ps.setInt(5, txtJournalVou_No);
                             ps.setInt(6, SL_NO);
                             ps.setInt(7, txtAcc_HeadCode);
                             ps.setString(8, "CR");
                             ps.setInt(9, cmbSL_type);
                             ps.setInt(10, cmbSL_Code);
                             ps.setString(11, txtBill_no);
                             ps.setString(12, txtBill_Type);
                             ps.setString(13, txtAgree_No);
                             ps.setDate(14, txtAgree_Date);
                             ps.setDate(15, txtBill_Date);

                             ps.setString(16, txtCheque_DD);
                             ps.setString(17, txtCheque_DD_NO);
                             ps.setDate(18, txtCheque_DD_date);

                             ps.setDouble(19, txtsub_Amount);
                             ps.setString(20, txtParticular);
                             ps.setInt(21, txtCB_REF_NO);
                             ps.setDate(22, txtCB_REF_DATE);
                             ps.setString(23, update_user);
                             ps.setTimestamp(24, ts);
                             SL_NO++;
                             slNo=SL_NO;
                             int kk= ps.executeUpdate();
if(kk>0){
	count=count+0;
}else{
	count=count+1;	
}
                             txtAcc_HeadCode = 0;
                             rad_sub_CR_DR = "";
                             cmbSL_type = 0;
                             cmbSL_Code = 0;
                          /*   txtBill_no = "";
                             txtBill_Type = "";
                             txtBill_Date = null;
                             txtCheque_DD = "";
                             txtCheque_DD_NO = "";
                             txtCheque_DD_date = null;
                             txtAgree_No = "";
                             txtAgree_Date = null;*/
                             txtsub_Amount = 0;
                             txtParticular = "";
                         }
               }  
                         
                         
                         
                         //DEBIT SIDE
                         
                         System.out.println("Debit side   txtJournalVou_No  "+txtJournalVou_No);  
                         
                   	  String Grid_H_code_DR[] =
                                 request.getParameterValues("ACCOUNT_HEAD_code");
                             String Grid_CR_DR_type_DR[] =
                                 request.getParameterValues("CR_DR_type1");
                             String Grid_SL_type_DR[] =null;
                                // request.getParameterValues("sel_debtype");
                         
                             // String Grid_rec_from[]=request.getParameterValues("rec_from");
                          

                             String Grid_sl_amt_DR[] =
                                 request.getParameterValues("sl_amt1");
                             String Grid_particular_DR[] =
                                 request.getParameterValues("particular1");
                         
                        
                         
                         for (int kj = 0; kj < Grid_H_code_DR.length; kj++) {
                       	  if(Grid_sl_amt_DR[kj]!="" && Grid_sl_amt_DR[kj] != "0"){
                             try {
                                 txtAcc_HeadCode1 = Integer.parseInt(Grid_H_code_DR[kj]);
                             } catch (Exception e) {
                                 System.out.println("exception in trans " + e);
                             }
                             rad_sub_CR_DR1 = Grid_CR_DR_type_DR[kj];
/*
                             try {
                                 cmbSL_type1 = Integer.parseInt(Grid_SL_type_DR[kj]);
                             } catch (Exception e) {
                                 System.out.println("exception in trans " + e);
                             }*/
                             try {
                           	  
                                 cmbSL_Code1 =    Integer.parseInt(request.getParameter("SLtypecode1"+kj));
                             } catch (Exception e) {
                                 System.out.println("exception in trans " + e);
                             }
                             System.out.println("Grid_H_code[k] " + Grid_H_code_DR[kj]);
                             System.out.println("Grid_CR_DR_type[k] " +
                            		 Grid_CR_DR_type_DR[kj]);
                          /*   System.out.println("Grid_SL_type[k]" +
                            		 Grid_SL_type_DR[kj] + "u");*/
                            /* System.out.println("Grid_SL_code[k]" +
                                              "from here" +
                                                cmbSL_Code1);*/
                             //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                             //txtsub_Recei_from=Grid_rec_from[k];


                             txtBill_no = Grid_Bill_No;

                             txtBill_Type = Grid_Bill_type;

                             if (!Grid_Bill_date.equalsIgnoreCase("")) {
                                 sd = Grid_Bill_date.split("/");
                                 c =
        new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                              Integer.parseInt(sd[0]));
                                 d = c.getTime();
                                 txtBill_Date = new Date(d.getTime());
                             }

                             txtAgree_No = Grid_Agree_No;
                             if (!Grid_Agree_date.equalsIgnoreCase("")) {
                                 sd = Grid_Agree_date.split("/");
                                 c =
        new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                              Integer.parseInt(sd[0]));
                                 d = c.getTime();
                                 txtAgree_Date = new Date(d.getTime());
                             }

                             System.out.println("txtBill_no..." + txtBill_no);
                             System.out.println("txtBill_Type..." + txtBill_Type);
                             System.out.println("txtBill_Date..." + txtBill_Date);
                             System.out.println("txtAgree_No..." + txtAgree_No);
                             System.out.println("txtAgree_Date..." + txtAgree_Date);

                             txtsub_Amount1 = Double.parseDouble(Grid_sl_amt_DR[kj]);
                             txtParticular1 = Grid_particular_DR[kj];
                             System.out.println("amount");
                             System.out.println("Grid_sl_amt[k] " + Grid_sl_amt_DR[kj]);
                             // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                             System.out.println("Grid_particular[k] " +
                            		 Grid_particular_DR[kj]);

                             ps.setInt(1, cmbAcc_UnitCode);
                             ps.setInt(2, cmbOffice_code);
                             ps.setInt(3, txtCash_year);
                             ps.setInt(4, txtCash_Month_hid);
                             ps.setInt(5, txtJournalVou_No);
                             ps.setInt(6, slNo);System.out.println("SL_NO  slNo  "+slNo);
                             ps.setInt(7, txtAcc_HeadCode1);
                             ps.setString(8, "DR");
                             ps.setInt(9, Integer.parseInt(request.getParameter("sel_debtype"+kj)));
                             ps.setInt(10, cmbSL_Code1);
                             ps.setString(11, txtBill_no);
                             ps.setString(12, txtBill_Type);
                             ps.setString(13, txtAgree_No);
                             ps.setDate(14, txtAgree_Date);
                             ps.setDate(15, txtBill_Date);

                             ps.setString(16, txtCheque_DD);
                             ps.setString(17, txtCheque_DD_NO);
                             ps.setDate(18, txtCheque_DD_date);

                             ps.setDouble(19, txtsub_Amount1);
                             ps.setString(20, txtParticular1);
                             ps.setInt(21, txtCB_REF_NO);
                             ps.setDate(22, txtCB_REF_DATE);
                             ps.setString(23, update_user);
                             ps.setTimestamp(24, ts);
                             slNo++;
                             int kk= ps.executeUpdate();
if(kk>0){
	count=count+0;
}else{
	count=count+1;
}
                             txtAcc_HeadCode1 = 0;
                             rad_sub_CR_DR1 = "";
                             cmbSL_type1 = 0;
                             cmbSL_Code1 = 0;
                         /*    txtBill_no = "";
                             txtBill_Type = "";
                             txtBill_Date = null;
                             txtCheque_DD = "";
                             txtCheque_DD_NO = "";
                             txtCheque_DD_date = null;
                             txtAgree_No = "";
                             txtAgree_Date = null;*/
                             txtsub_Amount1 = 0;
                             txtParticular1 = "";
                         }  
                         }
                         
                         
                         
                         
                      if(count == 0){   
                         ps.close();
                         System.out.println("b4 commit");
                         con.commit();
                         sendMessage(response,
                                     "The  Voucher Number '" + txtJournalVou_No +
                                     "' has been Created Successfully ", "ok");
                     }
                      else{
                   	     sendMessage(response,
                                    "Insertion Failed", "ok");
                      }
               }
           }
    
      
     
         
       catch(Exception e)
  {
	   e.printStackTrace();
  }
          
      
       }
       
       
       
       
        
       /*   else if (strCommand.equalsIgnoreCase("Add")) {
        	
        	System.out.println("nagaveniiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        	
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtJournalVou_No = 0;
           
            int Total_TRN_Rec = 0;
           
            String txtCheque_NO = "", txtCB_REF_TYPE = "",txtBill_type = null;

            Date txtCrea_date = null, txtCheque_date = null;
			
			String txtBill_date=null,   ACCOUNT_HEAD_DESC=null;;
            String txtRemarks = "",particlular=null;

            int cmbMas_SL_type = 0,SL_type=0,SL_code=0,sl_amt=0,Schemeno=0,  sno=0,cmbMas_SL_Code = 0,txtBill_NO = 0,Schemename=0,CR_DR_type=0;
            String txtMode_of_creat = "M", txtCreat_By_Module = "LJV";
            double dep_rate = 0; 
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            int count=0;

            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

              try{txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Acc_code "+txtCash_Acc_code);

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            System.out.println("b4 getting month and year");
            try {
                txtCash_year = Integer.parseInt(sd[2]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_year " + txtCash_year);

            try {
                txtCash_Month_hid = Integer.parseInt(sd[1]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

            int slNo=0;
            try {
                txtJournalVou_No =
                        Integer.parseInt(request.getParameter("txtJournalVou_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtJournalVou_No " + txtJournalVou_No);

              try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+txtAmount);

            //txtCheque_NO=request.getParameter("txtCheque_NO");                //no need in bill type*****************
          //  System.out.println("txtCheque_NO " + txtCheque_NO);

              if(!request.getParameter("txtCheque_date").equalsIgnoreCase(""))  // no need in bill type*****************
            {
            sd=request.getParameter("txtCheque_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtCheque_date=new Date(d.getTime());
            }
         //   System.out.println("txtCheque_date " + txtCheque_date);
            // changes here
            try {
                cmbMas_SL_type =
                        Integer.parseInt(request.getParameter("cmbMas_SL_type"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }

            try {
                cmbMas_SL_Code =
                        Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }


            System.out.println("cmbMas_SL and SL_CODE " + cmbMas_SL_type +
                               " " + cmbMas_SL_Code); //+" "+cmbMas_offid);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");
              //  int supplement_no=0;
                String No_TRN_Rec_cr[] = request.getParameterValues("sl_amt");
                String No_TRN_Rec_dr[] = request.getParameterValues("sl_amt1");
                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                int Total_TRN_cr=0,Total_TRN_dr=0;
                for(int c_cr=0;c_cr<No_TRN_Rec_cr.length;c_cr++)
                {
                	if(No_TRN_Rec_cr[c_cr]!="" && No_TRN_Rec_cr[c_cr]!="0"){
                		Total_TRN_cr=Total_TRN_Rec+1;
                	}
                	
                } for(int c_Dr=0;c_Dr<No_TRN_Rec_dr.length;c_Dr++)
                {
                	if(No_TRN_Rec_dr[c_Dr]!="" && No_TRN_Rec_dr[c_Dr]!="0"){
                		Total_TRN_dr=Total_TRN_dr+1;
                	}
                	
                }
                
                Total_TRN_Rec =Total_TRN_cr+Total_TRN_dr; //Integer.parseInt(No_TRN_Rec);
                System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
                cs =
  con.prepareCall("{call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setInt(3, txtCash_year);
                cs.setInt(4, txtCash_Month_hid);
                cs.setInt(5, txtJournalVou_No);
                cs.setDate(6, txtCrea_date);
                // cs.setString(7,txtReceipt_type);
                //  cs.setInt(8,txtCash_Acc_code);
                cs.setInt(7, cmbMas_SL_type);
                cs.setInt(8, cmbMas_SL_Code);
                cs.setDouble(9, dep_rate);
                cs.setString(10, txtCheque_NO);
                cs.setDate(11, txtCheque_date);
                cs.setString(12, txtCB_REF_TYPE);
                // cs.setInt(13,txtCB_REF_NO);
                // cs.setDate(14,txtCB_REF_DATE);
                // cs.setDouble(19,txtAmount);
                cs.setInt(13, Total_TRN_Rec);
                cs.setString(14, txtRemarks);
                cs.setString(15, txtMode_of_creat);
                cs.setString(16, txtCreat_By_Module);
                cs.setString(17, "insert");
                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
                cs.setString(19, update_user);
                cs.setTimestamp(20, ts);
              //  cs.setInt(21,supplement_no);
                System.out.println("b4 exe ");
                cs.execute();
                txtJournalVou_No = cs.getInt(5);
                int errcode = cs.getInt(18);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The  Voucher Number Creation Failed ", "ok");
                    xml = xml + "<flag>failure</flag>";
                }else{
                	
                	   String Grid_Bill_No =
                               request.getParameter("Bill_NO");
                           String Grid_Bill_date =
                               request.getParameter("Bill_date");
                           String Grid_Bill_type =
                               request.getParameter("Bill_type");

                           String Grid_Agree_No =
                               request.getParameter("Agree_No");
                           String Grid_Agree_date =
                               request.getParameter("Agree_date");
                	
                	
                	/// CREDIT SIDE
                           
                           
                           
                	  String Grid_H_code[] =
                              request.getParameterValues("H_code");
                          String Grid_CR_DR_type[] =
                              request.getParameterValues("CR_DR_type");
                          String Grid_SL_type[] =
                              request.getParameterValues("SL_type");
                          String Grid_SL_code[] =
                              request.getParameterValues("SL_code");
                          // String Grid_rec_from[]=request.getParameterValues("rec_from");
                       

                          String Grid_sl_amt[] =
                              request.getParameterValues("sl_amt");
                          String Grid_particular[] =
                              request.getParameterValues("particular");

                          String sql =
                              "insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                              "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                              "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                              "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
                              "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                              "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                          int SL_NO = 1, txtAcc_HeadCode = 0,txtAcc_HeadCode1=0, cmbSL_Code =
                              0,cmbSL_Code1=0, cmbSL_type = 0, cmbSL_type1=0,txtCB_REF_NO = 0;
                          Date txtBill_Date = null, txtAgree_Date =
                              null, txtCheque_DD_date = null, txtCB_REF_DATE = null;
                          double txtsub_Amount = 0,txtsub_Amount1=0;
                          String rad_sub_CR_DR = "",rad_sub_CR_DR1="", txtBill_no = "", txtBill_Type =
                              "", txtAgree_No = "", txtParticular = "",txtParticular1="";
                          String txtCheque_DD = "", txtCheque_DD_NO = "";

                          ps = con.prepareStatement(sql);
                          for (int k = 0; k < Grid_H_code.length; k++) {
                              try {
                                  txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              rad_sub_CR_DR = Grid_CR_DR_type[k];

                              try {
                                  cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              try {
                                  cmbSL_Code = Integer.parseInt(Grid_SL_code[k]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
                              System.out.println("Grid_CR_DR_type[k] " +
                                                 Grid_CR_DR_type[k]);
                              System.out.println("Grid_SL_type[k]" +
                                                 Grid_SL_type[k] + "u");
                              System.out.println("Grid_SL_code[k]" +
                                                 Grid_SL_code[k] + "from here" +
                                                 cmbSL_Code);
                              //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                              //txtsub_Recei_from=Grid_rec_from[k];


                              txtBill_no = Grid_Bill_No;

                              txtBill_Type = Grid_Bill_type;

                              if (!Grid_Bill_date.equalsIgnoreCase("")) {
                                  sd = Grid_Bill_date.split("/");
                                  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                                  d = c.getTime();
                                  txtBill_Date = new Date(d.getTime());
                              }

                              txtAgree_No = Grid_Agree_No;
                              if (!Grid_Agree_date.equalsIgnoreCase("")) {
                                  sd = Grid_Agree_date.split("/");
                                  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                                  d = c.getTime();
                                  txtAgree_Date = new Date(d.getTime());
                              }

                              System.out.println("txtBill_no..." + txtBill_no);
                              System.out.println("txtBill_Type..." + txtBill_Type);
                              System.out.println("txtBill_Date..." + txtBill_Date);
                              System.out.println("txtAgree_No..." + txtAgree_No);
                              System.out.println("txtAgree_Date..." + txtAgree_Date);

                              txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                              txtParticular = Grid_particular[k];
                              System.out.println("amount");
                              System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);
                              // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                              System.out.println("Grid_particular[k] " +
                                                 Grid_particular[k]);

                              ps.setInt(1, cmbAcc_UnitCode);
                              ps.setInt(2, cmbOffice_code);
                              ps.setInt(3, txtCash_year);
                              ps.setInt(4, txtCash_Month_hid);
                              ps.setInt(5, txtJournalVou_No);
                              ps.setInt(6, SL_NO);
                              ps.setInt(7, txtAcc_HeadCode);
                              ps.setString(8, rad_sub_CR_DR);
                              ps.setInt(9, cmbSL_type);
                              ps.setInt(10, cmbSL_Code);
                              ps.setString(11, txtBill_no);
                              ps.setString(12, txtBill_Type);
                              ps.setString(13, txtAgree_No);
                              ps.setDate(14, txtAgree_Date);
                              ps.setDate(15, txtBill_Date);

                              ps.setString(16, txtCheque_DD);
                              ps.setString(17, txtCheque_DD_NO);
                              ps.setDate(18, txtCheque_DD_date);

                              ps.setDouble(19, txtsub_Amount);
                              ps.setString(20, txtParticular);
                              ps.setInt(21, txtCB_REF_NO);
                              ps.setDate(22, txtCB_REF_DATE);
                              ps.setString(23, update_user);
                              ps.setTimestamp(24, ts);
                              SL_NO++;
                              slNo=SL_NO;
                              int kk= ps.executeUpdate();
if(kk>0){
	count=count+0;
}else{
	count=count+1;	
}
                              txtAcc_HeadCode = 0;
                              rad_sub_CR_DR = "";
                              cmbSL_type = 0;
                              cmbSL_Code = 0;
                              txtBill_no = "";
                              txtBill_Type = "";
                              txtBill_Date = null;
                              txtCheque_DD = "";
                              txtCheque_DD_NO = "";
                              txtCheque_DD_date = null;
                              txtAgree_No = "";
                              txtAgree_Date = null;
                              txtsub_Amount = 0;
                              txtParticular = "";
                          }
                          
                          
                          
                          
                          //DEBIT SIDE
                          
                    	  String Grid_H_code_DR[] =
                                  request.getParameterValues("ACCOUNT_HEAD_code");
                              String Grid_CR_DR_type_DR[] =
                                  request.getParameterValues("CR_DR_type1");
                              String Grid_SL_type_DR[] =
                                  request.getParameterValues("SLtype1");
                              String Grid_SL_code_DR[] =
                                  request.getParameterValues("SL_code1");
                              // String Grid_rec_from[]=request.getParameterValues("rec_from");
                           

                              String Grid_sl_amt_DR[] =
                                  request.getParameterValues("sl_amt1");
                              String Grid_particular_DR[] =
                                  request.getParameterValues("particular1");
                          
                          
                          
                          for (int kj = 0; kj < Grid_H_code_DR.length; kj++) {
                              try {
                                  txtAcc_HeadCode1 = Integer.parseInt(Grid_H_code_DR[kj]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              rad_sub_CR_DR1 = Grid_CR_DR_type_DR[kj];

                              try {
                                  cmbSL_type1 = Integer.parseInt(Grid_SL_type_DR[kj]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              try {
                                  cmbSL_Code1 = Integer.parseInt(Grid_SL_code_DR[kj]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              System.out.println("Grid_H_code[k] " + Grid_H_code_DR[kj]);
                              System.out.println("Grid_CR_DR_type[k] " +
                                                 Grid_CR_DR_type[kj]);
                              System.out.println("Grid_SL_type[k]" +
                                                 Grid_SL_type[kj] + "u");
                              System.out.println("Grid_SL_code[k]" +
                                                 Grid_SL_code[kj] + "from here" +
                                                 cmbSL_Code1);
                              //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                              //txtsub_Recei_from=Grid_rec_from[k];


                              txtBill_no = Grid_Bill_No;

                              txtBill_Type = Grid_Bill_type;

                              if (!Grid_Bill_date.equalsIgnoreCase("")) {
                                  sd = Grid_Bill_date.split("/");
                                  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                                  d = c.getTime();
                                  txtBill_Date = new Date(d.getTime());
                              }

                              txtAgree_No = Grid_Agree_No;
                              if (!Grid_Agree_date.equalsIgnoreCase("")) {
                                  sd = Grid_Agree_date.split("/");
                                  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                                  d = c.getTime();
                                  txtAgree_Date = new Date(d.getTime());
                              }

                              System.out.println("txtBill_no..." + txtBill_no);
                              System.out.println("txtBill_Type..." + txtBill_Type);
                              System.out.println("txtBill_Date..." + txtBill_Date);
                              System.out.println("txtAgree_No..." + txtAgree_No);
                              System.out.println("txtAgree_Date..." + txtAgree_Date);

                              txtsub_Amount1 = Double.parseDouble(Grid_sl_amt_DR[kj]);
                              txtParticular1 = Grid_particular_DR[kj];
                              System.out.println("amount");
                              System.out.println("Grid_sl_amt[k] " + Grid_sl_amt_DR[kj]);
                              // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                              System.out.println("Grid_particular[k] " +
                                                 Grid_particular[kj]);

                              ps.setInt(1, cmbAcc_UnitCode);
                              ps.setInt(2, cmbOffice_code);
                              ps.setInt(3, txtCash_year);
                              ps.setInt(4, txtCash_Month_hid);
                              ps.setInt(5, txtJournalVou_No);
                              ps.setInt(6, SL_NO);
                              ps.setInt(7, txtAcc_HeadCode1);
                              ps.setString(8, rad_sub_CR_DR1);
                              ps.setInt(9, cmbSL_type1);
                              ps.setInt(10, cmbSL_Code1);
                              ps.setString(11, txtBill_no);
                              ps.setString(12, txtBill_Type);
                              ps.setString(13, txtAgree_No);
                              ps.setDate(14, txtAgree_Date);
                              ps.setDate(15, txtBill_Date);

                              ps.setString(16, txtCheque_DD);
                              ps.setString(17, txtCheque_DD_NO);
                              ps.setDate(18, txtCheque_DD_date);

                              ps.setDouble(19, txtsub_Amount1);
                              ps.setString(20, txtParticular1);
                              ps.setInt(21, txtCB_REF_NO);
                              ps.setDate(22, txtCB_REF_DATE);
                              ps.setString(23, update_user);
                              ps.setTimestamp(24, ts);
                              slNo++;
                              int kk= ps.executeUpdate();
if(kk>0){
	count=count+0;
}else{
	count=count+1;
}
                              txtAcc_HeadCode = 0;
                              rad_sub_CR_DR = "";
                              cmbSL_type = 0;
                              cmbSL_Code = 0;
                              txtBill_no = "";
                              txtBill_Type = "";
                              txtBill_Date = null;
                              txtCheque_DD = "";
                              txtCheque_DD_NO = "";
                              txtCheque_DD_date = null;
                              txtAgree_No = "";
                              txtAgree_Date = null;
                              txtsub_Amount = 0;
                              txtParticular = "";
                              
                          }
                          
                          
                          
                          
                       if(count == 0){   
                          ps.close();
                          System.out.println("b4 commit");
                          con.commit();
                          sendMessage(response,
                                      "The  Voucher Number '" + txtJournalVou_No +
                                      "' has been Created Successfully ", "ok");
                      }
                       else{
                    	     sendMessage(response,
                                     "Insertion Failed", "ok");
                       }
                }
            }
     
       
      
          
        catch(Exception e)
   {
	   e.printStackTrace();
   }
           
       }*/
    }
       
	public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
                                                 IOException {     
 
		
    	System.out.println("Welcome Get");
        String strCommand = "";
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        PreparedStatement ps = null;
        String xml = "";
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

        PrintWriter out=response.getWriter();
        try {

            strCommand = request.getParameter("hid_cmd");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
if (strCommand.equalsIgnoreCase("Add")) {
        	
        	System.out.println("THIS IS MINE");
        	
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            Calendar c;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtJournalVou_No = 0;
           
            int Total_TRN_Rec = 0;
           
            String txtCheque_NO = "", txtCB_REF_TYPE = "",txtBill_type = null;

            Date txtCrea_date = null, txtCheque_date = null;
			
			String txtBill_date=null,   ACCOUNT_HEAD_DESC=null;;
            String txtRemarks = "",particlular=null;

            int cmbMas_SL_type = 0,SL_type=0,SL_code=0,sl_amt=0,Schemeno=0,  sno=0,cmbMas_SL_Code = 0,txtBill_NO = 0,Schemename=0,CR_DR_type=0;
            String txtMode_of_creat = "M", txtCreat_By_Module = "LJV";
            double dep_rate = 0; 
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            int count=0;

            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            /*  try{txtCash_Acc_code=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            System.out.println("txtCash_Acc_code "+txtCash_Acc_code);*/

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            System.out.println("b4 getting month and year");
            try {
                txtCash_year = Integer.parseInt(sd[2]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_year " + txtCash_year);

            try {
                txtCash_Month_hid = Integer.parseInt(sd[1]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);

            int slNo=0;
         /*   try {
                txtJournalVou_No =
                        Integer.parseInt(request.getParameter("txtJournalVou_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }*/
           // System.out.println("txtJournalVou_No " + txtJournalVou_No);

            /*  try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+txtAmount);*/

            //txtCheque_NO=request.getParameter("txtCheque_NO");                //no need in bill type*****************
          //  System.out.println("txtCheque_NO " + txtCheque_NO);

            /*  if(!request.getParameter("txtCheque_date").equalsIgnoreCase(""))  // no need in bill type*****************
            {
            sd=request.getParameter("txtCheque_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            d=c.getTime();
            txtCheque_date=new Date(d.getTime());
            }*/
         //   System.out.println("txtCheque_date " + txtCheque_date);
            // changes here
            try {
                cmbMas_SL_type =
                        Integer.parseInt(request.getParameter("cmbMas_SL_type"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }

            try {
                cmbMas_SL_Code =
                        Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }


            System.out.println("cmbMas_SL and SL_CODE " + cmbMas_SL_type +
                               " " + cmbMas_SL_Code); //+" "+cmbMas_offid);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");
              //  int supplement_no=0;
                String No_TRN_Rec_cr[] = request.getParameterValues("sl_amt");
                String No_TRN_Rec_dr[] = request.getParameterValues("sl_amt1");
                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                int Total_TRN_cr=0,Total_TRN_dr=0;
                for(int c_cr=0;c_cr<No_TRN_Rec_cr.length;c_cr++)
                {
                	if(No_TRN_Rec_cr[c_cr]!="" && No_TRN_Rec_cr[c_cr]!="0"){
                		Total_TRN_cr=Total_TRN_Rec+1;
                	}
                	
                } for(int c_Dr=0;c_Dr<No_TRN_Rec_dr.length;c_Dr++)
                {
                	if(No_TRN_Rec_dr[c_Dr]!="" && No_TRN_Rec_dr[c_Dr]!="0"){
                		Total_TRN_dr=Total_TRN_dr+1;
                	}
                	
                }
                
                Total_TRN_Rec =Total_TRN_cr+Total_TRN_dr; //Integer.parseInt(No_TRN_Rec);
                System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
                cs =
//  con.prepareCall("{call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                cs.setInt(1, cmbAcc_UnitCode);
//                cs.setInt(2, cmbOffice_code);
//                cs.setInt(3, txtCash_year);
//                cs.setInt(4, txtCash_Month_hid);
//                cs.setInt(5, txtJournalVou_No);
//                cs.setDate(6, txtCrea_date);
//                // cs.setString(7,txtReceipt_type);
//                //  cs.setInt(8,txtCash_Acc_code);
//                cs.setInt(7, 11);
//                cs.setInt(8, cmbMas_SL_Code);
//                cs.setDouble(9, dep_rate);
//                cs.setString(10, txtCheque_NO);
//                cs.setDate(11, txtCheque_date);
//                cs.setString(12, txtCB_REF_TYPE);
//                // cs.setInt(13,txtCB_REF_NO);
//                // cs.setDate(14,txtCB_REF_DATE);
//                // cs.setDouble(19,txtAmount);
//                cs.setInt(13, Total_TRN_Rec);
//                cs.setString(14, txtRemarks);
//                cs.setString(15, txtMode_of_creat);
//                cs.setString(16, txtCreat_By_Module);
//                cs.setString(17, "insert");
//                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
//                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
//                cs.setString(19, update_user);
//                cs.setTimestamp(20, ts);
//              //  cs.setInt(21,supplement_no);
//                System.out.println("b4 exe ");
//                cs.execute();
                		 con.prepareCall("call FAS_JOURNAL_MASTER_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?,?,?::int,?,?)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, cmbOffice_code);
                cs.setInt(3, txtCash_year);
                cs.setInt(4, txtCash_Month_hid);
                cs.setInt(5, txtJournalVou_No);
                cs.setDate(6, txtCrea_date);
                // cs.setString(7,txtReceipt_type);
                //  cs.setInt(8,txtCash_Acc_code);
                cs.setInt(7, cmbMas_SL_type);
                cs.setInt(8, cmbMas_SL_Code);
                cs.setDouble(9, dep_rate);
                cs.setString(10, txtCheque_NO);
                cs.setDate(11, txtCheque_date);
                cs.setString(12, txtCB_REF_TYPE);
                // cs.setInt(13,txtCB_REF_NO);
                // cs.setDate(14,txtCB_REF_DATE);
                // cs.setDouble(19,txtAmount);
                cs.setInt(13, Total_TRN_Rec);
                cs.setString(14, txtRemarks);
                cs.setString(15, txtMode_of_creat);
                cs.setString(16, txtCreat_By_Module);
                cs.setString(17, "insert");
                cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                cs.registerOutParameter(18, java.sql.Types.INTEGER);
                cs.setNull(5, java.sql.Types.NUMERIC);
                cs.setNull(18, java.sql.Types.NUMERIC);
                cs.setString(19, update_user);
                cs.setTimestamp(20, ts);
                System.out.println("b4 exe ");
                cs.execute();
                txtJournalVou_No = cs.getBigDecimal(5).intValue();
                int errcode = cs.getInt(18);
                System.out.println("SQLCODE:::" + errcode);
                 System.out.println("result txtJournalVou_No:::" + txtJournalVou_No);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response,
                                "The  Voucher Number Creation Failed ", "ok");
                    xml = xml + "<flag>failure</flag>";
                }else{
                	
                	   String Grid_Bill_No =
                               request.getParameter("txtBill_date");
                           String Grid_Bill_date =
                               request.getParameter("txtBill_date");
                           System.out.println("Grid_Bill_date"+Grid_Bill_date);
                           String Grid_Bill_type =
                               request.getParameter("txtBill_type");

                           String Grid_Agree_No =
                               request.getParameter("txtAgree_No");
                           String Grid_Agree_date =
                               request.getParameter("txtAgree_Date");
                	
                	
                	/// CREDIT SIDE
                           
                      System.out.println("credit side   txtJournalVou_No  "+txtJournalVou_No);  
                           
                	  String Grid_H_code[] =
                              request.getParameterValues("H_code");
                          String Grid_CR_DR_type[] =
                              request.getParameterValues("CR_DR_type");
                          String Grid_SL_type[] =
                              request.getParameterValues("SLtype");	
                          String Grid_SL_code[] =null;
                             // request.getParameterValues("SL_code");
                          // String Grid_rec_from[]=request.getParameterValues("rec_from");
                       

                          String Grid_sl_amt[] =
                              request.getParameterValues("sl_amt");
                          String Grid_particular[] =
                              request.getParameterValues("particular");

                          String sql =
                              "insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                              "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                              "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                              "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
                              "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                              "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                          int SL_NO = 1, txtAcc_HeadCode = 0,txtAcc_HeadCode1=0, cmbSL_Code =
                              0,cmbSL_Code1=0, cmbSL_type = 0, cmbSL_type1=0,txtCB_REF_NO = 0;
                          Date txtBill_Date = null, txtAgree_Date =
                              null, txtCheque_DD_date = null, txtCB_REF_DATE = null;
                          double txtsub_Amount = 0,txtsub_Amount1=0;
                          String rad_sub_CR_DR = "",rad_sub_CR_DR1="", txtBill_no = "", txtBill_Type =
                              "", txtAgree_No = "", txtParticular = "",txtParticular1="";
                          String txtCheque_DD = "", txtCheque_DD_NO = "";

                          ps = con.prepareStatement(sql);
                          for (int k = 0; k < Grid_H_code.length; k++) {
                        	  System.out.println("'Grid_sl_amt[k] "+Grid_sl_amt[k]);
                        	  if(Grid_sl_amt[k]!="" && Grid_sl_amt[k] != "0"){
                              try {
                                  txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              System.out.println("step1");
                              rad_sub_CR_DR = Grid_CR_DR_type[k];

                              try {
                                  cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              System.out.println("step2");
                              try {
                                  cmbSL_Code = Integer.parseInt(request.getParameter("SL_code"+k));
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              System.out.println("step3"+cmbSL_Code);
                              System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
                              System.out.println("Grid_CR_DR_type[k] " +
                                                 Grid_CR_DR_type[k]);
                              System.out.println("Grid_SL_type[k]" +
                                                 Grid_SL_type[k] + "u");
                              System.out.println("Grid_SL_code[k]" +
                                                "from here" +
                                                 cmbSL_Code);
                              //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                              //txtsub_Recei_from=Grid_rec_from[k];


                              txtBill_no = Grid_Bill_No;

                              txtBill_Type = Grid_Bill_type;

                              if (!Grid_Bill_date.equalsIgnoreCase("")) {
                                  sd = Grid_Bill_date.split("/");
                                  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                                  d = c.getTime();
                                  txtBill_Date = new Date(d.getTime());
                              }

                              txtAgree_No = Grid_Agree_No;
                              if (!Grid_Agree_date.equalsIgnoreCase("")) {
                                  sd = Grid_Agree_date.split("/");
                                  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                                  d = c.getTime();
                                  txtAgree_Date = new Date(d.getTime());
                              }

                              System.out.println("txtBill_no..." + txtBill_no);
                              System.out.println("txtBill_Type..." + txtBill_Type);
                              System.out.println("txtBill_Date..." + txtBill_Date);
                              System.out.println("txtAgree_No..." + txtAgree_No);
                              System.out.println("txtAgree_Date..." + txtAgree_Date);

                              txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                              txtParticular = Grid_particular[k];
                              System.out.println("amount");
                              System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);
                              // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                              System.out.println("Grid_particular[k] " +
                                                 Grid_particular[k]);

                              ps.setInt(1, cmbAcc_UnitCode);
                              ps.setInt(2, cmbOffice_code);
                              ps.setInt(3, txtCash_year);
                              ps.setInt(4, txtCash_Month_hid);
                              ps.setInt(5, txtJournalVou_No);
                              ps.setInt(6, SL_NO);
                              ps.setInt(7, txtAcc_HeadCode);
                              ps.setString(8, "CR");
                              ps.setInt(9, cmbSL_type);
                              ps.setInt(10, cmbSL_Code);
                              ps.setString(11, txtBill_no);
                              ps.setString(12, txtBill_Type);
                              ps.setString(13, txtAgree_No);
                              ps.setDate(14, txtAgree_Date);
                              ps.setDate(15, txtBill_Date);

                              ps.setString(16, txtCheque_DD);
                              ps.setString(17, txtCheque_DD_NO);
                              ps.setDate(18, txtCheque_DD_date);

                              ps.setDouble(19, txtsub_Amount);
                              ps.setString(20, txtParticular);
                              ps.setInt(21, txtCB_REF_NO);
                              ps.setDate(22, txtCB_REF_DATE);
                              ps.setString(23, update_user);
                              ps.setTimestamp(24, ts);
                              SL_NO++;
                              slNo=SL_NO;
                              int kk= ps.executeUpdate();
if(kk>0){
	count=count+0;
}else{
	count=count+1;	
}
                              txtAcc_HeadCode = 0;
                              rad_sub_CR_DR = "";
                              cmbSL_type = 0;
                              cmbSL_Code = 0;
                           /*   txtBill_no = "";
                              txtBill_Type = "";
                              txtBill_Date = null;
                              txtCheque_DD = "";
                              txtCheque_DD_NO = "";
                              txtCheque_DD_date = null;
                              txtAgree_No = "";
                              txtAgree_Date = null;*/
                              txtsub_Amount = 0;
                              txtParticular = "";
                          }
                }  
                          
                          
                          
                          //DEBIT SIDE
                          
                          System.out.println("Debit side   txtJournalVou_No  "+txtJournalVou_No);  
                          
                    	  String Grid_H_code_DR[] =
                                  request.getParameterValues("ACCOUNT_HEAD_code");
                              String Grid_CR_DR_type_DR[] =
                                  request.getParameterValues("CR_DR_type1");
                              String Grid_SL_type_DR[] =null;
                                 // request.getParameterValues("sel_debtype");
                          
                              // String Grid_rec_from[]=request.getParameterValues("rec_from");
                           

                              String Grid_sl_amt_DR[] =
                                  request.getParameterValues("sl_amt1");
                              String Grid_particular_DR[] =
                                  request.getParameterValues("particular1");
                          
                         
                          
                          for (int kj = 0; kj < Grid_H_code_DR.length; kj++) {
                        	  if(Grid_sl_amt_DR[kj]!="" && Grid_sl_amt_DR[kj] != "0"){
                              try {
                                  txtAcc_HeadCode1 = Integer.parseInt(Grid_H_code_DR[kj]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              rad_sub_CR_DR1 = Grid_CR_DR_type_DR[kj];

                              try {
                                  cmbSL_type1 = Integer.parseInt(Grid_SL_type_DR[kj]);
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              try {
                            	  
                                  cmbSL_Code1 =    Integer.parseInt(request.getParameter("SLtypecode1"+kj));
                              } catch (Exception e) {
                                  System.out.println("exception in trans " + e);
                              }
                              System.out.println("Grid_H_code[k] " + Grid_H_code_DR[kj]);
                              System.out.println("Grid_CR_DR_type[k] " +
                                                 Grid_CR_DR_type[kj]);
                              System.out.println("Grid_SL_type[k]" +
                                                 Grid_SL_type[kj] + "u");
                              System.out.println("Grid_SL_code[k]" +
                                               "from here" +
                                                 cmbSL_Code1);
                              //System.out.println(cmbSL_type.equalsIgnoreCase("7"));
                              //txtsub_Recei_from=Grid_rec_from[k];


                              txtBill_no = Grid_Bill_No;

                              txtBill_Type = Grid_Bill_type;

                              if (!Grid_Bill_date.equalsIgnoreCase("")) {
                                  sd = Grid_Bill_date.split("/");
                                  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                                  d = c.getTime();
                                  txtBill_Date = new Date(d.getTime());
                              }

                              txtAgree_No = Grid_Agree_No;
                              if (!Grid_Agree_date.equalsIgnoreCase("")) {
                                  sd = Grid_Agree_date.split("/");
                                  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                                  d = c.getTime();
                                  txtAgree_Date = new Date(d.getTime());
                              }

                              System.out.println("txtBill_no..." + txtBill_no);
                              System.out.println("txtBill_Type..." + txtBill_Type);
                              System.out.println("txtBill_Date..." + txtBill_Date);
                              System.out.println("txtAgree_No..." + txtAgree_No);
                              System.out.println("txtAgree_Date..." + txtAgree_Date);

                              txtsub_Amount1 = Double.parseDouble(Grid_sl_amt_DR[kj]);
                              txtParticular1 = Grid_particular_DR[kj];
                              System.out.println("amount");
                              System.out.println("Grid_sl_amt[k] " + Grid_sl_amt_DR[kj]);
                              // System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);
                              System.out.println("Grid_particular[k] " +
                                                 Grid_particular[kj]);

                              ps.setInt(1, cmbAcc_UnitCode);
                              ps.setInt(2, cmbOffice_code);
                              ps.setInt(3, txtCash_year);
                              ps.setInt(4, txtCash_Month_hid);
                              ps.setInt(5, txtJournalVou_No);
                              ps.setInt(6, slNo);System.out.println("SL_NO  slNo  "+slNo);
                              ps.setInt(7, txtAcc_HeadCode1);
                              ps.setString(8, "DR");
                              ps.setInt(9, Integer.parseInt(request.getParameter("sel_debtype"+kj)));
                              ps.setInt(10, cmbSL_Code1);
                              ps.setString(11, txtBill_no);
                              ps.setString(12, txtBill_Type);
                              ps.setString(13, txtAgree_No);
                              ps.setDate(14, txtAgree_Date);
                              ps.setDate(15, txtBill_Date);

                              ps.setString(16, txtCheque_DD);
                              ps.setString(17, txtCheque_DD_NO);
                              ps.setDate(18, txtCheque_DD_date);

                              ps.setDouble(19, txtsub_Amount1);
                              ps.setString(20, txtParticular1);
                              ps.setInt(21, txtCB_REF_NO);
                              ps.setDate(22, txtCB_REF_DATE);
                              ps.setString(23, update_user);
                              ps.setTimestamp(24, ts);
                              slNo++;
                              int kk= ps.executeUpdate();
if(kk>0){
	count=count+0;
}else{
	count=count+1;
}
                              txtAcc_HeadCode1 = 0;
                              rad_sub_CR_DR1 = "";
                              cmbSL_type1 = 0;
                              cmbSL_Code1 = 0;
                          /*    txtBill_no = "";
                              txtBill_Type = "";
                              txtBill_Date = null;
                              txtCheque_DD = "";
                              txtCheque_DD_NO = "";
                              txtCheque_DD_date = null;
                              txtAgree_No = "";
                              txtAgree_Date = null;*/
                              txtsub_Amount1 = 0;
                              txtParticular1 = "";
                          }  
                          }
                          
                          
                          
                          
                       if(count == 0){   
                          ps.close();
                          System.out.println("b4 commit");
                          con.commit();
                          sendMessage(response,
                                      "The  Voucher Number '" + txtJournalVou_No +
                                      "' has been Created Successfully ", "ok");
                      }
                       else{
                    	     sendMessage(response,
                                     "Insertion Failed", "ok");
                       }
                }
            }
     
       
      
          
        catch(Exception e)
   {
	   e.printStackTrace();
   }
           
       }
	}
            
            
            
                   
            
            
     

 

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (Exception e) {
            System.out.println("error in messenger" + e);
        }
    }
}

              
                
                