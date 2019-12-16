import React, { Component } from 'react'
import styles from './Dashboard.module.css'
import { connect } from 'react-redux'
import * as userDataActions from '../../../store/actions/userDataActions'

class Dashboard extends Component {

    componentWillMount() {
        if(!this.props.showingProvider) {
            this.props.setUsingProvider()
        }
    }

    render() {
        return (
            <div className={styles.Dashboard}>
                Dashboard
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        showingProvider: state.userData.showingProvider,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        setUsingProvider: () => dispatch(userDataActions.updateUsingProvider(true)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Dashboard)