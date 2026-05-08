 // loading office details
var currentlyEditing=0;
var UpdateingRow=0;

function loadOffice(id)
{   
   // alert("here");
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.UpdateOldRecForm.cmbOfficeLevel.focus();
    }
    else
    {
    
               //alert(" in loadOffice");
                var url="../../../../../ServletLoadOfficeDet.con?command=nil&ID="+id;            
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
               // alert(req.responseText);
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                //alert(response);
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                  //  alert("entered");
                    var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                   //alert(name);
                   // var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    //var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var add1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                   //alert("a1"+add1);
                    var add2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                   //alert("a2"+add2);
                    var cityname=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                   //alert("c"+cityname); 
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                   //alert("d"+district); 
                  //txt_ExtOffice_Name
                  //txt_ExtOffice_Address1
                  //txt_ExtOffice_Address2
                  //txt_ExtOffice_City
                  //cmb_ExtDistrict
                    if(name!="null")
                    {
                        document.UpdateOldRecForm.txt_ExtOffice_Name.value=name;
                    }
                   
                    if(add1!="null")
                    {
                        document.UpdateOldRecForm.txt_ExtOffice_Address1.value=add1;
                    }
                    if(add2!="null")
                    {
                        document.UpdateOldRecForm.txt_ExtOffice_Address2.value=add2;
                    }
                    if(cityname!="null")
                    {
                        document.UpdateOldRecForm.txt_ExtOffice_City.value=cityname;
                    }
                    if(district!="null")
                    {
                      document.UpdateOldRecForm.cmb_ExtDistrict.value=district;
                      //alert(" in district");
                    }
                    //document.UpdateOldRecForm.txtOffice_Name.value=name.firstChild.nodeValue;
                    
                    /*if(level=="Division" || level=="Sub-Division")
                        document.frmOffice.txtOfficeType.value=type;
                    else
                        document.frmOffice.txtOfficeType.value="Office level : " + level;*/
                        
                    //document.frmOffice.txtOfficeAddress.value=add;
                    //document.frmOffice.txtNewOfficeName.focus();
                    document.UpdateOldRecForm.cmb_ExtDistrict.disabled=true;
                    
                    document.UpdateOldRecForm.cmbOfficeLevel.selectedIndex=0;
                    document.UpdateOldRecForm.cmbOfficeType.selectedIndex=0;
                    document.UpdateOldRecForm.cmbSelectOffice.selectedIndex=0;
                    
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess); 
                    
                } 
          }
    }       
}

function loadOfficeSecond(id)
{   
    //alert("in second"+id);
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.UpdateOldRecForm.cmbAttachedOffice.focus();
    }
    else
    {
               //alert(" in loadOffice");
                var url="../../../../../ServletLoadOfficeDet.con?command=retrive&ID="+id;            
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {                
                   LoadOfficeDetailsSecond(req);             
                }
                req.send(null); 
    }
   
}
function LoadOfficeDetailsSecond(req) 
 {
  if(req.readyState==4)
    {
          if(req.status==200)
          {                
               //alert(req.responseText);
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                //alert(response);
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
           if(flag=="success")
                {
                  //  alert("entered");
                    var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                   //alert(name);
                   // var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    //var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
                   //alert("a1 "+id);
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                   //alert("d"+date);
                   
                   //txtAttachedOfficeID
                   //txt_ClsOffice_Name
                   //txt_DateClosure
                   
                   
                   if(id!="null")
                    {
                        document.UpdateOldRecForm.txtAttachedOfficeID.value=id;
                    }
                                      
                    if(name!="null")
                    {
                        document.UpdateOldRecForm.txt_ClsOffice_Name.value=name;
                    }
                   
                    if(date!="null")
                    {
                        document.UpdateOldRecForm.txt_DateClosure.value=date;
                    }
                    
                    document.UpdateOldRecForm.txtAttachedOfficeID.disabled=true;
                   document.UpdateOldRecForm.txt_ClsOffice_Name.disabled=true;
                   document.UpdateOldRecForm.txt_DateClosure.disabled=true;
                   document.UpdateOldRecForm.txt_DateHandover.focus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);
                    document.UpdateOldRecForm.txtAttachedOfficeID.value="";
                } 
           
           
           
           
           
                
          }
    }       
 }
