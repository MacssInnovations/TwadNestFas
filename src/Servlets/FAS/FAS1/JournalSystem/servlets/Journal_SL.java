/* Not in use*/
package Servlets.FAS.FAS1.JournalSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Journal_SL extends HttpServlet {

    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
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

        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null;
        PreparedStatement ps = null, ps2 = null, ps3 = null;
        //String xml="";
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        if (strCommand.equalsIgnoreCase("checkCode")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>checkCode</command>";
            int txtAcc_HeadCode = 0;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head ");
            }
            try {
                ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, txtAcc_HeadCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
   rs.getString("ACCOUNT_HEAD_DESC") + "</hdesc><BalType>" +
   rs.getString("BALANCE_TYPE") + "</BalType><SL_YN>" +
   rs.getString("SUB_LEDGER_TYPE_APPLICABLE") + "</SL_YN><rmk>" +
   rs.getString("REMARKS") + "</rmk>";

                    if (rs.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) {
                     int sl_cnt=0;
                    	ps =
  con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'"); //Status Field added in 05-07-2019
                        ps.setInt(1, txtAcc_HeadCode);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                           sl_cnt++;
                        	xml =
 xml + "<SLCODE>" + rs.getInt("SUB_LEDGER_TYPE_CODE") + "</SLCODE>";
                            System.out.println(rs.getInt("SUB_LEDGER_TYPE_CODE") +
                                               "code");
                            if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                                System.out.println("take SL DESC");
                                ps2 =
 con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                                ps2.setInt(1,
                                           rs.getInt("SUB_LEDGER_TYPE_CODE"));
                                rs2 = ps2.executeQuery();
                                if (rs2.next())
                                    xml =
 xml + "<SLDESC>" + rs2.getString("SUB_LEDGER_TYPE_DESC") + "</SLDESC>";
                            }
                        }
                        if(sl_cnt==0) {
                            System.out.println("STATUS is 'N'");
                            xml = xml + "<flag>failure</flag>";
                        }

                    }
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
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("loadcheckCode_grid")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>loadcheckCode_grid</command>";
            int txtAcc_HeadCode = 0;
            try {
                txtAcc_HeadCode =
                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
            } catch (Exception e) {
                System.out.println("Exception to catch account head");
            }
            try {
                ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC,BALANCE_TYPE,SUB_LEDGER_TYPE_APPLICABLE,REMARKS from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, txtAcc_HeadCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
   rs.getString("ACCOUNT_HEAD_DESC") + "</hdesc><BalType>" +
   rs.getString("BALANCE_TYPE") + "</BalType><SL_YN>" +
   rs.getString("SUB_LEDGER_TYPE_APPLICABLE") + "</SL_YN><rmk>" +
   rs.getString("REMARKS") + "</rmk>";

                    if (rs.getString("SUB_LEDGER_TYPE_APPLICABLE").equalsIgnoreCase("Y")) {
                    	int sl_cnt=0;
                    	ps =
  con.prepareStatement("select SUB_LEDGER_TYPE_CODE from FAS_APPLICABLE_SL_TYPE where ACCOUNT_HEAD_CODE=? and STATUS='Y'");//Status Field added in 05-07-2019
                        ps.setInt(1, txtAcc_HeadCode);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                        	sl_cnt++;
                        	xml =
 xml + "<SLCODE>" + rs.getInt("SUB_LEDGER_TYPE_CODE") + "</SLCODE>";
                            System.out.println(rs.getInt("SUB_LEDGER_TYPE_CODE") +
                                               "code");
                            if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
                                System.out.println("take SL DESC");
                                ps2 =
 con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
                                ps2.setInt(1,
                                           rs.getInt("SUB_LEDGER_TYPE_CODE"));
                                rs2 = ps2.executeQuery();
                                if (rs2.next())
                                    xml =
 xml + "<SLDESC>" + rs2.getString("SUB_LEDGER_TYPE_DESC") + "</SLDESC>";
                            }
                        }
                        if(sl_cnt==0) {
                            System.out.println("STATUS is 'N'");
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } else {
                    System.out.println("No record found");
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code grid." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        /*  else if(strCommand.equalsIgnoreCase("check_TB"))
          {
              String CONTENT_TYPE = "text/xml; charset=windows-1252";
              response.setContentType(CONTENT_TYPE);
              Calendar c;
              String xml="";
              Date txtCrea_date=null;
              int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
              System.out.println("check_TB if condi");
              xml="<response><command>check_TB</command>";

              try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);

              try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
              catch(NumberFormatException e){System.out.println("exception"+e );}
              System.out.println("cmbOffice_code "+cmbOffice_code);

              String[] sd=request.getParameter("TB_date").split("/"); // *** seee here getting TB_date date not " txtCrea_date " ***
              c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
              java.util.Date d=c.getTime();
              txtCrea_date=new Date(d.getTime());
              System.out.println("TB_date "+txtCrea_date);

              System.out.println("b4 getting month and year");
              try{txtCash_year=Integer.parseInt(sd[2]);}
                          catch(Exception e){System.out.println("exception"+e );}
                          System.out.println("txtCash_year "+txtCash_year);

                          try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                          catch(Exception e){System.out.println("exception"+e );}
                          System.out.println("txtCash_Month_hid "+txtCash_Month_hid);

              String[] sp=request.getParameter("TB_date").split("/");  // *** seee here getting TB_date date not " txtCrea_date " ***
              System.out.println(sp[0]+" "+sp[1]+" "+sp[2]);
              int check_year=Integer.parseInt(sp[2]);                 // to check in while loop
              int check_day=Integer.parseInt(sp[0]);                    // to check in while loop
                          System.out.println(Integer.parseInt(sp[2]));
                          System.out.println("here"+check_year);

               String check_date=request.getParameter("TB_date");    //  *** seee here getting TB_date date not " txtCrea_date " ***
               sp=request.getParameter("TB_date").split("/");
               check_date=sp[2]+"/"+sp[1]+"/"+sp[0];

               System.out.println(check_date); // to check in while loop with d/b date it converted to yyyy/mm/dd form
               try
               {
                   String sql1="select FINANCIAL_YEAR," +
                   "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ," +
                   "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
                   "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  " +
                   "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";

                // date is taken as string from database in above format for checking with receipt date variable ( check_date is string type)
                // checking of dates performed in form of String checking
                ps=con.prepareStatement(sql1);
                rs=ps.executeQuery();
                int Begin_yr,End_yr;
               while(rs.next())
               {
                   String[] yr=rs.getString("FINANCIAL_YEAR").split("-");
                    Begin_yr=Integer.parseInt(yr[0]);
                    End_yr=Integer.parseInt(yr[1]);
                                    System.out.println("while");
                                    System.out.println(Begin_yr+ " "+End_yr);
                                    System.out.println(rs.getString("mar_beg")+" "+rs.getString("mar_end"));

                   if(check_year==Begin_yr)          //   to check which financial year it belongs
                   {
                       if(txtCash_Month_hid>=4 && txtCash_Month_hid<=12)
                       {
                                System.out.println("if 4");
                                if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                                {
                                    txtCash_Month_hid=03;
                                System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                                }
                                else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                                {
                                    txtCash_Month_hid=04;
                                System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                                }
                                else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                                {
                                    txtCash_Month_hid=txtCash_Month_hid+1;
                                    if(txtCash_Month_hid>12)
                                        {
                                        txtCash_Month_hid=1;
                                        txtCash_year=txtCash_year+1;
                                        System.out.println("hello"+txtCash_year);
                                        }
                                    System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                                }
                                else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                                {
                                   //txtCash_Month_hid=txtCash_Month_hid;
                                   System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                                }
                       }

                   }
                   else  if(check_year==End_yr)
                   {
                       if(txtCash_Month_hid>=1 && txtCash_Month_hid<=3)
                       {
                           txtCash_year=End_yr;System.out.println("if 3");
                           if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                            {
                                txtCash_Month_hid=03;
                            System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                            }
                            else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                            {
                                txtCash_Month_hid=04;
                            System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                            }
                            else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                            {
                                txtCash_Month_hid=txtCash_Month_hid+1;
                                if(txtCash_Month_hid>12)
                                {
                                txtCash_Month_hid=1;
                                txtCash_year=txtCash_year+1;
                                System.out.println("hello"+txtCash_year);
                                }
                                System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                            }
                           else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                           {
                               //txtCash_Month_hid=txtCash_Month_hid;
                               System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                           }
                       }
                   }
               }
               ps.close();
               rs.close();

                  ps=con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
                     ps.setInt(1,cmbAcc_UnitCode);
                     ps.setInt(2,cmbOffice_code);
                     ps.setInt(3,txtCash_year);
                     ps.setInt(4,txtCash_Month_hid);
                     rs=ps.executeQuery();
                     //System.out.println(rs.next());
                  if(rs.next())
                   {
                      if(rs.getString("TB_STATUS").equalsIgnoreCase("N"))
                          xml=xml+"<flag>success</flag>";
                      else
                        xml=xml+"<flag>failure</flag>";
                   }
                  else
                      xml=xml+"<flag>failure</flag>";
                  System.out.println("tb_status..."+rs.getString("TB_STATUS"));
                 }
                  catch(Exception e)
                  {
                      System.out.println("catch..HERE.in TB_date "+e);
                      xml=xml+"<flag>failure</flag>";
                  }
              xml=xml+"</response>";
              System.out.println(xml);
              out.println(xml);
          }*/
        else if (strCommand.equalsIgnoreCase("loademp")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int eid = 0, offid = 0;
            String xml = "";
            System.out.println("loademp if condi");

            xml = "<response><command>loademp</command>";
            try {
                eid = Integer.parseInt(request.getParameter("eid"));
            } catch (Exception e) {
                System.out.println("Exception to catch emp id");
            }
            try {
                offid = Integer.parseInt(request.getParameter("offid"));
            } catch (Exception e) {
                System.out.println("Exception to catch office id");
            }
            try {
                ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? and c.OFFICE_ID=?");
                ps.setInt(1, eid);
                ps.setInt(2, offid);
                rs = ps.executeQuery();
                if (rs.next())
                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("dept")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>dept</command>";
            try {
                int oid = 0;
                String deptid = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                deptid = request.getParameter("deptid");
                ps2 =
 con.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?");
                ps2.setString(1, deptid);
                rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    xml =
 xml + "<flag>success</flag><dname>" + rs2.getString("OTHER_DEPT_NAME") +
   "</dname>";
                    ps2.close();
                    rs2.close();
                    ps2 =
 con.prepareStatement("select OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?");
                    ps2.setString(1, deptid);
                    ps2.setInt(2, oid);
                    rs2 = ps2.executeQuery();
                    if (rs2.next())
                        xml =
 xml + "<oname>" + rs2.getString("OTHER_DEPT_OFFICE_NAME") + "</oname>";
                    else
                        xml = xml + "<flag>failure</flag><err1>ofid</err1>";
                    ps2.close();
                    rs2.close();
                } else
                    xml = xml + "<flag>failure</flag><err>did</err>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("load_otherdept_office")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "", deptid = "";
            int dep_aliasid = 0;
            xml = "<response><command>load_otherdept_office</command>";
            try {
                dep_aliasid = Integer.parseInt(request.getParameter("deptid"));
            } catch (Exception e) {
                System.out.println("getting dept id failed");
            }

            try {

                xml = xml + "<flag>success</flag>";
                ps =
  con.prepareStatement("select OTHER_DEPT_ID from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ALIAS_ID=? ");
                ps.setInt(1, dep_aliasid);
                rs = ps.executeQuery();
                if (rs.next())
                    deptid = rs.getString("OTHER_DEPT_ID").trim();
                rs.close();
                ps.close();
                ps =
  con.prepareStatement("select OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? ");
                ps.setString(1, deptid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml =
 xml + "<cid>" + rs.getInt("OTHER_DEPT_OFFICE_ID") + "</cid><cname>" +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</cname>";
                }
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load other dept office." +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("office")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int oid = 0;
            xml = "<response><command>office</command>";
            try {
                oid = Integer.parseInt(request.getParameter("oid"));
            } catch (Exception e) {
                System.out.println("getting dept id failed");
            }

            try {
                // Error will occur ... query is incorrect format
                ps =
  con.prepareStatement("select OFFICE_ID, OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1, oid);
                rs = ps.executeQuery();
                System.out.println("inside offices");
                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
                    xml =
 xml + "<oid>" + rs.getInt("OFFICE_ID") + "</oid><oname>" +
   rs.getString("OFFICE_NAME") + "</oname>";
                } else {
                    xml = xml + "<oid>" + oid + "</oid>";
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e) {
                System.out.println("catch..HERE.in load office code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Load_SL_Code") ||
                   strCommand.equalsIgnoreCase("Load_MasterSL_Code")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
            int cmbSL_type = 0;
            //String deptid="";
            int y = 0;
            xml = "<response><command>" + strCommand + "</command>";
            try {
                cmbSL_type =
                        Integer.parseInt(request.getParameter("cmbSL_type"));
            } catch (Exception e) {
                System.out.println("error get SL_type");
            }
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("error get acc unit code");
            }
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (Exception e) {
                System.out.println("error get office id");
            }
            System.out.println("SL_TYPE " + cmbSL_type + "NN");
            System.out.println(cmbAcc_UnitCode);
            System.out.println(cmbOffice_code);
            xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
            if (cmbSL_type == 1) {
                System.out.println("here in supplier 1");
                try {
                    ps =
  con.prepareStatement("select SUPPLIER_ID,SUPPLIER_NAME from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("SUPPLIER_ID") + "</cid><cname>" +
   rs.getString("SUPPLIER_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 2) {
                System.out.println("here in Firms 2");
                try {
                    ps =
  con.prepareStatement("select FIRMS_ID, FIRMS_NAME from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("FIRMS_ID") + "</cid><cname>" +
   rs.getString("FIRMS_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load firms" + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 3) {
                System.out.println("here in assets 3");
                try {
                    ps =
  con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("ASSET_CODE") + "</cid><cname>" +
   rs.getString("ASSET_DESCRIPTION").trim() + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 4) {
                System.out.println("inside cheque 4 here ");
                try {
                    ps =
  con.prepareStatement("select  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("FIRMS_ID") + "</cid><cname>" +
   rs.getString("FIRMS_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load cheque book code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 5) {
                try {
                    // Error will occur ... query is incorrect format
                    ps =
  con.prepareStatement("select OFFICE_ID, OFFICE_NAME from COM_MST_OFFICES");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside offices 5");

                    int cnt = 0;
                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("OFFICE_ID") + "</cid><cname>" +
   rs.getString("OFFICE_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                    System.out.println(cnt + "count");
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load office code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 6) {
                try {
                    // Error will occur ... query is incorrect
                    ps =
  con.prepareStatement("select BANK_ID from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside 2");
                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 7) {
                int other_dept_off_alias_id = 0, oid = 0, cmbMas_SL_type = 0;
                String deptid = "";
                System.out.println("inside 7 employeees");
                try {

                    try {
                        cmbMas_SL_type =
                                Integer.parseInt(request.getParameter("cmbMas_SL_type"));
                    } catch (Exception e) {
                        System.out.println("getting master combo sl type failed");
                    }
                    try {
                        other_dept_off_alias_id =
                                Integer.parseInt(request.getParameter("other_dept_off_alias_id"));
                    } catch (Exception e) {
                        System.out.println("getting master combo slcode failed");
                    }
                    System.out.println("other_dept_off_alias_id.." +
                                       other_dept_off_alias_id);
                    //deptid=request.getParameter("deptid");
                    System.out.println(oid + " " +
                                       deptid); // OTHER_DEPT_ALIAS_ID
                    if (cmbMas_SL_type == 9) {
                        try {
                            System.out.println("inside other employee");
                            ps =
  con.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ALIAS_ID=? ");
                            ps.setInt(1, other_dept_off_alias_id);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                deptid = rs.getString("OTHER_DEPT_ID").trim();
                                oid = rs.getInt("OTHER_DEPT_OFFICE_ID");
                            }
                            rs.close();
                            ps.close();
                            System.out.println("hi");
                            ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and OFFICE_ID=? and DEPARTMENT_ID=? ");
                            ps.setInt(1, oid);
                            ps.setString(2, deptid);
                            rs = ps.executeQuery();

                            System.out.println("oid..dept.." + oid + "dep" +
                                               deptid);
                            while (rs.next()) {
                                System.out.println(rs.getInt("EMPLOYEE_ID"));
                                ps3 =
 con.prepareStatement("select EMPLOYEE_ID,max(SERVICE_LIST_SLNO) from HRM_EMP_SERVICE_DATA  where EMPLOYEE_STATUS_ID='DPN' and EMPLOYEE_ID=? and OFFICE_ID=? group by EMPLOYEE_ID");
                                ps3.setInt(1, rs.getInt("EMPLOYEE_ID"));
                                ps3.setInt(2, cmbOffice_code);
                                rs3 = ps3.executeQuery();
                                if (rs3.next()) {
                                    xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
   rs.getString("EMPLOYEE_NAME") + " - " + rs.getString("DESIGNATION") +
   "</cname>";
                                }
                                y++;
                            }
                            if (y != 0) {
                                xml = xml + "<flag>success</flag>";
                                rs3.close();
                                ps3.close();
                            } else
                                xml = xml + "<flag>failure</flag>";


                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else {
                        try {
                            ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and OFFICE_ID=? order by e.EMPLOYEE_NAME ");
                            ps.setInt(1, cmbOffice_code);
                            rs = ps.executeQuery();

                            while (rs.next()) {
                                xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
   rs.getString("EMPLOYEE_NAME") + " - " + rs.getString("DESIGNATION") +
   "</cname>";
                                y++;
                            }
                            if (y != 0) {
                                xml = xml + "<flag>success</flag>";
                            } else
                                xml = xml + "<flag>failure</flag>";

                            ps.close();
                            rs.close();
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } catch (Exception e) {
                    System.out.println("catch..HERE.in getting request value code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }

            } else if (cmbSL_type == 8) {
                // Error will occur ... query is incorrect
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside 8");

                    while (rs.next()) {

                    }
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 9) {
                System.out.println("inside 9 OTHER Departments");

                try {
                    ps =
  con.prepareStatement("select dep.OTHER_DEPT_NAME as OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFFICE_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                       ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ORDER BY dep.OTHER_DEPT_NAME");
                    rs = ps.executeQuery();

                    while (rs.next()) {

                        xml =
 xml + "<cid>" + rs.getInt("OTHER_DEPT_OFFICE_ALIAS_ID") + "</cid><cname>" +
   rs.getString("OTHER_DEPT_NAME") + " - " +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load department code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 10) {
                try {
                    System.out.println("inside 10 here projects");
                    ps =
  con.prepareStatement("select PROJECT_ID, PROJECT_NAME  from PMS_MST_PROJECTS_VIEW where  OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("PROJECT_ID") + "</cid><cname>" +
   rs.getString("PROJECT_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 11) {
                try {
                    System.out.println("inside 11 contractors");
                    ps =
  con.prepareStatement("select CONTRACTOR_ID,CONTRACTOR_NAME from PMS_MST_CONTRACTORS_VIEW where OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getString("CONTRACTOR_ID") + "</cid><cname>" +
   rs.getString("CONTRACTOR_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load contractor." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 12) {
                System.out.println("inside 12 scheme owner");
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {

                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load scheme." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 13) {
                System.out.println("inside 13 beneficiary");
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();
                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load beneficiary." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Load_SL_Code_grid")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
            int cmbSL_type = 0;
            int y = 0;
            xml = "<response><command>" + strCommand + "</command>";
            try {
                cmbSL_type =
                        Integer.parseInt(request.getParameter("cmbSL_type"));
            } catch (Exception e) {
                System.out.println("error get SL_type");
            }
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("error get acc unit code");
            }
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (Exception e) {
                System.out.println("error get office id");
            }
            System.out.println("SL_TYPE " + cmbSL_type + "NN");
            System.out.println(cmbAcc_UnitCode);
            System.out.println(cmbOffice_code);
            xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
            if (cmbSL_type == 1) {
                System.out.println("here in supplier 1");
                try {
                    ps =
  con.prepareStatement("select SUPPLIER_ID,SUPPLIER_NAME from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("SUPPLIER_ID") + "</cid><cname>" +
   rs.getString("SUPPLIER_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 2) {
                System.out.println("here in Firms 2");
                try {
                    ps =
  con.prepareStatement("select FIRMS_ID, FIRMS_NAME from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("FIRMS_ID") + "</cid><cname>" +
   rs.getString("FIRMS_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load firms" + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 3) {
                System.out.println("here in assets 3");
                try {
                    ps =
  con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("ASSET_CODE") + "</cid><cname>" +
   rs.getString("ASSET_DESCRIPTION").trim() + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 4) {
                System.out.println("inside cheque 4 here ");
                try {
                    ps =
  con.prepareStatement("select  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("FIRMS_ID") + "</cid><cname>" +
   rs.getString("FIRMS_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load cheque book code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 5) {
                try {
                    // Error will occur ... query is incorrect format
                    ps =
  con.prepareStatement("select OFFICE_ID, OFFICE_NAME from COM_MST_OFFICES");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside offices 5");

                    int cnt = 0;
                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("OFFICE_ID") + "</cid><cname>" +
   rs.getString("OFFICE_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                    System.out.println(cnt + "count");
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load office code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 6) {
                try {
                    // Error will occur ... query is incorrect
                    ps =
  con.prepareStatement("select BANK_ID from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside 2");
                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 7) {
                int other_dept_off_alias_id = 0, oid = 0, cmbMas_SL_type = 0;
                String deptid = "";
                System.out.println("inside 7 employeees");
                try {

                    try {
                        cmbMas_SL_type =
                                Integer.parseInt(request.getParameter("cmbMas_SL_type"));
                    } catch (Exception e) {
                        System.out.println("getting master combo sl type failed");
                    }
                    try {
                        other_dept_off_alias_id =
                                Integer.parseInt(request.getParameter("other_dept_off_alias_id"));
                    } catch (Exception e) {
                        System.out.println("getting master combo slcode failed");
                    }
                    System.out.println("other_dept_off_alias_id.." +
                                       other_dept_off_alias_id);
                    //deptid=request.getParameter("deptid");
                    System.out.println(oid + " " +
                                       deptid); // OTHER_DEPT_ALIAS_ID
                    if (cmbMas_SL_type == 9) {
                        try {
                            System.out.println("inside other employee");
                            ps =
  con.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ALIAS_ID=? ");
                            ps.setInt(1, other_dept_off_alias_id);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                deptid = rs.getString("OTHER_DEPT_ID").trim();
                                oid = rs.getInt("OTHER_DEPT_OFFICE_ID");
                            }
                            rs.close();
                            ps.close();
                            System.out.println("hi");
                            ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and OFFICE_ID=? and DEPARTMENT_ID=? ");
                            ps.setInt(1, oid);
                            ps.setString(2, deptid);
                            rs = ps.executeQuery();

                            System.out.println("oid..dept.." + oid + "dep" +
                                               deptid);
                            while (rs.next()) {
                                System.out.println(rs.getInt("EMPLOYEE_ID"));
                                ps3 =
 con.prepareStatement("select EMPLOYEE_ID,max(SERVICE_LIST_SLNO) from HRM_EMP_SERVICE_DATA  where EMPLOYEE_STATUS_ID='DPN' and EMPLOYEE_ID=? and OFFICE_ID=? group by EMPLOYEE_ID");
                                ps3.setInt(1, rs.getInt("EMPLOYEE_ID"));
                                ps3.setInt(2, cmbOffice_code);
                                rs3 = ps3.executeQuery();
                                if (rs3.next()) {
                                    xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
   rs.getString("EMPLOYEE_NAME") + " - " + rs.getString("DESIGNATION") +
   "</cname>";
                                }
                                y++;
                            }
                            if (y != 0) {
                                xml = xml + "<flag>success</flag>";
                                rs3.close();
                                ps3.close();
                            } else
                                xml = xml + "<flag>failure</flag>";


                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else {
                        try {
                            ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and OFFICE_ID=? order by e.EMPLOYEE_NAME ");
                            ps.setInt(1, cmbOffice_code);
                            rs = ps.executeQuery();

                            while (rs.next()) {
                                xml =
 xml + "<cid>" + rs.getInt("EMPLOYEE_ID") + "</cid><cname>" +
   rs.getString("EMPLOYEE_NAME") + " - " + rs.getString("DESIGNATION") +
   "</cname>";
                                y++;
                            }
                            if (y != 0) {
                                xml = xml + "<flag>success</flag>";
                            } else
                                xml = xml + "<flag>failure</flag>";

                            ps.close();
                            rs.close();
                        } catch (Exception e) {
                            System.out.println("catch..HERE.in load emp code." +
                                               e);
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                } catch (Exception e) {
                    System.out.println("catch..HERE.in getting request value code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }

            } else if (cmbSL_type == 8) {
                // Error will occur ... query is incorrect
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();
                    System.out.println("inside 8");

                    while (rs.next()) {

                    }
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 9) {
                System.out.println("inside 9 OTHER Departments");

                try {
                    ps =
  con.prepareStatement("select dep.OTHER_DEPT_NAME as OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFFICE_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                       ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ORDER BY dep.OTHER_DEPT_NAME");
                    rs = ps.executeQuery();

                    while (rs.next()) {

                        xml =
 xml + "<cid>" + rs.getInt("OTHER_DEPT_OFFICE_ALIAS_ID") + "</cid><cname>" +
   rs.getString("OTHER_DEPT_NAME") + " - " +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load department code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 10) {
                try {
                    System.out.println("inside 10 here projects");
                    ps =
  con.prepareStatement("select PROJECT_ID, PROJECT_NAME  from PMS_MST_PROJECTS_VIEW where  OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("PROJECT_ID") + "</cid><cname>" +
   rs.getString("PROJECT_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 11) {
                try {
                    System.out.println("inside 11 contractors");
                    ps =
  con.prepareStatement("select CONTRACTOR_ID,CONTRACTOR_NAME from PMS_MST_CONTRACTORS_VIEW where OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getString("CONTRACTOR_ID") + "</cid><cname>" +
   rs.getString("CONTRACTOR_NAME") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load contractor." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 12) {
                System.out.println("inside 12 scheme owner");
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {

                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load scheme." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 13) {
                System.out.println("inside 13 beneficiary");
                try {
                    ps =
  con.prepareStatement("select from where OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();
                    xml = xml + "<flag>success</flag>";
                    while (rs.next()) {

                    }
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load beneficiary." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 0) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (strCommand.equalsIgnoreCase("Load_Journal_MasterSL_Code")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
            int cmbSL_type = 0;
            int x = 0;
            xml = "<response><command>" + strCommand + "</command>";
            try {
                cmbSL_type =
                        Integer.parseInt(request.getParameter("cmbSL_type"));
            } catch (Exception e) {
                System.out.println("error get SL_type");
            }
            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("error get acc unit code");
            }
            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (Exception e) {
                System.out.println("error get office id");
            }
            System.out.println("SL_TYPE " + cmbSL_type + "NN");
            System.out.println(cmbAcc_UnitCode);
            System.out.println(cmbOffice_code);
            xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
            /*
                    // here only some of the sub-ledgers used,,.. only needed SL types retrieved..
              */
            if (cmbSL_type == 11) {
                try {
                    System.out.println("inside 11 contractor ..s");
                    ps =
  con.prepareStatement("select CONTRACTOR_ID,CONTRACTOR_NAME from PMS_MST_CONTRACTORS_VIEW where OFFICE_ID=? ");
                    ps.setInt(1, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("CONTRACTOR_ID") + "</cid><cname>" +
   rs.getString("CONTRACTOR_NAME") + "</cname>";
                        x++;
                    }
                    if (x != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();

                } catch (Exception e) {
                    System.out.println("catch..HERE.in load contractor." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type ==
                       9) // need not worry about other department employee,here only current office employee
            {

                System.out.println("inside 9 OTHER Departments");

                try {
                    ps =
  con.prepareStatement("select dep.OTHER_DEPT_NAME as OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFFICE_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                       ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID ORDER BY dep.OTHER_DEPT_NAME");
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("OTHER_DEPT_OFFICE_ALIAS_ID") + "</cid><cname>" +
   rs.getString("OTHER_DEPT_NAME") + " - " +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</cname>";

                        x++;
                    }
                    if (x != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load department code." +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
                /* int other_dept_off_alias_id=0, oid=0,cmbMas_SL_type=0;
                     String deptid="";
                     System.out.println("inside 7 employeees");
                     try{
                       try{ cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}catch(Exception e){System.out.println("getting master combo sl type failed");}
                        try{other_dept_off_alias_id=Integer.parseInt(request.getParameter("other_dept_off_alias_id"));}catch(Exception e){System.out.println("getting master combo slcode failed");}
                       System.out.println("other_dept_off_alias_id.."+other_dept_off_alias_id);
                         //deptid=request.getParameter("deptid");
                         System.out.println(oid+" "+deptid);              // OTHER_DEPT_ALIAS_ID

                          try {
                                  ps=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and OFFICE_ID=? order by e.EMPLOYEE_NAME ");
                                  ps.setInt(1,cmbOffice_code);
                                  rs=ps.executeQuery();
                                  xml=xml+"<flag>success</flag>";
                                  while(rs.next())
                                  {
                                      xml=xml+"<cid>"+rs.getInt("EMPLOYEE_ID")+
                                      "</cid><cname>"+rs.getString("EMPLOYEE_NAME")+" - "+rs.getString("DESIGNATION")+
                                      "</cname>";
                                  }
                              }
                              catch(Exception e)
                              {
                              System.out.println("catch..HERE.in load emp code."+e);
                              xml=xml+"<flag>failure</flag>";
                              }
                         //}
                   }
                  catch(Exception e)
                  {
                  System.out.println("catch..HERE.in getting request value code."+e);
                  xml=xml+"<flag>failure</flag>";
                  }*/


            } else if (cmbSL_type == 10) // No instruction from client...
            {
                xml = xml + "<flag>failure</flag>";
            } else if (cmbSL_type == 11) {
                xml = xml + "<flag>failure</flag>";
            } else if (cmbSL_type == 1) {
                System.out.println("here in supplier 1");
                try {
                    ps =
  con.prepareStatement("select SUPPLIER_ID,SUPPLIER_NAME from COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("SUPPLIER_ID") + "</cid><cname>" +
   rs.getString("SUPPLIER_NAME") + "</cname>";
                        x++;
                    }
                    if (x != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load supplier." + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (cmbSL_type == 2) {
                System.out.println("here in Firms 2");
                try {
                    ps =
  con.prepareStatement("select FIRMS_ID, FIRMS_NAME from COM_FIRMS_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        xml =
 xml + "<cid>" + rs.getInt("FIRMS_ID") + "</cid><cname>" +
   rs.getString("FIRMS_NAME") + "</cname>";
                        x++;
                    }
                    if (x != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else
                        xml = xml + "<flag>failure</flag>";

                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load firms" + e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    }

}
