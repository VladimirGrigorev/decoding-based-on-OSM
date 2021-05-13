import {Component, OnInit} from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  map: any;
  mapOptions: any;

  constructor() {
  }

  ngOnInit() {
    this.initializeMapOptions();
  }

  onMapReady(map: L.Map) {
    this.map = map;
    this.addSampleMarker();
  }

  private initializeMapOptions() {
    this.mapOptions = {
      center: L.latLng(51.6569, 39.2055),
      zoom: 18,
      layers: [
        L.tileLayer(
          'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
          {
            maxZoom: 24,
            attribution: 'Map data © OpenStreetMap contributors'
          })
      ],
    };
  }

  private addSampleMarker() {
    //const marker = new L.Marker([51.6569, 39.2055])
    //const marker = new L.Marker([51.6572286, 39.2049762])
    //const marker = new L.Marker([51.65748, 39.2055])
    const marker = new L.Marker([51.6561, 39.20632])
      .setIcon(
        L.icon({
          iconSize: [25, 41],
          iconAnchor: [13, 41],
          iconUrl: 'assets/marker-icon.png'
        }));
    marker.addTo(this.map);
    const circle = L.circleMarker([51.6569, 39.2055],
      {
        radius: 320
      }
    ).addTo(this.map);
  }

}
