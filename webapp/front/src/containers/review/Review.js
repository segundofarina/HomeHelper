import React, { Component } from 'react'
import styles from './Review.module.css'
import AppointmentDetails from '../../components/Review/AppointmentDetails/AppointmentDetails'
import WriteReview from '../../components/Review/WriteReview/WriteReview'
import { withRouter } from 'react-router-dom'
import queryString from 'query-string'
import BadRequest from '../../components/Errors/BadRequest/BadRequest'
import { connect } from 'react-redux'
import * as apiStatus from '../../store/apiStatus'
import axios from 'axios'
import Loading from '../../components/Status/Loading/Loading'
import ConnectionError from '../../components/Status/ConnectionError/ConnectionError'

class Review extends Component {
    state = {
        validUserId: false,
        id: 0,
        appointment: null,
        status: apiStatus.API_STATUS_NONE,
    }

    componentWillMount() {
        const queries = queryString.parse(this.props.location.search)
        if(queries.id){
            this.setState({
                validUserId: true,
                id: queries.id,
            })
        }
    }

    componentDidMount() {
        if(this.state.validUserId && this.props.appointments.length === 0 && this.state.status === apiStatus.API_STATUS_NONE) {
            /* Get appointment from the api */
            this.loadAppointmentFromApi()
        }
    }

    loadAppointmentFromApi = async () => {
        this.setState({status: apiStatus.API_STATUS_LOADING})
        try {
            const response = await axios.get('/users/appointments/'+this.state.id)
            this.setState({
                status: apiStatus.API_STATUS_DONE,
                appointment: response.data,
            })
        } catch(error) {
            if(error.response && (error.response.status === 404 || error.response.status === 403)) {
                this.setState({status: apiStatus.API_STATUS_ERROR_404})
            } else {
                this.setState({status: apiStatus.API_STATUS_ERROR})
            }
        }
    }
    

    render() {
        if(!this.state.validUserId) {
            return (<BadRequest />)
        }

        if(this.state.status === apiStatus.API_STATUS_LOADING ||
            (this.state.status === apiStatus.API_STATUS_NONE && this.props.appointments.length === 0)) {
            return (<Loading />)
        }

        if(this.state.status === apiStatus.API_STATUS_ERROR) {
            return(<ConnectionError reconnectHandler={this.loadAppointmentFromApi} />)
        }

        /* Get appointment provider data */
        let appointment = this.props.appointments.filter(appointment => {
            return appointment.id === parseInt(this.state.id)
        })[0]

        if(!appointment) {
            /* The page is reloaded get it from the api */
            if(!this.state.appointment || this.state.appointment.hasReview) {
                /* No results, user shouldn't be there */
                return (<BadRequest />)
            }
            appointment = this.state.appointment
        }

        return (
            <div className={styles.Review}>
                <div className={styles.Container}>
                    <AppointmentDetails name={`${appointment.provider.firstName} ${appointment.provider.lastName}`}
                                        serviceType={appointment.serviceType.name}
                                        date={appointment.date}
                                        description={appointment.description}
                                        calification={appointment.provider.generalCalification}
                                        img={appointment.provider.pictureUrl} />
                    <WriteReview appointmentId={appointment.id}
                                providerId={appointment.provider.id} />
                </div>
            </div>
        )   
    }
}

const mapStateToProps = state => {
    return {
        appointments: state.appointments.appointments
    }
}

export default connect(mapStateToProps)(withRouter(Review))