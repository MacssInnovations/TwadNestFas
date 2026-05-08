package Servlets.FAS.FAS1.TDA.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;

public class Post_TDA_List extends HttpServlet 
{
   
    public void init(ServletConfig config) throws ServletException
    {
         super.init(config);
       
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {   
         try
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
         }
         Connection con=null;
         ResultSet rs=null;
         
         PreparedStatement ps=null;
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
                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                 Class.forName(strDriver.trim());
                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
         }
         catch(Exception e)
         {
        	 System.out.println("Exception in openeing connection :"+e);

         }
         
        System.out.println("servlet called");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strType = "",xml="<response>";
        try
        {
        	     strType = request.getParameter("Command");
        }
        catch(Exception e)
        {
                     e.printStackTrace();
        }
        int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
        Date txtFrom_date=null,txtTo_date=null;
        Calendar c;
        String sql="",txtCreat_By_Module="",cmbStatus="",journal_type="";
        
        
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
        cmbStatus=request.getParameter("cmbStatus");
        
        try{journal_type=request.getParameter("journal_type");}
        catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
        
        txtCreat_By_Module=request.getParameter("txtCreat_By_Module");
        
        if(strType.equalsIgnoreCase("searchByMonth"))  
        {
	            xml="<response><command>searchByMonth</command>";                        
	            
	            if(journal_type.equalsIgnoreCase("TDAO"))
                    {
                        sql="select voucher_no,\n" + 
                            "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
                            "      particulars,\n" + 
                            "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" + 
                            "      ORGINATING_JVR_NO as payment_no,\n" + 
                            "      to_char(ORGINATING_JVR_DATE,'DD/MM/YYYY') as payment_date \n"+
                            "from fas_tda_tca_raised_mst \n" + 
                            "where    \n" + 
                            "      accounting_unit_id=? and  \n" + 
                            "      accounting_for_office_id=? and  \n" + 
                            "      cashbook_year=? and  \n" + 
                            "      cashbook_month=? and \n" + 
                            "      status=? and \n" + 
                            "      tda_or_tca=? and \n" +
                            "      advice_type='P' " + 
                            "order by\n" + 
                            "      voucher_no";
                        
                        System.out.println("SQL::::"+sql);
                        try
                        {
                                        int count=0;
                                        ps=con.prepareStatement(sql);
                                        ps.setInt(1,cmbAcc_UnitCode);
                                        ps.setInt(2,cmbOffice_code);
                                        ps.setInt(3,txtCB_Year);
                                        ps.setInt(4,txtCB_Month);
                                        ps.setString(5,cmbStatus);
                                        ps.setString(6,"TDACB");
                                       // ps.setString(7,txtMode_of_creat);
                                        xml=xml+"<flag>success</flag><jtype>TDAO</jtype><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                        "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                                        rs=ps.executeQuery();
                                        while(rs.next())
                                        {
                                                    xml=xml+"<leng>";
                                                    xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                                    xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                    xml=xml+"<payment_no>"+rs.getInt("payment_no") +"</payment_no>";
                                                    xml=xml+"<payment_date>"+rs.getString("payment_date") +"</payment_date>";
                                                    
                                                    xml=xml+"</leng>";
                                                    count++;
                                        }
                                        if(count==0) 
                                        {
                                                    System.out.println("inside count==0");
                                                    xml="<response><command>searchByMonth</command><flag>failure</flag>";
                                        }
                        }
                        catch(SQLException sqle)
                        {
                                        sqle.printStackTrace();
                                        System.out.println("error while fetching data " + sqle);
                                        xml="<response><command>searchByMonth</command><flag>failure</flag>";
                        }
                    }
                    else if(journal_type.equalsIgnoreCase("TCACB_journal"))
                    {
                        sql="select voucher_no,\n" + 
                            "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
                            "      particulars,\n" + 
                            "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" + 
                            "      ORGINATING_JVR_NO as receipt_no,\n" + 
                            "      to_char(ORGINATING_JVR_DATE,'DD/MM/YYYY') as receipt_date \n"+
                            "from fas_tda_tca_raised_mst \n" + 
                            "where    \n" + 
                            "      accounting_unit_id=? and  \n" + 
                            "      accounting_for_office_id=? and  \n" + 
                            "      cashbook_year=? and  \n" + 
                            "      cashbook_month=? and \n" + 
                            "      status=? and \n" + 
                            "      tda_or_tca=? and \n" +
                            "      advice_type='J' " + 
                            "order by\n" + 
                            "      voucher_no";
                        
                        System.out.println("SQL journal********"+sql);
                        try
                        {
                                        int count=0;
                                        ps=con.prepareStatement(sql);
                                        ps.setInt(1,cmbAcc_UnitCode);
                                        ps.setInt(2,cmbOffice_code);
                                        ps.setInt(3,txtCB_Year);
                                        ps.setInt(4,txtCB_Month);
                                        ps.setString(5,cmbStatus);
                                        ps.setString(6,"TCACB");
                                       // ps.setString(7,txtMode_of_creat);
                                        xml=xml+"<flag>success</flag><jtype>TCACB_journal</jtype><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                        "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                                        rs=ps.executeQuery();
                                        while(rs.next())
                                        {
                                                    xml=xml+"<leng>";
                                                    xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                                    xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                    xml=xml+"<receipt_no>"+rs.getInt("receipt_no") +"</receipt_no>";
                                                    xml=xml+"<receipt_date>"+rs.getString("receipt_date") +"</receipt_date>";
                                                    
                                                    xml=xml+"</leng>";
                                                    count++;
                                        }
                                        if(count==0) 
                                        {
                                                    System.out.println("inside count==0");
                                                    xml="<response><command>searchByMonth</command><flag>failure</flag>";
                                        }
                        }
                        catch(SQLException sqle)
                        {
                                        sqle.printStackTrace();
                                        System.out.println("error while fetching data " + sqle);
                                        xml="<response><command>searchByMonth</command><flag>failure</flag>";
                        }
                    }
            else if(journal_type.equalsIgnoreCase("TCACB_receipt"))
            {
                sql="select voucher_no,\n" + 
                    "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
                    "      particulars,\n" + 
                    "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" + 
                    "      ORGINATING_JVR_NO as receipt_no,\n" + 
                    "      to_char(ORGINATING_JVR_DATE,'DD/MM/YYYY') as receipt_date \n"+
                    "from fas_tda_tca_raised_mst \n" + 
                    "where    \n" + 
                    "      accounting_unit_id=? and  \n" + 
                    "      accounting_for_office_id=? and  \n" + 
                    "      cashbook_year=? and  \n" + 
                    "      cashbook_month=? and \n" + 
                    "      status=? and \n" + 
                    "      tda_or_tca=? and \n" +
                    "      advice_type='R' " + 
                    "order by\n" + 
                    "      voucher_no";
                
                System.out.println("SQL RECEIPT********"+sql);
                try
                {
                                int count=0;
                                ps=con.prepareStatement(sql);
                                ps.setInt(1,cmbAcc_UnitCode);
                                ps.setInt(2,cmbOffice_code);
                                ps.setInt(3,txtCB_Year);
                                ps.setInt(4,txtCB_Month);
                                ps.setString(5,cmbStatus);
                                ps.setString(6,"TCACB");
                               // ps.setString(7,txtMode_of_creat);
                                xml=xml+"<flag>success</flag><jtype>TCACB_receipt</jtype><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                                rs=ps.executeQuery();
                                while(rs.next())
                                {
                                            xml=xml+"<leng>";
                                            xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                            xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                            xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                            xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                            xml=xml+"<receipt_no>"+rs.getInt("receipt_no") +"</receipt_no>";
                                            xml=xml+"<receipt_date>"+rs.getString("receipt_date") +"</receipt_date>";
                                            
                                            xml=xml+"</leng>";
                                            count++;
                                }
                                if(count==0) 
                                {
                                            System.out.println("inside count==0");
                                            xml="<response><command>searchByMonth</command><flag>failure</flag>";
                                }
                }
                catch(SQLException sqle)
                {
                                sqle.printStackTrace();
                                System.out.println("error while fetching data " + sqle);
                                xml="<response><command>searchByMonth</command><flag>failure</flag>";
                }
            }
            
        }
        System.out.println("here "+strType.equalsIgnoreCase("searchByDate"));
        if(strType.equalsIgnoreCase("searchByDate"))  
        {
                    String[] sd=request.getParameter("txtFrom_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtFrom_date=new Date(d.getTime());
	            System.out.println("from_date "+txtFrom_date);
	            
	            sd=request.getParameter("txtTo_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            d=c.getTime();
	            txtTo_date=new Date(d.getTime());
	            System.out.println("txtTo_date "+txtTo_date);
	            xml="<response><command>searchByDate</command>"; 
                    
            if(journal_type.equalsIgnoreCase("TDAO"))
            {
                    sql="select voucher_no,\n" + 
                        "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
                        "      particulars,\n" + 
                        "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" + 
                        "      ORGINATING_JVR_NO as payment_no,\n" + 
                        "      to_char(ORGINATING_JVR_DATE,'DD/MM/YYYY') as payment_date \n" +
                        "from fas_tda_tca_raised_mst \n" + 
                        "where    \n" + 
                        "      accounting_unit_id=? and  \n" + 
                        "      accounting_for_office_id=? and  \n" + 
                        "      VOUCHER_DATE between ? and ? and\n" +  
                        "      status=? and\n" + 
                        "      tda_or_tca=? and \n" +
                        "      advice_type='P' " + 
                        "order by\n" + 
                        "      voucher_no";
                    
                        System.out.println("SQL::::"+sql);
                        try
                        {
                                        int count=0;
                                        ps=con.prepareStatement(sql);
                                        ps.setInt(1,cmbAcc_UnitCode);
                                        ps.setInt(2,cmbOffice_code);
                                        ps.setDate(3,txtFrom_date);
                                        ps.setDate(4,txtTo_date);
                                        ps.setString(5,cmbStatus);
                                        ps.setString(6,"TDACB");
                                        System.out.println("journal_type ::: "+journal_type);
                                       // ps.setString(7,txtMode_of_creat);
                                        xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                        "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                                        rs=ps.executeQuery();
                                        while(rs.next())
                                        {
                                                    xml=xml+"<leng>";
                                                    xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                                    xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                                    xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                                    xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                    xml=xml+"<payment_no>"+rs.getInt("payment_no") +"</payment_no>";
                                                    xml=xml+"<payment_date>"+rs.getString("payment_date") +"</payment_date>";
                                                    xml=xml+"</leng>";
                                                    count++;
                                        }
                                        if(count==0) 
                                        {
                                                    System.out.println("inside count==0");
                                                    xml="<response><command>searchByDate</command><flag>failure</flag>";
                                        }
                        }
                        catch(SQLException sqle)
                        {
                                        sqle.printStackTrace();
                                        System.out.println("error while fetching data " + sqle);
                                        xml="<response><command>searchByDate</command><flag>failure</flag>";
                        }
            }
            else if(journal_type.equalsIgnoreCase("TCACB_journal"))
            {
                sql="select voucher_no,\n" + 
                    "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
                    "      particulars,\n" + 
                    "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" + 
                    "      ORGINATING_JVR_NO as receipt_no,\n" + 
                    "      to_char(ORGINATING_JVR_DATE,'DD/MM/YYYY') as receipt_date \n" +
                    "from fas_tda_tca_raised_mst \n" + 
                    "where    \n" + 
                    "      accounting_unit_id=? and  \n" + 
                    "      accounting_for_office_id=? and  \n" + 
                    "      VOUCHER_DATE between ? and ? and\n" +  
                    "      status=? and\n" + 
                    "      tda_or_tca=? and \n" +
                    "      (advice_type='R' or advice_type='CB') " + 
                    "order by\n" + 
                    "      voucher_no";
                
                    System.out.println("SQL::::"+sql);
                    try
                    {
                                    int count=0;
                                    ps=con.prepareStatement(sql);
                                    ps.setInt(1,cmbAcc_UnitCode);
                                    ps.setInt(2,cmbOffice_code);
                                    ps.setDate(3,txtFrom_date);
                                    ps.setDate(4,txtTo_date);
                                    ps.setString(5,cmbStatus);
                                    ps.setString(6,"TCACB");
                                    System.out.println("journal_type ::: "+journal_type);
                                   // ps.setString(7,txtMode_of_creat);
                                    xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                    "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                                    rs=ps.executeQuery();
                                    while(rs.next())
                                    {
                                                xml=xml+"<leng>";
                                                xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                                xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                                xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                                xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                xml=xml+"<receipt_no>"+rs.getInt("receipt_no") +"</receipt_no>";
                                                xml=xml+"<receipt_date>"+rs.getString("receipt_date") +"</receipt_date>";
                                                xml=xml+"</leng>";
                                                count++;
                                    }
                                    if(count==0) 
                                    {
                                                System.out.println("inside count==0");
                                                xml="<response><command>searchByDate</command><flag>failure</flag>";
                                    }
                    }
                    catch(SQLException sqle)
                    {
                                    sqle.printStackTrace();
                                    System.out.println("error while fetching data " + sqle);
                                    xml="<response><command>searchByDate</command><flag>failure</flag>";
                    }
            
            } 
            else if(journal_type.equalsIgnoreCase("TCACB_receipt"))
            {
                sql="select voucher_no,\n" + 
                    "      to_char(voucher_date,'DD/MM/YYYY') as vou_date ,\n" + 
                    "      particulars,\n" + 
                    "      trim(to_char(total_amount,'99999999999999.99')) as total_amount,\n" + 
                    "      ORGINATING_JVR_NO as receipt_no,\n" + 
                    "      to_char(ORGINATING_JVR_DATE,'DD/MM/YYYY') as receipt_date \n" +
                    "from fas_tda_tca_raised_mst \n" + 
                    "where    \n" + 
                    "      accounting_unit_id=? and  \n" + 
                    "      accounting_for_office_id=? and  \n" + 
                    "      VOUCHER_DATE between ? and ? and\n" +  
                    "      status=? and\n" + 
                    "      tda_or_tca=? and \n" +
                    "      advice_type='R' " + 
                    "order by\n" + 
                    "      voucher_no";
                
                    System.out.println("SQL::::"+sql);
                    try
                    {
                                    int count=0;
                                    ps=con.prepareStatement(sql);
                                    ps.setInt(1,cmbAcc_UnitCode);
                                    ps.setInt(2,cmbOffice_code);
                                    ps.setDate(3,txtFrom_date);
                                    ps.setDate(4,txtTo_date);
                                    ps.setString(5,cmbStatus);
                                    ps.setString(6,"TCACB");
                                    System.out.println("journal_type ::: "+journal_type);
                                   // ps.setString(7,txtMode_of_creat);
                                    xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
                                    "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
                                    rs=ps.executeQuery();
                                    while(rs.next())
                                    {
                                                xml=xml+"<leng>";
                                                xml=xml+"<vou_no>"+rs.getInt("voucher_no")+"</vou_no>";
                                                xml=xml+"<vou_date>"+rs.getString("vou_date")+"</vou_date>";
                                                xml=xml+"<Remak><![CDATA["+rs.getString("particulars")+"]]></Remak>";
                                                xml=xml+"<Tot_Amt>"+rs.getString("total_amount") +"</Tot_Amt>";
                                                xml=xml+"<receipt_no>"+rs.getInt("receipt_no") +"</receipt_no>";
                                                xml=xml+"<receipt_date>"+rs.getString("receipt_date") +"</receipt_date>";
                                                xml=xml+"</leng>";
                                                count++;
                                    }
                                    if(count==0) 
                                    {
                                                System.out.println("inside count==0");
                                                xml="<response><command>searchByDate</command><flag>failure</flag>";
                                    }
                    }
                    catch(SQLException sqle)
                    {
                                    sqle.printStackTrace();
                                    System.out.println("error while fetching data " + sqle);
                                    xml="<response><command>searchByDate</command><flag>failure</flag>";
                    }
            
            } 
        }
        xml=xml+"</response>";   
        out.println(xml); 
        System.out.println(xml); 
    }
}
