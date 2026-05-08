/************** Java script for bill minor type transaction************/
/**************** Script to clear all the fields *******************/
var com_id=0;
var seq=1;

/************ creating xmlhttprequest object - AJAX concept*********/
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
  
function CallListjsp()
        {
                    //var maj_code=document.grtElementById("bill_majr_type_code").value;
                    //alert("major code  :"+maj_code);
                    window.open("../jsps/fas_bill_ListModi_jsp.jsp"); 
                   
        }

function clrForm()
{
        if(window.confirm("Do you want to clear ALL fields ?"))
        {
           document.getElementById("bill_minr_type_code").value="";
           document.getElementById("bill_minr_desc").value="";
           document.fas_bill_minr_form.sub_type_YN[1].checked=true;
           document.fas_bill_minr_form.pro_avai_YN[1].checked=true;
           document.getElementById("pro_remarks").value="";
           document.getElementById("sub_type_code").value="";
           document.getElementById("sub_type_desc").value="";
           document.getElementById("bill_majr_type_code").selectedIndex=0;
           
            var tbody=document.getElementById("grid_body");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(0);
            }
            
            document.getElementById("sub_type_disp").style.display='none';
            document.getElementById("grid").style.display='none';
        }
}

/********************Function called when Exit button is clicked************/
function exitmethod()
{
      window.close();
}
/******************** function called when clearall button is clicked***********/
function clearall()
{
 document.getElementById("sub_type_code").value="";
 document.getElementById("sub_type_desc").value="";
 document.fas_bill_minr_form.cmdadd.style.display='block';
 document.fas_bill_minr_form.cmdupdate.style.display='none';
 document.fas_bill_minr_form.cmddelete.disabled=true;
}
/***************** check all the fields before submit *********************/

function checkNull()
{
                if(document.getElementById("bill_minr_desc").value.length=="")
                {
                        alert("Enter the Bill Minor Type Description");
                        document.getElementById("bill_minr_desc").focus();
                        return false;
                }
                if(document.getElementById("pro_remarks").value.length=="")
                {
                        alert("Enter the Bill Minor Type Remarks");
                        document.getElementById("pro_remarks").focus();
                        return false;    
                }
              
                if(document.getElementById("bill_majr_type_code").value==0)
                {
                         alert("Select the Bill Major Group");
                         document.getElementById("bill_majr_type_code").focus();
                          return false;    
                }
                if(document.fas_bill_minr_form.sub_type_YN[0].checked==true)
                {
                    var tbody=document.getElementById("grid_body");
   // alert("tbody length:"+tbody.rows.length+" "+document.getElementById("txtsub_ledger_YN").value)
                    if(tbody.rows.length==0)
                     {
                        alert("Add Sub-Type in Bill Sub Types ");
                        return false;
                    }
                }
                return true;
}
/********************* function to add the values in the grid *****************************/

