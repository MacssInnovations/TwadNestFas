var err;
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

function clearAll()
 {
   document.frmCashBook.cmbFinancialYear.selectedIndex=0;
   document.frmCashBook.txtdateformarch.value="";
   document.frmCashBook.txtdatetomarch.value="";
   document.frmCashBook.txtdateforapril.value="";
   document.frmCashBook.txtdatetoapril.value="";
   document.frmCashBook.txtnoofother.value="";
   document.frmCashBook.txtnotoother.value="";
   document.frmCashBook.txtfileno.value="";
   document.frmCashBook.txtfilerefdate.value="";
   document.frmCashBook.txtremarks.value="";
   //document.frmCashBook.txtdepreciationrates.value="";
   document.frmCashBook.CmdAdd.disabled=false;
   document.frmCashBook.CmdUpdate.disabled=true;
   document.frmCashBook.CmdDelete.disabled=true;
   
   var tbody=document.getElementById("tblList");
        var t=0;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
 }


//Financial Year Coding Part ///

function loadfyr()
{
         var cyr, cdt,cdt1;
 	cdt=new Date();
 	cyr=cdt.getFullYear();
 	cmn=cdt.getMonth();
        //alert("cdate"+cdt);
        //alert("cmonth"+cmn);
        //alert("cyear"+cyr);
        var cmbFinancialYear=document.getElementById("cmbFinancialYear");
        cyr=cyr+1;
 	if (parseInt(cmn) <= 2)
        {
  
                document.frmCashBook.cmbFinancialYear.length=5;
                cmbFinancialYear.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select FinancialYear--";
                option.value=0;
                try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                for (var i = 0 ; i < 3; i++) 
                {
         
                  //document.frmCashBook.cmbFinancialYear.options[i].text=(cyr-2)+"-"+(cyr-1);
                  //document.frmCashBook.cmbFinancialYear.options[i].value=(cyr-2)+"-"+(cyr-1);
                  var id=(cyr-2)+"-"+(cyr-1);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                  
                  cyr--;
                }
 	}
 	else 
 	{
            //alert('hai');
            //alert(cmn);
           document.frmCashBook.cmbFinancialYear.length=5;
           cmbFinancialYear.innerHTML="";
           var option1=document.createElement("OPTION");
           option1.text="--Select FinancialYear--";
           option1.value=0;
           try
                        {
                            cmbFinancialYear.add(option1);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option1,null);
                        } 
        if(cmn>=12)
        {
            for (var i = 0 ; i < 3; i++) 
            {
                var id=(cyr-1)+"-"+(cyr);
              //document.frmCashBook.cmbFinancialYear.options[i].text=id;
              //document.frmCashBook.cmbFinancialYear.options[i].value=id;
              
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
              cyr--;
            }
        }
        else
        {
            for (var i = 0 ; i < 3; i++) 
            {
                var id=(cyr-1)+"-"+(cyr);
              //document.frmCashBook.cmbFinancialYear.options[i].text=id;
              //document.frmCashBook.cmbFinancialYear.options[i].value=id;
              
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
              cyr--;
            }
        }
 	}
        
}


//******************************Validation Checking**************************//
 function nullCheck()
        {
                  
                  
                  if((document.frmCashBook.cmbFinancialYear.value=="") ||(document.frmCashBook.cmbFinancialYear.value.length<=0) || (document.frmCashBook.cmbFinancialYear.value=="0"))
                  {
                    alert("Please Select Financial year");
                    document.frmCashBook.cmbFinancialYear.focus();
                    return false;
                  }
                  
                  if((document.frmCashBook.txtdateformarch.value=="") || (document.frmCashBook.txtdateformarch.value.length<=0))
                  { 
                       alert("Please Enter the Cash Book Period for March");
                       document.frmCashBook.txtdateformarch.focus();
                       return false;
                  }
                  
                  
                  if((document.frmCashBook.txtdatetomarch.value=="") || (document.frmCashBook.txtdatetomarch.value.length<=0))
                  {
                    alert("Please Enter the Cash Book Period To March");
                    document.frmCashBook.txtdatetomarch.focus();
                    return false;
                  }
                  
                  if((document.frmCashBook.txtdateforapril.value=="") || (document.frmCashBook.txtdateforapril.value.length<=0))
                  { 
                       alert("Please Enter the Cash Book Period for April");
                       document.frmCashBook.txtdateforapril.focus();
                       return false;
                  }
                  
                  
                  if((document.frmCashBook.txtdatetoapril.value=="") || (document.frmCashBook.txtdatetoapril.value.length<=0))
                  {
                    alert("Please Enter the Cash Book Period To April");
                    document.frmCashBook.txtdatetoapril.focus();
                    return false;
                  }
                  
                  if((document.frmCashBook.txtnoofother.value=="") || (document.frmCashBook.txtnoofother.value.length<=0))
                  {
                    alert("Please Enter Cash Book Period ");
                    document.frmCashBook.txtnoofother.focus();
                    return false;
                    
                  }
                  if((document.frmCashBook.txtnotoother.value=="") || (document.frmCashBook.txtnotoother.value.length<=0))
                  {
                    alert("Please Enter Cash Book Period ");
                    document.frmCashBook.txtnotoother.focus();
                    return false;
                  }
                  return true;
        }

