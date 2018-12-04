import * as actionTypes from '../actions/actionTypes'
import * as apiStatus from '../apiStatus'

const initialState = {
    providers: [],
    currentPage: 1,
    status: apiStatus.API_STATUS_NONE,
}

const reducer = (state = initialState, action) => {
    switch(action.type) {
        case actionTypes.SEARCH_RESULTS_UPDATE_LOADING:
            return {
                ...state,
                status: apiStatus.API_STATUS_LOADING,
            }
        case actionTypes.SEARCH_RESULTS_UPDATE_ERROR:
            return {
                ...state,
                status: apiStatus.API_STATUS_ERROR,
            }
        case actionTypes.SEARCH_RESULTS_UPDATE_DONE:
            return {
                ...state,
                providers: action.payload.providers,
                currentPage: action.payload.currentPage,
                status: apiStatus.API_STATUS_DONE,
            }
        default:
            return state
    }
}

export default reducer