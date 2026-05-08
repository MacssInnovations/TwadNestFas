var seq=0;
var rowid=0;
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

function checkRadio()
{
	var tbody=document.getElementById("grid_body");
    if(tbody.rows.length==0){
         document.Dcb_Journal.butGo1.disabled=true;
        // document.getElementById("butGo1").disabled=true;
    alert("No values Found");
    return false;
    }else{
         document.Dcb_Journal.butGo1.disabled=true;
        return true;
    }
    
    
    if(tbody.rows.length>0)
    {
    	rows=tbody.getElementsByTagName("tr");
    	for(i=0;i<rows.length;i++)
        {
    		var cells=rows[i].cells;
    		if(cells.item(1).lastChild.nodeValue==0)
            {
    			alert("Account headcode should not be less than zero");
    			return false;
            }
    		if(cells.item(2).lastChild.nodeValue==10 && cells.item(3).lastChild.nodeValue==0 )
    		{
    			alert("Subledger code Zero");
    			return false;
    		}
    		//return true;
        }
    }
    
    
	
	/*	alert("checkRadio");
         for(var i=0;i<checkNo.length;i++)
             { 
             alert("length in check:"+checkNo.length);
              if(checkNo[i].checked==true)
             {
                alert("i true:"+i);
              var schemes=schemeTypeId[i];
              alert("schemes"+schemes);
                
             }
             }*/
}





function callGo()
{
	
                var unitid=document.getElementById("cmbAcc_UnitCode").value;
                var officeid=document.getElementById("cmbOffice_code").value;
                var dated1=document.getElementById("txtCrea_date").value;
                var dt=dated1.split("/");
                var cashbkyear=dt[2];
                var cashbkmonth=dt[1];
                var tbody=document.getElementById("grid_body");
	         var t=0;
	         for(t=tbody.rows.length-1;t>=0;t--)
	         {
	                tbody.deleteRow(0);
	         }
	        
	        var url="../../../../../Dcb_Journal_Creation?Command=load&unitid="+unitid+"&officeid="+officeid+"&cashbookyear="+cashbkyear+"&cashbookmonth="+cashbkmonth;
                
	         req=getTransport();
	         req.open("GET",url,true);    
	         document.getElementById("imgfld").style.visibility = "visible";
	         req.onreadystatechange=function()
	         {   
                       callManipulated(req);
	         }   
	         req.send(null);     
	
}

function  callManipulated(req)
 {
	
	 if(req.readyState==4)
	   {
		
	      if(req.status==200)
	          {
                     var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
                     var tagCommand=baseResponse.getElementsByTagName("command")[0];
                     var command=tagCommand.firstChild.nodeValue; 
	               if(command=="loadgrid")
                       { 
                               loadDCBGrid(baseResponse);
                       }
	          }
	    }
 }

