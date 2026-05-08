 /*
  * major - TR,TD,
  * item - input ,label
  * type -  text,redio,check
  * name -  item name
  * value -  display value
  * size  -  size of control
  * sclass - style class
  * url    - linked url 
  * style -  user writed style
  * tdwidth - td width
  * tdalign -  td align
  * funtype - function type (event)
  * funname - function name if (more then one user comma sepearated)
 
  */
var src="";
function cell(major,item,type,name,value,size,sclass,url,style,tdwidth,tdalign,funtype,funname)
 {  
	var name_cell=document.createElement(major);
    if (major=="TR")
    {
    name_cell.setAttribute("id", name);
    name_cell.setAttribute("style", style);
   
    }
	
	
	
	
	
	if (major=="TD")
    {
	name_cell.setAttribute("align",tdalign);
    name_cell.setAttribute("width",tdwidth); 
    name_cell.setAttribute("style", style); 
    
    
    var name_label=document.createElement(item);  //label,text,combo
   
    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
    {
		    if (item=="select")
			{
		    	var fun1="";
		    	if (funtype!="")
		    		fun1= funtype+"='"+funname+"'";
          		  else
          			fun1="";	
		    		
			name_label=document.createElement("<SELECT id='"+name+"' name='"+name+"' style='"+style+"' class='"+sclass+"' "+fun1+" >");
			name_label.setAttribute("id", name)
			name_label.setAttribute("name", name)
 			var optn1 = document.createElement("OPTION");
			theText=document.createTextNode("Yes");
			optn1.appendChild(theText);
			optn1.setAttribute("value","Y");
			
			if (value=="Yes") 
		    optn1.setAttribute("selected ","1");
			name_label.appendChild(optn1);
		    var optn2 = document.createElement("OPTION");	
		    theText=document.createTextNode("No");
		    optn2.appendChild(theText);
		    optn2.setAttribute("value","N");
		    if (value=="No")
		    optn2.setAttribute("selected","1");
		    name_label.appendChild(optn2);
		    name_cell.appendChild(name_label);
		    }   
		    if (item=="span" )
	           {
	           name_label=document.createElement("<span id='"+name+"' name='"+name+"' style='"+style+"' class='"+sclass+"'></span>");
	           
	           name_label.innerHTML=""+value+"";            		 
 	           name_cell.appendChild(name_label);
	              }
		    
		    var fun="";
  		  if (funtype!="")
            fun= funtype+"="+funname+"";
  		  else
  			  fun="";
  		  var fs="";
  		  var fun_type=funtype.split("#");
            var fun_name=funname.split("#");
            
      	  var len=fun_type.length;
      	  
      	  if (funtype!="")
      	  {
      		  for (var i=0;i<len;i++)
      		  {
      			 
      			  fs+=fun_type[i]+"="+fun_name[i]+"   ";
      		  }
      		  
      	  }
		    
		    		if (item=="label" )
		    		{
        	   
		    			name_label=document.createElement("<label id='"+name+"'name='"+name+"' style='"+style+"' class='"+sclass+"' "+fs+" >");
		    			name_label.innerHTML=""+value+"";            		 
            		  name_cell.appendChild(name_label);
		    		}
            	  else if(item=="input")
            	  {
            		  
            		  
            		  
            		 
            		 
            		if (type=="checkbox" || type=="radio")  {          			 
            		    name_label=document.createElement("<INPUT type="+type+" id='"+name+"' name='"+name+"'  size='"+size+"' style='"+style+"'   class='"+sclass+"' "+fs+" >");
            		}
            		else if (type=="text")
            		{ 
            			    			name_label=document.createElement("<INPUT type="+type+" id='"+name+"' value="+value+" name='"+name+"'  size='"+size+"' style="+style+"   class='"+sclass+"' "+fs+" >");
            			    		
            		}
            		else
            		{	
            			name_label=document.createElement("<INPUT type="+type+" id='"+name+"' value='"+value+"' name='"+name+"' size='"+size+"' style='"+style+"'   class='"+sclass+"' "+fs+" >");
            		}
            		
            		 
            		  
             		  if(type!="hidden")
            		  name_cell.appendChild(name_label);
            		  else
            		  name_cell=name_label;
            		  
            		  
            		  
            		  
            	  }else if (item=="A")
                  {
            		  
            		  var name_label =document.createElement("<A href="+url+" id='"+name+"' name='"+name+"'  size='"+size+"' style='"+style+"'   class='"+sclass+"' "+fun+" >"+value+"</a>");
            		//  var name_label=document.createElement("A");
                     // var url=url;
                    //  name_label.href=url;
                       var edit=document.createTextNode(value);
                       name_label.appendChild(edit);
            		  
        	  		  name_cell.appendChild(name_label);
                  }
            	  
            	  	 
            	  	
            	  	  
              }
              else
              {	  
            	  if (item=="select")
              	 {
            		 	
                 	 name_label=document.createElement("SELECT");
                 	 name_label.setAttribute("id", name)
                 	 name_label.setAttribute("name", name)
                 	  name_label.setAttribute(funtype, funname);
                 	
                 	 
                 	 
                 	 var optn = document.createElement("OPTION");
              	     	 optn.text = "Yes";
              	     	 optn.value = "Y";
              	     	 
              	     	if (value=="Yes")
                  	    optn.setAttribute("selected","1");	     
              	     	 
              	     	name_label.options.add(optn);     
              	                  	     	
              	 	 var optn2 = document.createElement("OPTION");
              	 	 	 optn2.text = "No";
              	 	 	 optn2.value = "N";
              	 	 	 
              	 	 	if (value=="No")
              	 	 	optn2.setAttribute("selected","1");	
              	 	 	 
              	 	 	name_label.options.add(optn2);              	 	 	 
              		    name_cell.appendChild(name_label);            		
              	} else         		
              	{  
            	  
            	  if (type!="label")
            	  name_label.setAttribute("type",type);
            	 
            	  name_label.setAttribute("value",value);
            	  name_label.setAttribute("name", name);
            	  name_label.setAttribute("id", name);
            	  name_label.setAttribute("size",size);
            	  name_label.setAttribute("class",sclass);
            	  name_label.setAttribute("style",style);
            	 
            	  
            	  
                  
                  var fun_type=funtype.split("#");
                  var fun_name=funname.split("#");
                  
            	  var len=fun_type.length;
                  
            	  if (funtype!="")
            	  {
            		  for (var i=0;i<len;i++)
            		  {
            			 
            		     name_label.setAttribute(fun_type[i], fun_name[i]);
            		  }
            		  
            	  }
            	  
            	  
            	  
            	  if (item=="A")
                  {
            		     		  
            		  name_label.href=url;       
                  }
                  var name_data=document.createTextNode(value);//value
                  name_label.appendChild(name_data);
                  
                  
                  if(type!="hidden")
            		  name_cell.appendChild(name_label);
            		  else
            		  name_cell=name_label
                  
                  
              	}
              }
              
              
            } 
		       
              return name_cell;
 }
