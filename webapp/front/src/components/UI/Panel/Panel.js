import React from 'react'
import styles from './Panel.module.css'

const panel = (props) => (
    <div className={[styles.panel,props.className].join(' ')}>
        {props.children}
    </div>
)

export default panel