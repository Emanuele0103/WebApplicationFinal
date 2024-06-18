let map;
let marker;

function initMap() {
    const defaultLocation = { lat: 45.464664, lng: 9.188540 }; // Milano, Italia
    map = new google.maps.Map(document.getElementById("map"), {
        center: defaultLocation,
        zoom: 12,
    });

    map.addListener("click", (event) => {
        placeMarker(event.latLng);
    });
}

function placeMarker(location) {
    if (marker) {
        marker.setPosition(location);
    } else {
        marker = new google.maps.Marker({
            position: location,
            map: map,
        });
    }

    document.getElementById("location").value = `${location.lat()},${location.lng()}`;
}
