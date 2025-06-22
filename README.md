# Disaster Response Coordination Platform

This is a full-stack application designed to coordinate disaster response efforts. It provides real-time information about disasters, resource availability, and social media reports, all visualized on an interactive map.

## Features

*   **Real-time Disaster Tracking**: View active disasters on a map.
*   **Resource Management**: Locate and identify available resources like shelters, medical supplies, and food.
*   **Live Social Media Feeds**: Aggregate relevant posts from social media using keywords.
*   **Official Updates**: Scrape and display official updates from relief organizations.
*   **Geospatial Analysis**: Automatically extract locations from text and geocode them.
*   **WebSocket Integration**: Pushes real-time updates to all connected clients.

## Technology Stack

*   **Backend**: Java, Spring Boot, Spring WebFlux, Spring Data JPA, WebSocket, PostgreSQL with PostGIS.
*   **Frontend**: React, TypeScript, Vite, Mapbox GL JS, Tailwind CSS, Recoil.
*   **Database**: Supabase (PostgreSQL).
*   **External APIs**: Google Gemini, Mapbox Geocoding.

## Project Structure

```
/
├── backend/        # Spring Boot application
└── frontend/       # React frontend application
```

## Setup and Installation

### Prerequisites

*   Java 17 or later
*   Maven 3.8 or later
*   Node.js 18 or later
*   A Supabase account (for the PostgreSQL database)
*   API keys for Google Gemini and Mapbox.

### Backend Setup

1.  **Navigate to the backend directory:**
    ```bash
    cd backend/disaster
    ```

2.  **Create `application.properties`:**
    Duplicate the example file. This file is git-ignored and will store your secrets.
    ```bash
    cp src/main/resources/application.properties.example src/main/resources/application.properties
    ```

3.  **Configure Secrets:**
    Open `src/main/resources/application.properties` and fill in the required values:
    *   Your Supabase database credentials for `spring.datasource.*`.
    *   Your Google Gemini API key for `gemini.api.key`.
    *   Your Mapbox API key for `mapbox.api.key`.
    *   Your Twitter API credentials (if you implement the live feed).

4.  **Build and Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    The backend will start on `http://localhost:8080`.

### Frontend Setup

1.  **Navigate to the frontend directory:**
    ```bash
    cd ../../frontend
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    ```

3.  **Create `.env.local` file:**
    You need to add your Mapbox **public** key to an environment variable for the map component.
    Create a file named `.env.local` in the `frontend` directory and add the following:
    ```
    VITE_MAPBOX_TOKEN=your_mapbox_public_api_key
    ```

4.  **Run the development server:**
    ```bash
    npm run dev
    ```
    The frontend will start on `http://localhost:5173` (or another port if 5173 is busy).

## Usage

*   Open your browser to the frontend URL to view the application.
*   The backend includes a `DataInitializer` that populates the database with sample data on the first run, so you should see disasters and resources on the map immediately. 