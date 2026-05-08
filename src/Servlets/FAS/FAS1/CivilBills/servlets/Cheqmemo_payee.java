package Servlets.FAS.FAS1.CivilBills.servlets;

//import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Cheqmemo_payee extends HttpServlet
{
  //private static final String CONTENT_TYPE="text/xml; charset=windows-1252";
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException		
 	{
		String CONTENT_TYPE="text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                response.setHeader("Cache-Control","no-cache");
                System.out.println("Welcome to Cheque Memo PayeeType Servlet");
		String cmnd="";
		String xml="";
                String user_id;
                user_id = "";
                int count=0;int cheqmemo_code=0;int payee_code=0;int slno=0;String status="";
                String update_user="";
                HttpSession session=null;
                Timestamp ts=null;
                PrintWriter pw=response.getWriter();
                
                /*********** connection establishment****************/
                Connection con=null;
                ResultSet rs2,rs3;rs2=null;rs3=null;
                PreparedStatement ps2,ps3;ps2=null;ps3=null;
                xml="<response>";
                try
                {
                            ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                            String ConnectionString="";
                            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                            String strdsn=rs1.getString("Config.DSN");
                            String strhostname=rs1.getString("Config.HOST_NAME");
                            String strportno=rs1.getString("Config.PORT_NUMBER");
                            String strsid=rs1.getString("Config.SID");
                            String strdbusername=rs1.getString("Config.USER_NAME");
                            String strdbpassword=rs1.getString("Config.PASSWORD");
                                             
                            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                }
                catch(Exception e)
                {
                        System.out.println("Exception in connection...."+e);
                } 
                      try
                      {
                          session=request.getSession(false);
                          if(session==null)
                          {
                              System.out.println(request.getContextPath()+"/index.jsp");
                              response.sendRedirect(request.getContextPath()+"/index.jsp");
                              return;
                          }
                          System.out.println(session);
                      } 
                      catch(Exception e)
                      {
                            System.out.println("Redirect Error :"+e);
                      }
                        String userid=(String)session.getAttribute("UserId");
                        System.out.println("session id is:"+userid);
                        update_user=(String)session.getAttribute("UserId");
                        long l=System.currentTimeMillis();
                        ts=new Timestamp(l);           
   
                      /****************** getting the values from Button Pressed***********/
                    try
                        {
                              cmnd =  request.getParameter("command");     
                              System.out.println("Command passed via the button pressed : " + cmnd);
                        }
                    catch(Exception e3)
                      {
                        e3.printStackTrace();
                      }
                  
                  /*****************Getting the values from jsp page ***************/
                   if(cmnd.equalsIgnoreCase("loadCheqmemocode"))
                   {
                       xml=xml+"<command>loadCheqcode</command>";
                       try
                       {
                           String sql="select cheque_memo_type_code,cheque_memo_desc from fas_cheque_memo_types_mst where status='L' order by cheque_memo_type_code";
                           ps2=con.prepareStatement(sql);
                           rs2=ps2.executeQuery();
                           while(rs2.next())
                           {
                               xml=xml+"<option><desc>"+rs2.getString("cheque_memo_desc")+"</desc><id>"+rs2.getInt("cheque_memo_type_code")+"</id></option>";
                               count++;
                           } // while close
                           if(count>0)
                               xml=xml+"<flag>success</flag>";
                           else
                               xml=xml+"<flag>nodata</flag>";
                               ps2.close();
                               rs2.close();
                       } //try close
                       catch(Exception e)
                       {
                           xml=xml+"<flag>"+e.getMessage()+"</flag>";
                           System.out.println(e);
                       }
                   }
                   
                   else if(cmnd.equalsIgnoreCase("loadPayeecode")) 
                   {
                       xml=xml+"<command>loadPayeecode</command>";
                       try
                       {
                           String sql="select payee_type_code,payee_type_desc from fas_payee_types_mst where status='L' order by payee_type_code";
                           ps2=con.prepareStatement(sql);
                           rs2=ps2.executeQuery();
                           while(rs2.next())
                           {
                               xml=xml+"<option><desc>"+rs2.getString("payee_type_desc")+"</desc><id>"+rs2.getInt("payee_type_code")+"</id></option>";
                               count++;
                           } // while close
                           if(count>0)
                               xml=xml+"<flag>success</flag>";
                           else
                               xml=xml+"<flag>nodata</flag>";
                               ps2.close();
                               rs2.close();
                       } //try close
                       catch(Exception e)
                       {
                           xml=xml+"<flag>"+e.getMessage()+"</flag>";
                           System.out.println(e);
                       }
                   
                   }
            if(cmnd.equalsIgnoreCase("add")) 
            {
                xml=xml+"<command>addResponse</command>";

                        try
                        {    
                             String sqlsel="select decode(max(SERIALNUMBER),null,0,max(SERIALNUMBER))as SERIALNUMBER from FAS_CHEQUEMEMO_PAYEE_TYPES_MST";    
                             ps2=con.prepareStatement(sqlsel);
                             rs2=ps2.executeQuery();
                             if(rs2.next())
                             {
                                slno=rs2.getInt("SERIALNUMBER");
                             }
                                slno=slno+1;
                                 System.out.println("Maximum value of SERIALNUMBER is :"+slno);
                                 ps2.close();
                                 rs2.close();
                        }
                         catch(Exception e11)
                         {
                         System.out.println("Exception arised finding the maximum value **** :"+e11);
                         }
                         try
                         {
                            //slno++;
                            cheqmemo_code=Integer.parseInt(request.getParameter("cheqmemocode1"));
                            payee_code=Integer.parseInt(request.getParameter("payeecode1"));
                            System.out.println("code::::::"+cheqmemo_code);
                            System.out.println("code::::::"+payee_code);
                            status="L";
                            
                                    String sqlsel="select * from FAS_CHEQUEMEMO_PAYEE_TYPES_MST where CHEQUE_MEMO_TYPE_CODE=? and PAYEE_TYPE_CODE=?";
                                    ps2 = con.prepareStatement(sqlsel);
                                    System.out.println("select query :::"+sqlsel);
                                    ps2.setInt(1,cheqmemo_code);
                                    ps2.setInt(2,payee_code);
                                    rs2=ps2.executeQuery();
                                        if (!rs2.next()) {
                                        String sqlload="insert into FAS_CHEQUEMEMO_PAYEE_TYPES_MST                "   +
                                        "               (SERIALNUMBER,CHEQUE_MEMO_TYPE_CODE,PAYEE_TYPE_CODE,       " +
                                        "               STATUS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?)";
                                        System.out.println("insert query----"+sqlload);
                                        ps2 = con.prepareStatement(sqlload);
                                        ps2.setInt(1,slno);
                                        ps2.setInt(2,cheqmemo_code);
                                        ps2.setInt(3,payee_code);
                                        ps2.setString(4,status);
                                        ps2.setString(5,update_user);
                                        ps2.setTimestamp(6,ts);
                                        ps2.executeUpdate();
                                        count++;
                                        if(count>0){
                                            
                                            xml=xml+"<cheqmemocode>"+cheqmemo_code+"</cheqmemocode>";
                                            xml=xml+"<payeecode>"+payee_code+"</payeecode>";
                                            xml=xml+"<slno>"+slno+"</slno>";                                            
                                            xml=xml+"<flag>success</flag>";
                                        }
                                        ps2.close();
                                  } 
                                  else 
                                  {
                                       xml=xml+"<flag>AlreadyExist</flag>";
                                  }
                            }
                         catch(Exception e)
                         {
                             System.out.println("Exception arised :::::"+e);
                         }
                }
                else if(cmnd.equalsIgnoreCase("loadlist")) 
                {
                    xml=xml+"<command>gett</command>";
                    int cheqtype=Integer.parseInt(request.getParameter("cheqtype"));
                    String sqlload="";
                    try
                    {   
                    	if(cheqtype==0)
                    	{
                                sqlload="    select a.*,b.CHEQUE_MEMO_DESC,c.PAYEE_TYPE_DESC from        "  + 
                                "                   (                                                                 " + 
                                "                   (                                                                 "   +
                                "                   select SERIALNUMBER,CHEQUE_MEMO_TYPE_CODE,PAYEE_TYPE_CODE,STATUS from FAS_CHEQUEMEMO_PAYEE_TYPES_MST order by SERIALNUMBER)a   " + 
                                "                   left outer join                                                   " + 
                                "                   (                                                                   " +
                                "                   select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST)b      " + 
                                "                   on a.CHEQUE_MEMO_TYPE_CODE=b.CHEQUE_MEMO_TYPE_CODE                    " + 
                                "                   left outer join                                                       " + 
                                "                   (                                                                       " +
                                "                   select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST)c     " + 
                                "                   on a.PAYEE_TYPE_CODE=c.PAYEE_TYPE_CODE                                " + 
                                "                   )                                                                       ";
                    	}
                    	else
                    	{
                    		sqlload="    select a.*,b.CHEQUE_MEMO_DESC,c.PAYEE_TYPE_DESC from        "  + 
                            "                   (                                                                 " + 
                            "                   (                                                                 "   +
                            "                   select SERIALNUMBER,CHEQUE_MEMO_TYPE_CODE,PAYEE_TYPE_CODE,STATUS from FAS_CHEQUEMEMO_PAYEE_TYPES_MST where CHEQUE_MEMO_TYPE_CODE= "+cheqtype + "order by SERIALNUMBER)a   " + 
                            "                   left outer join                                                   " + 
                            "                   (                                                                   " +
                            "                   select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST)b      " + 
                            "                   on a.CHEQUE_MEMO_TYPE_CODE=b.CHEQUE_MEMO_TYPE_CODE                    " + 
                            "                   left outer join                                                       " + 
                            "                   (                                                                       " +
                            "                   select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST)c     " + 
                            "                   on a.PAYEE_TYPE_CODE=c.PAYEE_TYPE_CODE                                " + 
                            "                   )                                                                       ";
                    	}
                                ps2 = con.prepareStatement(sqlload);
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    xml=xml+"<serial_no>"+rs2.getInt("SERIALNUMBER")+"</serial_no>";
                                    xml=xml+"<cheqmemo_code>"+rs2.getInt("CHEQUE_MEMO_TYPE_CODE")+"</cheqmemo_code>";
                                    xml=xml+"<cheqmemo_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_desc>";
                                    xml=xml+"<status>"+rs2.getString("STATUS")+"</status>";
                                    xml=xml+"<payee_code>"+rs2.getInt("PAYEE_TYPE_CODE")+"</payee_code>";
                                    xml=xml+"<payee_desc>"+rs2.getString("PAYEE_TYPE_DESC")+"</payee_desc>";
                                    count++;
                                }
                                 if(count>0)
                                 {
                                     xml=xml+"<flag>success</flag>"; 
                                 }
                                 else
                                 {
                                     xml=xml+"<flag>nodata</flag>";    
                                 }
                         ps2.close();
                         rs2.close();
                     } //try close
                      catch(Exception e)
                      {
                                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                        System.out.println(e);
                       }
                }
                else if(cmnd.equalsIgnoreCase("retrieve")) 
                {
                    xml=xml+"<command>retrieve</command>";   
                    try
                    {
                            int serial_no=Integer.parseInt(request.getParameter("serial_no1"));
                            System.out.println("Serial no*****"+serial_no);
                           
                           String sql=" select a.*,b.CHEQUE_MEMO_DESC,c.PAYEE_TYPE_DESC from                                                       " + 
                           "            (                                                                                                               " + 
                           "            (                                                                                                               " + 
                           "            select CHEQUE_MEMO_TYPE_CODE,PAYEE_TYPE_CODE from FAS_CHEQUEMEMO_PAYEE_TYPES_MST where SERIALNUMBER=?)a         " + 
                           "            left outer join                                                                                                 " + 
                           "            (                                                                                                               " + 
                           "            select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST)b                             " + 
                           "            on a.CHEQUE_MEMO_TYPE_CODE=b.CHEQUE_MEMO_TYPE_CODE                                                              " + 
                           "            left outer join                                                                                                 " + 
                           "            (                                                                                                               " + 
                           "            select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST)c                                               " + 
                           "            on a.PAYEE_TYPE_CODE=c.PAYEE_TYPE_CODE                                                                          " + 
                           "            )                                                                                                               ";                                                                                                              
                           ps2 =con.prepareStatement(sql);   
                           ps2.setInt(1,serial_no);
                           rs2=ps2.executeQuery();
                           while(rs2.next()) 
                           {
                               xml=xml+"<cheqmemo_code>"+rs2.getInt("CHEQUE_MEMO_TYPE_CODE")+"</cheqmemo_code>";
                               xml=xml+"<cheqmemo_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_desc>";
                               xml=xml+"<payee_code>"+rs2.getInt("PAYEE_TYPE_CODE")+"</payee_code>";
                               xml=xml+"<payee_desc>"+rs2.getString("PAYEE_TYPE_DESC")+"</payee_desc>";
                               count++;
                           }
                            if(count>0)
                                xml = xml+"<flag>success</flag>";
                            else
                                xml=xml+"<flag>failure</flag>";
                        ps2.close();
                        rs2.close();
                    }
                    catch(Exception e) 
                    {
                        System.out.println("Exception in retrieving records ===> "+e);
                        xml=xml+"<flag>failure</flag>";
                    }
                }
            else if(cmnd.equalsIgnoreCase("updated")) 
             {
                 xml=xml+"<command>updated</command>";
                     try
                     {    
                         System.out.println("calling update query");  
                          int cheqmemocode=Integer.parseInt(request.getParameter("cheqmemocode1"));
                          System.out.println("code ::::::"+cheqmemocode);
                          int payeecode=Integer.parseInt(request.getParameter("payeecode1"));
                          System.out.println("description::::::"+payeecode);
                          slno=Integer.parseInt(request.getParameter("serialno1"));
                          System.out.println("serial no :"+slno);
                            
                             String sqlload="update FAS_CHEQUEMEMO_PAYEE_TYPES_MST set CHEQUE_MEMO_TYPE_CODE=?,PAYEE_TYPE_CODE=? where SERIALNUMBER=?";
                             System.out.println("update query---"+sqlload);
                             ps2 = con.prepareStatement(sqlload);
                             ps2.setInt(1,cheqmemocode);
                             ps2.setInt(2,payeecode);
                             ps2.setInt(3,slno);
                             ps2.executeUpdate();
                             count++;
                         if(count>0)
                         {
                             System.out.println("record updated successfully");
                             xml=xml+"<flag>success</flag>"; 
                         }
                          ps2.close();
                      } //try close
                       catch(Exception e)
                       {
                                         xml=xml+"<flag>failure</flag>";
                                         System.out.println(e);
                        }
             }
            else if(cmnd.equalsIgnoreCase("deleted"))
            {
                xml=xml+"<command>deleted</command>";
                slno=Integer.parseInt(request.getParameter("serialno1"));
                System.out.println("serial no ::::"+slno);
                try {
                        //ps2 = con.prepareStatement("delete from FAS_CHEQUEMEMO_PAYEE_TYPES_MST where SERIALNUMBER=?");  
                        ps2 = con.prepareStatement("update FAS_CHEQUEMEMO_PAYEE_TYPES_MST set STATUS='C' where SERIALNUMBER=?");
                        ps2.setInt(1,slno);
                        ps2.executeUpdate();                        
                        xml = xml+"<flag>success</flag>";
                        ps2.close();
                        rs2.close();
                    }
                    
                catch(Exception e) 
                    {
                        xml=xml+"<flag>failure</flag>";
                    }
            }
            
            else if(cmnd.equalsIgnoreCase("loadlist1")){
    			xml=xml+"<command>loadlist1</command>";
    			String cheqq=request.getParameter("cheqtype");
    			System.out.println("cheq"+cheqq+"-----");
    			int cheqtype=0;
    			if((!cheqq.equalsIgnoreCase(" "))||(!cheqq.equalsIgnoreCase(null))||(!cheqq.equalsIgnoreCase(""))||(!cheqq.equalsIgnoreCase("0"))){
    				cheqtype=Integer.parseInt(cheqq);
    			}
    		
                String sqlload="";
                try
                {   
                	if(cheqtype==0)
                	{
                            sqlload="    select a.*,b.CHEQUE_MEMO_DESC,c.PAYEE_TYPE_DESC from        "  + 
                            "                   (                                                                 " + 
                            "                   (                                                                 "   +
                            "                   select SERIALNUMBER,CHEQUE_MEMO_TYPE_CODE,PAYEE_TYPE_CODE,STATUS from FAS_CHEQUEMEMO_PAYEE_TYPES_MST order by SERIALNUMBER)a   " + 
                            "                   left outer join                                                   " + 
                            "                   (                                                                   " +
                            "                   select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST)b      " + 
                            "                   on a.CHEQUE_MEMO_TYPE_CODE=b.CHEQUE_MEMO_TYPE_CODE                    " + 
                            "                   left outer join                                                       " + 
                            "                   (                                                                       " +
                            "                   select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST)c     " + 
                            "                   on a.PAYEE_TYPE_CODE=c.PAYEE_TYPE_CODE                                " + 
                            "                   )                                                                       ";
                	}
                	else
                	{
                		sqlload="    select a.*,b.CHEQUE_MEMO_DESC,c.PAYEE_TYPE_DESC from        "  + 
                        "                   (                                                                 " + 
                        "                   (                                                                 "   +
                        "                   select SERIALNUMBER,CHEQUE_MEMO_TYPE_CODE,PAYEE_TYPE_CODE,STATUS from FAS_CHEQUEMEMO_PAYEE_TYPES_MST where CHEQUE_MEMO_TYPE_CODE= "+cheqtype + "order by SERIALNUMBER)a   " + 
                        "                   left outer join                                                   " + 
                        "                   (                                                                   " +
                        "                   select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST)b      " + 
                        "                   on a.CHEQUE_MEMO_TYPE_CODE=b.CHEQUE_MEMO_TYPE_CODE                    " + 
                        "                   left outer join                                                       " + 
                        "                   (                                                                       " +
                        "                   select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST)c     " + 
                        "                   on a.PAYEE_TYPE_CODE=c.PAYEE_TYPE_CODE                                " + 
                        "                   )                                                                       ";
                	}
                	System.out.println("qr y  "+sqlload);
                            ps2 = con.prepareStatement(sqlload);
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                xml=xml+"<serial_no>"+rs2.getInt("SERIALNUMBER")+"</serial_no>";
                                xml=xml+"<cheqmemo_code>"+rs2.getInt("CHEQUE_MEMO_TYPE_CODE")+"</cheqmemo_code>";
                                xml=xml+"<cheqmemo_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_desc>";
                                xml=xml+"<status>"+rs2.getString("STATUS")+"</status>";
                                xml=xml+"<payee_code>"+rs2.getInt("PAYEE_TYPE_CODE")+"</payee_code>";
                                xml=xml+"<payee_desc>"+rs2.getString("PAYEE_TYPE_DESC")+"</payee_desc>";
                                count++;
                            }
                             if(count>0)
                             {
                                 xml=xml+"<flag>success</flag>"; 
                             }
                             else
                             {
                                 xml=xml+"<flag>nodata</flag>";    
                             }
                     ps2.close();
                     rs2.close();
                 } //try close
                  catch(Exception e)
                  {
                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                    System.out.println(e);
                   }
    			System.out.println("sssl "+xml);
    			
    		}else if(cmnd.equalsIgnoreCase("edit")){
    			xml=xml+"<command>loadlist1</command>";
    			try {
    				int serial_no=Integer.parseInt(request.getParameter("serial_no1"));
                    System.out.println("Serial no*****"+serial_no);
                   
                   String sql=" select a.*,b.CHEQUE_MEMO_DESC,c.PAYEE_TYPE_DESC from                                                       " + 
                   "            (                                                                                                               " + 
                   "            (                                                                                                               " + 
                   "            select CHEQUE_MEMO_TYPE_CODE,PAYEE_TYPE_CODE from FAS_CHEQUEMEMO_PAYEE_TYPES_MST where SERIALNUMBER=?)a         " + 
                   "            left outer join                                                                                                 " + 
                   "            (                                                                                                               " + 
                   "            select CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_DESC from FAS_CHEQUE_MEMO_TYPES_MST)b                             " + 
                   "            on a.CHEQUE_MEMO_TYPE_CODE=b.CHEQUE_MEMO_TYPE_CODE                                                              " + 
                   "            left outer join                                                                                                 " + 
                   "            (                                                                                                               " + 
                   "            select PAYEE_TYPE_CODE,PAYEE_TYPE_DESC from FAS_PAYEE_TYPES_MST)c                                               " + 
                   "            on a.PAYEE_TYPE_CODE=c.PAYEE_TYPE_CODE                                                                          " + 
                   "            )                                                                                                               ";                                                                                                              
                   ps2 =con.prepareStatement(sql);   
                   ps2.setInt(1,serial_no);
    				rs2=ps2.executeQuery();
    				if(rs2.next()){
    					xml=xml+"<flag>success</flag>";
    					
    					
    					xml=xml+"<cheqmemo_code>"+rs2.getInt("CHEQUE_MEMO_TYPE_CODE")+"</cheqmemo_code>";
                        xml=xml+"<cheqmemo_desc>"+rs2.getString("CHEQUE_MEMO_DESC")+"</cheqmemo_desc>";
                        xml=xml+"<payee_code>"+rs2.getInt("PAYEE_TYPE_CODE")+"</payee_code>";
                        xml=xml+"<payee_desc>"+rs2.getString("PAYEE_TYPE_DESC")+"</payee_desc>";
    				}else{
    					xml=xml+"<flag>nodata</flag>";    
    				}
    				ps2.close();
    				rs2.close();
    			} catch(Exception e){
    				xml=xml+"<flag>"+e.getMessage()+"</flag>";
    				System.out.println(e);
    			}
    		}
                  xml=xml+"</response>";
                  System.out.println("xml is : " + xml);
                  pw.write(xml);
                  pw.flush();
                  pw.close();
        }
}
