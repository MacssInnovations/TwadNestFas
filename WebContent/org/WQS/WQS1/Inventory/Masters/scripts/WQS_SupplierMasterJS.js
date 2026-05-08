var seq=0;
var lst=0;
function getTransport()
{
var req=false;
try
{
req=new ActiveXObject("Msxml2.XMLHTTP");
}catch(e1)
 {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if(!req && typeof XMLHttpRequest!='undefined')
        {
        req=new XMLHttpRequest();
        }
   return req;
}   

function clearAll()
 {
   var did=document.getElementById("divwork");
   did.style.display="none";
   document.SupplierForm.txtSupId.value="";
   document.SupplierForm.txtSupName.value="";
   document.SupplierForm.txtAddr1.value="";
   document.SupplierForm.txtAddr2.value="";
   document.SupplierForm.txtAddr3.value="";
   document.SupplierForm.txtPin.value="";
   document.SupplierForm.txtdname.value="";
   document.SupplierForm.txtdname.selectedIndex=0;
   document.SupplierForm.txtPhone1.value="";
   document.SupplierForm.txtPhone2.value="";
   document.SupplierForm.txtfax.value="";
   document.SupplierForm.txtMail.value="";
   document.SupplierForm.txtref.value="";
   document.SupplierForm.txtstatus.value="";
   document.SupplierForm.txtremarks.value=""; 
   document.SupplierForm.CmdAdd.disabled=false;
   document.SupplierForm.CmdUpdate.disabled=true;
   document.SupplierForm.CmdDelete.disabled=true;
   document.SupplierForm.txtSupId.disabled=false; 
 }    


function nullCheck()
{
                 
                  if((document.SupplierForm.txtSupName.value=="") || (document.SupplierForm.txtSupName.value.length<=0))
                  {
                       alert("Dont leave Supplier Name Field Empty");
                       document.SupplierForm.txtSupName.focus();
                       return false;
                  }
                  if((document.SupplierForm.txtAddr1.value=="") || (document.SupplierForm.txtAddr2.value.length<=0))
                  {
                       alert("Dont leave Addrese Field Empty");
                       //document.SupplierForm.txtCon.focus();
                       return false;
                  }
                  if((document.SupplierForm.txtdname.value=="") || (document.SupplierForm.txtdname.value.length<=0))
                  {
                       alert("Dont leave District Name Field Empty");
                       document.SupplierForm.txtdname.focus();
                       return false;
                  }
                  if((document.SupplierForm.txtPhone1.value=="") && (document.SupplierForm.txtPhone2.value.length<=0))
                  {
                       alert("Enter Atleast one Phone No.");
                       //document.SupplierForm.txtPro.focus();
                       return false;
                  }
                  if((document.SupplierForm.txtPin.value=="") ||(document.SupplierForm.txtPin.value.length<=0))
                  {
                    alert("Dont leave fields empty");
                    //document.SupplierForm.txtMainId.focus();
                    return false;
                  }
                  return true;
}
  
 function callServer(command,param)
 {
       var lab=document.SupplierForm.lab.value;
       lab=lab.split("--");
       var txtsupid=document.SupplierForm.txtSupId.value;
       var txtsupname=document.SupplierForm.txtSupName.value;
       var txtaddr1=document.SupplierForm.txtAddr1.value;
       var txtaddr2=document.SupplierForm.txtAddr2.value;
       var txtaddr3=document.SupplierForm.txtAddr3.value;
       if(txtaddr3=="")
            txtaddr3="-";
       var txtpin=document.SupplierForm.txtPin.value;
      // var txtdname=document.SupplierForm.txtdname.value;
       var txtphone1=document.SupplierForm.txtPhone1.value;
       if(txtphone1=="")
            txtphone1="-";
       var txtphone2=document.SupplierForm.txtPhone2.value;
       if(txtphone2=="")
            txtphone2="-";
       var txtfax=document.SupplierForm.txtfax.value;
       if(txtfax=="")
            txtfax="-";
       var txtmail=document.SupplierForm.txtMail.value;
       if(txtmail=="")
            txtmail="-";
       var txtref=document.SupplierForm.txtref.value;
       if(txtref=="")
            txtref="-";
       var txtstatus=document.SupplierForm.txtstatus.value;
       var txtremarks=document.SupplierForm.txtremarks.value;
       if(txtremarks=="")
            txtremarks="-";
       var url="";
       if(command=="Add")
       {           
                var flag=nullCheck();
                if(flag==true)
                { 
                    var txtdname=document.SupplierForm.txtdname.options[document.SupplierForm.txtdname.selectedIndex].value;
                    txtdname=txtdname.toUpperCase();
                   // if(txtmail=="")
                     //   txtmail=null;
                    url="../../../../../../WQS_SupplierMasterServ?command=Add&lab="+lab[0]+"&SupplierName="+txtsupname+"&Address1="+txtaddr1+"&Address2="+txtaddr2+"&Address3="+txtaddr3+"&Pin="+txtpin +"&dname="+txtdname+"&Phone1="+txtphone1 +"&Phone2="+txtphone2 +"&Fax="+ txtfax+"&Mail="+txtmail+"&ref="+txtref+"&status="+txtstatus+"&remarks="+txtremarks;
                    var req=getTransport();
                    req.open("GET",url,true);       
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }  
                    req.send(null);   
                }
        }
        else if(command=="Update")
        {
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    var txtdname=document.SupplierForm.txtdname.value;
                    txtdname=txtdname.toUpperCase();
                    url="../../../../../../WQS_SupplierMasterServ?command=Update&lab="+lab[0]+"&SupplierId="+txtsupid+"&SupplierName="+txtsupname+"&Address1="+txtaddr1+"&Address2="+txtaddr2+"&Address3="+txtaddr3+"&Pin="+txtpin +"&dname="+txtdname+"&Phone1="+txtphone1 +"&Phone2="+txtphone2 +"&Fax="+ txtfax+"&Mail="+txtmail+"&ref="+txtref+"&status="+txtstatus+"&remarks="+txtremarks;
                    var req=getTransport();
                    req.open("GET",url,true);       
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }  
                    req.send(null);
                    }
        }
       
         else if(command=="Delete")
        { 
                   
                    url="../../../../../../WQS_SupplierMasterServ?command=Delete&lab="+lab[0]+"&SupplierId="+txtsupid;
                    var req=getTransport();
                    req.open("GET",url,true);       
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }  
                    req.send(null);
        }
        else if(command=="Get")
        {              
            clearAll();
            url="../../../../../../WQS_SupplierMasterServ?command=Get&lab="+lab[0];
            var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
               processResponse(req);
            }  
            req.send(null);
        }
        
} 

