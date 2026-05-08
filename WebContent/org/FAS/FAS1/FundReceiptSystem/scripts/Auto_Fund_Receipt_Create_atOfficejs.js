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
        
        
        var vdate=document.getElementById("txtCrea_date").value;
    	var mytool_array=vdate.split("/");
    	
    	
    	
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
       var fl=0; 
    //   alert(txtCB_Year+":"+mytool_array[2]);
        if(parseInt(txtCB_Year)>parseInt(mytool_array[2]))
        {
        	alert('Receipt Date should greater than or equal to Cash Book Month and year ');
    		return false;
    		fl=1;
        }
        if(parseInt(txtCB_Year)==parseInt(mytool_array[2]))
        {
       // alert(parseInt(mytool_array[1])+"::"+parseInt(txtCB_Month));
        	if(mytool_array[1]<txtCB_Month)
        	{
        		alert('Receipt Date should greater than or equal to Cash Book Month and year****');
        		return false;
        		fl=1;
        	}
        }  
        
        
        
        
        if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && fl==0)
        {
            var url="../../../../../Auto_Fund_Receipt_Create_atOfficeServ?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+
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
        //   alert("date is"+from_day+","+from_mon+","+from_year);
           
           var to_dat=txtTo_date.split("/");
           var to_day=to_dat[0];
           var to_mon=to_dat[1];
           var to_year=to_dat[2];
       //    alert("to date is"+to_day+","+to_mon+","+to_year);
           
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
           
            var url="../../../../../Auto_Fund_Receipt_Create_atOfficeServ?Command=searchByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+
            "&cmbOffice_code="+cmbOffice_code+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
         //   alert(url);
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
         //   alert("baseResponse"+baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
        //    alert("Command"+Command);
            if(Command=="searchByMonth")
            {
                loadTable(baseResponse);
            }
             else if(Command=="searchByDate")
            {
            	// alert("dat");
                loadTable(baseResponse);
            } 
        }
    }
}



