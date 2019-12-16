import React, { Component } from 'react'
import Summary from '../../components/profile/summary/summary'
import defaultImg from '../../assets/img/defaultProfile.png'
import styles from './profile.module.css'
import Contact from '../../components/profile/contact/contact'
import Aptitude from '../../components/profile/aptitude/aptitude'
import WorkingZone from '../../components/profile/workingZone/workingZone'
import Panel from '../../components/UI/Panel/Panel'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faAngleDown } from '@fortawesome/free-solid-svg-icons'
import {connect} from 'react-redux'
import * as ProfileActions from '../../store/actions/profileActions'
import * as apiStatus from '../../store/apiStatus'
import {withRouter} from 'react-router-dom'
import queryString from 'query-string'
import Loading from '../../components/Status/Loading/Loading'
import ConnectionError from '../../components/Status/ConnectionError/ConnectionError'
import axios from 'axios'
import BadRequest from '../../components/Errors/BadRequest/BadRequest'

class Profile extends Component {
    state = {
        showingOtherApitutdes : false,
        showReviews: false,
        idShowing: null,
        loading: true, 
        error: false,
        reviews: [],
        reviewsApiStatus: apiStatus.API_STATUS_NONE,
        providerId: '',
        searchedAddress: '',
    }

    showReviewsHandler = (id) => {
        this.setState({
            showReviews : true,
            idShowing: id,
        })
    }

    closeReviewsHandler = () => {
        this.setState({showReviews : false})
    }

    toggleClick = () => {
        this.setState({showingOtherApitutdes: !this.state.showingOtherApitutdes})
    }

    showMoreAptitudes = (aptitudes) => {
        const show = "Mostrar otras aptitudes"
        const hide = "Ocultar otras aptitudes"
        let results = aptitudes.map(aptitude => {
            let reviews = this.state.reviews.filter(aptitudeReviews => {
                return aptitudeReviews.aptitudeId === aptitude.id
            })
            if(reviews.length > 0) {
                reviews = reviews[0].reviews
            }

            return(<Aptitude
                {...aptitude}
                showReviesId= {this.state.idShowing}
                showReviews = {this.state.showReviews}
                showMoreReviewsClick = {this.showReviewsHandler.bind(this,aptitude.id)}
                closeReviewsClick = {this.closeReviewsHandler}
                key = {aptitude.id}
                reviews={reviews}
                reviewsApiStatus={this.state.reviewsApiStatus}
                reviewsReconnect={() => this.fetchReviews(this.state.providerId)}
                />)
        })
        
        return(
            <div>
                <div onClick={this.toggleClick} className={styles.ShowMore}>
                    <h4>{this.state.showingOtherApitutdes ? hide : show}</h4>
                    <FontAwesomeIcon icon={faAngleDown}/>
                </div>
                {this.state.showingOtherApitutdes? results:null}
            </div>
        )
    }

    fetchReviews = async (providerId) => {
        this.setState({reviewsApiStatus: apiStatus.API_STATUS_LOADING})
        try {
            const response = await axios.get(`/providers/${providerId}/reviews`)
            this.setState({
                reviewsApiStatus: apiStatus.API_STATUS_DONE,
                reviews: response.data.reviews,
            })
        } catch (error) {
            console.log(error)
            this.setState({reviewsApiStatus: apiStatus.API_STATUS_ERROR})
        }
    }

    componentDidMount (){
        const queries = queryString.parse(this.props.location.search)
        let validAddr = true
        try {
            atob(queries.addr)
        } catch(error) {
            validAddr = false
        }
        if(!queries.id || !queries.addr || !validAddr){
            this.setState({
                error:true,
                loading:false,
            })
            return
        }
        const provider = this.props.providers.filter(provider => provider.id === parseInt(queries.id))
        if(provider[0]){
            this.props.updateProfile(provider[0])
        }else{
            this.props.profileInit(queries.id)
        }

        this.setState({loading:false, providerId: parseInt(queries.id), searchedAddress: atob(queries.addr)})

        this.fetchReviews(parseInt(queries.id));
    }

