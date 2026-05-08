/** To handle Errors **/
/*onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page.\n\n";
	txt+="Error: " + msg + "\n";
	txt+="URL: " + url + "\n";
	txt+="Line: " + l + "\n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}*/

/** To create javascript request object **/
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

function checkNull_verify()
{
	var tbody=document.getElementById("tblList");
	if(tbody.rows.length==0){
	alert("No values Found");
	return false;
	}
	return true;
}



//This Coding for Date Validation and Checking     
function numInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=35 && unicode !=36 )
        {
             if (unicode<48||unicode>57 ) 
                return false ;
        }
}



function numbersonly1(e,t)
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
//This function allows users to enter Numbers only (with or without decimal places)
function numFloatInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(t.value.indexOf('.') != -1)alert('YES !');
        if((unicode == 8) || (unicode == 46) || (unicode == 37) || (unicode == 39)) 
        {
        	return true;
        	/*  8  --> Backspace
 			    46 --> Delete
 			    37 --> Left arrow
 			    39 --> Right arrow
        	 */
        }
        else
        {
	        var decimalPointIndex = (t.value.indexOf('.'));
	        if (unicode == 46)
	        {            
	            if (decimalPointIndex>=0)
	            return false;
	        }
	        else if (unicode<48 || unicode>57)
	        {
	            return false ;
	        }
        }
}


 function Exit()
 {
    self.close();
 }
 function UpdateTotallyValues()
 {
 	alert("update all values ");
 	
 	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
 	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
 	var assetmajor=document.getElementById("cmbmajorasset").value;
 	
     var tbody = document.getElementById("tblList");
     var rowCount=tbody.rows.length;
    
     var url="../../../../../A52_Register_OB_list?command=updateTotally&unit_id="+cmbAcc_UnitCode
     +"&office_id="+cmbOffice_code+"&financial_year="+cmbFinancialYear+"&assetmajor="+assetmajor;
     for(var i=1;i<=rowCount;i++)    
     {
    	 var assMajorCode=document.getElementById("majorcode"+i).value;
    	 var assMinorCode=document.getElementById("minorcode"+i).value;
    	 var assetcode =document.getElementById("assetcode"+i).value;
    		//var remark=document.getElementById("remark"+i).value;
			var remark_Edit=document.getElementById("remark_Edit"+i).value;
			
			var unitcode =document.getElementById("unitcode1"+i).value;
			var OPEN_BAL_QTY=document.getElementById("OPEN_BAL_QTY"+i).value;
			var OPEN_BAL_QTY_OLD=document.getElementById("OPEN_BAL_QTY_OLD"+i).value;
			if(OPEN_BAL_QTY!=OPEN_BAL_QTY_OLD)
				{
				remark_Edit+="OB-QtyChg frm"+OPEN_BAL_QTY_OLD+" to "+OPEN_BAL_QTY+"";
				}
			var OPENING_BAL_VALUE =document.getElementById("OPENING_BAL_VALUE"+i).value.split(".")[0];
			var RECIEPTS_YEAR_QTY=document.getElementById("RECIEPTS_YEAR_QTY"+i).value;
			var RECIEPTS_YEAR_QTY_OLD=document.getElementById("RECIEPTS_YEAR_QTY_OLD"+i).value;
			if(RECIEPTS_YEAR_QTY!=RECIEPTS_YEAR_QTY_OLD)
			{
			remark_Edit+="R-QtyChg frm"+RECIEPTS_YEAR_QTY_OLD+" to "+RECIEPTS_YEAR_QTY+"";
			}
			var RECIEPTS_YR_VALUE=document.getElementById("RECIEPTS_YR_VALUE"+i).value.split(".")[0];
			var ISSUES_YEAR_QTY = document.getElementById("ISSUES_YEAR_QTY"+i).value;
			var ISSUES_YEAR_QTY_OLD = document.getElementById("ISSUES_YEAR_QTY_OLD"+i).value;
			if(ISSUES_YEAR_QTY!=ISSUES_YEAR_QTY_OLD)
			{
			remark_Edit+="I-QtyChg frm"+ISSUES_YEAR_QTY_OLD+" to "+ISSUES_YEAR_QTY+"";
			}
			var ISSUES_YR_VALUE = document.getElementById("ISSUES_YR_VALUE"+i).value.split(".")[0];
			/*var DEP_PREV_YEAR = document.getElementById("DEP_PREV_YEAR"+i).value;
			var DEPRE_REC_AC =document.getElementById("DEPRE_REC_AC"+i).value;
			var DEPRE_ALLOWED_YR =document.getElementById("DEPRE_ALLOWED_YR"+i).value;
			var DEPRE_TR_AC=document.getElementById("DEPRE_TR_AC"+i).value;
			var DEPRE_UPTO_DATE =document.getElementById("DEPRE_UPTO_DATE"+i).value;
			var NET_DEPRE_COST=document.getElementById("NET_DEPRE_COST"+i).value.split(".")[0];;
			var APP_PRE_YR=document.getElementById("APP_PRE_YR"+i).value;
			var APP_GRANT_RECIEVED = document.getElementById("APP_GRANT_RECIEVED"+i).value;
			var APPRO_DURING_YR = document.getElementById("APPRO_DURING_YR"+i).value;
			var APP_GRANT_TR = document.getElementById("APP_GRANT_TR"+i).value;
			var APP_GRANT_UPTODATE = document.getElementById("APP_GRANT_UPTODATE"+i).value; */
		
			var offcode=document.getElementById("offcode"+i).value;
			
      url+="&assMinorCode="+assMinorCode+"&assetcode="+assetcode+"&OPEN_BAL_QTY="+OPEN_BAL_QTY+
 	"&OPENING_BAL_VALUE="+OPENING_BAL_VALUE+"&RECIEPTS_YEAR_QTY="+RECIEPTS_YEAR_QTY+"&RECIEPTS_YR_VALUE="+RECIEPTS_YR_VALUE+
 	"&ISSUES_YEAR_QTY="+ISSUES_YEAR_QTY+"&ISSUES_YR_VALUE="+ISSUES_YR_VALUE+"&remark="+remark_Edit+"&offcode="+offcode;
 	/*+DEP_PREV_YEAR+"&DEPRE_REC_AC="+DEPRE_REC_AC+
 	"&DEPRE_ALLOWED_YR="+DEPRE_ALLOWED_YR+"&DEPRE_TR_AC="+DEPRE_TR_AC+"&DEPRE_UPTO_DATE="+DEPRE_UPTO_DATE+"&NET_DEPRE_COST="+NET_DEPRE_COST+"&APP_PRE_YR="+APP_PRE_YR+
 	"&APP_GRANT_RECIEVED="+APP_GRANT_RECIEVED+"&APPRO_DURING_YR="+APPRO_DURING_YR+"&APP_GRANT_TR="+APP_GRANT_TR+"&APP_GRANT_UPTODATE="+APP_GRANT_UPTODATE+
 	"&remark="+remark*/
     }
    
 alert(url+"i "+i);
 	var req=getTransport();
    req.open("POST",url,true);  
 	
    req.onreadystatechange=function(){
    	processResponse(req);
    	};
    req.send(null);
 }
