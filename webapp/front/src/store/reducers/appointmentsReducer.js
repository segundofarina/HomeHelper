import * as actionTypes from '../actions/actionTypes'
import * as apiStatus from '../apiStatus'

const initialState = {
    appointments: [],
    status: apiStatus.API_STATUS_NONE,
}

const reducer = (state = initialState, action) => {
    switch(action.type) {
        case actionTypes.APPOINTMENTS_INIT_LOADING:
            return {
                ...state,
                status: apiStatus.API_STATUS_LOADING,
            }
        case actionTypes.APPOINTMENTS_INIT_ERROR:
            return {
                ...state,
                status: apiStatus.API_STATUS_ERROR,
            }
        case actionTypes.APPOINTMENTS_INIT_DONE:
            return {
                ...state,
                status: apiStatus.API_STATUS_DONE,
                appointments: action.payload.appointments,
            }
        case actionTypes.APPOINTMENTS_SAVE_APPOINTMENT:
            return {
                ...state,
                appointments: saveAppointment(action.payload.appointment, state.appointments),
            }
        case actionTypes.APPOINTMENTS_CLEAR_STATE:
            return initialState
        default:
            return state
    }
}

const saveAppointment = (appointment, appointments) => {
    const newAppointments = []
    let added = false
    appointments.forEach(oldAppointment => {
        if(oldAppointment.id === appointment.id) {
            newAppointments.push(appointment)
            added = true
        } else {
            newAppointments.push(oldAppointment)
        }
    })

    if(!added) {
        newAppointments.push(appointment)
    }
    return newAppointments
}

export default reducer