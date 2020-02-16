import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit
} from '@angular/core';
import {load} from 'google-maps';
import LatLng = google.maps.LatLng;
import {MessageController} from "../controller/messagecontroller";
import {mark} from '@angular/compiler-cli/src/ngtsc/perf/src/clock';
import Marker = google.maps.Marker;
@Component({
  selector: 'app-mainpage',
  templateUrl: './mainpage.component.html',
  styleUrls: ['./mainpage.component.css']
})
export class MainpageComponent implements OnInit {

  //From html
  @ViewChild('map', {static: true}) mapElement: ElementRef;
  map: google.maps.Map;
  mapOptions: google.maps.MapOptions;
  location: any;
  latitude: any;
  longitude: any;
  currentLocationMarker: any;
  infoWindow: google.maps.InfoWindow;
  radius: google.maps.Circle;

  constructor(public controller: MessageController) {
  }

  ngOnInit() {
    // standard mapoptions for setting the map
    this.controller.getMessage().subscribe(
      (messages: any) => {
        console.log("frontend messages:", messages);
        for (let msg of messages) {
          this.omerAutoAddMessagesAtStart(msg);
        }
      }
    );

    this.mapOptions = {
      center: new google.maps.LatLng(52.379189, 4.899431),
      zoom: 10,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      streetViewControl: false,
      fullscreenControl: false,
      zoomControl: false,
      mapTypeControl: false,

      styles: [
        {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#1d2c4d"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#8ec3b9"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#1a3646"
            }
          ]
        },
        {
          "featureType": "administrative.country",
          "elementType": "geometry.stroke",
          "stylers": [
            {
              "color": "#4b6878"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#64779e"
            }
          ]
        },
        {
          "featureType": "administrative.province",
          "elementType": "geometry.stroke",
          "stylers": [
            {
              "color": "#4b6878"
            }
          ]
        },
        {
          "featureType": "landscape.man_made",
          "elementType": "geometry.stroke",
          "stylers": [
            {
              "color": "#334e87"
            }
          ]
        },
        {
          "featureType": "landscape.natural",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#023e58"
            }
          ]
        },
        {
          "featureType": "poi",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#283d6a"
            }
          ]
        },
        {
          "featureType": "poi",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#6f9ba5"
            }
          ]
        },
        {
          "featureType": "poi",
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#1d2c4d"
            }
          ]
        },
        {
          "featureType": "poi.park",
          "elementType": "geometry.fill",
          "stylers": [
            {
              "color": "#023e58"
            }
          ]
        },
        {
          "featureType": "poi.park",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#3C7680"
            }
          ]
        },
        {
          "featureType": "road",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#304a7d"
            }
          ]
        },
        {
          "featureType": "road",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#98a5be"
            }
          ]
        },
        {
          "featureType": 'road',
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#1d2c4d"
            }
          ]
        },
        {
          "featureType": "road.highway",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#2c6675"
            }
          ]
        },
        {
          "featureType": "road.highway",
          "elementType": "geometry.stroke",
          "stylers": [
            {
              "color": "#255763"
            }
          ]
        },
        {
          "featureType": "road.highway",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#b0d5ce"
            }
          ]
        },
        {
          "featureType": "road.highway",
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#023e58"
            }
          ]
        },

        {
          "featureType": "road.highway",
          "elementType": "labels",
          "stylers": [
            {
              visibility: "off"
            }
          ]
        },
        {
          "featureType": "road.arterial",
          "elementType": "labels",
          "stylers": [
            {
              visibility: "off"
            }
          ]
        },


        {
          "featureType": "transit",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#98a5be"
            }
          ]
        },
        {
          "featureType": "transit",
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#1d2c4d"
            }
          ]
        },
        {
          "featureType": "transit.line",
          "elementType": "geometry.fill",
          "stylers": [
            {
              "color": "#283d6a"
            }
          ]
        },
        {
          "featureType": "transit.station",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#3a4762"
            }
          ]
        },
        {
          "featureType": "water",
          "elementType": "geometry",
          "stylers": [
            {
              "color": "#0e1626"
            }
          ]
        },
        {
          "featureType": "water",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#4e6d70"
            }
          ]
        }
      ]
    };

    // init map
    this.map = new google.maps.Map(this.mapElement.nativeElement, this.mapOptions);

    this.watchLocation();

    /**
     * Causing errors in the console.
     */
    /*var that = this;
    google.maps.event.addListener(this.map, "click", function(event) {
      that.infoWindow.close();
    });*/
  }

  omerAutoAddMessagesAtStart(msg: any) {
    //console.log("auto add", msg.longitude, msg.latitude);

    var msgLoc = new google.maps.LatLng(msg.longitude, msg.latitude);
    var ownLoc = new google.maps.LatLng(this.location.coords.latitude, this.location.coords.longitude);
    var distance = google.maps.geometry.spherical.computeDistanceBetween(msgLoc, ownLoc);

    if (distance < 10000) {
      const marker = new google.maps.Marker({
        /**
         * Het klopt dat ik als 1e longitude en als 2e latitude want
         * als je het op de juiste manier zet, kom je ergens in de
         * oosterse zee van afrika uit. dusssss ja....
         */
        position: new google.maps.LatLng(msg.longitude, msg.latitude),
        map: this.map,
        clickable: true,
        icon: {
          url: "https://image.flaticon.com/icons/svg/1381/1381552.svg",
          scaledSize: new google.maps.Size(40, 48)
        }
      });
      marker.set("id", msg.id);

      setInterval(function () {
        marker.setAnimation(google.maps.Animation.BOUNCE);
        setTimeout(function () {
          marker.setAnimation(null);
        }, 10);
      }, 5000);

      google.maps.event.addListener(marker, 'click', (evt) => this.openMessage(marker, msg));
    } else {
      const marker1 = new google.maps.Marker({
        /**
         * Het klopt dat ik als 1e longitude en als 2e latitude want
         * als je het op de juiste manier zet, kom je ergens in de
         * oosterse zee van afrika uit. dusssss ja....
         */
        position: new google.maps.LatLng(msg.longitude, msg.latitude),
        map: this.map,
        clickable: false,
        icon: {
          url: "https://media.discordapp.net/attachments/625774158311194662/663699246327005192/OutOfRangeMarker.png",
          scaledSize: new google.maps.Size(40, 48),
        }
      });
      marker1.set("id", msg.id);

      google.maps.event.addListener(marker1, 'click', (evt) => this.openMessage(marker1, msg));
    }
  }

  openMessage(marker: Marker, msg: any) {
    const infowindow = new google.maps.InfoWindow({
      content: '<h4>' + msg.title +  '</h4><p>' + msg.message + '</p>'
    });
    infowindow.open(this.map, marker);
    console.log("Clicked on Message");
    this.controller.updateMessage(msg).subscribe(
      (result) => {
        console.log("update message result:", result);
      }
    )
  }

  addMarkerOnCurrentLocation() {
    console.log("location:", this.location)
    this.latitude = this.location.coords.latitude;
    this.longitude = this.location.coords.longitude;

    // Message marker
    const marker = new google.maps.Marker({
      //position: new google.maps.LatLng(this.location.coords.latitude, this.location.coords.longitude),
      position: new google.maps.LatLng(this.latitude, this.longitude),
      map: this.map,
      clickable: true,
      icon: {
        url: "https://image.flaticon.com/icons/svg/1381/1381552.svg",
        scaledSize: new google.maps.Size(40, 48)
      }
    });

    console.log("Current marker:", marker);

    const DOMtitle = <HTMLInputElement>document.getElementsByName('title')[0];
    const DOMtext = <HTMLInputElement>document.getElementsByName('text')[0];

    let title = DOMtitle.value;
    title = title.replace('&', '&amp;');
    title = title.replace('<', '&#60;');
    title = title.replace('>', '&#62;');
    title = title.replace('\n', '');

    let text = DOMtext.value;
    text = text.replace('&', '&amp;');
    text = text.replace('<', '&#60;');
    text = text.replace('>', '&#62;');
    text = text.replace('\n', '<br/>');
    // setting up infowindow
    const infowindow = new google.maps.InfoWindow({
      content: '<h4>' + title + '</h4><p>' + text + '</p>'
    });

    setInterval(function () {
      marker.setAnimation(google.maps.Animation.BOUNCE);
      setTimeout(function () {
        marker.setAnimation(null);
      }, 10);
    }, 5000);

    // event for opening the infowindow when clicking on the marker
    var that = this;
    let body: any = {
      "marker": marker,
      "controller": this.controller
    };

    google.maps.event.addListener(marker, 'click', function () {
      if (that.infoWindow != undefined) {
        that.infoWindow.close();
      }
      infowindow.open(this.map, body.marker);
      that.infoWindow = infowindow;
    });

    this.controller.saveMessage(title, text, this.latitude, this.longitude)

    this.closePopup();

    setTimeout(function () {
      marker.setAnimation(google.maps.Animation.DROP);
      setTimeout(function () {
        marker.setAnimation(null);
      }, 10);
    }, 10)
  }

  // arePointsNear(point1, point2) {
  //   var sw = new google.maps.LatLng(point2.lat() - 0.005, point2.lng() - 0.005);
  //   var ne = new google.maps.LatLng(point2.lat() + 0.005, point2.lng() + 0.005);
  //   var bounds = new google.maps.LatLngBounds(sw, ne);
  //   if (bounds.contains (point1))
  //     return true;

  //
  //   return false;
  // }

  showPopup() {
    const transparent = document.getElementsByClassName('transparent')[0];
    const popUp = document.getElementsByClassName('popup')[0];

    const title = <HTMLInputElement>document.getElementsByName('title')[0];
    const text = <HTMLInputElement>document.getElementsByName('text')[0];

    title.value = "";
    text.value = "";

    transparent.setAttribute("style", "display:visible;");
    popUp.setAttribute("style", "display:visible;");
  }

  closePopup() {
    const transparent = document.getElementsByClassName('transparent')[0];
    const popUp = document.getElementsByClassName('popup')[0];
    transparent.setAttribute("style", "display:none;");
    popUp.setAttribute("style", "display:none;");
  }

  watchLocation = () => {


    // get geolocation
    navigator.geolocation.watchPosition((position) => {
      this.location = position;

      const location = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

      if (!location) {
        console.log('error no location');
      }

      if (this.currentLocationMarker != undefined) {
        console.log(this.currentLocationMarker);
        this.currentLocationMarker.setPosition(location);
        this.radius.setCenter(location);
        return;
      }

      // setting up marker
      this.currentLocationMarker = new google.maps.Marker({
        position: location,
        map: this.map,
        clickable: false,
        icon: {
          url: 'https://image.flaticon.com/icons/svg/149/149060.svg',
          scaledSize: new google.maps.Size(40, 48)
        }
      });

      this.radius = new google.maps.Circle({
        map: this.map,
        center: location,
        radius: 10000,
        strokeColor: '#ADD8E6',
        strokeOpacity: 0.28,
        strokeWeight: 2,
        fillColor: '#ADD8E6',
        fillOpacity: 0.28,
      });


    }, (error) => {
      console.log(error);
    });
  }
}




