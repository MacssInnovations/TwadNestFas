package Servlets.FAS.FAS1.CommonControls.servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeDetails {

    public static String getEmployeeDetails(Connection con, int txtRecei_from) 
    {
    
       PreparedStatement ps=null;
       ResultSet rs=null;
       String EmpName="";
    
       String sql="" +
       "select                                                                          \n" + 
       "      e.EMPLOYEE_ID,                                                            \n" + 
       "      e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,   \n" + 
       "      c.EMPLOYEE_STATUS_ID                                                      \n" + 
       "   from                                                                         \n" + 
       "      HRM_MST_EMPLOYEES e,                                                      \n" + 
       "      HRM_EMP_CURRENT_POSTING c,                                                \n" + 
       "      HRM_MST_DESIGNATIONS d                                                    \n" + 
       "  where                                                                         \n" + 
       "      c.DESIGNATION_ID=d.DESIGNATION_ID  and                                    \n" + 
       "      e.EMPLOYEE_ID=c.EMPLOYEE_ID and                                           \n" + 
       "      c.EMPLOYEE_ID=?                                                           \n" + 
       "  order by e.EMPLOYEE_NAME";
    
      try
      {
            ps=con.prepareStatement(sql);
            ps.setInt(1,txtRecei_from);
            rs=ps.executeQuery();
            while (rs.next()) {
               EmpName  =rs.getString("ENAME");    
            }            
            return EmpName;
      }  
      catch(Exception e) 
      {
          System.out.println(e);
          return "";    
      }
       
    }
    
}
