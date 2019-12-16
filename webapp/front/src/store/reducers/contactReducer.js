import * as actionTypes from '../actions/actionTypes'
import * as apiStatus from '../apiStatus'

const initialState = {
    serviceType: null,
    date: null,
    description: null,
    provider: null,
    apiStatus: apiStatus.API_STATUS_NONE,
}

const reducer =  (state = initialState, action ) => {
    switch(action.type){
        case actionTypes.CONTACT_UPDATE:
            return {
                ...state,
                serviceType: action.payload.serviceType,
                date: action.payload.date,
                description: action.payload.description,
                provider: action.payload.provider,
            }
        case actionTypes.CONTACT_SEND_LOADING:
            return {
                ...state,
                apiStatus: apiStatus.API_STATUS_LOADING,
            }
        case actionTypes.CONTACT_SEND_ERROR:
            return {
                ...state,
                apiStatus: apiStatus.API_STATUS_ERROR,
            }
        case actionTypes.CONTACT_SEND: 
            return {
                ...state,
                apiStatus: apiStatus.API_STATUS_DONE,
            }
        default:
            return state
    }
}

export default reducer