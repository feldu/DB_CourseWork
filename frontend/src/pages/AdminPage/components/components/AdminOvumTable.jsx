import React, {useMemo} from "react";
import {chakra, Table, Tbody, Td, Th, Thead, Tr} from '@chakra-ui/react'
import {TriangleDownIcon, TriangleUpIcon} from "@chakra-ui/icons";
import {useSortBy, useTable} from "react-table";
import {useSelector} from "react-redux";

export default function AdminOvumTable() {
    const ovumList = useSelector(state => state.predeterminer.ovumByOrder);
    const ovumContainerTypes = [{value: "OVUMRECEIVER", label: "Яйцеприемник"},
        {value: "BOTTLE", label: "Бутыль"}];

    const ovumTableData = useMemo(() =>
        ovumList.map(ovum => ({
            id: ovum.id,
            bud: ovum.bud ? "Да" : "Нет",
            fertilizationTime: ovum.fertilizationTime === null ? "Не оплодотворена" : ovum.fertilizationTime,
            embryoTime: ovum.embryoTime === null ? "Не зародыш" : ovum.embryoTime,
            babyTime: ovum.babyTime === null ? "Не ребенок" : ovum.embryoTime,
            ovumContainerId: ovum.ovumContainer.id,
            ovumContainerName: ovumContainerTypes.find(o => o.value.includes(ovum.ovumContainer.name)).label,
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

    const {getTableProps, getTableBodyProps, headerGroups, rows, prepareRow} =
        useTable({columns, data: ovumTableData}, useSortBy);

    return (
        ovumList.length !== 0 &&
        <Table borderWidth={1} borderRadius={14} boxShadow="lg" {...getTableProps()}>
            <Thead>
                {headerGroups.map((headerGroup) => (
                    <Tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map((column) => (
                            <Th
                                {...column.getHeaderProps(column.getSortByToggleProps())}
                                isNumeric={column.isNumeric}
                            >
                                {column.render('Header')}
                                <chakra.span pl='4'>
                                    {column.isSorted ? (column.isSortedDesc ?
                                        <TriangleDownIcon aria-label='sorted descending'/>
                                        : <TriangleUpIcon aria-label='sorted ascending'/>) : null}
                                </chakra.span>
                            </Th>
                        ))}
                    </Tr>
                ))}
            </Thead>
            <Tbody {...getTableBodyProps()}>
                {rows.map((row) => {
                    prepareRow(row);
                    return (
                        <Tr {...row.getRowProps()}>
                            {row.cells.map((cell) => (
                                <Td {...cell.getCellProps()} isNumeric={cell.column.isNumeric}>
                                    {cell.render('Cell')}
                                </Td>
                            ))}
                        </Tr>
                    );
                })}
            </Tbody>
        </Table>
    );
}