import React from 'react'
import styles from './Input.module.css'

const input = (props) => {
    let inputElem = null
    const {inputType, label, ...inputProps} = props

    switch(props.inputType) {
        case ('input'):default:
            inputElem = (<input {...inputProps} className={[styles.inputElem, props.className].join(' ')} />)
            break
        case ('textarea'):
            inputElem = (<textarea {...inputProps} className={[styles.textAreaElem, props.className].join(' ')}>{props.children}</textarea>)
            break
        case ('select'):
            inputElem = (<select value={props.value}
                            className={[styles.inputElem, props.className].join(' ')}
                            onChange={props.onChange}>
                    <option value={props.defaultValue.value}>{props.defaultValue.name}</option>
                    {props.options.map(option => (
                        <option value={option.value} key={option.value}>{option.name}</option>
                    ))}
                </select>)
            break
    }

    let labelElem = null
    if(props.label) {
        labelElem = (<label>{props.label}</label>)
    }

    let validationError = null
    if(props.validationError) {
        validationError = (<p className={styles.ValidationError}>{props.validationError}</p>)
    }

    return (
        <div className={[styles.formGroup, props.groupstyle].join(' ')}>
            {labelElem}
            {inputElem}
            {validationError}
        </div>
    )
}

export default input