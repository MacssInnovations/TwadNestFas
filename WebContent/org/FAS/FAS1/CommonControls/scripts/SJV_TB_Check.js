
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
 
function SJV_TB_Check()
{
   
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date =  document.getElementById("txtCrea_date").value;
             
             var url="../../../../../SJV_TB_Check.kv?Command=check_SJVTB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
             
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
                check_SJVTB(req);
             }   
             req.send(null);       
             
}

function check_SJVTB(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
         //   alert("comes");
            if(flag=="Success")
              {
              
                  var TB=baseResponse.getElementsByTagName("TB")[0].firstChild.nodeValue;
                  if (TB =="Freeze") 
                  {
                    alert("Supplement TB Frozen");
                    document.getElementById("txtCrea_date").value="1";
                  }  
                  else if ( TB=="NotFreeze") 
                  {                  
                    
                  }  
               
              }
             else if(flag=="Failure")
               {
                 alert("SJV TB Freeze Check Failed");
               }
        }
    }
}
