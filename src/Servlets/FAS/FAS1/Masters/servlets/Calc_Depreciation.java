package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Calc_Depreciation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE =
	        "text/xml; charset=windows-1252"; 
   
    public Calc_Depreciation() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{


        /**
         * Database con
         */

        Connection con = null;
        Statement statement = null;
        ResultSet rst = null, results = null;
        PreparedStatement ps = null;
        PrintWriter out=response.getWriter();
        int k=0;
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                
            String conString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            conString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(conString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                statement = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing con:" + e);
        }


        /**
         * Content Type Setting
         */

        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
         */
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        /**
         * 
         *Declaration
         */
        String qry="",strCommand="",xml="";
    ResultSet result=null;
     /*   int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        int txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        int txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));*/
       strCommand=request.getParameter("command");
        if(strCommand.equals("loadMajor"))
        { 
        	System.out.println("\n*************\nloadMajor\n**************\n");
            xml="<response><command>loadMajor</command>";
            try 
            {
             
             result = statement.executeQuery("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE");
             
              
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_MAJOR_CLASS_CODE>" + result.getInt("ASSET_MAJOR_CLASS_CODE") + "</ASSET_MAJOR_CLASS_CODE>";
                	 xml += "<ASSET_MAJOR_CLASS_DESC>" + result.getString("ASSET_MAJOR_CLASS_DESC") + "</ASSET_MAJOR_CLASS_DESC>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting : " + e);
             }
             
             result.close();
           
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        
        if(strCommand.equals("loadDepRate"))
        { 
        	int cmbmajorasset=Integer.parseInt(request.getParameter("cmbmajorasset"));
        	System.out.println("\n*************\nloadDepRate\n**************\n");
            xml="<response><command>loadDepRate</command>";
            try 
            {

				result = statement
						.executeQuery("SELECT dep.DEPRECIATION_CATE_CODE, "
								+ "  dep.DEPRECIATION_RATE, "
								+ "  cat.DEPRECIATION_CATEGORY "
								+ "FROM FAS_DEPRE_RATES dep, "
								+ "  FAS_DEPRE_CATEGORY_MST cat "
								+ "WHERE dep.DEPRECIATION_CATE_CODE=cat.depreciation_cate_code "
								+ "AND dep.depreciation_cate_code  = "
								+ cmbmajorasset);

				try {
					xml = xml + "<flag>success</flag>";
					String valExists = "No";
					while (result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<DEPRECIATION_CATE_CODE>" + result.getInt("DEPRECIATION_CATE_CODE") + "</DEPRECIATION_CATE_CODE>";
                	 xml += "<DEPRECIATION_RATE>" + result.getString("DEPRECIATION_RATE") + "</DEPRECIATION_RATE>";
                 
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting : " + e);
             }
             
             result.close();
           
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        
        if(strCommand.equals("listData"))
        { 
        	 int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	 int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
             String cmbFinancialYear=request.getParameter("cmbFinancialYear");
        	int cmbmajorasset=Integer.parseInt(request.getParameter("cmbmajorasset"));
        	float cmbdepRate=Float.parseFloat(request.getParameter("cmbdepRate"));
        	System.out.println("\n*************\nlistData\n**************\n");
            xml="<response><command>listData</command>";
            try 
            {
				qry = "SELECT ASSET_CODE, " + "  PARTICULARS, "
						+ "  OPEN_BAL_QTY, " + "  OPENING_BAL_VALUE, "
						+ "  (OPENING_BAL_VALUE*("
						+ cmbdepRate
						+ "/100))                    AS totl_amt, "
						+ "  (OPENING_BAL_VALUE-(OPENING_BAL_VALUE*("
						+ cmbdepRate
						+ "/100)))AS previous_amt, "
						+ "  RECIEPTS_YEAR_QTY, "
						+ "  RECIEPTS_YR_VALUE, "
						+ "  ISSUES_YEAR_QTY, "
						+ "  ISSUES_YR_VALUE, "
						+ "  DEP_PREV_YEAR, "
						+ "  DEPRE_REC_AC, "
						+ "  DEPRE_ALLOWED_YR, "
						+ "  DEPRE_TR_AC, "
						+ "  DEPRE_UPTO_DATE, "
						+ "  NET_DEPRE_COST "
						+ " FROM FAS_A52_REGISTER "
						+ " WHERE ACCOUNTING_UNIT_ID     ="
						+ cmbAcc_UnitCode
						+ " AND ACCOUNTING_UNIT_OFFICE_ID="
						+ cmbOffice_code
						+ " AND FINANCIAL_YEAR           ='"
						+ cmbFinancialYear
						+ "' "
						+ "AND ASSET_MAJOR_CLASS_CODE   ="
						+ cmbmajorasset;
				System.out.println(qry);
				result = statement.executeQuery(qry);

				try {
					xml = xml + "<flag>success</flag>";
					String valExists = "No";
					while (result.next()) {
						valExists = "Yes";
                	 xml += "<ASSET_CODE>" + result.getInt("ASSET_CODE") + "</ASSET_CODE>";
                	 xml += "<PARTICULARS>" + result.getString("PARTICULARS") + "</PARTICULARS>";
                	 xml += "<OPEN_BAL_QTY>" + result.getInt("OPEN_BAL_QTY") + "</OPEN_BAL_QTY>";
                	 xml += "<OPENING_BAL_VALUE>" + result.getInt("OPENING_BAL_VALUE") + "</OPENING_BAL_VALUE>";
                	 xml += "<totl_amt>" + result.getFloat("totl_amt") + "</totl_amt>";
                	 xml += "<previous_amt>" + result.getFloat("previous_amt") + "</previous_amt>";
                	 xml += "<RECIEPTS_YEAR_QTY>" + result.getInt("RECIEPTS_YEAR_QTY") + "</RECIEPTS_YEAR_QTY>";
                	 xml += "<RECIEPTS_YR_VALUE>" + result.getInt("RECIEPTS_YR_VALUE") + "</RECIEPTS_YR_VALUE>";
                	 xml += "<ISSUES_YEAR_QTY>" + result.getInt("ISSUES_YEAR_QTY") + "</ISSUES_YEAR_QTY>";
                	 xml += "<ISSUES_YR_VALUE>" + result.getInt("ISSUES_YR_VALUE") + "</ISSUES_YR_VALUE>";
                	 xml += "<DEP_PREV_YEAR>" + result.getInt("DEP_PREV_YEAR") + "</DEP_PREV_YEAR>";
                	 xml += "<DEPRE_REC_AC>" + result.getInt("DEPRE_REC_AC") + "</DEPRE_REC_AC>";
                	 xml += "<DEPRE_ALLOWED_YR>" + result.getInt("DEPRE_ALLOWED_YR") + "</DEPRE_ALLOWED_YR>";
                	 xml += "<DEPRE_TR_AC>" + result.getInt("DEPRE_TR_AC") + "</DEPRE_TR_AC>";
                	 xml += "<DEPRE_UPTO_DATE>" + result.getInt("DEPRE_UPTO_DATE") + "</DEPRE_UPTO_DATE>";
                	 xml += "<NET_DEPRE_COST>" + result.getInt("NET_DEPRE_COST") + "</NET_DEPRE_COST>";
                	  }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting : " + e);
             }
             
             result.close();
           
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } 
       
        
        if(strCommand.equals("Submit")) 
        { 
        	 int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	 int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
             String cmbFinancialYear=request.getParameter("cmbFinancialYear");
        	int cmbmajorasset=Integer.parseInt(request.getParameter("cmbmajorasset"));
        	int cmbdepRate=Integer.parseInt(request.getParameter("cmbdepRate"));
        	int tblist=Integer.parseInt(request.getParameter("tblist"));
        	System.out.println("tblist >>> "+tblist);
        	for(int j=0;j<tblist;j++){
        	String[] txtASSET_CODE=request.getParameterValues("txtASSET_CODE");
       	 String[] txtPARTICULARS=request.getParameterValues("txtPARTICULARS");
       	String[] txtOPENING_BAL_VALUE=request.getParameterValues("txtOPENING_BAL_VALUE");
       	String[] txttotl_amt=request.getParameterValues("txttotl_amt");
       	String[] OPEN_BAL_QTY=request.getParameterValues("txtOPEN_BAL_QTY");
     	String[] txtprevious_amt=request.getParameterValues("txtprevious_amt");
       	int opn_bal=Integer.parseInt(txtOPENING_BAL_VALUE[j]);
       	int total_amt=Integer.parseInt(txttotl_amt[j]);
       	int opn_qty=Integer.parseInt(OPEN_BAL_QTY[j]);
    	int ass_code=Integer.parseInt(txtASSET_CODE[j]);
    	float previous_amt=Float.parseFloat(txtprevious_amt[j]);
        	System.out.println("\n*************\nSubmit\n**************\n");
            xml="<response><command>Submit</command>";
            try 
            {
				qry = "UPDATE FAS_A52_REGISTER " +
				" SET TEST_DEP_COST            = " +previous_amt+
				" WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode +
				" AND ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+
				" AND FINANCIAL_YEAR           ='"+cmbFinancialYear+"' " +
				" AND ASSET_MAJOR_CLASS_CODE   = "+cmbmajorasset+
				" AND asset_code               ="+ass_code+
				" AND particulars              ='"+txtPARTICULARS[j]+"' " +
				" AND opening_bal_value        ="+opn_bal+
				" AND open_bal_qty             ="+opn_qty;
				System.out.println(qry);
				ps=con.prepareStatement(qry);
				k=ps.executeUpdate();
				
				
				// k=statement.executeUpdate(qry);
			/*	 int count[] = statement.executeBatch();
				 
		            for(int i=1;i<=count.length;i++){
		                System.out.println("Query "+i+" has effected "+count[i]+" times");
		            }
		            con.commit();*/
				 System.out.println("k Value is "+k);
				if(k==0){
					xml=xml+"<flag>failure</flag>";
				}
         if(k!=0){
					xml = xml + "<flag>success</flag>";}
				
           
           //result.close();
           
             
            
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } 
        }
        out.write(xml);
        System.out.println("xml >>> "+xml);
        out.close();
	}
}