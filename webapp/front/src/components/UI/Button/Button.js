import React from 'react'
import { Link } from 'react-router-dom'
import styles from './Button.module.css'

const Button = (props) => {
    const btnStyles = [styles.button, styles[props.btnType], styles[props.btnColor], props.className].join(' ')

    if(props.btnImpl === "Link") {
        return (<Link to={props.to} className={btnStyles}>{props.children}</Link>)
    }

    return (
        <button onClick={props.onClick} className={btnStyles}>{props.children}</button>
    )
}

export default Button