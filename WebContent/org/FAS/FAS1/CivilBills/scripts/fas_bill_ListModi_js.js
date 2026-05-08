////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
var seqno=1;
var currentrow;
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
/***************** clear all the Fields *****************/
function ClearAll()
{
    confirm("Do u want to clear all the fields")
    {    
    document.getElementById("txtbillsubcode").value="";
    document.getElementById("txtbillsubdesc").value="";
    document.getElementById("bill_majr_code").selectedIndex=0;
   // document.getElementById("bill_minr_code").selectedIndex=0;
    document.getElementById("bill_minr_code").value="";
    document.getElementById("bill_minr_code_desc").value="";
    ListMajorMinorSub();
    }
}
/********************** Function to list the grid values *****************/
function callServer(comnd)
{
         var maj_code_sel=document.getElementById("bill_majr_code").value;
        var minr_code_sel=document.getElementById("bill_minr_code").value;
        var minr_code_desc=document.getElementById("bill_minr_code_desc").value;
        var sub_code=document.getElementById("txtbillsubcode").value;
        var sub_desc=document.getElementById("txtbillsubdesc").value;
        //alert("Before the servlet is called");
        if(comnd=="Delete")
        {
         var ans=confirm("Do u want to cancel this record");
         if(ans)
        {
        var url="../../../../../fas_bill_transModi_servlet?command=delrec&major_code1=" +maj_code_sel+"&minor_code1="+minr_code_sel+"&minr_code_desc="+minr_code_desc+"&sub_code1="+sub_code;;
        alert("Major code Selected is :"+maj_code_sel);
        alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
                LoadGrid(req);
        };
        req.send(null);
        }
        else
        {
            alert("choose other option");
        }
        }
       else if(comnd=="Update")
        {
        //alert("inside the callserver");
        var url="../../../../../fas_bill_transModi_servlet?command=update&major_code1=" +maj_code_sel+"&minor_code1="+minr_code_sel+"&minr_code_desc="+minr_code_desc+"&sub_code1="+sub_code+"&sub_desc1="+sub_desc;
        //alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
                LoadGrid(req);
        }
        req.send(null);
        }
}
/******** Loads the values in the grid when there is records ****************/
function LoadGrid(req)
{
            if(req.readyState==4)
            {
                  if(req.status==200)
                   {
                              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                              var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
                              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                              //alert(flag);
                              var dynbody = document.getElementById("dynbody"); // dynbody -->     <tbody id="dynbody"></tbody>
                              var dyntable = document.getElementById("dyntable");
                              var t=0;
                              //var len=baseResponse.getElementsByTagName("sub_code").length;
                              if(command=='Delete')
                              {
                                    if(flag=='success')
                                        {
                                                var r=document.getElementById(currentrow);
                                                //alert(r);
                                                //var ri=r.rowIndex;
                                                //dyntable.deleteRow(ri);
                                                alert("Records successfully cancelled");
                                                document.getElementById("bill_majr_code").value="All";
                                                document.getElementById("bill_minr_code").value="All";
                                                document.getElementById("bill_minr_code_desc").value="";
                                                document.getElementById("txtbillsubcode").value="";
                                                document.getElementById("txtbillsubdesc").value="";                                                
                                        }
                            }   
                            else if(command=='Update')
                            {
                                if(flag=='success')
                                {
                                     var r=document.getElementById(currentrow); //alert(r);
                                    var rcells=r.cells;  // all columns or all<td>s    
                                    var mindesc=baseResponse.getElementsByTagName("minrdesc")[0].firstChild.nodeValue;
                                    var updtdesc=baseResponse.getElementsByTagName("subdesc")[0].firstChild.nodeValue;
                                    rcells.item(4).firstChild.nodeValue=mindesc;
                                    rcells.item(6).firstChild.nodeValue=updtdesc;
                                    alert("updated");
                                    document.getElementById("bill_majr_code").value="All";
                                    document.getElementById("bill_minr_code_desc").value="";
                                    document.getElementById("bill_minr_code").value="";
                                    document.getElementById("txtbillsubcode").value="";
                                    document.getElementById("txtbillsubdesc").value="";
                              }
                                else
                                {
                                    alert("Failed to update");
                                }
                            }
                              ListMajorMinorSub();
                   }
            }
}
/****************** Loading the grid vallues to the text  bos ********************/
function loadValuesFromTable(seqno)
        {
                      var r=document.getElementById(seqno); 
                      currentrow=seqno;
                      //alert("assigning seq no:"+currentrow);
                      var rcells=r.cells;                      
                      document.getElementById("bill_majr_code").value=rcells.item(1).firstChild.nodeValue; 
                      document.getElementById("bill_minr_code_desc").value=rcells.item(4).firstChild.nodeValue;
                      document.getElementById("bill_minr_code").value=rcells.item(3).firstChild.nodeValue; 
                      document.getElementById("txtbillsubcode").value=rcells.item(5).firstChild.nodeValue; 
                      document.getElementById("txtbillsubdesc").value=rcells.item(6).firstChild.nodeValue;
        }
        
