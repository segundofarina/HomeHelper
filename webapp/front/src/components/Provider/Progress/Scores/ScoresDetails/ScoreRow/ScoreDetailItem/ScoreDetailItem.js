import React from 'react'
import styles from './ScoreDetail.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faStar } from '@fortawesome/free-solid-svg-icons'
import ProgressBar from '../../../../../../UI/progressBar/progressBar'

const scoreDetailItem = (props) => {
    const percentage = Math.round(100*props.value/props.total)
    return (
        <div className={styles.ScoreDetailItem}>
            <p className={styles.Type}><strong>{props.type}</strong></p>
            <FontAwesomeIcon icon={faStar} className={styles.Star} />
            <ProgressBar value={percentage} className={styles.ProgressBar} />
            <p className={styles.Percentage}>{percentage}%</p>
        </div>
    )
}

export default scoreDetailItem