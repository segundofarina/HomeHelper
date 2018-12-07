import React, { Component } from 'react'
import SearchComponent from '../../components/Home/Search/Search'
import styles from './Search.module.css'
import defaultImg from '../../assets/img/defaultProfile.png'
import Profile from '../../components/Search/result/profile'
import EmptySearch from '../../components/Search/EmptySearch/EmptySearch'
import ErrorSearch from '../../components/Search/ErrorSearch/ErrorSearch'
import NoResultsSearch from '../../components/Search/NoResultsSearch/NoResultsSearch'
import LoadingResults from '../../components/Search/LoadingResults/LoadingResults'
import { connect } from 'react-redux'
import * as apiStatus from '../../store/apiStatus'
import { withRouter } from 'react-router-dom'
import queryString from 'query-string'
import * as searchDataActions from '../../store/actions/searchDataActions'
import * as searchResultsActions from '../../store/actions/searchResultsActions'
import BadRequest from '../../components/Errors/BadRequest/BadRequest'

class Search extends Component {

    state = {
        searchedAddress: '',
        error: false,
    }

    componentDidMount() {
        const queries = queryString.parse(this.props.location.search)
        if(!queries.addr){
            this.setState({
                error:true,
            })
        }
        this.setState({searchedAddress: queries.addr})
    }

    render () {
        /* No address in url, showing bad request */
        if(this.state.error) {
            return (<BadRequest />)
        }

        /* if api status is none the user enter the page without pressing search btn.
            show error msg asking to do a search */
        let results = (<EmptySearch />)

        /* Check if there is value in the url to perform the search */
        if(this.props.searchResults.status === apiStatus.API_STATUS_NONE) {
            /* Get query params */
            const queryParams = queryString.parse(this.props.location.search)
            const serviceType = queryParams.st
            const location = queryParams.addr ? atob(queryParams.addr) : queryParams.addr
            const coords = {
                lat: queryParams.lat,
                lng: queryParams.lng,
            }

            /* If all values set update search form and call api */
            if(serviceType && location && coords.lat && coords.lng) {
                this.props.searchDataUpdate(location, serviceType, coords)
                this.props.searchResultsUpdate(serviceType, coords)
                return (<LoadingResults />)
            }
        }

        /* if api status is done show results. if no results show empty results error */
        if(this.props.searchResults.status === apiStatus.API_STATUS_DONE) {
            results = this.props.searchResults.providers.map(provider => {
                const serviceTypes = provider.aptitudes.map(apt => {
                    return apt.serviceType.name
                })
                return (<Profile className={styles.Profile}
                            name={`${provider.firstName} ${provider.lastName}`}
                            serviceTypes={serviceTypes}
                            description={provider.description}
                            img={provider.pictureUrl}
                            calification={provider.generalCalification}
                            id={provider.id}
                            key={provider.id}
                            searchedAddress={this.state.searchedAddress}
                            />)
            })

            if(this.props.searchResults.providers.length === 0) {
                results = (<NoResultsSearch />)
            }
        }

        /* if api is loading show spinner and loading msg */
        if(this.props.searchResults.status === apiStatus.API_STATUS_LOADING) {
            results = (<LoadingResults />)
        }

        /* if api status is error show error msg */
        if(this.props.searchResults.status === apiStatus.API_STATUS_ERROR) {
            results = (<ErrorSearch />)
        }

        return (
            <div className={styles.Container}>
                <div className={styles.LeftPanel}>
                    <SearchComponent
                        className={styles.SearchComponent}
                        keepMemory />
                </div>
                <div className={styles.Results}>
                    {results}
               </div>
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        searchResults: state.searchResults,
    }
}

const mapDispatchToProps = dispatch => {
    return {
        searchDataUpdate: (location, serviceType, coords) => dispatch(searchDataActions.searchDataUpdate(location, serviceType, coords)),
        searchResultsUpdate: (serviceType, coords) => dispatch(searchResultsActions.searchResultsUpdate(serviceType, coords)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Search))