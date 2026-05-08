var __pagination=5;
totalblock=0;
    var items1;
    var items2;
    var items3;
    var items4;
    var items5;
    var items6;
    var totalblock=0;
    var sno=0;
       
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



    function Get()
    {      //alert('GD');
        var url="";
        url="dis_servlet?command=Get";
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
               processResponse(req);
        }   
        req.send(null);
    }


     function exitmethod()
     {
         self.close();
     }
     
     
     function processResponse(req)
     {   
           if(req.readyState==4)
            {
                 if(req.status==200)
                 {    
                   var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                  
                    var tagCommand=baseResponse.getElementsByTagName("command")[0];
               
                    var command=tagCommand.firstChild.nodeValue; 
                    if(command=="Get")
                    { 
            	            getRow(baseResponse);
                    }
                 }
            }
     }
     
     
     
    function changepagesize()
    {
          __pagination=document.district.cmbpagination.value;
            totalblock=0;
            if(record1.length>0)
            {
                 totalblock=parseInt(record1.length/__pagination);
                 if(record1.length%__pagination!=0)
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
                         }
                        catch(errorObject)
                        {
                            cmbpage.add(option,null);
                        }
                } 
            }
            loadRecordVal(1);
    }
        
        
    function changepage()
    {
        var page=document.district.cmbpage.value;
        loadRecordVal(parseInt(page));
    }



    function getRow(baseResponse)
    {   
          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;  
         
          if(flag=="success")
          {          
                    
                record1=new Array();record2=new Array();
                var display=baseResponse.getElementsByTagName("dis_code");   
                
                for(i=0;i<display.length;i++)
                {
                    record1[i]=baseResponse.getElementsByTagName("dis_code")[i].firstChild.nodeValue;
                    record2[i]=baseResponse.getElementsByTagName("dis_name")[i].firstChild.nodeValue;                                                                    
                    
                }
                totalblock=0;
                if(record1.length>0)
                {
                    totalblock=parseInt(record1.length/__pagination);
                    if(record1.length%__pagination!=0)
                    {
                        totalblock=totalblock+1;
                    }
                    var cmbpage=document.getElementById("cmbpage");
                    try
                    { 
                        cmbpage.innerHTML="";
                    }
                    catch(e){
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
                        }
                        catch(errorObject)
                        {
                        cmbpage.add(option,null);
                        }
                    }  
                
                    loadRecordVal(1);
                }
           }
           else
           {
               var tbody=document.getElementById("tblList");
               try
               {
                    tbody.innerHTML="";
               }catch(e) 
               {
                    tbody.innerText="";
               }
           }
    }
    
        
        
    function loadRecordVal(page)
    {
       // alert("inside loadRecordVal");
        var i=0;
        var c=0;    
        var p=__pagination*(page-1);
       // var sno=0;
        var tbody=document.getElementById("tblList");
        try{tbody.innerHTML="";}
        catch(e) {tbody.innerText="";}
      //  document.pms_area.cmbpage.selectedIndex=page-1;
        for(i=p;i<record1.length && c<__pagination;i++)
        {
                c++;
                sno++;
                var mycurrent_row=document.createElement("TR"); 
                // mycurrent_row.id=sno;
                
                mycurrent_row.id=record1[i];
                
                cell2=document.createElement("TD");
                var anc=document.createElement("A");
                var url="javascript:loadValuesFromTable('" + record1[i] + "')";
                anc.href=url;
                //var txtedit=document.createTextNode("Edit");
                //anc.appendChild(txtedit);
                cell2.appendChild(anc);
                mycurrent_row.appendChild(cell2);
                
                cell1=document.createElement("TD");
                var currentText=document.createTextNode(record1[i]);
                cell1.appendChild(currentText);
                mycurrent_row.appendChild(cell1);
                
                cell1=document.createElement("TD");
                var currentText=document.createTextNode(record2[i]);
                cell1.appendChild(currentText);
                mycurrent_row.appendChild(cell1);
             
                tbody.appendChild(mycurrent_row);
                
        }
        /*This Part Is Used To Move The Next Page Or The Previous Page In The Grid*/
        
        var cell=document.getElementById("divcmbpage");
        cell.style.display="block";
        var cell=document.getElementById("divpage");
        cell.style.display="block";
        try
        {
            cell.innerHTML='/'+totalblock;
        }
        catch(e)
        {
            cell.innerText='/'+totalblock;
        }
        if(page<totalblock)
        {
            var cell=document.getElementById("divnext");
            cell.style.display="block";
            try
            {
                cell.innerHTML="";
            }
            catch(e)
            {
                cell.innerText="";
            }
            var anc=document.createElement("A");
            var url="javascript:loadRecordVal("+(page+1)+")";
            anc.href=url;
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
            try{cell.innerHTML="";}
            catch(e) {cell.innerText="";}
            var anc=document.createElement("A");
            var url="javascript:loadRecordVal("+(page-1)+")";
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
    
    
    
    function loadValuesFromTable(rid)
    {      
        //      alert("loadValuesFromTable(rid)-->"+rid);
        var r=document.getElementById(rid); 
        var rcells=r.cells;
        var tbody=document.getElementById("tblList");
        var table=document.getElementById("Existing");
        document.dis.dis_code.value=rcells.item(2).firstChild.nodeValue;
        document.dis.dis_name.value=rcells.item(2).firstChild.nodeValue;
    }
      
      