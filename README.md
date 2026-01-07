E-Community Service And Volunteer Platform/
│
├── 📁 Source Packages/
│   └── 📁 com.ecommunity/
│       │
│       ├── 📁 bean/ ────────────────────────────────── MODEL LAYER (JavaBeans - Data Structure)
│       │   │
│       │   ├── 📄 User.java
│       │   │   └── Represents a user's data (email, password, name, type, admin status)
│       │   │   └── Fields: userId, email, password, fullName, userType, matricNumber, icNumber, phoneNumber, isAdmin, isDeleted
│       │   │   └── Purpose: Blueprint for user objects
│       │   │
│       │   ├── 📄 VolunteerProgram.java
│       │   │   └── Represents a volunteer program's data
│       │   │   └── Fields: programId, programName, description, location, startDate, endDate, maxParticipants, currentParticipants, category, organizerId, status
│       │   │   └── Purpose: Blueprint for program objects
│       │   │
│       │   ├── 📄 Participation.java
│       │   │   └── Represents a user joining a program
│       │   │   └── Fields: participationId, userId, programId, registrationDate, attendanceStatus, hoursContributed, feedback, rating
│       │   │   └── Purpose: Track who joined which program
│       │   │
│       │   └── 📄 ActivityLog.java
│       │       └── Represents system activity logs
│       │       └── Fields: logId, userId, actionType, description, ipAddress, createdAt
│       │       └── Purpose: Track user actions (LOGIN, LOGOUT, CREATE_PROGRAM, etc.)
│       │
│       ├── 📁 dao/ ─────────────────────────────────── DATA ACCESS LAYER (Database Operations)
│       │   │
│       │   ├── 📄 UserDAO.java
│       │   │   └── Handles all user database operations
│       │   │   └── Methods:
│       │   │       • createUser() - Register new user (INSERT)
│       │   │       • getUserByEmail() - Login validation (SELECT)
│       │   │       • getUserById() - Get user details
│       │   │       • getAllUsers() - Admin: view all users
│       │   │       • updateUser() - Update user info (UPDATE)
│       │   │       • deleteUser() - Soft delete user (UPDATE is_deleted=1)
│       │   │       • emailExists() - Check if email already registered
│       │   │       • getTotalUserCount() - Dashboard statistics
│       │   │   └── Purpose: All SQL queries for users table
│       │   │
│       │   ├── 📄 VolunteerProgramDAO.java
│       │   │   └── Handles all program database operations
│       │   │   └── Methods:
│       │   │       • createProgram() - Admin: create program (INSERT)
│       │   │       • getProgramById() - Get single program details
│       │   │       • getAllPrograms() - List all programs (SELECT WHERE is_deleted=0)
│       │   │       • updateProgram() - Admin: edit program (UPDATE)
│       │   │       • deleteProgram() - Admin: soft delete (UPDATE is_deleted=1)
│       │   │       • getTotalProgramCount() - Dashboard statistics
│       │   │       • getUpcomingProgramCount() - Count upcoming programs
│       │   │       • getRecentPrograms() - Dashboard: show recent programs
│       │   │   └── Purpose: All SQL queries for volunteer_program table
│       │   │
│       │   ├── 📄 ParticipationDAO.java
│       │   │   └── Handles participation database operations
│       │   │   └── Methods:
│       │   │       • createParticipation() - User joins program (INSERT)
│       │   │       • getParticipationById() - Get single participation
│       │   │       • getParticipationsByUser() - User's joined programs
│       │   │       • updateParticipation() - Update status/hours/rating
│       │   │       • deleteParticipation() - Cancel participation (soft delete)
│       │   │       • hasUserJoinedProgram() - Check if already joined
│       │   │       • getTotalParticipationCount() - Dashboard statistics
│       │   │       • getParticipationCountByUser() - User's total participations
│       │   │       • getTotalHoursByUser() - User's volunteer hours
│       │   │   └── Purpose: All SQL queries for participation table
│       │   │
│       │   └── 📄 ActivityLogDAO.java
│       │       └── Handles activity logging operations
│       │       └── Methods:
│       │           • logActivity() - Log user action (INSERT)
│       │           • getLogsByUser() - Get user's activity history
│       │           • getRecentLogs() - Get recent system activities
│       │           • getAllLogs() - Get all activity logs
│       │           • deleteLog() - Soft delete log
│       │       └── Purpose: Track LOGIN, LOGOUT, and other actions
│       │
│       ├── 📁 servlet/ ────────────────────────────── CONTROLLER LAYER (Business Logic)
│       │   │
│       │   ├── 📄 LoginServlet.java ⭐ MODULE 1
│       │   │   └── Handles user login authentication
│       │   │   └── doGet(): Display login page
│       │   │   └── doPost(): 
│       │   │       • Validate email & password
│       │   │       • Call UserDAO.getUserByEmail()
│       │   │       • If valid: Create HttpSession, store user data
│       │   │       • Log LOGIN action to activity_log
│       │   │       • Redirect to dashboard
│       │   │   └── Purpose: Authenticate users and create sessions
│       │   │
│       │   ├── 📄 LogoutServlet.java ⭐ MODULE 1
│       │   │   └── Handles user logout
│       │   │   └── doGet(): 
│       │   │       • Log LOGOUT action to activity_log
│       │   │       • Invalidate HttpSession (session.invalidate())
│       │   │       • Redirect to login page
│       │   │   └── Purpose: Destroy user session securely
│       │   │
│       │   ├── 📄 RegisterServlet.java ⭐ MODULE 2
│       │   │   └── Handles user registration with validation
│       │   │   └── doGet(): Display registration form
│       │   │   └── doPost():
│       │   │       • SERVER-SIDE VALIDATION:
│       │   │         - Email format check
│       │   │         - Password length (min 6 chars)
│       │   │         - Password confirmation match
│       │   │         - Email uniqueness check
│       │   │         - CONDITIONAL VALIDATION:
│       │   │           * If STUDENT: matric_number required & format check
│       │   │           * If PUBLIC: ic_number required & format check
│       │   │       • If valid: Call UserDAO.createUser()
│       │   │       • Redirect to login
│       │   │   └── Purpose: Register new users with proper validation
│       │   │
│       │   ├── 📄 DashboardServlet.java ⭐ MODULE 4
│       │   │   └── Displays dashboard with statistics
│       │   │   └── doGet():
│       │   │       • Check if user logged in (session check)
│       │   │       • Call DAOs to get statistics:
│       │   │         - UserDAO.getTotalUserCount()
│       │   │         - ProgramDAO.getTotalProgramCount()
│       │   │         - ParticipationDAO.getTotalParticipationCount()
│       │   │         - ParticipationDAO.getParticipationCountByUser()
│       │   │         - ParticipationDAO.getTotalHoursByUser()
│       │   │         - ProgramDAO.getRecentPrograms()
│       │   │       • Forward to dashboard.jsp with data
│       │   │   └── Purpose: Show overview statistics
│       │   │
│       │   ├── 📄 ProgramServlet.java ⭐ MODULE 3 (CREATE & UPDATE)
│       │   │   └── Handles program creation and editing (ADMIN ONLY)
│       │   │   └── doGet():
│       │   │       • Check if admin (session.isAdmin)
│       │   │       • If action=create: Show empty form
│       │   │       • If action=edit: Load program data, show filled form
│       │   │   └── doPost():
│       │   │       • Validate all fields (name, description, dates, etc.)
│       │   │       • If create: Call ProgramDAO.createProgram()
│       │   │       • If edit: Call ProgramDAO.updateProgram()
│       │   │       • Redirect to program list
│       │   │   └── Purpose: Admin creates/edits programs
│       │   │
│       │   ├── 📄 ProgramDeleteServlet.java ⭐ MODULE 3 (DELETE - SOFT DELETE)
│       │   │   └── Handles program deletion (ADMIN ONLY)
│       │   │   └── doPost():
│       │   │       • Check if admin
│       │   │       • Call ProgramDAO.deleteProgram()
│       │   │       • This sets is_deleted=1 (SOFT DELETE, not physical deletion)
│       │   │       • Redirect to program list
│       │   │   └── Purpose: Admin soft-deletes programs
│       │   │
│       │   ├── 📄 ParticipationServlet.java
│       │   │   └── Handles users joining/canceling programs
│       │   │   └── doGet():
│       │   │       • If action=join:
│       │   │         - Check if user already joined
│       │   │         - Check if program full
│       │   │         - Call ParticipationDAO.createParticipation()
│       │   │         - Increment program.currentParticipants
│       │   │         - Call ProgramDAO.updateProgram()
│       │   │       • If action=cancel:
│       │   │         - Call ParticipationDAO.deleteParticipation() (soft delete)
│       │   │         - Decrement program.currentParticipants
│       │   │   └── Purpose: Users join/cancel programs
│       │   │
│       │   └── 📄 UserListServlet.java
│       │       └── Displays all users (ADMIN ONLY)
│       │       └── doGet():
│       │           • Check if admin (if not, redirect with error)
│       │           • Call UserDAO.getAllUsers()
│       │           • Forward to user-list.jsp
│       │       └── Purpose: Admin views all registered users
│       │
│       └── 📁 util/ ────────────────────────────────── UTILITY CLASSES
│           │
│           └── 📄 DBConnection.java
│               └── Database connection manager (Singleton pattern)
│               └── Methods:
│                   • getConnection() - Get database connection
│                   • closeConnection() - Close connection
│                   • testConnection() - Test if database reachable
│               └── Configuration:
│                   • DB_URL: jdbc:derby://localhost:1527/ecommunity_db
│                   • DB_USER: sa
│                   • DB_PASSWORD: swizard
│               └── Purpose: Centralized database connection management
│
├── 📁 Web Pages/ ───────────────────────────────────── VIEW LAYER (User Interface)
│   │
│   ├── 📁 WEB-INF/
│   │   ├── 📄 web.xml
│   │   │   └── Deployment descriptor
│   │   │   └── Contains: servlet mappings, session timeout, error pages
│   │   │   └── Purpose: Configure web application settings
│   │   │
│   │   └── 📄 glassfish-web.xml
│   │       └── GlassFish server configuration
│   │       └── Purpose: Server-specific settings
│   │
│   ├── 📁 css/
│   │   └── 📄 style.css
│   │       └── Custom styles for the application
│   │       └── Contains: colors, layouts, animations, card styles, button hover effects
│   │       └── Purpose: Make the UI look professional and modern
│   │
│   ├── 📁 js/
│   │   ├── 📄 validation.js
│   │   │   └── CLIENT-SIDE form validation (UI enhancement only)
│   │   │   └── Functions:
│   │   │       • validateEmail() - Check email format
│   │   │       • validatePassword() - Check password length
│   │   │       • validateMatricNumber() - Check matric format
│   │   │       • validateICNumber() - Check IC format
│   │   │       • showError() / clearError() - Display validation messages
│   │   │   └── Purpose: Improve user experience with instant feedback
│   │   │   └── NOTE: This is NOT security - real validation is in servlets!
│   │   │
│   │   └── 📄 main.js
│   │       └── UI interactions and enhancements
│   │       └── Functions:
│   │           • Auto-dismiss alerts
│   │           • Logout confirmation dialog
│   │           • Delete confirmation dialog
│   │           • Active nav item highlighting
│   │           • Form validation feedback
│   │           • Table search functionality
│   │       └── Purpose: Make UI interactive and user-friendly
│   │
│   ├── 📁 includes/
│   │   ├── 📄 navbar.jsp
│   │   │   └── Reusable navigation bar
│   │   │   └── Shows different links based on:
│   │   │       • Not logged in: E-Community, Login, Register
│   │   │       • Logged in: Dashboard, Programs, My Participations, Profile, Logout
│   │   │       • Admin only: User List
│   │   │   └── Purpose: Consistent navigation across all pages
│   │   │
│   │   ├── 📄 header.jsp
│   │   │   └── Reusable HTML head section
│   │   │   └── Contains: meta tags, Bootstrap CSS, custom CSS
│   │   │   └── Purpose: Consistent head section, avoid duplication
│   │   │
│   │   └── 📄 footer.jsp
│   │       └── Reusable footer section
│   │       └── Contains: contact info, quick links, copyright
│   │       └── Purpose: Consistent footer across all pages
│   │
│   ├── 📁 images/
│   │   └── 📄 uitm-logo.png (or your logo filename)
│   │       └── UiTM logo displayed on login page
│   │       └── Purpose: Branding
│   │
│   ├── 📄 index.jsp
│   │   └── Landing/home page (first page visitors see)
│   │   └── Shows: Hero section, features, categories, call-to-action
│   │   └── Purpose: Welcome page, introduce the platform
│   │
│   ├── 📄 login.jsp ⭐ MODULE 1
│   │   └── Login form page
│   │   └── Contains: Email input, password input, submit button
│   │   └── Displays: Error messages from LoginServlet, success messages
│   │   └── Shows: UiTM logo
│   │   └── Purpose: User authentication form
│   │
│   ├── 📄 register.jsp ⭐ MODULE 2
│   │   └── Registration form page
│   │   └── Contains:
│   │       • Email, full name, password, confirm password
│   │       • User type dropdown (Student/Public)
│   │       • CONDITIONAL FIELDS:
│   │         - If Student selected: Show matric number field
│   │         - If Public selected: Show IC number field
│   │       • Phone number (optional)
│   │   └── JavaScript: Show/hide fields based on user type selection
│   │   └── Purpose: New user registration with conditional validation
│   │
│   ├── 📄 dashboard.jsp ⭐ MODULE 4
│   │   └── Dashboard showing statistics
│   │   └── Displays:
│   │       • Statistics cards:
│   │         - Total users
│   │         - Total programs
│   │         - My participations
│   │         - My volunteer hours
│   │       • Recent programs table
│   │       • Quick action buttons
│   │   └── Data source: DashboardServlet
│   │   └── Purpose: Overview of system and user activity
│   │
│   ├── 📄 program-list.jsp ⭐ MODULE 3 (READ)
│   │   └── List all volunteer programs
│   │   └── Displays:
│   │       • Program cards with: name, description, location, dates, category, status
│   │       • Participant count (e.g., 5/50)
│   │       • View Details button (all users)
│   │       • Edit & Delete buttons (ADMIN ONLY)
│   │       • Create New Program button (ADMIN ONLY)
│   │   └── Data source: VolunteerProgramDAO.getAllPrograms()
│   │   └── Purpose: Browse all available programs
│   │
│   ├── 📄 program-detail.jsp
│   │   └── View single program details
│   │   └── Displays:
│   │       • Full program information
│   │       • Participant count and spots remaining
│   │       • Join Program button (if logged in, not full, and upcoming)
│   │       • Error/success messages
│   │   └── Purpose: Detailed program view and join action
│   │
│   ├── 📄 program-form.jsp ⭐ MODULE 3 (CREATE & UPDATE)
│   │   └── Form to create or edit program (ADMIN ONLY)
│   │   └── Contains:
│   │       • Program name, description, location
│   │       • Start date, end date
│   │       • Max participants, category
│   │       • Status (if editing)
│   │   └── Used for both create and edit:
│   │       • Create: Empty form
│   │       • Edit: Pre-filled with existing data
│   │   └── Purpose: Admin program management
│   │
│   ├── 📄 my-participations.jsp
│   │   └── User's joined programs list
│   │   └── Displays:
│   │       • Table of programs user joined
│   │       • Columns: Program name, registration date, status, hours, rating
│   │       • View button (go to program detail)
│   │       • Cancel button (leave program)
│   │       • Summary: Total programs joined, total hours
│   │   └── Data source: ParticipationDAO.getParticipationsByUser()
│   │   └── Purpose: Track user's volunteer activities
│   │
│   ├── 📄 user-list.jsp
│   │   └── List all users (ADMIN ONLY)
│   │   └── Displays:
│   │       • Table of all registered users
│   │       • Columns: ID, name, email, type, matric/IC, phone, admin status, registration date
│   │       • Admin badge for admin users
│   │   └── Data source: UserDAO.getAllUsers()
│   │   └── Purpose: Admin view all users
│   │
│   └── 📄 profile.jsp
│       └── User profile page
│       └── Displays:
│           • User information: ID, name, email, type, matric/IC, phone
│           • Admin status badge (if admin)
│           • Member since date
│           • Buttons: Back to dashboard, View participations
│       └── Data source: session.getAttribute("user")
│       └── Purpose: View own profile information
│
├── 📁 Libraries/
│   ├── JDK 1.8 (Default)
│   │   └── Java Development Kit 8
│   │   └── Purpose: Compile and run Java code
│   │
│   └── GlassFish Server 4.1.1
│       └── Java EE application server
│       └── Purpose: Run web application, handle servlets/JSP
│
└── 📁 Configuration Files/
    ├── 📄 MANIFEST.MF
    │   └── JAR manifest file
    │   └── Purpose: JAR metadata


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

