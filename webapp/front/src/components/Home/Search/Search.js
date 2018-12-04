import React, { Component } from 'react'
import Button from '../../UI/Button/Button'
import Input from '../../UI/Input/Input'
import Panel from '../../UI/Panel/Panel'
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'
import * as serviceTypesActions from '../../../store/actions/serviceTypesActions'
import * as searchDataActions from '../../../store/actions/searchDataActions'
import * as searchResultsActions from '../../../store/actions/searchResultsActions'

class Search extends Component {

    state = {
        location: this.props.keepMemory ? this.props.searchData.location : '',
        serviceType: this.props.keepMemory ? this.props.searchData.serviceType : '',
    }

    componentDidMount() {
        if(this.props.serviceTypesOptions.length === 0) {
            /* Fetch service types options only the first time */
            this.props.serviceTypesInit()
        }
    }

    locationChangeHandler = (event) => {
        this.setState({location: event.target.value})
    }

    serviceTypeChangeHandler = (event) => {
        this.setState({serviceType: event.target.value})
    }

    onSubmitSearchHandler = () => {
        /* Validate form fields */
        this.props.searchDataUpdate(this.state.location, this.state.serviceType)
        this.props.searchResultsUpdate(this.state.location, this.state.serviceType)
        this.props.history.push('/search')
    }

    render () {
        const defaultValue = {
            value: '',
            name: 'Select a service type',
        }

        return (
            <Panel className={this.props.className}>
                <Input inputType="input"
                    label="Zona:"
                    type="text"
                    placeholder="Ingrese su direccion"
                    onChange={this.locationChangeHandler}
                    value={this.state.location} />
                <Input inputType="select"
                    label="Tipo de servicio"
                    value={this.state.serviceType}
                    onChange={this.serviceTypeChangeHandler}
                    defaultValue={defaultValue}
                    options={this.props.serviceTypesOptions} />
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
        searchDataUpdate: (location, serviceType) => dispatch(searchDataActions.searchDataUpdate(location, serviceType)),
        searchResultsUpdate: (location, serviceType) => dispatch(searchResultsActions.searchResultsUpdate(location, serviceType)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Search))