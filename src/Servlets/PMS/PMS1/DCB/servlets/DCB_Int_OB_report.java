package Servlets.PMS.PMS1.DCB.servlets;
/*		Module	 :DCB
 * 		Document :Opening Balance Report
 * 		Author	 :Panneer Selvam.K
 * 		Date	 :20/02/2010
 * */
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DCB_Int_OB_report extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public ResultSet rs = null;
    public Connection con;
    public String qry = "";
    public String xml = " ";
    public PrintWriter pr = null;


    public DCB_Int_OB_report() {
        super();
    }


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {

        Controller obj = new Controller();
        String userid = "0", Office_id = "", Office_Name = "";

        try {
            con = obj.con();
            obj.createStatement(con);

        } catch (Exception e) {
        }
        try {

            con = obj.con();
            obj.createStatement(con);
            HttpSession session = request.getSession(false);
            try {
                userid = (String)session.getAttribute("UserId");
            } catch (Exception e) {
                userid = "0";
            }

            if (userid == null) {
                userid = "0";
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }


            Office_id =
                    obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" +
                                 userid + "')");
            if (Office_id.equals(""))
                Office_id = "0";

        } catch (Exception e) {
        }

        try {
            String year = obj.setValue("YEAR", request);
            String bentype = obj.setValue("bentype", request);
            String process_code = obj.setValue("process_code", request);
            String cond = "";
            if (process_code.equals("1")) {
                cond = "where OFFICE_ID=" + Office_id;
            } else if (process_code.equals("2")) {
                cond =
" where BENEFICIARY_TYPE_ID=" + bentype + " and OFFICE_ID=" + Office_id;
            }

            qry =
 " select " + " BEN.BENEFICIARY_NAME," + " BEN.BENEFICIARY_TYPE_ID," +
   " BEN.OFFICE_ADDRESS1," + " BEN.OFFICE_ADDRESS2," +
   " BEN.OFFICE_ADDRESS3," + " BEN.OFFICE_PIN_CODE," +
   " BEN.OFFICE_LANDLINE_NO," + " BEN.OFFICE_MOBILE_NO," + " OB.ob," +
   " OB.addifany," + " OB.collection," + " OB.yesteryear," +
   " OB.currentyear," + " OB.demandupto" + " from " + " (" + " (" +
   " select " + " BENEFICIARY_SNO," + " BENEFICIARY_NAME," +
   " BENEFICIARY_TYPE_ID," + " DISTRICT_CODE," + " BLOCK_SNO," +
   " OFFICE_ADDRESS1," + " OFFICE_ADDRESS2," + " OFFICE_ADDRESS3," +
   " OFFICE_PIN_CODE," + " OFFICE_LANDLINE_NO," + " OFFICE_MOBILE_NO " +
   " from  " + " PMS_DCB_MST_BENEFICIARY " + cond + " order by  " +
   " BENEFICIARY_TYPE_ID " + " )BEN  " + " join " + " ( " + " select " +
   "     BENEFICIARY_SNO, " + "     OB_MAINTENANCE_CHARGES as ob," +
   "     ADDN_UPTO_PMTH_MCHARGES as addifany," +
   "     ADDN_UPTO_PMTH_MCHARGES as collection," +
   "     OB_YESTER_YR_upto_2004 as yesteryear," +
   "     OB_YESTER_YR_2004_CY as currentyear," +
   "     COLN_UPTO_PMTH_WCHARGES as demandupto" + " from  " +
   " PMS_DCB_OB_YEARLYTEST where FIN_YEAR=" + year + " )OB  " +
   " on OB.BENEFICIARY_SNO=BEN.BENEFICIARY_SNO  " + " )";
            xml = "<result>";
            int prow = 0;
            obj.testQry(qry);
            rs = obj.getRS(qry);
            while (rs.next()) {
                prow++;
                String address1 = "", address2 = "", address3 = "";
                address1 = obj.isNull(rs.getString("OFFICE_ADDRESS1"), 2);
                address2 = obj.isNull(rs.getString("OFFICE_ADDRESS2"), 2);
                address3 = obj.isNull(rs.getString("OFFICE_ADDRESS3"), 2);
                xml +=
"<BENEFICIARY_NAME>" + obj.isNull(rs.getString("BENEFICIARY_NAME"), 2) +
 "</BENEFICIARY_NAME>";
                xml += "<address1>" + address1 + "</address1>";
                xml += "<address2>" + address2 + "</address2>";
                xml += "<address3>" + address3 + "</address3>";
                xml += "<ob>" + obj.isNull(rs.getString("ob"), 2) + "</ob>";
                xml +=
"<addifany>" + obj.isNull(rs.getString("addifany"), 2) + "</addifany>";
                xml +=
"<collection>" + obj.isNull(rs.getString("collection"), 2) + "</collection>";
                xml +=
"<yesteryear>" + obj.isNull(rs.getString("yesteryear"), 2) + "</yesteryear>";
                xml +=
"<currentyear>" + obj.isNull(rs.getString("currentyear"), 2) +
 "</currentyear>";
                xml +=
"<demandupto>" + obj.isNull(rs.getString("demandupto"), 2) + "</demandupto>";
            }
            xml += "<prow>" + prow + "</prow>";
            xml += "</result>";
            obj.testQry(xml);
            PrintWriter pr = response.getWriter();
            pr.write(xml);

            pr.close();
            pr.flush();
            obj.conClose(con);


        } catch (Exception e) {
            System.out.println("----------------\n" + e);
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {

    }

}
