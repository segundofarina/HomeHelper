import * as actionTypes from './actionTypes'
import axios from 'axios'

export const providerAppointmentInitLoading = () => {
    return {
        type: actionTypes.PROVIDER_APPOINTMENTS_INIT_LOADING,
    }
}

export const providerAppointmentInitError = () => {
    return {
        type: actionTypes.PROVIDER_APPOINTMENTS_INIT_ERROR,
    }
}

export const providerAppointmentSave = (appointments) => {
    return {
        type: actionTypes.PROVIDER_APPOINTMENTS_INIT_DONE,
        payload: {
            appointments: appointments,
        }
    }
}

export const providerAppointmentsInit = () => {
    return async dispatch => {
        dispatch(providerAppointmentInitLoading())
        try {
            const response = await axios.get('/provider/appointments')
            dispatch(providerAppointmentSave(response.data.appointments))
        } catch(error) {
            console.log(error)
            dispatch(providerAppointmentInitError())
        }
    }
}