//******************************************** CallServer Coding *******************//
function callServer(command){  
           var accounting_unit_id=document.frmA52_Registerlist.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmA52_Registerlist.cmbOffice_code.value;
	   var assetmajor=document.frmA52_Registerlist.cmbmajorasset.value;
	  // var assetminor = document.frmA52_Registerlist.cmbminorasset.value;
	   var financial_year = document.frmA52_Registerlist.cmbFinancialYear.value;  
       var url="";
        if(command=="Go")
        {  
        	//alert("get values from db");
        	if(checkNull()){
        		  // callServer('checkVerifyA52')
        			url="../../../../../A52_Register_OB_list?command=GoGet&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;
        			//alert(url);
        			var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        		    }
        	//}
        }
        else if(command=="loadMajor"){
        	url="../../../../../A52_Register_OB_list?command=loadMajor";
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }else if(command=="loadMinor"){
        	url="../../../../../A52_Register_OB_list?command=loadMinor&assetmajor="+assetmajor;
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }else if(command=="loadAssetCode"){
        	url="../../../../../A52_Register_OB_list?command=loadAssetCode&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&assetmajor="+assetmajor+"&assetminor="+assetminor;
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }else if(command=="checkVerifyA52"){
        	//alert("inside check verfied");
        	url="../../../../../A52_Register_OB_list?command=checkVerifyA52Status&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year;
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }
}  

function common_LoadOfficeRedeploye()
{
	
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;     
  //  alert("unitID_val"+unitID_val);
    if(unitID_val!="")
    {
    	//alert("unit id ");
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../A52_Register_OB_list?command=LoadUnitWise_OfficeRedeploy&unit_id="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice_oly1(req);
        };
        req.send(null);
    }     
}

function handle_loadOffice_oly1(req)
{
  
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
      
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
         
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
           
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
       
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname+"("+offid+")";
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
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }
            
        }
        else
        {
          
         try
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
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }         
            
            
        }
            
             
     }
    }
}



function checkNull(){
	var accounting_unit_id=document.frmA52_Registerlist.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmA52_Registerlist.cmbOffice_code.value;
	   var assetmajor=document.frmA52_Registerlist.cmbmajorasset.value;
	   var financial_year = document.frmA52_Registerlist.cmbFinancialYear.value;  
	   if(financial_year==""){
		   alert("select Finanical year");
		   return false;
	   }else if(assetmajor==0){
		   alert("select Finanical year");
		   return false;
	   }
	   return true;
	
}

