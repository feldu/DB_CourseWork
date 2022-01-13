import React, {useMemo} from "react";
import TableWithPagination from "../../../../components/TableWithPagination";

export default function AdminOvumTable({ovumList}) {
    const ovumTableData = useMemo(() =>
        ovumList.map(ovum => ({
            id: ovum.id,
            bud: ovum.bud ? "Да" : "Нет",
            fertilizationTime: ovum.fertilizationTime === null ? "Не оплодотворена" : ovum.fertilizationTime,
            embryoTime: ovum.embryoTime === null ? "Не зародыш" : ovum.embryoTime,
            babyTime: ovum.babyTime === null ? "Не ребенок" : ovum.embryoTime,
            ovumContainerId: ovum.ovumContainer.id,
            ovumContainerName: [{value: "OVUMRECEIVER", label: "Яйцеприемник"},
                {value: "BOTTLE", label: "Бутыль"}].find(o => o.value.includes(ovum.ovumContainer.name)).label,
            volunteerId: ovum.volunteer.id,
            volunteerName: ovum.volunteer.fullname,
        })), [ovumList]);

    const columns = useMemo(() => [
        {
            Header: 'Яйцеклетка', columns: [
                {Header: '№', accessor: 'id'},
                {Header: 'Является почкой', accessor: 'bud'},
                {Header: 'Время оплодотворения', accessor: 'fertilizationTime'},
                {Header: 'Время зародыша', accessor: 'embryoTime'},
                {Header: 'Время ребёнка', accessor: 'babyTime'},
            ]
        },
        {
            Header: 'Контейнер', columns: [
                {Header: '№', accessor: 'ovumContainerId'},
                {Header: 'Тип', accessor: 'ovumContainerName'},
            ]
        },
        {
            Header: 'Волонтер', columns: [
                {Header: '№', accessor: 'volunteerId'},
                {Header: 'ФИО', accessor: 'volunteerName'},
            ]
        }
    ], []);

    return (
        <TableWithPagination columns={columns} data={ovumTableData}/>
    );
}