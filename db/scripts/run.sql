DROP TABLE IF EXISTS human CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS "role" CASCADE;
DROP TABLE IF EXISTS human_role CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS ovum_container CASCADE;
DROP TABLE IF EXISTS future_job_type CASCADE;
DROP TABLE IF EXISTS "order" CASCADE;
DROP TABLE IF EXISTS future_job_type_order CASCADE;
DROP TABLE IF EXISTS "label" CASCADE;
DROP TABLE IF EXISTS appointment CASCADE;
DROP TABLE IF EXISTS operation CASCADE;
DROP TABLE IF EXISTS ovum CASCADE;
DROP TABLE IF EXISTS defect CASCADE;
DROP TABLE IF EXISTS recovery_ovum_defect CASCADE;
DROP TABLE IF EXISTS removal_volunteer_for_defect CASCADE;
DROP TABLE IF EXISTS machine CASCADE;
DROP TABLE IF EXISTS use_machine_by_ovum_container CASCADE;
DROP TABLE IF EXISTS conditions_of_detention CASCADE;
DROP TABLE IF EXISTS material CASCADE;
DROP TABLE IF EXISTS add_material_to_ovum_container CASCADE;
DROP TABLE IF EXISTS move_ovum_container_to_room CASCADE;
DROP TABLE IF EXISTS move_material_to_room CASCADE;

--#################################################

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
    predeterminer_id INTEGER                                                                      NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
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
    ovum_id      INTEGER REFERENCES ovum ON DELETE CASCADE ON UPDATE CASCADE,
    defect_id    INTEGER REFERENCES defect ON DELETE CASCADE ON UPDATE CASCADE,
    reviewer_id  INTEGER NOT NULL REFERENCES human ON DELETE CASCADE ON UPDATE CASCADE,
    removal_time TIMESTAMP DEFAULT NOW() + '35 days'::INTERVAL,
    Primary Key (ovum_id, defect_id, removal_time)
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

--#################################################

CREATE INDEX IF NOT EXISTS role_name ON role USING hash (name);

CREATE INDEX IF NOT EXISTS appointment_volunteer ON appointment USING hash (volunteer_id);

CREATE INDEX IF NOT EXISTS ovum_volunteer ON ovum USING hash (volunteer_id);

CREATE INDEX IF NOT EXISTS ovum_ovum_container ON ovum USING hash (ovum_container_id);

CREATE INDEX IF NOT EXISTS defect_ovum ON defect USING hash (ovum_id);

CREATE INDEX IF NOT EXISTS removal_volunteer_removal_time ON removal_volunteer_for_defect (removal_time);

--#################################################

--функция для подтверждения записи на операцию
CREATE OR REPLACE FUNCTION checkAppointmentApprovement()
    RETURNS TRIGGER AS
$$
DECLARE
    v_ovum                   INTEGER;
    v_is_all_defects_fixable BOOLEAN;
    v_is_not_removaled       BOOLEAN;
BEGIN
    --Если у волонтёра в яйцеклетке есть дефекты
    CASE WHEN EXISTS(SELECT 1
                     FROM defect
                              JOIN ovum ON (defect.ovum_id = ovum.id)
                     WHERE ovum.volunteer_id = NEW.volunteer_id)
        THEN
            SELECT ovum.id INTO v_ovum FROM ovum WHERE ovum.volunteer_id = NEW.volunteer_id;
            --Если нет неисправимых
            SELECT COUNT(defect.fixable) = 0
            INTO v_is_all_defects_fixable
            FROM defect
            WHERE defect.fixable = FALSE
              AND defect.ovum_id = v_ovum;
            --Если время отстранения волонтёра закончилось
            SELECT MAX(removal_time) <= NOW()
            INTO v_is_not_removaled
            FROM removal_volunteer_for_defect
            WHERE volunteer_id = NEW.volunteer_id;
            --Если записи нет, то он не отстранен
            IF v_is_not_removaled IS NULL THEN v_is_not_removaled = TRUE; END IF;
            IF (v_is_all_defects_fixable AND v_is_not_removaled) THEN
                NEW.approved := TRUE;
                RAISE NOTICE 'Волонтер % имеет дефекты: нет неисправимых дефектов %, не отстранён %. Запись % подтверждена.', NEW.volunteer_id, v_is_all_defects_fixable, v_is_not_removaled, NEW.id;
            ELSE
                RAISE NOTICE 'Волонтер % имеет дефекты: нет неисправимых дефектов %, не отстранён %. Запись % отклонена.', NEW.volunteer_id, v_is_all_defects_fixable, v_is_not_removaled, NEW.id;

            END IF;
        ELSE
            RAISE NOTICE 'Волонтер % не имеет дефектов. Запись % подтверждена.', NEW.volunteer_id, NEW.id;
            NEW.approved := TRUE;
        END CASE;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


