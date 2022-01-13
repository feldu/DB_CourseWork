import TableWithPagination from "../../../../components/TableWithPagination";
import React, {useMemo} from "react";
import {Heading} from "@chakra-ui/react";

export default function AddingTable({addingList}) {
    const data = useMemo(() =>
        addingList.map(entry => ({
            ovumContainerId: entry.ovumContainer.id,
            ovumContainerName: [{value: "OVUMRECEIVER", label: "Яйцеприемник"},
                {value: "BOTTLE", label: "Бутыль"}].find(o => o.value.includes(entry.ovumContainer.name)).label,
            materialId: entry.material.id,
            materialName: entry.material.name,
            materialCurrentSize: entry.material.currentSize,
            materialQualityPartsPercentage: entry.material.qualityPartsPercentage,
            insertionTime: entry.insertionTime,
        })), [addingList]);

    const columns = useMemo(() => [
        {
            Header: 'Контейнер', columns: [
                {Header: '№', accessor: 'ovumContainerId'},
                {Header: 'Название', accessor: 'ovumContainerName'},
            ]
        },
        {
            Header: 'Материал', columns: [
                {Header: '№', accessor: 'materialId'},
                {Header: 'Название', accessor: 'materialName'},
                {Header: 'Размер', accessor: 'materialCurrentSize'},
                {Header: 'Процент качества', accessor: 'materialQualityPartsPercentage'},
            ]
        },
        {
            Header: 'Время', columns: [
                {Header: 'Время вставки', accessor: 'insertionTime'}
            ]
        },
    ], []);
    return (
        <>
            <Heading my={6} size="lg" textAlign="center">Добавление материалов в контейнеры заказа</Heading>
            <TableWithPagination data={data} columns={columns}/>
        </>
    )
}