function processResponse(req)
{  
      if(req.readyState==4)
        {
          if(req.status==200)
          {              
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;
             if(command=="Get")
              {
              getRow(baseResponse);
              }
              else if(command=="Add")
              {
              addRow(baseResponse);
              }
               else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              else if(command=="Delete")
              {
              deleteRow(baseResponse);
              }
              else
              {
              CheckDuplicate(baseResponse);
              }
             
          }
        }
} 
  
function getRow(baseResponse)
{  
              
    document.SupplierForm.txtstatus.value="A";
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {         
            var tbody=document.getElementById("tblList");
            var table=document.getElementById("Existing");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
            {
                  tbody.deleteRow(0);
            }  
            var count=baseResponse.getElementsByTagName("count");
            //alert(count.length);
            for(var k=0;k<count.length;k++)
            {
                   var LabCode=baseResponse.getElementsByTagName("labcode");
                   var SupplierCode=baseResponse.getElementsByTagName("code");
                   var SupplierName=baseResponse.getElementsByTagName("sname");
                   var Address1=baseResponse.getElementsByTagName("add1");
                   var Address2=baseResponse.getElementsByTagName("add2");
                   var Address3=baseResponse.getElementsByTagName("add3");
                   var Pin=baseResponse.getElementsByTagName("pincode");
                   var Dname=baseResponse.getElementsByTagName("distname");
                   var Phone1=baseResponse.getElementsByTagName("phone1");
                   var Phone2=baseResponse.getElementsByTagName("phone2");
                   var Fax=baseResponse.getElementsByTagName("fax");
                   var Mail=baseResponse.getElementsByTagName("mail");
                   
                   var CurrentStatus=baseResponse.getElementsByTagName("cstatus");
                   var Reference=baseResponse.getElementsByTagName("reference");
                   var Remarks=baseResponse.getElementsByTagName("remarks");
                     
                  var cLabCode=LabCode.item(k).firstChild.nodeValue;      
                  var cSupplierCode=SupplierCode.item(k).firstChild.nodeValue;
                  var cSupplierName=SupplierName.item(k).firstChild.nodeValue;
                  var cAddress1=Address1.item(k).firstChild.nodeValue;
                  var cAddress2=Address2.item(k).firstChild.nodeValue;
                  var cAddress3=Address3.item(k).firstChild.nodeValue;
                  var add="";var ph1="";var ph2="";var eid="";var rem="";var fx="";var rn="";
                  if(cAddress3==""||cAddress3=="null")
                        add="-";
                  else
                        add=cAddress3;
                  var cPin=Pin.item(k).firstChild.nodeValue;
                  var cDname=Dname.item(k).firstChild.nodeValue;
                  cDname=cDname.toLowerCase();
                  var cPhone1=Phone1.item(k).firstChild.nodeValue;
                  if(cPhone1==""||cPhone1=="null")
                        ph1="-";
                  else
                        ph1=cPhone1;
                  var cPhone2=Phone2.item(k).firstChild.nodeValue;
                  if(cPhone2==""||cPhone2=="null")
                        ph2="-";
                  else
                        ph2=cPhone2;
                  var cFax=Fax.item(k).firstChild.nodeValue;
                   if(cFax==""||cFax=="null")
                        fx="-";
                  else
                        fx=cFax;
                  var cMail=Mail.item(k).firstChild.nodeValue;
                  if(cMail==""||cMail=="null")
                        eid="-";
                  else
                        eid=cMail;
                  var cCurrentStatus=CurrentStatus.item(k).firstChild.nodeValue;
                  var cReference=Reference.item(k).firstChild.nodeValue;
                  if(cReference==""||cReference=="null")
                        rn="-";
                  else
                        rn=cReference;
                  var cRemarks=Remarks.item(k).firstChild.nodeValue;
                   if(cRemarks==""||cRemarks=="null")
                        rem="-";
                   else
                        rem=cRemarks;  
                  seq=seq+1;
                  var mycurrent_row=document.createElement("TR");
                  mycurrent_row.id=seq;
                         
                  var cell=document.createElement("TD");
                  var anc=document.createElement("A");      
                  var url="javascript:loadValuesFromTable('" + seq + "')";             
                  anc.href=url;
                         
                  var txtedit=document.createTextNode("Edit");
                  anc.appendChild(txtedit);
                  cell.appendChild(anc);
                  mycurrent_row.appendChild(cell);
                  
                  var cell21 =document.createElement("TD");   
                  var txtlab=document.createTextNode(cLabCode);                        
                  cell21.appendChild(txtlab);      
                  mycurrent_row.appendChild(cell21);   
                    
                  var cell2 =document.createElement("TD");   
                  var txtsucode=document.createTextNode(cSupplierCode);                        
                  cell2.appendChild(txtsucode);      
                  mycurrent_row.appendChild(cell2);   
                                                 
                  var cell3 =document.createElement("TD");   
                  var txtsuname=document.createTextNode(cSupplierName);   
                  cell3.appendChild(txtsuname);      
                  mycurrent_row.appendChild(cell3);
                        
                  var cell4 =document.createElement("TD");   
                  var txtad1=document.createTextNode(cAddress1);                        
                  cell4.appendChild(txtad1);      
                  mycurrent_row.appendChild(cell4);
                        
                  var cell5 =document.createElement("TD");   
                  var txtad2=document.createTextNode(cAddress2);                        
                  cell5.appendChild(txtad2);      
                  mycurrent_row.appendChild(cell5);
                        
                  var cell6 =document.createElement("TD");   
                  var txtad3=document.createTextNode(add);                        
                  cell6.appendChild(txtad3);      
                  mycurrent_row.appendChild(cell6);
                        
                        
                  var cell7 =document.createElement("TD");   
                  var txtpin=document.createTextNode(cPin);                        
                  cell7.appendChild(txtpin);      
                  mycurrent_row.appendChild(cell7);
                         
                  var cell8 =document.createElement("TD");   
                  var txtname=document.createTextNode(cDname);                        
                  cell8.appendChild(txtname);      
                  mycurrent_row.appendChild(cell8);    
                        
                  var cell9 =document.createElement("TD");   
                  var txtph1=document.createTextNode(ph1);                        
                  cell9.appendChild(txtph1);      
                  mycurrent_row.appendChild(cell9);
                        
                  var c1 =document.createElement("TD");   
                  var txtph2=document.createTextNode(ph2);                        
                  c1.appendChild(txtph2);      
                  mycurrent_row.appendChild(c1);
                         
                  var c2 =document.createElement("TD");   
                  var txtfa=document.createTextNode(cFax);                        
                  c2.appendChild(txtfa);      
                  mycurrent_row.appendChild(c2);
                        
                  var c3 =document.createElement("TD");   
                  var txtma=document.createTextNode(eid);                        
                  c3.appendChild(txtma);      
                  mycurrent_row.appendChild(c3);
                         
                  var c4 =document.createElement("TD");   
                  var txtref=document.createTextNode(cReference);                        
                  c4.appendChild(txtref);      
                  mycurrent_row.appendChild(c4);
                         
                  var c5 =document.createElement("TD");   
                  var txtStatus=document.createTextNode(cCurrentStatus);                        
                  c5.appendChild(txtStatus);      
                  mycurrent_row.appendChild(c5);
                         
                  var c6=document.createElement("TD");   
                  var txtrm=document.createTextNode(rem);                        
                  c6.appendChild(txtrm);      
                  mycurrent_row.appendChild(c6);
                        
                  tbody.appendChild(mycurrent_row);
                }
            }
            else
            {
                  alert("Failed to Load Values");
            }
            var flag1=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
            if(flag1=='Success')
            {
                var cou=baseResponse.getElementsByTagName("cou");
                for(var k=0;k<cou.length;k++)
                {
                  var Dname=baseResponse.getElementsByTagName("desc");
                  var cDname=Dname.item(k).firstChild.nodeValue;
                  cDname=cDname.toLowerCase();
                   
                  var cbo=document.getElementById("txtdname");
                  var opt =document.createElement("option"); 
                  var text=document.createTextNode(cDname);
                  opt.setAttribute("value",cDname);
                  opt.appendChild(text);
                  cbo.appendChild(opt);  
                                    
                } 
         }       
            
  }          
  
  
