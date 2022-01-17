CREATE TABLE IF NOT EXISTS human
(
    id        SERIAL PRIMARY KEY,
    full_name VARCHAR(255) CHECK (char_length(full_name) > 0) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    human_id INTEGER                                        NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    username VARCHAR(20) CHECK (char_length(username) > 0)  NOT NULL,
    password VARCHAR(255) CHECK (char_length(password) > 0) NOT NULL
);

CREATE TABLE IF NOT EXISTS "role"
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) CHECK (name in ('ROLE_PREDETERMINER', 'ROLE_ADMIN', 'ROLE_VOLUNTEER', 'ROLE_SURGEON',
                                      'ROLE_REVIEWER')) NOT NULL
);
CREATE TABLE IF NOT EXISTS human_role
(
    human_id INTEGER REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    role_id  INTEGER REFERENCES role ON DELETE CASCADE ON UPDATE CASCADE,
    Primary Key (human_id, role_id)
);
CREATE TABLE IF NOT EXISTS room
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) CHECK (char_length(name) > 0) NOT NULL
);

--ограничение имен
CREATE TABLE IF NOT EXISTS ovum_container
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) CHECK (name in ('OVUMRECEIVER', 'BOTTLE')) NOT NULL
);

CREATE TABLE IF NOT EXISTS future_job_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) CHECK (name in ('HIGH_TEMP', 'LOW_TEMP', 'HIGH_OXY', 'LOW_OXY')) NOT NULL
);
--todo: check delete
CREATE TABLE IF NOT EXISTS "order"
(
    id               SERIAL PRIMARY KEY,
    predeterminer_id INTEGER                                                                      NOT NULL REFERENCES human,
    human_number     INTEGER CHECK (human_number > 0)                                             NOT NULL,
    caste            VARCHAR(255) CHECK (caste in ('Alpha', 'Beta', 'Gamma', 'Delta', 'Epsilon')) NOT NULL,
    is_processing    BOOLEAN                                                                      NOT NULL
);

CREATE TABLE IF NOT EXISTS future_job_type_order
(
    future_job_type_id INTEGER REFERENCES future_job_type ON DELETE CASCADE ON UPDATE CASCADE,
    order_id           INTEGER REFERENCES "order" ON DELETE CASCADE ON UPDATE CASCADE,
    Primary Key (future_job_type_id, order_id)
);

--group NULL if Alpha or Beta trigger (DONE!) 
CREATE TABLE IF NOT EXISTS "label"
(
    id                        SERIAL PRIMARY KEY,
    additional_information_id INTEGER                                  NOT NULL REFERENCES "order" ON DELETE CASCADE ON UPDATE CASCADE,
    ovum_container_id         INTEGER REFERENCES ovum_container UNIQUE NOT NULL,
    Bokanovsky_group          INTEGER CHECK (Bokanovsky_group >= 72 AND Bokanovsky_group <= 96)
);

--approved trigger (DONE!)
CREATE TABLE IF NOT EXISTS appointment
(
    id             SERIAL PRIMARY KEY,
    volunteer_id   INTEGER               NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    surgeon_id     INTEGER               NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    reviewer_id    INTEGER               NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    operation_time TIMESTAMP             NOT NULL,
    approved       BOOLEAN DEFAULT FALSE NOT NULL,
    UNIQUE (surgeon_id, operation_time),
    UNIQUE (volunteer_id, operation_time)
);

-- if appointment approved (DONE!)
CREATE TABLE IF NOT EXISTS operation
(
    id             SERIAL PRIMARY KEY,
    appointment_id INTEGER UNIQUE                           NOT NULL REFERENCES appointment ON DELETE CASCADE ON UPDATE CASCADE,
    room_id        INTEGER REFERENCES room                  NOT NULL,
    start_time     TIMESTAMP                                NOT NULL,
    end_time       TIMESTAMP CHECK (end_time >= start_time) NOT NULL
);

CREATE TABLE IF NOT EXISTS ovum
(
    id                 SERIAL PRIMARY KEY,
    order_id           INTEGER REFERENCES "order" ON DELETE CASCADE ON UPDATE CASCADE,
    volunteer_id       INTEGER               NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    ovum_container_id  INTEGER               REFERENCES ovum_container ON DELETE SET NULL ON UPDATE SET NULL,
    is_bud             BOOLEAN DEFAULT FALSE NOT NULL,
    fertilization_time TIMESTAMP,
    embryo_time        TIMESTAMP CHECK (embryo_time >= fertilization_time),
    baby_time          TIMESTAMP CHECK (baby_time >= embryo_time)
);

CREATE TABLE IF NOT EXISTS defect
(
    id      SERIAL PRIMARY KEY,
    ovum_id INTEGER                                    NOT NULL REFERENCES ovum ON DELETE CASCADE ON UPDATE CASCADE,
    name    VARCHAR(255) CHECK (char_length(name) > 0) NOT NULL,
    fixable BOOLEAN                                    NOT NULL
);

