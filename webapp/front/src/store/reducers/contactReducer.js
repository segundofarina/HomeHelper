import * as actionTypes from '../actions/actionTypes'


const initialState = {
    serviceType: null,
    date: null,
    description: null,
}

const reducer =  (state = initialState, action ) => {
    switch(action.type){
        case actionTypes.CONTACT_UPDATE:
            return {
                ...state,
                serviceType: action.payload.serviceType,
                date: action.payload.date,
                description: action.payload.description,
            }
        default:
            return state
    }
}

export default reducer