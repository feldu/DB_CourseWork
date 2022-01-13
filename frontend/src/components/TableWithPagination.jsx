import {usePagination, useSortBy, useTable} from "react-table";
import {Box, Button, chakra, Flex, Input, Table, Tbody, Td, Text, Th, Thead, Tr} from "@chakra-ui/react";
import {TriangleDownIcon, TriangleUpIcon} from "@chakra-ui/icons";
import Select from "react-select";
import React from "react";

export default function TableWithPagination({columns, data}) {
    const {
        getTableProps, getTableBodyProps, headerGroups, prepareRow, page,
        canPreviousPage, canNextPage, pageOptions, pageCount, gotoPage, nextPage, previousPage, setPageSize,
        state: {pageIndex},
    } =
        useTable({columns, data}, useSortBy, usePagination);

    return (
        <Box>
            <Table borderWidth={1} borderRadius={14} boxShadow="lg" {...getTableProps()}>
                <Thead>
                    {headerGroups.map((headerGroup) => (
                        <Tr {...headerGroup.getHeaderGroupProps()}>
                            {headerGroup.headers.map((column) => (
                                <Th {...column.getHeaderProps(column.getSortByToggleProps())}>
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
                    {page.map((row, i) => {
                        prepareRow(row);
                        return (
                            <Tr {...row.getRowProps()}>
                                {row.cells.map((cell) => (
                                    <Td {...cell.getCellProps()}>
                                        {cell.render('Cell')}
                                    </Td>
                                ))}
                            </Tr>
                        );
                    })}
                </Tbody>
            </Table>
            <Flex>
                <Box m={3}>
                    <Button mx={1} onClick={() => gotoPage(0)} disabled={!canPreviousPage}>
                        {'<<'}
                    </Button>
                    <Button mx={1} onClick={() => previousPage()} disabled={!canPreviousPage}>
                        {'<'}
                    </Button>
                    <Button mx={1} onClick={() => nextPage()} disabled={!canNextPage}>
                        {'>'}
                    </Button>
                    <Button mx={1} mr={3} onClick={() => gotoPage(pageCount - 1)} disabled={!canNextPage}>
                        {'>>'}
                    </Button>
                    <Text mt={1} align={"center"}>{`Страница ${pageIndex + 1} из ${pageOptions.length}`}</Text>
                </Box>
                <Box m={3}>
                    <Text>{'Перейти к странице: '}
                        <Input
                            type="number"
                            defaultValue={pageIndex + 1}
                            onChange={e => {
                                const page = e.target.value ? Number(e.target.value) - 1 : 0;
                                gotoPage(page);
                            }}
                            w="144px"
                        />
                    </Text>
                </Box>
                <Box w={300} m={3}>
                    <Select
                        w={50}
                        placeholder={"Выберите размер страницы"}
                        onChange={e => setPageSize(Number(e.value))}
                        options={[5, 10, 20, 50, 100].map(pageSize => ({
                            value: pageSize,
                            label: `Показать ${pageSize}`
                        }))}
                    />
                </Box>
            </Flex>
        </Box>
    );
}