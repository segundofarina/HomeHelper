import React, { Component, Fragment } from 'react'
import styles from './Appointments.module.css'
import AppointmentsTable from '../../../components/Provider/Appointments/AppointmentsTable/AppointmetsTable'
import * as providerAppointmentsActions from '../../../store/actions/providerAppointmentsActions'
import { connect } from 'react-redux'
import * as apiStatus from '../../../store/apiStatus'
import Loading from '../../../components/Status/Loading/Loading'
import ConnectionError from '../../../components/Status/ConnectionError/ConnectionError'
import defaultImg from '../../../assets/img/defaultProfile.png'
import Badge from '../../../components/UI/Badge/Badge'
import Button from '../../../components/UI/Button/Button'
import EmptyTable from '../../../components/Provider/Appointments/EmptyTable/EmptyTable'
import MultiButton from '../../../components/UI/MultiButton/MultiButton'

class Appointments extends Component {
    state = {
        showingSection: 1,
        headers: [null, 'Name', 'Date', 'Address', 'Service Type', 'Status', null]
    }

    mapStatusToType = {
        1: 'warning',
        2: 'info',
        3: 'success',
        4: 'danger'
    }

    componentDidMount () {
        if(this.props.status === apiStatus.API_STATUS_NONE) {
            this.props.appointmentsInit()
        }
    }

    changeBtnHandler = (id) => {
        if(this.state.showingSection === id) {
            return
        }

        let headers = [null, 'Name', 'Date', 'Address', 'Service Type', 'Status', null]
        if(this.state.showingSection === 2) {
            headers = [null, 'Name', 'Date', 'Address', 'Service Type', 'Status']
        }
        this.setState({
            showingSection: id,
            headers: headers,
        })
    }

    handleAcceptAppointment = () => {

    }

    handleRejectAppointment = () => {

    }

    handelCompletedAppointment = () => {

    }

    render() {
        if(this.props.status === apiStatus.API_STATUS_NONE || this.props.status === apiStatus.API_STATUS_LOADING) {
            return (<Loading />)
        }

        if(this.props.status === apiStatus.API_STATUS_ERROR) {
            return (<ConnectionError reconnectHandler={this.props.appointmentsInit} />)
        }

        let title = 'Pending Appointments'        
        let noResultsDescription = 'No pending appointments...'

        if(this.state.showingSection === 2) {
            title='Completed Appointments'
            noResultsDescription = 'No completed appointments...'
        }

        /* In the api the appointment status are mapped as following:
         * Pending -> 1
         * Confirmed -> 2
         * Done -> 3
         * Reject -> 4
        */

        let rows = []
        if(this.state.showingPending) {
            rows = this.props.appointments.filter(appointment => {
                return appointment.status.id === 1 ||  appointment.status.id === 2
            }).map(appointment => {
                
                let actionsBtns = null
                if(appointment.status.id === 1) {
                    actionsBtns = (
                        <Fragment>
                            <Button btnType='Small' btnColor='Danger' onClick={this.handleRejectAppointment} className={styles.ActionBtns}>Reject</Button>
                            <Button btnType='Small' btnColor='Success' onClick={this.handleAcceptAppointment} className={styles.AcceptBtn}>Accept</Button>
                        </Fragment>
                    )
                } else {
                    actionsBtns = (
                        <Button btnType='Small' btnColor='Warning' onClick={this.handelCompletedAppointment} className={styles.ActionBtns}>Completed</Button>
                    )
                }

                return {
                    id: appointment.id,
                    columns: [
                        (<img src={defaultImg} alt="" className={styles.ProfilePicture} />),
                        `${appointment.user.firstName}`,
                        `${appointment.date}`,
                        `${appointment.address}`,
                        `${appointment.serviceType.name}`,
                        (<Badge type={this.mapStatusToType[appointment.status.id]}>{`${appointment.status.value}`}</Badge>),
                        (<div className={styles.ActionBtnsContainer}>{actionsBtns}</div>)
                    ]
                }
            })

        } else {
            rows = this.props.appointments.filter(appointment => {
                return appointment.status.id === 3 ||  appointment.status.id === 4
            }).map(appointment => {
                return {
                    id: appointment.id,
                    columns: [
                        (<img src={defaultImg} alt="" className={styles.ProfilePicture} />),
                        `${appointment.user.firstName} ${appointment.user.lastName}`,
                        `${appointment.date}`,
                        `${appointment.address}`,
                        `${appointment.serviceType.name}`,
                        (<Badge type={this.mapStatusToType[appointment.status.id]}>{`${appointment.status.value}`}</Badge>),
                    ]
                }
            })
 
        }

        const multiButtonItems = [{
            id: 1,
            text: 'Pending Appointments',
            onClick: () => this.changeBtnHandler(1),
        }, {
            id: 2,
            text: 'Completed Appointments',
            onClick: () => this.changeBtnHandler(2),
        }]

        if(rows.length === 0) {
            return (
                <div className={styles.Appointments}>
                    <div>
                        <h2 className={styles.SectionTitle}>Appointments</h2>
                        <MultiButton elements={multiButtonItems} active={this.state.showingSection} className={styles.Multibutton} />
                        <EmptyTable title={title}
                                        changeBtnOnClick={this.changeBtnHandler}
                                        description={noResultsDescription} />
                    </div>
               </div>
            )
        }

        return (
            <div className={styles.Appointments}>
                <div>
                    <h2 className={styles.SectionTitle}>Appointments</h2>
                    <MultiButton elements={multiButtonItems} active={this.state.showingSection} className={styles.Multibutton} />
                    <AppointmentsTable title={title}
                                        changeBtnOnClick={this.changeBtnHandler}
                                        headers={this.state.headers}
                                        rows={rows} />
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        status: state.providerAppointments.status,
        appointments: state.providerAppointments.appointments,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        appointmentsInit: () => dispatch(providerAppointmentsActions.providerAppointmentsInit()),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Appointments)