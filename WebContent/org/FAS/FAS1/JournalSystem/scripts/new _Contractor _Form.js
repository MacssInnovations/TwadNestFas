

var idnames ='';
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag; 
var mycurrent_row="";var assetcode111="";
//--------------------------------------------------------------------------------
window.onunload=function()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
    if (winemp && winemp.open && !winemp.closed) winemp.close();

}; 

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
function changecode()
{
	
	document.getElementById("accounthaed").style.display = "block";
	document.getElementById("accounthaed1").style.display = "none";
	
}
function changecode1(){
	document.getElementById("accounthaed1").style.display = "block";
	document.getElementById("accounthaed").style.display = "none";
	
}
/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
        com_id=scod;                                    
        // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        
        
       // document.FasAcc_Headform.cmdadd.disabled=true;
       //document.getElementById("txtAcc_HeadCode").readOnly=true;                // do not change the Account Head 
       //text_field.readOnly=true;
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
          doFunction('checkCode','null');
         try{com_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){com_cmbSL_type=""}
        try{com_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){com_cmbSL_Code=""}     
        
          if(com_cmbSL_type==5)
        {         
                document.getElementById("txtOfficeID_trs").value=com_cmbSL_Code;                
                job_flag=false;             
        }
        if(com_cmbSL_type==7)
        {
                document.getElementById("txtEmpID_trs").value=com_cmbSL_Code;
                emp_flag=false;            
        } 
              
                doFunction('Load_SL_Code',com_cmbSL_type);
                
                setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 
        setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
        
         if(rcells.item(2).firstChild.value=="CR")
         document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmJournal_Bill_Create.rad_sub_CR_DR[1].checked=true;
         
       //try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_NO").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_date").value=rcells.item(6).firstChild.value;}catch(e){}
       
        try{document.getElementById("txtBill_type").value=rcells.item(7).firstChild.value;}catch(e){}
       
        var nex=rcells.item(7).firstChild.nextSibling  
        try{document.getElementById("txtAgree_No").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtAgree_Date").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtsub_Amount").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       
       
    document.frmJournal_Bill_Create.cmdupdate.style.display='block';path
    document.frmJournal_Bill_Create.cmddelete.disabled=false;
    document.frmJournal_Bill_Create.cmdadd.style.display='none';
}
function check_leng1(val)
{
	//alert("");
	/*alert(""+val);
if(val.length>=250)
return false;*/
}


function accntdesc(val) {
	//alert(val);

	 var schno=document.getElementById("Schemename").value;
	
    var url= "../../../../../new_Contractor_LJV?command=loadAccDesc&txtAcc_HeadCode="+schno;
		
    // alert(url);
  var xmlrequest=getTransport();
	    xmlrequest.open("POST",url,true); 
	    xmlrequest.onreadystatechange=function()
	    
	    {
	    	LoadBankAccountNumberRes(xmlrequest);
	    };
	   
	    xmlrequest.send(null);
}   


function loadSchdebitcode(){
	 var schno=document.getElementById("Schemename").value;
	 var schnoDesc=document.getElementById("Schemename").options[document.getElementById("Schemename").selectedIndex].text;
	var cmbAcc_UnitCode=	document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=	document.getElementById("cmbOffice_code").value;
	    var url= "../../../../../new_Contractor_LJV?command=loadSchdebitcode&schno="+schno+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&schnoDesc="+schnoDesc;
			
	     alert(url);
	  var xmlrequest=getTransport();
		    xmlrequest.open("POST",url,true); 
		    xmlrequest.onreadystatechange=function()
		    
		    {
		    	LoadBankAccountNumberRes(xmlrequest);
		    };
		   
		    xmlrequest.send(null);
}
var winemp;
var my_window;
function loadscpage()
{

	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	 winemp= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/new_contract_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"Scheme List","status=1,height=1000,width=1000,resizable=YES, scrollbars=yes"); 
	 winemp.moveTo(250,250);  
	 winemp.focus();
}
function dofunc(schNO,hid_Unit,hid_Office)
{
	
document.getElementById("sch_code").value=schNO;
document.getElementById("cmbAcc_UnitCode").value=hid_Unit;
document.getElementById("cmbOffice_code").value=hid_Office;

ListSub('loadSchdebitcode','null');

}
//this is the function to close the employee popup windows...
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();

}

function ListSub(command,param)
{
	if(command=="loadSchdebitcode")
    {
//	alert("Welcome to ListSub function ")
		var uni=document.getElementById("cmbAcc_UnitCode").value;
	var offi=document.getElementById("cmbOffice_code").value;
	var schNo=document.getElementById("sch_code").value;
	var txtCrea_date=document.getElementById("txtCrea_date").value;
	var url= "../../../../../new_Contractor_LJV?command=loadSchdebitcode&schno="+schNo+"&cmbAcc_UnitCode="+uni+"&cmbOffice_code="+offi+"&txtCrea_date="+txtCrea_date;
		

	  var xmlrequest=getTransport();
		    xmlrequest.open("POST",url,true); 
		    xmlrequest.onreadystatechange=function()
		    
		    {
		    	
		    	 if(xmlrequest.readyState==4)
{ 
	
    if(xmlrequest.status==200)
    {  

   	 
    	   var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
    	   
           var tagcommand=baseResponse.getElementsByTagName("command")[0];
           var Command=tagcommand.firstChild.nodeValue;
           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            	{
 	   var tbody=document.getElementById("grid_body2");
        try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
	 var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;
	 
	       
     //alert("length"+leng);
			lll=1;
			seq=0;
		   tbody=document.getElementById("grid_body2");
			var item = new Array();

			for(var k=0;k<leng;k++)
			{

				item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
				
				item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
				
				item[3] =baseResponse.getElementsByTagName("cmbAcc_UnitCode")[0].firstChild.nodeValue;
			
				item[4] =baseResponse.getElementsByTagName("cmbOffice_code")[0].firstChild.nodeValue;
			
				item[5] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE")[k].firstChild.nodeValue;
			
				item[6] =baseResponse.getElementsByTagName("sub_ledger_name")[k].firstChild.nodeValue;
				
				item[7] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[k].firstChild.nodeValue;
			
				item[8] =baseResponse.getElementsByTagName("sub_ledger_type_desc")[k].firstChild.nodeValue;
			
				
				document.getElementById("sch_desc").value=item[8];
	            
				
				
				
				
				//item[3] =baseResponse.getElementsByTagName("slType")[k].firstChild.nodeValue;
			var	mycurrent_row=document.createElement("TR");
				mycurrent_row.id=item[1];                     
				var cell1 = document.createElement("TD");
				var dateofentry1=document.createElement("input");
				dateofentry1.type="hidden";
				dateofentry1.name="seq";
				dateofentry1.id=seq;
				dateofentry1.value=seq;
				var dateofentry = document.createTextNode(seq+1);
				dateofentry.size=7;
				
				cell1.appendChild(dateofentry);
				cell1.appendChild(dateofentry1);
				mycurrent_row.appendChild(cell1);

				var cell2 = document.createElement("TD");
				var assetcode1=document.createElement("input");
				assetcode1.type="hidden";
				assetcode1.name="ACCOUNT_HEAD_code";
				assetcode1.id="ACCOUNT_HEAD_code";
				assetcode1.value=item[1];
				var assetcode = document.createTextNode(item[1]+'-'+item[2]);
				assetcode.size=7;
				cell2.appendChild(assetcode);
				cell2.appendChild(assetcode1);
				//..cell2.align="right";
				mycurrent_row.appendChild(cell2);
				
				var cell2 = document.createElement("TD");
				cell2.setAttribute('style', 'display:none;');
				var assetcode1=document.createElement("input");
				assetcode1.type="hidden";
				assetcode1.name="CR_DR_type1";
				assetcode1.id="CR_DR_type1";
				assetcode1.value="DR";
			/*	var assetcode = document.createTextNode("DR");
				assetcode.size=7;
				cell2.appendChild(assetcode);*/
				cell2.appendChild(assetcode1);
				//cell2.align="right";
				mycurrent_row.appendChild(cell2);
				
			/*	var cell2 = document.createElement("TD");
				var assetcode1=document.createElement("input");
				assetcode1.type="hidden";
				assetcode1.name="SLtype1";
				assetcode1.id="SLtype1";
				assetcode1.value="10";
				var assetcode = document.createTextNode("Project");
				assetcode.size=7;
				cell2.appendChild(assetcode);
				cell2.appendChild(assetcode1);
				cell2.align="right";
				mycurrent_row.appendChild(cell2);*/
				
				
				
			
				
				
				var cell2 = document.createElement("TD");
				var sel=document.createElement("select");
				sel.id="sel_debtype"+seq;
				sel.name="sel_debtype"+seq;
				var option=document.createElement("OPTION");
			//	sel.setAttribute('onclick','javascript:chgeSlcode(this.value,'+seq+','+item[3]+','+item[4]+')');
			/*	var option=document.createElement("OPTION");
				option.value="";
				option.text="--Select--";
				sel.appendChild(option);*/
			/*	var slcodelen =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k).length;
			
				for(var jj=0;jj<slcodelen;jj++)
					{
					var option=document.createElement("OPTION");
  					option.value=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k)[jj].firstChild.nodeValue;
  			//	alert(baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue);
  					option.text=baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue;
  					sel.appendChild(option); 
					}*/
				option.value=item[5];
  	  			option.text=item[6];
  	  					sel.appendChild(option); 	
				
				
				
				cell2.appendChild(sel);
				mycurrent_row.appendChild(cell2);
				
				
				
				var cell2 = document.createElement("TD");
				var sel=document.createElement("select");
				sel.id="SLtypecode1"+seq;
				sel.name="SLtypecode1"+seq;
				sel.setAttribute('style','width:400px');

				var option=document.createElement("OPTION");
				option.value=item[7];
				option.text=item[8];
				sel.appendChild(option);
			
				
				cell2.appendChild(sel);
				
				mycurrent_row.appendChild(cell2);
				
		
				
				var cell2 = document.createElement("TD");
				var assetcode1=document.createElement("input");
				assetcode1.type="text";
				assetcode1.name="sl_amt1";
				assetcode1.id="sl_amt1"+lll;
				assetcode.size=7;
				cell2.appendChild(assetcode1);
				cell2.align="right";
				mycurrent_row.appendChild(cell2);
//				alert("s2*"+assetcode1.id);
				var cell2 = document.createElement("TD");
				var assetcode1=document.createElement("input");
				assetcode1.type="text";
				assetcode1.name="particular1";
				assetcode1.id="particular1";
				
				var assetcode = document.createTextNode("");
				assetcode.size=7;
				cell2.appendChild(assetcode);
				cell2.appendChild(assetcode1);
				cell2.align="right";
				mycurrent_row.appendChild(cell2);
				
				
				tbody.appendChild(mycurrent_row);
				lll++;
				 seq++;
		
    		
    		}         
            	}
            else if(flag=="failure1")
            	{
            	alert("Account Head Code doesn't Exist");
            	}
            else
            	{
            	alert("No Data found");
            	}
            	

	
		
    }
	
}
		    };
		   
		    xmlrequest.send(null);
	
    }

}





