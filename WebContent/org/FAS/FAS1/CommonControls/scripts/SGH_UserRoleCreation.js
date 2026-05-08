
/**
 * Variables Declaration
 */
 
 var winemp;
 var my_window;

//------------------------------------------------------------------------------//

/**
 *  Browser Indentification 
 */

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
 
//------------------------------------------------------------------------------//
 
 /**
  *  Clear Function 
  */
 
 function clearAll()
 {      
        document.frmSGH_UserRoleCreation.txtUserID.value="";
        document.frmSGH_UserRoleCreation.txtEmpID.value="";      
        document.frmSGH_UserRoleCreation.txtEmpName.value="";        
        document.frmSGH_UserRoleCreation.txtSection.selectedIndex=0;
        
        document.frmSGH_UserRoleCreation.Grant.disabled=false;
        document.frmSGH_UserRoleCreation.Revoke.disabled=true;        
      
        /** Clear Grid */
        var tbody=document.getElementById("tblList");
        var t=0;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }    
 }
 
 
//------------------------------------------------------------------------------// 
 
 
 function addRecord()
 {
  var strCaption=document.frmSGH_UserRoleCreation.cmdAdd.value;
    if(strCaption=="  Add  ")
    {
        document.frmSGH_UserRoleCreation.cmdAdd.value=" Save ";
        alert("Please Select Master Major Details.");
        document.frmSGH_UserRoleCreation.cmdEdit.disabled=true;                  
        document.frmSGH_UserRoleCreation.txtSysId.focus();        
    }
    else
    {
        callServer("Add","null");
    }
 
 }
 
 //------------------------------------------------------------------------------//
 
 
 
 /**
  *  Load Employee ID and Employee Name 
  */
  
 function callServer(command,param)
 {     
          if(command=="Add")
          {           
                var flag=nullCheck();
                if(flag==true)
                {
                    txtEmpID = document.getElementById("txtEmpID").value;
                    txtSection = document.getElementById("txtSection").value;
                    url="../../../../../SGH_UserRoleCreation.kv?command=Add&txtEmpID="+txtEmpID+"&txtSection="+txtSection;               
                    req.open("GET",url,true);        
                    req.onreadystatechange=processResponse;
                    req.send(null);
                }
                else
                {
                  alert("Please Enter Employee ID");
                }
                
          }      
          
          else if(command=="Delete")
          {
                    txtEmpID = document.getElementById("txtEmpID").value;
                    txtSection = document.getElementById("txtSection").value;
                    url="../../../../../SGH_UserRoleCreation.kv?command=Delete&txtEmpID="+txtEmpID+"&txtSection="+txtSection;               
                    req.open("GET",url,true);        
                    req.onreadystatechange=processResponse;
                    req.send(null);
          }
         
          else if(command=="combo")
          {
                url="../../../../../SGH_UserRoleCreation.kv?command=combo";               
                req.open("GET",url,true);        
                req.onreadystatechange=processResponse;
                req.send(null);          
                
          }   
          else if(command=="LoginUid")
          {
             var flag=nullCheck();
             if(flag==true)
             { 
               var txtUserId=document.getElementById("txtUserID").value;            
               url="../../../../../ServletUpdatingRoles.con?command=LoginUid&txtUserId="+txtUserId;
               req.open("GET",url,true);        
               req.onreadystatechange=processResponse;
               req.send(null);    
                 req.send(null);
             }
             else
             {
                alert("Please Enter Employee ID");
             }               
               
          }
          else if(command=="ListAll")
          {
             var flag=nullCheck();
             if(flag==true)
             {
                 txtEmpID = document.getElementById("txtEmpID").value;
                 txtSection = document.getElementById("txtSection").value;
                 url="../../../../../SGH_UserRoleCreation.kv?command=ListAll&txtEmpID="+txtEmpID+"&txtSection="+txtSection;               
                 req.open("GET",url,true);        
                 req.onreadystatechange=processResponse;
                 req.send(null);
             }
             else
             {
                alert("Please Enter Employee ID");
             }
             
          
          
          }
          
 }
 
 //------------------------------------------------------------------------------//
 
 /**
  *  Handle Response for Loading Employee ID and Employee Name 
  */
   
 function processResponse()
 {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {    
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              
            
              if(command=="Add")
              {
                  addRow(baseResponse);                 
              }
              else if(command=="Delete")
              {    
                  deleteRow(baseResponse);                             
              }
                          
              else if(command=="Load_Section")
              {
                  combo(baseResponse);
              
              }             
              else if(command=="LoginUid")
              {
                  LoginUid(baseResponse);
              }
              else if(command=="ListAll")
              {
                  ListAll(baseResponse);
              }
          }
        }
  }
 
 //------------------------------------------------------------------------------//
 
  /**   Add Row   */
 
    function addRow(baseResponse)
    {
            
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;          
              if(flag=="Success")
              {      
                    
                     alert("Record Inserted Into Database successfully.");
                     
                     var items=new Array();                   
                     items[0]=document.getElementById("txtSection").value;
                     items[1]=document.frmSGH_UserRoleCreation.txtSection.options[document.frmSGH_UserRoleCreation.txtSection.selectedIndex].text;
                     
                     var tbody=document.getElementById("tblList");
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=items[0];
                     var cell=document.createElement("TD");
                     
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" + items[0] + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     var i=0;
                 
                     var cell2;
                     for(i=0;i<2;i++)
                     {                                           
                         cell2=document.createElement("TD");                               
                         var currenttext=document.createTextNode(items[i]);                         
                         cell2.appendChild(currenttext);       
                         mycurrent_row.appendChild(cell2);       
                     }  
                     
                     tbody.appendChild(mycurrent_row); 
                      
             }
             else if(flag=="AlreadyExist")
             {
                      alert("Sorry! Record Already Exist");                      
             }
             else if(flag=="Failure")
             {
                      alert("Sorry! Record Not Inserted");                      
             }
             
        
    }
    
 //------------------------------------------------------------------------------//   
    
