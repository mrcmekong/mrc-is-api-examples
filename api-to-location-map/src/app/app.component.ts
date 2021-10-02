import {Component, OnInit} from '@angular/core';
import {circleMarker, FeatureGroup, latLng, Layer, tileLayer} from "leaflet";
import {StationService} from "./station.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  options = {
    layers: [
      tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {maxZoom: 18, attribution: '...'})
    ],
    zoom: 6,
    center: latLng(14, 103)
  };
  map: L.Map | undefined;
  layers: FeatureGroup<Layer>[] = [];

  constructor(
    private stationService: StationService,
  ) {
  }

  ngOnInit(): void {
    this.stationService.fetchLocations().subscribe((locations) => {
      const markers = locations
        .filter((l) =>l.latitude !== 0 && l.longitude !== 0)
        .map((l) => {
          const c = circleMarker([l.latitude, l.longitude], {})
          c.bindPopup(l.locationIdentifier)
          return c
        })

      this.layers = [new FeatureGroup(markers)]

      this.map?.flyToBounds(this.layers[0].getBounds())


    })
  }


  onMapReady(map: L.Map) {
    this.map = map
  }
}
