import React, {useMemo} from "react";
import TableWithPagination from "../../../../components/TableWithPagination";
import {Heading} from "@chakra-ui/react";

export default function MovingTable({movingList}) {
    const data = useMemo(() =>
        movingList.map(entry => ({
            roomId: entry.room.id,
            roomName: entry.room.name,
            ovumContainerId: entry.ovumContainer.id,
            ovumContainerName: [{value: "OVUMRECEIVER", label: "Яйцеприемник"},
                {value: "BOTTLE", label: "Бутыль"}].find(o => o.value.includes(entry.ovumContainer.name)).label,
            arrivalTime: entry.arrivalTime,
        })), [movingList]);

    const columns = useMemo(() => [
        {
            Header: 'Помещение', columns: [
                {Header: '№', accessor: 'roomId'},
                {Header: 'Название', accessor: 'roomName'},
            ]
        },
        {
            Header: 'Контейнер', columns: [
                {Header: '№', accessor: 'ovumContainerId'},
                {Header: 'Тип', accessor: 'ovumContainerName'},
            ]
        },
        {
            Header: 'Время', columns: [
                {Header: 'Время прибытия', accessor: 'arrivalTime'}
            ]
        },
    ], []);

    return (
        <>
            <Heading my={6} size="lg" textAlign="center">История перемещений контейнеров яйцеклеток заказчика</Heading>
            <TableWithPagination data={data} columns={columns}/>
        </>
    )
}