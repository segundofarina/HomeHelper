import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import axios from 'axios'
import { createStore, combineReducers, applyMiddleware, compose } from 'redux'
import { Provider } from 'react-redux'
import thunk from 'redux-thunk'
import serviceTypesReducer from './store/reducers/serviceTypesReducer'
import searchDataReducer from './store/reducers/searchDataReducer'
import searchResultsReducer from './store/reducers/searchResultsReducer'
import chatReducer from './store/reducers/chatReducer'
import profileReducer from './store/reducers/profileReducer'
import contactReducer from './store/reducers/contactReducer'
import appointmentsReducer from './store/reducers/appointmentsReducer'
import userDataReducer from './store/reducers/userDataReducer'
import providerAppointmentsReducer from './store/reducers/providerAppointmentsReducer'
import providerProfileReducer from './store/reducers/providerProfileReducer'
import loginModalReducer from './store/reducers/loginModalReducer'
import providerReviewsReducer from './store/reducers/providerReviewsReducer'

axios.defaults.baseURL = 'http://localhost:8080/api'
//axios.defaults.baseURL = 'http://10.0.0.53:8080/api'
//axios.defaults.baseURL = 'http://pawserver.it.itba.edu.ar/paw-2018a-4/api'
/*TODO profiles*/ 

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const rootReducer = combineReducers({
    serviceTypes: serviceTypesReducer,
    searchData: searchDataReducer,
    searchResults: searchResultsReducer,
    chat: chatReducer,
    profile: profileReducer,
    contact: contactReducer,
    appointments: appointmentsReducer,
    userData: userDataReducer,
    providerAppointments: providerAppointmentsReducer,
    providerProfile: providerProfileReducer,
    loginModal: loginModalReducer,
    providerReviews: providerReviewsReducer,
})

const store = createStore(rootReducer, composeEnhancers(applyMiddleware(thunk)))

ReactDOM.render(<Provider store={store}><App /></Provider>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