function  add_GRID()
{
        if(document.getElementById("sub_type_desc").value=="")
        {
            alert("Enter the Bill Sub-Type Description");
            return false;
        }
        if(true)
        {
         var items=new Array();
        items[0]=document.getElementById("sub_type_code").value;
        items[1]=document.getElementById("sub_type_desc").value;
        var tbody=document.getElementById("grid_body");
        var t=0;
/************ creating the first col of dynamic row ******************/       
           //var sub_code=tbody.rows.length+1;
           var mycurrent_row=document.createElement("TR");
          //mycurrent_row.id=sub_code;
            mycurrent_row.id=seq;
            //alert("row ID"+mycurrent_row.id);
            var cell1=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadTable('"+mycurrent_row.id+"')";
            anc.href=url;
            var txtedit=document.createTextNode("EDIT");
            anc.appendChild(txtedit);
            cell1.appendChild(anc);
            mycurrent_row.appendChild(cell1);

/******************* creating the second column of dynamic table-named as billtype_code*********/
                   var cell2 = document.createElement("TD");
                   /*
                    var subtypecode=document.createElement("input");
                    subtypecode.type="hidden";
                    subtypecode.name="subtypecode"+seq;
                    subtypecode.value=items[0];
                    cell2.appendChild(subtypecode);*/
                   
                   
                    var currentText=document.createTextNode(seq);
                    cell2.appendChild(currentText);
                    mycurrent_row.appendChild(cell2);
                
/**********************************creating the third column of dynamic table as billtype_desc****************/
                    var cell3 = document.createElement("TD");
                   
                    /*var subtypedesc=document.createElement("input");
                    subtypedesc.type="hidden";
                    subtypedesc.name="subtypedesc"+seq;
                    subtypedesc.value=items[1];
                    cell3.appendChild(subtypedesc);*/
                    
                    var currentText=document.createTextNode(items[1]);
                    cell3.appendChild(currentText);
                    mycurrent_row.appendChild(cell3);
                
                    tbody.appendChild(mycurrent_row);          
                }
                else
                {
                             var items=new Array();
                             items[0]=0;
                             items[1]="";
                }
                seq=seq+1;
               alert("Record added to the grid");
               clearall();
}
 /******************* Load the values from the grid *******************/
 function loadTable(scod)
{
            com_id=scod;
            clearall();
            var r=document.getElementById(com_id);
            var rcells=r.cells;
                 try {document.getElementById("sub_type_code").value=rcells.item(1).firstChild.nodeValue;}catch(e){}
                 try{document.getElementById("sub_type_desc").value=rcells.item(2).firstChild.nodeValue;}catch(e){}
      
            document.fas_bill_minr_form.cmdupdate.style.display='block';
            document.fas_bill_minr_form.cmddelete.disabled=false;
            document.fas_bill_minr_form.cmdadd.style.display='none';
}    
/************************Update the values in thte Grid *******************/
function update_GRID()
{      
             var items=new Array();
             try {items[0]=document.getElementById("sub_type_code").value}catch(e){}
             try{items[1]=document.getElementById("sub_type_desc").value }catch(e){}
            var r=document.getElementById(com_id);
            var rcells=r.cells;
           /* for(i=0,j=1;i<2;i++,j++)
            {
             try
             {
                rcells.item(j).firstChild.value=items[i];           // for hidden field
                rcells.item(j).lastChild.nodeValue=items[i];
                }
                catch(e)
                {}
            }*/
            rcells.item(1).firstChild.nodeValue=items[0];
            rcells.item(2).firstChild.nodeValue=items[1];
            alert("Record Updated");
            clearall();
}
      

/************************Delete the values from the Grid *********************/
function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        clearall();
        }
}
       
        
/************** on submit the form **********************/