function loadsch(un,off){
	 /*var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;*/
	 var cmbAcc_UnitCode=un;
	 var cmbOffice_code=off;
	    var url= "../../../../../new_Contractor_LJV?command=loadSchEME&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
			
	    // alert(url);
	  var xmlrequest=getTransport();
		    xmlrequest.open("POST",url,true); 
		    xmlrequest.onreadystatechange=function()
		    
		    {
		    	LoadBankAccountNumberRes(xmlrequest);
		    };
		   
		    xmlrequest.send(null);	
}
function getmyvalue(){

	for(var i=0;i<8;i++){
		
	 var r=document.getElementById("trow"+i);

     var rcells=r.cells;
  if( rcells.item(3).firstChild.value==11)
 {
	//alert(rcells.item(4).firstChild.length);
     rcells.item(3).firstChild.value="";
   //  rcells.item(4).firstChild.value="";
     rcells.item(4).firstChild.length=0;
 }
	
	}
}
	//alert(value);
	
	/*var selectedText = ele.options[ele.selectedIndex].text;
	//alert(selectedText);
	
	var url= "../../../../../new_Contractor_LJV?combotype="+value+"&command=no";
	var xmlhttp;
	if (window.XMLHttpRequest)
	  {
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
	   // document.getElementById("").innerHTML=xmlhttp.responseText;
	    }
	}
	xmlhttp.open("POST",url,true);
	xmlhttp.send();
	 var obj=document.getElementById("cmbSL_Code4");  
	var length = obj.options.length;
	
	for (i = 1; i < length; i++) {
		obj.options[i] = null;
	}
	   
         opt = document.createElement("option");
         opt.appendChild(document.createTextNode(value));
       
         opt.value = value;
         opt.text=selectedText;
         
         obj.appendChild(opt);
   
	seq++;*/
	
	/* var r=document.getElementById("trow"+s);
     var rcells=r.cells;
  
 
     rcells.item(3).firstChild.value="";
     rcells.item(4).firstChild.value="";}
	*/
	
	


 function subledgercode(ele,s){
	
	var chk=0;
	/*	 var txtAcc_HeadCode=ele;
	 alert(txtAcc_HeadCode);
	
		
	    var url= "../../../../../new_Contractor_LJV?command=loadsubDesc&txtAcc_HeadCode="+txtAcc_HeadCode+"&sel="+sel;
			
	
	  var xmlrequest=getTransport();
		    xmlrequest.open("POST",url,true); 
		    xmlrequest.onreadystatechange=function()
		    
		    {
		    	LoadBankAccountNumberRes(xmlrequest);
		    };
		   
		    xmlrequest.send(null);*/
	 
	// alert('')


		cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		cmbOffice_code=document.getElementById("cmbOffice_code").value;
			addtional_field_value=0;
		 cmbSL_type=ele;
		
		year11=0;
		month1=0;
	         var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
	         var other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
	       
	   if(cmbMas_SL_type==""){
		   alert('First Select General Journal Type .. ');
		chk=1;   
	   }
	   else{
	      
	         if(cmbSL_type==cmbMas_SL_type)
			 {
			 if(confirm(" Select valid General Sub ledger Code ... "))
			 {
				 chk=2;
				 
			 
			 }else
				 {
				 chk=1;   
				 document.getElementById("cmbMas_SL_Code").value="";
				 document.getElementById("cmbMas_SL_Code").focus();
				 }
			 }else{
				 chk=2; 
			 }
	   }
	
	   if(chk==2)
	   {   var url="../../../../../Receipt_SL.view?Command=Load_SL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+
	         "&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	         "&cmbOffice_code="+cmbOffice_code+"&addtional_field_value="+addtional_field_value+"&month1="+month1+"&year11="+year11;
	      // alert("year11   "+url);
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
	        	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	        	            
	        	            if(flag=="success")
	        	              {
	       		
	   			document.getElementById("SL_code"+s).length=0;
	   		  select1 = document.getElementById("SL_code"+s);
	   		 var option = document.createElement( 'option' );
	   		 option.value ="";
	   		option.text ="--Select--";
	   		
	   		select1.setAttribute('style','width:400px');
	   		
	   	    select1.appendChild( option );
	          	 var leng = baseResponse.getElementsByTagName("cid").length;
	             select = document.getElementById("SL_code"+s);
	            		for(var k = 0;k<leng ;k++ ){
	            			
	            	   
	            	      if(cmbSL_type==cmbMas_SL_type){
	            	    	  if(baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue==other_dept_off_alias_id)
	            	      {
	            	    		  
	            	    		    option.value =baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue; 
		            	        	option.text = baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
		            	        select.appendChild( option );	  
	            	      }
	            	    	
	            	      }else{
	            	    	   var option = document.createElement( 'option' );
	            	        option.value =baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue; 
	            	        	option.text = baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
	            	       select.appendChild( option );
	            	      }
	            	    
	            			
	          	}
	        	              }	
	        	              }
	        	        }
	        };   
	                req.send(null);
	    
	   }
	   else{
		   var r=document.getElementById("trow"+s);
	        var rcells=r.cells;
	     
	    
	        rcells.item(3).firstChild.value="";
	        rcells.item(4).firstChild.value="";
		   
	   } 


	 
	 
	 
	 
	 
 }
/*function subledgercode1(ele){
	 
	
	    var url= "../../../../../new_Contractor_LJV?command=loadsubdebitDesc&txtAcc_HeadCode="+ele;
			
	//alert(url);
	  var xmlrequest=getTransport();
		    xmlrequest.open("POST",url,true); 
 xmlrequest.onreadystatechange=function()
		    
		    {
	 LoadBank(xmlrequest);
	
	      
			
			
		    };
		    xmlrequest.send(null);
		    
		 
	 
	 
 }

function  LoadBank(xmlrequest){
	 if(xmlrequest.readyState==4)
	    { 
	    	
	        if(xmlrequest.status==200)
	        {  
		 var  baseResponse =xmlrequest.responseXML.getElementsByTagName("response")[0];
				
		 assetcode111=document.createElement("select");
			
		 assetcode111.id="SL_code1";
		 assetcode111.name="SL_code1";
			 var leng = baseResponse.getElementsByTagName("SUB_LEDGER_CODE").length;
	    	
	   for(var k = 0;k<leng ;k++ ){
	  		  var option = document.createElement('option');
	           
	         option.value = baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[k].firstChild.nodeValue ;
	         option.text = baseResponse.getElementsByTagName("project_name")[k].firstChild.nodeValue ;
	         assetcode111.appendChild(option);
	  	
	   }}
	   }
	      
}*/


