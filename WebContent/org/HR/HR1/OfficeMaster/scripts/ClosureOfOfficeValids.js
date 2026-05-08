var currentlyEditing=0;
// code for creating XMLHTTPREQUEST object
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


function clearAllWing()
{

        document.UpdateOldRecForm.txtAttachedOfficeID.value="";
        document.UpdateOldRecForm.txt_ClsOffice_Name.value="";
        document.UpdateOldRecForm.txt_DateClosure.value="";
        document.UpdateOldRecForm.txt_DateHandover.value="";
        document.UpdateOldRecForm.txt_Remark.value="";
        document.UpdateOldRecForm.cmbAttachedOffice.selectedIndex=0;
        
        document.UpdateOldRecForm.txtAttachedOfficeID.disabled=false;
        document.UpdateOldRecForm.txt_ClsOffice_Name.disabled=false;
        document.UpdateOldRecForm.txt_DateClosure.disabled=false;
        
        document.UpdateOldRecForm.cmdAdd.disabled=false;
        document.UpdateOldRecForm.cmdUpdate.disabled=true;
        document.UpdateOldRecForm.cmdDelete.disabled=true;
}

//Clear All
function clearAll()
{
        document.UpdateOldRecForm.txtSl_No.value="";
        document.UpdateOldRecForm.txtWing_Name.value="";
        document.UpdateOldRecForm.cmbWing_Head.selectedIndex=0
        document.UpdateOldRecForm.txtDateCreated.value="";
        document.UpdateOldRecForm.txtEmailId.value="";
        document.UpdateOldRecForm.txtPhone_No.value="";
        document.UpdateOldRecForm.txtFax_No.value="";
        document.UpdateOldRecForm.Work_Nature.value="";
        var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
            tbody.deleteRow(0);
        }
        document.UpdateOldRecForm.cmdAdd.disabled=false;
        document.UpdateOldRecForm.cmdUpdate.disabled=true;
        document.UpdateOldRecForm.cmdDelete.disabled=true;

}

/* NullCheck Validation on Submit */
            function nullcheck()
            {
                  
                 /* if((document.UpdateOldRecForm.txtOffice_Id.value=="") || 
(document.UpdateOldRecForm.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.UpdateOldRecForm.txtOffice_Id.focus();
                    return false;
                    
                  }*/
                  var tbody=document.getElementById("tblList");
                  var length=tbody.rows.length;
                  //alert(length);
                  if(length<=0)
                  {
                    
                    alert("There is No Values in the Grid");
                    return false;
                  
                  }
                  return true;
                }
                
