

var serialNo=0;
var currentlyEditing=0;


function loadOffice(id,flag)
{   
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose...)");
        //document.frmClosure.txtAttachedOfficeID.focus();
    }
    else
    {
            var url="../../../../../ServletLoadOfficeDetails.con?ID="+id;            
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
                LoadOfficeDetails(req);                
            }
            req.send(null);        
    }
}

function LoadOfficeDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var name=response.getElementsByTagName("name")[0];
                    var add=response.getElementsByTagName("address")[0];
                    document.frmClosure.txtOfficeName.value=name.firstChild.nodeValue;
                    document.frmClosure.txtOfficeAddress.value=add.firstChild.nodeValue;
                    document.frmClosure.cmbPrimaryID.focus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);                    
                } 
          }
    }       
}



// server handling

function callServer(command)
{
    if(command!="delete" && command!="list")
    {
        var c=document.frmClosure.butAdd.value;
        if(c=="  Add  ")
            command="add";
        else if(c=="Update")
            command="update";
    }
    alert(command);
    if(command=="list")
        {
            var url="../../../../../ServletAddlWorkNature.con?command=list&id=" + document.frmClosure.txtOffice_Id.value;            
            alert("calling "  + url);
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
                ProcessResponse(req);                
            }
            req.send(null);
            return;
        }
    if(nullcheck())
    {
        if(command=="add")
        {
            var url="../../../../../ServletAddlWorkNature.con?command=add";
            url=appendParameters(url);
            alert("calling "  + url);
            var req=getTransport();
            req.open("GET",url,true);            
            req.onreadystatechange=function()
            {                
                ProcessResponse(req);                
            }
            req.send(null);
            
        }
        else if(command=="update")
        {
            var url="../../../../../ServletAddlWorkNature.con?command=update";
            url=appendParameters(url);
            alert("calling "  + url);
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
                ProcessResponse(req);                
            }
            req.send(null);
        }
        else if(command=="delete")
        {
            var url="../../../../../ServletAddlWorkNature.con?command=delete";
            url=appendParameters(url);
            alert("calling "  + url);
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
                ProcessResponse(req);                
            }
            req.send(null);
        }        
    }
}

function appendParameters(url)
{
    var str="&id=" + document.frmClosure.txtOffice_Id.value;
    str=str+"&edate=" + document.frmClosure.txtEffectiveDate.value;    
    str=str+"&workNatureId=" + document.frmClosure.cmbPrimaryID.value;
    str=str+"&remarks=" + document.frmClosure.txtRemarks.value;
    str=url+str;
    return str;
}

function ProcessResponse(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {  
            alert("response received : " + req.responseText);
            var response=req.responseXML.getElementsByTagName("response")[0];
            var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            if(command=="add")
            {
                handleAdd(response);
            }
            else if(command=="update")
            {
                handleUpdate(response);
            }
            else if(command=="delete")
            {
                handleDelete(response);
            } 
            else
            {
                LoadAdditionalDetails(response);
            }
          }
    }
}

function handleAdd(response)
{
    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        // call add to servlet
        addToTable("Add");
    }
    else
    {
        alert("additional work nature is already there");
    }    
}

function handleUpdate(response)
{
    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        // call add to servlet
        addToTable("update");
    }
    else
    {
        alert("updating the additional work nature was failed");
    }    
}

function handleDelete(response)
{
    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {        
        deleteFromTable();
    }
    else
    {
        alert("Deleting the additional work nature was failed");
    }    
}

//loading additional values
function LoadAdditionalDetails(response)
{    
    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {        
        var tbody=document.getElementById("attachments");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
            tbody.deleteRow(0);
        }
        // process and load to table
        var aoptions=response.getElementsByTagName("options");
        var i=0;
        for(i=0;i<aoptions.length;i++)
        {
            var aid=aoptions[i].getElementsByTagName("id")[0].firstChild.nodeValue;
            var awork=aoptions[i].getElementsByTagName("work")[0].firstChild.nodeValue;
            var aworkdesc=aoptions[i].getElementsByTagName("workdesc")[0].firstChild.nodeValue;
            var aedate=aoptions[i].getElementsByTagName("edate")[0].firstChild.nodeValue;
            var aremarks=aoptions[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
            addToTableFromServer(aid,awork,aworkdesc,aedate,aremarks);
        }        
    }   
}