function createObject()
{	  
	try
	{
	var xmlHttp = false;
	   try {
		   xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")   // For Microsoft IE 6.0+
	   }
	   catch (e) {
		   
	     try {
	    	 xmlHttp = new ActiveXObject("Microsoft.XMLHTTP") 
	     // For Old Microsoft Browsers
	     }
	     catch (e2) {
	       xmlHttp = false;   // No Browser accepts the XMLHTTP Object then false
	     }
	   }
	   if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
	     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
	   }
	  
	   return xmlHttp;  // Mandatory Statement returning the ajax object created

	}catch(e)
	{
		alert("XML Object Not Created\n--------------------------")
	}
	
}
function addOption(selectbox,text,value)
{  
	 
	try
	{
			var optn = document.createElement("OPTION");
			optn.text = text;
			optn.value = value;
			if (this.src=='ob')
			{
				if (selectbox.id=="year")
				{
					 var d=new Date();
					 var cy=d.getFullYear();
					 var cm=d.getMonth()+1;
					 
					 
					if (value==parseInt(cy))
					{
							if ( parseInt(cm) <=3)
							{
								
								
							}	
						
					optn.selected=1;
					}
					
				}
				if (selectbox.id=="month")
				{
					if (value=="4")
					optn.selected=1;
				}
			}
			
			selectbox.options.add(optn);
	}catch(e)
	{
			alert("Option Creation Have Problem\n-------------------------------------")
		
	}
}





