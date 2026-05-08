
/**
 *  Browser Type Detection 
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



/**
 *  Check SJV TB Status 
 */
 
function Suppl_Number_Check()
{
            var cb_month="";
            var cb_year="";
   
            try
            {
               cb_month = document.getElementById("txtCB_Month").value;
            }
            catch(e)
            {
               cb_month = document.getElementById("to_txtCB_Month").value;
            }
             
            try
            {
               cb_year = document.getElementById("txtCB_Year").value;             
            }
            catch(e)
            {
               cb_year = document.getElementById("to_txtCB_Year").value;             
            }
            
            
                  
             
             var url="../../../../../Supplement_Number_Check.kv?Command=check_SJVNumber&cb_month="+cb_month+"&cb_year="+cb_year;
       
             
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
                check_Suppl_Number(req);
             }   
             req.send(null);       
           
}
//for all supnos
function Suppl_Number_Check_allsup()
{
            var cb_month="";
            var cb_year="";
   
            try
            {
               cb_month = document.getElementById("txtCB_Month").value;
            }
            catch(e)
            {
               cb_month = document.getElementById("to_txtCB_Month").value;
            }
             
            try
            {
               cb_year = document.getElementById("txtCB_Year").value;             
            }
            catch(e)
            {
               cb_year = document.getElementById("to_txtCB_Year").value;             
            }
            
            
                  
             
             var url="../../../../../Supplement_Number_Check.kv?Command=check_SJVNumber_all&cb_month="+cb_month+"&cb_year="+cb_year;
       
             
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
                check_Suppl_Number(req);
             }   
             req.send(null);       
             
}
function Suppl_Number_CheckNew()
{
            var cb_month="";
            var cb_year="";
            var acc_unit_d="";
              
                cb_month = document.getElementById("txtCB_Month").value;
           //alert("CB month ***********"+cb_month);
                cb_year = document.getElementById("txtCB_Year").value;             
              // alert("CB year ***********"+cb_year);        
                acc_unit_d = document.getElementById("cmbAcc_UnitCode").value;            
           //alert("acc_unit_d ***********"+acc_unit_d);
             
             var url="../../../../../Supplement_Number_Check.kv?Command=check_SJVNumberNew&cb_month="+cb_month+"&cb_year="+cb_year+"&acc_unit_d="+acc_unit_d;
           //alert(url);
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
                check_Suppl_NumberNew(req);
             }   
             req.send(null);       
             
}

function check_Suppl_Number(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="Success")
              {
                  var TB=baseResponse.getElementsByTagName("Suppl_Status")[0].firstChild.nodeValue;
                  if (TB =="Avail") 
                  {
                       /** Get Supplement Combo Object */
                       var txtsupplement_no = document.getElementById("txtsupplement_no");
                       txtsupplement_no.innerHTML="";
                       
                       
                       var suppl_no=baseResponse.getElementsByTagName("suppl_no");                            
                       for(i=0;i<suppl_no.length;i++)
                       {  
                                var option=document.createElement("OPTION");
                                var txt = baseResponse.getElementsByTagName("suppl_no")[i].firstChild.nodeValue;
                                var val = baseResponse.getElementsByTagName("suppl_no")[i].firstChild.nodeValue;
                                option.text=txt;
                                option.value=val;
                                try
                                {
                                    txtsupplement_no.add(option);
                                }
                                catch(errorObject )
                                {
                                    txtsupplement_no.add(option,null);
                                }  
                       }
                       
                       
                  }  
                  else if ( TB=="NotAvail") 
                  {  
                  
                        alert("Supplement Number Not Available");                   
                       
                        var txtsupplement_no = document.getElementById("txtsupplement_no");
                        txtsupplement_no.innerHTML="";
                           
                        var option=document.createElement("OPTION");
                        option.text="-- Select Suppl No. --";
                        option.value="";
                        try
                        {
                            txtsupplement_no.add(option);
                        }
                        catch(errorObject )
                        {
                            txtsupplement_no.add(option,null);
                        }
                  }  
               
              }
             else if(flag=="Failure")
             {
                 alert("Supplement Number Load Failed");
             }
        }
    }
}
function check_Suppl_NumberNew(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="Success")
              {
                  var TB=baseResponse.getElementsByTagName("Suppl_Status")[0].firstChild.nodeValue;
                  if (TB =="Avail") 
                  {
                       /** Get Supplement Combo Object */
                       var txtsupplement_no = document.getElementById("txtsupplement_no");
                       txtsupplement_no.innerHTML="";
                       
                       
                       var suppl_no=baseResponse.getElementsByTagName("suppl_no");                            
                       for(i=0;i<suppl_no.length;i++)
                       {  
                                var option=document.createElement("OPTION");
                                var txt = baseResponse.getElementsByTagName("suppl_no")[i].firstChild.nodeValue;
                                var val = baseResponse.getElementsByTagName("suppl_no")[i].firstChild.nodeValue;
                                option.text=txt;
                                option.value=val;
                                try
                                {
                                    txtsupplement_no.add(option);
                                }
                                catch(errorObject )
                                {
                                    txtsupplement_no.add(option,null);
                                }  
                       }
                       
                       
                  }  
                  else if ( TB=="NotAvail") 
                  {  
                  
                        alert("Supplement Number Not Available");                   
                       
                        var txtsupplement_no = document.getElementById("txtsupplement_no");
                        txtsupplement_no.innerHTML="";
                           
                        var option=document.createElement("OPTION");
                        option.text="-- Select Suppl No. --";
                        option.value="";
                        try
                        {
                            txtsupplement_no.add(option);
                        }
                        catch(errorObject )
                        {
                            txtsupplement_no.add(option,null);
                        }
                  }  
               
              }
             else if(flag=="Failure")
             {
                 alert("Supplement Number Load Failed");
             }
        }
    }
}
