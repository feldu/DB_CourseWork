import React, {useMemo} from "react";
import TableWithPagination from "../../../../components/TableWithPagination";

export default function UserOvumTable({ovumList}) {
    const ovumTableData = useMemo(() =>
        ovumList.map(ovum => ({
            id: ovum.id,
            bud: ovum.bud ? "Да" : "Нет",
            fertilizationTime: ovum.fertilizationTime === null ? "Не оплодотворена" : ovum.fertilizationTime,
            embryoTime: ovum.embryoTime === null ? "Не зародыш" : ovum.embryoTime,
            babyTime: ovum.babyTime === null ? "Не ребенок" : ovum.embryoTime
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
    ], []);

    return (
        <TableWithPagination columns={columns} data={ovumTableData}/>
    );
}