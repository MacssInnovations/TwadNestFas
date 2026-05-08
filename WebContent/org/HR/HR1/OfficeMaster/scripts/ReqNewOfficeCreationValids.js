var currentlyEditing=0;
var UpdateingRow=0;


/* NullCheck Validation on Submit */
function nullcheck1()
            {
                 //alert("nullchk");
//Checking TextBoxes for null  
                 if((document.frmOffice.txt_Prop_Office_Name.value=="") || 
(document.frmOffice.txt_Prop_Office_Name.value.length<=0))
                  {
                    alert("Please Enter Name of the Proposed Office");
                    document.frmOffice.txt_Prop_Office_Name.focus();
                    return false;
                    
                  }
                  
                   if((document.frmOffice.txt_Prop_Short_Name.value=="") || 
(document.frmOffice.txt_Prop_Short_Name.value.length<=0))
                  {
                    alert("Please Enter Short Name of the Proposed Office");
                    document.frmOffice.txt_Prop_Short_Name.focus();
                    return false;
                    
                  }
                
                   
                  
  //Checking Combos for null                
                  if((document.frmOffice.cmbLevelId.value==0) || 
(document.frmOffice.cmbLevelId.value.length<=0))
                  {
                    alert("Please Select Office Level");
                    document.frmOffice.cmbLevelId.focus();
                    return false;
                    
                  }
                   if((document.frmOffice.cmbPrimaryID.value==0) || 
(document.frmOffice.cmbPrimaryID.value.length<=0))
                  {
                    alert("Please Select Primary Nature of Work");
                    document.frmOffice.cmbPrimaryID.focus();
                    return false;
                    
                  }
//checking the Date field
                  if((document.frmOffice.txt_Date.value=="") || 
(document.frmOffice.txt_Date.value.length<=0))
                  {
                    alert("Please Enter Date of Request");
                    document.frmOffice.txt_Date.focus();
                    return false;
                    
                  }
                  
//checking combo                  
            /*      if((document.frmOffice.cmbSecondaryID.value==0) || 
(document.frmOffice.cmbSecondaryID.value.length<=0))
                  {
                    alert("Please Select Additional Nature of Work");
                    document.frmOffice.cmbSecondaryID.focus();
                    return false;
                    
                  }
              */    
       
        //code to validate the Grid        
                  var tbody=document.getElementById("tblList");
                  rows=tbody.rows;
                  var length=tbody.rows.length;
                 // alert(length);
                  if(length<=0)
                  {
                    
                    alert("There is No Values in the Grid");
                    return false;
                  
                  }
                  
                return true;  
                
             }
                
 
 
 
 
 
 function nullWing()
                {
               
               //alert("in null wing");
                  if((document.frmOffice.cmbSecondaryID.value==0) || 
(document.frmOffice.cmbSecondaryID.value.length<=0))
                  {
                    alert("Please Select Additional Nature of Work");
                    document.frmOffice.cmbSecondaryID.focus();
                    return false;
                    
                  }
       /*            if((document.frmOffice.txt_Remark_Second.value=="") || 
(document.frmOffice.txt_Remark_Second.value.length<=0))
                  {
                    alert("Please Enter Remarks");
                    document.frmOffice.txt_Remark_Second.focus();
                    return false;
                    
                  }*/
                  
                //alert("out null wing");
                    return true;
                
                }
 
 
 
 
                
 function callServer1(command,param)
{
//alert("Callserver Called"+command);
    var url="";
    var Office_Id="";
    if(command=="Load")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
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
       //alert("in add");
        if(nullWing())
        {
        var Secondary_Work="";
        var Remark="";
       
        var flag=checkIf();
        //alert("flag"+flag);
        if(flag!=true)
        {
       //document.frmOffice.SL_NO.value
       Secondary_Work=document.frmOffice.cmbSecondaryID.value;
       //alert("sw ;"+Secondary_Work);
       Remark=document.frmOffice.txt_Remark_Second.value;
        //alert("rem ;"+Remark);
       
        currentlyEditing=parseInt(currentlyEditing)+1;
        Sl_No=currentlyEditing;
       
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
        var hidden0=document.createElement("input");
        hidden0.type="hidden";
        hidden0.name="Old";
        hidden0.value="new";
        cell1.appendChild(hidden0);
        mycurrent_row.appendChild(cell1);
        
    
        var txtslno=document.createTextNode(Sl_No);
        //txtslno.type="hidden";
        cell2.appendChild(txtslno);
        var hidden1=document.createElement("input");
        hidden1.type="hidden";
        hidden1.name="sno";
        hidden1.value=Sl_No;
        cell2.appendChild(hidden1);
        
        mycurrent_row.appendChild(cell2);
       
        var txtOffice_secwork=document.createTextNode(Secondary_Work);
        cell3.appendChild(txtOffice_secwork);
        
        var hidden2=document.createElement("input");
        hidden2.type="hidden";
        hidden2.name="Secondary_Work";
        hidden2.value=Secondary_Work;
        cell3.appendChild(hidden2);
        mycurrent_row.appendChild(cell3);
        
         var txtRemark=document.createTextNode(Remark);
        cell4.appendChild(txtRemark);
        
        var hidden3=document.createElement("input");
        hidden3.type="hidden";
        hidden3.name="Remark";
        hidden3.value=Remark;
        cell4.appendChild(hidden3);
        
        mycurrent_row.appendChild(cell4);
        
        tbody.appendChild(mycurrent_row);
        //checking if already exist
        
        }
        //Clear the fields
       // document.frmOffice.SL_NO.value="";
       // document.frmOffice.cmbSecondaryID.value="";
        document.frmOffice.txt_Remark_Second.value="";
       
        
       
        
        document.frmOffice.cmbSecondaryID.selectedIndex=0;
        
      }  
        
    }
    else if(command=="Update")
    {
        //alert("Update");
        //alert("currnetlyEditing:"+currentlyEditing);
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
        //cells.item(0).lastChild.value="Update";
        cells.item(1).firstChild.nodeValue=UpdateingRow;
        cells.item(1).lastChild.value=UpdateingRow;
        
        
        cells.item(2).firstChild.nodeValue=document.frmOffice.cmbSecondaryID.value;
        cells.item(2).lastChild.value=document.frmOffice.cmbSecondaryID.value;
        
        
        cells.item(3).firstChild.nodeValue=document.frmOffice.txt_Remark_Second.value;
        cells.item(3).lastChild.value=document.frmOffice.txt_Remark_Second.value;
       /*  
        cells.item(4).firstChild.nodeValue=document.frmOffice.txt_DateClosure.value;
        cells.item(4).lastChild.value=document.frmOffice.txt_DateClosure.value;
        
        cells.item(5).firstChild.nodeValue=document.frmOffice.txt_DateHandover.value;
        cells.item(5).lastChild.value=document.frmOffice.txt_DateHandover.value;
        
        cells.item(6).firstChild.nodeValue=document.frmOffice.txt_Remark.value;
        cells.item(6).lastChild.value=document.frmOffice.txt_Remark.value;
       
        cells.item(6).firstChild.nodeValue=document.frmOffice.txtFax_No.value;
        cells.item(6).lastChild.value=document.frmOffice.txtFax_No.value;
        
        cells.item(7).firstChild.nodeValue=document.frmOffice.Work_Nature.value;
        cells.item(7).lastChild.value=document.frmOffice.Work_Nature.value;
        
        cells.item(8).firstChild.nodeValue=document.frmOffice.txtEmailId.value;
        cells.item(8).lastChild.value=document.frmOffice.txtEmailId.value;
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
        //document.frmOffice.SL_NO.value="";
        document.frmOffice.cmbSecondaryID.value="";
        document.frmOffice.txt_Remark_Second.value="";
        
         document.frmOffice.cmdAdd.disabled=false;
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
                         document.frmOffice.txtOffice_Id.focus();
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
                              document.frmOffice.txtOffice_Name.value=name;
                              if(officeAddress1!="null")
                              {                               
                                        document.frmOffice.txtOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                
                                        document.frmOffice.txtOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                
                                        document.frmOffice.txtOffice_Address3.value=officeAddress3;
                              }
                              
                              if(Phone!="null")
                              {
                                        document.frmOffice.txtPhoneNo.value=Phone;
                              }
                              if(Fax!="null")
                              {
                                        document.frmOffice.txtFaxNo.value=Fax;
                              }
                              //var 
                        length=document.getElementById("cmbDistrict").options.length;
                              //alert(length);
                              /*for(var 
j=0;j<document.frmOffice.cmbDistrict.options.length();j++)
                              {
                                var 
value=document.frmOffice.cmbDistrict.options[j].value;
                                alert(value);
                                
                              }*/
                              document.frmOffice.cmbDistrict1.value=district;
                              document.frmOffice.cmbDistrict1.disabled=true;
                              
                          }
                          
                      }   
            }
        }
    }
}
               
 function checkIf()
    {
        //alert("* ");
        var tbody=document.getElementById("tblList");
        var rows=tbody.rows;
        var i;
        //alert("rows :"+rows.length);
        for(i=0;i<rows.length;i++)
        {
            var cells=rows[i].cells;
            
            //alert("cell :"+cells[2].firstChild.nodeValue);
            //alert("txt  :"+document.frmOffice.txtAttachedOfficeID.value);
            
          /*
             if(cells[2].firstChild.nodeValue==document.frmOffice.txtAttachedOfficeID.value)
            {
            
            alert("Row With The Given Closed Office ID Already Exist");
            return true;
            }
          */
        }
        return false; 
    }               
    
    function clearAllWing()
{
        
      //alert("clear all wing");
       /* 
        //clearing the first part
        document.frmOffice.txtOffice_Id.value="";
        document.frmOffice.txt_ExtOffice_Name.value="";
        document.frmOffice.txt_ExtOffice_Address1.value="";
        document.frmOffice.txt_ExtOffice_Address2.value="";
        document.frmOffice.txt_ExtOffice_City.value="";
        document.frmOffice.cmb_ExtDistrict.selectedIndex=0;
*/
        //clearing the second part
       document.frmOffice.cmbSecondaryID.value="";
       document.frmOffice.txt_Remark_Second.value="";
      // document.frmOffice.SL_NO.value="";
        
        document.frmOffice.cmdAdd.disabled=false;
        document.frmOffice.cmdUpdate.disabled=true;
        document.frmOffice.cmdDelete.disabled=true;
}
