import React from 'react'
import styles from './ReviewItem.module.css'
import StarRatings from 'react-star-ratings'

const reviewItem = (props) => {
    return (
        <div className={styles.ReviewItem}>
            <h5>{props.label}</h5>
            <StarRatings starDimension='20px'
                        starSpacing='0px'
                        rating={props.rating}
                        changeRating={props.handleChangeRating}
                        starHoverColor='rgb(41,188,161)'
                        starRatedColor='rgb(58,58,58)' />
        </div>
    )
}

export default reviewItem