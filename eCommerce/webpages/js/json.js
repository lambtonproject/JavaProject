//common functions----------------------------------------------------------------------------------------------------
 
function userControl(user, pass){

	$.getJSON("users.txt", function(data){

		var usernameSearch = $(data).filter((i,v) => v.username.toLowerCase() == user.toLowerCase() && v.password.toLowerCase() == pass.toLowerCase());

		if (usernameSearch.length > 0){

			//$.mobile.changePage('welcome.htm', {dataUrl : 'welcome.htm', data : { 'userid' : usernameSearch[0].id }, reloadPage : true, changeHash : true });
			$(location).attr("href", "welcome.htm?userid=" + usernameSearch[0].id + "&screenusername=" + usernameSearch[0].nickname);

		} else {

			alert("Wrong username (email ID) or password");

		}

	});

}

function getUserId(url){
	
	if (url != undefined){
		
		var parameters = url.split("?")[1];
		
		if (parameters != undefined){
			
			var parameters2 = parameters.split("&");
			var searchvalue = "";
			var userid;
			var htmlstr = "";

			$.each(parameters2, function(key, value){

				var details = this.split("=");

				if (details[0] == "userid"){

					userid = details[1];

				}

			});

			$.data(document, "userid", userid);
			
		}
		
	}
	
}

function getUrlParameter(sParam) {

	var sPageURL = decodeURIComponent(window.location.search.substring(1)),
		sURLVariables = sPageURL.split('&'),
		sParameterName,
		i;

	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : sParameterName[1];
		}
	}
};

function updateJson(value, file){

	//ajax request
	$.ajax({

	  url: "file.php?content=" + value + "&file=" + file,
	  cache: false

	})
	  .done(function() {

		if (file == "cartadd"){
			alert( "Your item is added to your cart successfuly" );
		} else if (file == "users"){
			alert( "You are registered succesfully" );
			$(location).attr("href", "index.htm");	
		} else if (file == "cartdelete"){
			window.history.back();
			alert( "Your item is removed from your cart successfuly" );
		}

	  })
	  .fail(function() {

		alert( "Error while writing the json file. Please, check if there is a write permission." );

	  })
	  .always(function() {
		//alert( "complete" );
	  });		
}

//--------------------------------------------------------------------------------------------------------------------


//login---------------------------------------------------------------------------------------------------------------

$(document).ready(function(){

  	$.ajaxSetup({ 
		cache: false 
	});

	//$.data(document, "userid", getUrlParameter('userid'));
	
	$("#login").click(function(){

		userControl($("#username").val(), $("#password").val());
		
	});

	$("#register").click(function(){
		$(location).attr("href", "register.htm");
	});

	$("#signup").click(function(){

/*		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				myObj = JSON.parse(this.responseText);
				document.getElementById("demo").innerHTML = myObj.nickname;
			}
		};
		xmlhttp.open("GET", "users.txt", true);
		xmlhttp.send(); */
		
		var lastId;
		
		//var tData;
		
		var x = $("#username").val();
		var atpos = x.indexOf("@");
		var dotpos = x.lastIndexOf(".");
		
		if ($("#nickname").val() == "" || $("#username").val() == "" || $("#password").val() == "") {
			
			alert("All fields must be done");
			
		} else {		
		
			if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {

				alert("Username should be as e-mail address (email ID)");

			} else {
			
				$.getJSON("users.txt", function(data){

//					var uniqueSearch = $(data).filter(function (i,v){
//						return v.username.toLowerCase() == $("#username").val();
//					});

					//tData = data;

					var uniqueSearch = $(data).filter((i,v) => v.username.toLowerCase() == $("#username").val().toLowerCase());


					if (uniqueSearch.length > 0) {

						alert("This username is in use by other user. Try again");

					} else {

						var jsonObj = {};

						var jsonArray = [];

						var jsonArrayLastId = [];

						$.each(data, function(k, v) {

							var objJSON = data[k];

							var item = {};
							item.id = objJSON.id;
							item.nickname = objJSON.nickname;
							item.username = objJSON.username;
							item.password = objJSON.password;

							jsonArray.push(item);

							jsonArrayLastId.push(objJSON.id);

						});

						//lastId = jsonArray.pop().id;
						
						//in ascending order
						//jsonArrayLastId.sort(jsonArrayLastId.sort((a, b) => a - b)); 
						
						//in descending order
						jsonArrayLastId.sort((a, b) => b - a);	

						lastId = jsonArrayLastId[0];

						//lastId = jsonArray[jsonArray.length - 1].id;

						var item = {};
						item.id = lastId + 1;
						item.nickname = $("#nickname").val();
						item.username = $("#username").val();
						item.password = $("#password").val();

						//tData.push(item);
						jsonArray.push(item);

						//updateJson(JSON.stringify(tData));
						updateJson(JSON.stringify(jsonArray), "users");

					}

				});

			}
		}
		
	});		

	
//-----------------------------------------------------------------------------------------------------------------------------

 
//product search---------------------------------------------------------------------------------------------------------------
	
	$(document).on("change", "#selectproduct", function(){

//		if ($.data(document, "userid") == undefined){
//			alert("You must login before processing forward");
//			return false;
//		}
		
		var search = $("#selectproduct").val();
		
		//$.data(document, "userid", getUrlParameter('userid'));

		$.mobile.changePage('product_category.htm', {dataUrl : "", data : { 'search' : search,'searchmode' : 'category', 'userid' : $.data(document, "userid") }, reloadPage : true, changeHash : true });
		 
	});
	 
	$(document).on("click", "#btnsearch", function(){

//		if ($.data(document, "userid") == undefined){
//			alert("You must login before processing forward");
//			return false;
//		}
		
		var search = $("#search").val();
		
		//$.data(document, "userid", getUrlParameter('userid'));
		
		$.mobile.changePage('product_category.htm', {dataUrl : "", data : { 'search' : search,'searchmode' : 'manual', 'userid' : $.data(document, "userid") }, reloadPage : true, changeHash : true });
		 
	});
	
});
 
