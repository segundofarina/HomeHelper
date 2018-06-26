function initializeMap(selector) {
    // Map Center
    var myLatLng = new google.maps.LatLng(-34.572902, -58.423161);// calcularlas con las initialCoords
    // General Options
    var mapOptions = {
        zoom: 14,
        center: myLatLng,
        mapTypeId: google.maps.MapTypeId.RoadMap
    };
    var map = new google.maps.Map(document.getElementById(selector), mapOptions);
    // Polygon Coordinates

    return map;
}

//Display Coordinates below map
function getPolygonCoords() {
    var len = myPolygon.getPath().getLength();
    var htmlStr = "";
    for (var i = 0; i < len; i++) {
        htmlStr += myPolygon.getPath().getAt(i).toUrlValue(5) + ";";
    }

    $("#coordsStr").val(htmlStr);
}

function addPolygon(map, initialCoords, editable) {
    var polygonCoords = [];
    for(var i = 0; i < initialCoords.length; i++) {
        var coord = initialCoords[i].split(",");
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
        draggable: editable,
        editable: editable,
        strokeColor: '#FF0000',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#FF0000',
        fillOpacity: 0.35
    });

    myPolygon.setMap(map);

    if(editable) {
        //google.maps.event.addListener(myPolygon, "dragend", getPolygonCoords);
        google.maps.event.addListener(myPolygon.getPath(), "insert_at", getPolygonCoords);
        //google.maps.event.addListener(myPolygon.getPath(), "remove_at", getPolygonCoords);
        google.maps.event.addListener(myPolygon.getPath(), "set_at", getPolygonCoords);
    }

    getPolygonCoords();

    return myPolygon;
}