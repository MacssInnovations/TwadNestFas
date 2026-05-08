package Servlets.FAS.FAS1.CivilBills.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class phone_master_servlet extends HttpServlet
{
  //private static final String CONTENT_TYPE="text/xml; charset=windows-1252";
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
 	{
		String CONTENT_TYPE="text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                //System.out.println("Welcome to Servlet");
		String cmnd="";
		String xml="";
                String user_id;
                user_id = "";
                String emp_name="";
                int emp_id=0;
                PrintWriter pw=response.getWriter();
                HttpSession session=null;
                /*********** connection establishment****************/
                Connection con=null;
                ResultSet rs,rs2,rs3=null;
                PreparedStatement ps=null, ps2=null;
                      xml="<response>";
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
                                             
                            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                }
                catch(Exception e)
                {
                        System.out.println("Exception in connection...."+e);
                } 
                      try
                      {
                          session=request.getSession(false);
                          if(session==null)
                          {
                              System.out.println(request.getContextPath()+"/index.jsp");
                              response.sendRedirect(request.getContextPath()+"/index.jsp");
                              return;
                          }
                          System.out.println(session);
                      } 
                      catch(Exception e)
                      {
                            System.out.println("Redirect Error :"+e);
                      }
                     //String user_id=request.getSession();
                /****************** getting the values from Button Pressed***********/
                try
                {
                          cmnd =  request.getParameter("command");     
                          System.out.println("Command passed via the button pressed : " + cmnd);
                          
                }
                  catch(Exception e3)
                  {
                    e3.printStackTrace();
                  }
                  /*****************Getting the values from jsp page ***************/
                    if(cmnd.equalsIgnoreCase("loadempdetails")) 
                    {
                          emp_id=Integer.parseInt(request.getParameter("emp_id"));
                          
                        xml=xml+"<command>loadempdetails</command>";
                        try
                        {             
                                    String sqlload="select a.EMPLOYEE_ID,a.OFFICE_ID,b.employee_name, c.OFFICE_SHORT_NAME,d.DESIGNATION from "+
                                                         "   ( "+
                                                         "     select EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=? and EMPLOYEE_STATUS_ID='WKG' "+
                                                         "   )a left outer join "+
                                                          "  ( "+
                                                         "     select  EMPLOYEE_ID,EMPLOYEE_INITIAL||' '||EMPLOYEE_NAME as employee_name from HRM_MST_EMPLOYEES "+
                                                         "   )b on a.EMPLOYEE_ID=b.EMPLOYEE_ID left outer join "+
                                                        "    ( "+
                                                        "      select OFFICE_ID,OFFICE_SHORT_NAME from COM_MST_OFFICES "+
                                                        "    )c on a.OFFICE_ID=c.OFFICE_ID left outer join "+
                                                         "   ( "+
                                                        "      select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS "+
                                                         "   )d on a.DESIGNATION_ID=d.DESIGNATION_ID"              ;
                                    ps2 = con.prepareStatement(sqlload);
                                    ps2.setInt(1,emp_id);
                                    rs2=ps2.executeQuery();
                                    if(rs2.next())
                                    {
                                        emp_name=rs2.getString("employee_name");
                                        xml=xml+"<desig_name>"+rs2.getString("DESIGNATION")+"</desig_name>";
                                        xml=xml+"<OFFICE_ID>"+rs2.getString("OFFICE_ID")+"</OFFICE_ID>";
                                        xml=xml+"<office_name>"+rs2.getString("OFFICE_SHORT_NAME")+"</office_name>";                                       
                                        xml=xml+"<emp_name>"+emp_name+"</emp_name>";
                                        xml=xml+"<flag>success</flag>";
                                    }
                                    else
                                    xml=xml+"<flag>nodata</flag>";
                         } 
                          catch(Exception e)
                          {
                                            xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                            System.out.println(e);
                           }
                  }
                  else if(cmnd.equalsIgnoreCase("LoadGrid"))
                {
                        //System.out.println("\n*************\nLoad Phone Master Details \n**************\n");
                        int acc_unit_off_id=Integer.parseInt(request.getParameter("acc_unit_officeid"));
                        //System.out.println("Acc_unit_office id Selected :"+acc_unit_off_id);
                        int user_cat_id=Integer.parseInt(request.getParameter("user_cat_id"));
                        //System.out.println("user Selected :"+user_cat_id);
                        xml=xml+"<command>LoadGrid</command>";
                        int count=0;
                         try
                        {             
                                    String sqlload1="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID," +
                                    "EMPLOYEE_OFFICE_ID as Employee_id,USER_CATEGORY_ID FROM FAS_PHONE_MASTER where ACCOUNTING_UNIT_OFFICE_ID=? AND USER_CATEGORY_ID=?";
                                    ps2 = con.prepareStatement(sqlload1);
                                    ps2.setInt(1,acc_unit_off_id);
                                    ps2.setInt(2,user_cat_id);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next())
                                    {
                                        xml=xml+"<emp_det><Acc_unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</Acc_unit_id>";
                                        xml=xml+"<Acc_unit_officeid>"+rs2.getInt("ACCOUNTING_UNIT_OFFICE_ID")+"</Acc_unit_officeid>";                                       
                                        xml=xml+"<emp_id>"+rs2.getInt("EMPLOYEE_ID")+"</emp_id>";
                                        xml=xml+"<user_cat_id>"+rs2.getInt("USER_CATEGORY_ID")+"</user_cat_id></emp_det>";
                                         count++;
                                    }
                                   
                                    if(count>0)
                                    xml=xml+"<flag>success</flag>";
                                    else
                                    xml=xml+"<flag>nodata</flag>";
                                    
                         } //try close
                          catch(Exception e)
                          {
                                            xml=xml+"<flag>failure</flag>";
                                            //xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                            System.out.println(e);
                           }
                        
                        
                }
                  else if(cmnd.equalsIgnoreCase("GridPhoneDet")){
                	  int c=0;
                	  int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                      int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
                	  xml=xml+"<command>GridPhone</command>";
                	  String Qry="SELECT M.ACCOUNTING_UNIT_ID, " +
                	  "  M.ACCOUNTING_UNIT_OFFICE_ID, " +
                	  "  M.EMPLOYEE_OFFICE_ID as EMPLOYEE_ID, " +
                	  "  M.USER_CATEGORY_ID, " +
                	  "  M.REMARKS, " +
                	  "  T.EMPLOYEE_OFFICE_ID as EMPLOYEE_ID, " +
                	  "  T.SL_NO, " +
                	  "  T.PURPOSE, " +
                	  "  CASE " +
                	  "    WHEN t.purpose ='O' " +
                	  "    THEN 'Office' " +
                	  "    WHEN t.purpose='R' " +
                	  "    THEN 'Residence' " +
                	  "    WHEN t.purpose='F' " +
                	  "    THEN 'Fax' " +
                	  "  END purpose_name, " +
                	  "  T.CONNECTION_TYPE, " +
                	  "  CASE " +
                	  "    WHEN t.CONNECTION_TYPE ='L' " +
                	  "    THEN 'LandLine' " +
                	  "    WHEN t.CONNECTION_TYPE='M' " +
                	  "    THEN 'Mobile' " +
                	  "  END connection_Name, " +
                	  "  T.STD_CODE, " +
                	  "  T.PHONE_NO, " +
                	  "  T.SERVICE_PROVIDER_NAME, " +
                	  "  T.SERVICE_TYPE, " +
                	  "  T.CEILING_TYPE, " +
                	  "    case when  T.CEILING_TYPE='L' then 'Limited' "+
                      "    when  T.CEILING_TYPE='U' then 'UnLimited' "+
                      "   when  T.CEILING_TYPE='N' then 'Not Applicable' end ceil_desc,"+
                	  "  T.CEILING_LIMIT_AMOUNT, " +
                	  "  T.PARTICULARS, " +
                	  "  T.UPDATED_BY_USED_ID, " +
                	  "  T.UPDATED_DATE, " +
                	  "  T.USAGE_DETAILS ,  CASE "+
    " WHEN T.USAGE_DETAILS='IU' THEN 'In Use'"+
    " WHEN T.USAGE_DETAILS='ED'  THEN 'Ex_Dir'"+
    " WHEN T.USAGE_DETAILS='SC' THEN 'Safe Custody'"+
     " WHEN T.USAGE_DETAILS='DC' THEN 'Disconnected'"+
  " END Usage_desc  " +
                	  " FROM FAS_PHONE_MASTER M " +
                	  "INNER JOIN FAS_PHONE_TRN T " +
                	  "ON m.accounting_unit_id        =t.accounting_unit_id " +
                	  "AND m.accounting_unit_office_id=m.accounting_unit_office_id  AND m.employee_office_id=t.employee_office_id " +
                	  "AND m.accounting_unit_id       ="+cmbAcc_UnitCode+ " AND m.accounting_unit_office_id="+cmbOffice_code;
                	  try{
                		  System.out.println("Qry >>> "+Qry);
                		   ps=con.prepareStatement(Qry);
                		   //ps.setInt(1,cmbAcc_UnitCode);
                		  // ps.setInt(2,cmbOffice_code);
                		   rs=ps.executeQuery();
                		   while(rs.next()){
                			   c+=1;
                			   xml+="<ACCOUNTING_UNIT_ID>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";
                			   xml+="<ACCOUNTING_UNIT_OFFICE_ID>"+rs.getInt("ACCOUNTING_UNIT_OFFICE_ID")+"</ACCOUNTING_UNIT_OFFICE_ID>";
                			   xml+="<EMPLOYEE_ID>"+rs.getInt("EMPLOYEE_ID")+"</EMPLOYEE_ID>";
                			   xml+="<USER_CATEGORY_ID>"+rs.getInt("USER_CATEGORY_ID")+"</USER_CATEGORY_ID>";
                			   xml+="<REMARKS>"+rs.getString("REMARKS")+"</REMARKS>";
                			   xml+="<SL_NO>"+rs.getInt("SL_NO")+"</SL_NO>";
                			   xml+="<PURPOSE>"+rs.getString("PURPOSE").trim()+"</PURPOSE>";
                			   xml+="<purpose_name>"+rs.getString("purpose_name")+"</purpose_name>";
                			   xml+="<CONNECTION_TYPE>"+rs.getString("CONNECTION_TYPE").trim()+"</CONNECTION_TYPE>";
                			   xml+="<connection_Name>"+rs.getString("connection_Name")+"</connection_Name>";
                			   xml+="<STD_CODE>"+rs.getString("STD_CODE")+"</STD_CODE>";
                			   xml+="<PHONE_NO>"+rs.getString("PHONE_NO")+"</PHONE_NO>";
                			   xml+="<SERVICE_PROVIDER_NAME>"+rs.getString("SERVICE_PROVIDER_NAME")+"</SERVICE_PROVIDER_NAME>";
                			   xml+="<SERVICE_TYPE>"+rs.getString("SERVICE_TYPE")+"</SERVICE_TYPE>";
                			   xml+="<CEILING_TYPE>"+rs.getString("CEILING_TYPE")+"</CEILING_TYPE>";
                			  xml+="<ceil_desc>"+rs.getString("ceil_desc")+"</ceil_desc>";
                			   xml+="<CEILING_LIMIT_AMOUNT>"+rs.getBigDecimal("CEILING_LIMIT_AMOUNT")+"</CEILING_LIMIT_AMOUNT>";               			   
                			   xml+="<PARTICULARS>"+rs.getString("PARTICULARS")+"</PARTICULARS>";
                			   xml+="<USAGE_DETAILS>"+rs.getString("USAGE_DETAILS")+"</USAGE_DETAILS>";
                			   xml+="<Usage_desc>"+rs.getString("Usage_desc")+"</Usage_desc>";
                		   }
                		   if(c>0){
                			   xml+="<flag>success</flag>";
                		   }else{
                			   xml+="<flag>failure</flag>";
                		   }
                		  
                	  }catch (Exception e) {
						System.out.println("Error in Execute select Qry"+e.getMessage());
					}
                	  
                  }
                
                else if(cmnd.equalsIgnoreCase("LoadpopupGrid"))
                {
                        //System.out.println("\n*************\nLoad Phone Transaction Details \n**************\n");
                        xml=xml+"<command>LoadpopupGrid</command>";
                        int count=0;
                        int empl_id=Integer.parseInt(request.getParameter("empl_id"));
                         try
                        {             
                                    String sqlload2="select SL_NO,trim(PURPOSE) as PURPOSE,trim(CONNECTION_TYPE) as CONNECTION_TYPE,trim(USAGE_DETAILS) as USAGE_DETAILS,STD_CODE,PHONE_NO,SERVICE_PROVIDER_NAME," +
                                    "SERVICE_TYPE,trim(CEILING_TYPE)as CEILING_TYPE,CEILING_LIMIT_AMOUNT,PARTICULARS FROM FAS_PHONE_TRN " +
                                    "where EMPLOYEE_ID=? order by SL_NO";
                                    ps2 = con.prepareStatement(sqlload2);
                                    ps2.setInt(1,empl_id);
                                    rs3=ps2.executeQuery();
                                    while(rs3.next())
                                    {
                                        xml=xml+"<emp_det_trn><SL_No>"+rs3.getInt("SL_NO")+"</SL_No>";
                                        xml=xml+"<Purpose>"+rs3.getString("PURPOSE")+"</Purpose>";                                       
                                        xml=xml+"<Connection_type>"+rs3.getString("CONNECTION_TYPE")+"</Connection_type>";
                                        if(rs3.getString("USAGE_DETAILS")==null)
                                                xml=xml+"<usage_details>-</usage_details>";
                                        else
                                                xml=xml+"<usage_details>"+rs3.getString("USAGE_DETAILS")+"</usage_details>";
                                        if(rs3.getString("STD_CODE")==null)
                                                xml=xml+"<STD_code>-</STD_code>";
                                        else
                                                xml=xml+"<STD_code>"+rs3.getString("STD_CODE")+"</STD_code>";
                                        xml=xml+"<phone_no>"+rs3.getString("PHONE_NO")+"</phone_no>";
                                        xml=xml+"<SP_name>"+rs3.getString("SERVICE_PROVIDER_NAME")+"</SP_name>";                                       
                                        xml=xml+"<SP_type>"+rs3.getString("SERVICE_TYPE")+"</SP_type>";
                                        xml=xml+"<Ceil_type>"+rs3.getString("CEILING_TYPE")+"</Ceil_type>";
                                        xml=xml+"<Ceil_amt>"+rs3.getFloat("CEILING_LIMIT_AMOUNT")+"</Ceil_amt>";                                       
                                        xml=xml+"<particulars><![CDATA["+rs3.getString("PARTICULARS")+"]]></particulars></emp_det_trn>";
                                    }
                                    count++;
                                    if(count>0)
                                    xml=xml+"<flag>success</flag>";
                                    else
                                    xml=xml+"<flag>nodata</flag>";
                                    
                         } //try close
                          catch(Exception e)
                          {
                                            xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                            System.out.println(e);
                           }
                        
                        
                }else if (cmnd.equalsIgnoreCase("check")) {

            			try {
            				String txtOffice_Id_1 = request.getParameter("txtOffice_Id") != null ? request
            						.getParameter("txtOffice_Id").trim() : "";

            				String sql ="select OFFICE_NAME from COM_MST_OFFICES where  OFFICE_ID='"+txtOffice_Id_1 + "'";
            				// System.out.println(sql);
            				 rs = null;
            				Statement st = con.createStatement();
            				rs = st.executeQuery(sql);
            				if (rs.next()) {
            					xml = "<response> <status>success</status> <command>existing</command>"+
            						 "<officename>"+rs.getString("OFFICE_NAME")+"</officename>";
            				} else {
            					xml = "<response><status>success</status><command>Notexisting</command>";
            				}
            			} catch (Exception e) {
            				xml = "<response><flag>failure</flag>";
            				e.printStackTrace();
            			}
            		}
                     xml=xml+"</response>";
                          System.out.println("xml is : " + xml);
                          pw.write(xml);
                          pw.flush();
                          pw.close();
        }
