CREATE SCHEMA IF NOT EXISTS common;

CREATE TABLE common.audit_logs
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    action         VARCHAR(50),
    entity_name    VARCHAR(100),
    entity_id      INT,
    user_id        INT,
    actor_type     ENUM('user', 'admin', 'system'),
    request_id     VARCHAR(50),
    request_method VARCHAR(10),
    request_url    VARCHAR(255),
    client_ip      VARCHAR(45),
    created_at     TIMESTAMP,
    old_values     JSON,
    new_values     JSON,
    details        TEXT
);

CREATE SCHEMA IF NOT EXISTS user_module;

CREATE TABLE user_module.users
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    username        VARCHAR(50),
    email           VARCHAR(100) UNIQUE,
    hashed_password VARCHAR(255),
    status          ENUM('active', 'inactive', 'suspended'),
    last_login      TIMESTAMP,
    deleted_at      TIMESTAMP
);

CREATE SCHEMA IF NOT EXISTS organization_module;

CREATE TABLE organization_module.departments
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100),
    color       VARCHAR(20),
    description TEXT,
    deleted_at  TIMESTAMP
);

CREATE TABLE organization_module.employees
(
    id                    INT PRIMARY KEY AUTO_INCREMENT,
    first_name            VARCHAR(50),
    last_name             VARCHAR(50),
    company_email         VARCHAR(100),
    personal_email        VARCHAR(100),
    phone                 VARCHAR(20),
    avatar_url            VARCHAR(255),
    date_of_birth         DATE,
    education_level       ENUM('high_school', 'bachelor', 'master', 'phd'),
    education_institution VARCHAR(100),
    work_type             ENUM('full_time', 'part_time', 'contract', 'freelance', 'remote'),
    gender                ENUM('male', 'female', 'other'),
    level                 ENUM('intern', 'fresher', 'junior', 'mid', 'senior', 'lead'),
    status                ENUM('active', 'inactive', 'on_leave'),
    department_id         INT,
    joined_at             DATE,
    org_lft               INT,
    org_rgt               INT,
    deleted_at            TIMESTAMP
);

-- Recruitment
CREATE SCHEMA IF NOT EXISTS recruitment_module;

CREATE TABLE recruitment_module.recruitment_tags
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(50),
    color      VARCHAR(20),
    deleted_at TIMESTAMP
);

CREATE TABLE recruitment_module.job_descriptions
(
    id                   INT PRIMARY KEY AUTO_INCREMENT,
    title                VARCHAR(100),
    description          TEXT,
    requirements         TEXT,
    benefits             TEXT,
    work_type            ENUM('full_time', 'part_time', 'contract', 'freelance', 'remote'),
    level                ENUM('intern', 'fresher', 'junior', 'mid', 'senior', 'lead'),
    min_years_experience INT,
    min_salary           DECIMAL(10, 2),
    max_salary           DECIMAL(10, 2),
    department_id        INT,
    number_of_recruits   INT,
    status               ENUM('open', 'closed', 'draft'),
    created_at           TIMESTAMP,
    created_by           INT,
    updated_at           TIMESTAMP,
    updated_by           INT,
    deleted_at           TIMESTAMP
);

CREATE TABLE recruitment_module.job_description_tags
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    job_description_id INT,
    tag_id             INT
);

CREATE TABLE recruitment_module.job_posting_links
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    job_description_id INT,
    platform           ENUM('website', 'linkedin', 'facebook', 'twitter', 'other'),
    link               VARCHAR(255),
    status             ENUM('draft', 'published', 'archived'),
    deleted_at         TIMESTAMP
);

CREATE TABLE recruitment_module.applications
(
    id                    INT PRIMARY KEY AUTO_INCREMENT,
    job_description_id    INT,
    first_name            VARCHAR(50),
    last_name             VARCHAR(50),
    email                 VARCHAR(100),
    phone                 VARCHAR(20),
    year_of_experience    INT,
    education_level       ENUM('high_school', 'bachelor', 'master', 'phd'),
    education_institution VARCHAR(100),
    summary               TEXT,
    status                ENUM('applied', 'interviewing', 'offered', 'hired', 'rejected'),
    contact_link          VARCHAR(255),
    created_at            TIMESTAMP,
    created_by            INT,
    updated_at            TIMESTAMP,
    updated_by            INT,
    deleted_at            TIMESTAMP
);

CREATE TABLE recruitment_module.application_comments
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT,
    user_id        INT,
    comment        TEXT,
    created_at     TIMESTAMP,
    created_by     INT,
    updated_at     TIMESTAMP,
    deleted_at     TIMESTAMP
);

CREATE TABLE recruitment_module.application_documents
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT,
    name           VARCHAR(100),
    document_url   VARCHAR(255),
    created_at     TIMESTAMP,
    created_by     INT,
    deleted_at     TIMESTAMP
);

CREATE SCHEMA IF NOT EXISTS schedule_module;

CREATE TABLE schedule_module.rooms
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(100),
    color      VARCHAR(20),
    created_at TIMESTAMP,
    created_by INT,
    updated_at TIMESTAMP,
    updated_by INT,
    deleted_at TIMESTAMP
);

CREATE table schedule_module.schedule_tags
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(50),
    color      VARCHAR(20),
    deleted_at TIMESTAMP
);

CREATE TABLE schedule_module.bookings
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100),
    description TEXT,
    start_time  DATETIME,
    end_time    DATETIME,
    room_id     INT,
    created_at  TIMESTAMP,
    created_by  INT,
    updated_at  TIMESTAMP,
    updated_by  INT,
    deleted_at  TIMESTAMP
);

CREATE TABLE schedule_module.booking_tags
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT,
    tag_id     INT
);

CREATE TABLE schedule_module.booking_participants
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    booking_id  INT,
    user_id     INT,
    rsvp_status ENUM('invited', 'accepted', 'declined', 'scheduler'),
    created_at  TIMESTAMP,
    created_by  INT,
    updated_at  TIMESTAMP,
    deleted_at  TIMESTAMP
);