//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
{   
  if(req.readyState==4)
  {
      if(req.status==200)
      {   

    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          
          var command=tagCommand.firstChild.nodeValue; 
         // alert("command=="+command);
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    	       
               if(command=="updateTotally1")
              {
            	  // alert("command  inside updateTotally1 ");
            	  if(flag=="success")
            	  {
            		  alert("Records Updated into Database Successfully.");
            		  clearAll();
            	  }
            	  else
                  {
            		  alert("Failed to Update the record.");
                  }
              }
               else if(command=="checkVerifyA52")
               {
             	  // alert("command  inside updateTotally1 ");
             	  if(flag=="success")
             	  {
             		 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
             		// alert("exists"+exists);
             		  if(exists=="Yes"){
             			  
             			 var accunitid=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
             			 var a52status=baseResponse.getElementsByTagName("A52_STATUS")[0].firstChild.nodeValue;
             			// alert("a52status "+a52status);
             			 if(a52status=="Y"){
             				alert("You already Freezed A52 Quantity,So you cant edit the content"); 
             				//clearAll();
             			 }
             			// return true;
             			 
             		  }else {
             			  
             			 // alert("Not verify");
             			 callServer('Go');
             			 //return true;
             		  }
             		// return true;
             	  }
             	  else
                   {
             		  alert("Failed to Update the record.");
             		 
                   }
               }
              else if(command=="GoGet"){
            	 // alert("goget");
            	  getRow(baseResponse);
            	  
              }
            else if(command=="loadMajor")
              {
        		  var cmbMajorClass = document.getElementById('cmbmajorasset');
        		  cmbMajorClass.length=0;
        		 // var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        		
            	  if(flag=="success"){
            		  
            		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
              		  if(exists=="Yes"){
              		
            		  var mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE');
            		  
                	  var len = mjrCode.length;
            	  for(i=0; i<len; i++)
            	  {
            		  mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE')[i].firstChild.nodeValue;
            		  var mjrDesc = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_DESC')[i].firstChild.nodeValue;
            		  var opt = document.createElement("option");
            		  opt.value = mjrCode;
            		  opt.innerHTML = mjrDesc;
            		  
            		  cmbMajorClass.appendChild(opt);
            	  }
              		 }else{
             			  alert("No Records");
             		  }
            	  } else
			        {
				        alert("No Major AssetCode in Table");
				     
				        
				        }
            	 // fetchAlias();
            	  
              }
         
       
         
    	  }
     }
}

