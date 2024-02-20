
//Crear sistema que permita registrar todas las escuelas 
var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
                    osmAttrib = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
                    osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib});

            var map = L.map('map').setView([-4.036, -79.201], 15);

            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);
            //latitud, longitud
            
            L.marker([-4.045, -79.2015]).addTo(map)
                    .bindPopup('1.')
                    .openPopup();
            /*
            L.marker([51.5, -0.06]).addTo(map)
                    .bindPopup('2.')
                    .openPopup();*/