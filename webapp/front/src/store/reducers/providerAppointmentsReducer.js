import * as actionTypes from '../actions/actionTypes'
import * as apiStatus from '../apiStatus'

const initialState = {
    appointments: [],
    status: apiStatus.API_STATUS_NONE,
}

const reducer = (state = initialState, action) => {
    switch(action.type) {
        case actionTypes.PROVIDER_APPOINTMENTS_INIT_LOADING:
            return {
                ...state,
                status: apiStatus.API_STATUS_LOADING,
            }
        case actionTypes.PROVIDER_APPOINTMENTS_INIT_ERROR:
            return {
                ...state,
                status: apiStatus.API_STATUS_ERROR,
            }
        case actionTypes.PROVIDER_APPOINTMENTS_INIT_DONE:
            return {
                ...state, 
                status: apiStatus.API_STATUS_DONE,
                appointments: action.payload.appointments,
            }
        default: 
            return state
    }
}

export default reducer