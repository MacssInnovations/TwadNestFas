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
                var url="../../../../../CreateExtensionDiversion.con?command=nil&ID="+id;
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
                    
                    //OfficeLevel();
                    
                                       
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
    var url="../../../../../EditServletOfficeLevelStaffStrength.con?OfficeId="+officeid;
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
                    alert("Invalid Office Id");
                    document.frmStaffStrength.txtOfficeAddressFrom.value="";
                    document.frmStaffStrength.txtOfficeAddressTo.value="";
                    
                    
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
function PostRank(postrid)
{
    var officeid=document.frmStaffStrength.txtdiversionfromoffice.value;
    //var finyear=document.frmStaffStrength.cmbFinancialYear.value;
    //alert(officeid);
    startwaiting(document.frmStaffStrength) ;
    var url="../../../../../EditDiversionServlet.con?command=PostRank&OfficeId="+officeid;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       PostRankDetails(req,postrid);             
    }
    req.send(null); 
}


function PostRankDetails(req,postrid)
{

    if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.frmStaffStrength) ;
                var rankid=0;
                var employmentstatusid="";
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                var postrank=document.getElementById("cmbPostRank");
                var employmentstatus=document.getElementById("cmbPostCategory");
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
                    var value=response.getElementsByTagName("options");
                    
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                         rankid=tmpoption.getElementsByTagName("rankid")[0].firstChild.nodeValue;
                       // alert(rankid);
                        var rankname=tmpoption.getElementsByTagName("rankname")[0].firstChild.nodeValue;
                         //employmentstatusid=tmpoption.getElementsByTagName("employmentstatus")[0].firstChild.nodeValue;
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
                    }
                    document.frmStaffStrength.cmbPostCategory.value=employmentstatusid;*/
                    document.frmStaffStrength.cmbPostRank.value=postrid;
                    //alert(rankid);
                    //SanctionPost(rankid);
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
                    alert("Invalid Office Id");
                    document.frmStaffStrength.txtdiversionfromoffice.value="";
                    document.frmStaffStrength.txtOfficeAddressFrom.value="";
                    document.frmStaffStrength.txtOfficeAddressTo.value="";
                    document.frmStaffStrength.txtdiversiontooffice.value="";
                    document.frmStaffStrength.noofsanction.value="";
                    document.frmStaffStrength.divertedtopost.value="";
                    //document.frmStaffStrength.divertedfrompost.value="";
                    document.frmStaffStrength.txtPostDiverted.value="";
                    document.frmStaffStrength.txtDoD.value="";
                    document.frmStaffStrength.txtRemarks.value="";
                }
                
          }
    }
    

}


//Calling Server for Sanction post

function SanctionPost(p)
{
    var officeid=document.frmStaffStrength.txtdiversionfromoffice.value;
   // var finyear=document.frmStaffStrength.cmbFinancialYear.value;
    //alert(officeid);
    //var postrank=document.frmStaffStrength.cmbPostRank.value;
    //alert(p);
    var date=document.frmStaffStrength.txtDoD.value;
    var finyear;
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
    }

    startwaiting(document.frmStaffStrength) ;
    var url="../../../../../CreateDiversionExpiry.con?command=SanctionPost&OfficeId="+officeid+"&PostRank="+p+"&FinYear="+finyear;
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
                    document.frmStaffStrength.txtdiversiontooffice.value="";
                    document.frmStaffStrength.txtOfficeAddressTo.value="";
                    document.frmStaffStrength.txtPostDiverted.value="";
                    document.frmStaffStrength.noofsanction.value="";
                    document.frmStaffStrength.divertedtopost.value="";
                    document.frmStaffStrength.txtRemarks.value="";
                    document.frmStaffStrength.txtDoD.focus();
                }
            }
     }
}


