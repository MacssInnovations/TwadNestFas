package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class billRegister_list_servlet
 */
public class Sanc_Proc_Single_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sanc_Proc_Single_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
	         ResultSet rs=null,rs2=null;
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
	         PreparedStatement ps=null,ps2=null;
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
	                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                 Class.forName(strDriver.trim());
	                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	         }
	         catch(Exception e)
	         {
	        	 	 System.out.println("Exception in openeing connection :"+e);

	         }
	         
	        System.out.println("servlet called in bill register list ");
	        String CONTENT_TYPE = "text/xml; charset=windows-1252";
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        String strType = "",xml="<response><command>searchByMonth</command>";
	        try
	        {
	        	     strType = request.getParameter("Command");
	        }
	        catch(Exception e)
	        {
	        		 e.printStackTrace();
	        }
	       
		
		if(strType.equalsIgnoreCase("searchByMonth"))  
	        {

        	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             
             
             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
  try{
             txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
             txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));  
            
             ps = con.prepareStatement("SELECT DISTINCT u.accounting_unit_name, " +
        			 "  O.Office_Name, " +
        			 "  a.SANCTION_PROCEEDING_NO, " +
        			 "  TO_CHAR(A.Sanction_Proceeding_Date,'dd/mm/yyyy') AS Sanctiondate, " +
        			 "  f.bill_major_type_desc, " +
        			 "  F1.Bill_Minor_Type_Desc, " +
        			 "  f2.bill_sub_type_desc, " +
        			 "  A.Payee_Type, " +
        			 "  a.Payee_Code, " +
        			 "  H.Employee_Name, " +
        			 "  h.employee_name " +
        			 "  ||' ' " +
        			 "  ||h.employee_initial AS emp_name, " +
        			 "  a.payee_name, " +
        			 "  h1.designation, " +
        			 "  a.NO_OF_HR, " +
        			 "  TO_CHAR(a.HR_FROM_DATE,'dd/mm/yyyy') AS hrformate, " +
        			 "  TO_CHAR(a.HR_TO_DATE,'dd/mm/yyyy')   AS hrformate, " +
        			 "  a.HR_AMOUNT, " +
        			 "  a.VOU_ATTACHED, " +
        			 "  a.NO_OF_VOU, " +
        			 "  a.REF_NO, " +
        			 "  TO_CHAR(A.Ref_Date,'dd/mm/yyyy') AS Hrformate, " +
        			 "  a.sanction_authority, " +
        			 "  aut.designation AS san_authotityDesc, " +
        			 "  a.SANCTIONED_BY, " +
        			 "  A.Total_Sanction_Amount, " +
        			 "  A.Account_Head_Code, " +
        			 "  A.Bud_Provided, " +
        			 "  A.Bud_Spent, " +
        			 "  Bal_Amt, " +
        			 "  a.PAYMENT_TO_BE_MADE_UNIT_ID, " +
        			 "  a.REMARKS, " +
        			 "  a.UPDATED_BY_USERID, " +
        			 "  TO_CHAR(a.UPDATED_DATE,'dd/mm/yyyy') AS hrformate, " +
        			 "  a.HR_NOTE_NO, " +
        			 "  TO_CHAR(A.Updated_Date,'dd/mm/yyyy') AS Hrformate " +
        			 "FROM Fas_Hr_Sanc_Proc_Mst A, " +
        			 "  Fas_Mst_Acct_Units U, " +
        			 "  Com_Mst_Offices O, " +
        			 "  Hrm_Mst_Employees H, " +
        			 "  HRM_MST_DESIGNATIONS H1, " +
        			 "  HRM_MST_DESIGNATIONS Aut, " +
        			 "  FAS_BILL_MAJOR_TYPES f, " +
        			 "  FAS_BILL_MINOR_TYPES_MST f1, " +
        			 "  FAS_BILL_SUB_TYPES f2, " +
        			 "  HRM_EMP_CURRENT_POSTING post " +
        			 "WHERE a.accounting_unit_id     =? " +
        			 "AND A.Accounting_For_Office_Id =? " +
        			 "AND A.Cashbook_Year            =? " +
        			 "AND a.cashbook_month           =? " +
        			 "AND U.Accounting_Unit_Id       =A.Accounting_Unit_Id " +
        			 "AND O.Office_Id                =A.Accounting_For_Office_Id " +
        			 "AND H.Employee_Id              =A.Payee_Code " +
        			 "AND aut.Designation_Id         =A.Sanction_Authority " +
        			 "AND F.Bill_Major_Type_Code     =A.Bill_Major_Type_Code " +
        			 "AND F1.Bill_Major_Type_Code    =A.Bill_Major_Type_Code " +
        			 "AND F1.Bill_Minor_Type_Code    =A.Bill_Minor_Type_Code " +
        			 "AND F2.Bill_Major_Type_Code    =A.Bill_Major_Type_Code " +
        			 "AND F2.Bill_Minor_Type_Code    =A.Bill_Minor_Type_Code " +
        			 "AND f2.bill_sub_type_code      =a.bill_sub_type_code " +
        			 "AND Post.Employee_Id           =A.Payee_Code " +
        			 "AND post.designation_id        =h1.designation_id"
);
             
            
             ps.setInt(1, cmbAcc_UnitCode);
        	 ps.setInt(2, cmbOffice_code);
        	 ps.setInt(3, txtCB_Year);
        	 ps.setInt(4, txtCB_Month);
             
             ResultSet result = ps.executeQuery();
xml=xml+"<flag>success</flag>";
while (result.next()) 
    {
        xml=xml+"<Hrnoteno>"+result.getInt("HR_NOTE_NO")+"</Hrnoteno>";
       xml=xml+"<hrnotedate>"+result.getString("Hrformate")+"</hrnotedate>";
        xml=xml+"<SANCTION_PROCEEDING_NO>"+result.getInt("SANCTION_PROCEEDING_NO")+"</SANCTION_PROCEEDING_NO>";
       xml=xml+"<SANCTION_PROCEEDING_Date1>"+result.getString("Sanctiondate")+"</SANCTION_PROCEEDING_Date1>";
        
        
        xml=xml+"<bill_major_type_desc1>"+result.getString("bill_major_type_desc")+"</bill_major_type_desc1>";
        xml=xml+"<bill_minor_type_desc1>"+result.getString("bill_minor_type_desc")+"</bill_minor_type_desc1>";
        xml=xml+"<bill_sub_type_desc1>"+result.getString("bill_sub_type_desc")+"</bill_sub_type_desc1>";
        
    }

	        }
catch(Exception e) 
{
    System.out.println("Exception in minor ===> "+e);   
    xml=xml+"<flag>failure</flag>";  
}

	        xml=xml+"</response>";   
	        out.println(xml); 
	        System.out.println(xml); 

	}
	}
}
