package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Assets_Num_OB_Edit_Status
 */
public class Assets_Num_OB_Edit_Status extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
  
    public Assets_Num_OB_Edit_Status() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection connection=null;
	        ResultSet result=null,rs1=null,rs2=null; 
	   	    PreparedStatement ps=null,ps1=null,ps2=null,ps3=null;
	      Vector<Integer> status=new Vector<Integer>();
	      Vector<Integer> statusnot=new Vector<Integer>();
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
	           /*try
	           {
	        	   statement=connection.createStatement();
	               connection.clearWarnings();
	           }
	           catch(SQLException e)
	           {
	               System.out.println("Exception in creating statement:"+e);
	           }*/
	        }
	        catch(Exception e)
	        {
           System.out.println("Exception in openeing connection:"+e);
	        	}
     response.setContentType(CONTENT_TYPE);
     String strCommand = ""; 
     String xml="";
     int cmbAcc_UnitCode = 0;
     int cmbOffice_code = 0;
     String assetsnumstatus=null,assetsnumstatusnil=null;
     String cmbFinancialYear = null;

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

     	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode").trim());
     }
     catch(Exception e)
     { 
         System.out.println("IGNORABLE Exception getting cmbAcc_UnitCode from jsp " + e);
     }try{
			cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code").trim());
		}
	    catch(Exception e)
	    { 
	        System.out.println("IGNORABLE Exception getting cmbOffice_code from jsp " + e);
	    }try{
			cmbFinancialYear = request.getParameter("cmbFinancialYear");
	    }
	    catch(Exception e)
	    { 
	        System.out.println("IGNORABLE Exception getting cmbFinancialYear from jsp " + e);
	    }try{
			assetsnumstatusnil=request.getParameter("assetsNumEditStatusNil");
			assetsnumstatus=request.getParameter("assetsNumEditStatus");
          }
     catch(Exception e)
     { 
         System.out.println("IGNORABLE Exception getting assetsnumstatus from jsp " + e);
     }         
     System.out.println("accounting_unit_id : " + cmbAcc_UnitCode);
 	System.out.println("accounting_unit_office_id : " + cmbOffice_code);
 	System.out.println("financial_year : " + cmbFinancialYear);
 	System.out.println("assetsnumstatus : " + assetsnumstatus);
 	System.out.println("assetsnumstatusnil : " + assetsnumstatusnil);
 	
   if(strCommand.equals("checkStatus"))
     { 
     	int count=0;
     	System.out.println("\n*************\n checkStatus \n**************\n");
         xml="<response><command>checkStatus</command>";
         try 
         {
          
         		ps = connection.prepareStatement("select accounting_unit_id,accounting_for_office_id,NUM_OB_EDIT_STATUS from FAS_ASSETS_NUM_OB_EDIT where accounting_unit_id=? and accounting_for_office_id=? and FINANICAL_YEAR=? AND NUM_OB_EDIT_STATUS='Y'");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, cmbFinancialYear);				
					result = ps.executeQuery();

        try
          {
         	 xml=xml+"<flag>success</flag>";
         	 String valExists = "No";
             while(result.next())
              { 
             	 valExists = "Yes";
		             xml += "<accounting_unit_id>" + result.getInt("accounting_unit_id") + "</accounting_unit_id>"; 
		             xml += "<accounting_for_office_id>" + result.getInt("accounting_for_office_id") + "</accounting_for_office_id>"; 
                  xml=xml+"<NUM_OB_EDIT_STATUS>" + result.getString("NUM_OB_EDIT_STATUS") + "</NUM_OB_EDIT_STATUS>";  
             	 count++;
              }
              xml =xml+ "<exists>"+valExists+"</exists>";
              xml =xml+ "<count>"+count+"</count>";
          }
        catch(Exception e)
          {
         	 System.out.println("Exception in getting values from DB - check status: " + e);
          }

         }
         catch(Exception e1)
         {
         	System.out.println("Exception is in check "+e1);
         	xml=xml+"<flag>failure</flag>";
         }
         xml=xml+"</response>";
     } 

     else if(strCommand.equals("AddOBEdit"))
     { 
     	System.out.println("\n*************\n AddOBEdit \n**************\n");
         xml="<response><command>AddOBEdit</command>";
         java.sql.Date AssetsNumDate=new java.sql.Date(ts.getTime());
         int res=0;
         try 
         {
         	int cc=0;
         	int cc1=0,cc2=0;
				ps2=connection.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from FAS_ASSETS_NUM_OB_EDIT where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND FINANICAL_YEAR='"+cmbFinancialYear+"' AND NUM_OB_EDIT_STATUS='Y'");
				ResultSet rs11=ps2.executeQuery();
				//System.out.println("Already exists ");
				if(rs11.next()){
				//	System.out.println("Already exists");
					xml=xml+"<flag>AlreadyExist</flag>";									
				}else{
					String notofficestatusupdate="";
					String officestatusupdate="";
					String queryFind="select RENDERING_UNIT_OFFICE_ID from fas_asset_val_ac_render_units where acct_rendering_unit_id="+cmbAcc_UnitCode;
					ps1=connection.prepareStatement(queryFind);
					
					//cc1=ps1.executeUpdate();
					rs1=ps1.executeQuery();
					//System.out.println("insert get rending values ");
					if(rs1.next()){
						
						//System.out.println("insert if getting values ");
						while(rs1.next()){
							//System.out.println("inside while loop   ");
							int unitofficeid=rs1.getInt("RENDERING_UNIT_OFFICE_ID");
							//System.out.println("inside checking with this table..."+unitofficeid);
							String findingintab="select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from FAS_ASSETS_NUM_OB_EDIT where ACCOUNTING_FOR_OFFICE_ID="+unitofficeid+" AND FINANICAL_YEAR='"+cmbFinancialYear+"' AND NUM_OB_EDIT_STATUS='Y'";
							ps3=connection.prepareStatement(findingintab);
							//cc2=ps3.executeUpdate();
							rs2=ps3.executeQuery();
							if(rs2.next()){
								
								while(rs2.next()){
									//System.out.println("inside inner while looppp ");
									int statusoffice=rs2.getInt("ACCOUNTING_FOR_OFFICE_ID");
									//System.out.println("statusoffice "+statusoffice);
									//officestatusupdate+=statusoffice;
									if(!status.contains(statusoffice)){
										status.add(statusoffice);
									}								
								}
								
							}else{
								//notofficestatusupdate+=unitofficeid;
								if(!statusnot.contains(unitofficeid)){
								statusnot.add(unitofficeid);
								}
							}
							
							//System.out.println("  status  "+status);
							//System.out.println("  statusnot  "+statusnot);	
					   	}
						int sizevect=statusnot.size();
						if((statusnot.isEmpty())||(statusnot.contains(cmbAcc_UnitCode))){
							//System.out.println("vector status vector empty");
							String assetsnumstatus1=null;
							if(assetsnumstatus.equalsIgnoreCase("AssetsNumEditStatus")){
								assetsnumstatus1="Y";
							}else{
								assetsnumstatus1="N";
							}
							String assetnumstatusnil=null;
							if(assetsnumstatus.equalsIgnoreCase("AssetsNumEditStatusNil")){
								assetnumstatusnil="Y";
							}else{
								assetnumstatusnil="N";
							}
			            	
		         		ps = connection.prepareStatement("insert into FAS_ASSETS_NUM_OB_EDIT(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANICAL_YEAR,NUM_OB_EDIT_STATUS,NUM_OB_EDIT_DATE,updated_by_user_id,updated_date,NIL_NUM_OB_EDIT) values(?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, cmbFinancialYear);		
							ps.setString(4, assetsnumstatus1);
							ps.setDate(5, AssetsNumDate);
							ps.setString(6, userid);	
							ps.setTimestamp(7, ts);
							ps.setString(8, assetnumstatusnil);
							res = ps.executeUpdate();
							if(res>0){
								xml=xml+"<flag>success</flag>"; 
							}else{
								xml=xml+"<flag>NotInsert</flag>"; 
							}
							
						}else{
							xml=xml+"<flag>someOfficeNot</flag>"; 
						}
						
					}else{
						String assetsnumstatus1=null;
						if(assetsnumstatus.equalsIgnoreCase("AssetsNumEditStatus")){
							assetsnumstatus1="Y";
						}else{
							assetsnumstatus1="N";
						}
						String assetnumstatusnil=null;
						if(assetsnumstatus.equalsIgnoreCase("AssetsNumEditStatusNil")){
							assetnumstatusnil="Y";
						}else{
							assetnumstatusnil="N";
						}
		            	
	         		ps = connection.prepareStatement("insert into FAS_ASSETS_NUM_OB_EDIT(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANICAL_YEAR,NUM_OB_EDIT_STATUS,NUM_OB_EDIT_DATE,updated_by_user_id,updated_date,NIL_NUM_OB_EDIT) values(?,?,?,?,?,?,?,?)");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, cmbFinancialYear);		
						ps.setString(4, assetsnumstatus1);
						ps.setDate(5, AssetsNumDate);
						ps.setString(6, userid);	
						ps.setTimestamp(7, ts);
						ps.setString(8, assetnumstatusnil);
						res = ps.executeUpdate();
						if(res>0){
							xml=xml+"<flag>success</flag>"; 
						}else{
							xml=xml+"<flag>NotInsert</flag>"; 
						}
					}
					
					
				}
				
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
