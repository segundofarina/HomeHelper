import * as actionTypes from './../actions/actionTypes'

const initialState = {
    location: '',
    serviceType: '',
    coords: {
        lat: '',
        lng: '',
    }
}

const reducer = (state = initialState, action) => {
    switch(action.type) {
        case actionTypes.SEARCH_DATA_UPDATE:
            return {
                ...state,
                location: action.payload.location,
                serviceType: action.payload.serviceType,
                coords: action.payload.coords,
            }
        default:
            return state
    }
}

export default reducer