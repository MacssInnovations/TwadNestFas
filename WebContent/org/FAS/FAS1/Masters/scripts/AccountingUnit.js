var jobflag;
var officeid;
var seq=1;
var cur;
var winListAllBudget;



/** 
 *  Browser Identification 
 */

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



//Function for Icon Office Selection

var winjob;

function jobpopup()
{
    jobflag=true;
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

function forChildOption()
{
    if(jobflag==true)
    {
          if (winjob && winjob.open && !winjob.closed) 
                 winjob.officeSelection(true,true,true,false);
    }
    else
    {   
        if (winjob1 && winjob1.open && !winjob1.closed)
              {
               
                 winjob1.officeSelection(true,true,true,false);
               }
    
    }

}

function doParentJob(jobid,deptid)
{

    if(jobflag==true)
    {
        document.frmAccountUnit.txtLOffice.value=jobid;
    loadOffice(jobid,true);
    }
    else
    {
        document.frmAccountUnit.txtOffice.value=jobid;
        loadOffice(jobid,false);
    }

}

window.onunload=function()
{

//if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winjob1 && winjob1.open && !winjob1.closed) winjob1.close();
if (winListAllBudget && winListAllBudget.open && !winListAllBudget.closed) winListAllBudget.close();
}




/**
 * Number Checking 
 */
function numbersonly1(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}



//////////////   FOR JOB POPUP WINDOW1 //////////////////////
var winjob1;
function jobpopup1()
{
    jobflag=false;
    if (winjob1 && winjob1.open && !winjob1.closed) 
    {
       winjob1.resizeTo(500,500);
       winjob1.moveTo(250,250); 
       winjob1.focus();
    }
    else
    {
        winjob1=null
    }
        
    winjob1= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP_FAS.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob1.moveTo(250,250);  
    winjob1.focus();
    
}




/**
 * Load Office Details when giving Office ID in Location Text Box 
 */
function loadOffice(id,param)
{   

    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose...)");
    }
    else
    {          
            var url="../../../../../AccountingUnitServlet.con?command=List_Office_Name&cID="+id;                     
            var req=getTransport();
            req.open("Post",url,true);         
            req.onreadystatechange=function()
            {                
                LoadOfficeDetails(req,param);                
            }
            req.send(null);        
    }    
    
     
}


function LoadOfficeDetails(req,param)
{
   if(req.readyState==4)
    {
        
          if(req.status==200)
          {    
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    if(param==true)
                    {
                        document.frmAccountUnit.txtLofficename.value="";
                        var name=response.getElementsByTagName("accountid")[0].firstChild.nodeValue;
                        var add=response.getElementsByTagName("unitname")[0].firstChild.nodeValue;
                        document.frmAccountUnit.txtLofficename.value=add;
                        grid();
                    }
                    else
                    {
                        document.frmAccountUnit.txtofficename.value="";
                        var name=response.getElementsByTagName("accountid")[0];
                        var add=response.getElementsByTagName("unitname")[0].firstChild.nodeValue;
                        document.frmAccountUnit.txtofficename.value=add;
                    }
                 }
                else if (flag=="NotFound")
                {
                   alert("Office Not Found")
                }
                
                else if(flag=="failure")
                {
                   alert("Error in Loading Office Name ");                    
                }
                
            }
      }
}



/**
 *  Main Function -- Addition , Deletion and Updation performed inside Grid 
 */
