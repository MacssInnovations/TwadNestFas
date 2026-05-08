package Servlets.WQS.WQS1.WQTesting.Reports.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class WQS_SampleResult_RepServ extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to servlet");
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null;
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
        String cmd = request.getParameter("command");
        System.out.println("command:" + cmd);
        xml = "<response>";
        if (cmd.equalsIgnoreCase("changeLab")) {
            int lab = Integer.parseInt(request.getParameter("lab"));
            xml = xml + "<command>changeLab</command>";
            try {
                ps =
  con.prepareStatement("select distinct LOCATION from  WQS_SAMPLERESULT where LAB_CODE=?");
                ps.setInt(1, lab);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    xml =
 xml + "<location>" + rs.getString("LOCATION") + "</location>";
                    xml = xml + "</count>";
                }
                xml = xml + "<flag>Success</flag>";
            } catch (Exception e) {
                System.out.println("Err in changeLab:" + e.getMessage());
                xml = xml + "<flag>Failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        if (cmd.equalsIgnoreCase("element")) {
            CONTENT_TYPE = "text/html";
            String html = "";
            try {
                st = con.createStatement();
                rs =
  st.executeQuery("Select ELEMENT_SYMBOL from WQS_ELEMENT_SYMBOL");
                int count = 0;
                html =
"<table cellpadding='1%' cellspacing='1%' border='0' width='100%'>";
                boolean bool = false;
                while (rs.next()) {
                    String val = rs.getString("ELEMENT_SYMBOL");
                    System.out.println("value :" + val);
                    if (!(val.equalsIgnoreCase("appear") ||
                          val.equalsIgnoreCase("odor"))) {
                        if (bool = !bool) {
                            html =
html + "<tr bgcolor='pink'><td><input type='checkbox' name='chkelement' value='" +
 val + "'></input></td>";
                            html = html + "<td>" + val + "</td></tr>";
                        } else {
                            html =
html + "<tr ><td><input type='checkbox' name='chkelement' value='" + val +
 "'></input></td>";
                            html = html + "<td>" + val + "</td></tr>";
                        }
                    }
                    count++;
                }
                if (count == 0) {
                    html = "There is no Region";
                } else {
                    html =
html + "<tr ><td colspan='2'><a href='javascript:elementSelectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:elementclose()'>Close</a></td></tr>";
                }
                html = html + "</table>";
                System.out.println("html:" + html);
                out.println(html);
            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                System.out.println("html:" + html);
            }
        }

        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to servlet");
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null, ps1 = null;
        JasperPrint jasperPrint = null;
        String val = "";
        Date dt = null;
        float fval = 0;
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
        JasperDesign jasperDesign = null;
        File reportFile = null;
        try {
            System.out.println("calling servlet");
            /* String lab=request.getParameter("lab");
                        String lb[]=lab.split("--");
                        int lcode=Integer.parseInt(lb[0]);*/

            //System.out.println("lab=====>"+lcode);

            String[] fd = request.getParameter("fdate").split("/");
            Calendar c =
                new GregorianCalendar(Integer.parseInt(fd[2]), Integer.parseInt(fd[1]) -
                                      1, Integer.parseInt(fd[0]));
            java.util.Date dtfrom = c.getTime();
            System.out.println("fdate=====>" + dtfrom);

            String[] td = request.getParameter("tdate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(td[2]), Integer.parseInt(td[1]) - 1,
                         Integer.parseInt(td[0]));
            java.util.Date dtto = c.getTime();
            System.out.println("tdate=====>" + dtto);

            Format formatter;
            formatter = new SimpleDateFormat("dd/MMM/yyyy");
            String f = formatter.format(dtfrom);

            System.out.println("after fdate=====>" + f);
            String t = formatter.format(dtto);
            System.out.println("after tdate=====>" + t);

            String location = request.getParameter("loc");
            System.out.println("location=====>" + location);
            String es[] = request.getParameter("es").split(",");

            String opt = request.getParameter("opt");
            System.out.println("opt=====>" + opt);

            String cv = request.getParameter("cval");
            System.out.println("cval=======>" + cv);
            float cval = Float.parseFloat(cv);
            try {
                st = con.createStatement();
                st.executeUpdate("delete from WQS_NEWTABLE");
            } catch (Exception e) {
                System.out.println("Err in delete:" + e.getMessage());
            }
            for (int i = 0; i < es.length; i++) {
                String es1 = es[i];
                System.out.println(i +
                                   "=======================================================");
                System.out.println("Query   :" + "select " + es1 +
                                   " as f1,sample_collection_date from wqs_sampleresult where (sample_collection_date between to_date('" +
                                   f + "','dd/MM/yyyy') and to_date('" + t +
                                   "','dd/MM/yyyy')) and location='" +
                                   location + "' and " + es1 +
                                   " is not null and " + es1 +
                                   "!='Null' and " + es1 +
                                   "!='None' and customer_type!='Twad'");
                ps =
  con.prepareStatement("select " + es1 + " as f1,sample_collection_date from wqs_sampleresult where (sample_collection_date between to_date('" +
                       f + "','dd/MM/yyyy') and to_date('" + t +
                       "','dd/MM/yyyy')) and location=? and " + es1 +
                       " is not null and " + es1 + "!='Nil' and " + es1 +
                       "!='None' and customer_type!='Twad'");
                //ps.setInt(1,lcode);
                ps.setString(1, location);
                rs = ps.executeQuery();
                System.out.println("field selected for equal");
                while (rs.next()) {
                    System.out.println("value   :" + rs.getString("f1"));
                    val = rs.getString("f1");
                    fval = Float.parseFloat(val);
                    dt = rs.getDate("sample_collection_date");
                    System.out.println("date  :" + dt);
                    if (opt.equalsIgnoreCase("Equal")) {
                        if (fval == cval) {
                            System.out.println("value is equal");
                            ps1 =
 con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                            ps1.setString(1, es1);
                            ps1.setDate(2, dt);
                            ps1.setString(3, val);
                            ps1.executeUpdate();
                        } else {
                            System.out.println("value is not equal");
                        }
                    } else if (opt.equalsIgnoreCase("Lessthan")) {
                        if (fval < cval) {
                            System.out.println("value is lessthan");
                            ps1 =
 con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                            ps1.setString(1, es1);
                            ps1.setDate(2, dt);
                            ps1.setString(3, val);
                            ps1.executeUpdate();
                        } else {
                            System.out.println("value is not lessthan");
                        }
                    } else if (opt.equalsIgnoreCase("Greaterthan")) {
                        if (fval > cval) {
                            System.out.println("value is Greaterthan");
                            ps1 =
 con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                            ps1.setString(1, es1);
                            ps1.setDate(2, dt);
                            ps1.setString(3, val);
                            ps1.executeUpdate();
                        } else {
                            System.out.println("value is not Greaterthan");
                        }
                    } else if (opt.equalsIgnoreCase("Lessthanequal")) {
                        if (fval <= cval) {
                            System.out.println("value is Lessthanequal");
                            ps1 =
 con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                            ps1.setString(1, es1);
                            ps1.setDate(2, dt);
                            ps1.setString(3, val);
                            ps1.executeUpdate();
                        } else {
                            System.out.println("value is not Lessthanequal");
                        }
                    } else if (opt.equalsIgnoreCase("Greaterthanequal")) {
                        if (fval >= cval) {
                            System.out.println("value is Greaterthanequal");
                            ps1 =
 con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                            ps1.setString(1, es1);
                            ps1.setDate(2, dt);
                            ps1.setString(3, val);
                            ps1.executeUpdate();
                        } else {
                            System.out.println("value is not Greaterthanequal");
                        }
                    }
                }
                System.out.println(i +
                                   "=======================================================");
            }
            /*   if(opt.equalsIgnoreCase("Equal"))
                        {
                            for(int i=0;i<es.length;i++)
                            {
                                    String es1=es[i];
                                    System.out.println(i+"=======================================================");
                                    System.out.println("Query   :"+"select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code='"+lcode+"' and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location='"+location+"' and "+es1+"="+cval+"");
                                    ps=con.prepareStatement("select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code=? and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location=? and "+es1+"=? and "+es1+" is not null and "+es1+"!='-'");
                                    ps.setInt(1,lcode);
                                    ps.setString(2,location);
                                    ps.setFloat(3,cval);
                                    rs=ps.executeQuery();
                                    System.out.println("field selected for equal");
                                    while(rs.next())
                                    {
                                        System.out.println("value   :"+rs.getString("f1"));
                                        val=rs.getString("f1");
                                        dt=rs.getDate("sample_collection_date");
                                        System.out.println("date  :"+dt);
                                        ps1=con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                                        ps1.setString(1,es1);
                                        ps1.setDate(2,dt);
                                        ps1.setString(3,val);
                                        ps1.executeUpdate();
                                    }
                                    System.out.println(i+"=======================================================");
                                }
                        }
                            else if(opt.equalsIgnoreCase("Lessthan"))
                            {
                                for(int i=0;i<es.length;i++)
                                {
                                    String es1=es[i];
                                    System.out.println("Query---------->:"+"select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code='"+lcode+"' and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location='"+location+"' and "+es1+"<"+cval+" and "+es1+" is not null and "+es1+"!='-'");
                                    ps=con.prepareStatement("select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code=? and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location=? and "+es1+"<? and "+es1+" is not null and "+es1+"!='-'");
                                    ps.setInt(1,lcode);
                                    System.out.println("lcode:"+lcode);
                                    ps.setString(2,location);
                                    System.out.println("location:"+location);
                                    ps.setFloat(3,cval);
                                    System.out.println("cval:"+cval);
                                    rs=ps.executeQuery();
                                    System.out.println("field selected for Lessthan");
                                    while(rs.next())
                                    {
                                        System.out.println("value +"+rs.getString("f1"));
                                        val=rs.getString("f1");
                                        dt=rs.getDate("sample_collection_date");
                                        System.out.println("date :"+dt);
                                        ps1=con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                                        ps1.setString(1,es1);
                                        ps1.setDate(2,dt);
                                        ps1.setString(3,val);
                                        ps1.executeUpdate();
                                    }
                                }
                            }
                            else if(opt.equalsIgnoreCase("Greaterthan"))
                            {
                                for(int i=0;i<es.length;i++)
                                {
                                    String es1=es[i];
                                    System.out.println("Query---------->:"+"select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code='"+lcode+"' and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location='"+location+"' and "+es1+">'"+cval+"'");
                                    ps=con.prepareStatement("select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code=? and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location=? and "+es1+">? and "+es1+" is not null and "+es1+"!='-'");
                                    ps.setInt(1,lcode);
                                    ps.setString(2,location);
                                    ps.setFloat(3,cval);
                                    rs=ps.executeQuery();
                                    System.out.println("field selected for Greaterthan");
                                    while(rs.next())
                                    {
                                        System.out.println("value +"+rs.getString("f1"));
                                        val=rs.getString("f1");
                                        dt=rs.getDate("sample_collection_date");
                                        System.out.println("date :"+dt);
                                        ps1=con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                                        ps1.setString(1,es1);
                                        ps1.setDate(2,dt);
                                        ps1.setString(3,val);
                                        ps1.executeUpdate();
                                    }
                                }
                            }
                            else if(opt.equalsIgnoreCase("Lessthanequal"))
                            {
                                for(int i=0;i<es.length;i++)
                                {
                                    String es1=es[i];
                                    System.out.println("Query---------->:"+"select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code='"+lcode+"' and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location='"+location+"' and "+es1+"<='"+cval+"'");
                                    ps=con.prepareStatement("select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code=? and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location=? and "+es1+"<=? and "+es1+" is not null and "+es1+"!='-'");
                                    ps.setInt(1,lcode);
                                    ps.setString(2,location);
                                    ps.setFloat(3,cval);
                                    rs=ps.executeQuery();
                                    System.out.println("field selected for Lessthanequal");
                                    while(rs.next())
                                    {
                                        System.out.println("value +"+rs.getString("f1"));
                                        val=rs.getString("f1");
                                        dt=rs.getDate("sample_collection_date");
                                        System.out.println("date :"+dt);
                                        ps1=con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                                        ps1.setString(1,es1);
                                        ps1.setDate(2,dt);
                                        ps1.setString(3,val);
                                        ps1.executeUpdate();
                                    }
                                }
                            }
                            else if(opt.equalsIgnoreCase("Greaterthanequal"))
                            {
                                for(int i=0;i<es.length;i++)
                                {
                                    String es1=es[i];
                                    System.out.println("Query---------->:"+"select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code='"+lcode+"' and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location='"+location+"' and "+es1+">='"+cval+"'");
                                    ps=con.prepareStatement("select "+es1+ " as f1,sample_collection_date from wqs_sampleresult where lab_code=? and (sample_collection_date between to_date('"+f+"','dd/MM/yyyy') and to_date('"+t+"','dd/MM/yyyy')) and location=? and "+es1+">=? and "+es1+" is not null and "+es1+"!='-'");
                                    ps.setInt(1,lcode);
                                    ps.setString(2,location);
                                    ps.setFloat(3,cval);
                                    rs=ps.executeQuery();
                                    System.out.println("field selected for Greaterthanequal");
                                    while(rs.next())
                                    {
                                        System.out.println("value +"+rs.getString("f1"));
                                        val=rs.getString("f1");
                                        dt=rs.getDate("sample_collection_date");
                                        System.out.println("date :"+dt);
                                        ps1=con.prepareStatement("insert into WQS_NEWTABLE(ELEMENT_SYMBOL,ELEMENT_DATE,ELEMENT_VALUE)values(?,?,?)");
                                        ps1.setString(1,es1);
                                        ps1.setDate(2,dt);
                                        ps1.setString(3,val);
                                        ps1.executeUpdate();
                                    }
                                }
                            } */
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_SampleResult_Rep.jasper"));
            System.out.println("after path");
            if (!reportFile.exists()) {
                System.out.println("does not exsist");
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            }
            System.out.println(JRLoader.loadObject(reportFile.getPath()));
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            //map.put("lab",lb[1]);
            map.put("fromdt", dtfrom);
            map.put("todt", dtto);
            map.put("location", location);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);

            String rtype = request.getParameter("cmbReportType");
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SampleReport.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                      false);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                exporter.exportReport();
                out.flush();
                out.close();
            } else if (rtype.equalsIgnoreCase("PDF")) {
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SampleReport.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
                System.out.println("test1");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SampleReport.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);
                System.out.println("test2");
                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                         xlsReport);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                         Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                         Boolean.TRUE);
                exporterXLS.exportReport();
                System.out.println("test3");
                byte[] bytes;
                bytes = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                System.out.println("test4");
                ouputStream.flush();
                ouputStream.close();
                System.out.println("test5");
            } else if (rtype.equalsIgnoreCase("TXT")) {
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"SampleReport.txt\"");
                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      txtReport);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                      new Integer(200));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                      new Integer(50));
                exporter.exportReport();

                byte[] bytes;
                bytes = txtReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
    }
}
