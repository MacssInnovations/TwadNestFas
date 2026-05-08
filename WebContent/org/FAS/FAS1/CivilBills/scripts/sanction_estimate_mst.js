var seq=0;
var com_id;var val;
/////////////////////////////creating AJAX object////////////////////////
var emp_flag;
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
/////////////////////////////////code to 
textarea//////////////////////////////////////////////////////
function check_leng(param,val)
{	 
		if((val.length)>=190)
		{
			  if(param=='remarks')			  
				  	   alert("Please Enter Remarks below 200 characters");			           			  
			  else			  
				  	   alert("Please Enter Paticulars below 200 characters");
                          		  
		}
		
}
///////////////////////////////exit mathod////////////////////////////////////////////////////////////
function exitmethod()
{
      window.close();
}


//////////////////////////////////////////////////////////////////////////////////////////////////////
function loadasset()
{ 
        //alert("Load asset code");
        var url="../../../../../Sanction_estimate_mst?command=loadAssetClass";
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse(req);
        }   
        req.send(null);
}

function loadassetcode()
{
        var acc_unit_id=document.getElementById("cmbAcc_UnitCode").value;
        var acc_office_id=document.getElementById("cmbOffice_code").value;
        var fin_year=document.formsanc_estimate.cmbSanction_Estimate_FY.options[document.formsanc_estimate.cmbSanction_Estimate_FY.selectedIndex].text;
        
        var url="../../../../../Sanction_estimate_mst?command=loadAssetCode&acc_unit_id="+acc_unit_id+"&acc_office_id="+acc_office_id+
        "&fin_year="+fin_year;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse(req);
        }   
        req.send(null);
}
function loadassetmajclass()
{
    
    var assetcode=document.formsanc_estimate.cmbasset_code.options[document.formsanc_estimate.cmbasset_code.selectedIndex].text;
    
    //alert("inside asset major class"+assetcode); 
     var url="../../../../../Sanction_estimate_mst?command=loadAssetmajClass&assetcode1="+assetcode;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
                processResponse(req);
        }   
        req.send(null);
}
function processResponse(req)
{   
    if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="loadAssetClass")
              {
                    LoadAssetClass(baseResponse);
              }
              else if(command=="loadAssetmajClass")
              {
                    LoadAssetmajClass(baseResponse);
              }
              else if(command=="loadAssetCode")
              {
                    LoadAssetCode(baseResponse);
              }
              else if(command=="loadempdetails")
              {
                    LoadEmpDetails(baseResponse);
              }
              else if(command=="addResponse")
              {
                    //alert(command);
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                        var SancEstiNo=baseResponse.getElementsByTagName("sanc_esti_no")[0].firstChild.nodeValue;
                       
                        alert("Sanction Estimate Number " + SancEstiNo + "  inserted into the database successfully...");
                        call_clr();
                    }
                    else if(flag=="record")
                    {
                        alert("Record already exist");
                        call_clr();
                    }
              }
               else if(command=="retrieve")
               {
                  retrieveChecking(baseResponse);
               }
                else if(command=="updated")
                {
                   updateChecking(baseResponse);
                }
                else if(command=="deleted")
                { 
                     alert("record deleted successfully");   
                     call_clr();
                }
        }    
    }
}
function LoadAssetClass(baseResponse)
{
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {    
                        var option=baseResponse.getElementsByTagName("option");
                        var assetmaj_class=document.getElementById("cmbassetmaj_class");
                        var child=assetmaj_class.childNodes;
                        for(var i=child.length-1;i>1;i--)
                        {
                                assetmaj_class.removeChild(child[i]);
                        }
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("asset_class_code")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("asset_class_desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            assetmaj_class.appendChild(opt);
                         }
                  }
                  else if(flag=="nodata")
                  {
                        alert("Invalid asset class");
                  }
                  else
                  {
                        alert("Failed to load asset class");
                  }
}
function LoadAssetmajClass(baseResponse)
{
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {    
                        var code=baseResponse.getElementsByTagName("asset_class_code")[0].firstChild.nodeValue;
                        var desc=baseResponse.getElementsByTagName("asset_class_desc")[0].firstChild.nodeValue;
                        document.getElementById("txtAssetMajClassDesc").value=desc;
                      
                  }
                  else if(flag=="nodata")
                  {
                        alert("Invalid asset class");
                  }
                  else
                  {
                        alert("Failed to load asset class");
                  }
}

