/********************javascript*******************/
var __pagination=11;
var rec;
var seq=0;
var totalblock=0;
var emp_id="";
var s=0;
/***************** ajax CONCEPT***************/
function getTransport()
{
  var req=false;
  try
  {
      req=new ActiveXObject("Msxml2.XMLHTTP");
  }
  catch(e1)
  {
      try
        {
            req=new ActiveXObject("Microsoft.XMLHTTP");
        }
          catch(e2)
          {
            req=false;
          }
    }
    if (!req && typeof XMLHttpRequest != 'undefined')
    {
      req=new XMLHttpRequest();
    }
  return req;
}

function exitmethod()
{
      window.close();
}

function callGridList()
{
            var acc_unit_officeid=document.getElementById("cmbOffice_code").value;
            //alert(acc_unit_officeid);
            var user_cat_id="";
            var tbody=document.getElementById("grid_body");
                  var t=0;
                  for(t=tbody.rows.length-1;t>=0;t--)
                  {
                        tbody.deleteRow(0);
                  }
            if(document.formPhone_MasterList.rad_cust_type[0].checked)
            {
                    //alert("Data to load for twad users");
                    user_cat_id=1;
                    var url="";
                    url="../../../../../phone_master_servlet?command=LoadGrid&acc_unit_officeid="+acc_unit_officeid+"&user_cat_id="+user_cat_id;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                               processResponse1(req);
                    }   
                     req.send(null);
           }
            else
            {
                    //alert("Data found to Load for Privileged Users");
                    user_cat_id=3;
                    var url="";
                    url="../../../../../phone_master_servlet?command=LoadGrid&acc_unit_officeid="+acc_unit_officeid+"&user_cat_id="+user_cat_id;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                               processResponse1(req);
                    }   
                     req.send(null);
            }
}  
function processResponse1(req)
{   
      if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="LoadGrid")
              {
                    LoadGrid(baseResponse);
              }
              else if(command=="LoadpopupGrid")
              {
                    LoadpopupGrid(baseResponse);
              }
          }
      }
}
/**************************************Records from Phone_master loaded depends on the submit condition****************************************/
function   LoadGrid(baseResponse)
{
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  //alert(flag);
                  var items=new Array();
                  var tbody=document.getElementById("grid_body");
                  var t=0;
                  for(t=tbody.rows.length-1;t>=0;t--)
                  {
                        tbody.deleteRow(0);
                  }
                   if(flag=="failure")
                    {
                        alert("No Record exists");
                        s=0;
                        var tbody=document.getElementById("grid_body");
                        try{tbody.innerHTML="";}
                        catch(e) {tbody.innerText="";}                                     
                       
                        var cell=document.getElementById("divcmbpage");
                        cell.style.display="none";
                        var cell=document.getElementById("divpage");
                        cell.style.display="none";
                        var cell=document.getElementById("divnext");
                        cell.style.display="none";
                        var cell=document.getElementById("divpre");
                        cell.style.display="none";
                    }
                    else if(flag=="success")
                    {                       
                         var tbody=document.getElementById("grid_body");
                            if(tbody.rows.length >0)
                            {       
                                    if(tbody.innerText !='undefined'&&tbody.innerText !=null)
                                            tbody.innerText='';
                                    else 
                                        tbody.innerHTML='';
                            }
                            rec=baseResponse.getElementsByTagName("emp_det");
                         if(rec)
                         {
                                 var acc_unit_id=baseResponse.getElementsByTagName("Acc_unit_id")[0].firstChild.nodeValue;
                               //  alert("acc_unit_id"+acc_unit_id);
                                 var acc_unit_officeid=baseResponse.getElementsByTagName("Acc_unit_officeid")[0].firstChild.nodeValue;
                                 var emp_id=baseResponse.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                                 var user_cat_id=baseResponse.getElementsByTagName("user_cat_id")[0].firstChild.nodeValue;
                                if(user_cat_id==1)
                                {
                                    items[0]="TWAD"; 
                                }
                                else
                                {
                                        items[0]="OTHERS"; 
                                }
                                var tbody=document.getElementById("grid_body");
                                    try{tbody.innerHTML="";}
                                    catch(e) {tbody.innerText="";}
                                var i=0;
                                totalblock=0;
                            if(rec.length>0)
                            {
                                    totalblock=parseInt(rec.length/__pagination);
                                    if(rec.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    var cmbpage=document.getElementById("cmbpage");
                                   try
                                   { 
                                        cmbpage.innerHTML="";
                                   }
                                   catch(e)
                                   {
                                        cmbpage.innerText="";
                                   }
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
                        }
                    }
                    else if(flag=="nodata")
                   {
                            alert("No data match to load");
                   }
}
//*****************************when DETAILS link clicked********************************//
var Phone_list_SL1;

function Show(emp_id)
{
    //alert(emp_id);
    if (Phone_list_SL1 && Phone_list_SL1.open && !Phone_list_SL1.closed) 
    {
       Phone_list_SL1.resizeTo(500,500);
       Phone_list_SL1.moveTo(250,250); 
       Phone_list_SL1.focus();
    }
    else
    {
        Phone_list_SL1=null
    }
    //Phone_list_SL1= window.open("../jsps/Phone_List_jsp.jsp"); 
    Phone_list_SL1= window.open("../jsps/Phone_List_jsp.jsp?emp_id="+emp_id,"PhoneDetails1","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
    Phone_list_SL1.moveTo(250,250);  
    Phone_list_SL1.focus();
    
}

window.onunload=function()
{
    if (Phone_list_SL1 && Phone_list_SL1.open && !Phone_list_SL1.closed) 
        Phone_list_SL1.close();
}
//***********************************************************************************************//
function loadphoneDetails()
{
            var e_id=document.formPhone_List.empl_id.value;
            //alert(e_id);
            var url="";
            url="../../../../../phone_master_servlet?command=LoadpopupGrid&empl_id="+e_id;
            var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
                       processResponse1(req);
            }   
             req.send(null);
}  

function   LoadpopupGrid(baseResponse)
{
                  
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  var items=new Array();
                 
                  if(flag=="success")
                  {                       
                         var rec=baseResponse.getElementsByTagName("emp_det_trn").length;
                         for(var i=0;i<rec;i++)
                         {
                                 var SL_No=baseResponse.getElementsByTagName("SL_No")[i].firstChild.nodeValue;
                                 var Purpose=baseResponse.getElementsByTagName("Purpose")[i].firstChild.nodeValue;
                                 var Connection_type=baseResponse.getElementsByTagName("Connection_type")[i].firstChild.nodeValue;
                                 var Usage_details=baseResponse.getElementsByTagName("usage_details")[i].firstChild.nodeValue;
                                 var STD_code=baseResponse.getElementsByTagName("STD_code")[i].firstChild.nodeValue;
                                 var phone_no=baseResponse.getElementsByTagName("phone_no")[i].firstChild.nodeValue;
                                 var SP_name=baseResponse.getElementsByTagName("SP_name")[i].firstChild.nodeValue;
                                 var SP_type=baseResponse.getElementsByTagName("SP_type")[i].firstChild.nodeValue;
                                 var Ceil_type=baseResponse.getElementsByTagName("Ceil_type")[i].firstChild.nodeValue;
                                 var Ceil_amt=baseResponse.getElementsByTagName("Ceil_amt")[i].firstChild.nodeValue;
                                 var particulars=baseResponse.getElementsByTagName("particulars")[i].firstChild.nodeValue;
                                
                                   var Purpose1="";
                                   if(Purpose=="O")
                                        {
                                            Purpose1="Office";
                                        }
                                    else if(Purpose=="R")
                                        {
                                            Purpose1="Residence";
                                        }
                                     else if(Purpose=="F")
                                        {
                                            Purpose1="Fax";
                                        }
                                    var Connection_type1="";
                                   if(Connection_type=="L")
                                        {
                                            Connection_type1="Landline";
                                        }
                                    else if(Connection_type=="M")
                                        {
                                            Connection_type1="Mobile";
                                        }
                                    var Usage_details1="";
                                   if(Usage_details=="IU")
                                        {
                                            Usage_details1="In Use";
                                        }
                                    else if(Usage_details=="ED")
                                        {
                                            Usage_details1="Ex Dir";
                                        }
                                    else if(Usage_details=="SC")
                                        {
                                            Usage_details1="Safe Custody";
                                        }
                                    else if(Usage_details=="DC")
                                        {
                                            Usage_details1="Disconnected";
                                        }
                                    var Ceil_type1="";
                                   if(Ceil_type=="L")
                                        {
                                            Ceil_type1="Limited";
                                        }
                                    else if(Ceil_type=="U")
                                        {
                                            Ceil_type1="UnLimited";
                                        }
                                    else if(Ceil_type=="N")
                                        {
                                            Ceil_type1="Not Applicable";
                                        }
                                        
                                   
                                   var tbody=document.getElementById("tbody");
                                   var mycurrent_row=document.createElement("TR");
                                   seq=seq+1;
                                   mycurrent_row.id=seq;
                                    
                                   var cell2;
                                   cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(SL_No);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                            
                                   cell2=document.createElement("TD"); 
                                  
                                   var currentText=document.createTextNode(Purpose1);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                                 
                                   cell2=document.createElement("TD");
                                
                                   var currentText=document.createTextNode(Connection_type1);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                                   cell2=document.createElement("TD");
                                
                                   var currentText=document.createTextNode(Usage_details1);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                    
                                   cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(STD_code);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                                    cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(phone_no);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                                    cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(SP_name);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                                    cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(SP_type);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                                    cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(Ceil_type1);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                                    cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(Ceil_amt);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                    
                                    cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(particulars);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                           
                               tbody.appendChild(mycurrent_row);
                         }
                  }
}
// ***********************************Pagination coding*************************************************************//
function btncancel()
{
 self.close();
}
function delexistingrows()
{
                  var tbody=document.getElementById("grid_body");
                  var t=0;
                  for(t=tbody.rows.length-1;t>=0;t--)
                  {
                        tbody.deleteRow(0);
                  }
                  alert("Refersh the grid rows");
}
/***********************************code for inserting Pagination**************************************************/
function changepage()
{
        var page=document.formPhone_MasterList.cmbpage.value;
        loadPage(parseInt(page));
}  

function loadPage(page)
{
            //alert("load page********");
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
            document.formPhone_MasterList.cmbpage.selectedIndex=page-1;
                  var tbody=document.getElementById("grid_body");
                        try{tbody.innerHTML="";}
                            catch(e){tbody.innerText="";}
                                s=0;
                                for(i=p;i<rec.length&&c<__pagination;i++)
                                { 
                                     c++;
                                     var items=new Array();
                                     alert(rec[i].getElementsByTagName("Acc_unit_id")[0]);
                                     items[0]=rec[i].getElementsByTagName("Acc_unit_id")[0].firstChild.nodeValue;
                                     items[1]=rec[i].getElementsByTagName("Acc_unit_officeid")[0].firstChild.nodeValue;
                                     items[2]=rec[i].getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                                     items[3]=rec[i].getElementsByTagName("user_cat_id")[0].firstChild.nodeValue;
                                    //alert(items[3]);
                                     var tbody=document.getElementById("grid_body");
                                     var mycurrent_row=document.createElement("TR");
                        
                                    for(j=0;j<4;j++)
                                    {
                                        cell2=document.createElement("TD");
                                        cell2.setAttribute('align','left');
                                        if(items[j]!="null")
                                        {
                                            var currentText=document.createTextNode(items[j]);
                                        }
                                        else
                                        {
                                            var currentText=document.createTextNode('');
                                        }
                                        cell2.appendChild(currentText);
                                        mycurrent_row.appendChild(cell2);
                                    }
                                    var cell=document.createElement("TD");
                                    cell.align='CENTER';
                                    var anc=document.createElement("A");
                                    var url="javascript:Show('"+items[2]+"')";
                                    anc.href=url;
                                    var txtedit=document.createTextNode("DETAILS");
                                    anc.appendChild(txtedit);
                                    cell.appendChild(anc);
                                    mycurrent_row.appendChild(cell);
                              
                                    tbody.appendChild(mycurrent_row);
                            }
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                //anc.setAttribute('style','text-decoratin:none');
                var txtedit=document.createTextNode("<<Next>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
             if(page>1)
            {
                    var cell=document.getElementById("divpre");
                    cell.style.display="block";
                    //cell.innerText='';
                    try{cell.innerHTML="";}
                      catch(e) {cell.innerText="";}
                    var anc=document.createElement("A");
                    var url="javascript:loadPage("+(page-1)+")";
                    anc.href=url;
                    var txtedit=document.createTextNode("<<Previous>>");
                    anc.appendChild(txtedit);
                    cell.appendChild(anc);
            }
            else
            {
                    var cell=document.getElementById("divpre");
                    cell.style.display="block";
                    try{cell.innerHTML="";}
                      catch(e) {cell.innerText="";}
            }
}

function changepagesize()
{
           __pagination=document.formPhone_MasterList.cmbpagination.value;
        if(rec)
        {
                            totalblock=0;
                            if(rec.length>0)
                            {
                                    totalblock=parseInt(rec.length/__pagination);
                                    if(rec.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
        }
}
