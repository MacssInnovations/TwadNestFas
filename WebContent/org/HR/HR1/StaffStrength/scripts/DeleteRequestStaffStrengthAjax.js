var sergrp;
var postrank;
var postcat;

var sergrpto;
var postrankto;
var postcatto;
var noofpostto;
var noofpostfrom;


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
    //alert("here");
    if(id=="" || id==null)
    {
        //alert("Enter An Office..Then Click choose..)");
      //  document.frmOffice.txtOffice_Id.focus();
    }
    else
    {
    
               //alert(" in loadOffice"+id);
               startwaiting(document.frmOffice) ;
                var url="../../../../../DeleteRequestStaffStrength.con?command=nil&ID="+id;
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
                stopwaiting(document.frmOffice) ;
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                  
                   var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                   var add1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                   var add2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                   var cityname=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                   var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                   document.frmOffice.txt_ExtOffice_Name.value="";
                   //document.frmOffice.txt_ExtOffice_Address1.value="";
                   //document.frmOffice.txt_ExtOffice_Address2.value="";
                   //document.frmOffice.txt_ExtOffice_City.value="";
                   //document.frmOffice.cmb_ExtDistrict.value="";
                    if(name!="null")
                    {
                        document.frmOffice.txt_ExtOffice_Name.value=name;
                    }
                   
                    if(add1!="null")
                    {
                        //document.frmOffice.txt_ExtOffice_Address1.value=add1;
                    }
                    if(add2!="null")
                    {
                        //document.frmOffice.txt_ExtOffice_Address2.value=add2;
                    }
                    if(cityname!="null")
                    {
                        //document.frmOffice.txt_ExtOffice_City.value=cityname;
                    }
                    if(district!="null")
                    {
                     // document.frmOffice.cmb_ExtDistrict.value=district;
                      //alert(" in district");
                    }
                   // document.frmOffice.cmb_ExtDistrict.disabled=true;
                    //document.frmOffice.txtOffice_Id.disabled=true;
                    OfficeLevel();
                                       
                }
                else
                {
                        alert("Invalid Office Id");
                       document.frmOffice.txt_ExtOffice_Name.value="";
                      // document.frmOffice.txt_ExtOffice_Address1.value="";
                       //document.frmOffice.txt_ExtOffice_Address2.value="";
                      // document.frmOffice.txt_ExtOffice_City.value="";
                      // document.frmOffice.cmb_ExtDistrict.value="";
                       document.frmOffice.txtOffice_Id.value="";
                   
                    //var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    //alert("Error Occured : \n" + mess); 
                    
                } 
          }
    }       
}




function OfficeLevel()
{
    //alert("inside");
    var Office_Id=document.frmOffice.txtOffice_Id.value;
    startwaiting(document.frmOffice) ;
    url="../../../../../DeleteRequestStaffStrength.con?command=OfficeLevel&OfficeId="+Office_Id;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);         
    req.onreadystatechange=function()
    {
        LoadOfficeLevel(req);
    }
    req.send(null);
                
}

//Server Response Handling
function LoadOfficeLevel(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {     
                stopwaiting(document.frmOffice) ;
                var cmbFinancialYear=document.getElementById("cmbFinancialYear");
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="failure")
                {
                    alert("There is No Sanctioned Strength Details");
                    var tbody=document.getElementById("tblList");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                        tbody.deleteRow(0);
                    }
                    document.frmOffice.totalgrid.value="";
                    document.frmOffice.cmbFinancialYear.selectedIndex=0;
                    document.frmOffice.txtRemarks.value="";
                    document.frmOffice.txt_ExtOffice_Name.value="";
                    document.frmOffice.txtOffice_Id.value="";
                    
                }
                else
                {
                    var value=response.getElementsByTagName("options");
                    /*cmbFinancialYear.innerHTML="";
                    var option=document.createElement("OPTION");
                    //option.text="--Select TemplateId--";
                    //option.value=0;
                    try
                    {
                           cmbFinancialYear.add(option);
                    }catch(errorobject)
                    { 
                             cmbFinancialYear.add(option,null);
                    }*/
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var finyear=tmpoption.getElementsByTagName("finyear")[0].firstChild.nodeValue;
                        //var date=tmpoption.getElementsByTagName("date")[0].firstChild.nodeValue;
                        var remarks=tmpoption.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                        document.frmOffice.cmbFinancialYear.value=finyear;
                        //document.frmOffice.cmbFinancialYear.selectedIndex=0;
                        var tbody=document.getElementById("tblList");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                            tbody.deleteRow(0);
                        }
                        document.frmOffice.txtRemarks.value="";
                        document.frmOffice.cmbSubmit.disabled=false;
                        //document.frmOffice.txtRequest_Date.value=date;
                        if(remarks!="null")
                        {
                            //document.frmOffice.txtRemarks.value=remarks;
                        }
                        //alert(id);
                        /*var option=document.createElement("OPTION");
                        option.text=finyear;
                        option.value=finyear;
                        try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } */
                        //document.frmOffice.txtRequestDate.focus();
                        
                    }
                    callServer('TableView',null);
                }
                
          }
          
    }
    
}

