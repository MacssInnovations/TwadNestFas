package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class SubsidiaryLedgerServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        /*try
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
        }*/

        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet results = null;
        ResultSet rs = null;
        ResultSet rs3 = null;

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        String strCommand = "";
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Load_SL_Code") ||
            strCommand.equalsIgnoreCase("Load_MasterSL_Code")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
            int cmbSL_type = 0;
            //String deptid="";
            int y = 0;
            xml = "<response><command>" + strCommand + "</command>";
            try {
                cmbSL_type =
                        Integer.parseInt(request.getParameter("cmbSL_type"));
            } catch (Exception e) {
                System.out.println("error get SL_type");
            }
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("error get acc unit code");
            }
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (Exception e) {
                System.out.println("error get office id");
            }
            System.out.println("SL_TYPE " + cmbSL_type + "NN");
            System.out.println(cmbAcc_UnitCode);
            System.out.println(cmbOffice_code);
            xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
            if (cmbSL_type == 1) {
                System.out.println("here in supplier 1");
                try {
                    ps =
  con.prepareStatement("select SUPPLIER_ID,SUPPLIER_NAME from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("SUPPLIER_ID") + "</cid><cname>" +
   rs.getString("SUPPLIER_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 2) {
                System.out.println("here in Firms 2");
                try {
                    ps =
  con.prepareStatement("select FIRMS_ID, FIRMS_NAME from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("FIRMS_ID") + "</cid><cname>" +
   rs.getString("FIRMS_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load firms" + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 3) {
                System.out.println("here in assets 3");
                try {
                    ps =
  con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("ASSET_CODE") + "</cid><cname>" +
   rs.getString("ASSET_DESCRIPTION").trim() + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 4) {
                System.out.println("inside cheque 4 here ");
                try {
                    ps =
  con.prepareStatement("select  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("FIRMS_ID") + "</cid><cname>" +
   rs.getString("FIRMS_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load cheque book code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 5) {
                try {
                    // Error will occur ... query is incorrect format
                    ps =
  con.prepareStatement("select OFFICE_ID, OFFICE_NAME from COM_MST_OFFICES");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside offices 5");

                    int cnt = 0;
                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("OFFICE_ID") + "</cid><cname>" +
   rs.getString("OFFICE_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                    System.out.println(cnt + "count");
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load office code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 6) {
                try {
                    // Error will occur ... query is incorrect
                    //  ps=con.prepareStatement("select BANK_ID from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");

                    ps =
  con.prepareStatement("" + "SELECT                        \n" +
                       "   BANK_AC_NO,                \n" +
                       "   AC_OPERATIONAL_MODE_ID    \n" +
                    //   "   BANK_AC_NO_ALIAS_CODE      \n" +
                       "FROM                          \n" +
                       "   FAS_MST_BANK_BALANCE       \n" +
                       "WHERE                         \n" +
                       "   ACCOUNTING_UNIT_ID=?       \n" +
                       "   ORDER BY AC_OPERATIONAL_MODE_ID, BANK_AC_NO\n" +
                       "                         ");

                    ps.setInt(1, cmbAcc_UnitCode);
                    // ps.setInt(2,cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside 2");
                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getString("BANK_AC_NO") + "</cid><cname>" +
   rs.getString("BANK_AC_NO") + "-" + rs.getString("AC_OPERATIONAL_MODE_ID") +
   "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 7) {
                int other_dept_off_alias_id = 0, oid = 0, cmbMas_SL_type = 0;
                String deptid = "";
                System.out.println("inside 7 employeees");
                try {

                    try {
                        cmbMas_SL_type =
                                Integer.parseInt(request.getParameter("cmbMas_SL_type"));
                    } catch (Exception e) {
                        System.out.println("getting master combo sl type failed");
                    }
                    try {
                        other_dept_off_alias_id =
                                Integer.parseInt(request.getParameter("other_dept_off_alias_id"));
                    } catch (Exception e) {
                        System.out.println("getting master combo slcode failed");
                    }
                    System.out.println("other_dept_off_alias_id.." +
                                       other_dept_off_alias_id);
                    //deptid=request.getParameter("deptid");
                    System.out.println(oid + " " +
                                       deptid); // OTHER_DEPT_ALIAS_ID
                    if (cmbMas_SL_type == 9) {
                        try {
                            System.out.println("inside other employee");
                            ps =
  con.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ALIAS_ID=? ");
                            ps.setInt(1, other_dept_off_alias_id);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                deptid = rs.getString("OTHER_DEPT_ID").trim();
                                oid = rs.getInt("OTHER_DEPT_OFFICE_ID");
                            }
                            rs.close();
                            ps.close();
                            System.out.println("hi");
                            ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and OFFICE_ID=? and DEPARTMENT_ID=? ");
                            ps.setInt(1, oid);
                            ps.setString(2, deptid);
                            rs = ps.executeQuery();

                            System.out.println("oid..dept.." + oid + "dep" +
                                               deptid);
                            while (rs.next()) {
                                System.out.println(rs.getInt("EMPLOYEE_ID"));
                                ps3 =
 con.prepareStatement("select EMPLOYEE_ID,max(SERVICE_LIST_SLNO) from HRM_EMP_SERVICE_DATA  where EMPLOYEE_STATUS_ID='DPN' and EMPLOYEE_ID=? and OFFICE_ID=? group by EMPLOYEE_ID");
                                ps3.setInt(1, rs.getInt("EMPLOYEE_ID"));
                                ps3.setInt(2, cmbOffice_code);
                                rs3 = ps3.executeQuery();
                                if (rs3.next()) {
                                    xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
   rs.getString("EMPLOYEE_NAME") + " - " + rs.getString("DESIGNATION") +
   "</cname>";
                                }
                                y++;
                            }
                            if (y != 0) {
                                xml = xml + "<flag>success</flag>";
                                rs3.close();
                                ps3.close();
                            } else
                                xml = xml + "<flag>failure</flag>";


                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else {
                        try {
                            ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and OFFICE_ID=? order by e.EMPLOYEE_NAME ");
                            ps.setInt(1, cmbOffice_code);
                            rs = ps.executeQuery();

                            while (rs.next()) {
                                xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
   rs.getString("EMPLOYEE_NAME") + " - " + rs.getString("DESIGNATION") +
   "</cname>";
                                y++;
                            }
                            if (y != 0) {
                                xml = xml + "<flag>success</flag>";
                            } else
                                xml = xml + "<flag>failure</flag>";

                            ps.close();
                            rs.close();
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } catch (Exception e) {
                    System.out.println("catch..HERE.in getting request value code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }

            } else if (cmbSL_type == 8) {
                // Error will occur ... query is incorrect
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside 8");

                    while (rs.next()) {

                    }
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 9) {
                System.out.println("inside 9 OTHER Departments");

                try {
                    ps =
  con.prepareStatement("select dep.OTHER_DEPT_NAME as OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFFICE_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                       ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ORDER BY dep.OTHER_DEPT_NAME");
                    rs = ps.executeQuery();

                    while (rs.next()) {

                        xml =
 xml + "<cid>" + rs.getInt("OTHER_DEPT_OFFICE_ALIAS_ID") + "</cid><cname>" +
   rs.getString("OTHER_DEPT_NAME") + " - " +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load department code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 10) {
                try {
                    System.out.println("inside 10 here projects");
                    ps =
  con.prepareStatement("select PROJECT_ID, PROJECT_NAME  from PMS_MST_PROJECTS_VIEW where  OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("PROJECT_ID") + "</cid><cname>" +
   rs.getString("PROJECT_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } 
            else if (cmbSL_type == 11) {
                try {
                    System.out.println("inside 11 contractors");
               String contra ="SELECT CONTRACTOR_ID , "+
					"  CONTRACTOR_NAME    "+
					" From PMS_CONT_REQUEST_REGN"+
					" WHERE OFFICE_ID=? order by CONTRACTOR_NAME ";
                    ps = con.prepareStatement(contra);
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
   xml =xml + "<cid>" + rs.getString("CONTRACTOR_ID") + "</cid><cname><![CDATA[" + rs.getString("CONTRACTOR_NAME") + "]]></cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load contractor." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            }
            /*else if (cmbSL_type == 11) {
                try {
                    System.out.println("inside 11 contractors");
                    ps =
  con.prepareStatement("select CONTRACTOR_ID,CONTRACTOR_NAME from PMS_MST_CONTRACTORS_VIEW where OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getString("CONTRACTOR_ID") + "</cid><cname>" +
   rs.getString("CONTRACTOR_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load contractor." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            }*/ 
            else if (cmbSL_type == 12) {
                System.out.println("inside 12 scheme owner");
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {

                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load scheme." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 13) {
                System.out.println("inside 13 beneficiary");
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();
                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load beneficiary." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            }
            else if (cmbSL_type == 14) // Beneficiaries
            {
                try {
                    ps =  con.prepareStatement("SELECT BENEFICIARY_SNO AS sl_code,BENEFICIARY_NAME as sl_code_desc From Pms_Dcb_Mst_Beneficiary Where office_id = ? order by BENEFICIARY_NAME");


                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +   rs.getString("sl_code_desc") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
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
                           ps=con.prepareStatement("SELECT accounting_unit_id,trim(accounting_unit_name) as accounting_unit_name FROM fas_mst_acct_units WHERE accounting_unit_id!=? and STATUS is null order by accounting_unit_name");
                           ps.setInt(1,cmbAcc_UnitCode);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                          
                           while(rs.next())
                           {
                           xml=xml+"<cid>"+rs.getInt("accounting_unit_id")+
                           "</cid><cname>"+rs.getString("accounting_unit_name")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            //Miscellaneous
            else if(cmbSL_type==17)
            { 
               
                   try {
                           ps=con.prepareStatement("SELECT TYPE_ID,trim(TYPE_NAME) AS TYPE_NAME From Fas_Sl_Types_User_Defined WHERE ACCOUNTING_UNIT_ID=? ORDER BY TYPE_ID");
                           ps.setInt(1,cmbAcc_UnitCode);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                          
                           while(rs.next())
                           {
                           xml=xml+"<cid>"+rs.getInt("TYPE_ID")+
                           "</cid><cname>"+rs.getString("TYPE_NAME")+
                           "</cname>";
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
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
                           ps=con.prepareStatement("select ppo_no,pensioner_name  FROM hrm_pen_mst_details WHERE process_status='VALIDATE' and payment_office_id=? order by pensioner_name ");
                           ps.setInt(1,cmbOffice_code);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                           String acc_no_plus_mode_one = "",pen_name="";
                           int pp_one=0;
                          
                           while(rs.next())
                           {
                               pp_one=rs.getInt("ppo_no");
                               pen_name=rs.getString("pensioner_name");
                               acc_no_plus_mode_one =  pen_name+ "-" +pp_one ;
                           xml=xml+"<cid>"+rs.getInt("ppo_no")+"</cid><cname>"+acc_no_plus_mode_one+"</cname>";
                           
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            else if(cmbSL_type==19)
            { 
               System.out.println("Inside family pensioner Unit");
                   try {
                           ps=con.prepareStatement("SELECT ppo_no,FPENSIONER_NAME FROM HR_PEN_MST_FAMILY WHERE process_status ='VALIDATE' AND payment_office_id=? ORDER BY FPENSIONER_NAME ");
                           ps.setInt(1,cmbOffice_code);
                           System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                           rs=ps.executeQuery();
                           String acc_no_plus_mode_one = "",pen_name="";
                           int pp_one=0;
                          
                           while(rs.next())
                           {
                               pp_one=rs.getInt("ppo_no");
                               pen_name=rs.getString("FPENSIONER_NAME");
                               
                               acc_no_plus_mode_one =  pen_name+ "-" +pp_one ;
                           xml=xml+"<cid>"+rs.getInt("ppo_no")+"</cid><cname>"+acc_no_plus_mode_one+ "</cname>";
                           System.out.println(":::::::::::::xml::::::"+xml);
                           
                           y++;
                           }
                           if(y!=0)
                           {
                               xml=xml+"<flag>success</flag>";
                           }
                           else
                               xml=xml+"<flag>failure</flag>";
                               
                           ps.close();
                           rs.close();
                       }
                       catch(Exception e)
                       {
                       System.out.println("catch..HERE.in load supplier."+e);
                       xml=xml+"<flag>failure</flag>";
                       }
            }
            
            else if (cmbSL_type == 0) {
                xml = xml + "<flag>failure</flag>";
            } else if (cmbSL_type == 14) {
                System.out.println("inside 14 scheme owner");
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {

                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load scheme." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 15) {
                System.out.println("inside 15 scheme owner");
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {

                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load scheme." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } //
        else if (strCommand.equalsIgnoreCase("office")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int oid = 0;
            xml = "<response><command>office</command>";
            try {
                oid = Integer.parseInt(request.getParameter("oid"));
            } catch (Exception e) {
                System.out.println("getting office id failed");
            }

            try {
                // Error will occur ... query is incorrect format
                ps =
  con.prepareStatement("select OFFICE_ID, OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1, oid);
                rs = ps.executeQuery();
                System.out.println("inside offices");
                xml = xml + "<flag>success</flag>";
                int cnt = 0;
                if (rs.next()) {
                    xml =
 xml + "<oid>" + rs.getInt("OFFICE_ID") + "</oid><oname>" +
   rs.getString("OFFICE_NAME") + "</oname>";
                    cnt++;
                }
                System.out.println(cnt + "count");
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }


    }
}