function officedetails()
{
    var orderid=document.frmStaffStrength.txtOrderId.value;
    //var finyear=document.frmStaffStrength.cmbFinancialYear.value;
    //alert(orderid);
   // startwaiting(document.frmStaffStrength) ;
    var url="../../../../../CreateDiversionExpiry.con?command=OfficeDetails&OrderId="+orderid;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       OfficeDetails(req);             
    }
    req.send(null); 
    
}


function OfficeDetails(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {                
               // stopwaiting(document.frmStaffStrength) ;
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                if(flag=="success")
                {
                    var diversionfrom=response.getElementsByTagName("diversionfrom")[0].firstChild.nodeValue;
                    var diversionto=response.getElementsByTagName("diversionto")[0].firstChild.nodeValue;
                    var noofpost=response.getElementsByTagName("noofpost")[0].firstChild.nodeValue;
                    var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                   // var finyear=response.getElementsByTagName("finyear")[0].firstChild.nodeValue;
                    var postrid=response.getElementsByTagName("postrankid")[0].firstChild.nodeValue;
                    var divertedeffective=response.getElementsByTagName("dateeffective")[0].firstChild.nodeValue;
                    var dateperiod=response.getElementsByTagName("dateperiod")[0].firstChild.nodeValue;
                    document.frmStaffStrength.txtdiversionfromoffice.value="";
                    document.frmStaffStrength.txtdiversiontooffice.value="";
                    document.frmStaffStrength.txtHdiversionfromoffice.value="";
                    document.frmStaffStrength.txtHdiversiontooffice.value="";
                    document.frmStaffStrength.txtPostDiverted.value="";
                    document.frmStaffStrength.txtDoD.value="";
                    document.frmStaffStrength.txtRemarks.value="";
                    document.frmStaffStrength.txtDEDate.value="";
                    document.frmStaffStrength.txtDPDate.value="";
                    
                        document.frmStaffStrength.txtdiversionfromoffice.value=diversionfrom;
                        document.frmStaffStrength.txtHdiversionfromoffice.value=diversionfrom;
                        document.frmStaffStrength.txtdiversiontooffice.value=diversionto;
                        document.frmStaffStrength.txtHdiversiontooffice.value=diversionto;
                        document.frmStaffStrength.txtPostDiverted.value=noofpost;
                        //document.frmStaffStrength.cmbFinancialYear.value=finyear;
                    if(date!="Not Specified")
                    {
                        document.frmStaffStrength.txtDoD.value=date;
                    }
                    if(divertedeffective!="Not Specified")
                    {
                        document.frmStaffStrength.txtDEDate.value=divertedeffective;
                    }
                    if(dateperiod!="Not Specified")
                    {
                        document.frmStaffStrength.txtDPDate.value=dateperiod;
                    }
                    if(remarks!="null")
                    {
                        document.frmStaffStrength.txtRemarks.value=remarks;
                    }
                    if(recordstatus=="FR")
                    {
                       // alert("Record is Freezed");
                        //document.frmStaffStrength.cmbSubmit.disabled=true;
                    }
                    else
                    {
                        document.frmStaffStrength.cmbSubmit.disabled=false;
                    }
                    //alert(postrid);
                    PostRank(postrid);
                    //SanctionPost(postrid);
                    loadofficeaddress(diversionfrom);
                    loadofficeaddressto(diversionto);
                    diversiondetails();
                    SlNo();
                    statuscheck();
                    document.frmStaffStrength.cmbOrderId.disabled=true;
                }
                
                else
                {
                    alert("Invalid OrderId");
                    document.frmStaffStrength.txtOrderId.value="";
                    document.frmStaffStrength.txtOrderId.focus();
                    document.frmStaffStrength.cmbOrderId.disabled=false;
                    document.frmStaffStrength.txtdiversionfromoffice.value="";
                    document.frmStaffStrength.txtdiversiontooffice.value="";
                    document.frmStaffStrength.txtHdiversionfromoffice.value="";
                    document.frmStaffStrength.txtHdiversiontooffice.value="";
                    document.frmStaffStrength.txtPostDiverted.value="";
                    document.frmStaffStrength.txtDoD.value="";
                    document.frmStaffStrength.txtRemarks.value="";
                    document.frmStaffStrength.cmbPostRank.selectedIndex=0;
                    document.frmStaffStrength.txtOfficeAddressFrom.value="";
                    document.frmStaffStrength.txtOfficeAddressTo.value="";
                    document.frmStaffStrength.txtDEDate.value="";
                    document.frmStaffStrength.txtDPDate.value="";
                    document.frmStaffStrength.txtExpOrderId.value="";
                    document.frmStaffStrength.txtExpOrderId1.value="";
                    
                }
          }
    }
    
}


