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


//Calling Server
function loadOffice(id)
{   
   
    if(id=="" || id==null)
    {
        //alert("Enter An Office..Then Click choose..)");
      //  document.frmStaffStrength.txtOffice_Id.focus();
    }
    else
    {
               startwaiting(document.frmStaffStrength) ;
                var url="../../../../../UpdateDiversionServlet.con?command=nill&ID="+id;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {                
                   LoadOfficeDetails(req);             
                }
                req.send(null); 
    
    }
}


//Server Response Handling
function LoadOfficeDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.frmStaffStrength) ;
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                  
                   var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                   var add1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                   var add2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                   var cityname=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                   var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                   document.frmStaffStrength.txtOfficeName.value="";
                   //document.frmStaffStrength.txt_ExtOffice_Address1.value="";
                   //document.frmStaffStrength.txt_ExtOffice_Address2.value="";
                   //document.frmStaffStrength.txt_ExtOffice_City.value="";
                   //document.frmStaffStrength.cmb_ExtDistrict.value="";
                    if(name!="null")
                    {
                        document.frmStaffStrength.txtOfficeName.value=name;
                    }
                   
                    if(add1!="null")
                    {
                        //document.frmStaffStrength.txt_ExtOffice_Address1.value=add1;
                    }
                    if(add2!="null")
                    {
                        //document.frmStaffStrength.txt_ExtOffice_Address2.value=add2;
                    }
                    if(cityname!="null")
                    {
                        //document.frmStaffStrength.txt_ExtOffice_City.value=cityname;
                    }
                    if(district!="null")
                    {
                     // document.frmStaffStrength.cmb_ExtDistrict.value=district;
                      //alert(" in district");
                    }
                   // document.frmStaffStrength.cmb_ExtDistrict.disabled=true;
                   
                    document.frmStaffStrength.txtOffice_Id.disabled=true;
                    
                    OfficeLevel();
                   // PostRank();
                   // officeid();
                                       
                }
                else
                {
                        alert("Invalid Office Id");
                       document.frmStaffStrength.txtOfficeName.value="";
                      // document.frmStaffStrength.txt_ExtOffice_Address1.value="";
                       //document.frmStaffStrength.txt_ExtOffice_Address2.value="";
                      // document.frmStaffStrength.txt_ExtOffice_City.value="";
                      // document.frmStaffStrength.cmb_ExtDistrict.value="";
                       document.frmStaffStrength.txtOffice_Id.value="";
                   
                    //var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    //alert("Error Occured : \n" + mess); 
                    
                } 
          }
    }       
}

function OfficeLevel()
{
    var officeid=document.frmStaffStrength.txtOffice_Id.value;
   // alert(officeid);
    startwaiting(document.frmStaffStrength) ;
    var url="../../../../../ServletOfficeLevelStaffStrength.con?OfficeId="+officeid;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       LoadOfficeLevel(req);             
    }
    req.send(null); 
    
    
    
}

function LoadOfficeLevel(req)
{
    
    if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.frmStaffStrength);
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                var cmbFromOfficeId=document.getElementById("cmbFromOfficeId");
                var cmbToOfficeId=document.getElementById("cmbToOfficeId");
               // alert(cmbFromOfficeId);
                if(flag=="failure")
                {
                
                }
                else
                {
                    cmbFromOfficeId.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="---Select Office Id---";
                        try
                            {
                                   cmbFromOfficeId.add(option);
                            }catch(errorobject)
                            { 
                                     cmbFromOfficeId.add(option,null);
                            }
                    cmbToOfficeId.innerHTML="";
                    var option1=document.createElement("OPTION");
                    option1.text="---Select To Office Id---";
                    try
                    {
                        cmbToOfficeId.add(option1);
                    }catch(errorobject)
                    {
                        cmbToOfficeId.add(option1,null);
                    }
                    var value=response.getElementsByTagName("options");
                    
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var levelid=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var levelname=tmpoption.getElementsByTagName("value")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                        option.text=levelname;
                        option.value=levelid;
                        try
                            {
                                   cmbFromOfficeId.add(option);
                            }catch(errorobject)
                            { 
                                     cmbFromOfficeId.add(option,null);
                            }
                        var option1=document.createElement("OPTION");
                        option1.text=levelname;
                        option1.value=levelid;
                        
                        try
                        {
                            cmbToOfficeId.add(option1);
                        }catch(errorobject)
                        {
                            cmbToOfficeId.add(option1,null);
                        }
                    }
                
                }
                
          }
    }

}