function  LoadBankAccountNumberRes(xmlrequest)
{  

    if(xmlrequest.readyState==4)
    { 
    	
        if(xmlrequest.status==200)
        {  
        	// subledgercode1(11);
        		

         var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
  
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
   
            
        
         var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      // alert(flag);
         if(flag=="success"){
        	 //alert("idnames"+idnames);
         if(Command=="loadAccDesc"){
        	   var tbody=document.getElementById("grid_body2");
        	 var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;
         	
             //alert("length"+leng);
   				lll=1;
   				seq=1;
   				var item = new Array();

   				for(var k=0;k<leng;k++)
   				{

   					item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
   					item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
   					
   				var	mycurrent_row=document.createElement("TR");
   					mycurrent_row.id=item[1];                     
   					var cell1 = document.createElement("TD");
   					var dateofentry1=document.createElement("input");
   					dateofentry1.type="hidden";
   					dateofentry1.name="seq";
   					dateofentry1.id=seq;
   					dateofentry1.value=seq;
   					var dateofentry = document.createTextNode(seq);
   					dateofentry.size=7;
   					
   					cell1.appendChild(dateofentry);
   					cell1.appendChild(dateofentry1);
   					mycurrent_row.appendChild(cell1);

   					var cell2 = document.createElement("TD");
   					var assetcode1=document.createElement("input");
   					assetcode1.type="hidden";
   					assetcode1.name="ACCOUNT_HEAD_code";
   					assetcode1.id="ACCOUNT_HEAD_code";
   					assetcode1.value=item[1];
   					var assetcode = document.createTextNode(item[2]);
   					assetcode.size=7;
   					cell2.appendChild(assetcode);
   					cell2.appendChild(assetcode1);
   					cell2.align="right";
   					mycurrent_row.appendChild(cell2);
   					
   					var cell2 = document.createElement("TD");
   					var assetcode1=document.createElement("input");
   					assetcode1.type="hidden";
   					assetcode1.name="CR_DR_type1";
   					assetcode1.id="CR_DR_type1";
   					assetcode1.value="DR";
   					var assetcode = document.createTextNode("DR");
   					assetcode.size=7;
   					cell2.appendChild(assetcode);
   					cell2.appendChild(assetcode1);
   					cell2.align="right";
   					mycurrent_row.appendChild(cell2);
   					
   					var cell2 = document.createElement("TD");
   					var assetcode1=document.createElement("input");
   					assetcode1.type="hidden";
   					assetcode1.name="SLtype1";
   					assetcode1.id="SLtype1";
   					assetcode1.value="10";
   					var assetcode = document.createTextNode("Project");
   					assetcode.size=7;
   					cell2.appendChild(assetcode);
   					cell2.appendChild(assetcode1);
   					cell2.align="right";
   					mycurrent_row.appendChild(cell2);
   					
   					
   					var cell2 = document.createElement("TD");
   					var assetcode1=document.createElement("input");
   					assetcode1.type="hidden";
   					assetcode1.name="SL_code1";
   					assetcode1.id="SL_code1";
   					assetcode1.value="";
   					var assetcode = document.createTextNode("");
   					assetcode.size=7;
   					cell2.appendChild(assetcode);
   					cell2.appendChild(assetcode1);
   					cell2.align="right";
   					mycurrent_row.appendChild(cell2);
   					
   					
   					
   					
   					var cell2 = document.createElement("TD");
   				
   					cell2.appendChild(assetcode111);
   					cell2.align="right";
   					mycurrent_row.appendChild(cell2);
   					
   					var cell2 = document.createElement("TD");
   					var assetcode1=document.createElement("input");
   					assetcode1.type="text";
   					assetcode1.name="sl_amt1";
   					assetcode1.id="sl_amt1";
   					assetcode.size=7;
   					cell2.appendChild(assetcode1);
   					cell2.align="right";
   					mycurrent_row.appendChild(cell2);
   					
   					var cell2 = document.createElement("TD");
   					var assetcode1=document.createElement("input");
   					assetcode1.type="text";
   					assetcode1.name="particular1";
   					assetcode1.id="particular1";
   					
   					var assetcode = document.createTextNode("");
   					assetcode.size=7;
   					cell2.appendChild(assetcode);
   					cell2.appendChild(assetcode1);
   					cell2.align="right";
   					mycurrent_row.appendChild(cell2);
   					
   					
   					tbody.appendChild(mycurrent_row);
   					lll++;
   					 seq++;
   			
            		
            		}         

       	} 
         //Joan Changes
         
         else if(Command=="loadSchdebitcode")
       		{
        	 
       	 
        	   var tbody=document.getElementById("grid_body2");
               try{tbody.innerHTML="";}
           catch(e) {tbody.innerText="";}
       	 var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;
        	
            //alert("length"+leng);
  				lll=1;
  				seq=0;
  			   tbody=document.getElementById("grid_body2");
  				var item = new Array();

  				for(var k=0;k<leng;k++)
  				{

  					item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
  					item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
  					item[3] =baseResponse.getElementsByTagName("cmbAcc_UnitCode")[0].firstChild.nodeValue;
  					item[4] =baseResponse.getElementsByTagName("cmbOffice_code")[0].firstChild.nodeValue;
  					item[5] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE")[0].firstChild.nodeValue;
  					item[6] =baseResponse.getElementsByTagName("sub_ledger_name")[0].firstChild.nodeValue;
  					item[7] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[0].firstChild.nodeValue;
  					item[8] =baseResponse.getElementsByTagName("sub_ledger_type_desc")[0].firstChild.nodeValue;
  					//item[3] =baseResponse.getElementsByTagName("slType")[k].firstChild.nodeValue;
  				var	mycurrent_row=document.createElement("TR");
  					mycurrent_row.id=item[1];                     
  					var cell1 = document.createElement("TD");
  					var dateofentry1=document.createElement("input");
  					dateofentry1.type="hidden";
  					dateofentry1.name="seq";
  					dateofentry1.id=seq;
  					dateofentry1.value=seq;
  					var dateofentry = document.createTextNode(seq+1);
  					dateofentry.size=7;
  					
  					cell1.appendChild(dateofentry);
  					cell1.appendChild(dateofentry1);
  					mycurrent_row.appendChild(cell1);

  					var cell2 = document.createElement("TD");
  					var assetcode1=document.createElement("input");
  					assetcode1.type="hidden";
  					assetcode1.name="ACCOUNT_HEAD_code";
  					assetcode1.id="ACCOUNT_HEAD_code";
  					assetcode1.value=item[1];
  					var assetcode = document.createTextNode(item[1]+'-'+item[2]);
  					assetcode.size=7;
  					cell2.appendChild(assetcode);
  					cell2.appendChild(assetcode1);
  					//..cell2.align="right";
  					mycurrent_row.appendChild(cell2);
  					
  					var cell2 = document.createElement("TD");
  					var assetcode1=document.createElement("input");
  					assetcode1.type="hidden";
  					assetcode1.name="CR_DR_type1";
  					assetcode1.id="CR_DR_type1";
  					assetcode1.value="DR";
  					var assetcode = document.createTextNode("DR");
  					assetcode.size=7;
  					cell2.appendChild(assetcode);
  					cell2.appendChild(assetcode1);
  					//cell2.align="right";
  					mycurrent_row.appendChild(cell2);
  					
  				/*	var cell2 = document.createElement("TD");
  					var assetcode1=document.createElement("input");
  					assetcode1.type="hidden";
  					assetcode1.name="SLtype1";
  					assetcode1.id="SLtype1";
  					assetcode1.value="10";
  					var assetcode = document.createTextNode("Project");
  					assetcode.size=7;
  					cell2.appendChild(assetcode);
  					cell2.appendChild(assetcode1);
  					cell2.align="right";
  					mycurrent_row.appendChild(cell2);*/
  					
  					
  					
  				
  					
  					
  					var cell2 = document.createElement("TD");
  					var sel=document.createElement("select");
  					sel.id="sel_debtype"+seq;
  					sel.name="sel_debtype"+seq;
  					
  				//	sel.setAttribute('onclick','javascript:chgeSlcode(this.value,'+seq+','+item[3]+','+item[4]+')');
  				/*	var option=document.createElement("OPTION");
  					option.value="";
  					option.text="--Select--";
  					sel.appendChild(option);*/
  				/*	var slcodelen =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k).length;
  				
  					for(var jj=0;jj<slcodelen;jj++)
  						{
  						var option=document.createElement("OPTION");
  	  					option.value=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k)[jj].firstChild.nodeValue;
  	  			//	alert(baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue);
  	  					option.text=baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue;
  	  					sel.appendChild(option); 
  						}*/
  					option.value=item[5];
 	  	  			option.text=item[6];
  	  	  					sel.appendChild(option); 	
  					
  					
  					
  					cell2.appendChild(sel);
  					mycurrent_row.appendChild(cell2);
  					
  					
  					
  					var cell2 = document.createElement("TD");
  					var sel=document.createElement("select");
  					sel.id="SLtypecode1"+seq;
  					sel.name="SLtypecode1"+seq;
  					sel.setAttribute('style','width:400px');

  					var option=document.createElement("OPTION");
  					option.value=item[7];
  					option.text=item[8];
  					sel.appendChild(option);
  				
  					
  					cell2.appendChild(sel);
  					
  					mycurrent_row.appendChild(cell2);
  					
  			
  					
  					var cell2 = document.createElement("TD");
  					var assetcode1=document.createElement("input");
  					assetcode1.type="text";
  					assetcode1.name="sl_amt1";
  					assetcode1.id="sl_amt1"+lll;
  					
  					//assetcode1.size=7;
  					assetcode1.maxlength="15";
  					cell2.appendChild(assetcode1);
  					cell2.align="right";
  					mycurrent_row.appendChild(cell2);
  					
  					var cell2 = document.createElement("TD");
  					var assetcode1=document.createElement("input");
  					assetcode1.type="text";
  					assetcode1.name="particular1";
  					assetcode1.id="particular1";
  					
  					var assetcode = document.createTextNode("");
  					assetcode.size=7;
  					cell2.appendChild(assetcode);
  					cell2.appendChild(assetcode1);
  					cell2.align="right";
  					mycurrent_row.appendChild(cell2);
  					
  					
  					tbody.appendChild(mycurrent_row);
  					lll++;
  					 seq++;
  			
           		
           		}         

      	
       		}
         else if (Command=="loadsubDesc"){
       	 var leng = baseResponse.getElementsByTagName("SUB_LEDGER_CODE").length;
       	// alert(baseResponse.getElementsByTagName("sel")[0].firstChild.nodeValue)
        select = document.getElementById("SL_code"+baseResponse.getElementsByTagName("sel")[0].firstChild.nodeValue);
       		for(var k = 0;k<leng ;k++ ){
       	      var option = document.createElement( 'option' );
       	        option.value =baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[k].firstChild.nodeValue; 
       	        	option.text = baseResponse.getElementsByTagName("project_name")[k].firstChild.nodeValue;
       	        select.appendChild( option );
       	       
       			
     	}
       		
       		}  else if (Command=="loadSchEME"){
       			document.getElementById("grid_bodylist").length=0;
       		/*  var tbdy = document.getElementById("grid_bodylist");
       			var t=0;
       			for(t=){
       				
       			}*/
       			var len=baseResponse.getElementsByTagName("sch_sno").length;
       			for(var i=0;i<len;i++)
       				{
       				
       			    var scNo=baseResponse.getElementsByTagName("sch_sno")[i].firstChild.nodeValue; 
    	        	var scName= baseResponse.getElementsByTagName("project_name")[i].firstChild.nodeValue;
       		  var tbdy = document.getElementById("grid_bodylist");
       		var	mycurrent_row=document.createElement("TR");
				mycurrent_row.id=i+1;  
				
				var cell1 = document.createElement("TD");
				var dateofentry1=document.createElement("input");
				dateofentry1.type="radio";
				dateofentry1.name="sccheck";
				dateofentry1.id="sccheck";
				
			
				cell1.appendChild(dateofentry1);
			
				
				
				
				var dateofentry1=document.createElement("input");
				dateofentry1.type="hidden";
				dateofentry1.name="scNo"+i;
				dateofentry1.id="scNo"+i;
				dateofentry1.value=scNo;
			
				cell1.appendChild(dateofentry1);
				mycurrent_row.appendChild(cell1);
				
				
				var cell1 = document.createElement("TD");
				var dateofentry2=document.createElement("input");
				dateofentry2.type="hidden";
				dateofentry2.name="scName"+i;
				dateofentry2.id="scName"+i;
				dateofentry2.value=scName;
				var dateofentry1 = document.createTextNode(scNo+"  -  "+scName);
				cell1.appendChild(dateofentry1);
				cell1.appendChild(dateofentry2);
				mycurrent_row.appendChild(cell1);
				
				tbdy.appendChild(mycurrent_row);
				
				
       	/*	 var option = document.createElement( 'option' );
       		 option.value ="";
       		option.text ="--Select--";
       	    select1.appendChild( option );
              	 var leng = baseResponse.getElementsByTagName("sch_sno").length;
              //	 alert('len'+leng)
                 select = document.getElementById("Schemename");
                		for(var k = 0;k<leng ;k++ ){
                	      var option = document.createElement( 'option' );*/
                	      /*  option.value =baseResponse.getElementsByTagName("sch_sno")[k].firstChild.nodeValue; 
                	        	option.text = baseResponse.getElementsByTagName("project_name")[k].firstChild.nodeValue;
                	        select.appendChild( option );
                	       */
                			
              	}
                		
                		        }
       	else if (Command=="loadsubdebitDesc"){
          	 var leng = baseResponse.getElementsByTagName("SUB_LEDGER_CODE").length;
          	// alert(leng);
         for(var k = 0;k<leng ;k++ ){
        		
             
             var select =   document.getElementById("subcode1");
                 var option = document.createElement('option');
                 
               option.value = baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[k].firstChild.nodeValue ;
               option.text = baseResponse.getElementsByTagName("project_name")[k].firstChild.nodeValue ;
                 select.appendChild(option);
                
                   
  	}
         
       	}
       	else if(flag="failure")
       		{
       		
       		}
           
        }
    }
}


}




