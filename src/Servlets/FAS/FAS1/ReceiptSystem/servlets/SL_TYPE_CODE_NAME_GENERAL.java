package Servlets.FAS.FAS1.ReceiptSystem.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SL_TYPE_CODE_NAME_GENERAL {
    public ResultSet getResult_General(int cmbAcc_UnitCode, int cmbOffice_code,
                                       int cmbSL_type, int cmbSL_code,
                                       int addtional_field_value) {
        Connection con = null;
        ResultSet rs_get = null;
        PreparedStatement ps_get = null;
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }
       // System.out.println("here in General class::SL_TYPE_CODE_NAME_GENERAL::"+cmbSL_code+"---" + cmbAcc_UnitCode);
//        System.out.println(cmbOffice_code);
       System.out.println(cmbSL_type);
      // System.out.println("cmbSL_type"+cmbSL_type);
//        System.out.println(addtional_field_value);

        try {
            switch (cmbSL_type) {
//            case 1:
//            	// commanded on 06/07/2018
//            	
////            	ps_get =
////                        con.prepareStatement("select SUPPLIER_ID as cid,SUPPLIER_NAME as cname from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
//            	ps_get =
//                con.prepareStatement("select SUPPLIER_ID as cid,SUPPLIER_NAME as cname from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? union all select SUPPLIER_ID as cid,SUPPLIER_NAME as cname from COM_SUPPLIER_SL_MST_MAP where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
//            	
//                ps_get.setInt(1, cmbAcc_UnitCode);
//                ps_get.setInt(2, cmbOffice_code);
//                ps_get.setInt(3, cmbAcc_UnitCode);
//                ps_get.setInt(4, cmbOffice_code);
//                rs_get = ps_get.executeQuery();
//                break;
//            case 2:
//               // commanded on 06/07/2018
//            	
////            	ps_get =
////                con.prepareStatement("select FIRMS_ID as cid, FIRMS_NAME as cname from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
//
//            	ps_get =
//                        con.prepareStatement("select FIRMS_ID as cid, FIRMS_NAME as cname from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? UNION ALL select FIRMS_ID as cid, FIRMS_NAME as cname from COM_FIRMS_SL_MST_MAP where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
//                ps_get.setInt(1, cmbAcc_UnitCode);
//                ps_get.setInt(2, cmbOffice_code);
//                ps_get.setInt(3, cmbAcc_UnitCode);
//                ps_get.setInt(4, cmbOffice_code);
//                rs_get = ps_get.executeQuery();
//                break;
//            case 3:
//                ps_get =
//                        con.prepareStatement("select ASSET_CODE as cid,ASSET_DESCRIPTION as cname from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
//                ps_get.setInt(1, cmbAcc_UnitCode);
//                ps_get.setInt(2, cmbOffice_code);
//                rs_get = ps_get.executeQuery();
//                break;
//            case 5:
//                ps_get =
//                        con.prepareStatement("select OFFICE_ID as cid, OFFICE_NAME as cname from COM_MST_OFFICES where office_id=?");
//                ps_get.setInt(1,
//                              cmbSL_code); // loading only one office      *****
//                rs_get = ps_get.executeQuery();
//                break;
//            case 6:
//                ps_get =
//                        con.prepareStatement("SELECT ( AC_OPERATIONAL_MODE_ID ||'-' || BANK_AC_NO )  as cname , BANK_AC_NO_ALIAS_CODE as cid FROM FAS_MST_BANK_BALANCE_TMP WHERE ACCOUNTING_UNIT_ID=? ORDER BY AC_OPERATIONAL_MODE_ID , BANK_AC_NO");
//                ps_get.setInt(1, cmbAcc_UnitCode);
//                rs_get = ps_get.executeQuery();
//                break;
//            case 7:
//
//                ps_get =
//                        con.prepareStatement("select e.EMPLOYEE_ID as cid ,e.EMPLOYEE_NAME || '.' ||  e.EMPLOYEE_INITIAL|| '-'||d.DESIGNATION as cname " +
//                                             "from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d " +
//                                             "where " +
//                                             "c.DESIGNATION_ID=d.DESIGNATION_ID  " +
//                                             " and c.EMPLOYEE_ID=e.EMPLOYEE_ID " +
//                                             " and c.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
//                ps_get.setInt(1,
//                              cmbSL_code); //loading only one specific employee
//                rs_get = ps_get.executeQuery();
//
//                break;
//            case 9:
//                ps_get =
//                        con.prepareStatement("select off.OTHER_DEPT_OFFICE_ALIAS_ID as cid ,dep.OTHER_DEPT_NAME || ' - ' ||  off.OTHER_DEPT_OFFICE_NAME as cname " +
//                                             " from HRM_MST_OTHER_DEPTS dep,HRM_MST_OTHER_DEPT_OFFICES off " +
//                                             " where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID " +
//                                             " ORDER BY dep.OTHER_DEPT_NAME");
//                rs_get = ps_get.executeQuery();
//                break;
//            case 10:
//                ps_get =
//                        con.prepareStatement("select PROJECT_ID as cid, PROJECT_NAME as cname from PMS_MST_PROJECTS_VIEW where  OFFICE_ID=? ");
//                ps_get.setInt(1, cmbOffice_code);
//                rs_get = ps_get.executeQuery();
//                break;
//            case 11:
//                String contra ="SELECT CONTRACTOR_ID AS cid, "+
//					"  CONTRACTOR_NAME    AS cname"+
//					" From PMS_CONT_REQUEST_REGN"+
//					" WHERE OFFICE_ID=?";
////                    " select CONTRACTOR_ID as cid,CONTRACTOR_NAME as cname " +
////                    " from PMS_MST_CONTRACTORS_VIEW " +
////                    " where OFFICE_ID=? or OFFICE_ID in " +
////                    "                                (select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where office_id=? " +
////                    "                                 union all " +
////                    "                                 select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where office_id=? ) " +
////                    "order by CONTRACTOR_NAME";
//                ps_get = con.prepareStatement(contra);
//                ps_get.setInt(1, cmbOffice_code);
////                ps_get.setInt(2, cmbOffice_code);
////                ps_get.setInt(3, cmbOffice_code);
//                rs_get = ps_get.executeQuery();
//                break;
            
            
            case 1:
//              ps_get =
//                      con.prepareStatement("select SUPPLIER_ID as cid,SUPPLIER_NAME as cname from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SUPPLIER_ID=? and status='L' ");
          	 ps_get =
               con.prepareStatement("select SUPPLIER_ID as cid,SUPPLIER_NAME as cname from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SUPPLIER_ID=? and status='L' union all select SUPPLIER_ID as cid,SUPPLIER_NAME as cname from COM_SUPPLIER_SL_MST_MAP where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SUPPLIER_ID=? and status='L'");
          	
              ps_get.setInt(1, cmbAcc_UnitCode);
              ps_get.setInt(2, cmbOffice_code);
              ps_get.setInt(3, cmbSL_code);
              ps_get.setInt(4, cmbAcc_UnitCode);
              ps_get.setInt(5, cmbOffice_code);
              ps_get.setInt(6, cmbSL_code);
              rs_get = ps_get.executeQuery();
              break;
          case 2:
          	// commanded on 06/07/2018
          	//ps_get =
//              con.prepareStatement("select FIRMS_ID as cid, FIRMS_NAME as cname from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FIRMS_ID=? and status='L'");
          	
              ps_get =
                      con.prepareStatement("select FIRMS_ID as cid, FIRMS_NAME as cname from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FIRMS_ID=? and status='L' UNION ALL select FIRMS_ID as cid, FIRMS_NAME as cname from COM_FIRMS_SL_MST_MAP where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FIRMS_ID=? and status='L'");
              ps_get.setInt(1, cmbAcc_UnitCode);
              ps_get.setInt(2, cmbOffice_code);
              ps_get.setInt(3, cmbSL_code);
              ps_get.setInt(4, cmbAcc_UnitCode);
              ps_get.setInt(5, cmbOffice_code);
              ps_get.setInt(6, cmbSL_code);
              rs_get = ps_get.executeQuery();
              break;
          case 3:
              ps_get =
                      con.prepareStatement("select ASSET_CODE as cid,ASSET_DESCRIPTION as cname from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and ASSET_CODE=? and status='L' ");
              ps_get.setInt(1, cmbAcc_UnitCode);
              ps_get.setInt(2, cmbOffice_code);
              ps_get.setInt(3, cmbSL_code);
              rs_get = ps_get.executeQuery();
              break;
          case 5:
              ps_get =
                      con.prepareStatement("select OFFICE_ID as cid, OFFICE_NAME as cname  from COM_MST_OFFICES where office_id=? and office_status_id not in ('CL','RD','NC') ");
              ps_get.setInt(1,
                            cmbSL_code); // loading only one office      *****
              rs_get = ps_get.executeQuery();
              break;

          case 6:
              ps_get =
                      con.prepareStatement("SELECT ( AC_OPERATIONAL_MODE_ID ||'-' || BANK_AC_NO )  as cname , BANK_AC_NO_ALIAS_CODE as cid FROM FAS_MST_BANK_BALANCE WHERE ACCOUNTING_UNIT_ID=? and BANK_AC_NO=? and status='Y' ORDER BY AC_OPERATIONAL_MODE_ID , BANK_AC_NO");
              ps_get.setInt(1, cmbAcc_UnitCode);
              ps_get.setInt(2,cmbSL_code);
              rs_get = ps_get.executeQuery();
              break;

          case 7:

              ps_get =
                      con.prepareStatement("select e.EMPLOYEE_ID as cid ,e.EMPLOYEE_NAME || '.' ||  e.EMPLOYEE_INITIAL|| '-'||d.DESIGNATION as cname " +
                                           "from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d " +
                                           "where " +
                                           "c.DESIGNATION_ID=d.DESIGNATION_ID  " +
                                           " and c.EMPLOYEE_ID=e.EMPLOYEE_ID " +
                                           " and c.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
              ps_get.setInt(1,
                            cmbSL_code); // loading only one specific employee      *****
              rs_get = ps_get.executeQuery();

              break;
          case 9:
              ps_get =
                      con.prepareStatement("select off.OTHER_DEPT_OFFICE_ALIAS_ID as cid ,dep.OTHER_DEPT_NAME || ' - ' ||  off.OTHER_DEPT_OFFICE_NAME as cname " +
                                           " from HRM_MST_OTHER_DEPTS dep,HRM_MST_OTHER_DEPT_OFFICES off " +
                                           " where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID  and off.OTHER_DEPT_OFFICE_ALIAS_ID=?  " +
                                           " ORDER BY dep.OTHER_DEPT_NAME");
              ps_get.setInt(1, cmbSL_code);
              rs_get = ps_get.executeQuery();
              break;
          case 10:
              ps_get =
                      con.prepareStatement("select PROJECT_ID as cid, PROJECT_NAME as cname from PMS_MST_PROJECTS_VIEW where  OFFICE_ID=? and PROJECT_ID=? and status='L' ");
              ps_get.setInt(1, cmbOffice_code);
              ps_get.setInt(2, cmbSL_code);
              rs_get = ps_get.executeQuery();
              break;
          case 11:
              String contra =
                  " select CONTRACTOR_ID as cid,CONTRACTOR_NAME as cname from PMS_MST_CONTRACTORS_VIEW where CONTRACTOR_ID=? and status='L' and OFFICE_ID=? ";
              ps_get = con.prepareStatement(contra);
              ps_get.setInt(1, cmbSL_code);
              ps_get.setInt(2, cmbOffice_code);
              rs_get = ps_get.executeQuery();
              break;
            
            
            case 15:
                ps_get =con.prepareStatement("SELECT accounting_unit_id as cid,trim(accounting_unit_name) as cname FROM fas_mst_acct_units WHERE accounting_unit_id!=? and STATUS is null order by accounting_unit_name");
                ps_get.setInt(1, cmbAcc_UnitCode);
                rs_get = ps_get.executeQuery();
                break;
                
            case 96://SELECT TYPE_ID as cid,trim(TYPE_NAME) as cname AS TYPE_NAME From Fas_Sl_Types_User_Defined WHERE ACCOUNTING_UNIT_ID=? AND TYPE_ID=? ORDER BY TYPE_ID
            	//System.out.println("96");
                ps_get =con.prepareStatement("SELECT TYPE_ID as cid,trim(TYPE_NAME) as cname From Fas_Sl_Types_User_Defined WHERE ACCOUNTING_UNIT_ID=? ORDER BY TYPE_ID");
                ps_get.setInt(1, cmbAcc_UnitCode);
               // ps_get.setInt(2, cmbAcc_UnitCode);
                rs_get = ps_get.executeQuery();
                break;
                
            case 17://SELECT TYPE_ID as cid,trim(TYPE_NAME) as cname AS TYPE_NAME From Fas_Sl_Types_User_Defined WHERE ACCOUNTING_UNIT_ID=? AND TYPE_ID=? ORDER BY TYPE_ID
            	//System.out.println("96");
                ps_get =con.prepareStatement("SELECT TYPE_ID as cid,trim(TYPE_NAME) as cname From Fas_Sl_Types_User_Defined WHERE ACCOUNTING_UNIT_ID=? and TYPE_ID=?");
                ps_get.setInt(1, cmbAcc_UnitCode);
                ps_get.setInt(2, cmbSL_code);
                rs_get = ps_get.executeQuery();
                break;
                
            }

        } catch (Exception e) {
            System.out.println("Exception in SL_TYPE_CODE_NAME_GENERAL class");
        }

        return rs_get;

    }
}
