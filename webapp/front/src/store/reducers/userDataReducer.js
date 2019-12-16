import * as actionTypes from '../actions/actionTypes'
import * as apiStatus from '../apiStatus'

const initialState = {
    showingProvider: false,
    authenticated: false, //just for testing
    apiCall: {
        status: apiStatus.API_STATUS_NONE,
        errorCode: '',
    },
    userData: {
        token: '',
        id: '',
        username: '',
        firstName: '',
        lastName: '',
        imgUrl: '',
        isProvider: false,
        isVerified: false,
    }
}

const reducer = (state = initialState, action) => {
    switch(action.type) {
        case actionTypes.USER_DATA_UPDATE_AUTHENTICATED:
            return {
                ...state,
                authenticated: action.payload.authenticated,
                jwt: action.payload.jwt,
            }
        case actionTypes.USER_DATA_UPDATE_USING_PROVIDER:
            return {
                ...state,
                showingProvider: action.payload.showingProvider,
            }
        case actionTypes.USER_DATA_UPDATE_IS_PROVIDER:
            return {
                ...state,
                isProvider: true,
            }
        case actionTypes.USER_DATA_LOADING_LOGIN:
            return {
                ...state,
                apiCall: {
                    status: apiStatus.API_STATUS_LOADING,
                    errorCode: '',
                }
            }
        case actionTypes.USER_DATA_ERROR_LOGIN:
            return {
                ...state,
                apiCall: {
                    status: apiStatus.API_STATUS_ERROR,
                    errorCode: action.payload.errorCode,
                },

            }
        case actionTypes.USER_DATA_PERFORM_LOGIN:
            return {
                ...state,
                apiCall: {
                    status: apiStatus.API_STATUS_DONE,
                    errorCode: '',
                },
                userData: {
                    ...state.userData,
                    ...action.payload.userData
                },
                authenticated: true,
            }
        case actionTypes.USER_DATA_SET_TOKEN:
            return {
                ...state,
                userData: {
                    ...state.userData,
                    ...action.payload.userData
                },
                authenticated: true,
            }
        case actionTypes.USER_DATA_LOGOUT:
            return initialState
        default:
            return state
    }
}

export default reducer