//Calling Server for PostRank Loading Values
function PostRank()
{
    var officeid=document.frmStaffStrength.txtdiversionfromoffice.value;
    //alert(officeid);
    //var finyear=document.frmStaffStrength.cmbFinancialYear.value;
    //alert(officeid);
    startwaiting(document.frmStaffStrength) ;
    var url="../../../../../UpdateDiversionServlet.con?command=PostRank&OfficeId="+officeid;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       PostRankDetails(req);             
    }
    req.send(null); 
}


function PostRankDetails(req)
{

    if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.frmStaffStrength) ;
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                var postrank=document.getElementById("cmbPostRank");
               // var employmentstatus=document.getElementById("cmbPostCategory");
                if(flag=="success")
                {
                    postrank.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="---Select Post Rank Id---";
                        try
                            {
                                   postrank.add(option);
                            }catch(errorobject)
                            { 
                                     postrank.add(option,null);
                            }
                            
                   /* employmentstatus.innerHTML="";
                    var option1=document.createElement("OPTION");
                    option1.text="---Select PostCategory---";
                    try
                    {
                        employmentstatus.add(option1);
                    }catch(errorobject)
                    {
                        employmentstatus.add(option1,null);
                    }*/
                    var value=response.getElementsByTagName("options");
                    
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var rankid=tmpoption.getElementsByTagName("rankid")[0].firstChild.nodeValue;
                       // alert(rankid);
                        var rankname=tmpoption.getElementsByTagName("rankname")[0].firstChild.nodeValue;
                        //var employmentstatusid=tmpoption.getElementsByTagName("employmentstatus")[0].firstChild.nodeValue;
                        //var employmentstatusname=tmpoption.getElementsByTagName("empname")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                        option.text=rankname;
                        option.value=rankid;
                        try
                            {
                                   postrank.add(option);
                            }catch(errorobject)
                            { 
                                     postrank.add(option,null);
                            }
                        
                    }
                    /*var option1=document.createElement("OPTION");
                    option1.text=employmentstatusname;
                    option1.value=employmentstatusid;
                    
                    try
                    {
                        employmentstatus.add(option1);
                    }catch(errorobject)
                    {
                        employmentstatus.add(option1,null);
                    }*/
                }
                else
                {
                    postrank.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="---Select Post Rank Id---";
                        try
                            {
                                   postrank.add(option);
                            }catch(errorobject)
                            { 
                                     postrank.add(option,null);
                            }
                            
                    /*employmentstatus.innerHTML="";
                    var option1=document.createElement("OPTION");
                    option1.text="---Select PostCategory---";
                    try
                    {
                        employmentstatus.add(option1);
                    }catch(errorobject)
                    {
                        employmentstatus.add(option1,null);
                    }*/
                }
                
          }
    }
    

}


//Calling Server for Sanction post

function SanctionPost()
{
    var officeid=document.frmStaffStrength.txtdiversionfromoffice.value;
    var finyear=document.frmStaffStrength.cmbFinancialYear.value;
    //var postcategory=document.frmStaffStrength.cmbPostCategory.value;
    var date=document.frmStaffStrength.txtDoD.value;
    /*var finyear;
    var sc=date.split('/');
    var currentday=sc[0];
    var currentmonth=sc[1];
    var currentyear=sc[2];
    if(currentmonth<=3)
    {
        var year1=currentyear-1;
        var year2=currentyear;
        finyear=(year1)+"-"+(year2)
        //alert("if"+finyear);
    }
    else
    {
        var year1=currentyear;
        var year2=parseInt(currentyear)+parseInt(1);
        //alert(year2);
        finyear=(year1)+"-"+(year2);
        //alert("else"+finyear);
    }*/
    var postrank=document.frmStaffStrength.cmbPostRank.value;
    //alert(officeid);
    startwaiting(document.frmStaffStrength) ;
    var url="../../../../../UpdateDiversionServlet.con?command=SanctionPost&OfficeId="+officeid+"&PostRank="+postrank+"&FinYear="+finyear;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       SanctionPostDetails(req,finyear);             
    }
    req.send(null); 
}


