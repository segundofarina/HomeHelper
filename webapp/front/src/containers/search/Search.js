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

class Search extends Component {

    render () {
        /* if api status is none the user enter the page without pressing search btn.
            show error msg asking to do a search */
        let results = (<EmptySearch />)

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
                            img={defaultImg}
                            calification={provider.generalCalification}
                            id={provider.id}
                            key={provider.id}
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

export default connect(mapStateToProps)(Search)