seq=0;
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

function nullCheck()
{
                 
                  if((document.Instrument.category.value=="") || (document.Instrument.category.value.length<=0))
                  {
                       alert("Dont leave Instrument field is empty");
                       //document.Instrument.category.focus();
                       return false;
                  }                 
                  if((document.Instrument.type.value=="") && (document.Instrument.type.value.length<=0))
                  {
                       alert("Dont leave Type field is empty");
                       //document.SupplierForm.txtPro.focus();
                       return false;
                  }
                  
                  if((document.Instrument.sno.value=="") ||(document.Instrument.sno.value.length<=0))
                  {
                    alert("Dont leave Serial Number field is empty");
                    //document.SupplierForm.txtMainId.focus();
                    return false;
                  }
                  if((document.Instrument.make.value=="") ||(document.Instrument.make.value.length<=0))
                  {
                    alert("Dont leave Make field is empty");
                    //document.SupplierForm.txtMainId.focus();
                    return false;
                  }
                  if((document.Instrument.model.value=="") || (document.Instrument.model.value.length<=0))
                  {
                       alert("Dont leave Model field is empty");
                       //document.SupplierForm.txtCon.focus();
                       return false;
                  }
                  if((document.Instrument.cost.value=="") || (document.Instrument.cost.value.length<=0))
                  {
                       alert("Dont leave Cost field is empty");
                       return false;
                  }
                  if((document.Instrument.adate.value=="") && (document.Instrument.adate.value.length<=0))
                  {
                       alert("Dont leave Acquired Date field is empty");
                       //document.SupplierForm.txtPro.focus();
                       return false;
                  }
                  if((document.Instrument.cstatus.value=="") ||(document.Instrument.cstatus.value.length<=0))
                  {
                    alert("Dont leave Current Status field is empty");
                    //document.SupplierForm.txtMainId.focus();
                    return false;
                  }
                  return true;
}

function callServer(command,param)
{
       var lab=document.Instrument.lab.value;
       var lb=lab.split("--");
       var category=document.Instrument.category.value;
       var cat=category.split("--");
       var icode=document.Instrument.icode.value;
       var brand=document.Instrument.brand.value;
       var type=document.Instrument.type.value;
       var sno=document.Instrument.sno.value;
       var make=document.Instrument.make.value;
       var model=document.Instrument.model.value;
       var cost=document.Instrument.cost.value;
       var adate=document.Instrument.adate.value;
       var rcode=document.Instrument.rcode.value;
       if(rcode=="")
            rcode="-";
       var supplied=document.Instrument.supplied.value;
       if(supplied=="")
            supplied="-";
       var status=document.Instrument.cstatus.value;
       var remarks=document.Instrument.remarks.value;
       if(remarks=="")
            remarks="-";
       var rno=document.Instrument.rno.value;
       if(rno=="")
            rno="";
       if(command=="Add")
       {           
                var flag=nullCheck();
                if(flag==true)
                { 
                    if(cost=="")
                        cost=0;
                    url="../../../../../../WQS_InstrumentMaster?command=Add&LabCode="+lb[0]+"&CatCode="+cat[0]+"&Brand="+brand+"&Type="+type+"&Sno="+sno+"&Make="+make+"&Model="+model+"&Cost="+cost+"&Adate="+adate+"&Rcode="+rcode+"&Supplied="+supplied+"&Cstatus="+status+"&Remarks="+remarks+"&Rno="+rno;
                    var req=getTransport();
                    req.open("GET",url,true);       
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }  
                    req.send(null);  
                }
        }
        else if(command=="Get")
        {              
            var lab=document.Instrument.lab.value;
            var lb=lab.split("--");
            url="../../../../../../WQS_InstrumentMaster?command=Get&LabCode="+lb[0];
            var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
               processResponse(req);
            }  
            req.send(null);
        }
        else if(command=="Delete")
        { 
                    url="../../../../../../WQS_InstrumentMaster?command=Delete&LabCode="+lb[0]+"&CatCode="+cat[0]+"&Icode="+icode;
                    var req=getTransport();
                    req.open("GET",url,true);       
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }  
                    req.send(null);
        }
        else 
        {
                var flag=nullCheck();
                if(flag==true)
                { 
                    if(cost=="")
                        cost=0;
                    url="../../../../../../WQS_InstrumentMaster?command=Update&LabCode="+lb[0]+"&CatCode="+cat[0]+"&Icode="+icode+"&Brand="+brand+"&Type="+type+"&Sno="+sno+"&Make="+make+"&Model="+model+"&Cost="+cost+"&Adate="+adate+"&Rcode="+rcode+"&Supplied="+supplied+"&Cstatus="+cstatus+"&Remarks="+remarks+"&Rno="+rno;
                    var req=getTransport();
                    req.open("GET",url,true);       
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }  
                    req.send(null);
                }
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
              if(command=="Add")
              {
                  addRow(baseResponse);
              }
              else if(command=="Get")
              {
                  getRow(baseResponse);
              }
              else if(command=="Delete")
              {
                  deleteRow(baseResponse);
              }
              else
              {
                  updateRow(baseResponse);
              }
          }
    }
}

