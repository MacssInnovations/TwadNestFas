				
		
			
		
				
		/*****************************************************************
		 * 					REQUEST OBJECT
		 *****************************************************************/
		function createObject()
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
		
		
		
		/*****************************************************************
		 * 					NULL CHECK
		 *****************************************************************/
		function nullCheck(inx)
		{
			var index = document.getElementById(inx).value;
			if((index == null)||(index == '')||(index == ' '))
				return false;
			else
				return true;
		}
		
		
		/*****************************************************************
		 * 					   CLEAR
		 *****************************************************************/
		function clearAll()
		{
			document.forms[0].reset();
			document.getElementById('type').disabled = false;
			document.getElementById('Add').disabled = false;
			document.getElementById('Update').disabled = true;
			document.getElementById('Delete').disabled = true;
		}
		
		
		/*****************************************************************
		 * 					CLEAR COMBO
		 *****************************************************************/
		function unloadCombo(eleName)
		{
			var cmb = document.getElementById(eleName);
			
			//cmb.length = 1;
			var len = cmb.length; 
			for(var i=len; i>1; i--)
			{
				cmb.removeChild(cmb.lastChild);
			}
		}

		
		/*****************************************************************
		 * 					CLEAR CHILDREN / TBODY
		 *****************************************************************/
		function unloadChildren(eleName)
		{
			var prnt = document.getElementById(eleName);
			
			//prnt.childNodes.length = 0;
			var len = prnt.childNodes.length;
			for(var i=len; i>0; i--)
			{
				prnt.removeChild(prnt.lastChild);
			}
		}
	

		