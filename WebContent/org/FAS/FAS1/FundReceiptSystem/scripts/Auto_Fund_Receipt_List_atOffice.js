var j=0;
var seq=1;
var items1;
var items2;
var items3;
var items4;
var items5;
var items6;
var items7;
var items9;
var items10;


/** Get Browser Type */

function getTransport()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}



/**
 *  Main Function 
 */  
function doFunction(Command,param)
{  
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    if(Command=="searchByMonth")
    {  
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
        if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
        {
            var url="../../../../../Auto_Fund_Receipt_List_atOffice?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
            "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
           
        }
    }       
    else if(Command=="searchByDate")
    {  
        var txtFrom_date=document.getElementById("txtFrom_date").value;
        var txtTo_date=document.getElementById("txtTo_date").value;
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
      
        if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && txtFrom_date.length!=0 && txtTo_date.length!=0)
        {
           var from_dat=txtFrom_date.split("/");
           var from_day=from_dat[0];
           var from_mon=from_dat[1];
           var from_year=from_dat[2];
           alert("date is"+from_day+","+from_mon+","+from_year);
           
           var to_dat=txtTo_date.split("/");
           var to_day=to_dat[0];
           var to_mon=to_dat[1];
           var to_year=to_dat[2];
           alert("to date is"+to_day+","+to_mon+","+to_year);
           
           if(to_year<from_year)
           {
           alert("To Date Should Be Greater Than From Date");
           document.frmauto_Fund.txtTo_date.value="";
           return false;
           }
           else if((to_year>=from_year))
           {
               
                if(to_mon<from_mon)
                {
                     alert("To Date Should Be Greater Than From Date");
                     document.frmauto_Fund.txtTo_date.value="";
                     return false;
                }
                else if((to_mon>=from_mon))
                {
                   
                    if(to_day<from_day)
                    { 
                        alert("To Date Should Be Greater Than From Date");
                       document.frmauto_Fund.txtTo_date.value="";
                        return false;
                    }
               
                }               
            }
           
            var url="../../../../../Auto_Fund_Receipt_List_atOffice?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
            "&cmbOffice_code="+cmbOffice_code+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
           // alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
           
        }
    }       
}




/** 
 *  Response Handle 
 */ 
function handleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            //alert(Command);
            if(Command=="searchByMonth")
            {
                loadTable(baseResponse);
            }
             else if(Command=="searchByDate")
            {
                loadTable(baseResponse);
            } 
        }
    }
}



