if (window.console) {
	console.log("Welcome to your Play application's JavaScript!");
}
/**
 * 
 * Map canvas starts here
 */
function initialize() {
	var myLatlng = new google.maps.LatLng(28.6447, 77.17662);
	var mapCanvas = document.getElementById('map-canvas');
	var mapOptions = {
		center : myLatlng,
		zoom : 17,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	var map = new google.maps.Map(mapCanvas, mapOptions);

	var marker = new google.maps.Marker({
		position : myLatlng,
		map : map,
		title : 'Knoldus Software LLP'
	});
}
/**
 * Validation for Sign In Form
 */
(function($, W, D) {
	var JQUERY4U = {};

	JQUERY4U.UTIL = {
		setupFormValidation : function() {
			// form validation rules
			$("#signInForm")
					.validate(
							{
								rules : {
									Email : {
										required : true,
										email : true
									},
									Password : {
										required : true,
										minlength : 5
									}
								},
								messages : {
									Password : {
										required : "Please provide a password",
										minlength : "Your password must be at least 5 characters long"
									},
									Email : {
										required : "Please enter an email address",
										email : "Please enter a valid email address"
									}
								},
								submitHandler : function(form) {
									form.submit();
								}
							});
		}
	}

	// when the dom has loaded setup form validation rules
	$(D).ready(function($) {
		JQUERY4U.UTIL.setupFormValidation();
	});

})(jQuery, window, document);
/**
 * Validation for Sign Up and Update Form
 */
(function($, W, D) {
	var JQUERY4U = {};

	JQUERY4U.UTIL = {
		setupFormValidation : function() {
			// form validation rules
			$("#Form")
					.validate(
							{
								rules : {
									Name : {
										required : true,
									},
									Address : {
										required : true,
									},
									Company : {
										required : true,
									},
									Email : {
										required : true,
										email : true
									},
									Password : {
										required : true,
										minlength : 5
									},
									Phone : {
										required : true,
										minlength : 10,
										maxlength : 10,
										number : true

									}
								},
								messages : {
									Name : {
										required : "Please provide a name",
									},
									Address : {
										required : "Please provide an address",
									},
									Company : {
										required : "Please provide a company",
									},
									Password : {
										required : "Please provide a password",
										minlength : "Your password must be at least 5 characters long"
									},
									Email : {
										required : "Please enter an email address",
										email : "Please enter a valid email address"
									},
									Phone : {
										required : "Please enter phone number",
										minlength : "Invalid length",
										maxlength : "Invalid length",
										number : "Please Enter only number"

									}
								},
								submitHandler : function(form) {
									form.submit();
								}
							});
		}
	}

	// when the dom has loaded setup form validation rules
	$(D).ready(function($) {
		JQUERY4U.UTIL.setupFormValidation();
	});

})(jQuery, window, document);


/**
 * Twitter script to show on the page twitter
 * 
 * @param d
 * @param s
 * @param id
 */

!function(d,s,id){
	var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';
	if(!d.getElementById(id)){js=d.createElement(s);
	js.id=id;
	js.src=p+"://platform.twitter.com/widgets.js";
	fjs.parentNode.insertBefore(js,fjs);
	}
	}(document,"script","twitter-wjs");


/**
 * Facebook like box
 */

(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=828920907134556&version=v2.0";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));