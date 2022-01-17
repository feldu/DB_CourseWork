--Триггер для подтверждения записи на операцию
CREATE TRIGGER checkAprovement
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