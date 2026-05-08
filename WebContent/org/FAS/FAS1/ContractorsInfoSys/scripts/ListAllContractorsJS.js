//XMLHttpRequest Object Coding ///


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


function callServer()
{

    var accountunitid=document.frmAccountList.cmbAccountUnit.value;
    if(accountunitid!=0)
    {
    var url="../../../../../AccountingUnitServlet.con?command=List&AccountUnitId="+accountunitid;
    
    var req=getTransport();
    req.open("Post",url,true); 
    req.onreadystatechange=function()
        {
           LoadValuesResponse(req);
        }   
         req.send(null);
    }
}

function LoadValuesResponse(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {    
          
                var response=req.responseXML.getElementsByTagName("response")[0];
                
                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                
                if(flag=="failure")
                {
                     
                    alert("No Record exists");
                    s=0;
                    var tbody=document.getElementById("tbody");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                }
                else if(flag=="success")
                {  
                
                
                var tbody=document.getElementById("tblList");
                var table=document.getElementById("Existing");
               
                if(tbody.rows.length >0)
                    {       
                            //alert(tbody.innerText !='undefined'  && tbody.innerText !=null );
                            if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                    tbody.innerText='';
                            else 
                                tbody.innerHTML='';
                            
                           
                    }
                var t=0;
                for(t=tbody.rows.length-1;t>=0;t--)
                {
                    tbody.deleteRow(0);
                }
              //  service=baseResponse.getElementsByTagName("leng");
            
                    
                var options=response.getElementsByTagName("leng");
                if(options)
                {
                
                   var i=0;
                                    totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(options.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                   var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   } 
                
                                    for(var i=0;i<totalblock.length;i++)
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
 /*                var mycurrent_row=document.createElement("TR");
                var cell1=document.createElement("TD");
                var cell2=document.createElement("TD");
                var cell3=document.createElement("TD");
                var cell4=document.createElement("TD");
                var cell5=document.createElement("TD");
                
                var txtoffice=response.getElementsByTagName("accountid")[i].firstChild.nodeValue;
                var txtunit=response.getElementsByTagName("unitname")[i].firstChild.nodeValue;
                var accoffice=response.getElementsByTagName("accofficeid")[i].firstChild.nodeValue;
                var accforofficeid=response.getElementsByTagName("accforofficeid")[i].firstChild.nodeValue;
                var officename=response.getElementsByTagName("officename")[i].firstChild.nodeValue;
                var anc=document.createElement("A");       
                mycurrent_row.id=i;                    
                var url="javascript:loadValuesFromTable('" + i + "')";              
                anc.href=url;
                var txtedit=document.createTextNode("Edit");
                anc.appendChild(txtedit);
                cell1.appendChild(anc);
                mycurrent_row.appendChild(cell1);
                
                txtoffice1=document.createTextNode(txtoffice);
                cell2.appendChild(txtoffice1);
                mycurrent_row.appendChild(cell2);
                var hidden=document.createElement("input");
                 hidden.type="hidden";
                 hidden.name="txtunit";
                 hidden.value=txtunit;
                 cell2.appendChild(hidden);
                 mycurrent_row.appendChild(cell2);  
                
              
                
                accforofficeid1=document.createTextNode(accforofficeid);
                cell3.appendChild(accforofficeid1);
                mycurrent_row.appendChild(cell3);
                
                officename=document.createTextNode(officename);
                cell4.appendChild(officename);
                mycurrent_row.appendChild(cell4);
                tbody.appendChild(mycurrent_row);   */
                }
                
                loadPage(1);
          }
    }
        

}
}
}


