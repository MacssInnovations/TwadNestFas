package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.Security.classes.UserProfile;


public class Consumer_Search extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        //PreparedStatement ps=null;
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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
        String strCommand = "";
        String xml = "";

        int page = 1;
        int total = 2;
        int records = 15;
        int start = 1;
        int limit = 10;
        int end = 10;

        String dis = "%";
        String blk = "%";
        String pan = "";

        String ULBtype = "%";
        String ULBgrade = "%";
        String area = "%";
        String ulb = "";

        String priv = "";
        int ctype = 0;

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
        System.out.println("Session id is:" + userid);


        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");


        /*******************************************************************
         *                 Employee id - Session (UserProfile)
         *******************************************************************/

        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");
        System.out.println("emp id::" + empProfile.getEmployeeId());
        int empid = empProfile.getEmployeeId();

        /******************************************************************/


        /*******************************************************************
         *                 Command parameter
         *******************************************************************/

        try {
            strCommand = request.getParameter("command");
            System.out.println("strCommand : " + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /******************************************************************/


        /*******************************************************************
         *                 Pagination parameters
         *******************************************************************/

        try {
            page = Integer.parseInt(request.getParameter("page"));
            System.out.println("page : " + page);
        } catch (Exception e) {
            System.out.println("Exception getting 'page' parameter ==> " + e);
        }


        try {
            limit = Integer.parseInt(request.getParameter("limit"));
            System.out.println("limit : " + limit);
        } catch (Exception e) {
            System.out.println("Exception getting 'limit' parameter ==> " + e);
        }

        /******************************************************************/


        /*******************************************************************
         *                 Other parameters
         *******************************************************************/

        try {
            dis = request.getParameter("dis");
            if ((dis == "") || ((dis == null))) {
                dis =
 " SELECT DISTRICT_CODE " + " FROM HRM_EMP_CURRENT_POSTING a JOIN PMS_DCB_DIV_DIST_MAP b " +
   " ON a.OFFICE_ID=b.OFFICE_ID " + " WHERE EMPLOYEE_ID= " + empid;
            }
            System.out.println("dis : " + dis);
        } catch (Exception e) {
            System.out.println("Exception fetching dis ===> " + e);
            dis = "%";
        }


        /////////////////////// Town Panchayat //////////////////////////
        try {
            blk = request.getParameter("blk");
            if ((blk == "") || (blk == null)) {
                blk = "%";
                System.out.println("blk =======> %");
            }
            System.out.println("blk : " + blk);
        } catch (Exception e) {
            System.out.println("Exception fetching blk ===> " + e);
            blk = "%";
        }


        try {
            pan = request.getParameter("pan");
            System.out.println("pan : " + pan);
        } catch (Exception e) {
            System.out.println("Exception fetching pan ===> " + e);
        }
        ///////////////////////////////////////////////////////////////////////


        /////////////////////// Urban LB //////////////////////////

        try {
            ULBtype = request.getParameter("ULBtype");
            if ((ULBtype == "") || (ULBtype == null)) {
                ULBtype = "%";
                System.out.println("ULBtype =======> %");
            }
            System.out.println("ULBtype : " + ULBtype);
        } catch (Exception e) {
            System.out.println("Exception fetching ULBtype ===> " + e);
        }


        try {
            ULBgrade = request.getParameter("ULBgrade");
            if ((ULBgrade == "") || (ULBgrade == null)) {
                ULBgrade = "%";
                System.out.println("ULBgrade =======> %");
            }
            System.out.println("ULBgrade : " + ULBgrade);
        } catch (Exception e) {
            System.out.println("Exception fetching ULBgrade ===> " + e);
        }

        try {
            area = request.getParameter("area");
            if ((area == "") || (area == null)) {
                area = "%";
                System.out.println("area =======> %");
            }
            System.out.println("area : " + area);
        } catch (Exception e) {
            System.out.println("Exception fetching area ===> " + e);
        }

        try {
            ulb = request.getParameter("ulb");
            System.out.println("ulb : " + ulb);
        } catch (Exception e) {
            System.out.println("Exception fetching ulb ===> " + e);
        }
        ///////////////////////////////////////////////////////////////////////


        /////////////////////// Private & Others //////////////////////////
        try {
            priv = request.getParameter("priv");
            System.out.println("priv : " + priv);
        } catch (Exception e) {
            System.out.println("Exception fetching priv ===> " + e);
        }

        try {
            ctype = Integer.parseInt(request.getParameter("ctype"));
            System.out.println("ctype : " + ctype);
        } catch (Exception e) {
            System.out.println("Exception fetching ctype ===> " + e);
        }

        /******************************************************************/


        if (strCommand.equals("District")) {
            System.out.println("\n*************\nDistrict\n**************\n");
            xml = "<response><command>District</command>";


            try {
                result =
                        statement.executeQuery("SELECT DISTRICT_CODE,DISTRICT_NAME " +
                                               "FROM COM_MST_DISTRICTS  " +
                                               "WHERE DISTRICT_CODE " +
                                               "      IN  " + "      ( " +
                                               "        SELECT DISTRICT_CODE " +
                                               "        FROM HRM_EMP_CURRENT_POSTING a JOIN PMS_DCB_DIV_DIST_MAP b " +
                                               "        ON a.OFFICE_ID=b.OFFICE_ID " +
                                               "        WHERE EMPLOYEE_ID= " +
                                               empid + "      )");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (result.next()) {
                        xml += "<row>";

                        xml +=
"<dis>" + result.getInt("DISTRICT_CODE") + "</dis>";
                        xml +=
"<district>" + result.getString("DISTRICT_NAME") + "</district>";

                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values from District query: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception in District query" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equals("Block")) {
            System.out.println("\n*************\nblock\n**************\n");
            xml = "<response><command>Block</command>";
            try {
                result =
                        statement.executeQuery("SELECT " + "district_code, " + "block_sno, " +
                                               "block_name " +
                                               "FROM com_mst_blocks " +
                                               "WHERE district_code=" + dis +
                                               " ORDER BY block_name");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (result.next()) {
                        xml += "<row>";

                        xml +=
"<dis>" + result.getInt("district_code") + "</dis>";
                        xml += "<blk>" + result.getInt("block_sno") + "</blk>";
                        xml +=
"<block>" + result.getString("block_name") + "</block>";

                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values from Block query: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception in Block query" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equals("Panch")) {
            System.out.println("\n*************\nPanch\n**************\n");
            xml = "<response><command>Panch</command>";


            try {
                result =
                        statement.executeQuery("SELECT COUNT(*) AS rec " + "  FROM com_mst_panchayats " +
                                               "  WHERE district_code in (" +
                                               dis + ")" +
                                               "  AND block_sno like '" + blk +
                                               "'" +
                                               "  AND LOWER(panch_name) like '" +
                                               pan + "%'");
                if (result.next()) {
                    records = result.getInt("rec");
                }
            } catch (Exception e1) {
                System.out.println("Exception in Counting Records query ==> " +
                                   e1);
            }


            start = (page - 1) * limit + 1;
            end = start + limit - 1;
            total = (int)Math.ceil((float)records / limit);


            try {
                result =
                        statement.executeQuery("SELECT district_code," + "  block_sno, " +
                                               "  panch_sno, " +
                                               "  district_name, " +
                                               "  block_name, " +
                                               "  panch_name " + "FROM " +
                                               "  (SELECT ROWNUM AS ID, " +
                                               "       a.district_code AS district_code," +
                                               "       a.block_sno AS block_sno," +
                                               "       a.panch_sno AS panch_sno," +
                                               "       district_name," +
                                               "       block_name," +
                                               "       a.panch_name AS panch_name" +
                                               " FROM" + " (" + "   (" +
                                               "     SELECT district_code," +
                                               "       block_sno," +
                                               "       panch_sno," +
                                               "       panch_name" +
                                               "     FROM com_mst_panchayats" +
                                               "     WHERE district_code  in (" +
                                               dis + ")" +
                                               "     AND block_sno LIKE '" +
                                               blk + "'" +
                                               "     AND LOWER(panch_name) LIKE '" +
                                               pan + "%'" +
                                               "     ORDER BY district_code, block_sno, panch_name" +
                                               "   )a " + "   " + "   JOIN" +
                                               "   " + "   (" +
                                               "     SELECT district_code," +
                                               "       district_name" +
                                               "     FROM com_mst_districts" +
                                               "   )b" +
                                               "   ON a.district_code=b.district_code" +
                                               "   " + "   JOIN" + "   " +
                                               "   (" +
                                               "     SELECT district_code," +
                                               "		block_sno," +
                                               "       block_name" +
                                               "     FROM com_mst_blocks" +
                                               "   )c" +
                                               "   ON a.district_code=c.district_code AND a.block_sno=c.block_sno" +
                                               "   " + "   ) " + " )" +
                                               " WHERE id BETWEEN " + start +
                                               " AND " + end + " " +
                                               " ORDER BY panch_name");

                try {
                    xml = xml + "<flag>success</flag>";


                    xml = xml + "<page>" + page + "</page>";
                    xml = xml + "<total>" + total + "</total>";
                    xml = xml + "<records>" + records + "</records>";


                    System.out.println("----------------------");
                    System.out.println("start   : " + start);
                    System.out.println("end     : " + end);
                    System.out.println();
                    System.out.println("page   : " + page);
                    System.out.println("total   : " + total);
                    System.out.println("records : " + records);
                    System.out.println("limit   : " + limit);
                    System.out.println("----------------------");


                    while (result.next()) {
                        xml += "<row>";

                        xml +=
"<dis>" + result.getInt("district_code") + "</dis>";
                        xml += "<blk>" + result.getInt("block_sno") + "</blk>";
                        xml += "<pan>" + result.getInt("panch_sno") + "</pan>";
                        xml +=
"<district>" + result.getString("district_name") + "</district>";
                        xml +=
"<block>" + result.getString("block_name") + "</block>";
                        xml +=
"<panch>" + result.getString("panch_name") + "</panch>";

                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception fetching values from 'Panch' resultset ==> " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception in 'Panch' query ==> " + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        /*
        if(strCommand.equals("areaType"))
        {
        	System.out.println("\n*************\nareaType\n**************\n");
            xml="<response><command>areaType</command>";
            try
            {
             result = statement.executeQuery("SELECT AREA_TYPE_ID, " +
											"  AREA_TYPE_DESC " +
											"FROM COM_MST_AREA_TYPE");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 {
         		 	xml+= "<row>";

	     				xml+= "<aid>" + result.getInt("AREA_TYPE_ID") + "</aid>";
	     				xml+= "<adesc>" + result.getString("AREA_TYPE_DESC") + "</adesc>";

     				xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values from areaType query: " + e);
             }
             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in areaType query"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
*/

        else if (strCommand.equals("ULBgrade")) {
            System.out.println("\n*************\nULBgrade\n**************\n");
            xml = "<response><command>ULBgrade</command>";
            try {
                result =
                        statement.executeQuery("SELECT URBANLB_GRADE_ID, " + "  URBANLB_GRADE_DESC " +
                                               "FROM COM_MST_URBAN_LB_GRADE");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (result.next()) {
                        xml += "<row>";

                        xml +=
"<gid>" + result.getInt("URBANLB_GRADE_ID") + "</gid>";
                        xml +=
"<gdesc>" + result.getString("URBANLB_GRADE_DESC") + "</gdesc>";

                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values from ULBgrade query: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception in ULBgrade query" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equals("ULB")) {
            System.out.println("\n*************\nULB\n**************\n");
            xml = "<response><command>ULB</command>";


            try {
                result =
                        statement.executeQuery("SELECT COUNT(*) AS rec " + "  FROM COM_MST_URBAN_LB " +
                                               "  where DISTRICT_CODE  in (" +
                                               dis + ")" +
                                               "  	and URBANLB_TYPE_ID like '" +
                                               ULBtype + "'" +
                                               "  	and URBANLB_GRADE_ID like '" +
                                               ULBgrade + "'" +
                                               "  	and AREA_TYPE_ID like '" +
                                               area + "'" +
                                               "  	and LOWER(URBANLB_NAME) like '" +
                                               ulb + "%'");
                if (result.next()) {
                    records = result.getInt("rec");
                }
            } catch (Exception e1) {
                System.out.println("Exception in Counting Records query ==> " +
                                   e1);
            }


            start = (page - 1) * limit + 1;
            end = start + limit - 1;
            total = (int)Math.ceil((float)records / limit);


            try {
                result =
                        statement.executeQuery("SELECT " + "  DISTRICT_CODE," +
                                               "  DISTRICT_NAME, " +
                                               "  URBANLB_SNO, " +
                                               "  URBANLB_NAME " + "FROM " +
                                               "  (SELECT " +
                                               "	 ROWNUM AS ID, " +
                                               "    a.DISTRICT_CODE AS DISTRICT_CODE, " +
                                               "    DISTRICT_NAME, " +
                                               "    URBANLB_SNO, " +
                                               "    URBANLB_NAME " +
                                               "  FROM COM_MST_URBAN_LB a join COM_MST_DISTRICTS b " +
                                               "  ON a.DISTRICT_CODE=b.DISTRICT_CODE " +
                                               "  WHERE a.DISTRICT_CODE  in (" +
                                               dis + ")" +
                                               "    AND URBANLB_TYPE_ID like '" +
                                               ULBtype + "'" +
                                               "    AND URBANLB_GRADE_ID like '" +
                                               ULBgrade + "'" +
                                               "    AND AREA_TYPE_ID like '" +
                                               area + "'" +
                                               "    AND LOWER(URBANLB_NAME) like '" +
                                               ulb + "%'" + "  ) " +
                                               "WHERE id BETWEEN " + start +
                                               " AND " + end + " " +
                                               "ORDER BY DISTRICT_NAME,URBANLB_NAME");

                try {
                    xml = xml + "<flag>success</flag>";


                    xml = xml + "<page>" + page + "</page>";
                    xml = xml + "<total>" + total + "</total>";
                    xml = xml + "<records>" + records + "</records>";


                    System.out.println("----------------------");
                    System.out.println("start   : " + start);
                    System.out.println("end     : " + end);
                    System.out.println();
                    System.out.println("page   : " + page);
                    System.out.println("total   : " + total);
                    System.out.println("records : " + records);
                    System.out.println("limit   : " + limit);
                    System.out.println("----------------------");

                    xml += "<ULBtype>" + ULBtype + "</ULBtype>";
                    xml += "<ULBgrade>" + ULBgrade + "</ULBgrade>";
                    xml += "<area>" + area + "</area>";

                    while (result.next()) {
                        xml += "<row>";
                        xml +=
"<dis>" + result.getInt("DISTRICT_CODE") + "</dis>";
                        xml +=
"<district>" + result.getString("DISTRICT_NAME") + "</district>";
                        xml +=
"<ulb>" + result.getString("URBANLB_SNO") + "</ulb>";
                        xml +=
"<urbanlb>" + result.getString("URBANLB_NAME") + "</urbanlb>";
                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception fetching values from 'ULB' resultset ==> " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception in 'ULB' query ==> " + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        if (strCommand.equals("Private")) {
            System.out.println("\n*************\nPrivate\n**************\n");
            xml = "<response><command>Private</command>";


            try {
                result =
                        statement.executeQuery("SELECT COUNT(*) AS rec " + "  FROM COM_MST_PRIVATE " +
                                               "  where LOWER(PRIVATE_DESC) like '" +
                                               priv + "%'" +
                                               "	 AND PCAT_SNO = " + ctype +
                                               "	 AND DISTRICT_CODE  in (" +
                                               dis + ")");
                if (result.next()) {
                    records = result.getInt("rec");
                }
            } catch (Exception e1) {
                System.out.println("Exception in Counting Records query ==> " +
                                   e1);
            }


            start = (page - 1) * limit + 1;
            end = start + limit - 1;
            total = (int)Math.ceil((float)records / limit);


            try {
                String sqlAdd =


                    "SELECT PRIVATE_SNO, " + "	PRIVATE_DESC,  " +
                    "	PCAT_SNO,  " + "   BEN_TYPE_DESC, " + "   DIS, " +
                    "   DISTRICT_NAME " + " FROM   " + "	  ( " +
                    "            SELECT * FROM " + "              ( " +
                    "				 SELECT ROWNUM AS ID,   " + "					PRIVATE_SNO,   " +
                    "					PRIVATE_DESC,   " + "  					PCAT_SNO,   " +
                    "		  			DISTRICT_CODE   " +
                    "				 FROM COM_MST_PRIVATE   " +
                    "		 		 WHERE LOWER(PRIVATE_DESC) like '" + priv + "%'" +
                    "				  AND PCAT_SNO LIKE '" + ctype + "' " +
                    "			 	  AND DISTRICT_CODE  in (" + dis + ")" +
                    "              )a " + "              JOIN " +
                    "              ( " + "                SELECT  " +
                    "                  DISTRICT_CODE AS DIS, " +
                    "                  DISTRICT_NAME " +
                    "                FROM COM_MST_DISTRICTS " +
                    "              )b " +
                    "              ON a.DISTRICT_CODE = b.DIS " +
                    "              JOIN " + "              ( " +
                    "                  SELECT  " +
                    "                    BEN_TYPE_ID, " +
                    "                    BEN_TYPE_DESC " +
                    "                  FROM PMS_DCB_BEN_TYPE " +
                    "              )c " +
                    "              ON a.PCAT_SNO = c.BEN_TYPE_ID " +
                    "	  )   " + " WHERE id BETWEEN " + start + " AND " + end +
                    " ORDER BY PRIVATE_DESC";
                System.out.println("\n------------------------------\n" +
                                   sqlAdd +
                                   "\n------------------------------");
                result = statement.executeQuery(sqlAdd);

                try {
                    xml = xml + "<flag>success</flag>";

                    xml = xml + "<page>" + page + "</page>";
                    xml = xml + "<total>" + total + "</total>";
                    xml = xml + "<records>" + records + "</records>";

                    while (result.next()) {
                        xml += "<row>";

                        xml +=
"<pid>" + result.getInt("PRIVATE_SNO") + "</pid>";
                        xml +=
"<pdesc>" + result.getString("PRIVATE_DESC") + "</pdesc>";
                        xml += "<typ>" + result.getInt("PCAT_SNO") + "</typ>";
                        xml +=
"<type>" + result.getString("BEN_TYPE_DESC") + "</type>";
                        xml += "<dis>" + result.getInt("DIS") + "</dis>";
                        xml +=
"<district>" + result.getString("DISTRICT_NAME") + "</district>";

                        xml += "</row>";
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values from Private query: " +
                                       e);
                }
                result.close();
            } catch (Exception e1) {

                System.out.println("Exception in Private query " + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equals("Duplicate")) {
            System.out.println("\n*************\nDuplicate\n**************\n");
            xml = "<response><command>Duplicate</command>";


            int oid = 0;
            try {
                PreparedStatement ps =
                    connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                ps.setInt(1, empid);
                result = ps.executeQuery();
                if (result.next()) {
                    oid = result.getInt("OFFICE_ID");
                }
                result.close();
                ps.close();
            } catch (Exception e) {
                System.out.println(e);
            }


            try {
                result =
                        statement.executeQuery("SELECT COUNT(*) AS dup " + "FROM PMS_DCB_MST_BENEFICIARY " +
                                               "WHERE OFFICE_ID = " + oid +
                                               "  AND OTHERS_PRIVATE_SNO = " +
                                               priv +
                                               "  AND VILLAGE_PANCHAYAT_SNO = " +
                                               pan + "  AND URBANLB_SNO = " +
                                               ulb);
                try {
                    if (result.next()) {
                        int dup = result.getInt("dup");
                        if (dup == 0) {
                            xml = xml + "<flag>success</flag>";
                        } else {
                            xml = xml + "<flag>duplicate</flag>";
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Exception in the getting values from Duplication Checking query: " +
                                       e);
                    xml = xml + "<flag>failure</flag>";
                }
                result.close();
            } catch (Exception e1) {
                System.out.println("Exception in Duplication Checking query" +
                                   e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equals("ABC")) {
            System.out.println("\n*************\nGENERATE CODE - temp code!!\n**************\n");
            xml = "<response><command>ABC</command>";


            response.setContentType("text/html");

            try {
                int xy = 0;
                result =
                        statement.executeQuery("SELECT block_sno, " + "    PANCH_CODE, " +
                                               "    PANCH_NAME, " +
                                               "    TCODE, " + "    ASCODE, " +
                                               "    PLCODE, " + "    TYPE, " +
                                               "    DISTRICT_CODE, " +
                                               "    STATE_CODE " +
                                               "  FROM COM_MST_PANCHAYATS " +
                                               "  ORDER BY district_code, block_sno, panch_code, panch_name");
                while (result.next()) {
                    System.out.println("Record - " + xy);
                    try {
                        String qry =
                            "INSERT INTO com_mst_panchayats_abc values(" +
                            result.getInt("block_sno") + "," +
                            result.getInt("PANCH_CODE") + "," + "'" +
                            result.getString("panch_name") + "'," +
                            result.getInt("TCODE") + "," +
                            result.getInt("ASCODE") + "," +
                            result.getInt("PLCODE") + "," + "'" +
                            result.getString("TYPE") + "'," +
                            result.getInt("DISTRICT_CODE") + "," +
                            result.getInt("STATE_CODE") + "," + ++xy + ")";

                        PreparedStatement ps =
                            connection.prepareStatement(qry);
                        ps.executeUpdate();
                        ps.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }
}
