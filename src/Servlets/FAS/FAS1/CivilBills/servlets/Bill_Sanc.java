package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ResourceBundle;


public class Bill_Sanc extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
        PrintWriter out;
        out = response.getWriter();
        String xml;
        xml = "";
        Connection con=null;
        PreparedStatement ps;
        ResultSet result=null;
        
        response.setHeader("Cache-Control","no-cache");
        
        /**
         * Session Checking 
        */
        HttpSession session=request.getSession(false);
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
                       return;
              }
              System.out.println(session);
                 
        }
        catch(Exception e)
        {
              System.out.println("Redirect Error :"+e);
        }
        
        System.out.println("welcome 2 servlet");
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        String strCommand = "";        
        try 
        {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }
        catch (Exception e) 
        {
            System.out.println("Exception in assigning..." + e);
        }
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
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
                  System.out.println("Exception in opening connection :"+e);
        }        
        int cmbAcc_UnitCode = 0,cmbOffice_code=0,txtEmpIDmas=0,sanc=0;
        int majorType=0,minorType=0,txtOfficeIDmas=0;
        String finyr = "";        
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
        if(strCommand.equalsIgnoreCase("check"))
          {           
                 majorType=Integer.parseInt(request.getParameter("majorType"));     System.out.println("majorType"+majorType);                              
                 xml="<response><command>Disp</command>";
                 try 
                   {
                           System.out.println("inside try");
                           ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=?");
                           ps.setInt(1,majorType);
                           result = ps.executeQuery(); 
                           System.out.println("result is"+result);
                           while(result.next())      
                               {
                                  xml=xml+"<mincode>"+result.getString("BILL_MINOR_TYPE_CODE")+"</mincode>";
                                  xml=xml+"<mindesc>"+result.getString("BILL_MINOR_TYPE_DESC")+"</mindesc>";
                                  xml=xml+"<flag>success</flag>";
                               }
                                  xml=xml+"<flag>failure</flag>";
                   }
                 catch(Exception e1)
                   {
                           System.out.println("Exception in idcheck ===> "+e1);
                           xml=xml+"<flag>failure</flag>";
                   }  
                   System.out.println("response end............."+xml);
                   xml=xml+"</response>";
          }
        else if(strCommand.equalsIgnoreCase("Add"))
          {
                finyr=request.getParameter("finyr");
                
                txtEmpIDmas = Integer.parseInt(request.getParameter("txtEmpIDmas"));
                majorType=Integer.parseInt(request.getParameter("majorType"));     System.out.println("majorType"+majorType);
                minorType=Integer.parseInt(request.getParameter("minorType"));     System.out.println("minorType"+minorType);
                txtOfficeIDmas = Integer.parseInt(request.getParameter("txtOfficeIDmas"));
                System.out.println("txtOfficeIDmas.........."+txtOfficeIDmas);
                sanc=Integer.parseInt(request.getParameter("sanc"));
                xml="<response><command>Add</command>"; 
                 try 
                    {
                        ps=con.prepareStatement("select * from FAS_BILL_SANCTION_LEVEL_EMP where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and EMPLOYEE_ID=?");
                        ps.setInt(1,cmbAcc_UnitCode);     
                        ps.setInt(2,cmbOffice_code);
                        ps.setString(3,finyr);
                        ps.setInt(4,majorType);
                        ps.setInt(5,minorType);
                        ps.setInt(6,txtEmpIDmas);
                        ResultSet res=ps.executeQuery();
                        System.out.println(res);
                        if(res.next()) {
                            xml=xml+"<flag>AlreadyExist</flag>"; 
                        }
                        else
                        {
                            String sql="insert into FAS_BILL_SANCTION_LEVEL_EMP(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,EMPLOYEE_ID,OFFICE_ID,SANCTIONING_AUTHORITY,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?)";
                            System.out.println("sql"+sql);
                            ps = con.prepareStatement(sql);
                            System.out.println("ps"+ps);
                            ps.setInt(1,cmbAcc_UnitCode);     
                            ps.setInt(2,cmbOffice_code);
                            ps.setString(3,finyr);
                            ps.setInt(4,majorType);
                            ps.setInt(5,minorType);
                            ps.setInt(6,txtEmpIDmas);
                            ps.setInt(7,txtOfficeIDmas);
                            ps.setInt(8,sanc);
                            ps.setString(9,update_user);
                            ps.setTimestamp(10,ts);
                            ps.executeUpdate();
                            xml=xml+"<flag>success</flag>";   
                        }
                    }
                catch(Exception e) 
                    {   System.out.println("Error ****"+e.getMessage());  
                        xml=xml+"<flag>failure</flag>";
                        }
                xml=xml+"</response>";
          }
        else if (strCommand.equalsIgnoreCase("Update"))
          {
                        finyr=request.getParameter("finyr");
                                
                        txtEmpIDmas = Integer.parseInt(request.getParameter("txtEmpIDmas"));
                        majorType=Integer.parseInt(request.getParameter("majorType"));     System.out.println("majorType"+majorType);
                        minorType=Integer.parseInt(request.getParameter("minorType"));     System.out.println("minorType"+minorType);
                        txtOfficeIDmas = Integer.parseInt(request.getParameter("txtOfficeIDmas"));System.out.println(txtOfficeIDmas);
                        sanc=Integer.parseInt(request.getParameter("sanc"));
                        xml="<response><command>Updated</command>"; 
                             try {
                                ps =  con.prepareStatement("update FAS_BILL_SANCTION_LEVEL_EMP set  OFFICE_ID=?,SANCTIONING_AUTHORITY=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and EMPLOYEE_ID=?");
                                ps.setInt(1,txtOfficeIDmas);
                                ps.setInt(2,sanc);
                                ps.setString(3,update_user);
                                ps.setTimestamp(4,ts);
                                ps.setInt(5,cmbAcc_UnitCode);
                                ps.setInt(6,cmbOffice_code);
                                ps.setString(7,finyr);
                                ps.setInt(8,majorType);
                                ps.setInt(9,minorType);
                                ps.setInt(10,txtEmpIDmas);
                                
                                ps.executeUpdate();
                                xml = xml + "<flag>success</flag>";
                                System.out.println("here is ok");
                            }
                            catch (Exception e) {
                                System.out.println("catch..HERE.in load head code." + e);
                                xml = xml + "<flag>failure</flag>";
                            }
                            xml = xml + "</response>";
          } 
        else if (strCommand.equalsIgnoreCase("Delete")) 
          {
                        finyr=request.getParameter("finyr");
                        
                        majorType=Integer.parseInt(request.getParameter("majorType"));     System.out.println("majorType.........."+majorType);
                        minorType=Integer.parseInt(request.getParameter("minorType")); System.out.println("minor.............."+minorType);
                        txtEmpIDmas = Integer.parseInt(request.getParameter("txtEmpIDmas"));   xml = "<response><command>Delete</command>";System.out.println(xml);
                        try {
                            ps = con.prepareStatement("delete from FAS_BILL_SANCTION_LEVEL_EMP where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and EMPLOYEE_ID=?");
                            ps.setInt(1,cmbAcc_UnitCode);
                            ps.setInt(2,cmbOffice_code);
                            ps.setString(3,finyr);
                            ps.setInt(4,majorType);
                            ps.setInt(5,minorType);
                            ps.setInt(6,txtEmpIDmas);
                            ps.executeUpdate();
                            xml = xml + "<flag>success</flag>";
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load head code." + e);
                            xml = xml + "<flag>failure</flag>";
                        }
                        xml = xml + "</response>";
         }
                System.out.println("xml is : " + xml);
                out.write(xml);
                out.close();
        }
}