function  getRow(baseResponse)
{  
var seq=1;
//alert("inside get row from java to js");
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
  if(flag=="success")
    {          
        var tbody = document.getElementById("tblList");
       /* try{tbody.innerHTML="";}
        catch(e) {tbody.innerText="";}  */
        
        var table = document.getElementById("Existing");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(0);
            }                        
        var len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  
        //var len1=baseResponse.getElementsByTagName("asset_code")[0].length;  
        //alert(len);
        /*if (count!= 0) {
    		var item = new Array();
    		for ( var k = 0; k < count; k++) {*/
        if(len==0){
        	/*var mycurrent_row22=document.createElement("TR");
            mycurrent_row22.id=0;                     
            mycurrent_row22.align="center";
            var cell1 =document.createElement("TD");    
           cell1.setAttribute('colspan','20');
            var code=document.createTextNode("No records");                         
            cell1.appendChild(code);  
            cell1.align="center";
            mycurrent_row22.appendChild(cell1);        						

        	   tbody.appendChild(mycurrent_row22);*/
        	alert("No record ");
        	
        }else{
        	 var lll=1;
        	 var item = new Array();
        	 var codeMinor = new Array();
     		var descMinor = new Array();
               for(var k=0;k<len;k++)
                  {
  
            		item[0] =baseResponse.getElementsByTagName("asset_code")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("ASSETMINORCLASSCODE1")[k].firstChild.nodeValue;
					item[22] =baseResponse.getElementsByTagName("ASSET_MAJOR_CLASS_CODE")[k].firstChild.nodeValue;
					item[2] =baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("OPEN_BAL_QTY")[k].firstChild.nodeValue;
					item[4] =baseResponse.getElementsByTagName("OPENING_BAL_VALUE")[k].firstChild.nodeValue;
					item[5]=baseResponse.getElementsByTagName("RECIEPTS_YEAR_QTY")[k].firstChild.nodeValue;			
					item[6]=baseResponse.getElementsByTagName("RECIEPTS_YR_VALUE")[k].firstChild.nodeValue;	
					item[7] = baseResponse.getElementsByTagName("ISSUES_YEAR_QTY")[k].firstChild.nodeValue;
					item[8] = baseResponse.getElementsByTagName("ISSUES_YR_VALUE")[k].firstChild.nodeValue;
					
					item[9]=baseResponse.getElementsByTagName("N_OPEN_BAL_QTY")[k].firstChild.nodeValue;
					item[10]=baseResponse.getElementsByTagName("N_OPENING_BAL_VALUE")[k].firstChild.nodeValue;
					item[11]=baseResponse.getElementsByTagName("N_RECIEPTS_YEAR_QTY")[k].firstChild.nodeValue;			
					item[12]=baseResponse.getElementsByTagName("N_RECIEPTS_YR_VALUE")[k].firstChild.nodeValue;	
					item[13]= baseResponse.getElementsByTagName("N_ISSUES_YEAR_QTY")[k].firstChild.nodeValue;
					item[14]= baseResponse.getElementsByTagName("N_ISSUES_YR_VALUE")[k].firstChild.nodeValue;
					
					/*
					item[9] = baseResponse.getElementsByTagName("DEP_PREV_YEAR")[k].firstChild.nodeValue;
					item[10] =baseResponse.getElementsByTagName("DEPRE_REC_AC")[k].firstChild.nodeValue;
					item[11] =baseResponse.getElementsByTagName("DEPRE_ALLOWED_YR")[k].firstChild.nodeValue;
					item[12]=baseResponse.getElementsByTagName("DEPRE_TR_AC")[k].firstChild.nodeValue;
					item[13] =baseResponse.getElementsByTagName("DEPRE_UPTO_DATE")[k].firstChild.nodeValue;
					item[14]=baseResponse.getElementsByTagName("NET_DEPRE_COST")[k].firstChild.nodeValue;	*/		
					item[15]=baseResponse.getElementsByTagName("APP_PRE_YR")[k].firstChild.nodeValue;	
					item[16] = baseResponse.getElementsByTagName("APP_GRANT_RECIEVED")[k].firstChild.nodeValue;
					item[17]= baseResponse.getElementsByTagName("APPRO_DURING_YR")[k].firstChild.nodeValue;
					item[18] = baseResponse.getElementsByTagName("APP_GRANT_TR")[k].firstChild.nodeValue;
					item[19] = baseResponse.getElementsByTagName("APP_GRANT_UPTODATE")[k].firstChild.nodeValue;
					
					item[20]=baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
					item[21]=baseResponse.getElementsByTagName("accounting_unit_office_id")[k].firstChild.nodeValue;
					item[23]=baseResponse.getElementsByTagName("accounting_unit_id")[k].firstChild.nodeValue;
					item[24]=baseResponse.getElementsByTagName("offName")[k].firstChild.nodeValue;
					//alert("item[1]  "+item[1] );
                       var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=item[0];                     
                       var cell0 = document.createElement("TD");
            			var sno1=document.createElement("input");
            			sno1.type="hidden";
            			sno1.name="sno";
            			sno1.id="sno";
            			sno1.value=seq;
            			var sno2 = document.createTextNode(seq);
            			cell0.appendChild(sno2);
            			cell0.appendChild(sno1);
            			mycurrent_row.appendChild(cell0);
            			
            			var offn = document.createElement("TD");
               			/*var offn1=document.createElement("input");
               			offn1.type="hidden";
               			offn1.name="offcodeName"+seq;
               			offn1.id="offcodeName"+seq;
               			offn1.value=item[21];
               			offn1.size=1;          			
               			offn.appendChild(offn1);*/
               			var offn2 = document.createTextNode(item[24]);
               			offn.appendChild(offn2);
               			offn.style.textAlign="left";
               			mycurrent_row.appendChild(offn);
               			
            			
            			 var cellpar = document.createElement("TD");
            			var particulars_set=document.createElement("label");
            			/*particulars_set.type="text";*/
            			particulars_set.name="Particulars";
            			particulars_set.id="Particulars";
            			
            			particulars_set.innerHTML=item[2];
            			particulars_set.size=7;
               			
            			cellpar.appendChild(particulars_set);
            			cellpar.style.textAlign="left";
               			mycurrent_row.appendChild(cellpar);
               			
               			var cell3_dup = document.createElement("TD");
               			//cell3_dup.style.display="none";
               			var OPEN_BAL_QTY_old=document.createElement("input");
               			OPEN_BAL_QTY_old.type="hidden";
               			OPEN_BAL_QTY_old.name="OPEN_BAL_QTY_OLD";
               			OPEN_BAL_QTY_old.id="OPEN_BAL_QTY_OLD";
               			OPEN_BAL_QTY_old.value=item[3];
               			OPEN_BAL_QTY_old.size=7;
               			var OPEN_BAL_QTY2 = document.createTextNode(item[3]);
               			cell3_dup.appendChild(OPEN_BAL_QTY2);
               			cell3_dup.appendChild(OPEN_BAL_QTY_old);
               			mycurrent_row.appendChild(cell3_dup);
           			
           			var cell3 = document.createElement("TD");
           			var OPEN_BAL_QTY1=document.createElement("input");
           			OPEN_BAL_QTY1.type="text";
           			OPEN_BAL_QTY1.name="OPEN_BAL_QTY";
           			OPEN_BAL_QTY1.id="OPEN_BAL_QTY";
           			OPEN_BAL_QTY1.value=item[9];
           			OPEN_BAL_QTY1.size=7;
           			/*var OPEN_BAL_QTY2 = document.createTextNode(OPEN_BAL_QTY);
           			cell3.appendChild(OPEN_BAL_QTY2);*/
           			cell3.appendChild(OPEN_BAL_QTY1);
           			mycurrent_row.appendChild(cell3);
           			
           			
           			
           			var cell4 = document.createElement("TD");
           			cell4.style.display="none";
           			var OPENING_BAL_VALUE1=document.createElement("input");
           			OPENING_BAL_VALUE1.type="hidden";
           			OPENING_BAL_VALUE1.size=7;
           			OPENING_BAL_VALUE1.name="OPENING_BAL_VALUE";
           			OPENING_BAL_VALUE1.id="OPENING_BAL_VALUE";
           			OPENING_BAL_VALUE1.value=item[4];
           			var lab_ob=document.createElement("label");
           			lab_ob.innerHTML=item[4];
           			//var bank_bal2 = document.createTextNode(bank_bal);
           		   // cell3.style.textAlign="right";
           			cell4.appendChild(lab_ob);
           			cell4.appendChild(OPENING_BAL_VALUE1);
           			mycurrent_row.appendChild(cell4);
           			
           			var cell_old = document.createElement("TD");
           			//cell_old.style.display="none";
           			var RECIEPTS_YEAR_QTY_old=document.createElement("input");
           			RECIEPTS_YEAR_QTY_old.type="hidden";
           			RECIEPTS_YEAR_QTY_old.size=8;
           			RECIEPTS_YEAR_QTY_old.name="RECIEPTS_YEAR_QTY_OLD";
           			RECIEPTS_YEAR_QTY_old.id="RECIEPTS_YEAR_QTY_OLD";
           			RECIEPTS_YEAR_QTY_old.value=item[5];
           			var dtorcr2 = document.createTextNode(item[5]);
           			cell_old.appendChild(dtorcr2);
           			cell_old.appendChild(RECIEPTS_YEAR_QTY_old);
           			mycurrent_row.appendChild(cell_old);
           			
           			var cell5 = document.createElement("TD");
           			var RECIEPTS_YEAR_QTY1=document.createElement("input");
           			RECIEPTS_YEAR_QTY1.type="text";
           			RECIEPTS_YEAR_QTY1.size=8;
           			RECIEPTS_YEAR_QTY1.name="RECIEPTS_YEAR_QTY";
           			RECIEPTS_YEAR_QTY1.id="RECIEPTS_YEAR_QTY";
           			RECIEPTS_YEAR_QTY1.value=item[11];
           			//var dtorcr2 = document.createTextNode(dtorcr);
           			//cell5.appendChild(dtorcr2);
           			cell5.appendChild(RECIEPTS_YEAR_QTY1);
           			mycurrent_row.appendChild(cell5);
           			
           			
           			
           		var cell6 = document.createElement("TD");
           		cell6.style.display="none";
           			var RECIEPTS_YR_VALUE1=document.createElement("input");
           			RECIEPTS_YR_VALUE1.type="hidden";
           			//RECIEPTS_YR_VALUE1.size=8;
           			RECIEPTS_YR_VALUE1.name="RECIEPTS_YR_VALUE";
           			RECIEPTS_YR_VALUE1.id="RECIEPTS_YR_VALUE";
           			RECIEPTS_YR_VALUE1.value=item[6];	
           		
           			var rec_ob_label = document.createElement("label");
           			rec_ob_label.innerHTML=item[6];	
           			cell6.appendChild(rec_ob_label);
           			cell6.appendChild(RECIEPTS_YR_VALUE1);
           			mycurrent_row.appendChild(cell6);
           			
           			var cell7_dup = document.createElement("TD");
           			//cell7_dup.style.display="none";
           			var ISSUES_YEAR_QTY_old=document.createElement("input");
           			 ISSUES_YEAR_QTY_old.type="hidden";
           			//ISSUES_YEAR_QTY1.size=7;
           			ISSUES_YEAR_QTY_old.name="ISSUES_YEAR_QTY_OLD";
           			ISSUES_YEAR_QTY_old.id="ISSUES_YEAR_QTY_OLD";
           			ISSUES_YEAR_QTY_old.value=item[7];			
           			var remark2 = document.createTextNode(item[7]);
           			cell7_dup.appendChild(remark2);
           			cell7_dup.appendChild(ISSUES_YEAR_QTY_old);
           			mycurrent_row.appendChild(cell7_dup);
           			
           			
           			var cell7 = document.createElement("TD");
           			var ISSUES_YEAR_QTY1=document.createElement("input");
           			ISSUES_YEAR_QTY1.type="text";
           			ISSUES_YEAR_QTY1.size=7;
           			ISSUES_YEAR_QTY1.name="ISSUES_YEAR_QTY";
           			ISSUES_YEAR_QTY1.id="ISSUES_YEAR_QTY";
           			ISSUES_YEAR_QTY1.value=item[13];			
           			//var remark2 = document.createTextNode(remark);
           			//cell6.appendChild(remark2);
           			cell7.appendChild(ISSUES_YEAR_QTY1);
           			mycurrent_row.appendChild(cell7);
           			
           			
           			
           			var cell8 = document.createElement("TD");
           			cell8.style.display="none";
           			var ISSUES_YR_VALUE1=document.createElement("input");
           			ISSUES_YR_VALUE1.type="hidden";
           			//ISSUES_YR_VALUE1.size=7;
           			ISSUES_YR_VALUE1.name="ISSUES_YR_VALUE";
           			ISSUES_YR_VALUE1.id="ISSUES_YR_VALUE";
           			ISSUES_YR_VALUE1.value=item[8];	
           			var iss_ob_label = document.createElement("label");
           			iss_ob_label.innerHTML=item[8];	
           			cell8.appendChild(iss_ob_label);
           			cell8.appendChild(ISSUES_YR_VALUE1);
           			mycurrent_row.appendChild(cell8);
           			
           			/*var cell8 = document.createElement("TD");
           			var DEPRE_UPTO_DATE1=document.createElement("input");
           			DEPRE_UPTO_DATE1.type="text";
           			DEPRE_UPTO_DATE1.size=7;
           			DEPRE_UPTO_DATE1.name="DEPRE_UPTO_DATE"+seq;
           			DEPRE_UPTO_DATE1.id="DEPRE_UPTO_DATE"+seq;
           			DEPRE_UPTO_DATE1.value=item[13];			
           			cell8.appendChild(DEPRE_UPTO_DATE1);
           			mycurrent_row.appendChild(cell8);
           			//var cell19 = document.createElement("TD");
           			var APP_GRANT_UPTODATE1=document.createElement("input");
           			APP_GRANT_UPTODATE1.type="text";
           			APP_GRANT_UPTODATE1.size=7;
           			APP_GRANT_UPTODATE1.name="APP_GRANT_UPTODATE"+seq;
           			APP_GRANT_UPTODATE1.id="APP_GRANT_UPTODATE"+seq;
           			APP_GRANT_UPTODATE1.value=item[19];			
           			cell8.appendChild(APP_GRANT_UPTODATE1);
           			mycurrent_row.appendChild(cell8);
           			
           			var cell9 = document.createElement("TD");
           			var DEP_PREV_YEAR1=document.createElement("input");
           			DEP_PREV_YEAR1.type="text";
           			DEP_PREV_YEAR1.size=7;
           			DEP_PREV_YEAR1.name="DEP_PREV_YEAR"+seq;
           			DEP_PREV_YEAR1.id="DEP_PREV_YEAR"+seq;
           			DEP_PREV_YEAR1.value=item[9];			
           			cell9.appendChild(DEP_PREV_YEAR1);
           			mycurrent_row.appendChild(cell9);
           			//var cell15 = document.createElement("TD");
           			var APP_PRE_YR1=document.createElement("input");
           			APP_PRE_YR1.type="text";
           			APP_PRE_YR1.size=7;
           			APP_PRE_YR1.name="APP_PRE_YR"+seq;
           			APP_PRE_YR1.id="APP_PRE_YR"+seq;
           			APP_PRE_YR1.value=item[15];			
           			cell9.appendChild(APP_PRE_YR1);
           			mycurrent_row.appendChild(cell9);           			
           			
           			var cell10 = document.createElement("TD");
           			var DEPRE_REC_AC1=document.createElement("input");
           			DEPRE_REC_AC1.type="text";
           			DEPRE_REC_AC1.size=7;
           			DEPRE_REC_AC1.name="DEPRE_REC_AC"+seq;
           			DEPRE_REC_AC1.id="DEPRE_REC_AC"+seq;
           			DEPRE_REC_AC1.value=item[10];			
           			//var remark2 = document.createTextNode(remark);
           			//cell6.appendChild(remark2);
           			cell10.appendChild(DEPRE_REC_AC1);
           			mycurrent_row.appendChild(cell10);
           			//var cell16 = document.createElement("TD");
           			var APP_GRANT_RECIEVED1=document.createElement("input");
           			APP_GRANT_RECIEVED1.type="text";
           			APP_GRANT_RECIEVED1.size=7;
           			APP_GRANT_RECIEVED1.name="APP_GRANT_RECIEVED"+seq;
           			APP_GRANT_RECIEVED1.id="APP_GRANT_RECIEVED"+seq;
           			APP_GRANT_RECIEVED1.value=item[16];			
           			cell10.appendChild(APP_GRANT_RECIEVED1);
           			mycurrent_row.appendChild(cell10);
            			
           			var cell11 = document.createElement("TD");
           			var DEPRE_ALLOWED_YR1=document.createElement("input");
           			DEPRE_ALLOWED_YR1.type="text";
           			DEPRE_ALLOWED_YR1.size=7;
           			DEPRE_ALLOWED_YR1.name="DEPRE_ALLOWED_YR"+seq;
           			DEPRE_ALLOWED_YR1.id="DEPRE_ALLOWED_YR"+seq;
           			DEPRE_ALLOWED_YR1.value=item[11];			
           			cell11.appendChild(DEPRE_ALLOWED_YR1);
           			mycurrent_row.appendChild(cell11);
           			//var cell17 = document.createElement("TD");
           			var APPRO_DURING_YR1=document.createElement("input");
           			APPRO_DURING_YR1.type="text";
           			APPRO_DURING_YR1.size=7;
           			APPRO_DURING_YR1.name="APPRO_DURING_YR"+seq;
           			APPRO_DURING_YR1.id="APPRO_DURING_YR"+seq;
           			APPRO_DURING_YR1.value=item[17];			
           			cell11.appendChild(APPRO_DURING_YR1);
           			mycurrent_row.appendChild(cell11);
           			
           			var cell12 = document.createElement("TD");
           			var DEPRE_TR_AC1=document.createElement("input");
           			DEPRE_TR_AC1.type="text";
           			DEPRE_TR_AC1.size=7;
           			DEPRE_TR_AC1.name="DEPRE_TR_AC"+seq;
           			DEPRE_TR_AC1.id="DEPRE_TR_AC"+seq;
           			DEPRE_TR_AC1.value=item[12];			
           			cell12.appendChild(DEPRE_TR_AC1);
           			mycurrent_row.appendChild(cell12);
           			//var cell18 = document.createElement("TD");
           			var APP_GRANT_TR1=document.createElement("input");
           			APP_GRANT_TR1.type="text";
           			APP_GRANT_TR1.size=7;
           			APP_GRANT_TR1.name="APP_GRANT_TR"+seq;
           			APP_GRANT_TR1.id="APP_GRANT_TR"+seq;
           			APP_GRANT_TR1.value=item[18];			
           			cell12.appendChild(APP_GRANT_TR1);
           			mycurrent_row.appendChild(cell12);
           				
           			var cell14 = document.createElement("TD");
           			var NET_DEPRE_COST1=document.createElement("input");
           			NET_DEPRE_COST1.type="text";
           			NET_DEPRE_COST1.size=8;
           			NET_DEPRE_COST1.name="NET_DEPRE_COST"+seq;
           			NET_DEPRE_COST1.id="NET_DEPRE_COST"+seq;
           			NET_DEPRE_COST1.value=item[14];			
           			cell14.appendChild(NET_DEPRE_COST1);
           			mycurrent_row.appendChild(cell14);  	*/		
           			
           			var cell20 = document.createElement("TD");
           			cell20.style.display="none";
           			var remark1=document.createElement("input");
           			remark1.type="text";
           			remark1.size=8;
           			remark1.name="remark";
           			remark1.id="remark";
           			remark1.value=item[20];			
           			cell20.appendChild(remark1);
           			mycurrent_row.appendChild(cell20);

           			var cell1 = document.createElement("TD");
           			cell1.style.display="none";
           			var assetcode1=document.createElement("input");
           			assetcode1.type="hidden";
           			assetcode1.name="assetcode";
           			assetcode1.id="assetcode";
           			assetcode1.value=item[0];
           			assetcode1.size=1;
           			/*var assetcode2 = document.createTextNode(item[0]);
           			cell1.appendChild(assetcode2);*/
           			cell1.appendChild(assetcode1);
           			//cell1.appendChild(st_name_no1);
           			mycurrent_row.appendChild(cell1);
           			
           			var cell_unit = document.createElement("TD");
           			cell_unit.style.display="none";
           			var unitcode1=document.createElement("input");
           			unitcode1.type="hidden";
           			unitcode1.name="unitcode1";
           			unitcode1.id="unitcode1";
           			unitcode1.value=item[23];
           			unitcode1.size=1;          			
           			cell_unit.appendChild(unitcode1);
           			mycurrent_row.appendChild(cell_unit);
           			
           			var cell111 = document.createElement("TD");
           			cell111.style.display="none";
           			var offcode1=document.createElement("input");
           			offcode1.type="hidden";
           			offcode1.name="offcode";
           			offcode1.id="offcode";
           			offcode1.value=item[21];
           			offcode1.size=1;          			
           			cell111.appendChild(offcode1);
           			mycurrent_row.appendChild(cell111);
           			
           			var cell112 = document.createElement("TD");
           			cell112.style.display="none";
           			var majorcode1=document.createElement("input");
           			majorcode1.type="hidden";
           			majorcode1.name="majorcode";
           			majorcode1.id="majorcode";
           			majorcode1.value=item[22];
           			majorcode1.size=1;          			
           			cell112.appendChild(majorcode1);
           			mycurrent_row.appendChild(cell112);
           			
           			var cell113 = document.createElement("TD");
           			cell113.style.display="none";
           			var minorcode1=document.createElement("input");
           			minorcode1.type="hidden";
           			minorcode1.name="minorcode";
           			minorcode1.id="minorcode";
           			minorcode1.value=item[1];
           			minorcode1.size=1;          			
           			cell113.appendChild(minorcode1);
           			mycurrent_row.appendChild(cell113);
           			
           			/*var cell_remarks = document.createElement("TD");
           			cell_remarks.style.display="none";
           			var remark_Edit=document.createElement("input");
           			remark_Edit.type="hidden";
           			remark_Edit.name="remark_Edit"+seq;
           			remark_Edit.id="remark_Edit"+seq;
           			remark_Edit.value=item[20];			
           			cell_remarks.appendChild(remark_Edit);
           			mycurrent_row.appendChild(cell_remarks);
           			*/
           			//lll++;
                    tbody.appendChild(mycurrent_row);
                    seq++;  
                  }

        	
        }
         }
  else
  {
    alert("Failed to Load Values");
  }           
}

