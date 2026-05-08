 var seq =1;

 function getReq() {
	    var req = false;
	    try {
	         req = new ActiveXObject("Msxml2.XMLHTTP");
	    }
	    catch(Ex) {
	         try {
	             req = new ActiveXObject("Microsoft.XMLHTTP");
	         }
	         catch(ex1) {
	             req = false;
	         }
	    }
	    if(!req && typeof XMLHttpRequest != 'undefined') {
	            req = new XMLHttpRequest();
	    }
	    return req;
	}
 function dtval(d,e) {
	 var pK = e ? e.which : window.event.keyCode;
	 if (pK == 8) {d.value = substr(0,d.value.length-1); return;}
	 var dt = d.value;
	 var da = dt.split('/');
	 for (var a = 0; a < da.length; a++) {if (da[a] != +da[a]) da[a] = da[a].substr(0,da[a].length-1);}
	 if (da[1] > 12) {da[1] = da[0].substr(da[0].length-1,1);da[0] = '0'+da[0].substr(0,da[0].length-1);}
	 if (da[0] > 31) {da[2] = da[1].substr(da[1].length-1,1);da[1] = '0'+da[1].substr(0,da[1].length-1);}
	 if (da[2] > 9999) da[1] = da[2].substr(0,da[2].length-1);
	 dt = da.join('/');
	 if (dt.length == 2 || dt.length == 5) dt += '/';
	 d.value = dt;
	 }

 function CheckChangedNominee(ppoNo)
 {
	 var url='CheckChangedNominee.html?ppoNo='+ppoNo+"&rnd="+new Date().getTime();
	 
		var HttpPartialRequest = getReq();
		
		HttpPartialRequest.open("GET", url, true);
		HttpPartialRequest.onreadystatechange=function()
	     {    
			if(HttpPartialRequest.readyState == 4)
			{
				
				var out=HttpPartialRequest.responseText;
				var s=new String(out);
				
				
				if(s=="false")
					{
					loadMstNominee(ppoNo);
					}
				else
					{
					alert("You can't change this PPONO.This has been already changed and waiting for validation!");
					DeleteAll();
					}
			}
		}
		HttpPartialRequest.send(null);
 }
	function AddNominee()
	{    
		var ppoNo=document.getElementById("ppoNo");
		if(ppoNo.value.trim()!="")
			{
		alert("Adding Another Row");
		var tbody = document.getElementById('tblList');
		var tr = document.createElement('tr');
		tr.id=seq;
		
		var td = document.createElement('td');
		
		var rln = document.createElement('input');
		rln.type = 'text';
		rln.size=4;
		rln.name = 'nomineeInitial';
		rln.id = 'nomineeInitial'+seq;
		td.appendChild(rln);
		tr.appendChild(td);

		var td = document.createElement('td');
		
		var rln = document.createElement('input');
		rln.type = 'text';
		rln.name = 'familyMembers';
		rln.id = 'familyMembers'+seq;
		td.appendChild(rln);
		tr.appendChild(td);
		
		var td1 = document.createElement('td');
		var cmb = document.createElement('select');
		cmb.name='relation';
		cmb.id='relation'+seq;
		
		
		var opt3= document.createElement('option');
		opt3.value = '1';
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
		
		/*var opt5 = document.createElement('option');
		opt5.value = '4';
		opt5.innerHTML = 'Wife';
		cmb.appendChild(opt5);*/
		
		var opt1 = document.createElement('option');
		opt1.value = '4';
		opt1.innerHTML = 'Son';
		cmb.appendChild(opt1);

		var opt6 = document.createElement('option');
		opt6.value = '5';
		opt6.innerHTML = 'Daughter';
		cmb.appendChild(opt6);

		
        
        cmb.onchange=function(){
        	//alert(this.value);
        	var str=this.id;
        	var x=str.split('relation');
        	
        	var obj=document.getElementById("maritalStatus"+x[1]);
        	if(parseInt(this.value) >= 1 && parseInt(this.value) <=3)
        		{
        		        		obj.options[1].selected="selected";
        		        		//obj.disabled=true;
        		}
        	else
        		{
        					//obj.disabled=false;
        		}
        	
        };
        
        //anc.href = url;
        //anc.appendChild(cmb);
        //td1.appendChild(anc);
		
		
		td1.appendChild(cmb);
		tr.appendChild(td1);
		
		
		
		//////////////////////////////////////////////////////////
		var td2 = document.createElement('td');
		var dob = document.createElement('input');
		dob.type = 'text';
		dob.name = 'dobStr';
		
		
		dob.onkeyup=function(){
			
			dtval(this,"event");
		}
		dob.maxLength=10;
		dob.size=10;
		dob.id = 'dobStr'+seq;
		
		var image1 = document.createElement('img');
		image1.src="../images/calendr3.gif";
		image1.id="cal-button-1";
		image1.align="right";
		var anc=document.createElement("A");
        var url="";              
        anc.href = url;
       // anc.appendChild(image1);
        
        var hidN=document.createElement('input');
        hidN.type='hidden';
        hidN.name='nomineeID';
        hidN.id='nomineeID';
        hidN.value=-(ppoNo.value+""+seq);
        alert(hidN.value);
		
        td2.appendChild(hidN);
        
        td2.appendChild(dob);
        td2.appendChild(anc);
		
		tr.appendChild(td2);
		//////////////////////////////////////////////////////////////
		var td3 = document.createElement('td');
		var age = document.createElement('input');
		age.type = 'text';
		age.name = 'age';
		age.size=3;
		age.id = 'age'+seq;
		td3.appendChild(age);
		tr.appendChild(td3);
		
		var td4 = document.createElement('td');
		var cmb1 = document.createElement('Select');
		cmb1.name="maritalStatus";
		cmb1.id="maritalStatus"+seq;
		//cmb1.disabled=true;
		
		var opt3 = document.createElement('option');
		opt3.value = 'NS';
		opt3.innerHTML = '---Select---';
		opt3.selected="selected";
		//opt2.disabled=true;
		cmb1.appendChild(opt3);
		
		var opt2 = document.createElement('option');
		opt2.value = 'Married';
		opt2.innerHTML = 'Married';
		opt2.selected="selected";
		//opt2.disabled=true;
		cmb1.appendChild(opt2);
		
		var opt1 = document.createElement('option');
		opt1.value = 'Unmarried';
		opt1.innerHTML = 'Unmarried';
		cmb1.appendChild(opt1);

		
		
		/*var opt3= document.createElement('option');
		opt3.value = '3';
		opt3.innerHTML ='Divorced';
		cmb1.appendChild(opt3);*/
		
		td4.appendChild(cmb1);
		tr.appendChild(td4);
		
		
		
		var td5 = document.createElement('td');
		var cmb2 = document.createElement('select');
		cmb2.name="handicapped";
		cmb2.id="handicapped"+seq;
		
		var opt1 = document.createElement('option');
		opt1.value = 'y';
		opt1.innerHTML = 'Yes';
		cmb2.appendChild(opt1);
		
		var opt2= document.createElement('option');
		opt2.value = 'n';
		opt2.innerHTML ='No';
		opt2.selected='selected';
		cmb2.appendChild(opt2);
		td5.appendChild(cmb2);
		tr.appendChild(td5);
		
		var td6 = document.createElement('td');
		var nominationDate = document.createElement('input');
		nominationDate.type = 'text';
		nominationDate.name = 'nominationDateStr';
		
		nominationDate.maxLength=10;
		nominationDate.onkeyup=function(){
			
			dtval(this,"event");
		}
		nominationDate.size=10;
		nominationDate.id = 'nominationDateStr'+seq;
		
		td6.appendChild(nominationDate);
		tr.appendChild(td6);
		
		
		var td7 = document.createElement('td');
		var deletedReason = document.createElement('input');
		deletedReason.type = 'text';
		deletedReason.name = 'deletedReason';
		deletedReason.readOnly=true;
		
		deletedReason.id='deletedReason'+seq;
		td7.appendChild(deletedReason);
		
		
		var image2 = document.createElement('img');
		image2.src="../images/delete.png";
		image2.align="center";
		image2.border="0";
		
		//var td8=document.createElement("td");
        var anc=document.createElement("A");
        var url="javascript:Delete('" +seq+ "')";              
        anc.href = url;
        anc.appendChild(image2);
        td7.appendChild(anc);
        tr.appendChild(td7);
        //td8.appendChild(anc);
        //tr.appendChild(td8);
		
		
		
		tbody.appendChild(tr);

		seq++;
			}
		else
		{
		alert("You can't Add nominee without fetching the Pensioner!");
		
		}
	
}
	function loadNomineefrmXML(fMbr,NiTl,rltn,doB,Nid,agE,Hcpd,MrSts,nDate)
	{
		var tbody = document.getElementById('tblList');
		var tr = document.createElement('tr');
		tr.id=seq;
		
		var td = document.createElement('td');
		
		var rln = document.createElement('input');
		rln.type = 'text';
		rln.value=NiTl;
		rln.size=4;
		rln.name = 'nomineeInitial';
		rln.id = 'nomineeInitial'+seq;
		td.appendChild(rln);
		tr.appendChild(td);
		
		var td = document.createElement('td');
		
		var rln = document.createElement('input');
		rln.type = 'text';
		rln.value=fMbr;
		rln.name = 'familyMembers';
		rln.id='familyMembers'+seq;
		td.appendChild(rln);
		tr.appendChild(td);
		
		var td1 = document.createElement('td');
		var cmb = document.createElement('select');
		cmb.name='relation';
		cmb.id='relation'+seq;
		
		
		var opt3= document.createElement('option');
		opt3.value = '1';
		opt3.innerHTML ='Father';
		if(parseInt(rltn)==1)
			{
			opt3.selected="selected";
			}
		
		cmb.appendChild(opt3);
		
		var opt2 = document.createElement('option');
		opt2.value = '2';
		opt2.innerHTML = 'Mother';
		if(parseInt(rltn)==2)
		{
		opt2.selected="selected";
		}
		cmb.appendChild(opt2);
		
		var opt4 = document.createElement('option');
		opt4.value = '3';
		opt4.innerHTML = 'Spouse';
		if(parseInt(rltn)==3)
		{
		opt4.selected="selected";
		}
		cmb.appendChild(opt4);
		
		/*var opt5 = document.createElement('option');
		opt5.value = '4';
		opt5.innerHTML = 'Wife';
		if(parseInt(rltn)==4)
		{
		opt5.selected="selected";
		}
		cmb.appendChild(opt5);*/
		
		var opt1 = document.createElement('option');
		opt1.value = '4';
		opt1.innerHTML = 'Son';
		if(parseInt(rltn)==4)
		{
		opt1.selected="selected";
		}
		cmb.appendChild(opt1);

		var opt6 = document.createElement('option');
		opt6.value = '5';
		opt6.innerHTML = 'Daughter';
		if(parseInt(rltn)==5)
		{
		opt6.selected="selected";
		}
		cmb.appendChild(opt6);
		
		 cmb.onchange=function(){
	        	//alert(this.value);
	        	var str=this.id;
	        	var x=str.split('relation');
	        	
	        	var obj=document.getElementById("maritalStatus"+x[1]);
	        	if(parseInt(this.value) >= 1 && parseInt(this.value) <=3)
	        		{
	        		        		obj.options[1].selected="selected";
	        		        		//obj.disabled=true;
	        		}
	        	
	        	
	        };
		
		
		
		td1.appendChild(cmb);
		tr.appendChild(td1);
		
		
		
		//////////////////////////////////////////////////////////
		var td2 = document.createElement('td');
		var dob = document.createElement('input');
		dob.type = 'text';
		dob.name = 'dobStr';
		dob.value=doB;
		dob.maxLength=10;
		dob.onkeyup=function(){
			
			dtval(this,"event");
		}
		dob.size=10;
		dob.id = 'dobStr'+seq;
		
		var image1 = document.createElement('img');
		image1.src="../images/calendr3.gif";
		image1.id="cal-button-1";
		image1.align="right";
		image1.border="0";
		var anc=document.createElement("A");
        var url="";              
        anc.href = url;
       // anc.appendChild(image1);
        td2.appendChild(dob);
        td2.appendChild(anc);
        
        var hidN=document.createElement('input');
        hidN.type='hidden';
        hidN.name='nomineeID';
        hidN.id='nomineeID';
        hidN.value=Nid;
		
        td2.appendChild(hidN);
        
		tr.appendChild(td2);
		//////////////////////////////////////////////////////////////
		var td3 = document.createElement('td');
		var age = document.createElement('input');
		age.type = 'text';
		age.name = 'age';
		age.value=agE;
		age.size=3;
		age.id = 'age'+seq;
		td3.appendChild(age);
		tr.appendChild(td3);
		
		var td4 = document.createElement('td');
		var cmb1 = document.createElement('Select');
		cmb1.name="maritalStatus";
		cmb1.id="maritalStatus"+seq;
		
		var opt3 = document.createElement('option');
		opt3.value = 'NS';
		opt3.innerHTML = '---Select---';
		if(MrSts=='NS')
		{
			
			opt3.selected="selected";
		//cmb1.disabled=true;
		}
		
		//opt2.disabled=true;
		cmb1.appendChild(opt3);
		
		
		var opt2 = document.createElement('option');
		opt2.value = 'Married';
		opt2.innerHTML = 'Married';
		if(MrSts=='Married')
		{
			
		opt2.selected="selected";
		//cmb1.disabled=true;
		}
		cmb1.appendChild(opt2);
		
		var opt1 = document.createElement('option');
		opt1.value = 'Unmarried';
		opt1.innerHTML = 'Unmarried';
		if(MrSts=='Unmarried')
		{
		opt1.selected="selected";
		}
		cmb1.appendChild(opt1);

		
		
		/*var opt3= document.createElement('option');
		opt3.value = '3';
		opt3.innerHTML ='Divorced';
		if(parseInt(MrSts)==3)
		{
		opt3.selected="selected";
		}
		cmb1.appendChild(opt3);*/
		td4.appendChild(cmb1);
		tr.appendChild(td4);
		
		
		var td5 = document.createElement('td');
		var cmb2 = document.createElement('select');
		cmb2.name="handicapped";
		cmb2.id="handicapped"+seq;
		
		var opt1 = document.createElement('option');
		opt1.value = 'y';
		opt1.innerHTML = 'Yes';
		if(Hcpd=='y')
		{
		opt1.selected="selected";
		}
		cmb2.appendChild(opt1);
		
		var opt2= document.createElement('option');
		opt2.value = 'n';
		opt2.innerHTML ='No';
		if(Hcpd!='y')
		{
		opt2.selected="selected";
		}
		
		cmb2.appendChild(opt2);
		td5.appendChild(cmb2);
		tr.appendChild(td5);
		
		var td6 = document.createElement('td');
		var nominationDate = document.createElement('input');
		nominationDate.type = 'text';
		nominationDate.name = 'nominationDateStr';
		nominationDate.value=nDate;
		nominationDate.maxLength=10;
		nominationDate.onkeyup=function(){
			
			dtval(this,"event");
		}
		nominationDate.size=10;
		nominationDate.id = 'nominationDateStr'+seq;
		
		td6.appendChild(nominationDate);
		tr.appendChild(td6);
		
		
		var td7 = document.createElement('td');
		var deletedReason = document.createElement('input');
		deletedReason.type = 'text';
		deletedReason.name = 'deletedReason';
		deletedReason.readOnly=true;
		
		deletedReason.id='deletedReason'+seq;
		deletedReason.size=20;
		//deletedReason.readonly=true;
		td7.appendChild(deletedReason);
		
		//Nomination date
		
		var image2 = document.createElement('img');
		image2.src="../images/delete.png";
		image2.border="0";
		//image2.align="center";
		 var anc=document.createElement("A");
	        var url="javascript:Delete('" +seq+ "')";              
	        anc.href = url;
	        anc.appendChild(image2);
	        td7.appendChild(anc);
	        tr.appendChild(td7);    
		/*var td8=document.createElement("td");
        var anc=document.createElement("A");
        var url="javascript:Delete('" +seq+ "')";              
        anc.href = url;
        anc.appendChild(image2);
        td8.appendChild(anc);
        tr.appendChild(td8);*/
		
		
		
		tbody.appendChild(tr);

		seq++;
		
	}
	function ChangeRelation(MyseQ)
	{
		alert(MyseQ);
	}
	
	function loadChangedNomineefrmXML(fMbr,NiTl,rltn,doB,Nid,agE,Hcpd,MrSts,Ndate,DreasoN)
	{
		var tbody = document.getElementById('tblList');
		var tr = document.createElement('tr');
		tr.id=seq;
		
		var td = document.createElement('td');
		
		var rln = document.createElement('input');
		rln.type = 'text';
		rln.value=NiTl;
		rln.size=4;
		rln.name = 'nomineeInitial';
		rln.id = 'nomineeInitial'+seq;
		td.appendChild(rln);
		tr.appendChild(td);
		
		var td = document.createElement('td');
		
		var rln = document.createElement('input');
		rln.type = 'text';
		rln.value=fMbr;
		rln.name = 'familyMembers';
		rln.id='familyMembers'+seq;
		td.appendChild(rln);
		tr.appendChild(td);
		
		var td1 = document.createElement('td');
		var cmb = document.createElement('select');
		cmb.name='relation';
		cmb.id='relation'+seq;
		
		
		var opt3= document.createElement('option');
		opt3.value = '1';
		opt3.innerHTML ='Father';
		if(parseInt(rltn)==1)
			{
			opt3.selected="selected";
			}
		
		cmb.appendChild(opt3);
		
		var opt2 = document.createElement('option');
		opt2.value = '2';
		opt2.innerHTML = 'Mother';
		if(parseInt(rltn)==2)
		{
		opt2.selected="selected";
		}
		cmb.appendChild(opt2);
		
		var opt4 = document.createElement('option');
		opt4.value = '3';
		opt4.innerHTML = 'Spouse';
		if(parseInt(rltn)==3)
		{
		opt4.selected="selected";
		}
		cmb.appendChild(opt4);
		
		/*var opt5 = document.createElement('option');
		opt5.value = '4';
		opt5.innerHTML = 'Wife';
		if(parseInt(rltn)==4)
		{
		opt5.selected="selected";
		}
		cmb.appendChild(opt5);*/
		
		var opt1 = document.createElement('option');
		opt1.value = '4';
		opt1.innerHTML = 'Son';
		if(parseInt(rltn)==4)
		{
		opt1.selected="selected";
		}
		cmb.appendChild(opt1);

		var opt6 = document.createElement('option');
		opt6.value = '5';
		opt6.innerHTML = 'Daughter';
		if(parseInt(rltn)==5)
		{
		opt6.selected="selected";
		}
		cmb.appendChild(opt6);
		
		 cmb.onchange=function(){
	        	//alert(this.value);
	        	var str=this.id;
	        	var x=str.split('relation');
	        	
	        	var obj=document.getElementById("maritalStatus"+x[1]);
	        	if(parseInt(this.value) >= 1 && parseInt(this.value) <=3)
	        		{
	        		        		obj.options[1].selected="selected";
	        		        		//obj.disabled=true;
	        		}
	        	else
	        		{
	        					//obj.disabled=false;
	        		}
	        	
	        };
		
		
		
		td1.appendChild(cmb);
		tr.appendChild(td1);
		
		
		
		//////////////////////////////////////////////////////////
		var td2 = document.createElement('td');
		var dob = document.createElement('input');
		dob.type = 'text';
		dob.name = 'dobStr';
		dob.value=doB;
		dob.maxLength=10;
		dob.onkeyup=function(){
			
			dtval(this,"event");
		}
		dob.size=10;
		dob.id = 'dobStr'+seq;
		
		var image1 = document.createElement('img');
		image1.src="../images/calendr3.gif";
		image1.id="cal-button-1";
		image1.align="right";
		var anc=document.createElement("A");
        var url="";              
        anc.href = url;
       // anc.appendChild(image1);
        td2.appendChild(dob);
        td2.appendChild(anc);
        
        var hidN=document.createElement('input');
        hidN.type='hidden';
        hidN.name='nomineeID';
        hidN.id='nomineeID';
        hidN.value=Nid;
		
        td2.appendChild(hidN);
        
		tr.appendChild(td2);
		//////////////////////////////////////////////////////////////
		var td3 = document.createElement('td');
		var age = document.createElement('input');
		age.type = 'text';
		age.name = 'age';
		age.value=agE;
		age.size=3;
		age.id = 'age'+seq;
		td3.appendChild(age);
		tr.appendChild(td3);
		
		var td4 = document.createElement('td');
		var cmb1 = document.createElement('Select');
		cmb1.name="maritalStatus";
		cmb1.id="maritalStatus"+seq;
		
		var opt3 = document.createElement('option');
		opt3.value = 'NS';
		opt3.innerHTML = '---Select---';
		if(MrSts=='NS')
		{
			
			opt3.selected="selected";
		//cmb1.disabled=true;
		}
		cmb1.appendChild(opt3);
		
		var opt2 = document.createElement('option');
		opt2.value = 'Married';
		opt2.innerHTML = 'Married';
		if(MrSts=='Married')
		{
		opt2.selected="selected";
		//cmb1.disabled=true;
		}
		cmb1.appendChild(opt2);
		
		var opt1 = document.createElement('option');
		opt1.value = 'Unmarried';
		opt1.innerHTML = 'Unmarried';
		if(MrSts=='Unmarried')
		{
		opt1.selected="selected";
		}
		cmb1.appendChild(opt1);

		
		
		/*var opt3= document.createElement('option');
		opt3.value = '3';
		opt3.innerHTML ='Divorced';
		if(parseInt(MrSts)==3)
		{
		opt3.selected="selected";
		}
		cmb1.appendChild(opt3);*/
		td4.appendChild(cmb1);
		tr.appendChild(td4);
		
		
		var td5 = document.createElement('td');
		var cmb2 = document.createElement('select');
		cmb2.name="handicapped";
		cmb2.id="handicapped"+seq;
		
		var opt1 = document.createElement('option');
		opt1.value = 'y';
		opt1.innerHTML = 'Yes';
		if(Hcpd=='y')
		{
		opt1.selected="selected";
		}
		cmb2.appendChild(opt1);
		
		var opt2= document.createElement('option');
		opt2.value = 'n';
		opt2.innerHTML ='No';
		if(Hcpd!='y')
		{
		opt2.selected="selected";
		}
		
		cmb2.appendChild(opt2);
		td5.appendChild(cmb2);
		tr.appendChild(td5);
		
		var td6 = document.createElement('td');
		var nominationDate = document.createElement('input');
		nominationDate.type = 'text';
		nominationDate.name = 'nominationDateStr';
		nominationDate.value=Ndate;
		nominationDate.maxLength=10;
		nominationDate.onkeyup=function(){
			
			dtval(this,"event");
		}
		nominationDate.size=10;
		nominationDate.id = 'nominationDateStr'+seq;
		
		td6.appendChild(nominationDate);
		tr.appendChild(td6);
		
		
		var td7 = document.createElement('td');
		var deletedReason = document.createElement('input');
		deletedReason.type = 'text';
		deletedReason.name = 'deletedReason';
		deletedReason.readOnly=true;
		deletedReason.value=DreasoN;
		deletedReason.id='deletedReason'+seq;
		deletedReason.size=20;
		//deletedReason.readonly=true;
		td7.appendChild(deletedReason);
		
		//Nomination date
		
		var image2 = document.createElement('img');
		image2.src="../images/delete.png";
		image2.border="0";
		//image2.align="center";
		 var anc=document.createElement("A");
	        var url="javascript:Delete('" +seq+ "')";              
	        anc.href = url;
	        anc.appendChild(image2);
	        td7.appendChild(anc);
	        tr.appendChild(td7);    
		/*var td8=document.createElement("td");
        var anc=document.createElement("A");
        var url="javascript:Delete('" +seq+ "')";              
        anc.href = url;
        anc.appendChild(image2);
        td8.appendChild(anc);
        tr.appendChild(td8);*/
		
		
		
		tbody.appendChild(tr);

		seq++;
	}
	
	function DeleteAll()
	{
		 var tbody=document.getElementById("Existing");
		for(var i=1;i<seq;i++)
			{
				try
				{
					var r=document.getElementById(i);
					var ri=r.rowIndex;
					tbody.deleteRow(ri);
				
				}
				catch(Ex)
				{
					
				}
					}
		seq=1;
	}
	
	
	function Delete(seq)
	{
	  //alert("Deleting row Selected");
		
		if(confirm("Are You Sure to Delete?"))
			{
			var txt=prompt("What is the reason for delete?","");
			document.getElementById("deletedReason"+seq).value=txt;
			/*var tbody=document.getElementById("Existing");
			  var r=document.getElementById(seq);
			  var ri=r.rowIndex;
			  tbody.deleteRow(ri);*/
			}
	  	
	
	}
	
	function loadMstNominee(ppoNo)
	{
		var url='LoadMstNominee.html?ppoNo='+ppoNo+"&rnd="+new Date().getTime();

		var HttpPartialRequest = getReq();
		HttpPartialRequest.open("GET", url, true);
		HttpPartialRequest.onreadystatechange=function()
	     {    
			if(HttpPartialRequest.readyState == 4)
			{
				
				DeleteAll();
				var response=HttpPartialRequest.responseXML.getElementsByTagName("response")[0];
				
				//Clearing the Existing data
				
				var service=response.getElementsByTagName("record");
				if(service.length==0)
				{
				alert("Record Not Found!");
				}
				
				for(var i=0;i<service.length;i++)
					{
					
					var fMbr=service[i].getElementsByTagName("familymembers")[0].firstChild.nodeValue;
					var NiTl=service[i].getElementsByTagName("nomineeinitial")[0].firstChild.nodeValue;
					var rltn=service[i].getElementsByTagName("relation")[0].firstChild.nodeValue;
					var TmpDob=service[i].getElementsByTagName("dob")[0].firstChild.nodeValue;
					
					var dt;
					var doB;
					if(TmpDob!=null && TmpDob!="null")
					{
					dt=TmpDob.split("-");
					 doB=dt[2]+"/"+dt[1]+"/"+dt[0];
				}
			else
				{
					doB="";
				}
					
					//var doB=dateFormat(TmpDob, "dddd, mmmm dS, yyyy, h:MM:ss TT");
					
					var Nid=service[i].getElementsByTagName("nomineeid")[0].firstChild.nodeValue;
					var agE=service[i].getElementsByTagName("age")[0].firstChild.nodeValue;
					var Hcpd=service[i].getElementsByTagName("handicapped")[0].firstChild.nodeValue;
					var MrSts=service[i].getElementsByTagName("maritalstatus")[0].firstChild.nodeValue;
					TmpDob=service[i].getElementsByTagName("nominationdate")[0].firstChild.nodeValue;
					dt=TmpDob.split("-");
					var Ndate=dt[2]+"/"+dt[1]+"/"+dt[0];
					
					//alert(fMbr+":"+rltn+":"+doB+":"+Nid+":"+agE+":"+Hcpd+":"+MrSts);
					if(agE=="null" || agE=="0")
						{
						agE="";
						}
					if(NiTl=="null" )
					{
						NiTl="";
					}
					loadNomineefrmXML(fMbr,NiTl,rltn,doB,Nid,agE,Hcpd,MrSts,Ndate);
				
					}
				
				
			}
	    	 //getList(HttpPartialRequest);
	     };  
	     HttpPartialRequest.send(null);
	}

	
	function loadChangedNominee(ppoNo)
	{
		var url='LoadChangedNominee.html?ppoNo='+ppoNo+"&rnd="+new Date().getTime();

		var HttpPartialRequest = getReq();
		HttpPartialRequest.open("GET", url, true);
		HttpPartialRequest.onreadystatechange=function()
	     {    
			if(HttpPartialRequest.readyState == 4)
			{
				
				DeleteAll();
				var response=HttpPartialRequest.responseXML.getElementsByTagName("response")[0];
				
				//Clearing the Existing data
				
				var service=response.getElementsByTagName("record");
				if(service.length==0)
					{
					alert("Record Not Found!");
					}
				for(var i=0;i<service.length;i++)
					{
					
					var fMbr=service[i].getElementsByTagName("familymembers")[0].firstChild.nodeValue;
					var NiTl=service[i].getElementsByTagName("nomineeinitial")[0].firstChild.nodeValue;
					var rltn=service[i].getElementsByTagName("relation")[0].firstChild.nodeValue;
					var TmpDob=service[i].getElementsByTagName("dob")[0].firstChild.nodeValue;
					/*var dt=new Date();
					dt.setDate(TmpDob);
					alert(dt);*/
					var dt;
					var doB;
					
					if(TmpDob!=null && TmpDob!="null")
						{
						dt=TmpDob.split("-");
						 doB=dt[2]+"/"+dt[1]+"/"+dt[0];
					}
				else
					{
						doB="";
					}
					 
					//var doB=dateFormat(TmpDob, "dddd, mmmm dS, yyyy, h:MM:ss TT");
					
					var Nid=service[i].getElementsByTagName("nomineeid")[0].firstChild.nodeValue;
					var agE=service[i].getElementsByTagName("age")[0].firstChild.nodeValue;
					var Hcpd=service[i].getElementsByTagName("handicapped")[0].firstChild.nodeValue;
					var MrSts=service[i].getElementsByTagName("maritalstatus")[0].firstChild.nodeValue;
					TmpDob=service[i].getElementsByTagName("nominationdate")[0].firstChild.nodeValue;
					dt=TmpDob.split("-");
					var Ndate=dt[2]+"/"+dt[1]+"/"+dt[0];
					
					var dReason=service[i].getElementsByTagName("deletedreason")[0].firstChild.nodeValue;
					//alert(fMbr+":"+rltn+":"+doB+":"+Nid+":"+agE+":"+Hcpd+":"+MrSts);
					dReason=dReason!="null"?dReason:"";
					
					agE=agE!="null"?agE:"";
					if(NiTl=="null" )
					{
						NiTl="";
					}
					loadChangedNomineefrmXML(fMbr,NiTl,rltn,doB,Nid,agE,Hcpd,MrSts,Ndate,dReason);
				
					}
				
				
			}
	    	 
	     };  
	     HttpPartialRequest.send(null);
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
		var ppoNo=document.getElementById("ppoNo");
		if(ppoNo.value.trim()!="")
			{
			if(seq > 1)
			{
			var row=1;
				for(var i=1;i<seq;i++)
					{
					//Checking name is not null
					var familyMembers=document.getElementById("familyMembers"+i);
					var nomineeInitial=document.getElementById("nomineeInitial"+i);
					var relation=document.getElementById("relation"+i);
					var dobStr=document.getElementById("dobStr"+i);
					var nominationDate=document.getElementById("nominationDateStr"+i);
					var age=document.getElementById("age"+i);
					var maritalStatus=document.getElementById("maritalStatus"+i);
					var handicapped=document.getElementById("handicapped"+i);
					var colorBug="#F78181";
					var colorOk="#FFFFFF";
					var relFlag=false;
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
								relFlag=true;
								
								}
							
								
							
							if(nominationDate.value.trim().length>0)
								{
						datesSep=nominationDate.value.split("/");
						if(!isValidDate(datesSep[0],datesSep[1],datesSep[2]))
						{
							nominationDate.style.background=colorBug;
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
						nominationDate.style.background=colorOk;
						}
							}
						
					else
							{
								nominationDate.style.background=colorBug;
								str+="Row-"+row+"."+" Nomination Date should not be Empty!\n";
								flag=false;
								
							}
						if(!(age.value >= 0))
							{
							age.style.background=colorBug;
							str+="Row-"+row+"."+" Age is not valid!\n";
							flag=false;
							}
						else if(relFlag && age.value.trim().length==0) //This checking for 'Dob Mandatory if relation is son or daughter'
							{
							age.style.background=colorBug;
							dobStr.style.background=colorBug;
								str+="Row-"+row+"."+" Age or Date of birth should be filled  for this relation!\n";
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
			}
		else
			{
			alert("You can't submit empty form!");
			flag=false;
			}
		}
		else
			{
			alert("You can't submit without fetching the Pensioner!");
			flag=false;
			}
		
		return flag;
	}
	