function addRow(baseResponse)
{
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
            {                       
                 
                 
                 var items=new Array();    
                 labcode=baseResponse.getElementsByTagName("LabCode")[0].firstChild.nodeValue;
                 catcode=baseResponse.getElementsByTagName("CatCode")[0].firstChild.nodeValue;
                 icode=baseResponse.getElementsByTagName("Num")[0].firstChild.nodeValue;
                 brand=baseResponse.getElementsByTagName("Brand")[0].firstChild.nodeValue;
                 type=baseResponse.getElementsByTagName("Type")[0].firstChild.nodeValue;
                 sno=baseResponse.getElementsByTagName("Sno")[0].firstChild.nodeValue;
                 make=baseResponse.getElementsByTagName("Make")[0].firstChild.nodeValue;
                 model=baseResponse.getElementsByTagName("Model")[0].firstChild.nodeValue;
                 cost=baseResponse.getElementsByTagName("Cost")[0].firstChild.nodeValue;
                 adate=baseResponse.getElementsByTagName("Adate")[0].firstChild.nodeValue;
                 rcode=baseResponse.getElementsByTagName("Rcode")[0].firstChild.nodeValue;
                 supplied=baseResponse.getElementsByTagName("Supplied")[0].firstChild.nodeValue;
                 cstatus=baseResponse.getElementsByTagName("Cstatus")[0].firstChild.nodeValue;
                 remarks=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                 rno=document.getElementById("rno").value;
                 cost=Math.abs(cost);
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
                    
                        
                         var cell2 =document.createElement("TD");   
                         var lcode=document.createTextNode(labcode);                        
                         cell2.appendChild(lcode);      
                         mycurrent_row.appendChild(cell2);   
                         
                         
                         var cell3 =document.createElement("TD");   
                         var ccode=document.createTextNode(catcode);   
                         cell3.appendChild(ccode);      
                         mycurrent_row.appendChild(cell3);
                        
                         var cell4 =document.createElement("TD");   
                         var code=document.createTextNode(icode);                        
                         cell4.appendChild(code);      
                         mycurrent_row.appendChild(cell4);
                        
                        var cell5 =document.createElement("TD");   
                         var bnd=document.createTextNode(brand);                        
                         cell5.appendChild(bnd);      
                         mycurrent_row.appendChild(cell5);
                        
                        var cell6 =document.createElement("TD");   
                         var itype=document.createTextNode(type);                        
                         cell6.appendChild(itype);      
                         mycurrent_row.appendChild(cell6);
                        
                        
                        var cell7 =document.createElement("TD");   
                         var no=document.createTextNode(sno);                        
                         cell7.appendChild(no);      
                         mycurrent_row.appendChild(cell7);
                         
                         var cell8 =document.createElement("TD");   
                         var mke=document.createTextNode(make);                        
                         cell8.appendChild(mke);      
                         mycurrent_row.appendChild(cell8);    
                        
                         var cell9 =document.createElement("TD");   
                         var mdl=document.createTextNode(model);                        
                         cell9.appendChild(mdl);      
                         mycurrent_row.appendChild(cell9);
                        
                         var c1 =document.createElement("TD");   
                         var cst=document.createTextNode(cost);                        
                         c1.appendChild(cst);      
                         mycurrent_row.appendChild(c1);
                         
                         var c2 =document.createElement("TD");   
                         var adt=document.createTextNode(adate);                        
                         c2.appendChild(adt);      
                         mycurrent_row.appendChild(c2);
                         
                         var c6 =document.createElement("TD");   
                         var rcd=document.createTextNode(rcode);                        
                         c6.appendChild(rcd);      
                         mycurrent_row.appendChild(c6);
                         
                         var c7 =document.createElement("TD");   
                         var adt=document.createTextNode(supplied);                        
                         c7.appendChild(adt);      
                         mycurrent_row.appendChild(c7);
                        
                         var c3 =document.createElement("TD");   
                         var cstat=document.createTextNode(cstatus);                        
                         c3.appendChild(cstat);      
                         mycurrent_row.appendChild(c3);
                         
                         var c4 =document.createElement("TD");   
                         var rmk=document.createTextNode(remarks);                        
                         c4.appendChild(rmk);      
                         mycurrent_row.appendChild(c4);
                         
                         var c5 =document.createElement("TD");   
                         var fno=document.createTextNode(rno);                        
                         c5.appendChild(fno);      
                         mycurrent_row.appendChild(c5);
                         
                         tbody.appendChild(mycurrent_row);
                         alert("Record Inserted Into Database successfully. Instrument Code is "+icode);
                         clearAll();
              /*  document.Instrument.CmdAdd.disabled=false;
                document.Instrument.CmdUpdate.disabled=true;
                document.Instrument.CmdDelete.disabled=true;     */                                                                       
            }
            else
            {
                 alert("Record already exists, Insertion not possible");
            }
}


