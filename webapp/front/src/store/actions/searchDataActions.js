import * as actionTypes from './actionTypes'

export const searchDataUpdate = (location, serviceType) => {
    return {
        type: actionTypes.SEARCH_DATA_UPDATE,
        payload: {
            location: location,
            serviceType: serviceType,
        }
    }   
}