import React from 'react'
import styles from './EmptySearch.module.css'
import Panel from '../../UI/Panel/Panel'
import img from '../../../assets/img/searchEmpty.png'//Change img

const emptySearch = () => (
    <Panel className={styles.Panel}>
        <img src={img} alt="" className={styles.Img} />
        <p className={styles.Description}>Por favor realiza una busqueda</p>
    </Panel>
)

export default emptySearch