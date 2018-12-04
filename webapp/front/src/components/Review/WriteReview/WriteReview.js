import React, { Component, Fragment } from 'react'
import styles from './WriteReview.module.css'
import Panel from '../../UI/Panel/Panel'
import ReviewItem from './ReviewItem/ReviewItem'
import Button from '../../UI/Button/Button'
import { withRouter } from 'react-router-dom'
import * as apiStatus from '../../../store/apiStatus'
import Loader from './Loader/Loader'
import Alert from '../../UI/Alert/Alert'
import axios from 'axios'

class writeReview extends Component {
    state = {
        quality: 0,
        treatment: 0,
        price: 0,
        cleanness: 0,
        punctuality: 0,
        description: '',
        status: apiStatus.API_STATUS_NONE,
    }

    handleDescriptionChange = (event) => {
        this.setState({description: event.target.value})
    }

    handleQualityChange = (name, rating) => {
        this.setState((prevState) => {
            const newState = {...prevState}
            newState[name] = rating
            return newState
        })
    }

    /* TODO: Send appointment id to identify the review */
    sendReviewToApi = async () => {
        this.setState({status: apiStatus.API_STATUS_LOADING})
        try {
            const response = await axios.post('/reviews', {
                comment: this.state.description,
                scores: {
                    quality: this.state.quality,
                    treatment: this.state.treatment,
                    price: this.state.price,
                    cleanness: this.state.cleanness,
                    punctuality: this.state.punctuality,
                },
            })
            console.log(response)
            this.props.history.replace('/appointments')
        } catch (error) {
            console.log(error)
            this.setState({status: apiStatus.API_STATUS_ERROR})
        }
    }

    handleSendBtn = () => {
        /* Validate all inputs */

        /* Send data to the api */
        this.sendReviewToApi()
        //this.props.history.replace('/appointments')
    }

    render() {
        let errorMsg = null
        if(this.state.status === apiStatus.API_STATUS_ERROR) {
            errorMsg = (
                <Alert type='Danger'>Error while sending the review to the server. Please try again...</Alert>
            )
        }

        let reviewElem = (
            <Fragment>
                {errorMsg}
                <h5 className={styles.Subtitle}>Califica al proveedor</h5>
                <div className={styles.StarsContainer}>
                    <ReviewItem label='Quality:' rating={this.state.quality} handleChangeRating={(rating) => this.handleQualityChange('quality', rating)} />
                    <ReviewItem label='Treatment:' rating={this.state.treatment} handleChangeRating={(rating) => this.handleQualityChange('treatment', rating)} />
                    <ReviewItem label='Price:' rating={this.state.price} handleChangeRating={(rating) => this.handleQualityChange('price', rating)} />
                    <ReviewItem label='Cleanness:' rating={this.state.cleanness} handleChangeRating={(rating) => this.handleQualityChange('cleanness', rating)} />
                    <ReviewItem label='Punctuality:' rating={this.state.punctuality} handleChangeRating={(rating) => this.handleQualityChange('punctuality', rating)} />
                </div>
                <div className={styles.Description}>
                    <label>Write a review</label>
                    <textarea placeholder='Write a review...'
                            value={this.state.description}
                            onChange={this.handleDescriptionChange}></textarea>
                </div>
                <Button btnType='Small'
                        className={styles.SendBtn}
                        onClick={this.handleSendBtn}>
                        Send
                </Button>
            </Fragment>
       )

        if(this.state.status === apiStatus.API_STATUS_LOADING) {
            reviewElem = (<Loader />)
        }

        return (
            <Panel className={styles.Panel}>
                <div className={styles.Container}>
                    <h3 className={styles.Title}>Write a review</h3>
                    {reviewElem}
               </div>
            </Panel>
        ) 
    }
}

export default withRouter(writeReview)