function callServer(command,param)
{

    var url="";
    if(command=="TableView")
    {
        var Office_Id=document.frmOffice.txtOffice_Id.value;
        var finyear=document.frmOffice.cmbFinancialYear.value;
        startwaiting(document.frmOffice) ;
        url="../../../../../DeleteRequestStaffStrength.con?command=TableView&TemplateId="+Office_Id+"&finyear="+finyear;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            TableViewResponse(req);
        }
        req.send(null);
   }
   else if(command=="ServiceGroup")
    {
        var ServiceGroup=document.frmOffice.cmbServiceGroup.value;
        startwaiting(document.frmOffice) ;
        url="../../../../../DeleteRequestStaffStrength.con?command=ServiceGroup&ServiceGroup="+ServiceGroup;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        ServiceGroupResponse(req);
        }
        req.send(null);
    
    }
    else if(command=="Add")
    {
        var flag=nullCheck();
        var flag1=checkIf();
        if(flag==true && flag1!=true)
        {
            
            var Sl_No=0;
            var ServiceGroup="";
            var ServiceGroup1="";
            var PostRank="";
            var PostRank1="";
            var PostCategory="";
            var PostCategory1="";
            var NoOfPost="";
            var Remarks="";
            
            //Getting Values from Form Fields
            //Sl_No=document.frmOffice.txtSl_No.value;
            ServiceGroup=document.frmOffice.cmbServiceGroup.value;
            ServiceGroup1=document.frmOffice.cmbServiceGroup.options[document.frmOffice.cmbServiceGroup.selectedIndex].text;
            PostRank=document.frmOffice.cmbPostRank.value;
            PostRank1=document.frmOffice.cmbPostRank.options[document.frmOffice.cmbPostRank.selectedIndex].text;
            //PostCategory=document.frmOffice.cmbPostCategory.value;
            //PostCategory1=document.frmOffice.cmbPostCategory.options[document.frmOffice.cmbPostCategory.selectedIndex].text;
            NoOfPost=document.frmOffice.txtNoPost.value;
            Remarks=document.frmOffice.Remarks.value;
            var slno=slnocheck();
            //alert("b4 inc:"+slno);
            var slno1=++slno;
            //alert("before:"+slno1);
            var i=0;
            //Creating cells and Adding Values in Grid
            var tbody=document.getElementById("tblList");
            var mycurrent_row=document.createElement("TR");
            mycurrent_row.id=slno1;
            var cell1=document.createElement("TD");
            var cell2=document.createElement("TD");
            
            var cell3=document.createElement("TD");
            var cell4=document.createElement("TD");
            var cell5=document.createElement("TD");
            var cell6=document.createElement("TD");
            var cell7=document.createElement("TD");
            
            var anc=document.createElement("A");       
            
            var url="javascript:loadValuesFromTable('" + slno1 + "')";              
            anc.href=url;
            var txtedit=document.createTextNode("Edit");
            anc.appendChild(txtedit);
            cell1.appendChild(anc);
            mycurrent_row.appendChild(cell1);
            
            var hidden1=document.createElement("input");
            hidden1.type="hidden";
            hidden1.name="slno1";
            hidden1.value=slno1;
            cell1.appendChild(hidden1);
            mycurrent_row.appendChild(cell1);
                       
            var cmbServiceGroup1=document.createTextNode(ServiceGroup1);
            cell2.appendChild(cmbServiceGroup1);
            
            var hidden2=document.createElement("input");
            hidden2.type="hidden";
            hidden2.name="servicegroupid";
            hidden2.value=ServiceGroup;
            cell2.appendChild(hidden2);
            mycurrent_row.appendChild(cell2);
            
            var cmbPostRank1=document.createTextNode(PostRank1);
            cell3.appendChild(cmbPostRank1);
            
            var hidden3=document.createElement("input");
            hidden3.type="hidden";
            hidden3.name="postrankid";
            hidden3.value=PostRank;
            cell3.appendChild(hidden3);
            mycurrent_row.appendChild(cell3);
            
            var cmbpostcategory1=document.createTextNode(PostCategory1);
            cell4.appendChild(cmbpostcategory1);
            
            var hidden4=document.createElement("input");
            hidden4.type="hidden";
            hidden4.name="employmentstatusid";
            hidden4.value=PostCategory;
            cell4.appendChild(hidden4);
            mycurrent_row.appendChild(cell4);
            
            var txtnoofpost=document.createTextNode(NoOfPost);
            cell5.appendChild(txtnoofpost);
             
            var hidden5=document.createElement("input");
            hidden5.type="hidden";
            hidden5.name="noofpost";
            hidden5.value=NoOfPost;
            cell5.appendChild(hidden5);
            mycurrent_row.appendChild(cell5);
            
            
            var txtremarks=document.createTextNode(Remarks);
            cell6.appendChild(txtremarks);
             
            var hidden6=document.createElement("input");
            hidden6.type="hidden";
            hidden6.name="remarks";
            hidden6.value=Remarks;
            cell6.appendChild(hidden6);
            mycurrent_row.appendChild(cell6);
            tbody.appendChild(mycurrent_row);
            
            
            //Clearing The Fields
            //document.frmOffice.txtSl_No.value=++Sl_No;
            document.frmOffice.cmbServiceGroup.selectedIndex=0;
            document.frmOffice.cmbPostRank.selectedIndex=0;
            document.frmOffice.cmbPostCategory.selectedIndex=0;
            document.frmOffice.txtNoPost.value="";
            document.frmOffice.Remarks.value="";
        }
        
    }
    else if(command=="Update")
    {
        //alert("Update");
        //alert("currnetlyEditing:"+currentlyEditing);
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
        if(sergrp!=document.frmOffice.cmbServiceGroup.options[document.frmOffice.cmbServiceGroup.selectedIndex].value || postrank!=document.frmOffice.cmbPostRank.options[document.frmOffice.cmbPostRank.selectedIndex].value || postcat!=document.frmOffice.cmbPostCategory.options[document.frmOffice.cmbPostCategory.selectedIndex].value)
        {
             var flag1=checkIf();
        }
        if(flag1!=true)
        {
        cells.item(1).firstChild.nodeValue=document.frmOffice.cmbServiceGroup.options[document.frmOffice.cmbServiceGroup.selectedIndex].text;
        cells.item(1).lastChild.value=document.frmOffice.cmbServiceGroup.value;
        
        cells.item(2).firstChild.nodeValue=document.frmOffice.cmbPostRank.options[document.frmOffice.cmbPostRank.selectedIndex].text;
        cells.item(2).lastChild.value=document.frmOffice.cmbPostRank.value;
        
        cells.item(3).firstChild.nodeValue=document.frmOffice.cmbPostCategory.options[document.frmOffice.cmbPostCategory.selectedIndex].text;
        cells.item(3).lastChild.value=document.frmOffice.cmbPostCategory.value;
        
        cells.item(4).firstChild.nodeValue=document.frmOffice.txtNoPost.value;
        cells.item(4).lastChild.value=document.frmOffice.txtNoPost.value;
        
        cells.item(5).firstChild.nodeValue=document.frmOffice.txtNoOfPostTo.value;
        cells.item(5).lastChild.value=document.frmOffice.txtNoOfPostTo.value;
        
        cells.item(6).firstChild.nodeValue=document.frmOffice.txtNoOfPostFrom.value;
        cells.item(6).lastChild.value=document.frmOffice.txtNoOfPostFrom.value;
        
        cells.item(7).firstChild.nodeValue=document.frmOffice.txtTotal.value;
        cells.item(7).lastChild.value=document.frmOffice.txtTotal.value;
        
        cells.item(8).firstChild.nodeValue=document.frmOffice.Remarks.value;
        cells.item(8).lastChild.value=document.frmOffice.Remarks.value;
        //var slno=slnocheck();
        //document.frmOffice.txtSl_No.value=++slno;
        clearAll();
        }
            
    }
    else if(command=="Delete")
    {
        var trow=currentlyEditing;
        var tbody=document.getElementById("Existing"); 
        var r=document.getElementById(trow);    
        var ri=r.rowIndex;               
        tbody.deleteRow(ri);
        clearAll();
        
    }

}