function loadOfficeForMoveOldRec(id)
{   
    //alert("in second"+id);
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.UpdateOldRecForm.cmbOfficeLevel.focus();
    }
               //alert(" in loadOffice");
                var url="../../../../../ServletLoadOfficeDet.con?command=retrive&ID="+id;            
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {                
                   LoadOfficeDetailsForMoveOldRec(req);             
                }
                req.send(null); 
    
   
}

function LoadOfficeDetailsForMoveOldRec(req) 
 {
  if(req.readyState==4)
    {
          if(req.status==200)
          {                
               //alert(req.responseText);
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                //alert(response);
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
           if(flag=="success")
                {
                  //  alert("entered");
                    var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                   //alert(name);
                   // var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    //var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
                   //alert("a1 "+id);
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                   //alert("d"+date);
                   
                   //txtAttachedOfficeID
                   //txt_ClsOffice_Name
                   //txt_DateClosure
                   
                   
                   if(id!="null")
                    {
                        document.UpdateOldRecForm.txtAttachedOfficeID.value=id;
                    }
                                      
                    if(name!="null")
                    {
                        document.UpdateOldRecForm.txt_ClsOffice_Name.value=name;
                    }
                   
                    if(date!="null")
                    {
                        document.UpdateOldRecForm.txt_DateClosure.value=date;
                    }
                  //disabling the second part
                       document.UpdateOldRecForm.txtAttachedOfficeID.disabled=true;
                       document.UpdateOldRecForm.txt_ClsOffice_Name.disabled=true;
                       document.UpdateOldRecForm.txt_DateClosure.disabled=true;
                       document.UpdateOldRecForm.txt_Remark.disabled=true;
                   /*    
                  //disabling the third part
                        document.UpdateOldRecForm.txtOffice_Id_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Name_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Address1_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Address2_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_City_two.disabled=true;
                        document.UpdateOldRecForm.cmb_ExtDistrict_two.disabled=true;
                    //setting focus to the date field
                    document.UpdateOldRecForm.txt_DateShifting.focus();
                  */
                  
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);
                    document.UpdateOldRecForm.txtAttachedOfficeID.value="";
                } 
           
           
           
           
           
                
          }
    }       
 }
 
 
 function load_Closed_Office(id)
{   
   // alert("here  :"+id);
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.UpdateOldRecForm.cmbOfficeLevel.focus();
    }
    else
    {
    
               //alert(" in loadOffice");
                var url="../../../../../ServletLoadOfficeDet.con?command=ClosedOffice&ID="+id;            
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {                
                   Load_Closed_Office_Details(req);             
                }
                req.send(null); 
    
    }
}