function checkOffices(pppp)
{
var jj=document.getElementById("cmbMas_SL_type").value;
if(pppp=="550351" && jj=="84")
{
        doFunction('checkCode','null');
       setTimeout('meth1()',900);       
        
}
else
{
doFunction('checkCode','null');
}
}

function meth1()
{
        document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].value=5;
        document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text="Offices";
        document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].value=5000;
        document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text="Head Office, Chennai";
}

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
	
	
	
	/*var inc_code=0;
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
          
       if(document.getElementById("txtAcc_HeadDesc").value=="")
       {
            alert("Please Wait Account Head is Loading .......................");            
            return false;        
       }  
       
       
       if ( isMan.account_head_status) 
       {
        
        if(document.getElementById("cmbSL_type").value=="")
        {
            alert("Select The Sub Ledger Type")       ;
            document.getElementById("cmbSL_type").focus();
            return false;        
        }
        
        if(document.getElementById("cmbSL_Code").value=="")
        {
            alert("Select The Sub Ledger Code")       ;
            document.getElementById("cmbSL_Code").focus();
            return false;        
        }        
       }
        else
       {
            if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
            {
                if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
                    {
                        if(document.getElementById("cmbSL_type").value=="")
                            {
                                alert("Select a Sub-Ledger Type");
                                return false;
                            } 
                    }
                    else
                    {
                     
                    }          
            }
            if(document.getElementById("cmbSL_type").value!="")
            {
                if(document.getElementById("cmbSL_Code").value=="")
                    {
                        alert("Select The Sub Ledger Code")       ;
                        document.getElementById("cmbSL_Code").focus();
                        return false;
                    }
            }       
        }
      
      
  
    
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            //document.getElementById("txtAmount").focus();
            return false;    
        }*/
        var tbody=document.getElementById("grid_body");
            //alert("CODE"+document.getElementById("txtSL_Desc").value);
            //alert("TEXT"+document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text);
            //alert("AGA"+document.getElementById("txtSL_Desc").text)
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;
      //  if(checkForRedundancy(exist))
      //  {
        var items=new Array();
       // items[0]=document.getElementById("txtAcc_HeadCode").value;
        //items[1]=document.getElementById("txtAcc_HeadDesc").value;txtAcc_HeadDesccontract
        //alert(document.frmJournal_Bill_Create.txtAcc_HeadCode.value);
        //alert(document.frmJournal_Bill_Create.txtAcc_HeadDesc.value);
        //alert(document.frmJournal_Bill_Create.contract[1].value);
       // alert(document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text);
        //alert(document.getElementById("cmbSL_Code4").options[document.getElementById("cmbSL_Code4").selectedIndex].text);
       // alert(document.frmJournal_Bill_Create.txtsub_Amount.value);
       // alert(document.frmJournal_Bill_Create.txtParticular.value);
        /*if(document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked==true)
          items[0]=document.frmJournal_Bill_Create.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_Bill_Create.rad_sub_CR_DR[1].checked==true)
          items[1]=document.frmJournal_Bill_Create.rad_sub_CR_DR[1].value;*/
        
       /* items[2]=document.getElementById("cmbSL_type1").options[document.getElementById("cmbSL_type").selectedIndex].text;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[3]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        //alert("code"+items[4]+"ff");
        }
        else
        items[3]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[4]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[5]="";//"Not Available";
        //alert("code"+items[6]+"ff");
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; */
        
       // items[7]=document.getElementById("txtsub_Recei_from").value;
       
        if(document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked==true)
            items[0]=document.frmJournal_Bill_Create.rad_sub_CR_DR[0].value;
          else if(document.frmJournal_Bill_Create.rad_sub_CR_DR[1].checked==true)
            items[0]=document.frmJournal_Bill_Create.rad_sub_CR_DR[1].value;
      //  alert(items[0]);
        
        if(document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked==true)
            items[1]=document.frmJournal_Bill_Create.txtAcc_HeadCode.value;
          else if(document.frmJournal_Bill_Create.rad_sub_CR_DR[1].checked==true)
            items[1]=document.frmJournal_Bill_Create.contract.value;
        
       
        items[3]=document.getElementById("cmbSL_type").value;
        /*items[4]= document.getElementById("cmbSL_Code4").options[document.getElementById("cmbSL_Code4").selectedIndex].text;*/
        items[4]=document.frmJournal_Bill_Create.cmbSL_Code4.value;
        items[5]=document.frmJournal_Bill_Create.txtsub_Amount.value;
        items[6]=document.frmJournal_Bill_Create.txtParticular.value
        
        /*items[6]=document.getElementById("txtsub_Amount").value;
        items[7]=document.getElementById("txtParticular").value;*/
        
        //items[0]=document.getElementById("txtSL_code").value;
        //items[1]=document.getElementById("txtSL_Desc").options[document.getElementById("txtSL_Desc").selectedIndex].text;                
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
       /* var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);*/
        var i=0;
        var cell2;
        
       
            cell2=document.createElement("TD");
           
                  var H_code=document.createElement("input");
                  H_code.type="hidden";
                  H_code.name="CR_DR_code";
                  H_code.value=items[0];
                  cell2.appendChild(H_code);
                  var currentText=document.createTextNode(items[0]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);

           cell2=document.createElement("TD"); 
           cell2.style.textAlign="center";
                  var CR_DR_type=document.createElement("input");
                  CR_DR_type.type="hidden";
                  CR_DR_type.name="CR_DR_code";
                  CR_DR_type.value=items[1];
                  cell2.appendChild(CR_DR_type);
                   var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
                  var SL_type=document.createElement("input");
                  SL_type.type="hidden";
                  SL_type.name="SL_type";
                  SL_type.value=items[3];
                  cell2.appendChild(SL_type);
                   var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
           // alert(items[3]);
             cell2=document.createElement("TD");
             cell2.style.textAlign="center";
                  var SL_code=document.createElement("input");
                  SL_code.type="hidden";
                  SL_code.name="SL_code";
                  SL_code.value=items[4];
                  cell2.appendChild(SL_code);
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
           cell2=document.createElement("TD");
           cell2.style.textAlign="right";
                  var Bill_amt=document.createElement("input");
                  
                  Bill_amt.type="hidden";
                  Bill_amt.name="sl_Amt";
                  
                  Bill_amt.value=items[5];
                  cell2.appendChild(Bill_amt);
                   var currentText=document.createTextNode(items[5]);
                   currentText.a
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
                  var Bill_part=document.createElement("input");
                  Bill_part.type="hidden";
                  Bill_part.name="particlular";
                  Bill_part.value=items[6];
                  cell2.appendChild(Bill_part);
                   var currentText=document.createTextNode(items[6]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                
             

        tbody.appendChild(mycurrent_row);
         clear_main_fields();
}


