var currentlyEditing=0;

var sergrp;
var postrank;
var postcat;
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


//Calling Callserver

function callServer(command,param)
{
    var url="";
    
    if(command=="OfficeLevel")
    {
        //alert("hai");
        var OfficeLevel=document.frmStaffStrength.cmbOfficeLevel.value;
        startwaiting(document.frmStaffStrength) ;
        url="../../../../../EditStaffStrengthServlet.con?command=OfficeLevel&OfficeLevel="+OfficeLevel;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        OfficeLevelResponse(req);
        }
        req.send(null);
    }
    else if(command=="TempId")
    {
       var TempId=document.frmStaffStrength.cmbSSTemp_Id.value;
       startwaiting(document.frmStaffStrength) ;
        url="../../../../../EditStaffStrengthServlet.con?command=TempId&TemplateId="+TempId;
        var req=getTransport();
        req.open("POST",url,true);
        req.onreadystatechange=function()
        {
            TempNameResponse(req);
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
            //Sl_No=document.frmStaffStrength.txtSl_No.value;
            ServiceGroup=document.frmStaffStrength.cmbServiceGroup.value;
            ServiceGroup1=document.frmStaffStrength.cmbServiceGroup.options[document.frmStaffStrength.cmbServiceGroup.selectedIndex].text;
            PostRank=document.frmStaffStrength.cmbPostRank.value;
            PostRank1=document.frmStaffStrength.cmbPostRank.options[document.frmStaffStrength.cmbPostRank.selectedIndex].text;
            //PostCategory=document.frmStaffStrength.cmbPostCategory.value;
            //PostCategory1=document.frmStaffStrength.cmbPostCategory.options[document.frmStaffStrength.cmbPostCategory.selectedIndex].text;
            NoOfPost=document.frmStaffStrength.txtNoPost.value;
            Remarks=document.frmStaffStrength.Remarks.value;
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
            
            /*var cmbpostcategory1=document.createTextNode(PostCategory1);
            cell4.appendChild(cmbpostcategory1);
            
            var hidden4=document.createElement("input");
            hidden4.type="hidden";
            hidden4.name="employmentstatusid";
            hidden4.value=PostCategory;
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
            
            
            var txtremarks=document.createTextNode(Remarks);
            cell5.appendChild(txtremarks);
             
            var hidden5=document.createElement("input");
            hidden5.type="hidden";
            hidden5.name="remarks";
            hidden5.value=Remarks;
            cell5.appendChild(hidden5);
            mycurrent_row.appendChild(cell5);
            tbody.appendChild(mycurrent_row);
            
            
            //Clearing The Fields
            //document.frmStaffStrength.txtSl_No.value=++Sl_No;
            document.frmStaffStrength.cmbServiceGroup.selectedIndex=0;
            document.frmStaffStrength.cmbPostRank.selectedIndex=0;
            //document.frmStaffStrength.cmbPostCategory.selectedIndex=0;
            document.frmStaffStrength.txtNoPost.value="";
            document.frmStaffStrength.Remarks.value="";
        }
        
    }
    else if(command=="TableView")
    {
        var TempId=document.frmStaffStrength.cmbSSTemp_Id.value;
        startwaiting(document.frmStaffStrength) ;
        url="../../../../../EditStaffStrengthServlet.con?command=TableView&TemplateId="+TempId;
        var req=getTransport();
        req.open("POST",url,true);
        req.onreadystatechange=function()
        {
            TableViewResponse(req);
        }
        req.send(null);
    }
    else if(command=="Update")
    {
        //alert("Update");
        //alert("currnetlyEditing:"+currentlyEditing);
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
        
         if(sergrp!=document.frmStaffStrength.cmbServiceGroup.options[document.frmStaffStrength.cmbServiceGroup.selectedIndex].value || postrank!=document.frmStaffStrength.cmbPostRank.options[document.frmStaffStrength.cmbPostRank.selectedIndex].value )
        {
             var flag1=checkIf();
        }
        
        
        if(flag1!=true)
        {
        cells.item(1).firstChild.nodeValue=document.frmStaffStrength.cmbServiceGroup.options[document.frmStaffStrength.cmbServiceGroup.selectedIndex].text;
        cells.item(1).lastChild.value=document.frmStaffStrength.cmbServiceGroup.value;
        
        cells.item(2).firstChild.nodeValue=document.frmStaffStrength.cmbPostRank.options[document.frmStaffStrength.cmbPostRank.selectedIndex].text;
        cells.item(2).lastChild.value=document.frmStaffStrength.cmbPostRank.value;
        
       // cells.item(3).firstChild.nodeValue=document.frmStaffStrength.cmbPostCategory.options[document.frmStaffStrength.cmbPostCategory.selectedIndex].text;
        //cells.item(3).lastChild.value=document.frmStaffStrength.cmbPostCategory.value;
        
        cells.item(3).firstChild.nodeValue=document.frmStaffStrength.txtNoPost.value;
        cells.item(3).lastChild.value=document.frmStaffStrength.txtNoPost.value;
        
        cells.item(4).firstChild.nodeValue=document.frmStaffStrength.Remarks.value;
        cells.item(4).lastChild.value=document.frmStaffStrength.Remarks.value;
        //var slno=slnocheck();
        //document.frmStaffStrength.txtSl_No.value=++slno;
        clearAll();      
        }
            
    }
    else if(command=="ServiceGroup")
    {
        var ServiceGroup=document.frmStaffStrength.cmbServiceGroup.value;
        startwaiting(document.frmStaffStrength) ;
        url="../../../../../EditStaffStrengthServlet.con?command=ServiceGroup&ServiceGroup="+ServiceGroup;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        ServiceGroupResponse(req);
        }
        req.send(null);
    
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

//Server Response
function OfficeLevelResponse(req)
        {
             if(req.readyState==4)
                {
                    if(req.status==200)
                    {   stopwaiting(document.frmStaffStrength) ;
                          var cmbSSTemp_Id=document.getElementById("cmbSSTemp_Id");   
                          var response=req.responseXML.getElementsByTagName("response")[0];
                          var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                          if(flag=="failure")
                          {
                             alert("There is no Template Id for this Level");
                             document.frmStaffStrength.cmbSSTemp_Id.selectedIndex=0;
                             document.frmStaffStrength.txtTemplate_Name.value="";
                             document.frmStaffStrength.txtTemplate_Name1.value="";
                             //Clearing The Table Before Loading
                                    var tbody=document.getElementById("tblList");
                                    var t=0;
                                    for(t=tbody.rows.length-1;t>=0;t--)
                                    {
                                        tbody.deleteRow(0);
                                    }
                          }
                          else
                          {
                            var value=response.getElementsByTagName("options");
                            cmbSSTemp_Id.innerHTML="";
                            var option=document.createElement("OPTION");
                            option.text="--Select TemplateId--";
                            option.value=0;
                                  try
                                        {
                                            cmbSSTemp_Id.add(option);
                                    }catch(errorobject)
                                    { 
                                             cmbSSTemp_Id.add(option,null);
                                    }
                              
                                  for(var i=0;i<value.length;i++)
                                  {
                                      var tmpoption=value.item(i);
                                      var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                                      var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                                      var option=document.createElement("OPTION");
                                      option.text=name;
                                      option.value=id;
                                      try
                                        {
                                            cmbSSTemp_Id.add(option);
                                    }catch(errorobject)
                                    { 
                                             cmbSSTemp_Id.add(option,null);
                                    } 
                                }
                                   
                          }
                    }
                }   
        }
        
    
function TempNameResponse(req)
{
     if(req.readyState==4)
     {
                    if(req.status==200)
                    {   stopwaiting(document.frmStaffStrength) ;
                          var cmbSSTemp_Id=document.getElementById("cmbSSTemp_Id");   
                          var response=req.responseXML.getElementsByTagName("response")[0];
                          var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                          if(flag=="failure")
                          {
                             alert("failed to retrieve the values");
                          }
                          else
                          {
                            var value=response.getElementsByTagName("options");
                            for(var i=0;i<value.length;i++)
                            {
                                      var tmpoption=value.item(i);
                                      var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                                      document.frmStaffStrength.txtTemplate_Name.value=name;
                                      document.frmStaffStrength.txtTemplate_Name1.value=name;
                                      callServer("TableView",null);
                            }    
                          }
                    }
     }
}
//Server Response for Loading TableView Values
function TableViewResponse(req)
    {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                    stopwaiting(document.frmStaffStrength) ;
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
                                if(Remarks!="null")
                                {
                                Remarks=Remarks;
                                }
                                else
                                {
                                    Remarks="";
                                }
                                var tbody=document.getElementById("tblList");
                                var table=document.getElementById("Existing");
                                var mycurrent_row=document.createElement("TR");
                                
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
                                anc.href=url;
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
                                
                                var txtremarks=document.createTextNode(Remarks);
                                cell5.appendChild(txtremarks);
                                var hidden5=document.createElement("input");
                                hidden5.type="hidden";
                                hidden5.name="remarks";
                                hidden5.value=Remarks;
                                cell5.appendChild(hidden5);
                                mycurrent_row.appendChild(cell5);
                                   j++;                             
                                tbody.appendChild(mycurrent_row);
                                
                                                         
                          }
                      }
                    
                }
            }
   }
   
function ServiceGroupResponse(req)
        {
             if(req.readyState==4)
                {
                    if(req.status==200)
                    {
                        stopwaiting(document.frmStaffStrength) ;
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
      document.frmStaffStrength.cmbServiceGroup.value=rcells.item(1).lastChild.value;
      sergrp=rcells.item(1).lastChild.value;
      var ServiceGroup=rcells.item(1).lastChild.value;
      var PostRank=rcells.item(2).lastChild.value;
      postrank=rcells.item(2).lastChild.value;
      callServiceGroup(ServiceGroup,PostRank);  
      //document.frmStaffStrength.cmbPostCategory.value=rcells.item(3).lastChild.value;
      //postcat=rcells.item(3).lastChild.value;
      document.frmStaffStrength.txtNoPost.value=rcells.item(3).lastChild.value;
      document.frmStaffStrength.Remarks.value=rcells.item(4).lastChild.value;
            
      document.frmStaffStrength.cmdAdd.style.display="none";
      document.frmStaffStrength.cmdUpdate.style.display="block";
      document.frmStaffStrength.cmdDelete.style.display="block";
    }
    
    
    
    function callServiceGroup(ServiceGroup,PostRank)
{
        startwaiting(document.frmStaffStrength) ;
       url="../../../../../EditStaffStrengthServlet.con?command=ServiceGroup&ServiceGroup="+ServiceGroup+"&PostRank="+PostRank;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        ServiceGroupResponse(req);
        }
        req.send(null);

}


function zerocheck()
{
    if(document.frmStaffStrength.txtNoPost.value==0)
    {
        alert("No of Posts more than zero or Equal to One");
        document.frmStaffStrength.txtNoPost.value="";
        document.frmStaffStrength.txtNoPost.focus();
        return false;
    }
    else
    {
        return true;
    }
    return true;

}