function getCurrentYear()
{
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }

function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }
  
  
function checkdt(t)
{
  //alert('test');
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
         
       ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
         try{
             var f=isValidDate(c);
            }
        catch(e){
         
         
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear <1970)
            {
            
                    alert('Entered date should be greater than 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
          /* else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }*/
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
           if(currentYear <1970)
            {
            
                    alert('Entered date should be greater than 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
          /* else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }*/
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}

//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}



function numbersonly1(e,t)
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
                return false 
        }
     }


function callServer(command,param)
 {
   
       
       var cmbFinancialYear=document.frmCashBook.cmbFinancialYear.value;
       var txtdateofmarch=document.frmCashBook.txtdateformarch.value;
       var txtdatetomarch=document.frmCashBook.txtdatetomarch.value;
       var txtdateforapril=document.frmCashBook.txtdateforapril.value;
       var txtdatetoapril=document.frmCashBook.txtdatetoapril.value;
       var txtnoofother=document.frmCashBook.txtnoofother.value;
       var txtnotoother=document.frmCashBook.txtnotoother.value;
       var txtfileno=document.frmCashBook.txtfileno.value;
       var txtfilerefdate=document.frmCashBook.txtfilerefdate.value;
       var txtremarks=document.frmCashBook.txtremarks.value;
       
        var url="";
        
       
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../CashBookControlServlet.con?command=Add&FinancialYear=" + cmbFinancialYear+"&DateForMarch="+txtdateofmarch+"&DateToMarch="+txtdatetomarch+"&DateForApril="+txtdateforapril+"&DateToApril="+txtdatetoapril+"&NoOfOther="+txtnoofother+"&NoToOther="+txtnotoother+"&FileNo="+txtfileno+"&FileRefDate="+txtfilerefdate+"&Remarks="+txtremarks;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
                    }
                   
                    
        }
        else if(command=="Update")
        {
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../CashBookControlServlet.con?command=Update&FinancialYear=" + cmbFinancialYear+"&DateForMarch="+txtdateofmarch+"&DateToMarch="+txtdatetomarch+"&DateForApril="+txtdateforapril+"&DateToApril="+txtdatetoapril+"&NoOfOther="+txtnoofother+"&NoToOther="+txtnotoother+"&FileNo="+txtfileno+"&FileRefDate="+txtfilerefdate+"&Remarks="+txtremarks;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
                    }

        }
        
        else if(command=="Delete")
        {  
                    url="../../../../../CashBookControlServlet.con?command=Delete&FinancialYear=" +cmbFinancialYear;
                   var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
        }
        else if(command=="Get")
        {               
            url="../../../../../CashBookControlServlet.con?command=Get&FinancialYear=" +cmbFinancialYear;
            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
        else if(command=="Asset")
        {               
            url="../../../../../DepreciationMasterServlet.con?command=Asset";
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
        
}  


//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {    
            
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              
              var command=tagCommand.firstChild.nodeValue; 
              
              if(command=="Add")
              {
                  addRow(baseResponse);                 
              }
              else if(command=="Delete")
              { 
              deleteRow(baseResponse)
              }
              
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
               else if(command=="Get")
              { 
              getRow(baseResponse);
              }
              else if(command=="Asset")
              { 
              assetRow(baseResponse);
              }
          }
        }
  }


