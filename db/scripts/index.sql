--индексы: много поиска, мало вставок и мало NULL значений

CREATE INDEX IF NOT EXISTS role_name ON role USING hash (name);

CREATE INDEX IF NOT EXISTS appointment_volunteer ON appointment USING hash (volunteer_id);

CREATE INDEX IF NOT EXISTS ovum_volunteer ON ovum USING hash (volunteer_id);

CREATE INDEX IF NOT EXISTS ovum_ovum_container ON ovum USING hash (ovum_container_id);

CREATE INDEX IF NOT EXISTS defect_ovum ON defect USING hash (ovum_id);

CREATE INDEX IF NOT EXISTS removal_volunteer_removal_time ON removal_volunteer_for_defect (removal_time);


