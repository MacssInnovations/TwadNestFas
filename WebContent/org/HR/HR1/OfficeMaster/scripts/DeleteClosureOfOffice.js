
var winjob;
var serialNo=0;
var currentlyEditing=0;

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
function loadOffice(id,flag)
{   
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose...)");
        //document.frmClosure.txtAttachedOfficeID.focus();
    }
    else
    {
            startwaiting(document.frmClosure) ;
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
                stopwaiting(document.frmClosure) ;
                document.frmClosure.txtOfficeName.value="";
                document.frmClosure.txtOfficeAddress.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                    var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var city=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                    var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                    document.frmClosure.txtOfficeName.value=name;
                    if(address1!="null")
                    {
                    document.frmClosure.txtOfficeAddress.value=address1+"\n";
                    }
                    else
                    {
                    //document.frmClosure.txtOfficeAddress.value="";
                    }
                    if(address2!="null")
                    {
                        document.frmClosure.txtOfficeAddress.value=document.frmClosure.txtOfficeAddress.value+address2+"\n";
                    }
                    else
                    {
                      //  document.frmClosure.txtOfficeAddress.value=document.frmClosure.txtOfficeAddress.value+"\n"+"";
                    }
                    if(city!="null")
                    {
                    document.frmClosure.txtOfficeAddress.value=document.frmClosure.txtOfficeAddress.value+city+"\n";
                    }
                    else
                    {
                       // document.frmClosure.txtOfficeAddress.value=document.frmClosure.txtOfficeAddress.value+"\n"+"";
                    }
                    if(district!="null")
                    {
                        document.frmClosure.txtOfficeAddress.value=document.frmClosure.txtOfficeAddress.value+district+"\n";
                    }
                    else
                    {
                       // document.frmClosure.txtOfficeAddress.value=document.frmClosure.txtOfficeAddress.value+"\n"+"";
                    }
                    if(pincode!=0)
                    {
                        if(pincode!="null")
                        {
                            document.frmClosure.txtOfficeAddress.value=document.frmClosure.txtOfficeAddress.value+pincode;
                        }
                    }
                    else
                    {
                        //document.frmClosure.txtOfficeAddress.value=document.frmClosure.txtOfficeAddress.value;
                    }
                    dateofformation();
                    //document.frmClosure.txtClosureDate.focus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);
                    document.frmClosure.txtDateOfFormation.value="";
                    document.frmClosure.txtClosureDate.value="";
                    document.frmClosure.txtRemarks.value="";
                    document.frmClosure.submit.disabled=false;
                    document.frmClosure.submit.value="Submit";
                } 
          }
    }       
}

/*function LoadOfficeDetailsInAttachment(req)
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
                    //document.frmClosure.txtAttachedOfficeAddress.value=add.firstChild.nodeValue;
                    //document.frmClosure.cmbAccountType.focus();
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
}*/

function nullCheckMaster()
{
     if((document.frmClosure.txtOffice_Id.value=="") || (document.frmClosure.txtOffice_Id.value.length<=0))
    {
         alert("Please enter Office ID");
         document.frmClosure.txtOffice_Id.focus();
         return false;
    } 
     /*if((document.frmClosure.txtClosureDate.value=="") || (document.frmClosure.txtClosureDate.value.length<=0))
    {
        //alert('hai');
         alert("Please Enter Closure Date");
         document.frmClosure.txtClosureDate.focus();
         return false;
    } */ 
    //var w=window.open(window.location.href,"_self");
    //w.close();
    /* if((document.frmClosure.cmbReason.value=="" || document.frmClosure.cmbReason.value==0) || (document.frmClosure.cmbReason.value.length<=0))
    {
         alert("Please Select Reason for closure");
         document.frmClosure.cmbReason.focus();
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
    } */
    
    return true;
}

// for clearing attached office details

/*function clearOfficeDetails()
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
}*/

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
        
        
/*function hideorshowAttachments()
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
}*/

function dateofformation()
{
            var url="";
            //alert('hai');
            var officeid=document.frmClosure.txtOffice_Id.value;
            startwaiting(document.frmClosure) ;
            url="../../../../../ServletDate.con?command=Date&OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                DateResponse(req);                
            }
            req.send(null);
}

function DateResponse(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {         
                stopwaiting(document.frmClosure) ;
                document.frmClosure.txtDateOfFormation.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    if(date!="Not Specified")
                    {
                    document.frmClosure.txtDateOfFormation.value=date;
                    document.frmClosure.HDateOfFormation.value=date;
                    }
                    checkif();
                    //checkControlId();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);
                    document.frmClosure.txtDateOfFormation.value="";
                    document.frmClosure.txtClosureDate.value="";
                    //checkif();
                    document.frmClosure.submit.disabled=false;
                    //document.frmClosure.submit.value="Submit";
                }
                
           }     
        }
    

}


function checkControlId()
{
            var officeid=document.frmClosure.txtOffice_Id.value;
            //alert(officeid);
            startwaiting(document.frmClosure) ;
            var url="../../../../../NewClosureControlIdCheck.con?OfficeId="+officeid;            
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckControlIdResponse(req);
            }
            req.send(null);    

}