function addRow(baseResponse)
{
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
               
              if(flag=="success")
              {                        
                 alert("Record Inserted Into Database successfully.");
                 /*did=baseResponse.getElementsByTagName("AssetClassCode")[0].firstChild.nodeValue; 
                 alert("Your Asset Class Code Is " +  did);
                 var items=new Array();                                     
                 cadid=baseResponse.getElementsByTagName("AssetClassCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("FinancialYear")[0].firstChild.nodeValue;
                 DepreciationRate=baseResponse.getElementsByTagName("DepreciationRate")[0].firstChild.nodeValue;
                 //document.frmCashBook.txtassetclasscode.value=cadid;
                 //document.frmCashBook.txtassetclassdesc.value=document.frmCashBook.txtassetclassdesc.value;
                 var Drate=document.frmCashBook.txtdepreciationrates.value;
                 
                 var assetvalue=document.frmCashBook.cmbassetclasscode.options[document.frmCashBook.cmbassetclasscode.selectedIndex].text;*/
                 var cmbFinancialYear=document.frmCashBook.cmbFinancialYear.value;
                   var txtdateofmarch=document.frmCashBook.txtdateformarch.value;
                   var txtdatetomarch=document.frmCashBook.txtdatetomarch.value;
                   var txtdateforapril=document.frmCashBook.txtdateforapril.value;
                   var txtdatetoapril=document.frmCashBook.txtdatetoapril.value;
                   var txtnoofother=document.frmCashBook.txtnoofother.value;
                   var txtnotoother=document.frmCashBook.txtnotoother.value;
                   var txtfileno=document.frmCashBook.txtfileno.value;
                   var txtfilerefdate=document.frmCashBook.txtfilerefdate.value;
                   var txtremarks=document.frmCashBook.txtremarks.value;
                 
                 var tbody=document.getElementById("tblList");
                 var fin=baseResponse.getElementsByTagName("FinancialYear")[0].firstChild.nodeValue;
                                            
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=fin;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var url="javascript:loadValuesFromTable('" + fin + "')";              
                 anc.href=url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 
                 var hidden=document.createElement("input");
                 hidden.type="hidden";
                 hidden.name="k";
                 hidden.value=fin;
                 cell.appendChild(hidden);
                 mycurrent_row.appendChild(cell);
             
                 var cell2 =document.createElement("TD");    
                 var txtassetclassdesc=document.createTextNode(fin);                         
                 cell2.appendChild(txtassetclassdesc);
                 
                 var hidden1=document.createElement("input");
                 hidden1.type="hidden";
                 hidden1.name="cmbFinancialYear";
                 hidden1.value=fin;
                 cell2.appendChild(hidden1);
                 mycurrent_row.appendChild(cell2);
                
                
                 var cell3 =document.createElement("TD");    
                 var txtfinancialyear=document.createTextNode(txtdateofmarch);                         
                 cell3.appendChild(txtfinancialyear);   
                 
                 var hidden2=document.createElement("input");
                 hidden2.type="hidden";
                 hidden2.name="txtdateformarch";
                 hidden2.value=txtdateofmarch;
                 cell3.appendChild(hidden2);
                 mycurrent_row.appendChild(cell3);
                 
                 var cell4 =document.createElement("TD");    
                 var txtdatetomarch=document.createTextNode(txtdatetomarch);                         
                 cell4.appendChild(txtdatetomarch);       
                 mycurrent_row.appendChild(cell4);
                 
                 
                 var cell5=document.createElement("TD");
                 var txtdateforapril=document.createTextNode(txtdateforapril);
                 cell5.appendChild(txtdateforapril);
                 mycurrent_row.appendChild(cell5);
                 
                 var cell6=document.createElement("TD");
                 var txtdatetoapril=document.createTextNode(txtdatetoapril);
                 cell6.appendChild(txtdatetoapril);
                 mycurrent_row.appendChild(cell6);
                 
                 var cell7=document.createElement("TD");
                 var txtnoother=document.createTextNode(txtnoofother);
                 cell7.appendChild(txtnoother);
                 mycurrent_row.appendChild(cell7);
                 
                 var cell8=document.createElement("TD");
                 var txttoother=document.createTextNode(txtnotoother);
                 cell8.appendChild(txttoother);
                 mycurrent_row.appendChild(cell8);
                 
                 var cell9=document.createElement("TD");
                 var txtfileno=document.createTextNode(txtfileno);
                 cell9.appendChild(txtfileno);
                 mycurrent_row.appendChild(cell9);
                 
                 var cell10=document.createElement("TD");
                 var txtfilerefdate=document.createTextNode(txtfilerefdate);
                 cell10.appendChild(txtfilerefdate);
                 mycurrent_row.appendChild(cell10);
                 
                 var cell11=document.createElement("TD");
                 var txtremarks=document.createTextNode(txtremarks);
                 cell11.appendChild(txtremarks);
                 mycurrent_row.appendChild(cell11);
                 
                 tbody.appendChild(mycurrent_row);
                
                 document.frmCashBook.CmdAdd.disabled=false;
                document.frmCashBook.CmdUpdate.disabled=true;
                document.frmCashBook.CmdDelete.disabled=true;
                document.frmCashBook.txtdateformarch.value="";
                document.frmCashBook.txtdatetomarch.value="";
                document.frmCashBook.txtdateforapril.value="";
                document.frmCashBook.txtdatetoapril.value="";
                document.frmCashBook.txtnoofother.value="";
                document.frmCashBook.txtnotoother.value="";
                document.frmCashBook.txtfileno.value="";
                document.frmCashBook.txtfilerefdate.value="";
                document.frmCashBook.txtremarks.value="";
                //document.frmCashBook.cmbassetclasscode.selectedIndex=0;
                document.frmCashBook.cmbFinancialYear.selectedIndex=0;
                //document.frmCashBook.txtdepreciationrates.value="";
               }
               if(flag=="failure")
               {
                alert("Record Not Inserted");
               }
                if(flag=="AlreadyExist")
                   {
                    alert("Record AlreadyExist.so,can't Inserted");
                   }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               
               
              items[0]=document.frmCashBook.cmbFinancialYear.value;
              items[1]=document.frmCashBook.txtdateformarch.value;
              items[2]=document.frmCashBook.txtdatetomarch.value;
              items[3]=document.frmCashBook.txtdateforapril.value;
              items[4]=document.frmCashBook.txtdatetoapril.value;
              items[5]=document.frmCashBook.txtnoofother.value;
              items[6]=document.frmCashBook.txtnotoother.value;
              items[7]=document.frmCashBook.txtfileno.value;
              items[8]=document.frmCashBook.txtfilerefdate.value;
              items[9]=document.frmCashBook.txtremarks.value;
               
               /*alert(items[0]);
               alert(items[1]);
               alert(items[2]);
               alert(items[3]);
               alert(items[4]);
               alert(items[5]);
               alert(items[6]);
               alert(items[7]);
               alert(items[8]);
               alert(items[9]);*/
               
               var r=document.getElementById(rid);
               //alert(rid);
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                rcells.item(4).firstChild.nodeValue=items[3];
                rcells.item(5).firstChild.nodeValue=items[4];
                rcells.item(6).firstChild.nodeValue=items[5];
                rcells.item(7).firstChild.nodeValue=items[6];
                rcells.item(8).firstChild.nodeValue=items[7];
                rcells.item(9).firstChild.nodeValue=items[8];
                rcells.item(10).firstChild.nodeValue=items[9];
                
//                rcells.item(1).f
                document.frmCashBook.CmdAdd.disabled=false;
                document.frmCashBook.CmdUpdate.disabled=true;
                document.frmCashBook.CmdDelete.disabled=true;           
                                
                document.frmCashBook.cmbFinancialYear.selectedIndex=0;
                document.frmCashBook.txtdateformarch.value="";
               document.frmCashBook.txtdatetomarch.value="";
               document.frmCashBook.txtdateforapril.value="";
               document.frmCashBook.txtdatetoapril.value="";
               document.frmCashBook.txtnoofother.value="";
               document.frmCashBook.txtnotoother.value="";
               document.frmCashBook.txtfileno.value="";
               document.frmCashBook.txtfilerefdate.value="";
               document.frmCashBook.txtremarks.value="";
                           
       }
       else
       {
           alert("failed to update values");
       }                                  
    }