function update_GRID()
{      
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }
        if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
          
                alert("Select a Sub-Ledger Type");
                return false;
             
          
        }
        if(document.getElementById("cmbSL_type").value!="")
        {
          if(document.getElementById("cmbSL_Code").value=="")
           {
            alert("Select The Sub Ledger Code");
            return false;
            }
        }
     
        
        
      

        if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }
        var exist=document.getElementById("txtAcc_HeadCode").value;
        var items=new Array();
       
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked==true)
          items[2]=document.frmJournal_Bill_Create.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_Bill_Create.rad_sub_CR_DR[1].checked==true)
          items[2]=document.frmJournal_Bill_Create.rad_sub_CR_DR[1].value;
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        //alert("code"+items[4]+"ff");
        }
        else
        items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        items[6]="";//"Not Available";
        //alert("code"+items[6]+"ff");
        }
        else
        items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
        
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
        var r=document.getElementById(com_id);
        var rcells=r.cells;
                try{rcells.item(1).firstChild.value=items[0];}catch(e){}
                try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}
             
                try{rcells.item(2).firstChild.value=items[2];}catch(e){}
                try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
              
                try{rcells.item(3).firstChild.value=items[3];}catch(e){}
                try{rcells.item(3).lastChild.nodeValue=items[4];}catch(e){}
            
                try{rcells.item(4).firstChild.value=items[5];}catch(e){}
                try{rcells.item(4).lastChild.nodeValue=items[6];}catch(e){}
            
                //try{rcells.item(5).firstChild.value=items[7];}catch(e){}
               // try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
            
                try{rcells.item(5).firstChild.value=items[7];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
             
                try{rcells.item(6).firstChild.value=items[8];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){}
        rcells.item(7).firstChild.value=items[9];
        var nex_cell=rcells.item(7).firstChild.nextSibling;
        nex_cell.value=items[10];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[11];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[12];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[13];
        rcells.item(7).lastChild.nodeValue=items[12];
        alert("Record Updated");
        clearall();
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

function clear_main_fields()
{
    
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
 /*   document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    document.getElementById("txtAgree_No").value="";
    document.getElementById("txtAgree_Date").value="";*/
    //document.getElementById("txtsub_Recei_from").value="";
    document.getElementById("txtsub_Amount").value="";
    document.getElementById("txtParticular").value="";
                var cmbSL_type=document.getElementById("cmbSL_type"); 
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
     document.getElementById("offlist_div_trans").style.display='none';       
            var cmbSL_Code=document.getElementById("cmbSL_Code");   
            clear_Combo(cmbSL_Code);   

     document.frmJournal_Bill_Create.cmdadd.style.display='block';
     document.frmJournal_Bill_Create.cmdupdate.style.display='none';
     document.frmJournal_Bill_Create.cmddelete.disabled=true;
}
/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
    
     document.getElementById("offlist_div_trans").style.display='none';
     document.getElementById("emplist_div_trans").style.display='none';
     
    document.getElementById("txtAcc_HeadCode").value="";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    document.frmJournal_Bill_Create.rad_sub_CR_DR[0].checked=true;
    document.getElementById("cmbSL_type").value="";
    document.getElementById("cmbSL_Code").value="";
    document.getElementById("txtBill_NO").value="";
    document.getElementById("txtBill_date").value="";
    document.getElementById("txtBill_type").value="";
    document.getElementById("txtAgree_No").value="";
    document.getElementById("txtAgree_Date").value="";
    
    //document.getElementById("txtsub_Recei_from").value="";
    document.getElementById("txtsub_Amount").value="";
    document.getElementById("txtParticular").value="";
                var cmbSL_type=document.getElementById("cmbSL_type"); 
                cmbSL_type.innerHTML=""; 
                var option=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
                try
                {
                    cmbSL_type.add(option);
                }catch(errorObject)
                {
                    cmbSL_type.add(option,null);
                }
    document.getElementById("offlist_div_trans").style.display='none';       
            var cmbSL_Code=document.getElementById("cmbSL_Code");   
            clear_Combo(cmbSL_Code);   

 document.frmJournal_Bill_Create.cmdadd.style.display='block';
 document.frmJournal_Bill_Create.cmdupdate.style.display='none';
 document.frmJournal_Bill_Create.cmddelete.disabled=true;
}
 
function call_clr()
{
   try
   {
    // document.getElementById("txtAmount").value="";
    //document.getElementById("txtBill_NO").value="";
    //document.getElementById("txtBill_date").value="";
    clearall();
    
    document.getElementById("txtRemarks").value="";
   
    
    document.getElementById("cmbMas_SL_type").value="";
    clear_Combo(document.getElementById("cmbMas_SL_Code"));
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
   }
   catch(e)
   {
	  //     alert(e.description);
   }
   
    
}


function clrForm()
{
   if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
    
 }
}


/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

