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
        cyr=cyr+1
 	if (parseInt(cmn) <= 2)
        {
  
                document.frmStaffStrength.cmbFinancialYear.length=5;
                cmbFinancialYear.innerHTML="";
               /* var option=document.createElement("OPTION");
                option.text="--Select FinancialYear--";
                option.value=0;*/
                for (var i = 0 ; i < 2; i++) 
                {
         
                  //document.frmStaffStrength.cmbFinancialYear.options[i].text=(cyr-2)+"-"+(cyr-1);
                  //document.frmStaffStrength.cmbFinancialYear.options[i].value=(cyr-2)+"-"+(cyr-1);
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
           document.frmStaffStrength.cmbFinancialYear.length=5;
           cmbFinancialYear.innerHTML="";
         /*  var option=document.createElement("OPTION");
           option.text="--Select FinancialYear--";
           option.value=0;*/
            for (var i = 0 ; i < 2; i++) 
            {
              //document.frmStaffStrength.cmbFinancialYear.options[i].text=(cyr-1)+"-"+(cyr);
              //document.frmStaffStrength.cmbFinancialYear.options[i].value=(cyr-1)+"-"+(cyr);
              var id=(cyr-1)+"-"+(cyr);
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



//Controlling Office Selection

function getOfficesByType()
    {
       
        var type=document.frmStaffStrength.cmbOfficeType.options[document.frmStaffStrength.cmbOfficeType.selectedIndex].value;
        
        if(type!=0)
        {
            var din=document.getElementById("divType2");
            din.style.visibility="visible";
            document.frmStaffStrength.cmbSelectOffice.style.visibility="visible";
            loadOfficesByType(type);
        }
    }
    
    function getOfficesByLevel()
    {
    
        var level=document.frmStaffStrength.cmbFromOfficeId.options[document.frmStaffStrength.cmbFromOfficeId.selectedIndex].value;
        var levelt=document.frmStaffStrength.cmbFromOfficeId.options[document.frmStaffStrength.cmbFromOfficeId.selectedIndex].text;
        
        if(levelt=="Division" || levelt=="Sub-Division" || levelt=="Section" )
        {
            var din=document.getElementById("divType1");
            din.style.visibility="visible";
            document.frmStaffStrength.cmbOfficeType.style.visibility="visible";
            var din=document.getElementById("divType2");
            din.style.visibility="hidden";
            document.frmStaffStrength.cmbSelectOffice.style.visibility="hidden";
            try
            {
                document.frmStaffStrength.cmbOfficeType.focus();
                document.frmStaffStrength.cmbOfficeType.select();
            }catch(e){}
        }
        else
        {
            var din=document.getElementById("divType1");
            din.style.visibility="hidden";
            document.frmStaffStrength.cmbOfficeType.style.visibility="hidden";
            var din=document.getElementById("divType2");
            din.style.visibility="visible";
            document.frmStaffStrength.cmbSelectOffice.style.visibility="visible";
            if(level!="----Select OfficeLevel----")
            {
                loadOfficesByLevel(level);
            }
        }
    }
    
    function loadOfficesByLevel(level)
    {
        startwaiting(document.frmStaffStrength) ;
        var order=document.frmStaffStrength.txtOrderId.value;
        //alert(order);
        var url="../../../../../ServletOfficelevelStaff.con?command=level&level="+level;
        //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices(req);
        }
        req.send(null);
    }
    
    function loadOfficesByType(type)
    {
        startwaiting(document.frmStaffStrength) ;
        var order=document.frmStaffStrength.txtOrderId.value;
        var level=document.frmStaffStrength.cmbFromOfficeId.options[document.frmStaffStrength.cmbFromOfficeId.selectedIndex].value;
        var url="../../../../../ServletOfficelevelStaff.con?command=type&level=" + level + "&type="+type;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices(req);
        }
        req.send(null);
    }
    
    function loadOffices(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
                stopwaiting(document.frmStaffStrength) ;
                //alert(req.responseTEXT);
                var cmboffices=document.getElementById("cmbSelectOffice");
                //alert(cmboffices);
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                cmboffices.innerHTML="";
                
                var option1=document.createElement("OPTION");
                option1.text="----Select Office----";
                option1.value="0";
                try
                {
                    cmboffices.add(option1);
                }
                catch(errorObject)
                {
                    cmboffices.add(option1,null);
                }
                
                if(flag=="failure")
                {
                    alert("No Offices exists under this level");
                }
                else
                {                    
                    var value=response.getElementsByTagName("options");
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              cmboffices.add(option);
                          }
                          catch(errorObject)
                          {
                              cmboffices.add(option,null);
                          }
                    }
                }
            }
        }
    }
    
    function selectControllineOffice()
    {
        var ctlOffice=document.frmStaffStrength.cmbSelectOffice.options[document.frmStaffStrength.cmbSelectOffice.selectedIndex].value;
        var ctlOfficetext=document.frmStaffStrength.cmbSelectOffice.options[document.frmStaffStrength.cmbSelectOffice.selectedIndex].text;
        
        if(ctlOffice!=0)
        {
            document.frmStaffStrength.txtdiversionfromoffice.value=ctlOffice;
            document.frmStaffStrength.txtHdiversionfromoffice.value=ctlOffice;
            PostRank1(ctlOffice);
            loadofficeaddress(ctlOffice);
            //alert(ctlOffice);
           // officedetails(ctlOffice);
        }    
        /*if(document.frmStaffStrength.cmbSelectdOffice1.options[document.frmStaffStrength.cmbSelectOffice1.selectedIndex].value!="")
        {
            if((document.frmStaffStrength.cmbSelectOffice.options[document.frmStaffStrength.cmbSelectOffice.selectedIndex].value)==(document.frmStaffStrength.cmbSelectOffice1.options[document.frmStaffStrength.cmbSelectOffice1.selectedIndex].value))
                {
                alert("Choose Some Other Office");
                document.frmStaffStrength.txtdiversionfromoffice.value="";
                document.frmStaffStrength.txtHdiversionfromoffice.value="";
                document.frmStaffStrength.cmbSelectOffice.options[document.frmStaffStrength.cmbSelectOffice.selectedIndex].focus();
                return false;
                }
                else
                {
                    if(ctlOffice!=0)
                    {
                        document.frmStaffStrength.txtdiversionfromoffice.value="";
                        document.frmStaffStrength.txtHdiversionfromoffice.value="";
                        document.frmStaffStrength.txtdiversionfromoffice.value=ctlOffice;
                        document.frmStaffStrength.txtHdiversionfromoffice.value=ctlOffice;
                                 
                    }  
                }
        }*/
    }