// add to grid from server
function addToTableFromServer(aid,awork,aworkdesc,aedate,aremarks)
{ 
  ++serialNo;
         var table=document.getElementById("attachments");     
             var mycurrent_row=document.createElement("TR");
             mycurrent_row.id=serialNo;
             //setting address as attribute of tr
             //mycurrent_row.setAttribute('add_bak', document.frmClosure.txtAttachedOfficeAddress.value);
             
             var cell=document.createElement("TD");
             var anchor=document.createElement("A");
             anchor.href="javascript:LoadFromTable('" + serialNo + "')";
             var str=document.createTextNode("Edit");
             anchor.appendChild(str);
             cell.appendChild(anchor);
             mycurrent_row.appendChild(cell);    
            
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="OfficeID";            
             sno.value=aid;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="OfficeName";             
             sno.value=document.frmClosure.txtOfficeName.value;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="WorkNature";             
             sno.value=awork;
             cell.appendChild(sno); 
             //var acType=frmClosure.cmbPrimaryID.options[frmClosure.cmbPrimaryID.selectedIndex].text;
             cell.appendChild(document.createTextNode(aworkdesc));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="EffectiveDate";             
             sno.value=aedate;
             cell.appendChild(sno);              
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="Remarks";             
             sno.value=aremarks;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="txtAddress";             
             sno.value=document.frmClosure.txtOfficeAddress.value;
             cell.appendChild(sno);
             mycurrent_row.appendChild(cell);
             
             table.appendChild(mycurrent_row);
             clearOfficeDetails(); 
}


/// grid handling

function addToTable(butCaption)
{ 
  
  if(butCaption=="Add")
  {
      ++serialNo;
         var table=document.getElementById("attachments");     
             var mycurrent_row=document.createElement("TR");
             mycurrent_row.id=serialNo;
             //setting address as attribute of tr
             //mycurrent_row.setAttribute('add_bak', document.frmClosure.txtAttachedOfficeAddress.value);
             
             var cell=document.createElement("TD");
             var anchor=document.createElement("A");
             anchor.href="javascript:LoadFromTable('" + serialNo + "')";
             var str=document.createTextNode("Edit");
             anchor.appendChild(str);
             cell.appendChild(anchor);
             mycurrent_row.appendChild(cell);    
            
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="OfficeID";            
             sno.value=document.frmClosure.txtOffice_Id.value;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="OfficeName";             
             sno.value=document.frmClosure.txtOfficeName.value;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="WorkNature";             
             sno.value=document.frmClosure.cmbPrimaryID.value;
             cell.appendChild(sno); 
             var acType=document.frmClosure.cmbPrimaryID.options[document.frmClosure.cmbPrimaryID.selectedIndex].text;
             document.frmClosure.cmbPrimaryID.remove(document.frmClosure.cmbPrimaryID.selectedIndex);
             cell.appendChild(document.createTextNode(acType));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="EffectiveDate";             
             sno.value=document.frmClosure.txtEffectiveDate.value;
             cell.appendChild(sno);              
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="Remarks";             
             sno.value=document.frmClosure.txtRemarks.value;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="txtAddress";             
             sno.value=document.frmClosure.txtOfficeAddress.value;
             cell.appendChild(sno);
             mycurrent_row.appendChild(cell);
             
             table.appendChild(mycurrent_row);
             clearOfficeDetails();       
  }
  else
  {
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
        cells.item(1).firstChild.value=document.frmClosure.txtOffice_Id.value;
        cells.item(1).lastChild.nodeValue=document.frmClosure.txtOffice_Id.value;
        
        cells.item(2).firstChild.value=document.frmClosure.txtOfficeName.value;    
        cells.item(2).lastChild.nodeValue=document.frmClosure.txtOfficeName.value;    
        
        cells.item(4).firstChild.value=document.frmClosure.txtEffectiveDate.value;    
        cells.item(4).lastChild.nodeValue=document.frmClosure.txtEffectiveDate.value;    
        
        cells.item(3).firstChild.value=document.frmClosure.cmbPrimaryID.value;
        cells.item(3).lastChild.nodeValue=frmClosure.cmbPrimaryID.options[frmClosure.cmbPrimaryID.selectedIndex].text;
        
        cells.item(5).firstChild.value=document.frmClosure.txtRemarks.value;        
        cells.item(5).childNodes[1].nodeValue=document.frmClosure.txtRemarks.value;
        
        cells.item(5).lastChild.Value=document.frmClosure.txtOfficeAddress.value;
        
        clearOfficeDetails();
  }
}

//function to load values from table

