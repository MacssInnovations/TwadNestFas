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
 
function doFunction1(Command,param)
{  
	

   try
   {

 
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
   
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
   
    var txtCB_Year=document.getElementById("txtCB_Year").value;

    var txtCB_Month=document.getElementById("txtCB_Month").value;
       
    if(Command=="searchByMonth"){
    

      var url="../../../../../HR_Note_Report1?Command=searchByMonth&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;                
   

        //alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           handleResponse(req);
        } ;  
                req.send(null);
    }       
   }
  
 catch(Exception)
 {
	 System.out.print(e);
 }
	 }

function btncancel1()
{

 self.close();
} 
    
     




   

/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{  
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
        	//alert("req.status"+req.responseText);   
         var baseResponse=req.responseXML.getElementsByTagName("response")[0];
     	//alert("hhhhhhhhhhh"+baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
           // alert("tagcommand"+tagcommand);
            var Command=tagcommand.firstChild.nodeValue;
           // alert("Command"+Command);
           var tbody=document.getElementById("tbody");
           var t=0;
           for(t=tbody.rows.length-1;t>=0;t--)
               {
                  tbody.deleteRow(0);
               } 
            //alert("length"+len);
          if(Command=="searchByMonth")
            {
               var len=baseResponse.getElementsByTagName("HR_NOTE_NO").length;
                	
            //    alert("length"+len);
               
                if(len>0)
                {
                    for(var i=0;i<len;i++)
                    {
                        var HR_NOTE_NO=baseResponse.getElementsByTagName("HR_NOTE_NO")[i].firstChild.nodeValue;
                       
                        var tr = document.createElement("tr");
                        var td1=document.createElement("td");
                        var HR_NOTE_NO1=document.createTextNode(HR_NOTE_NO);
                        td1.appendChild(HR_NOTE_NO1);
                        td1.style.fontSize="14px";
                       
                        var td2=document.createElement("td");
                        var NOTE_DATE=baseResponse.getElementsByTagName("NOTE_DATE")[i].firstChild.nodeValue;
                        var NOTE_DATE1=document.createTextNode(NOTE_DATE);
                        td2.appendChild(NOTE_DATE1);
                        td2.style.fontSize="14px";
                       
                       
                        var td3=document.createElement("td");
                        var bill_major_type_desc=baseResponse.getElementsByTagName("bill_major_type_desc")[i].firstChild.nodeValue;
                        var bill_major_type_desc1=document.createTextNode(bill_major_type_desc);
                        td3.appendChild(bill_major_type_desc1);
                        td3.style.fontSize="14px";
                        
                       
                        var td4=document.createElement("td");
                        var bill_minor_type_desc=baseResponse.getElementsByTagName("bill_minor_type_desc")[i].firstChild.nodeValue;
                        //alert(service);
                        var bill_minor_type_desc1=document.createTextNode(bill_minor_type_desc);
                        td4.appendChild(bill_minor_type_desc1);
                        td4.style.fontSize="14px";
                       
                       
                        var td5=document.createElement("td");
                        var bill_sub_type_desc=baseResponse.getElementsByTagName("bill_sub_type_desc")[i].firstChild.nodeValue;
                       // alert(bill_sub_type_desc);
                        if(bill_sub_type_desc=='null')bill_sub_type_desc="";
                        var bill_sub_type_desc1=document.createTextNode(bill_sub_type_desc);
                        td5.appendChild(bill_sub_type_desc1);
                        td5.style.fontSize="14px";
                      
                       
                        var td6=document.createElement("td");
                        var NOTE_AMOUNT=baseResponse.getElementsByTagName("NOTE_AMOUNT")[i].firstChild.nodeValue;
                        var NOTE_AMOUNT1=document.createTextNode(NOTE_AMOUNT);
                        td6.appendChild(NOTE_AMOUNT1);
                        td6.style.fontSize="14px";
                       
                        var td7=document.createElement("td");
                        var NOTE_PREPARED_BY=baseResponse.getElementsByTagName("NOTE_PREPARED_BY")[i].firstChild.nodeValue;
                        var NOTE_PREPARED_BY1=document.createTextNode(NOTE_PREPARED_BY);
                        td7.appendChild(NOTE_PREPARED_BY1);
                        td7.style.fontSize="14px";
                       
                       
                        var td8=document.createElement("td");
                        var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
                        var ACCOUNT_HEAD_CODE1=document.createTextNode(ACCOUNT_HEAD_CODE);
                        td8.appendChild(ACCOUNT_HEAD_CODE1);
                        td8.style.fontSize="14px";
                       

                        tr.appendChild(td1);
                        tr.appendChild(td2);
                        tr.appendChild(td3);
                        tr.appendChild(td4);
                        tr.appendChild(td5);
                        tr.appendChild(td6);
                        tr.appendChild(td7);
                        tr.appendChild(td8);
                       
                        tbody.appendChild(tr);

        }
    }
    }
        }
    }
}
/*   
function loadtxtCB_Month1(baseResponse){*/
	
    /*var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var tbody=document.getElementById("tbody");
    alert(tbody);
	 //tbody.rows.length=0;
	 
	 var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        } 
    if(flag=="failure")
    {
    	 
                alert("No Record exists");
      
    }
    else
    { 
    
    	
    	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        var txtCB_Month=document.getElementById("txtCB_Month").value;
    	
    	 var tbody=document.getElementById("tbody");
            //service=baseResponse.getElementsByTagName("leng");
           '' alert("hjjjk"+service);
            if(tbody)
             {
            	 for(i=0;i<service.length;i++)
                 {
                     
            		 
	                   var items=new Array();
                         
                         items[0]=service[i].getElementsByTagName("HR_NOTE_NO")[0].firstChild.nodeValue;
                         items[1]=service[i].getElementsByTagName("NOTE_DATE")[0].firstChild.nodeValue;
                         items[2]=service[i].getElementsByTagName("bill_major_type_desc")[0].firstChild.nodeValue;
                         items[3]=service[i].getElementsByTagName("bill_minor_type_desc")[0].firstChild.nodeValue;
                         items[4]=service[i].getElementsByTagName("bill_sub_type_desc")[0].firstChild.nodeValue;
                         items[5]=service[i].getElementsByTagName("NOTE_AMOUNT")[0].firstChild.nodeValue;
                         items[6]=service[i].getElementsByTagName("NOTE_PREPARED_BY")[0].firstChild.nodeValue;
                         items[7]=service[i].getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
                      
                         
                        // var tbody=document.getElementById("tbody");
                         var mycurrent_row=document.createElement("TR");
                       //  items[4]="Employees";
                        for(j=0;j<7;j++)
                         {
                             cell2=document.createElement("TD");
                             
                             if((j==4)||(j==7)){
                            	 cell2.setAttribute('align','right');
                             }else{
                            	 cell2.setAttribute('align','left');
                             }
                            
                             if(items[j]!="null")
                             {
                                 var currentText=document.createTextNode(items[j]);
                             }
                             else
                             {
                                 var currentText=document.createTextNode('');
                             }
                             cell2.appendChild(currentText);
                             mycurrent_row.appendChild(cell2);
                         }
                        
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="";
                        //var url="javascript:Show_new('"+cmbAcc_UnitCode+"','"+cmbOffice_code+"','"+txtCB_Year+"','"+txtCB_Month+"','"+items[0]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("DETAILS");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        
                        mycurrent_row.appendChild(cell);
                            tbody.appendChild(mycurrent_row);
                        
                 }
             }
           
           
    }
}
	
*/
