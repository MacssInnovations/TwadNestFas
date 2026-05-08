var currentlyEditing=0;
var UpdateingRow=0;

function loadOffice(id)
{   
    //alert("here");
    if(id=="" || id==null)
    {
        //alert("Enter An Office..Then Click choose..)");
      //  document.frmOffice.txtOffice_Id.focus();
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
                   document.frmOffice.txt_ExtOffice_Name.value="";
                   document.frmOffice.txt_ExtOffice_Address1.value="";
                   document.frmOffice.txt_ExtOffice_Address2.value="";
                   document.frmOffice.txt_ExtOffice_City.value="";
                   document.frmOffice.cmb_ExtDistrict.value="";
                    if(name!="null")
                    {
                        document.frmOffice.txt_ExtOffice_Name.value=name;
                    }
                   
                    if(add1!="null")
                    {
                        document.frmOffice.txt_ExtOffice_Address1.value=add1;
                    }
                    if(add2!="null")
                    {
                        document.frmOffice.txt_ExtOffice_Address2.value=add2;
                    }
                    if(cityname!="null")
                    {
                        document.frmOffice.txt_ExtOffice_City.value=cityname;
                    }
                    if(district!="null")
                    {
                      document.frmOffice.cmb_ExtDistrict.value=district;
                      //alert(" in district");
                    }
                    //document.UpdateOldRecForm.txtOffice_Name.value=name.firstChild.nodeValue;
                    
                    /*if(level=="Division" || level=="Sub-Division")
                        document.frmOffice.txtOfficeType.value=type;
                    else
                        document.frmOffice.txtOfficeType.value="Office level : " + level;*/
                        
                    //document.frmOffice.txtOfficeAddress.value=add;
                    //document.frmOffice.txtNewOfficeName.focus();
                    
                    document.frmOffice.cmb_ExtDistrict.disabled=true;
                    document.frmOffice.txtOffice_Id.disabled=true;
                   // document.frmOffice.cmbOfficeLevel.selectedIndex=0;
                    //document.frmOffice.cmbOfficeType.selectedIndex=0;
                    //document.frmOffice.cmbSelectOffice.selectedIndex=0;
                    
                }
                else
                {
                       document.frmOffice.txt_ExtOffice_Name.value="";
                       document.frmOffice.txt_ExtOffice_Address1.value="";
                       document.frmOffice.txt_ExtOffice_Address2.value="";
                       document.frmOffice.txt_ExtOffice_City.value="";
                       document.frmOffice.cmb_ExtDistrict.value="";
                       document.frmOffice.txtOffice_Id.value="";
                       
                      // document.frmOffice.txtOffice_Id.focus();
                                              
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess); 
                    
                } 
          }
    }       
}
//Loading initial grid values
function loadGridInitValsForReq(id)
{   
    //alert("grid id  :"+id);
    if(id=="" || id==null)
    {
       // alert("Enter or (Select An Office..Then Click choose..)");
        document.frmOffice.txt_Request_Id.focus();
    }
    else
    {
    
               //alert(" in loadOffice");
                var url="../../../../../ServletLoadOfficeDet.con?command=InitGridForReq&ID="+id;            
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {                
                   loadGridInitVals_DetailsForReq(req);             
                }
                req.send(null); 
    
    }
}


