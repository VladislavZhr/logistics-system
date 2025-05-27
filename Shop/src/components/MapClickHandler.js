import { useMapEvents } from 'react-leaflet';

function MapClickHandler({ onClick }) {
    useMapEvents({
        click(e) {
            if (onClick) {
                const { lat, lng } = e.latlng;
                onClick({ lat: lat.toFixed(6), lng: lng.toFixed(6) });//d
            }
        },
    });

    return null;
}

export default MapClickHandler;
