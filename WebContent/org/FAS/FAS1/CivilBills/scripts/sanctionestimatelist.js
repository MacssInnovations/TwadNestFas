var __pagination=11;
var rec;
var seq=0;
var totalblock=0;
var emp_id="";
var s=0;

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
function callGridList()
{
            var acc_unit_id=document.getElementById("cmbAcc_UnitCode").value;
            var acc_unit_name=document.frmSanctionEstimateList.cmbAcc_UnitCode.options[document.frmSanctionEstimateList.cmbAcc_UnitCode.selectedIndex].text;
            var acc_unit_officeid=document.getElementById("cmbOffice_code").value;
            var acc_unit_officename=document.frmSanctionEstimateList.cmbOffice_code.options[document.frmSanctionEstimateList.cmbOffice_code.selectedIndex].text;
            var finyear=document.frmSanctionEstimateList.cmbfinyear.value;
            //alert(finyear);
           
            var tbody=document.getElementById("grid_body");
                  var t=0;
                  for(t=tbody.rows.length-1;t>=0;t--)
                  {
                        tbody.deleteRow(0);
                  }
            
                    var url="";
                    url="../../../../../Sanction_estimate_list?command=LoadGrid&acc_unit_id="+acc_unit_id+"&acc_unit_officeid="+acc_unit_officeid+"&finyear="+finyear;
                    //alert(url);
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                               processResponse1(req);
                    }   
                     req.send(null);
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
function   LoadGrid(baseResponse)
{
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
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
                            rec=baseResponse.getElementsByTagName("leng");
                      
                         if(rec)
                         {
                          
                                 var sanc_est_no=baseResponse.getElementsByTagName("sanc_est_no")[0].firstChild.nodeValue;
                                 var sanc_est_date=baseResponse.getElementsByTagName("sanc_est_date")[0].firstChild.nodeValue;
                                 var acc_head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
                                 var tot_amt=baseResponse.getElementsByTagName("tot_amt")[0].firstChild.nodeValue;
                                 var tbody=document.getElementById("grid_body");
                                    try{tbody.innerHTML="";}
                                    catch(e) {tbody.innerText="";}
                                var i=0;
                                var totalblock=0;
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
/***********************************code for inserting Pagination**************************************************/
function changepage()
{
        var page=document.frmSanctionEstimateList.cmbpage.value;
        loadPage(parseInt(page));
} 

function loadPage(page)
{
        
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
           document.frmSanctionEstimateList.cmbpage.selectedIndex=page-1;
                  var tbody=document.getElementById("grid_body");
                        try{tbody.innerHTML="";}
                            catch(e){tbody.innerText="";}
                                s=0;
                             
                                for(i=p;i<rec.length&&c<__pagination;i++)
                                {
                                   
                                     c++;
                                     var items=new Array();
                                                                        
                                     items[0]=rec[i].getElementsByTagName("sanc_est_no")[0].firstChild.nodeValue;
                                     items[1]=rec[i].getElementsByTagName("sanc_est_date")[0].firstChild.nodeValue;
                                     items[2]=rec[i].getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
                                     items[3]=rec[i].getElementsByTagName("tot_amt")[0].firstChild.nodeValue;
                                 
                                     var tbody=document.getElementById("grid_body");
                                     var mycurrent_row=document.createElement("TR");
                        
                                    for(j=0;j<4;j++)
                                    {
                                        cell2=document.createElement("TD");
                                        cell2.setAttribute('align','right');
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
                                    var url="javascript:Show('"+items[0]+"')";
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
           __pagination=document.frmSanctionEstimateList.cmbpagination.value;
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
//*****************************when DETAILS link clicked********************************//
var Sanction_est_list1;

function Show(sanc_est_no)
{
    if (Sanction_est_list1 && Sanction_est_list1.open && !Sanction_est_list1.closed) 
    {
       Sanction_est_list1.resizeTo(500,500);
       Sanction_est_list1.moveTo(250,250); 
       Sanction_est_list1.focus();
    }
    else
    {
        Sanction_est_list1=null
    }
    
    Sanction_est_list1= window.open("../jsps/Sanction_estimate_popuplist.jsp?sanc_est_no="+sanc_est_no,"SanctionEstimateList1","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
    Sanction_est_list1.moveTo(250,250);  
    Sanction_est_list1.focus();
    
}

window.onunload=function()
{
    if (Sanction_est_list1 && Sanction_est_list1.open && !Sanction_est_list1.closed) 
        Sanction_est_list1.close();
}
/*********************************************************************************************************************/
function loadestimatelist()
{
            var e_id=document.sanction_estimate_popuplist.sanc_est_no.value;
            var url="";
            url="../../../../../Sanction_estimate_list?command=LoadpopupGrid&empl_id="+e_id;
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
                         var rec=baseResponse.getElementsByTagName("length").length;
                         for(var i=0;i<rec;i++)
                         {
                                 var SL_No=baseResponse.getElementsByTagName("SL_No")[i].firstChild.nodeValue;
                                 var assetcode=baseResponse.getElementsByTagName("assetcode")[i].firstChild.nodeValue;
                                 var assetclasscode=baseResponse.getElementsByTagName("assetclasscode")[i].firstChild.nodeValue;
                                 var est_amt=baseResponse.getElementsByTagName("est_amt")[i].firstChild.nodeValue;
                                 var particulars=baseResponse.getElementsByTagName("particulars")[i].firstChild.nodeValue;
                                 
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
                                  
                                   var currentText=document.createTextNode(assetcode);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                                 
                                   cell2=document.createElement("TD");
                                
                                   var currentText=document.createTextNode(assetclasscode);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                                   cell2=document.createElement("TD");
                                
                                   var currentText=document.createTextNode(est_amt);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                    
                                   cell2=document.createElement("TD");
                        
                                   var currentText=document.createTextNode(particulars);
                                   cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                                    cell2=document.createElement("TD");
                                                                                
                               tbody.appendChild(mycurrent_row);
                         }
                  }
}