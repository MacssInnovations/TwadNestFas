function AjaxFunction()
    {
        var xmlrequest=false;
        try
            {
               xmlrequest=new ActiveXObject("Msxml2.XMLHTTP"); 
            }
         catch(e1)
          {
                 try
                 {
                     xmlrequest=new ActiveXObject("Microsoft.XMLHTTP"); 
                 }
                 catch(e2)
                 {     
                     xmlrequest=false;
                 }
          }
          if (!xmlrequest && typeof XMLHttpRequest != 'undefined') 
                {
                     xmlrequest=new XMLHttpRequest();
                }
        return xmlrequest;
    } 


function doFunction11(cmd,param)
{  
if(cmd=="checkCode1")
{   
     var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
    document.getElementById("txtAcc_HeadDesc").value="";
     
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
   
  
    if(txtAcc_HeadCode.length>=6)
    {
        var url="../../../../../CivilAgreement?command=checkCode1&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbOffice_code="+cmbOffice_code; 
       var xmlrequest=AjaxFunction();
   	xmlrequest.open("GET",url,true);
   xmlrequest.onreadystatechange=function()
   {
       manipulate(xmlrequest);
   };
   xmlrequest.send(null); 
    }         
}
}

function callString()
{
   var bb= document.forms[0].onbrowse.value;
  // alert("bb"+bb);
   tt=bb.lastIndexOf("\\");
//   alert("tt"+tt);
   lenvar=bb.length;
   //alert("lenvar"+lenvar);
   gg=bb.substr(tt+1,lenvar);
   var onbrowse=gg;
   
//   var cell1=document.getElementById("browseid");
//   cell1.style.display="none";
//   var cell1=document.getElementById("docid");
//   cell1.style.display="block";
//   document.forms[0].setdoc.value=onbrowse;
  
   
}
function callFirm()
    {
       document.forms[0].agreementtype[0].checked=true;
       var cell=document.getElementById("supplementLabel"); 
       cell.style.display="none";
       var cell=document.getElementById("supplementText"); 
       cell.style.display="none";
       document.forms[0].firmContrType[0].checked=true;
       callContractor();
    }
    
function callWork()
{
	 if(document.forms[0].wksup[0].checked==true)
		 {
			 var url="../../../../../CivilAgreement?command=work";
		 }
	 else
	    {
	    var url="../../../../../CivilAgreement?command=supply";
	    }
	 	var xmlrequest=AjaxFunction();
	 	xmlrequest.open("GET",url,true);
	    xmlrequest.onreadystatechange=function()
	    {
	        manipulate(xmlrequest);
	    };
	    xmlrequest.send(null);
}
function callwsDate()
{
	if(document.forms[0].wksup[0].checked==true)//work
	 {
		var ss=document.forms[0].supplyno.value;//wno
		 var url="../../../../../CivilAgreement?command=workdate&work="+ss;
	 }
	else
   {
	var ss=document.forms[0].supplyno.value;//wno
	 var url="../../../../../CivilAgreement?command=supplydate&supply="+ss;
   }
	var xmlrequest=AjaxFunction();
	xmlrequest.open("GET",url,true);
   xmlrequest.onreadystatechange=function()
   {
       manipulate(xmlrequest);
   }
   xmlrequest.send(null);
}
function callContractor()
{
//   var officeid=document.forms[0].officeid.value;
//   alert("officeid"+officeid);
	 var officeid=document.forms[0].cmbOffice_code.value;
  
   var unitid=document.forms[0].cmbAcc_UnitCode.value;
  // alert(unitid+"   "+officeid);
	
   var xmlrequest=AjaxFunction();
  
   /*if(document.forms[0].firmContrType[1].checked==true)
   {
        var url="../../../../../CivilAgreement?command=contractortype&officeid1="+officeid;
   }
   else
   {*/
    var url="../../../../../CivilAgreement?command=firmtype&officeid1="+officeid+"&unitid="+unitid;
   // }
   // alert(url);
    xmlrequest.open("GET",url,true);
    xmlrequest.onreadystatechange=function()
    {
        manipulate(xmlrequest);
    }
    xmlrequest.send(null);
}
function callOriginal()
{
    if(document.forms[0].agreementtype[1].checked==true)
    {
             var cell=document.getElementById("supplementLabel"); 
             cell.style.display="block";
             var cell=document.getElementById("supplementText"); 
             cell.style.display="block";
             var xmlrequest= AjaxFunction();
             var url="../../../../../CivilAgreement?command=supplement";
             xmlrequest.open("GET",url,true);              
             xmlrequest.onreadystatechange=function()
             {
                manipulate(xmlrequest);
             }
             xmlrequest.send(null);
    }
    else
    {
    		 document.forms[0].orgAgreementno.value="";
    		 document.forms[0].orgAgreementdate.value="";
    		 document.forms[0].namework.value="";
             var cell=document.getElementById("supplementLabel"); 
             cell.style.display="none";
             var cell=document.getElementById("supplementText"); 
             cell.style.display="none";
    }
        
}
function callAgreeDate(param)
    {
         var xmlrequest= AjaxFunction();
         var url="../../../../../CivilAgreement?command=suppleDate&OriAgreeNo="+param;
         xmlrequest.open("GET",url,true);              
         xmlrequest.onreadystatechange=function()
         {
            manipulate(xmlrequest);
         }
         xmlrequest.send(null);
    }