function SanctionPostDetails(req,finyear)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.frmStaffStrength) ;
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                    var sanctionpost=response.getElementsByTagName("sanctionpost")[0].firstChild.nodeValue;
                    var diversionto=response.getElementsByTagName("divertedto")[0].firstChild.nodeValue;
                    var diversionfrom=response.getElementsByTagName("divertedfrom")[0].firstChild.nodeValue;
                    document.frmStaffStrength.noofsanction.value=sanctionpost;
                    document.frmStaffStrength.divertedtopost.value=diversionto;
                    //document.frmStaffStrength.divertedfrompost.value=diversionfrom;
                }
                else
                {
                    
                    alert("Sanctioned Strength Details Not Available for the FinancialYear"+finyear);
                    document.frmStaffStrength.txtdiversionfromoffice.value="";
                    document.frmStaffStrength.cmbPostRank.selectedIndex=0;
                    document.frmStaffStrength.cmbSelectOffice.selectedIndex=0;
                    document.frmStaffStrength.cmbOfficeType.selectedIndex=0;
                    document.frmStaffStrength.txtOfficeAddressFrom.value="";
                    document.frmStaffStrength.cmbFromOfficeId.selectedIndex=0;
                    document.frmStaffStrength.txtDoD.value="";
                    document.frmStaffStrength.txtDoD.focus();
                    var noofsanction=document.getElementById("noofsantionpost");
                    noofsanction.style.display="none";
    
                }
            }
     }
}

function officeid()
{

    var Office_Id=document.frmStaffStrength.txtdiversionfromoffice.value;
    var tooffice=document.frmStaffStrength.txtdiversiontooffice.value;
    var cmbPostRank=document.frmStaffStrength.cmbPostRank.value;
    startwaiting(document.frmStaffStrength);
    url="../../../../../UpdateDiversionServlet.con?command=OfficeStatus&OfficeId="+Office_Id+"&ToOffice="+tooffice+"&PostRank="+cmbPostRank;
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       OfficeStatusDetails(req);             
    }
    req.send(null); 
}


function OfficeStatusDetails(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            stopwaiting(document.frmStaffStrength);
               var response=req.responseXML.getElementsByTagName("response")[0]; 
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="success")
               {
                var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                       if(recordstatus=="FR")
                       {
                        //alert("Record is Freezed");
                        //document.frmStaffStrength.cmbSubmit.disabled=true;
                       }
                       else
                       {
                            if((recordstatus=="CR") || (recordstatus=="MD"))
                            {
                                alert("This Record has been Created Now,Choose Edit Form or Validate Form");
                               // document.frmStaffStrength.txtOffice_Id.value="";
                                //document.frmStaffStrength.txt_ExtOffice_Name.value="";
                                //document.frmStaffStrength.txtOffice_Id1.value="";
                               // document.frmStaffStrength.txtOffice_Id.focus();
                                window.open('','_parent',''); 
                                window.close(); 
                            }
                            document.frmStaffStrength.cmbSubmit.disabled=false;
                       }
               
               
               }
               else
               {
               
               }
        }
    }


}

