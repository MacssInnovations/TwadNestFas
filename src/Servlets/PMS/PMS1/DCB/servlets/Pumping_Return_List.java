package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Pumping_Return_List
 */
public class Pumping_Return_List extends HttpServlet {


    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    Controller obj = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pumping_Return_List() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        // TODO Auto-generated method stub
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        obj = new Controller();

        /// VAR
        int row_count = 0;
        String xml = "<result>";
        String str_qry = "";
        //

        try {
            con = obj.con();
            obj.createStatement(con);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String cond = "(1=1)";
        String month = obj.setValue("month", request);
        String year = obj.setValue("year", request);
        String process = obj.setValue("process", request);
        String divsno = obj.setValue("subdiv", request);
        String bentype = obj.setValue("bentype", request);
        String condbase = "";
        String command =
            request.getParameter("command"); //Command ->view,insert etc
        String msg = "-";
        String subcount = "";
        if (command == null || command.equals(""))
            command = "no command";
        String bensno =
            request.getParameter("ben_sno"); //Command ->view,insert etc
        if (bensno == null || bensno.equals(""))
            bensno = "0";
        if (command.equals("show")) {
            if (process.equals("1")) {
                cond = cond;

            }
            if (process.equals("2")) {
                cond += " and SUBDIV_OFFICE_ID=" + divsno;

            }
            if (process.equals("3")) {
                cond += " and SUBDIV_OFFICE_ID=" + divsno;
                condbase = "where BENEFICIARY_TYPE_ID=" + bentype;
            }
            try {

                str_qry =
                        " select BEN.BENEFICIARY_SNO,BENEFICIARY_TYPE_ID, " + " BEN.BENEFICIARY_NAME " +
                        ",BTYPE.BEN_TYPE_DESC ,PUMP.qty ,PUMP1.pr_record from  " +
                        "(" + "( select  BENEFICIARY_TYPE_ID, " +
                        " BENEFICIARY_NAME,BENEFICIARY_SNO	 " +
                        " from PMS_DCB_MST_BENEFICIARY  " + condbase +
                        " )BEN " + "  left outer  JOIN " + "( " + " select " +
                        "BEN_TYPE_DESC ,BEN_TYPE_ID " +
                        " from PMS_DCB_BEN_TYPE " + ")BTYPE " +
                        "  ON BTYPE.BEN_TYPE_ID = BEN.BENEFICIARY_TYPE_ID " +
                        " JOIN " + " ( " +
                        " select BENEFICIARY_SNO,sum(QTY_CONSUMED) as qty  from  PMS_DCB_TRN_MONTHLY_PR  " +
                        " where " + cond + " and MONTH=" + month +
                        " and YEAR=" + year + " group by BENEFICIARY_SNO " +
                        ")PUMP " +
                        "  ON PUMP.BENEFICIARY_SNO = BEN.BENEFICIARY_SNO " +
                        " left outer JOIN " + " ( " +
                        " select  BENEFICIARY_SNO,count(*) as pr_record  from  PMS_DCB_TRN_MONTHLY_PR  " +
                        " where " + cond + " and MONTH=" + month +
                        " and YEAR=" + year +
                        " and PR_SNO  in (select PR_SNO from PMS_DCB_WC_BILLING) group by BENEFICIARY_SNO " +
                        ")PUMP1 " +
                        "  ON PUMP1.BENEFICIARY_SNO = BEN.BENEFICIARY_SNO " +
                        ")";
                obj.testQry(str_qry);
                ResultSet rs = obj.getRS(str_qry);
                row_count = 0;


                while (rs.next()) {
                    row_count++;
                    xml +=
"<BENEFICIARY_SNO>" + obj.isNull(rs.getString("BENEFICIARY_SNO"), 1) +
 "</BENEFICIARY_SNO>";
                    xml +=
"<BENEFICIARY_NAME>" + obj.isNull(rs.getString("BENEFICIARY_NAME"), 2) +
 "</BENEFICIARY_NAME>";
                    xml +=
"<BENEFICIARY_TYPE>" + obj.isNull(rs.getString("BEN_TYPE_DESC"), 1) +
 "</BENEFICIARY_TYPE>";
                    xml +=
"<BENEFICIARY_TYPE_SNO>" + obj.isNull(rs.getString("BENEFICIARY_TYPE_ID"), 1) +
 "</BENEFICIARY_TYPE_SNO>";
                    xml +=
"<qty>" + obj.isNull(rs.getString("qty"), 1) + "</qty>";
                    xml +=
"<pr_record>" + obj.isNull(rs.getString("pr_record"), 1) + "</pr_record>";
                }


                if (row_count != 0) {
                    xml +=
"<row_count>" + row_count + "</row_count><status>Data Found </status>";
                } else {
                    xml +=
"<row_count>" + row_count + "</row_count><status>No Data </status>";
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String PR_SNO = "", count = "0";
        int del_row = 0;

        if (command.equals("FR"))

        {
            int up_row = 0;
            str_qry =
                    "update  PMS_DCB_TRN_MONTHLY_PR set PROCESS_FLAG ='FR' where BENEFICIARY_SNO=" +
                    bensno + " and MONTH=" + month + " and YEAR=" + year + "";
            try {
                up_row = obj.setUpd(str_qry);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            str_qry =
                    "update  PMS_DCB_MONTHLY_PR set PROCESS_FLAG ='FR' where BENEFICIARY_SNO=" +
                    bensno + " and MONTH=" + month + " and YEAR=" + year + "";
            try {
                up_row = obj.setUpd(str_qry);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (up_row >= 1)
                msg = "Freezed Over ";
            xml += "<msg>" + msg + "</msg><up_row>" + up_row + "</up_row>";
        }
        if (command.equals("delete"))

        {
            try {

                PR_SNO =
                        obj.isNull(obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "PR_SNO",
                                                "where BENEFICIARY_SNO=" +
                                                bensno + " and MONTH=" +
                                                month + " and YEAR=" + year +
                                                " and PROCESS_FLAG <> 'FR'"),
                                   1);


                subcount =
                        obj.isNull(obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "count(*)",
                                                "where BENEFICIARY_SNO=" +
                                                bensno + " and MONTH=" +
                                                month + " and YEAR=" + year +
                                                ""), 1);
                //
                count =
obj.getValue("PMS_DCB_WC_BILLING", "count(*)", "where PR_SNO=" + PR_SNO + " ");


                if (Integer.parseInt(count) != 0) {
                    xml +=
"<status>you cant delete</status><count>" + count + "</count>";
                    msg = "Record Not Deleted !";
                } else {

                    try {
                        str_qry =
                                "delete from PMS_DCB_MONTHLY_PR where BENEFICIARY_SNO=" +
                                bensno + " and MONTH=" + month + " and YEAR=" +
                                year + " and PROCESS_FLAG <> 'FR'";

                        del_row = obj.setUpd(str_qry);

                        str_qry =
                                "delete from PMS_DCB_TRN_MONTHLY_PR where BENEFICIARY_SNO=" +
                                bensno + " and MONTH=" + month + " and YEAR=" +
                                year + " and PROCESS_FLAG <> 'FR'";

                        del_row = obj.setUpd(str_qry);

                        if (del_row >= 1) {
                            msg = "Record Deleted Succefully!";
                        } else {
                            msg = "Record Not Deleted !";
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        if (Integer.parseInt(subcount) >= 1)
                            msg = "Record Not Deleted ! , may be it freezed ";
                        else
                            msg = "Record Not Deleted !";
                    }
                }
                xml +=
"<msg>" + msg + "</msg><del_row>" + del_row + "</del_row><status>valid for delete</status><count>" +
 count + "</count>";
            } catch (Exception e) {
                System.out.println(e);
            }
            xml += "<count>" + count + "</count>";
        }


        if (command.equals("prv_show")) {

            String METER_SNO = obj.setValue("METER_SNO", request);
            obj.testQry("METER_SNO" + METER_SNO);
            obj.testQry("month" + month);
            obj.testQry("year" + year);
            try {
                String Ben_name =
                    obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_NAME",
                                 "where BENEFICIARY_SNO=" + bensno + " ");
                String METRE_LOCATION =
                    obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE",
                                 "METRE_LOCATION",
                                 "where METRE_SNO=" + METER_SNO + " ");

                xml += "";
                xml += "<Ben_name>" + Ben_name + "</Ben_name>";
                xml +=
"<METRE_LOCATION>" + METRE_LOCATION + "</METRE_LOCATION>";


                String METRE_TYPE =
                    obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "METRE_TYPE",
                                 "where METRE_SNO=" + METER_SNO + " ");

                if (METRE_TYPE.equalsIgnoreCase(""))
                    METRE_TYPE = "0";

                if (Integer.parseInt(METRE_TYPE) == 2)
                    xml += "<METRE_TYPE>ML</METRE_TYPE>";
                else
                    xml += "<METRE_TYPE>KL</METRE_TYPE>";

                String Prv_Metre_Work =
                    obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "METRE_WORKING",
                                 "where METRE_SNO=" + METER_SNO +
                                 " and  MONTH=" +
                                 (Integer.parseInt(month) - 1) + " and YEAR=" +
                                 year + " ");
                if (Prv_Metre_Work.equalsIgnoreCase(""))
                    Prv_Metre_Work = "-";
                xml +=
"<Prv_Metre_Work>" + Prv_Metre_Work + "</Prv_Metre_Work>";

                String Prv_Metre_Fix =
                    obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "METRE_FIXED",
                                 "where METRE_SNO=" + METER_SNO +
                                 " and  MONTH=" +
                                 (Integer.parseInt(month) - 1) + " and YEAR=" +
                                 year + " ");
                if (Prv_Metre_Fix.equalsIgnoreCase(""))
                    Prv_Metre_Fix = "-";
                xml += "<Prv_Metre_Fix>" + Prv_Metre_Fix + "</Prv_Metre_Fix>";

                String Prv_In_Read =
                    obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "METRE_INITIAL_READING",
                                 "where METRE_SNO=" + METER_SNO +
                                 " and  MONTH=" +
                                 (Integer.parseInt(month) - 1) + " and YEAR=" +
                                 year + " ");
                if (Prv_In_Read.equalsIgnoreCase(""))
                    Prv_In_Read = "-";
                xml += "<Prv_In_Read>" + Prv_In_Read + "</Prv_In_Read>";

                String Prv_Cls_Read =
                    obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "METRE_CLOSING_READING",
                                 "where METRE_SNO=" + METER_SNO +
                                 " and  MONTH=" +
                                 (Integer.parseInt(month) - 1) + " and YEAR=" +
                                 year + " ");
                if (Prv_Cls_Read.equalsIgnoreCase(""))
                    Prv_Cls_Read = "-";
                xml += "<Prv_Cls_Read>" + Prv_Cls_Read + "</Prv_Cls_Read>";


