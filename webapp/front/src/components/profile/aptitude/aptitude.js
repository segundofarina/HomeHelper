import React from 'react'
import Panel from '../../UI/Panel/Panel'
import styles from './aptitude.module.css'
import Calification from '../calification/calification'
import Review from '../../profile/review/review'
import Modal from 'react-modal'
import * as apiStatus from '../../../store/apiStatus'
import Loading from '../../Status/Loading/Loading'
import ConnectionError from '../../Status/ConnectionError/ConnectionError'
import emptyReviewsImg from '../../../assets/img/llaveFija.png'


const showReviews = (reviews, isOpen, showReviewsClick, hideReviewsClick) => {
    let firstReviews= reviews.filter((review, index) => { 
        return index < 3 
    }).map(review => {
        return(
            <div key={review.id}>
                <Review
                    name ={`${review.user.firstName} ${review.user.lastName}`}
                    date = {review.date}
                    rating ={review.scores.general}
                    description = {review.comment}
                    img = {review.user.pictureUrl}
                />
                <hr/>
            </div>
        )
    })
    let allReviews = reviews.map((review) => {
        return(
            <div key={review.id}>
                <Review
                    name ={`${review.user.firstName} ${review.user.lastName}`}
                    date = {review.date}
                    rating ={review.scores.general}
                    description = {review.comment}
                    img = {review.user.pictureUrl}
                />
                <hr/>
            </div>
        )
    })

    return (
        <div>
                
            {firstReviews}
            {reviews.length > 2 && (<button onClick={showReviewsClick} className={styles.ShowMoreButton}>Mostrar mas opiniones...</button>)}
            <Modal
                isOpen={isOpen}
                ariaHideApp={false}
                className={styles.Modal}
                overlayClassName={styles.ModalOverlay}
            >
            <div className={styles.ShowReviewsHeader}>
                <h3>Opiniones de clientes</h3>
                <button onClick={hideReviewsClick}>X</button>
            </div>
                
                <hr/>
                {allReviews}
            </Modal>
        </div>        
    )
}


const aptitude = (props)=>{

    let reviewsElem = (<Loading panelStyles={styles.StatusPanel} />)
    if(props.reviewsApiStatus === apiStatus.API_STATUS_ERROR) {
        reviewsElem = (<ConnectionError panelStyles={styles.StatusPanel} reconnectHandler={props.reviewsReconnect} />)
    }
    if(props.reviewsApiStatus === apiStatus.API_STATUS_DONE) {
        reviewsElem = (
            <Panel>
                {showReviews(props.reviews, props.showReviews, props.showMoreReviewsClick, props.closeReviewsClick)}
            </Panel>
        )
        if(props.reviews.length === 0) {
            reviewsElem = (
                <Panel>
                    <div className={styles.NoReviewsContainer}>
                        <img src={emptyReviewsImg} alt="" className={styles.NoReviewsImg} />
                        <p className={styles.NoReviewsDescription}>No reviews yet...</p>
                    </div>
                </Panel>
            )
        }
    }

    return(
        <div>
            <div>
                <div className={styles.Header}>
                    <h3>{props.serviceType.name}</h3>
                </div>
                <div className={styles.Container}>
                    <Panel className={styles.Description}>
                        <p>{props.description}</p>
                    </Panel>
                    {props.calification.general>0 ? <div className={styles.Calification}><Calification {...props.calification}/></div>: null}
                </div>
            </div>
            <div>
                <h4>Opiniones de clientes</h4>
                {reviewsElem}
           </div>
        </div>
    )}

export default aptitude