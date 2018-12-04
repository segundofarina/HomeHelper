import * as actionTypes from './actionTypes'

export const updateContact = (serviceType, date, description) =>{
    return {
        type: actionTypes.CONTACT_UPDATE,
        payload: {
            serviceType: serviceType,
            date: date,
            description: description,
        }
    }
}