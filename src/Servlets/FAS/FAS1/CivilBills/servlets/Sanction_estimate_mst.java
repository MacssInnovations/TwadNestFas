package Servlets.FAS.FAS1.CivilBills.servlets;

//import Servlets.Security.classes.UserProfile;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;
import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class Sanction_estimate_mst extends HttpServlet
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
                System.out.println("Welcome to Sanction_estimate Servlet");
		String cmnd="";
		String xml="";
                String user_id;
                user_id = "";
                //String emp_name="";
                int count=0;
                String update_user="";
                HttpSession session=null;
                Timestamp ts=null;
                int acc_unit_id=0;int acc_off_id=0;String fin_year="";
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
                    
                   
                    if(cmnd.equalsIgnoreCase("loadAssetClass")) 
                    {
                        xml=xml+"<command>loadAssetClass</command>";
                        try
                            {             
                                String sqlload="select ASSET_CLASS_CODE,ASSET_CLASS_DESC from COM_MST_ASSETS_CLASS order by asset_class_code";
                                ps2 = con.prepareStatement(sqlload);
                                rs2=ps2.executeQuery();
                                
                                while(rs2.next())
                                {
                                    
                                    xml=xml+"<option><asset_class_code>"+rs2.getString("ASSET_CLASS_CODE")+"</asset_class_code>";
                                    xml=xml+"<asset_class_desc>"+rs2.getString("ASSET_CLASS_DESC")+"</asset_class_desc></option>";
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
                    else if(cmnd.equalsIgnoreCase("loadAssetmajClass")) 
                    {
                        xml=xml+"<command>loadAssetmajClass</command>";
                        try
                            {             
                                int assetcode=Integer.parseInt(request.getParameter("assetcode1"));
                                System.out.println("Asset code ......"+assetcode);
                                String sqlload="select a.asset_class_code,b.asset_class_desc from" + 
                                "   (select asset_class_code from com_mst_assets_sl where asset_code=?)a" + 
                                "   left outer join" + 
                                "   (select asset_class_code,asset_class_desc from com_mst_assets_class)b" + 
                                "   on a.asset_class_code=b.asset_class_code";
                                System.out.println("query ****"+sqlload);
                                ps2 = con.prepareStatement(sqlload);
                                ps2.setInt(1,assetcode);
                                rs2=ps2.executeQuery();
                                
                                if(rs2.next())
                                {
                                    xml=xml+"<asset_class_code>"+rs2.getString("ASSET_CLASS_CODE")+"</asset_class_code>";
                                    xml=xml+"<asset_class_desc>"+rs2.getString("ASSET_CLASS_DESC")+"</asset_class_desc>";
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
                    else if(cmnd.equalsIgnoreCase("loadAssetCode")) 
                    {
                        xml=xml+"<command>loadAssetCode</command>";
                        try
                        {             
                                    acc_unit_id=Integer.parseInt(request.getParameter("acc_unit_id"));
                                    System.out.println("acc......."+acc_unit_id);
                                    acc_off_id=Integer.parseInt(request.getParameter("acc_office_id"));
                                    System.out.println("office........"+acc_off_id);
                                    fin_year=request.getParameter("fin_year");
                                    System.out.println("fin_year........."+fin_year);
                                    String sqlload="select ASSET_CODE,asset_description from COM_MST_ASSETS_SL where accounting_unit_id=? and accounting_for_office_id=?" + 
                                    "and financial_year=?";
                                    ps2 = con.prepareStatement(sqlload);
                                    ps2.setInt(1,acc_unit_id);
                                    ps2.setInt(2,acc_off_id);
                                    ps2.setString(3,fin_year);
                                    rs2=ps2.executeQuery();
                                    while(rs2.next())
                                    {
                                        xml=xml+"<option><asset_code>"+rs2.getString("ASSET_CODE")+"</asset_code>";
                                        xml=xml+"<asset_code_desc>"+rs2.getString("asset_description")+"</asset_code_desc></option>";
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
        xml=xml+"</response>";
        pw.write(xml);
        System.out.println("xml is : " + xml);
        pw.flush();
        pw.close();
    }//DOGET close
public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException          
{
              String CONTENT_TYPE="text/html";
              response.setContentType(CONTENT_TYPE);
              //response.setHeader("Cache-Control","no-cache");
              String cmnd="";
              String asset_maj_class="";int asset_code=0;int Sanc_Esti_PrepBy=0;int sanc_esti_appBy=0;int acc_head_code=0;
              String fin_year="";String sanc_esti_prep="";String gen_remarks="";
              String asset_desc="";
              double sanc_esti_amt=0.00;
              int sanc_esti_no=0;
              int acc_unit_id=0;int acc_office_id=0;
              int Total_TRN_Rec=0;
              double tot_sancamt=0;
              String det_particulars="";String gen_remarks1=""; 
              PrintWriter pw=response.getWriter();
              System.out.println("Welcome to dopost");
              HttpSession session=request.getSession(false);
              String update_user=(String)session.getAttribute("UserId");
              long l=System.currentTimeMillis();
              Timestamp ts=new Timestamp(l);
              System.out.println("Session :"+session);
              /*********** connection establishment****************/
              Connection con=null;
              ResultSet rs=null;
              PreparedStatement ps=null;
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
                                           
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
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
         System.out.println("Updaated_by_userid  ::::"+update_user);
         l=System.currentTimeMillis();
         ts=new Timestamp(l);  
                    session=request.getSession(false);
                  try
                     {
                            cmnd =  request.getParameter("Command");     
                            System.out.println("Command passed via the button pressed : " + cmnd);
                     }
                  catch(Exception e3)
                     {
                             e3.printStackTrace();
                     }
/*///////////////////////////////Getting the values from the JSP Page//////////////////////////////////////////////////*/
       if(cmnd.equalsIgnoreCase("Add")) 
      {
             int errcode=0;
             try{acc_unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}catch(Exception e){System.out.println("Exception arised"+e);}
             
             try{acc_office_id=Integer.parseInt(request.getParameter("cmbOffice_code"));}catch(Exception e){System.out.println("Exception arised"+e);}
             
             try{fin_year=request.getParameter("cmbSanction_Estimate_FY");}catch(Exception e){System.out.println("Exception arised"+e);}
             
             try{sanc_esti_prep=request.getParameter("txtSanction_Estimate_PreparedOn");}catch(Exception e){System.out.println("Exception arised"+e);}
             
             try{tot_sancamt=Double.parseDouble(request.getParameter("txtTotSanction_Estimate_Amount"));}catch(Exception e){System.out.println("Exception arised"+e);}
             
             try{acc_head_code=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));}catch(Exception e){System.out.println("Exception arised"+e);}
             
             try{Sanc_Esti_PrepBy=Integer.parseInt(request.getParameter("txtSanction_Estimate_Empcode1"));}catch(Exception e){System.out.println("Exception arised"+e);}
             
             try{sanc_esti_appBy=Integer.parseInt(request.getParameter("txtSanction_Estimate_Empcode2"));}catch(Exception e){System.out.println("Exception arised"+e);}
             
             try{gen_remarks=request.getParameter("txtRemarks");}catch(Exception e){System.out.println("Exception arised"+e);}
                
                if(gen_remarks!=null)
                {
                    gen_remarks1=gen_remarks;
                }
                else
                    gen_remarks1="";
                
                 System.out.println("Accounting unit id :"+acc_unit_id);
                 System.out.println("Accounting unit for office id:"+acc_office_id);
                 System.out.println("Financial Year :"+fin_year);
                 System.out.println("Sanction Estimate Prepared on Date:"+sanc_esti_prep);
                 System.out.println("Total Sanction amount :"+tot_sancamt);
                 System.out.println("account Head Code :"+acc_head_code);
                 System.out.println("Samction Estimate Prepared By :"+Sanc_Esti_PrepBy);
                 System.out.println("Sanction Estimate approved By:"+sanc_esti_appBy);
                 System.out.println("General Remarks :"+gen_remarks);
                 
                
                    /**********************************calculating Max value of sanction proceeding Number************************************/
                    try
                    {
                            String sqlsel="select decode(max(SANCTION_ESTIMATE_NO),null,0,max(SANCTION_ESTIMATE_NO))as SANCTION_ESTIMATE_NO from FAS_SANCTION_ESTIMATE_MST";
                            ps=con.prepareStatement(sqlsel);
                            rs=ps.executeQuery();
                            System.out.println(sqlsel);
                            if(rs.next())
                            {
                                     sanc_esti_no=rs.getInt("SANCTION_ESTIMATE_NO");
                            }
                            sanc_esti_no=sanc_esti_no+1;
                            System.out.println("Maximum value of Sanction Estimate Number is :"+sanc_esti_no);
                            ps.close();
                            rs.close();
                    }
                    catch(Exception ee) 
                    {
                         System.out.println("Exception in getting maximum value of sanction estimate number :"+ee);    
                    }
                            
                try
                {
                        con.clearWarnings();
                        con.setAutoCommit(false);
                        String sqlins="insert into FAS_SANCTION_ESTIMATE_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,               "  +
                                 "FINANCIAL_YEAR,SANCTION_ESTIMATE_NO,SANCTION_ESTIMATE_DATE,ACCOUNT_HEAD_CODE,TOTAL_ESTIMATE_AMOUNT,  "  +
                                 "SANCTION_ESTIMATE_PREPARED_BY,SANCTION_ESTIMATE_APPROVED_BY,REMARKS,UPDATED_BY_USERID,UPDATED_DATE)   "  +
                                 "values (?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?)";
                                 
                  ps=con.prepareStatement(sqlins);
                  ps.setInt(1,acc_unit_id);
                  ps.setInt(2,acc_office_id);
                  ps.setString(3,fin_year);
                  ps.setInt(4,sanc_esti_no);
                  ps.setString(5,sanc_esti_prep);
                  ps.setInt(6,acc_head_code);
                  ps.setDouble(7,tot_sancamt);
                  ps.setInt(8,Sanc_Esti_PrepBy);
                  ps.setInt(9,sanc_esti_appBy);
                  ps.setString(10,gen_remarks1);
                  ps.setString(11,update_user);
                  ps.setTimestamp(12,ts);          
                   errcode=ps.executeUpdate();
                   System.out.println("Error code :"+errcode);
                     ps.close();
                           if(errcode==0)
                           {         
                                     System.out.println("redirect");
                                     sendMessage(response,"The insertion into the Sanction Estimate master table Failed ","ok");                                 
                           }
                           else
                           {
                                      System.out.println("The records inserted into the Sanction Estimate master table scuccessfully");
                                      int SL_NO=1;
                                      String No_TRN_Rec[]=request.getParameterValues("H_AssetMajClassDesc");
                                      Total_TRN_Rec=No_TRN_Rec.length;//Integer.parseInt(No_TRN_Rec);
                                      System.out.println(" Total_TRN_Records :"+Total_TRN_Rec);
                              
                             String Grid_H_AssetMajClassDesc[]=request.getParameterValues("H_AssetMajClassDesc");
                             String Grid_H_asset_code[]=request.getParameterValues("H_asset_code");
                             String Grid_H_Asset_Description[]=request.getParameterValues("H_Asset_Description");
                             String Grid_H_Sanction_Estimate_Amount[]=request.getParameterValues("H_Sanction_Estimate_Amount");
                             String Grid_H_Particulars[]=request.getParameterValues("H_Particulars");
                                                       
                             String sql="insert into FAS_SANCTION_ESTIMATE_TRN(ACCOUNTING_UNIT_ID,         "   +
                                         "  ACCOUNTING_UNIT_OFFICE_ID,SANCTION_ESTIMATE_NO,ASSET_CODE,   "   +
                                         "  ASSET_CLASS_CODE,ESTIMATE_AMOUNT,PARTICULARS,                "   +
                                         "  UPDATED_BY_USERID,UPDATED_DATE,SERIAL_NO)                    "   +
                                         "  values(?,?,?,?,?,?,?,?,?,?)                                   "   ;
                         ps=con.prepareStatement(sql);
                         for(int k=0;k<Total_TRN_Rec;k++) 
                         {
                                 try{asset_maj_class=Grid_H_AssetMajClassDesc[k];}catch(Exception e1){System.out.println("exception in trans "+e1);}
                                 
                                 try{asset_code=Integer.parseInt(Grid_H_asset_code[k]);}catch(Exception e8){System.out.println("exception in trans "+e8);}                                   
                                 
                                 try{asset_desc=Grid_H_Asset_Description[k];}catch(Exception e2){System.out.println("exception in trans "+e2);}
                                 
                                 try{sanc_esti_amt=Double.parseDouble(Grid_H_Sanction_Estimate_Amount[k]);}catch(Exception e3){System.out.println("exception in trans "+e3);}
                                 
                                 try{det_particulars=Grid_H_Particulars[k];}catch(Exception e4){System.out.println("exception in trans "+e4);}
                                                                  
                                 System.out.println("Asset Major Class          :"+asset_maj_class);
                                 System.out.println("Asset Code                 :"+asset_code);
                                 System.out.println("Asset Code description     :"+asset_desc);
                                 System.out.println("Sanction Estimate amount   :"+sanc_esti_amt);                                   
                                 System.out.println("Particulars                :"+det_particulars);
                                 System.out.println("serial no                  :"+SL_NO);
                                 System.out.println("Sanction Estimate No       :"+sanc_esti_no);
                                 
                             ps.setInt(1,acc_unit_id);
                             ps.setInt(2,acc_office_id);
                             ps.setInt(3,sanc_esti_no);
                             ps.setInt(4,asset_code);
                             ps.setString(5,asset_maj_class);
                             ps.setDouble(6,sanc_esti_amt);
                             ps.setString(7,det_particulars);
                             ps.setString(8,update_user);
                             ps.setTimestamp(9,ts);
                             ps.setInt(10,SL_NO);
                             SL_NO++;
                             ps.executeUpdate(); 
                         }
                         ps.close();
                         System.out.println("b4 commit");
                         con.commit();
                         sendMessage(response,"The General Sanction Estimate Number "+sanc_esti_no+" is created Successfully \n ","ok");
                         //sendMessage(response,"The Records are inserted into both table Successfully ","ok");
                   }
            }
            catch(Exception e) 
                   {
                       try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
                       sendMessage(response,"The Insertion of records into the table Failed ","ok");
                       System.out.println("Exception occur due to "+e);
                   }
                   finally
                   {
                       System.out.println("done");
                       try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Exception arised :"+sqle);}
                   }
              }
              
                        pw.flush();
                        pw.close();
   }//DoPost method close

private void sendMessage(HttpServletResponse response,String msg,String bType)
{
     try
     {
          String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
          response.sendRedirect(url);
     }
     catch(IOException e)
     {
             System.out.println("Exception arised :"+e);
     }
}
}