function callAuthority()
    {
    	 var offid=document.forms[0].cmbOffice_code.value;
    	 
    	
         var xmlrequest= AjaxFunction();
         var url="../../../../../CivilAgreement?command=authority&offid="+offid;
        
         xmlrequest.open("GET",url,true);    
         
         xmlrequest.onreadystatechange=function()
         {
            manipulate(xmlrequest);
         };
         xmlrequest.send(null);
    }
function callAddress(param)
    {
	 var  firmContrType1;

	 var officeid=document.forms[0].cmbOffice_code.value;
    var  firmContrType1 =document.forms[0].firmContrType.value;  
	 /* if(document.forms[0].firmContrType[0].checked==true)
                 {
              firmContrType1 =document.forms[0].firmContrType[0].value;  
                }
               else
                 {
                   firmContrType =document.forms[0].firmContrType[1].value;  
                 }*/
                  
            
              
                var url="../../../../../CivilAgreement?command=address&firmContrType1="+firmContrType1+"&officeid1="+officeid+"&param1="+param;
                //alert(url);
                var xmlrequest= AjaxFunction();
                xmlrequest.open("GET",url,true);              
                xmlrequest.onreadystatechange=function()
                {
                    manipulate(xmlrequest);
                };
                xmlrequest.send(null);
    }
function clearAll()
    {
            document.getElementById("orgAgreementno").value="";
            document.getElementById("orgAgreementdate").value="";
            document.forms[0].agreementno.value="";
            document.forms[0].agreementdate.value="";
            document.forms[0].namework.value="";
            document.forms[0].firmContrName.value="";
            document.forms[0].address.value="";
            document.forms[0].valueofwork.value="";
            document.forms[0].supplyno.value="";
            document.forms[0].supplydate.value="";
            document.forms[0].tenderno.value="";
            document.forms[0].tenderdate.value="";
            document.forms[0].tenderdetails.value="";
            document.forms[0].from.value="";
            document.forms[0].to.value="";
            document.forms[0].authority.value="";
            document.forms[0].onbrowse.value="";
            document.forms[0].remarks.value=""; 
            document.forms[0].cmbSL_Code.value=""; 
            document.forms[0].cmb_HO_acc_unitid.value="";          
            document.forms[0].txtEmpID_trs.value=""; 
            document.forms[0].txtAcc_HeadCode.value="";          
            document.forms[0].txtAcc_HeadDesc.value=""; 
            
          //  document.forms[0].firmContrName.value=""; 
            
            
       /*     var cmbSL_Code1=document.getElementById(firmContrName);   
            cmbSL_Code1.innerHTML="";       
            var option=document.createElement("OPTION");
            option.text="--Select Code--";
            option.value="";           
                cmbSL_Code1.add(option);*/
           /* }catch(errorObject)
            {
                cmbSL_Code1.add(option,null);
            } */
            
            var cell1=document.getElementById("browseid");
            cell1.style.display="block";           
            
            document.forms[0].onadd.disabled=false;  
            document.forms[0].onupdate.disabled=true;
            document.forms[0].ondelete.disabled=true; 
            
    }