function callServer(command,param)
 {
    
        var txtoffice=document.frmAccountUnit.txtOffice.value;
        var txtofficename=document.frmAccountUnit.txtofficename.value;
        var flag=nullCheck();
        var max1=officecheck();
        if(command=="Add")
        {
              if(flag==true)
              {
                if(max1==false)
                {
                var tbody=document.getElementById("tblList");
                var table=document.getElementById("Existing");
                var mycurrent_row=document.createElement("TR");
                seq=seq+1;
                
                 mycurrent_row.id=seq;
                 
                 
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var url="javascript:loadValuesFromTable('" + seq + "')";              
                 anc.href=url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
             
                 var cell2 =document.createElement("TD");    
                 var txtcadid=document.createTextNode(txtoffice);                         
                 cell2.appendChild(txtcadid);
                 var hidden=document.createElement("input");
                 hidden.type="hidden";
               // hidden.type="text";
                 hidden.name="officeid";
                 hidden.id="officeid"+seq;
                 hidden.value=txtoffice;
                 cell2.appendChild(hidden);
                 mycurrent_row.appendChild(cell2);       

               

                 var cell3 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(txtofficename);                         
                 cell3.appendChild(txtsdesc);       
                 mycurrent_row.appendChild(cell3);
                 
                 tbody.appendChild(mycurrent_row);
                 
                 
                document.frmAccountUnit.txtOffice.value="";
                document.frmAccountUnit.txtofficename.value="";
                }
                else
                {
                    alert("Already Exist");
                    document.frmAccountUnit.txtOffice.value="";
                    document.frmAccountUnit.txtofficename.value="";
                }
            }
        }
        else if(command=="Update")
        {
            var office=document.frmAccountUnit.txtOffice.value;                        
            var r=document.getElementById(officeid);            
            document.getElementById("officeid"+officeid).value=office;            
            var rcells=r.cells;
            var url="javascript:loadValuesFromTable('" + office + "')";  
          
            rcells.item(0).firstChild.url=url;
            rcells.item(1).firstChild.nodeValue=txtoffice;
            rcells.item(2).firstChild.nodeValue=txtofficename;
            
            document.frmAccountUnit.CmdAdd.disabled=false;
            document.frmAccountUnit.CmdUpdate.disabled=true;
            document.frmAccountUnit.CmdDelete.disabled=true;
            document.frmAccountUnit.txtOffice.value="";
            document.frmAccountUnit.txtofficename.value="";
            
            
        }
        else if(command=="Delete")
        {
            var r=document.getElementById(officeid);
            var tbody=document.getElementById("tblList"); 
            var ri=r.rowIndex;
            tbody.deleteRow(r.rowIndex-1);
          
            document.frmAccountUnit.CmdAdd.disabled=false;
            document.frmAccountUnit.CmdUpdate.disabled=true;
            document.frmAccountUnit.CmdDelete.disabled=true;
            document.frmAccountUnit.txtOffice.value="";
            document.frmAccountUnit.txtofficename.value="";
        }
 }
 
 
 
 
/**
 *   Load Values in the corresponding Text Boxes in Grid when clicking Edit Button 
 */ 
function loadValuesFromTable(rid)
{      
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          
          document.frmAccountUnit.txtOffice.value=rcells.item(1).firstChild.nodeValue;
          document.frmAccountUnit.txtofficename.value=rcells.item(2).firstChild.nodeValue;
          
          document.frmAccountUnit.CmdAdd.disabled=true;
          document.frmAccountUnit.CmdUpdate.disabled=false;
          document.frmAccountUnit.CmdDelete.disabled=false;
          officeid=rid;
}


/**
 *   It Loads Grid Values from Database 
 */
function grid()
{

                var txtLOffice=document.frmAccountUnit.txtLOffice.value;
                var txtLofficename=document.frmAccountUnit.txtLofficename.value;                
                
                var tbody=document.getElementById("tblList");
                var table=document.getElementById("Existing");
                var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=1;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
             
                 var cell2 =document.createElement("TD");    
                 var txtcadid=document.createTextNode(txtLOffice);                         
                 cell2.appendChild(txtcadid); 
                 
                 var hidden=document.createElement("input");
                 hidden.type="hidden";
                 hidden.name="officeid";
                 hidden.value=txtLOffice;
                 cell2.appendChild(hidden);
                 mycurrent_row.appendChild(cell2);       

                 var cell3 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(txtLofficename);                         
                 cell3.appendChild(txtsdesc);       
                 mycurrent_row.appendChild(cell3);
                 tbody.appendChild(mycurrent_row);                
}





