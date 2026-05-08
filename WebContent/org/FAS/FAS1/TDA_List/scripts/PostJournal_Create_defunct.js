var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;

/** Browser Identification */

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

function nullCheck()
{
		 if(document.getElementById("cmbJournal_type").value=="")
		 {
			 	alert("Select Journal Type");
			 	return false;
		 }
			return true;
}

function pendingJour()
{
document.getElementById("butSub_go").disabled=false;
	       var cmbJournal_type=document.getElementById("cmbJournal_type").value;
              
                     
              var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
              var cmbOffice_code=document.getElementById("cmbOffice_code").value;
              
              var txtCrea_date=document.getElementById("txtCrea_date").value;
              if(txtCrea_date=="")
              {
            	  alert("Enter Voucher Date");
            	  return false;
              }
              else{
              var dat=txtCrea_date.split("/");	
              var mn=dat[1];
              var yr=dat[2];
              }
          //    alert("cmbJournal_type:::"+cmbJournal_type);
               if(cmbJournal_type=="63" ||cmbJournal_type=="66") //Accepting
               {
                   
                    var cell=document.getElementById("grid");
                    cell.style.display="none";
                     var cell9=document.getElementById("grid_two");
                    cell9.style.display="block";
                     var tbody=document.getElementById("grid_body2");
                      var t=0;
                      for(t=tbody.rows.length-1;t>=0;t--)
                      {
                             tbody.deleteRow(0);
                      }
                
                    url="../../../../../PostJournal_Create?command=loadVoucherByMonth_accepting&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbJournal_type="+cmbJournal_type+"&txtCB_Year="+yr+"&txtCB_Month="+mn+"&txtCrea_date="+txtCrea_date;
               }
               else
               {
                var cell=document.getElementById("grid");
                cell.style.display="block";
                 var cell9=document.getElementById("grid_two");
                cell9.style.display="none";
                
                var tbody=document.getElementById("grid_body");
                  var t=0;
                  for(t=tbody.rows.length-1;t>=0;t--)
                  {
                         tbody.deleteRow(0);
                  }
                 url="../../../../../PostJournal_Create?command=loadVoucherByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbJournal_type="+cmbJournal_type+"&txtCB_Year="+yr+"&txtCB_Month="+mn+"&txtCrea_date="+txtCrea_date;
               }
              
              req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {        	  
                     PostJournal_ServletResponse(req);
              }   
              req.send(null); 
     
}

function loadVoucher(command)
{   

         var val=nullCheck();
         if(val==true)
         {
        	 
             if(command=="searchByDate")
                   {  
                         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                         var cmbJournal_type=document.getElementById("cmbJournal_type").value;
                         var txtFrom_date=document.getElementById("txtFrom_date").value;
                         var txtTo_date=document.getElementById("txtTo_date").value;
                         if(cmbJournal_type=="63" ||cmbJournal_type=="66") //Accepting
                           {
                            var tbody=document.getElementById("grid_body2");
                                  var t=0;
                                  for(t=tbody.rows.length-1;t>=0;t--)
                                  {
                                         tbody.deleteRow(0);
                                  }
                            
                                 url="../../../../../PostJournal_Create?command=loadVoucherByDate_accepting&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbJournal_type="+cmbJournal_type+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date;
                          
                           }
                         else
                         {
                                 var tbody=document.getElementById("grid_body");
                                 var t=0;
                                 for(t=tbody.rows.length-1;t>=0;t--)
                                 {
                                        tbody.deleteRow(0);
                                 }
                                 url="../../../../../PostJournal_Create?command=loadVoucherByDate&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbJournal_type="+cmbJournal_type+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date;
                         }
                         req=getTransport();
                         req.open("GET",url,true);        
                         req.onreadystatechange=function()
                         {        	  
                                PostJournal_ServletResponse(req);
                         }   
                         req.send(null);  
                 }
            else  if(command=="searchByMonth")
                {
                document.getElementById("butSub_go").disabled=false;
                         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value; 
                         var cmbOffice_code=document.getElementById("cmbOffice_code").value; 
                        //var financial_year=document.getElementById("financial_year").value;
                         var cmbJournal_type=document.getElementById("cmbJournal_type").value;
                         var txtCB_Year=document.getElementById("txtCB_Year").value; 
                         var txtCB_Month=document.getElementById("txtCB_Month").value; 
                         var txtCrea_date=document.getElementById("txtCrea_date").value;
                         if(txtCrea_date=="")
                         {
                       	  alert("Enter Voucher Date");
                       	  return false;
                         }
                         else{
			                        if(cmbJournal_type=="63" ||cmbJournal_type=="66") //Accepting
			                           {
			                         
			                                 var tbody=document.getElementById("grid_body2");
			                                  var t=0;
			                                  for(t=tbody.rows.length-1;t>=0;t--)
			                                  {
			                                         tbody.deleteRow(0);
			                                  }
			                            
			                                url="../../../../../PostJournal_Create?command=loadVoucherByMonth_accepting&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbJournal_type="+cmbJournal_type+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCrea_date="+txtCrea_date;
			                          
			                           }
			                         
			                         else
			                         {
			                             var tbody=document.getElementById("grid_body"); 
			                             var t=0;
			                             for(t=tbody.rows.length-1;t>=0;t--)
			                             {
			                                    tbody.deleteRow(0);
			                             }
			                             url="../../../../../PostJournal_Create?command=loadVoucherByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbJournal_type="+cmbJournal_type+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCrea_date="+txtCrea_date;
			                         }
			                         req=getTransport();
			                         req.open("GET",url,true);        
			                         req.onreadystatechange=function()
			                         {        	  
			                                PostJournal_ServletResponse(req);
			                         }   
			                         req.send(null); 
                         }
                }
         }
}



