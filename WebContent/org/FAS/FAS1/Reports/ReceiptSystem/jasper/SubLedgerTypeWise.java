package Servlets.FAS.FAS1.ReceiptSystem.servlets;

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;
import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class SubLedgerTypeWise extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1696805545658090331L;
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,
                                                            IOException {
    	
        String strCommand = "";
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String xml = "",sql="";
        int count = 0;
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        try {
            LoadDriver load = new LoadDriver();
            connection = load.getConnection();
            strCommand = request.getParameter("command");
        } catch (Exception e) {
        	e.printStackTrace();
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        
        if(strCommand.equalsIgnoreCase("subType")){
        	response.setContentSub Ledger Report(TypeWise)Type(CONTENT_TYPE);
    		PrintWriter out = response.getWriter();
        	sql="";
        	xml="";
        	count = 0;
        	sql="select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES";
        	try {
				preparedStatement = connection.prepareStatement(sql);
				resultSet = preparedStatement.executeQuery();
				xml="<response><command>subType</command>";
				while(resultSet.next()){
					xml+="<SUB_LEDGER_TYPE_CODE>"+resultSet.getInt("SUB_LEDGER_TYPE_CODE")+"</SUB_LEDGER_TYPE_CODE>" +
						  "<SUB_LEDGER_TYPE_DESC>"+resultSet.getString("SUB_LEDGER_TYPE_DESC")+"</SUB_LEDGER_TYPE_DESC>";
					count++;
				}
				if(count>0){
					xml+="<status>success</status>";
				} else {
					xml+="<status>fail</status>";
				}
				xml+="</response>";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml="<response><command>subType</command><status>fail</status></response>";
				e.printStackTrace();
			}
			out.write(xml);
	        out.flush();
	        out.close();
        } else if(strCommand.equalsIgnoreCase("subCode")) {
        	response.setContentType(CONTENT_TYPE);
    		PrintWriter out = response.getWriter();
            int cmbSL_type = 0,cmbOffice_code=0,cmbAcc_UnitCode=0;
            int y = 0;
            xml = "<response><command>subCode</command>";
            try {
                cmbSL_type =
                        Integer.parseInt(request.getParameter("cmbSL_type"));
            } catch (Exception e) {
                System.out.println("error get SL_type");
            }            
            System.out.println("SL_TYPE " + cmbSL_type + "NN");
            try {
            	cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (Exception e) {
                System.out.println("error get cmbOffice_code");
            }
            try {
            	cmbAcc_UnitCode =Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("error get cmbAcc_UnitCode");
            }
            
            xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
            if (cmbSL_type == 1) {
                System.out.println("here in supplier 1");
                try {
                	preparedStatement =connection.prepareStatement("select SUPPLIER_ID,SUPPLIER_NAME from COM_SUPPLIER_SL_MST where STATUS='L' order by SUPPLIER_NAME");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        xml = xml + "<cid>" + resultSet.getInt("SUPPLIER_ID") + "</cid><cname>" +
                        	resultSet.getString("SUPPLIER_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";
                    preparedStatement.close();
                    resultSet.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 2) {
                System.out.println("here in Firms 2");
                try {
                    preparedStatement = connection.prepareStatement("select FIRMS_ID, FIRMS_NAME from COM_FIRMS_SL_MST where STATUS='L' order by FIRMS_NAME");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        xml = xml + "<cid>" + resultSet.getInt("FIRMS_ID") + "</cid><cname>" +
                        resultSet.getString("FIRMS_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    preparedStatement.close();
                    resultSet.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load firms" + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 3) {
                System.out.println("here in assets 3");
                try {
                    preparedStatement = connection.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION from COM_MST_ASSETS_SL where STATUS='L' order by ASSET_DESCRIPTION");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        xml = xml + "<cid>" + resultSet.getInt("ASSET_CODE") + "</cid><cname>" +
                        resultSet.getString("ASSET_DESCRIPTION").trim() + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    preparedStatement.close();
                    resultSet.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 5) {
                try {
                    preparedStatement = connection.prepareStatement("select OFFICE_ID, OFFICE_NAME,OFFICE_STATUS_ID from COM_MST_OFFICES");
                    resultSet = preparedStatement.executeQuery();
                    System.out.println("inside offices 5");

                    int cnt = 0;
                    if (resultSet.next()) {
                        xml = xml + "<cid>" + resultSet.getInt("OFFICE_ID") + "</cid><cname>" +
                        resultSet.getString("OFFICE_NAME") + "</cname>";
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    preparedStatement.close();
                    resultSet.close();

                    System.out.println(cnt + "count");
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load office code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 6) {

                try {
                    System.out.println("Inside 6 Load Bank Account Number ");
                    String bank_sql = "SELECT BANK_AC_NO , AC_OPERATIONAL_MODE_ID , BANK_AC_NO_ALIAS_CODE FROM FAS_MST_BANK_BALANCE WHERE STATUS='Y' AND ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" ORDER BY AC_OPERATIONAL_MODE_ID , BANK_AC_NO";
                    preparedStatement = connection.prepareStatement(bank_sql);
                    resultSet = preparedStatement.executeQuery();
                    long bank_acc_no;
                    String acc_no_plus_mode = "";
                    String operation_mode = "";
                    int bank_ac_no_alias_code;

                    while (resultSet.next()) {
                        bank_acc_no = resultSet.getLong("BANK_AC_NO");
                        operation_mode =
                                resultSet.getString("AC_OPERATIONAL_MODE_ID");
                        bank_ac_no_alias_code =
                                resultSet.getInt("BANK_AC_NO_ALIAS_CODE");

                        acc_no_plus_mode = operation_mode + "-" + bank_acc_no;
                        xml = xml + "<cid>" + bank_ac_no_alias_code + "</cid><cname>" + acc_no_plus_mode +
                        "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    preparedStatement.close();
                    resultSet.close();
                    System.out.println("Bank A/C is " + xml);
                } catch (Exception e) {
                    System.out.println("Error in Loading Bank Account Number." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }


            } else if (cmbSL_type == 7) {
                int oid = 0, cmbMas_SL_type = 0;
                String deptid = "";
                System.out.println("inside 7 employeees testing****"+cmbMas_SL_type);
                try {
                    try {
                        cmbMas_SL_type =
                                Integer.parseInt(request.getParameter("cmbMas_SL_type"));
                    } catch (Exception e) {
                        System.out.println("getting master combo sl type failed");
                    }                    
                    if (cmbMas_SL_type == 9) {
                        try {
                            System.out.println("inside other employee");
                            preparedStatement = connection.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID from HRM_MST_OTHER_DEPT_OFFICES");
                            resultSet = preparedStatement.executeQuery();
                            if (resultSet.next()) {
                                deptid = resultSet.getString("OTHER_DEPT_ID").trim();
                                oid = resultSet.getInt("OTHER_DEPT_OFFICE_ID");
                            }
                            resultSet.close();
                            preparedStatement.close();

                            preparedStatement = connection.prepareStatement(" select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL ||'-'|| d.DESIGNATION as ENAME" +
                       " from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d " +
                       " where c.DESIGNATION_ID=d.DESIGNATION_ID  and c.EMPLOYEE_ID=e.EMPLOYEE_ID and c.EMPLOYEE_STATUS_ID='DPN' " +
                       " and c.OFFICE_ID=? and c.DEPARTMENT_ID=?");
                            preparedStatement.setInt(1, oid);
                            preparedStatement.setString(2, deptid);                            
                            resultSet = preparedStatement.executeQuery();

                            if (resultSet.next()) {
                                xml = xml + "<flag>success</flag>";
                                xml = xml + "<cid>" + resultSet.getInt("EMPLOYEE_ID") + "</cid><cname>" +
                                resultSet.getString("ENAME") + "</cname>";

                            } else {
                                xml = xml + "<flag>failure</flag>";
                            }
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }

                    }

                    else if(cmbMas_SL_type!=0){
                        
                    	System.out.println("ELSE.........");
                    	
                    	try {
                            // preparedStatement=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and e.OFFICE_ID=? and e.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
                            //preparedStatement.setInt(1,cmbOffice_code);
                            preparedStatement = connection.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c," +
                            " HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID order by e.EMPLOYEE_NAME ");
                            resultSet = preparedStatement.executeQuery();
                            
                            
                            if (resultSet.next()) {
                                xml = xml + "<cid>" + resultSet.getInt("EMPLOYEE_ID") + "</cid><cname>" +
                                resultSet.getString("ENAME") + "</cname>";
                                xml =
                                xml + "<state>" + resultSet.getString("EMPLOYEE_STATUS_ID") + "</state>";
                                xml = xml + "<flag>success</flag>";
                            } else
                            {
                                xml = xml + "<flag>failure</flag>";
                            }
                            
                            preparedStatement.close();
                            resultSet.close();
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp cod in else part." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                    
                    else if(cmbMas_SL_type==0)
                    {
                    	try
                    	{
                    	preparedStatement = connection.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c," +
                                " HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID order by e.EMPLOYEE_NAME ");
                                resultSet = preparedStatement.executeQuery();
                    	
                    	System.out.println("cmbMas_SL_type===========>");
                    	while (resultSet.next()) {
                            xml = xml + "<cid>" + resultSet.getInt("EMPLOYEE_ID") + "</cid><cname>" +
                            resultSet.getString("ENAME") + "</cname>";
                            xml =
                            xml + "<state>" + resultSet.getString("EMPLOYEE_STATUS_ID") + "</state>";
                            //xml = xml + "<flag>success</flag>";
                            y++;
                        } 
//                    	else
//                            xml = xml + "<flag>failure</flag>";
                    	if(y!=0)
                    	{
                    		xml = xml + "<flag>success</flag>";	
                    	}
                    	else
                    	{
                    		xml = xml + "<flag>failure</flag>";	
                    	}
                    	
                    	preparedStatement.close();
                        resultSet.close();
                    	}
                    	catch(Exception e)
                    	{
                    		System.out.println("catch..HERE.in load emp cod in else part." +
                                    e);
                    		xml = xml + "<flag>failure</flag>";	
                    	}
                    	
                    }
                    
                } catch (Exception e) {
                    System.out.println("catch..HERE.in getting request value code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }

            } else if (cmbSL_type == 9) {
                System.out.println("inside 9 OTHER Departments");

                try {
                    preparedStatement = connection.prepareStatement("select dep.OTHER_DEPT_NAME || '-' || off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFF_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                       ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ORDER BY dep.OTHER_DEPT_NAME");
                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {

                        xml = xml + "<cid>" + resultSet.getInt("OTHER_DEPT_OFFICE_ALIAS_ID") + "</cid><cname>" +
                        resultSet.getString("OTHER_DEPT_OFF_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    preparedStatement.close();
                    resultSet.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load department code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 10) {
                try {
                    System.out.println("inside 10 here projects");
                    preparedStatement = connection.prepareStatement("select PROJECT_ID, sch_name as PROJECT_NAME  from pms_sch_master where OFFICE_ID="+cmbOffice_code+" order by sch_name");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        xml = xml + "<cid>" + resultSet.getInt("PROJECT_ID") + "</cid><cname>" +
                        resultSet.getString("PROJECT_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    preparedStatement.close();
                    resultSet.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 11) {
                try {
                    System.out.println("inside 11 contractors");
                    String contra ="SELECT CONTRACTOR_ID , "+
					"  CONTRACTOR_NAME    "+
					" From PMS_CONT_REQUEST_REGN"+
					" WHERE OFFICE_ID="+cmbOffice_code+" order by CONTRACTOR_NAME ";
                    System.out.println("contra::"+contra);
                    preparedStatement = connection.prepareStatement(contra);                    
                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        xml = xml + "<cid>" + resultSet.getString("CONTRACTOR_ID") + "</cid><cname>" +
                        resultSet.getString("CONTRACTOR_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    preparedStatement.close();
                    resultSet.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load contractor." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            }

            else if (cmbSL_type == 13) // Beneficiaries
            {
                try {
                    preparedStatement =  connection.prepareStatement("                                     " +
                       "select                                                     \n" +
                       "    beneficiary_id as sl_code,                             \n" +
                       "    BENEFICIARY_NAME || (select ' (' || DESCRIPTION ||')' from pms_mst_lookup where lookup_id = BENEFICIARY_TYPE_ID ) as sl_code_desc   \n" +
                       "from                                                       \n" +
                       "   pms_mst_beneficiary                                     \n" +
                       "where                                                      \n" +
                       "   DISTRICT_CODE in (                                      \n" +
                       "   select district_code  from com_mst_offices              \n" +                       
                       "                                                           \n" +
                       "order by sl_code_desc                                      \n" +
                       "                                                           \n ");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        xml = xml + "<cid>" + resultSet.getInt("sl_code") + "</cid><cname>" +
                        resultSet.getString("sl_code_desc") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                    preparedStatement.close();
                    resultSet.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }

            }
            else if (cmbSL_type == 14) // Beneficiaries
            {
                try {
                   PreparedStatement ps =connection.prepareStatement("SELECT BENEFICIARY_SNO AS sl_code,BENEFICIARY_NAME as sl_code_desc From Pms_Dcb_Mst_Beneficiary Where office_id = ? order by BENEFICIARY_NAME");


                    ps.setInt(1, cmbOffice_code);
                  ResultSet  rs = ps.executeQuery();
                    while (rs.next()) {
                        xml =
						 xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
						   rs.getString("sl_code_desc") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                    System.out.println(xml);
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }

            }
            else if(cmbSL_type==15)
            { 
               System.out.println("Inside Accounting Unit");
                   try {
                           preparedStatement=connection.prepareStatement("SELECT accounting_unit_id,trim(accounting_unit_name) as accounting_unit_name FROM fas_mst_acct_units order by accounting_unit_name");
                           resultSet=preparedStatement.executeQuery();
                          
                           while(resultSet.next())
                           {
                           xml=xml+"<cid>"+resultSet.getInt("accounting_unit_id")+
                           "</cid><cname>"+resultSet.getString("accounting_unit_name")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           preparedStatement.close();
                           resultSet.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            else if(cmbSL_type==18)
            { 
               System.out.println("Inside pensioner Unit");
                   try {
                           preparedStatement=connection.prepareStatement("select ppo_no,pensioner_name  FROM hrm_pen_mst_details WHERE process_status='VALIDATE' order by pensioner_name ");
                           resultSet=preparedStatement.executeQuery();
                          
                           while(resultSet.next())
                           {
                           xml=xml+"<cid>"+resultSet.getInt("ppo_no")+
                           "</cid><cname>"+resultSet.getString("pensioner_name")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           preparedStatement.close();
                           resultSet.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            else if(cmbSL_type==84)
            { 
               System.out.println("Inside ooooooo Unit");
                   try {
                           preparedStatement=connection.prepareStatement("select OFFICE_ID,OFFICE_NAME from com_mst_offices where OFFICE_ID=5000");
                       //    preparedStatement.setInt(1,cmbAcc_UnitCode);
                        //   System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           resultSet=preparedStatement.executeQuery();
                          
                           while(resultSet.next())
                           {
                           xml=xml+"<cid>"+resultSet.getInt("OFFICE_ID")+
                           "</cid><cname>"+resultSet.getString("OFFICE_NAME")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           preparedStatement.close();
                           resultSet.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
                
            else if(cmbSL_type==89) { 
                
                try {
                   preparedStatement = connection.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c," +
                   " HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID order by e.EMPLOYEE_NAME ");
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        xml = xml + "<cid>" + resultSet.getInt("EMPLOYEE_ID") + "</cid><cname>" +
                        resultSet.getString("ENAME") + "</cname>";
                        xml = xml + "<state>" + resultSet.getString("EMPLOYEE_STATUS_ID") + "</state>";
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    preparedStatement.close();
                    resultSet.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp cod in else part." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            }   
                
            else {
                System.out.println("no match found");
                xml = xml + "<flag>failure</flag>";
            }
            xml += "</response>";
            System.out.println(xml);
            out.write(xml);
            out.flush();
            out.close();
        } else if (strCommand.equalsIgnoreCase("report")){
        	String myQry = "",add_con="";
        	
        	int subLedgerType = Integer.parseInt(request.getParameter("subLedgerType"));
        	String subLedgerCode =request.getParameter("subLedgerCode");
        	
        	int cashYear_from = Integer.parseInt(request.getParameter("txtCB_Year"));
        	int cashYear_to = Integer.parseInt(request.getParameter("txtCB_Year_to"));
        	int cashMonth_from = Integer.parseInt(request.getParameter("txtCB_Month"));
        	int cashMonth_to = Integer.parseInt(request.getParameter("txtCB_Month_to"));
        	if(subLedgerCode.equals("select"))
        	{
        		add_con="";
        	}
        	else
        	{
        		add_con=" and t.sub_ledger_code="+subLedgerCode+" ";
        	}
        	try{
        		Map map = new HashMap();
            	Map<Integer,String> monthlist = new HashMap<Integer,String>();
            	monthlist.put(1,"January");
            	monthlist.put(2,"February");
            	monthlist.put(3,"March");
            	monthlist.put(4,"April");
            	monthlist.put(5,"May");
            	monthlist.put(6,"June");
            	monthlist.put(7,"July");
            	monthlist.put(8,"August");
            	monthlist.put(9,"September");
            	monthlist.put(10,"October");
            	monthlist.put(11,"November");
            	monthlist.put(12,"December");
            	map.put("year_from", cashYear_from);
            	map.put("year_to", cashYear_to);
        		map.put("month_from", monthlist.get(cashMonth_from));
        		map.put("month_to", monthlist.get(cashMonth_to));
        		
//        	myQry = "SELECT m.accounting_unit_id AS unit_id , " +
//		        	"  a.accounting_unit_name AS accounting_unit_name, " +
//		        	"  b.journal_type_desc AS journal_type_desc, " +
//		        	"  m.cashbook_year , " +
//		        	"  m.cashbook_month , " +
//		        	"  m.VOUCHER_NO AS voc_no , " +
//		        	"  to_char(m.voucher_date,'dd/MM/yyyy') AS voucher_date, " +
//		        	"  t.sl_no AS sl_no, " +
//		        	"  t.account_head_code AS account_head_code , " +
//		        	"  T.Amount AS Amount , " +
//		        	"  T.Cr_Dr_Indicator AS Cr_Dr_Indicator, " +
//		        	"  T.Sub_Ledger_Type_Code AS Sub_Ledger_Type_Code, " +
//		        	"  (SELECT Sub_Ledger_Type_Desc " +
//		        	"  FROM Com_Mst_Sl_Types " +
//		        	"  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code " +
//		        	"  )AS Typedesc, " +
//		        	"  T.Sub_Ledger_Code AS Sub_Ledger_Code" +
//		        	" FROM FAS_JOURNAL_MASTER m, " +
//		        	"  FAS_JOURNAL_TRANSACTION t , " +
//		        	"  Fas_Mst_Acct_Units A, " +
//		        	"  Fas_Mst_Journal_Type B " +
//		        	"WHERE m.accounting_unit_id =t.accounting_unit_id " +
//		        	"AND m.cashbook_year        = t.cashbook_year " +
//		        	"AND m.cashbook_month       = t.cashbook_month " +
//		        	"AND m.VOUCHER_NO           = t.VOUCHER_NO " +
//		        	"AND m.JOURNAL_STATUS       ='L' " +
//		        	"AND M.Accounting_Unit_Id   = A.Accounting_Unit_Id " +
//		        	"AND to_date(m.cashbook_month " +
//		        	"  ||'-' " +
//		        	"  || M.Cashbook_Year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
//		        	"  || '-' " +
//		        	"  || '"+cashYear_from+"', 'mm-yyyy') " +
//		        	"AND to_date( '"+cashMonth_to+"' " +
//		        	"  ||'-' " +
//		        	"  || '"+cashYear_to+"' , 'mm-yyyy') " +
//		        	"AND T.Sub_Ledger_Type_Code = '"+subLedgerType+"'" +add_con+
//		        	" AND M.Journal_Type_Code =B.Journal_Type_Code " +
//		        	"ORDER BY M.Accounting_Unit_Id , " +
//		        	"  M.Voucher_No";
        		// updated by B.Sathya on 20/08/14 ..............
        		myQry = "SELECT m.accounting_unit_id AS unit_id , " +
        				"  A.Accounting_Unit_Name    AS Accounting_Unit_Name, " +
        				"  b.journal_type_desc       AS journal_type_desc, " +
        				"  m.cashbook_year , " +
        				"  m.cashbook_month , " +
        				"  m.VOUCHER_NO                         AS voc_no , " +
        				"  TO_CHAR(m.voucher_date,'dd/MM/yyyy') AS voucher_date, " +
        				"  t.sl_no                              AS sl_no, " +
        				"  t.account_head_code                  AS account_head_code , " +
        				"  T.Amount                             AS Amount , " +
        				"  T.Cr_Dr_Indicator                    AS Cr_Dr_Indicator, " +
        				"  T.Sub_Ledger_Type_Code               AS Sub_Ledger_Type_Code, " +
        				"  (SELECT Sub_Ledger_Type_Desc " +
        				"  FROM Com_Mst_Sl_Types " +
        				"  WHERE Sub_Ledger_Type_Code=T.Sub_Ledger_Type_Code " +
        				"  )                 AS Typedesc, " +
        				"  T.Sub_Ledger_Code AS Sub_Ledger_Code " +
        				"FROM FAS_JOURNAL_MASTER m, " +
        				"  FAS_JOURNAL_TRANSACTION t , " +
        				"  Fas_Mst_Acct_Units A, " +
        				"  Fas_Mst_Journal_Type B " +
        				"WHERE m.accounting_unit_id =t.accounting_unit_id " +
        				"AND m.cashbook_year        = t.cashbook_year " +
        				"AND m.cashbook_month       = t.cashbook_month " +
        				"AND m.VOUCHER_NO           = t.VOUCHER_NO " +
        				"AND m.JOURNAL_STATUS       ='L' " +
        				"AND M.Accounting_Unit_Id   = A.Accounting_Unit_Id " +
        				"AND to_date(m.cashbook_month " +
        				"  ||'-' " +
        				"  || M.Cashbook_Year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
        				"  || '-' " +
        				"  || '"+cashYear_from+"', 'mm-yyyy') " +
        				"AND to_date( '"+cashMonth_to+"' " +
        				"  ||'-' " +
        				"  || '"+cashYear_to+"', 'mm-yyyy') " +
        				"AND T.Sub_Ledger_Type_Code = '"+subLedgerType+"'" +add_con+
        				"AND M.Journal_Type_Code    =B.Journal_Type_Code " +
        				"UNION " +
        				"SELECT m.accounting_unit_id AS unit_id , " +
        				"  A.Accounting_Unit_Name    AS Accounting_Unit_Name, " +
        				" CASE " +
        				" WHEN M.Payment_Type = 'B' THEN " +
        			    "  'Bank Payment' " +
        				" ELSE " +
        				"  'Cash Payment' " +
        			    " END " +
        				" AS " +
        				"  Payment_Desc," +
        				"  m.Cashbook_Year , " +
        				"  m.Cashbook_Month , " +
        				"  m.Voucher_No                         AS Voc_No , " +
        				"  TO_CHAR(m.Payment_Date,'dd/MM/yyyy') AS Voucher_Date, " +
        				"  t.Sl_No                              AS Sl_No, " +
        				"  t.Account_Head_Code                  AS Account_Head_Code , " +
        				"  t.Amount                             AS Amount , " +
        				"  t.Cr_Dr_Indicator                    AS Cr_Dr_Indicator, " +
        				"  t.Sub_Ledger_Type_Code               AS Sub_Ledger_Type_Code, " +
        				"  (SELECT Sub_Ledger_Type_Desc " +
        				"  FROM Com_Mst_Sl_Types " +
        				"  WHERE Sub_Ledger_Type_Code=t.Sub_Ledger_Type_Code " +
        				"  )                 AS Typedesc, " +
        				"  t.Sub_Ledger_Code AS Sub_Ledger_Code " +
        				"FROM Fas_Payment_Master m, " +
        				"  Fas_Payment_Transaction t, " +
        				"  Fas_Mst_Acct_Units A " +
        				"WHERE m.Accounting_Unit_Id = t.Accounting_Unit_Id " +
        				"AND m.Accounting_Unit_Id   = a.accounting_unit_id " +
        				"AND m.Cashbook_Year        = t.Cashbook_Year " +
        				"AND m.Cashbook_Month       = t.Cashbook_Month " +
        				"AND m.Voucher_No           = t.Voucher_No " +
        				"AND m.Payment_Status       = 'L' " +
        				"AND t.Sub_Ledger_Type_Code = '"+subLedgerType+"'" +add_con+
        				"AND To_Date(m.Cashbook_Month " +
        				"  ||'-' " +
        				"  || m.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
        				"  || '-' " +
        				"  || '"+cashYear_from+"', 'mm-yyyy') " +
        				"AND to_date( '"+cashMonth_to+"' " +
        				"  ||'-' " +
        				"  || '"+cashYear_to+"', 'mm-yyyy') " +
        				"UNION " +
        				" SELECT m.accounting_unit_id AS unit_id , " +
        				"  A.Accounting_Unit_Name    AS Accounting_Unit_Name, " +
        				" CASE " +
        				" WHEN M.receipt_Type = 'B' THEN " +
        			    "  'Bank Receipt' " +
        				" ELSE " +
        				"  'Cash Receipt' " +
        			    " END " +
        				" AS " +
        				"  Receipt_Desc," +
        				"  m.Cashbook_Year , " +
        				"  m.Cashbook_Month , " +
        				"  m.RECEIPT_NO                         AS Voc_No , " +
        				"  TO_CHAR(m.RECEIPT_DATE,'dd/MM/yyyy') AS Voucher_Date, " +
        				"  t.Sl_No                              AS Sl_No, " +
        				"  t.Account_Head_Code                  AS Account_Head_Code , " +
        				"  t.Amount                             AS Amount , " +
        				"  t.Cr_Dr_Indicator                    AS Cr_Dr_Indicator, " +
        				"  t.Sub_Ledger_Type_Code               AS Sub_Ledger_Type_Code, " +
        				"  (SELECT Sub_Ledger_Type_Desc " +
        				"  FROM Com_Mst_Sl_Types " +
        				"  WHERE Sub_Ledger_Type_Code=t.Sub_Ledger_Type_Code " +
        				"  )                 AS Typedesc, " +
        				"  T.Sub_Ledger_Code AS Sub_Ledger_Code " +
        				"FROM Fas_Receipt_Master M, " +
        				"  Fas_receipt_Transaction t, " +
        				"  Fas_Mst_Acct_Units A " +
        				"WHERE m.Accounting_Unit_Id = t.Accounting_Unit_Id " +
        				"AND m.Accounting_Unit_Id   = a.accounting_unit_id " +
        				"AND m.Cashbook_Year        = t.Cashbook_Year " +
        				"AND m.Cashbook_Month       = t.Cashbook_Month " +
        				"AND m.RECEIPT_NO           = t.RECEIPT_NO " +
        				"AND m.RECEIPT_STATUS       = 'L' " +
        				"AND t.Sub_Ledger_Type_Code =  '"+subLedgerType+"'" +add_con+
        				"AND To_Date(m.Cashbook_Month " +
        				"  ||'-' " +
        				"  || m.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonth_from+"' " +
        				"  || '-' " +
        				"  || '"+cashYear_from+"', 'mm-yyyy') " +
        				"AND to_date( '"+cashMonth_to+"' " +
        				"  ||'-' " +
        				"  || '"+cashYear_to+"', 'mm-yyyy') " +
        				"ORDER BY 6";

        	String s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/SubLedgerTypeWise.jrxml");
    		String output=request.getParameter("fileType");
    		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
    		System.out.println(myQry);
    		JRDesignQuery query=new JRDesignQuery();
    		query.setText(myQry);
    		jasperDesign.setQuery(query);
    		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
    		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
    		ByteArrayOutputStream bout=new ByteArrayOutputStream();
        	if(output.equalsIgnoreCase("pdf")){
	            	OutputStream os=response.getOutputStream();
	            	response.setContentType("application/pdf");
	            	response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.pdf\"");
	            	os.write(JasperManager.printReportToPdf(jasperPrint));
	            	os.close();
        	}else if(output.equalsIgnoreCase("excel")){
        			response.setContentType("application/vnd.ms-excel");
        			response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.xls\"");
        			OutputStream os=response.getOutputStream();
        			JRXlsExporter exporterXLS = new JRXlsExporter(); 
        			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, bout);
        			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
        			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        			exporterXLS.exportReport();
        		    byte[] buf=bout.toByteArray();
        		    os.write(buf);
        			os.close();
        		}else if(output.equalsIgnoreCase("html")){            		
            		 response.setContentType("text/html");
            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.html\"");
            		 PrintWriter out = response.getWriter();
            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
            		 exporter.exportReport();
            		 out.flush();
            		 out.close();
        		}
        	}catch (Exception e) {
				// TODO: handle exception
			}
        }
        
    }
}