function LoadAssetCode(baseResponse)
{
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                        //var asset_desc=baseResponse.getElementsByTagName("asset_code_desc")[0].firstChild.nodeValue;
                        //document.formSanctionEstimate_Master.txtAsset_Description.value=asset_desc;
                        //alert(flag);
                        var option=baseResponse.getElementsByTagName("option");
                        var asset_code=document.getElementById("cmbasset_code");
                        var child=asset_code.childNodes;
                        for(var i=child.length-1;i>1;i--)
                        {
                                asset_code.removeChild(child[i]);
                        } 
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("asset_code")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("asset_code_desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",desc);
                            var opttext=document.createTextNode(code);
                            opt.appendChild(opttext);
                            asset_code.appendChild(opt);
                         }
                  }
                  else if(flag=="nodata")
                  {
                        alert("Asset Code not Found for this Financial Year");
                  }
                  else
                  {
                        alert("Failed to load asset code");
                  }
}
function loadassetdesc(assetcode)
    {
        var assetdesc=assetcode;
        document.getElementById("txtAsset_Description").value=assetdesc;
    
    }
/////////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
function emp_sanction_preparedby()
{
        emp_flag=1;    
        Load_emp_details();
}
function emp_popup_sanction_preparedby()
{
        
        emp_flag=1;    
        servicepopup();
        
}
function emp_sanction_approvedby()
{
        emp_flag=2;  
        Load_emp_details();
}
function emp_popup_sanction_approvedby()
{
        emp_flag=2;    
        servicepopup();
}


var winemp;
function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(emp)
{
        if(emp_flag==1)
        {
                document.formsanc_estimate.txtSanction_Estimate_Empcode1.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==2)
        {
                document.formsanc_estimate.txtSanction_Estimate_Empcode2.value=emp;
                Load_emp_details();
        }
}
function Load_emp_details()
{
       
        if(emp_flag==1)
        {
                var emp_id=document.getElementById("txtSanction_Estimate_Empcode1").value;
              
        }
        else if(emp_flag==2)
        {
                var emp_id=document.getElementById("txtSanction_Estimate_Empcode2").value;
              
        }

             var url="";
             url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
             //alert(url);
             var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
                       processResponse(req);
              }   
              req.send(null);
     
}

function LoadEmpDetails(baseResponse)
{
                 
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                         var emp_name=baseResponse.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                         var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
                         var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                         if(emp_flag==1)
                         {
                                
                                document.formsanc_estimate.txtSanction_Estimate_PreparedBy.value=emp_name+"      "+desig_name;
                         }
                         else if(emp_flag==2)
                         {
                                document.formsanc_estimate.txtSanction_Estimate_ApprovedBy.value=emp_name+"      "+desig_name;
                               
                         }
                }
                else if(flag=="nodata")
                {
                        alert("Invalid Employee Id");
                }
                else
                {
                        alert("Failed to load");
                }
}
//function used to enter a valid amount i.e 10digit.2digit,numbers only......
function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
	          try{t.blur(); }catch(e){}
	          return true;
        
         }
         if (unicode!=8 && unicode !=9)
         {
	          if (unicode<48||unicode>57 ) 
	          {
	                return false 
	          }
         }
}
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46)
         {                
                if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                else return false;
         }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
         {
                        // to avoid over flow
                if(item.value.indexOf(".")<0)
                {
                        //alert("Length without . ="+item.value.length); 
                        return (item.value.length<n)?true:false;
                }
                // dont allow more than 2 precision no's after the point
                if(item.value.indexOf(".")>0)
                {
                        //alert("precision count ="+item.value.split(".")[1].length);
                        if(item.value.split(".")[1].length<pre) return true;
                        else return false;
                }
                return false;
         }else
         {
                return false;
         }
}


function valid_amt(field)
{
    
         var amt=field.value;
         if(amt.indexOf(".")!=amt.lastIndexOf("."))
         {
                alert("Enter a Valid Amount");
                field.value="";
                field.focus();
         }
         if(amt < 0 ) 
         {
                alert("Negative Amount Not Allowed");
                field.value="";
                field.focus();    
         }
        return true;
    
    
    
}

