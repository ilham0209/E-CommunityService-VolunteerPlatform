-- =====================================================
-- COMPLETE DATABASE SCHEMA FOR E-COMMUNITY PLATFORM
-- Database: ecommunity_db 
-- User: sa / Password: swizard
-- =====================================================

-- Table 1: users - Stores all registered users
CREATE TABLE users (
    user_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    user_type VARCHAR(10) NOT NULL CHECK (user_type IN ('STUDENT', 'PUBLIC')),
    matric_number VARCHAR(20),
    ic_number VARCHAR(20),
    phone_number VARCHAR(20),
    is_admin SMALLINT DEFAULT 0,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_deleted SMALLINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    CONSTRAINT unique_email UNIQUE (email)
);

-- Table 2: volunteer_program - Stores volunteer programs
CREATE TABLE volunteer_program (
    program_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    program_name VARCHAR(200) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    location VARCHAR(200) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_participants INT NOT NULL,
    current_participants INT DEFAULT 0,
    category VARCHAR(50) NOT NULL,
    organizer_id INT NOT NULL,
    status VARCHAR(20) DEFAULT 'UPCOMING' CHECK (status IN ('UPCOMING', 'ONGOING', 'COMPLETED', 'CANCELLED')),
    is_deleted SMALLINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (program_id),
    CONSTRAINT fk_organizer FOREIGN KEY (organizer_id) REFERENCES users(user_id)
);

-- Table 3: participation - Tracks user participation in programs
CREATE TABLE participation (
    participation_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_id INT NOT NULL,
    program_id INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    attendance_status VARCHAR(20) DEFAULT 'REGISTERED' CHECK (attendance_status IN ('REGISTERED', 'ATTENDED', 'ABSENT', 'CANCELLED')),
    hours_contributed DECIMAL(5,2) DEFAULT 0.00,
    feedback VARCHAR(2000),
    rating INT CHECK (rating >= 1 AND rating <= 5),
    is_deleted SMALLINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (participation_id),
    CONSTRAINT fk_participation_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_participation_program FOREIGN KEY (program_id) REFERENCES volunteer_program(program_id),
    CONSTRAINT unique_participation UNIQUE (user_id, program_id)
);

-- Table 4: activity_log - Logs user actions (login, logout, etc.)
CREATE TABLE activity_log (
    log_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_id INT,
    action_type VARCHAR(50) NOT NULL,
    description VARCHAR(2000),
    ip_address VARCHAR(45),
    is_deleted SMALLINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (log_id),
    CONSTRAINT fk_activity_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_type ON users(user_type);
CREATE INDEX idx_users_active ON users(is_deleted);
CREATE INDEX idx_users_admin ON users(is_admin);
CREATE INDEX idx_program_status ON volunteer_program(status, is_deleted);
CREATE INDEX idx_program_dates ON volunteer_program(start_date, end_date);
CREATE INDEX idx_participation_user ON participation(user_id, is_deleted);
CREATE INDEX idx_participation_program ON participation(program_id, is_deleted);
CREATE INDEX idx_activity_log_user ON activity_log(user_id, created_at);
CREATE INDEX idx_activity_log_action ON activity_log(action_type);

