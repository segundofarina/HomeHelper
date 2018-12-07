import React, { Component } from 'react'
import styles from './Login.module.css'
import { Link, withRouter, Redirect } from 'react-router-dom'
import Logo from '../../assets/img/HHLogo.png'
import Button from '../../components/UI/Button/Button'
import { connect } from 'react-redux'
import * as userDataActions from '../../store/actions/userDataActions'
import * as apiStatus from '../../store/apiStatus'
import FormValidator from '../../FormValidator/FormValidator'
import Alert from '../../components/UI/Alert/Alert'

class Login extends Component {

    /* Form validator */
    validator = new FormValidator([
        {
            field: 'username',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please fill a username'
        },{
            field: 'password',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please fill a password'
        }
    ])

    submitted = false

    state = {
        username: '',
        password: '',
        //rememberMe: false,
        validation: this.validator.valid(),
    }

    handleUsernameChange = (event) => {
        this.setState({username: event.target.value})
    }

    handlePasswordChange = (event) => {
        this.setState({password: event.target.value})
    }
/*
    handleRememberMeToggle = () => {
        this.setState((prevState) => {
            return {rememberMe: !prevState.rememberMe}
        })
    }
*/
    handleSubmit = () => {
        const validation = this.validator.validate(this.state)

        this.setState({validation})
        this.submitted = true

        if(validation.isValid) {
            this.props.performLogin(this.state.username, this.state.password)
        }
    }

    render() {
        
        /* if the form has been submitted at least once
            then check validity every time we render                   
            otherwise just use what's in state */
        let validation = this.submitted ? this.validator.validate(this.state) : this.state.validation  

        let loadingElem = null
        if(this.props.apiCall.status === apiStatus.API_STATUS_LOADING) {
            loadingElem = (
                <div className={styles.StatusContainer}>
                    <div className={styles.StatusOverlay}></div>
                    <div className={styles.Status}>
                        <div className={styles.Loader}></div>
                        Loading
                    </div>
                </div>
            )
        }

        let errorElem = null
        if(this.props.apiCall.status === apiStatus.API_STATUS_ERROR) {
            const errorCode = this.props.apiCall.errorCode
            if(errorCode === 404) {
                errorElem = (<Alert type='Danger' className={styles.ErrorAlert}>Connection to server failed</Alert>)
            } else if(errorCode === 401) {
                errorElem = (<Alert type='Danger' className={styles.ErrorAlert}>Invalid username or password</Alert>)
            } else {
                errorElem = (<Alert type='Danger' className={styles.ErrorAlert}>It seems we got an unexpected error...</Alert>)
            }
        }

        if(this.props.apiCall.status === apiStatus.API_STATUS_DONE) {
            return (<Redirect to='/' />)
        }
   
        const usernameStyles = [styles.FormGroup]
        if(validation.username.isInvalid) {
            usernameStyles.push(styles.ValidationError)
        }

        const passwordStyles = [styles.FormGroup]
        if(validation.password.isInvalid) {
            passwordStyles.push(styles.ValidationError)
        }

        return (
            <div className={styles.LoginContainer}>
                <div className={styles.Form}>
                    <Link to="/" className={styles.Link}>
                        <img className={styles.Logo} src={Logo} alt="" />
                        <h2 className={styles.Title}>Home-Helper</h2>
                    </Link>
                    {errorElem}
                    <div className={usernameStyles.join(' ')}>
                        <input className={styles.Input}
                                onChange={this.handleUsernameChange}
                                type="text"
                                placeholder="Username"
                                value={this.state.username} />
                        <div className={styles.ValidationMsg}>{validation.username.message}</div> 
                    </div>
                    <div className={passwordStyles.join(' ')}>
                        <input className={styles.Input}
                                    onChange={this.handlePasswordChange}
                                    type="password"
                                    placeholder="Password"
                                    value={this.state.password} />
                        <div className={styles.ValidationMsg}>{validation.password.message}</div> 
                    </div>
                    <Button onClick={this.handleSubmit} className={styles.SignInBtn}>Log in</Button>
                    <div className={styles.Forgot}>
                        New User?
                        <Link to="/signup" className={styles.SignUp}>Register</Link>
                    </div>

                    {loadingElem}
                </div>
            </div>
        )
    }
}

/*
                    <div className={styles.Checkbox}>
                        <label>
                            <input type="checkbox" onClick={this.handleRememberMeToggle} value={this.state.rememberMe} />
                            Remember me!
                        </label>
                    </div>
*/

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