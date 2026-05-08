var ch1;
var req=false;
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
 


var rad_val=false;

function callServer()
{

if(document.SearchRecord.search[0].checked)
{

document.SearchRecord.txtCont_Name.value=""
document.SearchRecord.txtRegn_no.disabled=false;
document.SearchRecord.txtRegn_no.focus();
document.SearchRecord.txtDate.disabled=true;
document.SearchRecord.txtCont_Name.disabled=true;
}
else if(document.SearchRecord.search[1].checked)
{
document.SearchRecord.txtRegn_no.value="";
document.SearchRecord.txtCont_Name.value=""
document.SearchRecord.txtDate.disabled=false;
document.SearchRecord.txtDate.focus();
document.SearchRecord.txtRegn_no.disabled=true;
document.SearchRecord.txtCont_Name.disabled=true;
}
else if(document.SearchRecord.search[2].checked)
{
document.SearchRecord.txtRegn_no.value="";
document.SearchRecord.txtCont_Name.disabled=false;
document.SearchRecord.txtCont_Name.focus();
document.SearchRecord.txtRegn_no.disabled=true;
document.SearchRecord.txtDate.disabled=true;
}
}

//function called on clicking the Go Button to fetch the records
function List()
{
  
 if(document.SearchRecord.search[0].checked)
 {
    //alert("1 choice");
   
                  var tbl=document.getElementById("tblList");
                      var i;
                                  
                          for(i=tbl.rows.length;i>0;i--)
                          {        
                            tbl.deleteRow(0);
                          }    
        
       
                          var RegnNo=document.SearchRecord.txtRegn_no.value;
                          
                           var url="";
                           url="../../../../../ListContractor.view?Type=RegnNo&value="+RegnNo;
                          req.open("GET",url,true);        
                          req.onreadystatechange=processResponse;
                          req.send(null);
                          var tbody=document.getElementById("tblList");
   
  }
  
  else if(document.SearchRecord.search[1].checked)
  {
    //alert("2nd choice");
    
                    var tbl=document.getElementById("tblList");
                      var i;
                                  
                          for(i=tbl.rows.length;i>0;i--)
                          {        
                            tbl.deleteRow(0);
                          }    
        
       
                          var RegnDate=document.SearchRecord.txtDate.value;
                          //alert(RegnDate);
                           var url="";
                           url="../../../../../ListContractor.view?Type=RegnDate&date="+RegnDate;
                          req.open("GET",url,true);        
                          req.onreadystatechange=processResponse;
                          req.send(null);
                          var tbody=document.getElementById("tblList");

  }
  
  else 
  {
    //alert("3rd choice");
    
   
                      var tbl=document.getElementById("tblList");
                      var i;
                                  
                          for(i=tbl.rows.length;i>0;i--)
                          {        
                            tbl.deleteRow(0);
                          }    
        
       
                         var CName=document.SearchRecord.txtCont_Name.value;
                         //alert(CName);
                           var url="";
                           url="../../../../../ListContractor.view?Type=CName&name="+CName;
                          // alert(url);
                          req.open("GET",url,true);        
                          req.onreadystatechange=processResponse;
                          req.send(null);
                          var tbody=document.getElementById("tblList");

  
   }
  
}


function processResponse()
    {   
     if(req.readyState==4)
        {
          if(req.status==200)
          {        
            
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              //alert(req.responseText);
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;             
             // alert("inside load")
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
                  if(flag=="success")
                  {
                  //getting the number of rows 
                                    
                  var value=baseResponse.getElementsByTagName("value");
                  var tbody=document.getElementById("tblList");
                  var j=0;
                  sequence=0;
                  
                   for(j=0;j<value.length;j++)
                  {
                         
                         var items=new Array();
                        items[0]=value[j].getElementsByTagName("regslno")[0].firstChild.nodeValue;
                        items[1]=value[j].getElementsByTagName("date")[0].firstChild.nodeValue;
                        items[2]=value[j].getElementsByTagName("cname")[0].firstChild.nodeValue;
                        items[3]=value[j].getElementsByTagName("addr")[0].firstChild.nodeValue;
                        items[4]=value[j].getElementsByTagName("class")[0].firstChild.nodeValue;
                        items[5]=value[j].getElementsByTagName("cid")[0].firstChild.nodeValue;
                      
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=items[0];
                         
                          var cell=document.createElement("TD");
                          var cell4=document.createElement("TD"); 
                          var chk1=document.createElement("<input type='checkbox' name='rad'>");
                        
                          chk1.value=items[5];
                          chk1.onclick=function()
                           {
                            //alert("*"+this.value);
                          
                            ch1=this.value;
                            
                            
                           }
                           
                          cell4.appendChild(chk1);
                          mycurrent_row.appendChild(cell4); 
                      
                          //serial number generation
                     //appending the sno
                     //sequence=0;
                           sequence=sequence+1;
                           var sno=document.createTextNode("" + sequence);  
                     
                           cell.appendChild(sno);  
                      
                           mycurrent_row.appendChild(cell);
                     
                           for(i=0;i<6;i++)
                           {                                           
                               
                               cell2=document.createElement("TD");    
                               
                               
                               var currenttext=document.createTextNode(items[i]);                         
                               cell2.appendChild(currenttext);       
                               mycurrent_row.appendChild(cell2); 
                               
                           }  
                           
                       
                     tbody.appendChild(mycurrent_row); 
                    
                 
                      
                  } 
                  }
                  else
                  {
                      alert("failed to Load values");
                  }
          
       
}  
}
}
function f1()
{
//alert("inside f1"+ch1);
 EditContractor(ch1);
}
//Code To Clear All Fields In PoP UP Window
function f2()
{
//alert("clear all");
      var tbl=document.getElementById("tblList");
      var i;
                                  
      for(i=tbl.rows.length;i>0;i--)
      {        
      tbl.deleteRow(0);
      }
 document.SearchRecord.txtRegn_no.value="";
 document.SearchRecord.txtDate.value="";
 document.SearchRecord.txtCont_Name.value="";
 loadDate();
}


