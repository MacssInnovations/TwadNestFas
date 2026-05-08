var j=0;
var seq=0;

var items1;
var items2;
var items3;
var items4;
var items5;
var items6;
var items7;
var items9;
var items10;

function checknull()
{
	var tbody=document.getElementById("tbody");
	if(tbody.rows.length==0)
	{
	    alert("Enter the Details Part");
	    // document.getElementById("txtAmount").focus();
	    return false; 
	}
}

function getxmlhttpObject()
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











function call(command)
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  

 if(command=="loadvoucher")
{ 
	
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cashyear=document.getElementById("txtCB_Year").value;
	var cashmonth=document.getElementById("txtCB_Month").value;	
	
	  var url="../../../../../Acceptance_Verification?command=loadvoucher&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cashyear="+cashyear+"&cashmonth="+cashmonth+"";
	 
	  url=url+"&sid="+Math.random();
      xmlhttp.open("GET",url,true);
      xmlhttp.onreadystatechange=stateChanged;
      xmlhttp.send(null);  
    
	
} 
 
 
}





function stateChanged()
{
    var flag,command,response;
   
    if(xmlhttp.readyState==4)
    {
    	
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="loadvoucher")
            {
            	
            	
                if(flag=='success')
                {
                    var i=0;
                    
                    s=0;
                    seq=0;
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
                   
                    var leng=response.getElementsByTagName("vno").length;
                    
                    var j=0;
                    /** Get Tbody Object */
                    var tbody=document.getElementById("tbody");
                    
                    
                    var t=0;
        	        for(t=tbody.rows.length-1;t>=0;t--)
        	        {
        	           tbody.deleteRow(0);
        	        }
                    for(i=0;i<leng;i++)
                    {
                    	
                        var items=new Array();
                        items1[i]=response.getElementsByTagName("vno")[i].firstChild.nodeValue;
                        items2[i]=response.getElementsByTagName("vdate")[i].firstChild.nodeValue;
                        items3[i]=response.getElementsByTagName("unitid")[i].firstChild.nodeValue;                
                        items4[i]=response.getElementsByTagName("acceptdate")[i].firstChild.nodeValue;                
                        items5[i]=response.getElementsByTagName("amount")[i].firstChild.nodeValue;                                       
                       if(response.getElementsByTagName("particular")[i].firstChild == null){
						  items6[i]="" 
					   }else{
						    items6[i]=response.getElementsByTagName("particular")[i].firstChild.nodeValue;
					   }
                       
                       
                        items7[i]=response.getElementsByTagName("unitname")[i].firstChild.nodeValue;                
                        items8[i]=response.getElementsByTagName("adviceno")[i].firstChild.nodeValue;
                        items9[i]=response.getElementsByTagName("slno")[i].firstChild.nodeValue;
                                   
                        var mycurrent_row=document.createElement("TR");
                        j++;
                     
                        
                        
                        //-------------------------- Cell2 ------------------------------------//
                        
                        /* Check Box */
                        var checkparam = seq ;
                        
                        var descell=document.createElement("TD");
                        descell.style.textAlign='center'; 
                        var chcksel="";
                        if(window.navigator.appName.toLowerCase().indexOf("netscape")==-1)
                        {
                            chcksel=document.createElement("<input type='checkbox' name='chckparameter' id='chckparameter' value='"+checkparam+"' />");
                        }
                        else
                        {
                               var chcksel=document.createElement("input");
                               chcksel.type="checkbox";
                               chcksel.name="chckparameter";
                               chcksel.id="chckparameter";                      
                               chcksel.value= seq;
                        }
                        descell.appendChild(chcksel);
                        mycurrent_row.appendChild(descell);
                        
                                            
                        //-------------------------- Cell2.5 ------------------------------------//
                        
                        /** Voucher Number */
                        
                        
                       var cell12=document.createElement("TD");
                        
                        // Hidden Voucher Number 
                        var adviceno=document.createElement("input");
                        adviceno.type="hidden";
                        adviceno.name="adviceno";
                        adviceno.id="adviceno";
                        adviceno.value= items8[i];
                        
                        cell12.appendChild(adviceno);  
                        
                        // Displaying Voucher Number 
                        var currentText1=document.createTextNode( items8[i]);                
                        var fnt=document.createElement("font");
                        fnt.size="2";
                        fnt.appendChild(currentText1);                
                        cell12.appendChild(fnt);        
                        
                        mycurrent_row.appendChild(cell12);
                        
                        
                        
                        var cell2_5=document.createElement("TD");
                        
                        // Hidden Voucher Number 
                        var Vou_No=document.createElement("input");
                        Vou_No.type="hidden";
                        Vou_No.name="Vou_No";
                        Vou_No.id="Vou_No";
                        Vou_No.value=items1[i]+"-"+items9[i];
                        Vou_No.size="5";
                        cell2_5.appendChild(Vou_No);  
                        
                        // Displaying Voucher Number 
                        var currentText1=document.createTextNode(items1[i]);                
                        var fnt=document.createElement("font");
                        fnt.size="2";
                        fnt.appendChild(currentText1);                
                        cell2_5.appendChild(fnt);        
                        
                        mycurrent_row.appendChild(cell2_5);
                        
                                            
                        //-------------------------- Cell3 ------------------------------------//
                        
                        /**date */
                        var cell3=document.createElement("TD");
                        var Vou_Date=document.createElement("input");
                        Vou_Date.type="hidden";
                        Vou_Date.name="Vou_Date";
                        Vou_Date.id="Vou_Date";
                        Vou_Date.value=items2[i];
                       
                        cell3.appendChild(Vou_No);  
                        var currentText1=document.createTextNode(items2[i]);                
                        var fnt=document.createElement("font");
                        fnt.size="2";
                        fnt.appendChild(currentText1);                
                        cell3.appendChild(fnt);          
                                               
                        mycurrent_row.appendChild(cell3);
                        
                        var cell4=document.createElement("TD");
                        var unitid=document.createElement("input");
                        unitid.type="hidden";
                        unitid.name="unitid";
                        unitid.id="unitid";
                        unitid.value=items3[i];
                       
                        cell4.appendChild(unitid);  
                        var currentText1=document.createTextNode( items7[i]);                
                        var fnt=document.createElement("font");
                        fnt.size="2";
                        fnt.appendChild(currentText1);                
                        cell4.appendChild(fnt);          
                                               
                        mycurrent_row.appendChild(cell4);
                
                        
                        var cell5=document.createElement("TD");
                        cell5.align="right";
                        var amount=document.createElement("input");
                        amount.type="hidden";
                        amount.name="amount";
                        amount.id="amount";
                        amount.value=items5[i];
                       
                        cell5.appendChild(amount);  
                        var currentText1=document.createTextNode(items5[i]);                
                        var fnt=document.createElement("font");
                        fnt.size="2";
                        fnt.appendChild(currentText1);                
                        cell5.appendChild(fnt);          
                                               
                        mycurrent_row.appendChild(cell5);
                        
                        
                        var cell6=document.createElement("TD");
                        var acc_date=document.createElement("input");
                        acc_date.type="hidden";
                        acc_date.name="acc_date";
                        acc_date.id="acc_date";
                        acc_date.value= items4[i];
                       
                        cell6.appendChild(acc_date);  
                        var currentText1=document.createTextNode(items4[i]);                
                        var fnt=document.createElement("font");
                        fnt.size="2";
                        fnt.appendChild(currentText1);                
                        cell6.appendChild(fnt);          
                                               
                        mycurrent_row.appendChild(cell6);
                        
                        var cell7=document.createElement("TD");
                        var particular=document.createElement("input");
                        particular.type="hidden";
                        particular.name="particular";
                        particular.id="particular";
                        particular.value=items6[i];
                       
                        cell7.appendChild(particular);  
                        var currentText1=document.createTextNode(items6[i]);                
                        var fnt=document.createElement("font");
                        fnt.size="2";
                        fnt.appendChild(currentText1);                
                        cell7.appendChild(fnt);          
                                               
                        mycurrent_row.appendChild(cell7);
                        
                        
                      
                        tbody.appendChild(mycurrent_row);
                        seq++;
                        
                    }             
                 }
               
                else
                    {
                	alert('Voucher Not Found');
                	 var tbody=document.getElementById("tbody");
                	  var t=0;
          	        for(t=tbody.rows.length-1;t>=0;t--)
          	        {
          	           tbody.deleteRow(0);
          	        }
                                     
                    }
                 }
                 
       }
    }
}







function checknull()
{

	if(document.getElementById("voucherno").value=="")
	{
		alert('Please Select Voucher No');
		return false;
	}
	if(document.getElementById("forunitid").value=="")
	{
		alert('Please Select For UnitId');
		return false;
	}

	if( document.Fix_Reject_Adjustment_Memo.extend_close[0].checked==true)
	{
		if(document.getElementById("txtCrea_date").value=="")
		alert('Please Enter Date Please');
		return false;
	}
	return true;

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




