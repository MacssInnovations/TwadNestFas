package Servlets.FAS.FAS1.CivilBills.servlets;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

public class SL_TYPE_CODE_NAME
{
    public ResultSet getResult(int cmbAcc_UnitCode,int cmbOffice_code,int cmbSL_type,int cmbSL_code)
    {
        Connection con=null;
        ResultSet rs_get=null;
        PreparedStatement ps_get=null;
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

                  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                   Class.forName(strDriver.trim());
                   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
          System.out.println("Exception in connection...."+e);
        }
        System.out.println("here in General class"+cmbAcc_UnitCode);
        System.out.println(cmbOffice_code);
        System.out.println(cmbSL_type);
        System.out.println(cmbSL_code);
       
            try
            {
                switch(cmbSL_type)
                {
                    case 1:
                       ps_get=con.prepareStatement("select SUPPLIER_ID as cid,SUPPLIER_NAME as cname from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                       ps_get.setInt(1,cmbAcc_UnitCode);
                       ps_get.setInt(2,cmbOffice_code);
                       rs_get=ps_get.executeQuery();
                       break;
                    case 2:
                       ps_get=con.prepareStatement("select FIRMS_ID as cid, FIRMS_NAME as cname from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
                       ps_get.setInt(1,cmbAcc_UnitCode);
                       ps_get.setInt(2,cmbOffice_code);
                       rs_get=ps_get.executeQuery();
                       break;
                    case 3:
                       ps_get=con.prepareStatement("select ASSET_CODE as cid,ASSET_DESCRIPTION as cname from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
                       ps_get.setInt(1,cmbAcc_UnitCode);
                       ps_get.setInt(2,cmbOffice_code);
                       rs_get=ps_get.executeQuery();
                       break;
                    case 5:
                        ps_get=con.prepareStatement("select OFFICE_ID as cid, OFFICE_NAME as cname,OFFICE_STATUS_ID as status from COM_MST_OFFICES where office_id=?");
                        ps_get.setInt(1,cmbSL_code);                          // loading only one office      *****                  
                        rs_get=ps_get.executeQuery();
                        break;
                    case 7:
                    
                        ps_get=con.prepareStatement("select e.EMPLOYEE_ID as cid ,e.EMPLOYEE_NAME || '.' ||  e.EMPLOYEE_INITIAL|| '-'||d.DESIGNATION as cname " +
                                                     "from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d " +
                                                     "where " +
                                                     "c.DESIGNATION_ID=d.DESIGNATION_ID  " +
                                                     " and c.EMPLOYEE_ID=e.EMPLOYEE_ID " +
                                                     " and c.EMPLOYEE_ID=? order by e.EMPLOYEE_NAME ");
                        ps_get.setInt(1,cmbSL_code);
                        rs_get=ps_get.executeQuery();
                        
                        break;
                    case 9:
                    
                        ps_get=con.prepareStatement("select off.OTHER_DEPT_OFFICE_ALIAS_ID as cid ,dep.OTHER_DEPT_NAME || ' - ' ||  off.OTHER_DEPT_OFFICE_NAME as cname " +
                                                     " from HRM_MST_OTHER_DEPTS dep,HRM_MST_OTHER_DEPT_OFFICES off " +
                                                     " where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID " +
                                                     " ORDER BY dep.OTHER_DEPT_NAME");
                        rs_get=ps_get.executeQuery();
                        break;
                    case 10:
                        ps_get=con.prepareStatement("select PROJECT_ID as cid, PROJECT_NAME as cname from PMS_MST_PROJECTS_VIEW where  OFFICE_ID=? ");
                        ps_get.setInt(1,cmbOffice_code);
                        rs_get=ps_get.executeQuery();
                        break;
                    case 11:
                        String contra=" select CONTRACTOR_ID as cid,CONTRACTOR_NAME as cname " +
                                      " from PMS_MST_CONTRACTORS_VIEW " +
                                      " where OFFICE_ID=? or OFFICE_ID in " +
                                      "                                (select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where office_id=? " +
                                      "                                 union all " +
                                      "                                 select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where office_id=? ) " +
                                      "order by CONTRACTOR_NAME";
                        ps_get=con.prepareStatement(contra);
                        ps_get.setInt(1,cmbOffice_code);
                        ps_get.setInt(2,cmbOffice_code);
                        ps_get.setInt(3,cmbOffice_code);
                        rs_get=ps_get.executeQuery();
                     break;
                }
               
        }
        catch(Exception e)
        {
           System.out.println("error");
        }
                
                return rs_get;
       
    }
}