function add()
    {
            var unitid=document.forms[0].cmbAcc_UnitCode.value;
            var offid=document.forms[0].cmbOffice_code.value;
            var agreedate=document.forms[0].agreementdate.value;
            var dt=agreedate.split("/");
            var cashbookyear=dt[2];
            var cashbookmonth=dt[1];
            
		     var finYear;
			 if(cashbookmonth>4)
			 {
				 var f1=cashbookyear;
				 cashbookyear++;
				 var f2=cashbookyear;
				 finYear=f1+"-"+f2;
			 }
			 else
			 {
				 var f4=cashbookyear;
				 cashbookyear--;
				 var f3=cashbookyear;
				 finYear=f3+"-"+f4;
		      }
			 
            var agreetype;
            if(document.forms[0].agreementtype[0].checked==true)
            {
               agreetype ="O";  
            }
            else
            {
                agreetype ="S";  
            }
            var orgAgreementno=document.forms[0].orgAgreementno.value;
            var namework=document.forms[0].namework.value;
            var firmContrType;
            firmContrType =document.forms[0].firmContrType.value;  
           /* if(document.forms[0].firmContrType[0].checked==true)
            {
               firmContrType =document.forms[0].firmContrType[0].value;  
             }
            else
            {
               firmContrType =document.forms[0].firmContrType[1].value;  
            }*/
            var firmContrName=document.forms[0].firmContrName.value;
            var worksup;
            if(document.forms[0].wksup[0].checked==true)
            {
            	worksup =document.forms[0].wksup[0].value;  
            //	alert(worksup);
             }
            else
            {
            	worksup =document.forms[0].wksup[1].value;  
            }
            var valueofwork=document.forms[0].valueofwork.value;
            var supplyno=document.forms[0].supplyno.value;
            var supplydate=document.forms[0].supplydate.value;
            var tenderno=document.forms[0].tenderno.value;
            var tenderdate=document.forms[0].tenderdate.value;
            var from=document.forms[0].from.value;
            var to=document.forms[0].to.value;
            var authority=document.forms[0].authority.value;
            var sectionname=document.forms[0].cmb_HO_acc_unitid.value;
            var tendetail=document.forms[0].tenderdetails.value;
            var bb= document.forms[0].onbrowse.value;
            // alert("bb"+bb);
             tt=bb.lastIndexOf("\\");
          //   alert("tt"+tt);
             lenvar=bb.length;
             //alert("lenvar"+lenvar);
             gg=bb.substr(tt+1,lenvar);
             var onbrowse=gg;
            
            var concludedcode=document.forms[0].txtEmpID_trs.value;
            var remarks=document.forms[0].remarks.value;
            var debitaccode=document.forms[0].txtAcc_HeadCode.value; 
            
            
            if(agreedate=="")
                {
                    alert("Enter agreement date");
                    document.forms[0].agreementdate.focus();
                    return false;
                }
            else if(tenderdate=="")
                {
                     alert("Enter tender date");
                     document.forms[0].tenderdate.focus();
                     return false;
                }
            else if(from=="")
                {
                     alert("Enter from date");
                     document.forms[0].from.focus();
                     return false;
                }
            else if(to=="")
            {
                 alert("Enter to date");
                 document.forms[0].to.focus();
                 return false;
            }
            else if(remarks=="")
                {
                     alert("Enter remarks");
                     document.forms[0].remarks.focus();
                     return false;
                }
           
            else
                {
                    var xmlrequest= AjaxFunction();
                    var url="../../../../../CivilAgreement?command=add&unitid1="+unitid+"&offid1="+offid+"&finYear="+finYear+"&agreedate1="+agreedate+"&agreetype1="+agreetype+"&orgAgreementno1="+orgAgreementno+"&namework1="+namework+"&firmContrType1="+firmContrType+"&firmContrName1="+firmContrName+"&value1="+valueofwork+"&worksup="+worksup+"&supplyno1="+supplyno+"&supplydate1="+supplydate+"&tenderno1="+tenderno+"&tenderdate1="+tenderdate+"&from1="+from+"&to1="+to+"&authority1="+authority+"&sectionname1="+sectionname+"&onbrowse1="+onbrowse+"&concludedcode1="+concludedcode+"&remarks1="+remarks+"&tendetail="+tendetail+"&debitaccode="+debitaccode;
                    //alert("add "+url);
                    xmlrequest.open("GET",url,true);              
                    xmlrequest.onreadystatechange=function()
                        {
                            manipulate(xmlrequest);
                        };
                    xmlrequest.send(null);
                }
            }
