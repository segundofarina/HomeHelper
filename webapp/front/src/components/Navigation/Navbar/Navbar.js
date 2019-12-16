import React from 'react'
import { Link } from 'react-router-dom'
import styles from './Navbar.module.css'
import hhLogo from '../../../assets/img/HHLogo.png'
import hhLogoPink from '../../../assets/img/HHLogoPink.png'
import MenuBtns from './MenuBtns/MenuBtns'
import { connect } from 'react-redux'
import * as userDataActions from '../../../store/actions/userDataActions'
import { withRouter } from 'react-router-dom'

const navBar = (props) => {

    let NavbarLinks = (<div className={styles.loginButton} onClick={props.onLoginClick}>Iniciar Sesion</div>)

    if(props.authenticated) {
        NavbarLinks = (
            <MenuBtns isProvider={props.isProvider}
                    showingProvider={props.showingProvider}
                    handleUseAsProvider={props.useAsProvider}
                    firstName={props.firstName}
                    handleLogOut={props.logOut}
                    img={props.img} />
        )
    }

    let title = (
        <Link className={styles.title} to="/">
            <img src={hhLogo} alt="logo" />
            <h1>Home-Helper</h1>
        </Link>
    )
    if(props.showingProvider) {
        title = (
            <Link className={styles.title} to="/provider">
                <img src={hhLogoPink} alt="logo" />
            </Link>
        )
    }

    return (
        <div className={styles.navBar}>
            {title}
            {NavbarLinks}
        </div>
    )
}

const mapStateToProps = state => {
    return {
        showingProvider: state.userData.showingProvider,
        authenticated: state.userData.authenticated,
        isProvider: state.userData.userData.isProvider,
        firstName: state.userData.userData.firstName,
        img: state.userData.userData.imgUrl,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        useAsProvider: (showingProvider) => dispatch(userDataActions.updateUsingProvider(showingProvider)),
        logOut: () => dispatch(userDataActions.logOut())
    }
}

export default connect(mapStateToProps, mapDispatchToProps, null, {pure: false})(withRouter(navBar))