/**
 *  This is Function is called in Javascript itself 
 */
function nullCheck()
{

    if((document.frmAccountUnit.txtOffice.value=="") || (document.frmAccountUnit.txtOffice.value.length<=0))
    {
        alert("Please Enter or Select Office Id");
        document.frmAccountUnit.txtOffice.focus();
        return false;
    }
    return true;
}




function officecheck()
{
    var tmpoffice=document.frmAccountUnit.txtOffice.value;
    var table=document.getElementById("tblList");
    var rows=table.rows;
    var len=rows.length;
   //alert(len);
    if(len>0)
    {
        var k=0;
        for( k=0;k<len;k++ )
        {
            alert("testing**"+rows[k].cells[1].firstChild.nodeValue);
        	if ( rows[k].cells[1].firstChild.nodeValue == tmpoffice )
            {
                return true;
            }
            
        }
        return false;
        
    }
    else
    {
        return false;
    }
}



/**
 *  Null Check During Form Submit 
 */
function nullcheck1()
{
        if((document.frmAccountUnit.txtaccountname.value=="") || (document.frmAccountUnit.txtaccountname.value.length<=0))
        {
            alert("Enter Accounting Unit Name");
            document.frmAccountUnit.txtaccountname.focus();
            return false;
        }
        if((document.frmAccountUnit.txtLOffice.value=="") || (document.frmAccountUnit.txtLOffice.value.length<=0))
        {
            alert("Enter Location Office");
            document.frmAccountUnit.txtLOffice.focus();
            return false;
        }
        var table=document.getElementById("tblList");
        var rows=table.rows;
        var len=rows.length;
        if(len>0)
        {
            return true;
        }
        else
        {
            alert("No data in Grid");
            return false;
        }
        
        return true;
}




/**
 *  OnLoad Function 
 */
function loadform()
{
    document.frmAccountUnit.txtaccountname.value="";
    document.frmAccountUnit.txtOffice.value="";
    document.frmAccountUnit.txtofficename.value="";
    document.frmAccountUnit.txtLOffice.value="";
    document.frmAccountUnit.txtLofficename.value="";
    var tbody=document.getElementById("tblList");
    var table=document.getElementById("Existing");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }   
}







/**
 *  ListtAll Accounting Units ID's 
 */

function ListAllBudget()
{
    if (winListAllBudget && winListAllBudget.open && !winListAllBudget.closed) 
    {
       winListAllBudget.resizeTo(500,500);
       winListAllBudget.moveTo(250,250); 
       winListAllBudget.focus();
    }
    else
    {
        winListAllBudget=null
    }
        
    winListAllBudget= window.open("../../../../../org/FAS/FAS1/Masters/jsps/ListAllAccounting1.jsp","ListAllAccounting","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winListAllBudget.moveTo(250,250);  
    winListAllBudget.focus();    
}




/**
 *  Clear T Body 
 */
function clearall()
{
    
    var tbody=document.getElementById("tblList");
    var table=document.getElementById("Existing");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }   
}





/** 
 * Calling This Function When clicking Edit Button in List 
 */

