package Servlets.HR.HR1.EmployeeMaster.Reports;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DesigCadRankServ_New extends HttpServlet {


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
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
            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        /*try
     {
            if(session==null)
             {
                    String xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
                     out.println(xml);
                     System.out.println(xml);
                     out.close();
                     return;

                 }
                 System.out.println(session);

     }catch(Exception e)
     {
             System.out.println("Redirect Error :"+e);
     }
    */


        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");

        //String xml="";
        String html = "";
        String strCommand = "";
        int sgroup = 0;
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            sgroup = Integer.parseInt(request.getParameter("cmbsgroup"));
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        if (strCommand.equalsIgnoreCase("Desig")) {
            // xml="<response>";
            try {
                System.out.println("Desig" + strCommand);
                System.out.println("sgroup::" + sgroup);
                ps =
  con.prepareStatement("select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS  where SERVICE_GROUP_ID=? order by DESIGNATION");
                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                //  xml=xml+"<flag>success</flag>";
                html =
"<table cellspacing=0 cellpadding=0 border=0 width=100%>";
                boolean bool = false;
                while (rs.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor='pink'><td width='5'><input type='checkbox' name='chkdesig' value='" +
 rs.getInt("DESIGNATION_ID") + "' onclick='desigChange()'></td>";
                        html =
html + "<td>" + rs.getString("DESIGNATION") + "</td></tr>";
                    } else {
                        html =
html + "<tr><td width='10%'><input type='checkbox' name='chkdesig' value='" +
 rs.getInt("DESIGNATION_ID") + "' onclick='desigChange()'></td>";
                        html =
html + "<td width='*'>" + rs.getString("DESIGNATION") + "</td></tr>";
                    }
                    count++;
                }

                /*   while(rs.next())
          {

             xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
             count++;
          }
          System.out.println("count::"+count);
              if(count==0)
                  xml="<response><flag>failure</flag>";
             else
                 xml=xml+"<flag>success</flag>";*/
                // html=html+"</table>";
                if (count == 0) {
                    html = "There is designation";
                } else {

                    html =
html + "<tr ><td colspan='2'><a href='javascript:designationselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:designationclose()'>Close</a></td></tr>";
                    html = html + "</table>";

                }
            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                //  xml="<response><flag>failure</flag>";
            }

            //   xml=xml+"</response>";

            out.println(html);
            System.out.println(html);


        } else if (strCommand.equalsIgnoreCase("Rank")) {
            // xml="<response>";
            try {
                System.out.println("Rank" + strCommand);
                System.out.println("sgroup::" + sgroup);
                ps =
  con.prepareStatement("select  post_rank_id , post_rank_name from HRM_MST_POST_RANKS where post_rank_id in(\n" +
                       "select post_rank_id from hrm_mst_designations\n" +
                       "where service_group_id =?) order by POST_RANK_NAME");
                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                //  xml=xml+"<flag>success</flag>";
                html =
"<table cellspacing=0 cellpadding=0 border=0 width=100%>";
                boolean bool = false;
                while (rs.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor='pink'><td width='5'><input type='checkbox' name='chkrank' value='" +
 rs.getInt("POST_RANK_ID") + "' onclick='rankChange()'></td>";
                        html =
html + "<td>" + rs.getString("POST_RANK_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr><td width='10%'><input type='checkbox' name='chkrank' value='" +
 rs.getInt("POST_RANK_ID") + "' onclick='rankChange()'></td>";
                        html =
html + "<td width='*'>" + rs.getString("POST_RANK_NAME") + "</td></tr>";
                    }
                    count++;
                }

                /*   while(rs.next())
          {

             xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
             count++;
          }
          System.out.println("count::"+count);
              if(count==0)
                  xml="<response><flag>failure</flag>";
             else
                 xml=xml+"<flag>success</flag>";*/
                // html=html+"</table>";
                if (count == 0) {
                    html = "There is no rank";
                } else {

                    html =
html + "<tr ><td colspan='2'><a href='javascript:rankselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:designationclose()'>Close</a></td></tr>";
                    html = html + "</table>";

                }
            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                //  xml="<response><flag>failure</flag>";
            }

            //   xml=xml+"</response>";

            out.println(html);
            System.out.println(html);


        } else if (strCommand.equalsIgnoreCase("Cadre")) {
            // xml="<response>";
            try {
                System.out.println("Cadre" + strCommand);
                System.out.println("sgroup::" + sgroup);
                ps =
  con.prepareStatement("select distinct a.service_group_id,b.cadre_id,b.cadre_name from" +
                       " (" +
                       " select service_group_id,cadre_id from hrm_mst_designations" +
                       " ) a" + " left outer join" + " (" +
                       " select cadre_id,cadre_name from hrm_mst_cadre" +
                       " ) b" + " on a.cadre_id=b.cadre_id" +
                       " where a.service_group_id=? order by b.cadre_name");

                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                //  xml=xml+"<flag>success</flag>";
                html =
"<table cellspacing=0 cellpadding=0 border=0 width=100%>";
                boolean bool = false;
                while (rs.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor='pink'><td width='5'><input type='checkbox' name='chkcadre' value='" +
 rs.getInt("cadre_id") + "' onclick='cadreChange()'></td>";
                        html =
html + "<td>" + rs.getString("cadre_name") + "</td></tr>";
                    } else {
                        html =
html + "<tr><td width='10%'><input type='checkbox' name='chkcadre' value='" +
 rs.getInt("cadre_id") + "' onclick='cadreChange()'></td>";
                        html =
html + "<td width='*'>" + rs.getString("cadre_name") + "</td></tr>";
                    }
                    count++;
                }

                /*   while(rs.next())
            {

               xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
               count++;
            }
            System.out.println("count::"+count);
                if(count==0)
                    xml="<response><flag>failure</flag>";
               else
                   xml=xml+"<flag>success</flag>";*/
                // html=html+"</table>";
                if (count == 0) {
                    html = "There is no cadre";
                } else {

                    html =
html + "<tr ><td colspan='2'><a href='javascript:cadreselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:designationclose()'>Close</a></td></tr>";
                    html = html + "</table>";

                }
            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                //  xml="<response><flag>failure</flag>";
            }

            //   xml=xml+"</response>";

            out.println(html);
            System.out.println(html);


        }

        /*   else if(strCommand.equalsIgnoreCase("Rank"))
     {
         xml="<response>";
          try
         {
         //int sgroup=Integer.parseInt(request.getParameter("cmbsgroup"));
         //System.out.println("sgroup::"+sgroup);
     ps=con.prepareStatement("select POST_RANK_ID,POST_RANK_NAME from HRM_MST_POST_RANKS  where SERVICE_GROUP_ID=? order by POST_RANK_NAME" );
     ps.setInt(1,sgroup);
     rs=ps.executeQuery();
     int count=0;
         xml=xml+"<flag>success</flag>";
     while(rs.next())           {

        xml=xml+"<option><id>"+rs.getInt("POST_RANK_ID")+"</id><name>"+rs.getString("POST_RANK_NAME")+"</name></option>";
        count++;
     }
     System.out.println("count::"+count);
         if(count==0)
             xml="<response><flag>failure</flag>";
        else
            xml=xml+"<flag>success</flag>";

     }

     catch(Exception e) {

         System.out.println("catch........"+e);
        xml="<response><flag>failure</flag>";
     }

     xml=xml+"</response>";

         out.println(xml);
         System.out.println(xml);


     }



      else if(strCommand.equalsIgnoreCase("Cadre"))
      {
          xml="<response>";
           try
          {
          //int sgroup=Integer.parseInt(request.getParameter("cmbsgroup"));
          //System.out.println("sgroup::"+sgroup);
      ps=con.prepareStatement("select distinct a.service_group_id,b.cadre_id,b.cadre_name from" +
                              " (" +
                              " select service_group_id,cadre_id from hrm_mst_designations" +
                              " ) a" +
                              " left outer join" +
                              " (" +
                              " select cadre_id,cadre_name from hrm_mst_cadre" +
                              " ) b" +
                              " on a.cadre_id=b.cadre_id" +
                              " where a.service_group_id=? order by b.cadre_name");

                          ps.setInt(1,sgroup);
                          rs=ps.executeQuery();
                          int count=0;
                          xml=xml+"<flag>success</flag>";

                          while(rs.next())
                          {

                           xml=xml+"<option><id>"+rs.getInt("cadre_id")+"</id><name>"+rs.getString("cadre_name")+"</name></option>";
                           count++;
                           }
                          System.out.println("count::"+count);
                          if(count==0)
                            xml="<response><flag>failure</flag>";
                          else
                          xml=xml+"<flag>success</flag>";

                        }

                       catch(Exception e)
                       {

                         System.out.println("catch........"+e);
                         xml="<response><flag>failure</flag>";
                       }

                      xml=xml+"</response>";

                      out.println(xml);
                      System.out.println(xml);


            }*/

        System.out.println("end");

    }

}
