import * as actionTypes from '../actions/actionTypes'
import * as apiStatus from '../apiStatus'

const initialState = {
    serviceTypesOptions: [],
    status: apiStatus.API_STATUS_NONE
}

const reducer = (state = initialState, action) => {
    switch(action.type) {
        case actionTypes.SERVICE_TYPES_INIT_DONE:
            return {
                ...state,
                serviceTypesOptions: action.payload.serviceTypesOptions,
                status: apiStatus.API_STATUS_DONE,
            }
        case actionTypes.SERVICE_TYPES_INIT_LOADING:
            return {
                ...state,
                status: apiStatus.API_STATUS_LOADING,
            }
        case actionTypes.SERVICE_TYPES_INIT_ERROR:
            return {
                ...state,
                status: apiStatus.API_STATUS_ERROR,
            }
        default:
            return state
    }
}

export default reducer