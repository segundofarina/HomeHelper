import React from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faStar } from '@fortawesome/free-solid-svg-icons'
import { faStarHalfAlt } from '@fortawesome/free-solid-svg-icons'
import { faStar as farStar } from '@fortawesome/free-regular-svg-icons'
import styles from './Rating.module.css'

const rating = (props) => {
    const value = props.value
    
    if(value === 0) {
        return (<p className={styles.emptyCalification}>Aun no hay calificaciones</p>)
    }

    let rating = []
    let stars= parseInt(value,10) 
    
    let half 
    if(value - stars < 0.25) {
        half = false
    } else if(value - stars < 0.75) {
        half = true
    } else {
        half = false
        stars += 1
    }

    let pushedHalfStar = false
    
    for (let i = 0; i < 5; i++) {
        if(i < stars) {
            rating.push(<FontAwesomeIcon icon={faStar} key={i} />)
        } else if( half && !pushedHalfStar ) {
            rating.push(<FontAwesomeIcon icon={faStarHalfAlt} key={i} />)
            pushedHalfStar = true
        } else {
            rating.push(<FontAwesomeIcon icon={farStar} key={i} />)
        }
    }
    

    return (
        <div className={props.className}>
            {rating}
        </div>
    )
}

export default rating
