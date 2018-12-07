import React, { Component } from 'react'
import styles from './Progress.module.css'
import Scores from '../../../components/Provider/Progress/Scores/Scores'
import { connect } from 'react-redux'
import * as userDataActions from '../../../store/actions/userDataActions'
import * as providerReviews from '../../../store/actions/providerReviewsActions'
import * as apiStatus from '../../../store/apiStatus'
import Loading from '../../../components/Status/Loading/Loading'
import ConnectionError from '../../../components/Status/ConnectionError/ConnectionError'

class Progress extends Component {
    state = {
    }

    componentWillMount() {
        if(!this.props.showingProvider) {
            this.props.setUsingProvider()
        }
    }

    componentDidMount() {
        if(this.props.status === apiStatus.API_STATUS_NONE) {
            this.props.reviewsInit()
        }
    }

    render() {
        if(this.props.status === apiStatus.API_STATUS_NONE || this.props.status === apiStatus.API_STATUS_LOADING) {
            return (<Loading />)
        }

        if(this.props.status === apiStatus.API_STATUS_ERROR) {
            return (<ConnectionError reconnctHandler={this.props.reviewsInit} />)
        }

        return (
            <div className={styles.Progress}>
                <div>
                    <h2 className={styles.SectionTitle}>Progress</h2>
                    <div className={styles.Container}>
                        <Scores />
                    </div>
               </div>
           </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        showingProvider: state.userData.showingProvider,
        reviews: state.providerReviews.reviews,
        status: state.providerReviews.status,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        setUsingProvider: () => dispatch(userDataActions.updateUsingProvider(true)),
        reviewsInit: () => dispatch(providerReviews.providerReviewsInit()),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Progress)