--Функция для проверки на неисправность дефектов при отстранения волонтера
CREATE OR REPLACE FUNCTION removalVolunteerForDefect()
    RETURNS TRIGGER AS
$$
DECLARE
    v_ovum                   INTEGER;
    v_volunteer              INTEGER;
    v_is_all_defects_fixable BOOLEAN;
BEGIN
    SELECT ovum_id INTO v_ovum FROM defect WHERE (defect.id = NEW.defect_id);
    SELECT volunteer_id INTO v_volunteer FROM ovum WHERE ovum.id = v_ovum;
    --проверить яйцеклетку волонтера на дефект и сравнить с введённым
    IF (v_volunteer != NEW.ovum_id) THEN
        RAISE EXCEPTION 'Яйцеклетка % не иммет дефекта %. (Возможно вы имели в виду яйкеклетку %)', NEW.ovum_id, NEW.defect_id, v_volunteer;
    END IF;
    SELECT COUNT(defect.fixable) = 0
    INTO v_is_all_defects_fixable
    FROM defect
    WHERE defect.fixable = FALSE
      AND defect.ovum_id = v_ovum;
    --Если нет неисправимых дефекты
    IF (v_is_all_defects_fixable = true) THEN
        RAISE EXCEPTION 'Яйцеклетка % не имеет неисправимых дефектов (принадлежит волонтёру: %)', v_ovum, v_volunteer;
    END IF;
    RAISE NOTICE 'Яйцеклетка % волонтера: % содержит неисправимые дефекты. Отстраняем за дефект %.', v_ovum, v_volunteer, NEW.defect_id;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


-- Функция для проверки на неисправность дефекта при восставлении яйцеклетки
CREATE OR REPLACE FUNCTION recoveryOvumDefect()
    RETURNS TRIGGER AS
$$
DECLARE
    v_is_all_defects_fixable BOOLEAN;
    v_ovum                   INTEGER;
BEGIN
    SELECT ovum_id INTO v_ovum FROM defect WHERE (defect.id = NEW.defect_id);
    --Проверить яйцеклетку на дефект и сравнить с введенным
    IF (v_ovum != NEW.ovum_id)
    THEN
        RAISE EXCEPTION 'Яйцеклетка % не иммет дефекта %. (Возможно вы имели в виду яйцеклетку %)', NEW.ovum_id, NEW.defect_id, v_ovum;
    END IF;
    --Есть ли у яйцклетки неиспраные дефекты
    SELECT COUNT(defect.fixable) = 0
    INTO v_is_all_defects_fixable
    FROM defect
    WHERE defect.fixable = FALSE
      AND defect.ovum_id = v_ovum;
    --Если есть неисправные яйцклетки
    IF v_is_all_defects_fixable = false
    THEN
        RAISE EXCEPTION 'Яйцеклетка % имеет неисправимый дефект', v_ovum;
    END IF;
    --Иначе яйцклетку можно восстановить
    RAISE NOTICE 'Яйцеклетка % не имеет обнаруженных неисправимых дефектов. Восстанавливаем дефект %.', v_ovum, NEW.defect_id;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


-- Функция для допуска волонтера на операцию
CREATE OR REPLACE FUNCTION checkApprovedVolunteerToOperation()
    RETURNS TRIGGER AS
$$
DECLARE
    v_approved  BOOLEAN;
    v_volunteer INTEGER;
BEGIN
    SELECT volunteer_id INTO v_volunteer FROM appointment WHERE (appointment.id = NEW.appointment_id);
    SELECT approved INTO v_approved FROM appointment WHERE (appointment.id = NEW.appointment_id);
    --Проверка разрешения проведения операции волонтеру
    IF (v_approved = false)
    THEN
        RAISE EXCEPTION 'Операция %: Волонтер % по записи % не допущен к операции.', NEW.id, v_volunteer, NEW.appointment_id;
    END IF;
    RAISE NOTICE 'Операция %: Волонтер % по записи % допущен к операции.', NEW.id, v_volunteer, NEW.appointment_id;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


