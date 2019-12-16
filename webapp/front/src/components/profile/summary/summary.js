import React from 'react'
import styles from './summary.module.css'
import Rating from '../../UI/rating/Rating'
import defaultImg from '../../../assets/img/defaultProfile.png'
const summary = (props)=>{
    return(
        <div className={styles.Container}>
        
            <div className={styles.Img}>
            <img src={props.img} 
            onError={(ev)=>ev.target.src = defaultImg} 
            className={styles.ProfilePicture}/>
            </div>
            <div className={styles.Name}>
                {props.name}
            </div>
            <div className={styles.ServiceTypes}>
                {props.serviceTypes}
            </div>
            <Rating value={props.rating}/>
        </div>
    )
}

export default summary