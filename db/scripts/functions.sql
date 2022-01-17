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
    IF (v_volunteer != NEW.volunteer_id) THEN
        RAISE EXCEPTION 'Волонтер % не иммет дефекта %. (Возможно вы имели в виду волонтёра %)', NEW.volunteer_id, NEW.defect_id, v_volunteer;
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
CREATE OR REPLACE PROCEDURE cutMaterial(old_id INTEGER, new_name VARCHAR(64))
AS
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