function loadValuesFromTable(rid)
{     
          var did=document.getElementById("divwork");
          did.style.display="block";
          com_id=rid;
          var r=document.getElementById(rid);
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          document.SupplierForm.lab.value=rcells.item(1).firstChild.nodeValue;
          document.SupplierForm.txtSupId.value=rcells.item(2).firstChild.nodeValue;
          document.SupplierForm.txtSupName.value=rcells.item(3).firstChild.nodeValue;
          document.SupplierForm.txtAddr1.value=rcells.item(4).firstChild.nodeValue;
          document.SupplierForm.txtAddr2.value=rcells.item(5).firstChild.nodeValue;
          document.SupplierForm.txtAddr3.value=rcells.item(6).firstChild.nodeValue;
          document.SupplierForm.txtPin.value=rcells.item(7).firstChild.nodeValue;
         
          document.SupplierForm.txtdname.value=rcells.item(8).firstChild.nodeValue;
          document.SupplierForm.txtPhone1.value=rcells.item(9).firstChild.nodeValue;
          document.SupplierForm.txtPhone2.value=rcells.item(10).firstChild.nodeValue;
          document.SupplierForm.txtfax.value=rcells.item(11).firstChild.nodeValue;
          document.SupplierForm.txtMail.value=rcells.item(12).firstChild.nodeValue;
          document.SupplierForm.txtref.value=rcells.item(13).firstChild.nodeValue;
          document.SupplierForm.txtstatus.value=rcells.item(14).firstChild.nodeValue;
          document.SupplierForm.txtremarks.value=rcells.item(15).firstChild.nodeValue;
          
          document.SupplierForm.CmdAdd.disabled=true;
          document.SupplierForm.CmdUpdate.disabled=false;
          document.SupplierForm.CmdDelete.disabled=false;
          document.SupplierForm.txtSupId.disabled=true;        
          document.SupplierForm.CmdDelete.focus();
     
}