//Server Response for Loading TableView Values
function TableViewResponse(req)
    {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                    stopwaiting(document.frmOffice) ;
                    //Clearing The Table Before Loading
                    var tbody=document.getElementById("tblList");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                        tbody.deleteRow(0);
                    }
                
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                     if(flag=="failure")
                      {
                         alert("There is No Values in Grid");
                         document.frmOffice.txtRemarks.value="";
                         document.frmOffice.cmbFinancialYear.selectedIndex=0;
                         document.frmOffice.txt_ExtOffice_Name.value="";
                         document.frmOffice.txtOffice_Id.value="";
                         //document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("servicegroupid");
                          
                          var j=1;
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              
                                var ServiceGroupId=response.getElementsByTagName("servicegroupid")[i].firstChild.nodeValue;
                                var ServiceGroupName=response.getElementsByTagName("servicegroupname")[i].firstChild.nodeValue;
                                var PostRankId=response.getElementsByTagName("postrankid")[i].firstChild.nodeValue;
                                var PostRankName=response.getElementsByTagName("postrankname")[i].firstChild.nodeValue;
                                var NoOfPost=response.getElementsByTagName("noofpost")[i].firstChild.nodeValue;
                                var Remarks=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                                //var EmploymentStatusId=response.getElementsByTagName("employmentstatusid")[i].firstChild.nodeValue;
                                //var EmploymentStatusName=response.getElementsByTagName("employmentstatusname")[i].firstChild.nodeValue;
                                var DiversionTo=response.getElementsByTagName("diversionto")[i].firstChild.nodeValue;
                                var DiversionFrom=response.getElementsByTagName("diversionfrom")[i].firstChild.nodeValue;
                                var TotalStrength=response.getElementsByTagName("totalstrength")[i].firstChild.nodeValue;
                                var recordstatus=response.getElementsByTagName("recordstatus")[i].firstChild.nodeValue;
                                var remarksabove=response.getElementsByTagName("remarksabove")[0].firstChild.nodeValue; 
                                if(remarksabove!="null")
                                {
                                document.frmOffice.txtRemarks.value=remarksabove;
                                }
                                var tbody=document.getElementById("tblList");
                                var table=document.getElementById("Existing");
                                var mycurrent_row=document.createElement("TR");
                                 if(Remarks=="null")
                                {
                                Remarks="";
                                }
                                else
                                {
                                Remarks=Remarks;
                                }
                                if(DiversionTo!=0)
                                {
                                    DiversionTo=DiversionTo;
                                }
                                else
                                {
                                    DiversionTo="";
                                }
                                if(DiversionFrom!=0)
                                {
                                    DiversionFrom=DiversionFrom;
                                }
                                else
                                {
                                    DiversionFrom="";
                                }
                                if(TotalStrength!=0)
                                {
                                    TotalStrength=TotalStrength;
                                }
                                else
                                {
                                    TotalStrength="";
                                }
                                mycurrent_row.id=j;
                                var cell1=document.createElement("TD");
                                var cell2=document.createElement("TD");
                                var cell3=document.createElement("TD");
                                var cell4=document.createElement("TD");
                                var cell5=document.createElement("TD");
                                var cell6=document.createElement("TD");
                                var cell7=document.createElement("TD");
                                var cell8=document.createElement("TD");
                                var cell9=document.createElement("TD");
                                
                                var anc=document.createElement("A");       
                                    
                                var url="javascript:loadValuesFromTable('" + j + "')";              
                                //anc.href=url;
                                var txtedit=document.createTextNode("Edit");
                                anc.appendChild(txtedit);
                                cell1.appendChild(anc);
                                var hidden1=document.createElement("input");
                                hidden1.type="hidden";
                                hidden1.name=j;
                                hidden1.value=j;
                                cell1.appendChild(hidden1);
                                                    
                                mycurrent_row.appendChild(cell1);
                                
                                var txtservicegroupname=document.createTextNode(ServiceGroupName);
                                cell2.appendChild(txtservicegroupname);
                                var hidden2=document.createElement("input");
                                hidden2.type="hidden";
                                hidden2.name="servicegroupid";
                                hidden2.value=ServiceGroupId;
                                cell2.appendChild(hidden2);
        
                                mycurrent_row.appendChild(cell2);
                                
                                var txtpostrankname=document.createTextNode(PostRankName);
                                cell3.appendChild(txtpostrankname);
                                var hidden3=document.createElement("input");
                                hidden3.type="hidden";
                                hidden3.name="postrankid";
                                hidden3.value=PostRankId;
                                cell3.appendChild(hidden3);
                                mycurrent_row.appendChild(cell3);
                                
                                /*var txtemploymentstatusname=document.createTextNode(EmploymentStatusName);
                                cell4.appendChild(txtemploymentstatusname);
                                var hidden4=document.createElement("input");
                                hidden4.type="hidden";
                                hidden4.name="employmentstatusid";
                                hidden4.value=EmploymentStatusId;
                                cell4.appendChild(hidden4);
                                mycurrent_row.appendChild(cell4);*/
                                
                                
                                var txtnoofpost=document.createTextNode(NoOfPost);
                                cell4.appendChild(txtnoofpost);
                                var hidden4=document.createElement("input");
                                hidden4.type="hidden";
                                hidden4.name="noofpost";
                                hidden4.value=NoOfPost;
                                cell4.appendChild(hidden4);
                                mycurrent_row.appendChild(cell4);
                                
                                
                                var txtdiversionto=document.createTextNode(DiversionTo);
                                cell5.appendChild(txtdiversionto);
                                var hidden5=document.createElement("input");
                                hidden5.type="hidden";
                                hidden5.name="diversionto";
                                hidden5.value=DiversionTo;
                                cell5.appendChild(hidden5);
                                mycurrent_row.appendChild(cell5);
                                
                                var txtdiversionfrom=document.createTextNode(DiversionFrom);
                                cell6.appendChild(txtdiversionfrom);
                                var hidden6=document.createElement("input");
                                hidden6.type="hidden";
                                hidden6.name="diversionfrom";
                                hidden6.value=DiversionFrom;
                                cell6.appendChild(hidden6);
                                mycurrent_row.appendChild(cell6);
                                
                                var txttotalstrength=document.createTextNode(TotalStrength);
                                cell7.appendChild(txttotalstrength);
                                var hidden7=document.createElement("input");
                                hidden7.type="hidden";
                                hidden7.name="totalstrength";
                                hidden7.value=TotalStrength;
                                cell7.appendChild(hidden7);
                                mycurrent_row.appendChild(cell7);
                                
                                var txtremarks=document.createTextNode(Remarks);
                                cell8.appendChild(txtremarks);
                                var hidden8=document.createElement("input");
                                hidden8.type="hidden";
                                hidden8.name="remarks";
                                hidden8.value=Remarks;
                                cell8.appendChild(hidden8);
                                mycurrent_row.appendChild(cell8);
                                   j++;                             
                                tbody.appendChild(mycurrent_row);
                                              
                          }
                           if(recordstatus=="FR")
                            {
                                alert("Record is Freezed");
                            document.frmOffice.cmbSubmit.disabled=true;
                            }
                            else
                            {
                            document.frmOffice.cmbSubmit.disabled=false;
                            }                                          
                      }
                    
                }
            }
            //callServer1('TableViewTo',null);
   }
   
   
   function ServiceGroupResponse(req)
        {
             if(req.readyState==4)
                {
                    if(req.status==200)
                    {
                        stopwaiting(document.frmOffice) ;
                          var cmbPostRank=document.getElementById("cmbPostRank");
                          
                          var response=req.responseXML.getElementsByTagName("response")[0];
                          var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                          if(flag=="failure")
                          {
                             cmbPostRank.innerHTML="";
                            var option=document.createElement("OPTION");
                            option.text="--Select PostRank--";
                            option.value=0;
                                  try
                                        {
                                            cmbPostRank.add(option);
                                    }catch(errorobject)
                                    { 
                                             cmbPostRank.add(option,null);
                                    }
                             alert("There is no Values for this Service Group");
                          }
                          else
                          {
                            var value=response.getElementsByTagName("options");
                            cmbPostRank.innerHTML="";
                            var option=document.createElement("OPTION");
                            option.text="--Select PostRank--";
                            option.value=0;
                                  try
                                        {
                                            cmbPostRank.add(option);
                                    }catch(errorobject)
                                    { 
                                             cmbPostRank.add(option,null);
                                    }
                              
                                  for(var i=0;i<value.length;i++)
                                  {
                                      var tmpoption=value.item(i);
                                      var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                                      var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                                      var combovalue=id;
                                      var combotext=name;
                                      var option=document.createElement("OPTION");
                                      option.text=combotext;
                                      option.value=combovalue;
                                      try
                                        {
                                            cmbPostRank.add(option);
                                    }catch(errorobject)
                                    { 
                                             cmbPostRank.add(option,null);
                                    }
                                }
                                   var PostRank=response.getElementsByTagName("PostRank")[0].firstChild.nodeValue;
                          
                                  if(PostRank)
                                  {
                                      cmbPostRank.value=PostRank;
                                      
                                  }
                          }
                    }
                }   
        }   
        
        
        