function update()
    {
	//alert("update ");
            var unitid=document.forms[0].cmbAcc_UnitCode.value;
            var offid=document.forms[0].cmbOffice_code.value;
            var agreeno=document.forms[0].agreementno.value;    
            var agreedate=document.forms[0].agreementdate.value;
            
            var dt=agreedate.split("/");
            var cashbookyear=dt[2];
            var cashbookmonth=dt[1];
            
		     var finYear;
			 if(cashbookmonth>4)
			 {
				 var f1=cashbookyear;
				 cashbookyear++;
				 var f2=cashbookyear;
				 finYear=f1+"-"+f2;
			 }
			 else
			 {
				 var f4=cashbookyear;
				 cashbookyear--;
				 var f3=cashbookyear;
				 finYear=f3+"-"+f4;
		      }
            
            var agreetype;
            if(document.forms[0].agreementtype[0].checked==true)
            {
               agreetype ="O";  
            }
            else
            {
                agreetype ="S";  
            }
           var namework=document.forms[0].namework.value;
            var firmContrType;
            if(document.forms[0].firmContrType.checked==true)
            {
               firmContrType =document.forms[0].firmContrType.value;  
             }
           /* else
            {
               firmContrType =document.forms[0].firmContrType[1].value;  
            }*/
            var firmContrName=document.forms[0].firmContrName.value;
            var worksup;
            if(document.forms[0].wksup[0].checked==true)
            {
            	worksup =document.forms[0].wksup[0].value;  
            	
             }
            else
            	worksup =document.forms[0].firmContrType[1].value;  
            var valueofwork=document.forms[0].valueofwork.value;
            var supplyno=document.forms[0].supplyno.value;
            var supplydate=document.forms[0].supplydate.value;
            var tenderno=document.forms[0].tenderno.value;
            var tenderdate=document.forms[0].tenderdate.value;
            var from=document.forms[0].from.value;
            var to=document.forms[0].to.value;
            var authority=document.forms[0].authority.value;
            var sectionname=document.forms[0].cmb_HO_acc_unitid.value;
            var concludedcode=document.forms[0].txtEmpID_trs.value;
            var remarks=document.forms[0].remarks.value;
            var tendetail=document.forms[0].tenderdetails.value;
            var debitaccode=document.forms[0].txtAcc_HeadCode.value; 
            
            var xmlrequest= AjaxFunction();
            var url="../../../../../CivilAgreement?command=updated&unitid1="+unitid+"&offid1="+offid+"&finYear="+finYear+"&cashbookyear="+cashbookyear+"&cashbookmonth="+cashbookmonth+"&agreeno1="+agreeno+"&agreetype1="+agreetype+"&namework1="+namework+"&firmContrType1="+firmContrType+"&firmContrName1="+firmContrName+"&value1="+valueofwork+"&worksup="+worksup+"&supplyno1="+supplyno+"&supplydate1="+supplydate+"&tenderno1="+tenderno+"&tenderdate1="+tenderdate+"&from1="+from+"&to1="+to+"&authority1="+authority+"&sectionname1="+sectionname+"&concludedcode1="+concludedcode+"&remarks1="+remarks+"&agreedate="+agreedate+"&tendetail="+tendetail+"&debitaccode="+debitaccode;
            //alert("upadte ur l"+url);
           //command=deleted&agreeno1="+agreeno+"&unitid="+unitid+"&offid="+offid+"&finYear="+finYear+"&cashbookyear="+cashbookyear+"&cashbookmonth="+cashbookmonth
            xmlrequest.open("GET",url,true);              
            xmlrequest.onreadystatechange=function()
                {
                    manipulate(xmlrequest);
                }
            xmlrequest.send(null);
            
    }
function deleted()
    {
	//alert("delet ");
			var unitid=document.forms[0].cmbAcc_UnitCode.value;
		    var offid=document.forms[0].cmbOffice_code.value;
            var agreeno=document.forms[0].agreementno.value;
            var agreedate=document.forms[0].agreementdate.value;
            
            var dt=agreedate.split("/");
            var cashbookyear=dt[2];
            alert("cashbookyear"+cashbookyear);
            var cashbookmonth=dt[1];
            alert("cashbookmonth"+cashbookmonth);
            
		     var finYear;
			 if(cashbookmonth>4)
			 {
				 var f1=cashbookyear;
				 cashbookyear++;
				 var f2=cashbookyear;
				 finYear=f1+"-"+f2;
			 }
			 else
			 {
				 alert("else");
				 var f4=cashbookyear;
				 cashbookyear--;
				 var f3=cashbookyear;
				 finYear=f3+"-"+f4;
		      }
			   alert("cashbookyear"+cashbookyear);
			   alert("cashbookmonth"+cashbookmonth);
            var r=confirm("Are U Sure?");
            if(r==true)
                {
                    var xmlrequest= AjaxFunction();
                    var url="../../../../../CivilAgreement?command=deleted&agreeno1="+agreeno+"&unitid1="+unitid+"&offid1="+offid+"&finYear="+finYear+"&agreedate="+agreedate;
                    //var url="../../../../../CivilAgreement?command=deleted&agreeno1="+agreeno+"&unitid="+unitid+"&offid="+offid+"&finYear="+finYear+"&cashbookyear="+cashbookyear+"&cashbookmonth="+cashbookmonth;
                    //alert("delete ur l  "+url);
                    xmlrequest.open("GET",url,true);              
                    xmlrequest.onreadystatechange=function()
                        {
                            manipulate(xmlrequest);
                        }
                    xmlrequest.send(null);
              }        
    }
