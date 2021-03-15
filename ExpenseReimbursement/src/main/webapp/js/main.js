function changeStatus(id, status) {
	//console.log(id);
//	var form = document.getElementById('employeeUpdateForm');
	var formData = new FormData();
	/* var fileInput = document.getElementById('receipt');
	var file = fileInput.files[0];
	
	if(fileInput.files && fileInput.files.length == 1){
	     var file = fileInput.files[0]
	     formData.append('receipt', file, file.name);
	 } */
	formData.append('id', id);
	formData.append('status', status);

	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			document.getElementById('alert_msg').innerHTML = xhr.responseText;
			document.getElementById("alert_div").style.display = "inline";
			getListOfEmployeesRequest();
		}
	};
	xhr.open('POST', 'updateEmployeeRequest', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send(formData);

	return false;
}

function approveRequest(id){
	changeStatus(id, 'APPROVED');
}

function denyRequest(id){
	changeStatus(id, 'DENIED');
}

function hideModal() {
	document.getElementById("modal_data").innerHTML = "";
	document.getElementById("modal").style.display = "none";
}

function showModal(content) {
//	modal_data
	var data = '<img src="data:image/png;base64, ';
	document.getElementById("modal_data").innerHTML = data.concat(content, '" width="600" height="600">');
	document.getElementById("modal").style.display = "inline";
}

function getListOfEmployeesRequest() {
	document.getElementById("manage_pending_receipts_data").innerHTML = "";
	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			var data = JSON.parse(xhr.responseText);
			// console.log(data);

			var rows = new String("");
			for (var i = 0; i < data.receipts.length; i++) {
				//console.log(data.users[i]);
				rows = rows.concat('<tr><th scope="row">', (i + 1),
						'</th><td>', data.receipts[i].username, '</td><td>',
						'</th><td>', data.receipts[i].title, '</td><td>',
						data.receipts[i].description, '</td><td>', data.receipts[i].amount, '</td>',
						'<td>', data.receipts[i].status, '</td>',
						'<td>', '<img src="data:image/png;base64, ',data.receipts[i].receipt, '" width="100" height="100"></td>',
						'<td><button class="btn btn-success" onClick="approveRequest(\'', data.receipts[i].id, '\')" >Approve</button>',
						'&nbsp;&nbsp;',
						'<button class="btn btn-danger" onClick="denyRequest(\'', data.receipts[i].id, '\')" >Deny</button>',
						'</td>')
			}
			document.getElementById("manage_pending_receipts_data").innerHTML = rows;
		}
	};
	xhr.open('GET', 'listEmployeesPendingRequests', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send();

	return false;
}


function submitEmployee() {

	var form = document.getElementById('employeeform');
	var formData = new FormData();
	/* var fileInput = document.getElementById('receipt');
	var file = fileInput.files[0];
	
	if(fileInput.files && fileInput.files.length == 1){
	     var file = fileInput.files[0]
	     formData.append('receipt', file, file.name);
	 } */
	formData.append('fname', form.elements[0].value);
	formData.append('lname', form.elements[1].value);
	formData.append('email', form.elements[2].value);
	formData.append('password', form.elements[3].value);
	formData.append('role', form.elements[4].value);

	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			document.getElementById('alert_msg').innerHTML = xhr.responseText;
			document.getElementById("alert_div").style.display = "inline";
			document.getElementById("employeeform").reset();
		}
	};
	xhr.open('POST', 'addEmployee', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send(formData);

	return false;
}

function updateEmployee() {
	var form = document.getElementById('employeeUpdateForm');
	var formData = new FormData();
	/* var fileInput = document.getElementById('receipt');
	var file = fileInput.files[0];
	
	if(fileInput.files && fileInput.files.length == 1){
	     var file = fileInput.files[0]
	     formData.append('receipt', file, file.name);
	 } */
	//console.log(form.elements[2].value)
	formData.append('fname', form.elements[0].value);
	formData.append('lname', form.elements[1].value);
	formData.append('password', form.elements[2].value);

	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			document.getElementById('alert_msg').innerHTML = xhr.responseText;
			document.getElementById("alert_div").style.display = "inline";
			document.getElementById("employeeUpdateForm").reset();
		}
	};
	xhr.open('POST', 'updateEmployee', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send(formData);

	return false;
}