function List(accountingunitid,accountunitname,accountunitoffice,accountrenderoffice,length,dteofopen)
{    
    document.frmAccountUnit.txtaccountid.value=accountingunitid;
    document.frmAccountUnit.txtaccountname.value=accountunitname;
/*    if(dteofopen=="--")
    	{
    		document.frmAccountUnit.date_open.value="";
    		document.frmAccountUnit.date_open.disabled=true;
    		document.frmAccountUnit.date_open.readonly=true;
    	}
    else if(dteofopen!="--")
    {
    	document.frmAccountUnit.date_open.value=dteofopen;
		document.frmAccountUnit.date_open.disabled=true;
		document.frmAccountUnit.date_open.readonly=true;
    }*/
    	
    
    var url="../../../../../AccountingUnitServlet.con?command=AccountUnit&AccountUnitId="+accountingunitid;    
    var req=getTransport();
    req.open("Post",url,true); 
    req.onreadystatechange=function()
    {
        LoadValuesResponse_II(req);
    }   
    req.send(null);    
    document.frmAccountUnit.txtHAccountid.value=accountingunitid;     
}


 
function LoadValuesResponse_II(req)
{
    
    if(req.readyState==4)
    {
         if(req.status)
          if(req.status==200)
          {    
          
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                var tbody=document.getElementById("tblList");
                var table=document.getElementById("Existing");
               
                var t=0;
                for(t=tbody.rows.length-1;t>=0;t--)
                {
                    tbody.deleteRow(0);
                }
                var options=response.getElementsByTagName("leng");
                
                
            for(var i=0;i<options.length;i++)
            {
                
                var mycurrent_row=document.createElement("TR");
                var cell1=document.createElement("TD");
                var cell2=document.createElement("TD");
                var cell3=document.createElement("TD");
                var cell4=document.createElement("TD");
                var cell5=document.createElement("TD");
                
                var txtoffice=response.getElementsByTagName("accountid")[0].firstChild.nodeValue;
                var txtunit=response.getElementsByTagName("unitname")[0].firstChild.nodeValue;
             
                var accoffice=response.getElementsByTagName("accofficeid")[0].firstChild.nodeValue;
                var officenameL=response.getElementsByTagName("officenameL")[0].firstChild.nodeValue;
                var Dopening=null;
                Dopening = response.getElementsByTagName("dopening")[0].firstChild.nodeValue;
               
                var accountforofficeid=response.getElementsByTagName("accountforofficeid")[i].firstChild.nodeValue;
                var officename=response.getElementsByTagName("officename")[i].firstChild.nodeValue;
               
                var date_open_tb=document.getElementById("date_open");
                if(Dopening!=null)
                	{
                	
                	document.getElementById("date_open").value=Dopening;
                	//document.frmAccountUnit.date_open.value=Dopening;
                	}
                else if(Dopening==null)
                	{
                	document.getElementById("date_open").value="";
                	//document.frmAccountUnit.date_open.value="";
                	}
			        		document.getElementById("date_open").disabled=true;
			        		document.getElementById("date_open").readOnly=true;
               // date_open_tb.setAttribute('readonly','readonly');

                document.frmAccountUnit.txtLOffice.value=accoffice;
                document.frmAccountUnit.txtLofficename.value=officenameL;
                
                
                if(accoffice==accountforofficeid)
                {
                    var anc=document.createElement("A");       
                    mycurrent_row.id=i;                    
                    var url="javascript:loadValuesFromTable('" + i + "')";                               
                    var txtedit=document.createTextNode("Edit");
                    anc.appendChild(txtedit);
                    cell1.appendChild(anc);
                }
                else
                {
               
                    var anc=document.createElement("A");       
                    mycurrent_row.id=i;                    
                    var url="javascript:loadValuesFromTable('" + i + "')";              
                    anc.href=url;
                    var txtedit=document.createTextNode("Edit");
                    anc.appendChild(txtedit);
                    cell1.appendChild(anc);
                
                }
                
                mycurrent_row.appendChild(cell1);
                
                txtoffice1=document.createTextNode(accountforofficeid);
                cell2.appendChild(txtoffice1);
                mycurrent_row.appendChild(cell2);
                var hidden=document.createElement("input");
                 hidden.type="hidden";
               // hidden.type="text";
                 hidden.name="officeid";
                 hidden.id='officeid'+i;
                // alert(i);
                 hidden.value=accountforofficeid;
                 cell2.appendChild(hidden);
                 mycurrent_row.appendChild(cell2);  
                
                txtunit1=document.createTextNode(officename);
                cell3.appendChild(txtunit1);
                mycurrent_row.appendChild(cell3);
                tbody.appendChild(mycurrent_row);                
                    
           }
                
          }
    }
        

}
    