function loadGridInitVals_DetailsForReq(req) 
 {
  if(req.readyState==4)
    {
          if(req.status==200)
          {    
          currentlyEditing=0;
              
               //alert("res**ponse");
              //alert(req.responseText);
               
                var tbody=document.getElementById("tblList");
               //alert("TB:"+tbody);
                var t=0;
                 for(t=tbody.rows.length-1;t>=0;t--)
                 {
                     tbody.deleteRow(0);
                 }

          
          
           if(req.responseText=="<response><flag>success</flag></response>")
                   {
                   
                   document.frmOffice.txt_Prop_Office_Name.value="";
                   document.frmOffice.txt_Prop_Short_Name.value="";
                   document.frmOffice.cmbLevelId.value="";
                   document.frmOffice.cmbPrimaryID.value="";
                   document.frmOffice.txt_Remark.value="";
                   document.frmOffice.txt_Date.value="";
                  // alert("No Existing Records Found");
                   return;
                   }
          
          
          
          
          
               //alert(req.responseText);
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                //alert(response);
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
           if(flag=="success")
                {
                    //alert("entered");
                   var len=response.getElementsByTagName("SecondaryWork").length;
                   //alert("l:"+len);
                   for(var i=0;i<len;i++)
                   {
                    currentlyEditing=currentlyEditing+1;
                    Sl_No=currentlyEditing;
                   
         /*          
                   Office_Id=document.UpdateOldRecForm.txtAttachedOfficeID.value;
        currentlyEditing=currentlyEditing+1;
        Sl_No=currentlyEditing;
        Office_Name=document.UpdateOldRecForm.txt_ClsOffice_Name.value;
        Handover_Date=document.UpdateOldRecForm.txt_DateHandover.value;
        Remark=document.UpdateOldRecForm.txt_Remark.value;
        Closure_Date=document.UpdateOldRecForm.txt_DateClosure.value;
           */        
                   
                   
                    var Secondary_Work=response.getElementsByTagName("SecondaryWork")[i].firstChild.nodeValue;
                     var Remark=response.getElementsByTagName("remrk")[i].firstChild.nodeValue;
                   //alert("rem"+Remark);            
             
                  
                  
                  
                    //var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                   // var Office_Id=response.getElementsByTagName("id")[i].firstChild.nodeValue;
                   //alert("a1 "+Office_Id);
                   // var Request_Date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                   //alert("d"+Request_Date);
                    //var Handover_Date=response.getElementsByTagName("date2")[i].firstChild.nodeValue;
                   //alert("d1"+Handover_Date);
                  
            
            
            
            
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
       
        var hidden0=document.createElement("input");
        hidden0.type="hidden";
        hidden0.name="Old";
        hidden0.value="old";
       
        cell1.appendChild(anc);
        cell1.appendChild(hidden0);
        mycurrent_row.appendChild(cell1);
        
        var txtslno=document.createTextNode(Sl_No);
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
       
        /*
        
        var txtReq_Name=document.createTextNode(Request_Date);
        cell4.appendChild(txtReq_Name);
        
        var hidden3=document.createElement("input");
        hidden3.type="hidden";
        hidden3.name="Request_Date";
        hidden3.value=Request_Date;
        cell4.appendChild(hidden3);
        mycurrent_row.appendChild(cell4);
        
        */
        
        
        
        var txtRemark=document.createTextNode(Remark);
        cell4.appendChild(txtRemark);
        
        var hidden3=document.createElement("input");
        hidden3.type="hidden";
        hidden3.name="Remark";
        hidden3.value=Remark;
        cell4.appendChild(hidden3);
        
        mycurrent_row.appendChild(cell4);
        
       
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
           // alert("sw"+Secondary_Work);
                    var OfficeName=response.getElementsByTagName("Office_Name")[0].firstChild.nodeValue;
                    //alert(":OFFNAM"+OfficeName);
                    var ShortName=response.getElementsByTagName("Short_Name")[0].firstChild.nodeValue;
                    //alert("Sn "+ShortName);
                    var LevelID=response.getElementsByTagName("Level_Id")[0].firstChild.nodeValue;
                    //alert("ld :"+LevelID);
                    var PrimaryNature=response.getElementsByTagName("Primary_Nature")[0].firstChild.nodeValue;
                    //alert("PN "+PrimaryNature);
                    var Request_Date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    //alert("d"+Request_Date);
                    var remark1=response.getElementsByTagName("rem")[0].firstChild.nodeValue;
           document.frmOffice.txt_Prop_Office_Name.value=OfficeName;
           document.frmOffice.txt_Prop_Short_Name.value=ShortName;
           document.frmOffice.cmbLevelId.value=LevelID;
           document.frmOffice.cmbPrimaryID.value=PrimaryNature;
           document.frmOffice.txt_Remark.value=remark1;
           document.frmOffice.txt_Date.value=Request_Date;
           
           
           
                
          }
    }       
 }
 }
// Loading values back to the fields from the grid
 function loadValuesFromTable(rid)
    {  
    //alert("load");
      var r=document.getElementById(rid);      
      //alert("R is:"+r);
      
      var rcells=r.cells;
      currentlyEditing=rcells.item(1).firstChild.nodeValue;
      UpdateingRow=rcells.item(1).firstChild.nodeValue;   
      //alert(" the edit id :"+UpdateingRow);
      //alert("currentlyEditing in Load"+currentlyEditing);
      //alert(rcells.item(1).lastChild.value);
      //alert(rcells.item(2).lastChild.value);
      //alert(rcells.item(3).lastChild.value);
      //alert(rcells.item(4).lastChild.value);
      //alert(rcells.item(5).lastChild.value);
      //alert(rcells.item(6).lastChild.value);
      //alert(rcells.item(7).lastChild.value);
      //alert(rcells.item(8).lastChild.value);
        
     //   document.frmOffice.SL_NO.value=rcells.item(1).firstChild.nodeValue;
        document.frmOffice.cmbSecondaryID.value=rcells.item(2).firstChild.nodeValue;
       //date loading
       // document.frmOffice.cmbSecondaryID.value=rcells.item(3).firstChild.nodeValue;
        document.frmOffice.txt_Remark_Second.value=rcells.item(3).firstChild.nodeValue;
       //alert("b4 5th");
       
       // document.frmOffice.txt_DateHandover.value=rcells.item(5).firstChild.nodeValue;
        //document.frmOffice.txt_Remark.value=rcells.item(6).firstChild.nodeValue;
     //disabling the closed office details
        
       // document.frmOffice.txt_ClsOffice_Name.disabled=true;
        //document.frmOffice.txt_DateClosure.disabled=true;
     
      
      document.frmOffice.cmdAdd.disabled=true;
      document.frmOffice.cmdUpdate.disabled=false;
      document.frmOffice.cmdDelete.disabled=false;
    }
    
