package Servlets.FAS.FAS1.FundTransferSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Verify_Fund_Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Verify_Fund_Transfer() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try
        {
            HttpSession session=request.getSession(false);
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        Date txtFrom_date=null,txtTo_date=null;
        
          try {
                                   ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                   String ConnectionString="";
                                   String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                   String strdsn=rs1.getString("Config.DSN");
                                   String strhostname=rs1.getString("Config.HOST_NAME");
                                   String strportno=rs1.getString("Config.PORT_NUMBER");
                                   String strsid=rs1.getString("Config.SID");
                                   String strdbusername=rs1.getString("Config.USER_NAME");
                                   String strdbpassword=rs1.getString("Config.PASSWORD");
                                   ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                   Class.forName(strDriver.trim());
                                   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                  }
                  catch(Exception e)
                      {
                         System.out.println("Exception in openeing connection :"+e);
                       }
        
        System.out.println("servlet called");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String strType = "",xml="<response>";
        try
        {
        strType = request.getParameter("Command");
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
        Calendar c;
        String sql="",cmbStatus="";
        
        HttpSession session=request.getSession(false);
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
        
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbOffice_code "+cmbOffice_code);
        System.out.println("strtype  "+strType);
        txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
        txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
        System.out.println("year..."+txtCB_Year);
        System.out.println("Month..."+txtCB_Month);
        // after receipt status
        cmbStatus=request.getParameter("cmbStatus");
        
        System.out.println("cmbStatus.."+cmbStatus);
        if(strType.equalsIgnoreCase("searchByMonth"))
        {
          xml="<response><command>searchByMonth</command>";
          
          sql="select m.VOUCHER_NO,to_char(m.DATE_OF_TRANSFER,'DD/MM/YYYY') as rec_date,m.REMITTANCE_TYPE ,m.PARTICULARS," +
          "trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,m.OFF_REF_NO,to_char(m.OFF_REF_DATE,'DD/MM/YYYY') as ref_date," +
          "bk.BANK_NAME ||'-'|| br.BRANCH_NAME ||'-'|| coalesce(br.CITY_TOWN_NAME,'0') AS BK_BR_CITY,  " +
           " m.CHEQUE_OR_DD,m.CHEQUE_DD_NO,to_char(m.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date " +
          "from FAS_FUND_TRF_FROM_OFFICE m,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br" +
          " where m.ACCOUNTING_UNIT_ID=? AND (m.VERIFY ='N' OR m.verify IS NULL) and " +
          "m.ACCOUNTING_FOR_OFFICE_ID=? and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=?  and m.TRANSFER_STATUS=? " +
          " and m.HO_BANK_ID=br.BANK_ID and m.HO_BRANCH_ID=br.BRANCH_ID and m.HO_BANK_ID=bk.BANK_ID ";
         try
         {
            
          int count=0;
          ps=con.prepareStatement(sql);//System.out.println(sql);
          ps.setInt(1,cmbAcc_UnitCode);
          ps.setInt(2,cmbOffice_code);
          ps.setInt(3,txtCB_Year);
          ps.setInt(4,txtCB_Month);
          ps.setString(5,cmbStatus);
          xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
          "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
          rs=ps.executeQuery();


              while(rs.next())
              {
                  

                  xml=xml+"<leng>";
                  xml=xml+"<Rec_no>"+rs.getInt("VOUCHER_NO")+"</Rec_no>";
                  xml=xml+"<Rec_Date>"+rs.getString("rec_date")+"</Rec_Date>";
                  

                  xml=xml+"<R_type>"+rs.getString("REMITTANCE_TYPE")+"</R_type>";
                  xml=xml+"<che_or_DD>"+rs.getString("CHEQUE_OR_DD")+ "</che_or_DD>" +
                  "<che_DD_no>"+rs.getString("CHEQUE_DD_NO")+"</che_DD_no>" +
                  "<che_DD_date>"+rs.getString("cheq_dd_date")+"</che_DD_date>" ;
                  

                  xml=xml+"<Remak>"+rs.getString("PARTICULARS")+"</Remak>";
                  xml=xml+"<Tot_Amt>"+rs.getString("TOTAL_AMOUNT") +"</Tot_Amt>";
                  xml=xml+"<BK_BR_CITY>"+rs.getString("BK_BR_CITY").trim()+"</BK_BR_CITY>";
                  

                  xml=xml+"<REF_NO>"+rs.getString("OFF_REF_NO")+"</REF_NO>";
                  xml=xml+"<ref_date>"+rs.getString("ref_date")+"</ref_date>";
                  xml=xml+"</leng>";
                  count++;
              }
             if(count==0) 
             {
                System.out.println("inside count==0");
                xml="<response><command>searchByMonth</command><flag>failure</flag>";
             }      

         }
         catch(SQLException sqle)
          {
            System.out.println("error while fetching data " + sqle);
              xml="<response><command>searchByMonth</command><flag>failure</flag>";
          }          
        }       
        System.out.println("here "+strType.equalsIgnoreCase("searchByDate"));
        if(strType.equalsIgnoreCase("searchByDate"))
        {
          xml="<response><command>searchByDate</command>";
          System.out.println("here "+strType.equalsIgnoreCase("searchByDate"));

//          String txtFrom_date=request.getParameter("txtFrom_date");
          String[] sd=request.getParameter("txtFrom_date").split("/");
          c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
          java.util.Date d=c.getTime();
          txtFrom_date=new Date(d.getTime());
          System.out.println("from_date "+txtFrom_date);
          
//          String txtTo_date=request.getParameter("txtTo_date");
          sd=request.getParameter("txtTo_date").split("/");
          c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
          d=c.getTime();
          txtTo_date=new Date(d.getTime());
          System.out.println("txtTo_date "+txtTo_date);
      //   String deee= to_char("txtTo_date","dd/mm/yyyy");
            
          sql="select m.VOUCHER_NO,to_char(m.DATE_OF_TRANSFER,'DD/MM/YYYY') as rec_date,m.REMITTANCE_TYPE ,m.PARTICULARS," +
          "trim(to_char(m.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,m.OFF_REF_NO,to_char(m.OFF_REF_DATE,'DD/MM/YYYY') as ref_date," +
          "bk.BANK_NAME ||'-'|| br.BRANCH_NAME ||'-'|| coalesce(br.CITY_TOWN_NAME,'0') AS BK_BR_CITY,  " +
           " m.CHEQUE_OR_DD,m.CHEQUE_DD_NO,to_char(m.CHEQUE_DD_DATE,'DD/MM/YYYY') as cheq_dd_date " +
          "from FAS_FUND_TRF_FROM_OFFICE m,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br" +
          "  where m.ACCOUNTING_UNIT_ID=? AND (m.VERIFY ='N' OR m.verify IS NULL) and " +
          "m.ACCOUNTING_FOR_OFFICE_ID=? " +
        //  "and m.CASHBOOK_YEAR=? and m.CASHBOOK_MONTH=? " +
       //  "	and m.DATE_OF_TRANSFER between to_date('2010-12-07',DD/MM/YYYY') and to_char('2011-02-07',DD/MM/YYYY')	"+
          
        //  "and m.DATE_OF_TRANSFER>=to_char(?,'dd-mm-yy')as txtFrom_date " +
        //  "and m.DATE_OF_TRANSFER<=to_char(?,'dd-mm-yy')as txtTo_date" +
          "	and m.DATE_OF_TRANSFER between ? and ? "+
          " and m.TRANSFER_STATUS=? " +
          "and m.HO_BANK_ID=br.BANK_ID and m.HO_BRANCH_ID=br.BRANCH_ID and m.HO_BANK_ID=bk.BANK_ID";
         try
         {
        	 
        	 
        	 
           int count=0;
          ps=con.prepareStatement(sql);System.out.println(sql);
          ps.setInt(1,cmbAcc_UnitCode);
          ps.setInt(2,cmbOffice_code);
//          ps.setInt(3,txtCB_Year);
//          ps.setInt(4,txtCB_Month);
          ps.setDate(3,txtFrom_date);
          ps.setDate(4,txtTo_date);
          ps.setString(5,cmbStatus);
           xml=xml+"<flag>success</flag><Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
           "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
             rs=ps.executeQuery();
                 while(rs.next())
                 {
                     xml=xml+"<leng>";
                     xml=xml+"<Rec_no>"+rs.getInt("VOUCHER_NO")+"</Rec_no>";
                     xml=xml+"<Rec_Date>"+rs.getString("rec_date")+"</Rec_Date>";
                     xml=xml+"<R_type>"+rs.getString("REMITTANCE_TYPE")+"</R_type>";
                     xml=xml+"<che_or_DD>"+rs.getString("CHEQUE_OR_DD")+ "</che_or_DD>" +
                     "<che_DD_no>"+rs.getString("CHEQUE_DD_NO")+"</che_DD_no>" +
                     "<che_DD_date>"+rs.getString("cheq_dd_date")+"</che_DD_date>" ;
                     xml=xml+"<Remak>"+rs.getString("PARTICULARS")+"</Remak>";
                     xml=xml+"<Tot_Amt>"+rs.getString("TOTAL_AMOUNT") +"</Tot_Amt>";
                     xml=xml+"<BK_BR_CITY>"+rs.getString("BK_BR_CITY").trim()+"</BK_BR_CITY>";
                     xml=xml+"<REF_NO>"+rs.getString("OFF_REF_NO")+"</REF_NO>";
                     xml=xml+"<ref_date>"+rs.getString("ref_date")+"</ref_date>";
                     xml=xml+"</leng>";
                     count++;
                 }
                if(count==0) 
                {
                   xml="<response><command>searchByDate</command><flag>failure</flag>";
                }
             }
             catch(SQLException sqle)
             {
               System.out.println("error while fetching data " + sqle);
                 xml="<response><command>searchByDate</command><flag>failure</flag>";
             }
        }
        xml=xml+"</response>";
        out.println(xml);
        System.out.println(xml);
    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strType="";
		Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try {
            ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";
            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		}
		catch(Exception e)
		{
		  System.out.println("Exception in openeing connection :"+e);
		}
        
		try
        {
        strType = request.getParameter("Command");
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
        Date txtFrom_date=null,txtTo_date=null;
        Calendar c;
        String sql="";
        
        HttpSession session=request.getSession(false);
        String update_user;
        update_user = (String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
		if (strType.equalsIgnoreCase("Update"))
        {
        /** Voucher Number */ 
        String voucher_no=request.getParameter("voucher_no");        
        String voucher_no1[]=voucher_no.split(",");        
        int voucher_no2[]=new int [voucher_no1.length];   
        for(int i=0;i<voucher_no1.length;i++)
        {
            voucher_no2[i]=Integer.parseInt(voucher_no1[i]);
        }  
        String flag="";	   
        String chcksel=request.getParameter("chcksel");
        String chcksel1[]=chcksel.split(",");
        int chcksel2[]=new int [chcksel1.length];
        for(int i=0;i<chcksel1.length;i++)
        {
            chcksel2[i]=Integer.parseInt(chcksel1[i]);
        }
        
        /** Voucher  Date */
        String date_trans=request.getParameter("voucher_date");        
        String date_trans1[]=date_trans.split(",");               
        Date date_trans2[]=new Date [date_trans1.length]; 
	   
        try 
        {
        	
           
            int vno=0; int count=0,yr=0,mon=0; 
            sql="update FAS_FUND_TRF_FROM_OFFICE set  VERIFY=?,VERIFIED_DATE=?,VERIFIED_BY=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?"; 
            ps=con.prepareStatement(sql);
            for(int j=0;j<chcksel2.length;j++)
            {
                System.out.println("the total length is"+chcksel2.length);
                vno=voucher_no2[j];                
                
                String[] sd2=date_trans1[j].split("/");
                int year[]=new int [sd2.length];  
                int month[]=new int [sd2.length];  
                
                c=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
                java.util.Date d2=c.getTime();
                date_trans2[j]=new Date(d2.getTime());
                
                year[j]=Integer.parseInt(sd2[2]);
                month[j]=Integer.parseInt(sd2[1]);
                
				yr=year[j];
                mon=month[j];
                                
                	 ps.setString(1,"Y");
                     ps.setTimestamp(2,ts);
                     ps.setString(3,update_user);
                     ps.setInt(4,cmbAcc_UnitCode);
                     ps.setInt(5,cmbOffice_code);
                     ps.setInt(6,vno); 
                     ps.setInt(7,yr);
                     ps.setInt(8,mon); 
                     
               vno=0;yr=0;mon=0;
               int i=ps.executeUpdate();
               if(i>0)
               {
            	   count++;
            	   flag="success";
               }
               else
               {
            	   flag="failure";
               }
               System.out.println(flag);
            }
            if(flag=="success")
            {
            	//con.commit();
                sendMessage(response,"Updated Successfully","ok");
            }
                   
        }
        catch(Exception e) 
        {
           System.out.println("the exception in update is"+e.getMessage());               
        }         
       finally
       {
          System.out.println("done here");
          try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
       }         
	}
	}
	
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
    	try
    	{
    		System.out.println("Inside.....................");
    		String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
    		response.sendRedirect(url);
    	}
    	catch(Exception e)
    	{
    		System.out.println("error in messenger"+e);
    	}        	
    }
    
}
