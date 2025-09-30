-- Create a table for users. In a real app, this would have passwords, roles, etc.
-- For this demo, role can be 'CITIZEN', 'VOLUNTEER', 'OFFICIAL'.
create database janseva;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    points INT DEFAULT 0,
    role VARCHAR(50) NOT NULL DEFAULT 'CITIZEN'
);

-- Create the main table for complaints.
CREATE TABLE complaints (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    description TEXT,
    photo_url VARCHAR(255), -- Link to the uploaded image in cloud storage
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    status VARCHAR(50) DEFAULT 'SUBMITTED', -- e.g., SUBMITTED, IN_PROGRESS, RESOLVED
    -- The magic field updated by our AI
    issue_type VARCHAR(100) DEFAULT 'UNCATEGORIZED', -- e.g., POTHOLE, GARBAGE_DUMP, STREETLIGHT_BROKEN
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert a dummy user for testing
INSERT INTO users (username, email, role) VALUES ('demo_citizen', 'citizen@janseva.com', 'CITIZEN');