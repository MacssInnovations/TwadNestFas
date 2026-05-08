package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;
import Servlets.Security.classes.UserProfile;

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


public class ChequeBookListServ extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
   
       
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
             Connection con=null;
             ResultSet rss=null,rs=null,rs3=null;
             PreparedStatement pss=null,ps=null,ps3=null;    
             response.setContentType(CONTENT_TYPE);
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

                  // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                   ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                   Class.forName(strDriver.trim());
                   con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
              {
                System.out.println("Exception in connection...."+e);
              }
            
                 try
                    {
                        HttpSession session=request.getSession(false);
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
            
            HttpSession session=request.getSession(false);
                  UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                  
                    System.out.println("user id::"+empProfile.getEmployeeId());
                   int empid=empProfile.getEmployeeId();
                    //int empid=9315;
                System.out.println("empid"+empid);
            

          int bankId=0;          
          int branchId=0; 
          long bankAccNO=0;
          int verifyBy=0;
          int userId=0;
          int acOffId=0;
          int NoOfLeaf=0;
          String PhyVerify="";
          int StartLeaf=0;
          int EndLeaf=0;
          String verifyON="";
          String DestDate="";
          String oprmodid="";
          String bankName="";
          String branchName="";
          String addr1="";
          String addr2="";
          String city="",final_addr="";
          int micr_code=0;
         int cmbAcc_UnitCode=0,cmbOffice_code=0;
         String Status="";
       String strchequeBookcode="";
        String strCommand="";
        String xml="";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
       
          
        
            try {
            System.out.println("..........................servlet chequebooklist started.............");
                strCommand=request.getParameter("command");
                System.out.println("assign....."+strCommand);
                strchequeBookcode=request.getParameter("chequeBookcode");
                System.out.println("assign....."+strchequeBookcode+"u");
            
             
                  try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                  catch(NumberFormatException e){System.out.println("exception"+e );}
                  System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                  
                  try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                  catch(NumberFormatException e){System.out.println("exception"+e );}
                  System.out.println("cmbOffice_code "+cmbOffice_code);
            }
            catch(Exception ae) {
                System.out.println("first exception...."+ae);
            }
                
        
        if(strCommand.equalsIgnoreCase("fetch")) 
        {
        
        String empNAME="";
        int verifiedID=0;
            xml="<response><command>fetch</command>";
          
            System.out.println("inside fetch command");
            try {
                pss=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID,BANK_ID ,BRANCH_ID ,ACCOUNT_NO ,CHEQUE_BOOK_CODE ,NO_OF_LEAVES" +
                ",PHYSICALLY_VERIFIED ,VERIFIED_BY ,to_char(VERIFIED_ON,'DD/MM/YYYY') as verifyON ,START_LEAF_NO ,END_LEAF_NO , to_char(DATE_OF_DESCTRUCTION,'DD/MM/YYYY') as dateOFdest,STATUS " +
                " from COM_MST_CHEQUE_BOOKS_SL where CHEQUE_BOOK_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
                pss.setString(1,strchequeBookcode);
                pss.setInt(2,cmbAcc_UnitCode);
                pss.setInt(3,cmbOffice_code);
                rss=pss.executeQuery();
                System.out.println("here");
                if(rss.next()) 
                {
                    bankId=rss.getInt("BANK_ID");
                    System.out.println("Asset type code is*****************"+bankId);
                    Status=rss.getString("STATUS");
                    //*****************  get user name
                     verifiedID=rss.getInt("VERIFIED_BY");
                   ps=con.prepareStatement("select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?");
                   ps.setInt(1,rss.getInt("VERIFIED_BY"));
                   rs=ps.executeQuery();
                 
                   if(rs.next())
                       empNAME=rs.getString("EMPLOYEE_NAME");
                    
                    ps=con.prepareStatement("select BANK_NAME from FAS_MST_BANKS where BANK_ID="+rss.getInt("BANK_ID"));
                    rs=ps.executeQuery();
                    if(rs.next())
                    bankName=rs.getString("BANK_NAME");
                    
                    branchId=rss.getInt("BRANCH_ID");
                    System.out.println("Asset type code is*****************"+branchId);
                    ps=con.prepareStatement("select BRANCH_NAME,BRANCH_ADDRESS1,BRANCH_ADDRESS2,CITY_TOWN_NAME,MICR_CODE from  FAS_MST_BANK_BRANCHES where BANK_ID="+rss.getInt("BANK_ID")+"and BRANCH_ID="+rss.getInt("BRANCH_ID"));
                    rs=ps.executeQuery();
                    if(rs.next())
                    {
                    micr_code=rs.getInt("MICR_CODE");
                     
                    if(rs.getString("BRANCH_ADDRESS1")==null)
                    addr1="";
                    else
                    addr1=rs.getString("BRANCH_ADDRESS1");
                    
                    if(rs.getString("BRANCH_ADDRESS2")==null)
                    addr2="";
                    else
                    addr2=rs.getString("BRANCH_ADDRESS2");
                    
                    if(rs.getString("CITY_TOWN_NAME")==null)
                    city="";
                    else
                    city=rs.getString("CITY_TOWN_NAME");
                    
                    final_addr=addr1+addr2+city;
                    if(final_addr.equals(""))
                    {
                    	final_addr="-";
                    }
                    bankName=bankName+" - "+rs.getString("BRANCH_NAME");
                    }
                    
                    ps3=con.prepareStatement("select distinct a.ac_operational_mode_id as oprmodid,b.account_type from FAS_OFFICE_BANK_AC_CURRENT a,"
                                                            +" FAS_MST_BANK_AC_TYPES b where a.bank_ac_type_id=b.account_type_id and a.bank_id ="+rss.getInt("BANK_ID")+ " and " 
                                                           +" accounting_unit_id="+cmbAcc_UnitCode+" and a.bank_ac_no="+rss.getLong("ACCOUNT_NO")); 
                    rs3=ps3.executeQuery();
                    System.out.println(("select distinct a.ac_operational_mode_id as oprmodid,b.account_type from FAS_OFFICE_BANK_AC_CURRENT a,"
                                                            +" FAS_MST_BANK_AC_TYPES b where a.bank_ac_type_id=b.account_type_id and a.bank_id ="+rss.getInt("BANK_ID")+ " and " 
                                                           +" accounting_unit_id="+cmbAcc_UnitCode+" and a.bank_ac_no="+rss.getLong("ACCOUNT_NO"))); 
                        if(rs3.next())
                                      {
                                      //rs3.getString("account_type").trim()+"</td>");
                                      oprmodid=rs3.getString("oprmodid");
                                          System.out.println("operational id"+oprmodid);
                                      }
                                     // rs3.close();
                                    //  ps3.close();
                   // oprmodid=rs3.getString("oprmodid");
                   
                   // System.out.println("operational id"+oprmodid);
                    bankAccNO=rss.getLong("ACCOUNT_NO");
                    System.out.println("ACCOUNT_NO is*****************"+bankAccNO);
                    verifyBy=rss.getInt("VERIFIED_BY");
                    System.out.println("AVERIFIED_BY*****************"+verifyBy);
                    acOffId=rss.getInt("ACCOUNTING_FOR_OFFICE_ID");
                    System.out.println("ACCOUNTING_FOR_OFFICE_ID*****************"+acOffId);
                    NoOfLeaf=rss.getInt("NO_OF_LEAVES");
                    System.out.println("NO_OF_LEAVES*****************"+NoOfLeaf);
                    PhyVerify=rss.getString("PHYSICALLY_VERIFIED");
                    System.out.println("PHYSICALLY_VERIFIED*****************"+PhyVerify);
                    StartLeaf=rss.getInt("START_LEAF_NO");
                    System.out.println("START_LEAF_NO*****************"+StartLeaf);
                    EndLeaf=rss.getInt("END_LEAF_NO");
                    System.out.println("END_LEAF_NO*****************"+EndLeaf);
                    
                    verifyON=rss.getString("verifyON");
                    DestDate=rss.getString("dateOFdest");
                    rs3.close();
                    ps3.close();
                    rss.close();
                    pss.close();
                    xml=xml+"<flag>success</flag><chequeBookcode>"+strchequeBookcode+"</chequeBookcode><bankId>"+bankId+"</bankId><bID>"+branchId+"</bID><branchId>"+oprmodid+
                    "</branchId><bankAccNO>"+bankAccNO+"</bankAccNO><micr_code>"+micr_code+"</micr_code><bankName>"+bankName+
                    "</bankName><final_addr>"+final_addr+"</final_addr><NoOfLeaf>"+NoOfLeaf+"</NoOfLeaf><PhyVerify>"+PhyVerify+
                    "</PhyVerify><verifiedID>"+verifiedID+"</verifiedID><verifiedbyname>"+empNAME+"</verifiedbyname>" +
                    "<StartLeaf>"+StartLeaf+"</StartLeaf><EndLeaf>"+EndLeaf+"</EndLeaf><verifyON>"+verifyON+
                    "</verifyON><DestDate>"+DestDate+"</DestDate><status>"+Status+"</status>";
                }
                else
                {
                    xml=xml+"<flag>failure</flag>";
                    System.out.println("else part");
                }
            }
            
            catch(Exception que) {
                xml=xml+"<flag>failure</flag>";
                System.out.println("exception in fetching aset details"+que);
            }
            
           
            xml=xml+"</response>";
        }
            System.out.println("xml is:"+xml);
            out.write(xml);
            out.flush();
            out.close();
        
        }
        
        
    }

