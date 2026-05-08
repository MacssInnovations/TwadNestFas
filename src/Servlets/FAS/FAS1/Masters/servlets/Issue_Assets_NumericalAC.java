package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.sql.Types;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import sun.util.calendar.Gregorian;

public class Issue_Assets_NumericalAC extends HttpServlet {
    private static final String CONTENT_TYPE = 
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, 
                                                           IOException {
     //  System.out.println("inside servelet");
        HttpSession session = request.getSession(false);
     
        try {

            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        Connection con = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null,rss1=null,rss2=null,rss3=null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps1 = null;
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
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
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), 
                            strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);

        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
           e.printStackTrace();
        }
        System.out.println("servelets");
        
        String xml = "";
    //    int count=0;
        int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCB_Year=0,txtCB_Month=0,cmbmajorclass=0,cmbmajorclass1=0;
        int cmbassetcode=0,txtOffice_Name=0,txtqtyissued=0,cmbjournalno=0,txtvalueissued=0;
        
        String cmbFinancialYear="",levelid="",txtRemarks="",txtassetdesc="",issue_date1="",txtjournal_date1="";
        Date issue_date = null,txtjournal_date=null;
        Calendar c;
        double Amc_Amount=0; 
          int recflag=0,CashBookYear=0,CashBookMonth=0;

        
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        try {
        	cmbmajorclass = Integer.parseInt(request.getParameter("cmbmajorclass"));
        	
        } catch (Exception e) {
           e.printStackTrace();
        }
     
        try {
        	cmbmajorclass1 = Integer.parseInt(request.getParameter("cmbassetclass"));
        	
        } catch (Exception e) {
           e.printStackTrace();
        }
        try {
        	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (Exception e) {
           e.printStackTrace();
        }
      
        levelid=request.getParameter("levelid");       
        txtRemarks = request.getParameter("txtRemarks");
        cmbFinancialYear=request.getParameter("cmbFinancialYear");
        txtassetdesc=request.getParameter("txtassetdesc");
        issue_date1=request.getParameter("issue_date");
        txtjournal_date1=request.getParameter("txtjournal_date");
        try {
        	txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));           
        } catch (Exception e){
            e.printStackTrace();
          
        }
        try{
        	txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));            
        } catch (Exception e){
           e.printStackTrace();
            
        }
        try        {
        	cmbassetcode = Integer.parseInt(request.getParameter("cmbassetcode"));	
        }catch(Exception e){
        	e.printStackTrace();
        }
        try{
        	txtOffice_Name = Integer.parseInt(request.getParameter("txtOffice_Name"));
        }catch(Exception e){
        	e.printStackTrace();
        }
        try{
        	txtqtyissued = Integer.parseInt(request.getParameter("txtqtyissued"));
        	txtvalueissued = Integer.parseInt(request.getParameter("txtvalueissued"));
        }catch(Exception e){
        	e.printStackTrace();
        }
        try{
        	cmbjournalno = Integer.parseInt(request.getParameter("cmbjournalno"));
        }catch(Exception e){
        	e.printStackTrace();
        }
       /* System.out.println("cmbAcc_UnitCode=="+cmbAcc_UnitCode);
        System.out.println("cmbOffice_code==="+cmbOffice_code);
        System.out.println("cmbmajorclass===="+cmbmajorclass);
        System.out.println("levelid=========="+levelid);
        System.out.println("txtRemarks======="+txtRemarks);
        System.out.println("cmbFinancialYear="+cmbFinancialYear);
        System.out.println("txtassetdesc====="+txtassetdesc);
        System.out.println("txtCB_Year======="+txtCB_Year);
        System.out.println("txtCB_Month======"+txtCB_Month);
        System.out.println("cmbassetcode====="+cmbassetcode);
        System.out.println("txtOffice_Name==="+txtOffice_Name);
        System.out.println("txtqtyissued===   txtvalueissued  =="+txtqtyissued+"  "+txtvalueissued);
        System.out.println("cmbjournalno====="+cmbjournalno);*/
        
        if (strCommand.equalsIgnoreCase("Add")) {

          
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            //String xml = "";
            xml = "<response><command>Add</command>";
            System.out.println("add");          
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
      
            System.out.println("ts"+ts);
                              /* try 
                                        {
                                            String sqlsel="select decode(max(ASSET_CODE),null,0,max(ASSET_CODE))as ASSET_CODE from FAS_ASSETS_NUM_OB";    
                                            ps2=con.prepareStatement(sqlsel);
                                            System.out.println("max ASSET_CODE no..."+sqlsel);
                                            rs2=ps2.executeQuery();
                                            if(rs2.next())
                                            {
                                            	cmbassetcode=rs2.getInt("ASSET_CODE");
                                            }
                                                cmbassetcode=cmbassetcode+1;
                                                System.out.println("Maximum value of assetcode is :"+cmbassetcode);
                                                ps2.close();
                                                rs2.close();
                                        }
                                        catch(Exception e11)
                                        {
                                           e11.printStackTrace();
                                        } 
                               try 
                                        {
                                           String sqlselect="select * from FAS_ASSETS_NUM_OB where ASSET_CODE=?";
                                            ps2=con.prepareStatement(sqlselect);
                                            ps2.setInt(1,cmbassetcode);
                                            rs2=ps2.executeQuery();
                                            if(rs2.next())
                                               {
                                                   recflag++;
                                               }
                                            ps2.close();
                                            
                                        }
                                       catch(Exception e11)
                                       {
                                          e11.printStackTrace();
                                       }
                                   
                                   if(recflag>0) 
                                   {
                                      System.out.println("Record already exist"); 
                                      xml=xml+"<flag>record</flag>";
                                   }
          else */
              


            try {

                ps =  con.prepareStatement("insert into FAS_ISSUEOF_ASSETS (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,CASHBOOK_YEAR,CASHBOOK_MONTH,ASSET_MAJOR_CLASS_CODE,ASSET_CODE,ISSUE_DATE,ISSUED_TO_LEVEL_ID,ISSUED_TO_OFFICE,QTY_ISSUED,REMARKS,TDA_ORIGINATING_JVR_NO,TDA_ORIGINATING_JVR_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,ASSET_DESC,VALUE_ISSUED)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setString(3, cmbFinancialYear);
                ps.setInt(4, txtCB_Year);
                ps.setInt(5, txtCB_Month);
                ps.setInt(6, cmbmajorclass);
                ps.setInt(7, cmbassetcode);
                if(issue_date1.equals(""))
                    ps.setNull(8,Types.DATE);
                else
                {
                    String[] sd2 =issue_date1.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    issue_date = new Date(d2.getTime());
                    System.out.println("date " + issue_date);
                    ps.setDate(8, issue_date);    
                }
                ps.setString(9, levelid);
                ps.setInt(10, txtOffice_Name);
                ps.setInt(11, txtqtyissued);
                ps.setString(12, txtRemarks);
                ps.setInt(13, cmbjournalno);
                if(txtjournal_date1.equals(""))
                    ps.setNull(14,Types.DATE);
                else
                {
                    String[] sd1 = txtjournal_date1.split("/");
                    c = 
                    new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
                                Integer.parseInt(sd1[0]));
                    java.util.Date d1 = c.getTime();
                    txtjournal_date = new Date(d1.getTime());
                    System.out.println("quantity_date " + txtjournal_date); 
                    ps.setDate(14,txtjournal_date);
                }
                
                ps.setString(15,update_user);
                ps.setTimestamp(16,ts);
                ps.setString(17,txtassetdesc);
                ps.setInt(18, txtvalueissued);
                ps.executeUpdate();
                /*if(cc>0){
                	  xml = xml + "<flag>success</flag>";	
                }*/
                /*if(ps.executeUpdate()>0)
                {
                	ps = con.prepareStatement("delete from  FAS_ASSETS_NUM_OB where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and ASSET_CODE="+cmbassetcode+" and FINANCIAL_YEAR='"+cmbFinancialYear+"'");
                	ps.executeQuery();
                }
                else
                {
                	System.out.println("Not Successfuly Inserted and deleted");
                }*/
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
             

            } catch (SQLException e) {
               e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
           
        }  else if (strCommand.equalsIgnoreCase("checkStatus")) {
        	int c1=0,c2=0,c3=0;
            xml = "<response><command>checkStatus</command>";
            
            String[] divyear=cmbFinancialYear.split("-");
        	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
        	
            //System.out.println("checkStatus");
            try {
            	int cricleID=0;
            	int cricleuintid=0;
            	/*String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
            	  ps =  con.prepareStatement(selectCircleID);
                  ps.setInt(1, cmbOffice_code);
                  rss1=ps.executeQuery();
                  while(rss1.next()){
                	  cricleID=rss1.getInt("CIRCLE_OFFICE_ID");	  
                  }*/
            	String selectCricleunitid="select ACCT_RENDERING_UNIT_ID from FAS_ASSET_VAL_AC_RENDER_UNITS where STATUS='L' and  ACCT_UNIT_ID_RENDERED_FOR=? and RENDERING_UNIT_OFFICE_ID=?";
            	
            	PreparedStatement pss0=con.prepareStatement(selectCricleunitid);
                pss0.setInt(1, cmbAcc_UnitCode);
                pss0.setInt(2, cmbOffice_code);
                ResultSet rss0=pss0.executeQuery();
                if(rss0.next()){
                	cricleuintid=rss0.getInt("ACCT_RENDERING_UNIT_ID");	
                	
                }
                //  System.out.println("circle Id "+cricleID);
               // System.out.println("cricleuintid "+cricleuintid);
                String selectCircleID="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?";
          	  ps =  con.prepareStatement(selectCircleID);
                ps.setInt(1, cricleuintid);
                rss1=ps.executeQuery();
                if(rss1.next()){
              	  cricleID=rss1.getInt("ACCOUNTING_UNIT_OFFICE_ID");	
              	  

                }
                 // System.out.println("circle Id "+cricleID);
            	String searchquery="select A52_STATUS_VAL from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR='"+newyear+"' ";//and finanical_year='"+cmbFinancialYear+"' 
                ps1 =  con.prepareStatement(searchquery);
                ps1.setInt(1, cricleID);
               // ps1.setString(2, newyear);
                rss2=ps1.executeQuery();
               // System.out.println(" searchquery  "+searchquery);
                if(rss2.next()){
    				xml = xml + "<flag>freezeCricle</flag>";
    				System.out.println("freezeCricle   ");
    				
                }else{
                	xml = xml + "<flag>notfreezeCricle</flag>";
                } 
            	/*String searchquery="select A52_STATUS from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and finanical_year='"+newyear+"' ";
            	
            	//FAS_ASSETS_NUM_OB_EDIT  before 
                ps =  con.prepareStatement(searchquery);
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                c1=ps.executeUpdate();
                if(c1>0){
    				rss1=ps.executeQuery();
    				xml = xml + "<flag>freezeoffice</flag>";
    				System.out.println("freezeoffice   ");
    				while(rss1.next()){
    					
    					String STATUS1=rss1.getString("A52_STATUS");
    					if(STATUS1.equalsIgnoreCase("Y")){
    						
    						String findingintab="select acct_rendering_unit_id,rendering_unit_office_id,ACCT_UNIT_ID_RENDERED_FOR from fas_asset_val_ac_render_units where rendering_unit_office_id="+newyear;
    						ps2=con.prepareStatement(findingintab);
    						c2=ps2.executeUpdate();
    						if(c2>0){
    							rss2=ps2.executeQuery();
    							while(rss2.next()){
    								
    								int cricleunitid=rss2.getInt("acct_rendering_unit_id");
    								//System.out.println("statusoffice "+statusoffice);
    								String searchquery1="select A52_STATUS from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_UNIT_ID=? and finanical_year='"+newyear+"' and A52_STATUS='Y'";
    					            ps =  con.prepareStatement(searchquery1);
    					            ps.setInt(1, cricleunitid);
    					            //ps.setInt(2, cmbOffice_code);
    					            c3=ps.executeUpdate();
    					            if(c3>0){
    									rss3=ps.executeQuery();
    									xml = xml + "<flagstatus>freezecricle</flagstatus>";	
    					            }else{
    					            	xml = xml + "<flagstatus>notfreezecricle</flagstatus>";	
    					            }
    							}
    							
    						}else{
    							
    						}	
    					}else{
    						System.out.println("not update status ");
    					}
    					
    				}
                }else{
                	xml = xml + "<flag>notfreezeoffice</flag>";
                }
                
                System.out.println("freezed "); */

            } catch (SQLException e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
           
        } 
        else if (strCommand.equalsIgnoreCase("Update")) {
        	
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
          
            xml = "<response><command>Update</command>";
           // System.out.println("inside update command");
            
            try {
                ps =  con.prepareStatement("update FAS_ISSUEOF_ASSETS set ISSUE_DATE=?, ISSUED_TO_LEVEL_ID=?, ISSUED_TO_OFFICE=?, QTY_ISSUED=?,REMARKS=?,TDA_ORIGINATING_JVR_NO=?,TDA_ORIGINATING_JVR_DATE=?,ASSET_DESC=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,VALUE_ISSUED=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ASSET_MAJOR_CLASS_CODE=? and FINANCIAL_YEAR=? and ASSET_CODE=?");
               
               
                if(issue_date1.equals(""))
                    ps.setNull(1,Types.DATE);
                else
                {
                    String[] sd2 =issue_date1.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    issue_date = new Date(d2.getTime());
                    System.out.println("issue_date " + issue_date);
                    ps.setDate(1, issue_date);    
                }
                           
                ps.setString(2, levelid);
                System.out.println("levelid==="+levelid);
                ps.setInt(3,txtOffice_Name);
                ps.setInt(4, txtqtyissued);
                ps.setString(5, txtRemarks);
                ps.setInt(6, cmbjournalno);
                System.out.println("txtjournal_date1 "+txtjournal_date1);
                if(txtjournal_date1.equals("--"))
                    ps.setNull(7,Types.DATE);
                else
                {
                    String[] sd1 = txtjournal_date1.split("/");
                    c = 
                    new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
                                Integer.parseInt(sd1[0]));
                    java.util.Date d1 = c.getTime();
                    txtjournal_date = new Date(d1.getTime());
                    System.out.println("txtjournal_date " + txtjournal_date); 
                    ps.setDate(7,txtjournal_date);
                }
                ps.setString(8, txtassetdesc);
                ps.setString(9, update_user);
                ps.setTimestamp(10 , ts);  
                ps.setInt(11, txtvalueissued);
                ps.setInt(12, cmbAcc_UnitCode);
                ps.setInt(13, cmbOffice_code);
                ps.setInt(14, txtCB_Year);
                ps.setInt(15, txtCB_Month);
                ps.setInt(16, cmbmajorclass);
                ps.setString(17,cmbFinancialYear);
                ps.setInt(18, cmbassetcode);
                
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
              }
              catch (Exception e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
           
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
           
        }  else if (strCommand.equalsIgnoreCase("Delete")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            System.out.println(strCommand+" cmbassetcode "+cmbassetcode);
            xml = "<response><command>Delete</command>";

            try {
                ps = con.prepareStatement("delete from  FAS_ISSUEOF_ASSETS  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ASSET_MAJOR_CLASS_CODE=? and ASSET_CODE=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setString(3,cmbFinancialYear);
                ps.setInt(4, txtCB_Year);
                ps.setInt(5, txtCB_Month);
                ps.setInt(6, cmbmajorclass);
                ps.setInt(7, cmbassetcode);
                int pp=ps.executeUpdate();
                if(pp>0)
                xml = xml + "<flag>success</flag>";
                else
                	 xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        else if(strCommand.equalsIgnoreCase("loadAssetCode2"))
        {       
        	System.out.println(cmbAcc_UnitCode);
          	System.out.println(cmbOffice_code);
          	System.out.println(cmbmajorclass);
          	System.out.println(cmbFinancialYear);
        	    xml="<response>"; 
                xml=xml+"<command>loadAssetCode</command>";
                
                try{
                	String[] divyear=cmbFinancialYear.split("-");
                	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
                //	System.out.println("sssggggsdrgsdg......");
                	//String qry="select A.ASSET_CODE, A.PARTICULARS from FAS_ASSET_VAL_AC_DETAILS A inner join FAS_ASSET_CLASSIFICATION B on B.ASSET_TYPE_CODE=A.ASSET_TYPE_CODE WHERE A.ACCOUNTING_UNIT_ID=? and A.ACCOUNTING_UNIT_OFFICE_ID=? and B.ASSET_MAJOR_CLASS_CODE=? and A.FINANCIAL_YEAR=?";
                	//String qry="SELECT A.ASSET_CODE as ASSET_CODE,A.PARTICULARS as PARTICULARS FROM FAS_ASSET_VAL_AC_DETAILS A WHERE a.accounting_unit_id =? AND A.accounting_unit_office_id= ? AND A.asset_major_class_code   = ? AND a.financial_year =?";
                	String qry="SELECT A.ASSET_CODE as ASSET_CODE,A.PARTICULARS as PARTICULARS FROM FAS_A52_REGISTER A WHERE A.accounting_unit_office_id= ? AND A.asset_major_class_code   = ? AND a.financial_year =? order by A.ASSET_CODE";
                	ps = con.prepareStatement(qry);
                 	
                	//ps.setInt(1,cmbAcc_UnitCode);
                	ps.setInt(1,cmbOffice_code);
                	ps.setInt(2,cmbmajorclass);
                	ps.setString(3,newyear);
                   
                    rs = ps.executeQuery();                   
                    int count = 0;
                    while(rs.next()){
                        xml+="<assetid>"+rs.getString("ASSET_CODE")+"</assetid>" +
                                         "<assetName><![CDATA["+rs.getString("PARTICULARS")+"]]></assetName>";
                    count++;
                    }
                        if (count > 0)
                        {
                        	xml=xml+"<count>"+count+"</count>";
                        	xml=xml+"<flag1>success</flag1>";
                        }else{
                        	xml=xml+"<flag1>failure</flag1>";
                        }
                }
                catch(SQLException e) {
                       e.printStackTrace();
                        xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
                System.out.println(xml);
                out.println(xml);
        }else if(strCommand.equals("loadAssetDesc")){
        	
           //String cmbassetcode=request.getParameter("cmbassetcode");
        	xml="<response>";
            xml=xml+"<command>loadAssetCode</command>";
            try{
            	String[] divyear=cmbFinancialYear.split("-");
            	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
            	//PreparedStatement pstmt = con.prepareStatement("select PARTICULARS from FAS_ASSET_VAL_AC_DETAILS where ASSET_CODE=?");
            	PreparedStatement pstmt = con.prepareStatement("select a.PARTICULARS from FAS_A52_REGISTER a  WHERE A.accounting_unit_office_id= ? AND A.asset_major_class_code   = ? AND a.financial_year =? and a.ASSET_CODE=?");
            	//pstmt.setInt(1,cmbAcc_UnitCode);
                pstmt.setInt(1,cmbOffice_code);
                pstmt.setInt(2,cmbmajorclass);
                pstmt.setString(3,newyear);
            	pstmt.setInt(4, cmbassetcode);
            	 rs = pstmt.executeQuery();
            	 int count=0;
            	 while(rs.next()){
                     xml=xml+ "<AssetName><![CDATA["+rs.getString("PARTICULARS")+"]]></AssetName>";
                 count++;
                 }
                     if (count > 0)
                     {
                     	xml=xml+"<count>"+count+"</count>";
                     	xml=xml+"<flag2>success</flag2>";
                     }else{
                     	xml=xml+"<flag2>failure</flag2>";
                     }
            }catch(SQLException e){
            	e.printStackTrace();
            	
            }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
    }
        else if(strCommand.equalsIgnoreCase("loadjournalno")) 
                            {            String CONTENT_TYPE = "text/xml; charset=windows-1252";
                                         response.setContentType(CONTENT_TYPE);
                                        // String xml = "";
                                         int count=0;
                                         xml="<response>";
                                xml=xml+"<command>loadjournalno</command>";
                                try
                                {             
                                		CashBookYear = Integer.parseInt(request.getParameter("txtCB_Year"));
                                		CashBookMonth = Integer.parseInt(request.getParameter("txtCB_Month"));
                                            String sqlload="select ORGINATING_JVR_NO,to_char(ORGINATING_JVR_DATE,'DD/MM/YYYY') as jvdate from FAS_TDA_TCA_RAISED_MST2 where " + 
                                            "ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
                                            ps2 = con.prepareStatement(sqlload);                                            
                                            ps2.setInt(1,cmbAcc_UnitCode);
                                            ps2.setInt(2,cmbOffice_code);
                                            ps2.setInt(3,CashBookYear);
                                            ps2.setInt(4,CashBookMonth);
                                            
                                            rs2=ps2.executeQuery();
                                            while(rs2.next())
                                            {
                                                xml=xml+"<option><jvno>"+rs2.getInt("ORGINATING_JVR_NO")+"</jvno>";
                                                xml=xml+"<jvdate>"+rs2.getString("jvdate")+"</jvdate></option>";
                                                count++;
                                            }
                                             if(count>0)
                                             {
                                                 xml=xml+"<flag>success</flag>"; 
                                             }
                                             else
                                             {
                                                 xml=xml+"<flag>nodata</flag>";    
                                             }
                                     ps2.close();
                                     rs2.close();
                                     xml=xml+"</response>";           
                                     out.println(xml);
                                 } //try close
                                  catch(Exception e)
                                  {
                                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                    System.out.println(e);
                                   }
                                 
                            }   
         
     /*   if(strCommand.equalsIgnoreCase("loadAssetClass")) 
                            {   String xml = "";
                                xml=xml+"<command>loadAssetClass</command>";
                                try
                                    {             
                                        String sqlload="select ASSET_CLASS_CODE,ASSET_CLASS_DESC from COM_MST_ASSETS_CLASS order by asset_class_code";
                                        ps2 = con.prepareStatement(sqlload);
                                        rs2=ps2.executeQuery();
                                        
                                        while(rs2.next())
                                        {
                                            
                                            xml=xml+"<option><asset_class_code>"+rs2.getString("ASSET_CLASS_CODE")+"</asset_class_code>";
                                            xml=xml+"<asset_class_desc>"+rs2.getString("ASSET_CLASS_DESC")+"</asset_class_desc></option>";
                                            count++;
                                        }
                                        if(count>0)
                                        {
                                            xml=xml+"<flag>success</flag>"; 
                                        }
                                        else
                                        {
                                            xml=xml+"<flag>nodata</flag>";    
                                        }
                                         ps2.close();
                                         rs2.close();
                                     } //try close
                                      catch(Exception e)
                                      {
                                                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                        System.out.println(e);
                                       }
                            }
                            else if(strCommand.equalsIgnoreCase("loadAssetmajClass")) 
                            {   String xml = "";
                                xml=xml+"<command>loadAssetmajClass</command>";
                                try
                                    {             
                                        int assetcode=Integer.parseInt(request.getParameter("assetcode1"));
                                        System.out.println("Asset code ......"+assetcode);
                                        String sqlload="select a.asset_class_code,b.asset_class_desc from" + 
                                        "   (select asset_class_code from com_mst_assets_sl where asset_code=?)a" + 
                                        "   left outer join" + 
                                        "   (select asset_class_code,asset_class_desc from com_mst_assets_class)b" + 
                                        "   on a.asset_class_code=b.asset_class_code";
                                        System.out.println("query ****"+sqlload);
                                        ps2 = con.prepareStatement(sqlload);
                                        ps2.setInt(1,assetcode);
                                        rs2=ps2.executeQuery();
                                        
                                        if(rs2.next())
                                        {
                                            xml=xml+"<asset_class_code>"+rs2.getString("ASSET_CLASS_CODE")+"</asset_class_code>";
                                            xml=xml+"<asset_class_desc>"+rs2.getString("ASSET_CLASS_DESC")+"</asset_class_desc>";
                                            count++;
                                        }
                                        if(count>0)
                                        {
                                            xml=xml+"<flag>success</flag>"; 
                                        }
                                        else
                                        {
                                            xml=xml+"<flag>nodata</flag>";    
                                        }
                                         ps2.close();
                                         rs2.close();
                                     } //try close
                                      catch(Exception e)
                                      {
                                                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                        System.out.println(e);
                                       }
                            }
                            else if(strCommand.equalsIgnoreCase("loadAssetCode")) 
                            {   String xml = "";
                                xml=xml+"<command>loadAssetCode</command>";
                                System.out.println("cmbAcc_UnitCode"+cmbOffice_code);
                                System.out.println("cmbOffice_code"+cmbOffice_code);
                                try
                                {           
                                            String sqlload="select ASSET_CODE,asset_description from COM_MST_ASSETS_SL where accounting_unit_id=? and accounting_for_office_id=?" + 
                                            "and financial_year=?";
                                            ps2 = con.prepareStatement(sqlload);
                                            ps2.setInt(1,cmbAcc_UnitCode);
                                            ps2.setInt(2,cmbOffice_code);
                                            ps2.setString(3,financial_year);
                                            rs2=ps2.executeQuery();
                                            while(rs2.next())
                                            {
                                                xml=xml+"<option><asset_code>"+rs2.getString("ASSET_CODE")+"</asset_code>";
                                                xml=xml+"<asset_code_desc>"+rs2.getString("asset_description")+"</asset_code_desc></option>";
                                                count++;
                                            }
                                             if(count>0)
                                             {
                                                 xml=xml+"<flag>success</flag>"; 
                                             }
                                             else
                                             {
                                                 xml=xml+"<flag>nodata</flag>";    
                                             }
                                     ps2.close();
                                     rs2.close();
                                 } //try close
                                  catch(Exception e)
                                  {
                                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                    System.out.println(e);
                                   }
                            }*/
    /*    if(strCommand.equalsIgnoreCase("Load_Asset_Code"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);    
             String xml = "";
            xml="<response><command>Load_Asset_Code</command>";             
            int y=0;           
            System.out.println("insideloadcode");
            int majorclass=0;
            int subcode=0;
            
            try
              {
              majorclass=Integer.parseInt(request.getParameter("cmbmajorclass"));
              System.out.println("majorclass:"+majorclass);
               }
              catch(Exception que) {
                  System.out.println("Exception in assigning values in load command majorclass...."+que);
              }
        
        
                                           try
                                           {
                                           String subname="";
                                           ps=con.prepareStatement("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ASSET_MAJOR_CLASS_CODE=? and ACCOUNTING_UNIT_ID=?");
                                           ps.setInt(1,majorclass); 
                                           ps.setInt(2,cmbAcc_UnitCode);
                                           rs=ps.executeQuery();
                                               if(rs.next())
                                               {
                                               xml=xml+"<cid>"+rs.getInt("ASSET_CODE")+
                                               "</cid><cname>"+rs.getInt("ASSET_CODE")+
                                               "</cname>";
                                               //y++;
                                              
                                                   xml=xml+"<flag>success</flag>";
                                               }
                                               else
                                                   xml=xml+"<flag>failure</flag>";
                                                   
                                               ps.close();
                                               rs.close();
                                               }
                                               
                                           
                                           catch(Exception e)
                                           {
                                               System.out.println("Finding subledgercode failed due to exception"+e);
                                               xml=xml+"<flag>failure</flag>";
                                           }                                           
            xml=xml+"</response>";           
            out.println(xml);    
            return;
         } */
    }
   
}

    