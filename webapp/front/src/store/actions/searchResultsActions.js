import * as actionTypes from './actionTypes'
import axios from 'axios'

export const searchResultsUpdateLoading = () => {
    return {
        type: actionTypes.SEARCH_RESULTS_UPDATE_LOADING,
    }
}

export const searchResultsUpdateError = () => {
    return {
        type: actionTypes.SEARCH_RESULTS_UPDATE_ERROR,
    }
}

export const searchResultsUpdateDone = (providers = [], currentPage) => {
    return {
        type: actionTypes.SEARCH_RESULTS_UPDATE_DONE,
        payload: {
            providers: providers,
            currentPage: currentPage
        }
    }
}

export const searchResultsUpdate = (serviceType, coords) => {
    return async dispatch => {
        dispatch(searchResultsUpdateLoading())

        let url = `/providers?st=${serviceType}&lat=${coords.lat}&lng=${coords.lng}`
 
        /* Async code to fetch new results */
        try {
            const ans = await axios.get(url)
            console.log(ans.data)
            dispatch(searchResultsUpdateDone(ans.data.providers, ans.data.page))
        } catch(error) {
            dispatch(searchResultsUpdateError())
        }
    }
}