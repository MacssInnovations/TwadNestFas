package Servlets.FAS.FAS1.AuthorizationSystem.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
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


public class Authorization_for_BRS extends HttpServlet 
{
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        
        	
      /**
       * Session Checking 
       */
    
       HttpSession session=request.getSession(false);
       try
        {
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
    
        String thisServer= getServletConfig().getServletContext().getServerInfo();
      
        
       /**
        *  Variables Declaration 
        */
               
         Connection con=null;         
         CallableStatement cs1=null;         
         String xml="";
         String strCommand="";
         
         
         
       /**
        * Database Connection
        */
        
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
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
             catch(Exception e)
                 {
                    System.out.println("Exception in opening connection :"+e);                    
                 }
                 
                 
       /**
        * Get Command Parameter 
        */
                 
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        /**
         * If Command is ADD 
         */
       
        if(strCommand.equalsIgnoreCase("Add")) 
		{
		        
		            /** Set Content Type */ 
		            String CONTENT_TYPE = "text/html; charset=windows-1252";
		            response.setContentType(CONTENT_TYPE);
		            
		            xml="<response><command>Add</command>";
		            
		            /** Variables Declaration */
		            Calendar c;
		            int cmbAcc_UnitCode=0;
		            int cmbOffice_code=0;
		            int txtCB_Year=0;
		            int txtCB_Month=0,ccdd_no=0;
		            String cmbBankAccNo="";
		            Date txtCrea_date=null;
		            String TransType="";
		            String update_user=(String)session.getAttribute("UserId");
		            long l=System.currentTimeMillis();
		            Timestamp ts=new Timestamp(l);
		            String sd[]=null;
		            java.util.Date d=null;
		            
		            /** Get Accounting Unit Id */
		            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
		            
		            /** Get Accounting Office ID */
		            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
		            catch(NumberFormatException e){System.out.println("exception"+e );}
		            System.out.println("cmbOffice_code "+cmbOffice_code);
		          
		            /** Get Cashbook Year */           
		            try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtCB_Year "+txtCB_Year);
		            
		            /** Get Cashbook Month */           
		            try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("txtCB_Month "+txtCB_Month);
		            
		            /** Get Transaction Type */           
		            try{TransType=request.getParameter("TransType");}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("TransType "+TransType);
		            
		           
                            /** Get Bank Account Number */           
		            try{cmbBankAccNo=request.getParameter("cmbBankAccNo");}
		            catch(Exception e){System.out.println("exception"+e );}
		            System.out.println("cmbBankAccNo "+cmbBankAccNo);
		           
                          
		            
		            /** Get Creation Date */
		            try
		            {
			            sd=request.getParameter("txtCrea_date").split("/");
			            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
			            d=c.getTime();
			            txtCrea_date=new Date(d.getTime());
			            System.out.println("txtCrea_date "+txtCrea_date);
		            }catch(Exception e)
                            {
                              System.out.println("exception"+e );
                            }
		            System.out.println("txtCrea_date "+txtCrea_date);
		            
                            
                            
		            try
				      {
                                                con.clearWarnings();
                                            //    con.setAutoCommit(false);
                                                
                                                String txtReferNO_edit="";
                                                String txtRemak_edit="";
                                                String cmbSubSystemType="";
                                                String txtRefdate="";       
                                                Date txtReferDate_edit=null; 
                                                String radAuth_MC="";                                                        
                                                int txtAuth_By=0;
                                                
                                                String Str_slno=null;
                                                int sl_no=0;
                                                
                                                int docNo=0;
                                                String docType="";
                                                
                                                int txtTotTrans=0;
                                                String txtSLNO="";
                                                
                                               
				                
                                                
					          /** Get Employee ID */  
				                  UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
				                  txtAuth_By=empProfile.getEmployeeId();
				                
                                                
				                  /** Get System Type */
				                 // cmbSubSystemType="BRS";
				                  
				                     
				                  /** Get Reference Number */
				                  try
				                  {
				                    txtReferNO_edit=request.getParameter("txtReferNO_edit");				                    
				                    System.out.println("txtReferNO_edit  "+txtReferNO_edit);
				                  }
				                  catch(Exception e)
				                  {
				                	  System.out.println("Error Getting Reference No-->"+e);
				                  }
				                  
				                  /** Get Remarks */
				                  try
				                  {
				                  	txtRemak_edit=request.getParameter("txtRemak_edit");
				                  	System.out.println("txtRemak_edit  "+txtRemak_edit);
						  }
				                  catch(Exception e)
				                  {
				                	  System.out.println("Error Getting Remarks -->"+e);
				                  }
				                  
				                  /** Get Ref Date */
				                  try
				                  {
				                    txtRefdate=request.getParameter("txtReferDate_edit");
							      }
				                  catch(Exception e)
				                  {
				                	  System.out.println("Error Getting Reference Date-->"+e);
				                  }
				                  
				                  
				                  if(!txtRefdate.equalsIgnoreCase(""))
				                  {
					                  sd=request.getParameter("txtReferDate_edit").split("/");
					                  c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
					                  d=c.getTime();
					                  txtReferDate_edit=new Date(d.getTime());
				                  }
				                
				                 /** Autorization Type */ 
				                 radAuth_MC="M";
				              //   System.out.println("radAuth_MC.. "+radAuth_MC);
				                 
				                 /**Get Total Number of Modified Records */
				                 try{txtTotTrans=Integer.parseInt(request.getParameter("txtTotTrans"));}
				                 catch(Exception e){System.out.println("exception"+e );}
					         System.out.println("txtTotTrans"+txtTotTrans);
                               /*        int check_bok_val=0;          
					         String check_bok = request.getParameter("sel");
                             if (check_bok!= null)
                              {
                                 // sl_no = docNo;
                            	 check_bok_val = Integer.parseInt(check_bok);

                                  System.out.println("check_bok_val -->"+check_bok_val);
                              }  */
                             
                             
                                		        
				 for ( int i=0;i<txtTotTrans;i++)
				 {	   
                                              System.out.println("Row  ==============================>"+i);  
                                              
                                              int Checked_value=-1;
                                              
					      /** Get Doc Number */
					      try
					      {
					          docNo = Integer.parseInt(request.getParameter("docNo"+i));
					          System.out.println("Doc Number -->"+docNo);                                                    
					      } 
					      catch(Exception e)
					      {
					          System.out.println("Error Getting Doc Number -->"+e);
					      }
					      
					      System.out.println("docNo:::"+docNo);
					      
					      
					      /** Get Doc Type */
					      try
					      {
					          docType = request.getParameter("docType"+i);
					          System.out.println("Doc Type -->"+docType);     
					          
					          
					      } 
					      catch(Exception e)
					      {
					          System.out.println("Error Getting Doc Type -->"+e);
					      }
					      String BrsDocT="";
					      //cheque no
				            try{ccdd_no=Integer.parseInt(request.getParameter("ccdd_no"+i));}
				            catch(Exception e){System.out.println("exception"+e );}
				            System.out.println("ccdd_no "+ccdd_no);
                                            
                                              //Lakshmi 3Dec13
                                              
                                               /** Get Sl No */
                                               try
                                               {
                                                      if ( TransType.equalsIgnoreCase("T") )
                                                      {
                                                         /** Get System Type */
                                                          cmbSubSystemType="BRST";
                                                           
                                                         // Str_slno = request.getParameter("r_w_no"+i);                                                     
                                                         // sl_no = docNo;
                                                          
                                                          BrsDocT="T";
                                                          Str_slno = request.getParameter("sno"+i);
                                                          if (Str_slno!= null)
                                                           {
                                                              // sl_no = docNo;
                                                               sl_no = Integer.parseInt(Str_slno);

                                                               System.out.println("Seq Number aft-->"+sl_no);
                                                           }
                                                         
                                                      }
                                                      else if ( TransType.equalsIgnoreCase("NT") )
                                                      {
                                                          /** Get System Type */
                                                          cmbSubSystemType="BRSNT";
                                                          BrsDocT="NT";
                                                         Str_slno = request.getParameter("sno"+i);
                                                         if (Str_slno!= null)
                                                          {
                                                             // sl_no = docNo;
                                                              sl_no = Integer.parseInt(Str_slno);

                                                              System.out.println("Seq Number aft-->"+sl_no);
                                                          }
                                                          
                                                      }                                                          
                                                      System.out.println("Seq Number bfr-->"+Str_slno);
                                                  
                                                }
				                catch(Exception e)
				                {
				                   System.out.println("Error Getting Seq Number -->"+e);
				                }
                                                
                                                
                                                
                                                
                                              
				                /** Get selected Check box values */                                                
                                                try
                                                {
                                                     Checked_value = Integer.parseInt(request.getParameter("sel"+i));
                                                     System.out.println("Selected Check Box -->"+Checked_value);                                                   
                                                } 
                                                catch(Exception e)
                                                {
                                                  System.out.println("Error Converting Seq Number -->"+e);
                                                }                                                                                                                                                                                                                       
				                
  				             //   cs1=con.prepareCall("{call FAS_CROSS_REFERENCE_BRS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                                
				                int errcode=0;
				                if (Checked_value == i) 
				                { 
                                  System.out.println("inside execute query");
                                  cs1=con.prepareCall("call FAS_BRS_CROSS_REFERENCE(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?,?::numeric,?,?::numeric,?,?,?,?::numeric,?,?::numeric)") ;
   				               
    				            cs1.setInt(1,cmbAcc_UnitCode);
    				            cs1.setInt(2,txtCB_Year);
  				                cs1.setInt(3,txtCB_Month);
  				                cs1.setInt(4,docNo);  // sl_no
                                cs1.setInt(5,cmbOffice_code);
  				                cs1.setDate(6,txtCrea_date);
  				                cs1.setString(7,cmbSubSystemType);
  				                cs1.setString(8,cmbBankAccNo); // Instead of Storing Ref Number, Bank A/C No is stored
  				                cs1.setDate(9,txtReferDate_edit);
  				                cs1.setString(10,txtRemak_edit);
  				                cs1.setInt(11,txtAuth_By);
  				                cs1.setString(12,"allow_modify");
  				                cs1.registerOutParameter(13,java.sql.Types.NUMERIC);
  				              cs1.setNull(13, java.sql.Types.NUMERIC);
  				                cs1.setString(14,update_user);
  				                cs1.setTimestamp(15,ts);
  				                System.out.println("radAuth_MC::::"+radAuth_MC);
  				                cs1.setString(16,radAuth_MC);
  				                cs1.setInt(17,sl_no);//sl_no
  				                
  					            //cs1.setString(18,docType); not coming 
  					          cs1.setString(18,BrsDocT);
  					          
  					            cs1.setInt(19,ccdd_no);  //cheque no
  					            
  					            System.out.println("ccdd_no "+ccdd_no);
                                                  
                                                  
                                                  
  				                /** Only selected Records should be stored */
                                                  System.out.println("Checked_value :: "+Checked_value);
                                                  System.out.println("Checked_value :: "+i);
				                  cs1.execute();
				                  txtSLNO = txtSLNO + sl_no + ",  ";
				                  //errcode=cs1.getInt(13);
				                  errcode = cs1.getBigDecimal(13).intValue();
				                  con.commit();
				                }  
                                                
                                                
				                
				                sl_no=0;
                                                
                                                
				                
				                System.out.println("SQLCODE:::"+errcode);
                                                
                                                
                                                
				                if(errcode!=0)
				                {   
				                	System.out.println("yes");
				                  con.rollback();
				                  sendMessage(response,"Authorization has failed ","ok");
				                  xml=xml+"<flag>failure</flag>";                          
				                  return;
				                }
				               
				               
                                                
			       }				          
                               con.commit();
                               sendMessage(response,"Modification Allowed to Bank Account number = "+cmbBankAccNo+" and SL NO ="+txtSLNO,"ok");
					         
			    }
		            catch(Exception e) 
		            {
		                try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
		                sendMessage(response,"The Authorization has failed ","ok");
		                System.out.println("Exception occur due to "+e);
		            }
		            finally
		            {
		                System.out.println("done");
		                try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
		            }
		            
		 }
    }
    
    
    
    
    
    
    /**
     * GET Method 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException    
    {
            
    
       /** Session Checking  */
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
        
        
        
        
       /** Database Connection  */
        Connection con=null;
        ResultSet rs=null,res4=null;
        PreparedStatement ps=null,ps4=null;
        
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control","no-cache");
        