//--------------------------------------------------------------------------------------------------------------------


//pageListOfProducts--------------------------------------------------------------------------------------------------

$(document).on("pagebeforeshow", "#pageListOfProducts", function (event, data) {
	
	$.data(document, "userid", getUrlParameter('userid'));
	$.data(document, "screenusername", getUrlParameter('screenusername'));
	
	$("#screenusername").html("<strong>Welcome: " + $.data(document, "screenusername") + "</strong><br><a href='index.htm'><h5>Logout</h5></a>");
 
});

//--------------------------------------------------------------------------------------------------------------------


//pageProductsCategory------------------------------------------------------------------------------------------------

$(document).on("pagebeforeshow", "#pageProductsCategory", function (event, data) {
	
	var parameters = $(this).data("url").split("?")[1];
	var parameters2 = parameters.split("&");
	var searchvalue = "";
	var searchmode = "";
	var userid; 
	var htmlstr = "";
	 
	//getUserId($(this).data("url"));	
	
	$.each(parameters2, function(key, value){
		
		var details = this.split("=");
		
		if (details[0] == "search"){
			
			searchvalue = details[1];
				
		} else if (details[0] == "userid"){
			
			userid = details[1];
			
		} else if (details[0] == "searchmode"){
			
			searchmode = details[1];
			
		}
		
	});

//$.data(document, "userid", getUrlParameter('userid'));

	
		$.getJSON("products.txt", function(data){
			
			if (searchmode == "category"){
			
				$.each(data, function(key, value) {
						
					if ((key.toLowerCase()).search(searchvalue.toLowerCase()) != -1){

						$.each(value, function(key2, value2) {

							objJSON = value2;

							htmlstr += '<li cat="' + key + '" data="' + objJSON.id + '" data-name="' + objJSON.name + '" data-quantity="' + objJSON.quantity + '" data-price="' + objJSON.price + '" data-image="' + objJSON.image + '"><a href="#"><img src="img/' + objJSON.image + '"><h2>' + objJSON.name + '</h2><p><b>Product No:</b> ' + objJSON.id + '</p><p><b>Quantity:</b> ' + objJSON.quantity + '</p><p><b>Price:</b> ' + objJSON.price + '</p></a></li>';

						});

					};

				});
			
			} else if (searchmode == "manual"){
			
				$.each(data, function(key, value) {

					$.each(value, function(key2, value2) {

						objJSON = value2;

						if (((objJSON.name).toLowerCase()).search(searchvalue.toLowerCase()) != -1){

							htmlstr += '<li cat="' + key + '" data="' + objJSON.id + '" data-name="' + objJSON.name + '" data-quantity="' + objJSON.quantity + '" data-price="' + objJSON.price + '" data-image="' + objJSON.image + '"><a href="#"><img src="img/' + objJSON.image + '"><h2>' + objJSON.name + '</h2><p><b>Product No:</b> ' + objJSON.id + '</p><p><b>Quantity:</b> ' + objJSON.quantity + '</p><p><b>Price:</b> ' + objJSON.price + '</p></a></li>';

						};

					});

				});
				
			}
			
			$("#productlist").html(htmlstr);
			$('#productlist').listview('refresh');
			
		});

});