function getRow(baseResponse)
{  
    document.Instrument.cstatus.value="A";         
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {         
            var tbody=document.getElementById("tblList");
            var table=document.getElementById("Existing");
            
            var count=baseResponse.getElementsByTagName("count");
            for(var k=0;k<count.length;k++)
            {
                   labcode=baseResponse.getElementsByTagName("LabCode")[k].firstChild.nodeValue;
                   catcode=baseResponse.getElementsByTagName("CatCode")[k].firstChild.nodeValue;
                   icode=baseResponse.getElementsByTagName("Num")[k].firstChild.nodeValue;
                   brand=baseResponse.getElementsByTagName("Brand")[k].firstChild.nodeValue;
                   if(brand=='null'||brand=="")
                        brand="-";
                   type=baseResponse.getElementsByTagName("Type")[k].firstChild.nodeValue;
                   sno=baseResponse.getElementsByTagName("Sno")[k].firstChild.nodeValue;
                   make=baseResponse.getElementsByTagName("Make")[k].firstChild.nodeValue;                   
                   model=baseResponse.getElementsByTagName("Model")[k].firstChild.nodeValue;
                   cost=baseResponse.getElementsByTagName("Cost")[k].firstChild.nodeValue;
                   adate=baseResponse.getElementsByTagName("Adate")[k].firstChild.nodeValue;
                   rcode=baseResponse.getElementsByTagName("Rcode")[k].firstChild.nodeValue;
                   if(rcode=='null'||rcode=="")
                        rcode="-";
                   supplied=baseResponse.getElementsByTagName("Supplied")[k].firstChild.nodeValue;
                   if(supplied=='null'||supplied=="")
                        supplied="-";
                   cstatus=baseResponse.getElementsByTagName("Cstatus")[k].firstChild.nodeValue;
                   remarks=baseResponse.getElementsByTagName("Remarks")[k].firstChild.nodeValue;
                   if(remarks=='null'||remarks=="")
                        remarks="-";
                   rno=baseResponse.getElementsByTagName("Rno")[k].firstChild.nodeValue;
                   cost=Math.abs(cost);
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
                         
                         var cell2 =document.createElement("TD");   
                         var lcode=document.createTextNode(labcode);                        
                         cell2.appendChild(lcode);      
                         mycurrent_row.appendChild(cell2);   
                         
                         
                         var cell3 =document.createElement("TD");   
                         var ccode=document.createTextNode(catcode);   
                         cell3.appendChild(ccode);      
                         mycurrent_row.appendChild(cell3);
                        
                         var cell4 =document.createElement("TD");   
                         var code=document.createTextNode(icode);                        
                         cell4.appendChild(code);      
                         mycurrent_row.appendChild(cell4);
                        
                        var cell5 =document.createElement("TD");   
                         var bnd=document.createTextNode(brand);                        
                         cell5.appendChild(bnd);      
                         mycurrent_row.appendChild(cell5);
                        
                        var cell6 =document.createElement("TD");   
                         var itype=document.createTextNode(type);                        
                         cell6.appendChild(itype);      
                         mycurrent_row.appendChild(cell6);
                        
                        
                        var cell7 =document.createElement("TD");   
                         var no=document.createTextNode(sno);                        
                         cell7.appendChild(no);      
                         mycurrent_row.appendChild(cell7);
                         
                         var cell8 =document.createElement("TD");   
                         var mke=document.createTextNode(make);                        
                         cell8.appendChild(mke);      
                         mycurrent_row.appendChild(cell8);    
                        
                         var cell9 =document.createElement("TD");   
                         var mdl=document.createTextNode(model);                        
                         cell9.appendChild(mdl);      
                         mycurrent_row.appendChild(cell9);
                        
                         var c1 =document.createElement("TD");   
                         var cst=document.createTextNode(cost);                        
                         c1.appendChild(cst);      
                         mycurrent_row.appendChild(c1);
                         
                          var c2 =document.createElement("TD");   
                         var adt=document.createTextNode(adate);                        
                         c2.appendChild(adt);      
                         mycurrent_row.appendChild(c2);
                         
                         var c6 =document.createElement("TD");   
                         var rcd=document.createTextNode(rcode);                        
                         c6.appendChild(rcd);      
                         mycurrent_row.appendChild(c6);
                         
                         var c7 =document.createElement("TD");   
                         var adt=document.createTextNode(supplied);                        
                         c7.appendChild(adt);      
                         mycurrent_row.appendChild(c7);
                        
                         var c3 =document.createElement("TD");   
                         var cstat=document.createTextNode(cstatus);                        
                         c3.appendChild(cstat);      
                         mycurrent_row.appendChild(c3);
                         
                         var c4 =document.createElement("TD");   
                         var rmk=document.createTextNode(remarks);                        
                         c4.appendChild(rmk);      
                         mycurrent_row.appendChild(c4);
                         
                         var c5 =document.createElement("TD");   
                         var fno=document.createTextNode(rno);                        
                         c5.appendChild(fno);      
                         mycurrent_row.appendChild(c5);
                         
                         tbody.appendChild(mycurrent_row);
                }
            }
            else
            {
                  alert("Failed to Load Values");
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
          
          
          var catcode=rcells.item(2).firstChild.nodeValue;
          var ctdesc="";
          var catcombo=document.getElementById("category");
          for(var c=catcombo.length-1;c>=0;c--)
          {
                var ct=catcombo.options[c].text;
                cat=ct.split("--");
                if(catcode==cat[0])
                {
                    ctdesc=cat[1];
                }
          }
          
          document.Instrument.category.value=catcode+"--"+ctdesc;
          document.Instrument.icode.value=rcells.item(3).firstChild.nodeValue;
          document.Instrument.brand.value=rcells.item(4).firstChild.nodeValue;
          document.Instrument.type.value=rcells.item(5).firstChild.nodeValue;
          document.Instrument.sno.value=rcells.item(6).firstChild.nodeValue;
         
          document.Instrument.make.value=rcells.item(7).firstChild.nodeValue;
          document.Instrument.model.value=rcells.item(8).firstChild.nodeValue;
          document.Instrument.cost.value=rcells.item(9).firstChild.nodeValue;
          document.Instrument.adate.value=rcells.item(10).firstChild.nodeValue;
          document.Instrument.rcode.value=rcells.item(11).firstChild.nodeValue;
          document.Instrument.supplied.value=rcells.item(12).firstChild.nodeValue;
          document.Instrument.cstatus.value=rcells.item(13).firstChild.nodeValue;
          document.Instrument.remarks.value=rcells.item(14).firstChild.nodeValue;
          document.Instrument.rno.value=rcells.item(15).firstChild.nodeValue;
          
          document.Instrument.category.disabled=true;
          document.Instrument.CmdAdd.disabled=true;
          document.Instrument.CmdUpdate.disabled=false;
          document.Instrument.CmdDelete.disabled=false;
}

function deleteRow(baseResponse)
  {
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
               if(flag=="Success")
               {
                var tbody=document.getElementById("Existing"); 
                
                var r=document.getElementById(com_id);   
                var ri=r.rowIndex;              
                tbody.deleteRow(ri);
                 
                clearAll()                            
                alert("Selected Records are Deleted");           
            }
            else
            {
                alert("Unable to Delete");
            }
  
}

function updateRow(baseResponse)
{
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="Success")
      {  
               alert("Record Updated Successfully.");
               var items=new Array();
               var lab=document.Instrument.lab.value;
               lb=lab.split("--");
               items[0]=lb[0];
               var cat=document.Instrument.category.value;
               category=cat.split("--");
               items[1]=category[0];
               items[2]=document.Instrument.icode.value;
               items[3]=document.Instrument.brand.value;
               if(items[3]==""||items[3]=="null")
                    items[3]="-";
               else
                    items[3]=items[3];
               items[4]=document.Instrument.type.value;
               items[5]=document.Instrument.sno.value;
               items[6]=document.Instrument.make.value;
               items[7]=document.Instrument.model.value;
               items[8]=document.Instrument.cost.value;
               items[9]=document.Instrument.adate.value;
               items[10]=document.Instrument.rcode.value;
               var rc="";var sup="";var rem="";
               if(items[10]==""||items[10]=="null")
                    rc="-";
               else
                    rc=items[10];
               items[11]=document.Instrument.supplied.value;
               if(items[11]==""||items[11]=="null")
                    sup="-";
               else
                    sup=items[11];
               items[12]=document.Instrument.cstatus.value;
               items[13]=document.Instrument.remarks.value;
               if(items[13]==""||items[13]=="null")
                    rem="-";
               else
                    rem=items[13];
               items[14]=document.Instrument.rno.value;
               
               var r=document.getElementById(com_id);   
               var rcells=r.cells;
               
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                rcells.item(4).firstChild.nodeValue=items[3];
                rcells.item(5).firstChild.nodeValue=items[4];
                rcells.item(6).firstChild.nodeValue=items[5];
                rcells.item(7).firstChild.nodeValue=items[6];
                rcells.item(8).firstChild.nodeValue=items[7];
                rcells.item(9).firstChild.nodeValue=items[8];
                rcells.item(10).firstChild.nodeValue=items[9];
                rcells.item(11).firstChild.nodeValue=rc;
                rcells.item(12).firstChild.nodeValue=sup;
                rcells.item(13).firstChild.nodeValue=items[12];
                rcells.item(14).firstChild.nodeValue=rem;
                rcells.item(15).firstChild.nodeValue=items[14];
                
                clearAll();                                              
                
       }
       else
       {
           alert("Failed to update values");
       }                                 
}