/** Delete Row */   

 function deleteRow(baseResponse)
 {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="Success")
      {
           alert("Record Deleted Successfully.");
           var section_id=baseResponse.getElementsByTagName("section_id")[0].firstChild.nodeValue;
           var tbody=document.getElementById("Existing");                      
           var r=document.getElementById(section_id);  
           var ri=r.rowIndex;               
           tbody.deleteRow(ri);
           
           document.frmSGH_UserRoleCreation.Grant.disabled=false;
           document.frmSGH_UserRoleCreation.Revoke.disabled=true;
      }
      else if (flag=="Failure") 
      {
          alert("Unable to Delete The Record");         
      }      
 }
    
 
 //------------------------------------------------------------------------------//


  /** Load Section Combo List */
    
  function combo(baseResponse)
  {
          var section_id=baseResponse.getElementsByTagName("section_id");
          var section_name=baseResponse.getElementsByTagName("section_name");
          
          var txtSection=document.getElementById("txtSection"); 
          
          for(var i=0;i<section_id.length;i++)
          {
              var sec_id=section_id.item(i).firstChild.nodeValue;
              var sec_name=section_name.item(i).firstChild.nodeValue;
              var option=document.createElement("OPTION");
              option.text=sec_name;
              option.value=sec_id;
             
              try
              {
                  txtSection.add(option);
              }
              catch(errorObject)
              {
                  txtSection.add(option,null);
              }
          }
   }
    
 
 //------------------------------------------------------------------------------//   
    
   
  /** Load Employee ID and Name */
  
 function LoginUid(baseResponse)
 {
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     
     if(flag =='Failure')
     {
            alert('Please Enter Valid User ID or User ID does not exist');
            var tbody=document.getElementById("tblList");
            if(tbody.rows.length >0)
                {        
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                         tbody.innerText='';
                    else 
                         tbody.innerHTML='';
                }
            document.frmSGH_UserRoleCreation.reset();
            return;
     }     
     else if(flag=='success') 
     {
        /** Load Employee ID */
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
        if(empid==0)
        {
            empid="";
        }      
        document.frmSGH_UserRoleCreation.txtEmpID.value=empid;        
    
        /** Load Employee Name */   
        var EmpName=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;        
        document.frmSGH_UserRoleCreation.txtEmpName.value=EmpName;         
        
        callServer('ListAll','null');
        
     }  
       
  }


