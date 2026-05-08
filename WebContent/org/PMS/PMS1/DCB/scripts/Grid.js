


        /*********************************************************************
         * 				CREATE CELL
         *********************************************************************/
        function makeCell(tr,val,vis)
        {
             cell = document.createElement("TD"); 
             cell.innerHTML = val;  
             if(vis==0)
             {
                cell.style.display = 'none';
             }
             tr.appendChild(cell);       
        }
        /*********************************************************************/
        
        
        
        
        
    	
        /*********************************************************************
         * 				GET - GRID LOAD
         *********************************************************************/
        function loadGrids()
        {
    	    var tbody = document.getElementById('tbody');
            
            var row = baseResponse.getElementsByTagName('row');
            var len = row.length;

            paginate(baseResponse);

	        for(var i=0; i<len; i++)
	        {
	        		var col = row[i].getElementsByTagName('col');
	        		var ln = col.length;

        			var tr = document.createElement('tr');
        			var rid = col[0].firstChild.nodeValue;
        			tr.id = rid;

	                var td = document.createElement('td');
	                var anc = document.createElement('a');
	                anc.href = 'javascript:edit(' + rid + ')';
	                anc.innerHTML = 'Edit';
	                td.rowspan = 4;
	                td.appendChild(anc);
	                tr.appendChild(td);

        			for(var j=1; j<=ln; j++ )
	        		{
	        			var val = col[i].firstChild.nodeValue;
	        			makeCell(tr,val,1);
	        		}	
	                
	                tbody.appendChild(tr);
	        }
        }
        /*********************************************************************/