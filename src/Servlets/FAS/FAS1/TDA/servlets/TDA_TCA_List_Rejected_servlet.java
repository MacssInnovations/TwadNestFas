package Servlets.FAS.FAS1.TDA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TDA_TCA_List_Rejected_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TDA_TCA_List_Rejected_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
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
         
        System.out.println("servlet called ******");
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
        int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
        Date txtFrom_date=null,txtTo_date=null;
        Calendar c;
        String sql="",cmbStatus="",journal_type="",cmbJournal_type="",type1="";
	    String query1="";
        
       
       
        if(strType.equalsIgnoreCase("searchByMonth"))  
        {
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
            txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
            cmbStatus=request.getParameter("cmbStatus");
            cmbJournal_type=request.getParameter("cmbJournal_type");
                type1=request.getParameter("type1");
        if(cmbJournal_type.equals("62A")){
           query1="accounting_unit_id    =?";    
        }
        else if(cmbJournal_type.equals("63B")) {
            query1="TRF_ACCOUNTING_UNIT_ID    =?";
        }
        else if(cmbJournal_type.equals("62B")) {
            query1="accounting_unit_id    =?";
        }
        else {//63A
            query1="TRF_ACCOUNTING_UNIT_ID    =?";    
        }
        
	            xml="<response><command>searchByMonth</command>";                        
	            
				            sql="SELECT voucher_no, "+
		                       "  TO_CHAR(voucher_date,'DD/MM/YYYY') AS vou_date , "+
				            	" particulars, "+
				            	" trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount   "+                   
				            	" FROM fas_tda_tca_raised_mst "+                       
				            	" WHERE " +query1+
                                                " AND  cashbook_year  =? "+
				            	" AND   cashbook_month   =? "+
				            	" AND    status     =? "+
				            	" AND   (tda_or_tca  =? or tda_or_tca  =? )"+ 
				            	" AND (ACCEPTANCE_STATUS='N' OR ACCEPTANCE_STATUS    ='R') "+
				            	" AND ORGINATING_JVR_NO is not null "+
				            	" ORDER BY  voucher_no";
				            
				            System.out.println("SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						       //     ps.setInt(2,cmbOffice_code);
						            ps.setInt(2,txtCB_Year);
						            ps.setInt(3,txtCB_Month);
						            ps.setString(4,cmbStatus);
                                                            ps.setString(5,type1);
                                                            System.out.println("type1::::"+type1);
                                                            if(type1.equals("TDAO")) {
                                                                ps.setString(6,"TDACB");
                                                            }
                                                            else if(type1.equals("TCAO")) {
                                                                ps.setString(6,"TCACB");
                                                            }
						          
						            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
						            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
						            rs=ps.executeQuery();
					                while(rs.next())
					                {System.out.println("while");
						                    xml=xml+"<leng>";
						                    xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
						                    xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
						                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
						                    
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
             
        }
        else if(strType.equalsIgnoreCase("searchByDate"))  
        {
        
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
            txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
            cmbStatus=request.getParameter("cmbStatus");
            cmbJournal_type=request.getParameter("cmbJournal_type");
                type1=request.getParameter("type1");
        
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
            /*if(cmbJournal_type.equals("62A") ||cmbJournal_type.equals("63A")){
               query1="accounting_unit_id    =?";    
            }
            else {
                query1="TRF_ACCOUNTING_UNIT_ID    =?";    
            }  */
            
             if(cmbJournal_type.equals("62A")){
                query1="accounting_unit_id    =?";    
             }
             else if(cmbJournal_type.equals("63B")) {
                 query1="TRF_ACCOUNTING_UNIT_ID    =?";
             }
             else if(cmbJournal_type.equals("62B")) {
                 query1="accounting_unit_id    =?";
             }
             else {//63A
                 query1="TRF_ACCOUNTING_UNIT_ID    =?";    
             }
	           
			            sql="SELECT voucher_no, "+
		                "  TO_CHAR(voucher_date,'DD/MM/YYYY') AS vou_date , "+
			            	" particulars, "+
			            	" trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount   "+                   
			            	" FROM fas_tda_tca_raised_mst "+                       
			            	" WHERE " +query1+
                                        " AND  VOUCHER_DATE between ? and   ? "+
			            	" AND    status     =? "+
			            	" AND   (tda_or_tca  =? or tda_or_tca  =?) "+ 
			            	" AND (ACCEPTANCE_STATUS='N' OR ACCEPTANCE_STATUS    ='R') "+
			            	" AND ORGINATING_JVR_NO is not null "+
			            	" ORDER BY  voucher_no";
						            
				            System.out.println("SQL::::"+sql);
				            try
				            {
                                          
						            int count=0;
						            ps=con.prepareStatement(sql);
						            ps.setInt(1,cmbAcc_UnitCode);
						   //         ps.setInt(2,cmbOffice_code);
						            ps.setDate(2,txtFrom_date);
						            ps.setDate(3,txtTo_date);
						            ps.setString(4,cmbStatus);
                                                            ps.setString(5,type1);
                                                            if(type1.equals("TDAO")) {
                                                                ps.setString(6,"TDACB");
                                                            }
                                                            else if(type1.equals("TCAO")) {
                                                                ps.setString(6,"TCACB");
                                                            }
                                                            System.out.println("lasttttttttttttttt");
						            xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
						            "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
				                System.out.println("xml**********************"+xml);
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
                                                        System.out.println("whileeeeeeeeeee");
						                    xml=xml+"<leng>";
						                    xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
						                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
						                    xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
						                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
						                    
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
             
        }
        
        //Supplement no load
        else if (strType.equalsIgnoreCase("Check_Supplement_No")) {
        	
        	
        	int txtCash_year=0,txtCash_Month_hid=0;
        	 String[] sd=request.getParameter("txtFrom_date").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
             java.util.Date d=c.getTime();
             txtFrom_date=new Date(d.getTime());
             System.out.println("from_date "+txtFrom_date);
             
        	
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
             
             

            Statement st = null;
           // ResultSet rs = null;
           // String sql = null;
            int supplement_no = 0;

            xml = "<response><command>Check_Supplement_No</command>";

            sql =
 "select SUPPLEMENT_NO from FAS_SUPPLEMENT_GJV where status='L' and cashbook_year = " +
   txtCash_year + " and cashbook_month =" + txtCash_Month_hid+" order by SUPPLEMENT_NO";

            System.out.println("supnoo "+sql);

            try {
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                
                    xml = xml + "<supno>"+rs.getInt("SUPPLEMENT_NO")+"</supno>";
                    supplement_no++;
                }
                if (supplement_no>0) {
                    xml = xml + "<flag>success</flag>";
                    
                } 
                else if (supplement_no <= 0) {
                    xml = xml + "<flag>failure</flag>";
                    xml =xml + "<suppl_error>No Live Supplement Number</suppl_error>";
                }
             

             

            } catch (Exception e) {
                System.out.println("Unable to load Supplement Number " + e);
            }


        }
        //searchByDate_verify_supp
        else  if(strType.equalsIgnoreCase("searchByDate_verify_supp"))  
        {
       
            xml="<response><command>searchByDate_verify_supp</command>"; 
            int supNo=0;
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            try{supNo=Integer.parseInt(request.getParameter("supNo"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            
            
            
                    String[] sd=request.getParameter("txtFrom_date").split("/");
                     c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                     java.util.Date d=c.getTime();
                     txtFrom_date=new Date(d.getTime());
                     System.out.println("from_date "+txtFrom_date);
                    
                         sql="SELECT mst.VOUCHER_NO as v_no,\n" + 
                         " to_char(mst.VOUCHER_DATE,'dd-mm-yyyy')as VOUCHER_DATE,\n" + 
                         "  mst.REASON_FOR_NON_ACCEPT,\n" + 
                         "  mst.TOTAL_AMOUNT,\n" + 
                         "  mst.PARTICULARS,mst.SUPPLEMENT_NO,\n" + 
                         "  mst.ACCOUNTING_UNIT_ID,\n" + 
                         "  (SELECT unit.ACCOUNTING_UNIT_NAME\n" + 
                         "  FROM fas_mst_acct_units unit\n" + 
                         "  WHERE unit.ACCOUNTING_UNIT_ID=mst.ACCOUNTING_UNIT_ID\n" + 
                         "  ) AS unit_name\n" + 
                         " FROM fas_tda_tca_raised_mst mst\n" + 
                         " WHERE mst.TRF_ACCOUNTING_UNIT_ID= ?\n" + 
                         " AND mst.ACCEPTANCE_STATUS       ='N'\n" + 
                         " AND mst.REASON_FOR_NON_ACCEPT   ='1'\n" + 
                         " AND mst.ACCEPTING_DATE =? "+//AND mst.SUPPLEMENT_NO=? " +
                         "order by mst.VOUCHER_NO";
                     
                     
                        System.out.println("SQL::::"+sql);
                        try
                        {
                                        int count=0;
                                        ps=con.prepareStatement(sql);
                                        ps.setInt(1,cmbAcc_UnitCode);
                                        ps.setDate(2,txtFrom_date);
                                       // ps.setInt(3,supNo);
                                       System.out.println("be4 qqqq");
                                       rs=ps.executeQuery();
                                        while(rs.next())
                                        {
                                           
                                                    xml=xml+"<leng>";
                                                    xml=xml+"<vou_no>"+rs.getInt("v_no")+"</vou_no>";
                                                    xml=xml+"<vou_date>"+rs.getString("VOUCHER_DATE")+"</vou_date>";
                                                   xml=xml+"<reason>"+rs.getString("REASON_FOR_NON_ACCEPT")+"</reason>";
                                                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                    xml=xml+"<parti><![CDATA["+rs.getString("PARTICULARS") +"]]></parti>";
                                                    xml=xml+"<unitid>"+rs.getInt("ACCOUNTING_UNIT_ID") +"</unitid>";
                                                    xml=xml+"<unitname>"+rs.getString("unit_name") +"</unitname>";
                                                    xml=xml+"<supNNO>"+rs.getInt("SUPPLEMENT_NO") +"</supNNO>";
                                                    xml=xml+"</leng>";
                                            xml=xml+"<flag>success</flag>";
                                                    count++;
                                        }
                                        if(count==0) 
                                        {
                                                    System.out.println("inside count==0");
                                                    xml="<response><command>searchByDate_verify_supp</command><flag>failure</flag>";
                                        }
                        }
                        catch(SQLException sqle)
                        {
                                        sqle.printStackTrace();
                                        System.out.println("error while fetching data " + sqle);
                                        xml="<response><command>searchByDate_verify_supp</command><flag>failure</flag>";
                        }
            
        }
        
        
        else  if(strType.equalsIgnoreCase("searchByDate_verify"))  
        {
       
            xml="<response><command>searchByDate_verify</command>"; 
            
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
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
                    
                         sql="SELECT mst.VOUCHER_NO as v_no,\n" + 
                         " to_char(mst.VOUCHER_DATE,'dd-mm-yyyy')as VOUCHER_DATE,\n" + 
                         "  mst.REASON_FOR_NON_ACCEPT,\n" + 
                         "  mst.TOTAL_AMOUNT,\n" + 
                         "  mst.PARTICULARS,\n" + 
                         "  mst.ACCOUNTING_UNIT_ID,\n" + 
                         "  (SELECT unit.ACCOUNTING_UNIT_NAME\n" + 
                         "  FROM fas_mst_acct_units unit\n" + 
                         "  WHERE unit.ACCOUNTING_UNIT_ID=mst.ACCOUNTING_UNIT_ID\n" + 
                         "  ) AS unit_name\n" + 
                         " FROM fas_tda_tca_raised_mst mst\n" + 
                         " WHERE mst.TRF_ACCOUNTING_UNIT_ID= ?\n" + 
                         " AND mst.ACCEPTANCE_STATUS       ='N'\n" + 
                      //   " AND mst.REASON_FOR_NON_ACCEPT   ='1'\n" + 
                         " AND mst.VOUCHER_DATE BETWEEN ? AND ? order by mst.VOUCHER_NO";
                     
                     
                        System.out.println("SQL::::"+sql);
                        try
                        {
                                        int count=0;
                                        ps=con.prepareStatement(sql);
                                        ps.setInt(1,cmbAcc_UnitCode);
                                        ps.setDate(2,txtFrom_date);
                                        ps.setDate(3,txtTo_date);
                                       System.out.println("be4 qqqq");
                                       rs=ps.executeQuery();
                                        while(rs.next())
                                        {
                                           
                                                    xml=xml+"<leng>";
                                                    xml=xml+"<vou_no>"+rs.getInt("v_no")+"</vou_no>";
                                                    xml=xml+"<vou_date>"+rs.getString("VOUCHER_DATE")+"</vou_date>";
                                                   xml=xml+"<reason>"+rs.getString("REASON_FOR_NON_ACCEPT")+"</reason>";
                                                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                    xml=xml+"<parti><![CDATA["+rs.getString("PARTICULARS") +"]]></parti>";
                                                    xml=xml+"<unitid>"+rs.getInt("ACCOUNTING_UNIT_ID") +"</unitid>";
                                                    xml=xml+"<unitname>"+rs.getString("unit_name") +"</unitname>";
                                                    xml=xml+"</leng>";
                                            xml=xml+"<flag>success</flag>";
                                                    count++;
                                        }
                                        if(count==0) 
                                        {
                                                    System.out.println("inside count==0");
                                                    xml="<response><command>searchByDate_verify</command><flag>failure</flag>";
                                        }
                        }
                        catch(SQLException sqle)
                        {
                                        sqle.printStackTrace();
                                        System.out.println("error while fetching data " + sqle);
                                        xml="<response><command>searchByDate_verify</command><flag>failure</flag>";
                        }
            
        }
        
        else if(strType.equalsIgnoreCase("rjvRejected"))
        {
            xml="<response><command>rjvRejected</command>"; 
            
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
           
            sql="select mst.VOUCHER_NO,\n" + 
            " to_char(mst.VOUCHER_DATE,'dd-mm-yyyy')as VOUCHER_DATE,\n" + 
            " mst.TDA_OR_TCA,\n" + 
            " mst.TOTAL_AMOUNT,\n" + 
            " mst.TRF_ACCOUNTING_UNIT_ID,\n" + 
            " (select unit.ACCOUNTING_UNIT_NAME from fas_mst_acct_units unit " +
            "where unit.accounting_unit_id=mst.TRF_ACCOUNTING_UNIT_ID) as unitName,\n" + 
            " mst.PARTICULARS,\n" + 
            " mst.REASON_FOR_NON_ACCEPT,to_char(Mst.Accepting_Date,'dd-mm-yyyy') As RejectedDate \n" + 
            " from FAS_TDA_TCA_RAISED_MST mst\n" + 
            " where mst.ACCEPTANCE_STATUS='R' and mst.REASON_FOR_NON_ACCEPT='1' and (mst.RESPONDING_JVR_NO is null or mst.RESPONDING_JVR_NO=0) and mst.RESPONDING_JVR_DATE is null\n" + 
            " and mst.ACCOUNTING_UNIT_ID=?\n" + 
            " and mst.ACCOUNTING_FOR_OFFICE_ID=?\n" + 
            " and mst.STATUS='L' order by mst.VOUCHER_NO";
            
            
            System.out.println("SQL::::"+sql);
            try
            {
                           int count=0;
                           ps=con.prepareStatement(sql);
                           ps.setInt(1,cmbAcc_UnitCode);
                           ps.setInt(2,cmbOffice_code);
                          
                          rs=ps.executeQuery();
                           while(rs.next())
                           {
                          
                                       xml=xml+"<leng>";
                                       xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                       xml=xml+"<vou_date>"+rs.getString("VOUCHER_DATE")+"</vou_date>";
                                       xml=xml+"<rejected_date>"+rs.getString("RejectedDate")+"</rejected_date>";
                                       xml=xml+"<tda_type>"+rs.getString("TDA_OR_TCA")+"</tda_type>";
                                       xml=xml+"<reason>"+rs.getString("REASON_FOR_NON_ACCEPT")+"</reason>";
                                       xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                       xml=xml+"<parti><![CDATA["+rs.getString("PARTICULARS") +"]]></parti>";
                                       xml=xml+"<unitid>"+rs.getInt("TRF_ACCOUNTING_UNIT_ID") +"</unitid>";
                                       xml=xml+"<unitname>"+rs.getString("unitName") +"</unitname>";
                                       xml=xml+"</leng>";
                               xml=xml+"<flag>success</flag>";
                                       count++;
                           }
                           if(count==0) 
                           {
                                       System.out.println("inside count==0");
                                       xml="<response><command>rjvRejected</command><flag>failure</flag>";
                           }
            }
            catch(SQLException sqle)
            {
                           sqle.printStackTrace();
                           System.out.println("error while fetching data " + sqle);
                           xml="<response><command>rjvRejected</command><flag>failure</flag>";
            }
            
        }
        xml=xml+"</response>";   
        out.println(xml); 
        System.out.println(xml); 
        
	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    System.out.println("dopostttttttttttttttttttttttttttt");

            HttpSession session=request.getSession(false);
            try
            {                  
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
            /**
             * Variables Declaration 
             */                     
            Connection con=null;
            PreparedStatement ps2=null,ps=null;        
            ResultSet rs2=null,rs=null;
            Date txtCrea_date=null;
            Calendar c;
            int txtCash_year=0,txtCash_Month_hid=0;
            /**
             * Database Connection 
             */
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
            {
                    System.out.println("Exception in opening connection :"+e);
            }
            int cmbAcc_UnitCode=0,cmbOffice_code=0;

            
            
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);                      
             Date ctdate = new java.sql.Date(ts.getTime());  
            //String txtFrom_date="";
            
            Date txtFrom_date=null,txtCreate_Date=null;
            
            String chckparameter_Voucher_no[]=null; 
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
    //      System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);

            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
    //      System.out.println("cmbOffice_code "+cmbOffice_code);
            
           /* try{txtFrom_date=request.getParameter("txtFrom_date");}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            */
            
            
            
            //sheron date change
            
            //txtCreate_Date
            String[] sd2=request.getParameter("txtCreate_Date").split("/");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            String dateString = String.join("/", sd2);
            try {
				java.util.Date date1 =  sdf.parse(dateString);
	
				txtCreate_Date = new java.sql.Date(date1.getTime());
				
				
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
            
            //txtFrom_date
            String[] sd1=request.getParameter("txtFrom_date").split("/");
            	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            
            String dateString1 = String.join("/", sd1);
            try {
				java.util.Date date2 =  sdf.parse(dateString1);
	
				txtFrom_date = new java.sql.Date(date2.getTime());
				
				
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
            
            
            //sheron close
            
            
            //uncomment this
//            c=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
//            java.util.Date d2=c.getTime();
//            txtCreate_Date=new Date(d2.getTime());
            
          //  String[] sd1=request.getParameter("txtFrom_date").split("/");
//            c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
//            java.util.Date d1=c.getTime();
//            txtFrom_date=new Date(d1.getTime());
            //
            
            
            String[] voucherno1=request.getParameterValues("v_number");
            //System.out.println("voucherno1"+voucherno1);
           
            int voucherno2=0;
            try{
                    con.clearWarnings();
                    con.setAutoCommit(false);
                    chckparameter_Voucher_no = request.getParameterValues("chckparameter"); 
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~chckparameter_Voucher_no>>>>"+chckparameter_Voucher_no);
                
                
                    System.out.println("chckparameter_Voucher_no>>>"+chckparameter_Voucher_no.length);
                
                    for(int i=0;i<chckparameter_Voucher_no.length;i++)
                    {
                    int asg=Integer.parseInt(chckparameter_Voucher_no[i]);
                    
                    
                     System.out.println("b4 getting month and year");
                     
                   
                            ps=con.prepareStatement("UPDATE FAS_TDA_TCA_RAISED_MST\n" + 
                            " SET ACCEPTANCE_STATUS       ='R',ACCEPTING_DATE=? " + 
                            " WHERE TRF_ACCOUNTING_UNIT_ID=?\n" + 
                            " AND voucher_no              =? "
                            // Joan Changed on 24 Apr 2015 
                       //     + "and REASON_FOR_NON_ACCEPT=1 "
                            + "");
                          // ps.setDate(1, ctdate);
                            ps.setDate(1,txtCreate_Date);
                            ps.setInt(2, cmbAcc_UnitCode); 
                        voucherno2=Integer.parseInt(voucherno1[asg]); 
                            ps.setInt(3, voucherno2); 
                          
                            int up=ps.executeUpdate();  
         
                            if(up==0)
                            {
                                System.out.println("redirect");                                
                                sendMessage(response," TDA/TCA verification Failed ","ok"); 
                            }else{
                                    sendMessage(response,"The TDA/TCA Voucher Number  has been Verified ","ok");   
                                con.commit();
                            }

                    }  
            
            }
    catch(Exception e){System.out.println(e);e.printStackTrace();
            try {
                    con.rollback();
            }
            catch (SQLException e1) {
                    e1.printStackTrace();
            }
            }
            try{
                    con.commit();
            }catch(Exception e){System.out.println(e);}
            
    }
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
    try
    {
        String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
        response.sendRedirect(url);
    }
    catch(Exception e)
    {
            System.out.println("error in messenger"+e);
    }
    }

}
