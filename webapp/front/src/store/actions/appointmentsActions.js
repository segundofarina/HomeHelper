import * as actionTypes from './actionTypes'
import axios from 'axios'

export const appointmentsLoading = () => {
    return {
        type: actionTypes.APPOINTMENTS_INIT_LOADING,
    }
}

export const appointmentsError = () => {
    return {
        type: actionTypes.APPOINTMENTS_INIT_ERROR,
    }
}

export const saveAppointments = (appointments) => {
    return {
        type: actionTypes.APPOINTMENTS_INIT_DONE,
        payload: {
            appointments: appointments,
        },
    }
}

export const appointmentsInit = () => {
    return async dispatch => {
        dispatch(appointmentsLoading())
        try {
            const response = await axios.get('/user/appointments')
            dispatch(saveAppointments(response.data.appointments))
        } catch(error) {
            console.log(error)
            dispatch(appointmentsError())
        }
    }
}