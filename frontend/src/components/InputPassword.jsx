import {Button, FormControl, FormLabel, Icon, Input, InputGroup, InputRightElement} from "@chakra-ui/react";
import React from "react";
import {ViewIcon, ViewOffIcon} from "@chakra-ui/icons";

export default function InputPassword({label, placeholder, value, setValue}) {
    const [show, setShow] = React.useState(false);
    return (
        <FormControl m6={6} isRequired={true}>
            {label && <FormLabel>{label}</FormLabel>}
            <InputGroup size='md'>
                <Input
                    type={show ? 'text' : 'password'}
                    placeholder={placeholder}
                    value={value}
                    onChange={e => {
                        setValue(e.target.value)
                    }}
                />
                <InputRightElement width='4.5rem'>
                    <Button h='1.75rem' size='sm' onClick={() => setShow(!show)}>
                        <Icon as={show ? ViewOffIcon : ViewIcon}/>
                    </Button>
                </InputRightElement>
            </InputGroup>
        </FormControl>
    )
}