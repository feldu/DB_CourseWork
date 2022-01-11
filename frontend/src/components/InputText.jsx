import {FormControl, FormLabel, Input} from "@chakra-ui/react";
import React from "react";

export default function InputText({label, placeholder, value, setValue, w, mx}) {
    return (
        <FormControl my={6} isRequired={true} w={w} mx={mx}>
            {label && <FormLabel>{label}</FormLabel>}
            <Input type="text"
                   placeholder={placeholder}
                   value={value}
                   onChange={e => {
                       setValue(e.target.value)
                   }}
            />
        </FormControl>
    )
}