function add_bill_trn()
{
           //alert("checkNull()");
	 var hid=document.getElementById("hid").value;
	 if(hid=="Y"){
            var bill_majr_type_code1=document.getElementById("bill_majr_type_code").value;
          // var bill_minr_type_code1=document.getElementById("bill_minr_type_code").value;
            var bill_minr_desc1=document.getElementById("bill_minr_desc").value;
         /******* Getting the sub type radio button value **************/
     //     for(var i=0;i<document.fas_bill_minr_form.sub_type_YN.length;i++)
        //  {
         var sub_type_YN1;
         
                        if(document.fas_bill_minr_form.sub_type_YN[0].checked)
                                    sub_type_YN1='Y';
                        else
                                   sub_type_YN1='N';
                                   //alert("sub_type_YN1"+sub_type_YN1);
                        
        //    }  
            /*************Getting the Proceeding radio value *******/
           //  for(var j=0;j<document.fas_bill_minr_form.pro_avai_YN.length;j++)
         // {
                        var pro_avai_YN1;
                         if(document.fas_bill_minr_form.pro_avai_YN[0].checked)
                                   pro_avai_YN1='Y';
                        else
                                    pro_avai_YN1='N';
          //  }
           
            var pro_remarks1=document.getElementById("pro_remarks").value;
             /*var Subtype_code=new Array();
             var Subtype_desc=new Array();
              Subtype_code[]=document.getElementById("subtypecode");
              Subtype_desc[]=document.getElementById("subtypedesc");
             alert("sub type available");
             alert(Subtype_code);
             var sub_type_code1=0;
             var sub_type_desc1="";
             for(var k=0;k<Subtype_code.length;k++)
              {
                sub_type_code1=Integer.parseInt(Subtype_code[k]);
                 alert("sub_type_code1");
                 sub_type_desc1=Subtype_desc[k];
                 alert("sub_type_desc1");
              }*/
            var i;
            i=checkNull();
      if(i==true)
      {
                    if(sub_type_YN1=='N')
                    {
                    var url="../../../../../fas_bill_trans_servlet?command=Add&bill_majr_type_code="+bill_majr_type_code1+
                  //  "&bill_minr_type_code="+bill_minr_type_code1+
                    "&bill_minr_desc="+bill_minr_desc1+
                    "&sub_type_YN="+sub_type_YN1+
                    "&pro_avai_YN="+pro_avai_YN1+
                    "&pro_remarks="+pro_remarks1+"&hid="+hid;
                   // alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                     req.onreadystatechange=function()
                        {
                             //alert("function--->");
                            LoadMajTypeCode(req);
                        }   
                            req.send(null);
                    }
                  else
                    {
                            var tbody=document.getElementById("grid_body");
                             var table=document.getElementById("mytable");           
                            var rid=0;
                          var  record1=new Array();
                            var record2=new Array();
                            var record_id;
                            var record_desc;
                            //alert("length of Row :"+tbody.rows.length);
                for(var i=1;i<=tbody.rows.length;i++)
                     {
                                rid=i;
                                var r=document.getElementById(rid);
                                var rcells=r.cells;
                                record1[i]=rcells.item(1).firstChild.nodeValue;
                                record2[i]=rcells.item(2).firstChild.nodeValue;
                               
                    /*            if(i==0)
                                {
                                        record=record1[i]+"//"+record2[i];
                                }
                                else
                                {
                                        record=record+",,"+record1[i]+"//"+record2[i];
                                }*/
                              if(i==1)
                                {
                                    record_id=record1[i];
                                    record_desc=record2[i];
                                    }
                                    else
                                    {
                                       record_id=record_id+','+record1[i];
                                      record_desc=record_desc+','+record2[i];
                                    }
//                                alert(record_id);
//                               alert(record_desc);

                    }               
                   // alert(record);
                            var url="../../../../../fas_bill_trans_servlet?command=Add&bill_majr_type_code="+bill_majr_type_code1+
                                    //"&bill_minr_type_code="+bill_minr_type_code1+
                                    "&bill_minr_desc="+bill_minr_desc1+
                                    "&sub_type_YN="+sub_type_YN1+
                                    "&pro_avai_YN="+pro_avai_YN1+
                                    "&pro_remarks="+pro_remarks1+
                                    "&record_id="+record_id+
                                    "&record_desc="+record_desc+"&hid="+hid;
                                    
                                 //  alert("secound "+url);  
                                    var req=getTransport();
                                    req.open("GET",url,true); 
                                     req.onreadystatechange=function()
                                        {
                                             //alert("function--->");
                                            LoadMajTypeCode(req);
                                        }   
                                    req.send(null);
                         }
        }
	 }else if(hid=="N"){
		 
		   var bill_majr_type_code1=document.getElementById("bill_majr_type_code").value;
		   var bill_minr_desc1=document.getElementById("bill_minr_desc1").value;
		   var bill_minr_type_code=document.getElementById("bill_minr_type_code").value;
		   var tbody=document.getElementById("grid_body");
           var table=document.getElementById("mytable");           
          var rid=0;
        var  record1=new Array();
          var record2=new Array();
          var record_id;
          var record_desc;
          //alert("length of Row :"+tbody.rows.length);
for(var i=1;i<=tbody.rows.length;i++)
   {
              rid=i;
              var r=document.getElementById(rid);
              var rcells=r.cells;
              record1[i]=rcells.item(1).firstChild.nodeValue;
              record2[i]=rcells.item(2).firstChild.nodeValue;
             
  /*            if(i==0)
              {
                      record=record1[i]+"//"+record2[i];
              }
              else
              {
                      record=record+",,"+record1[i]+"//"+record2[i];
              }*/
            if(i==1)
              {
                  record_id=record1[i];
                  record_desc=record2[i];
                  }
                  else
                  {
                     record_id=record_id+','+record1[i];
                    record_desc=record_desc+','+record2[i];
                  }
//              alert(record_id);
//             alert(record_desc);

  }    
		    var url="../../../../../fas_bill_trans_servlet?command=Add&bill_majr_type_code="+bill_majr_type_code1+
            "&bill_minr_type_code="+bill_minr_type_code+
            "&bill_minr_desc="+bill_minr_desc1+           
            "&record_id="+record_id+
            "&record_desc="+record_desc+"&hid="+hid;
		  //  alert("third "+url);  
            var req=getTransport();
            req.open("GET",url,true); 
             req.onreadystatechange=function()
                {
                     //alert("function--->");
                    LoadMajTypeCode(req);
                }   
            req.send(null);
	 }
}

function LoadMajTypeCode(req)
{  
 
    if(req.readyState==4)
    {
    if(req.status==200)
        {  
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
              //alert("baseResponse"+baseResponse);
              //alert("tagcommand"+tagcommand);
              //alert("Command"+Command);
            if(Command=="add")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 //alert(flag);
            	 if(flag=="success")
            	    {
                    alert("Records are successfully added");
                    }
                    else
                    {
                        alert("Failed to add the Records");
                    }
            clrForm();
            }
    }
  }
}  

