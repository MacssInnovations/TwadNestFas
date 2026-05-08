function getTransport()
{
    var req=false;
    try
    {
    req=new ActiveXObject("Msxml2.XMLHTTP");
    }catch(e1)
    {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if (!req && typeof XMLHttpRequest != 'undefined') 
        {
        req=new XMLHttpRequest();
        }
   return req;
   
}  
function loadfun()
{   
	var req=getTransport();  
    var url="../../../../../Assets_Major_Classification_Serv?command=Load";
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);                                       
}
function manipulate(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {              
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
              if(command=="Load")
              {
                  getDetails(baseResponse);
              }
            }
      }
}
function getDetails(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="Success")
     {    //alert("success=========");
            var count=baseResponse.getElementsByTagName("count");
           // alert(baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue);
         //   alert(count.length);
            var tbody=document.getElementById("tb");
           try{
            for(var i=0;i<count.length;i++)
            {     
                var class_code=baseResponse.getElementsByTagName("class_code")[i].firstChild.nodeValue;
              //  alert("class_code"+class_code);
                var class_desc=baseResponse.getElementsByTagName("class_desc")[i].firstChild.nodeValue;
                var asset_type=baseResponse.getElementsByTagName("asset_type")[i].firstChild.nodeValue;
                var alias_code=baseResponse.getElementsByTagName("alias_code")[i].firstChild.nodeValue;
                
                var major_class=baseResponse.getElementsByTagName("major_class")[i].firstChild.nodeValue;
                var individual_folio=baseResponse.getElementsByTagName("individual_folio")[i].firstChild.nodeValue;
                var minor_class=baseResponse.getElementsByTagName("minor_class")[i].firstChild.nodeValue;
                var depreciable=baseResponse.getElementsByTagName("depreciable")[i].firstChild.nodeValue;
                var view=baseResponse.getElementsByTagName("status")[i].firstChild.nodeValue;               
                var mycurrent_row=document.createElement("TR");
                mycurrent_row.id=class_code;
           
                var cell=document.createElement("TD");
                var check="";
                if(view=="C"){
                	var priceSpan = document.createElement("span");
        			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
        			priceSpan.appendChild(document.createTextNode("Cancel"));			
        			cell.appendChild(priceSpan);
        			mycurrent_row.appendChild(cell);
                }else{
                	if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                    {
                        check=document.createElement("<INPUT type='radio' name='check1' value='"+class_code+"' size='10'>");
                    }
                    else
                    {  
                           check=document.createElement("input");
                           check.type="radio";
                           check.name="check1";
                           check.value=class_code;
                    }
                    cell.appendChild(check);
                    mycurrent_row.appendChild(cell);
                }                
                var cell2=document.createElement("TD");
                cell2.setAttribute('align','left');
                var code="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                	code=document.createElement("<INPUT type='hidden' name='class_code' id='class_code' size='3' style='background-color:#ececec' value='"+class_code+"'>");
                }
                else
                {   
                	code=document.createElement("input");
                	code.type="hidden";
                	code.size="3";
                	code.name="class_code";
                	code.id="class_code";
                	code.value=class_code;
                	code.readonly="readonly";
                }
                cell2.appendChild(code);
                var currentText1=document.createTextNode(class_code);
                cell2.appendChild(currentText1);
                mycurrent_row.appendChild(cell2); 
                
                var cell2=document.createElement("TD");
                cell2.setAttribute('align','left');
                var code="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                	code=document.createElement("<INPUT type='hidden' name='class_desc' id='class_desc' size='3' style='background-color:#ececec' value='"+class_desc+"'>");
                }
                else
                {   
                	code=document.createElement("input");
                	code.type="hidden";
                	code.size="3";
                	code.name="class_desc";
                	code.id="class_desc";
                	code.value=class_desc;
                	code.readonly="readonly";
                }
                cell2.appendChild(code);
                var currentText1=document.createTextNode(class_desc);
                cell2.appendChild(currentText1);
                mycurrent_row.appendChild(cell2);
           
                var cell2=document.createElement("TD");
                cell2.setAttribute('align','left');
                var code="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                	code=document.createElement("<INPUT type='hidden' name='asset_type' id='asset_type' size='3' style='background-color:#ececec' value='"+asset_type+"'>");
                }
                else
                {   
                	code=document.createElement("input");
                	code.type="hidden";
                	code.size="3";
                	code.name="asset_type";
                	code.id="asset_type";
                	code.value=asset_type;
                	code.readonly="readonly";
                }
                cell2.appendChild(code);
                var currentText1=document.createTextNode(asset_type);
                cell2.appendChild(currentText1);
                mycurrent_row.appendChild(cell2); 
                
                var cell2=document.createElement("TD");
                cell2.setAttribute('align','left');
                var code="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                	code=document.createElement("<INPUT type='hidden' name='alias_code' id='alias_code' size='3' style='background-color:#ececec' value='"+alias_code+"'>");
                }
                else
                {   
                	code=document.createElement("input");
                	code.type="hidden";
                	code.size="3";
                	code.name="alias_code";
                	code.id="alias_code";
                	code.value=alias_code;
                	code.readonly="readonly";
                }
                cell2.appendChild(code);
                var currentText1=document.createTextNode(alias_code);
                cell2.appendChild(currentText1);
                mycurrent_row.appendChild(cell2); 
                
                var cell2=document.createElement("TD");
                cell2.setAttribute('align','left');
                var mclass="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                	mclass=document.createElement("<INPUT type='hidden' name='major_class' id='major_class' size='3' style='background-color:#ececec' value='"+major_class+"'>");
                }
                else
                {   
                	mclass=document.createElement("input");
                	mclass.type="hidden";
                	mclass.size="3";
                	mclass.name="major_class";
                	mclass.id="major_class";
                	mclass.value=major_class;
                	mclass.readonly="readonly";
                }
                cell2.appendChild(mclass);
                var currentText1=document.createTextNode(major_class);
                cell2.appendChild(currentText1);
                mycurrent_row.appendChild(cell2); 
    
                var cell11=document.createElement("TD");
                cell11.setAttribute('align','left');
                var folio="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                	folio=document.createElement("<INPUT type='hidden' name='individual_folio' id='individual_folio' size='20' style='background-color:#ececec' value="+individual_folio+">");
                }
                else
                {   
                	folio=document.createElement("input");
                	folio.type="hidden";
                	folio.size="20";
                	folio.name="individual_folio";
                	folio.id="individual_folio";
                	folio.value=individual_folio;
                }
                cell11.appendChild(folio);
                var currentText5=document.createTextNode(individual_folio);
                cell11.appendChild(currentText5);
                mycurrent_row.appendChild(cell11); 
                
                
                var cell11=document.createElement("TD");
                cell11.setAttribute('align','left');
                var mclass="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                	mclass=document.createElement("<INPUT type='hidden' name='minor_class' id='minor_class' size='20' style='background-color:#ececec' value="+minor_class+">");
                }
                else
                {   
                	mclass=document.createElement("input");
                	mclass.type="hidden";
                	mclass.size="20";
                	mclass.name="minor_class";
                	mclass.id="minor_class";
                	mclass.value=minor_class;
                }
                cell11.appendChild(mclass);
                var currentText5=document.createTextNode(minor_class);
                cell11.appendChild(currentText5);
                mycurrent_row.appendChild(cell11);
                
                var cell11=document.createElement("TD");
                cell11.setAttribute('align','left');
                var dep="";
                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                {
                	dep=document.createElement("<INPUT type='hidden' name='depreciable' id='depreciable' size='20' style='background-color:#ececec' value="+depreciable+">");
                }
                else
                {   
                	dep=document.createElement("input");
                	dep.type="hidden";
                	dep.size="20";
                	dep.name="depreciable";
                	dep.id="depreciable";
                	dep.value=depreciable;
                }
                cell11.appendChild(dep);
                var currentText5=document.createTextNode(depreciable);
                cell11.appendChild(currentText5);
                mycurrent_row.appendChild(cell11);
                
                var cell12 = document.createElement("TD");
        		var vie = document.createTextNode(view);
        		cell12.appendChild(vie);
        		mycurrent_row.appendChild(cell12);
                
                tbody.appendChild(mycurrent_row);
            }
           }catch(err){            	
           }
     }
}

function onSubmit()
{
    var v=document.getElementsByName("check1");
    if(v)
    {
        for(i=0;i<v.length;i++)
        {
                if(v[i].checked==true)
                {
                    Minimize();
                    opener.doParentEmp(v[i].value);
                    return true;
                }
        }
    }
}

function Minimize()
{
    window.close();
    opener.window.focus();
}