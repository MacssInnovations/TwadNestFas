

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
                if(flag=="nothing")
                    LoadOfficeDetails(req);
                else
                    LoadOfficeDetailsInAttachment(req);
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
                    document.frmClosure.txtClosureDate.focus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);                    
                } 
          }
    }       
}

function LoadOfficeDetailsInAttachment(req)
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
                    document.frmClosure.txtAttachedOfficeName.value=name.firstChild.nodeValue;
                    document.frmClosure.txtAttachedOfficeAddress.value=add.firstChild.nodeValue;
                    document.frmClosure.cmbAccountType.focus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);                    
                } 
          }
    }       
}

/// grid handling

function addToTable()
{
  var butCaption=document.frmClosure.butAdd.value;
  if(butCaption=="  Add  ")
  {
      ++serialNo;
       if(nullcheck())
         {
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
             sno.value=document.frmClosure.txtAttachedOfficeID.value;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="OfficeName";             
             sno.value=document.frmClosure.txtAttachedOfficeName.value;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             var sno=document.createElement("input");
             sno.type="hidden";
             sno.name="OfficeAddress";             
             sno.value=document.frmClosure.txtAttachedOfficeAddress.value;
             cell.appendChild(sno);  
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="AccountType";             
             sno.value=document.frmClosure.cmbAccountType.value;
             cell.appendChild(sno); 
             var acType=frmClosure.cmbAccountType.options[frmClosure.cmbAccountType.selectedIndex].text;
             cell.appendChild(document.createTextNode(acType));
             mycurrent_row.appendChild(cell);
             
             cell=document.createElement("TD");
             sno=document.createElement("input");
             sno.type="hidden";
             sno.name="Remarks";             
             sno.value=document.frmClosure.txtAttachedRemarks.value;
             cell.appendChild(sno); 
             cell.appendChild(document.createTextNode(sno.value));
             mycurrent_row.appendChild(cell);
             
             table.appendChild(mycurrent_row);                           
             if(frmClosure.cmbAccountType.options[frmClosure.cmbAccountType.selectedIndex].value=="A")
             { 
                document.frmClosure.butAdd.disabled=true;
             }
             clearOfficeDetails(); 
         }
  }
  else
  {
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
        cells.item(1).firstChild.value=document.frmClosure.txtAttachedOfficeID.value;
        cells.item(1).lastChild.nodeValue=document.frmClosure.txtAttachedOfficeID.value;
        
        cells.item(2).firstChild.value=document.frmClosure.txtAttachedOfficeName.value;    
        cells.item(2).lastChild.nodeValue=document.frmClosure.txtAttachedOfficeName.value;    
        
        cells.item(3).firstChild.value=document.frmClosure.txtAttachedOfficeAddress.value;    
        cells.item(3).lastChild.nodeValue=document.frmClosure.txtAttachedOfficeAddress.value;    
        
        cells.item(4).firstChild.value=document.frmClosure.cmbAccountType.value;
        cells.item(4).lastChild.nodeValue=frmClosure.cmbAccountType.options[frmClosure.cmbAccountType.selectedIndex].text;
        
        cells.item(5).firstChild.value=document.frmClosure.txtAttachedRemarks.value;        
        cells.item(5).lastChild.nodeValue=document.frmClosure.txtAttachedRemarks.value;        
        
        clearOfficeDetails();
  }
}

//function to load values from table

function LoadFromTable(id)
{    
    var trow=document.getElementById(id);
    var cells=trow.cells;
    currentlyEditing=parseInt(id);
    document.frmClosure.txtAttachedOfficeID.value=cells.item(1).firstChild.value;
    document.frmClosure.txtAttachedOfficeName.value=cells.item(2).firstChild.value;    
    document.frmClosure.txtAttachedOfficeAddress.value=cells.item(3).firstChild.value;    
    document.frmClosure.cmbAccountType.value=cells.item(4).firstChild.value;
    document.frmClosure.txtAttachedRemarks.value=cells.item(5).firstChild.value;    
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
     if((frmClosure.txtAttachedOfficeID.value=="") || (frmClosure.txtAttachedOfficeID.value.length<=0))
    {
         alert("Please enter Attached Office ID");
         frmClosure.txtAttachedOfficeID.focus();
         return false;
    } 
     if((frmClosure.txtAttachedRemarks.value=="") || (frmClosure.txtAttachedRemarks.value.length<=0))
    {
         alert("Please Enter Remarks");
         frmClosure.txtAttachedRemarks.focus();
         return false;
    }  
     if((frmClosure.cmbAccountType.value=="" || frmClosure.cmbAccountType.value==0) || (frmClosure.cmbAccountType.value.length<=0))
    {
         alert("Please Select Account Type");
         frmClosure.cmbAccountType.focus();
         return false;
    }      
    return true;
}

function nullCheckMaster()
{
     if((frmClosure.txtOffice_Id.value=="") || (frmClosure.txtOffice_Id.value.length<=0))
    {
         alert("Please enter Office ID");
         frmClosure.txtOffice_Id.focus();
         return false;
    } 
     if((frmClosure.txtClosureDate.value=="") || (frmClosure.txtClosureDate.value.length<=0))
    {
         alert("Please Enter Closure Date");
         frmClosure.txtClosureDate.focus();
         return false;
    }  
     if((frmClosure.cmbReason.value=="" || frmClosure.cmbReason.value==0) || (frmClosure.cmbReason.value.length<=0))
    {
         alert("Please Select Reason for closure");
         frmClosure.cmbReason.focus();
         return false;
    }  
    
    var reason=document.frmClosure.cmbReason.options[document.frmClosure.cmbReason.selectedIndex].text;    
    if(reason=="ATTACHMENT")
    {
        var table=document.getElementById("attachments");
        var noofrows=table.rows.length;
        //alert("rows : " + noofrows);
        if(noofrows<=0)
        {
            alert("please Enlist the attachments when you select the reason as attachment");
            return false;
        }
    } 
    
    return true;
}

// for clearing attached office details

function clearOfficeDetails()
{
    document.frmClosure.txtAttachedOfficeID.value="";
    document.frmClosure.txtAttachedOfficeName.value="";    
    document.frmClosure.txtAttachedRemarks.value="";
    document.frmClosure.cmbAccountType.selectedIndex=0;
    document.frmClosure.txtAttachedOfficeAddress.value="";        
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
        
        
function hideorshowAttachments()
{
    var reason=document.frmClosure.cmbReason.options[document.frmClosure.cmbReason.selectedIndex].text;    
    var divmas=document.getElementById("divmaster");
    if(reason=="ATTACHMENT")
    {        
        divmas.style.visibility="visible";
    } 
    else
    {
        divmas.style.visibility="hidden";
    }
}