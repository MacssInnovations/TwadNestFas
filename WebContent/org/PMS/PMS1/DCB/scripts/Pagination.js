		

	
		
		function clearAll()
		{
			var cmbpage = document.getElementById('cmbpage').value;
			var cmbpagination = document.getElementById('cmbpagination').value;
			
			document.forms[0].reset();
			document.getElementById('Add').style.display = 'inline';
			document.getElementById('Update').style.display = 'none';
			document.getElementById('Delete').style.display = 'none';
			
			document.getElementById('cmbpage').value = cmbpage;
			document.getElementById('cmbpagination').value = cmbpagination;
		}
		
	
		function unloadCombo(eleName)
		{
			var cmb = document.getElementById(eleName);
			var len = cmb.length; 
			for(var i=len; i>1; i--)
			{
				cmb.removeChild(cmb.lastChild);
			}
		}

		
		function unloadChildren(eleName)
		{
			var prnt = document.getElementById(eleName);
			var len = prnt.childNodes.length;
			for(var i=len; i>0; i--)
			{
				prnt.removeChild(prnt.lastChild);
			}
		}
	
		
		   
		   function prev()
		   {
			   var page = document.getElementById('cmbpage');
			   
			   if(new Number(page.value>1))
			   {
				   (page.value)--;
			   }
			   callServer(CONS);
		   }
		

		   
		   function next()
		   {
			   var page = document.getElementById('cmbpage');
			   var totpg = new Number(document.getElementById('divpage').firstChild.nodeValue);
			   if(new Number(page.value<totpg))
			   {
				   (page.value)++;
			   }
			   callServer(CONS);
		   }
		   
		   
		   
		   function hidePag()
		   {
			   document.getElementById('divnext').style.display="none";
			   document.getElementById('divpre').style.display="none";
			   document.getElementById('divpage').style.display="none";
			   document.getElementById('divcmbpage').style.display="none";
			   
			   document.getElementById('nodata').style.display="inline";     // Display 'No Data Found' msg
		   }
		   
		   
		function search()
		{
			document.getElementById('cmbpage').value=1;
			callServer(CONS);
		}
		

		function paginate(baseResponse)
		{
            unloadChildren('tbody');
            unloadCombo('cmbpage');
            
            var page = new Number(baseResponse.getElementsByTagName('page')[0].firstChild.nodeValue);
            var totpg = new Number(baseResponse.getElementsByTagName('total')[0].firstChild.nodeValue);
            

            
            
            /****** Load 'Page No.' Combo & 'Total Pages' **********/ 
            
            document.getElementById('divpage').innerHTML = totpg;
            var cmbpage = document.getElementById('cmbpage');
            
            for(var i=2; i<=totpg; i++)
            {
            	var opt = document.createElement('option');
            	opt.value = i;
            	opt.innerHTML = i;
            	cmbpage.appendChild(opt);
            }
            cmbpage.value = page; 
            
            /*******************************************************/
            
            
            
            
            
            /************* 'Next' & 'Previous' links **************/
            
            if(page<totpg)
            {
            	document.getElementById('divnext').style.display = 'inline';
            }
            else
            {
            	document.getElementById('divnext').style.display = 'none';
            }
            
            
            if(page>1)
            {
            	document.getElementById('divpre').style.display = 'inline';
            }
            else
            {
            	document.getElementById('divpre').style.display = 'none';
            }
            /*******************************************************/
            
            
            
            document.getElementById('nodata').style.display="none";     // Hide 'No Data Found' msg
            
            if(totpg==0)
            {
            	hidePag();
            }
            

		}