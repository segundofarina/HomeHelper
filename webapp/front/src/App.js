import React, { Component } from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import ScrollToTop from './components/ScrollToTop/ScrollToTop'
import PrivateRoute from './components/PrivateRoute/PrivateRoute'
import { connect } from 'react-redux'
import styles from './App.module.css'
import Layout from './hoc/Layout/Layout'
import Home from './containers/home/Home'
import Search from './containers/search/Search'
import Profile from './containers/profile/Profile'
import Messages from './containers/messages/Messages'
import Login from './containers/login/Login'
import Appointments from './containers/appointments/Appointments'
import Review from './containers/review/Review'
import AppointmentConfirmed from './containers/appointmentConfirmed/AppointmentConfirmed'
import Forbidden from './components/Errors/Forbidden/Forbidden'
import Loading from './components/Status/Loading/Loading'
import CreateUser from './containers/createUser/CreateUser'

import ProviderDashboard from './containers/provider/dashboard/Dashboard'
import ProviderMessages from './containers/provider/messages/Messages'
import ProviderAppointments from './containers/provider/appointments/Appointments'
import ProviderProgress from './containers/provider/progress/Progress'
import ProviderEditProfile from './containers/provider/editProfile/EditProfile'

import * as userDataAction from './store/actions/userDataActions'

import Test from './containers/testing/testing'
import NotFound from './components/Errors/NotFound/NotFound';


class App extends Component {
  componentDidUpdate() {
    window.scrollTo(0,0);
  }

  /* If no user data in redux check for remember me token or already logged in user after refresh
   * While not user data in redux show loading page
   * Ask to the api for user data according to token in local storage
   * Show page */

  render() {
    const token = localStorage.getItem('jwtToken')
    if(this.props.token === '' && token) {
      this.props.setToken(token)
      return (<Loading />)
    }

    return (
      <BrowserRouter basename='/paw-2018a-4' >
        <ScrollToTop>
          <div className={styles.app}>
            <Switch>
              <Route path="/login" exact component={Login} />
              <Layout>
                <Switch>
                  <Route path="/" exact component={Home} />
                  <Route path="/search" exact component={Search} />
                  <Route path="/profile" exact component={Profile} />
                  
                  <PrivateRoute path="/messages" exact component={Messages} authenticated={this.props.isAuthenticated} />
                  <PrivateRoute path="/appointments" exact component={Appointments} authenticated={this.props.isAuthenticated} />
                  <PrivateRoute path="/writeReview" exact component={Review} authenticated={this.props.isAuthenticated} />
                  <PrivateRoute path="/appointmentConfirmed" exact component={AppointmentConfirmed} authenticated={this.props.isAuthenticated} />

                  <PrivateRoute path="/provider" exact component={ProviderProgress} authenticated={this.props.isProvider} />
                  <PrivateRoute path="/provider/messages" exact component={ProviderMessages} authenticated={this.props.isProvider} />
                  <PrivateRoute path="/provider/appointments" exact component={ProviderAppointments} authenticated={this.props.isProvider} />
                  <PrivateRoute path="/provider/editProfile" exact component={ProviderEditProfile} authenticated={this.props.isProvider} />

                  <PrivateRoute path="/signup" exact component={CreateUser} authenticated={!this.props.isAuthenticated} />

                  <Route path="/test" exact component={Test}/>
                  <Route path="/forbidden" exact component={Forbidden} />
                  <Route component={NotFound} />
                </Switch>
              </Layout>
            </Switch>
          </div>
        </ScrollToTop>
      </BrowserRouter>
   );
  }
}

const mapStateToProps = state => {
  return {
    token: state.userData.userData.token,
    isAuthenticated: state.userData.authenticated,
    isProvider: state.userData.userData.isProvider,
    isVerified: state.userData.userData.isVerified,
  }
}

const mapDispatchToProps = dispatch => {
  return {
    setToken: (token) => dispatch(userDataAction.setToken(token))
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