--Функция для автозаполнения группы Бокановского
CREATE OR REPLACE FUNCTION checkBokanovskyGroup()
    RETURNS TRIGGER AS
$$
DECLARE
    v_caste VARCHAR(64);
BEGIN
    SELECT caste INTO v_caste FROM "order" WHERE "order".id = NEW.additional_information_id;
    RAISE NOTICE 'Каста у наклейки % заказа %: %; группа: %', NEW.id, NEW.additional_information_id, v_caste, NEW.Bokanovsky_group;
    IF v_caste IN ('Alpha', 'Beta') AND NEW.Bokanovsky_group IS NOT NULL THEN
        RAISE EXCEPTION 'Каста % не имеют группы Бокановского', v_caste;
    END IF;
    IF v_caste NOT IN ('Alpha', 'Beta') AND NEW.Bokanovsky_group IS NULL THEN
        RAISE EXCEPTION 'Каста % должна иметь группу Бокановского', v_caste;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


--Функция для проверки соответсвия размера материала при помещении в бутыль
CREATE OR REPLACE FUNCTION checkAddMaterialToOvumContainer()
    RETURNS TRIGGER AS
$$
DECLARE
    v_cur_size            FLOAT;
    v_req_size            FLOAT;
    v_quality_percent     FLOAT;
    v_ovum_container_name VARCHAR(64);
BEGIN
    SELECT current_size, required_size, quality_parts_percentage
    INTO v_cur_size, v_req_size, v_quality_percent
    FROM material
    WHERE (material.id = NEW.material_id);
    SELECT name INTO v_ovum_container_name FROM ovum_container WHERE (ovum_container.id = NEW.ovum_container_id);
    IF (v_ovum_container_name = 'BOTTLE') THEN
        IF (v_quality_percent != 100) THEN
            RAISE EXCEPTION 'Материал % имеет % качество, требуется: 100.)', NEW.material_id, v_quality_percent;
        END IF;
        IF (v_cur_size != v_req_size) THEN
            RAISE EXCEPTION 'Материал % имеет % размер, требуется: %.', NEW.material_id, v_cur_size ,v_req_size;
        END IF;
    ELSE
        RAISE EXCEPTION 'Материал % может быть помещён только в Бутыль.', NEW.material_id;
    END IF;
    RAISE NOTICE 'Материал % с размером cur: % req: % и качеством % добавлен в контейнер %', NEW.material_id, v_cur_size ,v_req_size, v_quality_percent, NEW.ovum_container_id;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;



--Функция для проверки взаимодействия ovum_container с machine 
CREATE OR REPLACE FUNCTION useCorrectMachineByOvumContainer()
    RETURNS TRIGGER AS
$$
DECLARE
    v_machine_name        VARCHAR(64);
    v_ovum_container_name VARCHAR(64);
BEGIN
    SELECT name INTO v_machine_name FROM machine WHERE (machine.id = NEW.machine_id);
    SELECT name INTO v_ovum_container_name FROM ovum_container WHERE (ovum_container.id = NEW.ovum_container_id);
    IF v_ovum_container_name = 'OVUMRECEIVER' AND v_machine_name ~ 'Дорожка_\d{2}' THEN
        RAISE EXCEPTION 'Яйцеприемник (контейнер %) не может взаимодействовать с % (машина %)', NEW.ovum_container_id, v_machine_name, NEW.machine_id;
    END IF;
    IF v_ovum_container_name = 'BOTTLE' AND v_machine_name IN
                                            ('Оплодотворитель', 'Бульон_со_сперматозоидами', 'Рентген', 'Инкубатор',
                                             'Морозилка', 'Глушитель_спиртом') THEN
        RAISE EXCEPTION 'Бутыль (контейнер %) не может взаимодействовать с % (машина %)', NEW.ovum_container_id, v_machine_name, NEW.machine_id;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


--Функция для почкования
CREATE OR REPLACE FUNCTION b_use_machine_for_multiplying_ovums()
    RETURNS TRIGGER AS
$$
DECLARE
    v_ovum_container_name VARCHAR(64);
    v_ovum                INTEGER;
