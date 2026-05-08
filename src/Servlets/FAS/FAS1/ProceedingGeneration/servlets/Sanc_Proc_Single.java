package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.io.*;
import Servlets.Security.classes.UserProfile;


public class Sanc_Proc_Single extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Sanc_Proc_Single() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String CONTENT_TYPE = "text/xml; charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String cmd;
        int major,sub;
        int count=0,txtCBYear=0,txtCBMonth=0;
        int billmajortype=0,billminortype=0,billsubtype=0;
        int txtEmpIDmas1=0,hr=0,subvou=0,refno=0,txtEmpIDmas=0,sanc=0,headcode=0,acunit=0;
        String xml="",sql="",payname="",active="",active1="",balamt="",txtRemarks="";
        String frmdate=null,todate=null,hramt="",refdate=null,sancamt="",sancdate=null,budpro="",budspent="";
int sanc_Office=0;
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
        
        String update_user;
        update_user = (String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
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

         int cmbAcc_UnitCode = 0,cmbOffice_code=0;
         try
         {
             cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
         }
         catch (Exception e) 
         {
             System.out.println("Exception to catch cmbAcc_UnitCode ");
         }
         try
         {
             cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
         }
         catch (Exception e) 
         {
             System.out.println("Exception to catch cmbOffice_code ");
         }      
         
      if(cmd.equalsIgnoreCase("majorType"))
            {
               xml="<response><command>major</command>"; 
                try 
                        {
                                ps = con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
                                result = ps.executeQuery();                                
                                while(result.next()) 
                                {
                                    xml=xml+"<mastercode>"+result.getInt("BILL_MAJOR_TYPE_CODE")+"</mastercode>";
                                    xml=xml+"<masterdesc>"+result.getString("BILL_MAJOR_TYPE_DESC")+"</masterdesc>";
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
      else if(cmd.equalsIgnoreCase("paydesc"))
      {
    	  xml="<response><command>paydesc</command>"; 
          try 
                  {
                          ps = con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES");
                          result = ps.executeQuery();                                
                          while(result.next()) 
                          {
                              xml=xml+"<mastercode>"+result.getInt("BILL_MAJOR_TYPE_CODE")+"</mastercode>";
                              xml=xml+"<masterdesc>"+result.getString("BILL_MAJOR_TYPE_DESC")+"</masterdesc>";
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
       }else if(cmd.equalsIgnoreCase("loadNote_no")){
    	   
    	   txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));//System.out.println(txtCBYear);
             txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth"));
             xml=xml+"<response><command>loadNoteNo</command>";
         
         	 try{
         		 PreparedStatement ps_Note=con.prepareStatement("select hr_note_no from fas_hr_note_details where accounting_unit_id =? and accounting_for_office_id = ? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
         	 ps_Note.setInt(1, cmbAcc_UnitCode);
         	 ps_Note.setInt(2, cmbOffice_code);
         	 ps_Note.setInt(3, txtCBYear);
         	 ps_Note.setInt(4, txtCBMonth);
         	 ResultSet rs_Note=ps_Note.executeQuery();
         	 int count_v=0;
         	 while(rs_Note.next())
         	 {
         		 xml=xml+"<hr_note_no>"+rs_Note.getInt("hr_note_no")+"</hr_note_no>";
          count_v++; 
         	 }if(count_v==0)
         	 {
             	   xml=xml+"<flag>failure</flag>";  
                }else{
             	 	xml=xml+"<flag>success</flag>";
                }
         	 }catch (Exception e) {
     			// TODO: handle exception
         		 e.printStackTrace();
         		 xml=xml+"<flag>failure</flag>";  
     		}
         	    xml=xml+"</response>"; 
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
  	else if(cmd.equalsIgnoreCase("txtgpf")) 
	    {
		System.out.println(":::::::::::::txtgpf::::::::::");
  		int empid=Integer.parseInt(request.getParameter("empid"));System.out.println("empidddddddd"+empid);
  		int group1=Integer.parseInt(request.getParameter("group1"));System.out.println("groupppppp"+group1);
	        xml="<response><command>txtgpf</command>"; 
	        try 
	                {
	                    ps = con.prepareStatement("SELECT a.EMPLOYEE_NAME ||'.'|| a.EMPLOYEE_INITIAL ||'-'|| b.DESIGNATION as name FROM HRM_MST_EMPLOYEES a LEFT OUTER JOIN HRM_EMP_CURRENT_POSTING d ON a.EMPLOYEE_ID=d.EMPLOYEE_ID LEFT OUTER JOIN HRM_MST_DESIGNATIONS b ON d.DESIGNATION_ID=b.DESIGNATION_ID LEFT OUTER JOIN HRM_MST_SERVICE_GROUP c ON b.SERVICE_GROUP_ID=c.SERVICE_GROUP_ID WHERE a.GPF_NO = ? ORDER BY EMPLOYEE_NAME");
	                    System.out.println("ps"+ps);
	                    ps.setInt(1,group1);
	                    result = ps.executeQuery();
	                    xml=xml+"<flag>success</flag>";
	                    while (result.next()) 
	                        {
	                    	    xml=xml+"<name>"+result.getString("name")+"</name>";
	                         }   
	                }
	          catch(Exception e) 
	                {
	                        System.out.println("Exception  ===> "+e);   
	                        xml=xml+"<flag>failure</flag>";  
	                }
	            xml=xml+"</response>";
	  	    }
  	else if(cmd.equalsIgnoreCase("desig")) 
       {
	    int empid=Integer.parseInt(request.getParameter("empid"));
		int group=Integer.parseInt(request.getParameter("group"));
		int des=Integer.parseInt(request.getParameter("des"));
        xml="<response><command>desig</command>"; 
        try 
                {
                    ps = con.prepareStatement("SELECT HRM_MST_EMPLOYEES.EMPLOYEE_NAME ||'.'|| HRM_MST_EMPLOYEES.EMPLOYEE_INITIAL ||'-'|| HRM_MST_DESIGNATIONS.DESIGNATION as name FROM HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS,HRM_MST_SERVICE_GROUP,HRM_EMP_CURRENT_POSTING WHERE HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID=HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID AND HRM_EMP_CURRENT_POSTING.DESIGNATION_ID =HRM_MST_DESIGNATIONS.DESIGNATION_ID AND HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID AND HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID=? AND HRM_MST_DESIGNATIONS.DESIGNATION_ID=? AND HRM_MST_EMPLOYEES.EMPLOYEE_ID=?");
                    System.out.println("ps"+ps);
                    ps.setInt(1,group);
                    ps.setInt(2,des);
                    ps.setInt(3,empid);
                    result = ps.executeQuery();
                    xml=xml+"<flag>success</flag>";
                    while (result.next()) 
                        {
                    	    xml=xml+"<name>"+result.getString("name")+"</name>";
                         }   
                }
          catch(Exception e) 
                {
                        System.out.println("Exception  ===> "+e);   
                        xml=xml+"<flag>failure</flag>";  
                }
            xml=xml+"</response>";
  	    }
  	else if(cmd.equalsIgnoreCase("Add"))
     { 
  		                //System.out.println("Inside Add");  
  		                sancdate=request.getParameter("sancdate");//System.out.println("dateeeeeeeeeeeee"+sancdate);
  		                txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));//System.out.println(txtCBYear);
  		                txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth"));//System.out.println(txtCBMonth);
  		                billmajortype=Integer.parseInt(request.getParameter("billmajortype"));//System.out.println(billmajortype);
  		                billminortype=Integer.parseInt(request.getParameter("billminortype"));//System.out.println(billminortype);
  		                billsubtype=Integer.parseInt(request.getParameter("billsubtype"));//System.out.println(billsubtype);
  		                txtEmpIDmas1=Integer.parseInt(request.getParameter("txtEmpIDmas1"));//System.out.println(txtEmpIDmas1);
  		                hr=Integer.parseInt(request.getParameter("hr"));//System.out.println(hr);
  		                subvou=Integer.parseInt(request.getParameter("subvou"));//System.out.println(subvou);
  		                refno=Integer.parseInt(request.getParameter("refno"));//System.out.println(refno);
  		                txtEmpIDmas=Integer.parseInt(request.getParameter("txtEmpIDmas"));//System.out.println(txtEmpIDmas);
  		                sanc=Integer.parseInt(request.getParameter("sanc"));//System.out.println(sanc);
  		                headcode=Integer.parseInt(request.getParameter("headcode"));//System.out.println(headcode);
				  		budpro=request.getParameter("budpro");//System.out.println(budpro);
				  		budspent=request.getParameter("budspent");//System.out.println(budspent);
				  		acunit=Integer.parseInt(request.getParameter("acunit"));//System.out.println(acunit);
				  		payname=request.getParameter("payname");//System.out.println(payname);
				  		active=request.getParameter("active");//System.out.println(active);
				  		active1=request.getParameter("active1");//System.out.println(active1);
				  		balamt=request.getParameter("balamt");//System.out.println(balamt);
				  		txtRemarks=request.getParameter("txtRemarks");//System.out.println(txtRemarks);
				  		frmdate=request.getParameter("frmdate");
				  		todate=request.getParameter("todate");
				  		hramt=request.getParameter("hramt");
				  		refdate=request.getParameter("refdate");
				  		sanc_Office=Integer.parseInt(request.getParameter("sanc_Office"));//System.out.println(txtEmpIDmas1);
				  	   int note_no=Integer.parseInt(request.getParameter("note_no"));
					   String ho_date=request.getParameter("ho_date");///System.out.println(txtCBYear);
					  java.sql.Date note_date=null;
					 /*  SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
				        java.util.Date d1;
				        try {
				            d1 = dateFormat1.parse(ho_date.trim());
				            dateFormat1.applyPattern("yyyy-MM-dd");
				            ho_date = dateFormat1.format(d1);
				            note_date = java.sql.Date.valueOf(ho_date);
				        } catch (Exception e) {
				            e.printStackTrace();
				           System.out.println("Problem in Sanction Date Format******");
				        }*/
					   sancamt=request.getParameter("sancamt");//System.out.println(sancamt);
				  		int inc=0;
				        try
				        {
                            ps=con.prepareStatement("select max(SANCTION_PROCEEDING_NO) from FAS_HR_SANC_PROC_MST");
                            ResultSet res=ps.executeQuery();
                            if(res.next())
                            {
                            	inc=res.getInt(1);
                            }
                            inc++;
                            System.out.println("value of records:::"+inc);
				        }
				     catch(Exception e){
				         System.out.println("Exception is "+e);
				     }
                    xml="<response><command>Add</command>"; 
                        try 
                           {//System.out.println("inside try");
                               ps=con.prepareStatement("select * from FAS_HR_SANC_PROC_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SANCTION_PROCEEDING_NO=? and SANCTION_PROCEEDING_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and HR_NOTE_NO=?");
                               ps.setInt(1,cmbAcc_UnitCode);     
                               ps.setInt(2,cmbOffice_code);
                               ps.setInt(3,inc);
                               ps.setString(4,sancdate);//System.out.println("no"+inc);
                               ps.setInt(5,txtCBYear);
                               ps.setInt(6,txtCBMonth);
                               ps.setInt(7,note_no);
                               ResultSet res=ps.executeQuery();
                               System.out.println(res);
                               if(res.next()) {
                                   xml=xml+"<flag>AlreadyExist</flag>"; 
                               }
                               else
                               {
                               sql="insert into FAS_HR_SANC_PROC_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SANCTION_PROCEEDING_NO,SANCTION_PROCEEDING_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,PAYEE_NAME,NO_OF_HR,HR_FROM_DATE,HR_TO_DATE,HR_AMOUNT,VOU_ATTACHED,NO_OF_VOU,REF_NO,REF_DATE,SANCTION_AUTHORITY,SANCTIONED_BY,TOTAL_SANCTION_AMOUNT,ACCOUNT_HEAD_CODE,BUD_PROVIDED,BUD_SPENT,BAL_AMT,PAYMENT_TO_BE_MADE_UNIT_ID,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,HR_NOTE_NO,HR_NOTE_DATE,SANCTION_PROC_OFFICE_ID) values(?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),to_date(?,'dd-mm-yyyy'),?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?)";
                               //System.out.println("sql"+sql);
                               ps = con.prepareStatement(sql);
                               //System.out.println("ps"+ps);
                               ps.setInt(1,cmbAcc_UnitCode);     
                               ps.setInt(2,cmbOffice_code);
                               ps.setInt(3,inc);
                               ps.setString(4,sancdate);//System.out.println("no"+inc);
                               ps.setInt(5,txtCBYear);
                               ps.setInt(6,txtCBMonth);
                               ps.setInt(7,billmajortype);
                               ps.setInt(8,billminortype);
                               ps.setInt(9,billsubtype);
                               ps.setString(10,active);
                               ps.setInt(11,txtEmpIDmas1);
                               ps.setString(12,payname);
                               ps.setInt(13,hr);
                               ps.setString(14,frmdate);
                               ps.setString(15,todate);
                               ps.setString(16,hramt);
                               ps.setString(17,active1);
                               ps.setInt(18,subvou);
                               ps.setInt(19,refno);
                               ps.setString(20,refdate);
                               ps.setInt(21,sanc);
                               ps.setInt(22,txtEmpIDmas);
                               ps.setString(23,sancamt);
                               ps.setInt(24,headcode);
                               ps.setString(25,budpro);
                               ps.setString(26,budspent);
                               ps.setString(27,balamt);
                               ps.setInt(28,acunit);
                               ps.setString(29,txtRemarks);
                               ps.setString(30,update_user);
                               ps.setTimestamp(31,ts);
                               ps.setInt(32,note_no);
                               ps.setString(33,ho_date);
                               ps.setInt(34, sanc_Office);
                               ps.executeUpdate();    
                               xml=xml+"<flag>success</flag>";
                              System.out.println("record inserted"+xml);
                               //con.commit();
                              }
                           }
                       catch(Exception e) 
                           {   System.out.println("Error ****"+e.getMessage());  
                               xml=xml+"<flag>failure</flag>";
                               }
                       xml=xml+"</response>";
    }   
  	else if (cmd.equalsIgnoreCase("Update"))
    {
  		  sancdate=request.getParameter("sancdate");System.out.println("dateee&&&&&&&&&&&&&&&&&&&&"+sancdate);
          txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));//System.out.println(txtCBYear);
          txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth"));//System.out.println(txtCBMonth);
          billmajortype=Integer.parseInt(request.getParameter("billmajortype"));//System.out.println(billmajortype);
          billminortype=Integer.parseInt(request.getParameter("billminortype"));//System.out.println(billminortype);
          billsubtype=Integer.parseInt(request.getParameter("billsubtype"));//System.out.println(billsubtype);
          txtEmpIDmas1=Integer.parseInt(request.getParameter("txtEmpIDmas1"));//System.out.println(txtEmpIDmas1);
          hr=Integer.parseInt(request.getParameter("hr"));//System.out.println(hr);
          subvou=Integer.parseInt(request.getParameter("subvou"));//System.out.println(subvou);
          refno=Integer.parseInt(request.getParameter("refno"));//System.out.println(refno);
          txtEmpIDmas=Integer.parseInt(request.getParameter("txtEmpIDmas"));//System.out.println(txtEmpIDmas);
          sanc=Integer.parseInt(request.getParameter("sanc"));//System.out.println(sanc);
          headcode=Integer.parseInt(request.getParameter("headcode"));//System.out.println(headcode);
  		  budpro=request.getParameter("budpro");//System.out.println(budpro);
  		  budspent=request.getParameter("budspent");//System.out.println(budspent);
  		  acunit=Integer.parseInt(request.getParameter("acunit"));//System.out.println(acunit);
  		  payname=request.getParameter("payname");//System.out.println(payname);
  		  active=request.getParameter("active");//System.out.println(active);
  		  active1=request.getParameter("active1");//System.out.println(active1);
  		  balamt=request.getParameter("balamt");//System.out.println(balamt);
  		  txtRemarks=request.getParameter("txtRemarks");//System.out.println(txtRemarks);
  		  frmdate=request.getParameter("frmdate");//System.out.println(frmdate);
  		  todate=request.getParameter("todate");//System.out.println(todate);
  		  hramt=request.getParameter("hramt");//System.out.println(hramt);
  		  refdate=request.getParameter("refdate");//System.out.println(refdate);
  		  sancamt=request.getParameter("sancamt");//System.out.println("amt"+sancamt);
  		  int inc=Integer.parseInt(request.getParameter("inc"));//System.out.println("no"+inc);
  		  //added 03/02/2014 update not executed due to DATE problem 
  		   int note_no=Integer.parseInt(request.getParameter("note_no"));
  		   String ho_date=request.getParameter("ho_date");
  		java.sql.Date sanc_date = null;
  		java.sql.Date note_date=null;
		/*java.util.GregorianCalendar c2;
		String[] sd = request.getParameter("sancdate").split("/");
		c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
				Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
		java.util.Date d = c2.getTime();
		sanc_date = new Date(d.getTime());*/
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d;
        try {
            d = dateFormat.parse(sancdate.trim());
            dateFormat.applyPattern("yyyy-MM-dd");
            sancdate = dateFormat.format(d);
            sanc_date = java.sql.Date.valueOf(sancdate);
        } catch (Exception e) {
            e.printStackTrace();
           System.out.println("Problem in Sanction Date Format******");
        }
      /*  if()
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d1;
        try {
            d1 = dateFormat1.parse(ho_date.trim());
            dateFormat1.applyPattern("yyyy-MM-dd");
            ho_date = dateFormat1.format(d1);
            note_date = java.sql.Date.valueOf(ho_date);
        } catch (Exception e) {
            e.printStackTrace();
           System.out.println("Problem in Sanction Date Format******");
        }*/
		
		System.out.println("SAnction Date in DATE format"+sanc_date);
  		 xml="<response><command>Updated</command>"; 
             try {System.out.println("inside try in update *****");
                ps =  con.prepareStatement("update FAS_HR_SANC_PROC_MST set  BILL_MAJOR_TYPE_CODE=?,BILL_MINOR_TYPE_CODE=?,BILL_SUB_TYPE_CODE=?,PAYEE_TYPE=?,PAYEE_CODE=?,PAYEE_NAME=?,NO_OF_HR=?,HR_FROM_DATE=to_date(?,'dd-mm-yyyy'),HR_TO_DATE=to_date(?,'dd-mm-yyyy'),HR_AMOUNT=?,VOU_ATTACHED=?,NO_OF_VOU=?,REF_NO=?,REF_DATE=to_date(?,'dd-mm-yyyy'),SANCTION_AUTHORITY=?,SANCTIONED_BY=?,TOTAL_SANCTION_AMOUNT=?,ACCOUNT_HEAD_CODE=?,BUD_PROVIDED=?,BUD_SPENT=?,BAL_AMT=?,PAYMENT_TO_BE_MADE_UNIT_ID=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SANCTION_PROCEEDING_NO=? and SANCTION_PROCEEDING_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and HR_NOTE_NO=? ");
            // ps =  con.prepareStatement("update FAS_HR_SANC_PROC_MST set REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SANCTION_PROCEEDING_NO=? and SANCTION_PROCEEDING_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                ps.setInt(1,billmajortype);
                ps.setInt(2,billminortype);
                ps.setInt(3,billsubtype);
                ps.setString(4,active);
                ps.setInt(5,txtEmpIDmas1);
                ps.setString(6,payname);
                ps.setInt(7,hr);
                ps.setString(8,frmdate);
                ps.setString(9,todate);
                ps.setString(10,hramt);
                ps.setString(11,active1);
                ps.setInt(12,subvou);
                ps.setInt(13,refno);
                ps.setString(14,refdate);
                ps.setInt(15,sanc);
                ps.setInt(16,txtEmpIDmas);
                ps.setString(17,sancamt);
                ps.setInt(18,headcode);
                ps.setString(19,budpro);
                ps.setString(20,budspent);
                ps.setString(21,balamt);
                ps.setInt(22,acunit);
                ps.setString(23,txtRemarks);
                ps.setString(24,update_user);
                ps.setTimestamp(25,ts);
                ps.setInt(26,cmbAcc_UnitCode);     
                ps.setInt(27,cmbOffice_code);
                ps.setInt(28,inc);
                ps.setDate(29,sanc_date);
                ps.setInt(30,txtCBYear);
                ps.setInt(31,txtCBMonth);
                ps.setInt(32,note_no);
               // ps.setString(33,ho_date);
                ps.executeUpdate();//System.out.println("Prepared statement :::"+ps.executeUpdate());
                int yy= ps.executeUpdate();
                System.out.println("after successful updation****"+yy);
               
                if(yy>0){
                	 con.commit();
                xml = xml + "<flag>success</flag>";
                }else{
                	con.rollback();
                	xml = xml + "<flag>failure</flag>";
                }
               // con.commit();
                ps.close();
            }
            catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } 
    else if (cmd.equalsIgnoreCase("Delete")) 
    {
    	try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    		sancdate=request.getParameter("sancdate");System.out.println("dateeeeeeeeeeeee"+sancdate);
          txtCBYear=Integer.parseInt(request.getParameter("txtCBYear"));System.out.println(txtCBYear);
          txtCBMonth=Integer.parseInt(request.getParameter("txtCBMonth"));System.out.println(txtCBMonth);
          int inc=Integer.parseInt(request.getParameter("inc"));System.out.println("no"+inc);
          
          	java.sql.Date sanc_date = null;
  			/*java.util.GregorianCalendar c2;
  			String[] sd = request.getParameter("sancdate").split("/");
  			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
  				Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
  			java.util.Date d = c2.getTime();
  			sanc_date = new Date(d.getTime());*/
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
          	if(!sancdate.equalsIgnoreCase("")||!sancdate.equalsIgnoreCase(null)){
          
            try {
                d = dateFormat.parse(sancdate.trim());
                dateFormat.applyPattern("yyyy-MM-dd");
                sancdate = dateFormat.format(d);
                sanc_date = java.sql.Date.valueOf(sancdate);
                System.out.println("Sanction Date in SANCTION Formattttt"+sanc_date);
            } catch (Exception e) {
                e.printStackTrace();
               System.out.println("Problem in Sanction Date Format******");
            }
          	}
          
          xml = "<response><command>Delete</command>";

              try {
                  ps = con.prepareStatement("delete from FAS_HR_SANC_PROC_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SANCTION_PROCEEDING_NO=? and SANCTION_PROCEEDING_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                  ps.setInt(1,cmbAcc_UnitCode);     
                  ps.setInt(2,cmbOffice_code);
                  ps.setInt(3,inc);
                  ps.setDate(4,sanc_date);
                  ps.setInt(5,txtCBYear);
                  ps.setInt(6,txtCBMonth);
                  ps.executeUpdate();
                  int xx=ps.executeUpdate();
                  xml = xml + "<flag>success</flag>";
                  System.out.println("Inside the Delete option*********"+xx);
                  con.commit();
                  ps.close();
              } catch (Exception e) {
				try {
					con.rollback();
					con.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
              xml = xml + "</response>";
             
  }
  else if (cmd.equalsIgnoreCase("Descrip"))
  {
	  headcode=Integer.parseInt(request.getParameter("headcode"));
      xml = "<response><command>Descrip</command>";

          try {
              ps = con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE="+headcode+"");
              result=ps.executeQuery();
              while (result.next()) 
              {
          	    xml=xml+"<code>"+result.getInt("ACCOUNT_HEAD_CODE")+"</code>";
          	    xml=xml+"<desc>"+result.getString("ACCOUNT_HEAD_DESC")+"</desc>";
               }
              xml = xml + "<flag>success</flag>";
          } catch (Exception e) {
              System.out.println("catch..HERE.in load head code." + e);
              xml = xml + "<flag>failure</flag>";
          }
          xml = xml + "</response>";
  }
      
      System.out.println("xml ::"+xml);
      //out.println(xml);
      out.write(xml);
      out.close();
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
