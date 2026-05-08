package Servlets.FAS.FAS1.ReceiptSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CommonControls.servlets.Restricted_AccountHead;
import Servlets.Security.classes.UserProfile;


public class Receipt_SL_New extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = null;
       

    public Receipt_SL_New() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	     
	     
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
	        
	        
	        int employee_id=0;
	        
	        HttpSession session=request.getSession(false);
	        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");          
	        System.out.println("user id::"+empProfile.getEmployeeId());
	        employee_id=empProfile.getEmployeeId();
	        
	        
	        
	  
	        Connection con=null;
	        ResultSet rs=null,rs2=null;
	        PreparedStatement ps=null,ps2=null;
	        //String xml="";
	        response.setContentType(CONTENT_TYPE);
	        response.setHeader("Cache-Control","no-cache");
	        PrintWriter out = response.getWriter();
	        String strCommand="";
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
	                               ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
	                               Class.forName(strDriver.trim());
	                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	        }
	        catch(Exception e)
	        {
	             System.out.println("Exception in opening connection :"+e);
	             //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	        }
	       
	        try 
	        {
	            strCommand=request.getParameter("Command");
	            System.out.println("assign..here command..."+strCommand);
	           
	        }
	        
	        catch(Exception e) 
	        {
	            System.out.println("Exception in assigning..."+e);
	        }
	        if(strCommand.equalsIgnoreCase("checkCodes")) 
	        {
	              
	             String CONTENT_TYPE = "text/xml; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	             String xml="";
	             xml="<response><command>"+strCommand+"</command>";
	             int txtAcc_HeadCode=0;
	             try{
	             
	               txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
	               
	               }
	             catch(Exception e){
	                System.out.println("Exception to catch account head ");
	             }
	            
	             
	            Restricted_AccountHead rah=new Restricted_AccountHead();            
	             
	  if(rah.accountHeadDetails(txtAcc_HeadCode , employee_id)==0)
	   {      
	             
	             try {
	            	     String sql="SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where FAS_DISABLE='Y' and ACCOUNT_HEAD_CODE="+txtAcc_HeadCode+"";
	            	     ps2=con.prepareStatement(sql);System.out.println("sql"+sql);
	            	     rs2=ps2.executeQuery();
	            	    	 if(rs2.next())
	            	    	 {
	            	    		 xml=xml+"<flag>failure</flag>";
	            	    	 }
	            	    	 else       	 
	            	       	 {	 	     
	            	          	     ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS,sl_mandatory from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
				                     ps.setInt(1,txtAcc_HeadCode);
				                     rs=ps.executeQuery();
				                     if(rs.next())
				                     {
				                     xml=xml+"<flag>success</flag><hid>"+txtAcc_HeadCode+
				                     "</hid><hdesc>"+rs.getString("ACCOUNT_HEAD_DESC")+
				                     "</hdesc><BalType>"+rs.getString("BALANCE_TYPE")+
				                     "</BalType><SL_YN>"+rs.getString("SUB_LEDGER_TYPE_APPLICABLE")+
				                         "</SL_YN><rmk>"+rs.getString("REMARKS")+"</rmk><sl_man>"+rs.getString("sl_mandatory")+"</sl_man>";
				                           
				                         if(rs.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) 
				                         {
				                            int sl_cnt=0;
				                        	 ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=?");
				                             ps.setInt(1,txtAcc_HeadCode);
				                             rs=ps.executeQuery();
				                             while(rs.next())
				                             {
				                                 sl_cnt++;
				                            	 xml=xml+"<SLCODE>"+rs.getInt("SUB_LEDGER_TYPE_CODE")+"</SLCODE>";
				                                 System.out.println(rs.getInt("SUB_LEDGER_TYPE_CODE")+"code");
				                                 if(rs.getInt("SUB_LEDGER_TYPE_CODE")!=0)
				                                 {
				                                 System.out.println("take SL DESC");
				                                 ps2=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
				                                 ps2.setInt(1,rs.getInt("SUB_LEDGER_TYPE_CODE"));
				                                 rs2=ps2.executeQuery();
				                                 if(rs2.next())
				                                 xml=xml+"<SLDESC>"+rs2.getString("SUB_LEDGER_TYPE_DESC")+"</SLDESC>";
				                                 }
				                             }
				                             if(sl_cnt==0) {
		                                    	 System.out.println("Account Head Not Allowed");
		                                    	 xml=xml+"<flag>failure</flag>";
		                                     }
				                         }                            
				                     }
				            	     
				                     else
				                      {
				                        System.out.println("No record found");
				                        xml=xml+"<flag>failure</flag>";
				                       }
	            	     }
	            	     }
	                
	                catch(Exception e)
	                {
	                System.out.println("catch..HERE.in load head code."+e);
	                xml=xml+"<flag>failure</flag>";
	                }
	         }       
	         else {
	             xml=xml+"<flag>failure</flag>";
	         }
	                
	            xml=xml+"</response>";
	            System.out.println(xml);
	            out.println(xml);
	         }
	      
	        
	        
	}

 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