function  loadTable(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var tbody=document.getElementById("tbody");
    try
    {
         tbody.innerHTML="";
    }
    catch(e){
         tbody.innerText="";
    }
    if(flag=="success")
    {
        var service=baseResponse.getElementsByTagName("details");
        if(service)
        { 
            var i=0;
            
            s=0;
            items1=new Array();
            items2=new Array();
            items3=new Array();
            items4=new Array();
            items5=new Array();
            items6=new Array();
            items7=new Array();
            items8=new Array();
            items9=new Array();
            items10=new Array();
            items11=new Array();
            items12=new Array();
            items13=new Array();
            items14=new Array();
            items15=new Array();
            items16=new Array();
            items17=new Array();
            items18=new Array();
            items19=new Array();
            items20=new Array();
            items21=new Array();
            items22=new Array();
            items23=new Array();
            for(i=0;i<service.length;i++)
            {
            
                var items=new Array();
                items1[i]=service[i].getElementsByTagName("fund_type")[0].firstChild.nodeValue;
                items2[i]=service[i].getElementsByTagName("dat_of_trans")[0].firstChild.nodeValue;
                items3[i]=service[i].getElementsByTagName("ho_ref_no")[0].firstChild.nodeValue;
                items4[i]=service[i].getElementsByTagName("ho_ref_date")[0].firstChild.nodeValue;               
                items5[i]=service[i].getElementsByTagName("cheque_dd_no")[0].firstChild.nodeValue;
                items6[i]=service[i].getElementsByTagName("cheque_dd_date")[0].firstChild.nodeValue;
                items7[i]=service[i].getElementsByTagName("amount")[0].firstChild.nodeValue;
                items8[i]=service[i].getElementsByTagName("particulars")[0].firstChild.nodeValue;
                items12[i]=service[i].getElementsByTagName("voucher_no")[0].firstChild.nodeValue;
                items13[i]=service[i].getElementsByTagName("sl_no")[0].firstChild.nodeValue;
                items14[i]=service[i].getElementsByTagName("account_head_code")[0].firstChild.nodeValue;
                items15[i]=service[i].getElementsByTagName("office_bank_id")[0].firstChild.nodeValue;
                items16[i]=service[i].getElementsByTagName("office_branch_id")[0].firstChild.nodeValue;
                items17[i]=service[i].getElementsByTagName("office_account_no")[0].firstChild.nodeValue;
                items18[i]=service[i].getElementsByTagName("total_amount")[0].firstChild.nodeValue;
                items19[i]=service[i].getElementsByTagName("ho_bank_id")[0].firstChild.nodeValue;
                items20[i]=service[i].getElementsByTagName("ho_branch_id")[0].firstChild.nodeValue;
                items21[i]=service[i].getElementsByTagName("ho_account_no")[0].firstChild.nodeValue;
                items22[i]=service[i].getElementsByTagName("cheque_or_dd")[0].firstChild.nodeValue;
                items23[i]=service[i].getElementsByTagName("AC_OPERATIONAL_MODE_ID")[0].firstChild.nodeValue;
                
              //  items23[i]=service[i].getElementsByTagName("autostatus")[0].firstChild.nodeValue;
//                alert("sssssss"+items23[i]);
//                alert("ssssss========="+items21[i]);
                if((items4[i]=='-') ||(items4[i]==""))
                {
                    items10[i]=" ";
                }
                else
                items10[i]=items4[i];
                items11[i]=false;
                
                
                var tbody=document.getElementById("tbody");
                var mycurrent_row=document.createElement("TR");
                
                j++;
                
                //------------------------------TD Row 1 ---------------------------------------------//
                
                /** Check box Display for Receipt Selection */
             /*   var descell=document.createElement("TD");
                var chcksel="";
                  if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
                   {
                        chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+j+"' >");
                   }
                   else
                   {
                       var chcksel=document.createElement("input");
                       chcksel.type="checkbox";
                       chcksel.name="chckparameter";
                       chcksel.id="chckparameter";
                       chcksel.value=j;                      
                   }                  
                descell.appendChild(chcksel);
                mycurrent_row.appendChild(descell);
                */
                
                
                //---------------------------TD Row 1.1 ------------------------------------------------// 
                //Lakshmi 8Nov13
                /// jonn change on 06/04/2014
                //alert("AC_OPERATIONAL_MODE_ID >>> "+items23[i]);
                var cell_1=document.createElement("TD"); 
               // alert(i+1);
                var slno=document.createTextNode(i+1);
            	// var Off_Dr_AccHeadCode=document.createTextNode(items14[i]);
                  cell_1.appendChild(slno);
                  mycurrent_row.appendChild(cell_1);
                  
                  
                var cell_1_1=document.createElement("TD"); 
                if( items23[i]=='FDW'){
                	    
                      var Off_Dr_AccHeadCode=document.createTextNode(items14[i]+"07");
                	// var Off_Dr_AccHeadCode=document.createTextNode(items14[i]);
                      cell_1_1.appendChild(Off_Dr_AccHeadCode);
                      mycurrent_row.appendChild(cell_1_1);
                }else if(items23[i]=="OPR-NRDWP-Support"){
                	
                	   var Off_Dr_AccHeadCode=document.createTextNode(items14[i]+"60");
                   	// var Off_Dr_AccHeadCode=document.createTextNode(items14[i]);
                         cell_1_1.appendChild(Off_Dr_AccHeadCode);
                         mycurrent_row.appendChild(cell_1_1);
                }else if(items23[i]=="OPR-NRDWP-Main"){
                	   var Off_Dr_AccHeadCode=document.createTextNode(items14[i]+"50");
                   	// var Off_Dr_AccHeadCode=document.createTextNode(items14[i]);
                         cell_1_1.appendChild(Off_Dr_AccHeadCode);
                         mycurrent_row.appendChild(cell_1_1);
                }
                else //if( items23[i]=='FDW')
                	{
                	 // var cell_1_1=document.createElement("TD");     
                      var Off_Dr_AccHeadCode=document.createTextNode(items14[i]+"04");
                	// var Off_Dr_AccHeadCode=document.createTextNode(items14[i]);
                      cell_1_1.appendChild(Off_Dr_AccHeadCode);
                      mycurrent_row.appendChild(cell_1_1);
                    
                }
               
                
                //---------------------------TD Row 1.2 ------------------------------------------------//               
                
                var cell_1_2=document.createElement("TD");  
                if(items23[i]=="OPR-NRDWP-Support" && items21[i]!="332502050000017"){
               
//                	alert("OPR-NRDWP-Support && 332502050000017");
                	// var Off_Cr_AccHeadCode=document.createTextNode(items14[i]+"05");
                var Off_Cr_AccHeadCode=document.createTextNode("820661");
                
                cell_1_2.appendChild(Off_Cr_AccHeadCode);
                mycurrent_row.appendChild(cell_1_2); }
                else if(items23[i]=="OPR-NRDWP-Support" && items21[i]=="332502050000017"){
                    // var Off_Cr_AccHeadCode=document.createTextNode(items14[i]+"05");
                     var Off_Cr_AccHeadCode=document.createTextNode("820663");
//                     alert("OPR-NRDWP-Support && 332502050000017**************");
                     
                     cell_1_2.appendChild(Off_Cr_AccHeadCode);
                     mycurrent_row.appendChild(cell_1_2); }
                else if(items23[i]=="OPR-NRDWP-Main"){
                   // var Off_Cr_AccHeadCode=document.createTextNode(items14[i]+"05");
                	
//                	alert("OPR-NRDWP-Main**************");
                	 var Off_Cr_AccHeadCode=document.createTextNode("822151");
                    cell_1_2.appendChild(Off_Cr_AccHeadCode);
                    mycurrent_row.appendChild(cell_1_2); }
                else{
                	
//                	alert("Else Part**************");
                	var Off_Cr_AccHeadCode=document.createTextNode(items14[i]+"05");
                cell_1_2.appendChild(Off_Cr_AccHeadCode);
                mycurrent_row.appendChild(cell_1_2);
                }
                                
                
                //---------------------------TD Row 1.3 ------------------------------------------------//               
                
                var cell_1_3=document.createElement("TD");     
                var Off_Account_Number=document.createTextNode(items17[i]);
                cell_1_3.appendChild(Off_Account_Number);
                mycurrent_row.appendChild(cell_1_3);
                
                
                //---------------------------TD Row 1.4 ------------------------------------------------//               
                
                var cell_1_4=document.createElement("TD");     
                var Off_Account_Number=document.createTextNode(items21[i]);
                cell_1_4.appendChild(Off_Account_Number);
                mycurrent_row.appendChild(cell_1_4);
                
                
                
                //---------------------------TD Row 2 ------------------------------------------------//                
                
                /** Fund Type */            
                var cell3=document.createElement("TD");
                var funty="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    funty=document.createElement("<INPUT type='hidden' name='fundtype' id='fundtype' value='"+items1[i]+"'>");
                }
                else
                {   
                    funty=document.createElement("input");
                    funty.type="hidden";
                    funty.name="fundtype";
                    funty.id="fundtype";
                    funty.value=items1[i];
                }
                cell3.appendChild(funty);
                
                
                /** Voucher Number */
                var vouchno=document.createElement("input");
                vouchno.type="hidden";
                vouchno.name="voucher_no";
                vouchno.id="voucher_no";
                vouchno.value=items12[i];
                cell3.appendChild(vouchno);
                
                var currentText1=document.createTextNode(items1[i]);
                cell3.appendChild(currentText1);
                mycurrent_row.appendChild(cell3);
                
                
                 //---------------------------TD Row 3 ------------------------------------------------//                
                
                var cell4=document.createElement("TD");
                var dttrans="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    dttrans=document.createElement("<INPUT type='hidden' name='date_trans' id='date_trans' value='"+items2[i]+"'>");
                }
                else
                {   
                    dttrans=document.createElement("input");
                    dttrans.type="hidden";
                    dttrans.name="date_trans";
                    dttrans.id="date_trans";
                    dttrans.value=items2[i];
                }    
                cell4.appendChild(dttrans);
                
                var slno=document.createElement("input");
                slno.type="hidden";
                slno.name="sl_no";
                slno.id="sl_no";
                slno.value=items13[i];
                cell4.appendChild(slno);
                var currentText1=document.createTextNode(items2[i]);
                cell4.appendChild(currentText1);
                mycurrent_row.appendChild(cell4);
                
                
                 //---------------------------TD Row 4 ------------------------------------------------//                                
                
            /*    var cell5=document.createElement("TD");
                var refno="";
                refno=document.createElement("input");
                refno.type="text";
                refno.name="ref_no";
                refno.id="ref_no";
                refno.size="10";
                refno.value=items3[i];
                cell5.appendChild(refno);*/
                
               // var currentText2=document.createTextNode(items3[i]);
               // cell5.appendChild(currentText2);
                
              //  mycurrent_row.appendChild(cell5);
                
                //---------------------------TD Row 5 ------------------------------------------------//                
                
           /*     var cell6=document.createElement("TD");
               var refdt="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    refdt=document.createElement("<INPUT type='text' name='ref_dt' id='ref_dt' size='10' value='"+items10[i]+"'>");
                }
                else
                {   
                    refdt=document.createElement("input");
                    refdt.type="text";
                    refdt.name="ref_dt";
                    refdt.id="ref_dt";
                    refdt.size="10";
                    refdt.value=items10[i];
                }    
                cell6.appendChild(refdt);
             
                
                var accthead_code=document.createElement("input");
                accthead_code.type="hidden";
                accthead_code.name="account_head_code";
                accthead_code.id="account_head_code";
                accthead_code.value=items14[i];
                cell6.appendChild(accthead_code);
                
                var bankid=document.createElement("input");
                bankid.type="hidden";
                bankid.name="bank_id";
                bankid.id="bank_id";
                bankid.value=items15[i];
                cell6.appendChild(bankid);
                
                 var branchid=document.createElement("input");
                branchid.type="hidden";
                branchid.name="branch_id";
                branchid.id="branch_id";
                branchid.value=items16[i];
                cell6.appendChild(branchid);
                
              //  var currentText1=document.createTextNode(items10[i]);
              //  cell6.appendChild(currentText1);
                
              //  mycurrent_row.appendChild(cell6);
                */
                //---------------------------TD Row 6 ------------------------------------------------//                
                
                var cell7=document.createElement("TD");
                var ddno="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    ddno=document.createElement("<INPUT type='hidden' name='dd_no' id'dd_no' value='"+items5[i]+"'>");
                }
                else
                {   
                    ddno=document.createElement("input");
                    ddno.type="hidden";
                    ddno.name="dd_no";
                    ddno.id="dd_no";
                    ddno.value=items5[i];
                }    
                cell7.appendChild(ddno);
                
                 var acctno=document.createElement("input");
                acctno.type="hidden";
                acctno.name="account_no";
                acctno.id="account_no";
                acctno.value=items17[i];
                cell7.appendChild(acctno);
                
                /* var tot_amt=document.createElement("input");
                tot_amt.type="hidden";
                tot_amt.name="total_amount";
                tot_amt.id="total_amount";
                tot_amt.value=items18[i];
                cell7.appendChild(tot_amt);*/
                
                var cheq_dd=document.createElement("input");
                cheq_dd.type="hidden";
                cheq_dd.name="cheque_or_dd";
                cheq_dd.id="cheque_or_dd";
                cheq_dd.value=items22[i];
                cell7.appendChild(cheq_dd);
                
                
                var currentText1=document.createTextNode(items5[i]);
                cell7.appendChild(currentText1);
                mycurrent_row.appendChild(cell7);
                
               //---------------------------TD Row 7 ------------------------------------------------//     
                
                var cell8=document.createElement("TD");
                var dddt="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    dddt=document.createElement("<INPUT type='hidden' name='dd_dt' id'dd_dt' value='"+items6[i]+"'>");
                }
                else
                {   
                    dddt=document.createElement("input");
                    dddt.type="hidden";
                    dddt.name="dd_dt";
                    dddt.id="dd_dt";
                    dddt.value=items6[i];
                }    
                cell8.appendChild(dddt);
                
                  var ho_bnk_id=document.createElement("input");
                ho_bnk_id.type="hidden";
                ho_bnk_id.name="ho_bank_id";
                ho_bnk_id.id="ho_bank_id";
                ho_bnk_id.value=items19[i];
                cell8.appendChild(ho_bnk_id);
                
                  var ho_branch=document.createElement("input");
                ho_branch.type="hidden";
                ho_branch.name="ho_branch_id";
                ho_branch.id="ho_branch_id";
                ho_branch.value=items20[i];
                cell8.appendChild(ho_branch);
                
                  var ho_acct=document.createElement("input");
                ho_acct.type="hidden";
                ho_acct.name="ho_acct_no";
                ho_acct.id="ho_acct_no";
                ho_acct.value=items21[i];
                cell8.appendChild(ho_acct);
                
                  var tot_amt=document.createElement("input");
                tot_amt.type="hidden";
                tot_amt.name="total_amount";
                tot_amt.id="total_amount";
                tot_amt.value=items18[i];
                cell7.appendChild(tot_amt);
                var currentText1=document.createTextNode(items6[i]);
                cell8.appendChild(currentText1);
                mycurrent_row.appendChild(cell8);
                
               //---------------------------TD Row 8 ------------------------------------------------//     
                
                var cell9=document.createElement("TD");
                var amt="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    amt=document.createElement("<INPUT type='hidden' name='amount' id'amount' value='"+items7[i]+"'>");
                }
                else
                {   
                    amt=document.createElement("input");
                    amt.type="hidden";
                    amt.name="amount";
                    amt.id="amount";
                    amt.value=items7[i];
                }    
                cell9.appendChild(amt);
                var currentText1=document.createTextNode(items7[i]);
                cell9.appendChild(currentText1);
                mycurrent_row.appendChild(cell9);
                
                
                //---------------------------TD Row 9 ------------------------------------------------//     
                
                var cell10=document.createElement("TD");
                var parti="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    parti=document.createElement("<INPUT type='text' name='particulars' id'particulars' value='"+items8[i]+"'>");
                }
                else
                {   
                    parti=document.createElement("input");
                    parti.type="text";
                    parti.name="particulars";
                    parti.id="particulars";
                    parti.value=items8[i];
                }    
                cell10.appendChild(parti);
               
              //  var currentText1=document.createTextNode(items8[i]);
              //  cell10.appendChild(currentText1);
                
                mycurrent_row.appendChild(cell10);
                
                
                //---------------------------TD column 10 ------------------------------------------------//     
                
              /*  var cell11=document.createElement("TD");
                var autostatus="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                    autostatus=document.createElement("<INPUT type='hidden' name='autostatus' id'autostatus' value='"+items23[i]+"'>");
                }
                else
                {   
                    autostatus=document.createElement("input");
                    autostatus.type="hidden";
                    autostatus.name="autostatus";
                    autostatus.id="autostatus";
                    autostatus.value=items23[i];
                }    
                cell11.appendChild(autostatus);
                var currentText1=document.createTextNode(items23[i]);
                cell11.appendChild(currentText1);
                mycurrent_row.appendChild(cell11);  */
                
                tbody.appendChild(mycurrent_row);
                seq++;
                
                
            }             
         }
     }
     else
     {
        alert("Record Doesn't Exist");
        
     }
}





