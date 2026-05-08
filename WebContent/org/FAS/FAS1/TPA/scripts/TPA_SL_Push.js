function getxmlhttpObject() {
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

function call(command) {
    xmlhttp = getxmlhttpObject();
    if (xmlhttp == null) {
        alert("Your browser does not support AJAX");
        return;
    }

    var unitid = document.getElementById("cmbAcc_UnitCode").value;
    var officeid = document.getElementById("cmbOffice_code").value;
    var sltype = document.getElementById("cmbMas_SL_type").value;
    var txtCB_Year = document.getElementById("txtCB_Year").value;
    var txtCB_Month = document.getElementById("txtCB_Month").value;
    var txtCreate_Date = document.getElementById("txtCreate_Date").value;

    var url = "../../../../../TPA_SL_Push?command=" + command +
        "&unitid=" + unitid +
        "&officeid=" + officeid +
        "&sltype=" + sltype +
        "&txtCreate_Date=" + txtCreate_Date +
        "&txtCB_Year=" + txtCB_Year +
        "&txtCB_Month=" + txtCB_Month;
    url += "&sid=" + Math.random();

    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = stateChanged;
    xmlhttp.send(null);
}

function stateChanged() {
    if (xmlhttp.readyState == 4) {
        if (xmlhttp.status == 200) {
            var contentType = xmlhttp.getResponseHeader("Content-Type");

            if (contentType.includes("application/xml") || contentType.includes("text/xml")) {
                if (xmlhttp.responseXML) {
                    processResponse();
                } else {
                    console.error("Expected XML response but got none.");
                }
            } else {
                console.error("Unexpected Content-Type: " + contentType);
                console.log("Response Text: " + xmlhttp.responseText);
            }
        } else {
            console.error("HTTP Error:", xmlhttp.status, xmlhttp.statusText);
        }
    }
}

function processResponse() {
    var response = xmlhttp.responseXML.getElementsByTagName("response")[0];
    var command = response.getElementsByTagName("command")[0].firstChild.nodeValue;
    var flag = response.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if (command == "get") {
        if (flag == 'success') {
            try {
                // Clear existing table rows
                var tbody = document.getElementById("grid_body");
                while (tbody.rows.length > 0) {
                    tbody.deleteRow(0);
                }

                // Process records
                var records = response.getElementsByTagName("record");
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var subledger_code = record.getElementsByTagName("subledger_code")[0].firstChild.nodeValue;
                    //var sub_ledger_desc = record.getElementsByTagName("sub_ledger_desc")[0].firstChild.nodeValue;
                    var sub_voucherno = record.getElementsByTagName("voucherno")[0].firstChild.nodeValue;
                    var sub_voucherdate = record.getElementsByTagName("voucherdate")[0].firstChild.nodeValue;
                    var unitid = record.getElementsByTagName("sub_unitid")[0].firstChild.nodeValue;
                    var amount = record.getElementsByTagName("amount")[0].firstChild.nodeValue;
                    var TPA_FREEZE_DATE = record.getElementsByTagName("TPA_FREEZE_DATE")[0].firstChild.nodeValue;

                    var txtCreate_Date = document.getElementById("txtCreate_Date").value;
                    var str1_grid = TPA_FREEZE_DATE.split("/");
                    var str2 = txtCreate_Date.split("/");

                    if (str1_grid[2] > str2[2]) {
                        alert("Sl_Pushed_Date should not be less than Freeze Date**");
                        document.getElementById("txtCreate_Date").value = "";
                        document.getElementById("txtCreate_Date").focus();
                        return false;
                    } else if (str1_grid[2] == str2[2]) {
                        if (str1_grid[1] > str2[1]) {
                            alert("Sl_Pushed_Date should not be less than Freeze Date**");
                            document.getElementById("txtCreate_Date").value = "";
                            document.getElementById("txtCreate_Date").focus();
                            return false;
                        } else if (str1_grid[1] == str2[1]) {
                            if (str1_grid[0] > str2[0]) {
                                alert("Sl_Pushed_Date should not be less than Freeze Date**");
                                document.getElementById("txtCreate_Date").value = "";
                                document.getElementById("txtCreate_Date").focus();
                                return false;
                            }
                        }
                    }

                    var mycurrent_row = document.createElement("TR");
                    var cell2 = document.createElement("TD");
                    cell2.style.textAlign = 'center';
                    var chcksel = document.createElement("input");
                    chcksel.type = "checkbox";
                    chcksel.name = "chckparameter";
                    chcksel.value = subledger_code + "," + sub_voucherno;
                    cell2.appendChild(chcksel);
                    mycurrent_row.appendChild(cell2);

                    cell2 = document.createElement("TD");
                    cell2.style.textAlign = 'center';
                    cell2.appendChild(document.createTextNode(sub_voucherno));
                    mycurrent_row.appendChild(cell2);

                    cell2 = document.createElement("TD");
                    cell2.style.textAlign = 'center';
                    cell2.appendChild(document.createTextNode(sub_voucherdate));
                    mycurrent_row.appendChild(cell2);

                    cell2 = document.createElement("TD");
                    var sucode = document.createElement("input");
                    sucode.type = "hidden";
                    sucode.name = "sucode";
                    sucode.value = subledger_code;
                    cell2.appendChild(sucode);
                    cell2.appendChild(document.createTextNode(subledger_code));
                    mycurrent_row.appendChild(cell2);

                    cell2 = document.createElement("TD");
                  //  cell2.appendChild(document.createTextNode(sub_ledger_desc));
                    mycurrent_row.appendChild(cell2);

                    cell2 = document.createElement("TD");
                    cell2.style.textAlign = 'center';
                    cell2.appendChild(document.createTextNode(unitid));
                    mycurrent_row.appendChild(cell2);

                    cell2 = document.createElement("TD");
                    cell2.style.textAlign = 'right';
                    cell2.appendChild(document.createTextNode(amount));
                    mycurrent_row.appendChild(cell2);

                    tbody.appendChild(mycurrent_row);
                }
            } catch (e) {
                alert("Error in processResponse: " + e);
            }
        } else if (flag == 'NoSubledger' || flag == 'NoData') {
            var tbody = document.getElementById("grid_body");
            while (tbody.rows.length > 0) {
                tbody.deleteRow(0);
            }
            alert('Subledger Not Found');
        } else if (flag == 'NoFreezeData') {
            alert("Freeze is not Completed!....");
        }
    } else if (command == "getOriUnit") {
        response = xmlhttp.responseXML.getElementsByTagName("response")[0];
        command = response.getElementsByTagName("command")[0].firstChild.nodeValue;
        flag = response.getElementsByTagName("flag")[0].firstChild.nodeValue;

        var records = response.getElementsByTagName("record");
        var select = document.getElementById('ori_offc');

        if (flag == 'success') {
            select.length = 0;
            var listOpt = document.createElement("option");
            select.appendChild(listOpt);
            listOpt.text = "select";
            listOpt.value = "select";
            for (var i = 0; i < records.length; i++) {
                var record = records[i];
                listOpt = document.createElement("option");
                select.appendChild(listOpt);
                listOpt.text = record.getElementsByTagName("ori_unit_offc_name")[0].firstChild.nodeValue +
                    "(" + record.getElementsByTagName("ori_unit_offc")[0].firstChild.nodeValue + ")";
                listOpt.value = record.getElementsByTagName("ori_unit_offc")[0].firstChild.nodeValue;
            }
        } else {
            select.length = 1;
            select.selectedIndex = 0;
            alert("No data");
        }
    }
}

function selectAll(Opt) {
    var len = document.getElementById("grid_body").rows.length;

    if (len == 1) {
        if (Opt == "ALL") {
            document.getElementById("chckparameter").checked = true;
        } else if (Opt == "UNSelect") {
            document.getElementById("chckparameter").checked = false;
        }
    } else if (len > 1) {
        for (var i = 0; i < len; i++) {
            if (Opt == "ALL") {
                document.tpa_sl_push.chckparameter[i].checked = true;
            } else if (Opt == "UNSelect") {
                document.tpa_sl_push.chckparameter[i].checked = false;
            }
        }
    }
}