function loadDCBGrid(baseResponse)
 {
 	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
        if(flag=="success")
        { 
        	try{
	        	var flagCheck=baseResponse.getElementsByTagName("flagCheck")[0].firstChild.nodeValue; 
	        	var slCheck=baseResponse.getElementsByTagName("slCheck")[0].firstChild.nodeValue; 
	        	if(flagCheck=="enabled")
	        	{
	        		document.getElementById("butGo").style.display="block";
	        	}
	        	else
	        	{
	        		document.getElementById("butGo").style.display="none";
	        	}
	        	if(slCheck=="SlcodeZero")
	        		{
	        		alert("Subledger code is zero ");
//	        		document.getElementById("butGo1").style.display="none";
	        		document.Dcb_Journal.butGo1.disabled=true;
	        		}
	        	else if(slCheck=="Slcodevalue")
	        		{
//	        		document.getElementById("butGo1").style.display="block";
	        		document.Dcb_Journal.butGo1.disabled=false;
	        		}
	        		
	        	
        	}
        	catch(e)
        	{
        		
        	}
        	
    	 var tbody=document.getElementById("grid_body");
           var t=0;
           for(t=tbody.rows.length-1;t>=0;t--)
               {
                  tbody.deleteRow(0);
               } 
                 var len=baseResponse.getElementsByTagName("schId").length;      
             for(var k=0;k<len;k++)
               {
                   rowid++;
                  // alert("rowid::::;"+rowid);
                   var unitid=baseResponse.getElementsByTagName("unitid")[k].firstChild.nodeValue;
                   var officeid=baseResponse.getElementsByTagName("officeid")[k].firstChild.nodeValue;
                   var cashyear=baseResponse.getElementsByTagName("cashyear")[k].firstChild.nodeValue;
                   var cashmonth=baseResponse.getElementsByTagName("cashmonth")[k].firstChild.nodeValue;
                   
                   var schId=baseResponse.getElementsByTagName("schId")[k].firstChild.nodeValue;
                   var schDesc=baseResponse.getElementsByTagName("schDesc")[k].firstChild.nodeValue;
                   var headCodedesc = baseResponse.getElementsByTagName("headCodedesc")[k].firstChild.nodeValue;
                   var headCode = baseResponse.getElementsByTagName("headCode")[k].firstChild.nodeValue;
                   var slType = baseResponse.getElementsByTagName("slTypedesc")[k].firstChild.nodeValue;
                   var slCode1 = baseResponse.getElementsByTagName("slCodedesc")[k].firstChild.nodeValue;
                   var crAmount = baseResponse.getElementsByTagName("crAmount")[k].firstChild.nodeValue;
                   var Remarks = baseResponse.getElementsByTagName("Remarks")[k].firstChild.nodeValue;
                 
                   
                   
                
                var slCode;
                if(slCode1=="null")
                {
                    slCode="-";
                }
                else
                slCode=slCode1;
                var items=new Array();
                items[0]=schId;
                items[1]=schDesc;
                
               var i=0;
               var cell2;
               var mycurrent_row=document.createElement("TR");
               mycurrent_row.id=seq;
                
         //  out.println("<td><input type='radio' id='radchoice' name='radchoice' checked value='"+rowid+"'/></td>");
         
               cell2=document.createElement("TD");
               var check="";
               if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
               {
                      check=document.createElement("<INPUT type='radio' name='checkNo' id='checkNo' value='"+rowid+"' size='10'>");
        //        out.println("<td><input type='radio' id='radchoice' name='radchoice' checked value='"+rowid+"'/></td>");
               }
               else
               {  
                      check=document.createElement("input");
                      check.type="hidden";
                      check.name="checkNo";
                      check.id="checkNo";
                      check.value=items[0];
               }
              // alert("rowid>>>>>>>>>."+rowid);
               //alert("schid"+items[0]);
               
               cell2.appendChild(check);
             //  mycurrent_row.appendChild(cell2);
               
               var cell2=document.createElement("TD");
               cell2.setAttribute('align','left');
               var currentText1=document.createElement("input");
               currentText1.type="hidden";
               currentText1.name="hiddenName";
               currentText1.value=rowid;
               cell2.appendChild(currentText1);
             //  mycurrent_row.appendChild(cell2); 
         
              cell2=document.createElement("TD");
              cell2.setAttribute('align','left');
              var sch_id=document.createElement("input");
              sch_id.type="hidden";
              sch_id.name="schemeTypeId";
              sch_id.value=items[0];
              cell2.appendChild(sch_id);
              var currentText=document.createTextNode(items[1]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);

           cell2=document.createElement("TD");   
           cell2.setAttribute('align','left');
           var tnodeheadCode=document.createTextNode(headCode+"-"+headCodedesc);                         
           cell2.appendChild(tnodeheadCode);       
           mycurrent_row.appendChild(cell2); 
           
           cell2=document.createElement("TD");   
           cell2.setAttribute('align','left');
           var tnodeslType=document.createTextNode(slType);                         
           cell2.appendChild(tnodeslType);       
           mycurrent_row.appendChild(cell2); 
           
           cell2=document.createElement("TD");   
           cell2.setAttribute('align','center');
           var tnodeslCode=document.createTextNode(slCode);                         
           cell2.appendChild(tnodeslCode);       
           mycurrent_row.appendChild(cell2);
           
           cell2=document.createElement("TD");   
           cell2.setAttribute('align','center');
           var tnodecrAmount=document.createTextNode(crAmount);                         
           cell2.appendChild(tnodecrAmount);       
           mycurrent_row.appendChild(cell2);
           
           cell2=document.createElement("TD");   
           cell2.setAttribute('align','left');
           var tnodeRemarks=document.createTextNode(Remarks);                         
           cell2.appendChild(tnodeRemarks);       
           mycurrent_row.appendChild(cell2);
           
           cell2=document.createElement("TD");
           cell2.align='left';
           var anc=document.createElement("A");
           var url="javascript:Show('"+unitid+"','"+officeid+"','"+cashyear+"','"+cashmonth+"','"+schId+"')";
           anc.href=url;
           var txtedit=document.createTextNode("DETAILS");
           anc.appendChild(txtedit);
           cell2.appendChild(anc);
           mycurrent_row.appendChild(cell2);
          
           tbody.appendChild(mycurrent_row);
           seq++;
         }
             document.getElementById("imgfld").style.visibility = "hidden";
        }
     else {
    	 document.getElementById("imgfld").style.visibility = "hidden";
    		alert("No Data Found");
     }
     
 }

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)    
{
         if(blr_flag==1)         
         {                              
                 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                 var TB_date=fromcal_dateCtrl.value;                 
                 if(fromcal_dateCtrl.value.length!=0)
                 {
                         var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;                         
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
                         dateCheck(dateCtrl);  
                }
         }
}



