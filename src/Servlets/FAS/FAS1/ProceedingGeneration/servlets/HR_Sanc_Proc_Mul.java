package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import Servlets.Security.classes.UserProfile;

import java.sql.*;
import java.util.ResourceBundle;

public class HR_Sanc_Proc_Mul extends HttpServlet {
	private static final long serialVersionUID = 1L;       

    public HR_Sanc_Proc_Mul() {
    	super();        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String CONTENT_TYPE = "text/xml; charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String cmd;System.out.println("welcomeeeeeeeeeeeeeeeeee");
        int major,sub;
        int count=0;
        String xml="";

        Connection con=null;
        PreparedStatement ps;
        ResultSet result=null;
        int eid=0;
        cmd=request.getParameter("Command");
        try
        {
                 ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                 String ConnectionString="";
                
                 String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                 String strdsn=rs.getString("Config.DSN");
                 String strhostname=rs.getString("Config.HOST_NAME");
                 String strportno=rs.getString("Config.PORT_NUMBER");
                 String strsid=rs.getString("Config.SID");
                 String strdbusername=rs.getString("Config.USER_NAME");
                 String strdbpassword=rs.getString("Config.PASSWORD");
                   
                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection    
                  Class.forName(strDriver.trim());
                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                  try
                  {
                        con.clearWarnings();
                  }
                  catch(SQLException e)
                  {
                        System.out.println("Exception in creating statement:"+e);
                  }          
        }
        catch(Exception e)
        {
                   System.out.println("Exception in openeing connection:"+e);
        }
          
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
        eid=empProfile.getEmployeeId();
        System.out.println("employee id:"+eid);
        
        try
         {
            
                if(session==null)
                {
                    System.out.println(request.getContextPath()+"/index.jsp");
                    response.sendRedirect(request.getContextPath()+"/index.jsp");                   
                }
                System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
         String userid=(String)session.getAttribute("UserId");
         System.out.println("session id is:"+userid);

         try
         {
         }
         catch (Exception e) 
         {
             System.out.println("Exception to catch cmbAcc_UnitCode ");
         }
         try
         {
         }
         catch (Exception e) 
         {
             System.out.println("Exception to catch cmbOffice_code ");
         }      
         
      if(cmd.equalsIgnoreCase("code"))
            {
    	       int code=Integer.parseInt(request.getParameter("code"));
               xml="<response><command>code</command>"; 
                try 
                        {
                                //ps = con.prepareStatement("select PAYEE_NAME,PAYEE_CODE from FAS_HR_SANC_PROC_MST where PAYEE_CODE="+code+"");
                                ps = con.prepareStatement("select a.EMPLOYEE_ID,a.EMPLOYEE_NAME,a.EMPLOYEE_INITIAL,  b.DESIGNATION,a.DATE_OF_BIRTH,a.GPF_NO   from HRM_MST_EMPLOYEES a left outer join  HRM_EMP_CURRENT_POSTING d on a.EMPLOYEE_ID=d.EMPLOYEE_ID  left outer join  HRM_MST_DESIGNATIONS b on d.DESIGNATION_ID=b.DESIGNATION_ID   left outer join  HRM_MST_SERVICE_GROUP c on b.SERVICE_GROUP_ID=c.SERVICE_GROUP_ID   where   a.GPF_NO = "+code+ " order by EMPLOYEE_NAME");
                				result = ps.executeQuery();                                
                                while(result.next()) 
                                {
                                    xml=xml+"<code>"+result.getInt("EMPLOYEE_ID")+"</code>";
                                    xml=xml+"<desc>"+result.getString("EMPLOYEE_NAME")+"</desc>";
                                    xml=xml+"<designation>"+result.getString("DESIGNATION")+"</designation>";
                                    count++;
                                }
                                if(count>0)
                                    xml=xml+"<flag>success</flag>";
                                else
                                    xml=xml+"<flag>failure</flag>";
                        }
                  catch(Exception e) 
                        {
                                System.out.println("Exception in masterdesc ===> "+e);   
                                xml=xml+"<flag>failure</flag>";  
                        }
                    xml=xml+"</response>";
            }
      else if(cmd.equalsIgnoreCase("loadNote_noSanc")){
           	 xml=xml+"<response><command>loadNote_noSanc</command>";
          	int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          	int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
          	  int txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));
         	  int txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth"));
     try{
      		 PreparedStatement Sanc_Note=con.prepareStatement("select SANCTION_PROCEEDING_NO from FAS_HR_SANC_PROC_MULTI_MST where accounting_unit_id =? and accounting_for_office_id = ? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
      		Sanc_Note.setInt(1, cmbAcc_UnitCode);
      		Sanc_Note.setInt(2, cmbOffice_code);
      		Sanc_Note.setInt(3, txtCBYear);
      		Sanc_Note.setInt(4, txtCBMonth);
      	 ResultSet Sanc_RS=Sanc_Note.executeQuery();
      	 int count1=0;
      	 while(Sanc_RS.next())
      	 {
      		 xml=xml+"<SANCTION_PROCEEDING_NO>"+Sanc_RS.getInt("SANCTION_PROCEEDING_NO")+"</SANCTION_PROCEEDING_NO>";
       count1++; 
      	 }if(count1==0)
      	 {
          	   xml=xml+"<flag>No Data</flag>";  
             }else{
          	 	xml=xml+"<flag>success</flag>";
             }
         }catch (Exception e) {
     	
     		 e.printStackTrace();
     		 xml=xml+"<flag>failure</flag>";  
     	}
     	    xml=xml+"</response>"; 
     	    System.out.println("xml : "+xml);
         }
         	
      else if(cmd.equalsIgnoreCase("minorType")) 
      {
          xml="<response><command>minor</command>"; 
          try 
                  {
                      major=Integer.parseInt(request.getParameter("major2"));
                      ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major +"and status='L'");
                      result = ps.executeQuery();
                      xml=xml+"<flag>success</flag>";
                      while (result.next()) 
                          {
                              xml=xml+"<minorcode>"+result.getString("BILL_MINOR_TYPE_CODE")+"</minorcode>";
                              xml=xml+"<minordesc>"+result.getString("BILL_MINOR_TYPE_DESC")+"</minordesc>";
                          }
                  }
            catch(Exception e) 
                  {
                          System.out.println("Exception in minor ===> "+e);   
                          xml=xml+"<flag>failure</flag>";  
                  }
              xml=xml+"</response>";
      }
	else if(cmd.equalsIgnoreCase("subType")) 
    {
	System.out.println(":::::::::::::sub type::::::::::");
        xml="<response><command>subb</command>"; 
        try 
                {
        	 		major=Integer.parseInt(request.getParameter("major2"));
                    sub=Integer.parseInt(request.getParameter("sub2"));	
                    System.out.println("sub"+sub);
                    ps = con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+sub +"and status='L'");
                    System.out.println("ps"+ps);
                    result = ps.executeQuery();
                    xml=xml+"<flag>success</flag>";
                    while (result.next()) 
                        {
                    	    System.out.println("subdesc"+result.getString("BILL_SUB_TYPE_DESC"));
                            xml=xml+"<subcode>"+result.getString("BILL_SUB_TYPE_CODE")+"</subcode>";
                            xml=xml+"<subdesc>"+result.getString("BILL_SUB_TYPE_DESC")+"</subdesc>";
                        }   
                }
          catch(Exception e) 
                {
                        System.out.println("Exception in minor ===> "+e);   
                        xml=xml+"<flag>failure</flag>";  
                }
            xml=xml+"</response>";
            System.out.println("xml"+xml);
    }
      System.out.println("xml ::"+xml);
      out.println(xml);
      out.close();	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		PrintWriter out = response.getWriter();
	    HttpSession session=request.getSession(true);
	    String cmd=request.getParameter("command");
	   
	    String xml="";
        Connection con=null;
        PreparedStatement ps=null,ps1=null;
        ResultSet rs=null;
        
        String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
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
		
		if(cmd.equalsIgnoreCase("Add")) 
        {
			    System.out.println("Add function starts");
			    String CONTENT_TYPE = "text/html; charset=windows-1252";
	            response.setContentType(CONTENT_TYPE);
	           
	            int cmbAcc_UnitCode1=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;            
	            String txtCrea_date=null,frmdate=null,todate=null,refdate=null;
	            int major= Integer.parseInt(request.getParameter("majorType"));System.out.println(major);
	            int minor= Integer.parseInt(request.getParameter("minorType"));
	            int sanc_auth=Integer.parseInt(request.getParameter("sanc"));
	            int sanc_by=Integer.parseInt(request.getParameter("txtEmpID_mas"));
	            int achead=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
	            String pay_ac=(request.getParameter("ac_unit"));	                        
	            String sanc_amt=request.getParameter("tot_amt");
	            String bud_pro=request.getParameter("bud_pro");
	            String bud_spent=request.getParameter("bud_spent");
	            String par=request.getParameter("txtRemarks");
	            int count=0,sub=0,pay_code=0;
	            
	            String pay_type="",parti="",hramt="";	            
//	            int sub=Integer.parseInt(request.getParameter("billsubtype"));
//	            int pay_code=Integer.parseInt(request.getParameter("pay_code"));
	            int hr=Integer.parseInt(request.getParameter("hr"));
	            int subvou=Integer.parseInt(request.getParameter("sub_vou"));
	            int refno=Integer.parseInt(request.getParameter("ref_no"));
	           int Hr_note=Integer.parseInt(request.getParameter("note_no"));
	           System.out.println("Hr_note >>> "+Hr_note);
//	            String pay_type=request.getParameter("radActive");
	            frmdate=request.getParameter("frm_date");
	            todate=request.getParameter("to_date");
//	            String hramt=request.getParameter("hr_amt");
	            String vou=request.getParameter("radActive1");
	            String sancamt=request.getParameter("sanc_amt");
	            refdate=request.getParameter("ref_date");System.out.println("ref_date........."+refdate);
//	            String parti=request.getParameter("parti");
		           
	            String update_user=(String)session.getAttribute("UserId");
	            long l=System.currentTimeMillis();
	            Timestamp ts=new Timestamp(l);
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                               
	            try{cmbAcc_UnitCode1=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cmbAcc_UnitCode........ "+cmbAcc_UnitCode);
	            
	            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            System.out.println("cmbOffice_code...... "+cmbOffice_code);
	            String[] sd=request.getParameter("sanc_date").split("/");
	         
	             
	            txtCrea_date=request.getParameter("sanc_date");
	            System.out.println("txtCrea_date "+txtCrea_date);
	            
	            System.out.println("b4 getting month and year");
	            try{txtCash_year=Integer.parseInt(sd[2]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtCash_year "+txtCash_year);
	            
	            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            System.out.println("txtCash_Month_hid "+txtCash_Month_hid);	            
                int org_VouNo=0;                   
	        	  
	           try
	           {System.out.println("inside 1st try");
	                    ps=con.prepareStatement("select max(SANCTION_PROCEEDING_NO) from FAS_HR_SANC_PROC_MULTI_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
	                    ps.setInt(1,cmbAcc_UnitCode1);
	                    ps.setInt(2,cmbOffice_code);
	                    ps.setInt(3,txtCash_year);
	                    ps.setInt(4,txtCash_Month_hid);                      
	                    rs=ps.executeQuery();
	                    if(rs.next()) 
	                    {
	                    		org_VouNo = rs.getInt(1); 
	                              System.out.println("org_VouNo"+org_VouNo);
	                    }
	                    org_VouNo=org_VouNo+1;
	                    rs.close();
	           }           
	           catch(Exception e){System.out.println("exception"+e );}
	           System.out.println("org_VouNo "+org_VouNo);            
	             
	             try {
	            	 System.out.println("inside 1st table");
	                 ps1=con.prepareStatement("insert into FAS_HR_SANC_PROC_MULTI_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_NO,SANCTION_PROCEEDING_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,SANCTION_AUTHORITY,SANCTIONED_BY,TOTAL_SANCTION_AMOUNT,ACCOUNT_HEAD_CODE,BUDGET_PROVIDED,BUDGET_SOFAR_SPENT,PAYMENT_ACCOUNTING_UNIT_ID,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,HR_NOTE_NO)values(?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	                 ps1.setInt(1,cmbAcc_UnitCode1);
	                 ps1.setInt(2,cmbOffice_code);
	                 ps1.setInt(3,txtCash_year);
	                 ps1.setInt(4,txtCash_Month_hid);  
	                 ps1.setInt(5,org_VouNo);
	                 ps1.setString(6,txtCrea_date);
	                 
	                 ps1.setInt(7,major);
	                 ps1.setInt(8,minor);
	                 ps1.setInt(9,sanc_auth);
	                 ps1.setInt(10,sanc_by);

	                 ps1.setString(11,sanc_amt);
	                 ps1.setInt(12,achead);
	                 ps1.setString(13,bud_pro);
	                 ps1.setString(14,bud_spent);
	                 ps1.setString(15,pay_ac);
	                 ps1.setString(16,par);
	                 ps1.setString(17,"L");//Default value is set
	                 
	                 ps1.setString(18,update_user);
	                 ps1.setTimestamp(19,ts);
	                 ps1.setInt(20, Hr_note);System.out.println(ts);
	                 ps1.executeUpdate();
	                 
	            	 System.out.println("inside 2nd table");
	               	  String pay_type1[]=request.getParameterValues("CR_DR_type");
	  	              String pay_code1[]=request.getParameterValues("SL_type");
	  	              
	                  String hramt1[]=request.getParameterValues("SL_code");
	                  String parti1[]=request.getParameterValues("sl_amt");
	                  String sub1[]=request.getParameterValues("H_code");
                      count=1;  
                 	 for(int k=0;k<sub1.length;k++)
         	           {
                 		sub=Integer.parseInt(sub1[k]);
                 		pay_type=pay_type1[k];
                 		pay_code=Integer.parseInt(pay_code1[k]);
                 		hramt=hramt1[k];
                 		parti=parti1[k];
	                 ps1=con.prepareStatement("insert into FAS_HR_SANC_PROC_MULTI_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_NO,SL_NO,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,NO_OF_HRS,HR_FROM_DATE,HR_TO_DATE,HR_AMOUNT,SUB_VOUCHERS_ATTACHED,NO_OF_SUB_VOUCHERS,SANCTION_AMOUNT,REF_NO,REF_DATE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),to_date(?,'dd-mm-yyyy'),?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?)");
	                 ps1.setInt(1,cmbAcc_UnitCode1);
	                 ps1.setInt(2,cmbOffice_code);
	                 ps1.setInt(3,txtCash_year);
	                 ps1.setInt(4,txtCash_Month_hid);  
	                 ps1.setInt(5,org_VouNo);
	                 ps1.setInt(6,count);//Default value is set
	                 ps1.setInt(7,sub);
	                 ps1.setString(8,pay_type);
	                 ps1.setInt(9,pay_code);
	                 ps1.setInt(10,hr);
	                 ps1.setString(11,frmdate);

	                 ps1.setString(12,todate);
	                 ps1.setString(13,hramt);
	                 ps1.setString(14,vou);
	                 ps1.setInt(15,subvou);
	                 ps1.setString(16,sancamt);
	                 ps1.setInt(17,refno);
	                 ps1.setString(18,refdate);
	                 ps1.setString(19,parti);
	                 ps1.setString(20,update_user);
	                 ps1.setTimestamp(21,ts);
	               
	                 System.out.println(ts);
	                 ps1.executeUpdate();
	                 count++;
           	           }
	                    sendMessage(response,"The  Sanction Proceeding Number "+org_VouNo+" has been Created Successfully ","ok");   
                        con.commit();
	             }
	            catch(Exception e) {
	            System.out.println("exception"+e.getMessage());	                 
	             }	           
                 finally
                 {    
                	 System.out.println("done");
                     try{con.setAutoCommit(true);  }catch(SQLException sqle){}
                 }
	       } 		
		out.write(xml);
        out.close();
	}
	private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
                  System.out.println("sendMessage");
                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
                  response.sendRedirect(url);
        }
        catch(IOException e)
        {
        }
    }      
}
