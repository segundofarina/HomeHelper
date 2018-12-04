import React from 'react'
import styles from './ScoreRow.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faChevronUp } from '@fortawesome/free-solid-svg-icons'
import { faChevronDown } from '@fortawesome/free-solid-svg-icons'
import { faStar } from '@fortawesome/free-solid-svg-icons'
import ScoreDetailItem from './ScoreDetailItem/ScoreDetailItem'

const scoreRow = (props) => {
    const elemStyles = [styles.ScoreRow]
    if(props.className) {
        elemStyles.push(props.className)
    }

    let arrowIcon = (<FontAwesomeIcon icon={faChevronDown} />)
    let content = null
    if(props.showingDetails) {
        arrowIcon = (<FontAwesomeIcon icon={faChevronUp} />)
        
        content = (
            <div className={styles.Content}>
                <ScoreDetailItem type={5} value={props.details._5} total={props.total} />
                <ScoreDetailItem type={4} value={props.details._4} total={props.total} />
                <ScoreDetailItem type={3} value={props.details._3} total={props.total} />
                <ScoreDetailItem type={2} value={props.details._2} total={props.total} />
                <ScoreDetailItem type={1} value={props.details._1} total={props.total} />
            </div>
        )
    }

    const totalValue = ((props.details._1 + props.details._2 * 2 + props.details._3 * 3 + props.details._4 * 4 + props.details._5 * 5) / props.total).toFixed(1)

    return (
        <div className={elemStyles.join(' ')}>
            <div className={styles.HeaderContainer} onClick={props.onClick}>
                <p className={styles.Description}>{props.description}</p>
                <div className={styles.TotalNumber}>
                    <strong>{totalValue}</strong>
                    <FontAwesomeIcon icon={faStar} className={styles.Star} />
                    {arrowIcon}
                </div>
            </div>
            {content}
       </div>
    )
}

export default scoreRow