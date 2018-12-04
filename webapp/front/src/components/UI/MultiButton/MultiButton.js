import React from 'react'
import styles from './MultiButton.module.css'

/* Gets by props.elements an array of objects. ie:
 * {
 *      id: 1,
 *      text: 'text',
 *      onClick: () => {},
 * }
 * 
 * props.active is the id of the active button
 *  */
const multiButton = (props) => {
    const elemStyles = [styles.MultiButton, props.className]

    const buttons = props.elements.map(button => {
        const btnStyles = [styles.Button]
        if(button.id === props.active) {
            btnStyles.push(styles.Active)
        }

        return (
            <div className={btnStyles.join(' ')} onClick={button.onClick} key={button.id}>
                {button.text}
            </div>
        )
    })

    return (
        <div className={elemStyles.join(' ')}>
            {buttons}
        </div>
    )
}

export default multiButton