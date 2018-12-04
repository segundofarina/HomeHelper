import * as actionTypes from './actionTypes'
import axios from 'axios';


export const updateProfile = (provider) =>{
    return {
        type: actionTypes.PROFILE_UPDATE_PROFILE,
        payload: {
            provider: provider,
        }
    }
}

export const profileInitLoading = () =>{
    return {
        type: actionTypes.PROFILE_INIT_LOADING,
    }
}

export const profileInitError = () =>{
    return {
        type: actionTypes.PROFILE_INIT_ERROR,
    }
}

export const profileInitDone = (profile) =>{
    return {
        type: actionTypes.PROFILE_INIT_DONE,
        payload: {
            profile : profile,
        }
    }
}

export const profileInit = (id) =>{
    return async dispatch =>{
        dispatch(profileInitLoading())
        try{
            const results = await axios.get(`/providers/${id}`)
            dispatch(profileInitDone(results.data))
        }
        catch(error){
            console.log(error)
            dispatch(profileInitError())
        }


    }


}