function checkNull()
{
//	alert('testbbbbb');
	var proceed=0;
	var flag=0;
	var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
	var tbody=document.getElementById("grid_body");
	   //alert("tbody.rows.length :"+tbody.rows.length);   
	if(document.getElementById("cmbAcc_UnitCode").value=="")
	{
	    alert("Select the Account Unit code");
	    //document.getElementById("txtAcc_HeadDesc").focus();
	    return false;    
	}
	if(document.getElementById("cmbOffice_code").value=="")
	{
	    alert("Select the Office Code");
	    //document.getElementById("cmbOffice_code").focus();
	    return false;
	}
	/*if(document.getElementById("Schemename").value=="")
	{
	    alert("Select the scheme Number");
	    //document.getElementById("Schemename").focus();
	    return false;
	}*/


	if(document.getElementById("cmbMas_SL_type").value=="")
	{
	    //if(document.getElementById("cmbMas_SL_Code").value=="")
	    //{
	    alert("Select The Journal Type in General");
	    return false;
	    //}
	}


	if(document.getElementById("cmbMas_SL_type").value!="" )
	{
	    if(document.getElementById("cmbMas_SL_Code").value=="")
	    {
	    alert("Enter the Sub-Ledger Code");
	    return false;
	    }


	if(document.getElementById("txtCrea_date").value.length==0)
	{
	    alert("Enter the Date of Creation");
	    //document.getElementById("txtCrea_date").focus();
	    return false;    
	}
	/* alert(document.getElementById("txtBill_NO").value);
	 alert("jjjj");
	if(document.getElementById("txtBill_NO").value=="")
	{
	    alert("enter bill number");
	   
	    return false;    
	}
	
	if(document.getElementById("txtBill_date").value.length==0)
	{
	    alert("select date from calender");
	    //document.getElementById("txtRecei_from").focus();
	    return false;    
	}

	

	if(document.getElementById("txtBill_type").value.length==0)
	{
	    alert("Enter bill type ");
	    //document.getElementById("txtRecei_from").focus();
	    return false;    
	}
	if(document.getElementById("txtAgree_No").value.length==0)
	{
	    alert("Enter agreement number ");
	    //document.getElementById("txtRecei_from").focus();
	    return false;    
	}

	if(document.getElementById("txtAgree_Date").value.length==0)
	{
	    alert("select date from calender");
	    //document.getElementById("txtRecei_from").focus();
	    return false;
	}*/
	if (document.getElementById("txtRemarks").value== "") {
		alert("Enter Remarks");
		// document.getElementById("txtCrea_date").focus();
		return false;
	}

	/*if(document.getElementById("SLtype").value=="")
	{
	    //if(document.getElementById("cmbMas_SL_Code").value=="")
	    //{
	    alert("Select The subledger  Type in credit");
	    return false;
	    //}
	}
*/

try
{

//	alert(document.frmJournal_Bill_Create1.sl_amt.length);
	var v=document.frmJournal_Bill_Create1.sl_amt.length;

	var total=0;
	//alert("value of rows in credit head"+v);
	for(var i=0;i<v;i++)
	{
		
		
	if(document.getElementById("sl_amt"+i).value==""){
		//document.getElementById("sl_amt_"+i).value==0
		sum=0;
		  total=parseFloat(total)+ parseFloat(sum);
			
	}   
	else if(document.getElementById("sl_amt"+i).value!="")
	    {
	    var sum=document.getElementById("sl_amt"+i).value;
	   
	    total=parseFloat(total)+ parseFloat(sum);
	  
	    }
	//alert("total credit is-"+total);
	}
}
catch(e)
{
alert("Exception in credit part");	
}
	
	//alert(document.getElementById("particular").value);
	
	/*if(document.getElementById("particular").value=="")
	{
	    //if(document.getElementById("particular").value=="")
	    //{
	    alert("enter remarks in creditside");
	    return false;
	    //}
	}*/
	
	
	
	
	try
	{

	var v1=document.frmJournal_Bill_Create1.sl_amt1.length;
	//alert("Length of Debit head"+v1);

	var total1=0;
	for(var j=0;j<v1;j++)
	{
	   var jj=j+1;
	   var dramt=document.getElementById("sl_amt1"+jj).value;
	   //alert("dramt==>"+dramt);
	    if(dramt==""){
	    	  var su=0;
	    	   
	   	    total1=parseFloat(total1)+ parseFloat(su);
	      }

	else if(dramt!="")
	    {
		//alert("hh");
	    var su=dramt;
	   
	    total1=parseFloat(total1)+ parseFloat(su);
	    
	    }
// alert("debit total  is"+total1);

	   //alert(j);
	    }
	
	//	alert("total ==1==> "+parseFloat(total));
//	alert("total1 ===1===> "+parseFloat(total1));
	
	//alert("total==1==>");
	}
	catch(e)
	{
		//alert("Exception in debit part");
	}
	
try
{
	if(parseFloat(total)==parseFloat(total1))
		{
		//alert("Both amt are equal");
		//alert("credit amount and debit amount are equal");
		return true;
		
		}
	
	else if(parseFloat(total)!=parseFloat(total1))
		{
		//alert("Both amont are not equal");
		alert("Credit amount and Debit amount is not Equal ... ");
		return false;
		
		}
}
catch(e)
{
	
alert("Exception in Total checking");
}


	if(document.getElementById("particular").value=="")
	{
	    //if(document.getElementById("particular").value=="")
	    //{
	    alert("enter remarks in creditside");
	    return false;
	    //}
	}


	if(document.getElementById("sel_debtype").value=="")
	{
	    //if(document.getElementById("sel_debtype").value=="")
	    //{
	    alert("select journal type in debit side");
	    return false;
	    //}
	}



	if(document.getElementById("particular1").value=="")
	{
	    //if(document.getElementById("particular1").value=="")
	    //{
	    alert("enter remarks in debitside");
	    return false;
	    //}
	}


	/*if(document.getElementById("txtAmount").value.length==0)
	{
	    alert("Enter the Total Amount in General");
	    //document.getElementById("txtAmount").focus();
	    return false;    
	}*//*
	if(tbody.rows.length==0)
	{
	    alert("Enter the Details Part");
	    //document.getElementById("txtAmount").focus();
	    return false; 
	}



	if(tbody.rows.length>0)
	{
	        var check_amt=0;
	        var cr_amt=0;
	        var db_amt=0;
	       
	      //  alert("cmbMas_SL_type"+cmbMas_SL_type);
	        rows=tbody.getElementsByTagName("tr");
	        var ccc;
	        var hCodes= new Array();
	        var spl11;
	        var val="";
	        for(i=0;i<rows.length;i++)
	        {
	            var cells=rows[i].cells;
	         
	            if(cells.item(2).lastChild.nodeValue=='CR')
	            {
	            	 var ac_code=cells.item(1).lastChild.nodeValue;
	                 var code1= ac_code.split("-");
	                 if( (code1[0]==550502) && (cmbMas_SL_type==96) )
	                 {
	                	 
	                flag=111;
	                 }
	                 
	            cr_amt=parseFloat(cr_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
	            }
	            else
	            {
	            db_amt=parseFloat(db_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
	            }
	          
	            var generalSLTYPE=document.getElementById("cmbMas_SL_type").options[document.getElementById("cmbMas_SL_type").selectedIndex].text;
	          
	            if(generalSLTYPE=="TWAD CPS Journal"){
	            	//pradha
	            	var hc=cells.item(1).lastChild.nodeValue;
	            	var tt=hc.split("-");
	            	
	            	if(tt[0]=="550351")
	            	{
	            		
	            		 proceed++;
	            	}
	            	if(tt[0]=="550350")
	            	{
	            		
	            		 proceed++;
	            	}
	            }
	            else if(generalSLTYPE=="Employee Journal")
	            {
	              if(cells.item(3).lastChild.nodeValue=="Employees")
	                {
	              //  alert("4:::"+cells.item(3).firstChild.value);
	                
	                if(cells.item(4).firstChild.value==document.getElementById("txtEmpID_mas").value)
	                {
	                proceed++;
	                }
	                }
	            }
	            else if(generalSLTYPE=="Rent Journal")
	            {
	            	
	            	if(cells.item(3).firstChild.value==17)
	            	{
	            		
	            		if(cells.item(4).firstChild.value==document.getElementById("cmbMas_SL_Code").value)
	                    {
	            		//	alert("end");
	                    proceed++;
	                    }
	            	}
	            }
	            else if(generalSLTYPE=="Firms")
	            {
	            	
	            	if(cells.item(3).firstChild.value==2)
	            	{
	            		
	            		if(cells.item(4).firstChild.value==document.getElementById("cmbMas_SL_Code").value)
	                    {
	            		
	                    proceed++;
	                    }
	            	}
	            }
	            else{
	            	
	           if(generalSLTYPE==cells.item(3).lastChild.nodeValue)
	            {
		        	   if(cells.item(4).firstChild.value==document.getElementById("cmbMas_SL_Code").value)
		               {
		            proceed++;
		               }
	            }
	          }  
	         
	       
	        }
	  
	        
	        
	        if(parseFloat(db_amt)<=0)
	        {
	            alert("Debit amount must be specified");
	            return false;
	        }
	        
	        if(parseFloat(cr_amt)!=parseFloat(db_amt))
	        {
	        alert("Amount doesn't Tally.. Difference " +(parseFloat(cr_amt)-parseFloat(db_amt)));
	        return false;
	        } 
	        
	}
	 if(proceed==0)
	    {
	    alert("Atleast One sub-Ledger Type should match with GeneralJournal");
	    return false;
	    }  
	if(cmbMas_SL_type==96)
	{
		if(flag==111)
		{
			 return true;
		}
		else
		{
			alert("A/c Head 550502 Should be Added as Credit Head for Rent Journal");
	        return false;
		}
	}*/
	return true;
	}
	return true;
}
 
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
                 //doFunction('load_Receipt_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                   // alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtCrea_date").value="";     
               }
            
            dateCheck1(dateCtrl); 
        }
    }
}




function dateCheck1(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}
function check_Date(req,datechk)
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
               //doFunction('load_Receipt_No','null');                 //return true;
          	document.getElementById("butSub").disabled=false;
            }
          else if(flag=="failure")
          {
          	datechk.value=""; 
          	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
          	datechk.focus();
          	document.getElementById("butSub").disabled=true;
          	
//          	document.getElementById("txtReceipt_No").value="";
               
          }
          else if(flag=="success1")
          {
             //doFunction('load_Receipt_No','null');                 //return true;
          	document.getElementById("butSub").disabled=false;
          }
         else if(flag=="failure1")
         {
      	  alert("Document Date is Greater than DATE_OF_CLOSURE");
      	  datechk.value=""; 
        		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
        		datechk.focus();
        		document.getElementById("butSub").disabled=true;
//        		document.getElementById("txtReceipt_No").value="";
         }
         else 
      	   {
      	    datechk.value=""; 
      	    alert("Date Value is Null");
         		datechk.focus();
         		document.getElementById("butSub").disabled=true;
//         		document.getElementById("txtReceipt_No").value="";
      	   }
        }
    }
}






function limit_amt_journal(field,e)
{

      var Journal_Creation_date=document.getElementById("txtCrea_date").value.split("/");;
        //  alert(Journal_Creation_date[1]);
    
      var unicode=e.charCode? e.charCode : e.keyCode;

      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
                            
            if (Journal_Creation_date[1] <=8 && Journal_Creation_date[2]<=2007 )     
            {
             
              if (unicode<45 || unicode==47 || unicode>57   )        // It  allow the negative amount
                  return false;  
             }
             else  
             {
             
              if (unicode<46 || unicode==47 || unicode>57   )       // It won't allow the negative amount   
                  return false;
             }     
                  
        }
      }
      else   
      return false;  
}
function call_date(dateCtrl)                        // TB_checking 
{
    call_clr();
    if(checkdt(dateCtrl))
    {
        //doFunction('check_TB',dateCtrl.value);
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
         var TB_date=dateCtrl.value;
       
         if(dateCtrl.value.length!=0)
         {
             var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
//             alert("UURRRLLL"+url);
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
               check_TB(req,dateCtrl);
             }   
             req.send(null);
       }
        //doFunction('load_Receipt_No','null');
    }
    else
    {
     // document.getElementById("txtReceipt_No").value="";
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
                 //doFunction('load_Receipt_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    //document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";     
               }
             else if(flag=="finyearLJVN")
             {
                        // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                  dateCtrl.value="";
                  alert("Cash Book Control Not Found for Journal");//return false;//
                  dateCtrl.focus();
                 // document.getElementById("txtReceipt_No").value="";     
             }
            dateCheck1(dateCtrl); 
        }
    }
}