--trigger for checking fixable (DONE!)
CREATE TABLE IF NOT EXISTS recovery_ovum_defect
(
    ovum_id       INTEGER REFERENCES ovum ON DELETE CASCADE ON UPDATE CASCADE,
    defect_id     INTEGER REFERENCES defect ON DELETE CASCADE ON UPDATE CASCADE,
    surgeon_id    INTEGER NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    recovery_time TIMESTAMP DEFAULT NOW(),
    Primary Key (ovum_id, defect_id, recovery_time)
);

--trigger for checking not fixable (DONE!)
CREATE TABLE IF NOT EXISTS removal_volunteer_for_defect
(
    volunteer_id INTEGER REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    defect_id    INTEGER REFERENCES defect ON DELETE CASCADE ON UPDATE CASCADE,
    reviewer_id  INTEGER NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    removal_time TIMESTAMP DEFAULT NOW() + '35 days'::INTERVAL,
    Primary Key (volunteer_id, defect_id, removal_time)
);

--ограничить имена машины  тогда адекватно прописать взаим-е с бутылём и яйцеприемником. (DONE!)
CREATE TABLE IF NOT EXISTS machine
(
    id      SERIAL PRIMARY KEY,
    room_id INTEGER                NOT NULL REFERENCES room ON DELETE CASCADE ON UPDATE CASCADE,
    name    VARCHAR(255) CHECK (name IN
                                ('Оплодотворитель', 'Бульон_со_сперматозоидами', 'Рентген', 'Инкубатор', 'Морозилка',
                                 'Глушитель_спиртом')
        OR name ~ 'Дорожка_\d{2}') NOT NULL
);

--bottle for bottle interactors and others (DONE!)
--add buds after multiplying (DONE!)
CREATE TABLE IF NOT EXISTS use_machine_by_ovum_container
(
    machine_id        INTEGER REFERENCES machine ON DELETE CASCADE ON UPDATE CASCADE,
    ovum_container_id INTEGER REFERENCES ovum_container ON DELETE CASCADE ON UPDATE CASCADE,
    start_time        TIMESTAMP DEFAULT NOW(),
    end_time          TIMESTAMP CHECK (end_time >= start_time),
    total_buds_count  INTEGER CHECK (total_buds_count BETWEEN 1 AND 8),
    Primary Key (machine_id, ovum_container_id, start_time)
);

CREATE TABLE IF NOT EXISTS conditions_of_detention
(
    id               SERIAL PRIMARY KEY,
    machine_id       INTEGER                                            NOT NULL UNIQUE REFERENCES machine ON DELETE CASCADE ON UPDATE CASCADE,
    oxygen_amount    FLOAT CHECK (oxygen_amount BETWEEN 0.6 AND 1.0)    NOT NULL,
    nutrients_amount FLOAT CHECK (nutrients_amount BETWEEN 0.6 AND 1.0) NOT NULL,
    sex_hormone_dose FLOAT CHECK (sex_hormone_dose BETWEEN 0.6 AND 1.0) NOT NULL,
    temperature      FLOAT CHECK (temperature BETWEEN 15.0 AND 30.0)    NOT NULL,
    flips            BOOLEAN                                            NOT NULL,
    immunization     BOOLEAN                                            NOT NULL
);

CREATE TABLE IF NOT EXISTS material
(
    id                       SERIAL PRIMARY KEY,
    name                     VARCHAR(255) CHECK (char_length(name) > 0)               NOT NULL,
    current_size             FLOAT CHECK (current_size > 0)                           NOT NULL,
    required_size            FLOAT CHECK (required_size > 0)                          NOT NULL,
    quality_parts_percentage FLOAT CHECK (quality_parts_percentage BETWEEN 0 and 100) NOT NULL
);

--trigger add only when required_size = current_size and ovum_container = "Бутыль" (DONE!)
CREATE TABLE IF NOT EXISTS add_material_to_ovum_container
(
    material_id       INTEGER                 REFERENCES material ON DELETE SET NULL ON UPDATE SET NULL,
    ovum_container_id INTEGER REFERENCES ovum_container ON DELETE CASCADE ON UPDATE CASCADE,
    insertion_time    TIMESTAMP DEFAULT NOW() NOT NULL,
    Primary Key (material_id, ovum_container_id)
);

CREATE TABLE IF NOT EXISTS move_ovum_container_to_room
(
    ovum_container_id INTEGER REFERENCES ovum_container ON DELETE CASCADE ON UPDATE CASCADE,
    room_id           INTEGER REFERENCES room ON DELETE CASCADE ON UPDATE CASCADE,
    arrival_time      TIMESTAMP DEFAULT NOW() NOT NULL,
    Primary Key (ovum_container_id, room_id, arrival_time)
);

CREATE TABLE IF NOT EXISTS move_material_to_room
(
    material_id  INTEGER REFERENCES material ON DELETE CASCADE ON UPDATE CASCADE,
    room_id      INTEGER REFERENCES room ON DELETE CASCADE ON UPDATE CASCADE,
    arrival_time TIMESTAMP DEFAULT NOW() NOT NULL,
    Primary Key (material_id, room_id, arrival_time)
);