/***************************** New Updates/
 * 
 * 
 */
function chkNeworOld(chk_val,path)
{
	if(document.getElementById("bill_majr_type_code").value=="0")
		{
		//alert(document.getElementById("bill_majr_type_code").value);
		document.getElementById("bill_majr_type_code").focus();
		alert('Select Bill Major Type Code ... ');
		
		}
	else if(document.getElementById("bill_majr_type_code").value!="0"){
	if(chk_val=="YES")
		{
		document.getElementById("bill_minr_type_code").value="";
		document.getElementById("hid").value='Y';
		document.getElementById("NewMinor").style.display="block";
		document.getElementById("OldMinor").style.display="none";
		document.getElementById("bill_majr_type_code").disabled=false;
		enableSub_type('N');
		var radios = document.fas_bill_minr_form.pro_avai_YN;

		for (var i=0, iLen=radios.length; i<iLen; i++) {
		  radios[i].disabled = false;
		} 
		
		
		var radios1 = document.fas_bill_minr_form.sub_type_YN;

		for (var i=0, iLen=radios1.length; i<iLen; i++) {
		  radios1[i].disabled = false;
		} 
		document.getElementById("pro_remarks").disabled=false;
		}
	else if(chk_val=="NO")
		{
		document.getElementById("hid").value='N';
		document.getElementById("NewMinor").style.display="none";	
		document.getElementById("OldMinor").style.display="block";
		var major_code=document.getElementById("bill_majr_type_code").value;
		document.getElementById("bill_majr_type_code").disabled=true;
		var radios = document.fas_bill_minr_form.pro_avai_YN;

		for (var i=0, iLen=radios.length; i<iLen; i++) {
		  radios[i].disabled = true;
		} 
		
		
		var radios1 = document.fas_bill_minr_form.sub_type_YN;

		for (var i=0, iLen=radios1.length; i<iLen; i++) {
		  radios1[i].disabled = true;
		} 
		
		document.getElementById("pro_remarks").disabled=true;
		
		enableSub_type('Y');
		var url="../../../../../fas_bill_trans_servlet?command=loadMinor&major_code="+major_code ;
		 var req=getTransport();	
         req.open("GET",url,true); 
         //alert('test');
          req.onreadystatechange=function()
             {
                 
        	  if(req.readyState==4)
        	    {
        	    if(req.status==200)
        	        {  
        	             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        	             var tagcommand=baseResponse.getElementsByTagName("command")[0];
        	             var Command=tagcommand.firstChild.nodeValue;
        	            // alert(Command);
        	             if(Command=="loadMinor")
        	            	 {
        	            	
        	            	 var oldminor=document.getElementById("bill_minr_desc1");
        	            	 oldminor.length=0;
        	            	 var option=document.createElement("option");
        	            	 
        	            	 var desc_len=baseResponse.getElementsByTagName("desc").length;
        	            	 option.text="--Select--";
    	            		 option.value="";
        	            	 oldminor.appendChild(option);
        	            	 for(var i=0;i<desc_len;i++){
        	            		
        	            		 var desc=baseResponse.getElementsByTagName("desc")[i].firstChild.nodeValue;
        	            		 var id=baseResponse.getElementsByTagName("id")[i].firstChild.nodeValue;
        	            		
        	            		 var option=document.createElement("option");
        	            		 option.text=desc;
        	            		 option.value=id;
        	            		
        	            		 oldminor.appendChild(option);
        	            	 } 
        	            	 }
             }   
       
        
		}  
}
          req.send(null);
		}
	}
}

/*function getMinorDetails(minor){
	alert(minor);
	var major_code=document.getElementById("bill_majr_type_code").value;
	var url="../../../../../fas_bill_trans_servlet?command=loadMinorDesc&major_code="+major_code+"&minor_code="+minor ;
	
}
*/
/*********** to display the sub type part if the subtype available radio button is checked yes************/
function enableSub_type(opt)
{
//alert(opt);
    if(opt=="Y")
    {
        document.getElementById("sub_type_disp").style.display='inline';
        document.getElementById("grid").style.display='inline';
    }
    else if(opt=="N")
    {
        document.getElementById("sub_type_disp").style.display='none';
        document.getElementById("grid").style.display='none';
    }
}
