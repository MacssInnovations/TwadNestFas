package Servlets.FAS.FAS1.MIS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class Accounts_hierachy
 */

/*
 * Developed By Nandakumar 
 * 
 * 04/Dec/2019
 * 
 * Common Servlet for loading Major,Minor and SubHeads
 * 
 * */
public class Accounts_Hierachy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accounts_Hierachy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	   
		/*Session checking*/
		try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
		/*Establishing Connection*/
		
	    Connection con = null;
        ResultSet rs = null, rs2 = null;
      /*  ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;
*/
        PreparedStatement ps = null, ps2 = null;
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in openeing connection :" + e);
            //               sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        /*Setting Response ContentType*/
    
        System.out.println("servlet called");
       /* String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();*/
        /*Generating xml response*/
        
        String strType,finYearStart,finYearEnd,minorsortOrder,minorHead,majorsortOrder,majorHead,subsortOrder,subHead,xml,sql;
        strType=finYearStart=finYearEnd=minorsortOrder=minorHead=majorsortOrder=majorHead=subsortOrder=subHead=sql="";
        String[] majorHeadarray= new String[20], minorHeadarray= new String[20], subHeadarray = new String[20];
        xml = "<response>";
        try {
        	 strType = request.getParameter("Command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        System.out.println("Command " + strType);
       
       
        if (strType.equalsIgnoreCase("load_Major_Heads")) {
        	String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
           /* response.setHeader("Cache-Control", "no-cache");*/
            PrintWriter out = response.getWriter();
        	
            System.out.println("inside if");
            try {
    	       	
    	       	finYearStart = request.getParameter("finYearStart");
    	       	finYearEnd = request.getParameter("finYearEnd");
    	       
    	       }
    	        catch (Exception e) {
    	           e.printStackTrace();
    	        					}
            System.out.println("finYearStart " + finYearStart);
            System.out.println("finYearEnd  " + finYearEnd);
            xml = "<response><command>load_Major_Heads</command>";
           
            	sql = ""
            			+ "SELECT  T.MAJORHEADSORTORDER, T.MAJORHEAD, "
               			+ "  T.CURRENT_MONTH_DEBIT, "
            			+ "  T.CURRENT_MONTH_CREDIT, "
            			+ "  CASE "
            			+ "    WHEN (NVL(( CURRENT_MONTH_DEBIT - CURRENT_MONTH_CREDIT ),0)) < 0 "
            			+ "    THEN (current_month_debit       - current_month_credit )*-1 "
            			+ "    ELSE (current_month_debit       - current_month_credit ) "
            			+ "  END AS curr_net, "
            			+ "  CASE "
            			+ "    WHEN ( current_month_debit - current_month_credit ) > 0 "
            			+ "    THEN ' Dr' "
            			+ "    ELSE ' Cr' "
            			+ "  END AS curr_net_disp "
            			+ "FROM "
            			+ "  (SELECT DISTINCT MAJORHEAD, "
            			+ "    MAJORHEADSORTORDER, "
            			+ "    SUM(CURRENT_MONTH_DEBIT)  AS CURRENT_MONTH_DEBIT, "
            			+ "    SUM(CURRENT_MONTH_CREDIT) AS CURRENT_MONTH_CREDIT "
            			+ "  FROM "
            			+ "    (SELECT AG.MAJORHEAD, "
            			+ "      AG.MAJORHEADSORTORDER, "
            			+ "      ag.minorhead, "
            			+ "      ag.subhead, "
            			+ "      X.ACCOUNT_HEAD_CODE, "
            			+ "      (SELECT ACCOUNT_HEAD_DESC "
            			+ "      FROM com_mst_account_heads "
            			+ "      WHERE ACCOUNT_HEAD_CODE = X.ACCOUNT_HEAD_CODE "
            			+ "      )                         AS account_head_des, "
            			+ "      SUM(current_month_debit)  AS current_month_debit , "
            			+ "      SUM(current_month_credit) AS current_month_credit "
            			+ "    FROM "
            			+ "      (SELECT ftbr.account_head_code, "
            			+ "        ftbr.current_month_debit, "
            			+ "        ftbr.current_month_credit "
            			+ "      FROM FAS_TRIAL_BALANCE FTBR "
            			+ "      WHERE (ftbr.cashbook_year =? "
            			+ "      AND ftbr.cashbook_month  >=4 "
            			+ "      AND FTBR.CASHBOOK_MONTH  <=12) "
            			+ "      OR (ftbr.cashbook_year    =? "
            			+ "      AND ftbr.cashbook_month  >=1 "
            			+ "      AND ftbr.cashbook_month  <=3) "
            			+ "      UNION ALL "
            			+ "      SELECT ftbs.account_head_code, "
            			+ "        ftbs.current_month_debit, "
            			+ "        ftbs.current_month_credit "
            			+ "      FROM FAS_TRIAL_BALANCE_SUPPLEMENT FTBS "
            			+ "      WHERE FTBS.CASHBOOK_YEAR =? "
            			+ "      ) x, "
            			+ "      fas_ho_annualgrouping ag "
            			+ "      WHERE AG.FINYEARSTART= ? "
            			+ "      AND AG.FINYEAREND    = ? "
            			+ "    AND X.ACCOUNT_HEAD_CODE  =AG.HOA "
            			+ "    AND AG.MAJORHEADSORTORDER IN "
            			+ "      (SELECT DISTINCT AG.Majorheadsortorder "
            			+ "      FROM FAS_HO_ANNUALGROUPING AG "
            			+ "      WHERE AG.FINYEARSTART= ? "
            			+ "      AND AG.FINYEAREND    = ? "
            			+ "      ) "
            			+ "    GROUP BY AG.MAJORHEAD, "
            			+ "      MAJORHEADSORTORDER, "
            			+ "      AG.MINORHEAD, "
            			+ "      MINORHEADSORTORDER, "
            			+ "      AG.SUBHEAD, "
            			+ "      SUBHEADSORTORDER, "
            			+ "      ACCOUNT_HEAD_CODE "
            			+ "    ORDER BY Majorheadsortorder, "
            			+ "      minorheadsortorder, "
            			+ "      SUBHEADSORTORDER, "
            			+ "      ACCOUNT_HEAD_CODE "
            			+ "    ) "
            			+ "  GROUP BY MAJORHEAD, "
            			+ "    MAJORHEADSORTORDER "
            			+ "  ORDER BY Majorheadsortorder "
            			+ "  )T";

            System.out.println("SQL::::" + sql);
            try {
                int count = 0;
                ps = con.prepareStatement(sql);
                ps.setString(1, finYearStart);
                ps.setString(2, finYearEnd);
                ps.setString(3, finYearEnd);
                ps.setString(4, finYearStart);
                ps.setString(5, finYearEnd);
                ps.setString(6, finYearStart);
                ps.setString(7, finYearEnd);
                
                
                xml =
 xml + "<flag>success</flag>";
                rs = ps.executeQuery();
                while (rs.next()) {
                	xml = xml + "<leng>";
                	 xml = xml + "<Majheadorder>" + rs.getInt("MAJORHEADSORTORDER") + "</Majheadorder>";
                    xml = xml + "<Majhead>" + rs.getString("MAJORHEAD") + "</Majhead>";
                    xml = xml + "<Debit>" + rs.getString("CURRENT_MONTH_DEBIT") + "</Debit>";
                    xml = xml + "<Credit>"+ rs.getString("CURRENT_MONTH_CREDIT") + "</Credit>";
                    xml = xml + "<Net>" + rs.getString("curr_net") + "</Net>";
                    xml = xml + "<Netdisp>" + rs.getString("curr_net_disp") + "</Netdisp>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml =
 "<response><command>load_Major_Heads</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>load_Major_Heads</command><flag>failure</flag>";
            }
            xml = xml + "</response>";
			out.println(xml);
			out.close();
            System.out.println(xml);
        }
        
        if (strType.equalsIgnoreCase("load_Minor_Heads")) {
        	String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            /*response.setHeader("Cache-Control", "no-cache");*/
            PrintWriter out = response.getWriter();
            try {
               	
               	finYearStart = request.getParameter("finYearStart");
               	finYearEnd = request.getParameter("finYearEnd");
               	majorsortOrder=request.getParameter("majorsortOrder");
               
               }
                catch (Exception e) {
                   e.printStackTrace();
                					}
            System.out.println("finYearStart " + finYearStart);
            System.out.println("finYearEnd  " + finYearEnd);
            System.out.println("majorsortOrder  " + majorsortOrder);
            
            xml = "<response><command>load_Minor_Heads</command>";
           
            sql = ""
            		+ "SELECT T.MINORHEADSORTORDER, "
            		+ "  T.MINORHEAD, "
            		+ "  T.MAJORHEAD, "
            		+ "  T.CURRENT_MONTH_DEBIT, "
            		+ "  T.CURRENT_MONTH_CREDIT, "
            		+ "  CASE "
            		+ "    WHEN (NVL(( CURRENT_MONTH_DEBIT - CURRENT_MONTH_CREDIT ),0)) < 0 "
            		+ "    THEN (current_month_debit       - current_month_credit )*-1 "
            		+ "    ELSE (current_month_debit       - current_month_credit ) "
            		+ "  END AS curr_net, "
            		+ "  CASE "
            		+ "    WHEN ( current_month_debit - current_month_credit ) > 0 "
            		+ "    THEN ' Dr' "
            		+ "    ELSE ' Cr' "
            		+ "  END AS curr_net_disp "
            		+ "FROM "
            		+ "  (SELECT DISTINCT MINORHEAD, "
            		+ "    MINORHEADSORTORDER, "
            		+ "    MAJORHEAD, "
            		+ "    SUM(CURRENT_MONTH_DEBIT)  AS CURRENT_MONTH_DEBIT, "
            		+ "    SUM(CURRENT_MONTH_CREDIT) AS CURRENT_MONTH_CREDIT "
            		+ "  FROM "
            		+ "    (SELECT AG.MINORHEAD, "
            		+ "      AG.MINORHEADSORTORDER, "
            		+ "      AG.MAJORHEAD, "
            		+ "      ag.subhead, "
            		+ "      X.ACCOUNT_HEAD_CODE, "
            		+ "      (SELECT ACCOUNT_HEAD_DESC "
            		+ "      FROM com_mst_account_heads "
            		+ "      WHERE ACCOUNT_HEAD_CODE = X.ACCOUNT_HEAD_CODE "
            		+ "      )                         AS account_head_des, "
            		+ "      SUM(current_month_debit)  AS current_month_debit , "
            		+ "      SUM(current_month_credit) AS current_month_credit "
            		+ "    FROM "
            		+ "      (SELECT ftbr.account_head_code, "
            		+ "        ftbr.current_month_debit, "
            		+ "        ftbr.current_month_credit "
            		+ "      FROM FAS_TRIAL_BALANCE FTBR "
            		+ "      WHERE (ftbr.cashbook_year ="+finYearStart.trim()
            		+ "      AND ftbr.cashbook_month  >=4 "
            		+ "      AND FTBR.CASHBOOK_MONTH  <=12) "
            		+ "      OR (ftbr.cashbook_year    = "+finYearEnd.trim()
            		+ "      AND ftbr.cashbook_month  >=1 "
            		+ "      AND ftbr.cashbook_month  <=3) "
            		+ "      UNION ALL "
            		+ "      SELECT ftbs.account_head_code, "
            		+ "        ftbs.current_month_debit, "
            		+ "        ftbs.current_month_credit "
            		+ "      FROM FAS_TRIAL_BALANCE_SUPPLEMENT FTBS "
            		+ "      WHERE FTBS.CASHBOOK_YEAR = "+finYearEnd.trim()
            		+ "      ) x, "
            		+ "      fas_ho_annualgrouping ag "
            		+ "    WHERE X.ACCOUNT_HEAD_CODE =AG.HOA "
            		+ "    AND AG.MAJORHEADSORTORDER = "+majorsortOrder.trim()
            		+ "    AND AG.FINYEARSTART       = "+finYearStart.trim()
            		+ "    AND AG.FINYEAREND         = "+finYearEnd.trim()
            		+ "    GROUP BY AG.MAJORHEAD, "
            		+ "      MAJORHEADSORTORDER, "
            		+ "      AG.MINORHEAD, "
            		+ "      MINORHEADSORTORDER, "
            		+ "      AG.SUBHEAD, "
            		+ "      SUBHEADSORTORDER, "
            		+ "      ACCOUNT_HEAD_CODE "
            		+ "    ORDER BY Majorheadsortorder, "
            		+ "      minorheadsortorder, "
            		+ "      SUBHEADSORTORDER, "
            		+ "      ACCOUNT_HEAD_CODE "
            		+ "    ) "
            		+ "  GROUP BY MINORHEADSORTORDER, "
            		+ "    MINORHEAD, "
            		+ "    MAJORHEAD "
            		+ "  ORDER BY MINORHEADSORTORDER "
            		+ "  )T";
            System.out.println("SQL::::" + sql);
            try {
                int count = 0;
                ps2 = con.prepareStatement(sql);
             
                xml =
 xml + "<flag>success</flag>";
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                	xml = xml + "<leng>";
                	 xml = xml + "<minheadorder>" + rs2.getInt("MINORHEADSORTORDER") + "</minheadorder>";
                    xml = xml + "<minhead>" + rs2.getString("MINORHEAD") + "</minhead>";
                    xml = xml + "<debit>" + rs2.getString("CURRENT_MONTH_DEBIT") + "</debit>";
                    xml = xml + "<credit>"+ rs2.getString("CURRENT_MONTH_CREDIT") + "</credit>";
                    xml = xml + "<net>" + rs2.getString("curr_net") + "</net>";
                    xml = xml + "<netdisp>" + rs2.getString("curr_net_disp") + "</netdisp>";
                    xml = xml + "<majhead>" + rs2.getString("MAJORHEAD") + "</majhead>";
                    xml = xml + "</leng>";
                    count++;
                }
                if (count == 0) {
                    System.out.println("inside count==0");
                    xml =
 "<response><command>load_Minor_Heads</command><flag>failure</flag>";
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                System.out.println("error while fetching data " + sqle);
                xml =
 "<response><command>load_Minor_Heads</command><flag>failure</flag>";
            }
            xml = xml + "</response>";
            try{
			out.println(xml);
			out.flush();
		    response.flushBuffer();
			out.close();
            }
            catch (Exception e)
            {
            	 System.out.println("error"+e);
            }
            System.out.println(xml);
        }
        
        			    if (strType.equalsIgnoreCase("load_Sub_Heads")) {
        			    	String CONTENT_TYPE = "text/xml; charset=windows-1252";
        			        response.setContentType(CONTENT_TYPE);
        			        response.setHeader("Cache-Control", "no-cache");
        			        PrintWriter out = response.getWriter();
        		            System.out.println("inside if");
        		            try {
        		               	
        		               	finYearStart = request.getParameter("finYearStart");
        		               	finYearEnd = request.getParameter("finYearEnd");
        		               	minorsortOrder=request.getParameter("minorsortOrder");
        		               	minorHead=request.getParameter("minorHead");
        		               	majorsortOrder=request.getParameter("majorsortOrder");
        		               	majorHead=request.getParameter("majorHead");
        		               	
        		               	majorHeadarray=majorHead.split(" ");
        		               	minorHeadarray=minorHead.split(" ");
        		               
        		               }
        		                catch (Exception e) {
        		                   e.printStackTrace();
        		                					}
        		            System.out.println("finYearStart " + finYearStart);
        		            System.out.println("finYearEnd  " + finYearEnd);
        		            System.out.println("minorsortOrder  " + minorsortOrder);
        		            System.out.println("minorHead  " + minorHead);
        		            System.out.println("majorsortOrder  " + majorsortOrder);
        		            System.out.println("majorHead  " + majorHead);
        		           
        		            xml = "<response><command>load_Sub_Heads</command>";
        		           
        		           sql = ""
        		            		+ "SELECT T.MINORHEADSORTORDER, "
        		            		+ "  T.MINORHEAD, "
        		            		+ "  MAJORHEADSORTORDER, "
        		            		+ "  T.MAJORHEAD, "
        		            		+ "  SUBHEADSORTORDER, "
        		            		+ "  SUBHEAD, "
        		            		+ "  T.CURRENT_MONTH_DEBIT, "
        		            		+ "  T.CURRENT_MONTH_CREDIT, "
        		            		+ "  CASE "
        		            		+ "    WHEN (NVL(( CURRENT_MONTH_DEBIT - CURRENT_MONTH_CREDIT ),0)) < 0 "
        		            		+ "    THEN (current_month_debit       - current_month_credit )*-1 "
        		            		+ "    ELSE (current_month_debit       - current_month_credit ) "
        		            		+ "  END AS curr_net, "
        		            		+ "  CASE "
        		            		+ "    WHEN ( current_month_debit - current_month_credit ) > 0 "
        		            		+ "    THEN ' Dr' "
        		            		+ "    ELSE ' Cr' "
        		            		+ "  END AS curr_net_disp "
        		            		+ "FROM "
        		            		+ "  (SELECT DISTINCT SUBHEAD, "
        		            		+ "    SUBHEADSORTORDER, "
        		            		+ "    MINORHEADSORTORDER, "
        		            		+ "    MINORHEAD, "
        		            		+ "    MAJORHEADSORTORDER, "
        		            		+ "    MAJORHEAD, "
        		            		+ "    SUM(CURRENT_MONTH_DEBIT)  AS CURRENT_MONTH_DEBIT, "
        		            		+ "    SUM(CURRENT_MONTH_CREDIT) AS CURRENT_MONTH_CREDIT "
        		            		+ "  FROM "
        		            		+ "    (SELECT AG.MINORHEAD, "
        		            		+ "      AG.MINORHEADSORTORDER, "
        		            		+ "      AG.MAJORHEAD, "
        		            		+ "      AG.MAJORHEADSORTORDER, "
        		            		+ "      AG.SUBHEAD, "
        		            		+ "      AG.SUBHEADSORTORDER, "
        		            		+ "      X.ACCOUNT_HEAD_CODE, "
        		            		+ "      (SELECT ACCOUNT_HEAD_DESC "
        		            		+ "      FROM com_mst_account_heads "
        		            		+ "      WHERE ACCOUNT_HEAD_CODE = X.ACCOUNT_HEAD_CODE "
        		            		+ "      )                         AS account_head_des, "
        		            		+ "      SUM(current_month_debit)  AS current_month_debit , "
        		            		+ "      SUM(current_month_credit) AS current_month_credit "
        		            		+ "    FROM "
        		            		+ "      (SELECT ftbr.account_head_code, "
        		            		+ "        ftbr.current_month_debit, "
        		            		+ "        ftbr.current_month_credit "
        		            		+ "      FROM FAS_TRIAL_BALANCE FTBR "
        		            		+ "      WHERE (ftbr.cashbook_year = "+finYearStart
        		            		+ "      AND ftbr.cashbook_month  >=4 "
        		            		+ "      AND FTBR.CASHBOOK_MONTH  <=12) "
        		            		+ "      OR (ftbr.cashbook_year    =  "+finYearEnd
        		            		+ "      AND ftbr.cashbook_month  >=1 "
        		            		+ "      AND ftbr.cashbook_month  <=3) "
        		            		+ "      UNION ALL "
        		            		+ "      SELECT ftbs.account_head_code, "
        		            		+ "        ftbs.current_month_debit, "
        		            		+ "        ftbs.current_month_credit "
        		            		+ "      FROM FAS_TRIAL_BALANCE_SUPPLEMENT FTBS "
        		            		+ "      WHERE FTBS.CASHBOOK_YEAR =  "+finYearEnd
        		            		+ "      ) x, "
        		            		+ "      fas_ho_annualgrouping ag "
        		            		+ "    WHERE X.ACCOUNT_HEAD_CODE =AG.HOA "
        		            		+ "    AND AG.MINORHEADSORTORDER = "+minorsortOrder   
        		            	/*	+ "    AND AG.MINORHEAD          ="+"'"+minorHead.trim()+"'"*/
        		            		+ "    AND AG.MINORHEAD          like "+"'%"+minorHeadarray[0].trim()+"%'"
        		            		+ "    AND AG.MAJORHEADSORTORDER = "+majorsortOrder
        		            		+ "    AND AG.MAJORHEAD          like "+"'%"+majorHeadarray[0].trim()+"%'"
        		            		+ "    AND AG.FINYEARSTART       =  "+finYearStart
        		            		+ "    AND AG.FINYEAREND         =  "+finYearEnd
        		            		+ "    GROUP BY AG.MAJORHEAD, "
        		            		+ "      MAJORHEADSORTORDER, "
        		            		+ "      AG.MINORHEAD, "
        		            		+ "      MINORHEADSORTORDER, "
        		            		+ "      AG.SUBHEAD, "
        		            		+ "      SUBHEADSORTORDER, "
        		            		+ "      ACCOUNT_HEAD_CODE "
        		            		+ "    ORDER BY Majorheadsortorder, "
        		            		+ "      minorheadsortorder, "
        		            		+ "      SUBHEADSORTORDER, "
        		            		+ "      ACCOUNT_HEAD_CODE "
        		            		+ "    ) "
        		            		+ "  GROUP BY SUBHEAD, "
        		            		+ "    SUBHEADSORTORDER, "
        		            		+ "    MINORHEADSORTORDER, "
        		            		+ "    MINORHEAD, "
        		            		+ "    MAJORHEADSORTORDER, "
        		            		+ "    MAJORHEAD "
        		            		+ "  ORDER BY MINORHEADSORTORDER "
        		            		+ "  )T";
        		            System.out.println("SQL::::" + sql);
        		            try {
        		                int count = 0;
        		                ps2 = con.prepareStatement(sql);
        		               /* ps2.setString(1, minorHead);
        		                ps2.setString(2, majorHead);*/
        		              
        		                xml =
        		 xml + "<flag>success</flag>";
        		                rs2 = ps2.executeQuery();
        		                while (rs2.next()) {
        		                	
        		                	xml = xml + "<leng>";
        		                	 xml = xml + "<minheadorder>" + rs2.getInt("MINORHEADSORTORDER") + "</minheadorder>";
        		                    xml = xml + "<minhead>" + rs2.getString("MINORHEAD") + "</minhead>";
        		                    xml = xml + "<majheadorder>" + rs2.getInt("MAJORHEADSORTORDER") + "</majheadorder>";
        		                    xml = xml + "<majhead>" + rs2.getString("MAJORHEAD") + "</majhead>";
        		                    xml = xml + "<subheadorder>" + rs2.getInt("SUBHEADSORTORDER") + "</subheadorder>";
        		                    xml = xml + "<subhead>" + rs2.getString("SUBHEAD") + "</subhead>";
        		                    xml = xml + "<debit>" + rs2.getString("CURRENT_MONTH_DEBIT") + "</debit>";
        		                    xml = xml + "<credit>"+ rs2.getString("CURRENT_MONTH_CREDIT") + "</credit>";
        		                    xml = xml + "<net>" + rs2.getString("curr_net") + "</net>";
        		                    xml = xml + "<netdisp>" + rs2.getString("curr_net_disp") + "</netdisp>";
        		                    xml = xml + "</leng>";
        		                    count++;
        		                }
        		                if (count == 0) {
        		                    System.out.println("inside count==0");
        		                    xml =
        		 "<response><command>load_Minor_Heads</command><flag>failure</flag>";
        		                }
        		            } catch (SQLException sqle) {
        		                sqle.printStackTrace();
        		                System.out.println("error while fetching data " + sqle);
        		                xml =
        		 "<response><command>load_Minor_Heads</command><flag>failure</flag>";
        		            }
        		            xml = xml + "</response>";
        		           out=response.getWriter();
        					out.println(xml);
        		            System.out.println(xml);
        		        }
        
        			  
        			    
        			    if (strType.equalsIgnoreCase("load_Acc_Heads")) {
        		        	String CONTENT_TYPE = "text/xml; charset=windows-1252";
        		            response.setContentType(CONTENT_TYPE);
        		            response.setHeader("Cache-Control", "no-cache");
        		            PrintWriter out = response.getWriter();
        		        	
        		            System.out.println("inside if");
        		            try {   
        		            	/*../../../../../Accounts_Hierachy.view?Command=load_Acc_Heads&minOrder=1&minHead=Employee Wefare Scheme  - Receipt&majOrder=1&
        		            			majHead=Income&subOrder=3&subHead=Employee Welfare Scheme Receipts&finYearStart=2016 &finYearEnd=2017   */
        		               	finYearStart = request.getParameter("finYearStart");
        		               	finYearEnd = request.getParameter("finYearEnd");
        		               	minorsortOrder=request.getParameter("minOrder");
        		               	minorHead=request.getParameter("minHead");
        		               	majorsortOrder=request.getParameter("majOrder");
        		               	majorHead=request.getParameter("majHead");
        		             	subsortOrder=request.getParameter("subOrder");
        		               	subHead=request.getParameter("subHead");
        		               	
        		               	majorHeadarray=majorHead.split(" ");
        		               	minorHeadarray=minorHead.split(" ");
        		               	subHeadarray=subHead.split(" ");

        		               }
        		                catch (Exception e) {
        		                   e.printStackTrace();
        		                					}
        		            System.out.println("finYearStart " + finYearStart);
        		            System.out.println("finYearEnd  " + finYearEnd);
        		            System.out.println("minorsortOrder  " + minorsortOrder);
        		            System.out.println("minorHead  " + minorHead);
        		            System.out.println("majorsortOrder  " + majorsortOrder);
        		            System.out.println("majorHead  " + majorHead);
        		            System.out.println("subOrder  " + subsortOrder);
        		            System.out.println("subHead  " + subHead);
        		            xml = "<response><command>load_Acc_Heads</command>";
        		        sql = ""
        		            		+ "SELECT T.MINORHEADSORTORDER, "
        		            		+ "  T.MINORHEAD, "
        		            		+ "  MAJORHEADSORTORDER, "
        		            		+ "  T.MAJORHEAD, "
        		            		+ "  SUBHEADSORTORDER, "
        		            		+ "  SUBHEAD, "
        		            		+ "  T.ACCOUNT_HEAD_CODE "
        		            		+ "  ||'-' "
        		            		+ "  || "
        		            		+ "  (SELECT ACCOUNT_HEAD_DESC "
        		            		+ "  FROM COM_MST_ACCOUNT_HEADS "
        		            		+ "  WHERE ACCOUNT_HEAD_CODE = T.ACCOUNT_HEAD_CODE "
        		            		+ "  ) AS account_head_des, "
        		            		+ "  T.CURRENT_MONTH_DEBIT, "
        		            		+ "  T.CURRENT_MONTH_CREDIT, "
        		            		+ "  CASE "
        		            		+ "    WHEN (NVL(( CURRENT_MONTH_DEBIT - CURRENT_MONTH_CREDIT ),0)) < 0 "
        		            		+ "    THEN (current_month_debit       - current_month_credit )*-1 "
        		            		+ "    ELSE (current_month_debit       - current_month_credit ) "
        		            		+ "  END AS curr_net, "
        		            		+ "  CASE "
        		            		+ "    WHEN ( current_month_debit - current_month_credit ) > 0 "
        		            		+ "    THEN ' Dr' "
        		            		+ "    ELSE ' Cr' "
        		            		+ "  END AS curr_net_disp "
        		            		+ "FROM "
        		            		+ "  (SELECT DISTINCT ACCOUNT_HEAD_CODE, "
        		            		+ "    SUBHEAD, "
        		            		+ "    SUBHEADSORTORDER, "
        		            		+ "    MINORHEADSORTORDER, "
        		            		+ "    MINORHEAD, "
        		            		+ "    MAJORHEADSORTORDER, "
        		            		+ "    MAJORHEAD, "
        		            		+ "    SUM(CURRENT_MONTH_DEBIT)  AS CURRENT_MONTH_DEBIT, "
        		            		+ "    SUM(CURRENT_MONTH_CREDIT) AS CURRENT_MONTH_CREDIT "
        		            		+ "  FROM "
        		            		+ "    (SELECT AG.MINORHEAD, "
        		            		+ "      AG.MINORHEADSORTORDER, "
        		            		+ "      AG.MAJORHEAD, "
        		            		+ "      AG.MAJORHEADSORTORDER, "
        		            		+ "      AG.SUBHEAD, "
        		            		+ "      AG.SUBHEADSORTORDER, "
        		            		+ "      X.ACCOUNT_HEAD_CODE, "
        		            		+ "      (SELECT ACCOUNT_HEAD_DESC "
        		            		+ "      FROM com_mst_account_heads "
        		            		+ "      WHERE ACCOUNT_HEAD_CODE = X.ACCOUNT_HEAD_CODE "
        		            		+ "      )                         AS account_head_des, "
        		            		+ "      SUM(current_month_debit)  AS current_month_debit , "
        		            		+ "      SUM(current_month_credit) AS current_month_credit "
        		            		+ "    FROM "
        		            		+ "      (SELECT ftbr.account_head_code, "
        		            		+ "        ftbr.current_month_debit, "
        		            		+ "        ftbr.current_month_credit "
        		            		+ "      FROM FAS_TRIAL_BALANCE FTBR "
        		            		+ "      WHERE (ftbr.cashbook_year =  "+finYearStart
        		            		+ "      AND ftbr.cashbook_month  >=4 "
        		            		+ "      AND FTBR.CASHBOOK_MONTH  <=12) "
        		            		+ "      OR (ftbr.cashbook_year    =  "+finYearEnd
        		            		+ "      AND ftbr.cashbook_month  >=1 "
        		            		+ "      AND ftbr.cashbook_month  <=3) "
        		            		+ "      UNION ALL "
        		            		+ "      SELECT ftbs.account_head_code, "
        		            		+ "        ftbs.current_month_debit, "
        		            		+ "        ftbs.current_month_credit "
        		            		+ "      FROM FAS_TRIAL_BALANCE_SUPPLEMENT FTBS "
        		            		+ "      WHERE FTBS.CASHBOOK_YEAR =  "+finYearEnd
        		            		+ "      ) x, "
        		            		+ "      fas_ho_annualgrouping ag "
        		            		+ "    WHERE X.ACCOUNT_HEAD_CODE =AG.HOA "
        		            		+ "    AND AG.MINORHEADSORTORDER = "+minorsortOrder
        		            		/*+ "    AND AG.MINORHEAD          ="+"'"+minorHead.trim()+"'"*/
        		            		+ "    AND AG.MINORHEAD          like"+"'%"+minorHeadarray[0].trim()+"%'"
        		            		+ "    AND AG.MAJORHEADSORTORDER = "+majorsortOrder
        		            		/*+ "    AND AG.MAJORHEAD          ="+"'"+majorHead.trim()+"'"*/
        		            		+ "    AND AG.MAJORHEAD          like"+"'%"+majorHeadarray[0].trim()+"%'"
        		            		+ "    AND AG.SUBHEADSORTORDER   = "+subsortOrder
        		            		
        		            		/*+ "    AND AG.SUBHEAD            ="+"'"+subHead.trim()+"'"*/
        		            		+ "    AND AG.SUBHEAD            like "+"'%"+subHeadarray[0].trim()+"%'"
        		            		+ "    AND AG.FINYEARSTART       =  "+finYearStart
        		            		+ "    AND AG.FINYEAREND         =  "+finYearEnd
        		            		+ "    GROUP BY AG.MAJORHEAD, "
        		            		+ "      MAJORHEADSORTORDER, "
        		            		+ "      AG.MINORHEAD, "
        		            		+ "      MINORHEADSORTORDER, "
        		            		+ "      AG.SUBHEAD, "
        		            		+ "      SUBHEADSORTORDER, "
        		            		+ "      ACCOUNT_HEAD_CODE "
        		            		+ "    ORDER BY Majorheadsortorder, "
        		            		+ "      minorheadsortorder, "
        		            		+ "      SUBHEADSORTORDER, "
        		            		+ "      ACCOUNT_HEAD_CODE "
        		            		+ "    ) "
        		            		+ "  GROUP BY SUBHEAD, "
        		            		+ "    SUBHEADSORTORDER, "
        		            		+ "    MINORHEADSORTORDER, "
        		            		+ "    MINORHEAD, "
        		            		+ "    MAJORHEADSORTORDER, "
        		            		+ "    MAJORHEAD, "
        		            		+ "    ACCOUNT_HEAD_CODE "
        		            		+ "  ORDER BY ACCOUNT_HEAD_CODE "
        		            		+ "  )T";
        		            	

        		            System.out.println("SQL::::" + sql);
        		            try {
        		                int count = 0;
        		                ps = con.prepareStatement(sql);
        		                xml =
        		 xml + "<flag>success</flag>";
        		                rs = ps.executeQuery();
        		                while (rs.next()) {
        		                	xml = xml + "<leng>";
        		                	 xml = xml + "<minheadorder>" + rs.getInt("MINORHEADSORTORDER") + "</minheadorder>";
         		                    xml = xml + "<minhead>" + rs.getString("MINORHEAD") + "</minhead>";
         		                    xml = xml + "<majheadorder>" + rs.getInt("MAJORHEADSORTORDER") + "</majheadorder>";
         		                    xml = xml + "<majhead>" + rs.getString("MAJORHEAD") + "</majhead>";
         		                    xml = xml + "<subheadorder>" + rs.getInt("SUBHEADSORTORDER") + "</subheadorder>";
         		                    xml = xml + "<subhead>" + rs.getString("SUBHEAD") + "</subhead>";
         		                   xml = xml + "<acchead>" + rs.getString("account_head_des") + "</acchead>";
         		                    xml = xml + "<debit>" + rs.getString("CURRENT_MONTH_DEBIT") + "</debit>";
         		                    xml = xml + "<credit>"+ rs.getString("CURRENT_MONTH_CREDIT") + "</credit>";
         		                    xml = xml + "<net>" + rs.getString("curr_net") + "</net>";
         		                    xml = xml + "<netdisp>" + rs.getString("curr_net_disp") + "</netdisp>";
        		                    xml = xml + "</leng>";
        		                    count++;
        		                }
        		                if (count == 0) {
        		                    System.out.println("inside count==0");
        		                    xml =
        		 "<response><command>load_Acc_Heads</command><flag>failure</flag>";
        		                }
        		            } catch (SQLException sqle) {
        		                sqle.printStackTrace();
        		                System.out.println("error while fetching data " + sqle);
        		                xml =
        		 "<response><command>load_Acc_Heads</command><flag>failure</flag>";
        		            }
        		            xml = xml + "</response>";
        					out.println(xml);
        					out.close();
        		            System.out.println(xml);
        		        }
        
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