function Minimize() 
{
//alert("called");
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}
function loadValuesFromTable(id)
{

    Minimize();
    var r=document.getElementById(id);
    var rcells=r.cells;
 //   alert("show");
    
  //  alert(rcells)
    var table=document.getElementById("tblList");
  //  alert(table);
   // alert("show");
    var rows=table.rows;
   // alert(rows);
    var value;
    var length=rows.length;
  
    var contratco_name=rcells.item(1).firstChild.nodeValue;
   // alert(contratco_name);
    var address=rcells.item(2).firstChild.nodeValue;
   // alert(address);
    var reg_slno=rcells.item(3).firstChild.nodeValue;
  //  alert(reg_slno);
    var reg_date=rcells.item(4).firstChild.nodeValue;
   // alert(reg_date);
   var class_id=rcells.item(5).firstChild.nodeValue;
   // alert(class_id);
    var phone=rcells.item(6).firstChild.nodeValue;
    //alert(phone);
     var email=rcells.item(7).firstChild.nodeValue;
    //alert(email);
    var juris=rcells.item(8).firstChild.nodeValue;
    //alert(juris);
    var contid=rcells.item(9).firstChild.nodeValue;
    //alert(contid);
     var date_upto=rcells.item(10).firstChild.nodeValue;
     
     var statewise_coverage = rcells.item(11).firstChild.nodeValue;
     var region_alias_code = rcells.item(12).firstChild.nodeValue;
      
     
    //alert(date_upto);
    opener.List(contratco_name,address,reg_slno,reg_date,class_id,phone,email,juris,contid,date_upto,length, statewise_coverage, region_alias_code);
    self.close();
}


function changepage()
{

var page=document.frmAccountList.cmbpage.value;
alert(page);
loadPage(parseInt(page));

}  



function loadPage(page)
{
//alert("calls");
  /*          var i=0;
            var c=0;
            var p=__pagination*(page-1);
         // alert(page);
            document.frmAccountList.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tbody");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                  
                   //alert(service.length);
             if(options)
                    {
                    ///////////////////////////////
                   
                   
                  s=0;
                  
                   var i=0;
                         
                 
                for(i=p;i<options.length&& c<__pagination;i++)
                {
                     c++;
                     var seq=0;
                         var items=new Array();
                         //service[i].getElementsByTagName("pay_date")[0].firstChild.nodeValue
                         
                         //alert('test1'+service[i].getElementsByTagName("pay_no")[0].firstChild.nodeValue);
                         items[0]=service[i].getElementsByTagName("pay_no")[0].firstChild.nodeValue;  //  <!-- Payment Voucher number -->
                         items[1]=service[i].getElementsByTagName("pay_date")[0].firstChild.nodeValue; 
                         items[2]=service[i].getElementsByTagName("Challan_no")[0].firstChild.nodeValue;   
                         items[3]=service[i].getElementsByTagName("challan_date")[0].firstChild.nodeValue;   //******* remitted on is filled with challan date, bcoz both are same
                         items[4]=service[i].getElementsByTagName("Amount")[0].firstChild.nodeValue;
                         items[5]=service[i].getElementsByTagName("userName")[0].firstChild.nodeValue; 
                         
                        var tbody=document.getElementById("tbody");
                        var mycurrent_row=document.createElement("TR");
                        seq=parseInt(seq)+1;
                        var mycurrent_row=document.createElement("TR");         // serial number generation
                          cell2=document.createElement("TD");
                           var currentText=document.createTextNode(seq);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                      
                      //for(j=0;j<=5;j++) 
                      for(j=2;j<=5;j++)         // here it's start from j=2 , bcoz we removed payment Vr.No and Date from the user view..
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
                        //--------------------------------------- for details----
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:Show('"+Ucode+"','"+Offid+"','"+items[2]+"','"+items[3]+"','"+items[4]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("Show Details");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
                      
                        tbody.appendChild(mycurrent_row);
                       
                      
                         // alert('ok');        
                        //tbody.appendChild(mycurrent_row);
                       
                }
              }          
            
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
               
                if(navigator.appName.indexOf("Microsoft")!=-1)
                    cell.innerText= ' / ' +totalblock;
                else
                    cell.innerHTML= ' / ' +totalblock;
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
            
            }*/
}