function loadofficeaddress(id)
{
    //alert(id);
    if(id=="" || id==null)
    {
        
        //alert("Enter An Office..Then Click choose..)");
      //  document.frmStaffStrength.txtOffice_Id.focus();
    }
    else
    {
               //startwaiting(document.frmStaffStrength) ;
                var url="../../../../../UpdateDiversionServlet.con?command=nil&ID="+id;
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
                   
                                       
                }
                else
                {
                        alert("Invalid Office Id");
                       //document.frmStaffStrength.txtOfficeNameFrom.value="";
                       document.frmStaffStrength.txtOffice_Id.value="";
                       document.frmStaffStrength.txtOfficeAddressFrom.value="";
                   
                    
                    
                } 
          }
    }       
}

function loadofficeaddressto(id)
{
    //alert(id);
    if(id=="" || id==null)
    {
        
        //alert("Enter An Office..Then Click choose..)");
      //  document.frmStaffStrength.txtOffice_Id.focus();
    }
    else
    {
               //startwaiting(document.frmStaffStrength) ;
                var url="../../../../../UpdateDiversionServlet.con?command=nil&ID="+id;
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
                   
                                       
                }
                else
                {
                        alert("Invalid Office Id");
                       //document.frmStaffStrength.txtOfficeNameFrom.value="";
                      document.frmStaffStrength.txtOffice_Id.value="";
                      document.frmStaffStrength.txtOfficeAddressTo.value="";
                   
                    
                    
                } 
          }
    }       
}

function orderid()
{
    
    document.frmStaffStrength.txtOrderId.value=document.frmStaffStrength.cmbOrderId.value;
    officedetails();
}


//Calling Server for PostRank Loading Values
function PostRank1()
{
    var officeid=document.frmStaffStrength.txtdiversionfromoffice.value;
    //var finyear=document.frmStaffStrength.cmbFinancialYear.value;
    //alert(officeid);
    startwaiting(document.frmStaffStrength) ;
    var url="../../../../../CreateDiversionExpiry.con?command=PostRank&OfficeId="+officeid;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       PostRankDetails1(req);             
    }
    req.send(null); 
}


function PostRankDetails1(req)
{

    if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.frmStaffStrength) ;
                var rankid=0;
                var employmentstatusid="";
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                var postrank=document.getElementById("cmbPostRank");
                var employmentstatus=document.getElementById("cmbPostCategory");
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
                    var value=response.getElementsByTagName("options");
                    
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                         rankid=tmpoption.getElementsByTagName("rankid")[0].firstChild.nodeValue;
                       // alert(rankid);
                        var rankname=tmpoption.getElementsByTagName("rankname")[0].firstChild.nodeValue;
                         //employmentstatusid=tmpoption.getElementsByTagName("employmentstatus")[0].firstChild.nodeValue;
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
                    }
                    document.frmStaffStrength.cmbPostCategory.value=employmentstatusid;*/
                    //document.frmStaffStrength.cmbPostRank.value=rankid;
                    //alert(rankid);
                    SanctionPost(rankid);
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
                    document.frmStaffStrength.txtdiversiontooffice.value="";
                    //document.frmStaffStrength.noofsanction.value="";
                    document.frmStaffStrength.divertedtopost.value="";
                    //document.frmStaffStrength.divertedfrompost.value="";
                    document.frmStaffStrength.txtPostDiverted.value="";
                    document.frmStaffStrength.txtDoD.value="";
                    document.frmStaffStrength.txtRemarks.value="";
                }
                
          }
    }
    

}