function validmail()
{
//alert("mail validation")
	if(document.SupplierForm.txtMail.value!= "")
	{
		var at=document.SupplierForm.txtMail.value.indexOf("@");
                //alert(at)
		var att=document.SupplierForm.txtMail.value.lastIndexOf(".");
                //alert(att)
		if (at<1||att-at<2) 
			{
			alert("Not a valid e-mail");
			document.SupplierForm.txtMail.value="";
			document.SupplierForm.txtMail.focus();
		
			}
            
	 }
}

function updateRow(baseResponse)
{
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="success")
      {  
               alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.SupplierForm.txtSupId.value;
               items[1]=document.SupplierForm.txtSupName.value;
               items[2]=document.SupplierForm.txtAddr1.value;
               items[3]=document.SupplierForm.txtAddr2.value;
               items[4]=document.SupplierForm.txtAddr3.value;
               items[5]=document.SupplierForm.txtPin.value;
               items[6]=document.SupplierForm.txtdname.value;
               items[7]=document.SupplierForm.txtPhone1.value;
               items[8]=document.SupplierForm.txtPhone2.value;
               items[9]=document.SupplierForm.txtfax.value;
               items[10]=document.SupplierForm.txtMail.value;
               items[11]=document.SupplierForm.txtref.value;
               items[12]=document.SupplierForm.txtstatus.value;
               items[13]=document.SupplierForm.txtremarks.value;
               var r=document.getElementById(com_id);   
               var rcells=r.cells;
               if(items[4]==""||items[4]=="null")
                        add="-";
               else
                        add=items[4];
               if(items[7]==""||items[7]=="null")
                        ph1="-";
               else
                        ph1=items[7];
               if(items[8]==""||items[8]=="null")
                        ph2="-";
               else
                        ph2=items[8];
               if(items[10]==""||items[10]=="null")
                        eid="-";
               else
                        eid=items[10];
               if(items[13]==""||items[13]=="null")
                        rem="-";
               else
                        rem=items[13];  
                //rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[0];
                rcells.item(3).firstChild.nodeValue=items[1];
                rcells.item(4).firstChild.nodeValue=items[2];
                rcells.item(5).firstChild.nodeValue=items[3];
                rcells.item(6).firstChild.nodeValue=add;
                rcells.item(7).firstChild.nodeValue=items[5];
                rcells.item(8).firstChild.nodeValue=items[6];
                rcells.item(9).firstChild.nodeValue=ph1;
                rcells.item(10).firstChild.nodeValue=ph2;
                rcells.item(11).firstChild.nodeValue=items[9];
                rcells.item(12).firstChild.nodeValue=eid;
                rcells.item(13).firstChild.nodeValue=items[11];
                rcells.item(14).firstChild.nodeValue=items[12];
                rcells.item(15).firstChild.nodeValue=rem;
                
                document.SupplierForm.CmdAdd.disabled=false;
                document.SupplierForm.CmdUpdate.disabled=true;
                document.SupplierForm.CmdDelete.disabled=true;     
                
                document.SupplierForm.txtSupId.value="";
                document.SupplierForm.txtSupName.value="";
                document.SupplierForm.txtAddr1.value="";
                document.SupplierForm.txtAddr2.value="";
                document.SupplierForm.txtAddr3.value="";
                document.SupplierForm.txtPin.value="";
                document.SupplierForm.txtdname.value="";
                document.SupplierForm.txtPhone1.value="";
                document.SupplierForm.txtPhone2.value="";
                document.SupplierForm.txtfax.value="";
                document.SupplierForm.txtMail.value="";
                document.SupplierForm.txtref.value="";
                document.SupplierForm.txtstatus.value="";
                document.SupplierForm.txtremarks.value="";
                var did=document.getElementById("divwork");
                did.style.display="none";
                
                document.SupplierForm.txtSupId.disabled=false;                                                
                
       }
       else
       {
           alert("Failed to update values");
       }                                 
}