$(document).ready(function(){
	
	$(document).on("click", "#productlist li", function(){
		
		//alert($(this).attr("data"));
		$.mobile.changePage('product_detail.htm', {dataUrl : "", data : { 'search' : $(this).attr("data"), 'userid' : $.data(document, "userid"), 'cat' : $(this).attr("cat"), 'quantity' : $(this).attr("data-quantity") , 'price' : $(this).attr("data-price") , 'image' : $(this).attr("data-image") , 'name' : $(this).attr("data-name") }, reloadPage : true, changeHash : true });
		
	});	
	
});

//--------------------------------------------------------------------------------------------------------------------

 
//pageProductDetail---------------------------------------------------------------------------------------------------

$(document).on("pagebeforeshow", "#pageProductDetail", function (event, data) {
	
	var parameters = $(this).data("url").split("?")[1];
	var parameters2 = parameters.split("&");
	var searchvalue = "";
	var userid;
	var htmlstr = "";
	
	$.each(parameters2, function(key, value){
		
		var details = this.split("=");
		
		if (details[0] == "search"){
			
			searchvalue = details[1];
			$.data(document, "pid", searchvalue)
			
		} else if (details[0] == "userid"){
			
			userid = details[1];
			
		} else if (details[0] == "cat"){
			
			$.data(document, "cat", details[1]);
			
		} else if (details[0] == "name"){
				
			$.data(document, "data_name", details[1]);

		} else if (details[0] == "quantity"){
			
			$.data(document, "data_quantity", details[1]);

		} else if (details[0] == "price"){
			
			$.data(document, "data_price", details[1]);

		} else if (details[0] == "image"){
			
			$.data(document, "data_image", details[1]);

		}
		
	});
 
	//alert($.data(document, "userid"));
	
		$.getJSON("products.txt", function(data){
			
			$.each(data, function(key, value) {

				$.each(value, function(key2, value2) {

					objJSON = value2;

					if (objJSON.id == searchvalue){

						htmlstr += '<img src="img/' + objJSON.image + '"><h2>' + objJSON.name + '</h2><p><b>Product No:</b> ' + objJSON.id + '</p><p><b>Quantity:</b> ' + objJSON.quantity + '</p><p><b>Price:</b> ' + objJSON.price + '</p><p><a class="cartadd ui-button ui-btn ui-corner-all ui-btn-inline" href="#"><b>Add to Cart</b></a></p>';

					};

				});

			});
		
			$("#productdetails").html(htmlstr);
			
		});

});