function submitReceipt() {

	var form = document.getElementById('reimbursementform');
	var formData = new FormData();
	var fileInput = document.getElementById('receipt');
	var file = fileInput.files[0];

	if (fileInput.files && fileInput.files.length == 1) {
		var file = fileInput.files[0]
		formData.append('receipt', file, file.name);
	}
	formData.append('title', form.elements[0].value);
	formData.append('description', form.elements[1].value);
	formData.append('amount', form.elements[2].value);

	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			document.getElementById('alert_msg').innerHTML = xhr.responseText;
			document.getElementById("alert_div").style.display = "inline";
			document.getElementById("reimbursementform").reset();
		}
	};
	xhr.open('POST', 'uploadReceipt', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send(formData);

	return false;
}

function getEmployeeInfo() {

	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			var data = JSON.parse(xhr.responseText);
			// console.log(data);

			document.getElementById("fname2").value = data.fname;
			document.getElementById("lname2").value = data.lname;
		}
	};
	xhr.open('GET', 'getEmployeeInfo', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	xhr.send();

	return false;

}

function getEmployees() {

	document.getElementById("employee_data").innerHTML = "";
	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			var data = JSON.parse(xhr.responseText);
			// console.log(data);

			var rows = new String("");
			for (var i = 0; i < data.users.length; i++) {
				//console.log(data.users[i]);
				rows = rows.concat('<tr><th scope="row">', (i + 1),
						'</th><td>', data.users[i].fname, '</td><td>',
						data.users[i].lname, '</td><td>', data.users[i].email,
						'</td><td>', data.users[i].role, '</td><td>',
						data.users[i].manager, '</td>')
			}
			document.getElementById("employee_data").innerHTML = rows;
		}
	};
	xhr.open('GET', 'listEmployees', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send();

	return false;

}

function getPendingRequests() {

	document.getElementById("pending_receipts_data").innerHTML = "";
	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			var data = JSON.parse(xhr.responseText);
			// console.log(data);

			var rows = new String("");
			for (var i = 0; i < data.receipts.length; i++) {
				//console.log(data.users[i]);
				rows = rows.concat('<tr><th scope="row">', (i + 1),
						'</th><td>', data.receipts[i].title, '</td><td>',
						data.receipts[i].description, '</td><td>', data.receipts[i].manager,
						'</td><td>', data.receipts[i].amount, '</td>',
						'<td>', data.receipts[i].status, '</td>',
						'<td>', '<img src="data:image/png;base64, ',data.receipts[i].receipt, '" width="100" height="100"></td>',
						'<td>','<button class="btn btn-danger" onClick="showModal(\'', data.receipts[i].receipt, '\')" >View</button>','</td>')
			}
			document.getElementById("pending_receipts_data").innerHTML = rows;
		}
	};
	xhr.open('GET', 'listPendingRequests', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send();

	return false;

}

function getResolvedRequests() {

	document.getElementById("resolved_receipts_data").innerHTML = "";
	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			//console.log(xhr.responseText);
			var data = JSON.parse(xhr.responseText);
			// console.log(data);

			var rows = new String("");
			for (var i = 0; i < data.receipts.length; i++) {
				//console.log(data.users[i]);
				rows = rows.concat('<tr><th scope="row">', (i + 1),
						'</th><td>', data.receipts[i].title, '</td><td>',
						data.receipts[i].description, '</td><td>', data.receipts[i].manager,
						'</td><td>', data.receipts[i].amount, '</td>',
						'<td>', data.receipts[i].status, '</td>',
						'<td>', '<img src="data:image/png;base64, ',data.receipts[i].receipt, '" width="100" height="100"></td>',
						'<td>','<button class="btn btn-danger" onClick="showModal(\'', data.receipts[i].receipt, '\')" >View</button>','</td>');
			}
			document.getElementById("resolved_receipts_data").innerHTML = rows;
		}
	};
	xhr.open('GET', 'listResolvedRequests', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send();

	return false;

}

