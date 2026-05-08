package Servlets.FAS.FAS1.CivilBills.servlets;

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

import Servlets.FAS.FAS1.CommonControls.servlets.Restricted_AccountHead;

public class Bill_Account_Heads extends HttpServlet {
   
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
                 
        }catch(Exception e)
        {
              System.out.println("Redirect Error :"+e);
        }
        

        System.out.println("welcome 2 servlet");

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        
        String strCommand = "";
        
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
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
        String finyr = "",anndate1 = "",active="",txtRemarks = "";        
          if(strCommand.equalsIgnoreCase("check"))
                                   {           
                                             int majorType=Integer.parseInt(request.getParameter("majorType"));     System.out.println("majorType"+majorType);                              
                                               xml="<response><command>Disp</command>";
                                               try 
                                                   {
                                                       System.out.println("inside try");
                                                       ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC,SUB_TYPE_APPLICABLE from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=?");
                                                       ps.setInt(1,majorType);
                                                       result = ps.executeQuery(); 
                                                       System.out.println("result is"+result);
                                                       while(result.next())      
                                                       {
                                                           xml=xml+"<mincode>"+result.getString("BILL_MINOR_TYPE_CODE")+"</mincode>";
                                                          xml=xml+"<mindesc>"+result.getString("BILL_MINOR_TYPE_DESC")+"</mindesc>";
                                                          xml=xml+"<subtype>"+result.getString("SUB_TYPE_APPLICABLE")+"</subtype>";
                                                           xml=xml+"<flag>success</flag>";
                                                       }xml=xml+"<flag>failure</flag>";
                                                     }
                                               catch(Exception e1)
                                                   {
                                                       System.out.println("Exception in idcheck ===> "+e1);
                                                       xml=xml+"<flag>failure</flag>";
                                                   }  
                                                   System.out.println("response end............."+xml);
                                               xml=xml+"</response>";
                           }
          else if(strCommand.equalsIgnoreCase("checksubcode"))
          {           
                    int majorType=Integer.parseInt(request.getParameter("majorType"));   
                    System.out.println("majorType"+majorType); 
                    int minorType=Integer.parseInt(request.getParameter("minorType"));   
                    
                      xml="<response><command>checksubcode</command>";
                      try 
                          {
                              System.out.println("inside try check sub coede ");
                              ps = con.prepareStatement("select SUB_TYPE_APPLICABLE from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?");
                              ps.setInt(1,majorType);
                              ps.setInt(2,minorType);
                              result = ps.executeQuery(); 
                              System.out.println("result is"+result);
                              while(result.next())      
                              {
                               
                                 xml=xml+"<subtype>"+result.getString("SUB_TYPE_APPLICABLE")+"</subtype>";
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
else if(strCommand.equalsIgnoreCase("checkCode1")) {
        	System.out.println("check vode ");  
              xml = "<response><command>checkCode1</command>";
              int txtAcc_HeadCode = 0;
              try {

                  txtAcc_HeadCode =Integer.parseInt(request.getParameter("txtAcc_HeadCode"));

              } catch (Exception e) {
                  System.out.println("Exception to catch account head ");
              }

                  try {
                      ps = con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS,sl_mandatory from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                      ps.setInt(1, txtAcc_HeadCode);
                      result = ps.executeQuery();
                      if (result.next()) {
                          xml =xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
  					   result.getString("ACCOUNT_HEAD_DESC") + "</hdesc>"; 					  
                          
                      } else {
                          System.out.println("No record found");
                          xml = xml + "<flag>failure</flag>";
                      }


                  } catch (Exception e) {
                      System.out.println("catch..HERE.in load head code." + e);
                      xml = xml + "<flag>failure</flag>";
                  }
             

              xml = xml + "</response>";
              System.out.println(xml);
              
          } 
        
		          else if(strCommand.equalsIgnoreCase("subType")) 
		    	    {
		    		System.out.println(":::::::::::::sub type::::::::::");
		    	        xml="<response><command>subb</command>"; 
		    	        try 
		    	                {
		    	        	 		int major=Integer.parseInt(request.getParameter("major2"));
		    	                    int sub=Integer.parseInt(request.getParameter("sub2"));	
		    	                    System.out.println("sub"+sub);
		    	                    ps = con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+sub);
		    	                    System.out.println("ps"+ps);
		    	                    result = ps.executeQuery();
		    	                    xml=xml+"<flag>success</flag>";
		    	                    while (result.next()) 
		    	                        {
		    	                    	
		    	                    	    System.out.println("subdesc"+result.getString("BILL_SUB_TYPE_DESC"));
		    	                            xml=xml+"<subcode>"+result.getString("BILL_SUB_TYPE_CODE")+"</subcode>";
		    	                            xml=xml+"<subdesc>"+result.getString("BILL_SUB_TYPE_DESC")+"</subdesc>";
		    	                        }   
		    	                }
		    	          catch(Exception e) 
		    	                {
		    	                        System.out.println("Exception in minor ===> "+e);   
		    	                        xml=xml+"<flag>failure</flag>";  
		    	                }
		    	            xml=xml+"</response>";
		    	            System.out.println("xml"+xml);
		    	    }
                else if(strCommand.equalsIgnoreCase("Add"))
                      {
                	        finyr=request.getParameter("finyr");
                            anndate1=request.getParameter("anndate1");
                           
                            txtRemarks = request.getParameter("txtRemarks");
                            active=request.getParameter("active");
                            int majorType=Integer.parseInt(request.getParameter("majorType"));     
                            int minorType=Integer.parseInt(request.getParameter("minorType"));     
                            int txtAccHeadCode=Integer.parseInt(request.getParameter("txtAccHeadCode"));
                            String billsubtt=request.getParameter("billsubtype");
                            if(billsubtt.equalsIgnoreCase("")){
                            	billsubtt="0";
                            }
                            int billsubtype=Integer.parseInt(billsubtt);
                            
                            xml="<response><command>Add</command>"; 
                             try 
                                {
                                    ps=con.prepareStatement("select * from FAS_BILL_ACCOUNT_HEADS where FINANCIAL_YEAR=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? and ACCOUNT_HEAD_CODE=?");
                                    ps.setString(1,finyr);
                                    ps.setInt(2,majorType);
                                    ps.setInt(3,minorType);
                                    ps.setInt(4,billsubtype);
                                    ps.setInt(5,txtAccHeadCode);
                                    ResultSet res=ps.executeQuery();
                                    System.out.println(res);
                                    if(res.next()) {
                                        xml=xml+"<flag>AlreadyExist</flag>"; 
                                    }
                                    else
                                    {
                                    String sql="insert into FAS_BILL_ACCOUNT_HEADS(FINANCIAL_YEAR,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,ACCOUNT_HEAD_CODE,STATUS,USED_UPTO_DATE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?)";
                                    System.out.println("sql"+sql);
                                    ps = con.prepareStatement(sql);
                                    System.out.println("ps"+ps);
                                    ps.setString(1,finyr);
                                    ps.setInt(2,majorType);
                                    ps.setInt(3,minorType);
                                    ps.setInt(4,billsubtype);
                                    ps.setInt(5,txtAccHeadCode);
                                    ps.setString(6,active);
                                    ps.setString(7,anndate1);
                                    ps.setString(8,txtRemarks);
                                    ps.setString(9,update_user);
                                    ps.setTimestamp(10,ts);System.out.println(ts);
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
                                
                        txtRemarks = request.getParameter("txtRemarks");
                        active=request.getParameter("active");
                        System.out.println(anndate1);
                        anndate1=request.getParameter("anndate1");
                        int majorType=Integer.parseInt(request.getParameter("majorType"));   
                        int minorType=Integer.parseInt(request.getParameter("minorType"));     
                        int txtAccHeadCode=Integer.parseInt(request.getParameter("txtAccHeadCode"));
                        int billsubtype=Integer.parseInt(request.getParameter("billsubtype"));
                        xml="<response><command>Updated</command>"; 
                             try {
                                ps =  con.prepareStatement("update FAS_BILL_ACCOUNT_HEADS set  STATUS=?,USED_UPTO_DATE=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where FINANCIAL_YEAR=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? and ACCOUNT_HEAD_CODE=?");
                                ps.setString(1,active);
                                ps.setString(2,anndate1);
                                ps.setString(3,txtRemarks);
                                ps.setString(4,update_user);
                                ps.setTimestamp(5,ts);
                                ps.setString(6,finyr);
                                ps.setInt(7,majorType);
                                ps.setInt(8,minorType);
                                ps.setInt(9,billsubtype);
                                ps.setInt(10,txtAccHeadCode);
                                
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
                            
                            int majorType=Integer.parseInt(request.getParameter("majorType"));     System.out.println("majorType.........."+majorType);
                            int minorType=Integer.parseInt(request.getParameter("minorType")); System.out.println("minor.............."+minorType);
                            int txtAccHeadCode=Integer.parseInt(request.getParameter("txtAccHeadCode"));System.out.println("acc............"+txtAccHeadCode+"\n");
                            int billsubtype=Integer.parseInt(request.getParameter("billsubtype"));
                            xml = "<response><command>Delete</command>";System.out.println(xml);
            
                            try {
                                ps = con.prepareStatement("delete from FAS_BILL_ACCOUNT_HEADS where FINANCIAL_YEAR=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? and ACCOUNT_HEAD_CODE=?");
                                ps.setString(1,finyr);
                                ps.setInt(2,majorType);
                                ps.setInt(3,minorType);
                                ps.setInt(4,billsubtype);
                                ps.setInt(5,txtAccHeadCode);
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
