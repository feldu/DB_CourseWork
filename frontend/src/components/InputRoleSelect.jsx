import {FormControl, Select} from "@chakra-ui/react";
import React from "react";

export default function InputRoleSelect({setValue}) {
    return (
        <FormControl mt={6} isRequired={true} w="fill">
            <Select onChange={e => setValue(e.target.value)}>
                <option disabled value="">Выберите профессию</option>
                <option defaultValue value="predeterminer">Предопределитель</option>
            </Select>
        </FormControl>
    )
}