function PostJournal_ServletResponse(req)
{
		 if(req.readyState==4)
		 {
                if(req.status==200)
                {  
                      
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;                                  
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(Command=="loadVoucher")
                        {                                       
                               if(flag=="success")
                               {                                                                           
                                       var count=baseResponse.getElementsByTagName("slno");
                                       var tbody=document.getElementById("grid_body");
                                       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                                       var cmbOffice_code=document.getElementById("cmbOffice_code").value;                                       
                                       for(var i=0;i<count.length;i++)
                                       {                                    	   	   
	                                           var slno=baseResponse.getElementsByTagName("slno")[i].firstChild.nodeValue;
	                                           var cashbook_month=baseResponse.getElementsByTagName("CASHBOOK_MONTH")[i].firstChild.nodeValue;
	                                           var cashbook_year=baseResponse.getElementsByTagName("CASHBOOK_YEAR")[i].firstChild.nodeValue;
	                                           var originated_date=baseResponse.getElementsByTagName("originated_date")[i].firstChild.nodeValue;
	                                           var transfer_unit_name=baseResponse.getElementsByTagName("TRANSFER_UNIT_NAME")[i].firstChild.nodeValue;
	                                           var reason_for_transfer=baseResponse.getElementsByTagName("REASON_FOR_TRANSFER")[i].firstChild.nodeValue;
	                                           if(reason_for_transfer=="-")
	                                        	    reason_for_transfer="";  
	                                           var sub_ledger_type=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE")[i].firstChild.nodeValue;
	                                           if(sub_ledger_type=="-")
	                                        	    sub_ledger_type="";
	                                           var sub_ledger_type_code=baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[i].firstChild.nodeValue;
	                                           if(sub_ledger_type_code=="-")
	                                        	   	sub_ledger_type_code="";
	                                           var total_amount=baseResponse.getElementsByTagName("TOTAL_AMOUNT")[i].firstChild.nodeValue;
	                                           var particulars=baseResponse.getElementsByTagName("PARTICULARS")[i].firstChild.nodeValue;
	                                           if(particulars=="null")
	                                        	   particulars="";
	                                           
	                                           var mycurrent_row=document.createElement("TR");
	                                           mycurrent_row.id=seq;
	                                      
	                                           var cell=document.createElement("TD");
	                                           var check="";
	                                           if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	                                           {
	                                               	  check=document.createElement("<INPUT type='checkbox' name='slno' id='slno' value='"+slno+"' size='10'>");
	                                           }
	                                           else
	                                           {  
	                                                  check=document.createElement("input");
	                                                  check.type="checkbox";
	                                                  check.name="slno";
	                                                  check.id="slno";
	                                                  check.value=slno;
	                                           }
	                                           cell.appendChild(check);
	                                           mycurrent_row.appendChild(cell);
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(slno);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2); 
	                                      
	                                           var cell2=document.createElement("TD");   	                                           
	                                           var currentText1=document.createTextNode(originated_date);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2); 
	                               
	                                           var cell2=document.createElement("TD");                                              
	                                           var currentText1=document.createTextNode(transfer_unit_name);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2);
	                                           
	                                           var cell2=document.createElement("TD");                                              
	                                           var currentText1=document.createTextNode(reason_for_transfer);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2);
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(sub_ledger_type);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2);
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(sub_ledger_type_code);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2);
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(total_amount);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2); 
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(particulars);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2); 
	                                           
	                                           var cell=document.createElement("TD");
	       			                           cell.align='CENTER';
	       			                           var anc=document.createElement("A");
	       			                           var url="javascript:Show('"+cmbAcc_UnitCode+"','"+cmbOffice_code+"','"+cashbook_year+"','"+cashbook_month+"','"+slno+"')";
	       			                           anc.href=url;
	       			                           var txtedit=document.createTextNode("DETAILS");
	       			                           anc.appendChild(txtedit);
	       			                           cell.appendChild(anc);
	       			                           mycurrent_row.appendChild(cell);
	                                           
	                                           tbody.appendChild(mycurrent_row);
	                                           seq++;	                                          
                                       }
                                       disabledAll();
                               }
                               else
                               {                                                   
                                       alert("No Records Available");
                               }
                       }
                       else  if(Command=="loadVoucherAccepting")
                        {                                       
                               if(flag=="success")
                               {                                                                           
                                       var count=baseResponse.getElementsByTagName("acceptedVouNo");
                                       var tbody=document.getElementById("grid_body2");
                                       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                                       var cmbOffice_code=document.getElementById("cmbOffice_code").value;                                       
                                       for(var i=0;i<count.length;i++)
                                       {                                    	   	   
	                                           var slno=baseResponse.getElementsByTagName("acceptedVouNo")[i].firstChild.nodeValue;
	                                           var acceptedVouDate=baseResponse.getElementsByTagName("acceptedVouDate")[i].firstChild.nodeValue;
	                                           var acceptedUnitname=baseResponse.getElementsByTagName("acceptedUnitname")[i].firstChild.nodeValue;
                                                   
                                                   var cashbook_month=baseResponse.getElementsByTagName("CASHBOOK_MONTH")[i].firstChild.nodeValue;
	                                           var cashbook_year=baseResponse.getElementsByTagName("CASHBOOK_YEAR")[i].firstChild.nodeValue;
	                                           var orgVoucherNo=baseResponse.getElementsByTagName("orgVoucherNo")[i].firstChild.nodeValue;
	                                           var orgDate=baseResponse.getElementsByTagName("orgDate")[i].firstChild.nodeValue;
	                                           var ogrUnitName=baseResponse.getElementsByTagName("ogrUnitName")[i].firstChild.nodeValue;
	                                           var reason_for_transfer=baseResponse.getElementsByTagName("REASON_FOR_TRANSFER")[i].firstChild.nodeValue;
	                                           if(reason_for_transfer=="-")
	                                        	    reason_for_transfer="";  
	                                          var total_amount=baseResponse.getElementsByTagName("TOTAL_AMOUNT")[i].firstChild.nodeValue;
	                                         
	                                           var mycurrent_row=document.createElement("TR");
	                                           mycurrent_row.id=seq;
	                                      
	                                           var cell=document.createElement("TD");
	                                           var check="";
	                                           if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	                                           {
	                                               	  check=document.createElement("<INPUT type='checkbox' name='slno' id='slno' value='"+slno+"' size='10'>");
	                                           }
	                                           else
	                                           {  
	                                                  check=document.createElement("input");
	                                                  check.type="checkbox";
	                                                  check.name="slno";
	                                                  check.id="slno";
	                                                  check.value=slno;
	                                           }
	                                           cell.appendChild(check);
	                                           mycurrent_row.appendChild(cell);
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(slno);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2); 
	                                      
	                                           var cell2=document.createElement("TD");   	                                           
	                                           var currentText1=document.createTextNode(acceptedVouDate);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2); 
	                               
	                                           var cell2=document.createElement("TD");                                              
	                                           var currentText1=document.createTextNode(acceptedUnitname);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2);
	                                           
	                                           var cell2=document.createElement("TD");                                              
	                                           var currentText1=document.createTextNode(orgVoucherNo);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2);
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(orgDate);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2);
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(ogrUnitName);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2);
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(reason_for_transfer);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2); 
	                                           
	                                           var cell2=document.createElement("TD");
	                                           var currentText1=document.createTextNode(total_amount);
	                                           cell2.appendChild(currentText1);
	                                           mycurrent_row.appendChild(cell2); 
	                                           
	                                           var cell=document.createElement("TD");
	       			                           cell.align='CENTER';
	       			                           var anc=document.createElement("A");
	       			                           var url="javascript:Show('"+cmbAcc_UnitCode+"','"+cmbOffice_code+"','"+cashbook_year+"','"+cashbook_month+"','"+slno+"')";
	       			                           anc.href=url;
	       			                           var txtedit=document.createTextNode("DETAILS");
	       			                           anc.appendChild(txtedit);
	       			                           cell.appendChild(anc);
	       			                           mycurrent_row.appendChild(cell);
	                                           
	                                           tbody.appendChild(mycurrent_row);
	                                           seq++;	                                          
                                       }
                                       disabledAll();
                               }
                               else
                               {                                                   
                                       alert("No Records Available");
                               }
                       }
                       
  }
 }    
}

