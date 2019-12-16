import React from 'react' 
import styles from './EmptyTable.module.css'
import Panel from '../../../UI/Panel/Panel'
import emptyImg from '../../../../assets/img/grua.png'

const emptyTable = (props) => {
    return (
        <div className={styles.EmptyTable}>
            <Panel className={styles.Panel}>
                <div className={styles.Title}>
                    <h3>{props.title}</h3>
                </div>
                <img className={styles.Img} src={emptyImg} alt="" />
                <p className={styles.Description}>{props.description}</p>
            </Panel>
        </div>
    )
}

export default emptyTable