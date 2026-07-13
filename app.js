const API_URL = 'http://localhost:8080/api/crimes';

// Initialize map (centered on a fictional city)
const map = L.map('map').setView([12.9716, 77.5946], 13);

// Add tile layer
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

let heatmapLayer = null;
let markers = [];
let showHeatmap = true;

// Add click event to report crime
map.on('click', async function(e) {
    const { lat, lng } = e.latlng;
    
    const crime = {
        latitude: lat,
        longitude: lng,
        description: 'Crime reported via map click'
    };
    
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(crime)
        });
        
        if (response.ok) {
            addMarker(lat, lng);
            refreshData();
        }
    } catch (error) {
        console.error('Error reporting crime:', error);
        alert('Failed to report crime. Make sure the server is running.');
    }
});

function addMarker(lat, lng) {
    const marker = L.circleMarker([lat, lng], {
        radius: 8,
        fillColor: '#e94560',
        color: '#fff',
        weight: 2,
        opacity: 1,
        fillOpacity: 0.8
    }).addTo(map);
    
    marker.bindPopup(`<b>Crime Reported</b><br>Lat: ${lat.toFixed(4)}<br>Lng: ${lng.toFixed(4)}`);
    markers.push(marker);
}

async function refreshData() {
    try {
        // Get all crimes
        const response = await fetch(API_URL);
        const crimes = await response.json();
        
        // Update stats
        document.getElementById('totalCrimes').textContent = crimes.length;
        
        // Clear existing markers
        markers.forEach(marker => map.removeLayer(marker));
        markers = [];
        
        // Add markers
        crimes.forEach(crime => {
            addMarker(crime.latitude, crime.longitude);
        });
        
        // Update heatmap
        updateHeatmap(crimes);
        
        // Get danger zones
        const dangerResponse = await fetch(`${API_URL}/danger-zones`);
        const dangerZones = await dangerResponse.json();
        document.getElementById('dangerZones').textContent = dangerZones.length;
        
    } catch (error) {
        console.error('Error refreshing data:', error);
    }
}

function updateHeatmap(crimes) {
    if (heatmapLayer) {
        map.removeLayer(heatmapLayer);
    }
    
    if (crimes.length > 0 && showHeatmap) {
        const heatData = crimes.map(crime => [
            crime.latitude,
            crime.longitude,
            0.8
        ]);
        
        heatmapLayer = L.heatLayer(heatData, {
            radius: 25,
            blur: 15,
            maxZoom: 17,
            max: 1.0,
            gradient: {
                0.0: 'green',
                0.5: 'yellow',
                1.0: 'red'
            }
        }).addTo(map);
    }
}

function toggleHeatmap() {
    showHeatmap = !showHeatmap;
    if (heatmapLayer) {
        if (showHeatmap) {
            map.addLayer(heatmapLayer);
        } else {
            map.removeLayer(heatmapLayer);
        }
    }
}

async function exportMap() {
    try {
        const mapElement = document.getElementById('map');
        const canvas = await html2canvas(mapElement, {
            useCORS: true,
            allowTaint: true
        });
        
        const link = document.createElement('a');
        link.download = `crime-map-${Date.now()}.png`;
        link.href = canvas.toDataURL();
        link.click();
    } catch (error) {
        console.error('Error exporting map:', error);
        alert('Failed to export map. Please try again.');
    }
}

async function clearAllCrimes() {
    if (confirm('Are you sure you want to clear all crime data?')) {
        // Note: You'd need to implement a DELETE endpoint in the backend
        alert('Clear all functionality requires a DELETE endpoint. For now, restart the application.');
    }
}

// Load initial data
refreshData();