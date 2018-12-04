import React, { Component } from 'react'
import styles from './Login.module.css'
import { Link, withRouter } from 'react-router-dom'
import Logo from '../../assets/img/HHLogo.png'
import Button from '../../components/UI/Button/Button'
import { connect } from 'react-redux'
import * as userDataActions from '../../store/actions/userDataActions'
import * as apiStatus from '../../store/apiStatus'

class Login extends Component {

    state = {
        username: '',
        password: '',
        rememberMe: false,
    }

    handleUsernameChange = (event) => {
        this.setState({username: event.target.value})
    }

    handlePasswordChange = (event) => {
        this.setState({password: event.target.value})
    }

    handleRememberMeToggle = () => {
        this.setState((prevState) => {
            return {rememberMe: !prevState.rememberMe}
        })
    }

    handleSubmit = () => {
        /* Validate form */
        console.log(this.state)

        /* Sign up with the api */
        this.props.performLogin(this.state.username, this.state.password)
    }

    render() {
        let loadingElem = null
        if(this.props.apiCall.status === apiStatus.API_STATUS_LOADING) {
            loadingElem = (<div>Loading</div>)
        }

        let errorElem = null
        if(this.props.apiCall.status === apiStatus.API_STATUS_ERROR) {
            const errorCode = this.props.apiCall.errorCode
            if(errorCode === 404) {
                errorElem = (<div>Connection to server failed</div>)
            } else if(errorCode === 401) {
                errorElem = (<div>Bad credentials</div>)
            } else {
                errorElem = (<div>Oh oh error</div>)
            }
        }

        if(this.props.apiCall.status === apiStatus.API_STATUS_DONE) {
            this.props.history.replace('/')
        }

        return (
            <div className={styles.LoginContainer}>
                {loadingElem}
                {errorElem}
                <div className={styles.Form}>
                    <Link to="/" className={styles.Link}>
                        <img className={styles.Logo} src={Logo} alt="" />
                        <h2 className={styles.Title}>Home-Helper</h2>
                    </Link>
                    <input className={styles.Input}
                            onChange={this.handleUsernameChange}
                            type="text"
                            placeholder="Username"
                            value={this.state.username} />
                    <input className={styles.Input}
                            onChange={this.handlePasswordChange}
                            type="password"
                            placeholder="Password"
                            value={this.state.password} />
                    <div className={styles.Checkbox}>
                        <label>
                            <input type="checkbox" onClick={this.handleRememberMeToggle} value={this.state.rememberMe} />
                            Remember me!
                        </label>
                    </div>
                    <Button onClick={this.handleSubmit} className={styles.SignInBtn}>Log in</Button>
                    <div className={styles.Forgot}>
                        New User?
                        <Link to="/signup" className={styles.SignUp}>Register</Link>
                    </div>
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        apiCall: state.userData.apiCall,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        authenticateUser: () => dispatch(userDataActions.updateAuthenticated(true)),
        performLogin: (username, password) => dispatch(userDataActions.performLogin(username, password))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Login))