var winemp;
function listpopup()
    {
        var unit_id=document.getElementById("cmbAcc_UnitCode").value;
        var office_id=document.getElementById("cmbOffice_code").value;
        winemp= window.open("CivilAgreement_List.jsp?unit_id="+unit_id+"&office_id="+office_id,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }
    
function doParentEmp(agno)    
    {   
        clearAll();
        document.forms[0].agreementno.value=agno;
        var url="../../../../../CivilAgreement?command=retrieve&agno1="+agno;
        var xmlrequest= AjaxFunction();
        xmlrequest.open("GET",url,true); 
        xmlrequest.onreadystatechange=function()
            {
              manipulate(xmlrequest);
            };
        xmlrequest.send(null);
              
    }
function manipulate(xmlrequest)
    {
        if(xmlrequest.readyState==4)
        {
           if(xmlrequest.status==200)
            {
        	  // alert("xmlrequest.status "+xmlrequest.status);
                var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
                var tagCommand=baseResponse.getElementsByTagName("command")[0];
                var command=tagCommand.firstChild.nodeValue;
                if(command=="firm")
                {
                    firmTypeChecking(baseResponse);
                }
                else if(command=="loadfirm")
                {
                	loadfirmChecking(baseResponse);
                }
                else if(command=="contractor")
                {
                    contractorTypeChecking(baseResponse);
                }
                else if(command=="authority")
                {
                    authorityChecking(baseResponse);
                }
                else if(command=="address")
                {
                    addressChecking(baseResponse);
                }
                else if(command=="add")
                {
                    alert("record inserted into database successfully");     
                    clearAll();
                }
                else if(command=="updated")
                {
                   updateChecking(baseResponse);
                   clearAll();
                }
                else if(command=="deleted")
                { 
                     alert("record deleted successfully");   
                     clearAll();
                }
                else if(command=="retrieve")
               {
                  retrieveChecking(baseResponse);
               }
               else if(command=="supplement")
               {
                  supplementChecking(baseResponse);
               }
              else if(command=="suppleDate")
               {
                  suppleDateChecking(baseResponse);
               }
              else if(command=="work")
              {
            	  workChecking(baseResponse);
              }
              else if(command=="supply")
              {
            	  supplyChecking(baseResponse);
              }
              else if(command=="workdate")
              {
            	  workdate(baseResponse);
              }
              else if(command=="supplydate")
              {
            	  supplydate(baseResponse);
              }
              else if(command=="checkCode1")
              {
                  loadcheckCode1(baseResponse);
              }
           }
        }
    }
function loadcheckCode1(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
         document.getElementById("txtAcc_HeadCode").value=hid;
         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
       
         document.getElementById("txtAcc_HeadCode").value=hid;
         document.getElementById("txtAcc_HeadDesc").value=hdesc;
  
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }

}
function workdate(baseResponse)
{
	 var workdate=baseResponse.getElementsByTagName("workdate")[0].firstChild.nodeValue;
	 document.forms[0].supplydate.value=workdate;
}
function supplydate(baseResponse)
{
	 var supplydate=baseResponse.getElementsByTagName("supplydate")[0].firstChild.nodeValue;
	 document.forms[0].supplydate.value=supplydate;
}
function workChecking(baseResponse)
{
    var supplyno=document.forms[0].supplyno;
    document.forms[0].supplyno.length=1;
    var workno=baseResponse.getElementsByTagName("workno");
    for(var i=0;i<workno.length;i++)
    {
        var opt=document.createElement('option');
        opt.value=workno[i].firstChild.nodeValue;
        opt.innerHTML=workno[i].firstChild.nodeValue;
        supplyno.appendChild(opt);
    }
}
function supplyChecking(baseResponse)
{
    var supplyno=document.forms[0].supplyno;
    document.forms[0].supplyno.length=1;
    var supno=baseResponse.getElementsByTagName("supplyno");
    for(var i=0;i<supplyno.length;i++)
    {
        var opt=document.createElement('option');
        opt.value=supno[i].firstChild.nodeValue;
        opt.innerHTML=supno[i].firstChild.nodeValue;
        supplyno.appendChild(opt);
    }
}

function firmTypeChecking(baseResponse)
    {
        var firmCombo=document.forms[0].firmContrName;
        document.forms[0].firmContrName.length=1;
        var firmsId=baseResponse.getElementsByTagName("firmsId");
        var firmsName=baseResponse.getElementsByTagName("firmsName");
        for(var i=0;i<firmsId.length;i++)
        {
            var opt=document.createElement('option');
            opt.value=firmsId[i].firstChild.nodeValue;
            opt.innerHTML=firmsName[i].firstChild.nodeValue;
            firmCombo.appendChild(opt);
        }
    }
function loadfirmChecking(baseResponse)
{
	var newCombo=document.forms[0].firmContrName;
    document.forms[0].firmContrName.length=0;
    var firmsId=baseResponse.getElementsByTagName("firmsId");
    var firmsName=baseResponse.getElementsByTagName("firmsName");
    for(var i=0;i<firmsId.length;i++)
    {
        var opt=document.createElement('option');
        opt.value=firmsId[i].firstChild.nodeValue;
        opt.innerHTML=firmsName[i].firstChild.nodeValue;
        newCombo.appendChild(opt);
    }
}
function contractorTypeChecking(baseResponse)
    {
        var contraCombo=document.forms[0].firmContrName;
        document.forms[0].firmContrName.length=1;
        var contractorid=baseResponse.getElementsByTagName("contractorid");
        var contractorname=baseResponse.getElementsByTagName("contractorname");
        for(var i=0;i<contractorid.length;i++)
        {
            var opt=document.createElement('option');
            opt.value=contractorid[i].firstChild.nodeValue;
            opt.innerHTML=contractorname[i].firstChild.nodeValue;
            contraCombo.appendChild(opt);
        }
    }