function checktooff()
{

    if((document.frmStaffStrength.txtdiversiontooffice.value==document.frmStaffStrength.txtdiversionfromoffice.value))
    {
        alert("Diversion Cannot be effected in the Same Office");
        document.frmStaffStrength.txtdiversiontooffice.value="";
        document.frmStaffStrength.txtHdiversiontooffice.value="";
       // document.frmStaffStrength.txtOfficeNameTo.value="";
        document.frmStaffStrength.txtOfficeAddressTo.value="";
        document.frmStaffStrength.txtdiversiontooffice.focus();
        return false;
    }
    else
    {
        return true;
    }
}

function ordercheck()
{

    if((document.frmStaffStrength.txtOrderId.value=="") ||(document.frmStaffStrength.txtOrderId.value.length<=0))
    {
        alert("Enter Order Id");
        document.frmStaffStrength.txtOrderId.focus();
        return false;
    }
    return true;

}

function diversiondetails()
{

    var orderid=document.frmStaffStrength.txtOrderId.value;
    var url="../../../../../CreateDiversionExpiry.con?command=DiversionDetails&OrderId="+orderid;
   // alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       DiversionDetailsResponse(req);             
    }
    req.send(null); 

}


function DiversionDetailsResponse(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {                
               // stopwaiting(document.frmStaffStrength) ;
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                    
                    var value=response.getElementsByTagName("options");
                    
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var slno=tmpoption.getElementsByTagName("slno")[0].firstChild.nodeValue;
                        var exorderdate=tmpoption.getElementsByTagName("extorderdate")[0].firstChild.nodeValue;
                        var exupto=tmpoption.getElementsByTagName("extupto")[0].firstChild.nodeValue;
                        var remarks=tmpoption.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                        var tbody=document.getElementById("tblList");
                        var mycurrent_row=document.createElement("TR");
                        var txtExRemarks="";
                        if(remarks!="null")
                        {
                            txtExRemarks=remarks;
                        }
                        var cell1=document.createElement("TD");
                        var cell2=document.createElement("TD");
                        var cell3=document.createElement("TD");
                        var cell4=document.createElement("TD");
                        
                        var txtslno=document.createTextNode(slno);
                        cell1.appendChild(txtslno);
                        var hidden1=document.createElement("input");
                        hidden1.type="hidden";
                        hidden1.name="txtslno";
                        hidden1.value=slno;
                        cell1.appendChild(hidden1);
                        mycurrent_row.appendChild(cell1);
                                                
                        
                        var txtexorderdate=document.createTextNode(exorderdate);
                        cell2.appendChild(txtexorderdate);
                        var hidden2=document.createElement("input");
                        hidden2.name="txtexorderdate";
                        hidden2.type="hidden";
                        hidden2.value=txtexorderdate;
                        cell2.appendChild(hidden2);
                        
                        mycurrent_row.appendChild(cell2);
                        
                        var txtexupto=document.createTextNode(exupto);
                        cell3.appendChild(txtexupto);
                        var hidden3=document.createElement("input");
                        hidden3.type="hidden";
                        hidden3.name="txtexupto";
                        hidden3.value=exupto;
                        cell3.appendChild(hidden3);
                        mycurrent_row.appendChild(cell3);
                        
                        var txtremarks=document.createTextNode(txtExRemarks);
                        cell4.appendChild(txtremarks);
                        var hidden4=document.createElement("input");
                        hidden4.type="hidden";
                        hidden4.name="txtremarks";
                        hidden4.value=txtExRemarks;
                        cell4.appendChild(hidden4);
                        mycurrent_row.appendChild(cell4);
                        
                        tbody.appendChild(mycurrent_row);
                        
                    }
    
                }
                else
                {
                    //alert("No Existing Extensions available for this Diversion");
                    
                
                }
                
         }
    }
    

}


