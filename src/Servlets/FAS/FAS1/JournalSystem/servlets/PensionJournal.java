package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.*;
import javax.servlet.http.*;

import com.sun.mail.iap.ParsingException;

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class PensionJournal extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7612196366306701598L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Variables Declaration
         */
    	PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps = null,psss=null,ps_update=null;
        CallableStatement cs=null;
        ResultSet rs = null,rsss=null;
        String xml = "";
        int cmbOffice_code=0,listtype=0,grouptype=0,cmbAcc_UnitCode=0;
        String command=request.getParameter("command");
        System.out.println("command::::"+command);
        
        /**
         * Database Connection
         */
        try {
            LoadDriver load = new LoadDriver();
            connection = load.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        int chk_cno=0;
        /**
         * Session Checking
         */
        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        int count = 0;
        //int unitCode=Integer.parseInt(request.getParameter("unitCode"));
        if(command.equalsIgnoreCase("listtype"))
        {
        	xml = "<response>"; 
			xml=xml+"<command>listtype</command>";

			
			try {
				ps = connection
						.prepareStatement("select CHK_LIST_ID,CHK_LIST_DESC from HRM_PEN_PAY_CHK_LIST_MASTER order by CHK_LIST_ID");
				
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<ListID>" + rs.getInt("CHK_LIST_ID")
							  + "</ListID>";
					xml = xml + "<ListDesc>" + rs.getString("CHK_LIST_DESC")
					          + "</ListDesc>";
					count++;
				}
				if(count>0)
				{
				xml = xml + "<flag3>success</flag3>";
				xml=xml+"<count>"+count+"</count>";
				}
				else
				{
					xml = xml + "<flag3>failure</flag3>";
				}
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
			xml=xml+"</response>";
			out.println(xml);
			System.out.println(xml);
        }else if(command.equalsIgnoreCase("listgroup"))
        {
        	
        	int officecode=Integer.parseInt(request.getParameter("cmbOffice_code"));
        	xml = "<response>"; 
			xml=xml+"<command>listgroup</command>";

			
			try {
				String sql="select GROUP_ID,GROUP_DESC from HRM_PEN_PAY_CHKLIST_GROUP where GROUP_OFFICE_ID="+officecode+" order by GROUP_ID";
				System.out.println("sql::::"+sql);
				ps = connection.prepareStatement(sql);
				
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<ListID>" + rs.getInt("GROUP_ID")
							  + "</ListID>";
					xml = xml + "<ListDesc>" + rs.getString("GROUP_DESC")
					          + "</ListDesc>";
					count++;
				}
				if(count>0)
				{
				xml = xml + "<flag3>Success1</flag3>";
				xml=xml+"<count>"+count+"</count>";
				}
				else
				{
					xml = xml + "<flag3>failure</flag3>";
				}
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
			xml=xml+"</response>";
			out.println(xml);
			System.out.println(xml);
        }
        else if(command.equalsIgnoreCase("loadpension"))
        {
        	int inc=0,lenplus=0;
        	xml = "<response>"; 
			xml=xml+"<command>loadpension</command>";
			try {
                cmbOffice_code =Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            int txtCB_Year =Integer.parseInt(request.getParameter("txtCB_Year"));
            int txtCB_Month =Integer.parseInt(request.getParameter("txtCB_Month"));
            listtype =Integer.parseInt(request.getParameter("listtype"));
            grouptype =Integer.parseInt(request.getParameter("grouptype"));
            String penfamily =request.getParameter("penfamily");
            
			//double crtotal=0,drtotal=0,drtot=0,crtot=0;
            int crtotal=0,drtotal=0,drtot=0,crtot=0;
			
			try {
				
				String ss="select case when Cr_Dr_Indicator='CR' then tt_amt else 0 end as CRAmount,\n"+
						" case when Cr_Dr_Indicator='DR' then tt_amt else 0 end as DRAmount,\n"+
						" decode(FAS_JVR_POSTED,'Y',FAS_JVR_POSTED,'X') as FAS_JVR_POSTED, \n"+
						" Code,Account_Head_Code,headDesc,Cr_Dr_Indicator from (Select Case When Code='MONTHLY_PENSION_AMT' Then (Select Sum(Monthly_Pension_Amt)\n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='DP_AMT' Then (Select Sum(DP_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='DA_AMT' Then (Select Sum(DA_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='MA_AMT' Then (Select Sum(MA_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='OTHER_EARNINGS' Then (Select Sum(OTHER_EARNINGS) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='HF_AMT' Then (Select Sum(HF_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='PFSF_AMT' Then (Select Sum(PFSF_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='IT_AMT' Then (Select Sum(IT_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='ADHOC_AMT' Then (Select Sum(ADHOC_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='OTHER_DEDUCTIONS' Then (Select Sum(OTHER_DEDUCTIONS) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='FESTIVAL_AMT' Then (Select Sum(FESTIVAL_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='PAY_REV_AMT' Then (Select Sum(PAY_REV_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='DA_ARR_AMT' Then (Select Sum(DA_ARR_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='DCRG_ARR_AMT' Then (Select Sum(DCRG_ARR_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='COMM_ARR_AMT' Then (Select Sum(COMM_ARR_AMT) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='NET_PAY' Then (Select Sum(NET_PAY) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " When Code='ADDL_PENSION' Then (Select Sum(ADDL_PENSION) \n" + 
		                " From Hrm_Pen_Pay_Final_Abstract Where Office_Id       ="+cmbOffice_code+" And Process_Month     ="+txtCB_Month+" And Process_Year      ="+txtCB_Year+" \n" + 
		                " And Chk_List_Type_Id  ="+listtype+" And Chk_List_Grp_Id   ="+grouptype+" And Pen_Familypen_Flag='"+penfamily+"')\n" + 
		                " end as tt_amt,\n" + 
		                " (SELECT distinct FAS_JVR_POSTED FROM Hrm_Pen_Pay_Final_Abstract "+
		                    "    WHERE Office_Id       ="+cmbOffice_code+"  AND Process_Month     ="+txtCB_Month+
		                    "       AND Process_Year      ="+txtCB_Year+"      AND Chk_List_Type_Id  ="+listtype+"    AND Chk_List_Grp_Id   ="   +grouptype+
		                    "       AND Pen_Familypen_Flag='"+penfamily+"') as FAS_JVR_POSTED, "   +
		                " Code,Account_Head_Code,decode(headDesc,null,'0',headDesc) as headDesc,Cr_Dr_Indicator from\n" + 
		                " (Select Col_To_Be_Journalised as code,\n" + 
		                "  A.Account_Head_Code,\n" + 
		                "  (select S.Account_Head_Desc from Com_Mst_Account_Heads s where S.Account_Head_Code=a.Account_Head_Code)as headDesc,\n" + 
		                "  Cr_Dr_Indicator\n" + 
		                " FROM FAS_INTEGRATION_JOURNAL_HEADS a\n" + 
		                " WHERE STATUS='L'\n" + 
		                " And Type    ='"+penfamily+"'\n" + 
		                "Order By Order_No)) where decode(FAS_JVR_POSTED,'Y',FAS_JVR_POSTED,'X')  not like 'Y'";
		              
                System.out.println("ss:::"+ss);
				ps = connection.prepareStatement(ss);
				
				rs = ps.executeQuery();
				while (rs.next()) {
					
					xml = xml + "<remarks>"+ rs.getString("Code")+ "</remarks>";			
					xml = xml + "<hcode>"+ rs.getString("Account_Head_Code")+"-"+rs.getString("headDesc")+"</hcode>";
					xml = xml + "<cramount>"+ rs.getString("CRAmount")+ "</cramount>";
					xml = xml + "<dramount>"+ rs.getString("DRAmount")+"</dramount>";
					xml = xml + "<Cr_Dr_Indicator>"+ rs.getString("Cr_Dr_Indicator")+"</Cr_Dr_Indicator>";
					
					String crvalues=rs.getString("CRAmount");
					try{
				//	crtot=Double.parseDouble(crvalues);
					crtot=Integer.parseInt(crvalues);
					}
					catch(Exception ee){
						crtot=0;
					}
					crtotal=crtot+crtotal;
					
					try{
					//drtot = Double.parseDouble(rs.getString("DRAmount"));
					drtot = Integer.parseInt(rs.getString("DRAmount"));
					}
					catch(Exception e)
					{
						drtot=0;
					}
					drtotal=drtot+drtotal;			
					
					count++;
				}
				if(count>0)
				{
					System.out.println("count:::"+count);
				//	xml=xml+"<inc>"+count+"</inc>";
				xml = xml+"<flag1>Success</flag1>";
				xml=xml+"<count>"+count+"</count>";
				}
				else
				{
					xml = xml + "<flag1>nodata</flag1>";
				}
			} catch (Exception e) {
				xml = xml + "<flag1>failure</flag1>";
				e.printStackTrace();
			}
			
			try
            {
            ps=connection.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES WHERE display_restricted is null order by SUB_LEDGER_TYPE_DESC");
            rs=ps.executeQuery();
                while(rs.next())
                {
                xml=xml+"<SUB_LEDGER_TYPE_CODE>"+rs.getString("SUB_LEDGER_TYPE_CODE")+"</SUB_LEDGER_TYPE_CODE>";
                xml=xml+"<SUB_LEDGER_TYPE_DESC>"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</SUB_LEDGER_TYPE_DESC>";
                }
            }
            catch(Exception e)
            {
            System.out.println("Exception in Reason combo..."+e);
            }
             
			
			
			
			
			xml = xml + "<crtotalamount>"+ crtotal+"</crtotalamount>";
			xml = xml + "<drtotalamount>"+ drtotal+"</drtotalamount>";
			//System.out.println("Crtotal"+crtotal);
			//System.out.println("Drtotal"+drtotal);
			xml=xml+"</response>";
			out.println(xml);
			System.out.println(xml);
        }
        else if(command.equalsIgnoreCase("Add"))
        {
        	
        	try
            {
            	cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            	System.out.println("cmbAcc_UnitCode::::"+cmbAcc_UnitCode);
            }
            catch(Exception e)
            {
            e.printStackTrace();	
            }
            try
            {
            	cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            	System.out.println("ofiicecode::::"+cmbOffice_code);
            }
            catch(Exception e)
            {
            e.printStackTrace();	
            }
            try
            {
            	listtype=Integer.parseInt(request.getParameter("listtype"));
            	System.out.println("listtype::::"+listtype);
            }catch(Exception e)
            {
            	e.printStackTrace();
            }
            try
            {
             grouptype=Integer.parseInt(request.getParameter("grouptype"));  
             System.out.println("grouptype::::"+grouptype);
            }catch(Exception e)
            {
            	e.printStackTrace();
            }
            int txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
            int txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
            String penfamily=request.getParameter("penfamily");
            System.out.println("txtCB_Year::::"+txtCB_Year);
            System.out.println("txtCB_Month::::"+txtCB_Month);
            System.out.println("penfamily::::"+penfamily);
        	
            String hid_chklist=request.getParameter("hid_chklist");
            String hid_Grplist=request.getParameter("hid_Grplist");
            String pen_Non="";
            if(penfamily.equalsIgnoreCase("P")){
            	pen_Non="Pensioner";
            }else{
            	pen_Non="Family Pensioner";
            }
        	 int Total_TRN_Rec=0,txtJournalVou_No=0,txtCash_year=0,txtCash_Month_hid=0;
        	 int cmbMas_SL_type=0,cmbMas_SL_Code=0,dep_rate=0;
        	 
        	 String txtCheque_NO=null,txtRemarks=null,txtCB_REF_TYPE=null;
        	 String txtMode_of_creat="A";
        	 String txtCreat_By_Module="GJV";
        	 txtRemarks="Auto Created Pension Journal for "+hid_chklist+" - "+hid_Grplist+" - For "+pen_Non+" Bill";
        	 Date txtCrea_date=null,txtCheque_date=null,cb_date=null;
        	  String update_user=(String)session.getAttribute("UserId");
              long l=System.currentTimeMillis();
              Timestamp ts=new Timestamp(l);
        	try 
            {   
                connection.clearWarnings();
                connection.setAutoCommit(false);
                System.out.println("inside proc");
            	
 
            	  String[] sd=request.getParameter("txtCrea_date").split("/");
                 Calendar c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                  java.util.Date d=c.getTime();
                  txtCrea_date=new Date(d.getTime());
                  System.out.println("txtCrea_date "+txtCrea_date);
                  
                  System.out.println("length:::"+sd[2].length());
                //  if(sd[2].length()>3){
                  
                  System.out.println("b4 getting month and year");
                  try{txtCash_year=Integer.parseInt(sd[2]);}
                  catch(Exception e){System.out.println("exception"+e );}
                  System.out.println("txtCash_year "+txtCash_year);
                  
                  try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                  catch(Exception e){System.out.println("exception"+e );}
                  System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
                              
                              
                 System.out.println("txtCash_year::"+txtCash_year);
            	
                 String slno[] = request.getParameterValues("slno");
                Total_TRN_Rec=slno.length;
                System.out.println(Total_TRN_Rec+" Total_TRN_Rec");
//                cs=connection.prepareCall("{call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
//                    cs.setInt(1,cmbAcc_UnitCode);
//                    cs.setInt(2,cmbOffice_code);
//                    cs.setInt(3,txtCash_year);
//                    cs.setInt(4,txtCash_Month_hid);
//                    cs.setInt(5,txtJournalVou_No);
//                    cs.setDate(6,txtCrea_date);
//                    cmbMas_SL_type=110;
//                    cs.setInt(7,cmbMas_SL_type);
//                  //  cmbMas_SL_type=18;
//                    cs.setInt(8,cmbMas_SL_Code);
//                    cs.setDouble(9,dep_rate);
//                    cs.setString(10,txtCheque_NO);
//                    cs.setDate(11,txtCheque_date);
//                    cs.setString(12,txtCB_REF_TYPE);
//                     cs.setInt(13,Total_TRN_Rec);
//                     cs.setString(14,txtRemarks);
//                     cs.setString(15,txtMode_of_creat);
//                     cs.setString(16,txtCreat_By_Module);
//                     cs.setString(17,"insert");                     
//                     cs.registerOutParameter(5,java.sql.Types.NUMERIC);
//                     cs.registerOutParameter(18,java.sql.Types.NUMERIC);  
//                     cs.setString(19,update_user);
//                     cs.setTimestamp(20,ts);
                
                cs= connection.prepareCall("call FAS_JOURNAL_MASTER_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?,?,?::int,?,?)");
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
                
                
                    System.out.println("b4 exe ");
                    try{
                cs.execute();
                    }catch (Exception e) {
                    	System.out.println("Error in procedure ... ");
						e.printStackTrace();
					}
               // txtJournalVou_No=cs.getInt(5);
                    txtJournalVou_No = cs.getBigDecimal(5).intValue();
                //int errcode=cs.getInt(18);
                    int errcode = cs.getInt(18);
                System.out.println("txtJournalVou_No >. "+txtJournalVou_No+"/n  SQLCODE:::"+errcode);
                if(errcode!=0) 
                {         
                  System.out.println("redirect");
                  sendMessage(response,"Pension Journal Creation Failed ","ok");
                  xml=xml+"<flag>failure</flag>";                          
                }
                else
                {
                	 String serialno[] = request.getParameterValues("slno");
                    String Grid_particular[]=request.getParameterValues("remarks");
                    String Grid_sl_amt[]=request.getParameterValues("cramount");
                    String head_code[]=request.getParameterValues("head_code");
                    String Grid_CR_DR_type[]=request.getParameterValues("dramount");
                    String cr_Dr[]=request.getParameterValues("cr_Dr");
                    
                    
                    
                    String sql="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                    "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                    "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                    "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
                    "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,ADJ_AGAINST_YEAR,ADJ_AGAINST_MONTH ) "+
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                    
                    int SL_NO=0,txtAcc_HeadCode=0,cmbSL_Code=0,cmbSL_type=0,txtCB_REF_NO=0,adjYear=0,adjMonth=0,comCbNo=0,last=0;
                    Date txtBill_Date=null,txtAgree_Date=null,txtCheque_DD_date=null,txtCB_REF_DATE=null,comCbDate=null;
                    double txtsub_Amount=0,txtCrDr_Amt=0;                                  
                    String rad_sub_CR_DR="",txtBill_no="",txtBill_Type="",txtAgree_No="",txtParticular="";
                    String txtCheque_DD="",txtCheque_DD_NO="";  
                    int seq=1;
                          ps=connection.prepareStatement(sql);
                          for(int k=0;k<serialno.length;k++) 
                          {
                        	  if(Grid_sl_amt[k].equalsIgnoreCase("null"))
                            	  txtsub_Amount=0;
                              else 
                            	  txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);
                        	  
                        	  if(Grid_CR_DR_type[k].equalsIgnoreCase("null"))
                        		  txtCrDr_Amt=0;
                              else 
                            	  txtCrDr_Amt=Double.parseDouble(Grid_CR_DR_type[k]);
                        	  if(txtsub_Amount==0  &&  txtCrDr_Amt==0)
                        	  {
                        		  System.out.println("IF ELSE PART RETURN");
                        	  }else{
                        		
                              try{
                            	  txtAcc_HeadCode=Integer.parseInt(head_code[k]);
                            	  }catch(Exception e){System.out.println("exception in trans "+e);}
                              rad_sub_CR_DR=Grid_CR_DR_type[k];
                            //  SL_NO=Integer.parseInt(serialno[k]);               
                         /*   //  System.out.println("Grid_sl_amt[k] "+Grid_sl_amt[k]);
                              if(Grid_sl_amt[k].equalsIgnoreCase("null"))
                            	  txtsub_Amount=0;
                              else 
                            	  txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);*/
                             
                              txtParticular=Grid_particular[k];
                              ps.setInt(1,cmbAcc_UnitCode);
                              ps.setInt(2,cmbOffice_code);
                              ps.setInt(3,txtCash_year);
                              ps.setInt(4,txtCash_Month_hid);
                              ps.setInt(5,txtJournalVou_No);
                              ps.setInt(6,seq);
                              ps.setInt(7,txtAcc_HeadCode);	System.out.println("txtAcc_HeadCode >> "+txtAcc_HeadCode);  
                              ps.setString(8, cr_Dr[k]);
                            //  ps.setString(8,rad_sub_CR_DR);
                              ps.setInt(9,cmbSL_type);
                              ps.setInt(10,cmbSL_Code);
                              ps.setString(11,txtBill_no);
                              ps.setString(12,txtBill_Type);
                              ps.setString(13,txtAgree_No);
                              ps.setDate(14,txtAgree_Date);
                              ps.setDate(15,txtBill_Date);
                              
                              ps.setString(16,txtCheque_DD);
                              ps.setString(17,txtCheque_DD_NO); 
                              ps.setDate(18,txtCheque_DD_date);
                             if(cr_Dr[k].equalsIgnoreCase("CR")){
                              ps.setDouble(19,txtsub_Amount);}
                             else if(cr_Dr[k].equalsIgnoreCase("DR")){
                            	 ps.setDouble(19,txtCrDr_Amt);
                             }
                              ps.setString(20,txtParticular);
                              ps.setInt(21,comCbNo);
                              ps.setDate(22,comCbDate);
                              ps.setString(23,update_user);
                              ps.setTimestamp(24,ts);
                              ps.setInt(25,adjYear);
                              ps.setInt(26,adjMonth);
                            try{
                            last=  ps.executeUpdate(); 
                            System.out.println("first last value >>>> "+last);
                            }catch (Exception e) {
								System.out.println("errot in here  :: ");
								e.printStackTrace();
							}
                            
                          }
                        	  
                              txtAcc_HeadCode=0;
                              rad_sub_CR_DR="";
                              cmbSL_type=0;
                              cmbSL_Code=0;
                              txtCheque_DD="";
                              txtCheque_DD_NO="";
                              txtCheque_DD_date=null;
                              txtAgree_No="";
                              txtAgree_Date=null;
                             txtsub_Amount=0;
                              txtParticular="";
                              adjYear=0;
                              adjMonth=0;
                              seq++;
                          }
                            
                        
                          System.out.println("last >>      "+last); 
                          if(last!=0){
						String qry = "update HRM_PEN_PAY_FINAL_ABSTRACT"
								+ " set FAS_JVR_POSTED='Y', " +
										" FAS_JVR_NO=? ," +
										" FAS_JVR_DATE=?, " +
									" FAS_JVR_POSTED_DATE=?, FAS_JVR_POSTED_BY=? "+
								 "  WHERE Office_Id       = ?    AND Process_Month     =?  AND Process_Year      =? "
								+ " AND Chk_List_Type_Id  =?         AND Chk_List_Grp_Id   =?         AND Pen_Familypen_Flag=?";
						System.out.println("qry" + qry);
                        	 try{
							 ps_update = connection
									.prepareStatement(qry);
                        	
                        		// ps_update.setString(1,"Y");
                        		 ps_update.setInt(1,txtJournalVou_No);
                        		 ps_update.setDate(2,txtCrea_date);
                        		 ps_update.setTimestamp(3,ts);
                         		 ps_update.setString(4,update_user);
                         		 ps_update.setInt(5,cmbOffice_code);
                         		 ps_update.setInt(6,txtCB_Month);
                         		 ps_update.setInt(7,txtCash_year);
                         		 ps_update.setInt(8,listtype);
                         		 ps_update.setInt(9,grouptype);
                        		 ps_update.setString(10,penfamily);
                        		 System.out.println("test here it cum"+connection);
                        		  chk_cno=ps_update.executeUpdate();
                        		 System.out.println("Check "+chk_cno);
                        	 }catch (Exception e) {
                        		 System.out.println("test here ");
						e.printStackTrace();
							}
                        	
                          }else{
                        	  sendMessage(response,"Error ","ok");
                        	  connection.rollback();
                          }
                          
                          
                          System.out.println("test Check update"+chk_cno);
                          if(chk_cno>0){
                              sendMessage(response,"The Pension Voucher Number '"+txtJournalVou_No+"' has been Created Successfully ","ok"); 
                              connection.commit();
                     	 }else {
                 			 sendMessage(response,"Not Updated  ","ok");
                        	  connection.rollback();
							}
                          ps.close();
                          //connection.close();
                }
               
            }
            
            catch(Exception e) 
            {
                try{connection.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                sendMessage(response,"The Pension Voucher Number Creation Failed ","ok");
                System.out.println("Exception occur due to "+e);
            }
            finally
            {
                System.out.println("done");
                try{connection.setAutoCommit(true);  }catch(SQLException sqle){}
            }
        	
        	
        }else if(command.equalsIgnoreCase("ValidateHead")){
        	xml="<response>";
        	xml+="<command>ValidateHead</command>";
        	int count_head=0;
        	int headCode=Integer.parseInt(request.getParameter("Head_Code"));
        	try{
        		String headQry="select count(*) as cno from com_mst_account_heads  where account_head_code=  ?";
    PreparedStatement ps_count=connection.prepareStatement(headQry);
    ps_count.setInt(1, headCode);
    ResultSet rs_count=ps_count.executeQuery();
    while(rs_count.next()){
    	count_head=rs_count.getInt("cno");
    	xml+="<head>"+count_head+"</head>";
    }
    if(count_head==0)
    	xml+="<flag>failure</flag>";
    else
    	xml+="<flag>success</flag>";
        	}catch (Exception e) {
				e.printStackTrace();
			}
        	xml+="</response>";
        	out.println(xml);
        	System.out.println("xml >>> "+xml);
        	out.close();
        }
        		

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
