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


class Contact extends Component {

    state = {
        serviceType: this.props.defaultServiceType.value,
        date: new Date(),
        description: "",
    }

    dayPickerRef = React.createRef();


    onDateClickHandler = (day, modifiers, e)=>{
        this.setState({date:day})
    }

    serviceTypeClickHandler = (event)=>{
        this.setState({serviceType:event.target.value})
    }

    descriptionChangeHandler = (event)=>{
        this.setState({description:event.target.value})
    }

    contactClickHandler = ()=>{
        this.props.updateContact(this.state.serviceType,this.state.date,this.state.description)
    }

    showDayPicker= ()=>{
        this.dayPickerRef.current.getInput().focus()
    }
    hideDayPicker = ()=>{
        this.dayPickerRef.current.showDayPicker()
    }

    
    render(){
        return(<Panel className={styles.Panel}>
                <h4>Contactese con {this.props.providerName}</h4>
                <Input inputType="select"
                    label="Tipo de servicio"
                    value={this.props.serviceTypes}
                    onChange={this.serviceTypeClickHandler}
                    defaultValue={this.props.defaultServiceType}
                    options={this.props.serviceTypesOptions}
                    className={styles.Input} />
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
                        
                <Input inputType="textarea"
                    label="Descripcion:"
                    className={styles.InputText}
                    onChange={this.descriptionChangeHandler} />

                <Button 
                    className={styles.Button}
                    onClick={this.contactClickHandler}>
                    Contactar
                </Button>
        </Panel>
        )}
}

const mapStateToProp = (state) =>{
    return {
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        updateContact: (serviceType,date,description) =>dispatch(ContactActions.updateContact(serviceType,date,description)),

    }
}

export default connect(null,mapDispatchToProps)(Contact)