package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ChequeBookServ extends HttpServlet 
{
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
           
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null;
             ResultSet rs1=null;
             PreparedStatement ps1=null;
             ResultSet rss1=null,result=null;
             ResultSet rss=null;
             PreparedStatement pss=null;  
             PrintWriter out = response.getWriter();
            
                 try
                   {
                   
                        ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                        String ConnectionString="";

                        String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
                        String strdsn=rb.getString("Config.DSN");
                        String strhostname=rb.getString("Config.HOST_NAME");
                        String strportno=rb.getString("Config.PORT_NUMBER");
                        String strsid=rb.getString("Config.SID");
                        String strdbusername=rb.getString("Config.USER_NAME");
                        String strdbpassword=rb.getString("Config.PASSWORD");

                        //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                        Class.forName(strDriver.trim());
                        con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                   }
                   catch(Exception e)
                   {
                     System.out.println("Exception in connection...."+e);
                   }
            HttpSession session=request.getSession(false);
                try
                    {
                        
                        if(session==null)
                        {
                            System.out.println(request.getContextPath()+"/index.jsp");
                            response.sendRedirect(request.getContextPath()+"/index.jsp");
                        
                        }
                        System.out.println(session);
                            
                    }catch(Exception e)
                    {
                    System.out.println("Redirect Error :"+e);
                    }
            
            
        int acId=0;
        int acOffId=0;
        String strCommand="";
        String ChequeCode="";
        int Acc_UnitCode=0;
        int OffCode=0;
        int BankName_ID=0;
        int Branch_ID=0,count=0;
        long BankAc=0;
        int NoLeaves=0;
        String radCheck_NoOfLeaf="";
        String PhyVerDate="";
        int StartLNO=0;
            String yes="0";
        int EndLNO=0,count_again=0;
        String DateDest="";
        int userId=0;
        int verifyBy=0;
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);
        String xml="";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
       
        
        try {
            System.out.println("................Start in the servlet...........");
            strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);
            ChequeCode=request.getParameter("txtChequeCode");
            System.out.println("assign....."+ChequeCode);
        }
        catch(Exception ae) {
            System.out.println("first exception...."+ae);
        }
            try
            {
               try{
                Acc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                }
               catch(Exception e){System.out.println("exception in ");}
              System.out.println("assign acc id....."+Acc_UnitCode);
            try{ 
                OffCode=Integer.parseInt(request.getParameter("comOffCode"));
               }
              catch(Exception e){System.out.println("exception in ");}
            System.out.println("assign acc for office id...."+OffCode);
            
            
            try{
                BankName_ID=Integer.parseInt(request.getParameter("BankName_ID"));
            }
             catch(Exception e){System.out.println("exception in ");}
            System.out.println("assign bank name...."+BankName_ID);
           
            try{
                Branch_ID=Integer.parseInt(request.getParameter("Branch_ID"));
            }
             catch(Exception e){System.out.println("exception in ");}
            System.out.println("assign bank address....."+Branch_ID);
            
            try{
            BankAc=Long.parseLong(request.getParameter("txtBankAc"));
            }
            catch(Exception e){System.out.println("exception in ");}
            System.out.println("assign bank account no....."+BankAc);
            
            ChequeCode=request.getParameter("txtChequeCode");
            System.out.println("cheque book code...."+ChequeCode);
            
            try{
            NoLeaves=Integer.parseInt(request.getParameter("txtNoLeaves"));
           
            }
            catch(Exception e){System.out.println("exception in ");}
                System.out.println("no of leaves...."+NoLeaves);
            
            radCheck_NoOfLeaf=request.getParameter("radCheck_NoOfLeaf");
            System.out.println("physical verified or not....."+radCheck_NoOfLeaf);
            
            PhyVerDate=request.getParameter("txtPhyVerDate");
            System.out.println("physical verify date...."+PhyVerDate);
            
            try{
            StartLNO=Integer.parseInt(request.getParameter("txtStartLNO"));
//            yes=request.getParameter("txtStartLNO");
//            int hh=Integer.parseInt(yes);
//            System.out.println("yes:::::"+yes);
//            System.out.println("hh:::::"+hh);
         
            }
            catch(Exception e){System.out.println("exception in ");}
            System.out.println("start leaf number..."+StartLNO);
            
            try{
            EndLNO=Integer.parseInt(request.getParameter("txtEndLNO"));
                System.out.println("EndLNO::::::dhana:::"+EndLNO);
            }
            catch(Exception e){System.out.println("exception in ");}
            System.out.println("end leaf number......"+EndLNO);
            
            DateDest=request.getParameter("txtDateDest");
            System.out.println("date of desctruction....."+DateDest);
            
            try{
            verifyBy=Integer.parseInt(request.getParameter("txtverifyBy"));
            }
            catch(Exception e){System.out.println("exception in ");}
            System.out.println("assign user id...."+verifyBy);      
            System.out.println("assign user id...."+userId);
        }
        
        catch(Exception e) {
            System.out.println("Exception in assigning..."+e);
        }
        
            java.sql.Date dateFORDateDest=null;
            java.sql.Date dateFORverified=null;

            //////////////is to format the date of desctruction/////////////
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
             try
             {
               java.util.Date d1=dateFormat1.parse(DateDest);
                 dateFormat1.applyPattern("yyyy-MM-dd");
               DateDest=dateFormat1.format(d1);
                 dateFORDateDest=Date.valueOf(DateDest);
             }catch(Exception e)
            {
             System.out.println("Exception in datedest ");
            }
            
            System.out.println("dateFORDateDest is...."+dateFORDateDest);
            
            
            //////////////////is to format the verification done on/////////////////
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
             try
             {
               java.util.Date d2=dateFormat2.parse(PhyVerDate);
                 dateFormat2.applyPattern("yyyy-MM-dd");
               PhyVerDate=dateFormat2.format(d2);
                 dateFORverified=Date.valueOf(PhyVerDate);
             }catch(Exception e)
            {
                System.out.println("Exception in physic verified date ");
            }
            
            System.out.println("dateFORverified is...."+dateFORverified);
            
        
        if(strCommand.equalsIgnoreCase("Add")) 
        {
            xml="<response><command>Add</command>";
            System.out.println("inside add command");
            
            try {
                ps=con.prepareStatement("insert into COM_MST_CHEQUE_BOOKS_SL (CHEQUE_BOOK_CODE,ACCOUNTING_UNIT_ID,BANK_ID,BRANCH_ID,ACCOUNT_NO,VERIFIED_BY,UPDATED_BY_USER_ID,ACCOUNTING_FOR_OFFICE_ID,NO_OF_LEAVES,PHYSICALLY_VERIFIED,VERIFIED_ON,START_LEAF_NO,END_LEAF_NO,DATE_OF_DESCTRUCTION,UPDATED_DATE,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'L')");
                System.out.println(ps);
                ps.setString(1,ChequeCode);
                ps.setInt(2,Acc_UnitCode);
                ps.setInt(3,BankName_ID);
                ps.setInt(4,Branch_ID);
                ps.setLong(5,BankAc);
                ps.setInt(6,verifyBy);
                ps.setString(7,update_user);
                ps.setInt(8,OffCode);
                ps.setInt(9,NoLeaves);
                ps.setString(10,radCheck_NoOfLeaf);
                ps.setDate(11,dateFORverified);
                ps.setInt(12,StartLNO);
               // ps.setString(12,yes);
                ps.setInt(13,EndLNO);
                ps.setDate(14,dateFORDateDest);
                ps.setTimestamp(15,ts);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
                ps.close();
               System.out.println("here"+xml);
            }
            
            catch(Exception e) {
            
                 System.out.println("catch. in  adding...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
            else if(strCommand.equalsIgnoreCase("checkCode"))
               {
                                int count_test=0;
                                xml="<response><command>checkCode</command>";
                                
                                System.out.println("inside checkCode command");
                                
                                try {
                                String sql="SELECT count(*)as count FROM COM_MST_CHEQUE_BOOKS_SL WHERE ACCOUNTING_UNIT_ID=? AND STATUS   ='L' and ACCOUNTING_FOR_OFFICE_ID=? and CHEQUE_BOOK_CODE='"+ChequeCode+"'";
                               
                                System.out.println("sql::::"+sql);
                                    ps=con.prepareStatement(sql);
                                    ps.setInt(1,Acc_UnitCode);
                                 //   System.out.println("Acc_UnitCode:::"+Acc_UnitCode);
                                    ps.setInt(2,OffCode);
                                  //  System.out.println("OffCode:::"+OffCode);
                                  
                                    
                                   rs=ps.executeQuery();
                                   while(rs.next()) {
                                       xml=xml+"<count>"+rs.getInt("count")+"</count>";
                                      count_test=rs.getInt("count");//duplicates there
                                      System.out.println("count_test:::::"+count_test);
                                   }
                                    xml=xml+"<flag>success</flag>";
                                   if(count_test>0) {
                                       xml=xml+"<flag_test>recordIsThere</flag_test>";
                                   }
                                   else 
                                   {
                                       xml=xml+"<flag_test>ProceedFurther</flag_test>";
                                   }
                                   
                                   ps.close();
                                   System.out.println("here"+xml);
                                }
                                
                                catch(Exception e) {
                                
                                     System.out.println("catch. in  checkCode...."+e);
                                    xml=xml+"<flag>failure</flag>";
                                }
                                xml=xml+"</response>";
                    
                }
        else if(strCommand.equalsIgnoreCase("TestLeaf"))
           {

                            xml="<response><command>TestLeaf</command>";
                            
                            System.out.println("inside TestLeaf command");
                            
                            try {
                            String sql="SELECT count(*)as count FROM COM_MST_CHEQUE_BOOKS_SL WHERE ACCOUNTING_UNIT_ID= " +Acc_UnitCode+
                            		" AND account_no='"+BankAc+"' AND STATUS   ='L' and (START_LEAF_NO between "+StartLNO+" and "+EndLNO+" or END_LEAF_NO " +
                            		 "between "+StartLNO+" and "+EndLNO+")";
                           
                            System.out.println("sql::::"+sql);
                                ps=con.prepareStatement(sql);
//                                ps.setInt(1,Acc_UnitCode);
//                                System.out.println("Acc_UnitCode"+Acc_UnitCode);
//                                ps.setInt(2,StartLNO); System.out.println("StartLNO"+StartLNO);
//                                ps.setInt(3,EndLNO); System.out.println("EndLNO"+EndLNO);
//                                ps.setInt(4,StartLNO);
//                                ps.setInt(5,EndLNO);
                                
                               rs=ps.executeQuery();
                               while(rs.next()) {
                                   xml=xml+"<count>"+rs.getInt("count")+"</count>";
                                  count=rs.getInt("count");//duplicates there
                                  System.out.println("count:::::"+count);
                               }
                                xml=xml+"<flag>success</flag>";
                               if(count>0) {
                                   xml=xml+"<flag_test>recordIsThere</flag_test>";
                               }
                               else {
                                   String sql_one=" SELECT count(*)as count1 FROM COM_MST_CHEQUE_BOOKS_SL WHERE ACCOUNTING_UNIT_ID="+ Acc_UnitCode+" AND STATUS  ='L' AND account_no='"+BankAc+"' and ("+StartLNO+" between START_LEAF_NO and END_LEAF_NO or "+EndLNO+" between START_LEAF_NO and END_LEAF_NO)";
                                 System.out.println("sql_one:::"+sql_one);
                                   ps1=con.prepareStatement(sql_one);
                                   result=ps1.executeQuery();
                                   while(result.next()) 
                                       {
                                           count_again=result.getInt("count1");//duplicates there
                                           System.out.println("count_again:::::"+count_again);
                                       }
                                  if(count_again>0) {
                                      xml=xml+"<flag_test>recordIsThere</flag_test>";
                                  }
                                  else{
                                   xml=xml+"<flag_test>norecord</flag_test>";
                                  }
                               }
                               
                                ps.close();
                               System.out.println("here"+xml);
                            }
                            
                            catch(Exception e) {
                            
                                 System.out.println("catch. in  TestLeaf...."+e);
                                xml=xml+"<flag>failure</flag>";
                            }
                            xml=xml+"</response>";
                
            }
       else if(strCommand.equalsIgnoreCase("Update"))
       {
         
          
          System.out.println("inside update command...");
            xml="<response><command>Update</command>";
            
            
            try {
                ps=con.prepareStatement("update COM_MST_CHEQUE_BOOKS_SL set " +
                "BANK_ID=?,BRANCH_ID=?,ACCOUNT_NO=?,VERIFIED_BY=?,UPDATED_BY_USER_ID=?,NO_OF_LEAVES=?," +
                " PHYSICALLY_VERIFIED=?,VERIFIED_ON=?,START_LEAF_NO=?,END_LEAF_NO=?,DATE_OF_DESCTRUCTION=?,UPDATED_DATE=?  " +
                "where CHEQUE_BOOK_CODE=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNTING_UNIT_ID=?");
                System.out.println("************************");
                ps.setInt(1,BankName_ID);
                ps.setInt(2,Branch_ID);
                ps.setLong(3,BankAc);
                ps.setInt(4,verifyBy);
                ps.setString(5,update_user);
                ps.setInt(6,NoLeaves);
                ps.setString(7,radCheck_NoOfLeaf);
                ps.setDate(8,dateFORverified);
                ps.setInt(9,StartLNO);
                ps.setInt(10,EndLNO);
                ps.setDate(11,dateFORDateDest);
                ps.setTimestamp(12,ts);
                ps.setString(13,ChequeCode);
                ps.setInt(14,OffCode);
                ps.setInt(15,Acc_UnitCode);
               
                
             
                System.out.println("************************");
                
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            
            xml=xml+"</response>";
        }
        else if(strCommand.equalsIgnoreCase("Delete"))
        {
        
            ChequeCode=request.getParameter("txtChequeCode");
            System.out.println("assign....."+ChequeCode);
        
            xml="<response><command>Delete</command>";
            try {
                //ps=con.prepareStatement("delete from COM_MST_CHEQUE_BOOKS_SL where CHEQUE_BOOK_CODE=?  and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNTING_UNIT_ID=? ");
            	ps=con.prepareStatement("update COM_MST_CHEQUE_BOOKS_SL set STATUS='C' where CHEQUE_BOOK_CODE=?  and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNTING_UNIT_ID=? ");
                ps.setString(1,ChequeCode);
                ps.setInt(2,OffCode);
                ps.setInt(3,Acc_UnitCode);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
            System.out.println("xml is:"+xml);
            out.write(xml);
            out.flush();
            out.close();
        
        }
        
        
    }

/*
 *             else if(strCommand.equalsIgnoreCase("bankDetails")) {
            
                Acc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                System.out.println("assign bank accounting unit id....."+Acc_UnitCode);
                 
                int branchID=0;
                int bankID=0;
                String branchName="";
                String addr1="";
                String addr2="";
                String city="";
                int micr_code=0;
               // String bankName="";
                int Offid=0;
                
                xml="<response><command>bankDetails</command>";
                try {
                    ps=con.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                    ps.setInt(1,Acc_UnitCode);
                    rs=ps.executeQuery();
                    while(rs.next()) {
                        Offid=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
                        System.out.println(Offid);
                    }
                    
                }
                catch(Exception qw) {
                    System.out.println("exception in fetching accounting office id for accounting id..."+qw);
                }
                try {
                    ps=con.prepareStatement("select * from FAS_OFFICE_BANK_AC_CURRENT where =?");
                    ps.setInt(1,Offid);
                    rss=ps.executeQuery();
                    while(rss.next()) {
                        branchID=rss.getInt("BRANCH_ID");
                        System.out.println("branch id is...."+branchID);
                        
                        bankID=rss.getInt("BANK_ID");
                        System.out.println("bank id is........"+bankID);
                        
                                                
                        
                        try
                        {
                        pss=con.prepareStatement("select * from FAS_MST_BANK_BRANCHES where BRANCH_ID=? and BANK_ID=?");
                        pss.setInt(1,branchID);
                        pss.setInt(2,bankID);
                        rs1=pss.executeQuery();
                        while(rs1.next()) {
                            branchName=rs1.getString("BRANCH_NAME");
                            addr1=rs1.getString("BRANCH_ADDRESS1");
                            addr2=rs1.getString("BRANCH_ADDRESS2");
                            city=rs1.getString("CITY_TOWN_NAME");
                            
                            xml=xml+"<flag>success</flag><bankID>"+bankID+"</bankID><branchID>"+branchID+"</branchID><branchName>"+branchName+"</branchName><addr1>"+addr1+"</addr1><addr2>"+addr2+"</addr2><city>"+city+"</city>";
                        }
                        }
                        catch(Exception que) {
                            System.out.println("Exception in fetching the bank branch details......"+que);
                        }
                    }
                    
                }
                catch(Exception e) {
                    System.out.println("catch...."+e);
                    xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
            }
        
        
            else if(strCommand.equalsIgnoreCase("bankAC")) {
                int AcOffId=0;
                Acc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                System.out.println("assign acc for office id...."+Acc_UnitCode);
                int bankACNo=0;
            try {
                ps=con.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                ps.setInt(1,Acc_UnitCode);
                rs=ps.executeQuery();
                while(rs.next()) {
                    AcOffId=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
                }
                rs.close();
                ps.close();
            }
            catch(Exception re) {
                System.out.println("exception in fetching accounting office id for accounting unit id...."+re);
            }
                try {
                    ps=con.prepareStatement("select * from FAS_OFFICE_BANK_AC_CURRENT where =?");
                    ps.setInt(1,AcOffId);
                    rs=ps.executeQuery();
                    while(rs.next()) {
                        
                        bankACNo=rs.getInt("BANK_AC_NO");
                        System.out.println("bank account number is....."+bankACNo);
                        xml=xml+"<option><bankACNo>"+bankACNo+"</bankACNo></option>";
                       
                    }
                    xml="<select>"+xml+"</select>";
                    rs.close();
                    ps.close();
                }
                catch(Exception e) {
                    System.out.println("catch...."+e);
                    xml=xml+"<response>failure</response>";
                }
               
            }
            
            else if(strCommand.equalsIgnoreCase("ForList")) {
                int AcOffId=0;
                Acc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                System.out.println("assign acc for office id...."+Acc_UnitCode);
                int bankACNo=0;
            
                try {
                    ps=con.prepareStatement("select * from FAS_OFFICE_BANK_AC_CURRENT where =?");
                    ps.setInt(1,AcOffId);
                    rs=ps.executeQuery();
                    while(rs.next()) {
                        
                        bankACNo=rs.getInt("BANK_AC_NO");
                        System.out.println("bank account number is....."+bankACNo);
                        xml=xml+"<option><bankACNo>"+bankACNo+"</bankACNo></option>";
                       
                    }
                    xml="<select>"+xml+"</select>";
                    rs.close();
                    ps.close();
                }
                catch(Exception e) {
                    System.out.println("catch...."+e);
                    xml=xml+"<response>failure</response>";
                }
               
            }
 */