function  loadTable(baseResponse)
{
	//alert("date");
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
            items24=new Array();
            items25=new Array();
            for(i=0;i<service.length;i++)
            {
            
                var items=new Array();
                items1[i]=service[i].getElementsByTagName("fund_type")[0].firstChild.nodeValue;
                items2[i]=service[i].getElementsByTagName("dat_of_trans")[0].firstChild.nodeValue;
                if(items3[i]=service[i].getElementsByTagName("ho_ref_no")[0]=null)
                items3[i]=service[i].getElementsByTagName("ho_ref_no")[0].firstChild.nodeValue;
                // alert("ttttttttttt"+items3[i]);
                items4[i]=service[i].getElementsByTagName("ho_ref_date")[0].firstChild.nodeValue;               
                items5[i]=service[i].getElementsByTagName("cheque_dd_no")[0].firstChild.nodeValue;
                items6[i]=service[i].getElementsByTagName("cheque_dd_date")[0].firstChild.nodeValue;
                items7[i]=service[i].getElementsByTagName("amount")[0].firstChild.nodeValue;
                if(items8[i]=service[i].getElementsByTagName("particulars")[0]=null)
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
                items23[i]=service[i].getElementsByTagName("another_dr_head")[0].firstChild.nodeValue;
                items24[i]=service[i].getElementsByTagName("office_another_bank")[0].firstChild.nodeValue;
                items25[i]=service[i].getElementsByTagName("operMode")[0].firstChild.nodeValue;
               // alert(items25[i]);
              //  alert(items25[i]);
                
                if((items4[i]=='-') ||(items4[i]==""))
                {
                    items10[i]=" ";
                }
                else
                items10[i]=items4[i];
                items11[i]=false;
                
               
                var tbody=document.getElementById("tbody");
                //alert(tbody);
                var mycurrent_row=document.createElement("TR");
                
                j++;
                
                //------------------------------TD Row 1 ---------------------------------------------//
                
                /** Check box Display for Receipt Selection */
                var descell=document.createElement("TD");
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
               // alert("items23[i] "+items23[i]);
                
                var cell_1_1 = document.createElement("TD");
                var Off_Dr_AccHeadCode = document.createElement("select");
                Off_Dr_AccHeadCode.name="Off_Dr_AccHeadCode";
                Off_Dr_AccHeadCode.id="Off_Dr_AccHeadCode";
                Off_Dr_AccHeadCode.setAttribute('onChange',"acc_change()");
                if(items23[i]!=0)
                {
		                for(var k=0;k<2;k++)
		                {
		                	//alert("k"+k);
			                if(k==0)
			                {
			                	
			                	if(items25[i]=="OPR-NRDWP-Main"){
			                		var opt = document.createElement("option");
			                		opt.value =items14[i]+"50/"+items17[i];
			                        var txt = document.createTextNode(items14[i]+"50");
			                        opt.appendChild(txt);
			                        Off_Dr_AccHeadCode.appendChild(opt);
			                        cell_1_1.appendChild(Off_Dr_AccHeadCode);
			                        mycurrent_row.appendChild(cell_1_1);
			                	}
			                	 else if(items25[i]=="OPR-NRDWP-Support"){
			                		 var opt = document.createElement("option");
				                		opt.value =items14[i]+"60/"+items17[i];
				                        var txt = document.createTextNode(items14[i]+"60");
				                        opt.appendChild(txt);
				                        Off_Dr_AccHeadCode.appendChild(opt);
				                        cell_1_1.appendChild(Off_Dr_AccHeadCode);
				                        mycurrent_row.appendChild(cell_1_1);
			                	 }else{
			                		var opt = document.createElement("option");
			                		opt.value =items14[i]+"04/"+items17[i];
			                        var txt = document.createTextNode(items14[i]+"04");
			                        opt.appendChild(txt);
			                        Off_Dr_AccHeadCode.appendChild(opt);
			                        cell_1_1.appendChild(Off_Dr_AccHeadCode);
			                        mycurrent_row.appendChild(cell_1_1);
			                	 }
			                }
			                if(k==1)
			                {
			                	
			                	if(items25[i]=="OPR-NRDWP-Main"){
			                		var opt = document.createElement("option");
			                		opt.value =items14[i]+"50/"+items24[i];
			                        var txt = document.createTextNode(items14[i]+"50");
			                        opt.appendChild(txt);
			                        Off_Dr_AccHeadCode.appendChild(opt);
			                        cell_1_1.appendChild(Off_Dr_AccHeadCode);
			                        mycurrent_row.appendChild(cell_1_1);
			                	}
			                	 else if(items25[i]=="OPR-NRDWP-Support"){
			                		 var opt = document.createElement("option");
				                		opt.value =items14[i]+"60/"+items24[i];
				                        var txt = document.createTextNode(items14[i]+"60");
				                        opt.appendChild(txt);
				                        Off_Dr_AccHeadCode.appendChild(opt);
				                        cell_1_1.appendChild(Off_Dr_AccHeadCode);
				                        mycurrent_row.appendChild(cell_1_1);
			                	 }else{
			                		var opt = document.createElement("option");
			                		opt.value =items23[i]+"/"+items24[i];
			                        var txt = document.createTextNode(items23[i]);
			                        opt.appendChild(txt);
			                        Off_Dr_AccHeadCode.appendChild(opt);
			                        cell_1_1.appendChild(Off_Dr_AccHeadCode);
			                        mycurrent_row.appendChild(cell_1_1);
			                	 }
			                }
		                }
                }
                else
                {
                	 if(items25[i]=="OPR-NRDWP-Main"){
                		 var opt = document.createElement("option");
                 		opt.value =items14[i]+"50/"+items17[i];
                         var txt = document.createTextNode(items14[i]+"50");
                         opt.appendChild(txt);
                         Off_Dr_AccHeadCode.appendChild(opt);
                         cell_1_1.appendChild(Off_Dr_AccHeadCode);
                         mycurrent_row.appendChild(cell_1_1);
                	 }
                	 else if(items25[i]=="OPR-NRDWP-Support"){
                		 var opt = document.createElement("option");
                 		opt.value =items14[i]+"60/"+items17[i];
                         var txt = document.createTextNode(items14[i]+"60");
                         opt.appendChild(txt);
                         Off_Dr_AccHeadCode.appendChild(opt);
                         cell_1_1.appendChild(Off_Dr_AccHeadCode);
                         mycurrent_row.appendChild(cell_1_1);
                	 }
                	 else{
                	var opt = document.createElement("option");
            		opt.value =items14[i]+"04/"+items17[i];
                    var txt = document.createTextNode(items14[i]+"04");
                    opt.appendChild(txt);
                    Off_Dr_AccHeadCode.appendChild(opt);
                    cell_1_1.appendChild(Off_Dr_AccHeadCode);
                    mycurrent_row.appendChild(cell_1_1);
                    
                	 }
                }
                
                
                //---------------------------TD Row 1.2 ------------------------------------------------//               
                
                
                if(items25[i]=="OPR-NRDWP-Main"){
                	 var cell_1_2=document.createElement("TD"); 
                	 var cr_head=document.createElement("input");
                	 cr_head.type="hidden";
                	 cr_head.id="Off_Cr_AccHeadCode";
                	 cr_head.value="822151";
                     var Off_Cr_AccHeadCode=document.createTextNode("822151");
                     cell_1_2.appendChild(Off_Cr_AccHeadCode);
                     cell_1_2.appendChild(cr_head);
                     mycurrent_row.appendChild(cell_1_2);
                	
                }else if(items25[i]=="OPR-NRDWP-Support" && items21[i] != "332502050000017"){
               	 var cell_1_2=document.createElement("TD"); 
               	 var cr_head=document.createElement("input");
            	 cr_head.type="hidden";
            	 cr_head.id="Off_Cr_AccHeadCode";
            	 cr_head.value="820661";
                 var Off_Cr_AccHeadCode=document.createTextNode("820661");
                 cell_1_2.appendChild(Off_Cr_AccHeadCode);
                 cell_1_2.appendChild(cr_head);
                 mycurrent_row.appendChild(cell_1_2);
            	
                }else if(items25[i]=="OPR-NRDWP-Support" && items21[i] == "332502050000017"){
                  	 var cell_1_2=document.createElement("TD"); 
                   	 var cr_head=document.createElement("input");
                	 cr_head.type="hidden";
                	 cr_head.id="Off_Cr_AccHeadCode";
                	 cr_head.value="820663";
                     var Off_Cr_AccHeadCode=document.createTextNode("820663");
                     cell_1_2.appendChild(Off_Cr_AccHeadCode);
                     cell_1_2.appendChild(cr_head);
                     mycurrent_row.appendChild(cell_1_2);
                	
                    }
                else{
                var cell_1_2=document.createElement("TD");  
                var cr_head=document.createElement("input");
           	 cr_head.type="hidden";
           	 cr_head.id="Off_Cr_AccHeadCode";
           	 cr_head.value="822151";
                var Off_Cr_AccHeadCode=document.createTextNode(items14[i]+"05");
                cell_1_2.appendChild(Off_Cr_AccHeadCode);
                cell_1_2.appendChild(cr_head);
                mycurrent_row.appendChild(cell_1_2);
                
                }
                
                
                
                
                
                
                
                //Office Bank Account Number
                var cell_1_1 = document.createElement("TD");
                var office_bank_acNumber = document.createElement("select");
                office_bank_acNumber.name="office_bank_acNumber";
                office_bank_acNumber.id="office_bank_acNumber";
                var opt = document.createElement("option");
        		opt.value =items17[i];
                var txt = document.createTextNode(items17[i]);
                opt.appendChild(txt);
                office_bank_acNumber.appendChild(opt);
                cell_1_1.appendChild(office_bank_acNumber);
                var mode=document.createElement("input");
                mode.type="hidden";
                mode.id="mode";
               // alert(items25[i]);
                mode.value=items25[i];
                cell_1_1.appendChild(mode);
                mycurrent_row.appendChild(cell_1_1);
                
                mycurrent_row.appendChild(cell_1_1);
                
               
               
              /*  var cell_1_3=document.createElement("TD");     
                var office_bank_acNumber=document.createTextNode(items17[i]);
                cell_1_3.appendChild(office_bank_acNumber);
                mycurrent_row.appendChild(cell_1_3);  */
                 
                
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
                
                
            /*    var chequ="";
                if(items22[i]=="D")
                	chequ="DD";
                else if(items22[i]=="C")
                	chequ="Cheque";
                else if(items22[i]=="E")
                	chequ="ECS"
                var cell_1_11=document.createElement("TD");   
                               
                var Off_Account_Number=document.createTextNode(chequ);
                cell_1_11.appendChild(Off_Account_Number);
                mycurrent_row.appendChild(cell_1_11);  */
                
                
         
    var cell_1_11 = document.createElement("TD");
    var Off_Account_Number = document.createElement("select");
    Off_Account_Number.name="Off_Account_Number";
    Off_Account_Number.id="Off_Account_Number";
  //  Off_Account_Number.type="hidden";
    for(var k=0;k<3;k++)
    {
    if(k==0)
    {
    	
    		var opt = document.createElement("option");
    		opt.value = "E";
               // items22[i]="E";
             //  cheque_or_dd.value="E";
            var txt = document.createTextNode("ECS");
            opt.appendChild(txt);
            Off_Account_Number.appendChild(opt);
            cell_1_11.appendChild(Off_Account_Number);
            mycurrent_row.appendChild(cell_1_11);
    }
    else if(k==1)
    {
    		
    		var opt = document.createElement("option");
    		opt.value = "D";
              //   items22[i]="D";
              // cheque_or_dd.value="D";
            var txt = document.createTextNode("DD");
            opt.appendChild(txt);
            Off_Account_Number.appendChild(opt); 
            cell_1_11.appendChild(Off_Account_Number);
            mycurrent_row.appendChild(cell_1_11);
    }
    else if(k==2)
    {
    	
    		var opt = document.createElement("option");
    		opt.value = "C";
               //  items22[i]="C";
             //    cheque_or_dd.value="C";
            var txt = document.createTextNode("Cheque");
            opt.appendChild(txt);
            Off_Account_Number.appendChild(opt); 
            cell_1_11.appendChild(Off_Account_Number);
            mycurrent_row.appendChild(cell_1_11);
    }
   
    }
 
                 //---------------------------TD Row 4 ------------------------------------------------//                                
                
                var cell5=document.createElement("TD");
                var refno="";
                refno=document.createElement("input");
                refno.type="text";
                refno.name="ref_no";
                refno.id="ref_no";
                refno.size="10";
                refno.value=items3[i];
                cell5.appendChild(refno);
                
               // var currentText2=document.createTextNode(items3[i]);
               // cell5.appendChild(currentText2);
                
                mycurrent_row.appendChild(cell5);
                
                //---------------------------TD Row 5 ------------------------------------------------//                
                
                var cell6=document.createElement("TD");
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
                
                mycurrent_row.appendChild(cell6);
                
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
             //     alert(":::"+items22[i]);
                
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
                cell9.align="right";
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
                    parti=document.createElement("<INPUT type='text' name='particulars' id'particulars' size='250' value='"+items8[i]+"'>");
                }
                else
                {   
                    parti=document.createElement("input");
                    parti.type="text";
                    parti.size="250";
                    parti.name="particulars";
                    parti.id="particulars";
                    parti.value=items8[i];
                }    
                cell10.appendChild(parti);
               
//                var currentText1=document.createTextNode(items8[i]);
//                cell10.appendChild(currentText1);                
                mycurrent_row.appendChild(cell10);
                
                
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


function acc_change()
{
//	alert("works");
var off_dr=(document.getElementById("Off_Dr_AccHeadCode").value).split("/");
//	alert(off_dr[1]);
document.getElementById("office_bank_acNumber").options[document.getElementById("office_bank_acNumber").selectedIndex].value=off_dr[1];
document.getElementById("office_bank_acNumber").options[document.getElementById("office_bank_acNumber").selectedIndex].text=off_dr[1];
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
	alert('test');
// alert("length:::"+document.frmauto_Fund.chckparameter.length);
 //alert("value:::"+document.frmauto_Fund.chckparameter.value);

    var proceed=0;
    var voucher_no="";
    var sl_no="";
    var cmbAcc_UnitCode="";
    var cmbOffice_code="";
    var txtCrea_date="";
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
    var mode="";var Off_Cr_AccHeadCode="";
    var date_trans="";
    
    var Off_Account_Number="";
    var Off_Dr_AccHeadCode="";
    
    var url1="../../../../../Auto_Fund_Receipt_Create_atOfficeServ?command=submit";  
   
   //alert(document.frmauto_Fund.chckparameter.length);
 
     if(document.frmauto_Fund.chckparameter.length>0)
     {
  //alert("comes");
        for(i=0;i<document.frmauto_Fund.chckparameter.length;i++)
        { 
            if(document.frmauto_Fund.chckparameter[i].checked==true)
            {
             
                voucher_no= voucher_no+document.frmauto_Fund.voucher_no[i].value +",";
                sl_no= sl_no+document.frmauto_Fund.sl_no[i].value +",";
                chcksel= chcksel+document.frmauto_Fund.chckparameter[i].value +",";
                acct_head_code=acct_head_code+document.frmauto_Fund.account_head_code[i].value+",";
               // Off_Dr_AccHeadCode=Off_Dr_AccHeadCode+document.frmauto_Fund.Off_Dr_AccHeadCode[i].value+",";
              
                // url1=url1+"&Off_Dr_AccHeadCode="+Off_Dr_AccHeadCode;
               // Off_Dr_AccHeadCode=document.frmauto_Fund.Off_Dr_AccHeadCode.value;
               // Off_Cr_AccHeadCode=document.frmauto_Fund.Off_Cr_AccHeadCode.value;
              //url1=url1+"&Off_Cr_AccHeadCode="+Off_Cr_AccHeadCode;
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
//                alert('test >>>>>>  '+particulars);
//alert(hello);
                ref_no=ref_no+document.frmauto_Fund.ref_no[i].value+",";
                ref_dt=ref_dt+document.frmauto_Fund.ref_dt[i].value+",";
                
                date_trans=date_trans+document.frmauto_Fund.date_trans[i].value+",";
                Off_Account_Number=Off_Account_Number+document.frmauto_Fund.Off_Account_Number[i].value+",";
                Off_Dr_AccHeadCode=Off_Dr_AccHeadCode+document.frmauto_Fund.Off_Dr_AccHeadCode[i].value+",";
                Off_Cr_AccHeadCode=Off_Cr_AccHeadCode+document.frmauto_Fund.Off_Cr_AccHeadCode[i].value+",";
             
                mode=mode+document.frmauto_Fund.mode[i].value+",";
            //    alert(Off_Account_Number);
             // alert("mode"+mode);
                if(ref_no==" ,")
                {
                alert("Enter RefNo");
                return false;
                }
              else  if(ref_dt==" ,")
                {
                alert("Enter RefDate");
                 return false;
                }
            
                proceed++;
            }
            
        }
        if(proceed==0)
        {
        alert("Please Select atleast one row by clicking the checkBox");
        return false;
        }
         
        
        if(voucher_no!="")
        {
      //  alert(voucher_no.length);
            voucher_no=voucher_no.substring(0,voucher_no.length-1); 
       //     alert("voucher_no::"+voucher_no);
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
        }  if(mode!="")
        {
            mode=mode.substring(0,mode.length-1); 
            url1=url1+"&mode="+mode;
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
      /*   if(cheque_or_dd!="")
        {
            cheque_or_dd=cheque_or_dd.substring(0,cheque_or_dd.length-1); 
            url1=url1+"&cheque_or_dd="+cheque_or_dd;
        }  */
         if(Off_Account_Number!="")
        {
            Off_Account_Number=Off_Account_Number.substring(0,Off_Account_Number.length-1); 
            url1=url1+"&Off_Account_Number="+Off_Account_Number;
        }
         if(Off_Dr_AccHeadCode!="")
         {
        	 Off_Dr_AccHeadCode=Off_Dr_AccHeadCode.substring(0,Off_Dr_AccHeadCode.length-1); 
             url1=url1+"&Off_Dr_AccHeadCode="+Off_Dr_AccHeadCode;
         }
         if(Off_Cr_AccHeadCode!="")
         {
         Off_Cr_AccHeadCode=Off_Cr_AccHeadCode.substring(0,Off_Dr_AccHeadCode.length-1); ;
         //Off_Cr_AccHeadCode=0;
         url1=url1+"&Off_Cr_AccHeadCode="+Off_Cr_AccHeadCode;
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
    //   alert("len:::"+ref_no.length);
     //  alert("after****"+ref_no.substring(0,ref_no.length-1));
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
            url1=url1+"&particulars='"+particulars+"'";
            alert(url1);
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
      
//        login_oid=document.getElementById("txtOffid").value;
//        alert("login_oid::::"+login_oid);  
//        url1=url1+"&login_oid="+login_oid;alert("url1:::"+url1);
        txtCB_Year=document.getElementById("txtCB_Year").value;  
        url1=url1+"&txtCB_Year="+txtCB_Year;
     
        txtCB_Month=document.getElementById("txtCB_Month").value;
        url1=url1+"&txtCB_Month="+txtCB_Month;    
//        txtCrea_date=document.getElementById("txtCrea_date").value;
//        url1=url1+"&txtCrea_date="+txtCrea_date;
      
    }
    
    else
    {
   
          if(document.getElementById("chckparameter").checked==false)
          {
           alert("please select the checkbox");
             return false;
          }
  
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
//         login_oid=document.getElementById("txtOffid").value;
//         url1=url1+"&login_oid="+login_oid;
         txtCB_Year=document.getElementById("txtCB_Year").value;
         url1=url1+"&txtCB_Year="+txtCB_Year;
         txtCB_Month=document.getElementById("txtCB_Month").value;
         url1=url1+"&txtCB_Month="+txtCB_Month;
         acct_head_code=document.frmauto_Fund.account_head_code.value;
 ////  
         mode=document.frmauto_Fund.mode.value; 
         url1=url1+"&mode="+mode;
         Off_Dr_AccHeadCode=document.frmauto_Fund.Off_Dr_AccHeadCode.value;
         url1=url1+"&Off_Dr_AccHeadCode="+Off_Dr_AccHeadCode;
         Off_Cr_AccHeadCode=document.frmauto_Fund.Off_Cr_AccHeadCode.value;
         url1=url1+"&Off_Cr_AccHeadCode="+Off_Cr_AccHeadCode;
         url1=url1+"&acct_head_code="+acct_head_code;
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
   //  var submitid=document.getElementById("submitid");
    // url1=url1+"&submitid=submitid";
     alert("*****"+url1);
    document.frmauto_Fund.action=url1;
    
    
    /** Check Receipt Date */
    if (document.getElementById("txtCrea_date").value=="") 
    {
      alert("Please Enter Receipt Date");
      return false;
    }
    document.getElementById("submitid").disabled="true";
    /** Final Form Posting */
    document.frmauto_Fund.method="post"; 
    document.frmauto_Fund.submit(); 
    
}
     
     
     
     
   


function LoadOffice(unitID_val)
{
	alert(heloo1);

    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Auto_Fund_Receipt_Create_atOfficeServ?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        //alert(url);
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
           // alert(flag);
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

function call_a52()
{
	//alert("hhh");
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var cmbOffice_code=document.getElementById('cmbOffice_code').value;
	if(accunitId!=3){
	var url="../../../../../BankPay_PendingBill_Create.view?Command=a52_verify&accunitId="+accunitId+"&cmbOffice_code="+cmbOffice_code;
		
		var req=getTransport();
		req.open("POST",url,true); 
		req.onreadystatechange=function(){
		processResponse_bk(req);
		};   
		req.send(null);
	}
	else
	{
		var today= new Date(); 
        var day=today.getDate();
        var month=today.getMonth();
        month=month+1;
        var mon=month;
        if(day<=9 && day>=1)
        day="0"+day;
        if(month<=9 && month>=1)
        month="0"+month;
        var year=today.getYear();
        if(year < 1900) year += 1900;                 
        
//       document.frmauto_Fund.txtCrea_date.value=day+"/"+month+"/"+year;
       
       document.frmauto_Fund.txtCB_Year.value=year;
       document.frmauto_Fund.txtCB_Month.value=month; 
	}
}

function processResponse_bk(req){
	 if(req.readyState==4)
   { 
       if(req.status==200)
       {  
      	 var rangeResponse=req.responseXML.getElementsByTagName("response")[0];
    	   update_bank_bal(rangeResponse);
       }
   }
}
function update_bank_bal(response){
	var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
     
	if(flag=="fail"){
		alert("Please Verify A52 and AA52");
		document.getElementById("newDiv").style.display="none";
		document.frmBankPay_PendingBill_create.txtCrea_date.value="";
		return false;
	}
	else
	{
		
		var asset_cleared=response.getElementsByTagName("asset_cleared")[0].firstChild.nodeValue;
		if(asset_cleared>0)
		{
			 var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
            document.frmBankPay_PendingBill_create.txtCrea_date.value=day+"/"+month+"/"+year;
            call_date(document.frmBankPay_PendingBill_create.txtCrea_date);
            document.getElementById("newDiv").style.display="block";
		}
		else
		{
			alert("Please Check A52 and AA52 Register Entry");
			document.getElementById("newDiv").style.display="none";
			document.frmBankPay_PendingBill_create.txtCrea_date.value="";
			return false;
		}
		
	}
}