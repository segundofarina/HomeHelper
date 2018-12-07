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
        case actionTypes.PROVIDER_APPOINTMENTS_UPDATE:
            return {
                ...state,
                appointments: updateAppointment(action.payload.appointment, state.appointments),
            }
        case actionTypes.PROVIDER_APPOINTMENTS_CLEAR_STATE:
            return initialState
        default: 
            return state
    }
}

const updateAppointment = (appointment, appointments) => {
    const newAppointments = []
    
    appointments.forEach(oldAppointment => {
        if(oldAppointment.id === appointment.id) {
            newAppointments.push(appointment)
        } else {
            newAppointments.push(oldAppointment)
        }
    })
    
    return newAppointments
}

export default reducer