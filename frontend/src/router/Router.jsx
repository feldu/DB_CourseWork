import React from 'react';

import {BrowserRouter, Navigate, Routes} from "react-router-dom";
import {useSelector} from "react-redux";
import {Route} from "react-router";
import UserPage from "../pages/UserPage";
import SignInPage from "../pages/AuthPages/SignInPage";
import SignUpPage from "../pages/AuthPages/SignUpPage";


export function Router() {
    const currentUser = useSelector(state => state.authorization.currentUser);
    return (
        <BrowserRouter>
            <Routes>
                <Route exact path="/" element={<Navigate to="/auth/signin"/>}/>
                <Route path='/auth/signin/*' element={(currentUser && currentUser.token) ? <Navigate to='/areas'/> :
                    <SignInPage/>}/>
                <Route path='/auth/signup/*' element={(currentUser && currentUser.token) ? <Navigate to='/areas'/> :
                    <SignUpPage/>}/>
                <Route path='/areas' element={
                    (currentUser && currentUser.token) &&
                    <UserPage/>}/>
            </Routes>
        </BrowserRouter>
    )
}