function call_clr()
    {
            //alert("callling clear");
            document.getElementById("cmbAcc_UnitCode").value=0;
            document.getElementById("cmbOffice_code").value=0;
            document.getElementById("cmbSanction_Estimate_FY").value=0;
            document.getElementById("txtAsset_Description").value="";
            document.getElementById("txtAssetMajClassDesc").value="";
            
            document.forms[0].cmbasset_code.value=0;
           
            var asset_code=document.getElementById("cmbasset_code");
            var child=asset_code.childNodes;
            for(var i=child.length-1;i>1;i--)
            {
                    asset_code.removeChild(child[i]);
            } 
            
            document.forms[0].txtTotSanction_Estimate_Amount.value="";
            document.forms[0].txtSanction_Estimate_Amount.value="";
            document.forms[0].txtSanction_Estimate_PreparedOn.value="";
            document.forms[0].txtSanction_Estimate_Empcode1.value=""; 
            document.forms[0].txtSanction_Estimate_PreparedBy.value=""; 
            document.forms[0].txtSanction_Estimate_Empcode2.value=""; 
            document.forms[0].txtAcc_HeadCode.value=""; 
            document.forms[0].txtAcc_HeadDesc.value=""; 
            document.forms[0].txtSanction_Estimate_ApprovedBy.value=""; 
            document.forms[0].txtRemarks.value=""; 
            document.forms[0].txtParticulars.value="";
            
            var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	    	  tbody.deleteRow(0);
	    }
            //alert("all fields r created");
            
            document.forms[0].cmdadd.disabled=false;  
            document.forms[0].cmdupdate.disabled=true;
            document.forms[0].cmddelete.disabled=true; 
            
    }
function clen()
{
            document.getElementById("cmbAcc_UnitCode").value=0;
            document.getElementById("cmbOffice_code").value=0;
            document.getElementById("cmbSanction_Estimate_FY").value=0;
            document.forms[0].txtSanction_Estimate_PreparedOn.value="";
            document.forms[0].txtTotSanction_Estimate_Amount.value="";
            document.forms[0].txtSanction_Estimate_Empcode1.value=""; 
            document.forms[0].txtSanction_Estimate_PreparedBy.value=""; 
            document.forms[0].txtSanction_Estimate_Empcode2.value=""; 
            document.forms[0].txtAcc_HeadCode.value=""; 
            document.forms[0].txtAcc_HeadDesc.value=""; 
            document.forms[0].txtSanction_Estimate_ApprovedBy.value=""; 
            document.forms[0].txtRemarks.value="";
            document.forms[0].txtParticulars.value="";
            
            var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	    	  tbody.deleteRow(0);
	    }
            //alert("allfields r cleared");
            return true
}

