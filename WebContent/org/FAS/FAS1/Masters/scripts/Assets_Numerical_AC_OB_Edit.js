//Assets_Numerical_AC_OB_Edit
/** To handle Errors **/
/*onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page. \n\n";
	txt+="Error: " + msg + " \n";
	txt+="URL: " + url + " \n";
	txt+="Line: " + l + " \n\n";
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
 if (!req && typeof XMLHttpRequest!='undefined') 
 {
	
       req = new XMLHttpRequest();
 }  
 
 return req;
 
}
function checkNull_verify()
{
      var tbody=document.getElementById("tblList");
    /*if(tbody.rows.length==0){
    alert("No values Found");
    return false;
    }*/
      if((tbody.rows.length==1)){
            var rowcount=tbody.rows.length;
             for(var i=0;i<rowcount;i++)
                 {
                    var r=tbody.rows[i];
                    var s=r.cells.length;
                    if(s==1){
                       alert("No values found to update");
                       return false;
                    }else{
                        return true;
                    }
                 }
        }else if((tbody.rows.length==0)){
            alert("No values Found to update");
            return false;
        }
     // document.getElementById("updateTotally").disabled=true;
        return true;
}
/*
function checkNull_verify()
{
	  var tbody=document.getElementById("tblList");
	if(tbody.rows.length==0){
	alert("No values Found");
	return false;
	}
	return true;
}
*/

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
 	//alert("update all values ");
 	
 	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
 	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
 	var assetmajor=document.getElementById("cmbmajorasset").value;
 	var dateofentry="";
	 var assetcode ="";
	 var qtyavlasondate ="";
		var locationdesc="";
		var status="";
		var remarks="";
		var qtyavailable = "";
		var officeid = "";
		var correctqty = "";
		var correctqtyremark="";
     var tbody = document.getElementById("tblList");
     var rowcount=tbody.rows.length;
    alert(rowcount+"rowCount update");
    
 	var t;
	var k=0;
 	if(rowcount==0)
 	{
 		alert("Wait..... The Grid Not Yet Loaded");
 		
 		return true;
 	}
 	//alert(rowcount);
 	var al= new Array() ;
    
     for(var i=0;i<rowcount;i++)
     	{
     	   var r=tbody.rows[i];
     	   var s=r.cells.length;
     	  //alert("s  "+s);
 	  
     	   for(var j=1;j<s;j++)
     		   {
     		   
     		   if(j<8)
     			   {
     		   al[k]=r.cells[j].firstChild.nodeValue;
     		   }
     	   else if(j==8 || j==9)
     		   {
     		  al[k]=r.cells[j].lastChild.value;
     		   }
     	   else
     		   {
     		  al[k]=r.cells[j].firstChild.nodeValue;
     		   }
     		 //  alert("::::"+al[k]);
     		    k++; 
     		    //alert(r.cells[j].firstChild.nodeValue);
     		   
     		   }
     	//alert("al "+al);
     	}
     alert("kkk "+k);
     alert("length  arraty "+al.length);
     var url="../../../../../Assets_Numerical_AC_OB_Edit?command=updateTotally&unit_id="+cmbAcc_UnitCode
     +"&office_id="+cmbOffice_code+"&financial_year="+cmbFinancialYear+"&assetmajor="+assetmajor+"&rowcount="+rowcount+"&grid="+al;
     /*for(var i=0;i<rowCount;i++)    
     {
    	 var dateofentry=document.getElementById("dateofentry"+i).value;
    	 var assetcode =document.getElementById("assetcode"+i).value;
		//	var qtyavlasondate =document.getElementById("qtyavlasondate"+i).value.split(".")[0];
    	 var qtyavlasondate =document.getElementById("qtyavlasondate"+i).value;
			var locationdesc=document.getElementById("locationdesc"+i).value;
			var status=document.getElementById("status"+i).value;
			var remarks=document.getElementById("remarks"+i).value;
			var qtyavailable = document.getElementById("qtyavailable"+i).value;
			var officeid = document.getElementById("officeid"+i).value;
			var correctqty = document.getElementById("correctqty"+i).value;
			var correctqtyremark = document.getElementById("correctqtyremark"+i).value;
			
      url+="&dateofentry="+dateofentry+"&assetcode="+assetcode+"&qtyavlasondate="+qtyavlasondate+
 	"&locationdesc="+locationdesc+"&status="+status+"&qtyavailable="+qtyavailable+
 	"&remarks="+remarks+"&officeid="+officeid+"&correctqty="+correctqty+"&correctqtyremark="+correctqtyremark;
     }*/
    /* if(rowCount>0){
alert("rowCount "+rowCount);
         for(var i=0;i<rowCount;i++)    
         {
        	 //alert(document.frmAssetsNumericalEdit.dateofentry[i].value);
        	dateofentry=dateofentry+document.frmAssetsNumericalEdit.dateofentry[i].value+",";
        	assetcode =assetcode+document.frmAssetsNumericalEdit.assetcode[i].value+",";
    	  qtyavlasondate =qtyavlasondate+document.frmAssetsNumericalEdit.qtyavlasondate[i].value+",";
    	  locationdesc=locationdesc+document.frmAssetsNumericalEdit.locationdesc[i].value+",";
    	  status=status+document.frmAssetsNumericalEdit.status[i].value+",";
    	  remarks=remarks+document.frmAssetsNumericalEdit.remarks[i].value+",";
    	  qtyavailable = qtyavailable+frmAssetsNumericalEdit.qtyavailable[i].value+",";
    	 officeid = officeid+document.frmAssetsNumericalEdit.officeid[i].value+",";
    	 correctqty =correctqty+ document.frmAssetsNumericalEdit.correctqty[i].value+",";
    	  correctqtyremark = correctqtyremark+document.frmAssetsNumericalEdit.correctqtyremark[i].value+",";
    	//	alert("rowCount inside "+rowCount+" dateofentry =="+dateofentry);	
         
         }

    }else{
    	dateofentry=document.frmAssetsNumericalEdit.dateofentry[i].value;
    	assetcode =document.frmAssetsNumericalEdit.assetcode[i].value;
	qtyavlasondate =document.frmAssetsNumericalEdit.qtyavlasondate[i].value;
	locationdesc=document.frmAssetsNumericalEdit.locationdesc[i].value;
	status=document.frmAssetsNumericalEdit.status[i].value;
	remarks=document.frmAssetsNumericalEdit.remarks[i].value;
	qtyavailable = document.frmAssetsNumericalEdit.qtyavailable[i].value;
	officeid = document.frmAssetsNumericalEdit.officeid[i].value;
	correctqty =document.frmAssetsNumericalEdit.correctqty[i].value;
	correctqtyremark = document.frmAssetsNumericalEdit.correctqtyremark[i].value;

    }
     url+="&dateofentry="+dateofentry+"&assetcode="+assetcode+"&qtyavlasondate="+qtyavlasondate+
  	"&locationdesc="+locationdesc+"&status="+status+"&qtyavailable="+qtyavailable+
  	"&remarks="+remarks+"&officeid="+officeid+"&correctqty="+correctqty+"&correctqtyremark="+correctqtyremark;
     document.frmAssetsNumericalEdit.method="post"; 
     document.frmAssetsNumericalEdit.action="../../../../../Assets_Numerical_AC_OB_Edit?command=submit"; 
     document.frmAssetsNumericalEdit.submit(); 
        */
 	alert(url);
 	var req=getTransport();
    req.open("GET",url,true);   
    req.onreadystatechange=function()
    {
       processResponse(req);
    };   
    req.send(null);
 }