function Load_Closed_Office_Details(req) 
 {
  if(req.readyState==4)
    {
          if(req.status==200)
          {    
          currentlyEditing=0;
              
                var tbody=document.getElementById("tb_List");
                var t=0;
                 for(t=tbody.rows.length-1;t>=0;t--)
                 {
                     tbody.deleteRow(0);
                 }

          
          
           if(req.responseText=="<response><flag>success</flag></response>")
                   {
                   alert("No Records Found");
                   return;
                   }
          
          
          
          
          
               //alert(req.responseText);
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                //alert(response);
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
           if(flag=="success")
                {
                    //alert("entered");
                   var len=response.getElementsByTagName("name").length;
                  // alert("l:"+len);
                   for(var i=0;i<len;i++)
                   {
                    currentlyEditing=currentlyEditing+1;
                    Sl_No=currentlyEditing;
                   
                   
                    var name=response.getElementsByTagName("name")[i].firstChild.nodeValue;
                  
                   //alert(name);
                   // var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    //var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var id=response.getElementsByTagName("id")[i].firstChild.nodeValue;
                   //alert("a1 "+id);
                    var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                   //alert("d"+date);
                  
                  
                                    
                  
                    var tbody=document.getElementById("tb_List");
                    var mycurrent_row=document.createElement("TR");
                    mycurrent_row.id=Sl_No;
                    //alert("my row "+mycurrent_row.id);
                    var cell1=document.createElement("TD");
                    var cell2=document.createElement("TD");
                    var cell3=document.createElement("TD");
                    var cell4=document.createElement("TD");
                    var cell5=document.createElement("TD");
               
                    var anc=document.createElement("input");       
                    anc.type="checkbox";
                    anc.name="checkName";
                    anc.id="checkName";
                   
                   // anc.onClick="javascript:ShiftTo('" +Sl_No+ "')";              
                    //anc.href=url;
                    //var txtedit=document.createTextNode("Edit");
                    //anc.appendChild(txtedit);
                    cell1.appendChild(anc);
                    
                    var hidden_Add=document.createElement("input");
                    hidden_Add.type="hidden";
                    hidden_Add.name="chk_vals";
                   // hidden_Add.value=Sl_No;
                    cell1.appendChild(hidden_Add);
                    
                    mycurrent_row.appendChild(cell1);
                   
                    var txtslno=document.createTextNode(Sl_No);
                    cell2.appendChild(txtslno);
                    var hidden1=document.createElement("input");
                    hidden1.type="hidden";
                    hidden1.name="sno";
                    hidden1.value=Sl_No;
                    cell2.appendChild(hidden1);
                    mycurrent_row.appendChild(cell2);
                   
                     
                   if(id!="null")
                    {
                          var txtId=document.createTextNode(id);
                          cell3.appendChild(txtId);
                          var hidden2=document.createElement("input");
                          hidden2.type="hidden";
                          hidden2.name="id";
                          hidden2.value=id;
                          cell3.appendChild(hidden2);
                          mycurrent_row.appendChild(cell3);
                    }
                    
                    
           
                    
                                      
                    if(name!="null")
                    {
                         var txtName=document.createTextNode(name);
                          cell4.appendChild(txtName);
                          var hidden3=document.createElement("input");
                          hidden3.type="hidden";
                          hidden3.name="name";
                          hidden3.value=name;
                          cell4.appendChild(hidden3);
                          mycurrent_row.appendChild(cell4);
                    }
                   
                    if(date!="null")
                    {
                        var txtDate=document.createTextNode(date);
                          cell5.appendChild(txtDate);
                          var hidden4=document.createElement("input");
                          hidden4.type="hidden";
                          hidden4.name="date";
                          hidden4.value=date;
                          cell5.appendChild(hidden4);
                          mycurrent_row.appendChild(cell5);
                    }
                    
                     tbody.appendChild(mycurrent_row);        
                    
               /*   //disabling the second part
                       document.UpdateOldRecForm.txtAttachedOfficeID.disabled=true;
                       document.UpdateOldRecForm.txt_ClsOffice_Name.disabled=true;
                       document.UpdateOldRecForm.txt_DateClosure.disabled=true;
                       document.UpdateOldRecForm.txt_Remark.disabled=true;
                     
                  //disabling the third part
                        document.UpdateOldRecForm.txtOffice_Id_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Name_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Address1_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_Address2_two.disabled=true;
                        document.UpdateOldRecForm.txt_ExtOffice_City_two.disabled=true;
                        document.UpdateOldRecForm.cmb_ExtDistrict_two.disabled=true;
                    //setting focus to the date field
                    document.UpdateOldRecForm.txt_DateShifting.focus();
                  */
                  
            /*      
                  
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);
                    document.UpdateOldRecForm.txtAttachedOfficeID.value="";
                } 
           
           */
           
           
           }
                
          }
    }       
 }
 }
 
/* function funCheckVals()
 {
 alert("called");
   var tbody=document.getElementById("tb_List");
    alert(tbody);
    
        var rows=tbody.rows;
        var i;
        alert("r"+rows.length);
    
    
        for(i=0;i<rows.length;i++)
        {
         var cells=rows[i].cells;
           if(document.UpdateOldRecForm.checkName[i].checked)
           {
          alert(cells[1].firstChild.nodeValue);
           }
           
            
        }
      

 
 
 
 }
 */