$(document).ready(function(){

	$(document).on("click", "a.cartadd", function(e){
		
		//alert($(this).attr("data"));
		e.preventDefault();

		var productUpdate2;
		var newData;
		
		$.getJSON("cart.txt", function(data){ 

			var item = {};

			item.id = $.data(document, "pid");
			item.userid = $.data(document, "userid");
			item.name = $.data(document, "data_name");
			item.quantity = 1;
			item.price = $.data(document, "data_price");
			item.image = $.data(document, "data_image");
			
			
			var searchKey = 0;
			
			$.each(Object.keys(data), function(key, value) {
				
				if (value == $.data(document, "cat")) {
					
					searchKey += 1;
					
				}
				
			});


			if (searchKey > 0){

				var uniqueProduct = $.grep(data[$.data(document, "cat")], function(object, index) {

					return object.id == $.data(document, "pid") && object.userid == $.data(document, "userid");

				});	

				if (uniqueProduct.length > 0){

					alert("This product was already added before to your cart list.");
					return;

				}
				
				productUpdate2 = $(data).filter(function (i,v){

					return true;

				});
				
				newData = productUpdate2[0];
				
				newData[$.data(document, "cat")].push(item);
				
			} else {
				
				var jsonObj = {};
				var jsonArray = [];
				
				jsonArray.push(item);

				productUpdate2 = $(data).filter(function (i,v){

					return true;

				});
				
				newData = productUpdate2[0];
				
				newData[$.data(document, "cat")] = jsonArray;
				
			}
			

			updateJson(JSON.stringify(newData), "cartadd");
			
/*			var jsonObj = {};
			
			$.each(data, function(k, v) {

				var jsonArray = [];

				$.each(v, function(k2, v2) {

					var objJSON = v[k2];

					var item = {};
					item.id = objJSON.id;
					item.userid = objJSON.userid;
					item.name = objJSON.name;
					item.quantity = objJSON.quantity;
					item.price = objJSON.price;
					item.image = objJSON.image;

					jsonArray.push(item);

				});
				
				if ($.data(document, "cat") == k){
					
					var item = {};
					
					item.id = $.data(document, "pid");
					item.userid = $.data(document, "userid");
					item.name = $(this).attr("name");
					item.quantity = 1;
					item.price = $(this).attr("price");
					item.image = $(this).attr("image");
					
					jsonArray.push(item);
					
				}
				
				jsonObj[k] = jsonArray;

				updateJson(JSON.stringify(jsonObj), "cartadd");
			
			});
*/		
		});
		
		//if there is no data in cart.txt file
/*		if(checkData == undefined){
			
			var jsonObj = {};
			var jsonArray = [];

			var item = {};

			item.id = $.data(document, "pid");
			item.userid = $.data(document, "userid");
			item.name = $(this).attr("name");
			item.quantity = 1;
			item.price = $(this).attr("price");
			item.image = $(this).attr("image");

			jsonArray.push(item);
			
			jsonObj[$.data(document, "cat")] = jsonArray;
			
			updateJson(JSON.stringify(jsonObj), "cartadd");
			
		}*/
		
		//quantity will be decreased by 1
		$.getJSON("products.txt", function(data){
			
			var productUpdate = $(data).filter(function (i,v){

				$.each(v, function(k1, v1) {
   
					$.each(v1, function(k2, v2) {

						var objJSON = v1[k2];
 
						if (objJSON.id == $.data(document, "pid")){
							objJSON.quantity -= 1;
						}

					});

				});	

				return true;

			});
			
			updateJson(JSON.stringify(productUpdate[0]), "products");
			
		});

	});	
	
});

//--------------------------------------------------------------------------------------------------------------------


//pageMyCartList------------------------------------------------------------------------------------------------------

$(document).on("pagebeforeshow", "#pageMyCartList", function (event, data) {

	//alert($.data(document, "userid"));
	
		$.getJSON("cart.txt", function(data){
			
			var htmlstr = "";
			
			$.each(data, function(key, value) {

					$.each(value, function(key2, value2) {
						
						var objJSON = value2; 

						if (objJSON.userid == $.data(document, "userid")){
							
							htmlstr += '<li cat="' + key + '" data="' + objJSON.id + '"><a href="#"><img src="img/' + objJSON.image + '"><h2>' + objJSON.name + '</h2><p><b>Product No:</b> ' + objJSON.id + '</p><p><b>Quantity:</b> ' + objJSON.quantity + '</p><p><b>Price:</b> ' + objJSON.price + '</p></a></li>';
							
						}

					});

			});

			$("#cartlist").html(htmlstr);
			$('#cartlist').listview('refresh');
			
		});

});


$(document).ready(function(){
	
	$(document).on("click", "#cartlist li", function(){
		
		//alert($(this).attr("data"));
		$.mobile.changePage('mycart_detail.htm', {dataUrl : "", data : { 'search' : $(this).attr("data"), 'userid' : $.data(document, "userid"), 'cat' : $(this).attr("cat") }, reloadPage : true, changeHash : true });
		
	});	
	
});

//--------------------------------------------------------------------------------------------------------------------


//pageMyCartDetail----------------------------------------------------------------------------------------------------

