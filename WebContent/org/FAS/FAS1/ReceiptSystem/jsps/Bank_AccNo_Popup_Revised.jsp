<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Account Number List</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" language="javascript">
    function btncancel()
    {
     self.close();
    }
    function btnsubmit()
    {
        
        var sele=document.getElementsByName("choice").length;
        var val=0;
        var getId='',bankid='',br_id='',B_name='',Acc_Head_Code='',Bank_Acc_No='',mode='';
        
           
        
        
        if(sele>0)
        {
           
            if(sele==1)
            {
             
                if(document.frm_AccNo_Popup_Form.choice.checked==true)
                {
                 getId=document.frm_AccNo_Popup_Form.choice.value;
                 r=document.getElementById(getId);
                 rcells=r.cells;
                 Acc_Head_Code=rcells.item(1).firstChild.nodeValue;
                 Bank_Acc_No=rcells.item(2).firstChild.nodeValue;
                 bankid=rcells.item(8).firstChild.nodeValue;
                 B_name=rcells.item(3).firstChild.nodeValue+" - "+rcells.item(4).firstChild.nodeValue;
                 br_id=rcells.item(9).firstChild.nodeValue;
                 mode=rcells.item(6).firstChild.nodeValue;
                 //mode_id=AC_OPERATIONAL_MODE_ID;
                 //alert("AC_OPERATIONAL_MODE_ID===>"+mode_id);
                }
            }
            else
            {      //alert("else"+sele);
                    for(i=0;i<sele;i++)
                    { 
                        //alert(document.frm_AccNo_Popup_Form.choice[i].checked)
                        if(document.frm_AccNo_Popup_Form.choice[i].checked==true)
                        {
                             getId=document.frm_AccNo_Popup_Form.choice[i].value;
                             
                             r=document.getElementById(getId);
                             rcells=r.cells;
                             Acc_Head_Code=rcells.item(1).firstChild.nodeValue;
                             Bank_Acc_No=rcells.item(2).firstChild.nodeValue;
                             bankid=rcells.item(8).firstChild.nodeValue;
                             B_name=rcells.item(3).firstChild.nodeValue+" - "+rcells.item(4).firstChild.nodeValue;
                             br_id=rcells.item(9).firstChild.nodeValue;
                             mode=rcells.item(6).firstChild.nodeValue;
                            // mode_id=AC_OPERATIONAL_MODE_ID;
                             //alert("AC_OPERATIONAL_MODE_ID===>"+mode_id);
                             break;
                        }
                    }
             }
           
            
        }
            
        Minimize();
        //alert(Acc_Head_Code+"    "+Bank_Acc_No+"    "+bankid+"    "+br_id+"    "+B_name)
      opener.doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name,mode);
     // alert(Acc_Head_Code+"    "+Bank_Acc_No+"    "+bankid+"    "+br_id+"    "+B_name);
        
        self.close();
        return true;
   }
   
    function Minimize() 
    {
    window.resizeTo(0,0);
    window.screenX = screen.width;
    window.screenY = screen.height;
    opener.window.focus();
    }

