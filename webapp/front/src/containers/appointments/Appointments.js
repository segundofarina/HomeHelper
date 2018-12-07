import React, { Component } from 'react'
import styles from './Appointments.module.css'
import AppointmentTable from '../../components/Appointments/AppointmentTable/AppointmentTable'
import defaultImg from '../../assets/img/defaultProfile.png'
import { connect } from 'react-redux'
import { appointmentsInit } from '../../store/actions/appointmentsActions'
import * as apiStatus from '../../store/apiStatus'
import Loading from '../../components/Status/Loading/Loading'
import ConnectionError from '../../components/Status/ConnectionError/ConnectionError'

class Appointments extends Component {

    componentDidMount() {
        if(this.props.status === apiStatus.API_STATUS_NONE) {
            this.props.appointmentsInit()
        }
    }

    mapStatusToType = {
        1: 'warning',
        2: 'info',
        3: 'success',
        4: 'danger'
    }

    render () {
        /* Check if api is loading */
        if(this.props.status === apiStatus.API_STATUS_NONE || this.props.status === apiStatus.API_STATUS_LOADING) {
            return (
                <Loading />
            )
        }

        /* Check if there is an error with the api */
        if(this.props.status === apiStatus.API_STATUS_ERROR) {
            return (
                <ConnectionError reconnectHandler={this.props.appointmentsInit} />
            )
        }

        /* this.pros.status === apiStatus.API_STATUS_DONE */
        const pendingColumnsHeaders = [null, 'Service Type', 'Name', 'Date', 'Status']
       
        /* In the api the appointment status are mapped as following:
         * Pending -> 1
         * Confirmed -> 2
         * Done -> 3
         * Reject -> 4
        */

        const pendingRows = this.props.appointments.filter(appointment => {
            return appointment.status.id === 1 || appointment.status.id === 2
        }).map(appointment => {
            return {
                id: appointment.id,
                columns: [
                    (<img src={appointment.provider.pictureUrl} 
                        onError={(ev)=>ev.target.src = defaultImg} 
                        className={styles.ProfilePicture}/>),
                    `${appointment.serviceType.name}`,
                    `${appointment.provider.firstName}`,
                    `${appointment.date}`,
                    {type: this.mapStatusToType[appointment.status.id], value: `${appointment.status.value}`}
                ]
            }
        })

        const doneColumnsHeaders = [null, 'Service Type', 'Name', 'Date', 'Status', null]

        const doneRows = this.props.appointments.filter(appointment => {
            return appointment.status.id === 3 || appointment.status.id === 4
        }).map(appointment => {
            const reviewBtn = appointment.hasReview ? null : appointment.id
            return {
                id: appointment.id,
                columns: [
                    (<img src={appointment.provider.pictureUrl} 
                        onError={(ev)=>ev.target.src = defaultImg} 
                        className={styles.ProfilePicture}/>),
                    `${appointment.serviceType.name}`,
                    `${appointment.provider.firstName} ${appointment.provider.lastName}`,
                    `${appointment.date}`,
                    {type: this.mapStatusToType[appointment.status.id], value: `${appointment.status.value}`},
                    reviewBtn
                ]
            }
        })

        return (
            <div className={styles.Appointments}>
                <AppointmentTable className={styles.Content}
                                    title="Pending Appointments"
                                    headers={pendingColumnsHeaders}
                                    rows={pendingRows}
                                    emptyDescription='No pending appointments...' />
                <AppointmentTable className={styles.Content}
                                    title="Completed Appointments"
                                    headers={doneColumnsHeaders}
                                    rows={doneRows}
                                    emptyDescription='No completed appointments...' />
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        appointments: state.appointments.appointments,
        status: state.appointments.status,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        appointmentsInit: () => dispatch(appointmentsInit())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Appointments)