function SlNo()
{
    var orderid=document.frmStaffStrength.txtOrderId.value;
    var url="../../../../../CreateDiversionExpiry.con?command=SlNO&OrderId="+orderid;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       SlNoResponse(req);             
    }
    req.send(null); 

}


function SlNoResponse(req)
{

    if(req.readyState==4)
    {
          if(req.status==200)
          {                
               // stopwaiting(document.frmStaffStrength) ;
               
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                //alert(req.responseTEXT);
                document.frmStaffStrength.txtExpOrderId.value="";
                if(flag=="success")
                {
                    
                    var slno=response.getElementsByTagName("slno")[0].firstChild.nodeValue;
                    
                    document.frmStaffStrength.txtExpOrderId.value=slno;
                    document.frmStaffStrength.txtExpOrderId1.value=slno;
                }
                else
                {
                
                }
        }
    }

}

function statuscheck()
{

    var orderid=document.frmStaffStrength.txtOrderId.value;
    var url="../../../../../CreateDiversionExpiry.con?command=Status&OrderId="+orderid;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {                
       StatusResponse(req);             
    }
    req.send(null);

}


function StatusResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var response=req.responseXML.getElementsByTagName("response")[0];
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
                var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                if((recordstatus=="CR") || (recordstatus=="MD"))
                {
                    alert("This Record has been Created now.so choose Validate Form");
                    window.open('','_parent','');                
                    window.close();
                }
            }
            else
            {
            
            }
        
        }
    }

}





function check2()
{
    var from=document.frmStaffStrength.txtDEDate.value;
    var to=document.frmStaffStrength.txtExOrderDate.value;
    //alert(check21(from,to));
    return check21(from,to);
    
    
}

function check21(c,todate)
{
	
   // document.workdemand.elements["txt_from"+c].value=""
     //fday=document.workdemand.elements["txt_from"+c].value.split("/");
     /*var tbody=document.getElementById("tblList");
     var rows=tbody.rows;
     var todate=rows[todate].cells[3].firstChild.nodeValue;*/
     //alert("todate"+todate);
     var todate=document.frmStaffStrength.txtExOrderDate.value;
     var fday=document.frmStaffStrength.txtDEDate.value.split("/");
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
	
       if (today<frmday)
	     {
		   alert ("Diversion Expiry Date Should be Greater Than Diversion Effective Date");
                   document.frmStaffStrength.txtExOrderDate.value="";
                   document.frmStaffStrength.txtExOrderDate.focus();
                   return false;
             }
             else
             {
                   return true;
             }
    return true;
}


function check3()
{
    var to=document.frmStaffStrength.txtExOrderDate.value;
    var from=document.frmStaffStrength.txtDPDate.value;
    return check31(from,to);
    
}

function check31(c,todate)
{
	
   // document.workdemand.elements["txt_from"+c].value=""
     //fday=document.workdemand.elements["txt_from"+c].value.split("/");
     /*var tbody=document.getElementById("tblList");
     var rows=tbody.rows;
     var todate=rows[todate].cells[3].firstChild.nodeValue;*/
     //alert("todate"+todate);
     
     
     var todate=document.frmStaffStrength.txtExOrderDate.value;
     var fday=document.frmStaffStrength.txtDPDate.value.split("/");
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
	
       if (today<frmday)
	     {
		   alert ("Diversion Expiry Date Should be Greater Than Diversion Period Upto");
                   document.frmStaffStrength.txtExOrderDate.value="";
                   document.frmStaffStrength.txtExOrderDate.focus();
                   return false;
             }
             else
             {
             return true;
             }
    return true;
}


