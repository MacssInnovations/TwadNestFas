package Servlets.WQS.WQS1.WQTesting.Directory1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_Customer_MasterServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        Connection con = null;
        ResultSet rs = null, rs2 = null;
        PreparedStatement ps = null, ps1 = null;
        Statement st = null;
        String xml = null, ErrorMessage = "";
        String updatedby = null, cmd = null, cid = null, name = null, type =
            null, address = null, flag = null, customer_id = null;
        Timestamp ts = null;
        boolean ErrorOccured = false;
        int lab = 0, offid = 0, id = 0, count = 0;
        String email_id = null;
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
            try {
                st = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;
            }
            System.out.println(session);
        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);
        xml = "<response>";
        cmd = request.getParameter("command");
        if (cmd.equalsIgnoreCase("load")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            String ctype = request.getParameter("customer_type");
            xml = xml + "<command>load</command>";
            try {
                String sql =
                    "select lab_code,customer_id,customer_type," + "(case when customer_type not in('Twad','Twad Staff','Twad Student','Local Body') then to_number(substr(trim(customer_id),(codelength+1),length(trim(customer_id))))" +
                    "      else to_number(customer_id) end" +
                    ")as code,name,address,email_id from" +
                    "(select lab_code,customer_id,customer_type,name,address,email_id from wqs_customer where lab_code=" +
                    lab + " and customer_type='" + ctype + "'" +
                    ")a inner join" +
                    "(select type_name,length(trim(customer_code)) as codelength from wqs_customer_type" +
                    ")b on a.customer_type=b.type_name order by customer_type,code";
                System.out.println("load qry: " + sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    count++;
                    xml = xml + "<display>";
                    xml =
 xml + "<lab_code>" + rs.getString("lab_code") + "</lab_code>";
                    xml =
 xml + "<cust_id>" + rs.getString("customer_id") + "</cust_id>";
                    xml =
 xml + "<cust_name>" + rs.getString("name") + "</cust_name>";
                    xml =
 xml + "<cust_type>" + rs.getString("customer_type") + "</cust_type>";
                    xml =
 xml + "<address>" + rs.getString("address") + "</address>";
                    System.out.println("email id:" + rs.getString("email_id"));
                    if (rs.getString("email_id") == null)
                        xml = xml + "<email_id>-</email_id>";
                    else
                        xml =
 xml + "<email_id>" + rs.getString("email_id") + "</email_id>";
                    xml = xml + "</display>";
                }
                xml = xml + "<count>" + count + "</count>";
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("getCustomer")) {
            cid = request.getParameter("cid");
            lab = Integer.parseInt(request.getParameter("lab"));
            type = request.getParameter("ctype");
            xml = xml + "<command>getCustomer</command>";
            try {
                if (type.equalsIgnoreCase("Twad Staff")) {
                    ResultSet rt2 =
                        st.executeQuery("select lpad('" + cid + "',5,0) as customer_id ");
                    if (rt2.next()) {
                        cid = rt2.getString("customer_id");
                    }
                    System.out.println("customer_id:::::::::::::::" +
                                       customer_id);
                }

                ps1 =
 con.prepareStatement("select * from wqs_customer where lab_code=? and customer_id=?");
                ps1.setInt(1, lab);
                ps1.setString(2, cid);
                rs2 = ps1.executeQuery();
                if (!rs2.next()) {
                    xml = xml + "<flag>success</flag>";
                    if (type.equalsIgnoreCase("Twad")) {
                        xml = xml + "<type>twad</type>";
                        try {
                            offid = Integer.parseInt(cid);
                            String sql =
                                "select a.office_short_name,a.office_address1,a.office_address2,a.city_town_name,a.Office_pin_code,c.Work_Nature_Desc,b.Office_Level_Name, d.district_name from com_mst_offices a left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id left outer join com_mst_districts d on d.district_code=a.district_code where office_id=?";
                            ps = con.prepareStatement(sql);
                            ps.setInt(1, offid);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<avail>available</avail>";
                                xml =
 xml + "<offadd1>" + rs.getString("office_address1") + "</offadd1><offadd2>" +
   rs.getString("office_address2") + "</offadd2>";
                                xml =
 xml + "<tname>" + rs.getString("CITY_TOWN_NAME") + "</tname><district>" +
   rs.getString("district_name") + "</district>";
                                xml =
 xml + "<level>" + rs.getString("Office_Level_Name") + "</level><type>" +
   rs.getString("Work_Nature_Desc") + "</type><pcode>" +
   rs.getInt("Office_pin_code") + "</pcode>";
                            } else {
                                ErrorOccured = true;
                                ErrorMessage = "Invalid ID,Record not found.";
                                xml =
 xml + "<avail>notavailable</avail><message>" + ErrorMessage + " </message>";
                            }
                            rs.close();
                            ps.close();
                            con.close();
                        } catch (Exception e) {
                            ErrorMessage =
                                    "Invalid Customer ID(Should be Numeric)";
                            xml =
 xml + "<avail>Err</avail><message>" + ErrorMessage + " </message>";
                        }
                    } else if (type.equalsIgnoreCase("Twad Staff") ||
                               type.equalsIgnoreCase("Twad Student")) {
                        xml = xml + "<type>twad_staff</type>";
                        try {
                            String sql =
                                "select aa.office_id,aa.employee_id,bb.employee_name,a.office_short_name,a.office_address1,a.office_address2," +
                                "a.city_town_name,a.Office_pin_code,c.Work_Nature_Desc,b.Office_Level_Name,d.district_name " +
                                "from hrm_emp_current_posting aa left outer join " +
                                "hrm_mst_employees bb on aa.employee_id=bb.employee_id  left outer join " +
                                "com_mst_offices a on aa.office_id=a.office_id left outer join " +
                                "COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id left outer join " +
                                "COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id left outer join " +
                                "com_mst_districts d on d.district_code=a.district_code where aa.employee_id=?";
                            System.out.println("sql:" + sql);
                            ps = con.prepareStatement(sql);
                            ps.setInt(1, Integer.parseInt(cid));
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<avail>available</avail>";
                                xml =
 xml + "<emp_name>" + rs.getString("employee_name") + "</emp_name>";
                                xml =
 xml + "<offadd1>" + rs.getString("office_address1") + "</offadd1><offadd2>" +
   rs.getString("office_address2") + "</offadd2>";
                                xml =
 xml + "<tname>" + rs.getString("CITY_TOWN_NAME") + "</tname><district>" +
   rs.getString("district_name") + "</district>";
                                xml =
 xml + "<level>" + rs.getString("Office_Level_Name") + "</level><type>" +
   rs.getString("Work_Nature_Desc") + "</type><pcode>" +
   rs.getInt("Office_pin_code") + "</pcode>";
                            } else {
                                ErrorOccured = true;
                                ErrorMessage = "Invalid ID,Record not found.";
                                xml =
 xml + "<avail>notavailable</avail><message>" + ErrorMessage + " </message>";
                            }
                            rs.close();
                            ps.close();
                            con.close();
                        } catch (Exception e) {
                            ErrorMessage =
                                    "Invalid Customer ID(Should be Numeric)";
                            xml =
 xml + "<avail>Err</avail><message>" + ErrorMessage + " </message>";
                        }
                    } else {
                        xml = xml + "<type>others</type>";
                    }
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in duplication checking:" +
                                   e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("add")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            name = request.getParameter("name");
            type = request.getParameter("type");
            email_id = request.getParameter("email_id");
            if (!type.equalsIgnoreCase("Twad") &&
                !type.equalsIgnoreCase("Twad Staff") &&
                !type.equalsIgnoreCase("Twad Student") &&
                !type.equalsIgnoreCase("Local Body")) {
                cid = request.getParameter("cid");
                try {
                    rs2 =
 st.executeQuery("select customer_code,length(trim(customer_code))as code from wqs_customer_type where type_name='" +
                 type + "'");
                    if (rs2.next()) {
                        String code = rs2.getString("customer_code");
                        int len = Integer.parseInt(rs2.getString("code")) + 1;
                        System.out.println("code:" + code + "  length:" + len);
                        rs =
  st.executeQuery("select max(to_number(substr(customer_id," + len +
                  ",length(customer_id))))as val from wqs_customer where lab_code=" +
                  lab + " and customer_type='" + type + "'");
                        if (rs.next()) {
                            System.out.println("val" + rs.getString("val"));
                            if (rs.getString("val") != null) {
                                System.out.println("not null");
                                id = Integer.parseInt(rs.getString("val")) + 1;
                            } else {
                                System.out.println("null");
                                id = 1;
                            }
                            customer_id = code.trim() + id;
                            System.out.println("customer_id:" + customer_id);
                            xml = xml + "<invoice>available</invoice>";
                            flag = "success";
                        } else {
                            xml = xml + "<invoice>not_available</invoice>";
                            flag = "failure";
                        }

                    }
                    System.out.println("Customer id==========>" + customer_id);
                } catch (Exception e) {
                    System.out.println("Err in increment customer id:" +
                                       e.getMessage());
                    flag = "failure";
                }
            } else {
                customer_id = request.getParameter("cid");
                try {
                    if (type.equalsIgnoreCase("Twad Staff")) {
                        ResultSet rt2 =
                            st.executeQuery("select lpad('" + customer_id +
                                            "',5,0) as customer_id ");
                        if (rt2.next()) {
                            customer_id = rt2.getString("customer_id");
                        }
                        System.out.println("customer_id:::::::::::::::" +
                                           customer_id);
                    }

                    rs2 =
 st.executeQuery("select * from wqs_customer_type where customer_code='" +
                 customer_id + "'");
                    if (rs2.next()) {
                        flag = "failure";
                    } else
                        flag = "success";
                } catch (Exception e) {
                    System.out.println("Err in duplication checking:" +
                                       e.getMessage());
                    flag = "failure";
                }
            }

            address = request.getParameter("address");
            xml = xml + "<command>add</command>";
            if (flag.equalsIgnoreCase("success")) {
                try {

                    String sql =
                        "select lab_code,customer_id,customer_type," + "(case when customer_type not in('Twad','Twad Staff','Twad Student','Local Body') then to_number(substr(trim(customer_id),(codelength+1),length(trim(customer_id))))" +
                        "      else to_number(customer_id) end" +
                        ")as code,name,address,email_id from" +
                        "(select lab_code,customer_id,customer_type,name,address,email_id from wqs_customer where lab_code=" +
                        lab + " and customer_type='" + type + "'" +
                        ")a inner join" +
                        "(select type_name,length(trim(customer_code)) as codelength from wqs_customer_type" +
                        ")b on a.customer_type=b.type_name order by customer_type,code";
                    st = con.createStatement();
                    String ss =
                        "insert into wqs_customer(lab_code,customer_id,name,customer_type,address,updated_by_user_id,updated_date,email_id) values(?,?,?,?,?,?,?,?)";
                    ps = con.prepareStatement(ss);
                    ps.setInt(1, lab);
                    ps.setString(2, customer_id);
                    ps.setString(3, name);
                    ps.setString(4, type);
                    ps.setString(5, address);
                    ps.setString(6, updatedby);
                    ps.setTimestamp(7, ts);
                    ps.setString(8, email_id);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        xml = xml + "<flag>success</flag>";
                        rs = st.executeQuery(sql);
                        while (rs.next()) {
                            count++;
                            xml = xml + "<display>";
                            xml =
 xml + "<lab_code>" + rs.getString("lab_code") + "</lab_code>";
                            xml =
 xml + "<cust_id>" + rs.getString("customer_id") + "</cust_id>";
                            xml =
 xml + "<cust_name>" + rs.getString("name") + "</cust_name>";
                            xml =
 xml + "<cust_type>" + rs.getString("customer_type") + "</cust_type>";
                            xml =
 xml + "<address>" + rs.getString("address") + "</address>";
                            if (rs.getString("email_id") == null)
                                xml = xml + "<email_id>-</email_id>";
                            else
                                xml =
 xml + "<email_id>" + rs.getString("email_id") + "</email_id>";
                            xml = xml + "</display>";
                        }
                        xml = xml + "<count>" + count + "</count>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    xml = xml + "<flag>failure</flag>";
                }
            } else
                xml = xml + "<flag>failure</flag>";

        } else if (cmd.equalsIgnoreCase("upd")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            cid = request.getParameter("cid");
            name = request.getParameter("name");
            type = request.getParameter("type");
            address = request.getParameter("address");
            email_id = request.getParameter("email_id");
            System.out.println(cid);
            xml = xml + "<command>upd</command>";
            try {
                String ss =
                    "update wqs_customer set name=?,customer_type=?,address=?,updated_by_user_id=?,updated_date=?,email_id=? where lab_code=? and customer_id=?";
                ps = con.prepareStatement(ss);
                ps.setString(1, name);
                ps.setString(2, type);
                ps.setString(3, address);
                ps.setString(4, updatedby);
                ps.setTimestamp(5, ts);
                ps.setString(6, email_id);
                ps.setInt(7, lab);
                ps.setString(8, cid);
                int i = ps.executeUpdate();
                if (i > 0) {
                    String sql =
                        "select lab_code,customer_id,customer_type," + "(case when customer_type not in('Twad','Twad Staff','Twad Student','Local Body') then to_number(substr(trim(customer_id),(codelength+1),length(trim(customer_id))))" +
                        "      else to_number(customer_id) end" +
                        ")as code,name,address,email_id from" +
                        "(select lab_code,customer_id,customer_type,name,address,email_id from wqs_customer where lab_code=" +
                        lab + " and customer_type='" + type + "'" +
                        ")a inner join" +
                        "(select type_name,length(trim(customer_code)) as codelength from wqs_customer_type" +
                        ")b on a.customer_type=b.type_name order by customer_type,code";
                    st = con.createStatement();
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        count++;
                        xml = xml + "<display>";
                        xml =
 xml + "<lab_code>" + rs.getString("lab_code") + "</lab_code>";
                        xml =
 xml + "<cust_id>" + rs.getString("customer_id") + "</cust_id>";
                        xml =
 xml + "<cust_name>" + rs.getString("name") + "</cust_name>";
                        xml =
 xml + "<cust_type>" + rs.getString("customer_type") + "</cust_type>";
                        xml =
 xml + "<address>" + rs.getString("address") + "</address>";
                        System.out.println("email:" +
                                           rs.getString("email_id"));
                        if (rs.getString("email_id") == null)
                            xml = xml + "<email_id>-</email_id>";
                        else
                            xml =
 xml + "<email_id>" + rs.getString("email_id") + "</email_id>";
                        xml = xml + "</display>";
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<count>" + count + "</count>";
                } else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("Err in update:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("del")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            cid = request.getParameter("cid");
            type = request.getParameter("type");
            System.out.println(cid);
            xml = xml + "<command>del</command>";
            try {
                ps =
  con.prepareStatement("select * from wqs_invoice_details where lab_code=? and customer_id=?");
                ps.setInt(1, lab);
                ps.setString(2, cid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>FoundData</flag>";
                } else {
                    String sql =
                        "select lab_code,customer_id,customer_type," + "(case when customer_type not in('Twad','Twad Staff','Twad Student','Local Body') then to_number(substr(trim(customer_id),(codelength+1),length(trim(customer_id))))" +
                        "      else to_number(customer_id) end" +
                        ")as code,name,address,email_id from" +
                        "(select lab_code,customer_id,customer_type,name,address,email_id from wqs_customer where lab_code=" +
                        lab + " and customer_type='" + type + "'" +
                        ")a inner join" +
                        "(select type_name,length(trim(customer_code)) as codelength from wqs_customer_type" +
                        ")b on a.customer_type=b.type_name order by customer_type,code";
                    System.out.println("sql:" + sql);
                    st = con.createStatement();
                    String ss =
                        "delete from wqs_customer where lab_code=? and customer_id=?";
                    ps1 = con.prepareStatement(ss);
                    ps1.setInt(1, lab);
                    ps1.setString(2, cid);
                    int i = ps1.executeUpdate();
                    if (i > 0) {
                        System.out.println("inside success");
                        rs2 = st.executeQuery(sql);
                        while (rs2.next()) {
                            count++;
                            xml = xml + "<display>";
                            xml =
 xml + "<lab_code>" + rs2.getString("lab_code") + "</lab_code>";
                            xml =
 xml + "<cust_id>" + rs2.getString("customer_id") + "</cust_id>";
                            xml =
 xml + "<cust_name>" + rs2.getString("name") + "</cust_name>";
                            xml =
 xml + "<cust_type>" + rs2.getString("customer_type") + "</cust_type>";
                            xml =
 xml + "<address>" + rs2.getString("address") + "</address>";
                            if (rs2.getString("email_id") == null)
                                xml = xml + "<email_id>-</email_id>";
                            else
                                xml =
 xml + "<email_id>" + rs.getString("email_id") + "</email_id>";
                            xml = xml + "</display>";
                        }
                        xml = xml + "<flag>success</flag>";
                        xml = xml + "<count>" + count + "</count>";
                    } else
                        xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("changeDistrict")) {
            String dist_code = request.getParameter("dist_code");
            String ltype = request.getParameter("ltype");
            xml = xml + "<command>changeDistrict</command>";
            if (ltype.equalsIgnoreCase("Corporation")) {
                System.out.println("Location Type=============>" + ltype);
                try {
                    String query =
                        "select district_code,corp_code,corporation_eng from pms_corporation where district_code='" +
                        dist_code + "' order by corporation_eng";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<code>" + rs.getString("corp_code") + "</code>";
                        xml =
 xml + "<name>" + rs.getString("corporation_eng") + "</name>";
                        xml = xml + "</count>";
                        count++;
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<tcount>" + count + "</tcount>";
                } catch (SQLException e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("error is:" + e.getMessage());
                }
            } else if (ltype.equalsIgnoreCase("Municipality")) {
                System.out.println("Location Type=============>" + ltype);
                try {
                    String query =
                        "select district_code,mucode,municipality_eng from pms_mst_municipality where district_code='" +
                        dist_code + "' order by municipality_eng";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<code>" + rs.getString("mucode") + "</code>";
                        xml =
 xml + "<name>" + rs.getString("municipality_eng") + "</name>";
                        xml = xml + "</count>";
                        count++;
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<tcount>" + count + "</tcount>";
                } catch (SQLException e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("error is:" + e.getMessage());
                }
            } else if (ltype.equalsIgnoreCase("UTP")) {
                System.out.println("Location Type=============>" + ltype);
                try {
                    String query =
                        "select dcode,tpcode,tpname from pms_mst_town_panchayats where dcode='" +
                        dist_code + "' and type='U' order by tpname";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<code>" + rs.getString("tpcode") + "</code>";
                        xml =
 xml + "<name>" + rs.getString("tpname") + "</name>";
                        xml = xml + "</count>";
                        count++;
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<tcount>" + count + "</tcount>";
                } catch (SQLException e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("error is:" + e.getMessage());
                }
            }
            if (ltype.equalsIgnoreCase("RTP")) {
                System.out.println("Location Type=============>" + ltype);
                try {
                    String query =
                        "select dcode,tpcode,tpname from pms_mst_town_panchayats where dcode='" +
                        dist_code + "' and type='R' order by tpname";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<code>" + rs.getString("tpcode") + "</code>";
                        xml =
 xml + "<name>" + rs.getString("tpname") + "</name>";
                        xml = xml + "</count>";
                        count++;
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<tcount>" + count + "</tcount>";
                } catch (SQLException e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("error is:" + e.getMessage());
                }
            }
        } else if (cmd.equalsIgnoreCase("changeType")) {
            String ltype = request.getParameter("ltype");
            xml = xml + "<command>changeType</command>";
            try {
                String query =
                    "select distinct designation from pms_localbody_designation where local_body='" +
                    ltype + "'";
                System.out.println(query);
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    xml =
 xml + "<name>" + rs.getString("designation") + "</name>";
                    xml = xml + "</count>";
                    count++;
                }
                if (count > 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("error is:" + e.getMessage());
            }
        } else {
            lab = Integer.parseInt(request.getParameter("lab"));
            cid = request.getParameter("cid");
            System.out.println(cid);
            xml = xml + "<command>duplicate</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("select customer_id from wqs_customer where lab_code=? and customer_id=?");
                System.out.println(pstmt);
                pstmt.setInt(1, lab);
                pstmt.setString(2, cid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Duplication");
                    xml = xml + "<flag>failure</flag>";
                } else {
                    System.out.println("No Redundancy");
                    xml = xml + "<flag>success</flag>";
                }
                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
