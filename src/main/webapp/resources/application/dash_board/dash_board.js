var selectedDriversArray = [];
var selectedPlatesArray = [];


//check plate id is already assign
function checkPlateIdISAssign(eleId){
	
	var plateId = eleId.split("_")[4];
	
	for(var i = 0 ; i < selectedPlatesArray.length ; i++){
		if(selectedPlatesArray[i].split("_")[4] == plateId){
			//alert('sorry this items plate is assign to another driver');
			notify('Error !','Sorry this Items Plate is already assigned','danger','#error_div');
			return false;
		}
	}
	return true;
	
}

//set driver tags
function setDriverTag(el){
	var r = selectedDriversArray.indexOf(el.id);
	if(r == -1){
		selectedDriversArray.push(el.id);
		el.setAttribute("class","btn btn-info active");
	}else{
		selectedDriversArray.splice(r,1);
		el.setAttribute("class","btn btn-danger");
	}
	
}

//set driver tags
function setPlateTag(el){
	var r = selectedPlatesArray.indexOf(el.id);
	if(r == -1){
		//check plate is already add 
		if(checkPlateIdISAssign(el.id) == false){return;}
		
		selectedPlatesArray.push(el.id);
		el.setAttribute("class","btn btn-info active");
		
	}else{
		selectedPlatesArray.splice(r,1);
		el.setAttribute("class","btn btn-success");
	}
	
}

// function - fire when <a>driver onclick
var actionDriverSelect = function() {
	setDriverTag(this);
};

// function - fire when <a>plate onclick
var actionPlateSelect = function() {
	setPlateTag(this);
};

// seting onclick for each a driver tags
var setOnclickForDriver = function() {
	// get all a_driver tags
	var tag_a = document.getElementsByName("a_fire_driver");
	
	for (var i = 0; i < tag_a.length; i++) {
		tag_a[i].onclick = actionDriverSelect;
	}
}

// seting onclick for each a plate tags
var setOnclickForPlate = function() {
	// get all a_plate tags
	var tag_a = document.getElementsByName("a_fire_plate");

	for (var i = 0; i < tag_a.length; i++) {
		tag_a[i].onclick = actionPlateSelect;
	}
}

//submit plate log data to the server
var submit = function(){
	
	//class - PlateLog
	var PlateLog = function(driver,itemsPlates){
		this.driver = driver;
		this.itemsPlates = itemsPlates;
	}
	//array - PlateLog
	var plateLogs = [];
	
	//go through each drivers
	for(var i = 0 ; i < selectedDriversArray.length ; i++){
		var driverId = selectedDriversArray[i].split("_")[2];
		
		//items plates for perticular driver
		var itemsPlates = [];
		
		//go through each selected plates
		for(var p = 0 ; p < selectedPlatesArray.length ; p++){
			
			//check plate element's id contains driver id.if contains it mean this plate connect with this driver
			if(driverId == selectedPlatesArray[p].split("_")[2]){
				var plateId = selectedPlatesArray[p].split("_")[4];
				itemsPlates.push(plateId)
			}
			
		}
		
		//driverPlates != 0 mean .this driver is assigned with 1 or more plates
		if(itemsPlates.length != 0){
			var plateLog = new PlateLog(driverId,itemsPlates);
			//add into plates log array
			plateLogs.push(plateLog);
		}
		
	}
	
	if(plateLogs.length == 0){
		notify('Error !','Unable to create Plate Log.','danger','#error_div');
	}else{
		var jsonRequest = JSON.stringify(plateLogs);
		console.log(jsonRequest);
		
		$.ajax({
			'url' : 'dash_board',
			'data' : jsonRequest,
			'type' : 'POST',
			success: function(data) {
				console.log(data);
				var response = JSON.parse(data);
				if(response.status == 1){
					//alert(response.value);
					notify('Success',response.value,'success','#error_div');
				}else{
					//alert(response.value);
					notify('Error !',response.value,'danger','#error_div');
				}
			}
		});
	}
	
	
	
}

//calling
setOnclickForDriver();
setOnclickForPlate();