function CheckControlIdResponse(req)
{

        if(req.readyState==4)
        {
          if(req.status==200)
          {         
                stopwaiting(document.frmClosure);
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                //alert(flag);
                if(flag=="failure")
                {
                    var ctl=document.getElementById("divcontrol");
                        ctl.style.display="none";
                    alert('As this Office controls some other offices,so this Office cannot be Closed');
                    window.open('','_parent',''); 
                    window.close(); 
                    //window.opener.focus();
                   //if (winjob && winjob.open && !winjob.closed) winjob.close();
                   //winjob.close();
                   return false;
                }
                else if(flag=="success")
                {
                        var officeid=response.getElementsByTagName("officeid");
                        var ctl=document.getElementById("divcontrol");
                        ctl.style.display="block";
                        var tbody=document.getElementById("tblList");
                            var t=0;
                            for(t=tbody.rows.length-1;t>=0;t--)
                            {
                                tbody.deleteRow(0);
                            }
                        for(var i=0;i<officeid.length;i++)
                        {
                            
                            //alert(officeid[i].firstChild.nodeValue);
                            var officeid1=response.getElementsByTagName("officeid")[i].firstChild.nodeValue;
                            var officename=response.getElementsByTagName("officename")[i].firstChild.nodeValue;
                            var officeaddress1=response.getElementsByTagName("officeaddress1")[i].firstChild.nodeValue;
                            var officeaddress2=response.getElementsByTagName("officeaddress2")[i].firstChild.nodeValue;
                            var city=response.getElementsByTagName("city")[i].firstChild.nodeValue;
                            var district=response.getElementsByTagName("district")[i].firstChild.nodeValue;
                            var pincode=response.getElementsByTagName("pincode")[i].firstChild.nodeValue;
                            var address="";
                            
                            if(officeaddress1!="null")
                            {
                            officeaddress1=officeaddress1;
                            }
                            else
                            {
                                //alert('elseaddres1');
                                officeaddress1="";
                            }
                            if(officeaddress2!="null")
                            {
                                officeaddress2=officeaddress1+officeaddress2;
                            }
                            else
                            {
                                
                                officeaddress2=officeaddress1;
                            }
                            if((city!="null"))
                            {
                                 address=officeaddress1+officeaddress2+city;
                            }
                            else
                            {
                                
                                address=officeaddress2;
                            }
                            if(district!="null")
                            {
                                if(district!=0)
                                {
                                district=district;
                                }
                                else
                                {
                                district="";
                                }
                            }
                            else
                            {
                                district="";
                            }
                            //This coding for adding the values in table grid
                            
                            var table=document.getElementById("Existing");
                            var mycurrent_row=document.createElement("TR");
                            var cell1=document.createElement("TD");
                            var cell2=document.createElement("TD");
                            var cell3=document.createElement("TD");
                            var cell4=document.createElement("TD");
                            var cell5=document.createElement("TD");
                            
                            var txtofficeid=document.createTextNode(officeid1);
                            cell1.appendChild(txtofficeid);
                            var hidden1=document.createElement("input");
                            hidden1.type="hidden";
                            hidden1.name="officeid";
                            hidden1.value=officeid1;
                            cell1.appendChild(hidden1);
                            mycurrent_row.appendChild(cell1);
                            
                            var txtofficename=document.createTextNode(officename);
                            cell2.appendChild(txtofficename);
                            var hidden2=document.createElement("input");
                            hidden2.type="hidden";
                            hidden2.name="officename";
                            hidden2.value=txtofficename;
                            cell2.appendChild(hidden2);
                            mycurrent_row.appendChild(cell2);
                            
                            var txtofficeaddress1=document.createTextNode(address);
                            //txtofficeaddress1=txtofficeaddress1+","+officeaddress2;
                            cell3.appendChild(txtofficeaddress1);
                            var hidden3=document.createElement("input");
                            hidden3.type="hidden";
                            hidden3.name="officeaddress1";
                            hidden3.value=txtofficeaddress1;
                            cell3.appendChild(hidden3);
                            mycurrent_row.appendChild(cell3);
                            
                            var txtcity=document.createTextNode(district);
                            cell4.appendChild(txtcity);
                            var hidden5=document.createElement("input");
                            hidden5.type="hidden";
                            hidden5.name="city";
                            hidden5.value=txtcity;
                            cell4.appendChild(txtcity);
                            mycurrent_row.appendChild(cell4);
                            tbody.appendChild(mycurrent_row);
                       
                        }
                       /*var con=confirm("The following offices will also be closed.Is it Ok");
                       if(con==false)
                       {
                        self.close();
                       }*/
                       
                }
                else if(flag=="failedcontrol")
                {
                        var ctl=document.getElementById("divcontrol");
                        ctl.style.display="none";
                        var tbody=document.getElementById("tblList");
                            var t=0;
                            for(t=tbody.rows.length-1;t>=0;t--)
                            {
                                tbody.deleteRow(0);
                            }
                }
                
                
           }
           
           
        }   

}
function checkif()
{
//alert('hai');

            var officeid=document.frmClosure.txtOffice_Id.value;
            startwaiting(document.frmClosure) ;
            url="../../../../../ServletCheck.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckResponse(req);                
            }
            req.send(null);

}   