function Acc_HeadCodeValidation()
{
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
	//alert(unitid);
	if(unitid!=5){
		if(unitid!=999){

var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
if(cmbMas_SL_type=="")
{
alert("Choose Journal Type in General");
document.getElementById("txtAcc_HeadCode").value="";
document.getElementById("txtAcc_HeadDesc").value="";
//document.getElementById("cmbMas_SL_type").focus();
return false;
}

var date1=document.getElementById("txtCrea_date").value;
    var spl=date1.split("/");
     if(spl[2]>=2011)
    {
          if(spl[0]>01 || spl[0]==01)
          {
                  var Acc_HeadCode=parseInt(document.getElementById("txtAcc_HeadCode").value);
                  var Acc_HeadCode1=document.getElementById("txtAcc_HeadCode").value;
                  var digit=parseInt(Acc_HeadCode1.substr(0, 2));  
               
                  if((Acc_HeadCode==900108) || (Acc_HeadCode==900109) || (Acc_HeadCode==901001)|| (Acc_HeadCode==901002)|| (Acc_HeadCode==620101)|| (Acc_HeadCode==900301)|| (Acc_HeadCode==610101)|| (Acc_HeadCode==610102) || (Acc_HeadCode==390602)) 
                  {
                   alert("Account Head Code  390602,900108,900109,901001,901002,620101,900301, 610101, 610102 Not Allowed ");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  }
                  else if((Acc_HeadCode==780401) ||(Acc_HeadCode==780402) ||(Acc_HeadCode==780403) ||(Acc_HeadCode==780405) ||(Acc_HeadCode==780406) || (Acc_HeadCode==160502) || (Acc_HeadCode==782401) ||(Acc_HeadCode==782402) ||(Acc_HeadCode==782403) || (Acc_HeadCode==782404) ||(Acc_HeadCode==782405) ||(Acc_HeadCode==782406))
                  {
                	  alert("DCB Head Codes like 780401,780402,780403,780405,780406,160502,782401,782402,782403,782404,782405,782406 Are Not Allowed ");
                      document.getElementById("txtAcc_HeadCode").value="";
                      document.getElementById("txtAcc_HeadCode").focus();
                  } else if((Acc_HeadCode==390302) ||(Acc_HeadCode==390303)|| (Acc_HeadCode==390305) || (Acc_HeadCode==391002) ||(Acc_HeadCode==391003) ||(Acc_HeadCode==391302) || (Acc_HeadCode==391303) ||(Acc_HeadCode==391502) ||(Acc_HeadCode==391503) )
                  {			
                	  alert("GPF Account Head Code cannot be used here***");
                      document.getElementById("txtAcc_HeadCode").value="";
                      document.getElementById("txtAcc_HeadCode").focus();
                  }
                  
                  else if(digit==82)
                  {
                  alert("This Account Head Code cannot be used here");
                  document.getElementById("txtAcc_HeadCode").value="";
                  document.getElementById("txtAcc_HeadCode").focus();
                  }
            }
    }
		}
	}
}

function manipulated(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
//alert("ttttttt");
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
// alert(baseResponse);
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
// alert(tagCommand);
			var command = tagCommand.firstChild.nodeValue;
//                        alert(command);
			if (command == "accCheck") {
                          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                          if(flag=="failure")
                          {
                          alert("Atleast one Liability Account Head must be added");
                          }
                          else
                          {
                          alert("proceed");
                          }
			} 
		}
	}
}

function call_a52()
{
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var cmbOffice_code=document.getElementById('cmbOffice_code').value;
	var url="../../../../../BankPay_PendingBill_Create.view?Command=a52_verify&accunitId="+accunitId+"&cmbOffice_code="+cmbOffice_code;
		
		var req=getTransport();
		req.open("POST",url,true); 
		req.onreadystatechange=function(){
		processResponse_bk(req);
		};   
		req.send(null);
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



function chgeSlcode(val,s,uni,offi)
{

	cmbAcc_UnitCode=uni;
	cmbOffice_code=offi;
		addtional_field_value=0;
	 cmbSL_type=val;
	year11=0;
	month1=0;
         var cmbMas_SL_type=document.getElementById("cmbMas_SL_type").value;
   
         var other_dept_off_alias_id=document.getElementById("cmbMas_SL_Code").value;
      
         var url="../../../../../Receipt_SL.view?Command=Load_SL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+
         "&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+
         "&cmbOffice_code="+cmbOffice_code+"&addtional_field_value="+addtional_field_value+"&month1="+month1+"&year11="+year11;
      // alert("year11   "+url);
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
        	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        	            
        	            if(flag=="success")
        	              {
       		
   			document.getElementById("SLtypecode1"+s).length=0;
   		  select1 = document.getElementById("SLtypecode1"+s);
   		 var option = document.createElement( 'option' );
   		 option.value ="";
   		option.text ="--Select--";
   		
   		select1.setAttribute('style','width:400px');
   		
   	    select1.appendChild( option );
          	 var leng = baseResponse.getElementsByTagName("cid").length;
             select = document.getElementById("SLtypecode1"+s);
            		for(var k = 0;k<leng ;k++ ){
            	      var option = document.createElement( 'option' );
            	        option.value =baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue; 
            	        	option.text = baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            	        select.appendChild( option );
            	       
            			
          	}
        	              }	
        	              }
        	        }
        };   
                req.send(null);
    
   
    


}


function btnListSub() 
{
	var schnum="";
	var schNO=new Array();
	var schNAME=new Array();
	hid_Unit=document.getElementById("hid_Unit").value;
	hid_Office=document.getElementById("hid_Office").value;
	sele=document.getElementsByName("sccheck").length;
	
	if(sele==1){
		  schNO[0]=document.getElementById("scNo"+0).value;
         
            schNAME[0]=document.getElementById("scName"+0).value;
	}
	 	//var tbody=document.getElementById("grid_bodylist"); 
	else{
		for(var i=0;i<sele;i++)
    { 
		
         if(document.frmLjvlist.sccheck[i].checked==true)
      {
       //vouNO[0]=document.frmPending_Bills_revised.vou_no.value;
        	// scod=i+1;
                    
                 //     var r=document.getElementById(scod);
                   //   alert(r)
                     // var rcells=r.cells;
                //      r=document.getElementById(document.frmLjvlist.scName[i].value);
                     // alert("cccc "+document.frmLjvlist.scName[i].value)// choose the particular row
                            //var rcells=r[i].cells;
                     // rcells=r.cells;
                  //    schNO[0]=document.getElementById("scNo"+i).value;
        	 schnum=schnum+document.getElementById("scNo"+i).value+",";
                     
                      schNAME[i]=document.getElementById("scName"+i).value;
      }
    }  
    schNO=schnum.substring(0, schnum.length-1);
}
	Minimize();
    opener.dofunc(schNO,hid_Unit,hid_Office);
//    window.history.go();
    return true;
//    self.close();
}
function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}
	function func(schNo,uni,offi)
	{
		
		var url= "../../../../../new_Contractor_LJV?command=loadSchdebitcode&schno="+schNo+"&cmbAcc_UnitCode="+uni+"&cmbOffice_code="+offi;
			

		  var xmlrequest=getTransport();
			    xmlrequest.open("POST",url,true); 
			    xmlrequest.onreadystatechange=function()
			    
			    {
			    	
			    	 if(xmlrequest.readyState==4)
    { 
    	
        if(xmlrequest.status==200)
        {  

       	 
        	   var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
        	   
               var tagcommand=baseResponse.getElementsByTagName("command")[0];
               var Command=tagcommand.firstChild.nodeValue;
      
                
     	   var tbody=document.getElementById("grid_body2");
            try{tbody.innerHTML="";}
        catch(e) {tbody.innerText="";}
    	 var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;
     	
         //alert("length"+leng);
				lll=1;
				seq=0;
			   tbody=document.getElementById("grid_body2");
				var item = new Array();

				for(var k=0;k<leng;k++)
				{

					item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
					
					item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
					
					item[3] =baseResponse.getElementsByTagName("cmbAcc_UnitCode")[0].firstChild.nodeValue;
				
					item[4] =baseResponse.getElementsByTagName("cmbOffice_code")[0].firstChild.nodeValue;
				
					item[5] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE")[k].firstChild.nodeValue;
				
					item[6] =baseResponse.getElementsByTagName("sub_ledger_name")[k].firstChild.nodeValue;
					
					item[7] =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[k].firstChild.nodeValue;
				
					item[8] =baseResponse.getElementsByTagName("sub_ledger_type_desc")[k].firstChild.nodeValue;
				
					//item[3] =baseResponse.getElementsByTagName("slType")[k].firstChild.nodeValue;
				var	mycurrent_row=document.createElement("TR");
					mycurrent_row.id=item[1];                     
					var cell1 = document.createElement("TD");
					var dateofentry1=document.createElement("input");
					dateofentry1.type="hidden";
					dateofentry1.name="seq";
					dateofentry1.id=seq;
					dateofentry1.value=seq;
					var dateofentry = document.createTextNode(seq+1);
					dateofentry.size=7;
					
					cell1.appendChild(dateofentry);
					cell1.appendChild(dateofentry1);
					mycurrent_row.appendChild(cell1);

					var cell2 = document.createElement("TD");
					var assetcode1=document.createElement("input");
					assetcode1.type="hidden";
					assetcode1.name="ACCOUNT_HEAD_code";
					assetcode1.id="ACCOUNT_HEAD_code";
					assetcode1.value=item[1];
					var assetcode = document.createTextNode(item[1]+'-'+item[2]);
					assetcode.size=7;
					cell2.appendChild(assetcode);
					cell2.appendChild(assetcode1);
					//..cell2.align="right";
					mycurrent_row.appendChild(cell2);
					
					var cell2 = document.createElement("TD");
					cell2.setAttribute('style', 'display:none;');
					var assetcode1=document.createElement("input");
					assetcode1.type="hidden";
					assetcode1.name="CR_DR_type1";
					assetcode1.id="CR_DR_type1";
					assetcode1.value="DR";
				/*	var assetcode = document.createTextNode("DR");
					assetcode.size=7;
					cell2.appendChild(assetcode);*/
					cell2.appendChild(assetcode1);
					//cell2.align="right";
					mycurrent_row.appendChild(cell2);
					
				/*	var cell2 = document.createElement("TD");
					var assetcode1=document.createElement("input");
					assetcode1.type="hidden";
					assetcode1.name="SLtype1";
					assetcode1.id="SLtype1";
					assetcode1.value="10";
					var assetcode = document.createTextNode("Project");
					assetcode.size=7;
					cell2.appendChild(assetcode);
					cell2.appendChild(assetcode1);
					cell2.align="right";
					mycurrent_row.appendChild(cell2);*/
					
					
					
				
					
					
					var cell2 = document.createElement("TD");
					var sel=document.createElement("select");
					sel.id="sel_debtype"+seq;
					sel.name="sel_debtype"+seq;
					var option=document.createElement("OPTION");
				//	sel.setAttribute('onclick','javascript:chgeSlcode(this.value,'+seq+','+item[3]+','+item[4]+')');
				/*	var option=document.createElement("OPTION");
					option.value="";
					option.text="--Select--";
					sel.appendChild(option);*/
				/*	var slcodelen =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k).length;
				
					for(var jj=0;jj<slcodelen;jj++)
						{
						var option=document.createElement("OPTION");
	  					option.value=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k)[jj].firstChild.nodeValue;
	  			//	alert(baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue);
	  					option.text=baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue;
	  					sel.appendChild(option); 
						}*/
					option.value=item[5];
	  	  			option.text=item[6];
	  	  					sel.appendChild(option); 	
					
					
					
					cell2.appendChild(sel);
					mycurrent_row.appendChild(cell2);
					
					
					
					var cell2 = document.createElement("TD");
					var sel=document.createElement("select");
					sel.id="SLtypecode1"+seq;
					sel.name="SLtypecode1"+seq;
					sel.setAttribute('style','width:400px');

					var option=document.createElement("OPTION");
					option.value=item[7];
					option.text=item[8];
					sel.appendChild(option);
				
					
					cell2.appendChild(sel);
					
					mycurrent_row.appendChild(cell2);
					
			
					
					var cell2 = document.createElement("TD");
					var assetcode1=document.createElement("input");
					assetcode1.type="text";
					assetcode1.name="sl_amt1";
					assetcode1.id="sl_amt1"+lll;
					assetcode.size=7;
					cell2.appendChild(assetcode1);
					cell2.align="right";
					mycurrent_row.appendChild(cell2);
					
					var cell2 = document.createElement("TD");
					var assetcode1=document.createElement("input");
					assetcode1.type="text";
					assetcode1.name="particular1";
					assetcode1.id="particular1";
					
					var assetcode = document.createTextNode("");
					assetcode.size=7;
					cell2.appendChild(assetcode);
					cell2.appendChild(assetcode1);
					cell2.align="right";
					mycurrent_row.appendChild(cell2);
					
					
					tbody.appendChild(mycurrent_row);
					lll++;
					 seq++;
			
        		
        		}         

   	
    		
        }
    	
    }
			    };
			   
			    xmlrequest.send(null);
		
		
	
	}

