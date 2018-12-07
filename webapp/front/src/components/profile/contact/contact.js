import React, { Component } from 'react'
import Button from '../../UI/Button/Button'
import Input from '../../UI/Input/Input'
import Panel from '../../UI/Panel/Panel'
import styles from './contact.module.css'
import { faCalendar } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import MomentLocaleUtils, {formatDate,parseDate} from 'react-day-picker/moment'
import 'moment/locale/es'
import DayPickerInput from 'react-day-picker/DayPickerInput'
import 'react-day-picker/lib/style.css'
import dayPickerStyles from './dayPickerStyles.css'
import {connect} from 'react-redux'
import * as ContactActions from '../../../store/actions/contactActions'
import { withRouter } from 'react-router-dom'
import FormValidator from '../../../FormValidator/FormValidator';
import * as loginModalActions from '../../../store/actions/loginModalAction'

class Contact extends Component {
    isValidDate = (date, state) => {
        date = Date.parse(date)
        return !isNaN(date) ? true : false
    }

    yesterdayDate = new Date()
    yesterdayDate = new Date(this.yesterdayDate.setDate(this.yesterdayDate.getDate() - 1))
    validator = new FormValidator([
        {
            field: 'serviceType',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please select a service type',
        },
        {
            field: 'emptyDate',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please select a date',   
        },{
            field: 'date',
            method: this.isValidDate,
            validWhen: true,
            message: 'Please select a valid date',
        },
        {
            field: 'date',
            method: 'isAfter',
            args: [this.yesterdayDate.toDateString()],
            validWhen: true,
            message: 'Please select a valid date',
        },
        {
            field: 'description',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please enter a description',
        }
    ])

    submitted = false

    state = {
        serviceType: this.props.defaultServiceType.value,
        date: new Date(),
        description: "",
        serviceTypeText: this.props.defaultServiceType.name,
        dateSelected: false,
        validation: this.validator.valid(),
   }

    dayPickerRef = React.createRef();

    onDateClickHandler = (day, modifiers, e)=>{
        this.setState({date:day, dateSelected: true})
    }

    serviceTypeClickHandler = (event) => {
        this.setState({serviceType:event.target.value, serviceTypeText: event.target.options[event.target.selectedIndex].text})
    }

    descriptionChangeHandler = (event) => {
        this.setState({description:event.target.value})
    }

    contactClickHandler = () => {
        const validation = this.validator.validate({
            serviceType: this.state.serviceType,
            date: this.isValidDate(this.state.date) ? this.state.date.toDateString() : '',
            description: this.state.description,
            emptyDate: this.state.dateSelected ? 'selected' : '',
        })

        this.setState({validation})
        this.submitted = true
        if(validation.isValid) {
            if(!this.props.isAuthenticated) {
                this.props.showLogin()
                return
            }

            this.props.updateContact(this.state.serviceTypeText, convertDate(this.state.date), this.state.description, {
                firstName: this.props.provider.firstName,
                lastName: this.props.provider.lastName,
                score: this.props.provider.score,
                imgUrl: this.props.provider.imgUrl,
                id: this.props.provider.id,
            })
            this.props.sendAppointment({
                provider: { id: this.props.provider.id },
                serviceType: { id: this.state.serviceType },
                date: convertDate(this.state.date),
                address: this.props.searchedAddress,
                description: this.state.description,
            })//pasarle los datos para la api
            this.props.history.push('/appointmentConfirmed')
        }
    }

    showDayPicker= ()=>{
        this.dayPickerRef.current.getInput().focus()
    }
    hideDayPicker = ()=>{
        this.dayPickerRef.current.showDayPicker()
    }
    
    render(){
        /* if the form has been submitted at least once
            then check validity every time we render                   
            otherwise just use what's in state */
        let validation = this.submitted ?              
                        this.validator.validate({
                            serviceType: this.state.serviceType,
                            date: this.isValidDate(this.state.date) ? this.state.date.toDateString() : '',
                            description: this.state.description,
                            emptyDate: this.state.dateSelected ? 'selected' : '',
                        }) : this.state.validation  

        const serviceTypesStyles = [styles.FormGroup]
        if(validation.serviceType.isInvalid) {
            serviceTypesStyles.push(styles.ValidationError)
        }

        const dateStyles = [styles.FormGroup]
        let dateErrorMsg = ''
        if(validation.date.isInvalid) {
            dateStyles.push(styles.ValidationError)
            dateErrorMsg = validation.date.message
        }
        if(validation.emptyDate.isInvalid) {
            dateStyles.push(styles.ValidationError)
            dateErrorMsg = validation.emptyDate.message
        }

        const descriptionStyles = [styles.FormGroup]
        if(validation.description.isInvalid) {
            descriptionStyles.push(styles.ValidationError)
        }

        return(<Panel className={styles.Panel}>
                <h4>Contactese con {this.props.providerName}</h4>
                <div className={serviceTypesStyles.join(' ')}>
                    <Input inputType="select"
                        label="Tipo de servicio"
                        value={this.props.serviceTypes}
                        onChange={this.serviceTypeClickHandler}
                        defaultValue={this.props.defaultServiceType}
                        options={this.props.serviceTypesOptions}
                        className={styles.Input} />
                    <div className={styles.ValidationMsg}>{validation.serviceType.message}</div> 
                </div>
                <div className={dateStyles.join(' ')}>
                    <label className={styles.Date}>Fecha: </label>
                    <div className={styles.Calendar}>                   
                        <div className={styles.CalendarPicker}>
                            <DayPickerInput
                                className={dayPickerStyles.DayPicker}
                                inputProps={{ style: { width: '100%' } }}
                                ref={this.dayPickerRef}
                                onDayChange={this.onDateClickHandler}
                                selectedDays={this.state.date}
                                formatDate={formatDate}
                                parseDate={parseDate}
                                format="LL"
                                placeholder={"Seleccione una fecha ..."}
                                dayPickerProps={{
                                    locale: window.navigator.language,
                                    localeUtils: MomentLocaleUtils,
                                    disabledDays:  {before: new Date()},
                                }}
                            />
                        </div>
                        <div className={styles.CalendarIcon} onClick={this.showDayPicker} >
                            <FontAwesomeIcon icon={faCalendar}  />
                        </div>
                    </div>
                    <div className={styles.ValidationMsg}>{dateErrorMsg}</div> 
                </div>
                <div className={descriptionStyles.join(' ')}>
                    <Input inputType="textarea"
                        label="Descripcion:"
                        className={styles.InputText}
                        onChange={this.descriptionChangeHandler} />
                    <div className={styles.ValidationMsg}>{validation.description.message}</div> 
                </div>
                <Button 
                    className={styles.Button}
                    onClick={this.contactClickHandler}>
                    Contactar
                </Button>
        </Panel>
        )}
}

const mapStateToProps = state => {
    return {
        isAuthenticated: state.userData.authenticated,
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        updateContact: (serviceType, date, description, provider) => dispatch(ContactActions.updateContact(serviceType, date, description, provider)),
        sendAppointment: (appointment) => dispatch(ContactActions.sendAppointment(appointment)),
        showLogin: () => dispatch(loginModalActions.showLogin()),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Contact))


const convertDate = inputFormat => {
    function pad(s) { return (s < 10) ? '0' + s : s; }
    var d = new Date(inputFormat);
    return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('/');
}