function addRow(baseResponse)
{
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {                       
                 var items=new Array();    
                 lcode=baseResponse.getElementsByTagName("LabCode")[0].firstChild.nodeValue;
                 scode=baseResponse.getElementsByTagName("SupplierCode")[0].firstChild.nodeValue;
                 sname=baseResponse.getElementsByTagName("SupplierName")[0].firstChild.nodeValue;
                 rate=baseResponse.getElementsByTagName("Address1")[0].firstChild.nodeValue;
                 actual=baseResponse.getElementsByTagName("Address2")[0].firstChild.nodeValue;
                 proc=baseResponse.getElementsByTagName("Address3")[0].firstChild.nodeValue;
                 pin=baseResponse.getElementsByTagName("Pin")[0].firstChild.nodeValue;
                 DistName=baseResponse.getElementsByTagName("DistName")[0].firstChild.nodeValue;
                 DistName=DistName.toLowerCase();
                 ph1=baseResponse.getElementsByTagName("Phone1")[0].firstChild.nodeValue;
                 ph2=baseResponse.getElementsByTagName("Phone2")[0].firstChild.nodeValue;
                 fax=baseResponse.getElementsByTagName("Fax")[0].firstChild.nodeValue;
                 mail=baseResponse.getElementsByTagName("Mail")[0].firstChild.nodeValue;
                 Ref=baseResponse.getElementsByTagName("Ref")[0].firstChild.nodeValue;
                 Status=baseResponse.getElementsByTagName("Status")[0].firstChild.nodeValue;
                 Remarks=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                 if(mail=="null"||mail=="")
                    mail="-";
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                 
                 seq=seq+1;                         
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");      
                
                 var url="javascript:loadValuesFromTable('" + seq + "')";             
                 anc.href=url;
                 
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
                    
                         var cell21 =document.createElement("TD");   
                         var txtlab=document.createTextNode(lcode);                        
                         cell21.appendChild(txtlab);      
                         mycurrent_row.appendChild(cell21); 
                        
                         var cell2 =document.createElement("TD");   
                         var txtsucode=document.createTextNode(scode);                        
                         cell2.appendChild(txtsucode);      
                         mycurrent_row.appendChild(cell2);   
                         
                         
                         var cell3 =document.createElement("TD");   
                         var txtsuname=document.createTextNode(sname);   
                         cell3.appendChild(txtsuname);      
                         mycurrent_row.appendChild(cell3);
                        
                         var cell4 =document.createElement("TD");   
                         var txtad1=document.createTextNode(rate);                        
                         cell4.appendChild(txtad1);      
                         mycurrent_row.appendChild(cell4);
                        
                        var cell5 =document.createElement("TD");   
                         var txtad2=document.createTextNode(actual);                        
                         cell5.appendChild(txtad2);      
                         mycurrent_row.appendChild(cell5);
                        
                        var cell6 =document.createElement("TD");   
                         var txtad3=document.createTextNode(proc);                        
                         cell6.appendChild(txtad3);      
                         mycurrent_row.appendChild(cell6);
                        
                        
                        var cell7 =document.createElement("TD");   
                         var txtpin=document.createTextNode(pin);                        
                         cell7.appendChild(txtpin);      
                         mycurrent_row.appendChild(cell7);
                         
                         var cell8 =document.createElement("TD");   
                         var txtname=document.createTextNode(DistName);                        
                         cell8.appendChild(txtname);      
                         mycurrent_row.appendChild(cell8);    
                        
                         var cell9 =document.createElement("TD");   
                         var txtph1=document.createTextNode(ph1);                        
                         cell9.appendChild(txtph1);      
                         mycurrent_row.appendChild(cell9);
                        
                         var c1 =document.createElement("TD");   
                         var txtph2=document.createTextNode(ph2);                        
                         c1.appendChild(txtph2);      
                         mycurrent_row.appendChild(c1);
                         
                          var c2 =document.createElement("TD");   
                         var txtfa=document.createTextNode(fax);                        
                         c2.appendChild(txtfa);      
                         mycurrent_row.appendChild(c2);
                        
                         var c3 =document.createElement("TD");   
                         var txtma=document.createTextNode(mail);                        
                         c3.appendChild(txtma);      
                         mycurrent_row.appendChild(c3);
                         
                         var c4 =document.createElement("TD");   
                         var txtref=document.createTextNode(Ref);                        
                         c4.appendChild(txtref);      
                         mycurrent_row.appendChild(c4);
                         
                         var c5 =document.createElement("TD");   
                         var txtStatus=document.createTextNode(Status);                        
                         c5.appendChild(txtStatus);      
                         mycurrent_row.appendChild(c5);
                         
                         var c6=document.createElement("TD");   
                         var txtrm=document.createTextNode(Remarks);                        
                         c6.appendChild(txtrm);      
                         mycurrent_row.appendChild(c6);
                        
                         tbody.appendChild(mycurrent_row);
               
                document.SupplierForm.CmdAdd.disabled=false;
                document.SupplierForm.CmdUpdate.disabled=true;
                document.SupplierForm.CmdDelete.disabled=true;     
                alert("Record Inserted Into Database successfully. Supplier Code is "+scode);
                
                document.SupplierForm.txtSupId.value="";
                document.SupplierForm.txtSupName.value="";
                document.SupplierForm.txtAddr1.value="";
                document.SupplierForm.txtAddr2.value="";
                document.SupplierForm.txtAddr3.value="";
                document.SupplierForm.txtPin.value="";
                document.SupplierForm.txtdname.value="";
                document.SupplierForm.txtPhone1.value="";
                document.SupplierForm.txtPhone2.value="";
                document.SupplierForm.txtfax.value="";
                document.SupplierForm.txtMail.value="";
                document.SupplierForm.txtref.value="";
                document.SupplierForm.txtstatus.value="";
                document.SupplierForm.txtremarks.value="";
                document.SupplierForm.txtSupId.disabled=false;    
            }
            else
            {
                 alert("Record already exists, Insertion not possible");
            }
}


