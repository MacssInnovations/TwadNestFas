var subvou1;
var seq=0;
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

function paydisp()
{	
	var code=document.getElementById("txtEmpID_trs").value;
	//alert("sssssssssss"+code);
    var url="../../../../../HR_Sanc_Proc_Mul?Command=code&code="+code;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
       handleResponse1(req);
    }   
            req.send(null);
}

function  callminor()
{
        var major1=document.forms[0].majorType.value;
        var url="../../../../../HR_Sanc_Proc_Mul?Command=minorType&major2="+major1;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse1(req);
        }   
                req.send(null);   
}
function loadNote_noSanccp(){
	var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	  var txtCBYear=document.forms[0].txtCB_Year.value;
	   var txtCBMonth=document.forms[0].txtCB_Month.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;	
	var url="../../../../../HR_Sanc_Proc_Mul?Command=loadNote_noSanc&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCBYear="+txtCBYear+
	 "&txtCBMonth="+txtCBMonth;
	 alert(url);
var req=getTransport();
alert(req);
req.open("GET",url,true); 
alert('testing');
req.onreadystatechange=function()
{
	alert('testing111');
	 if(req.readyState==4)
	    { 
	        if(req.status==200)
	        { 
	        	//alert('get value');

	   		 var sanc_noCmb = document.forms[0].sanc_no;
	            document.forms[0].sanc_no.length=0;
	            var Sanccode = baseResponse.getElementsByTagName("SANCTION_PROCEEDING_NO");  
	        
	   	   	 if(Sanccode.length>0)
	   	   	 {
	   	   		var sanc_noType = document.getElementById("sanc_no");
	   	   	sanc_noType.length=1;
	               for(var i=0;i<Sanccode.length;i++)
	                  {
	               	  if(Sanccode.length==1)
	               	  {
	               		  var opt1 = document.createElement('option');
	                         opt1.value = 0;
	                         opt1.innerHTML ="select"; 
	                         sanc_noType.appendChild(opt1);
	               		  
	               	  }
	               		  
	               	     var opt1 = document.createElement('option');
	                        opt1.value = SANCTION_PROCEEDING_NO[i].firstChild.nodeValue;
	                        opt1.innerHTML = SANCTION_PROCEEDING_NO[i].firstChild.nodeValue; 
	                        sanc_noType.appendChild(opt1);                 
	                  }  
	            
	   	   	 } 
	        }
	    }
};
	        
}
function loadDetailsMUL(val){
	
	var cmbAcc_UnitCode=document.forms[0].cmbAcc_UnitCode.value;
	  var txtCBYear=document.forms[0].txtCB_Year.value;
	   var txtCBMonth=document.forms[0].txtCB_Month.value;
	var cmbOffice_code=document.forms[0].cmbOffice_code.value;	
	// alert(cmbOffice_code);
	 var url="../../../../../HR_Note?Command=getDetailssing&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCBYear="+txtCBYear+
	 "&txtCBMonth="+txtCBMonth+"&NoteNo="+val;
	 alert(url);
var req=getTransport();
req.open("GET",url,true); 
req.onreadystatechange=function()
{
	 if(req.readyState==4)
	    { 
	        if(req.status==200)
	        { 
	        	
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	          //  alert(baseResponse);
	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            var Command=tagcommand.firstChild.nodeValue;
	          if(Command=="getDetailssing")
	        	  {
	        	  laodgetDetails(baseResponse);
	        	  
	        	  }
	        }
	    }
};   
    req.send(null);    
	
}