public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
{/*
                String CONTENT_TYPE="text/html";
		response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
		String cmnd="";
		 PreparedStatement p_del,p_del1=null;
    	 ResultSet r_del,r_del1=null;
		//String xml="";//String user_id="";//String acc_unit_code="";//int emp_id=0;//int office_id=0;
                String purposeOR="";String conn_typeLM="";String usage_details="";String SP_name="";String SP_Type="";String remarks="";
                String phone_no="";String std_code="";String ceil_type="";float ceil_amt=0;String particulars="";
                int acc_unit_id=0;int acc_office_id=0;int eid=0;
                int user_category_id=0;
                int Total_TRN_Rec=0; 
                String Emp_value1="",Emp_value2="";
                int cnt=0;
                PrintWriter pw=response.getWriter();
                //System.out.println("Welcome to dopost");
                HttpSession session=request.getSession(false);
                String update_user=(String)session.getAttribute("UserId");
                long l=System.currentTimeMillis();
		Timestamp ts=new Timestamp(l);
                System.out.println("Session :"+session);
                *//*********** connection establishment****************//*
                Connection con=null;
                ResultSet rs=null,rs_set=null,rs_set1=null,rs_set2=null;
                PreparedStatement ps=null,ps_st=null,ps_st1=null,ps_st2=null;
               
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
                                             
                                ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                }
                catch(Exception e)
                {
                        System.out.println("Exception in connection...."+e);
                } 
                      try
                      {
                          session=request.getSession(false);
                          if(session==null)
                          {
                              System.out.println(request.getContextPath()+"/index.jsp");
                              response.sendRedirect(request.getContextPath()+"/index.jsp");
                              return;
                          }
                          System.out.println(session);
                      } 
                      catch(Exception e)
                      {
                            System.out.println("Redirect Error :"+e);
                      }
                      session=request.getSession(false);
                     UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                      eid=empProfile.getEmployeeId();
                      System.out.println("employee id:"+eid);
                                   
                      try 
                      {
                          //eid=Integer.parseInt(request.getParameter("txtEmpId"));
                          //int userid=
                          ps=con.prepareStatement("select USER_ID,USER_CATEGORY_ID,EMPLOYEE_ID from SEC_MST_USERS where EMPLOYEE_ID=?");
                          ps.setInt(1,eid);
                          rs=ps.executeQuery(); 
                          if(rs.next()) 
                          {
                               user_category_id=rs.getInt("USER_CATEGORY_ID");
                          }
                          rs.close();
                          ps.close();
                      }
                      catch(Exception e) 
                      {
                          System.out.println("Err in eid selection:  "+e.getMessage());    
                      }
                 try
                {
                          cmnd =  request.getParameter("Command");     
                          System.out.println("Command passed via the button pressed : " + cmnd);
                }
                  catch(Exception e3)
                  {
                    e3.printStackTrace();
                  }
///////////////////////////////Getting the values from the JSP Page//////////////////////////////////////////////////
         if(cmnd.equalsIgnoreCase("Add")) 
        {
        	 cnt=0;int SL_NO=1;
             int errcode=0;
             String sub_qry="",sub_qry1="",D_qry="",D_qry1="";
        	 int c_val1=0,c_val2=0;
             try{acc_unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}catch(Exception e){System.out.println("Exception arised"+e);}
             try{acc_office_id=Integer.parseInt(request.getParameter("cmbOffice_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
             String emp_id[]=request.getParameterValues("hid_Empid");  
             try{remarks=request.getParameter("txtRemarks");}catch(Exception e){System.out.println("Exception arised"+e);}
             String Grid_phone_no[]=request.getParameterValues("H_phone_no");
             sub_qry="select count(*) as c_no from fas_phone_master where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
      	   try{
      		   ps_st1=con.prepareStatement(sub_qry);
      		   ps_st1.setInt(1,acc_unit_id);
      		   ps_st1.setInt(2,acc_office_id);
      		   rs_set1=ps_st1.executeQuery();
      		   while(rs_set1.next()){
      			 c_val1=rs_set1.getInt("c_no");  
      		   }
      	   }catch (Exception e) {
				System.out.println("Exception Conut"+e);
			}
      	   
      	   sub_qry1="select count(*) as c_val from fas_phone_trn where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
      	   try{
      		   ps_st2=con.prepareStatement(sub_qry1);
      		   ps_st2.setInt(1,acc_unit_id);
      		   ps_st2.setInt(2,acc_office_id);
      		   rs_set2=ps_st2.executeQuery();
      		   while(rs_set2.next()){
      			 c_val2=rs_set2.getInt("c_val");  
      		   }
      	   }catch (Exception e) {
				System.out.println("Exception Conut 1 "+e);
			}
      	   System.out.println("c_val1"+c_val1+"  >>>>>>>>c_val1"+c_val2);
      	   int c=0,c1=0;
           if(c_val1!=0 && c_val2!=0)  {
          	 D_qry="delete from fas_phone_master where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
          	 try{
        		 p_del=con.prepareStatement(D_qry);
        		 p_del.setInt(1,acc_unit_id);
               p_del.setInt(2,acc_office_id);
        		  c = p_del.executeUpdate();
        		if(c>0){
        			 D_qry1="delete from fas_phone_trn where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
        		  try{
        			 p_del1=con.prepareStatement(D_qry1);
        			 p_del1.setInt(1,acc_unit_id);
                   p_del1.setInt(2,acc_office_id);
            		 c1= p_del1.executeUpdate();
         		   
         	   }catch (Exception e) {
 				System.out.println("Exception Conut 3 "+e);
 			}
        		}
        	   }catch (Exception e) {
				System.out.println("Exception Conut 4 "+e);
			}
        		
           } 
             
             for(int jj=0;jj<Grid_phone_no.length;jj++)
            {   
          	 Emp_value1=emp_id[jj]; 
             try 
             {
                ps_st=con.prepareStatement("select USER_ID,USER_CATEGORY_ID,EMPLOYEE_ID from SEC_MST_USERS where EMPLOYEE_ID=?");
                ps_st.setInt(1,Integer.parseInt(emp_id[jj]));
                 rs_set=ps_st.executeQuery(); 
                 if(rs_set.next()) 
                 {
                      user_category_id=rs_set.getInt("USER_CATEGORY_ID");
                 }
               
                 //System.out.println("Employee ID >> "+jj+" "+emp_id[jj]+"  user_category_id  "+user_category_id);
try{
                 con.clearWarnings();
                 con.setAutoCommit(false);
                 if(!Emp_value1.equalsIgnoreCase(Emp_value2)){ 
              	  
                 String sqlins="insert into fas_phone_master (ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,EMPLOYEE_OFFICE_ID," +
                 "USER_CATEGORY_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?)";
                 ps=con.prepareStatement(sqlins);
                 ps.setInt(1,acc_unit_id);
                 ps.setInt(2,acc_office_id);
                 ps.setInt(3,Integer.parseInt(emp_id[jj]));
                 ps.setInt(4,user_category_id);
                 ps.setString(5,remarks);
                 ps.setString(6,update_user);
                 ps.setTimestamp(7,ts);          
                  errcode=ps.executeUpdate();
                    ps.close();
                 }
                 else if(Emp_value1.equalsIgnoreCase(Emp_value2)){ 
              	   //System.out.println("Emp_value1 >> "+Emp_value1+"  Emp_value2  >> "+Emp_value2);
                     cnt=1;
                     }
                 else{
              	   System.out.println("Error in If ... ");
                 }

                    //System.out.println("errcode value:  "+errcode+"  count value >> "+cnt);    
	                     if(errcode==0)
	                     {         
		                       //System.out.println("redirect");
		                       sendMessage(response,"The insertion into the phone master table Failed ","ok");		                       
	                     }
	                     else if(errcode!=0 || cnt==1)
	                     {
	                    	 try{
		                    	System.out.println("The records inserted into the phone master table scuccessfully");
		                    	
                                   
                                      String No_TRN_Rec[]=request.getParameterValues("H_purposeOR");
                                      Total_TRN_Rec=No_TRN_Rec.length;//Integer.parseInt(No_TRN_Rec);
                                      //System.out.println(" Total_TRN_Records :"+Total_TRN_Rec);
	                   
	                       String Grid_connection_type[]=request.getParameterValues("H_connection_type");
                         String H_usage_details[]=request.getParameterValues("H_usage_details");
	                       String Grid_STD_code[]=request.getParameterValues("H_STD_code");
	                       String Grid_SerProName[]=request.getParameterValues("H_SerProName");
	                       String Grid_SerProType[]=request.getParameterValues("H_SerProType");
	                       String Grid_ceiling_type[]=request.getParameterValues("H_ceiling_type");
	                       String Grid_ceil_Limit_amt[]=request.getParameterValues("H_ceil_Limit_amt");
	                       String Grid_purposeOR[]=request.getParameterValues("H_purposeOR");
	                       String Grid_Particulars[]=request.getParameterValues("H_Particulars");
	                       try{purposeOR=Grid_purposeOR[jj];}catch(Exception e1){System.out.println("exception in trans 1 "+e1);}
                         try{conn_typeLM=Grid_connection_type[jj];}catch(Exception e8){System.out.println("exception in trans 2 "+e8);}                                   
                         try{usage_details=H_usage_details[jj];}catch(Exception e2){System.out.println("exception in trans 3  "+e2);}
                         try{std_code=Grid_STD_code[jj];}catch(Exception e2){System.out.println("exception in trans 4 "+e2);}
                         try{phone_no=Grid_phone_no[jj];}catch(Exception e3){System.out.println("exception in trans 5 "+e3);}
                         try{SP_name=Grid_SerProName[jj];}catch(Exception e4){System.out.println("exception in trans 6 "+e4);}
                         try{SP_Type=Grid_SerProType[jj];}catch(Exception e5){System.out.println("exception in trans 7 "+e5);}
                         try{ceil_type=Grid_ceiling_type[jj];}catch(Exception e6){System.out.println("exception in trans 8 "+e6);}
                         try{ceil_amt=Float.parseFloat(Grid_ceil_Limit_amt[jj]);}catch(Exception e7){System.out.println("exception in trans 9 "+e7);}
                         try{particulars=Grid_Particulars[jj];}catch(Exception e9){System.out.println("exception in trans 10 "+e9);}
							
								String sql = "     insert into FAS_PHONE_TRN(ACCOUNTING_UNIT_ID, "
									+ "     ACCOUNTING_UNIT_OFFICE_ID ,EMPLOYEE_OFFICE_ID, SL_NO, PURPOSE, CONNECTION_TYPE,"
									+ "     USAGE_DETAILS,STD_CODE,PHONE_NO,SERVICE_PROVIDER_NAME,SERVICE_TYPE,CEILING_TYPE,CEILING_LIMIT_AMOUNT,"
									+ "     PARTICULARS,UPDATED_BY_USED_ID,UPDATED_DATE) "
									+ "     values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
								ps=con.prepareStatement(sql);
                                 ps.setInt(1,acc_unit_id);
                                 ps.setInt(2,acc_office_id);
                                 ps.setInt(3,Integer.parseInt(emp_id[jj]));
                                 ps.setInt(4,SL_NO);
                                 ps.setString(5,purposeOR);
                                 ps.setString(6,conn_typeLM);
                                 ps.setString(7,usage_details);
                                 ps.setString(8,std_code);
                                 ps.setString(9,phone_no);
                                 ps.setString(10,SP_name);
                                 ps.setString(11,SP_Type);
                                 ps.setString(12,ceil_type);
                                 ps.setFloat(13,ceil_amt);
                                 ps.setString(14,particulars);
                                 ps.setString(15,update_user);
                                 ps.setTimestamp(16,ts);
                                 SL_NO++;
                              errcode= ps.executeUpdate();
                         ps.close();
                         con.commit();
                         sendMessage(response,"The Records Saved Successfully ","ok");
	                    	 }catch (Exception e) {
								System.out.println("Exceoption in inner loop >> "+e);
							}
	                    	 }
	                     rs_set.close();
	                     ps_st.close();
}
					catch (Exception e) {
						try {
							con.rollback();
						} catch (SQLException sqle) {
							System.out.println("exception in rollback " + sqle);
						}
						sendMessage(
								response,
								"The Insertion of records into the table Failed ",
								"ok");
						System.out.println("Exception occur due to " + e);
					}
                   finally
                   {
                       System.out.println("done");
                       try{
                      	 con.setAutoCommit(true);  
                       }catch(SQLException sqle){System.out.println("Exception arised :"+sqle);}
                   }
             }
             catch(Exception e) 
             {
                 System.out.println("Last slection:  "+e.getMessage());    
             }
             Emp_value2=emp_id[jj];
            
            }}
         pw.flush();
         pw.close();
                       
*/

    String CONTENT_TYPE="text/html";
response.setContentType(CONTENT_TYPE);
    response.setHeader("Cache-Control","no-cache");
String cmnd="";
PreparedStatement p_del,p_del1=null;
ResultSet r_del,r_del1=null;
//String xml="";//String user_id="";//String acc_unit_code="";//int emp_id=0;//int office_id=0;
    String purposeOR="";String conn_typeLM="";String usage_details="";String SP_name="";String SP_Type="";String remarks="";
    String phone_no="";String std_code="";String ceil_type="";float ceil_amt=0;String particulars="";
    int acc_unit_id=0;int acc_office_id=0;int eid=0;
    int user_category_id=0;
    int Total_TRN_Rec=0; 
    String Emp_value1="",Emp_value2="";
    int cnt=0;
  
    //System.out.println("Welcome to dopost");
    HttpSession session=request.getSession(false);
    String update_user=(String)session.getAttribute("UserId");
    long l=System.currentTimeMillis();
Timestamp ts=new Timestamp(l);
    System.out.println("Session :"+session);
    /*********** connection establishment****************/
    Connection con=null;
    ResultSet rs=null,rs_set=null,rs_set1=null,rs_set2=null;
    PreparedStatement ps=null,ps_st=null,ps_st1=null,ps_st2=null;
   
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
            System.out.println("Exception in connection...."+e);
    } 
          try
          {
              session=request.getSession(false);
              if(session==null)
              {
                  System.out.println(request.getContextPath()+"/index.jsp");
                  response.sendRedirect(request.getContextPath()+"/index.jsp");
                  return;
              }
              System.out.println(session);
          } 
          catch(Exception e)
          {
                System.out.println("Redirect Error :"+e);
          }
          session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
     /*     eid=empProfile.getEmployeeId();
          System.out.println("employee id:"+eid);
                       
          try 
          {
              //eid=Integer.parseInt(request.getParameter("txtEmpId"));
              //int userid=
              ps=con.prepareStatement("select USER_ID,USER_CATEGORY_ID,EMPLOYEE_ID from SEC_MST_USERS where EMPLOYEE_ID=?");
              ps.setInt(1,eid);
              rs=ps.executeQuery(); 
              if(rs.next()) 
              {
                   user_category_id=rs.getInt("USER_CATEGORY_ID");
              }
              rs.close();
              ps.close();
          }
          catch(Exception e) 
          {
              System.out.println("Err in eid selection:  "+e.getMessage());    
          }*/
     try
    {
              cmnd =  request.getParameter("Command");     
              System.out.println("Command passed via the button pressed : " + cmnd);
    }
      catch(Exception e3)
      {
        e3.printStackTrace();
      }
