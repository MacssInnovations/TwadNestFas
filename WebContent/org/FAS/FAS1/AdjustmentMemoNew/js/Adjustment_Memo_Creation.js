function numbersonly(e,t)
{
        var unicode=e.charCode? e.charCode : e.keyCode;
        if(unicode==13)
        {
	        try{t.blur(); }catch(e){}
	        return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
            {
                return false 
            }
        }
} 
function handleResponse1(req)
{
	 if(req.readyState==4)
	   {
		   if(req.status==200)
	          {
                  var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
                  var tagCommand=baseResponse.getElementsByTagName("command")[0];                 
                  var command=tagCommand.firstChild.nodeValue; 
                 
	                if(command=="receiptNo")
                    { 
	                	receiptNoCheck(baseResponse);
                    }
	                else if(command=="load_Ref_Number")
	                { 
	                	RefNoCheck(baseResponse);
	                }
	          }
	    }

}

function RefNoCheck(baseResponse)
{
	 var HoNo=baseResponse.getElementsByTagName("HoNo")[0].firstChild.nodeValue;
	 var HoDate=baseResponse.getElementsByTagName("HoDate")[0].firstChild.nodeValue;
	 var totalAmt=baseResponse.getElementsByTagName("totalAmt")[0].firstChild.nodeValue;
	 document.forms[0].ho_ref_no.value=HoNo;
	 document.forms[0].ho_ref_date.value=HoDate;
//	 document.forms[0].txtTotalAmt.value=totalAmt;
	 document.forms[0].txtsub_Amount.value=totalAmt;
	 
}

function receiptNoCheck(baseResponse)
{
	
    var receiptno=document.forms[0].txtReceipt_No;
    document.forms[0].txtReceipt_No.length=1;
    var receiptnumber=baseResponse.getElementsByTagName("receiptno");
    if(receiptnumber.length>0){
    for(var i=0;i<receiptnumber.length;i++)
    {
       
        var opt=document.createElement('option');
        opt.value=receiptnumber[i].firstChild.nodeValue;
        opt.innerHTML=receiptnumber[i].firstChild.nodeValue;
        receiptno.appendChild(opt);
    }
    }
    else
    	 alert("No Data Found");
}


function clear_Combo(combo)
{
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";       
        var option=document.createElement("OPTION");
        option.text="--Select Code--";
        option.value="";        
        try
        {        
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}

function sixdigit()
{
	    if( document.getElementById("txtAcc_HeadCode").value!=0)
	    {
	        if((document.getElementById("txtAcc_HeadCode").value).length<6)
	        {
		        alert("Account Head Code Shouldn't be less than 6 digit number");
		        document.getElementById("txtAcc_HeadCode").focus();
		        return false;
	        }
	    }
}

function filter_real(evt,item,n,pre)
{
        var charCode = (evt.which) ? evt.which : event.keyCode
// allow "." for one time 
        if(charCode==46){
                        //	alert("Position of . "+item.value.indexOf("."));
                        if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                        else return false;
        }
        if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //			alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //	alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}


function valid_amt(field)
{
    
	    amt=field.value;
	    if(amt.indexOf(".")!=amt.lastIndexOf("."))
	    {
	        alert("Enter a Valid Amount");
	        field.value="";
	        field.focus();
	    }
	    if(amt < 0 ) 
	    {
	        alert("Negative Amount Not Allowed");
	        field.value="";
	        field.focus();    
	    }
	    if(parseInt(document.getElementById("txtsub_Amount").value) > parseInt(document.getElementById("txtTotalAmt").value))
	    {
	  	    alert("Enter Correct Amount");
	  	    document.getElementById("txtsub_Amount").value="";
	  	    document.getElementById("txtsub_Amount").focus();
	  	    return false
	    }
	    else
	  	  return true;
    
}

function doFunction_voucher(command,param)
{
	
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var txtCB_Year=document.getElementById("txtCB_Year").value;
		var txtCB_Month=document.getElementById("txtCB_Month").value;
		var txtCrea_date=document.getElementById("txtCrea_date").value;
		var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
		var office_id=document.getElementById("office_id").value;
		if(command=="load_Receipt_No")
		{
			
			var url="../../../../../Adjustment_Memo_Creation?Command=loadno&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCrea_date="+txtCrea_date+"&txtAcc_HeadCode="+txtAcc_HeadCode+"&office_id="+office_id;
			 var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                handleResponse1(req);
            }   
            req.send(null);	
		}
		else if(command=="load_Receipt_Details")
		{
			
			var txtReceipt_No=document.getElementById("txtReceipt_No").value;
			
			var url="../../../../../Adjustment_Memo_Creation?Command=load_Ref_No&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtReceipt_No="+txtReceipt_No;                                    
			var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                handleResponse1(req);
            }   
            req.send(null);	
		}
}