function dateCheck(datechk)
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
            //alert("Flag----->"+flag);
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
            	
            	document.getElementById("txtReceipt_No").value="";
                 
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
          		document.getElementById("txtReceipt_No").value="";
           }
           else 
        	   {
        	    datechk.value=""; 
        	    alert("Date Value is Null");
           		datechk.focus();
           		document.getElementById("butSub").disabled=true;
           		document.getElementById("txtReceipt_No").value="";
        	   }
        }
    }
}



 function Show(unitcode,offid,yr,mon,schId)
{
 		 var DCB_list= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/Dcb_Journal_Creation_list.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&schemeId="+schId,"DCBList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
 	 	 DCB_list.moveTo(250,250);  
 	 	 DCB_list.focus();     
}
 
function call_date(dateCtrl)                        // TB_checking 
{         
	  if(checkdt(dateCtrl))
          {
                  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                 // alert("cmbAcc_UnitCode===>"+cmbAcc_UnitCode);
                  var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                 // alert("cmbOffice_code===>"+cmbOffice_code);
                  var TB_date=dateCtrl.value;
                  if(dateCtrl.value.length!=0)
                  {
                          var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                         // alert("URL===>"+url);
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
                          var cbyear = document.Dcb_Journal.cashbookyear;
                          var cbmonth = document.Dcb_Journal.cashbookmonth;
                          if(flag=="success")
                        	  { callYear();}
                          if(flag=="failure")
                          {
                          
                                   dateCtrl.value="";
                                   cbyear.value="";
                                   cbmonth.value="";
                                   alert("Trial Balance Closed");//return false;//
                                   dateCtrl.focus();                                            
                          }
                          else if(flag=="finyear")
                          {
                          
                                   // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                   dateCtrl.value="";
                                   cbyear.value="";
                                   cbmonth.value="";
                                   alert("Cash Book Control Not Found ");//return false;//
                                   dateCtrl.focus();
                               
                                   //document.getElementById("txtVoucher_No").value="";     
                          }
                          else if(flag=="finyearLJVN")
                          {
                                     // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                               dateCtrl.value="";
                               cbyear.value="";
                               cbmonth.value="";
                               alert("Cash Book Control Not Found for Journal");//return false;//
                               dateCtrl.focus();
                              // document.getElementById("txtReceipt_No").value="";     
                          }
                          dateCheck(dateCtrl);  
                     }
          }
 }

function callYear()
{
//             alert("Calling callYear Function");	
            document.Dcb_Journal.cashbookyear.value="";
            document.Dcb_Journal.cashbookmonth.value="";
            var dated= document.Dcb_Journal.txtCrea_date.value;
            var dt=dated.split("/");
            document.Dcb_Journal.cashbookyear.value=dt[2];
            var month;
            if(dt[1]==01){month="Jan";}
             else if(dt[1]==02){month="Feb";}
             else if(dt[1]==03){month="Mar";}
             else if(dt[1]==04){month="Apr";}
             else if(dt[1]==05){month="May";}
             else if(dt[1]==06){month="Jun";}
             else if(dt[1]==07){month="Jul";}
             else if(dt[1]==08){month="Aug";}
             else if(dt[1]==09){month="Sep";}
             else if(dt[1]==10){month="Oct";}
             else if(dt[1]==11){month="Nov";}
             else if(dt[1]==12){month="Dec";}
            document.Dcb_Journal.cashbookmonth.value=month;
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)    
{
         if(blr_flag==1)         
         {                              
                 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                 var TB_date=fromcal_dateCtrl.value;                 
                 if(fromcal_dateCtrl.value.length!=0)
                 {
                         var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;                         
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