function CheckResponse(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {      
                stopwaiting(document.frmClosure) ;
                document.frmClosure.txtClosureDate.value="";
                document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    //alert("Office Id is Already Closed");
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                    document.frmClosure.txtClosureDate.value=date;
                    if(remarks!="null")
                    {
                    document.frmClosure.txtRemarks.value=remarks;
                    }
                    if(recordstatus=="FR")
                    {
                        alert('This Record is Freezed.');
                        //document.frmClosure.cmbRecordStatus.value=recordstatus;
                        document.frmClosure.submit.disabled=true;
                    }
                    else
                    {
                        //document.frmClosure.cmbRecordStatus.value=recordstatus;
                        document.frmClosure.submit.disabled=false;
                    }
                    //document.frmClosure.submit.value="Update";
                }
                else
                {
                    document.frmClosure.submit.disabled=false;
                    document.frmClosure.submit.value="Submit";
                    //var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    //alert("Error Occured : \n" + mess);  
                }
                
           }     
        }
}

function check1()
{
    var from=document.frmClosure.txtDateOfFormation.value;
    var to=document.frmClosure.txtClosureDate.value;
    check(from,to);
    
}

function check(c,todate)
{
	
   // document.workdemand.elements["txt_from"+c].value=""
     //fday=document.workdemand.elements["txt_from"+c].value.split("/");
     /*var tbody=document.getElementById("tblList");
     var rows=tbody.rows;
     var todate=rows[todate].cells[3].firstChild.nodeValue;*/
     //alert("todate"+todate);
     var todate=document.frmClosure.txtDateOfFormation.value;
     var fday=document.frmClosure.txtClosureDate.value.split("/");
     var todate=todate;
     //alert("todate"+todate);
     var frmDay = fday[0];
     var frmMonth = fday[1];
     var frmYear =fday[2];
    /* var frmDay = document.workdemand.elements["txt_from"+c].value.substr(0,2);
     var frmMonth = document.workdemand.elements["txt_from"+c].value.substr(3,2);
     var frmYear = document.workdemand.elements["txt_from"+c].value.substr(6,4)*/
     var frmday=new Date(frmYear,frmMonth-1,frmDay);
     tday=todate.split("/");
     var toDay = tday[0];
     var toMonth= tday[1];
     var toYear = tday[2];
  	/* var toDay = todate.value.substr(0,2);
     var toMonth = todate.value.substr(3,2);
     var toYear = todate.value.substr(6,4)*/
	 var today=new Date(toYear,toMonth-1,toDay);
       //alert("frmday"+frmday);
       //alert("today"+today);
	
       if (frmday<today)
	     {
		   alert ("Date Closure Should Not Be LesserThan Date of Formation");
                   document.frmClosure.txtClosureDate.focus();
		   //todate.value=""
		   //todate.focus();
		   
         
	   
     }
}





function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed) 
         winjob.officeSelection(true,true,true,false);
}

function doParentJob(jobid,deptid)
{
//alert(deptid);
if(deptid=="TWAD")
{
    document.frmClosure.txtOffice_Id.value=jobid;
    //document.HRE_EmployeeServiceDetails.txtDept_Id.value=deptid;
    loadOffice(jobid,'nothing');
    //checkif();
    checkControlId();
    checkclosure();
    return true
}
else
{
        alert('Please select a TWAD Office');
        if (winjob && winjob.open && !winjob.closed) 
        {
           winjob.resizeTo(500,500);
           winjob.moveTo(250,250); 
           winjob.focus();
        }
        return false
}
}

window.onunload=function()
{
//alert('hello');
//if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
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
                return false 
        }
     }
     
     
     function officeCheck()
{

                if((document.frmClosure.txtOffice_Id.value=="") || (document.frmClosure.txtOffice_Id.value.length<=0))
                {
                    alert("Please Enter Office Id or Select ");
                    document.frmClosure.txtOffice_Id.focus();
                    return false;
                    
                }


}

//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }

function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
} 


function funclear()
{
    var ctl=document.getElementById("divcontrol");
    ctl.style.display="none";
    var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
            tbody.deleteRow(0);
        }
document.frmClosure.submit.disabled=false;
}

function checkclosure()
{
//alert('hai');
            var officeid=document.frmClosure.txtOffice_Id.value;
            startwaiting(document.frmClosure) ;
            url="../../../../../ServletforClosure.con?OfficeId="+officeid;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckClosureResponse(req);                
            }
            req.send(null);
    
}

function CheckClosureResponse(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {    
          //alert(req.responseTEXT);
          stopwaiting(document.frmClosure) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    alert("This Office is Closed");
                    document.frmClosure.submit.disabled=true;
                    return false;
                
                }
                else
                {
                document.frmClosure.submit.disabled=false;
                    //return true;
                }
           }
        }
}