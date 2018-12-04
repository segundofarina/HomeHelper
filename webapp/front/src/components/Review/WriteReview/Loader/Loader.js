import React from 'react'
import styles from './Loader.module.css'

const loader = () => (
    <div className={styles.Container}>
        <div className={styles.Loader}></div>
        <p className={styles.Description}>Loading...</p>
    </div>
)

export default loader