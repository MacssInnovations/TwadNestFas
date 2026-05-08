package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.sql.Date;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Journal_Integration_Heads extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        ResultSet results2 = null;
        PreparedStatement ps = null;

        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
        String strCommand = "",display="";
        String xml = "";
        int txtAcc_HeadCode=0,txtorerno=0;
        int JournalTypeCode = 0;
        String datewef="";
        String Category = "";
        Date dateentry= null;
        Calendar c;
        
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
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);


        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    	 
    	   
    	if(strCommand.equalsIgnoreCase("loadcolumn"))
    	{
    	String moduleid1=request.getParameter("moduleid");
     	System.out.println("moduleid==="+moduleid1);
        if("PEN".equalsIgnoreCase(moduleid1))
       {
    	   xml="<response>";
    	   xml=xml+"<command>Pension</command>";
    	   try
    	   {
    		   String sql="SELECT column_name as columnName From User_Tab_Cols Where Table_Name=Upper('hrm_pen_pay_final_abstract') and ((column_id between 12 and 29) or column_id=37) order by column_id";
    		   //String sqr="select  column_name from USER_TAB_COLUMNS where table_name  = 'HRM_PEN_PAY_FINAL_ABSTRACT' and ((column_id >=12 and column_id<=29) or column_id=37)";
    		   statement=connection.createStatement();
    		   results=statement.executeQuery(sql);
    		   while(results.next())
    		   {
    			   xml=xml+"<colName>"+results.getString("columnName")+"</colName>";
    		   }
    		   xml=xml+"<flag>success</flag>";
    	   }
    	   catch(Exception e)
    	   {
    		   e.printStackTrace();
    		   xml=xml+"<flag>failure</flag>";
    	   }
    	   xml=xml+"</response>";
    	   
       } 
      }
       else if(strCommand.equalsIgnoreCase("Add"))
       {
    	   System.out.println("*******************ADDDDDD***************");
    	   String moduleid=request.getParameter("moduleid");
    	   System.out.println("moduleid==="+moduleid);
       
    	   String cmbjournalised=request.getParameter("cmbjournalised");
    	   System.out.println("jounal==="+cmbjournalised);
    	   try{
    		   txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
    	       System.out.println("txtAcc_HeadCode"+txtAcc_HeadCode);
    	   }catch(Exception e)
    	   {
    		   e.printStackTrace();
    	   }
    	   try
    	   {
    		   txtorerno=Integer.parseInt(request.getParameter("txtorerno"));
    		   System.out.println("txtorerno"+txtorerno);
    	   }
    	   catch(Exception e)
    	   {
    		   e.printStackTrace();
    		   
    	   }
    	   String cr_dr=request.getParameter("check_cr_dr");
    	   System.out.println("cr_dr"+cr_dr);
    	 
    	   String typepf=request.getParameter("typepf");
    	   System.out.println("typepf"+typepf);
    	   datewef=request.getParameter("txtdatewef");
    	   System.out.println("datewef"+datewef);
    	   xml="<response>";
    	   xml=xml+"<command>Add</command>";
    	   try
    	   {
    		ps=connection.prepareStatement("select ACCOUNT_HEAD_CODE from FAS_INTEGRATION_JOURNAL_HEADS where MODULE_ID=? and COL_TO_BE_JOURNALISED=? and ACCOUNT_HEAD_CODE=? ");
    		ps.setString(1, moduleid);
            ps.setString(2, cmbjournalised);
            ps.setInt(3, txtAcc_HeadCode);
            results=ps.executeQuery();
            if (results.next()) {
				xml = xml + "<flag>Exist</flag>";
			} else {
    	   String sql= "insert into FAS_INTEGRATION_JOURNAL_HEADS(MODULE_ID,COL_TO_BE_JOURNALISED,ACCOUNT_HEAD_CODE,DATE_WEF,CR_DR_INDICATOR,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,TYPE,ORDER_NO) values(?,?,?,?,?,?,?,?,?,?)";
           ps=connection.prepareStatement(sql);
           ps.setString(1, moduleid);
           ps.setString(2, cmbjournalised);
           ps.setInt(3, txtAcc_HeadCode);
           if(datewef.equals(""))
               ps.setNull(4,Types.DATE);
           else
           {
               String[] sd2 =datewef.split("/");
               c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
               java.util.Date d2 = c.getTime();
               dateentry = new Date(d2.getTime());
               System.out.println("receiptdate " + dateentry);
               ps.setDate(4, dateentry);    
           }
           ps.setString(5, cr_dr);
           ps.setString(6, "L");
           ps.setString(7, userid);
           ps.setTimestamp(8, ts);
           ps.setString(9, typepf);
           ps.setInt(10, txtorerno);
           ps.executeUpdate();
           
           xml=xml+"<ModuleID>"
                  +moduleid
                  +"</ModuleID>";
           xml=xml+"<ColJourname>"
                  +cmbjournalised
                  +"</ColJourname>";
           xml=xml+"<AccountHeadCode>"
                  +txtAcc_HeadCode
                  +"</AccountHeadCode>";
           xml=xml+"<DateWef>"
                  +dateentry
                  +"</DateWef>";
           xml=xml+"<CRDR>"
                  +cr_dr
                  +"</CRDR>";
          xml=xml+"<Type>"
                 +typepf
                 +"</Type>";
          xml=xml+"<OrderNo>"
                 +txtorerno
                 +"</OrderNo>";
           
           xml = xml + "<flag>success</flag>";
			
		}
           
       }
    	   catch(Exception e)
       {
    	   e.printStackTrace();
    	   xml = xml + "<flag>failure</flag>";
       }
       xml=xml+"</response>";
       
       }else if(strCommand.equalsIgnoreCase("loadData"))
       {
    	   xml="<response>";
    	   xml=xml+"<command>loadData</command>"; 
    	   int count=0;
    	   try
    	   {
    		   String sql="select rowid,MODULE_ID,COL_TO_BE_JOURNALISED,ACCOUNT_HEAD_CODE,to_char(DATE_WEF,'dd-mm-yyyy')as DATEWEF,CR_DR_INDICATOR,TYPE,ORDER_NO from FAS_INTEGRATION_JOURNAL_HEADS order by UPDATED_DATE desc";
    		   ps=connection.prepareStatement(sql);
    		   results=ps.executeQuery();
    		   while(results.next())
    		   {
    			   xml=xml+"<rowid>"
                   +results.getString("rowid")
                   +"</rowid>";
                xml=xml+"<ModuleID>"
                       +results.getString("MODULE_ID")
                       +"</ModuleID>";
                xml=xml+"<ColJourname>"
                       +results.getString("COL_TO_BE_JOURNALISED")
                       +"</ColJourname>";
                xml=xml+"<AccountHeadCode>"
                       +results.getString("ACCOUNT_HEAD_CODE")
                       +"</AccountHeadCode>";
                xml=xml+"<DateWef>"
                       +results.getString("DATEWEF")
                       +"</DateWef>";
                xml=xml+"<CRDR>"
                       +results.getString("CR_DR_INDICATOR")
                       +"</CRDR>";
                xml=xml+"<Type>"
                       +results.getString("TYPE")
                       +"</Type>";
                xml=xml+"<OrderNo>"
                       +results.getInt("ORDER_NO")
                       +"</OrderNo>";
                count++;
    		   }
    		   if(count>0)
    		   {
    		   xml = xml + "<flag>success</flag>";
    		   xml = xml +"<count>"+count+"</count>";
    		   }
    		   else
    		   {
    			xml = xml + "<flag>failure</flag>";
    		   }
    	   }catch(Exception e)
    	   {
    		   e.printStackTrace();
    		   xml = xml + "<flag>failure</flag>";
    	   }
    	   xml=xml+"</response>";
       }
       else if(strCommand.equalsIgnoreCase("updatedata"))
       {
    	   String moduleid=request.getParameter("moduleid");
    	   System.out.println("moduleid==="+moduleid);
       
    	   String cmbjournalised=request.getParameter("cmbjournalised");
    	   System.out.println("jounal==="+cmbjournalised);
    	   try{
    		   txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
    	       System.out.println("txtAcc_HeadCode"+txtAcc_HeadCode);
    	   }catch(Exception e)
    	   {
    		   e.printStackTrace();
    	   }
    	   try
    	   {
    		   txtorerno=Integer.parseInt(request.getParameter("txtorerno"));
    		   System.out.println("txtorerno"+txtorerno);
    	   }
    	   catch(Exception e)
    	   {
    		   e.printStackTrace();
    		   
    	   }
    	   String cr_dr=request.getParameter("check_cr_dr");
    	   System.out.println("cr_dr"+cr_dr);
    	 
    	   String typepf=request.getParameter("typepf");
    	   System.out.println("typepf"+typepf);
    	   datewef=request.getParameter("txtdatewef");
    	   System.out.println("datewef"+datewef);
    	   xml="<response>";
    	   xml=xml+"<command>updatedata</command>"; 
    	   try
    	   {
    	    String sql="update FAS_INTEGRATION_JOURNAL_HEADS set DATE_WEF=?,CR_DR_INDICATOR=?,TYPE=?,ORDER_NO=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,STATUS=? where MODULE_ID=? and COL_TO_BE_JOURNALISED=? and ACCOUNT_HEAD_CODE=?";
    	    ps=connection.prepareStatement(sql);
    	    
    	    if(datewef.equals(""))
                ps.setNull(1,Types.DATE);
            else
            {
                String[] sd2 =datewef.split("/");
                c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                java.util.Date d2 = c.getTime();
                dateentry = new Date(d2.getTime());
                System.out.println("receiptdate " + dateentry);
                ps.setDate(1, dateentry);    
            }
    	    System.out.println("dateentry::::"+dateentry);
    	    ps.setString(2, cr_dr);
    	    ps.setString(3, typepf);
            ps.setInt(4, txtorerno);
            ps.setString(5, userid);
            ps.setTimestamp(6, ts);
            ps.setString(7, "L");
            ps.setString(8, moduleid);
            ps.setString(9, cmbjournalised);
            ps.setInt(10, txtAcc_HeadCode);
            ps.executeUpdate();
            xml = xml + "<flag>success</flag>";
            xml = xml + "<id>"
	                  + (moduleid + cmbjournalised+ txtAcc_HeadCode+dateentry+cr_dr
	                  +typepf+txtorerno)
	                  + "</id>";
    	   }catch(Exception e)
    	   {
    		   e.printStackTrace();
    		   xml = xml + "<flag>failure</flag>";
    	   }
    	   xml=xml+"</response>";
       }else if(strCommand.equalsIgnoreCase("deletedata"))
       {
    	   String moduleid=request.getParameter("moduleid");
    	   System.out.println("moduleid==="+moduleid);
       
    	   String cmbjournalised=request.getParameter("cmbjournalised");
    	   System.out.println("jounal==="+cmbjournalised);
    	   try{
    		   txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
    	       System.out.println("txtAcc_HeadCode"+txtAcc_HeadCode);
    	   }catch(Exception e)
    	   {
    		   e.printStackTrace();
    	   }
    	  
    	   String row_id=request.getParameter("row_id");
    	   
    	   String sql="";
    	   xml="<response>";
    	   xml=xml+"<command>deletedata</command>"; 
    	  
    	   try
    	   {
				if (cmbjournalised.equalsIgnoreCase("valid")) {
					if (txtAcc_HeadCode == 10) {
						sql = "delete from FAS_INTEGRATION_JOURNAL_HEADS where rowid='"
								+ row_id
								+ "' and MODULE_ID='"
								+ moduleid
								+ "' and COL_TO_BE_JOURNALISED is null and ACCOUNT_HEAD_CODE is null";
					} else if (txtAcc_HeadCode != 10) {
						sql = "delete from FAS_INTEGRATION_JOURNAL_HEADS where rowid='"
								+ row_id
								+ "' and MODULE_ID='"
								+ moduleid
								+ "' and COL_TO_BE_JOURNALISED is null and ACCOUNT_HEAD_CODE="
								+ txtAcc_HeadCode;
					}
				} else if (!cmbjournalised.equalsIgnoreCase("valid")) {
					if (txtAcc_HeadCode == 10) {
						sql = "delete from FAS_INTEGRATION_JOURNAL_HEADS where rowid='"
								+ row_id
								+ "' and MODULE_ID='"
								+ moduleid
								+ "' and COL_TO_BE_JOURNALISED ='"
								+ cmbjournalised
								+ "' and ACCOUNT_HEAD_CODE is null";
					} else if (txtAcc_HeadCode != 10) {
						sql = "delete from FAS_INTEGRATION_JOURNAL_HEADS where rowid='"
								+ row_id
								+ "' and MODULE_ID='"
								+ moduleid
								+ "' and COL_TO_BE_JOURNALISED='"
								+ cmbjournalised
								+ "' and ACCOUNT_HEAD_CODE="
								+ txtAcc_HeadCode;
					}
				}
    	    ps=connection.prepareStatement(sql);
    	    System.out.println(txtAcc_HeadCode+"Delete >> "+sql);
    	  /*  ps.setString(1, row_id);
    	   * 
    	    ps.setString(2, moduleid);
            ps.setString(3, cmbjournalised);
            ps.setInt(4, txtAcc_HeadCode);*/
            ps.executeUpdate();
            xml = xml + "<flag>success</flag>";
            xml = xml + "<id>"
			          + (moduleid + cmbjournalised+ txtAcc_HeadCode )
			          + "</id>";
            
    	   }catch(Exception e)
    	   {
    		   e.printStackTrace();
    		   xml = xml + "<flag>failure</flag>";
    		   
    	   }
    	   xml = xml+"</response>";
       }  else if (strCommand.equalsIgnoreCase("HeadCode")) {

           String headcode = request.getParameter("txtAcc_Head_code");
           int headcodeno = Integer.parseInt(headcode);
           xml = "<response><command>HeadCode</command>";
           try {
               ps =
            	   connection.prepareStatement("select account_head_desc from com_mst_account_heads where account_head_code=?");
               ps.setInt(1, headcodeno);
               results = ps.executeQuery();
               if (results.next()) {
                   xml =
xml + "<flag>success</flag><headcode>" + results.getString("account_head_desc") +
  "</headcode>";
               } else {
                   xml = xml + "<flag>failure</flag>";
               }
               xml = xml + "</response>";
           } catch (Exception e) {
               System.out.println("Exception in HeadCode:" + e);
           }


       }

       System.out.println(xml);
       pw.println(xml);
    }
}