BEGIN
    --Если требуется почкование
    IF (NEW.total_buds_count IS NOT NULL) THEN

        SELECT name INTO v_ovum_container_name FROM ovum_container WHERE (ovum_container.id = NEW.ovum_container_id);
        --Почкование возможно лишь в яйцеприемнике
        IF (v_ovum_container_name != 'OVUMRECEIVER') THEN
            RAISE EXCEPTION 'Контейнер % не поддерживает почкование.', NEW.ovum_container_id;
        END IF;
        --Для каждой яйцеклетки в яйцеприемнике
        FOR v_ovum IN (SELECT ovum.id
                       FROM ovum
                                JOIN ovum_container ON (ovum_container_id = ovum_container.id)
                       WHERE (ovum_container.id = NEW.ovum_container_id))
            LOOP
                RAISE NOTICE 'Яйцеклетка % почкуется % раз', v_ovum, NEW.total_buds_count;
                --Превращаем старую яйцеклетку в почку
                UPDATE ovum SET is_bud = true WHERE id = v_ovum;
                --Добавляем недостающие почки
                FOR i IN 1..NEW.total_buds_count - 1
                    LOOP
                        INSERT INTO ovum (volunteer_id, order_id, ovum_container_id, is_bud, fertilization_time,
                                          embryo_time, baby_time)
                        SELECT volunteer_id,
                               order_id,
                               ovum_container_id,
                               is_bud,
                               fertilization_time,
                               embryo_time,
                               baby_time
                        FROM ovum
                        WHERE (ovum.id = v_ovum);
                    END LOOP;
            END LOOP;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


--Функция для проверки Предопределителя
CREATE OR REPLACE FUNCTION checkPredeterminerRole()
    RETURNS TRIGGER AS
$$
DECLARE
    v_name VARCHAR(64);
BEGIN
    FOR v_name IN (SELECT name
                   FROM role
                            JOIN human_role ON (role_id = role.id)
                   WHERE human_id = NEW.predeterminer_id)
        LOOP
            IF (v_name = 'ROLE_PREDETERMINER') THEN
                RETURN NEW;
            END IF;
        END LOOP;
    RAISE EXCEPTION 'Человек % не является Предопределителем (role = %)', NEW.predeterminer_id, v_name;
END
$$
    LANGUAGE plpgsql;


--Функция для проверки Добровольца
CREATE OR REPLACE FUNCTION checkVolunteerRole()
    RETURNS TRIGGER AS
$$
DECLARE
    v_name VARCHAR(64);
BEGIN
    SELECT name
    INTO v_name
    FROM role
             JOIN human_role ON (role_id = role.id)
    WHERE human_id = NEW.volunteer_id;
    IF (v_name != 'ROLE_VOLUNTEER') THEN
        RAISE EXCEPTION 'Человек % не является Добровольцем (role = %)', NEW.volunteer_id, v_name;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


--Функция для проверки Хирурга
CREATE OR REPLACE FUNCTION checkSurgeonRole()
    RETURNS TRIGGER AS
$$
DECLARE
    v_name VARCHAR(64);
BEGIN
    SELECT name
    INTO v_name
    FROM role
             JOIN human_role ON (role_id = role.id)
    WHERE human_id = NEW.surgeon_id;
    IF (v_name != 'ROLE_SURGEON') THEN
        RAISE EXCEPTION 'Человек % не является Хирургом (role = %)', NEW.surgeon_id, v_name;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


--Функция для проверки Ревьюера
CREATE OR REPLACE FUNCTION checkReviewerRole()
    RETURNS TRIGGER AS
$$
DECLARE
    v_name VARCHAR(64);
BEGIN
    SELECT name
    INTO v_name
    FROM role
             JOIN human_role ON (role_id = role.id)
    WHERE human_id = NEW.reviewer_id;
    IF (v_name != 'ROLE_REVIEWER') THEN
        RAISE EXCEPTION 'Человек % не является Ревьюером (role = %)', NEW.reviewer_id, v_name;
    END IF;
    RETURN NEW;
END
$$
    LANGUAGE plpgsql;


--Функция для нарезания свиных туш
CREATE OR REPLACE FUNCTION cutMaterial(old_id INTEGER, new_name VARCHAR(64))
    RETURNS void AS
