import React from 'react'
import styles from './summary.module.css'
import Rating from '../../UI/rating/Rating'
const summary = (props)=>{
    return(
        <div className={styles.Container}>
            <div className={styles.Img}>
                <img src={props.img} alt="" />
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