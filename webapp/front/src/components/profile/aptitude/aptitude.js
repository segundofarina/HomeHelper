import React from 'react'
import Panel from '../../UI/Panel/Panel'
import styles from './aptitude.module.css'
import Calification from '../calification/calification'
import Review from '../../profile/review/review'
import defaultImg from '../../../assets/img/defaultProfile.png'
import Modal from 'react-modal'


const showReviews = (reviews, isOpen,showReviewsClick,hideReviewsClick)=>{
    let firstReviews= reviews.filter((review, index)=>{return index<3}).map(review => {
        return(
            <div key={review.id}>
                <Review
                    name = {review.name}
                    date = {review.date}
                    rating ={review.rating}
                    description = {review.description}
                    img = {review.img}
                />
                <hr/>
            </div>
        )
    })
    let allReviews = reviews.map((review) => {
        return(
            <div key={review.id}>
                <Review
                    name = {review.name}
                    date = {review.date}
                    rating ={review.rating}
                    description = {review.description}
                    img = {review.img}
                />
                <hr/>
            </div>
        )
    })

    return (
        <div>
                
            {firstReviews}
            <button onClick={showReviewsClick} className={styles.ShowMoreButton}>Mostrar mas opiniones...</button>
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
    const description = "Realizo la ronovacion del cableado de toda la casa, colocando el tablero nuevo con varios circuitos. Estamos muy conformes con el trabajo y con la predisposición ante todos los pedidos. Saludos y muchas gracias."
    const reviews = [{id: 1,name: "Marcos Lopez", date:"16/05/2018", rating:4, description: description, img: defaultImg},
                     {id: 2,name: "Juan Perez", date:"16/05/2018", rating:4, description: description, img: defaultImg},
                     {id: 3,name: "Cachito Ruiz", date:"16/05/2018", rating:4, description: description, img: defaultImg},
                     {id: 4,name: "Bochita Leto", date:"16/05/2018", rating:4, description: description, img: defaultImg},
                     {id: 5,name: "Jorge Suarez", date:"16/05/2018", rating:4, description: description, img: defaultImg},
                     {id: 6,name: "Miguel Romanerolo", date:"16/05/2018", rating:4, description: description, img: defaultImg},
                     {id: 7,name: "Mariana Remarta", date:"16/05/2018", rating:4, description: description, img: defaultImg},
                     {id: 8,name: "Jorgelina Juarez", date:"16/05/2018", rating:4, description: description, img: defaultImg},
                     {id: 9,name: "Cristina Rombadina", date:"16/05/2018", rating:4, description: description, img: defaultImg}]                    
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
                <Panel>
                    {showReviews(reviews,props.showReviews,props.showMoreReviewsClick,props.closeReviewsClick)}
                </Panel>
            </div>
        </div>
    )}

export default aptitude