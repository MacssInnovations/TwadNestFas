//alert('hi');
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

function exit()
{
//var w=window.open(window.location.href,"_self");
//w.close();
self.close();
}


///////////////////////////////////////  Numbers only fields
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      //t.blur();
      //return true;-------------------- for taking action when press ENTER
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false 
    }
 }
function doFunction(Command,param)
{   
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value
       // var cmbStatus=document.getElementById("cmbStatus").value;
           var txtCB_Year=document.getElementById("txtCB_Year").value;
            var txtCB_Month=document.getElementById("txtCB_Month").value;
            //alert(txtCB_Month.length+"month")
        if(Command=='load_chequeNO')
        {
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0)
            {
                var url="../../../../../UDPRegister_ListAll.view?Command=load_chequeNO&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
                //"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCreat_By_Module="+txtCreat_By_Module+"&cmbStatus="+cmbStatus;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true);
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
      }
      else if(Command=='load_cheque_details')
      {
        var txtCheque_DD_NO=document.getElementById("txtCheque_DD_NO").value;
            if(txtCB_Year.length!=0 && txtCB_Month.length!=0 && txtCheque_DD_NO!="")
            {
                var url="../../../../../UDPRegister_ListAll.view?Command=load_cheque_details&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                "&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtCheque_DD_NO="+txtCheque_DD_NO;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true);
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
      }
}

function handleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
                    

            if(Command=="load_chequeNO")
            { 
                load_chequeNO(baseResponse);
            }
            else if(Command=="load_cheque_details")
            {
                load_cheque_details(baseResponse);
            }
        }
    }
}

function load_chequeNO(baseResponse)
{ 
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        var Cmb_chequeNO=document.getElementById("txtCheque_DD_NO");
        clear_Combo_local(Cmb_chequeNO);
        
        var LengOf_cheq_no=baseResponse.getElementsByTagName("cheq_no");
        for(var i=0;i<LengOf_cheq_no.length;i++)
        {
            var cheq_no=baseResponse.getElementsByTagName("cheq_no")[i].firstChild.nodeValue;
            var option=document.createElement("OPTION");
            option.text=cheq_no;
            option.value=cheq_no;
            try
            {
                Cmb_chequeNO.add(option);
            }catch(errorObject)
            {
                Cmb_chequeNO.add(option,null);
            }
        }
    }
    else
    {
        alert('No Cheque found');
        var Cmb_chequeNO=document.getElementById("txtCheque_DD_NO");
        clear_Combo_local(Cmb_chequeNO);
       
    }
}
function load_cheque_details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        document.getElementById("txtDrawl_date").value= baseResponse.getElementsByTagName("date_drwal")[0].firstChild.nodeValue;
        document.getElementById("txtAmount").value=baseResponse.getElementsByTagName("tot_cheq_amt")[0].firstChild.nodeValue;
        var remark=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
        if(remark!="null")
            document.getElementById("txtRemarks").value=remark;
        else
            document.getElementById("txtRemarks").value="";
        
    } 
    else
    {
        alert("Details not found");
        document.getElementById("txtDrawl_date").value="";
        document.getElementById("txtAmount").value="";
        document.getElementById("txtRemarks").value="";
        checkdate="";
    }
}


function call_clr()
{
    document.getElementById("txtCheque_DD_NO").value="";
    document.getElementById("txtDrawl_date").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
}

function clrForm()
{
   if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
 }
}


function checkNull()
{
        var tbody=document.getElementById("grid_body_Acq");
      
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
        if(document.getElementById("txtCB_Year").value.length==0)
        {
            alert("Enter the year");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        if(document.getElementById("txtCB_Year").value.length==0)
        {
            alert("Enter the Month");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        if(document.getElementById("txtCheque_DD_NO").value=="")
        {
            alert("Select the Cheque number");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        if(document.getElementById("txtDrawl_date").value=="")
        {
            alert("Select the Cheque number");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
        return true;
}


function clear_Combo_local(combo)
{
        //alert(combo.id)
        var combo_Id=document.getElementById(combo.id);   
        combo_Id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Cheque Number--";
        option.value="";
        try
        {
            combo_Id.add(option);
        }catch(errorObject)
        {
            combo_Id.add(option,null);
        }
}