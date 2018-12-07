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
import FormValidator from '../../../FormValidator/FormValidator';
import { connect } from 'react-redux'
import * as appointmentsActions from '../../../store/actions/appointmentsActions'

class writeReview extends Component {

    /* Review validation */
    validateScores = (scores, state) => {
        if(state.scores.quality === 0 || state.scores.treatment === 0 || state.scores.price === 0 ||
            state.scores.cleanness === 0 || state.scores.punctuality === 0) {
            return false
        }
        return true
    }


    validator = new FormValidator([
        {
            field: 'description',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please fill a description',
        },{
            field: 'scores',
            method: this.validateScores,
            validWhen: true,
            message: 'Please complete all scores for the provider'
        }
    ])

    submitted = false

    state = {
        quality: 0,
        treatment: 0,
        price: 0,
        cleanness: 0,
        punctuality: 0,
        description: '',
        status: apiStatus.API_STATUS_NONE,
        validation: this.validator.valid(),
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

    sendReviewToApi = async () => {
        this.setState({status: apiStatus.API_STATUS_LOADING})
        try {
            const response = await axios.post(`/providers/${this.props.providerId}/reviews`, {
                appointmentId: this.props.appointmentId,
                review: {
                    comment: this.state.description,
                    scores: {
                        quality: this.state.quality,
                        treatment: this.state.treatment,
                        price: this.state.price,
                        cleanness: this.state.cleanness,
                        punctuality: this.state.punctuality,
                    },
                }
            })
            if(response.status === 201) {
                this.props.updateAppointment(this.props.appointmentId)
                this.props.history.replace('/appointments')
            } else {
                this.setState({status: apiStatus.API_STATUS_ERROR})
            }
        } catch (error) {
            console.log(error)
            this.setState({status: apiStatus.API_STATUS_ERROR})
        }
    }

    handleSendBtn = () => {
        const validation = this.validator.validate({
            description: this.state.description,
            scores: {
                quality: this.state.quality,
                treatment: this.state.treatment,
                price: this.state.price,
                cleanness: this.state.cleanness,
                punctuality: this.state.punctuality,
            },
            validation: this.state.validation,
        })

        this.setState({validation})
        this.submitted = true
        if(validation.isValid) {
            this.sendReviewToApi()
        }
    }

    render() {
         /* if the form has been submitted at least once
            then check validity every time we render                   
            otherwise just use what's in state */
        const validation = this.submitted ?              
                        this.validator.validate({
                            description: this.state.description,
                            scores: {
                                quality: this.state.quality,
                                treatment: this.state.treatment,
                                price: this.state.price,
                                cleanness: this.state.cleanness,
                                punctuality: this.state.punctuality,
                            },
                            validation: this.state.validation,
                        }) : this.state.validation  

        const descriptionStyles = [styles.Description]
        if(validation.description.isInvalid) {
            descriptionStyles.push(styles.ValidationError)
        }

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
                <div className={styles.ValidationMsg}>{validation.scores.message}</div> 
                <div className={styles.StarsContainer}>
                    <ReviewItem label='Quality:' rating={this.state.quality} handleChangeRating={(rating) => this.handleQualityChange('quality', rating)} />
                    <ReviewItem label='Treatment:' rating={this.state.treatment} handleChangeRating={(rating) => this.handleQualityChange('treatment', rating)} />
                    <ReviewItem label='Price:' rating={this.state.price} handleChangeRating={(rating) => this.handleQualityChange('price', rating)} />
                    <ReviewItem label='Cleanness:' rating={this.state.cleanness} handleChangeRating={(rating) => this.handleQualityChange('cleanness', rating)} />
                    <ReviewItem label='Punctuality:' rating={this.state.punctuality} handleChangeRating={(rating) => this.handleQualityChange('punctuality', rating)} />
                </div>
                <div className={descriptionStyles.join(' ')}>
                    <label>Write a review</label>
                    <textarea placeholder='Write a review...'
                            value={this.state.description}
                            onChange={this.handleDescriptionChange}></textarea>
                    <div className={styles.ValidationMsg}>{validation.description.message}</div> 
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

const mapDispatchToProps = dispatch => {
    return {
        updateAppointment: (id) => dispatch(appointmentsActions.fetchAppointment(`/users/appointments/${id}`))
    }
}

export default connect(null, mapDispatchToProps)(withRouter(writeReview))