/////////////////////////////////////////////   doFunction()  /////////////////////////////////////////////////////
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
 


function doFun()
{  
	//alert("hsdjksh");

   try
   {

 
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    //alert(cmbAcc_UnitCode);
   
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
   
    var txtCB_Year=document.getElementById("txtCB_Year").value;

    var txtCB_Month=document.getElementById("txtCB_Month").value;
    var note_no=document.getElementById("cmbBankAccNo").value;
       
  
    

      var url="../../../../../HR_Note_Cancel1?command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&note_no="+note_no;                
   
//alert(url);
        
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        	 LoadBankAccountNumberRes(req);
        }   
                req.send(null);
    }       
 
  
 catch(Exception)
 {
	 System.out.print(e);
 }
	 }



function LoadBankAccountNumber_opr1()
{  	
	
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	   var url="../../../../../HR_Note_Cancel1?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	   if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
	// alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
		   LoadBankAccountNumberRes(req);
	   }   
       req.send(null);
	 
}

function btncancel1()
{

 self.close();
} 

  


function cancel_live()
{
	 var tbody=document.getElementById("tbody");
		if(tbody.rows.length==0){
		alert("No hr Found");
		return false;
		}
}



   

/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function  LoadBankAccountNumberRes(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
    // alert("req.status"+req.responseText);   
         var baseResponse=req.responseXML.getElementsByTagName("response")[0];
    //alert("hhhhhhhhhhh"+baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
       // alert("tagcommand"+tagcommand);
            var Command=tagcommand.firstChild.nodeValue;
        //alert("Command"+Command);
           var tbody=document.getElementById("tbody");
          
// alert("bodylength"+ tbody.rows.length);
           
           for(t=tbody.rows.length-1;t>=0;t--)
               {
                  tbody.deleteRow(0);
               } 
            //alert("length"+len);
            if(Command=="searchByMonth")
            {
            	//alert("yyyyyyyy");
               var len=baseResponse.getElementsByTagName("NOTE_DATE").length;
                	
           //alert("notedatelength"+len);
               
                if(len>0)
                {
                    for(var i=0;i<len;i++)
                    {
                    	//alert("hhhhhhhhhhhhhhhhhhh");
                        var NOTE_DATE=baseResponse.getElementsByTagName("NOTE_DATE")[i].firstChild.nodeValue;
           //         alert(NOTE_DATE);
                  
                       var tr = document.createElement("tr");
                        var td1=document.createElement("td");
                        var NOTE_DATE=baseResponse.getElementsByTagName("NOTE_DATE")[i].firstChild.nodeValue;
                        var NOTE_DATE1=document.createTextNode(NOTE_DATE);
                        td1.appendChild(NOTE_DATE1);
                        td1.style.fontSize="14px";
                       
                       
                        var td2=document.createElement("td");
                        var bill_major_type_desc=baseResponse.getElementsByTagName("bill_major_type_desc")[i].firstChild.nodeValue;
                        var bill_major_type_desc1=document.createTextNode(bill_major_type_desc);
                        td2.appendChild(bill_major_type_desc1);
                        td2.style.fontSize="14px";
                        
                    //alert(bill_major_type_desc);
                        var td3=document.createElement("td");
                        var bill_minor_type_desc=baseResponse.getElementsByTagName("bill_minor_type_desc")[i].firstChild.nodeValue;
                        
                        var bill_minor_type_desc1=document.createTextNode(bill_minor_type_desc);
                        td3.appendChild(bill_minor_type_desc1);
                        td3.style.fontSize="14px";
                        
                   // alert(bill_minor_type_desc);
                       
                        var td4=document.createElement("td");
                        var bill_sub_type_desc=baseResponse.getElementsByTagName("bill_sub_type_desc")[i].firstChild.nodeValue;
                        
                        var bill_sub_type_desc1=document.createTextNode(bill_sub_type_desc);
                        td4.appendChild(bill_sub_type_desc1);
                        td4.style.fontSize="14px";
                      
                      // alert(bill_sub_type_desc);
                        var td5=document.createElement("td");
                        var NOTE_AMOUNT=baseResponse.getElementsByTagName("NOTE_AMOUNT")[i].firstChild.nodeValue;
                        var NOTE_AMOUNT1=document.createTextNode(NOTE_AMOUNT);
                        td5.appendChild(NOTE_AMOUNT1);
                        td5.style.fontSize="14px";
                    // alert(NOTE_AMOUNT);
                       
                        var td6=document.createElement("td");
                        var NOTE_PREPARED_BY=baseResponse.getElementsByTagName("NOTE_PREPARED_BY")[i].firstChild.nodeValue;
                        var NOTE_PREPARED_BY1=document.createTextNode(NOTE_PREPARED_BY);
                        td6.appendChild(NOTE_PREPARED_BY1);
                        td6.style.fontSize="14px";
                    // alert(NOTE_PREPARED_BY);
                       
                        var td7=document.createElement("td");
                        var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
                        var ACCOUNT_HEAD_CODE1=document.createTextNode(ACCOUNT_HEAD_CODE);
                        td7.appendChild(ACCOUNT_HEAD_CODE1);
                        td7.style.fontSize="14px";
                       
                      
                        tr.appendChild(td1);
                        tr.appendChild(td2);
                        tr.appendChild(td3);
                        tr.appendChild(td4);
                        tr.appendChild(td5);
                        tr.appendChild(td6);
                        tr.appendChild(td7);
                       //tr.appendChild(td8);
                        tbody.appendChild(tr);

  
  
  
        }
    }

}
            
            if(Command=="LoadBankAccountNumber")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 //alert(flag);
            	 if(flag=="success")
            	    {
            	          
            	           
            	         
            	       /*    var acc_no=baseResponse.getElementsByTagName("Hr_Note_No");
            	          alert("hrno"+acc_no);*/
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	          
            	           var option=document.createElement("OPTION");
           	            option.text="--Select--";
           	            option.value="";
           	            cmbBankAccNo.appendChild(option);
            	    //alert(cmbBankAccNo);
            	            /*for(var k=0;k<acc_no.length;k++)
            	            {
            	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            	branch_name[k]=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
            	            	bank_id[k] =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	branch_id[k] =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
            	            	opr_mode[k] =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
            	            }*/
           	         var bank_ac_no=baseResponse.getElementsByTagName("Hr_Note_No");
       // alert(bank_ac_no.length);
            	            cmbBankAccNo.innerHTML="";
            	            
            	            for(var k=0;k<bank_ac_no.length;k++)
            	            {  
            	            	//alert("jskhfnskfnj");
            	             var cmbBankAccNo = document.getElementById("cmbBankAccNo");
               	          
            	            var	bank_ac_no1=baseResponse.getElementsByTagName("Hr_Note_No")[k].firstChild.nodeValue;
            	            	//alert("bank_ac_no1"+bank_ac_no1);
            	            	var option=document.createElement("OPTION");
            	            option.text=bank_ac_no1;
            	            option.value=bank_ac_no1;
            	            cmbBankAccNo.appendChild(option);
            	            }
            	            
            	           /* for(var k=0;k<acc_no.length;k++)
            	            {   
            	                  var option=document.createElement("OPTION");
            	                  option.text=acc_desc[k];
            	                  option.value=bank_ac_no[k];
            	                  try
            	                  {
            	                	  cmbBankAccNo.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  cmbBankAccNo.add(option,null);
            	                  }
            	            }*/
            	    
            	    }
              }
        }
    }
}


        	
        	
         
  

 