function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                  if(flag=="success")
                  {
                      
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(rid);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                       document.frmCashBook.CmdAdd.disabled=false;
                        document.frmCashBook.CmdUpdate.disabled=true;
                        document.frmCashBook.CmdDelete.disabled=true;           
                                        
                        document.frmCashBook.cmbFinancialYear.selectedIndex=0;
                        document.frmCashBook.txtdateformarch.value="";
                       document.frmCashBook.txtdatetomarch.value="";
                       document.frmCashBook.txtdateforapril.value="";
                       document.frmCashBook.txtdatetoapril.value="";
                       document.frmCashBook.txtnoofother.value="";
                       document.frmCashBook.txtnotoother.value="";
                       document.frmCashBook.txtfileno.value="";
                       document.frmCashBook.txtfilerefdate.value="";
                       document.frmCashBook.txtremarks.value="";     
                               
                      alert("Selected Details are Deleted");                      
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
   
  }


function getRow(baseResponse)
    {   
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              {          
                       var tbody=document.getElementById("tblList");
                       var table=document.getElementById("Existing");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   
                                             
                            var FileNo=baseResponse.getElementsByTagName("FileNo");
                       j=1;     
                     for(var k=0;k<FileNo.length;k++)
                        {
                         
                         var DateForMarch=baseResponse.getElementsByTagName("DateForMarch");
                        
                         var DateToMarch=baseResponse.getElementsByTagName("DateToMarch");
                         var FinacialYear=baseResponse.getElementsByTagName("FinancialYear");
                         var FileNo=baseResponse.getElementsByTagName("FileNo");
                         var cDateToMarch=DateToMarch.item(k).firstChild.nodeValue;
                         var cFinacialYear=FinacialYear.item(k).firstChild.nodeValue;
                         var cFileNo=FileNo.item(k).firstChild.nodeValue;
                         
                         var DateForMarch=baseResponse.getElementsByTagName("DateForMarch")[k].firstChild.nodeValue;
                         var DateForApril=baseResponse.getElementsByTagName("DateForApril")[k].firstChild.nodeValue;
                         var DateToApril=baseResponse.getElementsByTagName("DateToApril")[k].firstChild.nodeValue;
                         var FileRefDate=baseResponse.getElementsByTagName("FileRefDate")[k].firstChild.nodeValue;
                         var Remarks=baseResponse.getElementsByTagName("Remarks")[k].firstChild.nodeValue;
                         var NoOfOther=baseResponse.getElementsByTagName("NoOfOther")[k].firstChild.nodeValue;
                         var NoToOther=baseResponse.getElementsByTagName("NoToOther")[k].firstChild.nodeValue;
                         
                         
                       /* alert('Dateformarch'+DateForMarch);
                        alert('datetomarch'+cDateToMarch);
                        alert('dateforapril'+DateForApril);
                        alert('datetoapril'+DateToApril);
                        alert('fileno'+cFileNo);
                        alert('Noofother'+NoOfOther);
                        alert('notoother'+NoToOther);
                        alert('Filerefdate'+FileRefDate);
                        alert('remarks'+Remarks);*/
                         
                         if(DateForMarch!="Not Specified")
                         {
                            DateForMarch=DateForMarch;
                         }
                         else
                         {
                            DateForMarch="";
                         }
                         if(cDateToMarch!="Not Specified")
                         {
                            cDateToMarch=cDateToMarch;
                         }
                         else
                         {
                            cDateToMarch="";
                         }
                         if(cFileNo!="null")
                         {
                            cFileNo=cFileNo;
                         }
                         else
                         {
                            cFileNo="";
                         }
                         if(DateForApril!="Not Specified")
                         {
                            DateForApril=DateForApril;
                         }
                         else
                         {
                            DateForApril="";
                         }
                         if(DateToApril!="Not Specified")
                         {
                            DateToApril=DateToApril;
                         }
                         else
                         {
                            DateToApril="";
                         }
                         if(FileRefDate!="Not Specified")
                         {
                            FileRefDate=FileRefDate;
                         }
                         else
                         {
                            FileRefDate="";
                         }
                         if(Remarks!="null")
                         {
                            Remarks=Remarks;
                         }
                         else
                         {
                         Remarks="";
                         }
                         if(NoOfOther!="null")
                         {
                            if(NoOfOther!="0")
                            {
                                NoOfOther=NoOfOther;
                            }
                            else
                            {
                                NoOfOther="";
                            }
                         }
                         else
                            {
                                NoOfOther="";
                            }
                        if(NoToOther!="null")
                        {
                            if(NoToOther!="0")
                            {
                                NoToOther=NoToOther;
                            }
                            else
                            {
                                NoToOther="";
                            }
                        }
                        else
                            {
                                NoToOther="";
                            }
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=cFinacialYear;
                         var cell=document.createElement("TD");
                         var anc=document.createElement("A");       
                         var url="javascript:loadValuesFromTable('" + cFinacialYear + "')";              
                         anc.href=url;
                         var txtedit=document.createTextNode("Edit");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                         
                         var hidden=document.createElement("input");
                         hidden.type="hidden";
                         hidden.name="k";
                         hidden.value=cFinacialYear;
                         cell.appendChild(hidden);
                         mycurrent_row.appendChild(cell);
                     
                         var cell2 =document.createElement("TD");    
                         var txtassetclassdesc=document.createTextNode(cFinacialYear);                         
                         cell2.appendChild(txtassetclassdesc);
                         
                         var hidden1=document.createElement("input");
                         hidden1.type="hidden";
                         hidden1.name="cFinacialYear";
                         hidden1.value=cFinacialYear;
                         cell2.appendChild(hidden1);
                         mycurrent_row.appendChild(cell2);
                        
                        
                         var cell3 =document.createElement("TD");    
                         var txtfinancialyear=document.createTextNode(DateForMarch);                         
                         cell3.appendChild(txtfinancialyear);   
                         
                         var hidden2=document.createElement("input");
                         hidden2.type="hidden";
                         hidden2.name="cDateForMarch";
                         hidden2.value=DateForMarch;
                         cell3.appendChild(hidden2);
                         mycurrent_row.appendChild(cell3);
                         
                         var cell4 =document.createElement("TD");    
                         var txtdatetomarch=document.createTextNode(cDateToMarch);                         
                         cell4.appendChild(txtdatetomarch);       
                         mycurrent_row.appendChild(cell4);
                         
                         
                         var cell5=document.createElement("TD");
                         var txtdateforapril=document.createTextNode(DateForApril);
                         cell5.appendChild(txtdateforapril);
                         mycurrent_row.appendChild(cell5);
                         
                         var cell6=document.createElement("TD");
                         var txtdatetoapril=document.createTextNode(DateToApril);
                         cell6.appendChild(txtdatetoapril);
                         mycurrent_row.appendChild(cell6);
                         
                         var cell7=document.createElement("TD");
                         var txtnoother=document.createTextNode(NoOfOther);
                         cell7.appendChild(txtnoother);
                         mycurrent_row.appendChild(cell7);
                         
                         var cell8=document.createElement("TD");
                         var txttoother=document.createTextNode(NoToOther);
                         cell8.appendChild(txttoother);
                         mycurrent_row.appendChild(cell8);
                         
                         var cell9=document.createElement("TD");
                         var txtfileno=document.createTextNode(cFileNo);
                         cell9.appendChild(txtfileno);
                         mycurrent_row.appendChild(cell9);
                         
                         var cell10=document.createElement("TD");
                         var txtfilerefdate=document.createTextNode(FileRefDate);
                         cell10.appendChild(txtfilerefdate);
                         mycurrent_row.appendChild(cell10);
                         
                         var cell11=document.createElement("TD");
                         var txtremarks=document.createTextNode(Remarks);
                         cell11.appendChild(txtremarks);
                         mycurrent_row.appendChild(cell11);
                         
                         j++;
                         tbody.appendChild(mycurrent_row);
                        }
                  }
                  else
                  {
                    alert("Failed to Load Values");
                  }
}