function getCrHEAD(val) {
	var journal_ty = document.getElementById("cmbMas_SL_type").value;
	var cmbMas_SL_Code = document.getElementById("cmbMas_SL_Code").value;
	
	var url = "../../../../../new_Contractor_LJV?command=loadcRhead&journal_ty="+ journal_ty+"&cmbMas_SL_Code="+cmbMas_SL_Code;
	//alert(url);
	var xmlrequest = getTransport();
	xmlrequest.open("POST", url, true);
	  xmlrequest.onreadystatechange=function()
	{
		    	 if (xmlrequest.readyState == 4) {

			if (xmlrequest.status == 200) {

				var baseResponse = xmlrequest.responseXML
						.getElementsByTagName("response")[0];

				var tagcommand = baseResponse.getElementsByTagName("command")[0];
				var Command = tagcommand.firstChild.nodeValue;

     
 var tbody=document.getElementById("grid_body");
 try{tbody.innerHTML="";}
catch(e) {tbody.innerText="";}
var leng = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE").length;


		lll=1;
		seq1=1;
	   tbody=document.getElementById("grid_body");
	   var item = new Array();
	   for(var k=0;k<leng;k++)
		{

	
				item[1]=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[k].firstChild.nodeValue;
				
				item[2] =baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;
				
			
				item[3] =baseResponse.getElementsByTagName("c")[k].firstChild.nodeValue;
				
			
			//	item[6] =baseResponse.getElementsByTagName("sub_ledger_name")[k].firstChild.nodeValue;
		
				var	mycurrent_row=document.createElement("TR");
				mycurrent_row.id=item[1];                     
				var cell1 = document.createElement("TD");
				var dateofentry1=document.createElement("input");
				dateofentry1.type="hidden";
				dateofentry1.name="sno";
				dateofentry1.id="sno";
				dateofentry1.value=seq1;
				var dateofentry = document.createTextNode(seq1);
				dateofentry.size=7;
				
				cell1.appendChild(dateofentry);
				cell1.appendChild(dateofentry1);
				mycurrent_row.appendChild(cell1);

				var cell2 = document.createElement("TD");
				var assetcode1=document.createElement("input");
				assetcode1.type="hidden";
				assetcode1.name="H_code";
				assetcode1.id="H_code";
				assetcode1.value=item[1];
				var assetcode = document.createTextNode(item[1]+'-'+item[2]);
				assetcode.size=7;
				cell2.appendChild(assetcode);
				cell2.appendChild(assetcode1);
				//..cell2.align="right";
				mycurrent_row.appendChild(cell2);
				
				var cell2 = document.createElement("TD");
				cell2.setAttribute('style', 'display:none;');
				var assetcode1=document.createElement("input");
				assetcode1.type="hidden";
				assetcode1.name="CR_DR_type";
				assetcode1.id="CR_DR_type";
				assetcode1.value="CR";
			/*	var assetcode = document.createTextNode("DR");
				assetcode.size=7;
				cell2.appendChild(assetcode);*/
				cell2.appendChild(assetcode1);
				//cell2.align="right";
				mycurrent_row.appendChild(cell2);
				
			/*	var cell2 = document.createElement("TD");
				var assetcode1=document.createElement("input");
				assetcode1.type="hidden";
				assetcode1.name="SLtype1";
				assetcode1.id="SLtype1";
				assetcode1.value="10";
				var assetcode = document.createTextNode("Project");
				assetcode.size=7;
				cell2.appendChild(assetcode);
				cell2.appendChild(assetcode1);
				cell2.align="right";
				mycurrent_row.appendChild(cell2);*/
				
				
				
			
				
				
				var cell2 = document.createElement("TD");
				var sel=document.createElement("select");
				sel.id="SLtype";
				sel.name="SLtype";
				
				
				sel.setAttribute('onclick','javascript:subledgercode(this.value,'+k+')');
				var option=document.createElement("OPTION");
				option.value="";
				option.text="--Select--";
				sel.appendChild(option);
				var slcodelen =baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k).length;
			
				for(var jj=0;jj<slcodelen;jj++)
					{
					var option=document.createElement("OPTION");
  					option.value=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE"+k)[jj].firstChild.nodeValue;
  			//	alert(baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue);
  					option.text=baseResponse.getElementsByTagName("sub_ledger_type_desc"+k)[jj].firstChild.nodeValue;
  					sel.appendChild(option); 
					}
				
				cell2.appendChild(sel);
				mycurrent_row.appendChild(cell2);
				
				var cell2 = document.createElement("TD");
				var sel=document.createElement("select");
				sel.id="SL_code"+k;
				sel.name="SL_code"+k;
				sel.setAttribute('style','width:400px');
				cell2.appendChild(sel);
				mycurrent_row.appendChild(cell2);
				
				

				
				var cell2 = document.createElement("TD");
				var assetcode1=document.createElement("input");
				assetcode1.type="text";
				assetcode1.name="sl_amt";
				assetcode1.id="sl_amt"+k;
				assetcode.size=7;
				cell2.appendChild(assetcode1);
				cell2.align="right";
				mycurrent_row.appendChild(cell2);
//				alert("s"+assetcode1.id);
				var cell2 = document.createElement("TD");
				var assetcode1=document.createElement("input");
				assetcode1.type="text";
				assetcode1.name="particular";
				assetcode1.id="particular";
				
				var assetcode = document.createTextNode("");
				assetcode.size=7;
				cell2.appendChild(assetcode);
				cell2.appendChild(assetcode1);
				cell2.align="right";
				mycurrent_row.appendChild(cell2);
				
				
				
				
				
				tbody.appendChild(mycurrent_row);
				seq1++;
		}
				
			}
		}


	};xmlrequest.send(null);
  
}