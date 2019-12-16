import React from 'react'
import {Map, GoogleApiWrapper,Polygon} from 'google-maps-react'
import styles from './workingZone.module.css'
const MY_API_KEY = "AIzaSyBqSX1WHUw4OlgMDzYM40uSVPGkV06DR1I"


const adjustMap= (mapProps, map)=> {
    const {google, children} = mapProps;
    const bounds = new google.maps.LatLngBounds();
    // const coordenates = [{lat: -34.557176, lng: -58.430436},
    //     {lat: -34.575376, lng: -58.403839},
    //     {lat: -34.588696, lng: -58.431428}];
    const coordenates = children.props.paths    

  for (var i = 0; i < coordenates.length; i++) {
                bounds.extend(coordenates[i]);
            }
    // polygons.forEach(marker => {
    //   const {lat, lng} = marker.position;
  
    //   bounds.extend(new google.maps.LatLng(lat, lng));
    // });
  
    map.fitBounds(bounds);
    // map.panToBounds(bounds);
  }

const WorkingZone= (props)=>{
    const {google} = props;
    const maps = google.maps;


    google.maps.Polygon.prototype.getBounds = function() {
        var bounds = new google.maps.LatLngBounds();
        var paths = this.getPaths();
        var path;        
        for (var i = 0; i < paths.getLength(); i++) {
            path = paths.getAt(i);
            for (var ii = 0; ii < path.getLength(); ii++) {
                bounds.extend(path.getAt(ii));
            }
        }
        return bounds;
    }

    var bermudaTriangle = new maps.Polygon({paths: props.coordenates});
    let center = {lat: bermudaTriangle.getBounds().getCenter().lat(),
                lng:bermudaTriangle.getBounds().getCenter().lng()
            }
    return(
        <div className={styles.Container}>
            <Map google={props.google} zoom={10}
                style={{width: '100%', height: '100%',position:'relative'}}
                 initialCenter={center}
                 onReady={adjustMap}
                //initialCenter={props.coordenates[1]}
            >
            <Polygon
                paths={props.coordenates}
                strokeColor="#17b594"
                strokeOpacity={0.8}
                strokeWeight={2}
                fillColor="#26b99a"
                fillOpacity={0.35}
                 />
            </Map>
        </div>
    )
}


  export default GoogleApiWrapper({
    apiKey: (MY_API_KEY)
  })(WorkingZone)