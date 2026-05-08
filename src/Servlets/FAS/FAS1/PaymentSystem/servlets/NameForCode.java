package Servlets.FAS.FAS1.PaymentSystem.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

public class NameForCode {
    public String getname(int cmbAcc_UnitCode, int cmbOffice_code,
                          int cmbSL_type, int cmbSL_code,
                          int addtional_field_value) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String name = "Not Available";

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
            System.out.println("Exception in connection...." + e);
        }
        System.out.println("here in NAMEforCOde class" + cmbAcc_UnitCode);
        System.out.println(cmbOffice_code);
        System.out.println(cmbSL_type);
        System.out.println(cmbSL_code);

        if (cmbSL_type == 1) {
            System.out.println("here in supplier 1");
            try {
                ps =
  con.prepareStatement("select SUPPLIER_ID,SUPPLIER_NAME from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SUPPLIER_ID=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbSL_code);
                rs = ps.executeQuery();
                System.out.println("here");
                if (rs.next()) {
                    name = rs.getString("SUPPLIER_NAME");
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load supplier." + e);
            }
        } else if (cmbSL_type == 2) {
            System.out.println("here in Firms 2");
            try {
                ps =
  con.prepareStatement("select FIRMS_ID, FIRMS_NAME from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FIRMS_ID=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbSL_code);
                rs = ps.executeQuery();
                if (rs.next()) {
                    name = rs.getString("FIRMS_NAME");
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load firms" + e);
            }
        } else if (cmbSL_type == 3) {
            System.out.println("here in assets 3");
            try {
                ps =
  con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbSL_code);
                rs = ps.executeQuery();
                if (rs.next()) {
                    name = rs.getString("ASSET_DESCRIPTION");
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp code." + e);
            }
        } else if (cmbSL_type == 4) {
            System.out.println("inside cheque 4 here ");
            try {

            } catch (Exception e) {
                System.out.println("catch..HERE.in load cheque book code." +
                                   e);
            }
        } else if (cmbSL_type == 5) {
            try {
                // Error will occur ... query is incorrect format
                ps =
  con.prepareStatement("select OFFICE_ID, OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1, cmbSL_code);
                rs = ps.executeQuery();
                System.out.println("inside offices 5");
                if (rs.next()) {
                    name = rs.getString("OFFICE_NAME");
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office code." + e);
            }
        } else if (cmbSL_type == 6) {
            try {
                // Error will occur ... query is incorrect
                ps =
  con.prepareStatement("select BANK_ID from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbSL_code);
                rs = ps.executeQuery();
                System.out.println("inside 2");
                while (rs.next()) {

                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp code." + e);
            }
        } else if (cmbSL_type == 7) {
            //int other_dept_off_alias_id=0, oid=0,cmbMas_SL_type=0;
            //String deptid="";
            System.out.println("inside 7 employeees");
            try {

                ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and e.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
                ps.setInt(1, cmbSL_code);
                rs = ps.executeQuery();
                if (rs.next()) {
                    name =
rs.getString("EMPLOYEE_NAME") + " - " + rs.getString("DESIGNATION");
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp code." + e);
            }


        } else if (cmbSL_type == 8) {
            // Error will occur ... query is incorrect
            try {
                ps = con.prepareStatement("select from where OFFICE_ID=? ");
                ps.setInt(1, cmbOffice_code);
                rs = ps.executeQuery();
                System.out.println("inside 8");
                while (rs.next()) {

                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp code." + e);
            }
        } else if (cmbSL_type == 9) {
            System.out.println("inside 9 OTHER Departments");

            try {
                ps =
  con.prepareStatement("select dep.OTHER_DEPT_NAME as OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFFICE_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                       ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID and off.OTHER_DEPT_OFFICE_ALIAS_ID=? ORDER BY dep.OTHER_DEPT_NAME");
                ps.setInt(1, cmbSL_code);
                rs = ps.executeQuery();
                if (rs.next()) {
                    name =
rs.getString("OTHER_DEPT_NAME") + " - " + rs.getString("OTHER_DEPT_OFFICE_NAME");
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load department code." + e);
            }
        } else if (cmbSL_type == 10) {
            try {
                System.out.println("inside 10 here projects");
                ps =
  con.prepareStatement("select PROJECT_ID, PROJECT_NAME  from PMS_MST_PROJECTS_VIEW where  OFFICE_ID=? and PROJECT_ID=? ");
                ps.setInt(1, cmbOffice_code);
                ps.setInt(2, cmbSL_code);
                rs = ps.executeQuery();
                while (rs.next()) {
                    name = rs.getString("PROJECT_NAME");
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load supplier." + e);
            }
        } else if (cmbSL_type == 11) {
            try {
                System.out.println("inside 11 d contractors" + cmbSL_code);
                //ps=con.prepareStatement("select CONTRACTOR_ID,CONTRACTOR_NAME from PMS_MST_CONTRACTORS_VIEW where OFFICE_ID=? and CONTRACTOR_ID=?");
                ps =
  con.prepareStatement("select CONTRACTOR_ID,CONTRACTOR_NAME from PMS_MST_CONTRACTORS_VIEW where CONTRACTOR_ID=?");
                //ps.setInt(1,cmbOffice_code);
                ps.setInt(1, cmbSL_code);
                rs = ps.executeQuery();
                while (rs.next()) {
                    name = rs.getString("CONTRACTOR_NAME");
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load contractor." + e);
            }
        } else if (cmbSL_type == 12) {
            System.out.println("inside 12 scheme owner"); // Error will occur ... table not given
            try {
                ps = con.prepareStatement("select from where OFFICE_ID=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbSL_code);
                rs = ps.executeQuery();
                while (rs.next()) {

                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load scheme." + e);
            }
        } else if (cmbSL_type == 13) {
            System.out.println("inside 13 beneficiary"); // Error will occur ... table not given
            try {
                ps = con.prepareStatement("select from where OFFICE_ID=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbSL_code);
                rs = ps.executeQuery();
                while (rs.next()) {

                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load beneficiary." + e);
            }
        } else if (cmbSL_type == 0) {
        }
        return name;
    }
}