function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    	alert("Please Enter Paticulars below 200 characters");
	    }	 
}


var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
	        alert("Enter A/c Head Code");
	        return false;
        }                
        var acc=document.getElementById("txtAcc_HeadCode").value;
        var kk=acc.charAt(0)+acc.charAt(1);
        if(kk=="82")
        {
	          if(acc !="820102"  && acc !="820103")
	          {		          
		            alert("This A/C code can not be used here ");
		            document.getElementById("txtAcc_HeadCode").value="";
		            document.getElementById("txtAcc_HeadDesc").value="";
		            return false;
	          }  
        }        
                        
               
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
       
        var tbody=document.getElementById("grid_body");                            
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;     
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;        
        if(document.Adjustment_Memo_Form.rad_sub_CR_DR[0].checked==true)
        	items[2]=document.Adjustment_Memo_Form.rad_sub_CR_DR[0].value;
        else if(document.Adjustment_Memo_Form.rad_sub_CR_DR[1].checked==true)
        	items[2]=document.Adjustment_Memo_Form.rad_sub_CR_DR[1].value;        
        items[3]=document.forms[0].cmbSL_type.value;
        if(items[3]==0)
        {
        	items[4]="--";
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
        items[5]=document.forms[0].cmbSL_Code.value;
        if(items[5]==0)
        {
        	items[6]="--";
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
       
        items[7]=document.getElementById("txtCB_Year").value;
        items[8]=document.getElementById("txtCB_Month").value;
        items[9]=document.getElementById("txtReceipt_No").value;
        items[10]=document.getElementById("ho_ref_no").value;
        
        items[11]=document.getElementById("ho_ref_date").value;
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
        
        document.getElementById("txtTotalAmt").value=parseFloat(document.getElementById("txtTotalAmt").value) + parseFloat(items[12]);
        
       tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        
        mycurrent_row.id=seq;
        
        
        var cell=document.createElement("TD");
	        var anc=document.createElement("A");
	        var url="javascript:loadTable('"+mycurrent_row.id+"')";
	        anc.href=url;
	        var txtedit=document.createTextNode("EDIT");
	        anc.appendChild(txtedit);
	        cell.appendChild(anc);
	        mycurrent_row.appendChild(cell);
	        var i=0;
	        var cell2;
        
       
        cell2=document.createElement("TD");       
              var H_code=document.createElement("input");
              H_code.type="hidden";
              H_code.name="H_code";
              H_code.value=items[0];
              cell2.appendChild(H_code);
              var currentText=document.createTextNode(items[0]+"-"+items[1]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
              
         cell2=document.createElement("TD"); 
              var CR_DR_type=document.createElement("input");
              CR_DR_type.type="hidden";
              CR_DR_type.name="CR_DR_type";
              CR_DR_type.value=items[2];
              cell2.appendChild(CR_DR_type);
              var currentText=document.createTextNode(items[2]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
         
         cell2=document.createElement("TD");
              var SL_type=document.createElement("input");
              SL_type.type="hidden";
              SL_type.name="SL_type";
              SL_type.value=items[3];
              cell2.appendChild(SL_type);
              var currentText=document.createTextNode(items[4]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
        
         cell2=document.createElement("TD");
              var SL_code=document.createElement("input");
              SL_code.type="hidden";
              SL_code.name="SL_code";
              SL_code.value=items[5];
              cell2.appendChild(SL_code);
              var currentText=document.createTextNode(items[6]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
        
              
              cell2=document.createElement("TD"); 
              var cb_year=document.createElement("input");
              cb_year.type="hidden";
              cb_year.name="cashbkyear";
              cb_year.value=items[7];
              cell2.appendChild(cb_year);
              var currentText=document.createTextNode(items[7]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
              
              cell2=document.createElement("TD"); 
              var cb_month=document.createElement("input");
              cb_month.type="hidden";
              cb_month.name="cashbkmonth";
              cb_month.value=items[8];
              cell2.appendChild(cb_month);
              var currentText=document.createTextNode(items[8]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
              
              cell2=document.createElement("TD"); 
              var receipt_no=document.createElement("input");
              receipt_no.type="hidden";
              receipt_no.name="receiptNo";
              receipt_no.value=items[9];
              cell2.appendChild(receipt_no);
              var currentText=document.createTextNode(items[9]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);  
              
             cell2=document.createElement("TD");
              var ho_no=document.createElement("input");
              ho_no.type="hidden";
              ho_no.name="ref_no";
              ho_no.value=items[10];
              cell2.appendChild(ho_no);
              var currentText=document.createTextNode(items[10]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
              
          cell2=document.createElement("TD");
              var ho_date=document.createElement("input");
              ho_date.type="hidden";
              ho_date.name="ref_date";
              ho_date.value=items[11];
              cell2.appendChild(ho_date);
              var currentText=document.createTextNode(items[11]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
              
         cell2=document.createElement("TD"); 
              var sl_amt=document.createElement("input");
              sl_amt.type="hidden";
              sl_amt.name="sl_amt";
              sl_amt.value=items[12];
              cell2.appendChild(sl_amt);
              var currentText=document.createTextNode(items[12]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
          
          cell2=document.createElement("TD");
         
              var particular=document.createElement("input");
              particular.type="hidden";
              particular.name="sl_particular";
              particular.value=items[13];
              cell2.appendChild(particular);
              var currentText=document.createTextNode(items[13]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
              
          

         tbody.appendChild(mycurrent_row);
         clearall();
        
         seq=seq+1;  
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
	        var tbody=document.getElementById("mytable");
	        var r=document.getElementById(com_id);
	        var ri=r.rowIndex;
	        tbody.deleteRow(ri);
	        clearall();
        }
}

function update_GRID()
{      
		 if(document.getElementById("txtAcc_HeadCode").value.length==0)
	     {
		        alert("Enter A/c Head Code");
		        return false;
	     }                
	         
	     	    	     
         var items=new Array();
       
         items[0]=document.getElementById("txtAcc_HeadCode").value;
         items[1]=document.getElementById("txtAcc_HeadDesc").value;
         
         if(document.Adjustment_Memo_Form.rad_sub_CR_DR[0].checked==true)
        	 items[2]=document.Adjustment_Memo_Form.rad_sub_CR_DR[0].value;
         else if(document.Adjustment_Memo_Form.rad_sub_CR_DR[1].checked==true)
        	 items[2]=document.Adjustment_Memo_Form.rad_sub_CR_DR[1].value;
         
         items[3]=document.forms[0].cmbSL_type.value;
         
         if(items[3]==0)
         {
         	items[4]="--";
         }
         else
         {
         	items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
         }
        
         items[5]=document.forms[0].cmbSL_Code.value;
         if(items[5]==0)
         {
         	items[6]="--";
         }
         else
         {
         	items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
         }
         
         items[7]=document.getElementById("txtCB_Year").value;
         items[8]=document.getElementById("txtCB_Month").value;
         items[9]=document.getElementById("txtReceipt_No").value;
         items[10]=document.getElementById("ho_ref_no").value;
         items[11]=document.getElementById("ho_ref_date").value;
         items[12]=document.getElementById("txtsub_Amount").value;
         items[13]=document.getElementById("txtParticular").value;
        
        
         var r=document.getElementById(com_id);
         var rcells=r.cells;
	        
		         try{rcells.item(1).firstChild.value=items[0];}catch(e){}   //firstchild for value and last for desc
		         try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}
		      
		         try{rcells.item(2).firstChild.value=items[2];}catch(e){}
		         try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
		        
		         try{rcells.item(3).firstChild.value=items[3];}catch(e){}
		         try{rcells.item(3).lastChild.nodeValue=items[4];}catch(e){}
		
		         try{rcells.item(4).firstChild.value=items[5];}catch(e){}
		         try{rcells.item(4).lastChild.nodeValue=items[6];}catch(e){}
            
                try{rcells.item(5).firstChild.value=items[7];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
             
                try{rcells.item(6).firstChild.value=items[8];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
                
                try{rcells.item(7).firstChild.value=items[9];}catch(e){}
                try{rcells.item(7).lastChild.nodeValue=items[9];}catch(e){}
                
                try{rcells.item(8).firstChild.value=items[10];}catch(e){}
                try{rcells.item(8).lastChild.nodeValue=items[10];}catch(e){}
            
             
                try{rcells.item(9).firstChild.value=items[11];}catch(e){}
                try{rcells.item(9).lastChild.nodeValue=items[11];}catch(e){}
                
                try{rcells.item(10).firstChild.value=items[12];}catch(e){}
                try{rcells.item(10).lastChild.nodeValue=items[12];}catch(e){}
                
                try{rcells.item(11).firstChild.value=items[13];}catch(e){}
                try{rcells.item(11).lastChild.nodeValue=items[13];}catch(e){}
                
            
		        alert("Record Updated");
		        clearall();
  }

/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////
function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        try{common_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){cmbMas_SL_type=""}
        try{common_cmbSL_code=rcells.item(4).firstChild.value;} catch(e){cmbMas_SL_code=""}
        
        doFunction('checkCode','null');       
        doFunction('Load_SL_Code',common_cmbSL_type);
       // alert(common_cmbSL_type);
        document.getElementById("cmbSL_type").value=common_cmbSL_type;
        if(rcells.item(2).firstChild.value=="CR")
        	document.Adjustment_Memo_Form.rad_sub_CR_DR[0].checked=true;
        else if(rcells.item(2).firstChild.value=="DR")
        	document.Adjustment_Memo_Form.rad_sub_CR_DR[1].checked=true;
        
        sltype=rcells.item(3).firstChild.value;
        
        slcode=rcells.item(4).firstChild.value;
      
        try{document.getElementById("txtEmpID_trs").value=slcode;}catch(e){}  
        doFunction('Load_SL_Code',sltype);
        
        try{document.getElementById("txtCB_Year").value=rcells.item(5).firstChild.value;}catch(e){}
        try{document.getElementById("txtCB_Month").value=rcells.item(6).firstChild.value;}catch(e){}
        try{document.getElementById("txtReceipt_No").value=rcells.item(7).firstChild.value;}catch(e){}
        
        try{document.getElementById("ho_ref_no").value=rcells.item(8).firstChild.value;}catch(e){}
        try{document.getElementById("ho_ref_date").value=rcells.item(9).firstChild.value;}catch(e){}
       try{document.getElementById("txtsub_Amount").value=rcells.item(10).firstChild.value;}catch(e){}
       try{document.getElementById("txtParticular").value=rcells.item(11).firstChild.value;}catch(e){}
       
       
       document.Adjustment_Memo_Form.cmdupdate.style.display='block';
	   document.Adjustment_Memo_Form.cmddelete.disabled=false;
	   document.Adjustment_Memo_Form.cmdadd.style.display='none';
	   setTimeout('document.getElementById("cmbSL_type").value=sltype',900); 
}


/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
    // document.getElementById("txtAcc_HeadCode").value="";    
     //document.getElementById("txtAcc_HeadDesc").value="";
     document.Adjustment_Memo_Form.rad_sub_CR_DR[0].checked=true;
     document.getElementById("cmbSL_type").value="";     
     document.getElementById("txtsub_Amount").value="";
     document.getElementById("txtParticular").value="";
     document.getElementById("ho_ref_no").value="";
     document.getElementById("ho_ref_date").value="";
     document.getElementById("txtReceipt_No").value="s";
                  
     var cmbSL_Code1=document.getElementById("cmbSL_Code"); 
     clear_Combo(cmbSL_Code1);   
	 document.Adjustment_Memo_Form.cmdadd.style.display='block';
	 document.Adjustment_Memo_Form.cmdupdate.style.display='none';
	 document.Adjustment_Memo_Form.cmddelete.disabled=true;
	 document.Adjustment_Memo_Form.txtAcc_HeadCode.disabled=false;
	 
}

/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

function checkNull()
{
        var tbody=document.getElementById("grid_body");
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
            alert("Select the Account Unit code");
            return false;    
        }
        if(document.getElementById("cmbOffice_code").value=="")
        {
            alert("Select the Office Code");           
            return false;
        }
        if(document.getElementById("txtCrea_date").value.length==0)
        {
            alert("Enter the Date of Creation");           
            return false;    
        }          
        if(document.getElementById("txtTotalAmt").value.length==0)
        {
            alert("Enter the Total Amount in General");          
            return false;    
        }
        if(document.getElementById("office_id").value=='')
        {
            alert("Select the office id");          
            return false;    
        }
        if(tbody.rows.length==0)
        {
            alert("Enter the Details Part");         
            return false; 
        }
        else
        {
                        var dr_check_amt=0;var cr_check_amt=0;var count=0;
                        var totalamt=0;
                        rows=tbody.getElementsByTagName("TR");                           
                        for(i=0;i<rows.length;i++)
                        {
                                    var cells=rows[i].cells;                                              
                                    if(cells.item(2).lastChild.nodeValue=='CR')
                                    {
                                         cr_check_amt=parseFloat(cr_check_amt) + parseFloat(cells.item(10).lastChild.nodeValue);
                                        
                                    }                                  
                         }      

                        if(cr_check_amt!=document.getElementById("txtTotalAmt").value)
                        {
                                    alert("Total Amount of CR in Details & Total Amount in General part should be equal");
                                    return false;
                        }
        }
        return true;
}


function clrForm()
{
	    if(window.confirm("Do you want to clear ALL fields ?"))
		{
		   		call_clr();
		}
}

function call_clr()
{
		document.getElementById("txtAcc_HeadCode").value=610102;
		doFunction('checkCode','null');
	    document.getElementById("txtTotalAmt").value="";
	    document.getElementById("office_id").value="";
	    document.getElementById("txtCrea_date").value="";
	    document.getElementById("letterNo").value="";
	    document.getElementById("letterDate").value="";
	    document.getElementById("authority").value="";
	    document.getElementById("particulars").value="";
	    document.getElementById("authorityaddress").value="";
	    var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	    		tbody.deleteRow(0);
	    }
}

function clearMonth()
{
		document.getElementById("txtCB_Month").value="";
}

///////////////////////////////////////////    TB_checking and Calender control return value handling

function numbersonlyallowed(e,t)
{
   var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      try{t.blur();}catch(e){}
      return true;
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false ;
    }
 }

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
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
	    call_clr();
	    if(checkdt(dateCtrl))
	    {       
		         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		         cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
		         var cmbSL_Code=document.getElementById("txtReceipt_No");   
		         cmbSL_Code.innerHTML="";
		         var option=document.createElement("OPTION");
		         option.text="-- Select Receipt Number --";
		         option.value="";
		         try
		         {
		                 cmbSL_Code.add(option);
		         }catch(errorObject)
		         {
		        	 	 cmbSL_Code.add(option,null);
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
			            
			             if(flag=="success")
			             {
			            	 	doFunction('load_Receipt_No','null');                 //return true;
			             }
			             else if(flag=="failure")
			             {
			                    dateCtrl.value="";
			                    alert("Trial Balance Closed");//return false;//
			                    dateCtrl.focus();
			                    var cmbSL_Code=document.getElementById("txtReceipt_No");   
			                    cmbSL_Code.innerHTML="";
			                    var option=document.createElement("OPTION");
			                    option.text="-- Select Receipt Number --";
			                    option.value="";
			                    try
			                    {
			                        cmbSL_Code.add(option);
			                    }catch(errorObject)
			                    {
			                        cmbSL_Code.add(option,null);
			                    }
			               }
		        }
	    }
}


function CaLLoadSubLedger()
{
	//alert("enter"+document.getElementById("office_id").value);
	//document.getElementById("cmbSL_Code").value=document.getElementById("office_id").value;
	
	document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].value=document.getElementById("office_id").options[document.getElementById("office_id").selectedIndex].value;
	//alert("enter"+document.getElementById("cmbSL_Code").value);
	document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text=document.getElementById("office_id").options[document.getElementById("office_id").selectedIndex].text;

	
	
	
	//document.getElementById("cmbMas_SL_type").Text=document.getElementById("office_id").Text;
	
}