function setTrue(index)
{
  items11[index]=true;    
}



/** Number Checking */
function numbersonly(e)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {      
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
}




function btSubmit()
{
	alert(hello);
    var voucher_no="";
    var sl_no="";
    var cmbAcc_UnitCode="";
    var cmbOffice_code="";
    var txtCB_Year="";
    var txtCB_Month="";
    var chcksel="";
    var acct_head_code="";
    var bank_id="";
    var branch_id="";
    var ho_acct_id="";
    var total_amount="";
    var cheque_or_dd="";
    var dd_no="";
    var dd_dt="";
    var ho_bank_id="";
    var ho_branch_id="";
    var account_no="";
    var particulars="";
    var ref_no="";
    var ref_dt="";
    var login_oid="";
    
    var date_trans="";
    
    
    var url1="../../../../../Auto_Fund_Receipt_Create_atOfficeServ?command=submit";  
    
     if(document.frmauto_Fund.chckparameter.length>0)
     {
        for(i=0;i<document.frmauto_Fund.chckparameter.length;i++)
        {
            if(document.frmauto_Fund.chckparameter[i].checked==true)
            {
                voucher_no= voucher_no+document.frmauto_Fund.voucher_no[i].value +",";
                sl_no= sl_no+document.frmauto_Fund.sl_no[i].value +",";
                chcksel= chcksel+document.frmauto_Fund.chckparameter[i].value +",";
                acct_head_code=acct_head_code+document.frmauto_Fund.account_head_code[i].value+",";
                bank_id=bank_id+document.frmauto_Fund.bank_id[i].value+",";
                branch_id=branch_id+document.frmauto_Fund.branch_id[i].value+",";
                account_no=account_no+document.frmauto_Fund.account_no[i].value+",";
                total_amount=total_amount+document.frmauto_Fund.amount[i].value+",";
                cheque_or_dd=cheque_or_dd+document.frmauto_Fund.cheque_or_dd[i].value+",";
                dd_no=dd_no+document.frmauto_Fund.dd_no[i].value+",";
                dd_dt=dd_dt+document.frmauto_Fund.dd_dt[i].value+",";
                ho_bank_id=ho_bank_id+document.frmauto_Fund.ho_bank_id[i].value+",";
                ho_branch_id=ho_branch_id+document.frmauto_Fund.ho_branch_id[i].value+",";
                ho_acct_id=ho_acct_id+document.frmauto_Fund.ho_acct_no[i].value+",";
                particulars=particulars+document.frmauto_Fund.particulars[i].value+",";
                ref_no=ref_no+document.frmauto_Fund.ref_no[i].value+",";
                ref_dt=ref_dt+document.frmauto_Fund.ref_dt[i].value+",";
                
                date_trans=date_trans+document.frmauto_Fund.date_trans[i].value+",";
                
            }
        }
        if(voucher_no!="")
        {
            voucher_no=voucher_no.substring(0,voucher_no.length-1); 
            url1=url1+"&voucher_no="+voucher_no;
        }
        if(sl_no!="")
        {
            sl_no=sl_no.substring(0,sl_no.length-1); 
            url1=url1+"&sl_no="+sl_no;
        }
        if(chcksel!="")
        {
            chcksel=chcksel.substring(0,chcksel.length-1); 
            url1=url1+"&chcksel="+chcksel;
        }
         if(bank_id!="")
        {
            bank_id=bank_id.substring(0,bank_id.length-1); 
            url1=url1+"&bank_id="+bank_id;
        }
         if(account_no!="")
        {
            account_no=account_no.substring(0,account_no.length-1); 
            url1=url1+"&account_no="+account_no;
        }
         if(branch_id!="")
        {
            branch_id=branch_id.substring(0,branch_id.length-1); 
            url1=url1+"&branch_id="+branch_id;
        }
         if(total_amount!="")
        {
            total_amount=total_amount.substring(0,total_amount.length-1); 
            url1=url1+"&total_amount="+total_amount;
        }
         if(cheque_or_dd!="")
        {
            cheque_or_dd=cheque_or_dd.substring(0,cheque_or_dd.length-1); 
            url1=url1+"&cheque_or_dd="+cheque_or_dd;
        }
         if(dd_no!="")
        {
            dd_no=dd_no.substring(0,dd_no.length-1); 
            url1=url1+"&dd_no="+dd_no;
        }
          if(dd_dt!="")
        {
            dd_dt=dd_dt.substring(0,dd_dt.length-1); 
            url1=url1+"&dd_dt="+dd_dt;
        }
        
          if(ho_bank_id!="")
        {
            ho_bank_id=ho_bank_id.substring(0,ho_bank_id.length-1); 
            url1=url1+"&ho_bank_id="+ho_bank_id;
        }
        
          if(ho_branch_id!="")
        {
            ho_branch_id=ho_branch_id.substring(0,ho_branch_id.length-1); 
            url1=url1+"&ho_branch_id="+ho_branch_id;
        }
        
          if(ho_acct_id!="")
        {
            ho_acct_id=ho_acct_id.substring(0,ho_acct_id.length-1); 
            url1=url1+"&ho_acct_id="+ho_acct_id;
        }
        
         if(ref_no!="")
        {
            ref_no=ref_no.substring(0,ref_no.length-1); 
            url1=url1+"&ref_no="+ref_no;
        }
         if(ref_dt!="")
        {
            ref_dt=ref_dt.substring(0,ref_dt.length-1); 
            url1=url1+"&ref_dt="+ref_dt;
        }
        
        if(particulars!="")
        {
            particulars=particulars.substring(0,particulars.length-1); 
            url1=url1+"&particulars="+particulars;
        }
        
        if(acct_head_code!="")
        {
            acct_head_code=acct_head_code.substring(0,acct_head_code.length-1); 
           // acct_head_code=acct_head_code.subString(0,4);
           // alert(acct_head_code);
            url1=url1+"&acct_head_code="+acct_head_code;
        }
        
        
        if(date_trans!="")
        {
            date_trans=date_trans.substring(0,date_trans.length-1); 
            url1=url1+"&date_trans="+date_trans;
        }
        
        
        cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        url1=url1+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        cmbOffice_code=document.getElementById("cmbOffice_code").value;
        url1=url1+"&cmbOffice_code="+cmbOffice_code;
        login_oid=document.getElementById("txtOffid").value;
        url1=url1+"&login_oid="+login_oid;
        txtCB_Year=document.getElementById("txtCB_Year").value;
        url1=url1+"&txtCB_Year="+txtCB_Year;
        txtCB_Month=document.getElementById("txtCB_Month").value;
        url1=url1+"&txtCB_Month="+txtCB_Month;
    }
    else
    {
         voucher_no= document.frmauto_Fund.voucher_no.value;
         url1=url1+"&voucher_no="+voucher_no;
         sl_no= document.frmauto_Fund.sl_no.value;
         url1=url1+"&sl_no="+sl_no;
         chcksel= document.frmauto_Fund.chckparameter.value;
         url1=url1+"&chcksel="+chcksel;
         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;  
         url1=url1+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
         cmbOffice_code=document.getElementById("cmbOffice_code").value;
         url1=url1+"&cmbOffice_code="+cmbOffice_code;
         login_oid=document.getElementById("txtOffid").value;
         url1=url1+"&login_oid="+login_oid;
         txtCB_Year=document.getElementById("txtCB_Year").value;
         url1=url1+"&txtCB_Year="+txtCB_Year;
         txtCB_Month=document.getElementById("txtCB_Month").value;
         url1=url1+"&txtCB_Month="+txtCB_Month;
         acct_head_code=document.frmauto_Fund.account_head_code.value;
       
      
         
         bank_id=document.frmauto_Fund.bank_id.value;
         url1=url1+"&bank_id="+bank_id;
         branch_id=document.frmauto_Fund.branch_id.value;
         url1=url1+"&branch_id="+branch_id;
         account_no=document.frmauto_Fund.account_no.value;
         url1=url1+"&account_no="+account_no;
         total_amount=document.frmauto_Fund.amount.value;
         url1=url1+"&total_amount="+total_amount;
         cheque_or_dd=document.frmauto_Fund.cheque_or_dd.value;
         url1=url1+"&cheque_or_dd="+cheque_or_dd;
         dd_no=document.frmauto_Fund.dd_no.value;
         url1=url1+"&dd_no="+dd_no;
         dd_dt=document.frmauto_Fund.dd_dt.value;
         url1=url1+"&dd_dt="+dd_dt;
         ho_bank_id=document.frmauto_Fund.ho_bank_id.value;
          url1=url1+"&ho_bank_id="+ho_bank_id;
         ho_branch_id=document.frmauto_Fund.ho_branch_id.value;
          url1=url1+"&ho_branch_id="+ho_branch_id;
         ho_acct_id=document.frmauto_Fund.ho_acct_no.value;
          url1=url1+"&ho_acct_id="+ho_acct_id;
         particulars=document.frmauto_Fund.particulars.value;
          url1=url1+"&particulars="+particulars;
          ref_no=document.frmauto_Fund.ref_no.value;
          url1=url1+"&ref_no="+ref_no;
          ref_dt=document.frmauto_Fund.ref_dt.value;
            url1=url1+"&ref_dt="+ref_dt;
    }
    
    document.frmauto_Fund.action=url1;
    
    
    /** Check Receipt Date */
    if (document.getElementById("txtCrea_date").value=="") 
    {
      alert("Please Enter Receipt Date");
      return false;
    }
    
    /** Final Form Posting */
    document.frmauto_Fund.method="post"; 
    document.frmauto_Fund.submit(); 
    
}
     
     
     
     
   


