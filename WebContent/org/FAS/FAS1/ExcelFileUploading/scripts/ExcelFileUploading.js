function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function load() {
	var url = "../../../../../File_upload?Command=Upload";
	/*document.fileUploading.action = url;
	document.fileUploading.method = "POST";*/
	
	
	// The Javascript
	/*var formData = new FormData();
	 var fileSelect = document.getElementById("file");
	    if(fileSelect.files && fileSelect.files.length == 1){
	     var file = fileSelect.files[0]
	     formData.set("file", file , file.name);
	    }
	*/
	   
	var req = getTransport();
	//req.open("GET", url, true);
	req.open('POST', url, true);
	//req.send(formData);
	
	/*req.responseType = 'text/xml';
	req.overrideMimeType('text/xml');*/
	req.onreadystatechange = function() {
		handleResponse(req);
	}
	req.send(null);


}

function handleResponse(req)
{
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (flag == "success") {
alert("File Uploaded Successfully");
			}
		}
	}

}

var _validFileExtensions = [".xls"];    
function Validate(oForm) {
    var arrInputs = oForm.getElementsByTagName("input");
    for (var i = 0; i < arrInputs.length; i++) {
        var oInput = arrInputs[i];
        if (oInput.type == "file") {
            var sFileName = oInput.value;
            if (sFileName.length == 0)
            	{
            	alert("Please select Excelfile with .xls extension file to upload");
                return false;
            	}
            if (sFileName.length > 0) {
                var blnValid = false;
                for (var j = 0; j < _validFileExtensions.length; j++) {
                    var sCurExtension = _validFileExtensions[j];
                    if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                        blnValid = true;
                        break;
                    }
                }
                
                if (!blnValid) {
                    alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
                    return false;
                }
            }
        }
    }
  
    return true;
}




function btncancel() {

	self.close();
	
}