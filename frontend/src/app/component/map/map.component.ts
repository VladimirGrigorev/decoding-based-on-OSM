import {Component, OnInit} from '@angular/core';
import * as L from 'leaflet';
import {SecurityService} from "../../service/security/security.service";
import {GeocodingService} from "../../service/geocoding/geocoding.service";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  map: any;
  mapOptions: any;
  name: string | undefined;
  latitude: number | undefined;
  longitude: number | undefined;

  constructor(
    private geocodingService: GeocodingService
  ) {
  }

  ngOnInit() {
    if (!navigator.geolocation) {
      console.log('location is not supported');
    }
    navigator.geolocation.getCurrentPosition((position) => {
      console.log(
        `lat: ${position.coords.latitude}, lon: ${position.coords.longitude}, pog: ${position.coords.accuracy}`
      );

      this.addSampleMarker(position.coords.latitude, position.coords.longitude, position.coords.accuracy);
    });

    this.watchPosition();
    this.initializeMapOptions();
  }

  watchPosition() {
    let desLat = 0;
    let desLon = 0;
    let id = navigator.geolocation.watchPosition(
      (position) => {
        console.log(
          `lat: ${position.coords.latitude}, lon: ${position.coords.longitude}, pog: ${position.coords.accuracy}`
        );
        if (position.coords.latitude === desLat && position.coords.longitude === desLon) {
          navigator.geolocation.clearWatch(id);
        }

        this.addSampleMarker(position.coords.latitude, position.coords.longitude, position.coords.accuracy);
      },
      (err) => {
        console.log(err);
      },
      {
        enableHighAccuracy: true,
        timeout: 5*60*1000,
        maximumAge: 0,
      }
    );
  }

  onMapReady(map: L.Map) {
    this.map = map;
  }

  private initializeMapOptions() {
    this.mapOptions = {
      center: L.latLng(51.65, 39.20),
      zoom: 12,
      layers: [
        L.tileLayer(
          'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
          {
            maxZoom: 18,
            attribution: 'Map data © OpenStreetMap contributors'
          })
      ],
    };
  }

  private addSampleMarker(lat: number, lon: number, acc: number) {
    //const marker = new L.Marker([51.6561, 39.20632])
    this.geocodingService.findUserLocation(lat, lon, acc).subscribe(res => {
      const marker = new L.Marker([res.latitude, res.longitude])
        .setIcon(
          L.icon({
            iconSize: [25, 41],
            iconAnchor: [13, 41],
            iconUrl: 'assets/marker-icon.png'
          }));
      marker.addTo(this.map);
      this.name = res.searchResult.properties.name;
      this.latitude = res.latitude;
      this.longitude = res.longitude;
    });

    const circle = L.circleMarker([lat, lon],
      {
        radius: 320
      }
    ).addTo(this.map);
  }

}
