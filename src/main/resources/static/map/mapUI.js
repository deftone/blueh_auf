      /* OSM & OL example code provided by https://mediarealm.com.au/ */
      /*    https://mediarealm.com.au/articles/openstreetmap-openlayers-map-markers/ */
      /*    https://openlayers.org/download/ */
//      https://openlayers.org/en/latest/examples/icon.html
//todo: das auch  nur in eine js datei? macht ja wirklich das selbe, nur etwas mehr...

var map;
//center for map, center of rossdorf
var mapLat = 49.86034;
var mapLng = 8.77216;
var mapDefaultZoom = 14.6;
var list;
var showNewLocation;
var address;
var coordinatesString;

//die parameter muessen scheinbar gar nicht uebergeben werden??
function initialize_map(list, showNewLocation, coordinatesString, address, errorMsg) {
  map = new ol.Map({
    target: "map",
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM({
                  url: "https://a.tile.openstreetmap.org/{z}/{x}/{y}.png"
            })
        })
    ],
    view: new ol.View({
        center: ol.proj.fromLonLat([mapLng, mapLat]),
        zoom: mapDefaultZoom
    })
  });


  if (errorMsg!=null){
    console.log(errorMsg);
    //falls koordinaten falsch sind: stehen lassen und fehlermeldung anzeigen
    var errorDiv = document.getElementById("errormsg");
    errorDiv.innerHTML =errorMsg;
    errorDiv.style.display="inline";

  } else if (showNewLocation!=null) {
  console.log(showNewLocation)
    var saveButton = document.getElementById("saveButton");
    saveButton.style.display="inline";
    var messageDiv = document.getElementById("msg");
    messageDiv.innerHTML ="Soll der Punkt gespeichert werden?";
    messageDiv.style.display="inline";
  }

  addPoints(list, showNewLocation);
}

function addPoints(list, showNewLocation){
    console.log(list.length);
    console.log(showNewLocation);

    for(var i in list) {
        add_map_point(list[i].latitude, list[i].longitude);
    }

    if (showNewLocation != null){
      // neuer punkt soll in anderer farbe hinzugefuegt werden
      add_map_point_blue(showNewLocation.latitude, showNewLocation.longitude);
      //und koordinaten wieder in die boxen schreiben
      document.getElementById("lat").value = showNewLocation.latitude;
      document.getElementById("lon").value = showNewLocation.longitude;
    }

    // auch die google maps copy & paste coordinaten wieder reinschreiben, falls so eingetragen
    if (coordinatesString != null){
      document.getElementById("coordinatesInput").value = coordinatesString;
    }

    // und auch die adresse (falls sie eingegeben wurde)
    if (address != null){
      document.getElementById("addressInput").value = address;
    }
}

function add_map_point(lat, lng) {
  var vectorLayer = new ol.layer.Vector({
    source:new ol.source.Vector({
      features: [new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform([parseFloat(lng), parseFloat(lat)], 'EPSG:4326', 'EPSG:3857')),
        })]
    }),
    style: new ol.style.Style({
      image: new ol.style.Icon({
        anchor: [0.5, 0.5],
        anchorXUnits: "fraction",
        anchorYUnits: "fraction",
        src: "map/roterKreis.png"
      })
    })
  });
  map.addLayer(vectorLayer);
}

function add_map_point_blue(lat, lng) {
  var vectorLayer = new ol.layer.Vector({
    source:new ol.source.Vector({
      features: [new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform([parseFloat(lng), parseFloat(lat)], 'EPSG:4326', 'EPSG:3857')),
        })]
    }),
    style: new ol.style.Style({
      image: new ol.style.Icon({
        anchor: [0.5, 0.5],
        anchorXUnits: "fraction",
        anchorYUnits: "fraction",
        src: "map/blauerKreis.png"
      })
    })
  });
  map.addLayer(vectorLayer);
}