var j=0;
function callServer(command,param)
{
        var officeto=document.frmStaffStrength.txtdiversiontooffice.value;
        var noofpostto=document.frmStaffStrength.txtPostDiverted.value;
        var remarks=document.frmStaffStrength.txtRemarks.value;
        
        //Creating cells and Adding Values in Grid
        
        if(command=="Add")
        {
                //var slno=slnocheck();
                //alert(slno);
                //var slno1=++slno;
                var flag=nullcheck();
                if(flag==true)
                {
                var tbodyto=document.getElementById("tblListto");
                var mycurrent_row=document.createElement("TR");
                mycurrent_row.id=j
                var cell1=document.createElement("TD");
                var cell2=document.createElement("TD");    
                var cell3=document.createElement("TD");
                var cell4=document.createElement("TD");
                var anc=document.createElement("A");
            
                var url="javascript:loadValuesFromTable('" + j + "')";              
                anc.href=url;
                var txtedit=document.createTextNode("Edit");
                anc.appendChild(txtedit);
                cell1.appendChild(anc);
                mycurrent_row.appendChild(cell1);
                
                var hidden1=document.createElement("input");
                hidden1.type="hidden";
                hidden1.name="slno1";
                hidden1.value=j;
                cell1.appendChild(hidden1);
                mycurrent_row.appendChild(cell1);
        
                var officeto=document.createTextNode(officeto);
                cell2.appendChild(officeto);
            
                var hidden2=document.createElement("input");
                hidden2.type="hidden";
                hidden2.name="officeto";
                hidden2.value=officeto;
                cell2.appendChild(hidden2);
                mycurrent_row.appendChild(cell2);
                
                var noofpostto1=document.createTextNode(noofpostto);
                cell3.appendChild(noofpostto1);
                
                var hidden3=document.createElement("input");
                hidden3.type="hidden";
                hidden3.name="noofpostto";
                hidden3.value=noofpostto;
                cell3.appendChild(hidden3);
                mycurrent_row.appendChild(cell3);
                
                var txtremark=document.createTextNode(remarks);
                cell4.appendChild(txtremark);
                
                var hidden4=document.createElement("input");
                hidden4.type="hidden";
                hidden4.name="remark";
                hidden4.value=remarks;
                cell4.appendChild(hidden4);
                mycurrent_row.appendChild(cell4);
                j++;
                tbodyto.appendChild(mycurrent_row);
                
                document.frmStaffStrength.txtdiversiontooffice.value="";
                document.frmStaffStrength.txtPostDiverted.value="";
                document.frmStaffStrength.txtRemarks.value="";
                document.frmStaffStrength.cmbToOfficeId.selectedIndex=0;
                document.frmStaffStrength.cmbSelectOffice1.selectedIndex=0;
                document.frmStaffStrength.cmbOfficeType1.selectedIndex=0;
            }
         }
         else if(command=="Update")
         {
            
            var trow=document.getElementById(""+currentlyEditing);
            var cells=trow.cells;
            cells.item(1).firstChild.nodeValue=document.frmStaffStrength.txtdiversiontooffice.value;
            cells.item(1).lastChild.value=document.frmStaffStrength.txtdiversiontooffice.value;
            
            cells.item(2).firstChild.nodeValue=document.frmStaffStrength.txtPostDiverted.value;
            cells.item(2).lastChild.value=document.frmStaffStrength.txtPostDiverted.value;
            
            cells.item(3).firstChild.nodeValue=document.frmStaffStrength.txtRemarks.value;
            cells.item(3).lastChild.value=document.frmStaffStrength.txtRemarks.value;
            clearAll();
            
         }
         else if(command=="Delete")
         {
                var trow=currentlyEditing;
                var tbodyto=document.getElementById("Existingto"); 
                var r=document.getElementById(trow);    
                var ri=r.rowIndex;               
                tbodyto.deleteRow(ri);
                clearAll();
                
         }
         
         
}



function loadValuesFromTable(rid)
{

    var r=document.getElementById(rid);
    var rcells=r.cells;
    currentlyEditing=rcells.item(0).lastChild.value;
    
    document.frmStaffStrength.txtdiversiontooffice.value=rcells.item(1).firstChild.nodeValue;
    document.frmStaffStrength.txtPostDiverted.value=rcells.item(2).lastChild.value;
    document.frmStaffStrength.txtRemarks.value=rcells.item(3).lastChild.value;
    document.frmStaffStrength.cmdAdd.style.display="none";
    document.frmStaffStrength.cmdUpdate.style.display="block";
    document.frmStaffStrength.cmdDelete.style.display="block";

}


function loadofficeaddress(id)
{
    //alert(id);
    var finyear=document.getElementById("cmbFinancialYear").value;
    //alert(finyear);
    if(id=="" || id==null)
    {
        
        //alert("Enter An Office..Then Click choose..)");
      //  document.frmStaffStrength.txtOffice_Id.focus();
    }
    else
    {
               //startwaiting(document.frmStaffStrength) ;
                var url="../../../../../UpdateDiversionServlet.con?command=nil&ID="+id+"&financialyear="+finyear;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {                
                   LoadOfficeAddressDetails(req);             
                }
                req.send(null); 
    
    }
}