function laodgetDetails(baseResponse){
	//alert('test');
	   var BILL_MAJOR_TYPE_CODE = baseResponse.getElementsByTagName("BILL_MAJOR_TYPE_CODE")[0].firstChild.nodeValue;
 	   var BILL_MINOR_TYPE_CODE = baseResponse.getElementsByTagName("BILL_MINOR_TYPE_CODE")[0].firstChild.nodeValue;
 	   var BILL_SUB_TYPE_CODE = baseResponse.getElementsByTagName("BILL_SUB_TYPE_CODE")[0].firstChild.nodeValue;
 	   var NOTE_DATE = baseResponse.getElementsByTagName("NOTE_DATE")[0].firstChild.nodeValue;
 	   var NOTE_AMOUNT = baseResponse.getElementsByTagName("NOTE_AMOUNT")[0].firstChild.nodeValue;
 	   var NOTE_PREPARED_BY = baseResponse.getElementsByTagName("NOTE_PREPARED_BY")[0].firstChild.nodeValue;
 	   var ACCOUNT_HEAD_CODE = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
 	  // alert(ACCOUNT_HEAD_CODE);
 	   document.getElementById("majorType").value=BILL_MAJOR_TYPE_CODE;
 	  callminorCp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE);
 	// callsub()
 	   document.getElementById("ho_date").value=NOTE_DATE;
 	  // document.getElementById("hr_amt").value=NOTE_AMOUNT;
 	//  document.getElementById("hr_amt").value=NOTE_AMOUNT;
 	  //document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	//  document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	//  document.getElementById("txtRemarks").value=NOTE_PREPARED_BY;
	  load_headcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,ACCOUNT_HEAD_CODE);
	
}
function  callminorCp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE)
{
       
        var url="../../../../../HR_Sanc_Proc_Mul?Command=minorType&major2="+BILL_MAJOR_TYPE_CODE;
      // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {

      	  if(req.readyState==4)
      	    { 
      	        if(req.status==200)
      	        {             
      	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
      	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
      	            var Command=tagcommand.firstChild.nodeValue;
      	          if(Command=="minor")
                  {
      	        	 var res=  minortypechecking(baseResponse);
                  
        
          if (res==true)
        	  {
        	  
        	  document.forms[0].minorType.value=BILL_MINOR_TYPE_CODE;
        	  callsubcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE);
        	  }
                  }
      	        }
      	    }
        };   
                req.send(null);   
}
function  callsub(param)
{
	    var major1=document.forms[0].majorType.value;
        var url="../../../../../HR_Sanc_Proc_Mul?Command=subType&sub2="+param+"&major2="+major1;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse1(req);
        } ;  
                req.send(null);     
}
function  callsubcp(BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE)
{
        var url="../../../../../HR_Sanc_Proc_Mul?Command=subType&sub2="+BILL_MINOR_TYPE_CODE+"&major2="+BILL_MAJOR_TYPE_CODE;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        	 if(req.readyState==4)
        	    { 
        	        if(req.status==200)
        	        { 
        	            
        	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
        	            var Command=tagcommand.firstChild.nodeValue;
        	           
        	           
        	            	if(Command=="subb")
        	                {
        	                  var res=  subtypechecking(baseResponse);
        	            
             if (res==true)
           	  {
           	  
            	  document.forms[0].billsubtype.value=BILL_SUB_TYPE_CODE;
           	  }
        	                }
        	        }
        	    }
        } ;  
                req.send(null);     
}
function handleResponse1(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        { 
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="code")
            {
                loadpayName(baseResponse);
            }
            else if(Command=="minor")
            {
                minortypechecking(baseResponse);
            }
            else if(Command=="subb")
            {
                subtypechecking(baseResponse);
            }
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }  
        }
    }
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

function loadpayName(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;    
    if(flag=="success")
    {
         var desc=baseResponse.getElementsByTagName("desc")[0].firstChild.nodeValue;
         var desig=baseResponse.getElementsByTagName("designation")[0].firstChild.nodeValue;
         //alert("Payee name and designation:::"+desc+"designation:::::"+desig);
         document.getElementById("pay_name").value=desc+"   "+desig;
     }
     else if(flag=="failure")
     {
         document.getElementById("pay_name").value="";
      }
}

function chk()
{
	if(document.getElementById("txtEmpID_mas").value=="")
	{
		document.getElementById("txtEmpID_mas").value=0;	
	}
	if(document.getElementById("txtAcc_HeadCode").value=="")
	{
		document.getElementById("txtAcc_HeadCode").value=0;	
	}
	if(document.getElementById("hr").value=="")
	{
	    document.getElementById("hr").value=0;
	}
	if(document.getElementById("ref_no").value=="")
	{
	    document.getElementById("ref_no").value=0;
	}
}

