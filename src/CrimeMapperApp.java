import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class CrimeMapperApp extends JFrame {

    // IMPORTANT: Make sure your Spring Boot backend is running on this URL
    private final String API_URL = "http://localhost:8080/api/crimes";
    private final Gson gson = new Gson();

    private JLabel totalCrimesLabel;
    private JLabel dangerZonesLabel;
    private JPanel mapPanel;
    
    private static final double INITIAL_LAT = 12.9716; 
    private static final double INITIAL_LNG = 77.5946;

    public CrimeMapperApp() {
        setTitle("🚨 Crime Mapper (Java Swing)");
        // Setting up the main window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        // Define colors for the dark theme
        Color darkBackground = new Color(22, 33, 62); 
        Color primaryColor = new Color(233, 69, 96);
        Color secondaryColor = new Color(15, 52, 96);

        // --- 1. Sidebar Panel ---
        JPanel sidebar = createSidebar(darkBackground, primaryColor, secondaryColor);
        add(sidebar, BorderLayout.WEST);

        // --- 2. Map Panel (Placeholder for a map component) ---
        mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());
        mapPanel.setBackground(new Color(26, 33, 46));
        
        JLabel mapPlaceholder = new JLabel("<html><h2 style='color:#E94560;'>Map Visualization Placeholder</h2><p style='color:#FFFFFF;'>Click anywhere on this panel to report a crime at the center (Simulated).</p></html>", SwingConstants.CENTER);
        mapPlaceholder.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        mapPanel.add(mapPlaceholder, BorderLayout.CENTER);
        
        // Simulates the map click to report a crime
        mapPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Uses the pre-defined center coordinates for simplicity
                reportCrime(INITIAL_LAT, INITIAL_LNG); 
            }
        });

        add(mapPanel, BorderLayout.CENTER);

        // Load initial data when the application starts
        refreshData();
    }
    
    // --- Helper UI Methods ---
    
    private JPanel createSidebar(Color darkBackground, Color primaryColor, Color secondaryColor) {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, getHeight()));
        sidebar.setBackground(darkBackground);
        sidebar.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        
        // Title
        JLabel title = new JLabel("🚨 Crime Mapper", SwingConstants.CENTER);
        title.setForeground(primaryColor);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 20, 10);
        sidebar.add(title, gbc);
        
        // Stats Panel
        JPanel statsPanel = createStatsPanel(primaryColor, secondaryColor);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        sidebar.add(statsPanel, gbc);
        
        // Instructions Panel
        gbc.gridy = 2; sidebar.add(createInstructionsPanel(primaryColor), gbc);
        
        // Controls Panel
        gbc.gridy = 3; sidebar.add(createControlsPanel(primaryColor, secondaryColor), gbc);
        
        // Legend Panel
        gbc.gridy = 4; sidebar.add(createLegendPanel(primaryColor, secondaryColor), gbc);

        // Filler to push elements to the top
        gbc.weighty = 1.0;
        gbc.gridy = 5;
        sidebar.add(new JPanel() {{ setOpaque(false); }}, gbc);

        return sidebar;
    }
    
    private JPanel createStatsPanel(Color primaryColor, Color secondaryColor) {
        JPanel statsPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        statsPanel.setBackground(secondaryColor);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        totalCrimesLabel = new JLabel("Total Reports: 0", SwingConstants.LEFT);
        dangerZonesLabel = new JLabel("Danger Zones: 0", SwingConstants.LEFT);
        
        totalCrimesLabel.setForeground(primaryColor);
        dangerZonesLabel.setForeground(primaryColor);
        totalCrimesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dangerZonesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        statsPanel.add(totalCrimesLabel);
        statsPanel.add(dangerZonesLabel);
        return statsPanel;
    }
    
    private JPanel createInstructionsPanel(Color primaryColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(233, 69, 96, 25)); // Transparent red
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel title = new JLabel("📍 How to Use", SwingConstants.LEFT);
        title.setForeground(primaryColor);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JTextArea instructions = new JTextArea("Click on the map area to report a crime (simulated). Use the buttons to refresh data and toggle heatmap (simulated).");
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setBackground(panel.getBackground());
        instructions.setForeground(Color.WHITE);
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        panel.add(title, BorderLayout.NORTH);
        panel.add(instructions, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createControlsPanel(Color primaryColor, Color secondaryColor) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 8));
        panel.setOpaque(false);

        JButton toggleHeatmap = createButton("Toggle Heatmap (Simulated)", primaryColor);
        // Note: In a full implementation, this button would toggle a JPanel overlay visibility
        
        JButton refreshData = createButton("Refresh Data", secondaryColor);
        refreshData.addActionListener(e -> refreshData());

        JButton exportMap = createButton("📷 Export Map (Simulated)", secondaryColor);
        // Note: This button would typically involve rendering the map to an image

        JButton clearAllCrimes = createButton("Clear All Data (Simulated)", secondaryColor);
        clearAllCrimes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Clear all functionality requires a DELETE endpoint.", "Info", JOptionPane.INFORMATION_MESSAGE));

        panel.add(toggleHeatmap);
        panel.add(refreshData);
        panel.add(exportMap);
        panel.add(clearAllCrimes);

        return panel;
    }
    
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }
    
    private JPanel createLegendPanel(Color primaryColor, Color secondaryColor) {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(secondaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Legend");
        title.setForeground(primaryColor);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(title);
        
        panel.add(createLegendItem(new Color(0, 255, 0), "Safe Area"));
        panel.add(createLegendItem(new Color(255, 255, 0), "Medium Risk"));
        panel.add(createLegendItem(new Color(255, 0, 0), "High Risk"));
        
        return panel;
    }
    
    private JPanel createLegendItem(Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT));
        item.setOpaque(false);
        
        JLabel colorSquare = new JLabel(" ");
        colorSquare.setPreferredSize(new Dimension(30, 20));
        colorSquare.setBackground(color);
        colorSquare.setOpaque(true);
        colorSquare.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        item.add(colorSquare);
        item.add(label);
        return item;
    }

    // --- API Logic Methods ---

    /**
     * Fetches crime data asynchronously using SwingWorker.
     */
    public void refreshData() {
        // SwingWorker keeps the UI responsive while making network calls
        new SwingWorker<List<Crime>, Void>() {
            @Override
            protected List<Crime> doInBackground() throws Exception {
                // Network operations run in a background thread
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed to fetch crimes: HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                Type crimeListType = new TypeToken<List<Crime>>() {}.getType();
                List<Crime> crimes = gson.fromJson(br, crimeListType);
                
                conn.disconnect();
                return crimes;
            }

            @Override
            protected void done() {
                // UI updates run back in the Event Dispatch Thread (EDT)
                try {
                    List<Crime> allCrimes = get();
                    // Update Stats
                    totalCrimesLabel.setText("Total Reports: " + allCrimes.size());
                    // Fetch Danger Zones (separate async call to keep things fast)
                    fetchDangerZonesCount();
                    
                    // In a real app, you would now update your map visualization layer here
                    
                } catch (InterruptedException | ExecutionException e) {
                    // Handle API failure
                    JOptionPane.showMessageDialog(CrimeMapperApp.this, "Error fetching data. Check your backend server status. Details: " + e.getCause().getMessage(), "API Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    /**
     * Reports a new crime to the backend API asynchronously.
     */
    public void reportCrime(double lat, double lng) {
        Crime newCrime = new Crime(lat, lng, "Crime reported via Swing app");
        
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Network operations run in a background thread
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true); // Indicates this is a POST request

                String jsonInputString = gson.toJson(newCrime);
                
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);			
                }

                if (conn.getResponseCode() < 200 || conn.getResponseCode() >= 300) {
                    throw new RuntimeException("Failed to report crime: HTTP error code : " + conn.getResponseCode());
                }

                conn.disconnect();
                return null;
            }

            @Override
            protected void done() {
                // UI updates run back in the EDT
                try {
                    get(); // This checks for exceptions from doInBackground
                    JOptionPane.showMessageDialog(CrimeMapperApp.this, "Crime reported successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshData(); // Refresh data immediately after reporting
                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(CrimeMapperApp.this, "Error reporting crime. Make sure the server is running. Details: " + e.getCause().getMessage(), "API Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    // Helper to fetch danger zones count
    private void fetchDangerZonesCount() {
         new SwingWorker<Integer, Void>() {
            // Logic similar to refreshData, just for the /danger-zones endpoint
            @Override
            protected Integer doInBackground() throws Exception {
                URL url = new URL(API_URL + "/danger-zones");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed to fetch danger zones: HTTP error code : " + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                // Assuming the backend returns a JSON array of danger zones
                Type dangerZoneListType = new TypeToken<List<Object>>() {}.getType(); 
                List<Object> dangerZones = gson.fromJson(br, dangerZoneListType);
                
                conn.disconnect();
                return dangerZones.size();
            }

            @Override
            protected void done() {
                try {
                    int count = get();
                    dangerZonesLabel.setText("Danger Zones: " + count);
                } catch (InterruptedException | ExecutionException e) {
                    dangerZonesLabel.setText("Danger Zones: Error");
                }
            }
        }.execute();
    }
    
    // Main method to start the application
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures the Swing UI is initialized safely on the EDT
        SwingUtilities.invokeLater(() -> {
            new CrimeMapperApp().setVisible(true);
        });
    }
}