//Server Response Handling
function LoadOfficeAddressDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                //stopwaiting(document.frmStaffStrength) ;
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                  
                   var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                   var add1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                   var add2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                   var cityname=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                   var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                  
                   //document.frmStaffStrength.txtOfficeNameFrom.value="";
                   //document.frmStaffStrength.txt_ExtOffice_Address1.value="";
                   //document.frmStaffStrength.txt_ExtOffice_Address2.value="";
                   //document.frmStaffStrength.txt_ExtOffice_City.value="";
                   //document.frmStaffStrength.cmb_ExtDistrict.value="";
                   var flowstatus=response.getElementsByTagName("pflowstatus")[0].firstChild.nodeValue;
                   //alert(flowstatus);
                  // if(flowstatus=="FR")
                  // {
                   //alert("The sanction strength details for this office has already freezed!");
                  // }
                   //else{
                    if(name!="null")
                    {
                        document.frmStaffStrength.txtOfficeAddressFrom.value=name+"\n";
                    }
                   
                    if(add1!="null")
                    {
                        document.frmStaffStrength.txtOfficeAddressFrom.value=document.frmStaffStrength.txtOfficeAddressFrom.value+add1+"\n";
                    }
                    if(add2!="null")
                    {
                        document.frmStaffStrength.txtOfficeAddressFrom.value=document.frmStaffStrength.txtOfficeAddressFrom.value+add2+"\n";
                    }
                    if(cityname!="null")
                    {
                        document.frmStaffStrength.txtOfficeAddressFrom.value=document.frmStaffStrength.txtOfficeAddressFrom.value+cityname+"\n";
                    }
                    if(district!="null")
                    {
                        if(district!=0)
                        {
                            document.frmStaffStrength.txtOfficeAddressFrom.value=document.frmStaffStrength.txtOfficeAddressFrom.value+district;
                      }
                      //alert(" in district");
                    }
                  
                    //}                   
                }
                else
                {
                        alert("Invalid Office Id");
                       //document.frmStaffStrength.txtOfficeNameFrom.value="";
                      document.frmStaffStrength.txtOffice_Id.value="";
                   
                    
                    
                } 
          }
    }       
}

function loadofficeaddressto(id)
{
    //alert(id);
    var finyear=document.getElementById("cmbFinancialYear").value;
    //alert(finyear);
    if(id=="" || id==null)
    {
        
        //alert("Enter An Office..Then Click choose..)");
      //  document.frmStaffStrength.txtOffice_Id.focus();
    }
    else
    {
               //startwaiting(document.frmStaffStrength) ;
                var url="../../../../../UpdateDiversionServlet.con?command=nil&ID="+id+"&financialyear="+finyear;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true);         
                req.onreadystatechange=function()
                {                
                   LoadOfficeAddressDetailsTo(req);             
                }
                req.send(null); 
    
    }
}

//Server Response Handling
function LoadOfficeAddressDetailsTo(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                //stopwaiting(document.frmStaffStrength) ;
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                  
                   var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                   var add1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                   var add2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                   var cityname=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                   var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                   //document.frmStaffStrength.txtOfficeNameTo.value="";
                   //document.frmStaffStrength.txt_ExtOffice_Address1.value="";
                   //document.frmStaffStrength.txt_ExtOffice_Address2.value="";
                   //document.frmStaffStrength.txt_ExtOffice_City.value="";
                   //document.frmStaffStrength.cmb_ExtDistrict.value="";
                   //alert(name);
                    var flowstatus=response.getElementsByTagName("pflowstatus")[0].firstChild.nodeValue;
                   //alert(flowstatus+"to office");
                   //if(flowstatus=="FR")
                   //{
                   //alert("The sanction strength details for this office has already freezed!");
                   //}
                   //else
                   //{
                    if(name!="null")
                    {
                        document.frmStaffStrength.txtOfficeAddressTo.value=name+"\n";
                    }
                   
                    if(add1!="null")
                    {
                        document.frmStaffStrength.txtOfficeAddressTo.value=document.frmStaffStrength.txtOfficeAddressTo.value+add1+"\n";
                    }
                    if(add2!="null")
                    {
                        document.frmStaffStrength.txtOfficeAddressTo.value=document.frmStaffStrength.txtOfficeAddressTo.value+add2+"\n";
                    }
                    if(cityname!="null")
                    {
                        document.frmStaffStrength.txtOfficeAddressTo.value=document.frmStaffStrength.txtOfficeAddressTo.value+cityname+"\n";
                    }
                    if(district!="null")
                    {
                        if(district!=0)
                        {
                      document.frmStaffStrength.txtOfficeAddressTo.value=document.frmStaffStrength.txtOfficeAddressTo.value+district;
                      }
                      //alert(" in district");
                    }
                  // }
                                       
                }
                else
                {
                        alert("Invalid Office Id");
                       //document.frmStaffStrength.txtOfficeNameFrom.value="";
                      document.frmStaffStrength.txtOffice_Id.value="";
                   
                    
                    
                } 
          }
    }       
}
