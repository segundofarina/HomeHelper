import React from 'react'
import { Route, Redirect } from 'react-router-dom'

const privateRoute = ({component: Component, authenticated, ...rest}) => (
    <Route {...rest} render={(props) => (
        authenticated === true ?
            <Component {...props} /> :
            <Redirect to={{
                pathname: '/forbidden',
                state: {from: props.location}
            }} />
    )} />
)

export default privateRoute

