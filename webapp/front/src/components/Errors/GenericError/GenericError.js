import React from 'react'
import styles from './GenericError.module.css'
import Button from '../../UI/Button/Button'

const genericError = (props) => {
    return (
        <div className={styles.ErrorContainer}>
            <div className={styles.Img}></div>
            <div className={styles.ErrorNum}>
                <h1>{props.number}</h1>
            </div>
            <h3 className={styles.ErrorDescription}>{props.description}</h3>
            <Button btnImpl='Link' to='/' className={styles.Button}>Go back home</Button>
        </div>
    )
}

export default genericError