$$
DECLARE
    v_cur_size        FLOAT;
    v_req_size        FLOAT;
    v_quality_percent FLOAT;
    v_cnt             INTEGER;
BEGIN
    SELECT current_size, required_size, quality_parts_percentage
    INTO v_cur_size, v_req_size, v_quality_percent
    FROM material
    WHERE (material.id = old_id);
    v_cnt := TRUNC((v_cur_size * v_quality_percent / 100) / v_req_size);
    IF (v_cnt = 0) THEN
        RAISE EXCEPTION 'Материал % с размером % и качеством % невозможно поделить на части % размера.', old_id, v_cur_size, v_quality_percent, v_req_size;
    END IF;
    --Разрезаем материал на максимальное количество частей
    RAISE NOTICE 'Режем материал % с размером cur: % req: % и качеством % на % частей.', old_id, v_cur_size ,v_req_size, v_quality_percent, v_cnt;
    FOR i IN 1..v_cnt
        LOOP
            INSERT INTO material (name, current_size, required_size, quality_parts_percentage)
            VALUES (new_name, v_req_size, v_req_size, 100);
        END LOOP;
    UPDATE material
    SET current_size             = (v_cur_size * v_quality_percent / 100 - v_req_size * v_cnt),
        quality_parts_percentage = 100
    WHERE id = old_id;
END
$$
    LANGUAGE plpgsql;

--#################################################

--Триггер для подтверждения записи на операцию
CREATE TRIGGER checkApprovement
    BEFORE INSERT OR UPDATE
    ON appointment
    FOR EACH ROW
EXECUTE PROCEDURE checkAppointmentApprovement();


--Триггер для проверки на неисправность дефектов при отстранения волонтера
CREATE TRIGGER removalVolunteer
    BEFORE INSERT OR UPDATE
    ON removal_volunteer_for_defect
    FOR EACH ROW
EXECUTE PROCEDURE removalVolunteerForDefect();


-- Триггер для проверки на неисправность дефекта при восставлении яйцеклетки
CREATE TRIGGER recoveryOvum
    BEFORE INSERT OR UPDATE
    ON recovery_ovum_defect
    FOR EACH ROW
EXECUTE PROCEDURE recoveryOvumDefect();


-- Триггер для допуска волонтера на операцию
CREATE TRIGGER checkApprovedVolunteer
    BEFORE INSERT OR UPDATE
    ON operation
    FOR EACH ROW
EXECUTE PROCEDURE checkApprovedVolunteerToOperation();


--Триггер для автозаполнения группы Бокановского
CREATE TRIGGER checkBokonovsky
    BEFORE INSERT OR UPDATE
    ON "label"
    FOR EACH ROW
EXECUTE PROCEDURE checkBokanovskyGroup();


--Триггер для проверки соответсвия размера материала при помещении в бутыль
CREATE TRIGGER checkAddMaterial
    BEFORE INSERT OR UPDATE
    ON add_material_to_ovum_container
    FOR EACH ROW
EXECUTE PROCEDURE checkAddMaterialToOvumContainer();


--Триггер для проверки взаимодействия ovum_container с machine 
CREATE TRIGGER a_useCorrectMachine
    BEFORE INSERT OR UPDATE
    ON use_machine_by_ovum_container
    FOR EACH ROW
EXECUTE PROCEDURE useCorrectMachineByOvumContainer();


--Триггер для почкования
CREATE TRIGGER b_use_machine_for_multiplying
    BEFORE INSERT OR UPDATE
    ON use_machine_by_ovum_container
    FOR EACH ROW
EXECUTE PROCEDURE b_use_machine_for_multiplying_ovums();


----Триггеры для проверки ролей appointment
CREATE TRIGGER checkVolunteerRoleAppointment
    BEFORE INSERT OR UPDATE
    ON appointment
    FOR EACH ROW
EXECUTE PROCEDURE checkVolunteerRole();

CREATE TRIGGER checkSurgeonRoleAppointment
    BEFORE INSERT OR UPDATE
    ON appointment
    FOR EACH ROW
EXECUTE PROCEDURE checkSurgeonRole();

CREATE TRIGGER checkReviewerRoleAppointment
    BEFORE INSERT OR UPDATE
    ON appointment
    FOR EACH ROW
EXECUTE PROCEDURE checkReviewerRole();
-------------------------------------------