function Show(unitcode,offid,yr,mon,recNo)
{
		 //alert(unitcode+" "+offid+" "+yr+" "+mon+" "+recNo);
	 	 var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA/jsps/TDA_TCA_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+recNo,"VoucherList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	     Voucher_list_SL.moveTo(250,250);  
	     Voucher_list_SL.focus();
    
}


function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
         if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
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

function call_date(dateCtrl)                        // TB_checking 
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
                         if(flag=="failure")
                         {
                                  dateCtrl.value="";
                                  alert("Trial Balance Closed");//return false;//
                                  dateCtrl.focus();                                            
                         }
                         else if(flag=="finyear")
                         {
                                  // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                  dateCtrl.value="";
                                  alert("Cash Book Control Not Found ");//return false;//
                                  dateCtrl.focus();
                                  //document.getElementById("txtVoucher_No").value="";     
                         }
                }
         }
}


function checkNull()
{       
    
       var jType= document.getElementById("cmbJournal_type").value;  
   //    alert(jType);
       if(jType=="62" || jType=="65")
       {
                var tbody=document.getElementById("grid_body");              
                if(tbody.rows.length==0)
                {
                            alert("Select Originated Sl.No.");         
                            return false; 
                }
                else if(tbody.rows.length>0)
                {
                var len_two=tbody.rows.length;
                
                    for(var mm=0;mm<len_two;mm++)
                    {
                             var cell_one=tbody.rows[mm].cells;
                            
                             var grid_date=cell_one.item(2).lastChild.nodeValue;
                             var journal_date=document.getElementById("txtCrea_date").value;
                        
                             var str1_grid =grid_date.split("-");
                             var str2 = journal_date.split("/");
                             if(str1_grid[2]>str2[2])
                             {
                                        alert("Voucher Date should not less than Originated Date** ");
                                         document.getElementById("txtCrea_date").value="";
                                         document.getElementById("txtCrea_date").focus();
                                         return false;
                             }
                             else if(str1_grid[2]==str2[2])
                             {
                               
                                    if(str1_grid[1]>str2[1])
                                    {
                                        alert("Voucher Date should not less than Originated Date* ");
                                         document.getElementById("txtCrea_date").value="";
                                         document.getElementById("txtCrea_date").focus();
                                         return false;
                                    }
                                    else if(str1_grid[1]==str2[1])
                                    {
                                    	//alert("month:::");
                                    //	alert("day:::grid::::"+str1_grid[0]);
                                    //	alert("day:::voucher::::"+str2[0]);
                                        if(str1_grid[0]>str2[0])
                                        {
                                        alert("Voucher Date should not less than Originated Date");
                                         document.getElementById("txtCrea_date").value="";
                                         document.getElementById("txtCrea_date").focus();
                                         return false;
                                        }
                                    
                                    }
                             }
                     }
                                   
                        var count=0;
                        if(document.Post_Journal.slno.length>1)
                        {
                                for(var i=0;i<tbody.rows.length;i++)
                                {
                                                if(document.Post_Journal.slno[i].checked==true)
                                                        count++
                                }
                        }
                        else
                        {
                                        if(document.Post_Journal.slno.checked==true)
                                        count++
                        }
                        if(count==0)
                        {
                                alert("Select Originated Sl.No.");
                                return false;
                        }            	   
                }   
        }
        else{
            var tbody2=document.getElementById("grid_body2"); 
            if(tbody2.rows.length==0)
            {
                        alert("Select Accepted Voucher No.");         
                        return false; 
            }
             if(tbody2.rows.length>0)
            {
            
             var len_two=tbody2.rows.length;
                
                    for(var mm=0;mm<len_two;mm++)
                    {
                             var cell_one=tbody2.rows[mm].cells;
                            
                             var grid_date=cell_one.item(2).lastChild.nodeValue;
                             var journal_date=document.getElementById("txtCrea_date").value;
                        
                             var str1_grid =grid_date.split("-");
                             var str2 = journal_date.split("/");
                             if(str1_grid[2]>str2[2])
                             {
                                        alert("Voucher Date should not less than Accepted Date** ");
                                         document.getElementById("txtCrea_date").value="";
                                         document.getElementById("txtCrea_date").focus();
                                         return false;
                             }
                             else if(str1_grid[2]==str2[2])
                             {
                               
                                    if(str1_grid[1]>str2[1])
                                    {
                                        alert("Voucher Date should not less than Accepted Date* ");
                                         document.getElementById("txtCrea_date").value="";
                                         document.getElementById("txtCrea_date").focus();
                                         return false;
                                    }
                                    else if(str1_grid[1]==str2[1])
                                    {
                                        if(str1_grid[0]>str2[0])
                                        {
                                        alert("Voucher Date should not less than Accepted Date");
                                         document.getElementById("txtCrea_date").value="";
                                         document.getElementById("txtCrea_date").focus();
                                         return false;
                                        }
                                    
                                    }
                             }
                     }
            
                    var count=0;
                    if(document.Post_Journal.slno.length>1)
                    {
                            for(var i=0;i<tbody2.rows.length;i++)
                            {
                                            if(document.Post_Journal.slno[i].checked==true)
                                                    count++
                            }
                    }
                    else
                    {
                                    if(document.Post_Journal.slno.checked==true)
                                    count++
                    }
                    if(count==0)
                    {
                            alert("Select Accepted Voucher No.");
                            return false;
                    }            	   
            }
        }
        
        if(window.confirm("Please verify the voucher details.  \nBecause it will create journal entry. \nIf you want to continue,click 'OK' ?"))
        {
              	return true;
        }
        else
        {
        		return false;       		
        }
        
                      
}
 