function getAllReceipts(code, id) {
	
	var formData = new FormData();
	formData.append('status', code);

	document.getElementById(id).innerHTML = "";
	var xhr = new XMLHttpRequest(); ////window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
	xhr.onreadystatechange = function() {
		if (xhr.readyState > 3 && xhr.status === 200) {
			// console.log(xhr.responseText);
			var data = JSON.parse(xhr.responseText);
			// console.log(data);

			var rows = new String("");
			for (var i = 0; i < data.receipts.length; i++) {
				//console.log(data.users[i]);
				rows = rows.concat('<tr><th scope="row">', (i + 1),
						'</th><td>', data.receipts[i].name, '</td>',
						'<td>', data.receipts[i].manager,
						'</td><td>', data.receipts[i].title,
						'</td><td>', data.receipts[i].amount, '</td>',
						'<td>', data.receipts[i].status, '</td>',
						'<td>', '<img src="data:image/png;base64, ',data.receipts[i].receipt, '" width="100" height="100"></td>',
						'<td>','<button class="btn btn-danger" onClick="showModal(\'', data.receipts[i].receipt, '\')" >View</button>','</td>');
			}
			document.getElementById(id).innerHTML = rows;
		}
	};
	xhr.open('POST', 'listAllReceipts', true);
	xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	////xhr.setRequestHeader('Content-Type', 'multipart/form-data');
	////xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send(formData);

	return false;

}


function closeAlert() {
	document.getElementById("alert_div").style.display = "none";
	return true;
}

function hideAll() {
	
	document.getElementById("home").style.display = "none";
	document.getElementById("addReceipt").style.display = "none";
	
	if (document.getElementById("viewEmployees") !== null) {
		document.getElementById("viewEmployees").style.display = "none";
	}
	
	if (document.getElementById("addEmployee") !== null) {
		document.getElementById("addEmployee").style.display = "none";
	}
	
	if (document.getElementById("viewManagePendingReceipts") !== null) {
		document.getElementById("viewManagePendingReceipts").style.display = "none";
	}
	
	if (document.getElementById("viewAllReceipts") !== null) {
		document.getElementById("viewAllReceipts").style.display = "none";
	}
	
	if (document.getElementById("viewAllCompletedReceipts") !== null) {
		document.getElementById("viewAllCompletedReceipts").style.display = "none";
	}
	
	if (document.getElementById("viewAllReceiptsUnderMe") !== null) {
		document.getElementById("viewAllReceiptsUnderMe").style.display = "none";
	}
	
	document.getElementById("viewPendingReceipts").style.display = "none";
	document.getElementById("updateEmployee").style.display = "none";
	document.getElementById("viewResolvedReceipts").style.display = "none";
}

function onManageRequestsClick() {
	hideAll();
	if (document.getElementById("viewManagePendingReceipts") !== null) {
		document.getElementById("viewManagePendingReceipts").style.display = "inline";
	}
	getListOfEmployeesRequest();
}

function onViewAllReceiptClick() {
	hideAll();
	if (document.getElementById("viewAllReceipts") !== null) {
		document.getElementById("viewAllReceipts").style.display = "inline";
	}
	getAllReceipts('all', 'all_receipts_data');
}

function onViewAllResolvedClick() {
	hideAll();
	if (document.getElementById("viewAllCompletedReceipts") !== null) {
		document.getElementById("viewAllCompletedReceipts").style.display = "inline";
	}
	getAllReceipts('resolved', 'all_completed_receipts_data');
}

function onViewAllUnderMeClick() {
	hideAll();
	if (document.getElementById("viewAllReceiptsUnderMe") !== null) {
		document.getElementById("viewAllReceiptsUnderMe").style.display = "inline";
	}
	getAllReceipts('underme', 'all_receipts_under_me_data');
}

function onHomeClick() {
	hideAll();
	document.getElementById("home").style.display = "inline";
	return true;
}

function onProfileClick() {
	hideAll();
	document.getElementById("updateEmployee").style.display = "inline";
	getEmployeeInfo();
	return true;
}

function onBack() {
	onHomeClick();
	return false;
}

function onAddReimbursmentClick() {
	hideAll();
	document.getElementById("addReceipt").style.display = "inline";
	return true;
}

function onAddEmployeeClick() {
	hideAll();
	if (document.getElementById("addEmployee") !== null) {
		document.getElementById("addEmployee").style.display = "inline";
	}
	return true;
}

function onViewPendingRequestsClick() {
	hideAll();
	document.getElementById("viewPendingReceipts").style.display = "inline";
	getPendingRequests();
	return true;
}



function onViewResolvedRequestsClick() {
	hideAll();
	document.getElementById("viewResolvedReceipts").style.display = "inline";
	getResolvedRequests();
	return true;
}

function onViewEmployeeClick() {
	hideAll();
	
	if (document.getElementById("viewEmployees") !== null) {
		document.getElementById("viewEmployees").style.display = "inline";
	}

	getEmployees();

	return true;
}