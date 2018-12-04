import React from 'react'
import styles from './ErrorSearch.module.css'
import Panel from '../../UI/Panel/Panel'
import img from '../../../assets/img/searchEmpty.png'//Change img

const errorSearch = () => (
    <Panel className={styles.Panel}>
        <img src={img} alt="" className={styles.Img} />
        <p className={styles.Description}>Parece que no pudimos establecer conexion con el servidor</p>
    </Panel>
)

export default errorSearch