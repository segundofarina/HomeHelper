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

class Profile extends Component {
    state = {
        showingOtherApitutdes : false,
        showReviews: false,
        idShowing: null,
        loading: true, 
        error: false,
    }
    showReviewsHandler = (id)=>{
        this.setState({
            showReviews : true,
            idShowing: id,
        })

    }

    closeReviewsHandler = ()=>{
        this.setState({showReviews : false})
    }

    toggleClick = ()=>{
        this.setState({showingOtherApitutdes: !this.state.showingOtherApitutdes})
    }

    showMoreAptitudes = (aptitudes)=>{
        const show = "Mostrar otras aptitudes"
        const hide = "Ocultar otras aptitudes"
        let results = aptitudes.map(aptitude => {
            return(<Aptitude
                {...aptitude}
                showReviesId= {this.state.idShowing}
                showReviews = {this.state.showReviews}
                showMoreReviewsClick = {this.showReviewsHandler.bind(this,aptitude.id)}
                closeReviewsClick = {this.closeReviewsHandler}
                key = {aptitude.id}
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

        )}

    componentDidMount (){
        const queries = queryString.parse(this.props.location.search)
        if(!queries.id){
            this.setState({
                error:true,
                loading:false
            })
            return
        }
        const provider = this.props.providers.filter(provider => provider.id === parseInt(queries.id))
        if(provider[0]){
            this.props.updateProfile(provider[0])
            
        }else{
            this.props.profileInit(queries.id)
        }

        this.setState({loading:false})
    }



    render(){
        if(this.state.loading || this.props.apiStatus === apiStatus.API_STATUS_LOADING){
            return (<Loading/>)
        }
        if(this.state.error ){
            return(<div>BAD REQUEST</div>)
        }
        if(this.props.apiStatus === apiStatus.API_STATUS_ERROR){
            return (<ConnectionError/>)
        }
        const coordenates = [{lat: -34.557176, lng: -58.430436},
            {lat: -34.575376, lng: -58.403839},
            {lat: -34.588696, lng: -58.431428}];


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


        return (<div>
            <Summary name={`${provider.firstName} ${provider.lastName}`} serviceTypes={serviceTypes.join(", ")} rating={provider.generalCalification} img={defaultImg}/>
            <div className={styles.MainContainer}>
                <div className={styles.Contact}>
                    <Contact
                        serviceTypesOptions={serviceTypesOptions}
                        providerName = {provider.firstName}
                        defaultServiceType = {defaultServiceType}
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
                            showReviesId= {this.state.idShowing}
                            showMoreReviewsClick = {this.showReviewsHandler.bind(this,firstAptitude.id)}
                            closeReviewsClick = {this.closeReviewsHandler}
                            key = {provider.aptitudes[0].id}
                        />
                        
                        {otherAptitudes.length>0 ? this.showMoreAptitudes(otherAptitudes): null}
                    </div>
                    <div>
                        <h3>Area de trabajo</h3>
                        <Panel>
                            <WorkingZone
                                coordenates={coordenates}
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