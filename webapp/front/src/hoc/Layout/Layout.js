import React, {Component, Fragment} from 'react'
import Navbar from '../../components/Navigation/Navbar/Navbar'
import Footer from '../../components/Navigation/Footer/Footer'
import styles from './Layout.module.css'
import LoginModal from '../../components/LoginModal/LoginModal'
import { connect } from 'react-redux'
import * as loginModalActions from '../../store/actions/loginModalAction'

class Layout extends Component {

    handleShowLogin = () => {
        this.props.showLogin()
    }

    handleDismissLogin = () => {
        this.props.hideLogin()
    }

    render() {
        let login = null
        if(this.props.showingLogin) {
            login = (<LoginModal onDismiss={this.handleDismissLogin} />)
        }
        return (
            <Fragment>
                {login}
                <Navbar onLoginClick={this.handleShowLogin} />
                <div className={styles.MainContainer} >
                    {this.props.children}
                </div>
                <Footer />
            </Fragment>
        )
    }
}

const mapStateToProps = state => {
    return {
        showingLogin : state.loginModal.showingLogin,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        showLogin: () => dispatch(loginModalActions.showLogin()),
        hideLogin: () => dispatch(loginModalActions.hideLogin()),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Layout)