function EditContractor(ch1)
  {
  //alert(ch1);
// alert("inside edit:" +ch1);
  var ContractId=ch1;
 // alert(ContractId);
     
      var url="../../../../../ServletEdit.view?ContractorEdit="+ContractId;
     //alert(url);
      req.open("GET",url,true);
      req.onreadystatechange=proEdit;
      req.send(null);
  }
  
  
  function proEdit()
  {
    if(req.readyState==4)
        {
          if(req.status==200)
          {
            var baseResponse=req.responseXML.getElementsByTagName("select")[0];
            var tag=baseResponse.firstChild.nodeValue; 
             var doc=window.opener.document;
            doc.frmNewRegn.txtContId.value=baseResponse.getElementsByTagName("cid")[0].firstChild.nodeValue;
            doc.frmNewRegn.txtContId1.value=baseResponse.getElementsByTagName("cid")[0].firstChild.nodeValue;
            doc.frmNewRegn.txtContName.value=baseResponse.getElementsByTagName("cname")[0].firstChild.nodeValue;
            doc.frmNewRegn.txtCompName.value=baseResponse.getElementsByTagName("comname")[0].firstChild.nodeValue;
            doc.frmNewRegn.txtadd1.value=baseResponse.getElementsByTagName("addr1")[0].firstChild.nodeValue;
            try{
            doc.frmNewRegn.txtadd2.value=baseResponse.getElementsByTagName("addr2")[0].firstChild.nodeValue;
            }
            catch(e)
            {}
            doc.frmNewRegn.txtadd3.value=baseResponse.getElementsByTagName("addr3")[0].firstChild.nodeValue;
            doc.frmNewRegn.txtCmbDistrict.value=baseResponse.getElementsByTagName("district")[0].firstChild.nodeValue;
            doc.frmNewRegn.txtPincode.value=baseResponse.getElementsByTagName("pincode")[0].firstChild.nodeValue;
            doc.frmNewRegn.txtPhone.value=baseResponse.getElementsByTagName("phone")[0].firstChild.nodeValue;
            try
            {
            doc.frmNewRegn.txtCellNo.value=baseResponse.getElementsByTagName("cellno")[0].firstChild.nodeValue;
            }
            catch(e1)
            {}
            try
            {
            doc.frmNewRegn.txtEmail.value=baseResponse.getElementsByTagName("email")[0].firstChild.nodeValue;
            }
            catch(e2)
            {}
            doc.frmNewRegn.txtOffId.value=baseResponse.getElementsByTagName("offid")[0].firstChild.nodeValue;
            doc.frmNewRegn.txtOffId1.value=baseResponse.getElementsByTagName("offid")[0].firstChild.nodeValue;
            //alert(doc.frmNewRegn.txtOffId.value);
            var offid=doc.frmNewRegn.txtOffId.value ;
            //alert("*"+offid);
         
            
            doc.frmNewRegn.txtOffId.focus();
            doc.frmNewRegn.txtClass.focus();
            //alert("here");
            
            
             doc.frmNewRegn.txtYear.value=baseResponse.getElementsByTagName("year")[0].firstChild.nodeValue;
             doc.frmNewRegn.txtRegnNo.value=baseResponse.getElementsByTagName("regno")[0].firstChild.nodeValue;
             doc.frmNewRegn.txtRegnNo1.value=baseResponse.getElementsByTagName("regno")[0].firstChild.nodeValue;
             doc.frmNewRegn.DateOfRegn.value=baseResponse.getElementsByTagName("dateofreg")[0].firstChild.nodeValue;
             doc.frmNewRegn.DateOfRegn1.value=baseResponse.getElementsByTagName("dateofreg")[0].firstChild.nodeValue;
             doc.frmNewRegn.txtRef_FileNo.value=baseResponse.getElementsByTagName("refno")[0].firstChild.nodeValue;
             doc.frmNewRegn.txtRef_FileNo1.value=baseResponse.getElementsByTagName("refno")[0].firstChild.nodeValue;
             //doc.frmNewRegn.State.value=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
             var chkstate=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
             if(chkstate=='Yes')
             {
             doc.frmNewRegn.State[0].checked=true;
             }
             else
             {
             
             doc.frmNewRegn.State[1].checked=true;
             }
             doc.frmNewRegn.txtClass.value=baseResponse.getElementsByTagName("class")[0].firstChild.nodeValue;
             doc.frmNewRegn.txtRegn_Fees.value=baseResponse.getElementsByTagName("regfees")[0].firstChild.nodeValue;
             doc.frmNewRegn.txtOffId.disabled=true;
             doc.frmNewRegn.txtRegnNo.disabled=true;
             doc.frmNewRegn.DateOfRegn.disabled=true;
             doc.frmNewRegn.txtRef_FileNo.disabled=true;
             closeWindow();
            
             
          }
         }
      }  
      
      function closeWindow()
      {
      self.close();
      }
     
  
 


