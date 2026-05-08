 	
var seq =1;
 var cmb1,cmb;
 
	function AddNominee()
	{     
		
		var tbody = document.getElementById('tblList');
		tbody.align='center';
		var tr = document.createElement('tr');
		tr.id=seq;

		var td10 = document.createElement('td');		
		var rln10 = document.createElement('input');
		rln10.type = 'text';
		rln10.name = 'nomineeInitial';
		rln10.id = 'nomineeInitial'+seq;
		
		td10.appendChild(rln10);
		tr.appendChild(td10);
		
		var td = document.createElement('td');		
		var rln = document.createElement('input');
		rln.type = 'text';
		rln.name = 'familyMembers';
		rln.id = 'familyMembers'+seq;
		
		td.appendChild(rln);
		tr.appendChild(td);
		
		
		var td1 = document.createElement('td');
		cmb = document.createElement('select');
		cmb.name='relation';
		cmb.id='relation'+seq;
		
	/*	var opt0= document.createElement('option');
		opt0.value ='0';
		opt0.innerHTML ='--Select--';
		cmb.appendChild(opt0);
	*/	
		var opt3= document.createElement('option');
		opt3.value ='1';
		opt3.innerHTML ='Father';
		cmb.appendChild(opt3);
		
		var opt2 = document.createElement('option');
		opt2.value = '2';
		opt2.innerHTML = 'Mother';
		cmb.appendChild(opt2);
		
		var opt4 = document.createElement('option');
		opt4.value = '3';
		opt4.innerHTML = 'Spouse';
		cmb.appendChild(opt4);
		
		var opt1 = document.createElement('option');
		opt1.value = '4';
		opt1.innerHTML = 'Son';
		cmb.appendChild(opt1);

		var opt6 = document.createElement('option');
		opt6.value = '5';
		opt6.innerHTML = 'Daughter';
		cmb.appendChild(opt6);
		
		cmb.onchange=function(){
          
            var str=this.id;
            var x=str.split('relation');
           
            var obj=document.getElementById("martialStatus"+x[1]);
        
            if(parseInt(this.value) >= 1 && parseInt(this.value) <=3)
                {
                    obj.options[0].selected="selected";
                
                }
                      
        };
   
		

		/*cmb.onchange = function() {						
			changeMartitalStatus(this);
		};
	*/
	
		
		td1.appendChild(cmb);
		tr.appendChild(td1);
		
		
		
		//////////////////////////////////////////////////////////
		var td2 = document.createElement('td');
		var dob = document.createElement('input');		
		var event='event';
		
		dob.type = 'text';
		dob.name = 'dob';
		dob.maxLength=10;
		dob.id = 'dob'+seq;
		
		dob.onkeyup = function() {			
			dtval(this,event);
		};
		
		dob.onchange=function(){
			//checkdate(this);
			CheckSubmit();
		}
		
		
		td2.appendChild(dob);
		tr.appendChild(td2);
		
		/* var image1 = document.createElement('img');
		image1.src="../images/calendr3.gif";
		image1.id="cal-button-1";
		image1.border='0';
		image1.align="middle";
		var anc=document.createElement("A");
        var url="";              
        anc.href = url; 
        anc.appendChild(image1);
        td2.appendChild(dob);
        td2.appendChild(anc);
		
		tr.appendChild(td2);*/
		
		
		/*var image1 = document.createElement('img');
		image1.src="../images/calendr3.gif";
		image1.id="cal-button-1";
		image1.border='0';
		image1.align="middle";		
		
		 td2.appendChild(dob);
		td2.appendChild(image1);
       
		image1.onclick = function()
		{
			cal();
	
		}
	*/	
		
		
		
		//////////////////////////////////////////////////////////////
		var td3 = document.createElement('td');
		var age = document.createElement('input');
		age.type = 'text';
		age.name = 'age';
		age.id = 'age'+seq;
		age.maxLength=3;		
		age.onkeyup = function() {
			
			numchk(this);
		};
		td3.appendChild(age);
		tr.appendChild(td3);
		
				
		var td4 = document.createElement('td');
		cmb1 = document.createElement('Select');
		cmb1.name="martialStatus";
		cmb1.id="martialStatus"+seq;
		
		/* var opt00 = document.createElement('option');
		opt00.value = '0';
		opt00.innerHTML = '--Select--';
		cmb1.appendChild(opt00);
		*/
		
		var opt2 = document.createElement('option');
		opt2.value = '1';
		opt2.innerHTML = 'Married';
		cmb1.appendChild(opt2);
		
		var opt1 = document.createElement('option');
		opt1.value = '2';
		opt1.innerHTML = 'Unmarried';
		cmb1.appendChild(opt1);
				
		td4.appendChild(cmb1);
		tr.appendChild(td4);
		
		var td5 = document.createElement('td');
		var cmb2 = document.createElement('select');
		cmb2.name="handicapped";
		cmb2.id="handicapped"+seq;
		
		var opt1 = document.createElement('option');
		opt1.value = 'Y';
		opt1.innerHTML = 'Yes';
		cmb2.appendChild(opt1);
		
		var opt2= document.createElement('option');
		opt2.value = 'N';
		opt2.innerHTML ='No';
		opt2.selected='selected';
		cmb2.appendChild(opt2);
		td5.appendChild(cmb2);
		tr.appendChild(td5);
		
		
		var image2 = document.createElement('img');
		image2.src="../images/delete.png";
		image2.align="center";
		image2.border='0';
		var td6=document.createElement("td");
        var anc=document.createElement("A");
        var url="javascript:Delete('" +seq+ "')";              
        anc.href = url;
        anc.appendChild(image2);
        td6.appendChild(anc);
        tr.appendChild(td6);
		
		
		
		tbody.appendChild(tr);

		seq++;
	
}
	
	
	function Delete(seq){
		 
		if(confirm("Are You Sure Want to Delete?"))
          {       	  
				var tbody=document.getElementById("Existing");
				var r=document.getElementById(seq);
				var ri=r.rowIndex;
				tbody.deleteRow(ri);	
          }
	}
	
	
	function changeMartitalStatus(obj)
	{
	var value=obj.value;
	var ids=obj.id;
	
		alert("id"+ ids+ "value"+value);
	
		if(value==0)
		{
			cmb1.selectedIndex=0;
			//cmb1.disabled=false;
		}
		
		if(value==1)
		{
			cmb1.selectedIndex=2;
			//cmb1.disabled=true;
		}
		
		if(value==2)
		{
			cmb1.selectedIndex=2;
			//cmb1.disabled=true;
		}
		if(value==3)
		{
			cmb1.selectedIndex=2;
			//cmb1.disabled=true;
		}
		
		if(value==4)
		{
			cmb1.selectedIndex=0;
			//cmb1.disabled=false;
			
		}
		if(value==5)
		{
			cmb1.selectedIndex=0;
			//cmb1.disabled=false;
		}
		
		
	}
	
	 
	String.prototype.trim = function() {
	        a = this.replace(/^\s+/, '');
	        return a.replace(/\s+$/, '');
	        };
	       
	        
	function isValidDate(day,month,year)
    {
        month-=1;
        var dteDate;
        dteDate=new Date(year,month,day);
    return ((day==dteDate.getDate()) && (month==dteDate.getMonth()) && (year==dteDate.getFullYear()));
    }
   
    function isFutureDate(day,month,year)
    {
       
        month-=1;
        var dteDate;
        dteDate=new Date(year,month,day);
        return (dteDate.getTime()>new Date().getTime());
                   
    }
   
    function isAlpha(checkString) {
        var tempString="";
        var regExp = /^[A-Z. a-z]$/;
        if(checkString != null && checkString != "")
        {
          for(var i = 0; i < checkString.length; i++)
          {
            if (!checkString.charAt(i).match(regExp))
            {
              return false;
            }
          }
        }
        else
        {
          return false;
        }
        return true;
}
    
    
	
	 function CheckSubmit()
	    {
	        var flag=true;
	        var str="";
	        var dateOfNominee=document.getElementById("dateOfNominee");       
	        
	            var row=1;
	                
	            for(var i=1;i<seq;i++)
	                    {
	                  
	                    var familyMembers=document.getElementById("familyMembers"+i);
	                    var relation=document.getElementById("relation"+i);
	                    var dobStr=document.getElementById("dob"+i);
	                   
	                    var age=document.getElementById("age"+i);
	                    var maritalStatus=document.getElementById("martialStatus"+i);
	                    var handicapped=document.getElementById("handicapped"+i);
	                    var colorBug="#F78181";
	                    var colorOk="#FFFFFF";
	                  
	                    /*if(dateOfNominee.value.trim()!="")
	                    	{
	                    	dateOfNominee.style.background=colorBug;
                            str+="dateOfNominee should not be leave Empty!\n";
                            flag=false;
	                    	}*/
	                    
	                    if(familyMembers!=null)
	                        {
	                       
	                            if(familyMembers.value.trim().length==0)
	                            {
	                            familyMembers.style.background=colorBug;
	                            str+="Row-"+row+"."+" Family Member should not be leave Empty!\n";
	                            flag=false;
	                            }
	                        else if(!isAlpha(familyMembers.value.trim()))
	                            {
	                            familyMembers.style.background=colorBug;
	                            str+="Row-"+row+"."+" Family Member should Contain only Alphas!\n";
	                            flag=false;
	                            }
	                        else
	                            {
	                            familyMembers.style.background=colorOk;
	                            }
	                           
	                        
	                        if(dobStr.value.trim().length>0)
	                           {
	                        	var datesSep=dobStr.value.split("/");
	                       
	                        		if(!isValidDate(datesSep[0],datesSep[1],datesSep[2]))
	                        			{
	                        				dobStr.style.background=colorBug;
	                        				str+="Row-"+row+"."+" Date of birth Invalid!\n";
	                        				flag=false;
	                        			}
	                        
	                        		else if(isFutureDate(datesSep[0],datesSep[1],datesSep[2]))
	                        			{
	                        				dobStr.style.background=colorBug;
	                        				str+="Row-"+row+"."+" Date of birth should not be Future date!\n";
	                        				flag=false;
	                        				//dobStr.style.background=colorOk;
	                        			}
	                        		else
	                        			{
	                        				dobStr.style.background=colorOk;
	                        			}
	                                    
	                            }
	                            
	                        
	                        	else if(parseInt(relation.value) > 3) //This checking for 'Dob Mandatory if relation is son or daughter'
	                                {
	                                    dobStr.style.background=colorBug;
	                                    str+="Row-"+row+"."+" Date of Birth should not be Empty for this relation!\n";
	                                    flag=false;
	                                 
	                                }
	                            
	                        	else
	                                {
	                                dobStr.style.background=colorOk;
	                                }
	                           
	                               
	                           
	                            if(dateOfNominee.value.trim().length>0)
	                                {
	                            		datesSep=dateOfNominee.value.split("/");
	                            			if(!isValidDate(datesSep[0],datesSep[1],datesSep[2]))
	                            				{
	                            				dateOfNominee.style.background=colorBug;
	                            					str+="Row-"+row+"."+" Nomination Date Invalid!\n";
	                            					flag=false;
	                            				}
	                        
	                            			else if(isFutureDate(datesSep[0],datesSep[1],datesSep[2]))
	                            				{
	                            					dobStr.style.background=colorBug;
	                            					str+="Row-"+row+"."+" Nomination Date should not be Future date!\n";
	                            					flag=false;
	                            					//dobStr.style.background=colorOk;
	                            				}
	                            			else
	                            				{
	                            				dateOfNominee.style.background=colorOk;
	                            				}
	                                }
	                       
	                            else
	                            	{
	                            	dateOfNominee.style.background=colorBug;
	                                	str+="Row-"+row+"."+" Nomination Date should not be Empty!\n";
	                                	flag=false;
	                               
	                            	}
	                        
	                            
	                            if(!(age.value >= 0))
	                            	{
	                            		age.style.background=colorBug;
	                            		str+="Row-"+row+"."+" Age is not valid!\n";
	                            		flag=false;
	                            	}
	                        
	                            else if(parseInt(relation.value) < 4 && age.value.trim().length==0) //This checking for 'Dob Mandatory if relation is son or daughter'
	                            	{
	                            		age.style.background=colorBug;
	                                	str+="Row-"+row+"."+" Age should not be Empty for this Relation!\n";
	                                	flag=false;	                               
	                            	}
	                            
	                            else
	                            {
	                                age.style.background=colorOk;
	                            }
	                       
	                       
	                        row++;
	                        }
	                    }
	            
	              if(!flag)
	                    {
	                    alert(str);
	                    }
	          
	        
	        
/*	        else
	            {
	            alert("You can't submit without enter Date Of Nomination.!");
	            flag=false;
	            }
	       
	   */     return flag;
	    }
	
	
	
	
	