function checkNull()
{
	var sub_vou;
	if(document.getElementById("sanc_date").value=="")
	   {
	       alert("Select Date in General");
	       document.getElementById("sanc_date").focus();
	       return false;
	   }
	else if(document.HR_SancProcMul.radActive1[0].checked==true)
		{document.getElementById("sub_vou").value=0;
		return true;}
	else if(document.HR_SancProcMul.radActive1[1].checked==true)
		{
		if(document.getElementById("sub_vou").value=="")
		{alert("Enter No.of sub-vouchers");
		return false;}
		else
		sub_vou=document.getElementById("sub_vou").value;	}
	
	   else
	         return true;
}

function minortypechecking(baseResponse)
{

		 var minorcmb = document.forms[0].minorType;
         document.forms[0].minorType.length=0;
         var minorcode = baseResponse.getElementsByTagName("minorcode");  
         var minordesc = baseResponse.getElementsByTagName("minordesc"); 
	   	 if(minorcode.length>0)
	   	 {
	   		var minorType = document.getElementById("minorType");
	   		minorType.length=1;
            for(var i=0;i<minorcode.length;i++)
               {
            	  if(minorcode.length==1)
            	  {
            		  var opt1 = document.createElement('option');
                      opt1.value = 0;
                      opt1.innerHTML ="select"; 
                      minorcmb.appendChild(opt1);
            		  
            	  }
            		  
            	     var opt1 = document.createElement('option');
                     opt1.value = minorcode[i].firstChild.nodeValue;
                     opt1.innerHTML = minordesc[i].firstChild.nodeValue; 
                     minorcmb.appendChild(opt1);                 
               }  
         
	   	 } 
	   	 return true;
}

function subtypechecking(baseResponse)
{

          var subcmb = document.forms[0].billsubtype;
          document.forms[0].billsubtype.length=0;
          var subcode = baseResponse.getElementsByTagName("subcode"); 
          var subdesc = baseResponse.getElementsByTagName("subdesc"); 
          for(var i=0; i<subcode.length; i++)
               {
	        	  if(subcode.length==1)
	        	  {
	        		  var opt1 = document.createElement('option');
	                  opt1.value = 0;
	                  opt1.innerHTML ="select"; 
	                  subcmb.appendChild(opt1);
	        		  
	        	  }
        	         var opt1 = document.createElement('option');
                     opt1.value = subcode[i].firstChild.nodeValue;
                     opt1.innerHTML = subdesc[i].firstChild.nodeValue; 
                     subcmb.appendChild(opt1);
               }  
          return true;
}

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
                return false;
            }
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

