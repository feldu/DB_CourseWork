import {FormControl, FormLabel} from "@chakra-ui/react";
import Select from 'react-select'
import React from "react";

export default function InputSelect({label, placeholder, options, onChangeHandler, isMulti = false, isRequired = false, w, mx}) {
    return (
        <FormControl w={w} mx={mx} my={6} isRequired={isRequired}>
            {label && <FormLabel>{label}</FormLabel>}
            <Select onChange={onChangeHandler}
                    isMulti={isMulti}
                    placeholder={placeholder}
                    options={options}
                    className={isMulti ? "basic-multi-select" : "basic-single"}
                    classNamePrefix="select"
            />
        </FormControl>
    );
}