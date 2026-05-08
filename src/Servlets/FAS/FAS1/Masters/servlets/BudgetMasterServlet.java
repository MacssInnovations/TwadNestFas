package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BudgetMasterServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ResultSet rss = null;
        ResultSet res = null;
        PreparedStatement pss = null;
        ResultSet rs1 = null,results=null;
        PreparedStatement ps1 = null,ps2=null;


        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
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

        /*int stroffcode=0;
        int stroffnameren=0;
        int strfirmcode=0;
        String strfirmname="";
        String straddr="";
        String straddr1="";
        String straddr2="";
        String strphone="";
        String strfax="";
        String stremail="";*/

        /*String strDateReg="";
        String strDateLast="";
            int b=1;

        String FirmsAliasId="";
        int Pincode=0;
        Double ss=0.0;*/
        String xml = "";
        String strCommand = "";
        String cmbAcc_UnitCode = "";
        String cmbOffice_code = "";
        String cmbFinancialYear = "";
        String txtaccountheadcode = "";
        String txtaccountheadname = "";
        String txtpreviousyear = "";
        String txtcurrentyearbudget = "";
        String txtcurrentyearrevised = "";
        String txtnextyearestimate = "";
        String txtreferno = "";
        String txtreferdate = "";
        String txtRemarks = "";
        String txtpreviousyearrevised = "";
        String txtbudget_alloted = "";
        String txtbudget_spent = "";
        String typallocation="";
        int acc_unit_code = 0;
        int office_code = 0;
        double previous_year = 0;
        double previous_year_revised = 0;
        double current_year_budget = 0;
        double current_year_revised = 0;
        double next_year_estimate = 0;
        double budget_spent = 0;
        double budget_alloted = 0;
        int from_code=0,to_code=0;
        String userid = (String)session.getAttribute("UserId");
        System.out.println("User Id is:" + userid);


        try {

            strCommand = request.getParameter("Command");
            System.out.println("Command....." + strCommand);

        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }
        //Getting values from the Form/////

        cmbAcc_UnitCode = request.getParameter("cmbAccounting_Unit_Id");
        cmbOffice_code = request.getParameter("cmbOffice_Code");
        cmbFinancialYear = request.getParameter("cmbFinancial_Year");
        txtaccountheadcode = request.getParameter("txtAcc_Head_code");
        txtpreviousyear = request.getParameter("txtPreivous_Year");
        txtpreviousyearrevised =
                request.getParameter("txtPrevious_Year_Revised");
        txtcurrentyearbudget = request.getParameter("txtCurrent_Year_Budget");
        txtcurrentyearrevised =
                request.getParameter("txtCurrent_Year_Revised");
        txtnextyearestimate = request.getParameter("txtNext_Year_Estimate");
        txtreferno = request.getParameter("txtRefer_No");
        txtreferdate = request.getParameter("txtRefer_Date");
        txtRemarks = request.getParameter("txtRemarks");
        txtbudget_alloted = request.getParameter("txtbudget_alloted");
        txtbudget_spent = request.getParameter("txtbudget_spent");
        typallocation=request.getParameter("typallocation");
        
        
        System.out.println("txtbudget_alloted  " + txtbudget_alloted );
        System.out.println("txtbudget_spent" + txtbudget_spent);


        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


        try {
            acc_unit_code = Integer.parseInt(cmbAcc_UnitCode);
            office_code = Integer.parseInt(cmbOffice_code);
            previous_year = Double.parseDouble(txtpreviousyear);
            current_year_budget = Double.parseDouble(txtcurrentyearbudget);
            current_year_revised = Double.parseDouble(txtcurrentyearrevised);
            next_year_estimate = Double.parseDouble(txtnextyearestimate);
            previous_year_revised = Double.parseDouble(txtpreviousyearrevised);

        }

        catch (Exception e) {
            System.out.println("Exception in Number Format..." + e);
        }
        try {
            budget_spent = Double.parseDouble(txtbudget_spent);
            budget_alloted = Double.parseDouble(txtbudget_alloted);
        } catch (Exception e) {
            System.out.println("exp in budget_spent is:" + budget_spent);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            //to format the date of reg
        	if(typallocation.equalsIgnoreCase("G")){
        		txtaccountheadcode="0";
				from_code=0;
				to_code=0;
			}
			else{
				txtaccountheadcode = request.getParameter("txtAcc_Head_code");
					if(txtaccountheadcode.length()>6)
					{
						//System.out.println("if lenght greater");
						String[] from_to=txtaccountheadcode.split(" to ");
						from_code=Integer.parseInt(from_to[0]);
						to_code=Integer.parseInt(from_to[1]);
					}
					else
					{
						//System.out.println("else lenght single");
						from_code=Integer.parseInt(txtaccountheadcode);
						to_code=Integer.parseInt(txtaccountheadcode);
					}
			}
            java.sql.Date date1 = null;
            java.sql.Date date2 = null;

            if (!txtreferdate.equalsIgnoreCase("")) {
                SimpleDateFormat dateFormat2 =
                    new SimpleDateFormat("dd/MM/yyyy");
                try {
                    java.util.Date d2 = dateFormat2.parse(txtreferdate);
                    dateFormat2.applyPattern("yyyy-MM-dd");
                    txtreferdate = dateFormat2.format(d2);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                date2 = Date.valueOf(txtreferdate);
                System.out.println("date2 is...." + date2);
            }

            xml = "<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
int cc1=0;
            try {
            	String ss="select accounting_unit_id from com_budget_details where financial_year='"+cmbFinancialYear+"' and ACCOUNT_HEAD_CODE='"+txtaccountheadcode+"' and ACCOUNTING_UNIT_ID="+acc_unit_code;
                ps1 =
 con.prepareStatement(ss);
               /* ps1.setString(1, cmbFinancialYear);
                ps1.setString(2, txtaccountheadcode);
                ps1.setInt(3, acc_unit_code);*/
                cc1 = ps1.executeUpdate();
                System.out.println(ss);
                if (cc1>0) {
                	 System.out.println("This is if Loop");
                     xml = xml + "<flag>AlreadyExist</flag>";

                } else {
                	
                	
                	System.out.println("this i sinside the else loop");

                    try {
                        ps =
                        	con.prepareStatement("insert into com_budget_details (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACCOUNT_HEAD_CODE,PREVIOUS_YEAR_EXPENDITURE,PREVIOUS_YEAR_REVISED_ESTIMATE,CURRENT_YEAR_BUDGET_ESTIMATE,CURRENT_YEAR_REVISED_ESTIMATE,NEXT_YEAR_ESTIMATE,REF_NO,REF_DATE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,CURRENT_YEAR_BUDGET_ALLOTTED,BUDGET_SOFAR_SPENT,ALLOCATION_TYPE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        // System.out.println(ps);
                        ps.setInt(1, acc_unit_code);
                        ps.setInt(2, office_code);
                        ps.setString(3, cmbFinancialYear);
                        ps.setString(4, txtaccountheadcode);
                        ps.setDouble(5, previous_year);
                        ps.setDouble(6, previous_year_revised);
                        ps.setDouble(7, current_year_budget);
                        ps.setDouble(8, current_year_revised);
                        ps.setDouble(9, next_year_estimate);
                        ps.setString(10, txtreferno);
                        ps.setDate(11, date2);
                        ps.setString(12, txtRemarks);
                        ps.setString(13, userid);
                        long l = System.currentTimeMillis();
                        Timestamp ts = new Timestamp(l);
                        ps.setTimestamp(14, ts);
                        ps.setDouble(15, budget_alloted);
                        ps.setDouble(16, budget_spent);
                        ps.setString(17, typallocation);
                        ps.setInt(18, from_code);
                        ps.setInt(19, to_code);
                        
                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";


                    } catch (Exception e) {

                        System.out.println("catch. in  adding...." + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                   

                }

                xml = xml + "</response>";
            } catch (Exception e) {
                System.out.println("catch in x...." + e);

            }
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            java.sql.Date date2 = null;
           
            cmbFinancialYear = request.getParameter("cmbFinancial_Year");
            txtaccountheadcode = request.getParameter("txtAcc_Head_code").toString().trim();
            txtpreviousyear = request.getParameter("txtPreivous_Year");
            txtpreviousyearrevised =
                    request.getParameter("txtPrevious_Year_Revised");
            txtcurrentyearbudget =
                    request.getParameter("txtCurrent_Year_Budget");
            txtcurrentyearrevised =
                    request.getParameter("txtCurrent_Year_Revised");
            txtnextyearestimate =
                    request.getParameter("txtNext_Year_Estimate");
            txtreferno = request.getParameter("txtRefer_No").toString().trim();
            txtreferdate = request.getParameter("txtRefer_Date");
            txtRemarks = request.getParameter("txtRemarks");
            txtbudget_alloted = request.getParameter("txtbudget_alloted");
            txtbudget_spent = request.getParameter("txtbudget_spent");

            
           /* if (!txtreferdate.equalsIgnoreCase("")) {
                SimpleDateFormat dateFormat2 =
                    new SimpleDateFormat("dd/MM/yyyy");
                try {
                    java.util.Date d2 = dateFormat2.parse(txtreferdate);
                    dateFormat2.applyPattern("yyyy-MM-dd");
                    txtreferdate = dateFormat2.format(d2);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                date2 = Date.valueOf(txtreferdate);
                System.out.println("date2 is...." + date2);
            }*/

            double previous_year1 = 0;
            double previous_year_revised1 = 0;
            double current_year_budget1 = 0;
            double current_year_revised1 = 0;
            double next_year_estimate1 = 0;
            System.out.println("cmbFinancialyear is:" + cmbFinancialYear);
            System.out.println("accountheadcode is:" + txtaccountheadcode);
            System.out.println("previous year is:" + txtpreviousyear);
            System.out.println("previous year rev is:" +
                               txtpreviousyearrevised);
            System.out.println("current year is:" + txtcurrentyearbudget);
            System.out.println("current year revi is:" +
                               txtcurrentyearrevised);
            System.out.println("next year is:" + txtnextyearestimate);
            System.out.println("accountheadcode is:" + txtaccountheadcode);
            try {
                budget_spent = Double.parseDouble(txtbudget_spent);
                budget_alloted = Double.parseDouble(txtbudget_alloted);
            } catch (Exception e) {
                System.out.println("exp in budget_spent is:" + budget_spent);
            }

            try {
                previous_year1 = Double.parseDouble(txtpreviousyear);
            } catch (Exception e) {
                previous_year1 = 0;
            }
            try {
                current_year_budget1 =
                        Double.parseDouble(txtcurrentyearbudget);
            } catch (Exception e) {
                current_year_budget1 = 0;
            }
            try {
                current_year_revised1 =
                        Double.parseDouble(txtcurrentyearrevised);
            } catch (Exception e) {
                current_year_revised1 = 0;
            }
            try {
                next_year_estimate1 = Double.parseDouble(txtnextyearestimate);
            } catch (Exception e) {
                next_year_estimate1 = 0;
            }
            try {
                previous_year_revised1 =
                        Double.parseDouble(txtpreviousyearrevised);
            } catch (Exception e) {
                previous_year_revised1 = 0;
              //  System.out.println("Exception in Number Format..." + e);
            }

            System.out.println("previous year is:" + previous_year1);
            System.out.println("current year is:" + current_year_budget1);
            System.out.println("current year revised is:" +
                               current_year_revised1);
            System.out.println("next year is:" + next_year_estimate1);
            System.out.println("txtreferno is:" + txtreferno);
            System.out.println("date is:" + date2);
            System.out.println("Remarks is:" + txtRemarks);

       	 SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
       	    SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
       	    java.util.Date date11=null;
       	    String date22=null;
       		try {
       			date11 = format1.parse(txtreferdate);
       		} catch (ParseException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		}
       	   // System.out.println(format2.format(date11));
            date22=format2.format(date11);
           /* Date ps_date=null;
            Calendar c;
            String[] sd=request.getParameter("txtRefer_Date").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            ps_date=new Date(d.getTime());*/
            

            	int ii=0;
            try {
          //  String sql = "update com_budget_details set PREVIOUS_YEAR_EXPENDITURE=?,CURRENT_YEAR_BUDGET_ESTIMATE=?,CURRENT_YEAR_REVISED_ESTIMATE=?,NEXT_YEAR_ESTIMATE=?,REF_NO=?,REF_DATE=?,REMARKS=?,CURRENT_YEAR_BUDGET_ALLOTTED=?,BUDGET_SOFAR_SPENT=? where FINANCIAL_YEAR=? and ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?";
            	 
            	
            	   String sql = "update com_budget_details set PREVIOUS_YEAR_EXPENDITURE="+previous_year1+",CURRENT_YEAR_BUDGET_ESTIMATE="+current_year_budget1+",CURRENT_YEAR_REVISED_ESTIMATE="+current_year_revised1+",NEXT_YEAR_ESTIMATE="+next_year_estimate1+",REF_NO="+txtreferno+",REF_DATE='"+date22+"',REMARKS='"+txtRemarks+"',CURRENT_YEAR_BUDGET_ALLOTTED="+budget_alloted+",BUDGET_SOFAR_SPENT="+budget_spent +" where FINANCIAL_YEAR='"+cmbFinancialYear+"' and ACCOUNT_HEAD_CODE='"+txtaccountheadcode+"' and ACCOUNTING_UNIT_ID="+acc_unit_code+" and ACCOUNTING_FOR_OFFICE_ID="+office_code+" ";
            	   //System.out.println("sql "+sql);
            	  // System.out.println("sql11 "+sql1);
                ps = con.prepareStatement(sql);
        /*   ps.setDouble(1, previous_year1);
                ps.setDouble(2, current_year_budget1);
                ps.setDouble(3, current_year_revised1);
              ps.setDouble(4, next_year_estimate1);
               ps.setString(5, txtreferno);
                ps.setDate(6, ps_date);
               ps.setString(7, txtRemarks);
                ps.setDouble(8, budget_alloted);
                ps.setDouble(9, budget_spent);
               ps.setString(10, cmbFinancialYear);
               ps.setString(11, txtaccountheadcode);
               ps.setInt(12, acc_unit_code);
               ps.setInt(13, office_code);*/
               System.out.println("query  "+sql);
                ii = ps.executeUpdate();
                System.out.println("ii wel "+ii);
                if (ii >0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
                System.out.println("Exception in Update:" + e);
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
            //    System.out.println("financial year is:" + cmbFinancialYear +
             //                      ":");
             //   System.out.println("account head code is:" +
             //                      txtaccountheadcode + ":");
                /*ps=con.prepareStatement("delete from com_budget_details where FINANCIAL_YEAR=? and ACCOUNT_HEAD_CODE=?");
                    ps.setString(1,cmbFinancialYear.trim());
                    ps.setString(2,txtaccountheadcode.trim());
                     int ij=ps.executeUpdate();
                    */
                Statement st = con.createStatement();
                String sql =
                    "delete from com_budget_details where FINANCIAL_YEAR='" +
                    cmbFinancialYear + "' and ACCOUNT_HEAD_CODE='" +
                    txtaccountheadcode + "'";
                System.out.println(sql);
                int ij = st.executeUpdate(sql);

                if (ij > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        
        
        else 	if (strCommand.equalsIgnoreCase("getStatementName")) {
			xml = "<response><command>getStatementName</command>";
			try {
				ps = con
						.prepareStatement("select STATEMENT_NO,STATEMENT_DESC from FAS_STATEMENT_MASTER order by STATEMENT_NO");
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<STATEMENT_NO>"
							+ results.getString("STATEMENT_NO")
							+ "</STATEMENT_NO>";
					xml = xml + "<STATEMENT_DESC>"
							+ results.getString("STATEMENT_DESC")+" ( "+results.getString("STATEMENT_NO")+" )"
							+ "</STATEMENT_DESC>";
				}

				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
		}
        
    	else if (strCommand.equalsIgnoreCase("head_test")) {
			int count = 0;
			xml = "<response><command>head_test</command>";
			String head_code = request.getParameter("txtAcc_Head_code");
		/*	String cmbFinancialYear = request.getParameter("cmbFinancialYear");
			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));*/
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			if(head_code.length()>6)
			{
				//System.out.println("if lenght greater");
				String[] from_to=head_code.split(" to ");
				from_code=Integer.parseInt(from_to[0]);
				to_code=Integer.parseInt(from_to[1]);
			}
			else
			{
				//System.out.println("else lenght single");
				from_code=Integer.parseInt(head_code);
				to_code=Integer.parseInt(head_code);
			}
		int v=0;
			String sql = "";
			try {
				/*sql="Select Group_Type From Fas_Statement_Acc_Hd_Mapping Where " +
						" From_Acc_Hd_Code="+from_code+" And To_Acc_Hd_Code="+to_code+" And Statement_No= " +cmbStatementName+
						" and STATEMENT_GROUP_NO="+statementGp;*/
				sql="select a.groupty as grouptyp,b.grpDesc as grpDescc from "+
							" (SELECT Group_Type as GroupTy,STATEMENT_SUB_GROUP_NO "+
							" from fas_statement_acc_hd_mapping "+
							" where from_acc_hd_code=  "+from_code+" "+
							" and to_acc_hd_code               = "+to_code+""+
							" and statement_no                 = "+cmbStatementName+
							" and statement_group_no           = "+statementGp+" )a"+
							" inner join"+
							" (select statement_sub_group_dec as grpDesc,statement_sub_group_no " +
							" from fas_statement_sub_group_master "+ 
							" where statement_no= "+cmbStatementName+
							" and statement_group_no= "+statementGp+" )b"+
							" on a.statement_sub_group_no=b.statement_sub_group_no";
				ps1 = con.prepareStatement(sql);
				//System.out.println("sql  head display "+sql);
				v= ps1.executeUpdate();
				if(v>0){
					results = ps1.executeQuery();
					xml += "<flag>success</flag>";
					while(results.next()){
						
						xml += "<grouptype>"+results.getString("grouptyp")+"</grouptype>";
						xml += "<groupdesc>"+results.getString("grpDescc")+"</groupdesc>";
						count++;
					}
				}else{
					xml += "<flag>failure</flag>";
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
        else if (strCommand.equalsIgnoreCase("checkFreeze")) 
		{
//System.out.println("office_code  "+office_code);
			int count = 0;
			xml = "<response><command>checkFreeze</command>";
			int statementno = Integer.parseInt(request.getParameter("cmbStatementName"));			
			/* acc_unit_code = Integer.parseInt(cmbAcc_UnitCode);
	            office_code */
			String sql = "";
			int cc=0;
			try {
				
				//try{
					String sqlquery="select distinct(REGION_OFFICE_ID) from FAS_STATEMENT_BY_REGION where ACCOUNTING_FOR_OFFICE_ID="+office_code;
					ps1 = con.prepareStatement(sqlquery);
	                // ps1.setInt(1, office_code);
					System.out.println(sqlquery);
	                 count=ps1.executeUpdate();
	                 if(count>0){
	                	 results=ps1.executeQuery();
	                	 int regid=0;
	                	 while(results.next()){
	                		 regid=results.getInt("REGION_OFFICE_ID"); 
	                	 }
	                	 sql="select ACCOUNTING_UNIT_ID from fas_budget_closure_allocation where ACCOUNTING_FOR_OFFICE_ID="+regid+" and financial_year='"+cmbFinancialYear+"' and STATEMENT_NAME='"+statementno+"'";
	     				
	     			//	System.out.println(sql);
	     				ps2 = con.prepareStatement(sql);
	     				// ps2.setInt(1, acc_unit_code);
	                    //  ps2.setInt(1, regid);
	                    //  ps2.setString(2, cmbFinancialYear);     
	     				cc = ps2.executeUpdate();
	     				if(cc>0){
	     					xml = xml + "<flag>success</flag>";
	     					xml += "<status>Freeze</status>";
	     				}else{
	     					xml = xml + "<flag>success</flag>";
	     					xml += "<status>NotFreeze</status>";
	     				}
	                	
	                 }
	
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
        else if (strCommand.equalsIgnoreCase("getAmount")) 
		{
        	//System.out.println("office_code  "+office_code);
			int count = 0;
			//System.out.println("get amount ");
			xml = "<response><command>getAmount</command>";
			int statementno = Integer.parseInt(request.getParameter("cmbStatementName"));
			int stgp=Integer.parseInt(request.getParameter("statementGp"));
			String typall=request.getParameter("typallocation");
			//String acchead=request.getParameter("typallocation");
			/* acc_unit_code = Integer.parseInt(cmbAcc_UnitCode);
	            office_code */
			String sql = "";
			int cc=0;
			try {
				
				//try{
					String sqlquery="select distinct(REGION_OFFICE_ID) from FAS_STATEMENT_BY_REGION where ACCOUNTING_FOR_OFFICE_ID="+office_code;
					ps1 = con.prepareStatement(sqlquery);
	                // ps1.setInt(1, office_code);
	                 count=ps1.executeUpdate();
	                 if(count>0){
	                	 results=ps1.executeQuery();
	                	 int regid=0;
	                	 while(results.next()){
	                		 regid=results.getInt("REGION_OFFICE_ID"); 
	                	 }
	                	 if(typall.equalsIgnoreCase("G")){
	                		 sql="select amount from fas_statement_by_region where accounting_for_office_id="+office_code+" and region_office_id="+regid+" and financial_year='"+cmbFinancialYear+"' and statement_no='"+statementno+"' and statement_group_no="+stgp+" and ALLOCATION_TYPE='"+typall+"'"; 
	                		 
	                	 }else{
	                		 sql="select amount from fas_statement_by_region where accounting_for_office_id="+office_code+" and region_office_id="+regid+" and financial_year='"+cmbFinancialYear+"' and statement_no='"+statementno+"' and statement_group_no="+stgp+" and ALLOCATION_TYPE='"+typall+"' and  ACCOUNT_HEAD_CODE='"+txtaccountheadcode+"'"; 
	                	 }
	                	 
	                	 
	                	// System.out.println(" regid "+regid );
	                	
	     				
	     				System.out.println(sql);
	     				ps2 = con.prepareStatement(sql);
	     				/* ps2.setInt(1, office_code);
	                      ps2.setInt(2, regid);
	                      ps2.setString(3, cmbFinancialYear);  
	                      ps2.setInt(4, statementno);
	                      ps2.setInt(5, stgp);
	                      ps2.setString(6, typall);*/
	     				cc = ps2.executeUpdate();
	     				if(cc>0){	
	     					Float amt=0.0f;
	     					xml = xml + "<flag>success</flag>";
	     					rs=ps2.executeQuery();
	     					while(rs.next()){
	     						amt=rs.getFloat("amount");
	     						xml += "<amount>"+amt+"</amount>";
	     					}
	     					
	     				}else{
	     					xml += "<flag>NOAmt</flag>";
	     				}
	     				
	     				
	                	
	                 }
	
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
        
        else if (strCommand.equalsIgnoreCase("groupch")) 
		{

			int count = 0;
			xml = "<response><command>groupch</command>";
			int statementno = Integer.parseInt(request.getParameter("statement"));			
			/* acc_unit_code = Integer.parseInt(cmbAcc_UnitCode);
	            office_code */
			String sql = "";
			try {
				sql="Select Statement_Group_No,STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME="+statementno+" order by Statement_Group_No";
				//System.out.println(sql);
				ps1 = con.prepareStatement(sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml = xml + "<gp_no>"+ results.getInt("Statement_Group_No")+ "</gp_no>";
					xml = xml + "<gp_desc>"+ results.getString("STATEMENT_GROUP_DESC")+ "</gp_desc>";
					count++;
				}
				if(count>0){
					xml += "<flag>success</flag>";
				}else{
					xml += "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		
		
		else if (strCommand.equalsIgnoreCase("callHead")) 
		{
			
			int count = 0;
			xml = "<response><command>callHead</command>";
			
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			//System.out.println("cmbStatementName"+cmbStatementName);
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			//System.out.println("statementGp"+statementGp);
			String sql = "";
			
			try {
				sql="Select Case When Group_Type='H' Then From_Acc_Hd_Code||'' "+ 
			" When Group_Type='G' Then From_Acc_Hd_Code||' to '||To_Acc_Hd_Code  "+
			" End As Range_Of_Heads "+
			" From Fas_Statement_Acc_Hd_Mapping Where Statement_No="+cmbStatementName+" And " +
			" Statement_Group_No="+statementGp+" order by From_Acc_Hd_Code";
				//System.out.println(sql);
				ps1 = con.prepareStatement(sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml = xml + "<Range_Of_Heads>"+ results.getString("Range_Of_Heads")+ "</Range_Of_Heads>";
					count++;
				}
				if(count>0){
					xml += "<flag>success</flag>";
					
					
				}
				else{
					xml += "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		 else if (strCommand.equalsIgnoreCase("callstatement")) {
				xml = "<response><command>callstatement</command>";
				int count = 0;
			
				//int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
				//int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
				
				//String fyear =request.getParameter("cmbFinancialYear");
				//System.out.println("fyear"+fyear);
				int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
				//System.out.println("cmbStatementName"+cmbStatementName);
				int statementGp = Integer.parseInt(request.getParameter("statementGp"));
				//System.out.println("statementGp"+statementGp);
				String sql = "";
				
				try {
					sql="Select Allocation_Type,Account_Head_Code,sum(Amount)as regAmt,UNIT_ALLOCATION From Fas_Statement_By_Region Where Statement_No    = " +cmbStatementName+
							"And Statement_Group_No="+statementGp+" And Financial_Year    ='"+cmbFinancialYear+"' AND Region_Office_Id  = "+office_code+" group by Allocation_Type,Account_Head_Code,UNIT_ALLOCATION ";
					//System.out.println(sql);
					ps1 = con.prepareStatement(sql);
					
					results = ps1.executeQuery();
					if(results.next()){
						xml = xml + "<regAmt>"+ results.getInt("regAmt")+ "</regAmt>";
						xml = xml + "<Allocation_Type>"+ results.getString("Allocation_Type")+ "</Allocation_Type>";
						xml = xml + "<Account_Head_Code>"+ results.getInt("Account_Head_Code")+ "</Account_Head_Code>";
						xml = xml + "<unit_allocation>"+ results.getString("UNIT_ALLOCATION")+ "</unit_allocation>";
						count++;
					}
					if(count>0){
						xml += "<flag>already</flag>";
						
						
					}
					else{
						xml += "<flag>nodata</flag>";
					}
				} catch (Exception e) {
					e.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";
			}
        else if (strCommand.equalsIgnoreCase("geneId")) {
            System.out.println("Insiden Year ");
            String sxml = "<response><command>geneId</command>";
            /*b=10;
            try {
                ps=con.prepareStatement("SELECT MAX(SUPPLIER_ID) AS b FROM COM_FIRMS_SL_MST");


                rs=ps.executeQuery();
                if(rs.next())
                {
                            b=rs.getInt("b");
                            System.out.println("b is "+b);

                }
                 int j=b;
                System.out.println("b is"+j);

                if(j==0)
                {
                j=1;
                System.out.println("i...."+j);
                xml=sxml+"<flag>first</flag><j>"+j+"</j>";
                }
                else
                {
                    j=j+1;
                    System.out.println("i.."+j);
                    xml=sxml+"<flag>incremented</flag><j>"+j+"</j>";
                }
            }
            catch(Exception e) {
                System.out.println("catch in x...."+e);
                xml=xml+"<flag>failure</flag>";
            }*/
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("List")) {
            System.out.println("Inside List  ");
            int accId = 0;
            int offId = 0;
            String dateReg = "";
            String datelast = "";
            java.sql.Date date1 = null;
            java.sql.Date date2 = null;
            String sxml = "";
            String sql = "";
            int account = 0;
            int office = 0;
            try {
                account =
                        Integer.parseInt(request.getParameter("cmbAccounting_Unit_Id"));
            } catch (Exception e) {
                System.out.println("Exception in AccountUnit:" + e);
            }
            try {
                office =
                        Integer.parseInt(request.getParameter("cmbOffice_Code"));
            } catch (Exception e) {
                System.out.println("Exception in Office:" + e);
            }
            String Finyear = request.getParameter("cmbFinancial_Year");
            try {

                String previous_year1 = "";
                String previous_year_revised1 = "";
                String current_year_budget1 = "";
                String current_year_revised1 = "";
                String next_year_estimate1 = "";
                String account_unit_name = "";
                String account_head_desc = "", budgetalloted = "", budgetspent,allotype="";
                //sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ACCOUNT_HEAD_CODE,PREVIOUS_YEAR_EXPENDITURE,PREVIOUS_YEAR_REVISED_ESTIMATE,CURRENT_YEAR_BUDGET_ESTIMATE,CURRENT_YEAR_REVISED_ESTIMATE,NEXT_YEAR_ESTIMATE,REF_NO,REF_DATE,REMARKS from com_budget_details";
                /*sql =
 "select a.ACCOUNTING_UNIT_ID,b.accounting_unit_name,a.ACCOUNTING_FOR_OFFICE_ID,a.FINANCIAL_YEAR,a.ACCOUNT_HEAD_CODE,c.account_head_desc, " +
   " a.PREVIOUS_YEAR_EXPENDITURE,a.PREVIOUS_YEAR_REVISED_ESTIMATE,a.CURRENT_YEAR_BUDGET_ESTIMATE,a.CURRENT_YEAR_REVISED_ESTIMATE, " +
   " a.NEXT_YEAR_ESTIMATE,a.REF_NO,REF_DATE,a.REMARKS,a.CURRENT_YEAR_BUDGET_ALLOTTED,a.BUDGET_SOFAR_SPENT,a.ALLOCATION_TYPE from com_budget_details a,fas_mst_acct_units b,com_mst_account_heads c where " +
   " a.accounting_unit_id=b.accounting_unit_id and a.account_head_code=c.account_head_code and a.accounting_unit_id="+account+ " and a.accounting_for_office_id= " +office+
   " and a.financial_year='"+Finyear+"'";
                */
                
                
                sql =
                	 "select a.ACCOUNTING_UNIT_ID,b.accounting_unit_name,a.ACCOUNTING_FOR_OFFICE_ID,a.FINANCIAL_YEAR,a.ACCOUNT_HEAD_CODE, " +
                	   " a.PREVIOUS_YEAR_EXPENDITURE,a.PREVIOUS_YEAR_REVISED_ESTIMATE,a.CURRENT_YEAR_BUDGET_ESTIMATE,a.CURRENT_YEAR_REVISED_ESTIMATE, " +
                	   " a.NEXT_YEAR_ESTIMATE,a.REF_NO,REF_DATE,a.REMARKS,a.CURRENT_YEAR_BUDGET_ALLOTTED,a.BUDGET_SOFAR_SPENT,a.ALLOCATION_TYPE from com_budget_details a,fas_mst_acct_units b where " +
                	   " a.accounting_unit_id=b.accounting_unit_id and a.accounting_unit_id="+account+ " and a.accounting_for_office_id= " +office+
                	   " and a.financial_year='"+Finyear+"'";  
                
                
                ps = con.prepareStatement(sql);
                System.out.println(sql);
               /* ps.setInt(1, account);
                ps.setInt(2, office);
                ps.setString(3, Finyear);*/
                rs = ps.executeQuery();
                xml = "<response><command>List</command><flag>success</flag>";
                while (rs.next()) {

                    acc_unit_code = rs.getInt("ACCOUNTING_UNIT_ID");

                    account_unit_name = rs.getString("accounting_unit_name");

                   // account_head_desc = rs.getString("account_head_desc");

                    office_code = rs.getInt("ACCOUNTING_FOR_OFFICE_ID");

                    cmbFinancialYear = rs.getString("FINANCIAL_YEAR");

                    txtaccountheadcode = rs.getString("ACCOUNT_HEAD_CODE");

                    previous_year1 = rs.getString("PREVIOUS_YEAR_EXPENDITURE");

                    previous_year_revised1 =
                            rs.getString("PREVIOUS_YEAR_REVISED_ESTIMATE");

                    current_year_budget1 =
                            rs.getString("CURRENT_YEAR_BUDGET_ESTIMATE");

                    current_year_revised1 =
                            rs.getString("CURRENT_YEAR_REVISED_ESTIMATE");

                    next_year_estimate1 = rs.getString("NEXT_YEAR_ESTIMATE");

                    txtreferno = rs.getString("REF_NO");

                    txtreferdate = rs.getString("REF_DATE");

                    txtRemarks = rs.getString("REMARKS");
                    budgetalloted =
                            rs.getString("CURRENT_YEAR_BUDGET_ALLOTTED");
                    budgetspent = rs.getString("BUDGET_SOFAR_SPENT");
                    allotype=rs.getString("ALLOCATION_TYPE");
                    

                    /*if(txtreferno!=null)
                     {
                        txtreferno=txtreferno.trim();
                     }
                     else
                     {
                     txtreferno="";
                     System.out.println("inside else");
                     }
                     if(txtreferdate!=null)
                     {
                        txtreferdate=txtreferdate.trim();
                     }
                     else
                     {
                      txtreferdate="";
                     }
                     if(txtRemarks!=null) {
                         txtRemarks=txtRemarks.trim();
                     }
                     else
                     {
                        txtRemarks="";
                     }

                     if(account_unit_name!=null)
                     {
                            account_unit_name=account_unit_name.trim();
                     }
                     else {

                     }*/
                    System.out.println("accountunit is:" + acc_unit_code);
                    System.out.println("accountunit is:" + account_unit_name);
                    System.out.println("accountunit is:" + txtaccountheadcode);
                  //  System.out.println("accountunit is:" + account_head_desc);
                    System.out.println("accountunit is:" + office_code);
                    System.out.println("accountunit is:" + cmbFinancialYear);
                    System.out.println("accountunit is:" + previous_year1);
                    System.out.println("accountunit is:" +
                                       previous_year_revised1);
                    System.out.println("accountunit is:" +
                                       current_year_budget1);
                    System.out.println("accountunit is:" +
                                       current_year_revised1);
                    System.out.println("accountunit is:" +
                                       next_year_estimate1);
                    System.out.println("accountunit is:" + txtreferno);
                    System.out.println("accountunit is:" + txtreferdate);
                    System.out.println("accountunit is:" + txtRemarks);


                    //Date Conversion of User Format ///

                    java.sql.Date DateOfFormation = rs.getDate("REF_DATE");
                    String DateToBeDisplayed = "";
                    if (DateOfFormation == null) {
                        DateToBeDisplayed = "Not Specified";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            DateToBeDisplayed = sdf.format(DateOfFormation);
                        } catch (Exception e) {
                            System.out.println("error while formatting date : " +
                                               e);
                            DateToBeDisplayed = "Not Specified";
                        }
                    }
                   /* <accountheaddesc>" + account_head_desc +
                    "</accountheaddesc>
*/                    ////////////////////////////////////////////////////////////

                    sxml =
sxml + "<accountunitid>" + acc_unit_code + "</accountunitid><officecode>" +
 office_code + "</officecode><financialyear>" + cmbFinancialYear +
 "</financialyear><accountheadcode>" + txtaccountheadcode +
 "</accountheadcode><previousyear>" + previous_year1 +
 "</previousyear><previousyearrevised>" + previous_year_revised1 +
 "</previousyearrevised><currentyearbudget>" + current_year_budget1 +
 "</currentyearbudget><currentyearrevised>" + current_year_revised1 +
 "</currentyearrevised><nextyear>" + next_year_estimate1 +
 "</nextyear><referno>" + txtreferno + "</referno><referdate>" +
 DateToBeDisplayed + "</referdate><remarks>" + txtRemarks +
 "</remarks><accountunitname>" + account_unit_name +
 "</accountunitname><budgetalloted>" + budgetalloted +
 "</budgetalloted><budgetspent>" + budgetspent + "</budgetspent><allotype> "+allotype+"</allotype>";


                }
                xml = xml + sxml + "</response>";
                //System.out.println("xml is:"+xml);

            } catch (Exception e) {
                xml = xml + "</response>";
                System.out.println("Exception in List :" + e);
            }


        }
        else if (strCommand.equalsIgnoreCase("HeadCode")) {

            String headcode = request.getParameter("txtAcc_Head_code");
            int headcodeno = Integer.parseInt(headcode);
            xml = "<response><command>HeadCode</command>";
            try {
                ps =
  con.prepareStatement("select account_head_desc from com_mst_account_heads where account_head_code=?");
                ps.setInt(1, headcodeno);
                res = ps.executeQuery();
                if (res.next()) {
                    xml =
 xml + "<flag>success</flag><headcode>" + res.getString("account_head_desc") +
   "</headcode>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
                xml = xml + "</response>";
            } catch (Exception e) {
                System.out.println("Exception in HeadCode:" + e);
            }


        }


        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}

