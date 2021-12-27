import React from "react";

import Flex from "@chakra-ui/core/dist/Flex";
import AuthHeader from "../../components/AuthHeader";
import SignInForm from "./components/SignInForm";


export default function SignInPage() {
    return (
        <Flex direction="column">
            <AuthHeader buttonText={"Зарегистрироваться"} path={'/auth/signup'}/>
            <SignInForm/>
        </Flex>
    );
}