function addressChecking(baseResponse)
    {
             var address=baseResponse.getElementsByTagName("address");
             document.forms[0].address.value=address[0].firstChild.nodeValue;
    }
function authorityChecking(baseResponse)
    {
         var authoCombo=document.forms[0].authority;
         document.forms[0].authority.length=1;
         var designationid=baseResponse.getElementsByTagName("designationid");
         var designation=baseResponse.getElementsByTagName("designation");
         for(var i=0;i<designationid.length;i++)
         {
                var opt=document.createElement('option');
                opt.value=designationid[i].firstChild.nodeValue;
                opt.innerHTML=designation[i].firstChild.nodeValue;
                authoCombo.appendChild(opt);
         }
    }
function supplementChecking(baseResponse)
    {
            var supcombo=document.forms[0].orgAgreementno;
            document.forms[0].orgAgreementno.length=1;
            var agreeno=baseResponse.getElementsByTagName("agreeno");
            for(var i=0;i<agreeno.length;i++)
            {
                var opt=document.createElement('option');
                opt.value=agreeno[i].firstChild.nodeValue;
                opt.innerHTML=agreeno[i].firstChild.nodeValue;
                supcombo.appendChild(opt);
            }
    }
function  suppleDateChecking(baseResponse)
    {
            var agreedate=baseResponse.getElementsByTagName("agreedate");
            var namework=baseResponse.getElementsByTagName("namework");
            var ledgertypecode=baseResponse.getElementsByTagName("ledgertypecode");
            var ledgercode=baseResponse.getElementsByTagName("ledgercode");
            
            document.forms[0].orgAgreementdate.value=agreedate[0].firstChild.nodeValue;
            document.forms[0].namework.value=namework[0].firstChild.nodeValue;
            if(ledgertypecode[0].firstChild.nodeValue==2)
                document.forms[0].firmContrType[0].checked=true;
            else
                document.forms[0].firmContrType[1].checked=true;
           callContractor();
           document.forms[0].firmContrName.value=ledgercode[0].firstChild.nodeValue;
           var param=document.forms[0].firmContrName.value;
           callAddress(param);
    }
function updateChecking(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
           {   
               alert("Record Updated Successfully.");
               clearAll();
           }
       else
           {
               alert("Failed to update values");
           }                                  
    }
