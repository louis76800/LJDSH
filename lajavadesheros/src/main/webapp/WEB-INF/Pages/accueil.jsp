<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include  file="Parties/debutscript.jsp" %>
<link rel="stylesheet" href="style.css">
<link href="https://api.mapbox.com/mapbox-gl-js/v2.6.1/mapbox-gl.css" rel="stylesheet">
<script src="https://api.mapbox.com/mapbox-gl-js/v2.6.1/mapbox-gl.js"></script>
    <body>
        <div class="contenu margin_top">
            <div class="margin_padding">
                <h2 class="margin_top titre ">Page d'accueil</h2>
                <div id="menu">
                    <input id="satellite-v9" type="radio" name="rtoggle" value="satellite">
                    <!-- See a list of Mapbox-hosted public styles at -->
                    <!-- https://docs.mapbox.com/api/maps/styles/#mapbox-styles -->
                    <label for="satellite-v9">satellite</label>
                    <br>
                    <input id="light-v10" type="radio" name="rtoggle" value="light">
                    <label for="light-v10">light</label>
                    <br>
                    <input id="dark-v10" type="radio" name="rtoggle" value="dark">
                    <label for="dark-v10">dark</label>
                    <br>
                    <input id="streets-v11" type="radio" name="rtoggle" value="streets"  checked="checked">
                    <label for="streets-v11">streets</label>
                    <br>
                    <input id="outdoors-v11" type="radio" name="rtoggle" value="outdoors">
                    <label for="outdoors-v11">outdoors</label>
                    <div id="map"></div>
                </div>
            </div>
        </div>
    </body>
</html>
<script>
    let i = 0;
    let monument = [];

    if (navigator.geolocation)
    { // si la navigation est activée
        navigator.geolocation.getCurrentPosition(function(position)
        {
            var lat = position.coords.latitude;
            var long = position.coords.longitude;
            mapboxgl.accessToken = 'pk.eyJ1IjoibG91aXM3NjgwMCIsImEiOiJja3o2cGxvazMwZHozMm9uOWR4NXBhM2JzIn0.PbRGyj2Z42tgT_P78MB_aw';
            const map = new mapboxgl.Map({
                container: 'map', // container ID
                style: 'mapbox://styles/mapbox/streets-v11', // style URL
                center: [long,lat], // starting position [lng, lat]
                zoom: 10 // starting zoom
            });
            const layerList = document.getElementById('menu');
            const inputs = layerList.getElementsByTagName('input');
            for (const input of inputs) {
                input.onclick = (layer) => {
                    const layerId = layer.target.id;
                    map.setStyle('mapbox://styles/mapbox/' + layerId);
                };
            }
            //affichage de la position sur la carte avec un point bleu
            let marker_position = new mapboxgl.Marker({ color: 'blue' })
                .setLngLat([long,lat])
                .addTo(map);
            //affiche des point sur la carte en fonction de se qu'il y a dans la bdd avec un point rouge
            <c:forEach var="position" items="${ positions }">
            i++;
            monument = [<c:out value="${ position.longitude }"/>,<c:out value="${ position.lattitude }"/>];
            new mapboxgl.Marker({ color: 'red' })
                .setLngLat(monument)
                .addTo(map);
            </c:forEach>
            map.addControl(new mapboxgl.FullscreenControl());

        }, (error) => {
            // si la localisation est désactivée on affiche seulement la carte avec les points de a bdd en rouge
                var lat = 1;
                var long = 1;
                mapboxgl.accessToken = 'pk.eyJ1IjoibG91aXM3NjgwMCIsImEiOiJja3o2cGxvazMwZHozMm9uOWR4NXBhM2JzIn0.PbRGyj2Z42tgT_P78MB_aw';
                const map = new mapboxgl.Map({
                    container: 'map', // container ID
                    style: 'mapbox://styles/mapbox/streets-v11', // style URL
                    center: [lat,long], // starting position [lng, lat]
                    zoom: 1 // starting zoom
                });
                const layerList = document.getElementById('menu');
                const inputs = layerList.getElementsByTagName('input');
                for (const input of inputs) {
                    input.onclick = (layer) => {
                        const layerId = layer.target.id;
                        map.setStyle('mapbox://styles/mapbox/' + layerId);
                    };
                }
                <c:forEach var="position" items="${ positions }">
                i++;
                monument = [<c:out value="${ position.longitude }"/>,<c:out value="${ position.lattitude }"/>];
                new mapboxgl.Marker({ color: 'red' })
                    .setLngLat(monument)
                    .addTo(map);
                </c:forEach>
            //bouton sur la carte: fullscreen
            map.addControl(new mapboxgl.FullscreenControl());
            }
        );
    }else{
        alert("Latitude : " + lat + ", longitude : " + long);
    }
</script>