// code for loading the values from the table to the input boxes
    // functionality for edit anchor
    function loadValuesFromTable(rid)
    {  
    //alert(rid);
      var r=document.getElementById(rid);      
      var rcells=r.cells;
      currentlyEditing=rcells.item(0).lastChild.value;
      document.frmOffice.cmbServiceGroup.value=rcells.item(1).lastChild.value;
      sergrp=rcells.item(1).lastChild.value;
      var ServiceGroup=rcells.item(1).lastChild.value;
      var PostRank=rcells.item(2).lastChild.value;
      postrank=rcells.item(2).lastChild.value;
      callServiceGroup(ServiceGroup,PostRank);  
      document.frmOffice.cmbPostCategory.value=rcells.item(3).lastChild.value;
      postcat=rcells.item(3).lastChild.value;
      document.frmOffice.txtNoPost.value=rcells.item(4).lastChild.value;
      document.frmOffice.txtNoOfPostTo.value=rcells.item(5).lastChild.value;
      document.frmOffice.txtNoOfPostFrom.value=rcells.item(6).lastChild.value;
      document.frmOffice.txtTotal.value=rcells.item(7).lastChild.value;
      document.frmOffice.Remarks.value=rcells.item(8).lastChild.value;
            
      document.frmOffice.cmdAdd.style.display="none";
      document.frmOffice.cmdUpdate.style.display="block";
      document.frmOffice.cmdDelete.style.display="block";
    }    
    
    
    function callServiceGroup(ServiceGroup,PostRank)
{
        startwaiting(document.frmOffice) ;
       url="../../../../../DeleteRequestStaffStrength.con?command=ServiceGroup&ServiceGroup="+ServiceGroup+"&PostRank="+PostRank;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        ServiceGroupResponse(req);
        }
        req.send(null);

}