</script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body bgcolor="rgb(255,255,225)">
  
  <form name="frm_AccNo_Popup_Form" id="frm_AccNo_Popup_Form">
      <p>
        <%
  
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
      </p>
      <p>
        &nbsp;
      </p>
     
      <table  border="0" width="80%">
      <tr><td>
       <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="100%">
         <tr class="tdH" >
        <th align="center" colspan="2">
                Selection of Account Number
                </th>
           </tr>
     </table>
      
         
        
         <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
              Select
            </th>
            <th>
                Account Head Code
            </th>
             <th>
                Bank Account Number
            </th>
            <th>
                 Bank Name
            </th>
            <th>
                 Branch Name-City
            </th>
            <th>
             Account type
            </th>
            <th>
            Operation Mode
            </th>
            <th>
            CR/DR
            </th>
             <th >
                Bank Id
            </th>
            <th>
                Branch Id
            </th>          
            
            
          </tr>
          <tbody id="tb" class="table">

          <%
          	ResultSet rs2=null,rs3=null;
                     PreparedStatement ps2=null,ps3=null;
                 try
                 {
                     int cmbAcc_UnitCode=0,txtCash_Acc_code=0,y=0,Office_code=0,txtSubBankId=0,txtSub_Office_code=0;
                     String txtModule_Type="",cr_dr_indi="",unspent_OR_col="";
                     
                      try{
                       Office_code=Integer.parseInt(request.getParameter("Office_code"));
                      }catch(Exception e){System.out.println("Exception in getting Office_code:"+e);}
                     
                      try{
                       cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                      }catch(Exception e){System.out.println("Exception in getting cmbAcc_UnitCode:"+e);}
                     
                     
                      try{
                      txtModule_Type=request.getParameter("txtModule_Type");
                      }catch(Exception e){System.out.println("Exception in getting txtModule_Type:"+e);}
                      
                      try{
                      cr_dr_indi=request.getParameter("cr_dr_indi");
                      }catch(Exception e){System.out.println("Exception in getting cr_dr_indi:"+e);}
                      
                      unspent_OR_col=request.getParameter("unspent_OR_col");
                      
                       try{
                      txtSubBankId=Integer.parseInt(request.getParameter("txtSubBankId"));
                      }catch(Exception e){System.out.println("Exception in getting txtSubBankId:"+e);}
                      try{
                      	txtSub_Office_code=Integer.parseInt(request.getParameter("txtSub_Office_code"));
                          }catch(Exception e){System.out.println("Exception in getting txtSubBankId:"+e);}
                      
                      System.out.println("cmbAcc_UnitCode.."+cmbAcc_UnitCode);
                      System.out.println("txtModule_Type.."+txtModule_Type);
                      System.out.println("cr_dr_indi.."+cr_dr_indi);
                      System.out.println("Office_code.."+Office_code);
                      System.out.println("unspent_OR_col.."+unspent_OR_col);
                      System.out.println("txtSubBankId...."+txtSubBankId);
                      
                      if(Office_code!=0 )     // When office id send by request, this will execute
                      {
                          if(cmbAcc_UnitCode!=5)       // for other offices
                          {
                              ps3=con.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?");
                              ps3.setInt(1,Office_code);
                              rs3=ps3.executeQuery();
                              if(rs3.next())
                              {
                                          cmbAcc_UnitCode=rs3.getInt("ACCOUNTING_UNIT_ID");
                                          ps3.close();
                                          rs3.close();
                                          System.out.println("cmbAcc_UnitCode.."+cmbAcc_UnitCode);
                              }
                          }
                          else if(Office_code==5000)          // If Head office banking section means
                              cmbAcc_UnitCode=5;
                      }
                      String sub_Qry="";
                      System.out.println("after accounting unit finding....");
                      System.out.println("cmbAcc_UnitCode.."+cmbAcc_UnitCode);
                      System.out.println("Office_code.."+Office_code);
                                  //String sql_bank="select BANK_AC_NO,BANK_ID, BRANCH_ID, BANK_AC_NO from FAS_OFFICE_BANK_AC_CURRENT where OFFICE_ID="+cmbOffice_code+" and  AC_HEAD_CODE="+txtCash_Acc_code;
                                  //String sql_bank="select AC_HEAD_CODE,BANK_ID, BRANCH_ID, BANK_AC_NO from FAS_OFFICE_BANK_AC_CURRENT where OFFICE_ID="+cmbOffice_code+" and BANK_AC_TYPE_ID='"+txtBank_Acc_Type+"'";
                     
                     
                      String sql_bank="";
                          if(unspent_OR_col==null)
                          {
                          	System.out.println("txtModule_Type"+txtModule_Type);
                          	if(txtModule_Type.equalsIgnoreCase("MF006")){
                          		String txtRem_Type=request.getParameter("txtRem_Type");
                          		if(txtRem_Type.equalsIgnoreCase("C")){
                          		
                          		//	sub_Qry= " and ac.AC_OPERATIONAL_MODE_ID not like '%-NRD%' ";
                          		}
                          		
                          	else{
                          		sub_Qry="";
                          	}
                          		}else{
                          			sub_Qry="";
                          		}
                                  System.out.println("inside unspent_OR_col.. nulllll");
                                   sql_bank="select ac.AC_HEAD_CODE,ac.BANK_AC_NO,bk.BANK_NAME,br.BRANCH_NAME ||'-'|| coalesce(br.CITY_TOWN_NAME,'') AS BRANCH_CITY,"+
                                  " ac.BANK_ID,ac.BRANCH_ID,ac.BANK_AC_TYPE_ID, ac.AC_OPERATIONAL_MODE_ID,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE , ac.CR_DR_TYPE,ac.MODULE_ID from "+
                                  " FAS_OFFICE_BANK_AC_CURRENT ac,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t,FAS_MST_AC_OPER_MODES m  where ac.STATUS='Y' and ac.ACCOUNTING_UNIT_ID=? and ac.MODULE_ID=? and ac.CR_DR_TYPE=? "+
                                  " and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID "+sub_Qry+" order by ac.SL_NO desc";
                           
                                   
                          
                          }     
                          else if(unspent_OR_col.equalsIgnoreCase("COL"))
                          {
                                
                                if ( cmbAcc_UnitCode == 5 )                       
                                {
                                System.out.println("unit id::::5::::::");
                                           sql_bank="SELECT ac.AC_HEAD_CODE,\n" + 
                                                     "  ac.BANK_AC_NO,\n" + 
                                                     "  bk.BANK_NAME,\n" + 
                                                     "  br.BRANCH_NAME\n" + 
                                                     "  ||'-'\n" + 
                                                     "  || coalesce(br.CITY_TOWN_NAME,'') AS BRANCH_CITY,\n" + 
                                                     "  ac.BANK_ID,\n" + 
                                                     "  ac.BRANCH_ID,\n" + 
                                                     "  ac.BANK_AC_TYPE_ID,\n" + 
                                                     "  ac.AC_OPERATIONAL_MODE_ID,\n" + 
                                                     "  t.ACCOUNT_TYPE,\n" + 
                                                     "  m.AC_OPERATIONAL_MODE ,\n" + 
                                                     "  ac.CR_DR_TYPE,\n" + 
                                                     "  ac.MODULE_ID\n" + 
                                                     "FROM FAS_OFFICE_BANK_AC_CURRENT ac,\n" + 
                                                     "  FAS_MST_BANKS bk,\n" + 
                                                     "  FAS_MST_BANK_BRANCHES br,\n" + 
                                                     "  FAS_MST_BANK_AC_TYPES t,\n" + 
                                                     "  FAS_MST_AC_OPER_MODES m\n" + 
                                                     "WHERE ac.STATUS              ='Y'\n" + 
                                                     "AND ac.ACCOUNTING_UNIT_ID    =?\n" + 
                                                     "AND ac.MODULE_ID             =?\n" + 
                                                     "AND ac.CR_DR_TYPE            =?\n" + 
                                                     "AND t.ACCOUNT_TYPE_ID        =ac.BANK_AC_TYPE_ID\n" + 
                                                     "AND ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID\n" + 
                                                     "AND ac.BANK_ID               =br.BANK_ID\n" + 
                                                     "AND ac.BRANCH_ID             =br.BRANCH_ID\n" + 
                                                     "AND ac.BANK_ID               =bk.BANK_ID\n" + 
                                                    // "and ac.ac_head_code like '%3'\n" + 
                                                     // Lakshmi " and Ac.Bank_Id = "+txtSubBankId+
                                                      "  AND ac.AC_OPERATIONAL_MODE_ID in ('SNA-NIDA','SNA-NABARD','COL','SCH','RBI','OPR','OPR-NRDWP-Support','OPR-NRDWP-Main','OPR-NRDWP-Calamity','NRDWP-WQM-SP') ORDER BY ac.SL_NO DESC";
                                           //  "  AND ac.AC_OPERATIONAL_MODE_ID='COL' AND ac.ac_head_code like '8%3' ORDER BY ac.SL_NO DESC";

                                
                                }
                                else // For Other Units */                      
                                {
                              	  System.out.println("unit id:::in collection");
                                  sql_bank="select ac.AC_HEAD_CODE,ac.BANK_AC_NO,bk.BANK_NAME,br.BRANCH_NAME ||'-'||coalesce( br.CITY_TOWN_NAME,'') AS BRANCH_CITY,"+
                                  " ac.BANK_ID,ac.BRANCH_ID,ac.BANK_AC_TYPE_ID, ac.AC_OPERATIONAL_MODE_ID,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE , ac.CR_DR_TYPE,ac.MODULE_ID from "+
                                  " FAS_OFFICE_BANK_AC_CURRENT ac,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t, FAS_MST_AC_OPER_MODES m  where ac.STATUS='Y' and ac.ACCOUNTING_UNIT_ID=? and ac.MODULE_ID=? and ac.CR_DR_TYPE=? "+
                                  " and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID "+
                                 // " and ac.AC_OPERATIONAL_MODE_ID in ('COL','FDW') "+
                             		// " and ac.AC_OPERATIONAL_MODE_ID IN('COL','OPR') "+
                              		//unspent_OR_col
                                 // " and Ac.Bank_Id = "+txtSubBankId+" AND ac.ac_head_code like '8%6' "+
                                  " order by ac.SL_NO desc";
                                }  
                                  
                                  
                                  
                                  
                          }   else if(unspent_OR_col.equalsIgnoreCase("OPR-NRDWP-Support"))
                          {
                          	sql_bank="SELECT ac.AC_HEAD_CODE, " +
                          	"  ac.BANK_AC_NO, " +
                          	"  bk.BANK_NAME, " +
                          	"  br.BRANCH_NAME " +
                          	"  ||'-' " +
                          	"  || coalesce(br.CITY_TOWN_NAME,'') AS BRANCH_CITY, " +
                          	"  ac.BANK_ID, " +
                          	"  ac.BRANCH_ID, " +
                          	"  ac.BANK_AC_TYPE_ID, " +
                          	"  ac.AC_OPERATIONAL_MODE_ID, " +
                          	"  t.ACCOUNT_TYPE, " +
                          	"  m.AC_OPERATIONAL_MODE , " +
                          	"  ac.CR_DR_TYPE, " +
                          	"  ac.MODULE_ID " +
                          	"FROM FAS_OFFICE_BANK_AC_CURRENT ac, " +
                          	"  FAS_MST_BANKS bk, " +
                          	"  FAS_MST_BANK_BRANCHES br, " +
                          	"  FAS_MST_BANK_AC_TYPES t, " +
                          	"   FAS_MST_AC_OPER_MODES m " +
                          	" WHERE ac.STATUS              ='Y' " +
                          	" AND ac.ACCOUNTING_UNIT_ID    =? " +
                          	" AND ac.MODULE_ID             =? " +
                          	" AND ac.CR_DR_TYPE            =? " +
                          	// siva hide on 2016-03-03 issue no=21275 thats why hide this  line
                          	" AND ac.ac_operational_mode_id='OPR-NRDWP-Support' " +
                          	" AND t.ACCOUNT_TYPE_ID        =ac.BANK_AC_TYPE_ID " +
                          	" AND ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID " +
                          	" AND ac.BANK_ID               =br.BANK_ID " +
                          	" AND ac.BRANCH_ID             =br.BRANCH_ID " +
                          	" AND ac.BANK_ID               =bk.BANK_ID " +
                          	" ORDER BY ac.SL_NO DESC";
                          }
                          else if(unspent_OR_col.equalsIgnoreCase("OPR"))
                          {
                          System.out.println("cmbAcc_UnitCode***&&&&::::"+cmbAcc_UnitCode);
                                if ( cmbAcc_UnitCode == 5 )                 
                                 {
                                if(txtSub_Office_code!=6777){
                                             /*
                                             
                                             Joan change don 29May2015
                                             
                                             sql_bank="select ac.AC_HEAD_CODE,ac.BANK_AC_NO,bk.BANK_NAME,br.BRANCH_NAME ||'-'|| br.CITY_TOWN_NAME AS BRANCH_CITY,"+
                                  " ac.BANK_ID,ac.BRANCH_ID,ac.BANK_AC_TYPE_ID, ac.AC_OPERATIONAL_MODE_ID,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE , ac.CR_DR_TYPE,ac.MODULE_ID from "+
                                  " FAS_OFFICE_BANK_AC_CURRENT ac,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t,FAS_MST_AC_OPER_MODES m  where ac.STATUS='Y' and ac.ACCOUNTING_UNIT_ID=? and ac.MODULE_ID=? and ac.CR_DR_TYPE=? "+
                                //  " and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID and ac.AC_OPERATIONAL_MODE_ID IN('OPR','COL') and Ac.Bank_Id = "+txtSubBankId+" AND ac.ac_head_code like '82%' order by ac.SL_NO desc";
                              " and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID and ac.AC_OPERATIONAL_MODE_ID IN('OPR') and Ac.Bank_Id = "+txtSubBankId+" AND ac.ac_head_code like '8%2' order by ac.SL_NO desc"; */
                              
                              
                              	  
                                    	sql_bank = 
                                    			"select ac.AC_HEAD_CODE,ac.BANK_AC_NO,bk.BANK_NAME,br.BRANCH_NAME ||'-'|| coalesce(br.CITY_TOWN_NAME,'') AS BRANCH_CITY,"+
                                                        " ac.BANK_ID,ac.BRANCH_ID,ac.BANK_AC_TYPE_ID, ac.AC_OPERATIONAL_MODE_ID,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE , ac.CR_DR_TYPE,ac.MODULE_ID from "+
                                                        " FAS_OFFICE_BANK_AC_CURRENT ac,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t,FAS_MST_AC_OPER_MODES m  where ac.STATUS='Y' and ac.ACCOUNTING_UNIT_ID=? and ac.MODULE_ID=? and ac.CR_DR_TYPE=? "+
                                                      //  " and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID and ac.AC_OPERATIONAL_MODE_ID IN('OPR','COL') and Ac.Bank_Id = "+txtSubBankId+" AND ac.ac_head_code like '82%' order by ac.SL_NO desc";
                                                    " and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID and ac.AC_OPERATIONAL_MODE_ID IN('OPR') and Ac.Bank_Id = "+txtSubBankId+" AND ac.ac_head_code like '8%2' "+
                                                    
                                    	
                                    	
                                    	
                                    	" union all select ac.AC_HEAD_CODE,ac.BANK_AC_NO,bk.BANK_NAME,br.BRANCH_NAME ||'-'|| coalesce(br.CITY_TOWN_NAME,'') AS BRANCH_CITY,"
          							+ " ac.BANK_ID,ac.BRANCH_ID,ac.BANK_AC_TYPE_ID, ac.AC_OPERATIONAL_MODE_ID,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE , ac.CR_DR_TYPE,ac.MODULE_ID from "
          							+ " FAS_OFFICE_BANK_AC_CURRENT ac,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t,FAS_MST_AC_OPER_MODES m  where ac.STATUS='Y' and ac.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ac.MODULE_ID='"+txtModule_Type+"' and ac.CR_DR_TYPE='"+cr_dr_indi+"' "
          							+
          							//  " and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID and ac.AC_OPERATIONAL_MODE_ID IN('OPR','COL') and Ac.Bank_Id = "+txtSubBankId+" AND ac.ac_head_code like '82%' order by ac.SL_NO desc";
          							" and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID and ac.AC_OPERATIONAL_MODE_ID IN('OPR-NRDWP-Main','OPR-NRDWP-Support','OPR-NRDWP-Calamity') ";
          							//" and Ac.Bank_Id = "          							+ txtSubBankId;
          							// " AND ac.ac_head_code like '8%2' 
          							// " order by SL_NO desc";
          							
                                    	
          				} else {
          					sql_bank = "SELECT ac.AC_HEAD_CODE,\n"
          							+ "  ac.BANK_AC_NO,\n"
          							+ "  bk.BANK_NAME,\n"
          							+ "  br.BRANCH_NAME\n"
          							+ "  ||'-'\n"
          							+ "  || coalesce(br.CITY_TOWN_NAME,'') AS BRANCH_CITY,\n"
          							+ "  ac.BANK_ID,\n"
          							+ "  ac.BRANCH_ID,\n"
          							+ "  ac.BANK_AC_TYPE_ID,\n"
          							+ "  ac.AC_OPERATIONAL_MODE_ID,\n"
          							+ "  t.ACCOUNT_TYPE,\n"
          							+ "  m.AC_OPERATIONAL_MODE ,\n"
          							+ "  ac.CR_DR_TYPE,\n"
          							+ "  ac.MODULE_ID\n"
          							+ "  \n"
          							+ "FROM FAS_OFFICE_BANK_AC_CURRENT ac,\n"
          							+ "  FAS_MST_BANKS bk,\n"
          							+ "  FAS_MST_BANK_BRANCHES br,\n"
          							+ "  FAS_MST_BANK_AC_TYPES t,\n"
          							+ "  FAS_MST_AC_OPER_MODES m\n"
          							+ "WHERE ac.STATUS                ='Y'\n"
          							+ "AND ac.ACCOUNTING_UNIT_ID      =?\n"
          							+ "AND ac.MODULE_ID               =?\n"
          							+ "AND ac.CR_DR_TYPE              =?\n"
          							+ "AND t.ACCOUNT_TYPE_ID          =ac.BANK_AC_TYPE_ID\n"
          							+ "AND ac.AC_OPERATIONAL_MODE_ID  =m.AC_OPERATIONAL_MODE_ID\n"
          							+ "AND ac.BANK_ID                 =br.BANK_ID\n"
          							+ "AND ac.BRANCH_ID               =br.BRANCH_ID\n"
          							+ "AND ac.BANK_ID                 =bk.BANK_ID\n"
          							+ "AND ac.AC_OPERATIONAL_MODE_ID IN('OPR')\n"
          							+ "and Ac.Bank_Id ="
          							+ txtSubBankId
          							+ "AND ac.ac_head_code LIKE '8%2'\n"
          							+ "union all\n"
          							+ "SELECT ac.AC_HEAD_CODE,\n"
          							+ "  ac.BANK_AC_NO,\n"
          							+ "  bk.BANK_NAME,\n"
          							+ "  br.BRANCH_NAME\n"
          							+ "  ||'-'\n"
          							+ "  || br.CITY_TOWN_NAME AS BRANCH_CITY,\n"
          							+ "  ac.BANK_ID,\n"
          							+ "  ac.BRANCH_ID,\n"
          							+ "  ac.BANK_AC_TYPE_ID,\n"
          							+ "  ac.AC_OPERATIONAL_MODE_ID,\n"
          							+ "  t.ACCOUNT_TYPE,\n"
          							+ "  m.AC_OPERATIONAL_MODE ,\n"
          							+ "  ac.CR_DR_TYPE,\n"
          							+ "  ac.MODULE_ID\n"
          							+ "  \n"
          							+ "FROM FAS_OFFICE_BANK_AC_CURRENT ac,\n"
          							+ "  FAS_MST_BANKS bk,\n"
          							+ "  FAS_MST_BANK_BRANCHES br,\n"
          							+ "  FAS_MST_BANK_AC_TYPES t,\n"
          							+ "  FAS_MST_AC_OPER_MODES m\n"
          							+ "WHERE ac.STATUS                ='Y'\n"
          							+ "AND ac.ACCOUNTING_UNIT_ID      ="
          							+ cmbAcc_UnitCode
          							+ "AND ac.MODULE_ID               ='"
          							+ txtModule_Type
          							+ "'\n"
          							+ "AND ac.CR_DR_TYPE              ='"
          							+ cr_dr_indi
          							+ "'\n"
          							+ "AND t.ACCOUNT_TYPE_ID          =ac.BANK_AC_TYPE_ID\n"
          							+ "AND ac.AC_OPERATIONAL_MODE_ID  =m.AC_OPERATIONAL_MODE_ID\n"
          							+ "AND ac.BANK_ID                 =br.BANK_ID\n"
          							+ "AND ac.BRANCH_ID               =br.BRANCH_ID\n"
          							+ "AND ac.BANK_ID                 =bk.BANK_ID\n"
          							+ "AND ac.AC_OPERATIONAL_MODE_ID ='SCH'\n"
          							+ "AND ac.ac_head_code LIKE '%09'\n";
          				}

          			} else {
          				System.out.println("all units::::::::");
          				sql_bank = "select ac.AC_HEAD_CODE,ac.BANK_AC_NO,bk.BANK_NAME,br.BRANCH_NAME ||'-'|| coalesce(br.CITY_TOWN_NAME,'') AS BRANCH_CITY,"
          						+ " ac.BANK_ID,ac.BRANCH_ID,ac.BANK_AC_TYPE_ID, ac.AC_OPERATIONAL_MODE_ID,t.ACCOUNT_TYPE,m.AC_OPERATIONAL_MODE , ac.CR_DR_TYPE,ac.MODULE_ID from "
          						+ " FAS_OFFICE_BANK_AC_CURRENT ac,FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br,FAS_MST_BANK_AC_TYPES t,FAS_MST_AC_OPER_MODES m  where ac.STATUS='Y' and ac.ACCOUNTING_UNIT_ID=? and ac.MODULE_ID=? and ac.CR_DR_TYPE=? "
          						+ " and t.ACCOUNT_TYPE_ID=ac.BANK_AC_TYPE_ID and ac.AC_OPERATIONAL_MODE_ID=m.AC_OPERATIONAL_MODE_ID and ac.BANK_ID=br.BANK_ID and ac.BRANCH_ID=br.BRANCH_ID and ac.BANK_ID=bk.BANK_ID and ac.AC_OPERATIONAL_MODE_ID='"
          						+ unspent_OR_col + "'" +
          						//  " and Ac.Bank_Id = "+txtSubBankId+" AND ac.ac_head_code like '8%5' "+
          						"order by ac.SL_NO desc";
          			}
          		}

          		// here SL_NO=1 not mentioned here for  DEFAULT account number for that unit .. instead it shows all the account numbers
          		System.out.println("test:::::" + sql_bank);
          		ps2 = con.prepareStatement(sql_bank);
          		ps2.setInt(1, cmbAcc_UnitCode);
          		System.out.println("lst****cmbAcc_UnitCode::::"
          				+ cmbAcc_UnitCode);
          		ps2.setString(2, txtModule_Type);
          		System.out.println("2nd txtModule_Type ::::"
          				+ txtModule_Type);
          		ps2.setString(3, cr_dr_indi);
          		System.out.println("3rd cr_dr_indi::::"
          				+ cr_dr_indi);

          		rs2 = ps2.executeQuery();

          		int rowid = 0;
          		while (rs2.next()) {
          			rowid++;
          			out.println("<tr id=" + rowid + ">");
          			if (y == 0) {
          				out.println("<td><input type='radio' id='choice' name='choice' checked value='"
          						+ rowid + "'/></td>");
          				y = 1;
          			} else
          				out.println("<td><input type='radio' id='choice' name='choice' value='"
          						+ rowid + "'/></td>");

          			out.println("<td align='left'>"
          					+ rs2.getInt("AC_HEAD_CODE") + "</td>");
          			out.println("<td align='left'>" + rs2.getLong("BANK_AC_NO")
          					+ "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("BANK_NAME") + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("BRANCH_CITY") + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("ACCOUNT_TYPE") + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("AC_OPERATIONAL_MODE") + "</td>");
          			out.println("<td align='left'>"
          					+ rs2.getString("CR_DR_TYPE") + "</td>");
          			out.println("<td align='left'>" + rs2.getInt("BANK_ID")
          					+ "</td>");
          			out.println("<td align='left'>" + rs2.getInt("BRANCH_ID")
          					+ "</td></tr>");
          			
          			
          			String AC_OPERATIONAL_MODE_ID=rs2.getString("AC_OPERATIONAL_MODE_ID");
          			System.out.println("AC_OPERATIONAL_MODE_ID--->"+AC_OPERATIONAL_MODE_ID);
          			
          			
          			
          		}
          		if (y != 0) {
          			rs2.close();
          			ps2.close();
          		} else
          			out.println("<tr><td align='left'>No data found</td><td></td><td></td><td></td><td></td><td></td></tr>");

          	} catch (Exception e) {
          		System.out.println("Exception in grid.." + e);
          	}
          %>
         </tbody>
        </table>
     
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick="btnsubmit()">
         <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
       </div>
    </td></tr></table>
    </form></body>
</html>