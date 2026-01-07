E-Community Service And Volunteer Platform/
│
├── Source Packages/
│   └── com.ecommunity/
│       │
│       ├── bean/                         (Model Layer – JavaBeans / Data Structure)
│       │   ├── User.java
│       │   │   - Represents a user's data
│       │   │   - Fields:
│       │   │     userId, email, password, fullName, userType,
│       │   │     matricNumber, icNumber, phoneNumber,
│       │   │     isAdmin, isDeleted
│       │   │   - Purpose: Blueprint for user objects
│       │   │
│       │   ├── VolunteerProgram.java
│       │   │   - Represents a volunteer program
│       │   │   - Fields:
│       │   │     programId, programName, description, location,
│       │   │     startDate, endDate, maxParticipants,
│       │   │     currentParticipants, category,
│       │   │     organizerId, status
│       │   │   - Purpose: Blueprint for program objects
│       │   │
│       │   ├── Participation.java
│       │   │   - Represents a user's participation in a program
│       │   │   - Fields:
│       │   │     participationId, userId, programId,
│       │   │     registrationDate, attendanceStatus,
│       │   │     hoursContributed, feedback, rating
│       │   │   - Purpose: Track user participation records
│       │   │
│       │   └── ActivityLog.java
│       │       - Represents system activity logs
│       │       - Fields:
│       │         logId, userId, actionType,
│       │         description, ipAddress, createdAt
│       │       - Purpose: Track system and user actions
│       │
│       ├── dao/                          (Data Access Layer – Database Operations)
│       │   ├── UserDAO.java
│       │   │   - Handles all user-related database operations
│       │   │   - Methods:
│       │   │     createUser()
│       │   │     getUserByEmail()
│       │   │     getUserById()
│       │   │     getAllUsers()
│       │   │     updateUser()
│       │   │     deleteUser()
│       │   │     emailExists()
│       │   │     getTotalUserCount()
│       │   │   - Purpose: Execute SQL queries for users table
│       │   │
│       │   ├── VolunteerProgramDAO.java
│       │   │   - Handles all volunteer program database operations
│       │   │   - Methods:
│       │   │     createProgram()
│       │   │     getProgramById()
│       │   │     getAllPrograms()
│       │   │     updateProgram()
│       │   │     deleteProgram()
│       │   │     getTotalProgramCount()
│       │   │     getUpcomingProgramCount()
│       │   │     getRecentPrograms()
│       │   │   - Purpose: Execute SQL queries for volunteer_program table
│       │   │
│       │   ├── ParticipationDAO.java
│       │   │   - Handles participation-related database operations
│       │   │   - Methods:
│       │   │     createParticipation()
│       │   │     getParticipationById()
│       │   │     getParticipationsByUser()
│       │   │     updateParticipation()
│       │   │     deleteParticipation()
│       │   │     hasUserJoinedProgram()
│       │   │     getTotalParticipationCount()
│       │   │     getParticipationCountByUser()
│       │   │     getTotalHoursByUser()
│       │   │   - Purpose: Manage participation records
│       │   │
│       │   └── ActivityLogDAO.java
│       │       - Handles activity log database operations
│       │       - Methods:
│       │         logActivity()
│       │         getLogsByUser()
│       │         getRecentLogs()
│       │         getAllLogs()
│       │         deleteLog()
│       │       - Purpose: Track system activity history
│       │
│       ├── servlet/                      (Controller Layer – Business Logic)
│       │   ├── LoginServlet.java
│       │   │   - Handles user authentication
│       │   │   - Creates session and logs login activity
│       │   │
│       │   ├── LogoutServlet.java
│       │   │   - Handles user logout
│       │   │   - Invalidates session and logs logout activity
│       │   │
│       │   ├── RegisterServlet.java
│       │   │   - Handles user registration
│       │   │   - Performs server-side validation
│       │   │
│       │   ├── DashboardServlet.java
│       │   │   - Displays dashboard statistics
│       │   │   - Retrieves system and user activity data
│       │   │
│       │   ├── ProgramServlet.java
│       │   │   - Handles program creation and editing (Admin only)
│       │   │
│       │   ├── ProgramDeleteServlet.java
│       │   │   - Handles soft deletion of programs (Admin only)
│       │   │
│       │   ├── ParticipationServlet.java
│       │   │   - Handles joining and canceling program participation
│       │   │
│       │   └── UserListServlet.java
│       │       - Displays list of all users (Admin only)
│       │
│       └── util/                         (Utility Classes)
│           └── DBConnection.java
│               - Manages database connections
│               - Uses Singleton pattern
│               - Configuration:
│                 DB_URL: jdbc:derby://localhost:1527/ecommunity_db
│                 DB_USER: sa
│                 DB_PASSWORD: swizard
│
├── Web Pages/                            (View Layer – User Interface)
│   ├── WEB-INF/
│   │   ├── web.xml
│   │   │   - Application deployment descriptor
│   │   │
│   │   └── glassfish-web.xml
│   │       - GlassFish server configuration
│   │
│   ├── css/
│   │   └── style.css
│   │       - Application styling and layout
│   │
│   ├── js/
│   │   ├── validation.js
│   │   │   - Client-side form validation
│   │   │
│   │   └── main.js
│   │       - UI interactions and enhancements
│   │
│   ├── includes/
│   │   ├── navbar.jsp
│   │   ├── header.jsp
│   │   └── footer.jsp
│   │
│   ├── images/
│   │   └── uitm-logo.png
│   │
│   ├── index.jsp
│   ├── login.jsp
│   ├── register.jsp
│   ├── dashboard.jsp
│   ├── program-list.jsp
│   ├── program-detail.jsp
│   ├── program-form.jsp
│   ├── my-participations.jsp
│   ├── user-list.jsp
│   └── profile.jsp
│
├── Libraries/
│   ├── JDK 1.8
│   └── GlassFish Server 4.1.1
│
└── Configuration Files/
    └── MANIFEST.MF

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

