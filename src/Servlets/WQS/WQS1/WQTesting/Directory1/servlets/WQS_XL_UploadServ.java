package Servlets.WQS.WQS1.WQTesting.Directory1.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class WQS_XL_UploadServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        if (ServletFileUpload.isMultipartContent(request)) {

            ServletFileUpload servletFileUpload =
                new ServletFileUpload(new DiskFileItemFactory());

            try {
                java.util.List fileItemsList =
                    servletFileUpload.parseRequest(request);
                System.out.println("Get the List");
                String optionalFileName = "";
                FileItem fileItem = null;

                Iterator it = fileItemsList.iterator();
                while (it.hasNext()) {
                    FileItem fileItemTemp = (FileItem)it.next();
                    System.out.println("The Field Name = " +
                                       fileItemTemp.getFieldName());
                    if (fileItemTemp.isFormField()) {
                        if (fileItemTemp.getFieldName().equals("filename"))
                            optionalFileName = fileItemTemp.getString();
                    } else
                        fileItem = fileItemTemp;
                }
                if (fileItem != null) {
                    String fileName = fileItem.getName();
                    System.out.println("The Content Type = " +
                                       fileItem.getContentType());
                    System.out.println("The File Name = " + fileName);
                    System.out.println("The File Length = " +
                                       fileItem.getSize());
                    /* Save the uploaded file if its size is greater than 0. */
                    if (fileItem.getSize() > 0) {
                        if (optionalFileName.trim().equals(""))
                            fileName = FilenameUtils.getName(fileName);
                        else
                            fileName = optionalFileName;

                        String userAgent = request.getHeader("User-Agent");
                        System.out.println("The User Agent ========== " +
                                           userAgent);
                        String userSeparator = "/"; // default
                        if (userAgent.indexOf("Windows") != -1)
                            userSeparator = "\\";
                        if (userAgent.indexOf("Linux") != -1)
                            userSeparator = "/";
                        System.out.println("The File Separator === " +
                                           userSeparator);
                        ResourceBundle rs =
                            ResourceBundle.getBundle("Servlets.Security.servlets.Config");

                        File f =
                            new File(getServletConfig().getServletContext().getRealPath(rs.getString("Config.FILE_PATH")) +
                                     userSeparator + fileName);
                        System.out.println("Path================" +
                                           f.getPath());
                        String path = f.getPath();

                        File saveTo = new File(path);
                        try {
                            fileItem.write(saveTo);
                        } catch (Exception e) {
                            out.println("File is not Writed...");
                            out.println(e);
                        }
                        HttpSession ses = request.getSession(true);
                        ses.setAttribute("path", path);
                        doGet(request, response);
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
                out.println(e);
            }
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Calendar c;
        boolean flag = false;
        HttpSession ses = request.getSession(true);
        String path = (String)ses.getAttribute("path");
        System.out.println("The Path ==================== " + path);
        try {
            Connection con = null;
            try {
                ResourceBundle rss =
                    ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                String ConnectionString = "";

                String strDriver = rss.getString("Config.DATA_BASE_DRIVER");
                String strdsn = rss.getString("Config.DSN");
                String strhostname = rss.getString("Config.HOST_NAME");
                String strportno = rss.getString("Config.PORT_NUMBER");
                String strsid = rss.getString("Config.SID");
                String strdbusername = rss.getString("Config.USER_NAME");
                String strdbpassword = rss.getString("Config.PASSWORD");
                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                System.out.println("The Driver Specification = " + strDriver);
                System.out.println("The Connection String = " +
                                   ConnectionString);
                System.out.println("The User Id = " + strdbusername +
                                   " and Password = " + strdbpassword);
                Class.forName(strDriver.trim());
                con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
                System.out.println("Get the Connection for Oracle Database...");

            } catch (Exception e) {
                System.out.println("Exception while Connecting to the Database..." +
                                   e);
            }
            PreparedStatement ps = null;

            InputStream myxls = new FileInputStream(path);
            POIFSFileSystem fs = new POIFSFileSystem(myxls);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            int nosheets = wb.getNumberOfSheets();
            System.out.println("The Number of Sheets in the Excel File = " +
                               nosheets);
            int r = 0;
            for (int index = 0; index < nosheets; index++) {
                System.out.println("The Sheet Name = " +
                                   wb.getSheetName(index));
                HSSFSheet sheet = wb.getSheetAt(index);
                System.out.println("total number of rows:" +
                                   sheet.getPhysicalNumberOfRows());
                int row2 = sheet.getPhysicalNumberOfRows();
                String[][] values = new String[200][69];
                String[][] v1 = new String[200][69];
                double val = 0.0;
                for (r = 1; r < row2; r++) {
                    try {
                        System.out.println(r + " th row:" + r);
                        HSSFRow row1 = sheet.getRow(r);
                        for (int i = 0; i < 69; i++) {
                            HSSFCell c1 = row1.getCell((short)i);
                            System.out.println(i + " th column value:" + c1);
                            if (c1 == null) {
                                System.out.println("null value");
                                values[r][i] = null;
                            } else {
                                System.out.println("not null");
                                switch (c1.getCellType()) {
                                case 0:
                                    if (c1.getCellType() ==
                                        HSSFCell.CELL_TYPE_NUMERIC) {
                                        if (HSSFDateUtil.isCellDateFormatted(c1)) {
                                            SimpleDateFormat formatter =
                                                new SimpleDateFormat("dd/MM/yyyy");
                                            values[r][i] =
                                                    formatter.format(c1.getDateCellValue());
                                            System.out.println("The Date Value = " +
                                                               values[r][i]);

                                        } else {
                                            val = c1.getNumericCellValue();
                                            v1[r][i] = "" + val;
                                            System.out.println("Numeric Value:" +
                                                               v1[r][i]);
                                            String s1 = v1[r][i].toString();
                                            System.out.println("first index:" +
                                                               s1.substring(0,
                                                                            s1.indexOf(".")));
                                            System.out.println("second index:" +
                                                               s1.substring((s1.indexOf(".") +
                                                                             1),
                                                                            s1.length()));
                                            String len =
                                                s1.substring((s1.indexOf(".") +
                                                              1), s1.length());
                                            if (len.equalsIgnoreCase("0") ||
                                                len.equalsIgnoreCase("00")) {
                                                values[r][i] =
                                                        s1.substring(0, s1.indexOf("."));
                                            } else
                                                values[r][i] = s1;
                                        }
                                    }
                                    break;
                                case 1:
                                    String s = c1.getStringCellValue();
                                    if (s.equalsIgnoreCase("nil"))
                                        s = "Nil";
                                    else if (s.equalsIgnoreCase("none"))
                                        s = "None";
                                    values[r][i] = s;
                                    System.out.println(r + " " + i +
                                                       " The String Value " +
                                                       c1.getStringCellValue());
                                    break;
                                case 3:
                                    val = c1.getCellType();
                                    values[r][i] = "" + val;
                                    break;
                                default:
                                    System.out.println("The Cell Type = " +
                                                       c1.getCellType());
                                    values[r][i] = "" + 0.0;
                                    break;
                                }
                            }
                        }

                    } catch (Exception e) {
                        System.out.println("err in retrive column:" +
                                           e.getMessage());
                    }
                }

                String query =
                    "Insert into WQS_SAMPLERESULT(LAB_CODE,SAMPLE_NO,SAMPLE_COLLECTION_DATE,CUSTOMER_TYPE,CUSTOMER_REF_NO,PROGRAMME_CODE,RESURVEY_DIST_CODE,RESURVEY_BLK_CODE,RESURVEY_PAN_CODE,RESURVEY_HAB_CODE,SCM_TYPE,SOURCE_TYPE,SOURCE_CODE,LOCATION,SOURCE_DIST_NAME,SOURCE_PAN_NAME,SOURCE_HAB_NAME,APPEAR,COLOR,ODOR,TURB,TOTALSOLID,EC,TDS,PH,ACIDITY,PHALK,TALK,TH,HARDNESS,CA,MG,NA,POTASSIUM,FE,MN,NH3,NO2,NO3,CL,FLUORIDE,SO4,PO4,TIDYS,SIO2,CYANIDE,SPC,AG,CD,CU,PB,RESIDUALCHBRINE,CR,ZN,ALUMINIUM,OILGREASE,PNP,REASON,DO,BOD,COD,TC,FC,FSC,CNC,ALKALINITY)";
                query +=
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(query);
                for (int row = 1; row < r; row++) {
                    try {
                        System.out.println("first value:" + values[row][0]);
                        System.out.println("second value:" + values[row][1]);
                        System.out.println("third value:" + values[row][2]);

                        int intval1 = Integer.parseInt(values[row][0]);
                        System.out.println("The Value 1 = " + intval1);
                        int intval2 = Integer.parseInt(values[row][1]);
                        System.out.println("The Value 2 = " + intval2);

                        ps.setInt(1, intval1);
                        System.out.println("LAB_CODE = " + intval1);
                        ps.setInt(2, intval2);
                        System.out.println("SAMPLE_NO = " + intval2);
                        Date rdate = null;
                        //  if(values[row][2].indexOf("/")!= -1)
                        //{
                        System.out.println("String format");
                        String[] od = values[row][2].split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
                        java.util.Date d = c.getTime();
                        rdate = new Date(d.getTime());
                        System.out.println("The Date = " + rdate);

                        ps.setDate(3, rdate);
                        System.out.println("SAMPLE_COLLECTION_DATE:" + rdate);

                        if (values[row][3].equalsIgnoreCase("twad"))
                            values[row][3] = "Twad";
                        else if (values[row][3].equalsIgnoreCase("govt"))
                            values[row][3] = "Govt";
                        else if (values[row][3].equalsIgnoreCase("private"))
                            values[row][3] = "Private";

                        ps.setString(4, values[row][3]);
                        System.out.println("CUSTOMER_TYPE = " +
                                           values[row][3]);
                        if (values[row][4] == null) {
                            System.out.println("null customer refernce");
                            ps.setString(5, "-");
                            System.out.println("CUSTOMER_REF_NO = -");
                        } else {
                            ps.setString(5, values[row][4]);
                            System.out.println("CUSTOMER_REF_NO = " +
                                               values[row][4]);
                        }
                        ps.setString(6, values[row][5]);
                        System.out.println("STD_NONSTD_TEST = " +
                                           values[row][5]);
                        ps.setString(7, values[row][6]);
                        System.out.println("PROGRAMME_CODE = " +
                                           values[row][6]);
                        ps.setString(8, values[row][7]);
                        System.out.println("RESURVEY_DIST_CODE = " +
                                           values[row][7]);
                        ps.setString(9, values[row][8]);
                        System.out.println("RESURVEY_BLK_CODE = " +
                                           values[row][8]);
                        ps.setString(10, values[row][9]);
                        System.out.println("RESURVEY_PAN_CODE = " +
                                           values[row][9]);
                        ps.setString(11, values[row][10]);
                        System.out.println("RESURVEY_HAB_CODE = " +
                                           values[row][10]);
                        ps.setString(12, values[row][11]);
                        System.out.println("SCM_TYPE = " + values[row][11]);
                        ps.setString(13, values[row][12]);
                        System.out.println("SOURCE_TYPE = " + values[row][12]);
                        ps.setString(14, values[row][13]);
                        System.out.println("SOURCE_CODE = " + values[row][13]);
                        ps.setString(15, values[row][14]);
                        System.out.println("LOCATION = " + values[row][14]);
                        ps.setString(16, values[row][15]);
                        System.out.println("SOURCE_DIST_NAME = " +
                                           values[row][15]);
                        ps.setString(17, values[row][16]);
                        System.out.println("SOURCE_PAN_NAME = " +
                                           values[row][16]);
                        ps.setString(18, values[row][17]);
                        System.out.println("SOURCE_HAB_NAME = " +
                                           values[row][17]);
                        ps.setString(19, values[row][18]);
                        System.out.println("APPEAR = " + values[row][18]);
                        ps.setString(20, values[row][19]);
                        System.out.println("COLOR = " + values[row][19]);
                        ps.setString(21, values[row][20]);
                        System.out.println("ODOR = " + values[row][20]);
                        ps.setString(22, values[row][21]);
                        System.out.println("TURB = " + values[row][21]);
                        ps.setString(23, values[row][22]);
                        System.out.println("TOTAL_SOLID = " + values[row][22]);
                        ps.setString(24, values[row][23]);
                        System.out.println("TDSWT = " + values[row][23]);
                        ps.setString(25, values[row][24]);
                        System.out.println("TDSEC = " + values[row][24]);
                        ps.setString(26, values[row][25]);
                        System.out.println("EC = " + values[row][25]);
                        ps.setString(27, values[row][26]);
                        System.out.println("TDS = " + values[row][26]);
                        ps.setString(28, values[row][27]);
                        System.out.println("PH = " + values[row][27]);
                        ps.setString(29, values[row][28]);
                        System.out.println("ACIDITY = " + values[row][28]);
                        ps.setString(30, values[row][29]);
                        System.out.println("ALKPH = " + values[row][29]);
                        ps.setString(31, values[row][30]);
                        System.out.println("ALKT = " + values[row][30]);
                        ps.setString(32, values[row][31]);
                        System.out.println("PALK = " + values[row][31]);
                        ps.setString(33, values[row][32]);
                        System.out.println("TALK = " + values[row][32]);
                        ps.setString(34, values[row][33]);
                        System.out.println("TH = " + values[row][33]);
                        ps.setString(35, values[row][34]);
                        System.out.println("CACO3 = " + values[row][34]);
                        ps.setString(36, values[row][35]);
                        System.out.println("CA = " + values[row][35]);
                        ps.setString(37, values[row][36]);
                        System.out.println("MG = " + values[row][36]);
                        ps.setString(38, values[row][37]);
                        System.out.println("NA = " + values[row][37]);
                        ps.setString(39, values[row][38]);
                        System.out.println("POTASSIUM = " + values[row][38]);
                        ps.setString(40, values[row][39]);
                        System.out.println("FE = " + values[row][39]);
                        ps.setString(41, values[row][40]);
                        System.out.println("MN = " + values[row][40]);
                        ps.setString(42, values[row][41]);
                        System.out.println("NH3 = " + values[row][41]);
                        ps.setString(43, values[row][42]);
                        System.out.println("NO2 = " + values[row][42]);
                        ps.setString(44, values[row][43]);
                        System.out.println("NO3 = " + values[row][43]);
                        ps.setString(45, values[row][44]);
                        System.out.println("CL = " + values[row][44]);
                        ps.setString(46, values[row][45]);
                        System.out.println("FLUORIDE = " + values[row][45]);
                        ps.setString(47, values[row][46]);
                        System.out.println("SO4 = " + values[row][46]);
                        ps.setString(48, values[row][47]);
                        System.out.println("PO4 = " + values[row][47]);
                        ps.setString(49, values[row][48]);
                        System.out.println("TIDYS = " + values[row][48]);
                        ps.setString(50, values[row][49]);
                        System.out.println("SIO2 = " + values[row][49]);
                        ps.setString(51, values[row][50]);
                        System.out.println("NM = " + values[row][50]);
                        ps.setString(52, values[row][51]);
                        System.out.println("SPC = " + values[row][51]);
                        ps.setString(53, values[row][52]);
                        System.out.println("AS = " + values[row][52]);
                        ps.setString(54, values[row][53]);
                        System.out.println("CD = " + values[row][53]);
                        ps.setString(55, values[row][54]);
                        System.out.println("CU = " + values[row][54]);
                        ps.setString(56, values[row][55]);
                        System.out.println("PB = " + values[row][55]);
                        ps.setString(57, values[row][56]);
                        System.out.println("RESIDUAL = " + values[row][56]);
                        ps.setString(58, values[row][57]);
                        System.out.println("CR = " + values[row][57]);
                        ps.setString(59, values[row][58]);
                        System.out.println("ZN = " + values[row][58]);
                        ps.setString(60, values[row][59]);
                        System.out.println("ALUMINIUM = " + values[row][59]);
                        ps.setString(61, values[row][60]);
                        System.out.println("OIL_GREASE = " + values[row][60]);
                        ps.setString(62, values[row][61]);
                        System.out.println("RESIDUAL_CL2 = " +
                                           values[row][61]);
                        ps.setString(63, values[row][62]);
                        System.out.println("INMATT = " + values[row][62]);
                        ps.setString(64, values[row][63]);
                        System.out.println("SOLFE = " + values[row][63]);
                        ps.setString(65, values[row][64]);
                        System.out.println("AL2O3 = " + values[row][64]);
                        ps.setString(66, values[row][65]);
                        System.out.println("H2SO4 = " + values[row][65]);
                        /*ps.setString(67,values[row][66]);
                                    System.out.println("AS2O3 = "+values[row][66]);
                                    ps.setString(68,values[row][67]);
                                    System.out.println("PNP = "+values[row][67]);
                                    ps.setString(69,values[row][68]);
                                    System.out.println("REASON = "+values[row][68]);
                                 /*   ps.setString(70,values[row][69]);
                                    System.out.println("RC = "+values[row][69]);
                                    ps.setString(71,values[row][70]);
                                    System.out.println("RAL = "+values[row][70]);
                                    ps.setString(72,values[row][71]);
                                    System.out.println("DO = "+values[row][71]);
                                    ps.setString(73,values[row][72]);
                                    System.out.println("BOD = "+values[row][72]);
                                    ps.setString(74,values[row][73]);
                                    System.out.println("COD = "+values[row][73]);
                                    ps.setString(75,values[row][74]);
                                    System.out.println("TCF = "+values[row][74]);
                                    ps.setString(76,values[row][75]);
                                    System.out.println("FCF = "+values[row][75]);
                                    ps.setString(77,values[row][76]);
                                    System.out.println("FS9 = "+values[row][76]);*/
                        ps.executeUpdate();
                        System.out.println("Successfully Inserted into the Table");
                    } catch (Exception e) {
                        System.out.println("Exception in connection:" + e);
                        con.rollback();
                        String msg = "<br><br>" + e;
                        sendMessage(response, msg, "ok");
                        System.out.println("Exception " + e);
                    }
                }
            }
            String msg = "Record Successfully Uploaded";
            msg = "<br><br><p><b>" + msg + "</b></p>";
            sendMessage(response, msg, "ok");
            con.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

        String msg = "";
        if (flag) {
            msg = "success";
        } else
            msg = "failure";

    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}