function assetRow(baseResponse)
{
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
            
              if(flag=="success")
              {
                
                var cmbassetclasscode=document.getElementById("cmbassetclasscode");
                var option=document.createElement("OPTION");
                cmbassetclasscode.innerHTML="";
                    option.text="--Select AssetClassCode--";
                    try
                                {
                                    cmbassetclasscode.add(option);
                            }catch(errorobject)
                            { 
                                     cmbassetclasscode.add(option,null);
                            }
                            
                var AssetClassCode=baseResponse.getElementsByTagName("options"); 
                
                for(var i=0;i<AssetClassCode.length;i++)
                {
                    var tmpoption=AssetClassCode.item(i);
                    
                    var AssetClassCode1=tmpoption.getElementsByTagName("AssetClassCode")[0].firstChild.nodeValue;
                    var AssetClassDesc1=tmpoption.getElementsByTagName("AssetClassDesc")[0].firstChild.nodeValue;
                    var option=document.createElement("OPTION");
                        option.text=AssetClassDesc1;
                        option.value=AssetClassCode1;
                              try
                                {
                                    cmbassetclasscode.add(option);
                            }catch(errorobject)
                            { 
                                     cmbassetclasscode.add(option,null);
                            }
                }
              }
    
}

function loadValuesFromTable(rid1)
    {      
        rid=rid1;
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          
          var table=document.getElementById("Existing");
          
          /*alert(rcells.item(1).lastChild.value);
          alert(rcells.item(2).firstChild.nodeValue);
          alert(rcells.item(3).firstChild.nodeValue);
          alert(rcells.item(4).firstChild.nodeValue);
          alert(rcells.item(5).firstChild.nodeValue);
          alert(rcells.item(6).firstChild.nodeValue);
          alert(rcells.item(7).firstChild.nodeValue);
          alert(rcells.item(8).firstChild.nodeValue);
          alert(rcells.item(9).firstChild.nodeValue);
          alert(rcells.item(10).firstChild.nodeValue);*/
          
          document.frmCashBook.cmbFinancialYear.value=rcells.item(1).lastChild.value;
          document.frmCashBook.txtdateformarch.value=rcells.item(2).firstChild.nodeValue;
          document.frmCashBook.txtdatetomarch.value=rcells.item(3).firstChild.nodeValue;
          document.frmCashBook.txtdateforapril.value=rcells.item(4).firstChild.nodeValue;
          document.frmCashBook.txtdatetoapril.value=rcells.item(5).firstChild.nodeValue;
          document.frmCashBook.txtnoofother.value=rcells.item(6).firstChild.nodeValue;
          document.frmCashBook.txtnotoother.value=rcells.item(7).firstChild.nodeValue;
          document.frmCashBook.txtfileno.value=rcells.item(8).firstChild.nodeValue;
          document.frmCashBook.txtfilerefdate.value=rcells.item(9).firstChild.nodeValue;
          document.frmCashBook.txtremarks.value=rcells.item(10).firstChild.nodeValue;
          
          /*document.frmCashBook.cmbassetclasscode.value=rcells.item(1).lastChild.value;
          document.frmCashBook.cmbFinancialYear.value=rcells.item(2).lastChild.value;
          document.frmCashBook.txtdepreciationrates.value=rcells.item(3).firstChild.nodeValue;*/
          document.frmCashBook.CmdAdd.disabled=true;
        document.frmCashBook.CmdUpdate.disabled=false;
        document.frmCashBook.CmdDelete.disabled=false;
        
          document.frmCashBook.CmdDelete.focus();
      
    }
    
function date()
{
    var datefrom=document.frmCashBook.txtdateformarch.value;
    var fin=document.frmCashBook.cmbFinancialYear.value;
    var f=fin.split("-")
    
    var s=datefrom.split("/");
    if((s[1]<2 || s[1]>2) || ((s[0]<26 || s[0]>31)) || (s[2]>f[1] || (s[2]<f[1]) )) 
    {
        alert("Please Enter Current Month and Date");
        return false;
    }
    /*if((s[0]<26 || s[0]>31))
    {
      alert("Please Enter Current Date");
      return false;
    
    }*/
}
function dateto()
{
    alert('inside');
    var dateto=document.frmCashBook.txtdatetomarch.value;
    var fin=document.frmCashBook.cmbFinancialYear.value;
    var f=fin.split("-")
    
    var s=dateto.split("/");
    if(s[1]>3 || s[1]<2)
    if(((s[1]>3 || s[1]<2) || (s[0]>31)) || (s[2]>f[1] || (s[2]<f[1]))) 
    {
        alert("Please Enter Current Month");
        return false;
    }

}