$(document).ready(function () {
    //Initialize tooltips
    $('.nav-tabs > li a[title]').tooltip();
    
    //Wizard
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {

        var $target = $(e.target);
    
        if ($target.parent().hasClass('disabled')) {
            return false;
        }
    });

    $(".next-step").click(function (e) {

        var $active = $('.wizard .nav-tabs li.active');
        $active.next().removeClass('disabled');
        nextTab($active);

    });
    $(".prev-step").click(function (e) {

        var $active = $('.wizard .nav-tabs li.active');
        prevTab($active);

    });
});

function nextTab(elem) {
    $(elem).next().find('a[data-toggle="tab"]').click();
}
function prevTab(elem) {
    $(elem).prev().find('a[data-toggle="tab"]').click();
}


//according menu

$(document).ready(function()
{
    //Add Inactive Class To All Accordion Headers
    $('.accordion-header').toggleClass('inactive-header');
	
	//Set The Accordion Content Width
	var contentwidth = $('.accordion-header').width();
	$('.accordion-content').css({});
	
	//Open The First Accordion Section When Page Loads
	$('.accordion-header').first().toggleClass('active-header').toggleClass('inactive-header');
	$('.accordion-content').first().slideDown().toggleClass('open-content');
	
	// The Accordion Effect
	$('.accordion-header').click(function () {
		if($(this).is('.inactive-header')) {
			$('.active-header').toggleClass('active-header').toggleClass('inactive-header').next().slideToggle().toggleClass('open-content');
			$(this).toggleClass('active-header').toggleClass('inactive-header');
			$(this).next().slideToggle().toggleClass('open-content');
		}
		
		else {
			$(this).toggleClass('active-header').toggleClass('inactive-header');
			$(this).next().slideToggle().toggleClass('open-content');
		}
	});
	
	return false;
});


function initializeMap() {
    // Map Center
    var myLatLng = new google.maps.LatLng(-34.572902, -58.423161);//-34.572902, -58.423161
    // General Options
    var mapOptions = {
        zoom: 14,
        center: myLatLng,
        mapTypeId: google.maps.MapTypeId.RoadMap
    };
    var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
    // Polygon Coordinates
    // read them from #aptMap and crate an array
    var coordsList = $("#aptMap").val();
    var coordsArr = coordsList.split(";");

    var polygonCoords = [];
    for(var i = 0; i < coordsArr.length - 1; i++) {
        var coord = coordsArr[i].split(",");
        polygonCoords.push(new google.maps.LatLng(coord[0], coord[1]));
    }

    if(polygonCoords.length === 0) {
        // set default coords
        polygonCoords.push(new google.maps.LatLng(-34.557176, -58.430436));
        polygonCoords.push(new google.maps.LatLng(-34.588696, -58.431428));
        polygonCoords.push(new google.maps.LatLng(-34.575376, -58.403839));
    }

    // Styling & Controls
    myPolygon = new google.maps.Polygon({
        paths: polygonCoords,
        draggable: true, // turn off if it gets annoying
        editable: true,
        strokeColor: '#FF0000',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#FF0000',
        fillOpacity: 0.35
    });

    myPolygon.setMap(map);
    //google.maps.event.addListener(myPolygon, "dragend", getPolygonCoords);
    google.maps.event.addListener(myPolygon.getPath(), "insert_at", getPolygonCoords);
    //google.maps.event.addListener(myPolygon.getPath(), "remove_at", getPolygonCoords);
    google.maps.event.addListener(myPolygon.getPath(), "set_at", getPolygonCoords);

    getPolygonCoords();
}

//Display Coordinates below map
function getPolygonCoords() {
    var len = myPolygon.getPath().getLength();
    var htmlStr = "";
    for (var i = 0; i < len; i++) {
        htmlStr += myPolygon.getPath().getAt(i).toUrlValue(5) + ";";
    }
    $("#aptMap").val(htmlStr);
}