function clrForm()
{
	   if(window.confirm("Do you want to clear ALL fields ?"))
	   {
		   	  call_clr();
	   }
}
////////////////////Adding the details part in the grid//////////////////////////
function ADD_GRID()
{
       //alert("inside the grid");
       var flag=nullfieldcheck();
       // alert(flag);
       if(flag)
       {
               var items=new Array();
               items[0]=document.getElementById("txtAssetMajClassDesc").value;
               items[1]=document.formsanc_estimate.cmbasset_code.options[document.formsanc_estimate.cmbasset_code.selectedIndex].text;
               items[2]=document.getElementById("txtAsset_Description").value;
               items[3]=document.getElementById("txtSanction_Estimate_Amount").value;
               items[4]=document.getElementById("txtParticulars").value; 
                            
               
            var tbody=document.getElementById("grid_body");
            var t=0;
            var mycurrent_row=document.createElement("TR");
            seq=seq+1;
            mycurrent_row.id=seq;
            
            var cell=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadTable('"+mycurrent_row.id+"')";
            anc.href=url;
            var txtedit=document.createTextNode("EDIT");
            anc.appendChild(txtedit);
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);
            var i=0;
            var cell2;
            
                cell2=document.createElement("TD");
                
                var H_seqno=document.createElement("input");
                H_seqno.type="hidden";
                H_seqno.name="H_seqno";
                H_seqno.value=tbody.rows.length+1;
                cell2.appendChild(H_seqno);
                
                cell2=document.createElement("TD");
                
                var H_AssetMajClassDesc=document.createElement("input");
                H_AssetMajClassDesc.type="hidden";
                H_AssetMajClassDesc.name="H_AssetMajClassDesc";
                H_AssetMajClassDesc.value=items[0];
                cell2.appendChild(H_AssetMajClassDesc);
                var currentText=document.createTextNode(items[0]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                        
                cell2=document.createElement("TD"); 
                
                var H_asset_code=document.createElement("input");
                H_asset_code.type="hidden";
                H_asset_code.name="H_asset_code";
                H_asset_code.value=items[1];
                cell2.appendChild(H_asset_code);
                var currentText=document.createTextNode(items[1]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                             
                cell2=document.createElement("TD");
               
                var H_Asset_Description=document.createElement("input");
                H_Asset_Description.type="hidden";
                H_Asset_Description.name="H_Asset_Description";
                H_Asset_Description.value=items[2];
                cell2.appendChild(H_Asset_Description);
                var currentText=document.createTextNode(items[2]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                cell2=document.createElement("TD");
    
                var H_Sanction_Estimate_Amount=document.createElement("input");
                H_Sanction_Estimate_Amount.type="hidden";
                H_Sanction_Estimate_Amount.name="H_Sanction_Estimate_Amount";
                H_Sanction_Estimate_Amount.value=items[3];
                cell2.appendChild(H_Sanction_Estimate_Amount);
                var currentText=document.createTextNode(items[3]);
                cell2.setAttribute("align","right");
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                    
                cell2=document.createElement("TD");
                
                var H_Particulars=document.createElement("input");
                H_Particulars.type="hidden";
                H_Particulars.name="H_Particulars";
                H_Particulars.value=items[4];
                cell2.appendChild(H_Particulars);
                var currentText=document.createTextNode(items[4]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                
                
            tbody.appendChild(mycurrent_row);
            clear_main_fields();
            
           
        }
       /* else
        {
                alert("error in loding data in grid");
        }*/
}

function clear_main_fields()
{          //alert("inside clear main fields");
	   document.getElementById("txtAssetMajClassDesc").value="";
	   document.getElementById("cmbasset_code").value=0;
	   document.getElementById("txtAsset_Description").value="";
	   document.getElementById("txtSanction_Estimate_Amount").value=""; 
	   document.getElementById("txtParticulars").value="";
	   
            document.formsanc_estimate.cmdadd.style.display='block';
	    document.formsanc_estimate.cmdupdate.style.display='none';
	    document.formsanc_estimate.cmddelete.disabled=true;
}

function update_GRID()
{      
        var items=new Array();
               items[0]=document.getElementById("txtAssetMajClassDesc").value;
               items[1]=document.formsanc_estimate.cmbasset_code.options[document.formsanc_estimate.cmbasset_code.selectedIndex].text;
               items[2]=document.getElementById("txtAsset_Description").value;
               items[3]=document.getElementById("txtSanction_Estimate_Amount").value;
               items[4]=document.getElementById("txtParticulars").value;  
               
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
        try{rcells.item(1).firstChild.value=items[0];}catch(e){}
        try{rcells.item(1).lastChild.nodeValue=items[0];}catch(e){}
      
        try{rcells.item(2).firstChild.value=items[1];}catch(e){}
        try{rcells.item(2).lastChild.nodeValue=items[1];}catch(e){}
    
        try{rcells.item(3).firstChild.value=items[2];}catch(e){}
        try{rcells.item(3).lastChild.nodeValue=items[2];}catch(e){}
        
        try{rcells.item(4).firstChild.value=items[3];}catch(e){}
        try{rcells.item(4).lastChild.nodeValue=items[3];}catch(e){}
      
        try{rcells.item(5).firstChild.value=items[4];}catch(e){}
        try{rcells.item(5).lastChild.nodeValue=items[4];}catch(e){}
       
        alert("Record Updated");
        clear_main_fields();
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
		        var tbody=document.getElementById("grid_body");
		        var r=document.getElementById(com_id);
		        //alert(com_id);
                        var ri=r.rowIndex;
		        tbody.deleteRow(ri-1);
		        clear_main_fields();
        }
}
function loadTable(scod)
{
        com_id=scod;                                    
        //call_clr();
        //alert(com_id);
        var r=document.getElementById(com_id);
        var rcells=r.cells;
       
       try{document.getElementById("txtAssetMajClassDesc").value=rcells.item(1).firstChild.value;}catch(e){}
       try{document.getElementById("cmbasset_code").value=rcells.item(3).firstChild.value;} catch(e){}
       try{document.getElementById("txtAsset_Description").value=rcells.item(3).firstChild.value;} catch(e){}
       try{document.getElementById("txtSanction_Estimate_Amount").value=rcells.item(4).firstChild.value;} catch(e){}  
       try{document.getElementById("txtParticulars").value=rcells.item(5).firstChild.value;} catch(e){} 
                
           document.formsanc_estimate.cmdadd.style.display='none';
	   document.formsanc_estimate.cmdupdate.style.display='block';
	   document.formsanc_estimate.cmddelete.disabled=false;
}
function nullfieldcheck()
{
                  if(document.getElementById("cmbasset_code").value==0)
                   {
                        alert("select the asset code");
                        document.getElementById("cmbasset_code").focus();
                        return false;
                   }
                  else if(document.getElementById("txtSanction_Estimate_Amount").value=="")
                   {
                        alert("Enter the Sanctioned Estimate Amount");  
                        document.getElementById("txtSanction_Estimate_Amount").focus();
                        return false;        
                   }  
                 else if(document.getElementById("txtRemarks").value!="")
                   {
                            if((document.getElementById("txtRemarks").value.length)>=190)
                            {
                                          alert("Please Enter Particulars below 200 characters");
                                          document.getElementById("txtRemarks").value="";
                                          return false;
                            }
                   }
                   else if(document.getElementById("txtParticulars").value!="")
                   {
                            if((document.getElementById("txtParticulars").value.length)>=190)
                            {
                                          alert("Please Enter Particulars below 200 characters");
                                          document.getElementById("txtParticulars").value="";
                                          return false;
                            }
                   }
                   return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function checkfields()
{
                if(document.getElementById("cmbAcc_UnitCode").value=="")
                {
                    alert("Select the Account Unit code");
                    //document.getElementById("txtAcc_HeadDesc").focus();
                    return false;    
                }
                if(document.getElementById("cmbOffice_code").value=="")
                {
                    alert("Select the Office Code");
                    //document.getElementById("cmbOffice_code").focus();
                    return false;
                }
                if(document.getElementById("cmbSanction_Estimate_FY").value=="")
                {
                    alert("Enter the Financial Year");
                    //document.getElementById("txtCrea_date").focus();
                    return false;    
                }
                 if(document.getElementById("txtSanction_Estimate_PreparedOn").value=="")
                {
                    alert("Enter the Sanction Estimate Prepared on Date");
                    //document.getElementById("txtCrea_date").focus();
                    return false;    
                }
                 if(document.getElementById("txtTotSanction_Estimate_Amount").value=="")
                {
                    alert("Enter the total amount");
                    //document.getElementById("txtCrea_date").focus();
                    return false;    
                }
                 if(document.getElementById("txtAcc_HeadCode").value=="")
                {
                    alert("Enter the Account Head Code");
                    //document.getElementById("txtCrea_date").focus();
                    return false;    
                }
                 if(document.getElementById("txtSanction_Estimate_PreparedBy").value=="")
                {
                    alert("Enter the Prepared By");
                    //document.getElementById("txtCrea_date").focus();
                    return false;    
                }
                 if(document.getElementById("txtSanction_Estimate_ApprovedBy").value=="")
                {
                    alert("Enter the Approved By");
                    //document.getElementById("txtCrea_date").focus();
                    return false;    
                }
                var tbody=document.getElementById("grid_body");
                var t=0;
                if(tbody.rows.length==0)
                {
                        alert("Enter the Details Part");
                        return false; 
                }
                if(tbody.rows.length>0)
                 {
                        //alert("some rows in the details");
                        var check_amt=0;
                        rows=tbody.getElementsByTagName("tr");
                        for(i=0;i<rows.length;i++)
                        {
                            var cells=rows[i].cells;
                            check_amt=parseFloat(check_amt) + parseFloat(cells.item(4).firstChild.value);
                            //alert(check_amt);
                        }
                            if(parseFloat(document.getElementById("txtTotSanction_Estimate_Amount").value)!=parseFloat(check_amt))
                            {
                                 alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtTotSanction_Estimate_Amount").value)-parseFloat(check_amt)));
                                 return false; 
                            }
                        
                 }
                return true;
}

                            
                            
                      