        PrintWriter out = response.getWriter();
        String strCommand="";
        
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
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
             System.out.println("Exception in opening connection :"+e);            
        }
        
        
        
        
        
        /** Receiving Command  */
        try 
        {
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        /** Variables Declaration  */
        String xml="";
        int cmbAcc_UnitCode=0;
        int cmbOffice_code=0;       
        int txtCB_Year=0; 
        int txtCB_Month=0,closure_status=0;
        String TransType="";
        Date txtTo_date=null,txtFrom_date=null; 
        long cmbBankAccNo=0; 
      Calendar c;
        
        /** Get Accounting Unit ID  */
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
        
        /** Get Accounting Office ID  */
        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbOffice_code "+cmbOffice_code);
        
        /** Get Cashbook Month */
        try{txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("txtCB_Month "+txtCB_Month);
        
        /** Get Cashbook year */
        try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("txtCB_Year "+txtCB_Year);
        
        /** Get Transaction Type */
        try{TransType=request.getParameter("TransType");}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("TransType "+TransType);
        
        /** Get Bank Account Number */
        try{cmbBankAccNo=Long.parseLong(request.getParameter("cmbBankAccNo"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cmbBankAccNo "+cmbBankAccNo);
        

       /** Load Transaction Details */        
        if(strCommand.equalsIgnoreCase("Load_TransDetails")) 
        {
        	
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             xml="<response><command>Load_TransDetails</command>";
             
             
             
             try {
            	
              String sql="";
              String sql_T="";
              String sql_NT="";
              
                    sql_T="	select    						 \n"+
                        "	   SL_NO, doc_type, doc_no, 				 \n"+		  
						" 	   VOUCHER_OR_CHALLAN_NO,  		 \n"+ 
						"	   to_char(REMITTANCE_DATE,'DD/MM/YYYY')  as REMITTANCE_DATE,			 \n"+
						"           to_char(PASSBOOK_DATE,'DD/MM/YYYY')  as PASSBOOK_DATE,  \n"+
						"	   to_char(WITHDRAWAL_DATE,'DD/MM/YYYY')  as WITHDRAWAL_DATE,			 \n"+
						"	   CHEQUE_DD_NO,				 \n"+
						"	   CR_AMOUNT,					 \n"+
						"	   DR_AMOUNT,					 \n"+
						"	   AMOUNT_IN_PASSBOOK	    	 \n"+
						"	from 							 \n"+
						"	  fas_brs_transaction			 \n"+
						"	where 							 \n"+
						"	     ACCOUNTING_UNIT_ID = ?		 \n"+
						"	 and ACCOUNTING_FOR_OFFICE_ID=?  \n"+
						"	 and EXTRACT(year FROM PASSBOOK_DATE) = ? 			 \n"+
						"	 and EXTRACT(month FROM PASSBOOK_DATE) = ?			 \n"+
						"	 and TWAD_OR_NON_TWAD = 'T'		 \n"+ 
						"	 and ACCOUNT_NO = ?		 order by PASSBOOK_DATE,CHEQUE_DD_NO      ";
           
                    sql_NT= "select 						\n"+ 
                    		"  SL_NO,doc_type, doc_no,						\n"+
                    		"  to_char(PASSBOOK_DATE,'DD/MM/YYYY')  as PASSBOOK_DATE,	\n"+
							"  PARTICULARS,  				\n"+
							"  CHEQUE_DD_NO,				\n"+
							"  CR_AMOUNT,					\n"+
							"  DR_AMOUNT  					\n"+
							"from 							\n"+
							"  fas_brs_transaction			\n"+
							"where 							\n"+
							"     ACCOUNTING_UNIT_ID = ?	\n"+
							" and ACCOUNTING_FOR_OFFICE_ID = ?	\n"+
							" and EXTRACT(year FROM PASSBOOK_DATE) = ?			\n"+
							" and EXTRACT(month FROM PASSBOOK_DATE) = ?		\n"+
							" and TWAD_OR_NON_TWAD = 'NT'	\n"+ 
							" and ACCOUNT_NO = ?	 order by PASSBOOK_DATE,CHEQUE_DD_NO    ";
							
                    
                     if (TransType.equalsIgnoreCase("T"))
                     {
                       sql=sql_T;
                     }
                     else if (TransType.equalsIgnoreCase("NT"))
                     {
                       sql=sql_NT;
                     }
                    System.out.println("sql:::"+sql);
		                    try {
		                            ps=con.prepareStatement(sql);
		                            ps.setInt(1,cmbAcc_UnitCode);
		                            ps.setInt(2,cmbOffice_code);
		                            ps.setInt(3,txtCB_Year);
		                            ps.setInt(4,txtCB_Month);
		                            ps.setLong(5,cmbBankAccNo);
		                            
		                            rs=ps.executeQuery();
		                            
		                             int count=0;
		                         
		                            while(rs.next())
		                            {
		                            	xml=xml+"<Sl_No>"+rs.getString("SL_NO")+"</Sl_No>";
		                            	
		                            	if (TransType.equalsIgnoreCase("T"))
		                                {
			                            xml=xml+"<docNo>"+rs.getString("doc_no")+"</docNo>";
			                            xml=xml+"<docType>"+rs.getString("doc_type")+"</docType>";
		                                    xml=xml+"<Vou_No>"+rs.getString("VOUCHER_OR_CHALLAN_NO")+"</Vou_No>";
		                                    xml=xml+"<Rem_Date>"+rs.getString("REMITTANCE_DATE")+"</Rem_Date>";
			                            xml=xml+"<With_Date>"+rs.getString("WITHDRAWAL_DATE")+"</With_Date>";
			                            xml=xml+"<entryDate>"+rs.getString("PASSBOOK_DATE")+"</entryDate>";
			                            xml=xml+"<Amount_Passbook>"+rs.getFloat("AMOUNT_IN_PASSBOOK")+"</Amount_Passbook>";
			                        }
		                                else if (TransType.equalsIgnoreCase("NT"))
		                                {
		                                	xml=xml+"<docNo>"+rs.getString("doc_no")+"</docNo>";
				                            xml=xml+"<docType>"+rs.getString("doc_type")+"</docType>";
		                                  xml=xml+"<Particulars>"+rs.getString("PARTICULARS")+"</Particulars>";
		                                  xml=xml+"<Passbook_Date>"+rs.getString("PASSBOOK_DATE")+"</Passbook_Date>";
		                                }
		                            	
		                            	xml=xml+"<Cheque_No>"+rs.getString("CHEQUE_DD_NO")+"</Cheque_No>";
		                            	xml=xml+"<cr_amt>"+rs.getString("CR_AMOUNT")+"</cr_amt>";
		                            	xml=xml+"<dr_amt>"+rs.getString("DR_AMOUNT")+"</dr_amt>";
		                                count++;
		                            }
		                            
		                            System.out.println("Count -->"+count);
		                            
		                            if(count==0)
		                                xml=xml+"<flag>NoRecords</flag>";
		                            else 
		                                xml=xml+"<flag>success</flag>";
		                            
		                           
		                        xml=xml+"<TransType>"+TransType+"</TransType>";
		                            
		                        System.out.println("count  "+count);
		                        
		                        ps.close();
		                        rs.close();
		                        
		                        }
		                        catch(Exception e)
		                        {
		                            System.out.println("catch..HERE.in load VOUCHER."+e);
		                            xml=xml+"<flag>failure</flag>";
		                        }
		                        
		                        
                        
	    						
	    			
	    			}catch (Exception e) {
	    				xml=xml+"<flag>failure</flag>";
					}
    
	    			xml=xml+"</response>";
                    System.out.println(xml);
                    out.println(xml);
        }  
        
      else if(strCommand.equalsIgnoreCase("ckMonth")) 
      {
          String CONTENT_TYPE = "text/xml; charset=windows-1252";
          response.setContentType(CONTENT_TYPE);
          xml="<response><command>ckMonth</command>";
          try {
                             PreparedStatement ps1 = con
                                             .prepareStatement("select STATUS FROM FAS_BRS_MONTHLY_CLOSURE WHERE " +
                                                             "ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND " +
                                                             "CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? and ACCOUNT_NO=?");
                             ps1.setInt(1, cmbAcc_UnitCode);
                             ps1.setInt(2, cmbOffice_code);
                             ps1.setInt(3, txtCB_Year);
                             ps1.setInt(4, txtCB_Month);
                             ps1.setLong(5, cmbBankAccNo);
                
                             ResultSet rs1 = ps1.executeQuery();
                              if (rs1.next()) 
                              {
                                  xml=xml+"<flag>BRSfreezed</flag>";
                                 String status = rs1.getString("STATUS");                                                
                                
                              }
                  xml=xml+"<flag>NoData</flag>";
                             
           }
          catch(Exception e2) {
              System.out.println("Exception in FAS_BRS_MONTHLY_CLOSURE::::"+e2);
          }
          xml=xml+"</response>";
          System.out.println(xml);
          out.println(xml);
      }
      else if(strCommand.equalsIgnoreCase("ckyear")) 
      {
          String CONTENT_TYPE = "text/xml; charset=windows-1252";
          response.setContentType(CONTENT_TYPE);
          xml="<response><command>ckyear</command>";
          try {
                             PreparedStatement ps1 = con
                                             .prepareStatement("select STATUS,CASHBOOK_YEAR,CASHBOOK_MONTH FROM FAS_BRS_MONTHLY_CLOSURE WHERE " +
                                                             "ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND " +
                                                             "ACCOUNT_NO=?");
                             ps1.setInt(1, cmbAcc_UnitCode);
                             ps1.setInt(2, cmbOffice_code);
                            // ps1.setInt(3, txtCB_Year);
                           //  ps1.setInt(4, txtCB_Month);
                             ps1.setLong(3, cmbBankAccNo);
                
                             ResultSet rs1 = ps1.executeQuery();
                              if (rs1.next()) 
                              {
                                  xml=xml+"<flag>BRSfreezed</flag>";
                                  xml=xml+"<year>"+rs1.getString("CASHBOOK_YEAR")+"</year>";
                                  xml=xml+"<month>"+rs1.getString("CASHBOOK_MONTH")+"</month>";
                                
                              }
                              else{
                            	  xml=xml+"<flag>NoData</flag>";
                              }
                             
           }
          catch(Exception e2) {
              System.out.println("Exception in FAS_BRS_MONTHLY_CLOSURE::::"+e2);
          }
          xml=xml+"</response>";
          System.out.println(xml);
          out.println(xml);
      } 
        else if(strCommand.equalsIgnoreCase("Load_TransDetails_date")) 
        {	    
                    String[] sd=request.getParameter("txtFrom_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtFrom_date=new Date(d.getTime());
	            System.out.println("from_date "+txtFrom_date);
	            
	            sd=request.getParameter("txtTo_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            d=c.getTime();
	            txtTo_date=new Date(d.getTime());
	            System.out.println("txtTo_date "+txtTo_date);
             
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             xml="<response><command>Load_TransDetails_date</command>";
             try {
                            
             String sql="";
             String sql_T="";
             String sql_NT="";
             
                 sql_T="     select                                                   \n"+
                     "          SL_NO, doc_type, doc_no,                              \n"+             
                                             "          VOUCHER_OR_CHALLAN_NO,                \n"+ 
                                             "          to_char(REMITTANCE_DATE,'DD/MM/YYYY')  as REMITTANCE_DATE,  " +
                                             "           to_char(PASSBOOK_DATE,'DD/MM/YYYY')  as PASSBOOK_DATE,  \n"+
                                             "          to_char(WITHDRAWAL_DATE,'DD/MM/YYYY')  as WITHDRAWAL_DATE,                    \n"+
                                             "          CHEQUE_DD_NO,                                 \n"+
                                             "          CR_AMOUNT,                                    \n"+
                                             "          DR_AMOUNT,                                    \n"+
                                             "          AMOUNT_IN_PASSBOOK            \n"+
                                             "       from                                                     \n"+
                                             "         fas_brs_transaction                    \n"+
                                             "       where                                                    \n"+
                                             "            ACCOUNTING_UNIT_ID = ?              \n"+
                                             "        and ACCOUNTING_FOR_OFFICE_ID=?  \n"+
                                             "        and (PASSBOOK_DATE between ? and ?)                   \n"+
                                             "        and TWAD_OR_NON_TWAD = 'T'              \n"+ 
                                             "        and ACCOUNT_NO = ?             order by PASSBOOK_DATE,CHEQUE_DD_NO            ";
             
                 sql_NT= "select                                             \n"+ 
                             "  SL_NO,doc_type, doc_no,                                              \n"+
                             "  to_char(PASSBOOK_DATE,'DD/MM/YYYY')  as PASSBOOK_DATE,       \n"+
                                                     "  PARTICULARS,                                 \n"+
                                                     "  CHEQUE_DD_NO,                                \n"+
                                                     "  CR_AMOUNT,                                   \n"+
                                                     "  DR_AMOUNT                                    \n"+
                                                     "from                                                   \n"+
                                                     "  fas_brs_transaction                  \n"+
                                                     "where                                                  \n"+
                                                     "     ACCOUNTING_UNIT_ID = ?    \n"+
                                                     " and ACCOUNTING_FOR_OFFICE_ID = ?      \n"+
                                                     "        and (PASSBOOK_DATE between ? and ?)                   \n"+
                                                     " and TWAD_OR_NON_TWAD = 'NT'   \n"+ 
                                                     " and ACCOUNT_NO = ?          order by PASSBOOK_DATE,CHEQUE_DD_NO          ";
                                                     
                 
                  if (TransType.equalsIgnoreCase("T"))
                  {
                    sql=sql_T;
                  }
                  else if (TransType.equalsIgnoreCase("NT"))
                  {
                    sql=sql_NT;
                  }
                 
                                 try {
                                         ps=con.prepareStatement(sql);
                                         ps.setInt(1,cmbAcc_UnitCode);
                                         ps.setInt(2,cmbOffice_code);
                                         ps.setDate(3,txtFrom_date);
                                         ps.setDate(4,txtTo_date);
                                         ps.setLong(5,cmbBankAccNo);
                                         
                                         rs=ps.executeQuery();
                                         
                                          int count=0;
                                      
                                         while(rs.next())
                                         {
                                             xml=xml+"<Sl_No>"+rs.getString("SL_NO")+"</Sl_No>";
                                             
                                             if (TransType.equalsIgnoreCase("T"))
                                             {
                                                 xml=xml+"<docNo>"+rs.getString("doc_no")+"</docNo>";
                                                 xml=xml+"<docType>"+rs.getString("doc_type")+"</docType>";
                                                 xml=xml+"<Vou_No>"+rs.getString("VOUCHER_OR_CHALLAN_NO")+"</Vou_No>";
                                                 xml=xml+"<Rem_Date>"+rs.getString("REMITTANCE_DATE")+"</Rem_Date>";
                                                 xml=xml+"<With_Date>"+rs.getString("WITHDRAWAL_DATE")+"</With_Date>";
                                                 xml=xml+"<entryDate>"+rs.getString("PASSBOOK_DATE")+"</entryDate>";
                                                 xml=xml+"<Amount_Passbook>"+rs.getFloat("AMOUNT_IN_PASSBOOK")+"</Amount_Passbook>";
                                             }
                                             else if (TransType.equalsIgnoreCase("NT"))
                                             {
                                                     xml=xml+"<docNo>"+rs.getString("doc_no")+"</docNo>";
                                                         xml=xml+"<docType>"+rs.getString("doc_type")+"</docType>";
                                               xml=xml+"<Particulars>"+rs.getString("PARTICULARS")+"</Particulars>";
                                               xml=xml+"<Passbook_Date>"+rs.getString("PASSBOOK_DATE")+"</Passbook_Date>";
                                             }
                                             
                                             xml=xml+"<Cheque_No>"+rs.getString("CHEQUE_DD_NO")+"</Cheque_No>";
                                             xml=xml+"<cr_amt>"+rs.getString("CR_AMOUNT")+"</cr_amt>";
                                             xml=xml+"<dr_amt>"+rs.getString("DR_AMOUNT")+"</dr_amt>";
                                             count++;
                                         }
                                         
                                         System.out.println("Count -->"+count);
                                         
                                         if(count==0)
                                             xml=xml+"<flag>NoRecords</flag>";
                                         else 
                                             xml=xml+"<flag>success</flag>";
                                         
                                        
                                     xml=xml+"<TransType>"+TransType+"</TransType>";
                                         
                                     System.out.println("count  "+count);
                                     
                                     ps.close();
                                     rs.close();
                                     
                                     }
                                     catch(Exception e)
                                     {
                                         System.out.println("catch..HERE.in load VOUCHER."+e);
                                         xml=xml+"<flag>failure</flag>";
                                     }
                                     
                                     
                     
                        
                             
                             }catch (Exception e) {
                                     xml=xml+"<flag>failure</flag>";
                                     }
                             xml=xml+"</response>";
                 System.out.println(xml);
                 out.println(xml);
        }
        else if(strCommand.equalsIgnoreCase("check_ob")) 
        {
        	 String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             xml="<response><command>check_ob</command>";
             try {
                                PreparedStatement ps1 = con.prepareStatement("");
                                ps1.setInt(1, cmbAcc_UnitCode);
                                ps1.setInt(2, cmbOffice_code);
                                ps1.setInt(3, txtCB_Year);
                                ps1.setInt(4, txtCB_Month);
                                ps1.setLong(5, cmbBankAccNo);
                   
                                ResultSet rs1 = ps1.executeQuery();
                                 if (rs1.next()) 
                                 {
                                     xml=xml+"<flag>BRSfreezed</flag>";
                                    String status = rs1.getString("STATUS");                                                
                                   
                                 }
                     xml=xml+"<flag>NoData</flag>";
                                
              }
             catch(Exception e2) {
                 System.out.println("Exception in FAS_BRS_MONTHLY_CLOSURE::::"+e2);
             }
        	 xml=xml+"</response>";
             System.out.println(xml);
             out.println(xml);
        }
  }
        
    
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);
        }
        catch(IOException e)
        {
        System.out.println("Excep"+e);
        }
    }
    
    
}
