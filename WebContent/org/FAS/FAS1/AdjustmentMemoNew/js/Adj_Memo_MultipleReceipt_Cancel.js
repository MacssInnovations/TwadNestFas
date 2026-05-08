var seq=0;
var com_id;
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

function doFunction_voucher(command,param)
{
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	//	var txtCB_Year=document.getElementById("txtCB_Year").value;
		//var txtCB_Month=document.getElementById("txtCB_Month").value;
		   var txtCrea_date=document.getElementById("txtCrea_date").value;
		  // alert(txtCrea_date);
		if(command=="load_Receipt_No")
		{
			var url="../../../../../Adj_Memo_MultipleReceipt_Cancel?Command=load_Voucher_No&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
			//alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
            	 callManipulated(req);
            }   
            req.send(null);	
		}
		else if(command=="load_Receipt_Details")
		{
			var cmbvocharNo=document.getElementById("cmbvocharNo").value;
			
			var url="../../../../../Adj_Memo_MultipleReceipt_Cancel?Command=load_Ref_No&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&cmbvocharNo="+cmbvocharNo;                                    
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
            	 callManipulated(req);
            }   
            req.send(null);	
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

function callManipulated(req)
{
	 if(req.readyState==4)
	   {
		   if(req.status==200)
	          {
                    var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
                  
                    var tagCommand=baseResponse.getElementsByTagName("command")[0];
                   
                    var command=tagCommand.firstChild.nodeValue; 
                   
	                if(command=="receiptNo1")
                      { 
	                	receiptNoCheck(baseResponse);
                      }
	                else if(command=="load_Ref_No1")
                    { 
	                	RefNoCheck(baseResponse);
                    }
	              
	          }
	    }
}
function receiptNoCheck(baseResponse)
{
	
	var receiptno=document.forms[0].cmbvocharNo;
    document.forms[0].cmbvocharNo.length=1;
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



function RefNoCheck(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue
	if(flag=="success")
	{
	 var accountunitid=baseResponse.getElementsByTagName("accountunitid")[0].firstChild.nodeValue;
	 var officename=baseResponse.getElementsByTagName("officename")[0].firstChild.nodeValue;
	 var totalAmt=baseResponse.getElementsByTagName("totalAmt")[0].firstChild.nodeValue;
	 var particulars=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
	 var authorityname=baseResponse.getElementsByTagName("authorityname")[0].firstChild.nodeValue;
	 var authorityaddress=baseResponse.getElementsByTagName("authorityaddress")[0].firstChild.nodeValue;
	 var letterno=baseResponse.getElementsByTagName("letterno")[0].firstChild.nodeValue;
	 var letterdate=baseResponse.getElementsByTagName("letterdate")[0].firstChild.nodeValue;
	 var vdate=baseResponse.getElementsByTagName("vdate")[0].firstChild.nodeValue;
		
	 
	 document.forms[0].txtCrea_date.value=vdate;
	 document.forms[0].letterNo.value=letterno;
	 document.forms[0].letterDate.value=letterdate;
	 document.forms[0].txtTotalAmt.value=totalAmt;
	 
	 document.forms[0].particulars.value=particulars;
	 document.forms[0].authority.value=authorityname;
	 document.forms[0].authorityaddress.value=authorityaddress;
	
	 document.forms[0].office_id.value=accountunitid;
	 document.forms[0].office_id.text=officename;
	 
	 
	 
	 
	 
	 
	 var tbody=document.getElementById("grid_body");    
	 var len=baseResponse.getElementsByTagName("slno").length;
	// alert(len);
	 for(var k=0;k<len;k++)
	 {
		 
	 var accheadcode=baseResponse.getElementsByTagName("accheadcode")[k].firstChild.nodeValue;
	 var accheaddes=baseResponse.getElementsByTagName("accheaddes")[k].firstChild.nodeValue;
	 var type=baseResponse.getElementsByTagName("type")[k].firstChild.nodeValue;
	 var amount=baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
	 var SUB_LEDGER_TYPE_CODE=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[k].firstChild.nodeValue;
	 var SUB_LEDGER_CODE=baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[k].firstChild.nodeValue;
	 var cashbkyear=baseResponse.getElementsByTagName("year")[k].firstChild.nodeValue;
	 var remaks=baseResponse.getElementsByTagName("remaks")[k].firstChild.nodeValue;
	 var letterno=baseResponse.getElementsByTagName("letterno")[k].firstChild.nodeValue;
	 var letterdate=baseResponse.getElementsByTagName("letterdate")[k].firstChild.nodeValue;
	 var cashbkmonth=baseResponse.getElementsByTagName("month")[k].firstChild.nodeValue;
	 var receiptno=baseResponse.getElementsByTagName("receiptno")[k].firstChild.nodeValue;
	 
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
       H_code.value=accheadcode;
       cell2.appendChild(H_code);
       var currentText=document.createTextNode(accheadcode+"-"+accheaddes);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
       
  cell2=document.createElement("TD"); 
       var CR_DR_type=document.createElement("input");
       CR_DR_type.type="hidden";
       CR_DR_type.name="CR_DR_type";
       CR_DR_type.value=type;
       cell2.appendChild(CR_DR_type);
       var currentText=document.createTextNode(type);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
  
  cell2=document.createElement("TD");
       var SL_type=document.createElement("input");
       SL_type.type="hidden";
       SL_type.name="SL_type";
       SL_type.value=SUB_LEDGER_TYPE_CODE;
       cell2.appendChild(SL_type);
       var currentText=document.createTextNode(SUB_LEDGER_TYPE_CODE);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
 
  cell2=document.createElement("TD");
       var SL_code=document.createElement("input");
       SL_code.type="hidden";
       SL_code.name="SL_code";
       SL_code.value=SUB_LEDGER_CODE;
       cell2.appendChild(SL_code);
       var currentText=document.createTextNode(SUB_LEDGER_CODE);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
 
       
       cell2=document.createElement("TD"); 
       var cb_year=document.createElement("input");
       cb_year.type="hidden";
       cb_year.name="cashbkyear";
       cb_year.value=cashbkyear;
       cell2.appendChild(cb_year);
       var currentText=document.createTextNode(cashbkyear);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
       
       cell2=document.createElement("TD"); 
       var cb_month=document.createElement("input");
       cb_month.type="hidden";
       cb_month.name="cashbkmonth";
       cb_month.value=cashbkmonth;
       cell2.appendChild(cb_month);
       var currentText=document.createTextNode(cashbkmonth);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
       
       cell2=document.createElement("TD"); 
       var receipt_no=document.createElement("input");
       receipt_no.type="hidden";
       receipt_no.name="receiptNo";
       receipt_no.value=receiptno;
       cell2.appendChild(receipt_no);
       var currentText=document.createTextNode(receiptno);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);  
       
      cell2=document.createElement("TD");
       var ho_no=document.createElement("input");
       ho_no.type="hidden";
       ho_no.name="ref_no";
       ho_no.value=letterno;
       cell2.appendChild(ho_no);
       var currentText=document.createTextNode(letterno);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
       
   cell2=document.createElement("TD");
       var ho_date=document.createElement("input");
       ho_date.type="hidden";
       ho_date.name="ref_date";
       ho_date.value=letterdate;
       cell2.appendChild(ho_date);
       var currentText=document.createTextNode(letterdate);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
       
  cell2=document.createElement("TD"); 
       var sl_amt=document.createElement("input");
       sl_amt.type="hidden";
       sl_amt.name="sl_amt";
       sl_amt.value=amount;
       cell2.appendChild(sl_amt);
       var currentText=document.createTextNode(amount);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
   
   cell2=document.createElement("TD");
  
       var particular=document.createElement("input");
       particular.type="hidden";
       particular.name="sl_particular";
       particular.value=remaks;
       cell2.appendChild(particular);
       var currentText=document.createTextNode(remaks);
       cell2.appendChild(currentText);
       mycurrent_row.appendChild(cell2);
       
   

  tbody.appendChild(mycurrent_row);
    // clearall();
     seq=seq+1;
   
	 
	 }
	
	 
	}
	else 
	{
		alert("fail to load");
	}
	// document.forms[0].txtsub_Amount.value=totalAmt;
	 
}

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function load_grid(cmd)
{

        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
                alert("Enter A/c Head Code");
                return false;
        }                
             
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
                alert("Enter the Amount in Details Part ");
                return false;    
        }
        if(document.getElementById("office_id").value.length==0)
        {
                alert("Select Office Id ");
                return false;   
                              
        }
        if(document.getElementById("letterNo").value=="")
        {
        	 alert("Enter Letter No ");
             return false;  
                
        }
        if(document.getElementById("letterDate").value=="")
        {
        	 alert("Enter Letter Date ");
             return false;  
                
        }
        
        var tbody=document.getElementById("grid_body");                            
        var t=0;            
        var items=new Array();
        
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;   
        
        if(document.forms[0].rad_sub_CR_DR[0].checked==true)
        	items[2]=document.forms[0].rad_sub_CR_DR[0].value;
        else if(document.forms[0].rad_sub_CR_DR[1].checked==true)
        	items[2]=document.forms[0].rad_sub_CR_DR[1].value;  
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
       
        items[7]=document.getElementById("office_id").value;
        items[8]=document.getElementById("office_id").options[document.getElementById("office_id").selectedIndex].text;
        items[9]=document.getElementById("txtsub_Amount").value;
        items[10]=document.getElementById("txtParticular").value;
        items[11]=document.getElementById("letterNo").value;
        items[12]=document.getElementById("letterDate").value;
       
       
       
        if(cmd=="ADD_GRID")
        {
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
                var Office_code=document.createElement("input");
                Office_code.type="hidden";
                Office_code.name="OfficeCode";
                Office_code.value=items[7];
                cell2.appendChild(Office_code);
                var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD"); 
                var sl_amt=document.createElement("input");
                sl_amt.type="hidden";
                sl_amt.name="sl_amt";
                sl_amt.value=items[9];
                cell2.appendChild(sl_amt);
                var currentText=document.createTextNode(items[9]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
                cell2=document.createElement("TD"); 
                var par_code=document.createElement("input");
                par_code.type="hidden";
                par_code.name="remarkss";
                par_code.value=items[10];
                cell2.appendChild(par_code);
                var parti=document.createTextNode(items[10]);                         
                cell2.appendChild(parti);       
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD"); 
                var let_no=document.createElement("input");
                let_no.type="hidden";
                let_no.name="letterno";
                let_no.value=items[11];
                cell2.appendChild(let_no);
                var letterNo=document.createTextNode(items[11]);                         
                cell2.appendChild(letterNo);       
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD"); 
                var let_date=document.createElement("input");
                let_date.type="hidden";
                let_date.name="letterdate";
                let_date.value=items[12];
                cell2.appendChild(let_date);
                var letterDate=document.createTextNode(items[12]);                         
                cell2.appendChild(letterDate);       
                mycurrent_row.appendChild(cell2);
                
                tbody.appendChild(mycurrent_row);
             //   clearall();
                seq=seq+1;
              
        }
    
}
function up()
{

	var tbody=document.getElementById("grid_body");                            
    var t=0;            
    var items=new Array();
    
    items[0]=document.getElementById("txtAcc_HeadCode").value;
    items[1]=document.getElementById("txtAcc_HeadDesc").value;   
    
    if(document.forms[0].rad_sub_CR_DR[0].checked==true)
    	items[2]=document.forms[0].rad_sub_CR_DR[0].value;
    else if(document.forms[0].rad_sub_CR_DR[1].checked==true)
    	items[2]=document.forms[0].rad_sub_CR_DR[1].value;  
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
    items[7]=document.getElementById("office_id").value;
   
    items[8]=document.getElementById("office_id").options[document.getElementById("office_id").selectedIndex].text;
    items[9]=document.getElementById("txtsub_Amount").value;
 
    items[10]=document.getElementById("txtParticular").value;
  
    items[11]=document.getElementById("letterNo").value;
  
    items[12]=document.getElementById("letterDate").value;
  
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
    try{rcells.item(5).lastChild.nodeValue=items[8];}catch(e){}

    try{rcells.item(6).firstChild.value=items[9];}catch(e){}
    try{rcells.item(6).lastChild.nodeValue=items[9];}catch(e){}
    
    try{rcells.item(7).firstChild.value=items[10];}catch(e){}
    try{rcells.item(7).lastChild.nodeValue=items[10];}catch(e){}
    
    try{rcells.item(8).firstChild.value=items[11];}catch(e){}  //rcells.item(6).childNodes.item(1).value
    try{rcells.item(8).lastChild.nodeValue=items[11];}catch(e){} 
    
    try{rcells.item(9).firstChild.value=items[12];}catch(e){}
    try{rcells.item(9).lastChild.nodeValue=items[12];}catch(e){} 
    
     document.forms[0].cmdupdate.style.display='none';        
     document.forms[0].cmdadd.style.display='block';                   
    alert("Record Updated successfully");
    //clearall();
	
} 