var window_BankAccNumber;
function Lists()
    {  
	  var sancdate=document.getElementById("sanc_date").value;
      var cmbAcc_UnitCode=document.HR_SancProcMul.cmbAcc_UnitCode.value;
      var cmbOffice_code=document.HR_SancProcMul.cmbOffice_code.value;
      var major_code = document.HR_SancProcMul.majorType.value;
      var minor_code = document.HR_SancProcMul.minorType.value;
     // alert("major_code********"+major_code+"minor_code*********"+minor_code);
      if((document.HR_SancProcMul.cmbAcc_UnitCode.value=="")||(document.HR_SancProcMul.cmbAcc_UnitCode.value.length<=0)||(document.HR_SancProcMul.cmbAcc_UnitCode.value=="0"))
      {
          alert("Select Accounting Unit Id");
          document.HR_SancProcMul.cmbAcc_UnitCode.focus();
          return false;
      }
      
      if((document.HR_SancProcMul.cmbOffice_code.value=="") || (document.HR_SancProcMul.cmbOffice_code.value.length<=0) || (document.HR_SancProcMul.cmbOffice_code.value=="0"))
      {
          alert("Select Office Code");
          document.HR_SancProcMul.cmbOffice_code.focus();
          return false;
      }   
	  if(document.getElementById("sanc_date").value=="")
	   {
	       alert("Select Date in General");
	       document.getElementById("sanc_date").focus();
	       return false;
	   }
	  if((document.HR_SancProcMul.majorType.value=="") || (document.HR_SancProcMul.majorType.value==0))
	   {
	       alert("Select Major type code");
	       document.HR_SancProcMul.majorType.focus();
	       return false;
	   }
	  if((document.HR_SancProcMul.minorType.value=="") || (document.HR_SancProcMul.minorType.value==0))
	   {
	       alert("Select Minor type code");
	       document.HR_SancProcMul.minorType.focus();
	       return false;
	   }
	 
         window_BankAccNumber= window.open("../jsps/HR_Sanc_Proc_Mul_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&sancdate="+sancdate+"&major_code="+major_code+"&minor_code="+minor_code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
         window_BankAccNumber.focus();
    }
    
function ADD_GRID()
{
        var tbody=document.getElementById("grid_body");
        
        var t=0;
        if(document.HR_SancProcMul.radActive1[1].checked==true)
		{
		if(document.getElementById("sub_vou").value=="")
		{alert("Enter No.of sub-vouchers");
		return false;}
		else
		sub_vou=document.getElementById("sub_vou").value;	}	
	   
        var items=new Array();
        items[0]=document.getElementById("billsubtype").value;
        if(document.HR_SancProcMul.radActive[0].checked==true)
            items[1]=document.HR_SancProcMul.radActive[0].value;
          else if(document.HR_SancProcMul.radActive[1].checked==true)
              items[1]=document.HR_SancProcMul.radActive[1].value;
          else if(document.HR_SancProcMul.radActive[2].checked==true)
              items[1]=document.HR_SancProcMul.radActive[2].value;
          
        items[2]=document.getElementById("txtEmpID_trs").value;
        items[3]=document.getElementById("pay_name").value;

        items[4]=document.getElementById("hr_amt").value;
        items[5]=document.getElementById("parti").value;
        
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;

        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTablevalues('"+mycurrent_row.id+"')";
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
                  var currentText=document.createTextNode(items[0]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
             cell2=document.createElement("TD"); 
                  var CR_DR_type=document.createElement("input");
                  CR_DR_type.type="hidden";
                  CR_DR_type.name="CR_DR_type";
                  CR_DR_type.value=items[1];
                  cell2.appendChild(CR_DR_type);
                   var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
                  var SL_type=document.createElement("input");
                  SL_type.type="hidden";
                  SL_type.name="SL_type";
                  SL_type.value=items[2];
                  cell2.appendChild(SL_type);
                   var currentText=document.createTextNode(items[2]+"-"+items[3]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
                  var SL_code=document.createElement("input");
                  SL_code.type="hidden";
                  SL_code.name="SL_code";
                  SL_code.value=items[4];
                  cell2.appendChild(SL_code);
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                    
             cell2=document.createElement("TD"); 
                  var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[5];
                  cell2.appendChild(sl_amt);
                  var currentText=document.createTextNode(items[5]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
              
       tbody.appendChild(mycurrent_row);
       clearall();
}

function loadTablevalues(scod)
{
	loadTable(scod);
}

function clearall()
{
	 document.getElementById("billsubtype").value="0";
     document.getElementById("txtEmpID_trs").value="0";
     document.getElementById("pay_name").value="";
     document.getElementById("hr_amt").value="";
     document.getElementById("parti").value="";
     document.getElementById("hr").value="";
     document.getElementById("frm_date").value="";
     document.getElementById("to_date").value="";
     document.getElementById("sanc_amt").value="";
     document.getElementById("ref_no").value="";
     document.getElementById("ref_date").value="";
     document.getElementById("sub_vou").value="";
}

function clrForm()
{
	document.getElementById("billsubtype").value="0";
     document.getElementById("txtEmpID_trs").value="0";
     document.getElementById("pay_name").value="";
     document.getElementById("hr_amt").value="";
     document.getElementById("parti").value="";
     document.getElementById("hr").value="";
     document.getElementById("frm_date").value="";
     document.getElementById("to_date").value="";
     document.getElementById("sanc_amt").value="";
     document.getElementById("ref_no").value="";
     document.getElementById("ref_date").value="";
     document.getElementById("sub_vou").value="";
     document.getElementById("majorType").value="0";
  
  		//alert("inside delete function****"); 
  		var tbody=document.getElementById("mytable");
  		var gridbody=document.getElementById("grid_body");
  		//alert("value of gridbody:::::"+gridbody.rows.length);
  		var j=gridbody.rows.length;
  		//alert("value of j:::::::"+j);
  		for(var i=1;i<=j;i++)
  			{
  					//alert("inside for loop::::"+i);
  					var r=document.getElementById(i);
  					//alert("value of row:::::::::"+r);
  					var ri=r.rowIndex;
  					//alert("Value of Ri ::::"+ri);
  					tbody.deleteRow(ri);
  				  //alert("value of i::::::::::"+i);
  			}
  //alert("value of i::::::::::"+i);
	//alert("deleting the main ");
     document.getElementById("minorType").value="0";
     document.getElementById("sanc").value="0";
     document.getElementById("txtEmpID_mas").value="";
     document.getElementById("sanc_no").value="";
     document.getElementById("sanc_date").value="";
     document.getElementById("tot_amt").value="";
     document.getElementById("txtAcc_HeadCode").value="";
     document.getElementById("txtAcc_HeadDesc").value="";
     document.getElementById("bud_pro").value="";
     document.getElementById("bud_spent").value="";
     document.getElementById("amt").value="";
     document.getElementById("bal_amt").value="";
     document.getElementById("ac_unit").value="";
     document.getElementById("txtRemarks").value="";
    
     
     
}

function loadTable(scod)
{
	 com_id=scod;                                   // to identify in UPDATE_GRID ,which row loaded 
       // clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
       
        try {document.getElementById("billsubtype").value=rcells.item(1).firstChild.value;}catch(e){}
        
        if(rcells.item(2).firstChild.value=="E")
        	document.HR_SancProcMul.radActive[0].checked=true;
        else if(rcells.item(2).firstChild.value=="U")
        	document.HR_SancProcMul.radActive[1].checked=true;
        else if(rcells.item(2).firstChild.value=="P")
            document.HR_SancProcMul.radActive[2].checked=true; 
            
        try{document.getElementById("txtEmpID_trs").value=rcells.item(3).firstChild.value;} catch(e){document.getElementById("txtEmpID_trs").value="";}
        paydisp();
       
        try{document.getElementById("hr_amt").value=rcells.item(4).firstChild.value;} catch(e){document.getElementById("hr_amt").value="";}
        
        try{document.getElementById("parti").value=rcells.item(5).firstChild.value;} catch(e){document.getElementById("parti").value="";} 
              
	    document.HR_SancProcMul.cmdupdate.style.display='block';
	    document.HR_SancProcMul.cmddelete.disabled=false;
	    document.HR_SancProcMul.cmdadd.style.display='none';
}

function update_GRID()
{      
        var items=new Array();
       
        items[0]=document.getElementById("billsubtype").value;
        if(document.HR_SancProcMul.radActive[0].checked==true)
            items[1]=document.HR_SancProcMul.radActive[0].value;
        else if(document.HR_SancProcMul.radActive[1].checked==true)
              items[1]=document.HR_SancProcMul.radActive[1].value;
        else if(document.HR_SancProcMul.radActive[2].checked==true)
              items[1]=document.HR_SancProcMul.radActive[2].value;alert(items[1]);
          
        items[2]=document.getElementById("txtEmpID_trs").value;
        items[3]=document.getElementById("pay_name").value;

        items[4]=document.getElementById("hr_amt").value;
        items[5]=document.getElementById("parti").value;
        
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
               try{rcells.item(1).firstChild.value=items[0];}catch(e){}
               try{rcells.item(1).lastChild.nodeValue=items[0];}catch(e){}
               
               try{rcells.item(2).firstChild.value=items[1];}catch(e){}               
               try{rcells.item(2).lastChild.nodeValue=items[1];}catch(e){}
             
               try{rcells.item(3).firstChild.value=items[2];}catch(e){}
               try{rcells.item(3).lastChild.nodeValue=items[2]+"-"+items[3];}catch(e){}
              
                try{rcells.item(4).firstChild.value=items[4];}catch(e){}
                try{rcells.item(4).lastChild.nodeValue=items[4];}catch(e){}
                        
                try{rcells.item(5).firstChild.value=items[5];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[5];}catch(e){}
             
		        alert("Record Updated");
		        clearall();
  }

function filter_real(evt,item,n,pre)
{
        var charCode = (evt.which) ? evt.which : evt.keyCode;
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

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
	    if(blr_flag==1)                 // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
	    {
	            call_clr();
	            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	            cmbOffice_code=document.getElementById("cmbOffice_code").value;
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