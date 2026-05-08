package Servlets.FAS.FAS1.MTCRegister.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MTC70Register_Updated_By_Treasury_Section
 */
public class MTC70Register_Updated_By_Treasury_Section extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MTC70Register_Updated_By_Treasury_Section() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0;
		String unitname = "";
		int accid = 0;

		try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		String strCommand = "";
		String xml = "";

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try {
			System.out.println("chk 3");
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("command");
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		if (strCommand.equalsIgnoreCase("gett")) {

			xml = xml + "<response><command>gett</command>";
			xml = xml + "<empid>" + empid + "</empid>";
			xml = xml + "<empName>" + empName + "</empName>";
			
			int year = Integer.parseInt(request.getParameter("year"));
			try {
				String su = "select MTC70_REGISTER_NO from FAS_MTC70_REGISTER_MST where CASHBOOK_YEAR=?";				
				ps = connection.prepareStatement(su);
				ps.setInt(1, year);
				rs = ps.executeQuery();
				while (rs.next()) {
					
															
					xml = xml + "<mtc70RegisterNo>"
							+ rs.getInt("MTC70_REGISTER_NO")
							+ "</mtc70RegisterNo>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if (strCommand.equalsIgnoreCase("getRegisterDate")) {
			xml = xml + "<response><command>getRegisterDate</command>";
			int RegisterNo = Integer.parseInt(request.getParameter("MTCRegisterNo"));
			String mtcRegisterDate=null;
			try {
				String su = "select MTC70_REGISTER_DATE from FAS_MTC70_REGISTER_MST where MTC70_REGISTER_NO=?";				
				ps = connection.prepareStatement(su);
				ps.setInt(1, RegisterNo);
				rs = ps.executeQuery();
				while (rs.next()) {
					
					Date mtcRegisterDate1=rs.getDate("MTC70_REGISTER_DATE");
					
					String Stringdate = mtcRegisterDate1.toString();
					
					String[] ddd = Stringdate.split("-");
										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					if(month>=10)
			        {
						mtcRegisterDate=(day+"/"+month+"/"+year);
			        }
			        else
			        {
			        	mtcRegisterDate=(day+"/0"+month+"/"+year);	
			        }	   
					

					xml = xml + "<mtc70RegisterDate>"
							+ mtcRegisterDate
							+ "</mtc70RegisterDate>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";
			
			int RegisterNo = Integer.parseInt(request.getParameter("MTCRegisterNo"));
			
			java.sql.Date MTCEntryDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("MTCEntryDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			MTCEntryDate = new Date(d.getTime());
			
			int RecevedBy=Integer.parseInt(request.getParameter("EmpID_mas"));
			
			java.sql.Date ReceivedOn = null;
			java.util.GregorianCalendar c3;
			String[] sd3 = request.getParameter("ReceivedOn").split("/");
			c3 = new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),
					Integer.parseInt(sd3[1]) - 1, Integer.parseInt(sd3[0]));
			java.util.Date d3 = c3.getTime();
			ReceivedOn = new Date(d3.getTime());
			
			java.sql.Date UpdatedOn = null;
			java.util.GregorianCalendar c4;
			String[] sd4 = request.getParameter("UpdatedOn").split("/");
			c4 = new java.util.GregorianCalendar(Integer.parseInt(sd4[2]),
					Integer.parseInt(sd4[1]) - 1, Integer.parseInt(sd4[0]));
			java.util.Date d4 = c4.getTime();
			UpdatedOn = new Date(d4.getTime());
			
			int UpdatedBy=Integer.parseInt(request.getParameter("UpdatedBy"));
			
			java.sql.Date SenttoPreAuditon = null;
			java.util.GregorianCalendar c5;
			String[] sd5 = request.getParameter("SenttoPreAuditon").split("/");
			c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
					Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
			java.util.Date d5 = c5.getTime();
			SenttoPreAuditon = new Date(d5.getTime());
			
			String Remarks = request.getParameter("Remarks");
			
			try {
				xml = xml + "<flag>success</flag>";
				ps = connection
						.prepareStatement("update FAS_MTC70_REGISTER_MST set RECEIVED_BY=?,RECEIVED_DATE=?,REGISTER_UPDATED_DATE=?,REGISTER_UPDATED_BY=?,PRE_AUDIT_SENT_DATE=?,UPDATED_REMARKS=? where MTC70_REGISTER_NO=?");
				ps.setInt(1, RecevedBy);
				ps.setDate(2, ReceivedOn);
				ps.setDate(3, UpdatedOn);
				ps.setInt(4, UpdatedBy);
				ps.setDate(5, SenttoPreAuditon);
				ps.setString(6, Remarks);
				ps.setInt(7, RegisterNo);
				
				ps.executeUpdate();
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);

	}

}