function retrieveChecking(baseResponse)   
    {
	
   var unitid=baseResponse.getElementsByTagName("unitid")[0].firstChild.nodeValue;
   var officeid= baseResponse.getElementsByTagName("officeid")[0].firstChild.nodeValue;
   var agreedate= baseResponse.getElementsByTagName("agreedate")[0].firstChild.nodeValue;
   var agreetype =  baseResponse.getElementsByTagName("agreetype")[0].firstChild.nodeValue;
   var supno=  baseResponse.getElementsByTagName("supno")[0].firstChild.nodeValue;
   var supdate=  baseResponse.getElementsByTagName("supdate")[0].firstChild.nodeValue;
   var namework =  baseResponse.getElementsByTagName("namework")[0].firstChild.nodeValue;
   var ledgertypecode = baseResponse.getElementsByTagName("ledgertypecode")[0].firstChild.nodeValue;
   var ledgercode= baseResponse.getElementsByTagName("ledgercode")[0].firstChild.nodeValue;
   var valuework =  baseResponse.getElementsByTagName("valuework")[0].firstChild.nodeValue;
   var worksupp =  baseResponse.getElementsByTagName("worksupp")[0].firstChild.nodeValue;
   var orderno =  baseResponse.getElementsByTagName("orderno")[0].firstChild.nodeValue;
   var orderdate=  baseResponse.getElementsByTagName("orderdate")[0].firstChild.nodeValue;
   var tenderno =  baseResponse.getElementsByTagName("tenderno")[0].firstChild.nodeValue;
   var tenderdate =  baseResponse.getElementsByTagName("tenderdate")[0].firstChild.nodeValue;
   var periodfrom=  baseResponse.getElementsByTagName("periodfrom")[0].firstChild.nodeValue;
   var periodupto  =  baseResponse.getElementsByTagName("periodupto")[0].firstChild.nodeValue;
   var authority =  baseResponse.getElementsByTagName("authority")[0].firstChild.nodeValue;
   var sectionid =  baseResponse.getElementsByTagName("sectionid")[0].firstChild.nodeValue;
    var documentpath =  baseResponse.getElementsByTagName("documentpath")[0].firstChild.nodeValue;
    var concludedcode =  baseResponse.getElementsByTagName("concludedcode")[0].firstChild.nodeValue;
    var remarks =  baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
    var tendeta =  baseResponse.getElementsByTagName("TENDER_DETAILS")[0].firstChild.nodeValue;
    var debitcode =  baseResponse.getElementsByTagName("DEBIT_ACHEAD")[0].firstChild.nodeValue;
    
       
   // alert(unitid+" officeid "+officeid+" agreedate "+agreedate+" agreetype "+agreetype+"supno "+supno+"supdate "+supdate);
   // alert(" namework "+namework+" ledgertypecode "+ledgertypecode+" ledgercode "+ledgercode+" valuework "+valuework+"worksupp "+worksupp+"orderno "+orderno);
   // alert(" orderdate "+orderdate+" tenderno "+tenderno+" tenderdate "+tenderdate+" periodfrom "+periodfrom+"periodupto "+periodupto+"authority "+authority);
   // alert(" sectionid "+sectionid+" documentpath "+documentpath+" concludedcode "+concludedcode+" remarks "+remarks);
    document.forms[0].cmbAcc_UnitCode.value=unitid;
    document.forms[0].cmbOffice_code.value=officeid;
    document.forms[0].agreementdate.value=agreedate;
    if(agreetype=="O")
     {
          document.forms[0].agreementtype[0].checked=true;
          var cell=document.getElementById("supplementLabel"); 
          cell.style.display="none";
          var cell=document.getElementById("supplementText"); 
          cell.style.display="none";
          document.getElementById("orgAgreementno").value="";
          document.getElementById("orgAgreementdate").value="";
     }
    else
     {
          document.forms[0].agreementtype[1].checked=true;
          callOriginal();
          setTimeout('document.getElementById("orgAgreementno").value=supno',5000);
          document.getElementById("orgAgreementdate").value=supdate;
      }
    document.forms[0].namework.value=namework;
   
    if(ledgertypecode=="2"){
      document.forms[0].firmContrType.checked=true;
    loadFirm(ledgercode);  
    }
    /*else{
      document.forms[0].firmContrType[1].checked=true;
    callContractor();  
    }*/
    document.forms[0].firmContrName.value=ledgercode;
    var param=document.forms[0].firmContrName.value;
    callAddress(param);
    document.forms[0].valueofwork.value=valuework;
 
    if(worksupp=="W")
    {
  	  document.forms[0].wksup[0].checked=true;
    }
    else 
    {
  	  document.forms[0].wksup[1].checked=true;
    }
    callWork();
    //alert(orderno);
    document.forms[0].supplyno.value=orderno;
    document.forms[0].supplydate.value=orderdate;
    document.forms[0].tenderno.value=tenderno;
    document.forms[0].tenderdate.value=tenderdate;
    document.forms[0].from.value=periodfrom;
    document.forms[0].to.value=periodupto;
    document.forms[0].authority.value=authority;
    document.forms[0].cmb_HO_acc_unitid.value=sectionid;
    document.forms[0].txtEmpID_trs.value=concludedcode;
    trs_employee(document.forms[0].txtEmpID_trs.value);
   // alert("dddddddd"+documentpath);
    document.forms[0].onbrowse.src=documentpath;
    document.forms[0].remarks.value=remarks;
    document.forms[0].tenderdetails.value=tendeta;
    document.forms[0].txtAcc_HeadCode.value=debitcode;
    doFunction11('checkCode1','null');
    
    document.forms[0].onadd.disabled=true;  
    document.forms[0].onupdate.disabled=false;
    document.forms[0].ondelete.disabled=false;  
	/*
	
	
	
          unitid=baseResponse.getElementsByTagName("unitid");
          officeid= baseResponse.getElementsByTagName("officeid");
          agreedate= baseResponse.getElementsByTagName("agreedate");
          agreetype =  baseResponse.getElementsByTagName("agreetype");
          supno=  baseResponse.getElementsByTagName("supno");
          supdate=  baseResponse.getElementsByTagName("supdate");
          namework =  baseResponse.getElementsByTagName("namework");
          ledgertypecode = baseResponse.getElementsByTagName("ledgertypecode");
          ledgercode= baseResponse.getElementsByTagName("ledgercode");
          valuework =  baseResponse.getElementsByTagName("valuework");
          worksupp =  baseResponse.getElementsByTagName("worksupp");
          orderno =  baseResponse.getElementsByTagName("orderno");
          orderdate=  baseResponse.getElementsByTagName("orderdate");
          tenderno =  baseResponse.getElementsByTagName("tenderno");
          tenderdate =  baseResponse.getElementsByTagName("tenderdate");
          periodfrom=  baseResponse.getElementsByTagName("periodfrom");
          periodupto  =  baseResponse.getElementsByTagName("periodupto");
          authority =  baseResponse.getElementsByTagName("authority");
          sectionid =  baseResponse.getElementsByTagName("sectionid");
          documentpath =  baseResponse.getElementsByTagName("documentpath");
          concludedcode =  baseResponse.getElementsByTagName("concludedcode");
          remarks =  baseResponse.getElementsByTagName("remarks");
          
          
          
          alert(unitid+" officeid "+officeid+" agreedate "+agreedate+" agreetype "+agreetype+"supno "+supno+"supdate "+supdate);
          alert(" namework "+namework+" ledgertypecode "+ledgertypecode+" ledgercode "+ledgercode+" valuework "+valuework+"worksupp "+worksupp+"orderno "+orderno);
          alert(" orderdate "+orderdate+" tenderno "+tenderno+" tenderdate "+tenderdate+" periodfrom "+periodfrom+"periodupto "+periodupto+"authority "+authority);
          alert(" sectionid "+sectionid+" documentpath "+documentpath+" concludedcode "+concludedcode+" remarks "+remarks);
          document.forms[0].cmbAcc_UnitCode.value=unitid[0].firstChild.nodeValue;
          document.forms[0].cmbOffice_code.value=officeid[0].firstChild.nodeValue;
          document.forms[0].agreementdate.value=agreedate[0].firstChild.nodeValue;
          if(agreetype[0].firstChild.nodeValue=="O")
           {
                document.forms[0].agreementtype[0].checked=true;
                var cell=document.getElementById("supplementLabel"); 
                cell.style.display="none";
                var cell=document.getElementById("supplementText"); 
                cell.style.display="none";
                document.getElementById("orgAgreementno").value="";
                document.getElementById("orgAgreementdate").value="";
           }
          else
           {
                document.forms[0].agreementtype[1].checked=true;
                callOriginal();
                setTimeout('document.getElementById("orgAgreementno").value=supno[0].firstChild.nodeValue',5000);
                document.getElementById("orgAgreementdate").value=supdate[0].firstChild.nodeValue;
            }
          document.forms[0].namework.value=namework[0].firstChild.nodeValue;
         
          if(ledgertypecode[0].firstChild.nodeValue=="2"){
            document.forms[0].firmContrType[0].checked=true;
          loadFirm(ledgercode[0].firstChild.nodeValue);  
          }
          else{
            document.forms[0].firmContrType[1].checked=true;
          callContractor();  
          }
          document.forms[0].firmContrName.value=ledgercode[0].firstChild.nodeValue;
          var param=document.forms[0].firmContrName.value;
          callAddress(param);
          document.forms[0].valueofwork.value=valuework[0].firstChild.nodeValue;
       
          if(worksupp[0].firstChild.nodeValue=="W")
          {
        	  document.forms[0].wksup[0].checked=true;
          }
          else 
          {
        	  document.forms[0].wksup[1].checked=true;
          }
          callWork();
          alert(orderno[0].firstChild.nodeValue);
          document.forms[0].supplyno.value=orderno[0].firstChild.nodeValue;
          document.forms[0].supplydate.value=orderdate[0].firstChild.nodeValue;
          document.forms[0].tenderno.value=tenderno[0].firstChild.nodeValue;
          document.forms[0].tenderdate.value=tenderdate[0].firstChild.nodeValue;
          document.forms[0].from.value=periodfrom[0].firstChild.nodeValue;
          document.forms[0].to.value=periodupto[0].firstChild.nodeValue;
          document.forms[0].authority.value=authority[0].firstChild.nodeValue;
          document.forms[0].cmb_HO_acc_unitid.value=sectionid[0].firstChild.nodeValue;
          document.forms[0].txtEmpID_trs.value=concludedcode[0].firstChild.nodeValue;
          trs_employee(document.forms[0].txtEmpID_trs.value);
          alert("dddddddd"+documentpath[0].firstChild.nodeValue);
          document.forms[0].onbrowse.src=documentpath[0].firstChild.nodeValue;
          document.forms[0].remarks.value=remarks[0].firstChild.nodeValue;
           
          document.forms[0].onadd.disabled=true;  
          document.forms[0].onupdate.disabled=false;
          document.forms[0].ondelete.disabled=false;  */

    }
function loadFirm(ledcode)
{
	   var officeid=document.forms[0].cmbOffice_code.value;
	   var unitid=document.forms[0].cmbAcc_UnitCode.value;
	   var ledcode=ledcode;
	   var xmlrequest=AjaxFunction();
	  
	   if(document.forms[0].firmContrType.checked==true)
	   {
		   var url="../../../../../CivilAgreement?command=loadfirm&officeid1="+officeid+"&unitid="+unitid+"&ledcode="+ledcode;
	   }
	  // alert(" loadfirm "+url);
	    xmlrequest.open("GET",url,true);
	    xmlrequest.onreadystatechange=function()
	    {
	        manipulate(xmlrequest);
	    }
	    xmlrequest.send(null);
}
function clear_Combo(combo)
{
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";       
        var option=document.createElement("OPTION");
        option.text="--Select Code--";
        option.value="";        
        try
        {        
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}
function numbersonlyallowed(e,t)
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
                return false ;
        }
     }