function deleteRow(baseResponse)
  {
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;                 
               if(flag=="success")
               {
                      //var concessioncode=baseResponse.getElementsByTagName("ConcessionCode")[0].firstChild.nodeValue;
                var tbody=document.getElementById("Existing"); 
                
                var r=document.getElementById(com_id);   
                var ri=r.rowIndex;              
                tbody.deleteRow(ri);
                 
                document.SupplierForm.CmdAdd.disabled=false;
                document.SupplierForm.CmdUpdate.disabled=true;
                document.SupplierForm.CmdDelete.disabled=true;     
                
                document.SupplierForm.txtSupId.value="";
                document.SupplierForm.txtSupName.value="";
                document.SupplierForm.txtAddr1.value="";
                document.SupplierForm.txtAddr2.value="";
                document.SupplierForm.txtAddr3.value="";
                document.SupplierForm.txtPin.value="";
                document.SupplierForm.txtdname.value="";
                document.SupplierForm.txtPhone1.value="";
                document.SupplierForm.txtPhone2.value="";
                document.SupplierForm.txtfax.value="";
                document.SupplierForm.txtMail.value="";
                document.SupplierForm.txtref.value="";
                document.SupplierForm.txtstatus.value="";
                document.SupplierForm.txtremarks.value="";
                document.SupplierForm.txtSupId.disabled=false; 
                var did=document.getElementById("divwork");
                did.style.display="none";
                alert("Selected Records are Deleted");                     
            }
            else if("FoundData")
            {
                    alert("Can not delete this Supplier");
            }
            else
            {
                alert("Unable to Delete");
            }
  
  }

