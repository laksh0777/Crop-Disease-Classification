public class Crime {
    private double latitude;
    private double longitude;
    private String description;

    // Default constructor for Gson (required for deserialization)
    public Crime() {}

    public Crime(double latitude, double longitude, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    // Getters and Setters (Required for Gson and accessing data)
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    @Override
    public String toString() {
        return "Crime [Lat=" + String.format("%.4f", latitude) + ", Lng=" + String.format("%.4f", longitude) + "]";
    }
}