/***************** Clears the dynamic row by choosing the new Major Code ***************/
function cleardynrow()
        {
                            var dynbody = document.getElementById("dynbody"); // dynbody -->     <tbody id="dynbody"></tbody>
                            var dyntable = document.getElementById("dyntable");
                            var t=0; 
                            for(t=dynbody.rows.length-1;t>=0;t--)
                                                {
                                                   dynbody.deleteRow(0);
                                                }
                            //ListMajorMinorSub();
        }
/********************loads the table with all maj and minr code of sub type ***************/
function loadMinorType()
{
        var MajorCode=document.getElementById("bill_majr_code").value;       
        var bill_minr_code=document.getElementById("bill_minr_code");
       /* var child=bill_minr_code.childNodes;
        for(var i=child.length-1;i>1;i--)
        {
                bill_minr_code.removeChild(child[i]);
        } */
        var url="../../../../../fas_bill_transModi_servlet?command=loadMinorType&MajorCode1="+MajorCode;
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
                handleResponse(req);
       };   
       req.send(null);
}
function ListMajorMinorSub()
{
    //startwaiting(document.fas_bill_list_form);
    var MajorCode=document.getElementById("bill_majr_code").value;

    var MinorCode=document.getElementById("bill_minr_code").value;
  
    var url="../../../../../fas_bill_transModi_servlet?command=MajorMinorSub&MajorCode1="+MajorCode+"&MinorCode1="+MinorCode;
     var req=getTransport();
   //  alert(url);
     req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            };   
                    req.send(null);
}
/********** response function***************/
function handleResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
                var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                var tagcommand=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
               
                if(tagcommand=="loadMinorType")
                {
                                if(flag=="success")
                                {
                                        var option=baseResponse.getElementsByTagName("option");
                                        var MinorCode=document.getElementById("bill_minr_code");
                                        var MinorCode_des=document.getElementById("bill_minr_code_desc");
                                        
                                       /* for(var i=0;i<option.length;i++)
                                        {
                                                var code=option[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                                var desc=option[i].getElementsByTagName("desc")[0].firstChild.nodeValue;
                                               // alert(code+"   "+desc);
                                                 var opt=document.createElement("option");
                                                opt.setAttribute("value",code);
                                                var opttext=document.createTextNode(desc);
                                                opt.appendChild(opttext);
                                                MinorCode.appendChild(opt);
                                                
                                        }*/
                                }
                                cleardynrow();
                }
                else if(tagcommand=="MajorMinorSub")
                {
                                if(flag=="success")
                                {
                                         cleardynrow();
                                        // alert("Records are loaded"); 
                                        var subcoderec;
                                        subcoderec=baseResponse.getElementsByTagName("subcoderec").length;                                        
                                        var data=baseResponse.getElementsByTagName("subcoderec");
                                        if(subcoderec>0)
                                        {
                                                  var dyntable=document.getElementById("dyntable");
                                                  var dynbody=document.getElementById("dynbody");     // dynbody -->     <tbody id="dynbody"></tbody>
                                                 var items=new Array();
                                                 for(var k=0;k<subcoderec;k++)
                                                {
                                                        items[0]=data[k].getElementsByTagName("subcode")[0].firstChild.nodeValue;
                                                        items[1]=data[k].getElementsByTagName("subdesc")[0].firstChild.nodeValue;
                                                        items[2]=data[k].getElementsByTagName("majordesc")[0].firstChild.nodeValue;
                                                        items[3]=data[k].getElementsByTagName("majorcode")[0].firstChild.nodeValue;                                                      
                                                        items[7]=data[k].getElementsByTagName("status")[0].firstChild.nodeValue;
                                                        for(var s=0; s<data[k].getElementsByTagName("minordesc").length; s++){
                                                        	items[4]=data[k].getElementsByTagName("minordesc")[s].firstChild.nodeValue;
                                                            items[5]=data[k].getElementsByTagName("minorcode")[s].firstChild.nodeValue;
                                                            items[6]=data[k].getElementsByTagName("substatus")[s].firstChild.nodeValue;
                                                            var dyn_row=document.createElement("TR");
                                                            dyn_row.id=seqno; 
                                                            //alert("items[6] "+items[6]+" items[7] "+items[7]);
                                                
                                                            var cell1=document.createElement("TD");                                                            
                                                            if(items[6]=='C'||items[7]=='C'){                                                            	
                                                            	var priceSpan = document.createElement("span");
                                                    			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
                                                    			priceSpan.appendChild(document.createTextNode("Cancel"));			
                                                    			cell1.appendChild(priceSpan);                                                    			
                                                            }else{
                                                            	var anc=document.createElement("A");       
                                                                anc.href="javascript:loadValuesFromTable('" +seqno+ "')";
                                                                var txtedit=document.createTextNode("Edit");
                                                                anc.appendChild(txtedit);
                                                                cell1.appendChild(anc);
                                                            }                                                            
                                                            dyn_row.appendChild(cell1);
                                                            
                                                            var cell2 =document.createElement("TD"); 
                                                            cell2.style.display="none";
                                                            var tnode_majcode=document.createTextNode(items[3]);     
                                                            cell2.appendChild(tnode_majcode);       
                                                            dyn_row.appendChild(cell2);       
                                    
                                                            var cell3 =document.createElement("TD");    
                                                            var tnode_majdesc=document.createTextNode(items[2]);                         
                                                            cell3.appendChild(tnode_majdesc);       
                                                            dyn_row.appendChild(cell3);
                                                            
                                                            var cell4 =document.createElement("TD");   
                                                            cell4.style.display="none";
                                                            var tnode_mincode=document.createTextNode(items[0]);                         
                                                            cell4.appendChild(tnode_mincode);       
                                                            dyn_row.appendChild(cell4);
                                                            
                                                            var cell5 =document.createElement("TD");    
                                                            var tnode_mindesc=document.createTextNode(items[1]);                         
                                                            cell5.appendChild(tnode_mindesc);       
                                                            dyn_row.appendChild(cell5);
                                                            
                                                            var cell6 =document.createElement("TD");  
                                                            cell6.style.display="none";
                                                            var tnode_subcode=document.createTextNode(items[5]);                         
                                                            cell6.appendChild(tnode_subcode);       
                                                            dyn_row.appendChild(cell6);
                                                            
                                                            var cell7 =document.createElement("TD");    
                                                            var tnode_subdesc=document.createTextNode(items[4]);                         
                                                            cell7.appendChild(tnode_subdesc);       
                                                            dyn_row.appendChild(cell7);
                                                            var td5 = document.createElement("TD");
                                                    		if(items[6]=="C"||items[7]=='C'){
                                                    			var tdst = document.createTextNode("CANCEL");
                                                    		}else{
                                                    			var tdst = document.createTextNode("LIVE");
                                                    		}
                                                    		td5.appendChild(tdst);
                                                    		dyn_row.appendChild(td5);
                                    
                                                            dynbody.appendChild(dyn_row);
                                                            seqno++;
                                                        }
                                                        
                                                } 
                                                //alert(seqno);
                                      }
                                }
                                else if(flag=="nodata")
                                {
                                           cleardynrow(); 
                                            alert("No records to load");
                                }
                                else
                                {
                                            alert("Failed to load records");
                                }
                }
        }
    }
}