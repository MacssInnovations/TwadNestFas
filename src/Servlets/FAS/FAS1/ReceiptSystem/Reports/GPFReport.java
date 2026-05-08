package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import Servlets.Security.classes.UserProfile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class GPFReport extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

public void doGet(HttpServletRequest request,
                   HttpServletResponse response) throws ServletException, IOException 
{
        response.setContentType(CONTENT_TYPE);
        
        /**
         * Set Content Type 
         */
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        
        
        /**
         * Session Checking 
         */
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
        
        /** Get User ID */
        String userid=(String)session.getAttribute("UserId");
        String empid = userid.substring(4,userid.length());
        
        System.out.println("Empid -------------->"+empid);
        
        /**
         * Variables Declaration 
         */        
        Connection con=null;
        
        /** Combo Loading */
        PreparedStatement ps=null;        
        ResultSet rs=null;  
        
       /**
        * Database Connection 
        */
        try{
                           ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                           String ConnectionString="";
                           String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                           String strdsn=rs1.getString("Config.DSN");
                           String strhostname=rs1.getString("Config.HOST_NAME");
                           String strportno=rs1.getString("Config.PORT_NUMBER");
                           String strsid=rs1.getString("Config.SID");
                           String strdbusername=rs1.getString("Config.USER_NAME");
                           String strdbpassword=rs1.getString("Config.PASSWORD");
                           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                           Class.forName(strDriver.trim());
                           con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
          }
          catch(Exception e)
          {
              System.out.println("Exception in opening connection :"+e);
          }
          
          /** xml */
          String xml="";
         // empid="9744";
         String Command=request.getParameter("Command");
         System.out.println("Command ..... "+Command);
         if(empid.equalsIgnoreCase("02"))
        	 empid="10099";
  if(Command.equalsIgnoreCase("load_Section")){
          xml="<response><command>Load_Section</command>";
        
         /** Query */            
          String sql_all="select sectionid, (select section_name from fas_mst_sections where section_id= sectionid) as sectionname from fas_section_roles where userid = "+empid; 
         System.out.println(sql_all);
          int cnt=0;
    
    
         /** Fetch Section ID and Name from DB */
         try
         {
             ps=con.prepareStatement(sql_all);
             rs=ps.executeQuery();
             while (rs.next()) 
             {
                xml=xml+"<Section_Pair>";                     
                 xml=xml+"<section_id>"+rs.getInt("sectionid")+"</section_id>";  
                 xml=xml+"<section_name>"+rs.getString("sectionname")+"</section_name>";                      
                xml=xml+"</Section_Pair>";   
                cnt++;
             }
      
      
            if(cnt==0) {
                xml=xml+"<flag>NoData</flag>";   
            }
            else{
                xml=xml+"<flag>Success</flag>";   
            }
        }
        catch(Exception e) {
            xml=xml+"<flag>Failure</flag>";    
            System.out.println(e);
        }  xml=xml+"</response>";      
  }else if(Command.equalsIgnoreCase("loadGroup")){
	  xml="<response><command>loadGroup</command>";
      int sec_id=Integer.parseInt(request.getParameter("sec_id"));
      /** Query */            
       String sql_all="SELECT GROUP_ID,  GROUP_NAME FROM fas_mst_groups WHERE section_id="+sec_id+" ORDER BY group_id"; 
       int cnt=0;
       
       System.out.println("qry"+sql_all);
       
       try
       {
           ps=con.prepareStatement(sql_all);
           rs=ps.executeQuery();
           while (rs.next()) 
           {
                      
               xml=xml+"<GROUP_ID>"+rs.getInt("GROUP_ID")+"</GROUP_ID>";  
               xml=xml+"<GROUP_NAME>"+rs.getString("GROUP_NAME")+"</GROUP_NAME>";                      
           
              cnt++;
           }
   
   
         if(cnt==0) {
             xml=xml+"<flag>NoData</flag>";   
         }
         else{
             xml=xml+"<flag>Success</flag>";   
         }
     }
     catch(Exception e) {
         xml=xml+"<flag>Failure</flag>";    
         System.out.println(e);
     }  xml=xml+"</response>";      
  }else if(Command.equalsIgnoreCase("loadHead")){

	  xml="<response><command>loadHead</command>";
      int sec_id=Integer.parseInt(request.getParameter("sec_id"));
      int Grp_id=Integer.parseInt(request.getParameter("Grp_id"));
      /** Query */            
       String sql_all="select h.account_head_code,ch.account_head_desc from fas_mst_sections_groups_heads h inner join com_mst_account_heads ch on "+
                      " h.account_head_code=ch.account_head_code and section_id="+sec_id+" and group_id="+Grp_id+" order by account_head_code "; 
       int cnt=0;
       
       System.out.println("qry"+sql_all);
       
       try
       {
           ps=con.prepareStatement(sql_all);
           rs=ps.executeQuery();
           while (rs.next()) 
           {
                      
               xml=xml+"<account_head_code>"+rs.getInt("account_head_code")+"</account_head_code>";  
               xml=xml+"<account_head_desc>"+rs.getString("account_head_desc")+"</account_head_desc>";                      
           
              cnt++;
           }
   
   
         if(cnt==0) {
             xml=xml+"<flag>NoData</flag>";   
         }
         else{
             xml=xml+"<flag>Success</flag>";   
         }
     }
     catch(Exception e) {
         xml=xml+"<flag>Failure</flag>";    
         System.out.println(e);
     }  xml=xml+"</response>";      
  
  }
   /**/       
               
out.println(xml);
System.out.println(xml);      
}


