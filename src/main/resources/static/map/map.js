      /* OSM & OL example code provided by https://mediarealm.com.au/ */
      /*    https://mediarealm.com.au/articles/openstreetmap-openlayers-map-markers/ */
      /*    https://openlayers.org/download/ */
//      https://openlayers.org/en/latest/examples/icon.html

var map;
//center for map, center of rossdorf
var mapLat = 49.86034;
var mapLng = 8.77216;
var mapDefaultZoom = 14.6;
var list;
var showNewLocation;

function initialize_map(list, showNewLocation) {
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

  var messageDiv = document.getElementById("msg");
  if (errorMsg!=null){
    console.log(errorMsg);
    //falls koordinaten falsch sind: stehen lassen und fehlermeldung anzeigen

    messageDiv.innerHTML =errorMsg;
  } else if (showNewLocation!=null) {
    var saveButton = document.getElementById("saveButton");
    saveButton.style.display="inline";
    messageDiv.innerHTML ="soll der Punkt gespeichert werden?";
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