$(document).on("pagebeforeshow", "#pageMyCartDetail", function (event, data) {
	
	var parameters = $(this).data("url").split("?")[1];
	var parameters2 = parameters.split("&");
	var searchvalue = "";
	var userid;
	var htmlstr = "";
	
	$.each(parameters2, function(key, value){
		
		var details = this.split("=");
		
		if (details[0] == "search"){
			
			searchvalue = details[1];
			$.data(document, "pid", searchvalue)
			
		} else if (details[0] == "userid"){
			
			userid = details[1];
			
		} else if (details[0] == "cat"){
			
			$.data(document, "cat", details[1]);
			
		}
		
	});

	//alert($.data(document, "userid"));
	
		$.getJSON("cart.txt", function(data){
			
			$.each(data, function(key, value) {

				$.each(value, function(key2, value2) {

					objJSON = value2;

					if (objJSON.id == searchvalue && objJSON.userid == $.data(document, "userid")){

						htmlstr += '<img src="img/' + objJSON.image + '"><h2>' + objJSON.name + '</h2><p><b>Product No:</b> ' + objJSON.id + '</p><p><b>Quantity:</b> ' + objJSON.quantity + '</p><p><b>Price:</b> ' + objJSON.price + '</p><p><a class="cartdelete ui-button ui-btn ui-corner-all ui-btn-inline" href="#" cat="' + key + '" data="' + objJSON.id + '"><b>Delete the product from your cart</b></a></p>';

					};

				});

			});
		
			$("#productdetails").html(htmlstr);
			
		});

});


$(document).ready(function(){

	$(document).on("click", "a.cartdelete", function(e){
		
		//alert($(this).attr("data"));
		e.preventDefault();
		
		$.getJSON("cart.txt", function(data){ 
			
			var returnedData = $(data).filter(function (i,v){

				var idx;
				var keyx;
				
				$.each(data, function(k, v) {

					$.each(v, function(k2, v2) {

						var objJSON = v[k2];

						if (objJSON.id == $.data(document, "pid") && objJSON.userid == $.data(document, "userid")) {

							idx = k2;
							keyx = k;

						}

					});

				});	

				v[keyx].splice(idx,1);

				return true;		//only one time, this filter runs and return whole object
				
			});
			
			updateJson(JSON.stringify(returnedData[0]), "cartdelete");
			
		});

		
		//quantity will be increased by 1
		$.getJSON("products.txt", function(data){
			
			var productUpdate = $(data).filter(function (i,v){

				$.each(data, function(k1, v1) {

					$.each(v1, function(k2, v2) {

						var objJSON = v1[k2];
 
						if (objJSON.id == $.data(document, "pid")){
							objJSON.quantity += 1;
						}

					});

				});	

				return true;

			});
			
			updateJson(JSON.stringify(productUpdate[0]), "products");
			
		});

	});	
	
});

//--------------------------------------------------------------------------------------------------------------------


//pageMyProfile-------------------------------------------------------------------------------------------------------

$(document).on("pagebeforeshow", "#pageMyProfile", function (event, data) {
	
	$.getJSON("users.txt", function(data){
		
		var usernameSearch = $.grep(data, function (element, index) {

			return element.id == $.data(document, "userid");

		});

		$("#nickname").val(usernameSearch[0].nickname);
		$("#username").val(usernameSearch[0].username);
		$("#password").val(usernameSearch[0].password);
		
	});
	
});


$(document).ready(function(){

	$(document).on("click", "#linkMyProfile", function(e){
		
		e.preventDefault();

		//alert($(this).attr("data"));
		$.mobile.changePage('myprofile.htm', {dataUrl : "", data : { 'search' : $(this).attr("data"), 'userid' : $.data(document, "userid") }, reloadPage : true, changeHash : true });
		
	});	
	
	$(document).on("click", "#updateprofile", function(e){
		
		$("#nickname").removeClass('ui-disabled');
		$("#username").removeClass('ui-disabled');
		$("#password").removeClass('ui-disabled');
		$("#profilesubmit").removeClass('ui-disabled');
		
	});

	$(document).on("click", "#profilesubmit", function(e){
		
		$.getJSON("users.txt", function(data){

			var productUpdate = $.grep(data, function (v, i) {

					var objJSON = v;

					if (objJSON.id == $.data(document, "userid")){

						objJSON.nickname = $("#nickname").val();
						objJSON.username = $("#username").val();
						objJSON.password = $("#password").val();

					}

					return true;

			});

			updateJson(JSON.stringify(productUpdate), "users");
			
		});
		
	});
	
});		
//--------------------------------------------------------------------------------------------------------------------