function nullWing()
                {
               
                  if((document.UpdateOldRecForm.txtOffice_Id.value=="") || 
(document.UpdateOldRecForm.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Exiting Office Id");
                    document.UpdateOldRecForm.txtOffice_Id.focus();
                    return false;
                    
                  }
                  if((document.UpdateOldRecForm.txtAttachedOfficeID.value=="") || 
(document.UpdateOldRecForm.txtAttachedOfficeID.value.length<=0))
                  {
                    alert("Please Enter Closed Office Id");
                    document.UpdateOldRecForm.txtAttachedOfficeID.focus();
                    return false;
                    
                  }
                   if((document.UpdateOldRecForm.txt_DateClosure.value=="") || 
(document.UpdateOldRecForm.txt_DateClosure.value.length<=0))
                  {
                    alert("Please Enter Closure Date");
                    document.UpdateOldRecForm.txt_DateClosure.focus();
                    return false;
                    
                  }
                   if((document.UpdateOldRecForm.txt_DateHandover.value=="") || 
(document.UpdateOldRecForm.txt_DateHandover.value.length<=0))
                  {
                    alert("Please Select Handover Date");
                    document.UpdateOldRecForm.txt_DateHandover.focus();
                    return false;
                  }
                
                    return true;
                
                }
                

function callServer1(command,param)
{
//alert("Callserver Called"+command);
    var url="";
    var Office_Id="";
    if(command=="Load")
    {
        Office_Id=document.UpdateOldRecForm.txtOffice_Id.value;
        //alert(Office_Id);
        url="../../../../../ServeUntitled.con?command=Load&OfficeId="+Office_Id;
        //alert(url);
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        processResponse(req);
        }
        req.send(null);
    }
   else if(command=="Add")
    {
       
        if(nullWing())
        {
        var Office_Id="";
        //var i=1;
        var Office_Name="";
        var Handover_Date="";
        var Remark="";
        var flag=checkIf();
        //alert("flag"+flag);
        if(flag!=true)
        {
        Office_Id=document.UpdateOldRecForm.txtAttachedOfficeID.value;
        currentlyEditing=currentlyEditing+1;
        Sl_No=currentlyEditing;
        Office_Name=document.UpdateOldRecForm.txt_ClsOffice_Name.value;
        Handover_Date=document.UpdateOldRecForm.txt_DateHandover.value;
        Remark=document.UpdateOldRecForm.txt_Remark.value;
        Closure_Date=document.UpdateOldRecForm.txt_DateClosure.value;
        //alert(Remark+"  "+Office_Id+"  "+Sl_No+"  "+Office_Name+"  "+Handover_Date);
        //url="../../../../../ServletOfficeWingInsert.con?command=Add&Office_Id="+Office_Id;
       
        
        //Append a row
        var tbody=document.getElementById("tblList");
        var mycurrent_row=document.createElement("TR");
        mycurrent_row.id=Sl_No;
        //alert("my row "+mycurrent_row.id);
        var cell1=document.createElement("TD");
        var cell2=document.createElement("TD");
        var cell3=document.createElement("TD");
        var cell4=document.createElement("TD");
        var cell5=document.createElement("TD");
        var cell6=document.createElement("TD");
        var cell7=document.createElement("TD");
   
        var anc=document.createElement("A");       
        var url="javascript:loadValuesFromTable('" +Sl_No+ "')";              
        anc.href=url;
        var txtedit=document.createTextNode("Edit");
        anc.appendChild(txtedit);
        cell1.appendChild(anc);
        mycurrent_row.appendChild(cell1);
        
        var txtslno=document.createTextNode(Sl_No);
        cell2.appendChild(txtslno);
        var hidden1=document.createElement("input");
        hidden1.type="hidden";
        hidden1.name="sno";
        hidden1.value=Sl_No;
        cell2.appendChild(hidden1);
        mycurrent_row.appendChild(cell2);
       
        var txtOffice_Id=document.createTextNode(Office_Id);
        cell3.appendChild(txtOffice_Id);
        
        var hidden2=document.createElement("input");
        hidden2.type="hidden";
        hidden2.name="Office_Id";
        hidden2.value=Office_Id;
        cell3.appendChild(hidden2);
        mycurrent_row.appendChild(cell3);
       
        
        
        var txtOffice_Name=document.createTextNode(Office_Name);
        cell4.appendChild(txtOffice_Name);
        
        var hidden3=document.createElement("input");
        hidden3.type="hidden";
        hidden3.name="Office_Name";
        hidden3.value=Office_Name;
        cell4.appendChild(hidden3);
        mycurrent_row.appendChild(cell4);
        
        
        
        
        
        var txtClosure_Date=document.createTextNode(Closure_Date);
        cell5.appendChild(txtClosure_Date);
        
        var hidden4=document.createElement("input");
        hidden4.type="hidden";
        hidden4.name="Closure_Date";
        hidden4.value=Closure_Date;
        cell5.appendChild(hidden4);
        
        mycurrent_row.appendChild(cell5);
        
        var txtHandover_Date=document.createTextNode(Handover_Date);
        cell6.appendChild(txtHandover_Date);
        
        var hidden5=document.createElement("input");
        hidden5.type="hidden";
        hidden5.name="Handover_Date";
        hidden5.value=Handover_Date;
        cell6.appendChild(hidden5);
        mycurrent_row.appendChild(cell6);
        
        var txtRemark=document.createTextNode(Remark);
        cell7.appendChild(txtRemark);
        
        var hidden6=document.createElement("input");
        hidden6.type="hidden";
        hidden6.name="Remark";
        hidden6.value=Remark;
        cell7.appendChild(hidden6);
        mycurrent_row.appendChild(cell7);
        
        tbody.appendChild(mycurrent_row);
        //checking if already exist
        
        }
        //Clear the fields
        document.UpdateOldRecForm.txtAttachedOfficeID.value="";
        document.UpdateOldRecForm.txt_ClsOffice_Name.value="";
        document.UpdateOldRecForm.txt_DateClosure.value="";
        document.UpdateOldRecForm.txt_DateHandover.value="";
        document.UpdateOldRecForm.txt_Remark.value="";
        
        document.UpdateOldRecForm.txtAttachedOfficeID.disabled=false;
        document.UpdateOldRecForm.txt_ClsOffice_Name.disabled=false;
        document.UpdateOldRecForm.txt_DateClosure.disabled=false;
        
        document.UpdateOldRecForm.cmbAttachedOffice.selectedIndex=0;
        
      }  
        
    }
    else if(command=="Update")
    {
        //alert("Update");
        //alert("currnetlyEditing:"+currentlyEditing);
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
        cells.item(1).firstChild.nodeValue=Sl_No;
        cells.item(1).lastChild.value=Sl_No;
        
        
cells.item(2).firstChild.nodeValue=document.UpdateOldRecForm.txtAttachedOfficeID.value;
        cells.item(2).lastChild.value=document.UpdateOldRecForm.txtAttachedOfficeID.value;
        
        
cells.item(3).firstChild.nodeValue=document.UpdateOldRecForm.txt_ClsOffice_Name.value;
        cells.item(3).lastChild.value=document.UpdateOldRecForm.txt_ClsOffice_Name.value;
        
        cells.item(4).firstChild.nodeValue=document.UpdateOldRecForm.txt_DateClosure.value;
        cells.item(4).lastChild.value=document.UpdateOldRecForm.txt_DateClosure.value;
        
        cells.item(5).firstChild.nodeValue=document.UpdateOldRecForm.txt_DateHandover.value;
        cells.item(5).lastChild.value=document.UpdateOldRecForm.txt_DateHandover.value;
        
        cells.item(6).firstChild.nodeValue=document.UpdateOldRecForm.txt_Remark.value;
        cells.item(6).lastChild.value=document.UpdateOldRecForm.txt_Remark.value;
      /*  
        cells.item(6).firstChild.nodeValue=document.UpdateOldRecForm.txtFax_No.value;
        cells.item(6).lastChild.value=document.UpdateOldRecForm.txtFax_No.value;
        
        cells.item(7).firstChild.nodeValue=document.UpdateOldRecForm.Work_Nature.value;
        cells.item(7).lastChild.value=document.UpdateOldRecForm.Work_Nature.value;
        
        cells.item(8).firstChild.nodeValue=document.UpdateOldRecForm.txtEmailId.value;
        cells.item(8).lastChild.value=document.UpdateOldRecForm.txtEmailId.value;
        */
        clearAllWing();
    }
    else if(command=="Delete")
    {
        var trow=currentlyEditing;
        //alert(trow);
        var tbody=document.getElementById("Existing"); 
        var r=document.getElementById(trow);    
        var ri=r.rowIndex;               
        tbody.deleteRow(ri);
        document.UpdateOldRecForm.txtAttachedOfficeID.value="";
        document.UpdateOldRecForm.txt_ClsOffice_Name.value="";
        document.UpdateOldRecForm.txt_DateClosure.value="";
        document.UpdateOldRecForm.txt_DateHandover.value="";
        document.UpdateOldRecForm.txt_Remark.value="";
    }
    
    
    function processResponse(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //alert(req.responseText);
                      var OfficeName=document.getElementById("txtOfficeName");
                      var OfficeId=document.getElementById("txtOfficeId");
                     
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var 
flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("Failed to retrieve the values");
                         document.UpdateOldRecForm.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("options");
                          //alert(value);
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                              var district=tmpoption.getElementsByTagName("District")[0].firstChild.nodeValue;
                              var Phone=tmpoption.getElementsByTagName("Phone")[0].firstChild.nodeValue;
                              var Fax=tmpoption.getElementsByTagName("Fax")[0].firstChild.nodeValue;
                              //alert(district);
                              //district=district.trim();
                              document.UpdateOldRecForm.txtOffice_Name.value=name;
                              if(officeAddress1!="null")
                              {                               
                                        document.UpdateOldRecForm.txtOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                
                                        document.UpdateOldRecForm.txtOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                
                                        document.UpdateOldRecForm.txtOffice_Address3.value=officeAddress3;
                              }
                              
                              if(Phone!="null")
                              {
                                        document.UpdateOldRecForm.txtPhoneNo.value=Phone;
                              }
                              if(Fax!="null")
                              {
                                        document.UpdateOldRecForm.txtFaxNo.value=Fax;
                              }
                              //var 
                        length=document.getElementById("cmbDistrict").options.length;
                              //alert(length);
                              /*for(var 
j=0;j<document.UpdateOldRecForm.cmbDistrict.options.length();j++)
                              {
                                var 
value=document.UpdateOldRecForm.cmbDistrict.options[j].value;
                                alert(value);
                                
                              }*/
                              document.UpdateOldRecForm.cmbDistrict1.value=district;
                              document.UpdateOldRecForm.cmbDistrict1.disabled=true;
                              
                          }
                          
                      }   
            }
        }
    }
}



