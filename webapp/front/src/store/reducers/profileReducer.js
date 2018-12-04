import * as actionTypes from '../actions/actionTypes'
import { API_STATUS_LOADING, API_STATUS_NONE, API_STATUS_DONE, API_STATUS_ERROR } from '../apiStatus';



const initialState = {
    provider: null,
    status: API_STATUS_NONE,
}

const reducer = (state = initialState, action )=>{
    switch(action.type){
        case actionTypes.PROFILE_UPDATE_PROFILE:
            return {
                ...state,
                provider: action.payload.provider
            }
        case actionTypes.PROFILE_INIT_LOADING:
            return{
                ...state,
                status:API_STATUS_LOADING
            }
        case actionTypes.PROFILE_INIT_ERROR:
            return{
                ...state,
                status:API_STATUS_ERROR
            }
        case actionTypes.PROFILE_INIT_DONE:
            return{
                ...state,
                status: API_STATUS_DONE,
                provider: action.payload.profile,
            }    
        default:
            return state
    }
}

export default reducer