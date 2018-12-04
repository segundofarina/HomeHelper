import React from 'react'
import styles from './LoadingResults.module.css'
import Panel from '../../UI/Panel/Panel'

const loadingResults = () => (
    <Panel className={styles.Panel}>
        <div className={styles.Loader}></div>
        <p className={styles.Description}>Loading...</p>
    </Panel>
)

export default loadingResults