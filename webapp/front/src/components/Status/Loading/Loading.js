import React from 'react'
import styles from './Loading.module.css'
import Panel from '../../UI/Panel/Panel'

const loading = () => {
    return (
        <div className={styles.StatusContainer}>
            <Panel className={styles.Panel}>
                <div className={styles.Loader}></div>
                <p className={styles.Description}>Loading...</p>
            </Panel>
        </div>
    )
}

export default loading