import * as actionTypes from './actionTypes'
import axios from 'axios'

export const loadingServiceTypes = () => {
    return {
        type: actionTypes.SERVICE_TYPES_INIT_LOADING
    }
}

export const saveServiceTypes = (serviceTypesOptions) => {
    return {
        type: actionTypes.SERVICE_TYPES_INIT_DONE,
        payload: {
            serviceTypesOptions: serviceTypesOptions
        }
    }
}

export const serviceTypesError = () => {
    return {
        type: actionTypes.SERVICE_TYPES_INIT_ERROR
    }
}

export const serviceTypesInit = () => {
    return async dispatch => {
        dispatch(loadingServiceTypes())
        /* Async code */
        try {
            const resp = await axios.get('/serviceTypes')
            const serviceTypeOptions = resp.data.serviceTypesList.map(st => {
                return {
                    value: st.id,
                    name: st.name,
                }
            })
            dispatch(saveServiceTypes(serviceTypeOptions))
        } catch (error) {
            dispatch(serviceTypesError())
            console.log(error)
        }
    }
}