/*///////////////////////////////Getting the values from the JSP Page//////////////////////////////////////////////////*/
if(cmnd.equalsIgnoreCase("Add")) 
{  PrintWriter pw=response.getWriter();
 cnt=0;int SL_NO=1;
 int errcode=0;
 String sub_qry="",sub_qry1="",D_qry="",D_qry1="";
 int c_val1=0,c_val2=0;
 try{acc_unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}catch(Exception e){System.out.println("Exception arised"+e);}
 try{acc_office_id=Integer.parseInt(request.getParameter("cmbOffice_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
 String emp_id[]=request.getParameterValues("hid_Empid");  
 try{remarks=request.getParameter("txtRemarks");}catch(Exception e){System.out.println("Exception arised"+e);}
 String Grid_phone_no[]=request.getParameterValues("H_phone_no");
 sub_qry="select count(*) as c_no from fas_phone_master where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
 try{
	   ps_st1=con.prepareStatement(sub_qry);
	   ps_st1.setInt(1,acc_unit_id);
	   ps_st1.setInt(2,acc_office_id);
	   rs_set1=ps_st1.executeQuery();
	   while(rs_set1.next()){
		 c_val1=rs_set1.getInt("c_no");  
	   }
 }catch (Exception e) {
	System.out.println("Exception Conut"+e);
}
 
 sub_qry1="select count(*) as c_val from fas_phone_trn where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
 try{
	   ps_st2=con.prepareStatement(sub_qry1);
	   ps_st2.setInt(1,acc_unit_id);
	   ps_st2.setInt(2,acc_office_id);
	   rs_set2=ps_st2.executeQuery();
	   while(rs_set2.next()){
		 c_val2=rs_set2.getInt("c_val");  
	   }
 }catch (Exception e) {
	System.out.println("Exception Conut 1 "+e);
}
 System.out.println("c_val1"+c_val1+"  >>>>>>>>c_val1"+c_val2);
 int c=0,c1=0;
if(c_val1!=0 && c_val2!=0)  {
	 D_qry="delete from fas_phone_master where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
	 try{
	 p_del=con.prepareStatement(D_qry);
	 p_del.setInt(1,acc_unit_id);
   p_del.setInt(2,acc_office_id);
	  c = p_del.executeUpdate();
	if(c>0){
		 D_qry1="delete from fas_phone_trn where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?";
	  try{
		 p_del1=con.prepareStatement(D_qry1);
		 p_del1.setInt(1,acc_unit_id);
       p_del1.setInt(2,acc_office_id);
		 c1= p_del1.executeUpdate();
		   
	   }catch (Exception e) {
		System.out.println("Exception Conut 3 "+e);
	}
	}
   }catch (Exception e) {
	System.out.println("Exception Conut 4 "+e);
}
	
} 
 
 for(int jj=0;jj<Grid_phone_no.length;jj++)
{   
	 Emp_value1=emp_id[jj]; 
 try 
 {
    ps_st=con.prepareStatement("select USER_ID,USER_CATEGORY_ID,EMPLOYEE_ID from SEC_MST_USERS where EMPLOYEE_ID=?");
    ps_st.setInt(1,Integer.parseInt(emp_id[jj]));
     rs_set=ps_st.executeQuery(); 
     if(rs_set.next()) 
     {
          user_category_id=rs_set.getInt("USER_CATEGORY_ID");
     }
   
     //System.out.println("Employee ID >> "+jj+" "+emp_id[jj]+"  user_category_id  "+user_category_id);
try{
     con.clearWarnings();
     con.setAutoCommit(false);
     if(!Emp_value1.equalsIgnoreCase(Emp_value2)){ 
  	  
     String sqlins="insert into fas_phone_master (ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,EMPLOYEE_OFFICE_ID," +
     "USER_CATEGORY_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?)";
     ps=con.prepareStatement(sqlins);
     ps.setInt(1,acc_unit_id);
     ps.setInt(2,acc_office_id);
     ps.setInt(3,Integer.parseInt(emp_id[jj]));
     ps.setInt(4,user_category_id);
     ps.setString(5,remarks);
     ps.setString(6,update_user);
     ps.setTimestamp(7,ts);          
      errcode=ps.executeUpdate();
        ps.close();
     }
     else if(Emp_value1.equalsIgnoreCase(Emp_value2)){ 
  	   //System.out.println("Emp_value1 >> "+Emp_value1+"  Emp_value2  >> "+Emp_value2);
         cnt=1;
         }
     else{
  	   System.out.println("Error in If ... ");
     }

        //System.out.println("errcode value:  "+errcode+"  count value >> "+cnt);    
             if(errcode==0)
             {         
                   //System.out.println("redirect");
                   sendMessage(response,"The insertion into the phone master table Failed ","ok");		                       
             }
             else if(errcode!=0 || cnt==1)
             {
            	 try{
                	System.out.println("The records inserted into the phone master table scuccessfully");
                	
                       
                          String No_TRN_Rec[]=request.getParameterValues("H_purposeOR");
                          Total_TRN_Rec=No_TRN_Rec.length;//Integer.parseInt(No_TRN_Rec);
                          //System.out.println(" Total_TRN_Records :"+Total_TRN_Rec);
           
               String Grid_connection_type[]=request.getParameterValues("H_connection_type");
             String H_usage_details[]=request.getParameterValues("H_usage_details");
               String Grid_STD_code[]=request.getParameterValues("H_STD_code");
               String Grid_SerProName[]=request.getParameterValues("H_SerProName");
               String Grid_SerProType[]=request.getParameterValues("H_SerProType");
               String Grid_ceiling_type[]=request.getParameterValues("H_ceiling_type");
               String Grid_ceil_Limit_amt[]=request.getParameterValues("H_ceil_Limit_amt");
               String Grid_purposeOR[]=request.getParameterValues("H_purposeOR");
               String Grid_Particulars[]=request.getParameterValues("H_Particulars");
               try{purposeOR=Grid_purposeOR[jj];}catch(Exception e1){System.out.println("exception in trans 1 "+e1);}
             try{conn_typeLM=Grid_connection_type[jj];}catch(Exception e8){System.out.println("exception in trans 2 "+e8);}                                   
             try{usage_details=H_usage_details[jj];}catch(Exception e2){System.out.println("exception in trans 3  "+e2);}
             try{std_code=Grid_STD_code[jj];}catch(Exception e2){System.out.println("exception in trans 4 "+e2);}
             try{phone_no=Grid_phone_no[jj];}catch(Exception e3){System.out.println("exception in trans 5 "+e3);}
             try{SP_name=Grid_SerProName[jj];}catch(Exception e4){System.out.println("exception in trans 6 "+e4);}
             try{SP_Type=Grid_SerProType[jj];}catch(Exception e5){System.out.println("exception in trans 7 "+e5);}
             try{ceil_type=Grid_ceiling_type[jj];}catch(Exception e6){System.out.println("exception in trans 8 "+e6);}
             try{ceil_amt=Float.parseFloat(Grid_ceil_Limit_amt[jj]);}catch(Exception e7){System.out.println("exception in trans 9 "+e7);}
             try{particulars=Grid_Particulars[jj];}catch(Exception e9){System.out.println("exception in trans 10 "+e9);}
				
					String sql = "     insert into FAS_PHONE_TRN(ACCOUNTING_UNIT_ID, "
						+ "     ACCOUNTING_UNIT_OFFICE_ID ,EMPLOYEE_OFFICE_ID, SL_NO, PURPOSE, CONNECTION_TYPE,"
						+ "     USAGE_DETAILS,STD_CODE,PHONE_NO,SERVICE_PROVIDER_NAME,SERVICE_TYPE,CEILING_TYPE,CEILING_LIMIT_AMOUNT,"
						+ "     PARTICULARS,UPDATED_BY_USED_ID,UPDATED_DATE) "
						+ "     values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					ps=con.prepareStatement(sql);
                     ps.setInt(1,acc_unit_id);
                     ps.setInt(2,acc_office_id);
                     ps.setInt(3,Integer.parseInt(emp_id[jj]));
                     ps.setInt(4,SL_NO);
                     ps.setString(5,purposeOR);
                     ps.setString(6,conn_typeLM);
                     ps.setString(7,usage_details);
                     ps.setString(8,std_code);
                     ps.setString(9,phone_no);
                     ps.setString(10,SP_name);
                     ps.setString(11,SP_Type);
                     ps.setString(12,ceil_type);
                     ps.setFloat(13,ceil_amt);
                     ps.setString(14,particulars);
                     ps.setString(15,update_user);
                     ps.setTimestamp(16,ts);
                     SL_NO++;
                  errcode= ps.executeUpdate();
             ps.close();
             con.commit();
             sendMessage(response,"The Records Saved Successfully ","ok");
            	 }catch (Exception e) {
					System.out.println("Exceoption in inner loop >> "+e);
				}
            	 }
             rs_set.close();
             ps_st.close();
}
		catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException sqle) {
				System.out.println("exception in rollback " + sqle);
			}
			sendMessage(
					response,
					"The Insertion of records into the table Failed ",
					"ok");
			System.out.println("Exception occur due to " + e);
		}
       finally
       {
           System.out.println("done");
           try{
          	 con.setAutoCommit(true);  
           }catch(SQLException sqle){System.out.println("Exception arised :"+sqle);}
       }
 }
 catch(Exception e) 
 {
     System.out.println("Last slection:  "+e.getMessage());    
 }
 Emp_value2=emp_id[jj];

}
 
 pw.flush();
 pw.close();
}else if(cmnd.equalsIgnoreCase("Report")){
	  JasperDesign jasperDesign = null;
      File reportFile = null;
	try{
		String type=request.getParameter("type");
		System.out.println("type >>> "+type);
		int accountingunit=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int accountingoffice=Integer.parseInt(request.getParameter("cmbOffice_code"));
		String rtype=request.getParameter("txtoption");
	 if (type.equalsIgnoreCase("All")) {
		 reportFile =
             new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/CivilBills/jasper/PhoneDetailsReportHO.jasper"));
 
	 }if (type.equalsIgnoreCase("office")){
		 reportFile =
             new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/CivilBills/jasper/PhoneDetailsReportoFFICE.jasper"));
 
 }
	
System.out.println( " >>>>reportFile >>> "+reportFile);

 if (!reportFile.exists())
     throw new JRRuntimeException("File J not found. The report design must be compiled first.");
 JasperReport jasperReport =
     (JasperReport)JRLoader.loadObject(reportFile.getPath());


 Map map = new HashMap();


 map.put("unitid", accountingunit);
 map.put("accountingunit_name", accountingoffice);


//    map.put("BankName", cmbBankName);
//  map.put("AccountName", AccNumber);
System.out.println("map value >>> "+map);
 JasperPrint jasperPrint =
     JasperFillManager.fillReport(jasperReport, map, con);


 if (rtype.equalsIgnoreCase("HTML")) {
     response.setContentType("text/html");
     response.setHeader("Content-Disposition",
                        "attachment;filename=\"Phone Details.html\"");
     PrintWriter out = response.getWriter();
     JRHtmlExporter exporter = new JRHtmlExporter();
     exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                           false);
     exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                           jasperPrint);
     exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
     exporter.exportReport();
     out.flush();
     out.close();
 } else if (rtype.equalsIgnoreCase("PDF")) {
     byte buf[] =
         JasperExportManager.exportReportToPdf(jasperPrint);
     response.setContentType("application/pdf");
     response.setContentLength(buf.length);
     response.setHeader("Content-Disposition",
                        "attachment;filename=\"Phone Details.pdf\"");
     OutputStream out = response.getOutputStream();
     out.write(buf, 0, buf.length);
     out.close();
 } else if (rtype.equalsIgnoreCase("EXCEL")) {

     response.setContentType("application/vnd.ms-excel");
     response.setHeader("Content-Disposition",
                        "attachment;filename=\"Phone Details.xls\"");
     JRXlsExporter exporterXLS = new JRXlsExporter();
     exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                              jasperPrint);

     ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
     exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                              xlsReport);
     exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                              Boolean.FALSE);
     exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                              Boolean.TRUE);
     exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                              Boolean.FALSE);
     exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                              Boolean.TRUE);
     exporterXLS.exportReport();
     byte[] bytes;
     bytes = xlsReport.toByteArray();
     ServletOutputStream ouputStream = response.getOutputStream();
     ouputStream.write(bytes, 0, bytes.length);
     ouputStream.flush();
     ouputStream.close();

 } else if (rtype.equalsIgnoreCase("TXT")) {

     response.setContentType("text/plain");
     response.setHeader("Content-Disposition",
                        "attachment;filename=\"Phone Details.txt\"");

     JRTextExporter exporter = new JRTextExporter();
     exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                           jasperPrint);
     ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
     exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                           txtReport);
     exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                           new Integer(200));
     exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                           new Integer(50));
     exporter.exportReport();

     byte[] bytes;
     bytes = txtReport.toByteArray();
     ServletOutputStream ouputStream = response.getOutputStream();
     ouputStream.write(bytes, 0, bytes.length);
     ouputStream.flush();
     ouputStream.close();

 }

} catch (Exception ex) {
 String connectMsg =
     "Could not create the report " + ex.getMessage() + " " +
     ex.getLocalizedMessage();
 System.out.println(connectMsg);
}

}
	
}


//DoPost method close
private void sendMessage(HttpServletResponse response,String msg,String bType)
{
        try
        {
             String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
             response.sendRedirect(url);
        }
        catch(IOException e)
        {
                System.out.println("Exception arised :"+e);
        }
}
}  