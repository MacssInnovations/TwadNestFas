package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javax.servlet.ServletConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class veh_ser extends HttpServlet {

    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {
                      response.setContentType(CONTENT_TYPE);
        PrintWriter out;
        out = response.getWriter();
        String xml;
        xml = "";
        Connection con=null;
        con = null;
        PreparedStatement ps,ps1;
        ResultSet result=null,res1=null;
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
        int vehno=0,ceillimit=0,annno=0;
        String fuelqty="",oilqty="";
        String fuelamt="",oilamt="";
        String offuse = "",anndate = "",vehdesc="",vehalias="";
        String txtRemarks = "", finyr="";
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
                xml="<response><command>Disp</command>";
                try 
                    {
                        System.out.println("vehno:::::::;"+vehno);
                        ps = con.prepareStatement("select ASSET_CODE,ALIAS_CODE,ASSET_DESCRIPTION from COM_MST_ASSETS_SL where ASSET_CODE=?");
                        ps.setInt(1,vehno);
                        result = ps.executeQuery(); 
                        System.out.println("result is"+result);
                        if(result.next())      
                        {
                        System.out.println("inside result");
                            xml=xml+"<flag>success</flag>";
                            xml=xml+"<assetcode>"+result.getInt("ASSET_CODE")+"</assetcode>";
                            xml=xml+"<aliascode>"+result.getString("ALIAS_CODE")+"</aliascode>";
                            xml=xml+"<assetdesc>"+result.getString("ASSET_DESCRIPTION")+"</assetdesc>";
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
        if(strCommand.equalsIgnoreCase("checkNo"))
         {
                vehno=Integer.parseInt(request.getParameter("vehno"));
                xml="<response><command>Display</command>";
                try 
                    {                                                                       
                        ps1 = con.prepareStatement("select SANCTION_ESTIMATE_NO,to_char(SANCTION_ESTIMATE_DATE,'dd-mm-yyyy') as SANCTION_ESTIMATE_DATE from FAS_SANCTION_ESTIMATE where ASSET_CODE=?");
                        ps1.setInt(1,vehno);
                        res1=ps1.executeQuery();
                        if(res1.next())   
                            {   
                                xml=xml+"<flag>success</flag>";
                                xml=xml+"<estimateno>"+res1.getInt("SANCTION_ESTIMATE_NO")+"</estimateno>";
                                xml=xml+"<estimatedate>"+res1.getString("SANCTION_ESTIMATE_DATE")+"</estimatedate>";
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
         else if(strCommand.equalsIgnoreCase("checkParam"))
         {    
                vehno=Integer.parseInt(request.getParameter("vehno"));
                finyr=(request.getParameter("finyr"));
                xml=xml+"<response><command>checkParam</command>";
                try
                {
                    ps=con.prepareStatement("select * from FAS_VEHICLE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and VEHICLE_CODE=?");                        
                    ps.setInt(1,cmbAcc_UnitCode); 
                    ps.setInt(2,cmbOffice_code);
                    ps.setString(3,finyr);
                    ps.setInt(4,vehno);System.out.println("vehno.........."+vehno);
                    result=ps.executeQuery();
                    if(result.next())
                    {
                    xml=xml+"<flag>success</flag>";
                    }
                  }
                catch(Exception e)
                {
                     xml=xml+"<flag>failure</flag>";
                     System.out.println(e);
                }
                xml=xml+"</response>";
         }
        else if(strCommand.equalsIgnoreCase("Add"))
         {
                vehno=Integer.parseInt(request.getParameter("vehno"));
                finyr=(request.getParameter("finyr"));
                vehalias=(request.getParameter("vehalias"));
                vehdesc=request.getParameter("vehdesc");
                offuse = request.getParameter("offuse");
                ceillimit=Integer.parseInt(request.getParameter("ceillimit"));
                annno=Integer.parseInt(request.getParameter("annno"));
                anndate=request.getParameter("anndate");
                fuelqty=(request.getParameter("fuelqty"));
                fuelamt=(request.getParameter("fuelamt"));
                oilqty=(request.getParameter("oilqty"));
                oilamt=(request.getParameter("oilamt"));
                txtRemarks = request.getParameter("txtRemarks");
                xml="<response><command>Add</command>"; 
                try 
                {
                    ps=con.prepareStatement("select * from FAS_VEHICLE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and VEHICLE_CODE=?");
                    ps.setInt(1,cmbAcc_UnitCode);   
                    ps.setInt(2,cmbOffice_code);
                    ps.setString(3,finyr);
                    ps.setInt(4,vehno);
                    ResultSet res=ps.executeQuery();
                    System.out.println(res);
                    if(res.next()) {
                        xml=xml+"<flag>AlreadyExist</flag>"; 
                    }
                    else
                    {
                        String sql="insert into FAS_VEHICLE_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,VEHICLE_CODE,OFFICE_IN_USE,CEILING_LIMIT,ANNUAL_ESTIMATE_NO,ANNUAL_ESTIMATE_DATE,FUEL_CEILING_QTY,FUEL_CEILING_AMT,OIL_CEILING_QTY,OIL_CEILING_AMT,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?)";
                        System.out.println("sql"+sql);
                        ps = con.prepareStatement(sql);
                        System.out.println("ps"+ps);
                        ps.setInt(1,cmbAcc_UnitCode);   
                        ps.setInt(2,cmbOffice_code);
                        ps.setString(3,finyr);
                        ps.setInt(4,vehno);System.out.println("vehno"+vehno);
                        ps.setString(5,offuse);
                        ps.setInt(6,ceillimit);
                        ps.setInt(7,annno);System.out.println("annno"+annno);
                        ps.setString(8,anndate);
                        ps.setString(9,fuelqty);
                        ps.setString(10,fuelamt);System.out.println("fuelamt"+fuelamt);
                        ps.setString(11,oilqty)                                    ;
                        ps.setString(12,oilamt);
                        ps.setString(13,txtRemarks);System.out.println("txtRemarks"+txtRemarks);
                        ps.setString(14,update_user);
                        ps.setTimestamp(15,ts);
                        ps.executeUpdate();    
                        xml=xml+"<flag>success</flag>";
                    }
                }
                catch(Exception e) 
                {
                       System.out.println("Error ****"+e.getMessage());  
                       xml=xml+"<flag>failure</flag>";
                 }
                xml=xml+"</response>";
         }              
        else if (strCommand.equalsIgnoreCase("Delete")) 
         {
                vehno=Integer.parseInt(request.getParameter("vehno"));
                finyr=(request.getParameter("finyr"));
                xml = "<response><command>Delete</command>";System.out.println(xml);
                try 
                {
                    ps = con.prepareStatement("delete from  FAS_VEHICLE_MASTER " + 
                          " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and VEHICLE_CODE=?");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setString(3,finyr);
                    ps.setInt(4,vehno);
                    ps.executeUpdate();
                    xml = xml + "<flag>success</flag>";
                }
                catch (Exception e) 
                {
                    System.out.println("catch..HERE.in load head code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
                xml = xml + "</response>";
         }
       else if (strCommand.equalsIgnoreCase("Update"))
         {
                    vehno=Integer.parseInt(request.getParameter("vehno"));
                    finyr=(request.getParameter("finyr"));
                    vehalias=(request.getParameter("vehalias"));
                    vehdesc=request.getParameter("vehdesc");
                    offuse = request.getParameter("offuse");
                    ceillimit=Integer.parseInt(request.getParameter("ceillimit"));
                    annno=Integer.parseInt(request.getParameter("annno"));
                    anndate=request.getParameter("anndate");
                    fuelqty=(request.getParameter("fuelqty"));
                    fuelamt=(request.getParameter("fuelamt"));
                    oilqty=(request.getParameter("oilqty"));
                    oilamt=(request.getParameter("oilamt"));
                    txtRemarks = request.getParameter("txtRemarks");
                    xml = "<response><command>Updated</command>";
                     try 
                     {
                        ps =  con.prepareStatement("update FAS_VEHICLE_MASTER set OFFICE_IN_USE=?,CEILING_LIMIT=?,ANNUAL_ESTIMATE_NO=?,ANNUAL_ESTIMATE_DATE=to_date(?,'dd/mm/yyyy'),FUEL_CEILING_QTY=?,FUEL_CEILING_AMT=?,"+
                                                   "OIL_CEILING_QTY=?,OIL_CEILING_AMT=?,REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and VEHICLE_CODE=?");
                        ps.setString(1,offuse);
                        ps.setInt(2,ceillimit);
                        ps.setInt(3, annno);
                        ps.setString(4,anndate);
                        ps.setString(5, fuelqty);
                        ps.setString(6, fuelamt);
                        ps.setString(7, oilqty);
                        ps.setString(8, oilamt);
                        ps.setString(9,txtRemarks);
                        ps.setInt(10, cmbAcc_UnitCode);
                        ps.setInt(11, cmbOffice_code);System.out.println(cmbOffice_code);
                        ps.setString(12, finyr);
                        ps.setInt(13,vehno);
                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                        System.out.println("here is ok");
                       }
                    catch (Exception e) 
                    {
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