function enabledAll()
{
		document.getElementById("cmbAcc_UnitCode").readOnly=false;
		document.getElementById("cmbOffice_code").readOnly=false;
		//document.getElementById("financial_year").readOnly=false;
		document.getElementById("cmbJournal_type").readOnly=false;
		document.getElementById("txtFrom_date").readOnly=false;
		document.getElementById("txtTo_date").readOnly=false;
}

function call_clr()
{
		enabledAll();
		document.getElementById("cmbJournal_type").value="";
		//document.getElementById("txtFrom_date").value="";
		//document.getElementById("txtTo_date").value="";
		var tbody=document.getElementById("grid_body");
                var t=0;
                for(t=tbody.rows.length-1;t>=0;t--)
                {
                        tbody.deleteRow(0);
                }
                var tbody2=document.getElementById("grid_body2");
                var t2=0;
                for(t2=tbody2.rows.length-1;t2>=0;t2--)
                {
                        tbody2.deleteRow(0);
                }
}

function disabledAll()
{
		document.getElementById("cmbAcc_UnitCode").readOnly=true;
		document.getElementById("cmbOffice_code").readOnly=true;
		//document.getElementById("financial_year").readOnly=true;
		document.getElementById("cmbJournal_type").readOnly=true;
		document.getElementById("txtFrom_date").readOnly=true;
		document.getElementById("txtTo_date").readOnly=true;
}

function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    		alert("Please Enter Paticulars below 200 characters");
	    }	 
}

function LoadOffice(unitID_val)
{
   
    if((unitID_val!="") && (unitID_val!=999))
    {
        
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
     
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice_test(req);
        }
        req.send(null);
    }
    else if(unitID_val==999)
    {
         var cmboffice=document.getElementById("cmbOffice_code");
         cmboffice.innerHTML="";
    }
}

function handle_loadOffice_test(req)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {
        //alert(req.responseText);
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
       
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
          
            var offidvalues=baseresponse.getElementsByTagName("offid");
            //alert(offid.length)
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
