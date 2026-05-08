var currentlyEditing=0;
var sergrp;
var postrank;
var postcat;
var j=1;
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



function callServiceGroup(ServiceGroup,PostRank)
{
      startwaiting(document.frmStaffStrength) ;
       url="../../../../../CreateStaffStrengthServlet.con?command=ServiceGroup&ServiceGroup="+ServiceGroup+"&PostRank="+PostRank;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        ServiceGroupResponse(req);
        }
        req.send(null);

}

//Calling Callserver

function callServer(command,param)
{
    var url="";
    
    if(command=="ServiceGroup")
    {
        var ServiceGroup=document.frmStaffStrength.cmbServiceGroup.value;
        startwaiting(document.frmStaffStrength) ;
        url="../../../../../CreateStaffStrengthServlet.con?command=ServiceGroup&ServiceGroup="+ServiceGroup;
        var req=getTransport();
        req.open("POST",url,true);        
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
            //Sl_No=document.frmStaffStrength.txtSl_No.value;
            ServiceGroup=document.frmStaffStrength.cmbServiceGroup.value;
            ServiceGroup1=document.frmStaffStrength.cmbServiceGroup.options[document.frmStaffStrength.cmbServiceGroup.selectedIndex].text;
            PostRank=document.frmStaffStrength.cmbPostRank.value;
            PostRank1=document.frmStaffStrength.cmbPostRank.options[document.frmStaffStrength.cmbPostRank.selectedIndex].text;
            //PostCategory=document.frmStaffStrength.cmbPostCategory.value;
            //PostCategory1=document.frmStaffStrength.cmbPostCategory.options[document.frmStaffStrength.cmbPostCategory.selectedIndex].text;
            NoOfPost=document.frmStaffStrength.txtNoPost.value;
            Remarks=document.frmStaffStrength.Remarks.value;
            
            
            //Creating cells and Adding Values in Grid
            var tbody=document.getElementById("tblList");
            var mycurrent_row=document.createElement("TR");
            mycurrent_row.id=j;
            var cell1=document.createElement("TD");
            var cell2=document.createElement("TD");
            
            var cell3=document.createElement("TD");
            var cell4=document.createElement("TD");
            var cell5=document.createElement("TD");
            var cell6=document.createElement("TD");
            var cell7=document.createElement("TD");
            
            var anc=document.createElement("A");       
            
            var url="javascript:loadValuesFromTable('" + j + "')";              
            anc.href=url;
            var txtedit=document.createTextNode("Edit");
            anc.appendChild(txtedit);
            cell1.appendChild(anc);
            var hidden1=document.createElement("input");
            hidden1.type="hidden";
            hidden1.name="sno";
            hidden1.value=j;
            cell1.appendChild(hidden1);
            mycurrent_row.appendChild(cell1);
            
            /*var txtslno=document.createTextNode(Sl_No);
            cell2.appendChild(txtslno);
            var hidden1=document.createElement("input");
            hidden1.type="hidden";
            hidden1.name="sno";
            hidden1.value=Sl_No;
            cell2.appendChild(hidden1);
            mycurrent_row.appendChild(cell2);*/
            
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
            
            /*var cmbpostcategory1=document.createTextNode(PostCategory1);
            cell4.appendChild(cmbpostcategory1);
            
            var hidden4=document.createElement("input");
            hidden4.type="hidden";
            hidden4.name="postcategory";
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
            j++;
            
            //Clearing The Fields
            //document.frmStaffStrength.txtSl_No.value=++Sl_No;
            document.frmStaffStrength.cmbServiceGroup.selectedIndex=0;
            document.frmStaffStrength.cmbPostRank.selectedIndex=0;
            //document.frmStaffStrength.cmbPostCategory.selectedIndex=0;
            document.frmStaffStrength.txtNoPost.value="";
            document.frmStaffStrength.Remarks.value="";
        }
        
    }
    else if(command=="Update")
    {
        //alert("Update");
        //alert("currnetlyEditing:"+currentlyEditing);
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
           
//var flag1=true;
        if(sergrp!=document.frmStaffStrength.cmbServiceGroup.options[document.frmStaffStrength.cmbServiceGroup.selectedIndex].value || postrank!=document.frmStaffStrength.cmbPostRank.options[document.frmStaffStrength.cmbPostRank.selectedIndex].value )
        {
             var flag1=checkIf();
        }
        
        //var flag2=checkupdate();
        //alert('flag1'+flag1);
        //alert('flag2'+flag2);
        //cells.item(1).firstChild.nodeValue=document.frmStaffStrength.txtSl_No.value;
        //cells.item(1).lastChild.value=document.frmStaffStrength.txtSl_No.value;
        if(flag1!=true)
        {
        cells.item(1).firstChild.nodeValue=document.frmStaffStrength.cmbServiceGroup.options[document.frmStaffStrength.cmbServiceGroup.selectedIndex].text;
        cells.item(1).lastChild.value=document.frmStaffStrength.cmbServiceGroup.value;
        
        cells.item(2).firstChild.nodeValue=document.frmStaffStrength.cmbPostRank.options[document.frmStaffStrength.cmbPostRank.selectedIndex].text;
        cells.item(2).lastChild.value=document.frmStaffStrength.cmbPostRank.value;
        
        //cells.item(3).firstChild.nodeValue=document.frmStaffStrength.cmbPostCategory.options[document.frmStaffStrength.cmbPostCategory.selectedIndex].text;
        //cells.item(3).lastChild.value=document.frmStaffStrength.cmbPostCategory.value;
        
        cells.item(3).firstChild.nodeValue=document.frmStaffStrength.txtNoPost.value;
        cells.item(3).lastChild.value=document.frmStaffStrength.txtNoPost.value;
        
        cells.item(4).firstChild.nodeValue=document.frmStaffStrength.Remarks.value;
        cells.item(4).lastChild.value=document.frmStaffStrength.Remarks.value;
        clearAll();
        }
        /*else if(flag2==false && flag1==true)
        {
            cells.item(4).firstChild.nodeValue=document.frmStaffStrength.txtNoPost.value;
            cells.item(4).lastChild.value=document.frmStaffStrength.txtNoPost.value;
            
            cells.item(5).firstChild.nodeValue=document.frmStaffStrength.Remarks.value;
            cells.item(5).lastChild.value=document.frmStaffStrength.Remarks.value;
            clearAll();
        
        }*/
        //var slno=slnocheck();
        //document.frmStaffStrength.txtSl_No.value=++slno;
              
                
    }
    else if(command=="Delete")
    {
        alert(currentlyEditing);
        var trow=currentlyEditing;
        var tbody=document.getElementById("Existing"); 
        var r=document.getElementById(trow);    
        var ri=r.rowIndex;               
        tbody.deleteRow(ri);
        clearAll();
        /*document.frmStaffStrength.cmdAdd.style.display="block";
        document.frmStaffStrength.cmdUpdate.style.display="none";
        document.frmStaffStrength.cmdDelete.style.display="none";*/
        
        
    }
        
    
    
    
        
}

//Server Response Handling
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
    
 
    
      var r=document.getElementById(rid);      
      var rcells=r.cells;
      currentlyEditing=rcells.item(0).lastChild.value;
      //alert('edit'+currentlyEditing);
      //document.frmStaffStrength.txtSl_No.value=rcells.item(1).firstChild.nodeValue;
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