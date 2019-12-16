import React, { Component } from 'react'
import styles from './Search.module.css'
import Button from '../../UI/Button/Button'
import Input from '../../UI/Input/Input'
import Panel from '../../UI/Panel/Panel'
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'
import * as serviceTypesActions from '../../../store/actions/serviceTypesActions'
import * as searchDataActions from '../../../store/actions/searchDataActions'
import * as searchResultsActions from '../../../store/actions/searchResultsActions'
import GoogleAutocomplete from './GoogleAutocomplete/GoogleAutocomplete'
import { geocodeByAddress, getLatLng } from 'react-places-autocomplete'
import FormValidator from '../../../FormValidator/FormValidator'

class Search extends Component {

    /* Form validation */
    validator = new FormValidator([
        {
            field: 'location',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please fill a valid address'
        },{
            field: 'serviceType',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please select a service type'
        },{
            field: 'lat',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please fill a valid address'
        },{
            field: 'lng',
            method: 'isEmpty',
            validWhen: false,
            message: 'Please fill a valid address',
        }
    ])

    submitted = false

    state = {
        location: this.props.keepMemory ? this.props.searchData.location : '',
        serviceType: this.props.keepMemory ? this.props.searchData.serviceType : '',
        coords: {
            lat: this.props.keepMemory ? this.props.searchData.coords.lat : '',
            lng: this.props.keepMemory ? this.props.searchData.coords.lng : '',
        },
        validation: this.validator.valid(),
    }

    componentDidMount() {
        if(this.props.serviceTypesOptions.length === 0) {
            /* Fetch service types options only the first time */
            this.props.serviceTypesInit()
        }
    }

    locationChangeHandler = (value) => {
        this.setState({location: value})
    }

    locationSelectHandler = (address, placeId) => {
        this.setState({location: address})
        this.getLatLng(address)
    }

    getLatLng = async (address) => {
        try {
            const geoCode = await geocodeByAddress(address)
            const latLng = await getLatLng(geoCode[0])
            this.setState({coords: {
                lat: latLng.lat,
                lng: latLng.lng,
            }})
        } catch(error) {
            console.log('Error')
            console.log(error)
        }
    }

    googleErrorHandler = (status, clearSuggestions) => {
        console.log('Error')
    }

    serviceTypeChangeHandler = (event) => {
        this.setState({serviceType: event.target.value})
    }

    onSubmitSearchHandler = () => {
        const validation = this.validator.validate({
            location: this.state.location,
            serviceType: this.state.serviceType,
            lat: this.state.coords.lat,
            lng: this.state.coords.lng,
            validation: this.state.validation,
        })

        this.setState({validation})
        this.submitted = true
        if(validation.isValid) {
            this.props.searchDataUpdate(this.state.location, this.state.serviceType, this.state.coords)
            this.props.searchResultsUpdate(this.state.serviceType, this.state.coords)
            this.props.history.push(`/search?st=${this.state.serviceType}&lat=${this.state.coords.lat}&lng=${this.state.coords.lng}&addr=${btoa(this.state.location)}`)
        }
    }


    

    render () {
        const defaultValue = {
            value: '',
            name: 'Select a service type',
        }

        /* if the form has been submitted at least once
            then check validity every time we render                   
            otherwise just use what's in state */
        let validation = this.submitted ?              
                        this.validator.validate({
                            location: this.state.location,
                            serviceType: this.state.serviceType,
                            lat: this.state.coords.lat,
                            lng: this.state.coords.lng,
                            validation: this.state.validation,
                        }) : this.state.validation  

        const locationStyles = [styles.FormGroup]
        if(validation.location.isInvalid || validation.lat.isInvalid || validation.lng.isInvalid) {
            locationStyles.push(styles.ValidationError)
        }
        
        const serviceTypeStyles = [styles.FormGroup]
        if(validation.serviceType.isInvalid) {
            serviceTypeStyles.push(styles.ValidationError)
        }

        let locationValidationMsg = validation.location.message
        if(locationValidationMsg === '') {
            locationValidationMsg = validation.lat.message
        }
        if(locationValidationMsg === '') {
            locationValidationMsg = validation.lng.message
        }

        return (
            <Panel className={this.props.className}>
                <div className={locationStyles.join(' ')}>
                    <GoogleAutocomplete value={this.state.location}
                                        onChange={this.locationChangeHandler}
                                        onSelect={this.locationSelectHandler}
                                        onError={this.googleErrorHandler} />
                    <div className={styles.ValidationMsg}>{locationValidationMsg}</div> 
                </div>
                <div className={serviceTypeStyles.join(' ')}>
                    <Input inputType="select"
                            label="Tipo de servicio"
                            value={this.state.serviceType}
                            onChange={this.serviceTypeChangeHandler}
                            defaultValue={defaultValue}
                            options={this.props.serviceTypesOptions} />
                    <div className={styles.ValidationMsg}>{validation.serviceType.message}</div> 
                </div>
               <Button onClick={this.onSubmitSearchHandler}>
                    Buscar
                </Button>
            </Panel>
        )
    }
}

const mapStateToProps = state => {
    return {
        serviceTypesOptions: state.serviceTypes.serviceTypesOptions,
        searchData: state.searchData,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        serviceTypesInit: () => dispatch(serviceTypesActions.serviceTypesInit()),
        searchDataUpdate: (location, serviceType, coords) => dispatch(searchDataActions.searchDataUpdate(location, serviceType, coords)),
        searchResultsUpdate: (serviceType, coords) => dispatch(searchResultsActions.searchResultsUpdate(serviceType, coords)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Search))