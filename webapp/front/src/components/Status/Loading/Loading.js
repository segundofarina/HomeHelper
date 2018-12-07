import React from 'react'
import styles from './Loading.module.css'
import Panel from '../../UI/Panel/Panel'

const loading = (props) => {
    const panelStyles = [styles.Panel]
    if(props.panelStyles) {
        panelStyles.push(props.panelStyles)
    }
    return (
        <div className={styles.StatusContainer}>
            <Panel className={panelStyles.join(' ')}>
                <div className={styles.Loader}></div>
                <p className={styles.Description}>Loading...</p>
            </Panel>
        </div>
    )
}

export default loading