function loadtableValues(scod)
{
   //alert(scod);
	   com_id=scod;   
        var r=document.getElementById(scod);
        var rcells=r.cells;
     //   alert(rcells);
      
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        doFunction('checkCode','null');   
             
        if(rcells.item(2).firstChild.value=="CR")
        		document.forms[0].rad_sub_CR_DR[0].checked=true;
        else if(rcells.item(2).firstChild.value=="DR")
        		document.forms[0].rad_sub_CR_DR[1].checked=true;
                        
        sltype=rcells.item(3).firstChild.value;
       
        slcode=rcells.item(4).firstChild.value;
      
        try{document.getElementById("txtEmpID_trs").value=slcode;}catch(e){}  
        doFunction('Load_SL_Code',sltype);
        
        offId=rcells.item(5).firstChild.value;
        try{document.getElementById("office_id").value=offId;}catch(e){}
        try{document.getElementById("txtsub_Amount").value=rcells.item(6).firstChild.value;}catch(e){}
     
        try{document.getElementById("txtParticular").value=rcells.item(7).firstChild.value;}catch(e){} 
        try{document.getElementById("letterNo").value=rcells.item(8).firstChild.value;}catch(e){}
        try{document.getElementById("letterDate").value=rcells.item(9).firstChild.value;}catch(e){}  
          
        document.forms[0].cmdadd.style.display='none';     
        document.forms[0].cmddelete.style.display='block';
        document.forms[0].cmddelete.disabled=false;
        document.forms[0].cmdupdate.style.display='block';    
 	setTimeout('document.getElementById("cmbSL_type").value=sltype',900); 
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



function numbersonly(e)
{
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false;
        }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////// For Edit New



function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
	        var tbody=document.getElementById("mytable");
	        var r=document.getElementById(com_id);
	        var ri=r.rowIndex;
	        tbody.deleteRow(ri);
	        //clearall();
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
		        //clearall();
  }

/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////
function loadTable(scod)
{
	//alert("enter into the load");
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
       // clearall();
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
      //  alert("**********&&&"+rcells.item(7).firstChild.value);
        try{
        	
        	//document.getElementById("txtReceipt_No").text=rcells.item(7).firstChild.value;
        document.getElementById("txtReceipt_No").options[document.getElementById("txtReceipt_No").selectedIndex].text=rcells.item(7).firstChild.value;
        document.getElementById("txtReceipt_No").options[document.getElementById("txtReceipt_No").selectedIndex].value=rcells.item(7).firstChild.value;
        
        
        
        }catch(e){}
     //   alert(document.getElementById("txtReceipt_No").value=rcells.item(7).firstChild.value);
       
        
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


