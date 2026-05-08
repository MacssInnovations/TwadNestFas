package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Land_details extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        

    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        try
        {
    	   ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
           String ConnectionString="";

           String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
           String strdsn=rs.getString("Config.DSN");
           String strhostname=rs.getString("Config.HOST_NAME");
           String strportno=rs.getString("Config.PORT_NUMBER");
           String strsid=rs.getString("Config.SID");
           String strdbusername=rs.getString("Config.USER_NAME");
           String strdbpassword=rs.getString("Config.PASSWORD");
              
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

           Class.forName(strDriver.trim());
           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           try
           {
        	   statement=connection.createStatement();
               connection.clearWarnings();
           }
           catch(SQLException e)
           {
               System.out.println("Exception in creating statement:"+e);
           }
        }
        catch(Exception e)
        {
           System.out.println("Exception in openeing connection:"+e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
        int unit_id = 0;
        int office_id = 0;
	   	String txtvillage = null;
	   	String financial_year = null;
	   	int asset_code = 0;
	   	int txtBP_No  = 0;
	   	int txttotal_amount  = 0;
                int txtlease  =  0;
                String txtvoucher_date  = "";
             //   String txtdetails  = "";
                String txtsurvey_date="";
                String txtdoc_date="",txtregister_office="";
                String titledetail="",txttaluk="",txtnorth="",txtSouth="",txtEast="";
                String txtsurvey_no  = "";
                int txtauction_amt  = 0;
                int cb_vrno  =  0,txtpaid_buildings=0,txtvoucher_no=0;
               
                String cmbacqn="",txtproject_name="";
	   	int txt_profit = 0;
	   	int txtpaid_land = 0;
	   	int txtext_area=0;
	   	int txt_loss = 0;
	   	int txtoff_debit  = 0;
	   	int txtoff_credit  = 0;
	   	int  txtjournal_vno2   =0;
                String txtdoc_no="";
                java.sql.Date cbdate=null;
                java.sql.Date doc_date=null; 
                java.sql.Date voucher_date=null;
                java.sql.Date journal_date2=null;
                java.sql.Date survey_date=null;
	   	String remarks = "",txtowner="",txtWest="",cmblandtype="",txtfound="";
	   	
	   	
        HttpSession session=request.getSession(false);
        try
        {
	        if(session==null)
	        {
	            System.out.println(request.getContextPath()+"/index.jsp");
	            response.sendRedirect(request.getContextPath()+"/index.jsp");
	        }
	        System.out.println(session);
        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
        
        
        String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      
        try
        {
        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
        	System.out.println("accounting_unit_id : " + unit_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
        }       
        try
        {
                txtdoc_no = request.getParameter("txtdoc_no");
                System.out.println("txtdoc_no : " + txtdoc_no);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
        }        
        try
        {
                txtext_area = Integer.parseInt(request.getParameter("txtext_area"));
                System.out.println("txtext_area : " + txtext_area);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
        }     
        try
        {
        	office_id = Integer.parseInt(request.getParameter("office_id"));
        	System.out.println("accounting_unit_office_id : " + office_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
        }     
        txtvoucher_date = request.getParameter("txtvoucher_date");
     
        txtsurvey_date = request.getParameter("txtsurvey_date");
        txtdoc_date = request.getParameter("txtdoc_date");
        titledetail = request.getParameter("txtdetails");
        System.out.println("titledetail"+titledetail);
        
        try
        {
        	financial_year = request.getParameter("financial_year");
        	System.out.println("financial_year : " + financial_year);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
        }     
        
        try
        {
        	asset_code = Integer.parseInt(request.getParameter("asset_code"));
        	System.out.println("asset_code : " + asset_code);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'asset_code' parameter ===> " + e);
        }        
        try
        {
                txtBP_No = Integer.parseInt(request.getParameter("txtBP_No"));
                System.out.println("txtBP_No : " + txtBP_No);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtBP_No' parameter ===> " + e);
        }    
           
        
        try
        {
        	txtpaid_land = Integer.parseInt(request.getParameter("txtpaid_land"));
        	System.out.println("txtpaid_land : " + txtpaid_land);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtpaid_land' parameter ===> " + e);
        }        
        try
        {
                txttotal_amount = Integer.parseInt(request.getParameter("txttotal_amount"));
                System.out.println("txttotal_amount : " + txttotal_amount);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txttotal_amount' parameter ===> " + e);
        }    
        try
        {
                txtlease = Integer.parseInt(request.getParameter("txtlease"));
                System.out.println("txtlease : " + txtlease);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtlease' parameter ===> " + e);
        } 
        
        try
        {
        	txtsurvey_no = request.getParameter("txtsurvey_no");
        	System.out.println("txtsurvey_no : " + txtsurvey_no);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtsurvey_no' parameter ===> " + e);
        }   
       
        try
        {
                txtowner = request.getParameter("txtowner");
                System.out.println("txtowner : " + txtowner);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtowner' parameter ===> " + e);
        }  
        try
        {
                txtpaid_buildings = Integer.parseInt(request.getParameter("txtpaid_buildings"));
                System.out.println("txtpaid_buildings : " + txtpaid_buildings);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtpaid_buildings' parameter ===> " + e);
        }
        
	
       
        
   try{
        if(!txtvoucher_date.equalsIgnoreCase(""))
        { System.out.println("inside");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            java.util.Date d1=dateFormat1.parse(txtvoucher_date);
            dateFormat1.applyPattern("yyyy-MM-dd");
            txtvoucher_date=dateFormat1.format(d1);
        
        }catch(Exception e)
        {
             e.printStackTrace();
        }
        voucher_date=Date.valueOf(txtvoucher_date);
        System.out.println("From date : "+voucher_date);
        }
    
        if(!txtdoc_date.equalsIgnoreCase(""))
        {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            java.util.Date d1=dateFormat1.parse(txtdoc_date);
            dateFormat1.applyPattern("yyyy-MM-dd");
            txtdoc_date=dateFormat1.format(d1);
        
        }catch(Exception e)
        {
             e.printStackTrace();
        }
        doc_date=Date.valueOf(txtdoc_date);
        System.out.println("doc_date : "+doc_date);
        }
      
      
   }   catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'Date' parameter ===> " + e);
        } 
      
        try
        {
                remarks = request.getParameter("remarks");
                System.out.println("remarks : " + remarks);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'remarks' parameter ===> " + e);
        }
        try
        {
                txtregister_office = request.getParameter("txtregister_office");
                System.out.println("txtregister_office : " + txtregister_office);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtregister_office' parameter ===> " + e);
        }  
        try
        {
        	txttaluk = request.getParameter("txttaluk");
        	System.out.println("taluks : " + txttaluk);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txttaluk' parameter ===> " + e);
        }     
        try
        {
                txtvillage = request.getParameter("txtvillage");
                System.out.println("txtvillage : " + txtvillage);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtvillage' parameter ===> " + e);
        } 
        try
        {
                cmbacqn = request.getParameter("cmbacqn");
                System.out.println("cmbacqn : " + cmbacqn);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'cmbacqn' parameter ===> " + e);
        } 
        try
        {
                txtnorth = request.getParameter("txtnorth");
                System.out.println("txtnorth : " + txtnorth);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtnorth' parameter ===> " + e);
        } 
        try
        {
                txtSouth = request.getParameter("txtSouth");
                System.out.println("txtSouth : " + txtSouth);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtnorth' parameter ===> " + e);
        } 
        try
        {
                txtEast = request.getParameter("txtEast");
                System.out.println("txtEast : " + txtEast);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtEast' parameter ===> " + e);
        } 
        try
        {
                txtWest = request.getParameter("txtWest");
                System.out.println("txtWest : " + txtWest);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtWest' parameter ===> " + e);
        } 
        try
        {
                cmblandtype = request.getParameter("cmblandtype");
                System.out.println("cmblandtype : " + cmblandtype);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'cmblandtype' parameter ===> " + e);
        }
        try
        {
                txtfound = request.getParameter("txtfound");
                System.out.println("txtfound : " + txtfound);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtfound' parameter ===> " + e);
        }
        
        try
        {
                txtproject_name = request.getParameter("txtproject_name");
                System.out.println("txtproject_name : " + txtproject_name);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'txtproject_name' parameter ===> " + e);
        }     
        
        
        /*
         * Execute the task adhering to the requested command
         */
        //////////////////////////////// if else - STARTS ///////////////////////////////////////////////
        if(strCommand.equalsIgnoreCase("Delete"))
        {
        	System.out.println("\n*************\nDelete\n**************\n");
            xml="<response><command>Delete</command>";
            try 
            {
             String sqlDelete = "DELETE FROM FAS_LAND_DETAILS " +
								"WHERE accounting_unit_id = ? " +
								" AND accounting_unit_office_id = ? " +
								" AND financial_year = ? " +
								" AND asset_code = ? ";
             PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setInt(1, unit_id);
		   	 ps.setInt(2, office_id);
		   	 ps.setString(3, financial_year);
		   	 ps.setInt(4, asset_code);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<accounting_unit_id>" + unit_id + "</accounting_unit_id>";
             xml=xml+"<accounting_unit_office_id>" + office_id + "</accounting_unit_office_id>";
             xml=xml+"<financial_year>" + financial_year + "</financial_year>";
             xml=xml+"<asset_code>" + asset_code + "</asset_code>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Deleting record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
               
        else if(strCommand.equalsIgnoreCase("Update"))
        {
        	System.out.println("\n*************\nUpdate\n**************\n");
            xml="<response><command>Update</command>";
            try 
            {
             String sqlUpdate = "UPDATE FAS_LAND_DETAILS SET SUB_LEDGER_CODE=?,BP_NO=?,TALUK=?,VILLAGE=?,NATURE_ACQN=?,BOUND_NORTH=?,BOUND_SOUTH=?,BOUND_EAST=?," + 
                "BOUND_WEST=?,LAND_TYPE=?,EXTENT_AREA=?,SURVEY_NO=?,FOUNDN_TYPE=?,OWNER_NAME=?,LEASE_PERIOD=?, " + 
                "AMOUNT_LAND=?,AMOUNT_BUILDINGS=?,TOTAL_AMOUNT=?,VOUCHER_NO=?,VOUCHER_DATE=?,REG_OFFICE=?," + 
                "REG_DOCNO=?,REG_DATE=?,TITLE_DETAILS=?,REMARKS=?,"+
		"UPDATED_BY_USERID=?,UPDATED_DATE=? " + 
		"WHERE accounting_unit_id = ? AND accounting_unit_office_id = ? " +
	        "AND financial_year = ?  AND asset_code = ? ";
		 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
                ps.setString(1,txtproject_name); 
                ps.setInt(2,txtBP_No);
                ps.setString(3,txttaluk);
                ps.setString(4,txtvillage);
                ps.setString(5,cmbacqn);
                ps.setString(6,txtnorth);
                ps.setString(7,txtSouth);
                ps.setString(8,txtEast);
                ps.setString(9,txtWest);
                ps.setString(10,cmblandtype);
                ps.setInt(11,txtext_area);
                ps.setString(12,txtsurvey_no);
                ps.setString(13,txtfound);
                ps.setString(14,txtowner);
                ps.setInt(15,txtlease);
                ps.setInt(16,txtpaid_land);
                ps.setInt(17,txtpaid_buildings);
                ps.setInt(18,txttotal_amount);
                ps.setInt(19,txtvoucher_no);
                ps.setDate(20,voucher_date);
                ps.setString(21,txtregister_office);
                ps.setString(22,txtdoc_no);
                ps.setDate(23,doc_date);
                ps.setString(24,titledetail);
                         
                ps.setString(25,remarks);
                ps.setString(26,userid);
                ps.setTimestamp(27,ts);
                ps.setInt(28,unit_id);
                ps.setInt(29,office_id);
                ps.setString(30,financial_year);
                ps.setInt(31,asset_code);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
	
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Updating record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
                
     else if(strCommand.equalsIgnoreCase("Add"))
        {
        	System.out.println("\n*************\nAdd\n**************\n");
            xml="<response><command>Add</command>";

            try 
            {
             String sqlAdd = "INSERT INTO FAS_LAND_DETAILS" +
                "(SUB_LEDGER_CODE,BP_NO,TALUK,VILLAGE,NATURE_ACQN,BOUND_NORTH,BOUND_SOUTH,BOUND_EAST, \n" + 
                "BOUND_WEST,LAND_TYPE,EXTENT_AREA,SURVEY_NO,FOUNDN_TYPE,OWNER_NAME,LEASE_PERIOD, \n" + 
                "AMOUNT_LAND,AMOUNT_BUILDINGS,TOTAL_AMOUNT,VOUCHER_NO,VOUCHER_DATE,REG_OFFICE , \n" + 
                "REG_DOCNO,REG_DATE,TITLE_DETAILS,REMARKS,UPDATED_BY_USERID,UPDATED_DATE , \n" + 
                "accounting_unit_id,accounting_unit_office_id,financial_year,asset_code,SUB_LEDGER_TYPE_CODE) "  +
	   	" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	 	 PreparedStatement ps = connection.prepareStatement(sqlAdd);
                ps.setString(1,txtproject_name); 
                ps.setInt(2,txtBP_No);
                ps.setString(3,txttaluk);
                ps.setString(4,txtvillage);
                ps.setString(5,cmbacqn);
                ps.setString(6,txtnorth);
                ps.setString(7,txtSouth);
                ps.setString(8,txtEast);
                ps.setString(9,txtWest);
                ps.setString(10,cmblandtype);
                ps.setInt(11,txtext_area);
                ps.setString(12,txtsurvey_no);
                ps.setString(13,txtfound);
                ps.setString(14,txtowner);
                ps.setInt(15,txtlease);
                ps.setInt(16,txtpaid_land);
                ps.setInt(17,txtpaid_buildings);
                ps.setInt(18,txttotal_amount);
                ps.setInt(19,txtvoucher_no);
                ps.setDate(20,voucher_date);
                ps.setString(21,txtregister_office);
                ps.setString(22,txtdoc_no);
                ps.setDate(23,doc_date);
                ps.setString(24,titledetail);
                         
                ps.setString(25,remarks);
                ps.setString(26,userid);
                ps.setTimestamp(27,ts);
                ps.setInt(28,unit_id);
                ps.setInt(29,office_id);
                ps.setString(30,financial_year);
                ps.setInt(31,asset_code);
                ps.setInt(32,10);
               
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
           
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Adding record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } 
       else if(strCommand.equals("Get"))
        { 
        	System.out.println("\n*************\nGet\n**************\n");
            xml="<response><command>Get</command>";
            try 
            {
                System.out.println("bef res");
              System.out.println(unit_id);
              System.out.println(office_id);
              System.out.println(financial_year);
              System.out.println(asset_code);
             result = statement.executeQuery("select SUB_LEDGER_CODE,BP_NO,TALUK,VILLAGE,NATURE_ACQN,BOUND_NORTH,BOUND_SOUTH,BOUND_EAST," + 
             "BOUND_WEST,LAND_TYPE,EXTENT_AREA,SURVEY_NO,FOUNDN_TYPE,OWNER_NAME,LEASE_PERIOD," + 
             "AMOUNT_LAND,AMOUNT_BUILDINGS,TOTAL_AMOUNT,VOUCHER_NO,to_char(VOUCHER_DATE,'dd/mm/yyyy') as VOUCHER_DATE,REG_OFFICE ," + 
             "REG_DOCNO,to_char(REG_DATE,'dd/mm/yyyy')as REG_DATE,TITLE_DETAILS,REMARKS" + 
             " from FAS_LAND_DETAILS " +
             "WHERE accounting_unit_id = "+unit_id +" AND accounting_unit_office_id = "+ office_id +" AND " +
             "financial_year = '" + financial_year + "'  AND asset_code = " + asset_code+" ");
             
                System.out.println("aft res");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	          xml=xml+"<txtproject_name>"+result.getString("SUB_LEDGER_CODE")+"</txtproject_name>";
                                  xml=xml+"<txtBP_No>" + result.getInt("BP_NO") + "</txtBP_No>";
                                  xml=xml+"<txttaluk>"+result.getString("taluk")+"</txttaluk>";
                                  xml=xml+"<txtvillage>" + result.getString("VILLAGE") + "</txtvillage>";
                                  xml=xml+"<cmbacqn>" +result.getString("NATURE_ACQN") + "</cmbacqn>";
                                  xml=xml+"<txtnorth>" + result.getString("BOUND_NORTH") + "</txtnorth>";
                                  xml=xml+"<txtSouth>" + result.getString("BOUND_SOUTH") + "</txtSouth>";
                                  xml=xml+"<txtEast>" + result.getString("BOUND_EAST") + "</txtEast>";
                                  xml=xml+"<txtWest>"+result.getString("BOUND_WEST")+"</txtWest>";
                                  xml=xml+"<cmblandtype>" + result.getString("LAND_TYPE") + "</cmblandtype>";
                                  xml=xml+"<txtext_area>" + result.getInt("EXTENT_AREA") + "</txtext_area>";
                                  xml=xml+"<txtsurvey_no>" + result.getString("SURVEY_NO") + "</txtsurvey_no>";
                                  xml=xml+"<txtfound>"+ result.getString("FOUNDN_TYPE") + "</txtfound>";
                                  xml=xml+"<txtowner>" + result.getString("OWNER_NAME") + "</txtowner>";
                                  xml=xml+"<txtlease>" + result.getInt("LEASE_PERIOD") + "</txtlease>";
                                  xml=xml+"<txtpaid_land>" + result.getInt("AMOUNT_LAND") + "</txtpaid_land>";
                                  xml=xml+"<txtpaid_buildings>" + result.getInt("AMOUNT_BUILDINGS") + "</txtpaid_buildings>";
                                  xml=xml+"<txttotal_amount>" + result.getInt("TOTAL_AMOUNT") + "</txttotal_amount>";
                                  xml=xml+"<txtvoucher_no>" + result.getInt("VOUCHER_NO") + "</txtvoucher_no>";
                                  
                                  xml=xml+"<voucher_date>" + result.getString("VOUCHER_DATE")+ "</voucher_date>";
                                  xml=xml+"<txtregister_office>" + result.getString("REG_OFFICE") + "</txtregister_office>";
                                  xml=xml+"<txtdoc_no>" + result.getString("REG_DOCNO") + "</txtdoc_no>";
                                //  xml=xml+"<remarks>" + result.getString("REMARKS") + "</remarks>";
                                  xml=xml+"<txtdoc_date>" + result.getString("REG_DATE") + "</txtdoc_date>";
                                  xml=xml+"<txtdetails>" + result.getString("TITLE_DETAILS") + "</txtdetails>";
                                  
                	 remarks = result.getString("remarks");
                	 if(remarks == null)
                	 {
                		 remarks = " ";
                	 }
                	 xml += "<remarks>" + remarks + "</remarks>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from DB - GET: " + e);
             }
             
             result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } 
        
        
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
        
    }
}
