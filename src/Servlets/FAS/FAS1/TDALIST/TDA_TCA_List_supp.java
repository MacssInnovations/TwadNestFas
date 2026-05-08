package Servlets.FAS.FAS1.TDALIST;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;
public class TDA_TCA_List_supp extends HttpServlet 
{
   
    public void init(ServletConfig config) throws ServletException
    {
         super.init(config);
       
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {   
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
                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                 Class.forName(strDriver.trim());
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
        int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0,supNo=0;
        Date txtFrom_date=null,txtTo_date=null;
        Calendar c;
        String sql="",txtCreat_By_Module="",cmbStatus="",journal_type="",jType1="",jType2="";
        
        
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
        cmbStatus=request.getParameter("cmbStatus");
        
        try{journal_type=request.getParameter("journal_type");}
        catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
        
        System.out.println("cccc");
        
        supNo=Integer.parseInt(request.getParameter("supNo"));
        
        txtCreat_By_Module=request.getParameter("txtCreat_By_Module");
        if(strType.equalsIgnoreCase("searchByMonth"))  
        {
	            xml="<response><command>searchByMonth</command>";                        
	            if(journal_type.equals("TDAO")||journal_type.equals("TCAO"))
                {
                                xml=xml+"<jtype>"+journal_type+"</jtype>";
				            sql="select voucher_no,\n" + 
				                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
				                "      particulars,\n" + 
				                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" + 
				                "      orginating_jvr_no as GJV_No,\n" + 
				                "      to_char(orginating_jvr_date,'DD/MM/YYYY') as GJV_Date ,\n" +
				                "      ACCEPTING_JVR_NO," +
				                "      to_char(ACCEPTING_JVR_DATE,'DD/MM/YYYY')as accepting_date " + 
				                "from fas_tda_tca_raised_mst \n" + 
				                "where    \n" + 
				                "      accounting_unit_id=? and  \n" + 
				                "      accounting_for_office_id=? and  \n" + 
				                "      cashbook_year=? and  \n" + 
				                "      cashbook_month=? and \n" + 
				                "      status=? and SUPPLEMENT_NO="+supNo+" and\n" + 
				                "      (tda_or_tca=?  or tda_or_tca=?)\n" + 
				                "order by\n" + 
				                "      voucher_no";
				            
				            System.out.println("SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						            ps.setInt(2,cmbOffice_code);
						            ps.setInt(3,txtCB_Year);
						            ps.setInt(4,txtCB_Month);
						            ps.setString(5,cmbStatus);
						            ps.setString(6,journal_type);
                                                            if(journal_type.equals("TDAO")) {
                                                                ps.setString(7,"TDACB");
                                                            }
                                                            else if(journal_type.equals("TCAO")) {
                                                                ps.setString(7,"TCACB");
                                                            }
						           // ps.setString(7,txtMode_of_creat);
						            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
						            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
						                    xml=xml+"<leng>";
						                    xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
						                    xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
						                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
						                    if(journal_type.equals("TDAO")||journal_type.equals("TCAO"))
						                    {
								                    if(rs.getInt("GJV_No")!=0)
									                    	xml=xml+"<GJV_No>"+rs.getInt("GJV_No") +"</GJV_No>";
								                    else
								                    		xml=xml+"<GJV_No>--</GJV_No>";
								                    
								                    if(rs.getString("GJV_Date")==null)
								                    		xml=xml+"<GJV_Date>--</GJV_Date>";
								                    else
									                    	xml=xml+"<GJV_Date>"+rs.getString("GJV_Date") +"</GJV_Date>";
						                    }
						                    else
						                    {
							                    	if(rs.getInt("ACCEPTING_JVR_NO")!=0)
								                    	xml=xml+"<GJV_No>"+rs.getInt("ACCEPTING_JVR_NO") +"</GJV_No>";
								                    else
								                    		xml=xml+"<GJV_No>--</GJV_No>";
								                    
								                    if(rs.getString("accepting_date")==null)
								                    		xml=xml+"<GJV_Date>--</GJV_Date>";
								                    else
									                    	xml=xml+"<GJV_Date>"+rs.getString("accepting_date") +"</GJV_Date>";
						                    }
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count==0) 
					                {
						                    System.out.println("inside count==0");
						                    xml="<response><command>searchByMonth</command><flag>failure</flag><jtype>"+journal_type+"</jtype>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByMonth</command><flag>failure</flag><jtype>"+journal_type+"</jtype>";
				            }
                }
                else if (journal_type.equals("TDAA") ||journal_type.equals("TCAA"))
                {
                    xml=xml+"<jtype>"+journal_type+"</jtype>";
                    if(journal_type.equals("TDAA")) {
                        jType1="TDAO";
                        jType2="TDACB";
                    }
                    else if(journal_type.equals("TCAA")) {
                        jType1="TCAO";
                        jType2="TCACB";
                    }
                    sql="SELECT voucher_no as Org_Advice_No,\n" + 
                    "  TO_CHAR(voucher_date,'DD/MM/YYYY') AS Org_Advice_date ,\n" + 
                    "  TRF_ACCOUNTING_UNIT_ID,\n" + 
                    "  (select aa.Accounting_unit_name from fas_mst_acct_units aa \n" + 
                    "  where aa.accounting_unit_id= TRF_ACCOUNTING_UNIT_ID) as Accept_Unit,\n" + 
                    "  trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount,\n" + 
                    "  ACCEPTING_SLNO                               AS Accept_Advice_No,\n" + 
                    "  TO_CHAR(ACCEPTING_DATE,'DD/MM/YYYY')       AS Accept_Advice_Date" +
                    " FROM fas_tda_tca_raised_mst\n" + 
                    " WHERE accounting_unit_id    =?\n" + 
                    " AND accounting_for_office_id=?\n" + 
                    " AND cashbook_year           =?\n" + 
                    " AND cashbook_month          =?\n" + 
                    " AND status                  ='L' and SUPPLEMENT_NO="+supNo+" \n" + 
                    " and ACCEPTANCE_STATUS='Y'\n" + 
                    " and ORGINATING_JVR_NO is not null\n" + 
                    " and ORGINATING_JVR_DATE is not null\n" + 
                    " and (ACCEPTING_SLNO is not null or ACCEPTING_SLNO!=0)\n" + 
                    " AND (tda_or_tca              =? or tda_or_tca=?)\n" + 
                    " ORDER BY voucher_no";
                    
                    System.out.println("SQL::::"+sql);
                    try
                    {
                                    int count=0;
                                    ps=con.prepareStatement(sql);
                                    ps.setInt(1,cmbAcc_UnitCode);
                                    ps.setInt(2,cmbOffice_code);
                                    ps.setInt(3,txtCB_Year);
                                    ps.setInt(4,txtCB_Month);
                                if(journal_type.equals("TDAA")) {
                                    ps.setString(5,jType1);
                                    ps.setString(6,jType2);
                                }
                                else if(journal_type.equals("TCAA")) {
                                    ps.setString(5,jType1);
                                    ps.setString(6,jType2);
                                }
                                System.out.println("jType1"+jType1+":::"+jType2);  
                                    xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                    "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                              //      System.out.println("xml:::"+xml);
                                    rs=ps.executeQuery();
                                while(rs.next())
                                {
                             //   System.out.println("while");
                                            xml=xml+"<leng>";
                                            xml=xml+"<vou_no>"+rs.getInt("Org_Advice_No")+"</vou_no>";
                                            xml=xml+"<vou_date>"+rs.getString("Org_Advice_date")+"</vou_date>";
                                            xml=xml+"<unitName>"+rs.getString("Accept_Unit")+"</unitName>";
                                            xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                            xml=xml+"<acc_adNo>"+rs.getInt("Accept_Advice_No") +"</acc_adNo>";
                                            xml=xml+"<acc_adDate>"+rs.getString("Accept_Advice_Date") +"</acc_adDate>";
                                            xml=xml+"</leng>";
                                            count++;
                                }
                                if(count==0) 
                                {
                                            System.out.println("inside count==0");
                                            xml="<response><command>searchByMonth</command><flag>failure</flag><jtype>"+journal_type+"</jtype>";
                                }
                    }
                    catch(SQLException sqle)
                    {
                                    sqle.printStackTrace();
                                    System.out.println("error while fetching data " + sqle);
                                xml="<response><command>searchByMonth</command><flag>failure</flag><jtype>"+journal_type+"</jtype>";
                    }
                }
             /*   else 
                {
			                sql="select\n" + 
			                "  * \n" + 
			                "from\n" + 
			                "(\n" + 
			                "    select \n" + 
			                "          ACCOUNTING_UNIT_ID,\n" + 
			                "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
			                "          ACCEPTING_JVR_NO,\n" + 
			                "          extract(year from ACCEPTING_JVR_DATE)as cashbook_year,\n" + 
			                "          extract(month from ACCEPTING_JVR_DATE)as cashbook_month,\n" +
			                "		   trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as total_amount " + 
			                "    from  fas_tda_tca_raised_mst \n" + 
			                "    where    \n" + 
			                "          accounting_unit_id=? and  \n" + 
			                "          accounting_for_office_id=? and  \n" + 
			                "          extract(year from ACCEPTING_JVR_DATE)=? and  \n" + 
			                "          extract(month from ACCEPTING_JVR_DATE)=? and \n" + 
			                "          status=? and\n" + 
			                "          ACCEPTING_JVR_NO is not null and \n" + 
			                "          ACCEPTING_JVR_DATE is not null\n" + 
			                ")a inner join \n" + 
			                "(\n" + 
			                "    select\n" + 
			                "          VOUCHER_NO,\n" + 
			                "          to_char(VOUCHER_DATE,'DD/MM/YYYY') as voucher_date,\n" + 
			                "          JOURNAL_TYPE_CODE,\n" + 
			                "          REMARKS \n" + 
			                "    from  FAS_JOURNAL_MASTER    \n" + 
			                "    where  \n" + 
			                "          accounting_unit_id=? and  \n" + 
			                "          accounting_for_office_id=? and\n" + 
			                "          cashbook_year=? and\n" + 
			                "          cashbook_month=?\n" + 
			                ")b on a.ACCEPTING_JVR_NO=b.VOUCHER_NO left outer join " +
			                "( " +
			                "		   select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE  " +
			                ")c on b.JOURNAL_TYPE_CODE=c.JOURNAL_TYPE_CODE  " + 			                
			                "order by \n" + 
			                "voucher_no";			            
				            
				            System.out.println("TDAR/TCAR SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						            ps.setInt(2,cmbOffice_code);
						            ps.setInt(3,txtCB_Year);
						            ps.setInt(4,txtCB_Month);
						            ps.setString(5,cmbStatus);
						            ps.setInt(6,cmbAcc_UnitCode);
						            ps.setInt(7,cmbOffice_code);
						            ps.setInt(8,txtCB_Year);
						            ps.setInt(9,txtCB_Month);
						            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
						            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
						                    xml=xml+"<leng>";
						                    xml=xml+"<vou_no>"+rs.getInt("VOUCHER_NO")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("voucher_date")+"</vou_date>";
						                    xml=xml+"<Remak>"+rs.getString("REMARKS")+"</Remak>";
						                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";			
						                    xml=xml+"<journal_type>"+rs.getString("JOURNAL_TYPE_DESC")+"</journal_type>";
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count==0) 
					                {
						                    System.out.println("inside count==0");
						                    xml="<response><command>searchByMonth</command><flag>failure</flag>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByMonth</command><flag>failure</flag>";
				            }
                }  */
            
        }
        System.out.println("here searchByDate"+strType.equalsIgnoreCase("searchByDate"));
        if(strType.equalsIgnoreCase("searchByDate"))  
        {
	        	String[] sd=request.getParameter("txtFrom_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtFrom_date=new Date(d.getTime());
	            System.out.println("from_date "+txtFrom_date);
	            
	            sd=request.getParameter("txtTo_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            d=c.getTime();
	            txtTo_date=new Date(d.getTime());
	            System.out.println("txtTo_date "+txtTo_date);
	            xml="<response><command>searchByDate</command>";                        
	            if(journal_type.equals("TDAO")||journal_type.equals("TCAO"))
                {
                    xml=xml+"<jtype>"+journal_type+"</jtype>";
				            sql="select voucher_no,\n" + 
				                "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
				                "      particulars,\n" + 
				                "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" + 
				                "      orginating_jvr_no as GJV_No,\n" + 
				                "      to_char(orginating_jvr_date,'DD/MM/YYYY') as GJV_Date ,\n" +
				                "      ACCEPTING_JVR_NO," +
				                "      to_char(ACCEPTING_JVR_DATE,'DD/MM/YYYY')as accepting_date " + 
				                "from fas_tda_tca_raised_mst \n" + 
				                "where    \n" + 
				                "      accounting_unit_id=? and  \n" + 
				                "      accounting_for_office_id=? and  \n" + 
				                "      VOUCHER_DATE between ? and ? and\n" +  
				                "      status=? and  SUPPLEMENT_NO="+supNo+" and\n" + 
                                                "      (tda_or_tca=?  or tda_or_tca=?)\n" + 
				                "order by\n" + 
				                "      voucher_no";
				            
				            System.out.println("SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						            ps.setInt(2,cmbOffice_code);
						            ps.setDate(3,txtFrom_date);
						            ps.setDate(4,txtTo_date);
						            ps.setString(5,cmbStatus);
						            ps.setString(6,journal_type);
                                                            if(journal_type.equals("TDAO")) {
                                                                ps.setString(7,"TDACB");
                                                            }
                                                            else if(journal_type.equals("TCAO")) {
                                                                ps.setString(7,"TCACB");
                                                            }
                                                            
						            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
						            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
                                                  
						                    xml=xml+"<leng>";
						                    xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
						                    xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
						                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
						                    if(journal_type.equals("TDAO")||journal_type.equals("TCAO"))
						                    {
								                    if(rs.getInt("GJV_No")!=0)
									                    	xml=xml+"<GJV_No>"+rs.getInt("GJV_No") +"</GJV_No>";
								                    else
								                    		xml=xml+"<GJV_No>--</GJV_No>";
								                    
								                    if(rs.getString("GJV_Date")==null)
								                    		xml=xml+"<GJV_Date>--</GJV_Date>";
								                    else
									                    	xml=xml+"<GJV_Date>"+rs.getString("GJV_Date") +"</GJV_Date>";
						                    }
						                    else
						                    {
							                    	if(rs.getInt("ACCEPTING_JVR_NO")!=0)
								                    	xml=xml+"<GJV_No>"+rs.getInt("ACCEPTING_JVR_NO") +"</GJV_No>";
								                    else
								                    		xml=xml+"<GJV_No>--</GJV_No>";
								                    
								                    if(rs.getString("accepting_date")==null)
								                    		xml=xml+"<GJV_Date>--</GJV_Date>";
								                    else
									                    	xml=xml+"<GJV_Date>"+rs.getString("accepting_date") +"</GJV_Date>";
						                    }
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count==0) 
					                {
						                    System.out.println("inside count==0");
						                    xml="<response><command>searchByDate</command><flag>failure</flag><jtype>"+journal_type+"</jtype>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByDate</command><flag>failure</flag><jtype>"+journal_type+"</jtype>";
				            }
                }
                else if(journal_type.equals("TDAA")||journal_type.equals("TCAA")) 
                {
                
                    xml=xml+"<jtype>"+journal_type+"</jtype>";
                    if(journal_type.equals("TDAA")) {
                        jType1="TDAO";
                        jType2="TDACB";
                    }
                    else if(journal_type.equals("TCAA")) {
                        jType1="TCAO";
                        jType2="TCACB";
                    }
                    sql="SELECT voucher_no as Org_Advice_No,\n" + 
                    "  TO_CHAR(voucher_date,'DD/MM/YYYY') AS Org_Advice_date ,\n" + 
                    "  TRF_ACCOUNTING_UNIT_ID,\n" + 
                    "  (select aa.Accounting_unit_name from fas_mst_acct_units aa \n" + 
                    "  where aa.accounting_unit_id= TRF_ACCOUNTING_UNIT_ID) as Accept_Unit,\n" + 
                    "  trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount,\n" + 
                    "  ACCEPTING_SLNO                               AS Accept_Advice_No,\n" + 
                    "  TO_CHAR(ACCEPTING_DATE,'DD/MM/YYYY')       AS Accept_Advice_Date" +
                    " FROM fas_tda_tca_raised_mst\n" + 
                    " WHERE accounting_unit_id    =?\n" + 
                    " AND accounting_for_office_id=?\n" + 
                    " AND VOUCHER_DATE between ? and ? \n"+
                    " AND status                  ='L' and  SUPPLEMENT_NO="+supNo+" \n" + 
                    " and ACCEPTANCE_STATUS='Y'\n" + 
                    " and ORGINATING_JVR_NO is not null\n" + 
                    " and ORGINATING_JVR_DATE is not null\n" + 
                    " and (ACCEPTING_SLNO is not null or ACCEPTING_SLNO!=0)\n" + 
                    " AND (tda_or_tca              =? or tda_or_tca=?)\n" + 
                    " ORDER BY voucher_no";
                    
                    System.out.println("SQL::::"+sql);
                    try
                    {
                                    int count=0;
                                    ps=con.prepareStatement(sql);
                                    ps.setInt(1,cmbAcc_UnitCode);
                                    ps.setInt(2,cmbOffice_code);
                                    ps.setDate(3,txtFrom_date);
                                    ps.setDate(4,txtTo_date);
                                if(journal_type.equals("TDAA")) {
                                    ps.setString(5,jType1);
                                    ps.setString(6,jType2);
                                }
                                else if(journal_type.equals("TCAA")) {
                                    ps.setString(5,jType1);
                                    ps.setString(6,jType2);
                                }
                                System.out.println("jType1"+jType1+":::"+jType2);  
                                    xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                    "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                              //      System.out.println("xml:::"+xml);
                                    rs=ps.executeQuery();
                                while(rs.next())
                                {
                             //   System.out.println("while");
                                            xml=xml+"<leng>";
                                            xml=xml+"<vou_no>"+rs.getInt("Org_Advice_No")+"</vou_no>";
                                            xml=xml+"<vou_date>"+rs.getString("Org_Advice_date")+"</vou_date>";
                                            xml=xml+"<unitName>"+rs.getString("Accept_Unit")+"</unitName>";
                                            xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                            xml=xml+"<acc_adNo>"+rs.getInt("Accept_Advice_No") +"</acc_adNo>";
                                            xml=xml+"<acc_adDate>"+rs.getString("Accept_Advice_Date") +"</acc_adDate>";
                                            xml=xml+"</leng>";
                                            count++;
                                }
                                if(count==0) 
                                {
                                            System.out.println("inside count==0");
                                            xml="<response><command>searchByDate</command><flag>failure</flag><jtype>"+journal_type+"</jtype>";
                                }
                    }
                    catch(SQLException sqle)
                    {
                                    sqle.printStackTrace();
                                    System.out.println("error while fetching data " + sqle);
                                xml="<response><command>searchByDate</command><flag>failure</flag><jtype>"+journal_type+"</jtype>";
                    }
                    
                }
	 /*           else 
                {
			                sql="select\n" + 
			                "  * \n" + 
			                "from\n" + 
			                "(\n" + 
			                "    select \n" + 
			                "          ACCOUNTING_UNIT_ID,\n" + 
			                "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
			                "          ACCEPTING_JVR_NO,\n" + 
			                "          extract(year from ACCEPTING_JVR_DATE)as cashbook_year,\n" + 
			                "          extract(month from ACCEPTING_JVR_DATE)as cashbook_month,\n" +
			                "		   trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as total_amount " + 
			                "    from  fas_tda_tca_raised_mst \n" + 
			                "    where    \n" + 
			                "          accounting_unit_id=? and  \n" + 
			                "          accounting_for_office_id=? and  \n" + 
			                "          ACCEPTING_JVR_DATE between ? and ? and  \n" + 
			                "          status=? and\n" + 
			                "          ACCEPTING_JVR_NO is not null and \n" + 
			                "          ACCEPTING_JVR_DATE is not null\n" + 
			                ")a inner join \n" + 
			                "(\n" + 
			                "    select\n" + 
			                "          VOUCHER_NO,\n" + 
			                "          to_char(VOUCHER_DATE,'DD/MM/YYYY') as voucher_date,\n" + 
			                "          JOURNAL_TYPE_CODE,\n" + 
			                "          REMARKS \n" + 
			                "    from  FAS_JOURNAL_MASTER    \n" + 
			                "    where  \n" + 
			                "          accounting_unit_id=? and  \n" + 
			                "          accounting_for_office_id=? and\n" + 
			                "          VOUCHER_DATE between ? and ?\n" + 
			                ")b on a.ACCEPTING_JVR_NO=b.VOUCHER_NO left outer join " +
			                "( " +
			                "		   select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE  " +
			                ")c on b.JOURNAL_TYPE_CODE=c.JOURNAL_TYPE_CODE  " + 			                
			                "order by \n" + 
			                "voucher_no";			            
				            
				            System.out.println("TDAR/TCAR SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						            ps.setInt(2,cmbOffice_code);
						            ps.setDate(3,txtFrom_date);
						            ps.setDate(4,txtTo_date);
						            ps.setString(5,cmbStatus);
						            ps.setInt(6,cmbAcc_UnitCode);
						            ps.setInt(7,cmbOffice_code);
						            ps.setDate(8,txtFrom_date);
						            ps.setDate(9,txtTo_date);
						            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
						            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
						                    xml=xml+"<leng>";
						                    xml=xml+"<vou_no>"+rs.getInt("VOUCHER_NO")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("voucher_date")+"</vou_date>";
						                    xml=xml+"<Remak>"+rs.getString("REMARKS")+"</Remak>";
						                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";			
						                    xml=xml+"<journal_type>"+rs.getString("JOURNAL_TYPE_DESC")+"</journal_type>";
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count==0) 
					                {
						                    System.out.println("inside count==0");
						                    xml="<response><command>searchByMonth</command><flag>failure</flag>";
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByMonth</command><flag>failure</flag>";
				            }
                }  */
            
        }
        xml=xml+"</response>";   
        out.println(xml); 
        System.out.println(xml); 
    }
}