// code for loading the values from the table to the input boxes
    // functionality for edit anchor
    function loadValuesFromTable(rid)
    {  
    //alert("hai");
      var r=document.getElementById(rid);      
      //alert("R is:"+r);
      var rcells=r.cells;
      currentlyEditing=rcells.item(1).firstChild.nodeValue;
      //alert("currentlyEditing in Load"+currentlyEditing);
      //alert(rcells.item(1).lastChild.value);
      //alert(rcells.item(2).lastChild.value);
      //alert(rcells.item(3).lastChild.value);
      //alert(rcells.item(4).lastChild.value);
      //alert(rcells.item(5).lastChild.value);
      //alert(rcells.item(6).lastChild.value);
      //alert(rcells.item(7).lastChild.value);
      //alert(rcells.item(8).lastChild.value);
        
        document.UpdateOldRecForm.txtAttachedOfficeID.value=rcells.item(2).firstChild.nodeValue;
        document.UpdateOldRecForm.txt_ClsOffice_Name.value=rcells.item(3).firstChild.nodeValue;
        document.UpdateOldRecForm.txt_DateClosure.value=rcells.item(4).firstChild.nodeValue;
        document.UpdateOldRecForm.txt_DateHandover.value=rcells.item(5).firstChild.nodeValue;
        document.UpdateOldRecForm.txt_Remark.value=rcells.item(6).firstChild.nodeValue;
     
      
      document.UpdateOldRecForm.cmdAdd.disabled=true;
      document.UpdateOldRecForm.cmdUpdate.disabled=false;
      document.UpdateOldRecForm.cmdDelete.disabled=false;
    }
    
    function checkIf()
    {
        //alert("* ");
        var tbody=document.getElementById("tblList");
        var rows=tbody.rows;
        var i;
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            
            //alert(cells[2].firstChild.nodeValue);
            //alert(document.UpdateOldRecForm.txtAttachedOfficeID.value);
            
             if(cells[2].firstChild.nodeValue==document.UpdateOldRecForm.txtAttachedOfficeID.value)
            {
            alert("Row With The Given Closed Office ID Already Exist");
           return true;
            }
        return false;
        }
        
    }