function checkAvail()
{
    var scode=document.SupplierForm.txtSupId.value;
    url="../../../../../../WQS_SupplierMasterServ?command=CheckDuplicate&SupplierId="+scode;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }  
    req.send(null);   
}

function CheckDuplicate(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='failure')
    {
        alert("Not a valid supplier code");
        document.SupplierForm.txtSupId.value="";
	document.SupplierForm.txtSupId.focus(); 
    }
}

function checkVal()
{
    var status=document.SupplierForm.txtstatus.value;
    var iChar="aAtT";
    for(var i=0;i<=status.length;i++)
    {
         if(iChar.indexOf(status.charAt(i))==-1)
         {
             alert("Enter either a or t");
             document.SupplierForm.txtstatus.value="";
             document.SupplierForm.txtstatus.focus(); 
             break;
         }   
         var st1=status.toUpperCase();
         document.SupplierForm.txtstatus.value=st1;
    }
}

function checkPin()
{
    if((parseInt(document.SupplierForm.txtPin.value.length)<6)||(parseInt(document.SupplierForm.txtPin.value.length)>6))
    {
        alert("Pincode length must be in 6 digits");
        document.getElementById('txtPin').select();
        document.getElementById('txtPin').focus();
        return false;
    }
    if ((document.SupplierForm.txtPin.value < '600000' )|| (document.SupplierForm.txtPin.value> '700000'))
    {
        alert ("Pincode must be within 600000-700000 ");
        document.SupplierForm.txtPin.value="";
        document.SupplierForm.txtPin.focus();
	document.SupplierForm.txtPin.select();
        return;
  }
}

function isChar(string) 
{
   var iChars = "0123456789";
   for (var i=0; i<string.length; i++)
   {
       if (iChars.indexOf(string.charAt(i))!= -1)
        {
        alert("Numbers not allowed.....");
        document.SupplierForm.txtSupName.value="";
        document.SupplierForm.txtSupName.focus();
        document.SupplierForm.txtSupName.select();
        break;
        }
       }
}

function close_win()
{
    window.close();
}

function checklength(evn,item)
{
    var text=document.SupplierForm.txtremarks.value.length;
    var maxqty=100;
    var result = true;
    if(text >= maxqty)
    {
        result = false;	
    }
    return result;
}