function clearAll()
{
       document.Instrument.category.selectedIndex=0;
       document.Instrument.icode.value="";
       document.Instrument.brand.value="";
       document.Instrument.type.value="";
       document.Instrument.sno.value="";
       document.Instrument.make.value="";
       document.Instrument.model.value="";
       document.Instrument.cost.value="";
       document.Instrument.adate.value="";
       document.Instrument.rcode.value="";
       document.Instrument.supplied.value="";
       document.Instrument.cstatus.value="";
       document.Instrument.remarks.value="";
       document.Instrument.rno.value="";
       var did=document.getElementById("divwork");
       did.style.display="none";
       document.Instrument.category.disabled=false;
       document.Instrument.CmdAdd.disabled=false;
       document.Instrument.CmdUpdate.disabled=true;
       document.Instrument.CmdDelete.disabled=true;    
}

function isNumberKey(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
     return false;
    return true;
}

function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
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
    
function checkVal()
{
    var status=document.Instrument.cstatus.value;
    var iChar="aAtT";
    for(var i=0;i<=status.length;i++)
    {
         if(iChar.indexOf(status.charAt(i))==-1)
         {
             alert("Enter either a or t");
             document.Instrument.cstatus.value="";
             document.Instrument.cstatus.focus(); 
             break;
         }   
         var st1=status.toUpperCase();
         document.Instrument.cstatus.value=st1;
    }
}

function checklength(evt,item)
{
    var maxqty=document.Instrument.remarks.value.length;
    var text =100;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}