function monthselect(i)
{
	var month =new Array("Select","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec");	
return month[i];
}
function month(smonth,src)
{
var d=new Date();
var cm=d.getMonth()+1;
this.src=src;
var month =new Array("-select month-","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec");	
for (i=0;i<month.length;i++)
{
	var sno =i;
    var name =month[i];
    addOption(document.getElementById(smonth),name,sno)
}
}

function year(year,src)
{
	this.src=src;
	var year_v1 =new Array("-select year-","2009","2010");	
	var year_v =new Array("-select year-","2009-2010","2010-2011");
	 
	 
 	for (i=0;i<year_v.length;i++)
	{
		
		var sno=year_v1[i];
		var name =year_v1[i]
	
		addOption(document.getElementById(year),name,sno)
}
}
function fld_clr(name)
{
	 
	document.getElementById(name).value="";
	 
}

function combo_sel_value(in_id,out_id)
{
	
	 var sel_value_res=document.getElementById(in_id).options[document.getElementById(in_id).selectedIndex].text;
 	 document.getElementById(out_id).value=sel_value_res;
}
function IsNumeric(inputVal,sErrorMsg) {
	
    if (isNaN(parseFloat(inputVal))) {
         alert(sErrorMsg)
         return false;
    }
    return true;
}

function comboRemove(id){
	document.getElementById(id).length = 0;
}

function vTest(a)
{
alert("TESTING\n====================\n"+a)	
}
 
function isInteger(s,dig,event)
{ 
       
       var i;
	  s = s.value.toString();
	  var ss="";
	  
      for (i = 0; i < s.length; i++)
      {
       var c = s.charAt(i);
       
       if (isNaN(c)) 
	   {
		alert("Given value is not a number");
		return false;
	   }
       else
       {
    	   ss+=c;
    	   
       }
       
       
      }
      
      
      return true;
    /* 
	
	var unicode=event.charCode? event.charCode : event.keyCode;
	
		if (unicode!=8 && unicode!=9 && unicode!=37 && unicode!=39 && unicode!=46)
    {
         if (unicode<48 || unicode>57 || txt.length>=dig)
            return false;
    }
		 */
}
 
function dec(input)
{
	 var input=new String(input);
	 
		
	if (input=="0.0" || input=="") input="0";
	var d3=new Packages.java.text.DecimalFormat("0.00");
	
	var res=d3.format(input);
	
	
	return (res)
	
}
	
function date_val(first,second)
{
	try
	{
	var first=document.getElementById(first).value;
	var second=document.getElementById(second).value;
	 
	var browser=navigator.appName;
	
	if (browser=="Netscape")
	{
		var dd1=first.split('/');
		first=dd1[1]+"/"+dd1[0]+"/"+dd1[2];
		
		var dd2=second.split('/');
		second=dd2[1]+"/"+dd2[0]+"/"+dd2[2];
		
	}
	var a=first.split('/');
	var b=second.split('/');
	var fDate=new Date(a[2],a[0]-1,a[1]);
	var sDate=new Date(b[2],b[0]-1,b[1]);

	if (fDate >= sDate)
	{
		alert("Date Not Valid for Transaction");
	return 1;	
	}else
	{
		return 0;
	}
	}catch(e) {return 1;}
}

function flash()
{	
	pr_status()
}
function pr_status()
{
	var first=document.getElementById("pr_status").value;
	if (first==0)
	{
	document.getElementById("msg").innerHTML="<blink><font size=2 color='red'>Processing<img src='../../../../../images/circle.gif'></font>";
	
	}
	else
	{
		document.getElementById("msg").innerHTML="";
	}
	setTimeout("pr_status()",1);
}
 