    render(){
        if(this.state.loading || this.props.apiStatus === apiStatus.API_STATUS_LOADING){
            return (<Loading/>)
        }
        if(this.state.error) {
            return(<BadRequest />)
        }
        if(this.props.apiStatus === apiStatus.API_STATUS_ERROR){
            return (<ConnectionError/>)
        }

        const provider = this.props.provider
        const searched = provider.aptitudes.filter((aptitude) => parseInt(aptitude.serviceType.id) === parseInt(this.props.serviceTypeSelected)).find(()=>true)
        const firstAptitude = searched ? searched : provider.aptitudes.find(()=>true)
        const otherAptitudes = provider.aptitudes.filter((aptitude) => parseInt(aptitude.serviceType.id) !== parseInt(firstAptitude.serviceType.id))

        const serviceTypes = provider.aptitudes.map(apt => {
            return apt.serviceType.name
        })
        const defaultServiceType = {value: firstAptitude.serviceType.id ,name: firstAptitude.serviceType.name}
        const serviceTypesOptions = otherAptitudes.map(apt => {
            return {value:apt.serviceType.id , name:apt.serviceType.name}
        })

        let firstAptitudeReviews = this.state.reviews.filter(aptitudeReviews => {
            return aptitudeReviews.aptitudeId === firstAptitude.id
        })
        if(firstAptitudeReviews.length > 0) {
            firstAptitudeReviews = firstAptitudeReviews[0].reviews
        }
 
         return (<div className={styles.Profile}>
            <Summary name={`${provider.firstName} ${provider.lastName}`} serviceTypes={serviceTypes.join(", ")} rating={provider.generalCalification} img={provider.pictureUrl}/>
            <div className={styles.MainContainer}>
                <div className={styles.Contact}>
                    <Contact
                        serviceTypesOptions={serviceTypesOptions}
                        providerName = {provider.firstName}
                        defaultServiceType = {defaultServiceType}
                        provider={{
                            firstName: this.props.provider.firstName,
                            lastName: this.props.provider.lastName,
                            imgUrl: 'toBeCompleted in Profile.js',
                            score: this.props.provider.generalCalification,
                            id: this.props.provider.id,
                        }}
                        searchedAddress={this.state.searchedAddress}
                    />
                </div>
                <div className={styles.Content}>
                    <div className ={styles.GeneralDescription}>
                        <p>
                            {provider.description}
                        </p>
                    </div>
                    <div >
                        <Aptitude
                            {...firstAptitude}
                            showReviews = {this.state.showReviews}
                            showReviewsId= {this.state.idShowing}
                            showMoreReviewsClick = {this.showReviewsHandler.bind(this,firstAptitude.id)}
                            closeReviewsClick = {this.closeReviewsHandler}
                            key = {provider.aptitudes[0].id}
                            reviews={firstAptitudeReviews}
                            reviewsApiStatus={this.state.reviewsApiStatus}
                            reviewsReconnect={() => this.fetchReviews(this.state.providerId)}
                        />
                        
                        {otherAptitudes.length>0 ? this.showMoreAptitudes(otherAptitudes): null}
                    </div>
                    <div>
                        <h3>Area de trabajo</h3>
                        <Panel>
                            <WorkingZone
                                coordenates={provider.coordenates}
                            />
                        </Panel>
                    </div>
                    
                </div>
            </div>
        </div>
        )}
}

const mapStateToProp = (state) =>{
    return {
        provider: state.profile.provider,
        providers: state.searchResults.providers,
        apiStatus: state.profile.status,
        serviceTypeSelected: state.searchData.serviceType,
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        updateProfile: (provider) =>dispatch(ProfileActions.updateProfile(provider)),
        profileInit: (id) => dispatch(ProfileActions.profileInit(id)),

    }
}



export default connect(mapStateToProp,mapDispatchToProps)(withRouter(Profile)) 