function clearAll()
{
	  document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	  document.getElementById('cmbOffice_code').options[0].selected = "selected";
	  document.getElementById('cmbFinancialYear').value = "selected";
	  document.getElementById('cmbmajorasset').value = 0;
	 // document.getElementById('cmbminorasset').value = 0;
	  var tbody = document.getElementById("tblList");
      
      var table = document.getElementById("Existing");
      var t=0;
      for(t=tbody.rows.length-1;t>=0;t--)
          {
             tbody.deleteRow(0);
          } 

}



function loadUnits()
{
	
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
	var url ="../../../../../A52_Register_OB_list?command=loadUnitRendering&unit_id="+unitid ;
	//alert(url);		
	
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}
function loadUnitsDelete() {
	 var unit_rendered = document.getElementById("cmbOffice_code");
	 unit_rendered.length=0;
	 var codeHeads = "--office id --";
    
               var opt1 = document.createElement('option');
               opt1.value = 0;
               opt1.innerHTML = codeHeads; //instead of using textnode ,we use innerhtml
               unit_rendered.appendChild(opt1);             
               return true;
	
}

function manipulate1(xmlrequest) {

	if (xmlrequest.readyState == 4) {
	
		if (xmlrequest.status == 200) {
			

			var baseResponse1 = xmlrequest.responseXML
			.getElementsByTagName("response")[0];
			
			
			var tagCommand = baseResponse1.getElementsByTagName("command")[0];
		

			var command = tagCommand.firstChild.nodeValue;
			
			
			 if (command=="unitLoad")
			{
				
				 var i = 0;
					var flag = baseResponse1.getElementsByTagName("flag")[0].firstChild.nodeValue;
					var count = baseResponse1.getElementsByTagName("count")[0].firstChild.nodeValue;
				    if(flag=="success"){
				    	var len4 = baseResponse1.getElementsByTagName("unit_No").length;
				    	var se = document.getElementById("cmbOffice_code");
				    	se.length=0;
				    	//alert("len4 "+len4);
						for (i=0 ; i < len4; i++) {
							var unit_No = baseResponse1.getElementsByTagName("unit_No")[i].firstChild.nodeValue;
							var desc = baseResponse1.getElementsByTagName("desc")[i].firstChild.nodeValue;
							var op = document.createElement("OPTION");
							op.value = unit_No;
							var txt = document.createTextNode(desc);
							op.appendChild(txt);
							se.appendChild(op);
						}
				    	/*var len4 = baseResponse1.getElementsByTagName("unit_No").length;
				    	var se = document.getElementById("cmbOffice_code");
				    	se.length=0;
				    	//alert("len4 "+len4);
						for (i=0 ; i < len4; i++) {
							var unit_No = baseResponse1
									.getElementsByTagName("RENDERING_UNIT_OFFICE_ID")[i].firstChild.nodeValue;
							var desc = baseResponse1
									.getElementsByTagName("desc")[i].firstChild.nodeValue;
							var op = document.createElement("OPTION");
							op.value = unit_No;
							var txt = document.createTextNode(desc);
							op.appendChild(txt);
							se.appendChild(op);
						}*/
				        }
				        else
				        {	
				        alert("No Record Exist");
				      /*  document.frmA52_Register_OBEntry.allid[0].checked=true;
				        document.frmA52_Register_OBEntry.allid[1].checked=false;*/
				        }
				//unitLoad(baseResponse);
			}
			
	}
}
}



