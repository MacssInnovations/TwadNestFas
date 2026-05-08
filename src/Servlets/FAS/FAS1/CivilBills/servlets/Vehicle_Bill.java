package Servlets.FAS.FAS1.CivilBills.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Vehicle_Bill extends HttpServlet {
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
        String update_user=(String)session.getAttribute("UserId");
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
        int cmbAcc_UnitCode = 0,cmbOffice_code=0;
        int vehno=0,txtCash_Month_hid=0,txtCash_year=0,refno=0;
        String fuelqty="",oilqty="";
        String fuelamt="",oilamt="";
        String anndate = "",active="",txtRemarks = "",anndate1 = "";
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
        if(strCommand.equalsIgnoreCase("aliasDisp"))
          {           
                   vehno=Integer.parseInt(request.getParameter("vehno"));                                   
                   xml="<response><command>Disp</command>";System.out.println(xml);
                   try 
                       {
                           System.out.println("inside try");
                           ps = con.prepareStatement("select FUEL_CEILING_QTY,FUEL_CEILING_AMT,OIL_CEILING_QTY,OIL_CEILING_AMT from FAS_VEHICLE_MASTER where VEHICLE_CODE=?");
                           ps.setInt(1,vehno);
                           result = ps.executeQuery(); 
                           System.out.println("result is"+result);
                           if(result.next())      
                           {
                               System.out.println("inside result");
                               xml=xml+"<flag>success</flag>";
                               xml=xml+"<fuelqty>"+result.getString("FUEL_CEILING_QTY")+"</fuelqty>";
                               xml=xml+"<fuelamt>"+result.getString("FUEL_CEILING_AMT")+"</fuelamt>";
                               xml=xml+"<oilqty>"+result.getString("OIL_CEILING_QTY")+"</oilqty>";
                               xml=xml+"<oilamt>"+result.getString("OIL_CEILING_AMT")+"</oilamt>";
                           }
                           else 
                           {
                               xml=xml+"<flag>failure</flag>";    
                           }
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
                            anndate=request.getParameter("anndate");
                            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
                            Com_CashBook1 cb=new Com_CashBook1();
                            /** Assign Cashbook Year and Month to year_month Variable */
                            String year_month=cb.cb_date(anndate).toString();
                            /** Split Cash Book Year and Month */
                            String []ym=year_month.split("/");
                            /** Assign Year and Month */
                            txtCash_year=Integer.parseInt(ym[0]);
                            txtCash_Month_hid=Integer.parseInt(ym[1]);
                            vehno=Integer.parseInt(request.getParameter("vehno"));
                            refno=Integer.parseInt(request.getParameter("refno"));
                            anndate1=request.getParameter("anndate1");
                            active=request.getParameter("active");System.out.println("Active......."+active);
                            fuelqty=request.getParameter("fuelqty");
                            fuelamt=request.getParameter("fuelamt");System.out.println(fuelamt);
                            oilqty=request.getParameter("oilqty");
                            oilamt=request.getParameter("oilamt");System.out.println(oilamt);
                            txtRemarks = request.getParameter("txtRemarks");
                            xml="<response><command>Add</command>"; 
                             try 
                                {
                                    ps=con.prepareStatement("select * from FAS_VEHICLE_BILL_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_CODE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VEHICLE_CODE=?");
                                    ps.setInt(1,cmbAcc_UnitCode);     
                                    ps.setInt(2,cmbOffice_code);
                                    ps.setInt(3,txtCash_year);
                                    ps.setInt(4,txtCash_Month_hid);System.out.println("vehno"+vehno);
                                    ps.setInt(5,vehno);
                                    ResultSet res=ps.executeQuery();
                                    System.out.println(res);
                                    if(res.next()) {
                                        xml=xml+"<flag>AlreadyExist</flag>"; 
                                    }
                                    else
                                    {
                                    String sql="insert into FAS_VEHICLE_BILL_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_CODE,CASHBOOK_YEAR,CASHBOOK_MONTH,VEHICLE_CODE,REF_NO,REF_DATE,FUEL_USED_QTY,FUEL_USED_AMOUNT,OIL_USED_QTY,OIL_USED_AMOUNT,R_AND_M,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?)";
                                    System.out.println("sql"+sql);
                                    ps = con.prepareStatement(sql);
                                    System.out.println("ps"+ps);
                                    ps.setInt(1,cmbAcc_UnitCode);     
                                    ps.setInt(2,cmbOffice_code);
                                    ps.setInt(3,txtCash_year);
                                    ps.setInt(4,txtCash_Month_hid);System.out.println("vehno"+vehno);
                                    ps.setInt(5,vehno);
                                    ps.setInt(6,refno);
                                    ps.setString(7,anndate1);
                                    ps.setString(8,fuelqty);
                                    ps.setString(9,fuelamt);System.out.println("annno"+fuelamt);
                                    ps.setString(10,oilqty);
                                    ps.setString(11,oilamt);
                                    ps.setString(12,active);System.out.println("fuelamt"+active);
                                    ps.setString(13,txtRemarks);
                                    ps.setString(14,update_user);
                                    ps.setTimestamp(15,ts);
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
        else if (strCommand.equalsIgnoreCase("Delete")) 
                  {
                            vehno=Integer.parseInt(request.getParameter("vehno"));
                            anndate=request.getParameter("anndate");System.out.println("aaaaaaaaaaaaa"+anndate);
                                    
                            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
                            Com_CashBook1 cb=new Com_CashBook1();
                            
                            /** Assign Cashbook Year and Month to year_month Variable */
                            String year_month=cb.cb_date(anndate).toString();
                            
                            /** Split Cash Book Year and Month */
                            String []ym=year_month.split("/");
                            
                            /** Assign Year and Month */
                            txtCash_year=Integer.parseInt(ym[0]);
                            txtCash_Month_hid=Integer.parseInt(ym[1]);
                            xml = "<response><command>Delete</command>";System.out.println(xml);
            
                            try {
                                //ps = con.prepareStatement("delete from FAS_VEHICLE_BILL_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_CODE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VEHICLE_CODE=?");
                            	ps = con.prepareStatement("update FAS_VEHICLE_BILL_DETAILS set STATUS='C' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_CODE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VEHICLE_CODE=?");
                                ps.setInt(1, cmbAcc_UnitCode);
                                ps.setInt(2, cmbOffice_code);
                                ps.setInt(3,txtCash_year);
                                ps.setInt(4,txtCash_Month_hid);
                                ps.setInt(5,vehno);
                                ps.executeUpdate();
                                xml = xml + "<flag>success</flag>";
                            } catch (Exception e) {
                                System.out.println("catch..HERE.in load head code." + e);
                                xml = xml + "<flag>failure</flag>";
                            }
                            xml = xml + "</response>";
            }
            else if (strCommand.equalsIgnoreCase("Update"))
                    {
                        anndate=request.getParameter("anndate");System.out.println("aaaaaaaaaaaaa"+anndate);
                                
                        /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
                        Com_CashBook1 cb=new Com_CashBook1();
                        
                        /** Assign Cashbook Year and Month to year_month Variable */
                        String year_month=cb.cb_date(anndate).toString();
                        
                        /** Split Cash Book Year and Month */
                        String []ym=year_month.split("/");
                        
                        /** Assign Year and Month */
                        txtCash_year=Integer.parseInt(ym[0]);
                        txtCash_Month_hid=Integer.parseInt(ym[1]);
                        vehno=Integer.parseInt(request.getParameter("vehno"));
                        refno=Integer.parseInt(request.getParameter("refno"));
                        anndate1=request.getParameter("anndate1");
                        active=request.getParameter("active");System.out.println("Activeeeeeeeeeeee............"+active);
                        fuelqty=request.getParameter("fuelqty");
                        fuelamt=request.getParameter("fuelamt");System.out.println(fuelamt);
                        oilqty=request.getParameter("oilqty");
                        oilamt=request.getParameter("oilamt");System.out.println(oilamt);
                        txtRemarks = request.getParameter("txtRemarks");
                            xml="<response><command>Updated</command>"; 
                             try {
                                ps =  con.prepareStatement("update FAS_VEHICLE_BILL_DETAILS set REF_NO=?,REF_DATE=to_date(?,'dd/mm/yyyy'),FUEL_USED_QTY=?,FUEL_USED_AMOUNT=?,OIL_USED_QTY=?,OIL_USED_AMOUNT=?,R_AND_M=?,REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_CODE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VEHICLE_CODE=?");
                                ps.setInt(1,refno);
                                ps.setString(2,anndate1);
                                ps.setString(3,fuelqty);
                                ps.setString(4,fuelamt);
                                ps.setString(5, oilqty);
                                ps.setString(6,oilamt);
                              
                                ps.setString(7,active);
                                ps.setString(8,txtRemarks );
                                ps.setInt(9,cmbAcc_UnitCode);
                                ps.setInt(10,cmbOffice_code );
                                ps.setInt(11,txtCash_year);
                                ps.setInt(12,txtCash_Month_hid );
                                ps.setInt(13,vehno );System.out.println(active);
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
            else if(strCommand.equalsIgnoreCase("checkParam"))
              {    
                  vehno=Integer.parseInt(request.getParameter("vehno"));
                  anndate=(request.getParameter("anndate"));
                  Com_CashBook1 cb=new Com_CashBook1();
                  
                  /** Assign Cashbook Year and Month to year_month Variable */
                  String year_month=cb.cb_date(anndate).toString();
                  
                  /** Split Cash Book Year and Month */
                  String []ym=year_month.split("/");
                  
                  /** Assign Year and Month */
                  txtCash_year=Integer.parseInt(ym[0]);
                  txtCash_Month_hid=Integer.parseInt(ym[1]);
                  xml=xml+"<response><command>checkParam</command>";
                  try
                  {
                      ps=con.prepareStatement("select * from FAS_VEHICLE_BILL_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_CODE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VEHICLE_CODE=?");                        
                      ps.setInt(1,cmbAcc_UnitCode); 
                      ps.setInt(2,cmbOffice_code);
                      ps.setInt(3,txtCash_year);
                      ps.setInt(4,txtCash_Month_hid);
                      ps.setInt(5,vehno);
                      result=ps.executeQuery();
                      if(result.next())
                      {
                      xml=xml+"<flag>success</flag>";
                      }
                      else
                      {  
                    	  xml=xml+"<flag>failure</flag>";
                      }
                    }
                  catch(Exception e){
                                      xml=xml+"<flag>failure</flag>";
                  System.out.println(e);
                                  }
                  xml=xml+"</response>";
              }
                System.out.println("xml is : " + xml);
                out.write(xml);
        out.close();
        }
}