function LoadOffice(unitID_val)
{

    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Auto_Fund_Receipt_List_atOffice?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        }
        req.send();
    }
}

function handle_loadOffice(req)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {
       
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];       
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
          
            var offidvalues=baseresponse.getElementsByTagName("offid");            
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname;
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
        }
        else
        {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
        }
            
             
     }
    }
}



/** Combo Selection */
function selectAll(Opt)
{

  var len=  document.getElementById("tbody").rows.length;
  
  if(len==1)
  {
          if ( Opt =="ALL")
          {
             document.getElementById("chckparameter").checked=true;
          }
          else if (Opt=="UNSelect" )
          {
             document.getElementById("chckparameter").checked=false;
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.frmauto_Fund.chckparameter[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.frmauto_Fund.chckparameter[i].checked=false;
                }
          }
  }
}








///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)    
{
    if(blr_flag==1)      
    {
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;  
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req,fromcal_dateCtrl);
                 }   
                 req.send(null);
            }
    }
}


function call_date(dateCtrl)                        
{
    if(checkdt(dateCtrl))
    {
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
         var TB_date=dateCtrl.value;
       
         if(dateCtrl.value.length!=0)
         {
             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
               check_TB(req,dateCtrl);
             }   
             req.send(null);
       }       
    }
    else
    {
      document.getElementById("txtCrea_date").value="";
    }
}



function check_TB(req,dateCtrl)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
              {
                 
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";
               }
             else if(flag=="finyear")
               {
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";     
               }
        }
    }
}