function LoadFromTable(id)
{    
    var trow=document.getElementById(id);
    var cells=trow.cells;
    currentlyEditing=parseInt(id);
    //document.frmClosure.txtOffice_Id.value=cells.item(1).firstChild.value;
    //document.frmClosure.txtOfficeName.value=cells.item(2).firstChild.value;    
    document.frmClosure.txtEffectiveDate.value=cells.item(4).firstChild.value;    
    document.frmClosure.cmbPrimaryID.value=cells.item(3).firstChild.value;
    document.frmClosure.txtRemarks.value=cells.item(5).firstChild.value;
    //document.frmClosure.txtOfficeAddress.value=cells.item(5).lastChild.value;
    document.frmClosure.butAdd.value="Update";
    document.frmClosure.butCancel.disabled=false;
    document.frmClosure.butAdd.disabled=false;
    document.frmClosure.butDelete.disabled=false;
}

// function to delete a row from table
function deleteFromTable()
{
    var table=document.getElementById("attachments");
    var rid=""+currentlyEditing;    
    var r=document.getElementById(rid);   
    var ri=r.rowIndex;   
    table.deleteRow(r);
    clearOfficeDetails();
}

function nullcheck()
{
     if((frmClosure.txtOffice_Id.value=="") || (frmClosure.txtOffice_Id.value.length<=0))
    {
         alert("Please enter Attached Office ID");
         frmClosure.txtOffice_Id.focus();
         return false;
    } 
     if((frmClosure.txtRemarks.value=="") || (frmClosure.txtRemarks.value.length<=0))
    {
         alert("Please Enter Remarks");
         frmClosure.txtRemarks.focus();
         return false;
    }  
     if((frmClosure.cmbPrimaryID.value=="" || frmClosure.cmbPrimaryID.value==0) || (frmClosure.cmbPrimaryID.value.length<=0))
    {
         alert("Please Select Account Type");
         frmClosure.cmbPrimaryID.focus();
         return false;
    }      
    return true;
}


// for clearing attached office details

function clearOfficeDetails()
{     
    document.frmClosure.txtRemarks.value="";    
    document.frmClosure.cmbPrimaryID.selectedIndex=0;
    document.frmClosure.txtEffectiveDate.value="";        
    currentlyEditing=0;
    document.frmClosure.butAdd.value="  Add  ";
    document.frmClosure.butCancel.disabled=true;
    document.frmClosure.butDelete.disabled=true;    
}

/* Date Validation Checking */
        function validate_date(formName, textName)
        {
                var errMsg="", lenErr=false, dateErr=false;
                var testObj=eval('document.' + formName + '.' + textName + '.value');
                var testStr=testObj.split('/');
                if(testStr.length>3 || testStr.length<3)
                {
                    lenErr=true;
                    dateErr=true;
                    errMsg+="There is an error in the date format.";
                }
                var monthsArr = new Array("01", "02", "03", "04", "05", "06", "07", "08" ,"09", "10", "11", "12");
                var daysArr = new Array;
                for (var i=0; i<12; i++)
                {
                    if(i!=1)
                    {
                       if((i/2)==(Math.round(i/2)))
                       {
                          if(i<=6)
                          {
                              daysArr[i]="31";
                           }
                           else
                           {
                               daysArr[i]="30";
                           }
                        }
                       else
                       {
                          if(i<=6)
                          {
                                daysArr[i]="30";
                          }
                          else
                          {
                               daysArr[i]="31";
                          }
                       }
                    }
                    else
                    {
                        if((testStr[2]/4)==(Math.round(testStr[2]/4)))
                        {
                            daysArr[i]="29";
                        }
                        else
                        {
                            daysArr[i]="28";
                        }
                    }
                } 
                var monthErr=false, yearErr=false;
                if(testStr[2]<1000 && !lenErr)
                {
                    yearErr=true;
                    dateErr=true;
                    errMsg+="\nThe year \"" + testStr[2] + "\" is not correct.";
                }
                for(var i=0; i<12; i++)
                {
                    if(testStr[1]==monthsArr[i])
                    {
                      var setMonth=i;
                      break;
                    }
                }
                if(!lenErr && (setMonth==undefined))
                {
                    monthErr=true;
                    errMsg+="\nThe month \"" + testStr[1] + "\" is not correct.";
                    dateErr=true;
                }
                if(!monthErr && !yearErr && !lenErr)
                {
                    if(testStr[0]>daysArr[setMonth])
                    {
                        errMsg+=testStr[1] + ' ' + testStr[2] + ' does not have ' + testStr[0] + ' days.';
                        dateErr=true;
                    }
                }
                if(!dateErr)
                {
                    //eval('document.' + formName + '.' + 'submit()');
                }
                else
                {
                    alert(errMsg + '\n____________________________\n\nSample Date Format :\n dd/MM/yyyy');
                    //eval('document.' + formName + '.' + textName + '.focus()');
                    //alert(eval);
                    //eval('document.' + formName + '.' + textName + '.select()');
                    return false;
                }
                return true;
        }
        
        
