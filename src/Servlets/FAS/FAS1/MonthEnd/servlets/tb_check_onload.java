package Servlets.FAS.FAS1.MonthEnd.servlets;

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

/**
 * Servlet implementation class tb_check_onload
 */
public class tb_check_onload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public tb_check_onload() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
		PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	      
	      
	      
	      int count=0,AccUnitId=0,yesCount=0;
          String xml=null,cmd="",option="";    
	      HttpSession session=request.getSession(false);
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
	      Connection con=null;
	      PreparedStatement ps=null,ps2=null,ps1=null,ps3=null;        
	      ResultSet rs=null,rs2=null,rs3=null;
	      String Command=request.getParameter("Command");
	      /**
	       * Database Connection 
	      */
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
        
	      
	      String sql="";
	      xml="<response>";
          if(Command.equalsIgnoreCase("tbcheck"))
          {
        	  int txtCB_Year=0,txtCB_Month=0,unitcode=0;
        	  double receipt_amt=0.0,trf_amt=0.0;
        	  
        	  System.out.println();
        	  
        	  try{unitcode=Integer.parseInt(request.getParameter("unitcode"));}
              catch(Exception e){System.out.println(e);}
        	  
         	 try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
              catch(Exception e){System.out.println(e);}
         	 
         	 try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
              catch(Exception e){System.out.println(e);}
                  xml=xml+"<command>tbcheck</command>";
                  sql="SELECT case when SUM(TOTAL_AMOUNT)is null then 0 else SUM(TOTAL_AMOUNT) end as receipt_amt "+
					" FROM FAS_RECEIPT_MASTER "+
                	  " WHERE ACCOUNTING_UNIT_ID= "+unitcode+
                	  " AND CASHBOOK_YEAR       = "+txtCB_Year+
                	  " AND CASHBOOK_MONTH      = "+txtCB_Month+
                	  " And Receipt_Status      ='L' "+
                	  " And Created_By_Module  In ('CR','BR')";                    
                  System.out.println(" SQL :: "+sql);
                  try
                  {
                	  System.out.println("ins");
                           ps2=con.prepareStatement(sql);
                           rs2=ps2.executeQuery();                                 
                           if(rs2.next()) 
                           {
                        	   count++;
                        	   receipt_amt= rs2.getDouble("receipt_amt");
                        	   
                        	   ps3=con.prepareStatement("SELECT SUM(TOTAL_AMOUNT)as trf_amt  "+
								" FROM FAS_FUND_TRF_FROM_OFFICE  WHERE ACCOUNTING_UNIT_ID= "+unitcode+
                        			   " AND CASHBOOK_YEAR       ="+txtCB_Year+"  AND CASHBOOK_MONTH      ="+txtCB_Month+
                        			   " And Transfer_Status     ='L' And Remittance_Type     ='C'");
                        	   rs3=ps3.executeQuery();
                        	   if(rs3.next())
                        	   {
                        		   trf_amt= rs3.getDouble("trf_amt");
                        	   }
                                  
                           }					              
                           if(count==0)
                           {
                                   xml+="<flag>NoData</flag>";
                           }
                           else  
                           {
                        	   xml+="<flag>success</flag>";
                        	   System.out.println("receipt_amt   >>> "+receipt_amt+"   >>> trf_amt >>  "+trf_amt);
                        	   if(receipt_amt==trf_amt)
                        	   {
                        		   xml+="<result>resultEqual</result>";  
                        	   }
                        	   else
                        	   {
                        		   xml+="<result>resultNotEqual</result>";  
                        	   }
                                  
                           }
                                       
                  }
                  catch(Exception e) 
                  {
                           System.out.println("Exception in loadTransferUnit..."+e);
                           xml+="<flag>"+e.getMessage()+"</flag>";
                  }                      
          }
          else if(Command.equalsIgnoreCase("tb_restrict"))
          {
        	 
        	  xml="<response>";
        	  
        	  int txtCB_Year=0,txtCB_Month=0,unitcode=0;
        	  int unitid=0;
        	  
        	  try{unitcode=Integer.parseInt(request.getParameter("unitcode"));}
              catch(Exception e){System.out.println(e);}
        	  
         	 try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
              catch(Exception e){System.out.println(e);}
         	 
         	 try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
              catch(Exception e){System.out.println(e);}
         	 
         	xml=xml+"<command>tb_restrict</command>";
         	
         	System.out.println("UnitCode=====>"+unitcode);
         	System.out.println("txtCB_Year===>"+txtCB_Year);	
         	System.out.println("txtCB_Month===>"+txtCB_Month);	
         	
         	 
         	if(txtCB_Year >= 2018 && txtCB_Month > 3) 
         	{
         	try
            {
            ps =con.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_TBFreeze_Restrict2018 where ACCOUNTING_UNIT_ID="+unitcode);
            rs = ps.executeQuery();
            
            if (rs.next())
            {
            	unitid=rs.getInt("ACCOUNTING_UNIT_ID");
            }
            else
            {
            	unitid=0;
            }
            }
            catch(Exception e)
            {
            	System.out.println("Exception is"+e);
            }
            
            
            System.out.println("cmbAcc_UnitCode===>"+unitcode+"unitid===>"+unitid);
            
            if(unitid==unitcode)
            {
            	xml+="<flag>NotAllowed</flag>";
            }
            else
            {
            	xml+="<flag>Allowed</flag>";  
            }
         	}
         	else
         	{
         		xml+="<flag>Allowed</flag>";  
         	}
        	  
          }
          xml=xml+"</response>";
          System.out.println(xml);
          out.println(xml);
          //out.close();
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