//------------------------------------------------------------------------------//



function ListAll(baseResponse)
{

     /** Get Grid Table Object */  
      
      var tbody=document.getElementById("tblList");
      var table=document.getElementById("Existing");
      
       
      /** Delete all the Existing Records in Grid Table */ 
      if(tbody.rows.length >0)
      {        
          if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
              tbody.innerText='';
          else 
              tbody.innerHTML='';
      }
      
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;     
     if(flag=='Success')
     {
      
      var list_section_id = baseResponse.getElementsByTagName("list_section_id");
      var list_section_name = baseResponse.getElementsByTagName("list_section_name");
          
      for(var i=0;i<list_section_id.length;i++)
      {
      
         var list_sec_id = list_section_id.item(i).firstChild.nodeValue;
         var list_sec_name = list_section_name.item(i).firstChild.nodeValue;
      
         /** Create Dynamic Table Row */
         var mycurrent_row=document.createElement("TR");
         
         
         /** Create Dynamic Table Definition */
         var cell1=document.createElement("TD");
         var cell2=document.createElement("TD");
         var cell3=document.createElement("TD");
         
         
         mycurrent_row.id=list_sec_id;
         
         
         var cell=document.createElement("TD");
         var anc=document.createElement("A");
         anc.id="a"+list_sec_id;
         anc.href="javascript:loadValuesFromTable('"+list_sec_id+"')";
         var edit=document.createTextNode("Edit");
         anc.appendChild(edit);
         cell.appendChild(anc);
         mycurrent_row.appendChild(cell);
        
         var  s_id = document.createTextNode(list_sec_id);
         var s_name=document.createTextNode(list_sec_name);         
        
         cell1.appendChild(s_id);
         cell2.appendChild(s_name);         
         
         mycurrent_row.appendChild(cell);
         mycurrent_row.appendChild(cell1);
         mycurrent_row.appendChild(cell2);
        
         tbody.appendChild(mycurrent_row);
         table.appendChild(tbody);
     }       
   }
   else if(flag=='NoData')
   {
      alert("No Records");
   }
   else if(flag=='Failure')
   {
      alert("Error in Loading List of Roles");
   }
   
   
}

//------------------------------------------------------------------------------//

   /**
    *  Load Values from Grid Table when Clicking Edit Hyper Link
    */

   function loadValuesFromTable(rid)
   {      
          var r=document.getElementById(rid);
          
          /** Get Combo Object */
          var txtSection=document.getElementById("txtSection");
                    
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
         
          document.frmSGH_UserRoleCreation.txtSection.value=rid;
          
          document.frmSGH_UserRoleCreation.Revoke.disabled=false;
          document.frmSGH_UserRoleCreation.Grant.disabled=true;
          document.frmSGH_UserRoleCreation.Revoke.focus();
      
    }    
    
//------------------------------------------------------------------------------//    
   

function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
   
    winemp= window.open("../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

//------------------------------------------------------------------------------//

function doParentEmp(emp)
{
   document.frmSGH_UserRoleCreation.txtSysId.value=emp;
   callServer('Login','null');
}

//------------------------------------------------------------------------------//

//this is the function to close the employee popup windows...

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();

}

//------------------------------------------------------------------------------//

 /**
  *  Window Close 
  */
 function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
 
 //------------------------------------------------------------------------------//
 
 function nullCheck()
 {
    var txtUserID = document.getElementById("txtUserID").value;
    if ( txtUserID=="")
      return false; 
    else 
      return true;
 
 }