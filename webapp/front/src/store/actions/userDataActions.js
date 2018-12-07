import * as actionTypes from './actionTypes'
import axios from 'axios'
import jwtDecode from 'jwt-decode'
import * as chatActions  from './chatActions'
import * as appointmentsActions from './appointmentsActions'
import * as providerAppointmentsActions from './providerAppointmentsActions'
import * as providerProfileActions from './providerProfileActions'

export const updateUsingProvider = (showingProvider) => {
    return {
        type: actionTypes.USER_DATA_UPDATE_USING_PROVIDER,
        payload: {
            showingProvider: showingProvider,
        },
    }
}

export const updateAuthenticated = (authenticated, jwt = '') => {
    return {
        type: actionTypes.USER_DATA_UPDATE_AUTHENTICATED,
        payload: {
            authenticated: authenticated,
            jwt: jwt,
        },
    }
}

export const updateIsProvider = () => {
    return {
        type: actionTypes.USER_DATA_UPDATE_IS_PROVIDER,
    }
}

export const loadingLogin = () => {
    return {
        type: actionTypes.USER_DATA_LOADING_LOGIN,
    }
}

export const errorLogin = (errorCode) => {
    return {
        type: actionTypes.USER_DATA_ERROR_LOGIN,
        payload: {
            errorCode: errorCode,
        }
    }
}

export const saveLogin = (userData) => {
    return {
        type: actionTypes.USER_DATA_PERFORM_LOGIN,
        payload: {
            userData: userData,
        }
    }
}

export const performLogin = (username, password) => {
    return async dispatch => {
        dispatch(loadingLogin())

        try {
            const response = await axios.post('/login', `username=${username}&password=${password}`, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })

            const token = response.headers['x-authorization']
            dispatch(saveLogin(getTokenInfo(token)))
            
            /* Save token to local storage */
            saveTokenToLocalStorage(token)
            axios.defaults.headers.common['X-Authorization'] = token

        } catch(error) {
            let errorCode = ''
            if(error.response) {
                errorCode = error.response.status
            }
            dispatch(errorLogin(errorCode))
        }
    }
}

const getTokenInfo = (token) => {
    const decoded = jwtDecode(token)
    
    return {
        id: parseInt(decoded.jti),
        username: decoded.sub,
        firstName: decoded.firstName,
        lastName: decoded.lastName,
        imgUrl: decoded.imgUrl,
        isProvider: decoded.isProvider === 'true',
        isVerified: decoded.isVerified === 'true',
        token: token,
    }
}

const saveTokenToLocalStorage = (token) => {
    localStorage.setItem('jwtToken', token)
}

const clearTokenFromLocalStorage = () => {
    localStorage.removeItem('jwtToken')
}

export const setToken = (token) => {
    axios.defaults.headers.common['X-Authorization'] = token
    saveTokenToLocalStorage(token)
    return {
        type: actionTypes.USER_DATA_SET_TOKEN,
        payload: {
            userData: getTokenInfo(token)
        }
    }
}

export const logOut = () => {
    clearTokenFromLocalStorage()
    clearReduxState()
    axios.defaults.headers.common['X-Authorization'] = ''
    return {
        type: actionTypes.USER_DATA_LOGOUT,
    }
}

const clearReduxState = () => {
    return dispatch => {
        dispatch(chatActions.chatClearState())
        dispatch(appointmentsActions.appointmentsClearState())
        dispatch(providerAppointmentsActions.providersAppointmentsClearState())
        dispatch(providerProfileActions.providerClearState())
    }
}