                String Prv_Qty_Cons =
                    obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "QTY_CONSUMED",
                                 "where METRE_SNO=" + METER_SNO +
                                 " and  MONTH=" +
                                 (Integer.parseInt(month) - 1) + " and YEAR=" +
                                 year + " ");
                if (Prv_Qty_Cons.equalsIgnoreCase(""))
                    Prv_Qty_Cons = "-";
                xml += "<Prv_Qty_Cons>" + Prv_Qty_Cons + "</Prv_Qty_Cons>";

                String Prv_EQty_Cons =
                    obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "EXCESS_QTY",
                                 "where METRE_SNO=" + METER_SNO +
                                 " and  MONTH=" +
                                 (Integer.parseInt(month) - 1) + " and YEAR=" +
                                 year + " ");
                xml += "<Prv_EQty_Cons>" + Prv_EQty_Cons + "</Prv_EQty_Cons>";

                String Act_Allow_Qty =
                    obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "ALLOTED_QTY",
                                 "where METRE_SNO=" + METER_SNO +
                                 " and  MONTH=" +
                                 (Integer.parseInt(month) - 1) + " and YEAR=" +
                                 year + " ");
                xml += "<Act_Allow_Qty>" + Act_Allow_Qty + "</Act_Allow_Qty>";
                obj.testQry(xml);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        xml += "</result>";

        PrintWriter pr = response.getWriter();
        pr.write(xml);
        obj.testQry(xml);
        pr.flush();
        pr.close();
        obj.conClose(con);


    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        // TODO Auto-generated method stub
    }

}

