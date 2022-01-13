import TableWithPagination from "../../../../components/TableWithPagination";
import React, {useMemo} from "react";
import {Heading} from "@chakra-ui/react";

export default function UsingTable({usingList}) {
    const data = useMemo(() =>
        usingList.map(entry => ({
            roomId: entry.machine.room.id,
            roomName: entry.machine.room.name,
            machineId: entry.machine.id,
            machineName: entry.machine.name,
            ovumContainerId: entry.ovumContainer.id,
            ovumContainerName: [{value: "OVUMRECEIVER", label: "Яйцеприемник"},
                {value: "BOTTLE", label: "Бутыль"}].find(o => o.value.includes(entry.ovumContainer.name)).label,
            startTime: entry.startTime,
            endTime: entry.endTime,
        })), [usingList]);

    const columns = useMemo(() => [
        {
            Header: 'Комната', columns: [
                {Header: '№', accessor: 'roomId'},
                {Header: 'Название', accessor: 'roomName'},
            ]
        },
        {
            Header: 'Машина', columns: [
                {Header: '№', accessor: 'machineId'},
                {Header: 'Название', accessor: 'machineName'},
            ]
        },
        {
            Header: 'Контейнер', columns: [
                {Header: '№', accessor: 'ovumContainerId'},
                {Header: 'Тип', accessor: 'ovumContainerName'},
            ]
        },
        {Header: 'Время начала', accessor: 'startTime'},
        {Header: 'Время завершения', accessor: 'endTime'},
    ], []);
    return (
        <>
            <Heading my={6} size="lg">Использование машин контейнерами яйцеклеток заказчика</Heading>
            <TableWithPagination data={data} columns={columns}/>
        </>
    )
}