//Controlling Office to Selection


function getOfficesByType1()
    {
       
        var type=document.frmStaffStrength.cmbOfficeType1.options[document.frmStaffStrength.cmbOfficeType1.selectedIndex].value;
        
        if(type!=0)
        {
            var din=document.getElementById("divType4");
            din.style.visibility="visible";
            document.frmStaffStrength.cmbSelectOffice1.style.visibility="visible";
            loadOfficesByType1(type);
        }
    }
    
    function getOfficesByLevel1()
    {
    
        var level=document.frmStaffStrength.cmbToOfficeId.options[document.frmStaffStrength.cmbToOfficeId.selectedIndex].value;
        var levelt=document.frmStaffStrength.cmbToOfficeId.options[document.frmStaffStrength.cmbToOfficeId.selectedIndex].text;
        
        if(levelt=="Division" || levelt=="Sub-Division" || levelt=="Section" )
        {
            var din=document.getElementById("divType3");
            din.style.visibility="visible";
            document.frmStaffStrength.cmbOfficeType1.style.visibility="visible";
            var din=document.getElementById("divType4");
            din.style.visibility="hidden";
            document.frmStaffStrength.cmbSelectOffice1.style.visibility="hidden";
            try
            {
                document.frmStaffStrength.cmbOfficeType1.focus();
                document.frmStaffStrength.cmbOfficeType1.select();
            }catch(e){}
        }
        else
        {
            var din=document.getElementById("divType3");
            din.style.visibility="hidden";
            document.frmStaffStrength.cmbOfficeType1.style.visibility="hidden";
            var din=document.getElementById("divType4");
            din.style.visibility="visible";
            document.frmStaffStrength.cmbSelectOffice1.style.visibility="visible";
            if(level!="----Select OfficeLevel----")
            {
                loadOfficesByLevel1(level);
            }
        }
    }
    
    function loadOfficesByLevel1(level)
    {
        startwaiting(document.frmStaffStrength) ;
        var url="../../../../../ServletGetOfficesByTypeOrLevel.con?command=level&level="+level;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices1(req);
        }
        req.send(null);
    }
    
    function loadOfficesByType1(type)
    {
        startwaiting(document.frmStaffStrength) ;
        var level=document.frmStaffStrength.cmbToOfficeId.options[document.frmStaffStrength.cmbToOfficeId.selectedIndex].value;
        var url="../../../../../ServletGetOfficesByTypeOrLevel.con?command=type&level=" + level + "&type="+type;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices1(req);
        }
        req.send(null);
    }
    
    function loadOffices1(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
                stopwaiting(document.frmStaffStrength) ;
                var cmboffices=document.getElementById("cmbSelectOffice1");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                cmboffices.innerHTML="";
                
                var option1=document.createElement("OPTION");
                option1.text="----Select Office----";
                option1.value="0";
                try
                {
                    cmboffices.add(option1);
                }
                catch(errorObject)
                {
                    cmboffices.add(option1,null);
                }
                
                if(flag=="failure")
                {
                    alert("No Offices exists under this level");
                }
                else
                {                    
                    var value=response.getElementsByTagName("options");
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              cmboffices.add(option);
                          }
                          catch(errorObject)
                          {
                              cmboffices.add(option,null);
                          }
                    }
                }
            }
        }
    }
    
    function selectControllineOffice1()
    {
        var ctlOffice=document.frmStaffStrength.cmbSelectOffice1.options[document.frmStaffStrength.cmbSelectOffice1.selectedIndex].value;
        var ctlOfficetext=document.frmStaffStrength.cmbSelectOffice1.options[document.frmStaffStrength.cmbSelectOffice1.selectedIndex].text;
        
        //alert(ctlOffice);
        //document.frmStaffStrength.cmbSelectOffice.options[document.frmStaffStrength.cmbSelectOffice.selectedIndex].value)==(document.frmStaffStrength.cmbSelectOffice1.options[document.frmStaffStrength.cmbSelectOffice1.selectedIndex].value
        document.frmStaffStrength.txtdiversiontooffice.value=ctlOffice;
        if((document.frmStaffStrength.txtdiversiontooffice.value==document.frmStaffStrength.txtdiversionfromoffice.value))
        {
        alert("Diversion Cannot be effected in the Same Office");
        document.frmStaffStrength.txtdiversiontooffice.value="";
        document.frmStaffStrength.txtHdiversiontooffice.value="";
        document.frmStaffStrength.cmbSelectOffice1.options[document.frmStaffStrength.cmbSelectOffice1.selectedIndex].focus();
        return false;
        }
        else
        {
            if(ctlOffice!=0)
            {
                document.frmStaffStrength.txtdiversiontooffice.value="";
                document.frmStaffStrength.txtHdiversiontooffice.value="";
                document.frmStaffStrength.txtdiversiontooffice.value=ctlOffice;
                document.frmStaffStrength.txtHdiversiontooffice.value=ctlOffice;
                loadofficeaddressto(ctlOffice);          
            }  
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


function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
           /*else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to 1970');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to 1970');
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
         
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
           /*else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be greater than or equal to 1970');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be greater than or equal to 1970');
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

     
function nullCheck()
{
    if((document.frmStaffStrength.cmbOrderSlNo.value==0) || (document.frmStaffStrength.cmbOrderSlNo.value.length<=0))
    {
        alert("Enter Diversion Extension Number");
        document.frmStaffStrength.cmbOrderSlNo.focus();
        return false;
    }
    if((document.frmStaffStrength.txtExOrderDate.value==0) ||(document.frmStaffStrength.txtExOrderDate.value.lenth<=0))
    {
        alert("Enter Diversion Extension Order Date");
        document.frmStaffStrength.txtExOrderDate.focus();
        return false;
    }
    if((document.frmStaffStrength.txtExUpto.value==0)||(document.frmStaffStrength.txtExUpto.value.length<=0))
    {
        alert("Enter Diversion Extended Upto");
        document.frmStaffStrength.txtExUpto.focus();
        return false;
    }
    return true;
}

function clear1()
{

document.frmStaffStrength.txtOffice_Id.disabled=false;
document.frmStaffStrength.txtOffice_Id.value="";
document.frmStaffStrength.txtOffice_Id.disabled=true;
document.frmStaffStrength.cmbSubmit.disabled=false;
document.frmStaffStrength.cmbOrderId.disabled=false;
//var noofsanction=document.getElementById("noofsantionpost");
//noofsanction.style.display="none";

var tbody=document.getElementById("tblList");
var t=0;
for(t=tbody.rows.length-1;t>=0;t--)
{
    tbody.deleteRow(0);
}
}


function noofsanction1()
{
var noofsanction=document.getElementById("noofsantionpost");
noofsanction.style.display="block";
    
}

function checksanction()
{
    var sanction=document.frmStaffStrength.noofsanction.value;
    var noofpost=document.frmStaffStrength.txtPostDiverted.value;
    var divertedto=document.frmStaffStrength.divertedtopost.value;
    var result=parseInt(sanction)-parseInt(divertedto);
    //alert(result);
    if(parseInt(noofpost)>parseInt(result))
    {
        alert("No of Post to be diverted exists the Sanctioned Strength");
        document.frmStaffStrength.txtPostDiverted.focus();
        return false;
    }
    
}