--Триггер для проверки роли order
CREATE TRIGGER checkPredeterminerRoleOrder
    BEFORE INSERT OR UPDATE
    ON "order"
    FOR EACH ROW
EXECUTE PROCEDURE checkPredeterminerRole();

----Триггеры для проверки роли removal_volunteer_for_defect
CREATE TRIGGER checkVolunteerRoleRemoval
    BEFORE INSERT OR UPDATE
    ON removal_volunteer_for_defect
    FOR EACH ROW
EXECUTE PROCEDURE checkVolunteerRole();

CREATE TRIGGER checkReviewerRoleRemoval
    BEFORE INSERT OR UPDATE
    ON removal_volunteer_for_defect
    FOR EACH ROW
EXECUTE PROCEDURE checkReviewerRole();
-------------------------------------------


--Триггер для проверки роли recovery_ovum_defect
CREATE TRIGGER checkSurgeonRoleRecovery
    BEFORE INSERT OR UPDATE
    ON recovery_ovum_defect
    FOR EACH ROW
EXECUTE PROCEDURE checkSurgeonRole();


----Триггер для проверки роли ovum
CREATE TRIGGER checkVolunteerRoleOvum
    BEFORE INSERT OR UPDATE
    ON ovum
    FOR EACH ROW
EXECUTE PROCEDURE checkVolunteerRole();

--#################################################

INSERT INTO "role" (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_PREDETERMINER'),
       ('ROLE_VOLUNTEER'),
       ('ROLE_SURGEON'),
       ('ROLE_REVIEWER');
INSERT INTO room (name)
VALUES ('Зал оплодотворения'),
       ('Укупорочный зал'),
       ('Органохранилище'),
       ('Зал социального предопределения'),
       ('Эмбрионарий'),
       ('Младопитомник'),
       ('Операционный зал');
INSERT INTO machine (room_id, name)
VALUES (1, 'Оплодотворитель'),
       (1, 'Оплодотворитель'),
       (1, 'Оплодотворитель'),
       (1, 'Бульон_со_сперматозоидами'),
       (1, 'Бульон_со_сперматозоидами'),
       (1, 'Бульон_со_сперматозоидами'),
       (1, 'Рентген'),
       (1, 'Рентген'),
       (1, 'Инкубатор'),
       (1, 'Инкубатор'),
       (1, 'Инкубатор'),
       (1, 'Морозилка'),
       (1, 'Морозилка'),
       (1, 'Глушитель_спиртом'),
       (1, 'Глушитель_спиртом'),
       (4, 'Дорожка_01'),
       (4, 'Дорожка_02'),
       (4, 'Дорожка_03'),
       (4, 'Дорожка_04'),
       (4, 'Дорожка_05'),
       (4, 'Дорожка_06'),
       (4, 'Дорожка_07'),
       (4, 'Дорожка_08'),
       (4, 'Дорожка_09'),
       (4, 'Дорожка_10'),
       (4, 'Дорожка_11'),
       (4, 'Дорожка_12'),
       (4, 'Дорожка_13'),
       (4, 'Дорожка_14'),
       (4, 'Дорожка_15');
INSERT INTO conditions_of_detention (machine_id, oxygen_amount, nutrients_amount, sex_hormone_dose, temperature, flips,
                                     immunization)
VALUES (16, 0.6, 0.6, 0.6, 15.0, false, false),
       (17, 0.8, 0.8, 1.0, 30.0, true, false),
       (18, 0.9, 0.6, 1.0, 25.0, true, true),
       (19, 0.9, 0.6, 1.0, 25.0, true, false),
       (20, 0.9, 0.6, 1.0, 25.0, false, true),
       (21, 0.9, 0.6, 1.0, 25.0, false, false),
       (22, 0.9, 0.6, 1.0, 25.0, true, false),
       (23, 0.9, 0.6, 1.0, 25.0, false, true),
       (24, 0.9, 0.6, 1.0, 15.0, true, false),
       (25, 0.9, 0.6, 1.0, 25.0, true, false),
       (26, 0.9, 0.6, 1.0, 25.0, false, true),
       (27, 0.9, 0.6, 1.0, 15.0, false, true),
       (28, 0.9, 0.6, 1.0, 25.0, true, false),
       (29, 0.9, 0.6, 1.0, 25.0, false, true),
       (30, 0.9, 0.6, 1.0, 15.0, true, true);