/*function callServer1(command,param)
{

        if(command=="ServiceGroup")
        {
            var ServiceGroupTo=document.frmOffice.cmbServiceGroupTo.value;
            startwaiting(document.frmOffice) ;
            url="../../../../../EditRequestStaffStrength.con?command=ServiceGroup&ServiceGroup="+ServiceGroupTo;
            var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
            ServiceGroupResponseTo(req);
            }
            req.send(null);
    
        }
        else if(command=="TableViewTo")
        {
            var Office_Id=document.frmOffice.txtOffice_Id.value;
            startwaiting(document.frmOffice) ;
            url="../../../../../EditRequestStaffStrength.con?command=TableViewTo&OfficeTableTo="+Office_Id;
            var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
            TableViewResponseTo(req);
            }
            req.send(null);
    
        }
        else if(command=="Add")
        {
           // alert("hai");
                var flag=nullCheckTo();
                var flag1=checkIfTo();
                //alert(flag1);
                var flag2=noofpostcheck();
                if(flag==true && flag1!=true && flag2!=true)
                {
                    
                    var Sl_No=0;
                    var ServiceGroup="";
                    var ServiceGroup1="";
                    var PostRank="";
                    var PostRank1="";
                    var PostCategory="";
                    var PostCategory1="";
                    var NoOfPostTo="";
                    var NoOfPostFrom="";
                    var RemarksTo="";
                    var RemarksFrom="";
                    
                    //Getting Values from Form Fields
                    //Sl_No=document.frmOffice.txtSl_NoTo.value;
                   // alert(Sl_No);
                    ServiceGroup=document.frmOffice.cmbServiceGroupTo.value;
                    ServiceGroup1=document.frmOffice.cmbServiceGroupTo.options[document.frmOffice.cmbServiceGroupTo.selectedIndex].text;
                    PostRank=document.frmOffice.cmbPostRankTo.value;
                    PostRank1=document.frmOffice.cmbPostRankTo.options[document.frmOffice.cmbPostRankTo.selectedIndex].text;
                    PostCategory=document.frmOffice.cmbPostCategoryTo.value;
                    PostCategory1=document.frmOffice.cmbPostCategoryTo.options[document.frmOffice.cmbPostCategoryTo.selectedIndex].text;
                    NoOfPostTo=document.frmOffice.txtNoOfPostTo.value;
                    NoOfPostFrom=document.frmOffice.txtNoOfPostFrom.value;
                    RemarksTo=document.frmOffice.RemarksTo.value;
                    RemarksFrom=document.frmOffice.RemarksFrom.value;
                    //var slno=slnocheckTo();
                    //alert(slno);
                    //alert("b4 inc:"+slno);
                    //var slno1=++slno;
                    //alert("before:"+slno1);
                    //var i=0;
                    var editid=slnocheckTo1()
                    var editid1=++editid;
                    //Creating cells and Adding Values in Grid
                    var tbody=document.getElementById("testTo");
                    var mycurrent_row=document.createElement("TR");
                    mycurrent_row.id=editid1;
                    var cell1=document.createElement("TD");
                    var cell2=document.createElement("TD");
                    
                    var cell3=document.createElement("TD");
                    var cell4=document.createElement("TD");
                    var cell5=document.createElement("TD");
                    var cell6=document.createElement("TD");
                    var cell7=document.createElement("TD");
                    var cell8=document.createElement("TD");
                    var cell9=document.createElement("TD");
                    
                    var anc=document.createElement("A");       
                    
                    var url="javascript:loadValuesTo('" + editid1 +"')";              
                    anc.href=url;
                    var txtedit=document.createTextNode("Edit");
                    anc.appendChild(txtedit);
                    cell1.appendChild(anc);
                    var hidden11=document.createElement("input");
                    hidden11.type="hidden";
                    hidden11.name=editid1;
                    hidden11.value=editid1;
                    cell1.appendChild(hidden11);
                    mycurrent_row.appendChild(cell1);
                    
                    
                               
                    var cmbServiceGroup1=document.createTextNode(ServiceGroup1);
                    cell2.appendChild(cmbServiceGroup1);
                    
                    var hidden2=document.createElement("input");
                    hidden2.type="hidden";
                    hidden2.name="servicegroup";
                    hidden2.value=ServiceGroup;
                    cell2.appendChild(hidden2);
                    mycurrent_row.appendChild(cell2);
                    
                    var cmbPostRank1=document.createTextNode(PostRank1);
                    cell3.appendChild(cmbPostRank1);
                    
                    var hidden3=document.createElement("input");
                    hidden3.type="hidden";
                    hidden3.name="postrank";
                    hidden3.value=PostRank;
                    cell3.appendChild(hidden3);
                    mycurrent_row.appendChild(cell3);
                    
                    var cmbpostcategory1=document.createTextNode(PostCategory1);
                    cell4.appendChild(cmbpostcategory1);
                    
                    var hidden4=document.createElement("input");
                    hidden4.type="hidden";
                    hidden4.name="employmentstatus";
                    hidden4.value=PostCategory;
                    cell4.appendChild(hidden4);
                    mycurrent_row.appendChild(cell4);
                    
                    var txtnoofpostto=document.createTextNode(NoOfPostTo);
                    cell5.appendChild(txtnoofpostto);
                     
                    var hidden5=document.createElement("input");
                    hidden5.type="hidden";
                    hidden5.name="noofpostto";
                    hidden5.value=NoOfPostTo;
                    cell5.appendChild(hidden5);
                    mycurrent_row.appendChild(cell5);
                    
                    
                    var txtremarksto=document.createTextNode(RemarksTo);
                    cell6.appendChild(txtremarksto);
                     
                    var hidden6=document.createElement("input");
                    hidden6.type="hidden";
                    hidden6.name="remarksto";
                    hidden6.value=RemarksTo;
                    cell6.appendChild(hidden6);
                    mycurrent_row.appendChild(cell6);
                    
                    var txtnoofpostfrom=document.createTextNode(NoOfPostFrom);
                    cell7.appendChild(txtnoofpostfrom);
                     
                    var hidden7=document.createElement("input");
                    hidden7.type="hidden";
                    hidden7.name="noofpostfrom";
                    hidden7.value=NoOfPostFrom;
                    cell7.appendChild(hidden7);
                    mycurrent_row.appendChild(cell7);
                    
                    
                    var txtremarksfrom=document.createTextNode(RemarksFrom);
                    cell8.appendChild(txtremarksfrom);
                     
                    var hidden8=document.createElement("input");
                    hidden8.type="hidden";
                    hidden8.name="remarksfrom";
                    hidden8.value=RemarksFrom;
                    cell8.appendChild(hidden8);
                    mycurrent_row.appendChild(cell8);
                            
                    tbody.appendChild(mycurrent_row);
                    
                    //document.frmOffice.txtSl_NoTo.disabled=false;
                    //var val=document.frmOffice.txtSl_NoTo.value;
                    //val=++val;
                    //Clearing The Fields
                    //document.frmOffice.txtSl_NoTo.value=val;
                    //document.frmOffice.txtSl_NoTo.disabled=true;
                    document.frmOffice.cmbServiceGroupTo.selectedIndex=0;
                    document.frmOffice.cmbPostRankTo.selectedIndex=0;
                    document.frmOffice.cmbPostCategoryTo.selectedIndex=0;
                    document.frmOffice.txtNoOfPostTo.value="";
                    document.frmOffice.RemarksTo.value="";
                    document.frmOffice.txtNoOfPostFrom.value="";
                    document.frmOffice.RemarksFrom.value="";
        }
        
    }
    else if(command=="Update")
    {
        //alert("Update");
        //alert("currnetlyEditing:"+currentlyEditingTo);
        var trow=document.getElementById(""+currentlyEditingTo);
        var cells=trow.cells; 
        //cells.item(1).firstChild.nodeValue=document.frmOffice.txtSl_NoTo.value;
        //cells.item(1).lastChild.value=document.frmOffice.txtSl_NoTo.value;
        if(sergrpto!=document.frmOffice.cmbServiceGroupTo.options[document.frmOffice.cmbServiceGroupTo.selectedIndex].value || postrankto!=document.frmOffice.cmbPostRankTo.options[document.frmOffice.cmbPostRankTo.selectedIndex].value || postcatto!=document.frmOffice.cmbPostCategoryTo.options[document.frmOffice.cmbPostCategoryTo.selectedIndex].value)
        {
             var flag1=checkIfTo();
        }
        if(flag1!=true)
        {
        cells.item(1).firstChild.nodeValue=document.frmOffice.cmbServiceGroupTo.options[document.frmOffice.cmbServiceGroupTo.selectedIndex].text;
        cells.item(1).lastChild.value=document.frmOffice.cmbServiceGroupTo.value;
        
        cells.item(2).firstChild.nodeValue=document.frmOffice.cmbPostRankTo.options[document.frmOffice.cmbPostRankTo.selectedIndex].text;
        cells.item(2).lastChild.value=document.frmOffice.cmbPostRankTo.value;
        
        cells.item(3).firstChild.nodeValue=document.frmOffice.cmbPostCategoryTo.options[document.frmOffice.cmbPostCategoryTo.selectedIndex].text;
        cells.item(3).lastChild.value=document.frmOffice.cmbPostCategoryTo.value;
        
        cells.item(4).firstChild.nodeValue=document.frmOffice.txtNoOfPostTo.value;
        cells.item(4).lastChild.value=document.frmOffice.txtNoOfPostTo.value;
        
        cells.item(5).firstChild.nodeValue=document.frmOffice.RemarksTo.value;
        cells.item(5).lastChild.value=document.frmOffice.RemarksTo.value;
        
        cells.item(6).firstChild.nodeValue=document.frmOffice.txtNoOfPostFrom.value;
        cells.item(6).lastChild.value=document.frmOffice.txtNoOfPostFrom.value;
        
        cells.item(7).firstChild.nodeValue=document.frmOffice.RemarksFrom.value;
        cells.item(7).lastChild.value=document.frmOffice.RemarksFrom.value;
        
        //alert("before"+cells.item(1).lastChild.value);
        //var slno=slnocheckTo();
        //alert(slno);
        //document.frmOffice.txtSl_NoTo.value=++slno;
        clearAllTo(); 
        }
            
    }
    else if(command=="Delete")
    {
        var trow=currentlyEditingTo;
        var tbody=document.getElementById("ExistingTo"); 
        var r=document.getElementById(trow);    
        var ri=r.rowIndex;               
        tbody.deleteRow(ri);
        clearAllTo();
        
    }
    else if(command=="SlNo")
    {
        //alert('hai');
        Office_IdNo=document.frmOffice.txtOffice_Id1.value;
        startwaiting(document.frmOffice) ;
        url="../../../../../EditRequestStaffStrength.con?command=SlNo&slno="+Office_IdNo;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        SlNoResponse(req);
        }
        req.send(null);
    
    }
    

}

function SlNoResponse(req)
    {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                stopwaiting(document.frmOffice) ;
                var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                         document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("slno");
                          var id=response.getElementsByTagName("slno")[0].firstChild.nodeValue;
                          document.frmOffice.txtSl_NoTo.value=++id;
                          
                      }
                }
            }
   }         
function ServiceGroupResponseTo(req)
        {
             if(req.readyState==4)
                {
                    if(req.status==200)
                    {
                          stopwaiting(document.frmOffice) ;
                          var cmbPostRankTo=document.getElementById("cmbPostRankTo");
                          
                          var response=req.responseXML.getElementsByTagName("response")[0];
                          var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                          if(flag=="failure")
                          {
                             alert("failed to retrieve the values");
                          }
                          else
                          {
                            var value=response.getElementsByTagName("options");
                            cmbPostRankTo.innerHTML="";
                            var option=document.createElement("OPTION");
                            option.text="--Select PostRank--";
                            option.value=0;
                                  try
                                        {
                                            cmbPostRankTo.add(option);
                                    }catch(errorobject)
                                    { 
                                             cmbPostRankTo.add(option,null);
                                    }
                              
                                  for(var i=0;i<value.length;i++)
                                  {
                                      var tmpoption=value.item(i);
                                      var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                                      var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                                      var combovalue=id;
                                      var combotext=name;
                                      var option=document.createElement("OPTION");
                                      option.text=combotext;
                                      option.value=combovalue;
                                      try
                                        {
                                            cmbPostRankTo.add(option);
                                    }catch(errorobject)
                                    { 
                                             cmbPostRankTo.add(option,null);
                                    }
                                }
                                   var PostRank=response.getElementsByTagName("PostRank")[0].firstChild.nodeValue;
                          
                                  if(PostRank)
                                  {
                                      cmbPostRankTo.value=PostRank;
                                      
                                  }
                          }
                    }
                }   
        } 


function callServiceGroupTo(ServiceGroup,PostRank)
{
        startwaiting(document.frmOffice) ;
       url="../../../../../EditRequestStaffStrength.con?command=ServiceGroup&ServiceGroup="+ServiceGroup+"&PostRank="+PostRank;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        ServiceGroupResponseTo(req);
        }
        req.send(null);

}

// code for loading the values from the table to the input boxes
    // functionality for edit anchor
    function loadValuesTo(rid1)
    {  
    //var ss=document.getElementById("a"+rid1);
      //alert("Acid:"+ss);  
        //alert(document.getElementById(frm));
       var table=document.getElementById("testTo");
      //alert(table.id); 
      var r1=document.getElementById(rid1);
      var rcells1=r1.cells;
      currentlyEditingTo=rcells1.item(0).lastChild.value;
        
      //document.frmOffice.txtSl_NoTo.value=rcells1.item(1).firstChild.nodeValue;
      document.frmOffice.cmbServiceGroupTo.value=rcells1.item(1).lastChild.value;
      sergrpto=rcells1.item(1).lastChild.value;
      var ServiceGroup=rcells1.item(1).lastChild.value;
      var PostRank=rcells1.item(2).lastChild.value;
      postrankto=rcells1.item(2).lastChild.value;
      callServiceGroupTo(ServiceGroup,PostRank);  
      document.frmOffice.cmbPostCategoryTo.value=rcells1.item(3).lastChild.value;
      postcatto=rcells1.item(3).lastChild.value;
      document.frmOffice.txtNoOfPostTo.value=rcells1.item(4).lastChild.value;
      document.frmOffice.RemarksTo.value=rcells1.item(5).lastChild.value;
      document.frmOffice.txtNoOfPostFrom.value=rcells1.item(6).lastChild.value;
      document.frmOffice.RemarksFrom.value=rcells1.item(7).lastChild.value;
            
      document.frmOffice.cmdAddTo.style.display="none";
      document.frmOffice.cmdUpdateTo.style.display="block";
      document.frmOffice.cmdDeleteTo.style.display="block";
    }    
    
    
    
 //Server Response for Loading TableView Values
function TableViewResponseTo(req)
    {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                    stopwaiting(document.frmOffice) ;
                    //Clearing The Table Before Loading
                    var tbodyto=document.getElementById("testTo");
                    var t=0;
                    for(t=tbodyto.rows.length-1;t>=0;t--)
                    {
                        tbodyto.deleteRow(0);
                    }
                //var frm=document.getElementById("frmOfficeTo");
                //frm=frm.id;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                     if(flag=="failure")
                      {
                         alert("There is No Values in Grid");
                         //document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("servicegroupid");
                          
                          var k=300;
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                                
                                var SlNo=response.getElementsByTagName("slno")[i].firstChild.nodeValue;
                                var ServiceGroupId=response.getElementsByTagName("servicegroupid")[i].firstChild.nodeValue;
                                var ServiceGroupName=response.getElementsByTagName("servicegroupname")[i].firstChild.nodeValue;
                                var PostRankId=response.getElementsByTagName("postrankid")[i].firstChild.nodeValue;
                                var PostRankName=response.getElementsByTagName("postrankname")[i].firstChild.nodeValue;
                                var NoOfPost=response.getElementsByTagName("noofpost")[i].firstChild.nodeValue;
                                var Remarks=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                                var NoOfPostFrom=response.getElementsByTagName("noofpostfrom")[i].firstChild.nodeValue;
                                var RemarksFrom=response.getElementsByTagName("remarksfrom")[i].firstChild.nodeValue;
                                var EmploymentStatusId=response.getElementsByTagName("employmentstatusid")[i].firstChild.nodeValue;
                                var EmploymentStatusName=response.getElementsByTagName("employmentstatusname")[i].firstChild.nodeValue;
                                
                                var tbodyto=document.getElementById("testTo");
                                var tableto=document.getElementById("ExistingTo");
                                var mycurrent_row=document.createElement("TR");
                                
                                mycurrent_row.id=k;
                                var cell1=document.createElement("TD");
                                var cell2=document.createElement("TD");
                                var cell3=document.createElement("TD");
                                var cell4=document.createElement("TD");
                                var cell5=document.createElement("TD");
                                var cell6=document.createElement("TD");
                                var cell7=document.createElement("TD");
                                var cell8=document.createElement("TD");
                                var cell9=document.createElement("TD");
                                
                                var anc=document.createElement("A");       
                                    
                                var url="javascript:loadValuesTo('" + k +"')";              
                                anc.href=url;
                                //anc.id="a"+k;
                               
                                var txtedit=document.createTextNode("Edit");
                                anc.appendChild(txtedit);
                                cell1.appendChild(anc);
                                var hidden11=document.createElement("input");
                                hidden11.type="hidden";
                                hidden11.name=k;
                                hidden11.value=k;
                                cell1.appendChild(hidden11);
                                                    
                                mycurrent_row.appendChild(cell1);
                                
                                
                                
                                var txtservicegroupname=document.createTextNode(ServiceGroupName);
                                cell2.appendChild(txtservicegroupname);
                                var hidden2=document.createElement("input");
                                hidden2.type="hidden";
                                hidden2.name="servicegroup";
                                hidden2.value=ServiceGroupId;
                                cell2.appendChild(hidden2);
        
                                mycurrent_row.appendChild(cell2);
                                
                                var txtpostrankname=document.createTextNode(PostRankName);
                                cell3.appendChild(txtpostrankname);
                                var hidden3=document.createElement("input");
                                hidden3.type="hidden";
                                hidden3.name="postrank";
                                hidden3.value=PostRankId;
                                cell3.appendChild(hidden3);
                                mycurrent_row.appendChild(cell3);
                                
                                var txtemploymentstatusname=document.createTextNode(EmploymentStatusName);
                                cell4.appendChild(txtemploymentstatusname);
                                var hidden4=document.createElement("input");
                                hidden4.type="hidden";
                                hidden4.name="employmentstatus";
                                hidden4.value=EmploymentStatusId;
                                cell4.appendChild(hidden4);
                                mycurrent_row.appendChild(cell4);
                                
                                
                                var txtnoofpost=document.createTextNode(NoOfPost);
                                cell5.appendChild(txtnoofpost);
                                var hidden5=document.createElement("input");
                                hidden5.type="hidden";
                                hidden5.name="noofpostto";
                                hidden5.value=NoOfPost;
                                cell5.appendChild(hidden5);
                                mycurrent_row.appendChild(cell5);
                                
                                var txtremarks=document.createTextNode(Remarks);
                                cell6.appendChild(txtremarks);
                                var hidden6=document.createElement("input");
                                hidden6.type="hidden";
                                hidden6.name="remarksto";
                                hidden6.value=Remarks;
                                cell6.appendChild(hidden6);
                                mycurrent_row.appendChild(cell6);
                                
                                var txtnoofpostfrom=document.createTextNode(NoOfPostFrom);
                                cell7.appendChild(txtnoofpostfrom);
                                var hidden7=document.createElement("input");
                                hidden7.type="hidden";
                                hidden7.name="noofpostfrom";
                                hidden7.value=NoOfPostFrom;
                                cell7.appendChild(hidden7);
                                mycurrent_row.appendChild(cell7);
                                
                                var txtremarksfrom=document.createTextNode(RemarksFrom);
                                cell8.appendChild(txtremarksfrom);
                                var hidden8=document.createElement("input");
                                hidden8.type="hidden";
                                hidden8.name="remarksfrom";
                                hidden8.value=RemarksFrom;
                                cell8.appendChild(hidden8);
                                mycurrent_row.appendChild(cell8);
                                k++;
                                                        
                                tbodyto.appendChild(mycurrent_row);
                                
                                                         
                          }
                      }
                       
                }
            }
   }
function SlNo()
{
//alert('hai');
     callServer1('SlNo',null);
}*/


function total()
{

    var sanction=document.frmOffice.txtNoPost.value;
    var postto=document.frmOffice.txtNoOfPostTo.value;
    var postfrom=document.frmOffice.txtNoOfPostFrom.value;
    var totallength=parseInt(sanction)+parseInt(postto)-parseInt(postfrom);
    document.frmOffice.txtTotal.value=totallength;

}