//******************************************** CallServer Coding *******************//
function callServer(command){  
          
       var url="";
        if(command=="Go")
        {  
        	 var accounting_unit_id=document.frmAssetsNumericalEdit.cmbAcc_UnitCode.value;
      	   var accounting_unit_office_id = document.frmAssetsNumericalEdit.cmbOffice_code.value;
      	   var assetmajor=document.frmAssetsNumericalEdit.cmbmajorasset.value;
      	   var financial_year = document.frmAssetsNumericalEdit.cmbFinancialYear.value;  
      	  // var assetmajor=document.getElementById("cmbmajorasset").value;
      	   var assetmajor=document.frmAssetsNumericalEdit.cmbmajorasset.value;
        			url="../../../../../Assets_Numerical_AC_OB_Edit?command=GoGet&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;
        			var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        }
        else if(command=="loadMajor"){
        	url="../../../../../Assets_Numerical_AC_OB_Edit?command=loadMajor";
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }else if(command=="checkVerifyA52"){
        	 var accounting_unit_id=document.frmAssetsNumericalEdit.cmbAcc_UnitCode.value;
      	   var accounting_unit_office_id = document.frmAssetsNumericalEdit.cmbOffice_code.value;
      	   var assetmajor=document.frmAssetsNumericalEdit.cmbmajorasset.value;
      	   
        	//alert("inside check verfied");
        	url="../../../../../Assets_Numerical_AC_OB_Edit?command=checkVerifyA52&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id;
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }
}  
/*function officeload() {
	 var accounting_unit_id=document.frmAssetsNumericalEdit.cmbAcc_UnitCode.value;
	  
	var url="../../../../../A52_Value_Edit?command=officeload&unit_id="+accounting_unit_id;
	var req=getTransport();
req.open("GET",url,true);  
req.onreadystatechange=function(){
   processResponse(req);
};   
req.send(null);

}
*/
function common_LoadOfficeCode()
{
	
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;     
  // alert("unitID_val"+unitID_val);
    if(unitID_val!="")
    {
    	
    	//alert("unit id ");
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Freeze_Qty_A52?command=LoadUnitWise_OfficeCode&cmbAcc_UnitCode="+cmbAcc_UnitCode;
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
                var uuid=baseresponse.getElementsByTagName("uuid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname+"("+offid+")"+"("+uuid+")";
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
            	   //alert("command  inside updateTotally1 ");
            	  if(flag=="success")
            	  {
            		  alert("Records Updated Successfully.");
            		  //clearAll();
            	  }
            	  else
                  {
            		  alert("Failed to Update the record.");
                  }
              }
               else if(command=="officeload")
      	     {
      	 	  officeload1(baseResponse);
      	     }
              else if(command=="GoGet"){
            	 // alert("goget");
            	  GoGet(baseResponse);
            	  
              }else if(command=="checkVerifyA52")
               {
             	  // alert("command  inside updateTotally1 ");
             	  if(flag=="success")
             	  {
             		 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
             		// alert("exists"+exists);
             		  if(exists=="Yes"){
             			  
             			 var accunitid=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
             			 var a52status=baseResponse.getElementsByTagName("NUM_OB_EDIT_STATUS")[0].firstChild.nodeValue;
             			// alert("a52status "+a52status);
             			 if(a52status=="Y"){
             				alert("You already Freezed ,So you cant edit the content"); 
             				//return false;
             				//clearAll();
             			 }
             			// return true;
             			 
             		  }else {
             			  
             			  alert("Not verify");
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
            	  
              }
       
         
    	  }
     }
}

function  GoGet(baseResponse)
{  
var seq=0;
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
        var verfiyst=baseResponse.getElementsByTagName("numstvalExists")[0].firstChild.nodeValue;	
        if(verfiyst=="Yes")
        	{
        	alert("You Already Freezed ,So You cant modified");
        	}
        //var len1=baseResponse.getElementsByTagName("asset_code")[0].length;  
        //alert(len);
        /*if (count!= 0) {
    		var item = new Array();
    		for ( var k = 0; k < count; k++) {*/
        if(len==0){
        	var mycurrent_row22=document.createElement("TR");
            mycurrent_row22.id=0;                     
            mycurrent_row22.align="center";
            var cell1 =document.createElement("TD");    
           cell1.setAttribute('colspan','20');
            var code=document.createTextNode("No records");                         
            cell1.appendChild(code);  
            cell1.align="center";
            mycurrent_row22.appendChild(cell1);        						

        	   tbody.appendChild(mycurrent_row22);
        	
        }else{
        	 var lll=1;
        	 var item = new Array();
        	 var codeMinor = new Array();
     		var descMinor = new Array();
               for(var k=0;k<len;k++)
                  {
            		item[0] =baseResponse.getElementsByTagName("date_of_entry")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("asset_code")[k].firstChild.nodeValue;
					item[2] =baseResponse.getElementsByTagName("qty_avl_ason_date")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("location_desc")[k].firstChild.nodeValue;
					item[4] =baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
					item[5]=baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;			
					item[6]=baseResponse.getElementsByTagName("qty_available")[k].firstChild.nodeValue;	
					item[7]=baseResponse.getElementsByTagName("accounting_unit_id")[k].firstChild.nodeValue;			
					item[8]=baseResponse.getElementsByTagName("accounting_unit_office_id")[k].firstChild.nodeValue;	
					item[9]=baseResponse.getElementsByTagName("CORRECTQTY")[k].firstChild.nodeValue;	
					item[10]=baseResponse.getElementsByTagName("CORRECTQTYREMARK")[k].firstChild.nodeValue;	
					//item[11]=baseResponse.getElementsByTagName("numstvalExists")[k].firstChild.nodeValue;	
					//alert(" verfiyst "+verfiyst);
					
					var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=item[0];                     

                  

        			var cell1 = document.createElement("TD");
           			var sno2=document.createElement("input");
           			sno2.type="hidden";
           			sno2.name="serialNo";
           			sno2.id="serialNo";
           			sno2.value=lll;
           			cell1.appendChild(sno2);
           			var sno2 = document.createTextNode(lll);
           			cell1.appendChild(sno2);
           			mycurrent_row.appendChild(cell1);
        			
           			var cell1 = document.createElement("TD");
           			var dateofentry1=document.createElement("input");
           			dateofentry1.type="hidden";
           			dateofentry1.name="dateofentry";
           			dateofentry1.id="dateofentry";
           			dateofentry1.value=item[0];
           			var dateofentry = document.createTextNode(item[0]);
           			dateofentry.size=7;
           			cell1.appendChild(dateofentry);
           			cell1.appendChild(dateofentry1);
           			mycurrent_row.appendChild(cell1);
           			
           			var cell2 = document.createElement("TD");
           			var assetcode1=document.createElement("input");
           			assetcode1.type="hidden";
           			assetcode1.name="assetcode";
           			assetcode1.id="assetcode";
           			assetcode1.value=item[1];
           			var assetcode = document.createTextNode(item[1]);
           			assetcode.size=7;
           			cell2.appendChild(assetcode);
           			cell2.appendChild(assetcode1);
           			cell2.align="right";
           			mycurrent_row.appendChild(cell2);
           			
           			var cell3 = document.createElement("TD");
           			var qtyavlasondate1=document.createElement("input");
           			qtyavlasondate1.type="hidden";
           			qtyavlasondate1.name="qtyavlasondate";
           			qtyavlasondate1.id="qtyavlasondate";
           			qtyavlasondate1.value=item[2];
           			var qtyavlasondate = document.createTextNode(item[2]);
           			qtyavlasondate.size=7;
           			cell3.appendChild(qtyavlasondate);
           			cell3.appendChild(qtyavlasondate1);
           			cell3.align="right";
           			mycurrent_row.appendChild(cell3);
           			
           			var cell4 = document.createElement("TD");
           			var locationdesc1=document.createElement("input");
           			locationdesc1.type="hidden";
           			locationdesc1.name="locationdesc";
           			locationdesc1.id="locationdesc";
           			locationdesc1.value=item[3];         			
           			var locationdesc = document.createTextNode(item[3]);
           			locationdesc.size=7;
           			cell4.appendChild(locationdesc);
           			cell4.appendChild(locationdesc1);
           			cell4.align="left";
           			mycurrent_row.appendChild(cell4);
           			
           			var cell5 = document.createElement("TD");
           			var status1=document.createElement("input");
           			status1.type="hidden";
           			status1.name="status";
           			status1.id="status";
           			status1.value=item[4];
           			var status = document.createTextNode(item[4]);
           			status.size=7;
           			cell5.appendChild(status);
           			cell5.appendChild(status1);
           			mycurrent_row.appendChild(cell5);
           			
           			var cell6 = document.createElement("TD");
           			var remarks1=document.createElement("input");
           			remarks1.type="hidden";
           			remarks1.name="remarks";
           			remarks1.id="remarks";
           			remarks1.value=item[5];
           			var remarks = document.createTextNode(item[5]);
           			remarks.size=7;
           			cell6.align="left";
           			cell6.appendChild(remarks);
           			cell6.appendChild(remarks1);
           			mycurrent_row.appendChild(cell6);
           			
           			var cell7 = document.createElement("TD");
           			var qtyavailable=document.createElement("input");
           			qtyavailable.type="hidden";
           			qtyavailable.name="qtyavailable";
           			qtyavailable.id="qtyavailable";
           			qtyavailable.value=item[6];
           			var qtyavailable1 = document.createTextNode(item[6]);
           			qtyavailable1.size=7;
           			cell7.appendChild(qtyavailable);
           			cell7.appendChild(qtyavailable1);
           			mycurrent_row.appendChild(cell7);
           			
           			
           			
           			var cell70 = document.createElement("TD");
           			var correctqty=document.createElement("input");
           			correctqty.type="text";
           			correctqty.name="correctqty";
           			correctqty.id="correctqty";
			      // if(verfiyst=="Yes"){
				    
			    	  correctqty.disabled=true;
			    	//  document.frmAssetsNumericalEdit.updateTotally.disabled =true;
			          // 			}else{
			           			//	correctqty.disabled=false;
			           			// document.frmAssetsNumericalEdit.updateTotally.disabled =false;
			           		//	}
           			correctqty.value=item[9];
           			correctqty.setAttribute("onkeypress", "return numbersonly1(event,this)");
           		
           			correctqty.size=7;
           			cell70.appendChild(correctqty);
           			mycurrent_row.appendChild(cell70);
           			
           			
           			
           			var cell71 = document.createElement("TD");
           			var correctqtyremark=document.createElement('TEXTAREA','option1');
           			correctqtyremark.setAttribute("cols", "2");
           			//if(verfiyst=="Yes"){
           				correctqtyremark.disabled= true;
           	//			document.frmAssetsNumericalEdit.updateTotally.disabled =true;
           			//}
           			//else{
           			// document.frmAssetsNumericalEdit.updateTotally.disabled =false;
           			//	correctqtyremark.disabled=false;
           			//}
           			correctqtyremark.style.height = "30px";
           			correctqtyremark.style.width = "80px";
           			correctqtyremark.name="correctqtyremark";
           			correctqtyremark.id="correctqtyremark";
           			correctqtyremark.value=item[10];
           			/*var qty_available = document.createTextNode(item[6]);
           			correctqtyremark.size=7;*/
           			cell71.appendChild(correctqtyremark);
           			mycurrent_row.appendChild(cell71);
           			           		            			
           			//hidden
           			
           			var cell8 = document.createElement("TD");
           			cell8.style.display="none";
           			var unitid1=document.createElement("input");
           			unitid1.type="hidden";
           			unitid1.name="unitid";
           			unitid1.id="unitid";
           			unitid1.value=item[7];
           			var unitid2= document.createTextNode(item[7]);
           			unitid2.size=7;
           			cell8.appendChild(unitid1);
           			cell8.appendChild(unitid2);
           			mycurrent_row.appendChild(cell8);
           			
           			var cell9 = document.createElement("TD");
           			cell9.style.display="none";
           			var officeid1=document.createElement("input");
           			officeid1.type="hidden";
           			officeid1.name="officeid";
           			officeid1.id="officeid";
           			officeid1.value=item[8];
           			var officeid2 = document.createTextNode(item[8]);
           			officeid2.size=7;
           			cell9.appendChild(officeid1);
           			cell9.appendChild(officeid2);
           			mycurrent_row.appendChild(cell9);
           			
           			
           			
           			lll++;
                    tbody.appendChild(mycurrent_row);
                    seq+=1;  
                  }
			
		        }
		         }
				  else
				  {
				    alert("Failed to Load Values");
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
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      //t.blur();
      //return true;-------------------- for taking action when press ENTER
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false ;
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