public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Variables Declarations 
         */
        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps=null,ps1=null,ps2=null;
        ResultSet results=null,rs1=null,rs2=null;
        int oid=0;
        String office_level_id="";
    
        /**
         * Database Connection 
         */
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }


        /**
         * Set Content Type 
         */
        response.setContentType(CONTENT_TYPE);


        /** 
         *   Session Checking 
         */ 
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

        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);

        
        /**
         * Get Cashbook_month , Cashbook Year and Command ( Either Abstract or Summary ) 
         */
        int CashBook_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
        int CashBook_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
        int frm_yr=0,to_yr=0;
        if(CashBook_Month>=4)
        	{
        	frm_yr=CashBook_Year;
        	to_yr=CashBook_Year+1;
        	}
        else{
        	frm_yr=CashBook_Year-1;
        	to_yr=CashBook_Year;
        }
        int txtSection=0;
        int sup_No=0;
        String Type_wise="";
        int txtGrp=0,txtHead=0;
       
       
        try {
             txtSection =  Integer.parseInt(request.getParameter("txtSection"));
            sup_No=Integer.parseInt(request.getParameter("txtsupplement_no"));
             System.out.println("******************************************************"+txtSection);
            System.out.println("******************************************************"+sup_No);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        String Grp=request.getParameter("txtGrp");
        if(Grp.equalsIgnoreCase(""))Grp="0";
        txtGrp = Integer.parseInt(Grp);
        String Head=request.getParameter("txtHead");
        if(Head.equalsIgnoreCase(""))Head="0";
        txtHead = Integer.parseInt(Head);
        
        if(txtGrp==0 && txtHead==0){
        	Type_wise="All";}
        	else if(txtGrp!=0 && txtHead==0){
        		Type_wise="Group";
        	}else if(txtGrp!=0 && txtHead!=0){
        		Type_wise="Head";
        	}
       System.out.println("Type wise >>> "+Type_wise);
        String command = request.getParameter("Command");
       
        System.out.println("CashBook_Year---"+CashBook_Year);
        System.out.println("CashBook_Month--"+CashBook_Month);
        System.out.println("command --"+command);
          

        /**
         * Find Month Value 
         */
        String monthInWords = "";
        if (CashBook_Month == 1)
            monthInWords = "January";
        else if (CashBook_Month == 2)
            monthInWords = "February";
        else if (CashBook_Month == 3)
            monthInWords = "March";
        else if (CashBook_Month == 4)
            monthInWords = "April";
        else if (CashBook_Month == 5)
            monthInWords = "May";
        else if (CashBook_Month == 6)
            monthInWords = "June";
        else if (CashBook_Month == 7)
            monthInWords = "July";
        else if (CashBook_Month == 8)
            monthInWords = "August";
        else if (CashBook_Month == 9)
            monthInWords = "September";
        else if (CashBook_Month == 10)
            monthInWords = "October";
        else if (CashBook_Month == 11)
            monthInWords = "November";
        else if (CashBook_Month == 12)
            monthInWords = "December";
        System.out.println("CashBook_Month..." + CashBook_Month);
        
        
        
        /**
         * Seperate Report for compilation and other sections 
         */
        String sql_ptr_grp_Others="";
        String sql_ptr_grp_compilation="";
        String sql1="",sub_qry="",sub_qry1="";
        String sql2="";
        if(Type_wise.equalsIgnoreCase("All")){
    		sub_qry="";
    		sub_qry1="";
    	}else  if(Type_wise.equalsIgnoreCase("Group")){
    		sub_qry=" and GROUP_ID="+txtGrp;
    		sub_qry1="";
    	}else  if(Type_wise.equalsIgnoreCase("Head")){
    		sub_qry=" and GROUP_ID="+txtGrp;
    		sub_qry1=" and account_head_code="+txtHead;
    	}
        if(!command.equals("Sjv")){
        	if(!command.equals("Sjv_Summary")){
        		if(!command.equals("AbstractwithSJV")){
			        String report_option_test=request.getParameter("reptype");
			        System.out.println("report_option_test==========>"+report_option_test);
			        
			        if(report_option_test.equalsIgnoreCase("Regular"))
			        {
			        	
		        	 sql_ptr_grp_Others="" +
        		        "SELECT                                         \n" + 
        		        "     group_name                                \n" + 
        		        "FROM                                           \n" + 
        		        "     fas_mst_groups                            \n" + 
        		        "WHERE                                          \n" + 
        		        "   group_id IN                                 \n" + 
        		        "  (SELECT group_id                             \n" + 
        		        "  FROM fas_mst_sections_groups_heads           \n" + 
        		        "  WHERE section_id      =    "+txtSection+"    \n" + 
        		        sub_qry+
        		        "  AND account_head_code = year_account_head_code   \n" + 
        		        "  )";
        		        
        		        sql_ptr_grp_compilation="                \n" + 
        		        "select                                         \n" + 
        		        "   major_head_desc                             \n" + 
        		        "from                                           \n" + 
        		        "   COM_MST_MAJOR_HEADS                         \n" + 
        		        "where                                          \n" + 
        		        "   major_head_code = (                         \n" + 
        		        "       SELECT                                  \n" + 
        		        "           major_head_code                     \n" + 
        		        "       FROM                                    \n" + 
        		        "           com_mst_account_heads               \n" + 
        		        "       where account_head_code = year_account_head_code  \n" + 
        		        ")   ";
			        }
			        else{
			        sql_ptr_grp_Others="" +
			        "SELECT                                         \n" + 
			        "     group_name                                \n" + 
			        "FROM                                           \n" + 
			        "     fas_mst_groups                            \n" + 
			        "WHERE                                          \n" + 
			        "   group_id IN                                 \n" + 
			        "  (SELECT group_id                             \n" + 
			        "  FROM fas_mst_sections_groups_heads           \n" + 
			        "  WHERE section_id      =    "+txtSection+"    \n" + 
			        sub_qry+
			        "  AND account_head_code = year_account_head_code   \n" + 
			        "  )";
			        
			        sql_ptr_grp_compilation="                \n" + 
			        "select                                         \n" + 
			        "   major_head_desc                             \n" + 
			        "from                                           \n" + 
			        "   COM_MST_MAJOR_HEADS                         \n" + 
			        "where                                          \n" + 
			        "   major_head_code = (                         \n" + 
			        "       SELECT                                  \n" + 
			        "           major_head_code                     \n" + 
			        "       FROM                                    \n" + 
			        "           com_mst_account_heads               \n" + 
			        "       where account_head_code = year_account_head_code \n" + 
			        ")   ";
			        }
        	}
        		else
        		{
        			 sql_ptr_grp_Others="" +
        				        "SELECT                                         \n" + 
        				        "     group_name                                \n" + 
        				        "FROM                                           \n" + 
        				        "     fas_mst_groups                            \n" + 
        				        "WHERE                                          \n" + 
        				        "   group_id IN                                 \n" + 
        				        "  (SELECT group_id                             \n" + 
        				        "  FROM fas_mst_sections_groups_heads           \n" + 
        				        "  WHERE section_id      =    "+txtSection+"    \n" + 
        				        sub_qry+
        				        "  AND account_head_code = year_account_head_code   \n" + 
        				        "  )";
        				        
        				        sql_ptr_grp_compilation="                \n" + 
        				        "select                                         \n" + 
        				        "   major_head_desc                             \n" + 
        				        "from                                           \n" + 
        				        "   COM_MST_MAJOR_HEADS                         \n" + 
        				        "where                                          \n" + 
        				        "   major_head_code = (                         \n" + 
        				        "       SELECT                                  \n" + 
        				        "           major_head_code                     \n" + 
        				        "       FROM                                    \n" + 
        				        "           com_mst_account_heads               \n" + 
        				        "       where account_head_code = year_account_head_code  \n" + 
        				        ")   ";
        			
        		}
        	}
        	else
        	{
        		System.out.println("Else part of Sjv Summary");
        		
        		sql_ptr_grp_Others="" +
            	        "SELECT                                         \n" + 
            	        "     group_name                                \n" + 
            	        "FROM                                           \n" + 
            	        "     fas_mst_groups                            \n" + 
            	        "WHERE                                          \n" + 
            	        "   group_id IN                                 \n" + 
            	        "  (SELECT group_id                             \n" + 
            	        "  FROM fas_mst_sections_groups_heads           \n" + 
            	        "  WHERE section_id      =    "+txtSection+"    \n" + 
            	        sub_qry+
            	        "  AND account_head_code = year_account_head_code   \n" + 
            	        "  )";
            	        
            	        sql_ptr_grp_compilation="                \n" + 
            	        "select                                         \n" + 
            	        "   major_head_desc                             \n" + 
            	        "from                                           \n" + 
            	        "   COM_MST_MAJOR_HEADS                         \n" + 
            	        "where                                          \n" + 
            	        "   major_head_code = (                         \n" + 
            	        "       SELECT                                  \n" + 
            	        "           major_head_code                     \n" + 
            	        "       FROM                                    \n" + 
            	        "           com_mst_account_heads               \n" + 
            	        "       where account_head_code = year_account_head_code  \n" + 
            	        ")   ";
        	}
        }
        else
        { 
        	
        	System.out.println("All");
        	if(Type_wise=="All"){
        		
        	}
        	sql_ptr_grp_Others="" +
        	        "SELECT                                         \n" + 
        	        "     group_name                                \n" + 
        	        "FROM                                           \n" + 
        	        "     fas_mst_groups                            \n" + 
        	        "WHERE                                          \n" + 
        	        "   group_id IN                                 \n" + 
        	        "  (SELECT group_id                             \n" + 
        	        "  FROM fas_mst_sections_groups_heads           \n" + 
        	        "  WHERE section_id      =    "+txtSection+"    \n" + 
        	        sub_qry+
        	        "  AND account_head_code = year_account_head_code   \n" + 
        	        "  )";
        	        
        	        sql_ptr_grp_compilation="                \n" + 
        	        "select                                         \n" + 
        	        "   major_head_desc                             \n" + 
        	        "from                                           \n" + 
        	        "   COM_MST_MAJOR_HEADS                         \n" + 
        	        "where                                          \n" + 
        	        "   major_head_code = (                         \n" + 
        	        "       SELECT                                  \n" + 
        	        "           major_head_code                     \n" + 
        	        "       FROM                                    \n" + 
        	        "           com_mst_account_heads               \n" + 
        	        "       where account_head_code = year_account_head_code  \n" + 
        	        ")   ";
        	
        }
        
        System.out.println("sql_ptr_grp_Others==>"+sql_ptr_grp_Others);
        
        String sql_acc_head_Others="select account_head_code from fas_mst_sections_groups_heads where section_id="+txtSection+" "+sub_qry1+" "+sub_qry ;
        String sql_acc_head_compilation="select account_head_code from com_mst_account_heads";
        
        
        
        if (txtSection == 4 ) {
            sql1=sql_ptr_grp_compilation;
            sql2=sql_acc_head_compilation;            
        }
        else {
            sql1=sql_ptr_grp_Others;
            sql2=sql_acc_head_Others;
        }        
        
        
        File reportFile = null;
        
        UserProfile up=(UserProfile)session.getAttribute("UserProfile");
        int empid1= up.getEmployeeId();
        System.out.println("======================================================================>"+empid1);
        
        try {
                                  
                                   ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                                   ps.setInt(1,empid1);
                                   results=ps.executeQuery();
                                   if(results.next())
                                   {
                                   oid=results.getInt("OFFICE_ID");
                                   System.out.println("-----------------------------------------------------------------------------------"+oid);
                                   }
                                   results.close();
                                   ps.close();   
                                   
                               }
                               catch(Exception e) {
                                   System.out.println(e);
                               }
        
        
        
        
        
        
        
        
           try
           {
               System.out.println("*****************************************************************BEFORE"+oid);
           ps2 =
           connection.prepareStatement("select OFFICE_LEVEL_ID  from COM_MST_OFFICES  where OFFICE_ID=?");
           ps2.setInt(1, oid);
           rs2 = ps2.executeQuery();
           if(rs2.next())
           {
           office_level_id=rs2.getString("OFFICE_LEVEL_ID");
               System.out.println("*****************************************************************office_level_id"+office_level_id);
           if(office_level_id.equals("AW")) {
           
               System.out.println("*****************************************************************Enter"+office_level_id);
               ps1 =
               connection.prepareStatement("select REGION_OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  where OFFICE_ID=?");
               ps1.setInt(1, oid);
               rs1 = ps1.executeQuery();
               if(rs1.next()) {
                   
                   oid=rs1.getInt("REGION_OFFICE_ID");
                   
               }
               
               
               
           }
           }
           }
           catch(Exception e) {
           System.out.println(e);
        
        }
           
           
           System.out.println("*****************************************************************"+oid);
        
           Map map = null;
           map = new HashMap();
        
        
        try {
            
            if(oid==5000) {

System.out.println("****for HEAD OFFICE *****");
                if (command.equalsIgnoreCase("Abstract"))
                {
                	  map.put("f_yr", frm_yr);
                      map.put("t_yr", to_yr);
                System.out.println("enter into abstact***************************************************************");
                    reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract_Headoffice.jasper"));
                }
                else if (command.equalsIgnoreCase("Summary")) 
                {
                System.out.println("Enter into Summary%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                    reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Summary _Headoffice.jasper")); //3
                }

                else if (command.equalsIgnoreCase("Sjv")) 
                {
                System.out.println("Enter into sjv????????????????????");
                map.put("f_yr", frm_yr);
                map.put("t_yr", to_yr);
                /*@NK included on 11/Oct/2019 New Abstract Report (SJV only)*/ 
                    //reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract_Headoffice_sjv.jasper")); 
                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/NewGPF_Abstract_Headoffice_sjv.jasper")); //2
                /*@NK included on 11/Oct/2019 New Abstract Report (SJV only)*/
                }


                else if (command.equalsIgnoreCase("Sjv_Summary")) 
                {
                System.out.println("Enter into sjv++++++++++++++++++++++++++++");
                /*@NK included on 11/Oct/2019 New Detail Report (SJV only)*/
                    //reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Summary _Headoffice_sjv_only.jasper"));
                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/NewGPF_Summary _Headoffice_sjv_only.jasper")); //5
                /*@NK included on 11/Oct/2019 New Detail Report (SJV only)*/
                }
                //added on 19/01/2012

                 else if (command.equalsIgnoreCase("AbstractwithSJV")) 
                 {
                    System.out.println("Enter into AbstractwithSJV for Head office-------------------------------");
                    String report_option=request.getParameter("reptype");
                    if(report_option.equalsIgnoreCase("Regular"))
                    {
                    	  map.put("f_yr", frm_yr);
                          map.put("t_yr", to_yr);
                            System.out.println("Report for Regular option be created");
                            reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract_Headoffice.jasper"));
                    }
                    else if(report_option.equalsIgnoreCase("InclusiveSJV"))
                    {
                    	  map.put("f_yr", frm_yr);
                          map.put("t_yr", to_yr);
                     System.out.println("Report for Inclusive SJV option be created");
                     reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract_Headoffice_sjv.jasper"));//1
                    }
                 }
                //added on 20/02/2012
                 else if (command.equalsIgnoreCase("SummarywithSJV")) 
                 {
                    System.out.println("Enter into SummarywithSJV for Head office-////////////////");
                    String report_option=request.getParameter("reptype");
                    if(report_option.equalsIgnoreCase("Regular"))
                    {
                    	System.out.println("test");
                            System.out.println("Report for Regular Summary  option be created in test");
                            reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Summary _Headoffice.jasper"));
                    }
                    else if(report_option.equalsIgnoreCase("InclusiveSJV"))
                    {
                     System.out.println("Report for Inclusive SJV  summary option be created::here1111111:::");
                     /*@NK included on 15/Oct/2019 New Detail Report (SJV only)*/
                     reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Summary _Headoffice_sjv.jasper"));//4
                    // reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/NewGPF_Summary _Headoffice_sjv.jasper"));
                     /*@NK included on 15/Oct/2019 New Detail Report (SJV only)*/
                    }
                 }
               
            }
            else
            {
            
            if (command.equalsIgnoreCase("Abstract"))
            {
            
            System.out.println("enter into abstact***************************************************************");
                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract.jasper"));
            }
            else if (command.equalsIgnoreCase("Summary")) 
            {
            System.out.println("Enter into Summary%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Summary.jasper"));
            }
            
                else if (command.equalsIgnoreCase("Sjv")) 
                {
                System.out.println("Enter into Sjv&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                System.out.println("Enter into Sjv for other offices^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/NewGPF_Abstract_SJV.jasper"));
//                reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract_SJV_NEW.jasper"));
                }
                
                else if (command.equalsIgnoreCase("Sjv_Summary")) 
                {
                System.out.println("Enter into Sjv#############################################");
                    reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Summary_sjv1.jasper"));
                }
                //added on 19/01/2012
                 else if (command.equalsIgnoreCase("AbstractwithSJV")) 
                 {
                    System.out.println("Enter into AbstractwithSJV for other offices^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                    String report_option=request.getParameter("reptype");
                    if(report_option.equalsIgnoreCase("Regular"))
                    {
                            System.out.println("Report for Regular option be created");
                        reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract.jasper"));
                    }
                    else if(report_option.equalsIgnoreCase("InclusiveSJV"))
                    {
                     System.out.println("Report for Inclusive SJV option be created");
                        //reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract_SJV.jasper"));
                     map.put("f_yr", frm_yr);
                     map.put("t_yr", to_yr);
                     map.put("Section", txtSection);
                     
                     reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Abstract_Headoffice_sjv.jasper"));
                    }
                 }
                //added on 20/02/2012
                 else if (command.equalsIgnoreCase("SummarywithSJV")) 
                 {
                    System.out.println("Enter into SummarywithSJV for other offices^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                    String report_option=request.getParameter("reptype");
                    if(report_option.equalsIgnoreCase("Regular"))
                    {
                            System.out.println("Report for Regular option be created");
                        reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Summary.jasper"));
                    }
                    else if(report_option.equalsIgnoreCase("InclusiveSJV"))
                    {
                     System.out.println("Report for Inclusive SJV option be created here222222:::");
                        reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GPF_Sum_Abs/GPF_Summary_sjv.jasper"));
                    }
                 }
                
                
            }
            
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
            
            JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
            int yr=0;
            if(CashBook_Month<=3)
            {
            	yr=CashBook_Year-1;
            }
            else
            {
            	yr=CashBook_Year;
            }
            System.out.println("from year:::"+yr);
          
            map.put("cb_mon", CashBook_Month);
            map.put("cb_year", CashBook_Year);
            map.put("month_value", monthInWords);
            map.put("sql1",sql1);
            map.put("sql2",sql2);
            map.put("officeid",oid);
            map.put("sup_no",sup_No);
            map.put("FromYear",yr);
            
            System.out.println("report File-->"+reportFile);

   /*         System.out.println("CashBook_Month-->"+CashBook_Month);
            System.out.println("CashBook_Year-->"+CashBook_Year);
            System.out.println("monthInWords-->"+monthInWords);*/
            System.out.println("sql1--->"+sql1);
            System.out.println("sql2--->"+sql2);
            System.out.println("Mappppppppp      -->"+map);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
            
            
            String rtype = "PDF";

            if (rtype.equalsIgnoreCase("PDF")) {
                 
                byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"GPFReport_NEW.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }


        } catch (Exception ex) {
            String connectMsg ="Could not create the report " + ex.getMessage(); 
            String con_err ="Could not create the report " + ex; 
            System.out.println(con_err);
            System.out.println(connectMsg);
        }


    }
}

