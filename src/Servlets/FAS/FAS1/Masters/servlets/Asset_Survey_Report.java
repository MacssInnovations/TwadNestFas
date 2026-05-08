package Servlets.FAS.FAS1.Masters.servlets;

import Servlets.Security.classes.UserProfile;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Asset_Survey_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Asset_Survey_Report() {
        super();
        
    }

	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        
        String update_user=(String)session.getAttribute("UserId");
        //System.out.println("update_user"+update_user);
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        Connection con=null;
        ResultSet rs=null;
	    ResultSet rs2=null;
        PreparedStatement ps=null;
	PreparedStatement ps2=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
	catch(Exception e)
	{
	  System.out.println("Exception in openeing connection :"+e);
	}
	Date approvaldate=null;
	
	
	String CONTENT_TYPE = "text/xml; charset=windows-1252";
	response.setContentType(CONTENT_TYPE);
	PrintWriter out = response.getWriter();
	String strType = "",xml="<response>";
        int strcode=0;
	int cmbAcc_UnitCode=0;
       
	try
	{
	  strType = request.getParameter("Command");
		//System.out.println(strType);
	}
	catch(Exception e)
	{
	  e.printStackTrace();
	}
	int  txtCB_Year=0, txtCB_Month=0,cmbOffice_code=0,max_survey_no=0;
	Date txtFrom_date=null,txtTo_date=null;
	Calendar c;
	String sql="",finYear="",cmbStatus="",verifyEmp1="";
        String offname="";
	Date Surveydate=null;
	try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	catch(NumberFormatException e){System.out.println("exception"+e );}
	//System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
	
	try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	catch(NumberFormatException e){System.out.println("exception"+e );}  
        //System.out.println("cmbOffice_code"+cmbOffice_code);

	finYear=request.getParameter("finYear");
        //System.out.println(finYear);
	    if(strType.equalsIgnoreCase("loademp") ||(strType.equalsIgnoreCase("loadempview"))) {
		    strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
		    //System.out.println("assign..... Code::"+strcode);
	    
	        xml="<response><command>"+strType+"</command>" ;
                
	         try
	        {
	        
	          //  HttpSession session=request.getSession(false);
	            UserProfile up=null;
	            up=(UserProfile)session.getAttribute("UserProfile");
	            boolean flag=true;
	            ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
	            ps.setInt(1,strcode);
	            rs=ps.executeQuery();
	            if(!rs.next())
	            {
	               xml=xml+"<flag>failure</flag>";
	               flag=false;
	            }
	            else  if(up.getEmployeeId()!=strcode)
	             {
	                           int OfficeId=0;
	                           String sqlcont="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
	                           ps=con.prepareStatement(sqlcont);
	                           ps.setInt(1,strcode);
	                           rs=ps.executeQuery();
	                          
	                          if(rs.next()) {
	                               OfficeId=rs.getInt("CONTROLLING_OFFICE_ID");
	                           }

	                           if(OfficeId!=0)
	                           {
	                                  sql="select a.OFFICE_ID,b.office_name  from HRM_EMP_CURRENT_POSTING a,COM_MST_OFFICES b\n" + 
	                                      "where a.employee_id=? and a.office_id=b.office_id";
	                                   ps=con.prepareStatement(sql);
	                                   ps.setInt(1,up.getEmployeeId());
	                                   rs=ps.executeQuery();  
	                                    if(rs.next()) {
	                                       int offid=rs.getInt("OFFICE_ID");
                                              // offname=rs.getString("office_name");
	                                       if(offid!=OfficeId)
	                                       {
	                                           //System.out.println("Admin Session:"+session.getAttribute("Admin"));
	                                                   if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
	                                                   {//response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
	                                                    xml=xml+"<flag>failure1</flag>";
	                                                    flag=false;
	                                                   }
	                                       }
	                                   }
	                                   else
	                                   {
	                                          // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
	                                          
	                                                    xml=xml+"<flag>failure2</flag>";
	                                                    flag=false;
	                                           
	                                   }
	                           
	                           }
	                           else{
	                              // if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
	                               {
	                                       xml=xml+"<flag>failure3</flag>";
	                                       flag=false;
	                               }                               
	                           }
	             }
	             else {
	                 
	               //  xml=xml+"<flag>failure4</flag>";
	                //flag=false;
	             }
	                       
	            if(flag)
	                {
	                        //con.setAutoCommit(false);
	                         //System.out.println("ok1");
	                ps=con.prepareStatement("select a.EMPLOYEE_ID,\n" + 
	                "EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME, \n" + 
	                "GPF_NO, OFFICE_ID,\n" + 
	                "a.DESIGNATION_ID,c.DESIGNATION,\n" + 
	                "DATE_EFFECTIVE_FROM, a.REMARKS,\n" + 
	                "OFFICE_GRADE,DEPARTMENT_ID,a.PROCESS_FLOW_STATUS_ID, \n" + 
	                "EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,\n" + 
	                "c.DESIGNATION,\n" + 
	                "DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING a,\n" + 
	                "HRM_MST_EMPLOYEES b,HRM_MST_DESIGNATIONS c where \n" + 
	                "c.DESIGNATION_ID=a.DESIGNATION_ID \n" + 
	                "and b.EMPLOYEE_ID=a.EMPLOYEE_ID\n" + 
	                "and  a.EMPLOYEE_ID=? and \n" + 
	                "a.PROCESS_FLOW_STATUS_ID='FR'");
	                //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR'" );
	                ps.setInt(1,strcode);
	                rs=ps.executeQuery();
	                boolean oldrec=false;
	                if(!rs.next())
	                {
	                         xml=xml+"<flag>norecord</flag>";
	                        
	                }
	                else{
	                    oldrec=true;  //  data in temporary table is available
	                }
	                if(oldrec==true)
	                {
	                         //System.out.println("ok2");
	                         // 
	                         {     
                                    //System.out.println("select office_name from COM_MST_OFFICES where office_id="+rs.getInt("OFFICE_ID")+""); 
                                            ps=con.prepareStatement("select office_name from COM_MST_OFFICES where office_id="+rs.getInt("OFFICE_ID")+""); 
	                                     rs2=ps.executeQuery();  
	                                     
	                                     if(rs2.next()){
                                                 offname=rs2.getString("office_name");
                                                 //System.out.println("offname"+offname);
	                                     xml=xml+"<EMPLOYEE_ID>"+rs.getInt("EMPLOYEE_ID")+"</EMPLOYEE_ID>";
	                                     xml=xml+"<EMPLOYEE_NAME>"+rs.getString("EMPLOYEE_NAME")+"</EMPLOYEE_NAME>";
	                                     xml=xml+"<GPF_NO>"+rs.getInt("GPF_NO")+"</GPF_NO>";
	                                     xml=xml+"<OFFICE_ID>"+rs.getInt("OFFICE_ID")+"</OFFICE_ID>";
	                                     xml=xml+"<OFFICE>"+offname+"</OFFICE>";
	                                     xml=xml+"<DESIGNATION_ID>"+rs.getInt("DESIGNATION_ID")+"</DESIGNATION_ID>";
	                                     xml=xml+"<DESIGNATION>"+rs.getString("DESIGNATION")+"</DESIGNATION>";
	                                     if(rs.getDate("DATE_EFFECTIVE_FROM")!=null)
	                                     {
	                                      String[] sd1=rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
	                                      String od=sd1[2]+"/"+sd1[1]+"/"+sd1[0];
	                                     xml=xml+"<DATE_EFFECTIVE_FROM>"+od+"</DATE_EFFECTIVE_FROM>";
	                                     }
	                                     else {
	                                         xml=xml+"<DATE_EFFECTIVE_FROM>"+rs.getDate("DATE_EFFECTIVE_FROM")+"</DATE_EFFECTIVE_FROM>";
	                                         
	                                     }
	                                     
	                                     xml=xml+"<REMARKS>"+rs.getString("REMARKS")+"</REMARKS>";
	                                     xml=xml+"<OFFICE_GRADE>"+rs.getString("OFFICE_GRADE")+"</OFFICE_GRADE>";
	                                     xml=xml+"<DEPARTMENT_ID>"+rs.getString("DEPARTMENT_ID")+"</DEPARTMENT_ID>";
	                                     xml=xml+"<PROCESS_FLOW_STATUS_ID>"+rs.getString("PROCESS_FLOW_STATUS_ID")+"</PROCESS_FLOW_STATUS_ID>";
	                                     xml=xml+"<EMPLOYEE_STATUS_ID>"+rs.getString("EMPLOYEE_STATUS_ID")+"</EMPLOYEE_STATUS_ID>";
	                                     xml=xml+"<LEAVE_TYPE>"+rs.getString("LEAVE_TYPE_CODE")+"</LEAVE_TYPE>";
	                                     xml=xml+"<DATE_EFFECTIVE_FROM_SESSION>"+rs.getString("DATE_EFFECTIVE_FROM_SESSION")+"</DATE_EFFECTIVE_FROM_SESSION>";
	                                     xml=xml+"<DEPARTMENT_ID>"+rs.getString("DEPARTMENT_ID")+"</DEPARTMENT_ID>";
	                                     xml=xml+"<SERVICE_GROUP_ID>"+rs.getInt("SERVICE_GROUP_ID")+"</SERVICE_GROUP_ID>";
                                             }                             
	                                     rs.close(); 
	                                     ps.close();
	                                     
	                                     /*    wing  */
	                                                                          
	                                      int wing=0;
	                                     ps=con.prepareStatement("select OFFICE_WING_SINO from HRM_EMP_CURRENT_WING where employee_id=?" );
	                                     ps.setInt(1,strcode);
	                                     rs=ps.executeQuery();
	                                     if(rs.next()){
	                                          if(rs.getString("OFFICE_WING_SINO")!=null) {
	                                              wing=Integer.parseInt(rs.getString("OFFICE_WING_SINO"));
	                                          }
	                                     }
	                                       xml=xml+"<WING>"+wing+"</WING>";
	                                     
	                                     /* wing  */
	                                     
	                                    xml=xml+"<flag>success</flag>";
	                                 }
	                            
	                } 
	                
	            }
	               
	        }
	        catch(Exception e) {
	                    System.out.println("catch........"+e);
	                    xml=xml+"<flag>failure</flag>";
	        }
	       // xml=xml+"</response>";
	    }
	    
	    
	if(strType.equalsIgnoreCase("goCmd"))  
    {
        
        xml="<response><command>goCmd</command>";    
      /*  sql="SELECT a.ASSET_CODE,a.OPEN_BAL_QTY,a.OPENING_BAL_VALUE,a.NET_DEPRE_COST,DEPRE_UPTO_DATE as assessed_date from FAS_A52REGISTER a \n" + 
        " where a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and a.ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and a.FINANCIAL_YEAR='"+finYear+"'";
     // sql="SELECT ASSET_CODE,TOTAL_YEAR_QTY FROM FAS_A52REGISTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and FINANCIAL_YEAR='"+finYear+"'";
       */
        //joan Add on 22 JAn 2015
        
        sql="SELECT a.ASSET_CODE,a.OPEN_BAL_QTY,a.OPENING_BAL_VALUE,a.NET_DEPRE_COST,DEPRE_UPTO_DATE as assessed_date from FAS_A52_REGISTER_EDIT_LOG a \n" + 
                " where a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and a.ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and a.FINANCIAL_YEAR='"+finYear+"'";
       try
       {
        int count=0;
        ps=con.prepareStatement(sql);
        
        xml=xml+"<flag>success</flag>";
        rs=ps.executeQuery();
            while(rs.next())
            {
         	  
                xml=xml+"<leng>";
                xml=xml+"<acc_code>"+rs.getInt("ASSET_CODE")+"</acc_code>";
                xml=xml+"<qty>"+rs.getInt("OPEN_BAL_QTY")+"</qty>";
                xml=xml+"<bookvalue>"+rs.getInt("OPENING_BAL_VALUE")+"</bookvalue>";
                xml=xml+"<assvalue>"+rs.getInt("NET_DEPRE_COST")+"</assvalue>";
                xml=xml+"<assdate>"+rs.getString("assessed_date")+"</assdate>";
                xml=xml+"</leng>";
                count++;
            }
           if(count==0) 
           {
              System.out.println("inside count==0");
              xml="<response><command>goCmd</command><flag>failure</flag>";
           }
       }
       catch(SQLException sqle)
        {
    	   sqle.printStackTrace();
    	   System.out.println("error while fetching data " + sqle);
            xml="<response><command>goCmd</command><flag>failure</flag>";
        }
        
    }
    
    
	
	if(strType.equalsIgnoreCase("Add"))  
	{
		 xml="<response><command>Add</command>";
	/*	String[] txtDtFrm1=request.getParameter("txtDtFrm").split("/");
		System.out.println(txtDtFrm1[1]);
		int cashbookmonth=Integer.parseInt(txtDtFrm1[1]);
		System.out.println("cashbookmonth"+cashbookmonth);
		
		int cashbookyear=Integer.parseInt(txtDtFrm1[2]);
		
		verifyEmp1=request.getParameter("verifyEmp");
		System.out.println("emp>>>"+verifyEmp1);*/
	                 int RecordCount = 0;
	                 String filter = null;
	                 /*
	                  * Get Total Number of Records
	                  */
	                 try {
	                         filter = request.getParameter("filter");
	                 } catch (Exception e) {
	                         System.out.println("Error Getting filter value ");
	                 }

	                 /*
	                  * Get filter value
	                  */
	                 try {
	                         RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
	                 } catch (Exception e) {
	                         System.out
	                                         .println("Error Getting Total Number of Records in TWAD Transaction ");
	                 }

	                 /* String Array Declaration */
//	                 String check[] = new String[RecordCount];
//	                 String slno[] = new String[RecordCount];
//
//	                 String Category[] = new String[RecordCount];
//	                 String No_of_Pensioners[] = new String[RecordCount];
//	                 String Total_Basic_Pension_Upto_11Y[] = new String[RecordCount];
//	                 String Total_Basic_Pension_Anticipated_12Y_3Y1[] = new String[RecordCount];
//	                 String Total_D_A_Upto_11Y[] = new String[RecordCount];
	
		  String[] grid1=request.getParameter("grid").split(",");
	           int len=grid1.length;
			System.out.println("arraylength"+len);
			System.out.println("1>>>"+grid1[1]+"2>>"+grid1[2]+"3>>"+grid1[3]+"4>>>"+grid1[4]+"5>>"+grid1[5]+"6>>"+grid1[6]+"7>>>"+grid1[7]+"8>>"+grid1[8]+"9>>"+grid1[9]+"10>>>"+grid1[0]);
                        String qty=grid1[2];
                        String bvalue=grid1[3];
                        String avalue=grid1[4];
	           int surveyqty=Integer.parseInt(qty);
                   System.out.println(surveyqty);
                   int bookvalue=Integer.parseInt(bvalue);
                   System.out.println(bookvalue);
                   int assvalue=Integer.parseInt(avalue);
                   System.out.println(assvalue);
                   
            try {
	        ps2 = con.prepareStatement("SELECT nvl(max(SURVEY_NO),0) AS br_id FROM FAS_ASSET_SURVEY_REPORT where ACCOUNTING_UNIT_ID=?");
	        ps2.setInt(1, cmbAcc_UnitCode);
	        rs2 = ps2.executeQuery();

	        while (rs2.next()) {
	            max_survey_no = rs2.getInt("br_id");
	        }
	        if (max_survey_no == 0) {
	            max_survey_no = 1;
	        } else {
	            max_survey_no++;
	        }

	        System.out.println("Maximum Survey no is ==" + max_survey_no);

	        ps2.close();
	        rs2.close();

	    } catch (Exception e) {
	        System.out.println("Failed to Fetch Maximum Survey no " + e);
	    }
	    String[] sd = request.getParameter("txtsurveydate").split("/");
	     c = 
	    new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
	                 Integer.parseInt(sd[0]));
	     java.util.Date d = c.getTime();
	     Surveydate = new Date(d.getTime());
	     System.out.println("Surveydate " + Surveydate);
             
	    String[] sd3 = request.getParameter("txtappdate").split("/");
	     c = 
	    new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,
	                 Integer.parseInt(sd3[0]));
	     java.util.Date d3 = c.getTime();
	     approvaldate = new Date(d3.getTime());
	     System.out.println("txtappdate " + approvaldate);
             
		try{ String txtEmployeeid=request.getParameter("txtEmployeeid");
             int surveyempid=Integer.parseInt(txtEmployeeid);
             System.out.println("surveyempid"+surveyempid);
	     String txtOffice_Id=request.getParameter("txtOffice_Id");
	     int surveyoffid=Integer.parseInt(txtOffice_Id);
             System.out.println("surveyoffid"+surveyoffid);
	     String Desig_Id=request.getParameter("Desig_Id");
	     int assetcode=Integer.parseInt(grid1[1]);
		    int surveydesigid=Integer.parseInt(Desig_Id);
		    System.out.println("surveydesigid"+surveydesigid);
             System.out.println("surveydesigid"+surveydesigid);
             
		    String txtEmployeeid2=request.getParameter("txtEmployeeid2");
		    int appempid=Integer.parseInt(txtEmployeeid2);
                    System.out.println("appempid"+appempid);
		    String txtOffice_Id2=request.getParameter("txtOffice_Id2");
                    int appoffid=Integer.parseInt(txtOffice_Id2);
                    System.out.println(appoffid);
		    String Desig_Id2=request.getParameter("Desig_Id2");
		    int appdesigid=Integer.parseInt(Desig_Id2);
                    System.out.println("appdesigid"+appdesigid);
                    String txtrefno=request.getParameter("txtrefno");
		    int refno=Integer.parseInt(txtrefno);
                    System.out.println("refno"+refno);
                    Date refdate=null;
		    String[] sd1 = request.getParameter("txtrefdate").split("/");
		     c = 
		    new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
		                 Integer.parseInt(sd1[0]));
		     java.util.Date d1 = c.getTime();
		     refdate = new Date(d1.getTime());
		     System.out.println("refdate " + refdate);
                     
		    Date assdate=null;
                    Date BPdate=null;
                    Date Prodate=null;
		    String[] sdd = grid1[5].split("/");
		     c = 
		    new GregorianCalendar(Integer.parseInt(sdd[2]), Integer.parseInt(sdd[1]) - 1,
		                 Integer.parseInt(sdd[0]));
		     java.util.Date dd = c.getTime();
		     assdate = new Date(dd.getTime());
		     System.out.println("------------------------------assdate " + assdate);
                     
		    String[] sdd1 = grid1[10].split("/");
		     c = 
		    new GregorianCalendar(Integer.parseInt(sdd1[2]), Integer.parseInt(sdd1[1]) - 1,
		                 Integer.parseInt(sdd1[0]));
		     java.util.Date dd1 = c.getTime();
		     BPdate = new Date(dd1.getTime());
		     System.out.println("------------------------------BPdate " + BPdate);
                     int BPNo=Integer.parseInt(grid1[9]);
		    int ProNo=Integer.parseInt(grid1[11]);
		    String[] sdd2 = grid1[12].split("/");
		     c = 
		    new GregorianCalendar(Integer.parseInt(sdd2[2]), Integer.parseInt(sdd2[1]) - 1,
		                 Integer.parseInt(sdd2[0]));
		     java.util.Date dd2 = c.getTime();
		     Prodate = new Date(dd2.getTime());
		     System.out.println("------------------------------Prodate " + Prodate);
		     
                     
			ps=con.prepareStatement("insert into  FAS_ASSET_SURVEY_REPORT (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SURVEY_NO,SURVEY_DATE,SURVEY_DONE_BY, \n" + 
			"DESIGNATION,POSTING,APPROVAL_DATE,APPROVED_BY,APPROVAL_AUTHORITY_DESIGN,APPROVAL_AUTHORITY_POST, \n" + 
			"REF_NO,REF_DATE,ASSET_CODE,QUANTITY,BOOK_VALUE,ASSESED_VALUE,ASSESMENT_DATE,REMARKS_OFFICER,REMARKS_DIV_OFFICER,\n" + 
			"REMARKS_SE,BP_NO,BP_DATE,PROCEED_ORDER_NO,PROCEED_DATE, \n" + 
			"REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		System.out.println("ps");
	     ps.setInt(1,cmbAcc_UnitCode);
             ps.setInt(2,cmbOffice_code);
             ps.setString(3,finYear);
            // System.out.println("finYear"+finYear);
             ps.setInt(4,max_survey_no);
            // System.out.println(max_survey_no);
             ps.setDate(5,Surveydate);
           //  System.out.println(Surveydate);
             ps.setInt(6,surveyempid);
            // System.out.println(surveyempid);
	     ps.setInt(7,surveydesigid);
             //System.out.println(surveydesigid);
             ps.setInt(8,surveyoffid);
             //System.out.println(surveyoffid);
            
             ps.setDate(9,approvaldate);
             //System.out.println(approvaldate);
             ps.setInt(10,appempid);
             //System.out.println(appempid);
             ps.setInt(11,appdesigid);
             //System.out.println(appdesigid);
	     ps.setInt(12,appoffid);
             //System.out.println(appoffid);
             ps.setInt(13,refno);
             //System.out.println(refno);
             ps.setDate(14,refdate);
             //System.out.println(refdate);
             //System.out.println("refdate"+refdate);
             ps.setInt(15,assetcode);
             //System.out.println(assetcode);
             ps.setInt(16,surveyqty);
             //System.out.println(surveyqty);
             ps.setInt(17,bookvalue);
             //System.out.println(bookvalue);
             ps.setInt(18,assvalue);
             //System.out.println(assvalue);
             ps.setDate(19,assdate);
             //System.out.println(assdate);
          //   //System.out.println(grid1[5]);
             ps.setString(20,grid1[6]);
             //System.out.println("grid1[6]"+grid1[6]);
             ps.setString(21,grid1[7]);
	    //System.out.println("grid1[7]"+grid1[7]);
             ps.setString(22,grid1[8]);
            //System.out.println("grid1[8]"+grid1[8]);
             ps.setInt(23,BPNo);
             //System.out.println("BPNo"+BPNo);
             ps.setDate(24,BPdate);
             //System.out.println("BPdate"+BPdate);
       //      System.out.println(grid1[10]);
             ps.setInt(25,ProNo);
             //System.out.println("ProNo"+ProNo);
             ps.setDate(26,Prodate);
             //System.out.println("Prodate"+Prodate);
            // System.out.println(grid1[12]);
             ps.setString(27,grid1[13]);
             //System.out.println("grid1[13]"+grid1[13]);
	     ps.setString(28,update_user);
             ps.setTimestamp(29,ts);
             
           int eg= ps.executeUpdate();
           if(eg==0)
           {
        	   System.out.println("redirect");
        	   xml=xml+"<flag>failure</flag>";		 
           }
           else
           {
        	   System.out.println("verified");
        	   xml=xml+"<flag>success</flag>";
           }
		
		}
		catch(Exception e)
		{
			System.out.println("exception in insertion"+e.getMessage());
			xml=xml+"<flag>failure</flag>";	
		}
	}
	
	xml=xml+"</response>";   
    out.println(xml); 
    System.out.println("xml last:"+xml);
	
	
	}
}