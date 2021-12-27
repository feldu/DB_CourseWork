import React from "react";
import Flex from "@chakra-ui/core/dist/Flex";
import AuthHeader from "../../components/AuthHeader";
import SignUpForm from "./components/SignUpForm";


export default function SignUpPage() {
    return (
        <Flex direction="column">
            <AuthHeader buttonText={"Войти"} path={'/auth/signin'}/>
            <SignUpForm/>
        </Flex>
    );
}