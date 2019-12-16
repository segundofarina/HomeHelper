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
import * as userDataActions from '../../../store/actions/userDataActions'
import axios from 'axios'

class Appointments extends Component {
    state = {
        showingSection: 1,
        headers: [null, 'Name', 'Date', 'Address', 'Service Type', 'Status', null],
        loadingRowsActions: [],
    }

    mapStatusToType = {
        1: 'warning',
        2: 'info',
        3: 'success',
        4: 'danger'
    }
    
    componentWillMount() {
        if(!this.props.showingProvider) {
            this.props.setUsingProvider()
        }
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

    sendAppointmentAction = async (id, action) => {
        this.setState(prevState => {
            return {
                loadingRowsActions: [...prevState.loadingRowsActions, id]
            }
        })
        try {
            const response = await axios.put(`/providers/appointments/${id}`,{
                action: action,
            })
            if(response.status === 200) {
                this.props.appointmentUpdate(response.data)
            }
        } catch(error) {
            console.log(error)
        }
        this.setState(prevState => {
            return {
                loadingRowsActions: prevState.loadingRowsActions.filter(loadingId => loadingId !== id)
            }
        })
    }

    handleAcceptAppointment = (id) => {
        this.sendAppointmentAction(id, 'confirm')
    }

    handleRejectAppointment = (id) => {
        this.sendAppointmentAction(id, 'reject')
    }

    handelCompletedAppointment = (id) => {
        this.sendAppointmentAction(id, 'complete')
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
        if(this.state.showingSection === 1) {
            rows = this.props.appointments.filter(appointment => {
                return appointment.status.id === 1 ||  appointment.status.id === 2
            }).map(appointment => {
                let actionsBtns = null
                if(appointment.status.id === 1) {
                    actionsBtns = (
                        <Fragment>
                            <Button btnType='Small' btnColor='Danger' onClick={() => this.handleRejectAppointment(appointment.id)} className={styles.ActionBtns}>Reject</Button>
                            <Button btnType='Small' btnColor='Success' onClick={() => this.handleAcceptAppointment(appointment.id)} className={styles.AcceptBtn}>Accept</Button>
                        </Fragment>
                    )
                } else {
                    actionsBtns = (
                        <Button btnType='Small' btnColor='Warning' onClick={() => this.handelCompletedAppointment(appointment.id)} className={styles.ActionBtns}>Completed</Button>
                    )
                }

                if(this.state.loadingRowsActions.includes(appointment.id)) {
                    actionsBtns = (
                    <div className={styles.ActionsLoading}>
                        <div className={styles.Bounce1}></div>
                        <div className={styles.Bounce2}></div>
                        <div className={styles.Bounce3}></div>
                    </div>)
                }

                return {
                    id: appointment.id,
                    columns: [
                        (<img src={appointment.client.pictureUrl} 
                            onError={(ev)=>ev.target.src = defaultImg} 
                            className={styles.ProfilePicture}/>),
                        `${appointment.client.firstName}`,
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
                        (<img src={appointment.client.pictureUrl} 
                            onError={(ev)=>ev.target.src = defaultImg} 
                            className={styles.ProfilePicture}/>),
                        `${appointment.client.firstName} ${appointment.client.lastName}`,
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
        showingProvider: state.userData.showingProvider,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        appointmentsInit: () => dispatch(providerAppointmentsActions.providerAppointmentsInit()),
        setUsingProvider: () => dispatch(userDataActions.updateUsingProvider(true)),
        appointmentUpdate: (appointment) => dispatch(providerAppointmentsActions.providerAppointmentsUpdate(appointment)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Appointments)